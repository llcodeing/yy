package com.example.demo;

import java.io.Serializable;

public class TodoItem implements Serializable {
    private Long id;
    private String title;
    private boolean done;
    private String attachmentUrl;
    // 无参构造，必须
    public TodoItem() {}

    public TodoItem(String title) {
        this.title = title;
    }

    // ====== getter/setter 一个都不能少 ======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // boolean 类型的正确 getter 名称是 isXxx()
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
}