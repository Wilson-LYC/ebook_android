package com.ebook.app.view.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.FunctionCategory;

import java.util.List;

public class HomeNavAdapter extends RecyclerView.Adapter<HomeNavAdapter.HomeNavItemHolder> {
    private List<FunctionCategory> list;

    public HomeNavAdapter(List<FunctionCategory> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HomeNavItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_nav, parent, false);//获取导航栏项目的布局
        return new HomeNavItemHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeNavItemHolder holder, int position) {
        FunctionCategory functionCategory=list.get(position);
        holder.label.setText(functionCategory.getName());
        holder.image.setImageResource(functionCategory.getIcon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeNavItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView label;
        ImageView image;
        public HomeNavItemHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.home_nav_item_label);//获取导航栏项目的标签
            image = itemView.findViewById(R.id.home_nav_item_img);//获取导航栏项目的图片
            itemView.setOnClickListener(this);//设置点击事件
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.i("HomeNavItemHolder","第"+position+"个被点击");
        }
    }

}
