package com.example.demo.dto;

public class TodoResponse {
    private Long id;
    private String title;
    private boolean done;
    private String attachmentUrl;

//    public TodoResponse(Long id, String title, boolean done) {
//        this.id = id;
//        this.title = title;
//        this.done = done;
//    }

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}
