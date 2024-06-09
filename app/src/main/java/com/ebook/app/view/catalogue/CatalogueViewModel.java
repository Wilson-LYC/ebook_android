package com.ebook.app.view.catalogue;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.FunctionRepository;
import com.ebook.app.util.RequestCallback;

public class CatalogueViewModel extends ViewModel {
    MutableLiveData<ResponseDto> categoryList = new MutableLiveData<>();
    MutableLiveData<ResponseDto> functionList = new MutableLiveData<>();
    FunctionRepository functionRepository = new FunctionRepository();

    public MutableLiveData<ResponseDto> getCategoryList() {
        return categoryList;
    }

    public MutableLiveData<ResponseDto> getFunctionList() {
        return functionList;
    }

    public void loadCategoryList() {
        functionRepository.getCategoryList(new RequestCallback(categoryList));
    }
    public void loadFunctionList(int cid) {
        functionRepository.getFunctionListByCid(cid, new RequestCallback(functionList));
    }
}
