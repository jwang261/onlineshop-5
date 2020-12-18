package com.jwang261.onlineshop.product.web;

import com.jwang261.onlineshop.product.entity.CategoryEntity;
import com.jwang261.onlineshop.product.service.CategoryService;
import com.jwang261.onlineshop.product.vo.Catalog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author jwang261
 * @date 2020/9/1 4:00 PM
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;
    @GetMapping({"/", "index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson(){
        Map<String, List<Catalog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;
    }
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        RLock lock = redisson.getLock("my-lock");

        //lock.lock();

        lock.lock(10, TimeUnit.SECONDS); //10s自动解锁，自动解锁时间一定要大于业务执行时间
        //1、如果我们传递了锁的超时时间，就发送给Redis执行脚本进行占锁，默认超时是指定时间
        //2、如果未指定，就使用LockWatchDogTimeout - 看门狗默认时间 - 30s
        //只要占锁成功，就会启动一个定时任务 - 重新设置给锁的过期时间，新的过期时间就是看门狗的默认时间，
        //1/3的看门狗时间以后进行续期

        //最佳用法：
        //1、使用 lock.lock(10, TimeUnit.SECONDS); 省掉了续期操作，手动解锁
        try {
            System.out.println("枷锁");
            Thread.sleep(30000);
        }catch (Exception e){

        }finally {
            lock.unlock();
            System.out.println("释放");
        }
        return "hello";
    }

    @GetMapping("/write")
    @ResponseBody
    public String writeValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        RLock rLock = lock.writeLock();
        rLock.lock();
        try {
            s = UUID.randomUUID().toString();
            Thread.sleep(20000);
            redisTemplate.opsForValue().set("writeValue", s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }

    @GetMapping("/read")
    @ResponseBody
    public String readValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        RLock rLock = lock.readLock();
        rLock.lock();
        try {

            s = redisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }

    @GetMapping("/park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.acquire();//获取一个信号
        return "acquired";
    }

    @GetMapping("/go")
    @ResponseBody
    public String go() throws InterruptedException{
        RSemaphore park = redisson.getSemaphore("park");
        park.release();//释放一个车位
        return "released";
    }

    /**
     * close the classroom - all the students have left
     */
    @GetMapping("/lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);//等5个
        door.await();//等待闭锁完成

        return "classroom locked";
    }

    @GetMapping("/goOut/{id}")
    @ResponseBody
    public String goOut(@PathVariable("id") Long id){
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();//计数-1

        return id + "has left";
    }
}
