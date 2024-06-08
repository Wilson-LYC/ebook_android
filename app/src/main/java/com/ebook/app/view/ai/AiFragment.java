package com.ebook.app.view.ai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebook.app.R;
import com.ebook.app.config.DataMock;
import com.ebook.app.databinding.PageAiBinding;
import com.ebook.app.model.Message;

import java.util.List;

public class AiFragment extends Fragment {
    private PageAiBinding binding;
    private RecyclerView rvMessageList;
    private AiMessageAdapter messageAdapter;
    private List<Message> messageList;
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

        init();
    }

    private void init(){
        initRecyclerView();
    }

    private void initRecyclerView(){
        messageAdapter=new AiMessageAdapter();
        rvMessageList=binding.aiRvMessage;
        rvMessageList.setAdapter(messageAdapter);
        rvMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageList= DataMock.messageList;
        messageAdapter.setList(messageList);
    }
}