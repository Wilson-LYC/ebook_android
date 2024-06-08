package com.ebook.app.config;

import com.ebook.app.model.Function;
import com.ebook.app.model.FunctionCategory;
import com.ebook.app.model.Message;

import java.util.ArrayList;
import java.util.List;

public class DataMock {

    public static List<FunctionCategory> catalogue=new ArrayList<>();
    public static List<Message> messageList=new ArrayList<>();
    static {
        int id=0;
        for (int i=1;i<=10;i++){
            List<Function> functionList=new ArrayList<>();
            for(int j=1;j<=10;j++){
                id++;
                Function function=new Function(id,"函数"+id);
                functionList.add(function);
            }
            FunctionCategory category=new FunctionCategory("分类"+i,functionList);
            catalogue.add(category);
        }

        messageList.add(new Message("You","你好!"));
        messageList.add(new Message("EBook","欢迎使用EBook!在这里,学习Excel函数使用技巧,赋能未来!"));
        messageList.add(new Message("You","谢谢!"));
        messageList.add(new Message("EBook","不客气!"));
    }
}
