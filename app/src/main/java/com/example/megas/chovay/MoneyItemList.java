package com.example.megas.chovay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MoneyItemList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MoneyItem> list;
    MoneyAdapter adapter;
    Button btnDelete;
    MoneyDBHelper database;
    long mainID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_item_list);

        recyclerView = findViewById(R.id.recyclerViewMoneyItemList);
        btnDelete = findViewById(R.id.btnDelete);

        database = new MoneyDBHelper(this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked()) {
                        database.deleteByLocalId(list.get(i).getLocalID());
                    }
                }

                refreshList();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        mainID = intent.getLongExtra("mainID", 0);
        list = database.getData(mainID);

        adapter = new MoneyAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    public void refreshList() {
        list = database.getData(mainID);

        adapter = new MoneyAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
