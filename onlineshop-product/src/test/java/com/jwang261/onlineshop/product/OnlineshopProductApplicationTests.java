package com.jwang261.onlineshop.product;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.onlineshop.product.entity.BrandEntity;
import com.jwang261.onlineshop.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

@SpringBootTest
class OnlineshopProductApplicationTests {

    @Autowired
    BrandService brandService;

    public final static String ROOT_ACCESS_KEY_ID = "AKIAJ7M5VNWQ64A3LM7A";
    public final static String ROOT_SECRET_ACCESS_KEY = "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";
    public final static String ACCESS_KEY_ID = "AKIAUUPELXEH3OA2LSOI";
    public final static String SECRET_ACCESS_KEY = "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";

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
    public void test2() throws IOException{
        File uploadFile = new File("/Users/kotarooshio/Downloads/IMG_1967.PNG");
        String uploadKey = "hello";
        upload(uploadFile,uploadKey);
        //upload01(uploadFile,uploadKey);



    }

    @Test
    public void test() throws IOException {
        String clientRegion = "us-east-2";
        //Regions clientRegion = "us-east-2";
        String bucketName = "onlineshop-5";
        String stringObjKeyName = "ImageString";
        String fileObjKeyName = "ImageFile";
        String fileName = "~/Downloads/image-20200725231837424.png";

        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);

        AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
        conn.setEndpoint("http://s3.us-east-2.amazonaws.com");
        Bucket bucket = conn.createBucket(bucketName);
        ObjectListing objects = conn.listObjects(bucket.getName());
        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println(objectSummary.getKey() + "\t" +
                        objectSummary.getSize() + "\t" +
                        StringUtils.fromDate(objectSummary.getLastModified()));
            }
            objects = conn.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());





        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html

            // Upload a text string as a new object.
            conn.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            conn.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }

//        String clientRegion = "us-east-2";
//        String bucketName = "onlineshop-5";
//        String keyName = "abc/demo.txt";
//        String filePath = "~/Downloads/image-20200725231837424.png";
//
//        try {
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withRegion(clientRegion)
//                    .withCredentials(new ProfileCredentialsProvider())
//                    .build();
//            TransferManager tm = TransferManagerBuilder.standard()
//                    .withS3Client(s3Client)
//                    .build();
//            Upload upload = tm.upload(bucketName, keyName, new File(filePath));
//            System.out.println("Object upload started");
//            upload.waitForCompletion();
//            tm.shutdownNow();
//            System.out.println("Object upload complete");
//        }
//        catch(AmazonServiceException e) {
//            e.printStackTrace();
//        }
//        catch(SdkClientException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    @Test
    void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//
//        brandEntity.setName("Peach");
//        brandService.save(brandEntity);
//        System.out.println("Save Successfully");
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach((item)->{
            System.out.println(item);
        });
    }

    private static ByteBuffer getRandomByteBuffer(int size) throws IOException {
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }
}
