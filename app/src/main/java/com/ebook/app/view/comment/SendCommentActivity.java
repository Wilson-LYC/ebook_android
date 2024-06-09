package com.ebook.app.view.comment;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ebook.app.R;
import com.ebook.app.databinding.PageSendCommentBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Comment;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.InputValidator;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.util.SharedPrefsUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

public class SendCommentActivity extends AppCompatActivity {
    private PageSendCommentBinding binding;
    private MaterialToolbar topAppBar;
    private Button btnSend;
    private TextInputLayout tilComment;
    private CommentViewModel viewModel;
    private int fid;
    private String token;
    private SharedPrefsUtil prefsUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageSendCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        initViewModel();
        initTopAppBar();
        initSendComment();
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(CommentViewModel.class);
    }
    private void initTopAppBar(){
        topAppBar=binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
    private void initSendComment(){
        fid=getIntent().getIntExtra("fid",0);
        prefsUtil=SharedPrefsUtil.with(this);
        token=prefsUtil.getString("token","");
        btnSend=binding.sendCommentBtnSend;
        btnSend.setOnClickListener(v->sendComment());
        tilComment=binding.sendCommentTilComment;
        viewModel.getSendComment().observe(this,sendComment->{
            new ResponseOperation("SendComment",getApplicationContext()){
                @Override
                public void onSuccess(ResponseDto response) {
                    AlertUtil.showToast(getContext(),"评论成功");
                    finish();
                }

                @Override
                public void showError(String msg) {
                    AlertUtil.showToast(getContext(),msg);
                }
            }.onRespond(sendComment);
        });
    }
    private void sendComment(){
        String content=binding.getComment();
        if(!InputValidator.isNotEmpty(content)){
            tilComment.setError("评论不能为空");
            return;
        }
        tilComment.setError(null);
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setFid(fid);
        viewModel.sendComment(comment,token);
    }
}