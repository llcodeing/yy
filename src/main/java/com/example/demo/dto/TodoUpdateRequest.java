package com.example.demo.dto;

import jakarta.validation.constraints.Size;

public class TodoUpdateRequest {

    @Size(max = 100,message = "标题最长100个字符")
    private String title;

    private Boolean done;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
