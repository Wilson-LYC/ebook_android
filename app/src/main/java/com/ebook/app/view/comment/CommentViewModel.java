package com.ebook.app.view.comment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.config.DataMock;
import com.ebook.app.model.Comment;

import java.util.List;

public class CommentViewModel extends ViewModel {
    int fid;
    MutableLiveData<List<Comment>> commentList=new MutableLiveData<>();

    public void creat(int fid){
        this.fid=fid;
        loadCommentList();
    }
    public void refresh(){
        loadCommentList();
    }
    private void loadCommentList(){
        // Load comment list from database
        commentList.postValue(DataMock.commentList);
    }
    public LiveData<List<Comment>> getCommentList(){
        return commentList;
    }
}
