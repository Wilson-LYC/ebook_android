package com.ebook.app.view.catalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.ebook.app.R;
import com.ebook.app.databinding.PageCatalogueBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Category;
import com.ebook.app.model.Function;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.view.function.FunctionActivity;
import com.ebook.app.view.function.FunctionListAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class CatalogueActivity extends AppCompatActivity {
    private PageCatalogueBinding binding;
    private RecyclerView rvCategory,rvFunction;
    private CatalogueCategoryAdapter categoryAdapter;
    private FunctionListAdapter functionAdapter;
    private List<Category> categoryList=new ArrayList<>();
    private List<Function> functionList;
    private MaterialToolbar topAppBar;
    private int index=0;
    private CatalogueViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageCatalogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        initTopAppBar();
        initViewModel();
        initCategory();
        initFunction();
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(CatalogueViewModel.class);
    }
    private void initCategory(){
        //激活的分类
        index=getIntent().getIntExtra("index",0);
        //配置视图
        rvCategory=binding.catalogueRvCategory;
        categoryAdapter = new CatalogueCategoryAdapter(null, position -> {
            changeIndex(position);
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.setAdapter(categoryAdapter);
        //获取数据
        viewModel.getCategoryList().observe(this, new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto responseDto) {
                new ResponseOperation("GetCategoryList",getApplicationContext()){
                    @Override
                    public void onSuccess(ResponseDto response) {
                        JSONArray categoryArray = response.getData().getJSONArray("categories");
                        System.out.println(categoryArray.toJSONString());
                        categoryList= categoryArray.toJavaList(Category.class);
                        categoryAdapter.setList(categoryList);
                        changeIndex(index);
                    }

                    @Override
                    public void showError(String msg) {
                        AlertUtil.showToast(getContext(),msg);
                    }
                }.onRespond(responseDto);
            }
        });
        viewModel.loadCategoryList();
    }
    private void initFunction(){
        //配置视图
        rvFunction=binding.catalogueRvFunction;
        functionAdapter = new FunctionListAdapter(null, (position, fid) -> {
            Intent intent = new Intent(CatalogueActivity.this, FunctionActivity.class);
            intent.putExtra("fid", fid);
            startActivity(intent);
        },R.layout.item_function_card);
        rvFunction.setLayoutManager(new LinearLayoutManager(this));
        rvFunction.setAdapter(functionAdapter);
        //获取数据
        viewModel.getFunctionList().observe(this, new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto responseDto) {
                System.out.println(responseDto.toJSONString());
                new ResponseOperation("GetFunctionList",getApplicationContext()){
                    @Override
                    public void onSuccess(ResponseDto response) {
                        JSONArray functionArray = response.getData().getJSONArray("functions");
                        functionList= functionArray.toJavaList(Function.class);
                        functionAdapter.setList(functionList);
                    }

                    @Override
                    public void showError(String msg) {
                        AlertUtil.showToast(getContext(),msg);
                    }
                }.onRespond(responseDto);
            }
        });
    }
    private void changeIndex(int position){
        index=position;
        categoryAdapter.changeIndex(position);//更换激活的分类
        viewModel.loadFunctionList(categoryList.get(position).getId());//加载对应的函数
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }

}