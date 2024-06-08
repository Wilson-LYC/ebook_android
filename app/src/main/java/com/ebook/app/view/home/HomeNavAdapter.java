package com.ebook.app.view.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.Category;

import java.util.List;

public class HomeNavAdapter extends RecyclerView.Adapter<HomeNavAdapter.HomeNavItemHolder> {
    private List<Category> list;

    private OnClickListener listener;
    public interface OnClickListener {
        void onClick(int position);
    }

    public HomeNavAdapter(List<Category> list, OnClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeNavItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_nav, parent, false);//获取导航栏项目的布局
        return new HomeNavItemHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeNavItemHolder holder, int position) {
        Category category =list.get(position);
        holder.label.setText(category.getName());
        holder.image.setImageResource(category.getIcon());
        holder.itemView.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeNavItemHolder extends RecyclerView.ViewHolder{
        TextView label;
        ImageView image;
        public HomeNavItemHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.home_nav_item_label);//获取导航栏项目的标签
            image = itemView.findViewById(R.id.home_nav_item_img);//获取导航栏项目的图片
        }
    }

}
