package com.ebook.app.view.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.MessageAI;

import java.util.List;

public class MessageAIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageAI> mMessageList;



    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView sentMsg;

        public SentViewHolder(View view) {
            super(view);
            sentMsg = (TextView) view.findViewById(R.id.ai_user_message);
        }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView receivedMsg;

        public ReceivedViewHolder(View view) {
            super(view);
            receivedMsg = (TextView) view.findViewById(R.id.ai_user_message);
        }
    }



    //获取是 *用户的发送数据* 还是 *AI的返回数据*
    public MessageAIAdapter(List<MessageAI> messageList) {
        mMessageList = messageList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MessageAI.TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message_item, parent, false);
            return new SentViewHolder(view);
        } else if (viewType == MessageAI.TYPE_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_message_item, parent, false);
            return new ReceivedViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageAI message = mMessageList.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).sentMsg.setText(message.getContent());
        } else if (holder instanceof ReceivedViewHolder) {
            ((ReceivedViewHolder) holder).receivedMsg.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
