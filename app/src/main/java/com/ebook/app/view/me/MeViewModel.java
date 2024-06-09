package com.ebook.app.view.me;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.User;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.GeneralCallback;
import com.ebook.app.view.set.activity.SetInfoActivity;

import java.io.IOException;

import okhttp3.Call;

public class MeViewModel extends ViewModel {
    private final static String TAG = "MeViewModel";
    private Context context;
    MutableLiveData<User> user = new MutableLiveData<>();
    UserRepository userRepository = new UserRepository();

    public void setContext(Context context) {
        this.context = context;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void loadUser(String token){
        userRepository.getUserByToken(token, new GeneralCallback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                super.onFailure(call, e);
                AlertUtil.showToast(context, "无法连接服务器");
            }

            @Override
            public void onSucceed(ResponseDto response) {
                if (response.getCode()!=200) {
                    AlertUtil.showToast(context, response.getMsg());
                    goToLogin();
                    return;
                }
                JSONObject userJson = response.getData().getJSONObject("user");
                User newUser = userJson.toJavaObject(User.class);
                user.postValue(newUser);
            }

            @Override
            public void onError(Exception e) {
                AlertUtil.showToast(context, "系统错误");
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(context, SetInfoActivity.class);
        context.startActivity(intent);
    }
}