package com.ebook.app.view.ai;

import android.nfc.tech.MifareUltralight;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.AiReposity;
import com.ebook.app.util.RequestCallback;

public class AiViewModel extends ViewModel {
    MutableLiveData<ResponseDto> message;

    public AiViewModel() {
        this.message=new MutableLiveData<>();
        Log.d("AiViewModel", "创建");
    }

    public MutableLiveData<ResponseDto> getMessage() {
        return message;
    }
    AiReposity aiReposity=new AiReposity();
    public void sendMsg(String msg){
        aiReposity.sendMsg(msg, new RequestCallback(message));
    }

    @Override
    public void onCleared() {
        super.onCleared();
        Log.d("AiViewModel", "销毁");
    }
}