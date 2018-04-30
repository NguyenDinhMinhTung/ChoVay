package com.example.megas.chovay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MoneyItemList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MoneyItem> list;
    MoneyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_item_list);

        recyclerView=findViewById(R.id.recyclerViewMoneyItemList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent=getIntent();
        list= (ArrayList<MoneyItem>) intent.getSerializableExtra("list");

        adapter=new MoneyAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
