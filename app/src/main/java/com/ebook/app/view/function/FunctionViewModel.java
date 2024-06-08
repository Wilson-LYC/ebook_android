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
                "## 代码测试\n"+
                "\n" +
                "```java\n" +
                "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "## 标题测试\n"+
                "\n" +
                "大家好，我是测试文本\n"+
                "\n" +
                "## 代码测试\n"+
                "\n" +
                "```java\n" +
                "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "## 标题测试\n"+
                "\n" +
                "大家好，我是测试文本\n"+
                "\n" +
                "## 代码测试\n"+
                "\n" +
                "```java\n" +
                "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "## 标题测试\n"+
                "\n" +
                "大家好，我是测试文本\n"));
    }
    public LiveData<Function> getFunction(){
        return function;
    }
}
