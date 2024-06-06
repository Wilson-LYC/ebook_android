package com.ebook.app.view.main.fragment;

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
import com.ebook.app.view.authority.viewmodel.LoginViewModel;
import com.ebook.app.view.main.adapter.HomeArticleAdapter;
import com.ebook.app.view.main.adapter.HomeNavAdapter;
import com.ebook.app.view.main.viewmodel.HomeViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    final static String TAG="HomeFragment";
    private RecyclerView navView,articleView;
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
        return inflater.inflate(R.layout.fragment_home, container, false);
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
        GridLayoutManager navGridLayoutManager = new GridLayoutManager(getContext(), 5){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        navAdapter= new HomeNavAdapter(getLayoutInflater(),FunctionCategoryConfig.categories);
        navView = view.findViewById(R.id.home_nav);
        navView.setLayoutManager(navGridLayoutManager);
        navView.setAdapter(navAdapter);
    }

    private void initArticleList(View view) {
        LinearLayoutManager aricleLayoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        articleAdapter = new HomeArticleAdapter(getLayoutInflater());
        articleView = view.findViewById(R.id.home_articles);
        articleView.setLayoutManager(aricleLayoutManager);
        articleView.setAdapter(articleAdapter);
        articleObserver = new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articles == null)
                    articles = new ArrayList<>();
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
                homeViewModel.refreshArticles();
                refreshlayout.finishRefresh(2000);
            }
        });
    }
}