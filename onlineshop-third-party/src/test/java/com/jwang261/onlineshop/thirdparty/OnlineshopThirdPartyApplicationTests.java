package com.jwang261.onlineshop.thirdparty;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.URL;

@SpringBootTest
class OnlineshopThirdPartyApplicationTests {

    public final static String ROOT_ACCESS_KEY_ID = "";
    public final static String ROOT_SECRET_ACCESS_KEY = "";

    public final static String ACCESS_KEY_ID = "";
    public final static String SECRET_ACCESS_KEY = "";

    public static AmazonS3 s3Client;

    public final static String bucketName = "onlineshop-5";

    static {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        s3Client = AmazonS3Client.builder()
                .withRegion("us-east-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }


    public static String upload(File tempFile, String remoteFileName) throws FileNotFoundException {

        String bucketPath = bucketName + "/upload";
        s3Client.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
        URL url = s3Client.generatePresignedUrl(urlRequest);
//        FileInputStream fis = new FileInputStream("/Users/kotarooshio/Downloads/image-20200725231837424.png");
//        s3Client.putObject(new PutObjectRequest(bucketName, remoteFileName, fis,new ObjectMetadata()));
        return url.toString();

    }
    public static String upload01(File tempFile, String remoteFileName) {
        byte[] buf = "123".getBytes();
        InputStream stream = new ByteArrayInputStream(buf);
        ObjectMetadata metadata = new ObjectMetadata();

        //由于S3的存储相同路径的提交文件时覆盖操作，这里可以加入自定的文件路径创建规则
        String bucketPath = bucketName + "/upload" ;
        s3Client.putObject(new PutObjectRequest(bucketPath, remoteFileName, stream,metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
        URL url = s3Client.generatePresignedUrl(urlRequest);
        System.out.println(url.toString());
        return url.toString();
    }



    @Test
    public void test2() throws IOException {
        File uploadFile = new File("/Users/kotarooshio/Downloads/IMG_2007.JPG");
        String uploadKey = "SeTu02";
        upload(uploadFile,uploadKey);
        //upload01(uploadFile,uploadKey);



    }


    @Test
    void contextLoads() {
    }

}
