package com.example.pruebasandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pruebasandroid.R;

/**
 * Created by Jordi on 02/07/2015.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    public String[] names;
    private View.OnClickListener callback;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, descView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView= (TextView) itemView.findViewById(R.id.name);
            descView= (TextView) itemView.findViewById(R.id.desc);
        }
    }

    public MainAdapter(String[] names, View.OnClickListener callback)
    {
        this.names=names;
        this.callback=callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_main,parent,false);
         view.setOnClickListener(callback);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if(position>=0 && position<names.length && names[position]!=null && names[position].split(",").length>1) {
            String str = names[position];
            viewHolder.nameView.setText(str.split(",")[0]);
            viewHolder.descView.setText(str.split(",")[1]);
        }
    }

    @Override
    public int getItemCount() {
        return names.length;
    }


}
