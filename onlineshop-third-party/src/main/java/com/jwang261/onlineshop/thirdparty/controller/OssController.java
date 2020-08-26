package com.jwang261.onlineshop.thirdparty.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jwang261.common.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author jwang261
 * @date 2020/8/24 8:54 PM
 */
@RestController
public class OssController {
    @Value("${my.s3.access}")
    private String ACCESS_KEY_ID;
    @Value("${my.s3.secret}")
    private String SECRET_ACCESS_KEY;
    private AmazonS3 s3Client;
    @Value("${my.s3.bucket}")
    private String BUCKET_NAME = "onlineshop-5";
    @Value("${my.s3.region}")
    private String REGION;

    private String path;

    private String fileName;

    //https://onlineshop-5.s3.us-east-2.amazonaws.com/upload/SeTu02
//https://onlineshop-5.s3.us-east-2.amazonaws.com/2020-08-25/SeTu02




    public String upload(File tempFile, String remoteFileName) throws FileNotFoundException {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        s3Client = AmazonS3Client.builder()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        String bucketPath = BUCKET_NAME + path;
        s3Client.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, remoteFileName);

        URL url = s3Client.generatePresignedUrl(urlRequest);
        String[] s = url.toString().split("\\?");

//        FileInputStream fis = new FileInputStream("/Users/kotarooshio/Downloads/image-20200725231837424.png");
//        s3Client.putObject(new PutObjectRequest(bucketName, remoteFileName, fis,new ObjectMetadata()));
        return s[0];

    }
    public String upload01(File tempFile, String remoteFileName) {
        byte[] buf = "123".getBytes();
        InputStream stream = new ByteArrayInputStream(buf);
        ObjectMetadata metadata = new ObjectMetadata();

        //由于S3的存储相同路径的提交文件时覆盖操作，这里可以加入自定的文件路径创建规则
        String bucketPath = BUCKET_NAME + "/" + path ;
        s3Client.putObject(new PutObjectRequest(bucketPath, remoteFileName, stream,metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, remoteFileName);
        URL url = s3Client.generatePresignedUrl(urlRequest);
        System.out.println(url.toString());
        return url.toString();
    }

    @PostMapping("/oss/upload")
    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getName();
        File uploadFile = transfer(file);


        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        path = "/" + format;
     //   File uploadFile = new File("/Users/kotarooshio/Downloads/IMG_2007.JPG");
        fileName += UUID.randomUUID().toString();
        String newUrl = upload(uploadFile, fileName);
        //upload01(uploadFile,uploadKey);

        int index = newUrl.lastIndexOf('/');
        String finalUrl = newUrl.substring(0, index) + path + newUrl.substring(index, newUrl.length());


        //System.out.println(finalUrl);
        return finalUrl;


    }

    private File transfer(MultipartFile file) throws IOException {
        File f = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = file.getInputStream();
            f = new File(file.getOriginalFilename());
            inputStreamToFile(ins, f);
        }
        return f;
    }

    public static void inputStreamToFile(InputStream ins,File file) {

        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hell0";
    }

    @RequestMapping("/oss/policy")
    public R policy(){
        return R.ok().put("msg","success");
    }
}
