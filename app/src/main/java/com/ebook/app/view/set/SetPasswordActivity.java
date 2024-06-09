package com.ebook.app.view.set;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ebook.app.R;
import com.ebook.app.databinding.PageSetEmailBinding;
import com.ebook.app.databinding.PageSetPasswordBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class SetPasswordActivity extends AppCompatActivity {

    private PageSetPasswordBinding binding;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageSetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initTopAppBar();//初始化顶部工具栏
    }

    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
}