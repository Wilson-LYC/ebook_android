package com.ebook.app.view.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.FunctionRepository;
import com.ebook.app.util.RequestCallback;

public class HomeViewModel extends ViewModel {
    public HomeViewModel() {
        loadRecommendFunctions();
        loadCategoryList();
    }

    private MutableLiveData<ResponseDto> functions = new MutableLiveData<>();
    private MutableLiveData<ResponseDto> categories = new MutableLiveData<>();

    public LiveData<ResponseDto> getFunctions() {
        return functions;
    }

    public MutableLiveData<ResponseDto> getCategories() {
        return categories;
    }

    FunctionRepository functionRepository=new FunctionRepository();
    public void loadRecommendFunctions() {
        functionRepository.getRecommendedFunction(new RequestCallback(functions));
    }
    public void loadCategoryList() {
        functionRepository.getCategoryList(new RequestCallback(categories));
    }
}