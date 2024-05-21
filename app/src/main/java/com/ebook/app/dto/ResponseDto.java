package com.ebook.app.dto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import java.util.function.BiFunction;

public class ResponseDto extends JSONObject {
    public ResponseDto(int code, String msg) {
        super();
        this.put("code", code);
        this.put("msg", msg);
    }
    public ResponseDto(int code, String msg, com.alibaba.fastjson2.JSONObject data) {
        super();
        this.put("code", code);
        this.put("msg", msg);
        this.put("data", data);
    }
    public int getCode(){
        return this.getIntValue("code");
    }

}
