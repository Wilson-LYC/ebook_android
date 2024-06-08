package com.ebook.app.view.comment.send;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ebook.app.R;
import com.ebook.app.databinding.PageSendCommentBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class SendCommentActivity extends AppCompatActivity {
    private PageSendCommentBinding binding;
    private MaterialToolbar topAppBar;
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
        initTopAppBar();
    }
    private void initTopAppBar(){
        topAppBar=binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
}