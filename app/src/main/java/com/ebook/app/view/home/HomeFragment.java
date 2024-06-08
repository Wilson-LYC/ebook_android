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
import com.ebook.app.config.EBookConfig;

import com.ebook.app.databinding.PageHomeBinding;
import com.ebook.app.model.Category;
import com.ebook.app.model.Function;
import com.ebook.app.view.catalogue.CatalogueActivity;
import com.ebook.app.view.function.FunctionActivity;
import com.ebook.app.view.function.FunctionListAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public class HomeFragment extends Fragment {
    final static String TAG="HomePage";
    private PageHomeBinding binding;
    private RecyclerView rvNav,rvArticle;
    private SmartRefreshLayout refreshLayout;
    private HomeNavAdapter navAdapter;
    private FunctionListAdapter functionListAdapter;
    private HomeViewModel homeViewModel;
    private List<Function> functionList;
    private Observer<List<Function>> functionListObserver;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
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
        binding= PageHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void init(){
        initViewModel();
        initNavBar();
        initFunctionList();
        initRefresh();
    }

    private void initViewModel(){
        if (homeViewModel==null)
            homeViewModel= new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void initNavBar(){
        List<Category> categoryList=EBookConfig.categoryList;//从配置类读取
        rvNav=binding.homeRvNav;
        navAdapter= new HomeNavAdapter(categoryList, position -> {
            //点击导航栏项目,跳转到目录页面
            Log.i(TAG,"导航栏第"+position+"项被点击");
            Intent intent = new Intent(getContext(), CatalogueActivity.class);
            intent.putExtra("index",position);
            startActivity(intent);
        });
        rvNav.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvNav.setAdapter(navAdapter);
    }
    private void initFunctionList() {
        functionListAdapter=new FunctionListAdapter(null,position -> {
            //跳转到函数页面
            Intent intent = new Intent(getContext(), FunctionActivity.class);
            intent.putExtra("fid", functionList.get(position).getId());
            startActivity(intent);
        });
        rvArticle = binding.homeRvArticle;
        rvArticle.setAdapter(functionListAdapter);
        rvArticle.setLayoutManager(new LinearLayoutManager(getContext()));
        functionListObserver=new Observer<List<Function>>() {
            @Override
            public void onChanged(List<Function> functions) {
                functionList=functions;
                functionListAdapter.setList(functions);
            }
        };
        homeViewModel.getFunctionList().observe(getViewLifecycleOwner(),functionListObserver);
    }

    private void initRefresh(){
        refreshLayout=binding.homeRefreshLayout;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.i(TAG,"下拉刷新");
                homeViewModel.getFunctionList();
                refreshlayout.finishRefresh(2000);
            }
        });
    }
}