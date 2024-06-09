package com.ebook.app.model;

public class Notify {
    final public static String DIALOG = "dialog";
    final public static String TOAST = "toast";
    private String type;

    private String title="提示";
    private String msg;



    public Notify(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
