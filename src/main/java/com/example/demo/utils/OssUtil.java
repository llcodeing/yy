package com.example.demo.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class OssUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    private static final long DEFAULT_URL_EXPIRATION = 3600; // 默认1小时

    /**
     * 创建 OSS 客户端（从环境变量读取凭证）
     */
    private OSS createOSSClient() {
        try {
            EnvironmentVariableCredentialsProvider credentialsProvider =
                    CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            System.out.println(">>> OSS Endpoint: " + endpoint);
            System.out.println(">>> OSS BucketName: " + bucketName);
            System.out.println(">>> OSS AccessKeyId from env: " + System.getenv("OSS_ACCESS_KEY_ID"));
            return OSSClientBuilder.create()
                    .endpoint(endpoint)
                    .credentialsProvider(credentialsProvider)
                    .build();
        } catch (Exception e) {
            System.err.println("!!! 创建 OSS 客户端失败 !!!");
            e.printStackTrace();
            throw new RuntimeException("创建 OSS 客户端失败，请检查环境变量配置", e);
        }
    }

    /**
     * 上传文件（默认1小时有效期）
     */
    public String uploadFile(MultipartFile file) throws IOException {
        return uploadFile(file, DEFAULT_URL_EXPIRATION);
    }

    /**
     * 上传文件（自定义有效期）
     */
    public String uploadFile(MultipartFile file, long expirationSeconds) throws IOException {
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String objectName = "todos/" + UUID.randomUUID() + extension;

        OSS ossClient = createOSSClient();

        try {
            // 上传文件
            ossClient.putObject(bucketName, objectName, file.getInputStream());

            // 生成带签名的临时访问 URL
            return generateSignedUrl(objectName, expirationSeconds);
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 生成带签名的临时访问 URL（默认1小时有效期）
     */
    public String generateSignedUrl(String objectName) {
        return generateSignedUrl(objectName, DEFAULT_URL_EXPIRATION);
    }

    /**
     * 生成带签名的临时访问 URL（自定义有效期）
     */
    public String generateSignedUrl(String objectName, long expirationSeconds) {
        OSS ossClient = createOSSClient();

        try {
            Date expiration = new Date(System.currentTimeMillis() + expirationSeconds * 1000);

            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
            request.setExpiration(expiration);
            request.setMethod(com.aliyun.oss.HttpMethod.GET);

            URL signedUrl = ossClient.generatePresignedUrl(request);

            // 如果使用的是内网 endpoint，替换为外网地址
            String url = signedUrl.toString();
            String publicEndpoint = getPublicEndpoint(endpoint);

            if (!publicEndpoint.equals(endpoint)) {
                String internalEndpoint = endpoint.replace("https://", "").replace("http://", "");
                String publicEndpointHost = publicEndpoint.replace("https://", "").replace("http://", "");
                url = url.replace(internalEndpoint, publicEndpointHost);
            }

            return url;
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        String objectName = extractObjectNameFromUrl(fileUrl);
        if (objectName != null) {
            OSS ossClient = createOSSClient();
            try {
                ossClient.deleteObject(bucketName, objectName);
            } finally {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 获取外网 endpoint
     */
    private String getPublicEndpoint(String endpoint) {
        String endpointWithoutProtocol = endpoint.replace("https://", "").replace("http://", "");
        if (endpointWithoutProtocol.contains("internal")) {
            String publicEndpoint = endpointWithoutProtocol.replace("-internal", "");
            if (endpoint.startsWith("https://")) {
                return "https://" + publicEndpoint;
            } else {
                return "http://" + publicEndpoint;
            }
        }
        return endpoint;
    }

    /**
     * 从 URL 中提取 objectName
     */
    private String extractObjectNameFromUrl(String fileUrl) {
        try {
            String publicEndpoint = getPublicEndpoint(endpoint);
            String pattern = "https://" + bucketName + "." + publicEndpoint + "/";

            int queryIndex = fileUrl.indexOf("?");
            String baseUrl = queryIndex > 0 ? fileUrl.substring(0, queryIndex) : fileUrl;

            if (baseUrl.startsWith(pattern)) {
                return baseUrl.substring(pattern.length());
            }
        } catch (Exception e) {
            // 忽略解析错误
        }
        return null;
    }
}