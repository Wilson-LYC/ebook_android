package com.ebook.app.view.function;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.Function;

import java.util.ArrayList;
import java.util.List;

public class FunctionListAdapter extends RecyclerView.Adapter<FunctionListAdapter.FunctionHolder> {
    private List<Function> list;
    private OnClickListener listener;

    int face=R.layout.item_function;

    public interface OnClickListener{
        void onClick(int position,int fid);
    }

    public FunctionListAdapter(List<Function> list, OnClickListener listener) {
        if (list==null){
            list=new ArrayList<>();
        }
        this.list = list;
        this.listener = listener;
    }

    public FunctionListAdapter(List<Function> list, OnClickListener listener, int face) {
        if (list==null){
            list=new ArrayList<>();
        }
        this.list = list;
        this.listener = listener;
        this.face = face;
    }

    @NonNull
    @Override
    public FunctionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(face, parent, false);
        return new FunctionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionHolder holder, int position) {
        Function function=list.get(position);
        holder.tvName.setText(function.getName());
        holder.tvUsage.setText(function.getUsage());
        holder.itemView.setOnClickListener(v->listener.onClick(position,function.getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Function> list){
        this.list=list;
        notifyDataSetChanged();
    }


    public class FunctionHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvUsage;
        public FunctionHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_function_name);
            tvUsage = itemView.findViewById(R.id.item_function_usage);
        }
    }
}
