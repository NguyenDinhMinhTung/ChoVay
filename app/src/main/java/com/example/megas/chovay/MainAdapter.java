package com.example.megas.chovay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by megas on 2018/04/28.
 */

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    private ArrayList<MainItem> list;
    OnClickListener onClickListener;

    public MainAdapter(ArrayList<MainItem> list, OnClickListener onClickListener) {
        this.list = list;
        this.onClickListener=onClickListener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        MainItem item=list.get(position);
        holder.txtName.setText(item.getName());
        holder.txtMoney.setText(String.valueOf(item.getMoney()));

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(position);
            }
        });

        holder.txtMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClickListener{
        void OnClick(int position);
    }

}

class MainViewHolder extends RecyclerView.ViewHolder{
    TextView txtName, txtMoney;

    public MainViewHolder(View view) {
        super(view);
        txtName = view.findViewById(R.id.txtName);
        txtMoney = view.findViewById(R.id.txtMoney);
    }
}
