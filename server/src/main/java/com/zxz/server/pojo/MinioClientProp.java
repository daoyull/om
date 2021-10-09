package com.zxz.server.pojo;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Slf4j
@Data
public class MinioClientProp implements InitializingBean {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private Boolean userSSL;
    private String bucketName;

    @Bean
    public MinioClient minioClient() {

        MinioClient minioClient;
        try {
            // 创建minioClient 
            minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
            // 判断是否存在桶，不存在则创建桶
//            boolean hasBucket = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//            if (!hasBucket) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("minioClient初始化失败");
        }
        return minioClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("-------minio初始化完成---------");
    }
}