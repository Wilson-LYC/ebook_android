package com.ebook.app.view.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebook.app.R;
import com.ebook.app.config.FunctionCategoryConfig;

import com.ebook.app.model.Article;
import com.ebook.app.view.main.adapter.HomeArticleAdapter;
import com.ebook.app.view.main.adapter.HomeNavAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView navView,articleView;
    private HomeNavAdapter navAdapter;
    private HomeArticleAdapter articleAdapter;
    private List<Article> articleList=new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
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
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        //导航栏
        GridLayoutManager navGridLayoutManager = new GridLayoutManager(getContext(), 5);
        navView = view.findViewById(R.id.home_nav);
        navAdapter= new HomeNavAdapter(inflater,FunctionCategoryConfig.categories);
        navView.setLayoutManager(navGridLayoutManager);
        navView.setAdapter(navAdapter);
        //本周精选文章
        tempInitData();
        LinearLayoutManager aritcleLayoutManager = new LinearLayoutManager(getContext());
        articleView = view.findViewById(R.id.home_articles);
        articleAdapter = new HomeArticleAdapter(inflater,articleList);
        articleView.setLayoutManager(aritcleLayoutManager);
        articleView.setAdapter(articleAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void tempInitData(){
        for (int i=1;i<=15;i++)
            articleList.add(new Article("文章"+i,"大家好！这是文章！你好你好！大家好"));
    }
}