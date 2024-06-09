package com.ebook.app.view.set;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ebook.app.databinding.PageSetInfoBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.User;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.util.SharedPrefsUtil;
import com.google.android.material.appbar.MaterialToolbar;

public class SetInfoActivity extends AppCompatActivity {
    private User user;
    private PageSetInfoBinding binding;
    private MaterialToolbar topAppBar;
    private SetViewMobel viewModel;
    private Button btnSave;
    private SharedPrefsUtil prefsUtil;
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
        initViewModel();
        initElement();
        initData();
        initButtonListener();//初始化按钮
        initUpdataObserver();
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(SetViewMobel.class);
        binding.setLifecycleOwner(this);
    }

    private void initElement(){
        btnSave=binding.setInfoBtnSave;
    }

    private void initData(){
        user=new User();
        int id=getIntent().getIntExtra("id",0);
        String name=getIntent().getStringExtra("name");
        user.setId(id);
        binding.setName(name);
        prefsUtil=SharedPrefsUtil.with(this);
        String token=prefsUtil.getString("token","");
        user.setToken(token);
    }
    private void initButtonListener(){
        btnSave.setOnClickListener(v->btnSaveOnClick());
    }

    private void btnSaveOnClick(){
        String name=binding.getName();
        user.setName(name);
        viewModel.updateInfo(user.getId(),user.getName(),user.getToken());
    }

    private ResponseOperation reop=new ResponseOperation("updateInfo",this) {
        @Override
        public void onSuccess(ResponseDto response) {
            AlertUtil.showToast(getContext(),response.getMsg());
            finish();
        }

        @Override
        public void showError(String msg) {
            AlertUtil.showToast(getContext(),msg);
        }
    };

    private void initUpdataObserver(){
        viewModel.getUpdateResponse().observe(this,response->{
            reop.onRespond(response);
        });
    }
}