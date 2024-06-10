package com.ebook.app.view.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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
import com.ebook.app.view.set.SetEmailActivity;
import com.ebook.app.view.set.SetInfoActivity;
import com.ebook.app.view.set.SetPasswordActivity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

public class MeFragment extends Fragment {
    private PageMeBinding binding;
    private SmartRefreshLayout refreshLayout;
    private ConstraintLayout setInfo,setEmail,setPwd;
    private ImageView imgAvatar;
    private MeViewModel viewModel;
    private SharedPrefsUtil prefsUtil;
    private User user=new User();
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
        initUserInfo();
        initMenu();
        initRefresh();
    }

    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(MeViewModel.class);
        binding.setLifecycleOwner(this);
    }

    private void initUserInfo(){
        viewModel.getUser().observe(getViewLifecycleOwner(),response -> {
            new ResponseOperation("GetUserInfo",getContext()){
                @Override
                public void onSuccess(ResponseDto response) {
                    JSONObject userJson=response.getData().getJSONObject("user");
                    user=userJson.toJavaObject(User.class);
                    setAvatar(user.getAvatar());
                    binding.setName(user.getName());
                    binding.setEmail(user.getEmail());
                    refreshLayout.finishRefresh();
                }

                @Override
                public void showError(String msg) {
                    AlertUtil.showToast(getContext(),msg);
                    goToLogin();
                }
            }.onRespond(response);
        });
        prefsUtil=SharedPrefsUtil.with(getContext());
        String token=prefsUtil.getString("token","");
        if (token==null || token.equals("")){
            AlertUtil.showToast(getContext(),"请先登录");
            goToLogin();
            return;
        }
        user.setToken(token);
        viewModel.loadUser(token);
    }

    private void initMenu(){
        setInfo=binding.meSetInfo;
        setEmail=binding.meSetEmail;
        setPwd=binding.meSetPassword;
        imgAvatar=binding.meImgAvatar;
        setInfo.setOnClickListener(v -> setInfoOnClick());
        setEmail.setOnClickListener(v -> setEmailOnClick());
        setPwd.setOnClickListener(v -> setPwdOnClick());
    }

    private void setInfoOnClick() {
        Intent intent = new Intent(getActivity(), SetInfoActivity.class);
        intent.putExtra("name",user.getName());
        intent.putExtra("id",user.getId());
        startActivity(intent);
    }

    private void setEmailOnClick() {
        Intent intent = new Intent(getActivity(), SetEmailActivity.class);
        intent.putExtra("id",user.getId());
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

    /**
     * 跳转到登录页面
     */
    private void goToLogin(){
        Intent intent = new Intent(getActivity(), AuthorityActivity.class);
        startActivity(intent);
    }

    private void initRefresh(){
        refreshLayout=binding.refreshLayout;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                String token=prefsUtil.getString("token","");
                if (token==null || token.equals("")){
                    AlertUtil.showToast(getContext(),"请先登录");
                    goToLogin();
                    return;
                }
                viewModel.loadUser(token);
                refreshlayout.finishRefresh(1500);
            }
        });
    }
}