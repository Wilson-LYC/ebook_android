package com.ebook.app.config;

import com.ebook.app.R;
import com.ebook.app.model.FunctionCategory;

import java.util.ArrayList;
import java.util.List;

public class FunctionCategoryConfig {
    public static List<FunctionCategory> categories=new ArrayList<>();

    static {
        categories.add(new FunctionCategory("测试1", R.drawable.avatar));
        categories.add(new FunctionCategory("测试2", R.drawable.avatar));
        categories.add(new FunctionCategory("测试3", R.drawable.avatar));
        categories.add(new FunctionCategory("测试4", R.drawable.avatar));
        categories.add(new FunctionCategory("测试5", R.drawable.avatar));
        categories.add(new FunctionCategory("测试6", R.drawable.avatar));
        categories.add(new FunctionCategory("测试7", R.drawable.avatar));
        categories.add(new FunctionCategory("测试8", R.drawable.avatar));
        categories.add(new FunctionCategory("测试9", R.drawable.avatar));
        categories.add(new FunctionCategory("测试10", R.drawable.avatar));
    }
}
