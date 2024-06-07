package com.ebook.app.view.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ebook.app.R;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.CheckInputUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.view.authority.AuthorityActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    final String TAG = "Register Page";
    private TextInputEditText etEmail,etPassword,etCaptcha,etPasswordConfirm;
    private TextInputLayout tilEmail,tilPassword,tilCaptcha,tilPasswordConfirm;
    private Button btnRegister, btnGetCaptcha;
    private RegisterViewModel registerViewModel;
    Observer<ResponseDto> registerObserver,getCaptchaObserver;//注册观察者，获取验证码观察者

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.page_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //绑定viewmodel
        if(registerViewModel==null)
            registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        //绑定组件
        etEmail=view.findViewById(R.id.register_et_email);
        etCaptcha=view.findViewById(R.id.register_et_captcha);
        etPassword=view.findViewById(R.id.register_et_password);
        etPasswordConfirm=view.findViewById(R.id.register_et_password_confirm);
        tilEmail=view.findViewById(R.id.register_til_email);
        tilCaptcha=view.findViewById(R.id.register_til_captcha);
        tilPassword=view.findViewById(R.id.register_til_password);
        tilPasswordConfirm=view.findViewById(R.id.register_til_password_confirm);
        btnGetCaptcha=view.findViewById(R.id.register_btn_get_captcha);
        btnRegister=view.findViewById(R.id.register_btn_register);
        //注册观察者
        registerObserver = new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                //注册响应
                registerResponse.onRespond(response);
            }
        };
        registerViewModel.getRegisterLiveData().observe(getViewLifecycleOwner(), registerObserver);
        //获取验证码观察者
        getCaptchaObserver = new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                //获取验证码响应
                getCaptchaResponse.onRespond(response);
            }
        };
        registerViewModel.getGetCaptchaLiveData().observe(getViewLifecycleOwner(), getCaptchaObserver);
        //注册按钮点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册点击事件
                btnRegisterOnClick();
            }
        });
        //获取验证码按钮点击事件
        btnGetCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码点击事件
                btnGetCaptchaOnClick();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 移除观察者
        registerViewModel.getRegisterLiveData().removeObserver(registerObserver);
        registerViewModel.getGetCaptchaLiveData().removeObserver(getCaptchaObserver);
    }

    /**
     * 校验注册数据
     * @param email 邮箱
     * @param password 密码
     * @param passwordConfirm 确认密码
     * @param captcha 验证码
     * @return 校验结果
     */
    private boolean checkRegisterInput(String email, String password, String passwordConfirm, String captcha) {
        // 校验注册数据
        if (!CheckInputUtil.checkEmpty(email, tilEmail, "邮箱不能为空"))
            return false;
        if (!CheckInputUtil.checkEmail(email, tilEmail))
            return false;
        if (!CheckInputUtil.checkEmpty(password, tilPassword, "密码不能为空"))
            return false;
        if (!CheckInputUtil.checkEmpty(passwordConfirm, tilPasswordConfirm, "确认密码不能为空"))
            return false;
        if (!CheckInputUtil.checkSame(password, passwordConfirm, tilPasswordConfirm, "两次密码不一致"))
            return false;
        if (!CheckInputUtil.checkEmpty(captcha, tilCaptcha, "验证码不能为空"))
            return false;
        return true;
    }

    /**
     * 注册按钮点击事件
     */
    private void btnRegisterOnClick() {
        // 点击“注册”按钮
        Log.i(TAG, "点击“注册”按钮");
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String captcha = etCaptcha.getText().toString().trim();
        String passwordConfirm = etPasswordConfirm.getText().toString().trim();
        if (!checkRegisterInput(email, password, passwordConfirm, captcha))
            return;
        AlertUtil.showToast(getContext(), "注册中...");
        registerViewModel.register(email, password, captcha);
    }

    /**
     * 注册响应
     */
    private ResponseOperation registerResponse = new ResponseOperation(TAG,getView()) {
        @Override
        public void onSuccess(ResponseDto response) {
            // 注册成功
            clearInput();// 清空输入框
            AlertUtil.showDialog(getContext(), "注册成功");
            if (getActivity() instanceof AuthorityActivity)
                ((AuthorityActivity) getActivity()).changeToLogin();// 切换到登录
        }
    };

    /**
     * 清空输入框
     */
    private void clearInput() {
        // 清空输入框
        etEmail.setText("");
        etPassword.setText("");
        etPasswordConfirm.setText("");
        etCaptcha.setText("");
    }

    /**
     * 获取验证码按钮点击事件
     */
    public void btnGetCaptchaOnClick() {
        // 点击获取验证码
        Log.i(TAG, "点击“获取验证码”按钮");
        AlertUtil.showToast(getContext(), "获取验证码中...");
        String email = etEmail.getText().toString().trim();
        if (CheckInputUtil.checkEmpty(email,tilEmail,"请输入邮箱")&&CheckInputUtil.checkEmail(email,tilEmail)){
            registerViewModel.getCaptcha(email);
        }
    }

    /**
     * 获取验证码响应
     */
    ResponseOperation getCaptchaResponse = new ResponseOperation(TAG,getView()) {
        @Override
        public void onSuccess(ResponseDto response) {
            // 获取验证码成功
            Log.i(TAG, "验证码已发送");
            AlertUtil.showToast(getContext(), "验证码已发送");
            disableBtnGetCaptcha();
        }
    };

    /**
     * 禁用获取验证码按钮
     */
    private void disableBtnGetCaptcha() {
        // 设置获取验证码按钮60s倒计时
        btnGetCaptcha.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 60; i > 0; i--) {
                    final int finalI = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnGetCaptcha.setText(finalI + "s后重发");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnGetCaptcha.setText("获取验证码");
                        btnGetCaptcha.setEnabled(true);
                    }
                });
            }
        }).start();
    }
}