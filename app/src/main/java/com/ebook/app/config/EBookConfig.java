package com.ebook.app.config;

import com.ebook.app.R;
import com.ebook.app.model.Category;

import java.util.ArrayList;
import java.util.List;

public class EBookConfig {
    public static List<Category> categoryList=new ArrayList<>();
    static {
        categoryList.add(new Category("财务",R.drawable.ic_hs));
        categoryList.add(new Category("日期",R.drawable.ic_hs));
        categoryList.add(new Category("三角函数",R.drawable.ic_hs));
        categoryList.add(new Category("统计",R.drawable.ic_hs));
        categoryList.add(new Category("查找",R.drawable.ic_hs));
        categoryList.add(new Category("数据库",R.drawable.ic_hs));
        categoryList.add(new Category("文本",R.drawable.ic_hs));
        categoryList.add(new Category("逻辑",R.drawable.ic_hs));
        categoryList.add(new Category("信息",R.drawable.ic_hs));
        categoryList.add(new Category("更多",R.drawable.ic_hs));
    }

}
