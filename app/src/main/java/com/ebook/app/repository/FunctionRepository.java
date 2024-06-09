package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.RequestCallback;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FunctionRepository {
    private final HttpUtil httpUtil= new HttpUtil();

    public void getRecommendedFunction(Callback callback) {
        Request request=new Request.Builder()
                .url(HttpUtil.BASE_URL+"/v1/function/recommended")
                .build();
        httpUtil.request(request,callback);
    }

    public void getCategoryList(Callback callback) {
        Request request=new Request.Builder()
                .url(HttpUtil.BASE_URL+"/v1/function/category")
                .get()
                .build();
        httpUtil.request(request,callback);
    }

    public void getFunctionListByCid(int cid, Callback callback) {
        String url=HttpUtil.BASE_URL+"/v1/function/category/"+cid;
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        httpUtil.request(request,callback);
    }

    public void getFunctionById(int fid, Callback callback) {
        String url=HttpUtil.BASE_URL+"/v1/function/"+fid;
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        httpUtil.request(request,callback);
    }
}
