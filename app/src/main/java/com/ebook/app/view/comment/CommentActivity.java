package com.ebook.app.view.comment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
import com.ebook.app.databinding.PageCommentBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Comment;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.ResponseOperation;
import com.google.android.material.appbar.MaterialToolbar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private int fid;
    private PageCommentBinding binding;
    private RecyclerView rvCommentList;
    private Button btnSend;
    private CommentAdapter commentAdapter;
    private CommentViewModel viewModel;
    private SmartRefreshLayout refreshLayout;
    private MaterialToolbar topAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        initRefreshLayout();
        initTopAppBar();
        initViewModel();
        initCommentList();
        initSend();
    }

    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(CommentViewModel.class);
    }
    private void initCommentList(){
        fid=getIntent().getIntExtra("fid",0);
        rvCommentList=binding.commentRvList;
        commentAdapter=new CommentAdapter(null);
        rvCommentList.setAdapter(commentAdapter);
        rvCommentList.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getCommentList().observe(this, new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto responseDto) {
                new ResponseOperation("GetFunction",getApplicationContext()){
                    @Override
                    public void onSuccess(ResponseDto response) {
                        JSONArray commentArray=response.getData().getJSONArray("comments");
                        List<Comment> commentList=commentArray.toJavaList(Comment.class);
                        commentAdapter.setList(commentList);
                        refreshLayout.finishRefresh();
                    }
                    @Override
                    public void showError(String msg) {
                        AlertUtil.showToast(getContext(),msg);
                    }
                }.onRespond(responseDto);
            }
        });
        viewModel.loadCommentList(fid);
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
    private void initRefreshLayout(){
        refreshLayout=binding.functionRefreshLayout;
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            viewModel.loadCommentList(fid);
        });
    }

    private void initSend(){
        btnSend=binding.commentBtnSend;
        btnSend.setOnClickListener(v -> {
            Intent intent=new Intent(this, SendCommentActivity.class);
            intent.putExtra("fid",fid);
            startActivity(intent);
        });
    }
}