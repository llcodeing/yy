package com.example.demo.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class OssUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    /**
     * 上传文件到 OSS，返回可公开访问的 URL
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String objectName = "todos/" + UUID.randomUUID() + extension;

        // 创建 OSS 客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            PutObjectResult result = ossClient.putObject(bucketName, objectName, file.getInputStream());
            // 返回文件的公开访问 URL
            return "https://" + bucketName + "." + endpoint + "/" + objectName;
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 删除 OSS 文件
     */
    public void deleteFile(String fileUrl) {
        // 从 URL 中提取 objectName
        String prefix = "https://" + bucketName + "." + endpoint + "/";
        if (fileUrl.startsWith(prefix)) {
            String objectName = fileUrl.substring(prefix.length());
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            try {
                ossClient.deleteObject(bucketName, objectName);
            } finally {
                ossClient.shutdown();
            }
        }
    }
}