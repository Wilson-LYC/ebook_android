package com.ebook.app.view.ai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
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
    public void bind(Message message,AiMessageHolder holder) {
        if (message.isLoading()) {
            holder.imgLoading.setVisibility(View.VISIBLE);
            // 为ImageView设置动画效果
            RotateAnimation rotateAnim = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnim.setDuration(2000); // 动画持续时间
            rotateAnim.setRepeatCount(Animation.INFINITE); // 无限重复
            rotateAnim.setInterpolator(new LinearInterpolator()); // 线性插值器，保持恒定速度旋转
            rotateAnim.setFillAfter(true); // 动画执行完毕后保持最后的状态
            holder.imgLoading.startAnimation(rotateAnim);
            holder.tvContent.setText(message.getContent());
        } else {
            holder.imgLoading.setVisibility(View.GONE);
            holder.tvContent.setVisibility(View.VISIBLE);
            Markwon markwon = Markwon.create(holder.itemView.getContext());
            markwon.setMarkdown(holder.tvContent, message.getContent());
        }
    }
    @Override
    public void onBindViewHolder(@NonNull AiMessageHolder holder, int position) {
        Message message=list.get(position);
        holder.tvSender.setText(message.getSender());
        Markwon markwon = Markwon.create(holder.itemView.getContext());
        markwon.setMarkdown(holder.tvContent, message.getContent());
        bind(message,holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AiMessageHolder extends RecyclerView.ViewHolder {
        TextView tvSender,tvContent;
        ImageView imgLoading;
        public AiMessageHolder(@NonNull View itemView) {
            super(itemView);
            tvSender=itemView.findViewById(R.id.item_message_tv_sender);
            tvContent=itemView.findViewById(R.id.item_message_tv_content);
            imgLoading=itemView.findViewById(R.id.item_messgae_img_load);
        }
    }

    public void addItem(Message message){
        list.add(message);
        notifyItemInserted(list.size()-1);
    }

    public void updateAiMessage(Message message){
        list.set(list.size()-1,message);
        notifyItemChanged(list.size()-1);
    }
}
