package com.example.demo;

import com.example.demo.utils.OssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private OssUtil ossUtil;

    @PostMapping("/upload")
    @Operation(summary = "上传文件到OSS")
    public Result<String> uploadFile(
            @Parameter(description = "要上传的文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            String url = ossUtil.uploadFile(file);
            return Result.success(url);
        } catch (IOException e) {
            return Result.fail("文件上传失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Result<String> deleteFile(@RequestParam String fileUrl) {
        try {
            ossUtil.deleteFile(fileUrl);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.fail("删除失败：" + e.getMessage());
        }
    }
}