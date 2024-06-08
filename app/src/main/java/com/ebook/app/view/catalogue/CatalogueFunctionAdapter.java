package com.ebook.app.view.catalogue;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.app.R;
import com.ebook.app.model.Function;

import java.util.ArrayList;
import java.util.List;

public class CatalogueFunctionAdapter extends RecyclerView.Adapter<CatalogueFunctionAdapter.CatalogueFunctionHolder> {

    private List<Function> list;

    public CatalogueFunctionAdapter(List<Function> list) {
        if (list==null)
            list=new ArrayList<>();
        this.list = list;
    }

    @NonNull
    @Override
    public CatalogueFunctionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogue_function, parent, false);
        return new CatalogueFunctionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueFunctionHolder holder, int position) {
        Function function=list.get(position);
        holder.label.setText(function.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Function> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class CatalogueFunctionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label;
        public CatalogueFunctionHolder(@NonNull View itemView) {
            super(itemView);
            label=itemView.findViewById(R.id.catalogue_function_item_label);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Log.i("CatalogueFunctionAdapter","onClick: "+position);
        }

    }
}
