package com.ebook.app.view.catalogue;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.FunctionCategory;

import java.util.List;

public class CatalogueCategoryAdapter extends RecyclerView.Adapter<CatalogueCategoryAdapter.CatalogueCategoryHolder> {
    private List<FunctionCategory> list;
    private OnCategoryClickListener listener;
    private int index = 0;

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    public CatalogueCategoryAdapter(List<FunctionCategory> list, OnCategoryClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CatalogueCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogue_category, parent, false);
        return new CatalogueCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueCategoryHolder holder, int position) {
        holder.textView.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(position));
        holder.itemView.setSelected(index== position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void changeIndex(int newIndex){
        int oldIndex = index;
        index = newIndex;
        notifyItemChanged(oldIndex);
        notifyItemChanged(index);
    }

    public class CatalogueCategoryHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CatalogueCategoryHolder (View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.catalogue_category_item_label);
        }
    }

}
