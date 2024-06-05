package com.ebook.app.view.authority.fragment;

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
import android.widget.EditText;

import com.ebook.app.R;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.util.SharedPrefsUtil;
import com.ebook.app.view.authority.viewmodel.LoginViewModel;
import com.ebook.app.view.authority.viewmodel.RegisterViewModel;
import com.ebook.app.view.authority.AuthorityActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    final String TAG = "RegisterFragment";
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
        View view=inflater.inflate(R.layout.fragment_register, container, false);
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
                registerResponse(response);
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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 移除观察者
        registerViewModel.getRegisterLiveData().removeObserver(registerObserver);
        registerViewModel.getGetCaptchaLiveData().removeObserver(getCaptchaObserver);
    }

    private boolean checkEmail(String email) {
        // 校验邮箱
        if (email==null || email.isEmpty()) {
            Log.e(TAG, "邮箱不能为空");
            tilEmail.setError("邮箱不能为空");
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            Log.e(TAG, "邮箱格式不正确");
            tilEmail.setError("邮箱格式不正确");
            return false;
        }
        return true;
    }

    private boolean checkPassword(String password) {
        // 校验密码
        if (password == null || password.isEmpty()) {
            Log.e(TAG, "密码不能为空");
            tilPassword.setError("密码不能为空");
            return false;
        }
        return true;
    }

    private boolean checkPasswordConfirm(String password, String passwordConfirm) {
        // 校验确认密码
        if (passwordConfirm == null || passwordConfirm.isEmpty()) {
            Log.e(TAG, "确认密码不能为空");
            tilPasswordConfirm.setError("确认密码不能为空");
            return false;
        }
        if (!password.equals(passwordConfirm)) {
            Log.e(TAG, "两次密码不一致");
            tilPasswordConfirm.setError("两次密码不一致");
            return false;
        }
        return true;
    }

    private boolean checkCaptcha(String captcha) {
        // 校验验证码
        if (captcha == null || captcha.isEmpty()) {
            Log.e(TAG, "验证码不能为空");
            tilCaptcha.setError("验证码不能为空");
            return false;
        }
        return true;
    }

    private boolean checkRegisterInput(String email, String password, String passwordConfirm, String captcha) {
        // 校验注册数据
        return checkEmail(email) && checkPassword(password) && checkPasswordConfirm(password, passwordConfirm) && checkCaptcha(captcha);
    }


    private void clearInput() {
        // 清空输入框
        etEmail.setText("");
        etPassword.setText("");
        etPasswordConfirm.setText("");
        etCaptcha.setText("");
    }

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
    private void registerResponse(ResponseDto response) {
        // 注册响应
        if (response.getCode() == 200) {
            registerSuccess();
        }else {
            registerFailed(response);
        }
    }

    private void registerSuccess() {
        // 注册成功
        Log.i(TAG, "注册成功");
        clearInput();// 清空输入框
        AlertUtil.showDialog(getContext(), "注册成功");
        if (getActivity() instanceof AuthorityActivity)
            ((AuthorityActivity) getActivity()).changeToLogin();// 切换到登录
    }
    private void registerFailed(ResponseDto response) {
        // 注册失败
        Log.e(TAG, "注册失败:"+response.getMsg());
        AlertUtil.showDialog(getContext(), response.getMsg());
    }

    public void btnGetCaptchaOnClick() {
        // 点击获取验证码
        Log.i(TAG, "点击“获取验证码”按钮");
        AlertUtil.showToast(getContext(), "获取验证码中...");
        String email = etEmail.getText().toString().trim();
        if (checkEmail(email)){
            registerViewModel.getCaptcha(email);
        }
    }

    ResponseOperation getCaptchaResponse = new ResponseOperation(TAG) {

        @Override
        public void onSuccess(ResponseDto response) {
            // 获取验证码成功
            Log.i(TAG, "验证码已发送");
            AlertUtil.showToast(getContext(), "验证码已发送");
            disableBtnGetCaptcha();
        }

        @Override
        public void showDialog(String msg) {
            AlertUtil.showDialog(getContext(), msg);
        }
    };

    private void getCaptchaFailed(ResponseDto response) {

    }

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