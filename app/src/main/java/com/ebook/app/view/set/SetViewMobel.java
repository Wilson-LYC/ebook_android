package com.ebook.app.view.set;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.RequestCallback;

public class SetViewMobel extends ViewModel {
    MutableLiveData<ResponseDto> updateResponse=new MutableLiveData<>();
    public MutableLiveData<ResponseDto> getUpdateResponse() {
        return updateResponse;
    }

    UserRepository userRepository=new UserRepository();

    public void updateInfo(int id,String name,String token){
        userRepository.updateInfo(id,name,token,new RequestCallback(updateResponse));
    }
}
