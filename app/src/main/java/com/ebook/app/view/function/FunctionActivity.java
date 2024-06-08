package com.ebook.app.view.function;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ebook.app.R;
import com.ebook.app.databinding.PageFunctionBinding;
import com.google.android.material.appbar.MaterialToolbar;

import io.noties.markwon.Markwon;

public class FunctionActivity extends AppCompatActivity {
    private PageFunctionBinding binding;
    private MaterialToolbar topAppBar;
    private FunctionViewModel viewModel;
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
        initContent();
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(FunctionViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
    private void initContent(){
        if (getIntent().hasExtra("fid")){
            fid=getIntent().getIntExtra("fid",0);
        }
        tvMarkdown=binding.functionTvContent;
        viewModel.getFunction().observe(this,function -> {
            setContent(function.getMarkdown());
        });
        viewModel.setFid(fid);
    }
    private void setContent(String content){
        Markwon markwon = Markwon.create(this);
        markwon.setMarkdown(tvMarkdown, content);
    }
}