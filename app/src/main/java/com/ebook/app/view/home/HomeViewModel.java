package com.ebook.app.view.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.config.DataMock;
import com.ebook.app.model.Function;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    final static String TAG = "HomeViewModel";
    int id=0;
    public HomeViewModel() {
        getRecommendFunctions();
    }
    private MutableLiveData<List<Function>> functionList = new MutableLiveData<>();

    public void getRecommendFunctions() {
        List<Function> recommendFunctionList = new ArrayList<>();
        for (int i=1;i<=10;i++){
            id++;
            Function function=new Function(id,"函数"+id,"函数"+id+"的使用方法","");
            recommendFunctionList.add(function);
        }
        functionList.postValue(recommendFunctionList);
    }

    public LiveData<List<Function>> getFunctionList() {
        return functionList;
    }
}