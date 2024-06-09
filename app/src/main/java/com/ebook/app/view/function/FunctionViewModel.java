package com.ebook.app.view.function;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Function;
import com.ebook.app.repository.FunctionRepository;
import com.ebook.app.util.RequestCallback;

public class FunctionViewModel extends ViewModel {
    MutableLiveData<ResponseDto> function = new MutableLiveData<>();
    public LiveData<ResponseDto> getFunction(){
        return function;
    }
    FunctionRepository functionRepository=new FunctionRepository();
    public void loadFunction(int id) {
        functionRepository.getFunctionById(id, new RequestCallback(function));
    }
}
