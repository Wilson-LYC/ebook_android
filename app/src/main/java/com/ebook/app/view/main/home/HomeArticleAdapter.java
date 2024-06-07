package com.ebook.app.view.main.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.Article;

import java.util.ArrayList;
import java.util.List;

public class HomeArticleAdapter extends RecyclerView.Adapter<HomeArticleHolder> {
    final static String TAG="HomeArticleAdapter";

    private LayoutInflater inflater;
    private List<Article> list=new ArrayList<>();

    public HomeArticleAdapter(LayoutInflater inflater) {
        Log.i(TAG, "创建");
        this.inflater = inflater;
    }

    public HomeArticleAdapter(LayoutInflater inflater, List<Article> list) {
        this.inflater = inflater;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_article, parent, false);
        return new HomeArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeArticleHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        String content=list.get(position).getContent();
        content=content.length()>50?content.substring(0,50)+"...":content;
        holder.briefContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Article item){
        list.add(item);
        notifyItemInserted(list.size()-1);
    }

    public void setList(List<Article> list){
        this.list=list;
        notifyDataSetChanged();
    }
}

class HomeArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title,briefContent;
    ImageView image;

    public HomeArticleHolder(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.article_item_title);
        briefContent=itemView.findViewById(R.id.article_item_brief_content);
        image=itemView.findViewById(R.id.article_item_img);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position=getAdapterPosition();
        Log.i("HomeArticleAdapter","onClick: "+position);
    }
}