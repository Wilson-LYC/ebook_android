package com.ebook.app.model;

public class Message {
    private String sender;
    private String content;
    private boolean isLoading=false;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public Message(String sender, String content, boolean isLoading) {
        this.sender = sender;
        this.content = content;
        this.isLoading = isLoading;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}

