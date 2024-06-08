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

public class AiMessageAdapter extends RecyclerView.Adapter<AiMessageAdapter.AiMessageHolder> {
    private List<Message> list;

    public AiMessageAdapter() {
        Log.d("AiMessageAdapter","构造");
        list=new ArrayList<>();
    }

    @NonNull
    @Override
    public AiMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("AiMessageAdapter","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new AiMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AiMessageHolder holder, int position) {
        Message message=list.get(position);
        holder.tvSender.setText(message.getSender());
        holder.tvContent.setText(message.getContent());
    }

    public AiMessageAdapter(List<Message> list) {
        this.list = list;
    }

    public void setList(List<Message> messages){
        System.out.println("addItems");
        this.list=messages;
        notifyDataSetChanged();
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



}
