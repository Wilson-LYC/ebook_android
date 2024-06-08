package com.ebook.app.view.comment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.databinding.PageCommentBinding;
import com.ebook.app.model.Comment;
import com.ebook.app.view.comment.send.SendCommentActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private int fid;
    private PageCommentBinding binding;
    private RecyclerView rvCommentList;
    private Button btnSend;
    private CommentAdapter commentAdapter;
    private CommentViewModel commentViewModel;
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
        initTopAppBar();
        initViewModel();
        initCommentList();
        initRefreshLayout();
        initSend();
    }

    private void initViewModel(){
        commentViewModel=new ViewModelProvider(this).get(CommentViewModel.class);
    }
    private void initCommentList(){
        if (getIntent().hasExtra("fid")){
            fid=getIntent().getIntExtra("fid",0);
        }
        rvCommentList=binding.commentRvList;
        commentAdapter=new CommentAdapter(null);
        rvCommentList.setAdapter(commentAdapter);
        rvCommentList.setLayoutManager(new LinearLayoutManager(this));
        commentViewModel.getCommentList().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                commentAdapter.setList(comments);
            }
        });
        commentViewModel.creat(fid);
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
    private void initRefreshLayout(){
        refreshLayout=binding.functionRefreshLayout;
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            commentViewModel.refresh();
            refreshLayout.finishRefresh(1500);
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