package com.example.PING.image;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
//    @Value("${cloud.aws.credentials.accessKey}")
    @Value("${AWS_ACCESS_KEY}")
    private String accessKey;
//    @Value("${cloud.aws.credentials.secretKey}")
    @Value("${AWS_SECRET_KEY}")
    private String secretKey;
//    @Value("${cloud.aws.region.static}")
    @Value("${AWS_REGION}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

}