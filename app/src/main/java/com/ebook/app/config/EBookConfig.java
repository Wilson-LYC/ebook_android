package com.ebook.app.config;

import com.ebook.app.R;
import com.ebook.app.model.FunctionCategory;

import java.util.ArrayList;
import java.util.List;

public class EBookConfig {
    public static List<FunctionCategory> categoryList=new ArrayList<>();
    static {
        for(int i=1;i<=10;i++) {
            categoryList.add(new FunctionCategory("分类" + i, R.drawable.avatar));
        }
    }

}
