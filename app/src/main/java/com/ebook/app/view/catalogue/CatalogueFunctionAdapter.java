package com.ebook.app.view.catalogue;

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

public class CatalogueFunctionAdapter extends RecyclerView.Adapter<CatalogueFunctionAdapter.CatalogueFunctionHolder> {

    private List<Function> list;
    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(int position,int fid);
    }

    public CatalogueFunctionAdapter(List<Function> list, OnClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CatalogueFunctionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogue_function, parent, false);
        return new CatalogueFunctionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueFunctionHolder holder, int position) {
        Function function=list.get(position);
        holder.label.setText(function.getName());
        holder.itemView.setOnClickListener(v->listener.onClick(position,list.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Function> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class CatalogueFunctionHolder extends RecyclerView.ViewHolder{
        TextView label;
        public CatalogueFunctionHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.catalogue_function_item_label);
        }
    }
}
