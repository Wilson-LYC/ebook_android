package com.ebook.app.view.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ebook.app.R;
import com.ebook.app.model.MessageAI;
import com.ebook.app.view.main.adapter.MessageAIAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AiFragment extends Fragment {

    private RecyclerView recyclerView;
    private MessageAIAdapter adapter;
    private List<MessageAI> messageList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AiFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ai, container, false);

//        recyclerView = view.findViewById(R.id.ai_user_message);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        adapter = new MessageAIAdapter(messageList);
//        recyclerView.setAdapter(adapter);




        return view;
    }
}