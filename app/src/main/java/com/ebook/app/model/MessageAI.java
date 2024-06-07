package com.ebook.app.model;

public class MessageAI {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int type;

    public MessageAI() {
    }

    public MessageAI(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
