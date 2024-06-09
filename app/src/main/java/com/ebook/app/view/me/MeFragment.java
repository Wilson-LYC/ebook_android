package com.ebook.app.view.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.ebook.app.R;
import com.ebook.app.databinding.PageMeBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.User;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.util.SharedPrefsUtil;
import com.ebook.app.view.authority.AuthorityActivity;
import com.ebook.app.view.set.activity.SetEmailActivity;
import com.ebook.app.view.set.activity.SetInfoActivity;
import com.ebook.app.view.set.activity.SetPasswordActivity;
import com.squareup.picasso.Picasso;

public class MeFragment extends Fragment {
    private static final String TAG="MeFragment";
    private PageMeBinding binding;
    private ConstraintLayout setInfo,setEmail,setPwd;
    private ImageView imgAvatar;
    private MeViewModel viewModel;
    private Observer<ResponseDto> userObserver;
    private User user;
    private SharedPrefsUtil prefsUtil;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MeFragment() {
    }
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        binding=PageMeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        initViewModel();
        initElement();//初始化元素
        initButtonListener();//初始化按钮
        initUserObserver();//初始化用户信息观察者
        loadUserInfo();//初始化时，加载用户信息
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(MeViewModel.class);
        binding.setLifecycleOwner(this);
    }

    private void initElement(){
        setInfo=binding.meSetInfo;
        setEmail=binding.meSetEmail;
        setPwd=binding.meSetPassword;
        imgAvatar=binding.meImgAvatar;
        prefsUtil=SharedPrefsUtil.with(getContext());
    }

    private void initButtonListener(){
        setInfo.setOnClickListener(v -> setInfoOnClick());
        setEmail.setOnClickListener(v -> setEmailOnClick());
        setPwd.setOnClickListener(v -> setPwdOnClick());
    }

    private void setInfoOnClick() {
        System.out.println("setInfoOnClick");
        Intent intent = new Intent(getActivity(), SetInfoActivity.class);
        startActivity(intent);
    }
    private void setEmailOnClick() {
        Intent intent = new Intent(getActivity(), SetEmailActivity.class);
        startActivity(intent);
    }
    private void setPwdOnClick() {
        Intent intent = new Intent(getActivity(), SetPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 设置头像
     * @param url 头像地址
     */
    private void setAvatar(String url){
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(imgAvatar);
    }


    private ResponseOperation userOperation = new ResponseOperation("User") {
        @Override
        public void onSuccess(ResponseDto response) {
            JSONObject userJSon=response.getData().getJSONObject("user");
            user=userJSon.toJavaObject(User.class);
            //设置头像
            setAvatar(user.getAvatar());
            //设置用户名和邮箱
            binding.setEmail(user.getEmail());
            binding.setName(user.getName());
        }

        @Override
        public void onParamError(ResponseDto response) {
            AlertUtil.showToast(getContext(),"请重新登录");
            goToLogin();
        }

        @Override
        public void showError(String msg) {
        }
    };

    private void initUserObserver(){
        userObserver=new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto responseDto) {
                userOperation.onRespond(responseDto);
            }
        };
        viewModel.getUserInfoResponse().observe(getViewLifecycleOwner(),userObserver);
    }

    /**
     * 加载用户信息
     */
    private void loadUserInfo(){
        Log.i(TAG,"加载用户信息");
        String token=prefsUtil.getString("token","");
        if (token==null || token.equals("")){
            AlertUtil.showToast(getContext(),"未登录");
            goToLogin();
            return;
        }
        viewModel.loadUserInfo(token);
    }

    /**
     * 跳转到登录页面
     */
    private void goToLogin(){
        Intent intent = new Intent(getActivity(), AuthorityActivity.class);
        startActivity(intent);
    }
}