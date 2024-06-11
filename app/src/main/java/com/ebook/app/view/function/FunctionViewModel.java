package com.ebook.app.view.function;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.FunctionRepository;
import com.ebook.app.util.RequestCallback;

public class FunctionViewModel extends ViewModel {
    MutableLiveData<ResponseDto> function = new MutableLiveData<>();
    MutableLiveData<ResponseDto> response=new MutableLiveData<>();
    public LiveData<ResponseDto> getFunction(){
        return function;
    }
    FunctionRepository functionRepository=new FunctionRepository();
    public void loadFunction(int id, String token) {
        functionRepository.getFunctionById(id,token, new RequestCallback(function));
    }
    public void like(int id,String token){
        functionRepository.likeFunction(id,token, new RequestCallback(response));
    }
    public void disLike(int id,String token){
        functionRepository.disLikeFunction(id,token, new RequestCallback(response));
    }
}
