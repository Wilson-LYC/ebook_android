package com.ebook.app.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * 统一返回结果
 */
public class ResponseDto extends JSONObject {
    public ResponseDto() {
    }
    public ResponseDto(int code, String msg) {
        super();
        this.put("code",code);
        this.put("msg",msg);
    }
    public ResponseDto(int code,String msg,JSONObject data) {
        super();
        this.put("code",code);
        this.put("msg",msg);
        this.put("data",data);
    }
    public int getCode() {
        return this.getInteger("code");
    }
    public String getMsg() {
        return this.getString("msg");
    }
    public JSONObject getData() {
        return this.getJSONObject("data");
    }
}
