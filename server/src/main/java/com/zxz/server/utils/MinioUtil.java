package com.zxz.server.utils;

import com.zxz.server.pojo.MinioClientProp;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Slf4j
public class MinioUtil {

    @Autowired
    private MinioClientProp minioClientConfig;
    @Autowired
    private MinioClient minioClient;

    public StatObjectResponse getFileInfo(String objectName, String bucketName){
        try {
            if (StringUtils.isBlank(bucketName)){
                bucketName = minioClientConfig.getBucketName();
            } else {
                throw new Exception("操作失败！桶不存在！");
            }
            return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            return null;
        }
    }

    public void download(String objectName, String bucketName, String fileName,  HttpServletResponse response) throws Exception{
        if (StringUtils.isNotBlank(bucketName)){
            if (!this.bucketExists(bucketName)){
                throw new Exception("操作失败！桶不存在！");
            }
        } else {
            bucketName = minioClientConfig.getBucketName();
        }
        InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(stream, response.getOutputStream());
    }

    /**
     * 上传单个文件到指定的桶
     * @param file MultipartFile
     * @param bucketName 桶名称，如果不存在，则系统配置的桶
     * @throws Exception
     */
    public String upload(MultipartFile file, String bucketName) throws Exception{
        // 判断是否传入桶名称
        if (StringUtils.isNotBlank(bucketName)){
            if (!this.bucketExists(bucketName)){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } else {
            bucketName = minioClientConfig.getBucketName();
        }
        // 获取原始，名称
        String fileName = file.getOriginalFilename();
        // 截取新的名称
        String objectName = UUID.randomUUID().toString().replaceAll("-", "") +
                fileName.substring(fileName.lastIndexOf("."), fileName.length());
        // 日期前缀
        String folderPrefix = this.timeFolder();
        objectName = folderPrefix + "/" +objectName;
        // 上传
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs
                .builder().method(Method.GET).bucket(bucketName).object(objectName).build());
        // 原始路径为 ip:port/bucketName/objectName?minio自带参数
        // 截取后只保留 ip:port/bucketName/objectName
        url = url.split("\\?")[0];
        return url;
    }

    /**
     * 上传多个文件到系统配置的桶
     * @param files MultipartFile
     * @param bucketName 桶名称，如果不存在，则系统配置的桶
     * @throws Exception
     */
    public List<String> uploadBatch(MultipartFile[] files, String bucketName) throws Exception{
        // 判断是否传入桶名称
        if (StringUtils.isNotBlank(bucketName)){
            if (!this.bucketExists(bucketName)){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } else {
            bucketName = minioClientConfig.getBucketName();
        }
        String finalBucketName = bucketName;
        List<String> urlList = Collections.synchronizedList(new ArrayList<>());
        Arrays.asList(files).parallelStream().forEach(file -> {
            try {
                String filePath = this.upload(file, finalBucketName);
                urlList.add(filePath);
            } catch (Exception e) {
                log.info("文件上传失败，文件名：{}",file.getOriginalFilename());
                throw new RuntimeException("文件上传失败", e);
            }
        });
        return urlList;
    }

    /**
     * 从指定的桶删除文件
     * @param objectName 文件名称
     * @param bucketName 桶名称，如果不存在，则系统配置的桶
     * @throws Exception
     */
    public void delete(String objectName, String bucketName) throws Exception{
        // 判断是否传入桶名称
        if (StringUtils.isBlank(bucketName)){
            bucketName = minioClientConfig.getBucketName();
        } else {
            if (!this.bucketExists(bucketName)){
                throw new Exception("操作失败！桶不存在！");
            }
        }
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 批量从指定的桶删除文件
     * @param objectNames 文件名称
     * @param bucketName 桶名称，如果不存在，则系统配置的桶
     * @throws Exception
     */
    public void deleteBatch(String[] objectNames, String bucketName) throws Exception{
        // 判断是否传入桶名称
        if (StringUtils.isBlank(bucketName)){
            bucketName = minioClientConfig.getBucketName();
        } else {
            if (!this.bucketExists(bucketName)){
                throw new Exception("操作失败！桶不存在！");
            }
        }
        String finalBucketName = bucketName;
        Arrays.stream(objectNames).parallel().forEach(objectName ->{
            try {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(finalBucketName).object(objectName).build());
            } catch (Exception e) {
                log.info("文件删除失败，文件名：{}", objectName);
                throw new RuntimeException("文件上传失败", e);
            }
        });
    }

    /**
     * 获取当前时间转换成字符串
     * @return
     */
    private String timeFolder(){
        LocalDate time = LocalDate.now();
        String format = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return format;
    }

    /**
     * 判断是否存在传入的桶，不存在创建
     * @param bucketName
     * @throws IOException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws InsufficientDataException
     * @throws NoSuchAlgorithmException
     * @throws ServerException
     * @throws InternalException
     * @throws XmlParserException
     * @throws ErrorResponseException
     */
    private Boolean bucketExists(String bucketName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException,
            NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        return found;
    }
}