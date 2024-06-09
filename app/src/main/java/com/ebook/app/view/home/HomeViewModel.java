package com.ebook.app.view.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.config.DataMock;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Function;
import com.ebook.app.repository.FunctionRepository;
import com.ebook.app.util.RequestCallback;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    public HomeViewModel() {
        loadRecommendFunctions();
    }

    private MutableLiveData<ResponseDto> functions = new MutableLiveData<>();

    public LiveData<ResponseDto> getFunctions() {
        return functions;
    }

    FunctionRepository functionRepository=new FunctionRepository();
    public void loadRecommendFunctions() {
        functionRepository.getRecommendedFunction(new RequestCallback(functions));
    }
}