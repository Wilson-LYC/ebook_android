package com.ebook.app.model;

import java.util.List;

public class FunctionCategory {
    private int id;
    private String name;//分类名称
    private int icon;//分类图标
    boolean selected=false;//是否选中
    private List<Function> functionList;//下属函数列表

    public FunctionCategory(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public FunctionCategory(String name, List<Function> functionList) {
        this.name = name;
        this.functionList = functionList;
    }

    public FunctionCategory(String name, int icon, List<Function> functionList) {
        this.name = name;
        this.icon = icon;
        this.functionList = functionList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
