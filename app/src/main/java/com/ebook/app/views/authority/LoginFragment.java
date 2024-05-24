package com.ebook.app.views.authority;

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
import com.ebook.app.dtos.ResponseDto;
import com.ebook.app.viewmodel.LoginViewModel;
import com.ebook.app.util.AlertUtil;

public class LoginFragment extends Fragment {

    private EditText edEmail, edPassword;
    private Button btnLogin;
    private LoginViewModel loginViewModel;
    Observer<ResponseDto> observer;

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
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        edEmail = getView().findViewById(R.id.login_ed_email);
        edPassword = getView().findViewById(R.id.login_ed_password);
        btnLogin= getView().findViewById(R.id.login_btn_login);
        observer = new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                Log.d("LoginFragment", response.getMsg());
                switch (response.getCode()){
                    case 200:
                        AlertUtil.showToast(getContext(),"登录成功");
                        break;
                    case 400:
                        AlertUtil.showErrorDialog(getContext(),"用户名或密码错误");
                        break;
                    case 500:
                        AlertUtil.showErrorDialog(getContext(),"服务器错误");
                        break;
                    default:
                        AlertUtil.showErrorDialog(getContext(),"未知错误");
                }
            }
        };
        loginViewModel.getLiveData().observe(getViewLifecycleOwner(), observer);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                ResponseDto parmsValidate = verifyValidLogin(email,password);
                if (parmsValidate.getCode() != 200) {
                    AlertUtil.showErrorDialog(getContext(), parmsValidate.getMsg());
                    return;
                }
                loginViewModel.login(email,password);
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
        loginViewModel.getLiveData().removeObserver(observer);
    }

    private ResponseDto verifyValidLogin(String email,String password) {
        //校验邮箱密码是否为空
        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseDto(400,"邮箱或密码不能为空");
        }
        //校验邮箱格式
        if (!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            return new ResponseDto(400,"请输入正确的邮箱地址");
        }
        return new ResponseDto(200,"校验成功");
    }
}