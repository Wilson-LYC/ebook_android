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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;

import com.ebook.app.R;
import com.ebook.app.databinding.PageHomeBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Category;
import com.ebook.app.model.Function;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.view.catalogue.CatalogueActivity;
import com.ebook.app.view.function.FunctionActivity;
import com.ebook.app.view.function.FunctionListAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public class HomeFragment extends Fragment {
    int refreshFlag=0;
    final static String TAG="HomePage";
    private PageHomeBinding binding;
    private RecyclerView rvNav,rvArticle;
    private SmartRefreshLayout refreshLayout;
    private HomeNavAdapter navAdapter;
    private FunctionListAdapter functionListAdapter;
    private HomeViewModel viewModel;
    private List<Function> functionList;
    private List<Category> categoryList;
    private Observer<ResponseDto> functionListObserver;
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
        initRefresh();
        initNavBar();
        initFunctionList();
    }

    private void initViewModel(){
        viewModel= new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void initNavBar(){
        //配置视图与绑定点击事件
        rvNav=binding.homeRvNav;
        navAdapter= new HomeNavAdapter(categoryList, position -> {
            Intent intent = new Intent(getContext(), CatalogueActivity.class);
            intent.putExtra("index",position);
            startActivity(intent);
        });
        rvNav.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvNav.setAdapter(navAdapter);
        //配置数据
        viewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                new ResponseOperation("GetCategories",getContext()) {
                    @Override
                    public void onSuccess(ResponseDto response) {
                        JSONArray categoryArray = response.getData().getJSONArray("categories");
                        categoryList= categoryArray.toJavaList(Category.class);
                        navAdapter.setList(categoryList);
                        refreshFlag++;
                        if (refreshFlag==2){
                            refreshLayout.finishRefresh();
                        }
                    }
                    @Override
                    public void showError(String msg) {
                    }
                }.onRespond(response);
            }
        });
    }

    private void initFunctionList() {
        //配置视图与绑定点击事件
        functionListAdapter=new FunctionListAdapter(null,(position,fid) -> {
            Intent intent = new Intent(getContext(), FunctionActivity.class);
            intent.putExtra("fid", functionList.get(position).getId());
            startActivity(intent);
        });
        rvArticle = binding.homeRvArticle;
        rvArticle.setAdapter(functionListAdapter);
        rvArticle.setLayoutManager(new LinearLayoutManager(getContext()));
        //配置数据
        viewModel.getFunctions().observe(getViewLifecycleOwner(),new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                new ResponseOperation("GetRecommended",getContext()) {
                    @Override
                    public void onSuccess(ResponseDto response) {
                        JSONArray functionArray = response.getData().getJSONArray("functions");
                        functionList= functionArray.toJavaList(Function.class);
                        functionListAdapter.setList(functionList);
                        refreshFlag++;
                        if (refreshFlag==2){
                            refreshLayout.finishRefresh();
                        }
                    }
                    @Override
                    public void showError(String msg) {
                        AlertUtil.showToast(getContext(),msg);
                    }
                }.onRespond(response);
            }
        });
    }

    private void initRefresh(){
        refreshLayout=binding.refreshLayout;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.i(TAG,"下拉刷新");
                refreshFlag=0;
                viewModel.loadRecommendFunctions();
                viewModel.loadCategoryList();
                refreshlayout.finishRefresh(1500);
            }
        });
    }
}