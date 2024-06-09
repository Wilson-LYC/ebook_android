package com.ebook.app.view.set.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ebook.app.R;
import com.ebook.app.databinding.PageSetInfoBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class SetInfoActivity extends AppCompatActivity {
    private PageSetInfoBinding binding;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageSetInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.activity, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

    }

    private void init(){
        initTopAppBar();
//        initViewModel();
//        initElement();//初始化元素
//        initButtonListener();//初始化按钮
//        initUserObserver();//初始化用户信息观察者
//        loadUserInfo();//初始化时，加载用户信息
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
//    private void initViewModel(){
//        viewModel=new ViewModelProvider(this).get(MeViewModel.class);
//    }

}