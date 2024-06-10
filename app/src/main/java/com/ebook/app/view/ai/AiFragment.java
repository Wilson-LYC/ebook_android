package com.ebook.app.view.ai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ebook.app.databinding.PageAiBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Message;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.InputValidator;
import com.ebook.app.util.ResponseOperation;

import java.util.List;

public class AiFragment extends Fragment {
    private PageAiBinding binding;
    private RecyclerView rvMessageList;
    private AiMessageAdapter messageAdapter;
    private AiViewModel viewModel;
    private Button btnSend;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public AiFragment() {
    }

    public static AiFragment newInstance(String param1, String param2) {
        AiFragment fragment = new AiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = PageAiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("AI","创建ai");
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("AI","销毁ai");
        viewModel=null;
    }

    private void init(){
        initViewModel();
        initMessageList();
        initSend();
        initReceive();
    }

    private void initViewModel(){
        if(viewModel==null){
            Log.i("AI","创建ViewModel");
        }
        viewModel=new AiViewModel();
    }

    private void initMessageList(){
        rvMessageList=binding.aiRvMessage;
        messageAdapter=new AiMessageAdapter();
        rvMessageList.setAdapter(messageAdapter);
        rvMessageList.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initSend(){
        btnSend=binding.aiBtnSend;
        btnSend.setOnClickListener(v->send());
    }
    private void send(){
        String sendMsg=binding.getSendMsg();
        if(!InputValidator.isNotEmpty(sendMsg)){
            AlertUtil.showToast(getContext(),"请输入内容");
            return;
        }
        Message message=new Message("You",sendMsg);
        messageAdapter.addItem(message);
        Message messageAI=new Message("AI","E宝正在思考🤔",true);
        messageAdapter.addItem(messageAI);
        //滚动到最后一条
        rvMessageList.scrollToPosition(messageAdapter.getItemCount()-1);
        binding.setSendMsg(null);
        btnSend.setEnabled(false);
        viewModel.sendMsg(sendMsg);
    }
    private void initReceive(){
        viewModel.getMessage().observe(getViewLifecycleOwner(),responseDto -> {
            new ResponseOperation("AI",getContext()){
                @Override
                public void onSuccess(ResponseDto response) {
                    Message message=new Message("AI",response.getData().getString("ans"));
                    messageAdapter.updateAiMessage(message);
                    btnSend.setEnabled(true);
                    rvMessageList.scrollToPosition(messageAdapter.getItemCount()-1);
                }

                @Override
                public void showError(String msg) {
                    AlertUtil.showToast(getContext(),msg);
                }
            }.onRespond(responseDto);
        });
    }
}