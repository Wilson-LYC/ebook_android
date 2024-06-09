package com.ebook.app.view.function;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.model.Function;

public class FunctionViewModel extends ViewModel {
    int fid;
    MutableLiveData<Function> function = new MutableLiveData<>();
    public void create(int fid){
        this.fid=fid;
        loadFunction();
    }
    public void refresh(){
        loadFunction();
    }
    private void loadFunction() {}

    public LiveData<Function> getFunction(){
        return function;
    }
}
