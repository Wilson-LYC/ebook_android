package com.ebook.app.config;

import com.ebook.app.model.Comment;
import com.ebook.app.model.Function;
import com.ebook.app.model.Category;
import com.ebook.app.model.Message;
import com.ebook.app.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataMock {

    public static List<Comment> commentList=new ArrayList<>();
    public static List<Category> categoryList=new ArrayList<>();
    public static List<Message> messageList=new ArrayList<>();
    static {
        messageList.add(new Message("You","你好!"));
        messageList.add(new Message("EBook","欢迎使用EBook!在这里,学习Excel函数使用技巧,赋能未来!"));
        messageList.add(new Message("You","谢谢!"));
        messageList.add(new Message("EBook","不客气!"));
        for (int i=1;i<=20;i++)
            messageList.add(new Message("EBook","不客气!"));

        for (int i=1;i<=20;i++)
            commentList.add(new Comment(new User(),"你好!","2019-01-01"));

    }
}
