package com.ebook.app.view.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
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
    private PageMeBinding binding;
    private ConstraintLayout setInfo,setEmail,setPwd;
    private ImageView imgAvatar;
    private MeViewModel viewModel;
    private Observer<ResponseDto> userInfoObserver;
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
        initUserInfoObserver();//初始化用户信息观察者
        loadUserInfo();
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

    private void setAvatar(String url){
        Picasso.with(getContext()).load(url).into(imgAvatar);
    }

    private ResponseOperation userInfoOperation = new ResponseOperation("userInfo") {
        @Override
        public void onSuccess(ResponseDto response) {
            user=new User(response.getData().getJSONObject("user"));
            setAvatar(user.getAvatar());
            System.out.println(user.getName());
            binding.setEmail(user.getEmail());
            binding.setName(user.getName());
        }

        @Override
        public void onParamError(ResponseDto response) {
            AlertUtil.showToast(getContext(),"请重新登录");
            Intent intent = new Intent(getActivity(), AuthorityActivity.class);
            startActivity(intent);
        }

        @Override
        public void showError(String msg) {
        }
    };

    private void initUserInfoObserver(){
        userInfoObserver=new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto responseDto) {
                userInfoOperation.onRespond(responseDto);
            }
        };
        viewModel.getUserInfoResponse().observe(getViewLifecycleOwner(),userInfoObserver);
        prefsUtil=SharedPrefsUtil.with(getContext());
    }

    private void loadUserInfo(){
        String token=prefsUtil.getString("token","");
        System.out.println("token:"+token);
        viewModel.loadUserInfo(token);
    }
}