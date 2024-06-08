package com.ebook.app.model;

public class Function {
    private int id;
    private String name;
    private String usage;
    private String markdown;
    private int commentCount;
    private int likeCount;

    public Function(int id, String name, String usage, String markdown) {
        this.id = id;
        this.name = name;
        this.usage = usage;
        this.markdown = markdown;
        this.commentCount= 10;
        this.likeCount = 88;
    }

    public Function(int id, String name, String usage, String markdown, int commentCount, int likeCount) {
        this.id = id;
        this.name = name;
        this.usage = usage;
        this.markdown = markdown;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
