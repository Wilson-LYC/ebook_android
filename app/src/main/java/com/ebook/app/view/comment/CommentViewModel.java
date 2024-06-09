package com.ebook.app.view.comment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Comment;
import com.ebook.app.repository.CommentReposity;
import com.ebook.app.util.RequestCallback;

import java.util.List;

public class CommentViewModel extends ViewModel {
    MutableLiveData<ResponseDto> commentList=new MutableLiveData<>();
    MutableLiveData<ResponseDto> sendComment=new MutableLiveData<>();
    public LiveData<ResponseDto> getCommentList() {
        return commentList;
    }

    public LiveData<ResponseDto> getSendComment() {
        return sendComment;
    }

    CommentReposity commentReposity=new CommentReposity();
    public void loadCommentList(int fid){
        commentReposity.getCommentListByFid(fid,new RequestCallback(commentList));
    }
    public void sendComment(Comment comment,String token){
        commentReposity.sendComment(comment.getFid(),comment.getContent(),token,new RequestCallback(sendComment));
    }
}
