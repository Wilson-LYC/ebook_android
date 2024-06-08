package com.ebook.app.model;
import com.ebook.app.R;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private int icon= R.drawable.logo_circle;
    private List<Function> functionList;

    public Category(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public Category(String name, List<Function> functionList) {
        this.name = name;
        this.functionList = functionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public List<Function> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Function> functionList) {
        this.functionList = functionList;
    }
}
