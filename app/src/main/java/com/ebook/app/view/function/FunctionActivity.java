package com.ebook.app.view.function;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSONObject;
import com.ebook.app.R;
import com.ebook.app.databinding.PageFunctionBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Function;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.view.comment.CommentActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;


import io.noties.markwon.Markwon;

public class FunctionActivity extends AppCompatActivity {
    private PageFunctionBinding binding;
    private MaterialToolbar topAppBar;
    private FunctionViewModel viewModel;
    private SmartRefreshLayout refreshLayout;
    private TextView tvWriteComment;
    private ImageView imgComment, imgLike;
    private Function function;
    private int fid;
    private TextView tvMarkdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageFunctionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        initViewModel();
        initTopAppBar();
        initFunction();
        initRefreshLayout();
        initBottom();
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(FunctionViewModel.class);
        binding.setLifecycleOwner(this);
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
    private void initFunction(){
        tvMarkdown=binding.functionTvContent;
        fid=getIntent().getIntExtra("fid",0);
        viewModel.getFunction().observe(this, new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto responseDto) {
                new ResponseOperation("GetFunction",getApplicationContext()){
                    @Override
                    public void onSuccess(ResponseDto response) {
                        JSONObject functionJson=response.getData().getJSONObject("function");
                        function=functionJson.toJavaObject(Function.class);
                        function.setCommentCount(response.getData().getInteger("commentCount"));
                        binding.setTitle(function.getName());
                        binding.setLikeCount(String.valueOf(function.getLikeCount()));
                        binding.setCommentCount(String.valueOf(function.getCommentCount()));
                        setContent(function.getMarkdown());
                        refreshLayout.finishRefresh();
                    }
                    @Override
                    public void showError(String msg) {
                        AlertUtil.showToast(getContext(),msg);
                    }
                }.onRespond(responseDto);
            }
        });
        viewModel.loadFunction(fid);
    }

    private void setContent(String content){
        Markwon markwon = Markwon.create(this);
        markwon.setMarkdown(tvMarkdown, content);
    }

    private void initRefreshLayout(){
        refreshLayout=binding.functionRefreshLayout;
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.loadFunction(fid);
            refreshLayout.finishRefresh(1500);
        });
    }

    private void initBottom(){
        tvWriteComment=binding.functionTvComment;
        imgComment=binding.functionImgComment;
        imgLike=binding.functionImgLike;
        imgComment.setOnClickListener(v -> {
            Intent intent=new Intent(this, CommentActivity.class);
            intent.putExtra("fid",fid);
            startActivity(intent);
        });
    }
}