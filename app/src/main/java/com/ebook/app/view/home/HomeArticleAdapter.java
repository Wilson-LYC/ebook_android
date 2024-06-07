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
import com.ebook.app.model.Article;

import java.util.ArrayList;
import java.util.List;

public class HomeArticleAdapter extends RecyclerView.Adapter<HomeArticleAdapter.HomeArticleHolder> {
    final static String TAG="HomeArticleAdapter";

    private List<Article> list;

    public HomeArticleAdapter() {
        list=new ArrayList<>();
    }

    public HomeArticleAdapter(List<Article> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HomeArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new HomeArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeArticleHolder holder, int position) {
        Article article=list.get(position);
        holder.title.setText(article.getTitle());
        holder.briefContent.setText(article.getContent().length()>50?article.getContent().substring(0,50)+"...":article.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Article> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public class HomeArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
}