package com.example.demo;


import com.example.demo.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private OssUtil ossUtil;

    /**
     * 上传文件（默认1小时有效期）
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 上传并获取带签名的临时 URL
            String fileUrl = ossUtil.uploadFile(file);

            result.put("success", true);
            result.put("url", fileUrl);
            result.put("expiresIn", "1小时");
            result.put("message", "上传成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "上传失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 上传文件（自定义有效期）
     * @param expirationSeconds URL有效期（秒），例如 86400 表示24小时
     */
    @PostMapping("/upload-with-expiry")
    public Map<String, Object> uploadFileWithExpiry(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "expiration", defaultValue = "3600") long expirationSeconds) {
        Map<String, Object> result = new HashMap<>();
        try {
            String fileUrl = ossUtil.uploadFile(file, expirationSeconds);

            result.put("success", true);
            result.put("url", fileUrl);
            result.put("expiresIn", expirationSeconds + "秒");
            result.put("message", "上传成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "上传失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取文件的临时访问 URL（不重新上传）
     */
    @GetMapping("/url/{objectName}")
    public Map<String, Object> getFileUrl(@PathVariable String objectName) {
        Map<String, Object> result = new HashMap<>();
        try {
            String url = ossUtil.generateSignedUrl(objectName);
            result.put("success", true);
            result.put("url", url);
            result.put("expiresIn", "1小时");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取URL失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    public Map<String, Object> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        Map<String, Object> result = new HashMap<>();
        try {
            ossUtil.deleteFile(fileUrl);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败: " + e.getMessage());
        }
        return result;
    }
}