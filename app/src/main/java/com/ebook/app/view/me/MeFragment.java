package com.ebook.app.view.me;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebook.app.databinding.PageMeBinding;
import com.ebook.app.view.set.activity.SetEmailActivity;
import com.ebook.app.view.set.activity.SetInfoActivity;
import com.ebook.app.view.set.activity.SetPasswordActivity;

public class MeFragment extends Fragment {
    private PageMeBinding binding;
    private ConstraintLayout setInfo,setEmail,setPwd;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MeFragment() {
    }
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        binding=PageMeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInfo = binding.meSetInfo;
        setInfo.setOnClickListener(v->setInfoOnClick());
        setEmail = binding.meSetEmail;
        setEmail.setOnClickListener(v->setEmailOnClick());
        setPwd=binding.meSetPassword;
        setPwd.setOnClickListener(v->setPwdOnClick());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void setInfoOnClick() {
        System.out.println("setInfoOnClick");
        Intent intent = new Intent(getActivity(), SetInfoActivity.class);
        startActivity(intent);
    }
    private void setEmailOnClick() {
        Intent intent = new Intent(getActivity(), SetEmailActivity.class);
        startActivity(intent);
    }
    private void setPwdOnClick() {
        Intent intent = new Intent(getActivity(), SetPasswordActivity.class);
        startActivity(intent);
    }
}