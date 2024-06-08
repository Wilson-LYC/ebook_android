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
    private void loadFunction() {
        function.setValue(new Function(fid++, "VLOOKUP", null,"# Hello World\n" +
                "\n" +
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "This is a **Markdown** document with [link](https://www.example.com).\n"+
                "T123123123123\n"));
    }
    public LiveData<Function> getFunction(){
        return function;
    }
}
