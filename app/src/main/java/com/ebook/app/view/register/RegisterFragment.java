package com.ebook.app.view.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ebook.app.databinding.PageRegisterBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Notify;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.InputValidator;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.view.authority.AuthorityActivity;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {
    private PageRegisterBinding binding;
    final String TAG = "Register Page";
    private RegisterViewModel viewModel;
    private TextInputLayout tilEmail,tilPassword,tilCaptcha,tilPasswordConfirm;
    private Button btnRegister, btnGetCaptcha;

    Observer<ResponseDto> registerObserver,sendCaptchaObserver;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
    }

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
        binding=PageRegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getRegisterResponse().removeObserver(registerObserver);
        viewModel.getSendCaptchaResponse().removeObserver(sendCaptchaObserver);
        viewModel=null;
    }

    private void init(){
        initViewModel();
        initSendCaptcha();
        initRegister();
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setLifecycleOwner(this);
    }
    private void initSendCaptcha(){
        tilEmail=binding.registerTilEmail;
        btnGetCaptcha=binding.registerBtnGetCaptcha;
        btnGetCaptcha.setOnClickListener(v->sendCaptcha());
        viewModel.getSendCaptchaResponse().observe(getViewLifecycleOwner(),ResponseDto->{
            new ResponseOperation("SendCaptcha",getContext()) {
                @Override
                public void onSuccess(ResponseDto response) {
                    AlertUtil.alert(getContext(),new Notify(Notify.TOAST,"验证码已发送"));
                    banBtnSendCaptcha();
                }

                @Override
                public void showError(String msg) {
                    btnGetCaptcha.setEnabled(true);
                    AlertUtil.alert(getContext(),new Notify(Notify.TOAST,msg));
                }
            }.onRespond(ResponseDto);
        });
    }
    private void sendCaptcha(){
        String email= binding.getEmail();
        System.out.println(email);
        if(email==null||email.isEmpty()){
            tilEmail.setError("请输入邮箱");
            return;
        }
        if(!InputValidator.isValidEmail(email)){
            tilEmail.setError("邮箱格式不正确");
            return;
        }
        tilEmail.setError(null);
        viewModel.sendCaptcha(email);
    }
    private void initRegister(){
        tilPassword=binding.registerTilPassword;
        tilCaptcha=binding.registerTilCaptcha;
        tilPasswordConfirm=binding.registerTilConfirmPassword;
        btnRegister=binding.registerBtnRegister;
        btnRegister.setOnClickListener(v->register());
        viewModel.getRegisterResponse().observe(getViewLifecycleOwner(),responseDto -> {
            new ResponseOperation("Register",getContext()) {
                @Override
                public void onSuccess(ResponseDto response) {
                    AlertUtil.alert(getContext(),new Notify(Notify.TOAST,"注册成功"));
                    binding.setEmail(null);
                    binding.setPassword(null);
                    binding.setCaptcha(null);
                    binding.setConfirmPassword(null);
                    ((AuthorityActivity) getActivity()).changeToLogin();
                }

                @Override
                public void showError(String msg) {
                    AlertUtil.alert(getContext(),new Notify(Notify.TOAST,msg));
                }
            }.onRespond(responseDto);
        });
    }
    private void register(){
        String email= binding.getEmail();
        String password= binding.getPassword();
        String captcha= binding.getCaptcha();
        String passwordConfirm= binding.getConfirmPassword();
        if(email==null||email.isEmpty()){
            tilEmail.setError("请输入邮箱");
            return;
        }
        if(!InputValidator.isValidEmail(email)){
            tilEmail.setError("邮箱格式不正确");
            return;
        }
        tilEmail.setError(null);
        if (password==null||password.isEmpty()){
            tilPassword.setError("请输入密码");
            return;
        }
        if(password.length()<6){
            tilPassword.setError("密码长度不能小于6位");
            return;
        }
        tilPassword.setError(null);
        if(passwordConfirm==null||passwordConfirm.isEmpty()){
            tilPasswordConfirm.setError("确认密码不能为空");
            return;
        }
        if(!password.equals(passwordConfirm)){
            tilPasswordConfirm.setError("两次密码不一致");
            return;
        }
        tilPasswordConfirm.setError(null);
        if (captcha==null||captcha.isEmpty()){
            tilCaptcha.setError("请输入验证码");
            return;
        }
        tilCaptcha.setError(null);
        viewModel.register(binding.getEmail(),binding.getPassword(),binding.getCaptcha());
    }

    private void banBtnSendCaptcha(){
        btnGetCaptcha.setEnabled(false);
        new Thread(()->{
            for(int i=60;i>0;i--){
                final int finalI = i;
                getActivity().runOnUiThread(()->{
                    btnGetCaptcha.setText(finalI+"秒后重新发送");
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getActivity().runOnUiThread(()->{
                btnGetCaptcha.setText("获取验证码");
                btnGetCaptcha.setEnabled(true);
            });
        }).start();
    }
}