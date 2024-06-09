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
        viewModel=null;//销毁ViewModel
    }

    private void init(){
        initViewModel();
        initElement();//初始化元素
        initButtonListener();//初始化按钮
        initSendCaptchaObserver();//初始化发送验证码观察者
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setLifecycleOwner(this);
    }
    private void initElement(){
        tilEmail=binding.registerTilEmail;
        tilPassword=binding.registerTilPassword;
        tilCaptcha=binding.registerTilCaptcha;
        tilPasswordConfirm=binding.registerTilConfirmPassword;
        btnRegister=binding.registerBtnRegister;
        btnGetCaptcha=binding.registerBtnGetCaptcha;
    }
    private void initButtonListener(){
        btnRegister.setOnClickListener(v->btnRegisterOnClick());
        btnGetCaptcha.setOnClickListener(v->btnGetCaptchaOnClick());
    }

    private ResponseOperation sendCaptchaOperation =new ResponseOperation("Send Captcha") {
        @Override
        public void onSuccess(ResponseDto response) {
            AlertUtil.alert(getContext(),new Notify(Notify.TOAST,"验证码已发送"));
        }

        @Override
        public void showError(String msg) {
            AlertUtil.alert(getContext(),new Notify(Notify.TOAST,msg));
        }
    };

    private void initSendCaptchaObserver(){
        sendCaptchaObserver=new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                sendCaptchaOperation.onRespond(response);
            }
        };
        viewModel.getSendCaptchaResponse().observe(getViewLifecycleOwner(),sendCaptchaObserver);
    }
    private void btnRegisterOnClick(){

    }
    private void btnGetCaptchaOnClick(){
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
}