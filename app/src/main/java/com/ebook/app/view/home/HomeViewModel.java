package com.ebook.app.view.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.model.Article;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    final static String TAG = "HomeViewModel";
    int id=0;

    public HomeViewModel() {
        getWeeklyArticles();
    }

    private MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>();

    public void getWeeklyArticles() {
        Log.i(TAG, "模拟异步加载文章");
        //新线程加载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Article> articles = new ArrayList<>();
                for (int i = 1; i <= 15; i++) {
                    id++;
                    articles.add(new Article("文章" + id, "大家好！这是文章！你好你好！大家好"));
                }
                articlesLiveData.postValue(articles); // 使用postValue在任何线程更新数据
            }
        }).start();
    }
    public LiveData<List<Article>> getArticlesLiveData() {
        return articlesLiveData;
    }
}