package com.ebook.app.views.authority;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.ebook.app.R;
import com.ebook.app.dtos.ResponseDto;
import com.ebook.app.util.SharedPrefsUtil;
import com.ebook.app.viewmodel.LoginViewModel;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.views.main.MainActivity;

public class LoginFragment extends Fragment {
    final String TAG = "LoginFragment";
    private SharedPrefsUtil prefsUtil;//SharedPreferences工具
    private TextView tvForgotPwd;
    private EditText edEmail, edPassword;
    private Button btnLogin;
    private LoginViewModel loginViewModel;//登录视图模型
    Observer<ResponseDto> loginObserver;//登录观察者

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }
    
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    /**
     * 视图创建时调用
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        // 初始化
        prefsUtil=SharedPrefsUtil.with(getActivity());
        edEmail = getView().findViewById(R.id.login_ed_email);
        edPassword = getView().findViewById(R.id.login_ed_password);
        btnLogin= getView().findViewById(R.id.login_btn_login);
        tvForgotPwd=getView().findViewById(R.id.login_tv_forgot_pwd);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginObserver = new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                loginResponse(response);//登录响应
            }
        };
        loginViewModel.getLoginLiveData().observe(getViewLifecycleOwner(), loginObserver);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginClick();//登录
            }
        });
    }

    /**
     * 销毁时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 移除观察者
        loginViewModel.getLoginLiveData().removeObserver(loginObserver);
    }

    /**
     * 登录
     */
    public void btnLoginClick(){
        Log.i(TAG, "登录按钮被点击" );
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (!validLoginInput(email,password)) return;
        loginViewModel.login(email,password);
    }

    /**
     * 验证登录输入数据是否有效
     * @param email 邮箱
     * @param password 密码
     * @return 验证结果
     */
    private boolean validLoginInput(String email,String password) {
        //判断是否为空
        if (email.isEmpty() || password.isEmpty()){
            Log.w(TAG,"请输入账号和密码");
            AlertUtil.showDialog(getContext(),"请输入账号和密码");
            return false;
        }
        //判断邮箱格式
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")){
            Log.w(TAG,"请输入正确的邮箱");
            AlertUtil.showDialog(getContext(),"请输入正确的邮箱");
            return false;
        }
        return true;
    }
    /**
     * 登录响应
     */
    private void loginResponse(ResponseDto response){
        Log.d(TAG, response.getMsg());
        AlertUtil.showToast(getContext(),response.getMsg());
        if(response.getCode()==200){
            JSONObject data = response.getJSONObject("data");
            String token = data.getString("token");
            prefsUtil.putString("token", token);
            startActivity(new Intent(getContext(), MainActivity.class));
        }
    }
}