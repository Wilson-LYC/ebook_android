package com.ebook.app.view.ai;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.Message;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;

public class AiMessageAdapter extends RecyclerView.Adapter<AiMessageAdapter.AiMessageHolder> {
    private static List<Message> list;

    public AiMessageAdapter() {
        if (list==null) {
            Message message=new Message("AI","你好！欢迎使用EBook！目前我只支持单轮对话～");
            list=new ArrayList<>();
            list.add(message);
        }
    }

    @NonNull
    @Override
    public AiMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new AiMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AiMessageHolder holder, int position) {
        Message message=list.get(position);
        holder.tvSender.setText(message.getSender());
        Markwon markwon = Markwon.create(holder.itemView.getContext());
        markwon.setMarkdown(holder.tvContent, message.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AiMessageHolder extends RecyclerView.ViewHolder {
        TextView tvSender,tvContent;
        public AiMessageHolder(@NonNull View itemView) {
            super(itemView);
            tvSender=itemView.findViewById(R.id.item_message_sender);
            tvContent=itemView.findViewById(R.id.item_message_content);
        }
    }

    public void addItem(Message message){
        list.add(message);
        notifyDataSetChanged();
    }

}
