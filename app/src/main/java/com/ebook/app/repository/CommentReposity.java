package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CommentReposity {
    private final HttpUtil httpUtil= new HttpUtil();
    public void getCommentListByFid(int fid, Callback callback) {
        String url=HttpUtil.BASE_URL+"/v1/comment?fid="+fid;
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        httpUtil.request(request,callback);
    }

    public void sendComment(int fid, String content,String token,Callback callback) {
        String url=HttpUtil.BASE_URL+"/v1/comment";
        RequestBody requestBody=new FormBody.Builder()
                .add("fid",String.valueOf(fid))
                .add("content",content)
                .build();
        Request request=new Request.Builder()
                .url(url)
                .header("Authorization",token)
                .post(requestBody)
                .build();
        httpUtil.request(request,callback);
    }
}
