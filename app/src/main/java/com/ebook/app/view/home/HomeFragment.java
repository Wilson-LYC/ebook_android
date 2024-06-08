package com.ebook.app.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebook.app.R;
import com.ebook.app.config.FunctionCategoryConfig;

import com.ebook.app.model.Article;
import com.ebook.app.view.catalogue.CatalogueActivity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public class HomeFragment extends Fragment {
    final static String TAG="HomeFragment";
    private RecyclerView navView;
    private RecyclerView articleView;
    private HomeNavAdapter navAdapter;
    private HomeArticleAdapter articleAdapter;
    private HomeViewModel homeViewModel;
    private Observer<List<Article>> articleObserver;
    private SmartRefreshLayout refreshLayout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        Log.i(TAG, "构造");
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.page_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        // 初始化ViewModel
        if (homeViewModel==null)
            homeViewModel= new ViewModelProvider(this).get(HomeViewModel.class);
        initNavBar(view);// 初始化导航栏
        initArticleList(view);// 初始化文章列表
        initRefresh(view);// 初始化刷新控件
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView");
        super.onDestroyView();
        homeViewModel.getArticlesLiveData().removeObserver(articleObserver);
    }

    private void initNavBar(View view){
        // 初始化导航栏
        navAdapter= new HomeNavAdapter(FunctionCategoryConfig.categories, position -> {
            //导航栏项目点击事件 - 跳转到目录页面
            Log.i(TAG,"点击了导航栏项目:"+position);
            Intent intent = new Intent(getContext(), CatalogueActivity.class);
            intent.putExtra("index",position);
            startActivity(intent);
        });
        navView = view.findViewById(R.id.home_nav);
        navView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        navView.setAdapter(navAdapter);
    }

    private void initArticleList(View view) {
        //配置文章列表试图
        articleAdapter=new HomeArticleAdapter();
        articleView = view.findViewById(R.id.home_articles);
        articleView.setAdapter(articleAdapter);
        articleView.setLayoutManager(new LinearLayoutManager(getContext()));
        //配置文章数据
        articleObserver = new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                articleAdapter.setList(articles);
            }
        };
        homeViewModel.getArticlesLiveData().observe(getViewLifecycleOwner(), articleObserver);
    }

    private void initRefresh(View view){
        refreshLayout=view.findViewById(R.id.home_refresh_layout);// 初始化刷新控件
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.i(TAG,"触发下拉刷新");
                homeViewModel.getWeeklyArticles();
                refreshlayout.finishRefresh(2000);
            }
        });
    }
}