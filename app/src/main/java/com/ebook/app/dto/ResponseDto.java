package com.ebook.app.dto;

import com.alibaba.fastjson.JSONObject;

public class ResponseDto extends JSONObject {

    public ResponseDto() {
    }

    public ResponseDto(int code, String msg){
        super();
        this.put("code", code);
        this.put("msg", msg);
    }

    public ResponseDto(int code, String msg, JSONObject data){
        super();
        this.put("code", code);
        this.put("msg", msg);
        this.put("data", data);
    }
    public ResponseDto(int code, String msg, String error){
        super();
        this.put("code", code);
        this.put("msg", msg);
        this.put("error", error);
    }

    public int getCode(){
        return this.getInteger("code");
    }
}
