package com.ebook.app.view.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.FunctionCategory;

import java.util.List;

public class HomeNavAdapter extends RecyclerView.Adapter<HomeNavItemHolder> {
    //HomeNavAdapter - 首页导航栏的适配器
    private LayoutInflater inflater; //布局填充器
    private List<FunctionCategory> list; //导航栏列表

    public HomeNavAdapter(LayoutInflater inflater, List<FunctionCategory> list) {
        this.inflater = inflater;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeNavItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_nav_item, parent, false);//获取导航栏项目的布局
        return new HomeNavItemHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeNavItemHolder holder, int position) {
        //绑定数据
        holder.label.setText(list.get(position).getName());
        holder.image.setImageResource(list.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        //获取项目数量
        return list.size();
    }

    /**
     * 添加数据
     * @param item 数据
     * @param position 位置
     */
    public void addItem(FunctionCategory item,int position){
        list.add(position,item);
        notifyItemInserted(position);
    }

    /**
     * 删除数据
     * @param position 位置
     */
    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setList(List<FunctionCategory> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}

class HomeNavItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
