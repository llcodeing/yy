package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.kafka.common.protocol.types.Field;

/**
 * 新增待办事项的请求参数
 */
public class TodoCreateRequest {

    @NotBlank(message = "标题不能为空")
    @Size(max = 100,message = "标题最长100个字符")
    private  String title;
    private String attachmentUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}
