package com.example.megas.chovay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by megas on 2018/04/28.
 */

public class MoneyAdapter extends RecyclerView.Adapter<MoneyViewHolder> {
    private ArrayList<MoneyItem> list;

    public MoneyAdapter(ArrayList<MoneyItem> list) {
        this.list = list;
    }

    @Override
    public MoneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_item, parent, false);

        return new MoneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoneyViewHolder holder, final int position) {
        MoneyItem item = list.get(position);
        if (item.getState() == 1) {
            holder.txtNote.setText(item.getNote());
            holder.txtMoney.setText(String.valueOf(item.getMoney()) + "円");

            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).setChecked(b);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MoneyViewHolder extends RecyclerView.ViewHolder {
    TextView txtNote, txtMoney;
    CheckBox checkbox;

    public MoneyViewHolder(View view) {
        super(view);
        txtNote = view.findViewById(R.id.txtNote);
        txtMoney = view.findViewById(R.id.txtMoney);
        checkbox = view.findViewById(R.id.moneyCheckbox);
    }

}
