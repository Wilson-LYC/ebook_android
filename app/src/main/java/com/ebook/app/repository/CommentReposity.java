package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;

import okhttp3.Callback;
import okhttp3.Request;

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
}
