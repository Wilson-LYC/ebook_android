package com.ebook.app.view.comment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{
    private List<Comment> list;

    public CommentAdapter(List<Comment> list){
        if (list==null){
            list=new ArrayList<>();
        }
        this.list = list;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = list.get(position);
        holder.tvName.setText(comment.getSender().getName());
        holder.tvContent.setText(comment.getContent());
        holder.tvTime.setText(comment.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvContent,tvTime;
        public CommentHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.item_comment_name);
            tvContent = itemView.findViewById(R.id.item_comment_content);
            tvTime = itemView.findViewById(R.id.item_comment_time);
        }
    }

    public void setList(List<Comment> list){
        Log.d("CommentAdapter", "setList: "+list.size());
        this.list = list;
        notifyDataSetChanged();
    }
}
