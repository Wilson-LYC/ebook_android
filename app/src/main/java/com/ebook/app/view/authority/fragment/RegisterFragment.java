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
import com.ebook.app.util.SharedPrefsUtil;
import com.ebook.app.view.authority.viewmodel.RegisterViewModel;
import com.ebook.app.view.authority.AuthorityActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    final String TAG = "RegisterFragment";
    private SharedPrefsUtil prefsUtil;//SharedPreferences工具
    private EditText edEmail, edCaptcha, edPassword, edConfirmPassword;//邮箱，验证码，密码，确认密码输入框
    private Button btnRegister, btnGetCaptcha;//注册按钮，获取验证码按钮
    private RegisterViewModel registerViewModel;//注册视图模型
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化
        prefsUtil = SharedPrefsUtil.with(getContext());
        edEmail = view.findViewById(R.id.register_ed_email);
        edCaptcha = view.findViewById(R.id.register_ed_captcha);
        edPassword = view.findViewById(R.id.register_ed_set_pwd);
        edConfirmPassword = view.findViewById(R.id.register_ed_conf_pwd);
        btnRegister = view.findViewById(R.id.register_btn_register);
        btnGetCaptcha = view.findViewById(R.id.register_btn_get_captcha);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        getCaptchaObserver = new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                //获取验证码
                getCaptchaResponse(response);
            }
        };
        registerViewModel.getGetCaptchaLiveData().observe(getViewLifecycleOwner(), getCaptchaObserver);
        registerObserver = new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                //注册
                registerResponse(response);
            }
        };
        registerViewModel.getRegisterLiveData().observe(getViewLifecycleOwner(), registerObserver);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                registerOnClick();
            }
        });
        btnGetCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码
                getCaptchaOnClick();
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
     * 验证邮箱
     * @param email 邮箱
     * @return 验证结果
     */
    private boolean validEmail(String email) {
        boolean result = email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+") && !email.isEmpty();
        if (!result) {
            Log.w(TAG, "邮箱格式不正确");
            AlertUtil.showDialog(getContext(), "邮箱格式不正确");
        }
        return result;
    }

    /**
     * 验证密码
     * @param password 密码
     * @return 验证结果
     */
    private boolean validPassword(String password) {
        boolean result = password.length() >= 6 && password.length() <= 16;
        if (!result){
            Log.w(TAG, "密码长度应在6-16位之间");
            AlertUtil.showDialog(getContext(), "密码长度应在6-16位之间");
        }
        return result;
    }

    /**
     * 验证确认密码
     * @param password 密码
     * @param confirmPassword 确认密码
     * @return 验证结果
     */
    private boolean validConfirmPassword(String password, String confirmPassword) {
        boolean result = password.equals(confirmPassword);
        if (!result) {
            Log.w(TAG, "两次密码不一致");
            AlertUtil.showDialog(getContext(), "两次密码不一致");
        }
        return result;
    }

    /**
     * 验证验证码
     * @param captcha 验证码
     * @return 验证结果
     */
    private boolean validCaptcha(String captcha) {
        boolean result = !captcha.isEmpty();
        if (!result) {
            Log.w(TAG, "验证码不能为空");
            AlertUtil.showDialog(getContext(), "验证码不能为空");
        }
        return result;
    }

    /**
     * 验证注册输入
     * @param email 邮箱
     * @param password 密码
     * @param confirmPassword 确认密码
     * @param captcha 验证码
     * @return 验证结果
     */
    private boolean validRegisterInput(String email, String password, String confirmPassword, String captcha) {
        return validEmail(email)
                && validPassword(password)
                && validConfirmPassword(password, confirmPassword)
                && validCaptcha(captcha);
    }

    /**
     * 清空输入框
     */
    private void clearInput() {
        edEmail.setText("");
        edPassword.setText("");
        edConfirmPassword.setText("");
        edCaptcha.setText("");
    }

    /**
     * 注册
     */
    private void registerOnClick() {
        Log.i(TAG, "点击注册");
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String captcha = edCaptcha.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();
        if (validRegisterInput(email, password, confirmPassword, captcha))
            registerViewModel.register(email, password, captcha);
    }

    /**
     * 注册响应
     * @param response 响应
     */
    private void registerResponse(ResponseDto response) {
        AlertUtil.showToast(getContext(), response.getMsg());
        switch (response.getCode()) {
            case 200:
                registerSuccess();
                break;
            default:
                break;
        }
    }

    /**
     * 注册成功
     */
    private void registerSuccess() {
        // 注册成功
        clearInput();// 清空输入框
        if (getActivity() instanceof AuthorityActivity)
            ((AuthorityActivity) getActivity()).changeToLogin();// 切换到登录
    }

    /**
     * 获取验证码
     */
    public void getCaptchaOnClick() {
        Log.i(TAG, "点击获取验证码");
        String email = edEmail.getText().toString().trim();
        if (validEmail(email))
            registerViewModel.getCaptcha(email);
    }

    /**
     * 获取验证码响应
     * @param response 响应
     */
    private void getCaptchaResponse(ResponseDto response) {
        AlertUtil.showToast(getContext(), response.getMsg());
        switch (response.getCode()) {
            case 200:
                getCaptchaSuccess();
                break;
            default:
                break;
        }
    }

    /**
     * 发送验证码成功
     * 禁用发送按钮60s
     */
    private void getCaptchaSuccess() {
        btnGetCaptcha.setEnabled(false);
        btnGetCaptcha.setBackground(getResources().getDrawable(R.drawable.ebook_button_disabled));
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 60; i >= 0; i--) {
                    final int finalI = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnGetCaptcha.setText(finalI + "s后重新获取");
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
                        btnGetCaptcha.setBackground(getResources().getDrawable(R.drawable.ebook_button));
                    }
                });
            }
        }).start();
    }
}