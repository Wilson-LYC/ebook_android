package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.RequestCallback;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;

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

    public void getFunctionById(int fid, String token, Callback callback) {
        String url=HttpUtil.BASE_URL+"/v1/function/"+fid;
        Request request=new Request.Builder()
                .url(url)
                .header("Authorization",token)
                .get()
                .build();
        httpUtil.request(request,callback);
    }

    public void likeFunction(int id, String token, RequestCallback callback) {
        String url=HttpUtil.BASE_URL+"/v1/function/like/"+id;
        Request request=new Request.Builder()
                .url(url)
                .header("Authorization",token)
                .post(new FormBody.Builder().build())
                .build();
        httpUtil.request(request,callback);
    }

    public void disLikeFunction(int id, String token, RequestCallback callback) {
        String url=HttpUtil.BASE_URL+"/v1/function/like/"+id;
        Request request=new Request.Builder()
                .url(url)
                .header("Authorization",token)
                .delete()
                .build();
        httpUtil.request(request,callback);
    }
}
