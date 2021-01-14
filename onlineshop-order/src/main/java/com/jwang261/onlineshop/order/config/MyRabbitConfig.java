package com.jwang261.onlineshop.order.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author jwang261
 * @date 2021/1/12 11:50 PM
 */
@Configuration
public class MyRabbitConfig {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    /**
     * 定制rabbit template
     * 1、配置消息确认机制 publisher -> exchange
     * 2、设置消息回调 exchange <- queue 失败情况下才会回调的方法
     * 3、消费端确认 queue -> consumer 默认自动确认
     *      问题：n个消息处理到中间如果宕机，剩下的消息都会丢失
     *          在Listener/Handler里面用channel.ack()
     */
    @Bean
    @PostConstruct //类调用构造器创建完成以后，执行这个方法
    public void initRabbitTemplate(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 当前消息唯一关联数据(消息的唯一id)
             * @param ack 成功收到？
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                //只要抵达服务器，ack = true
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback(){
            /**
             * 消息抵达队列的确认(回调代表失败)
             * @param message 投递失败消息的详细信息
             * @param replyCode 回复状态码
             * @param replyText 回复的文本内容
             * @param exchange
             * @param routingKey
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                //
            }
        });
    }

}
