package com.example.megas.chovay;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    MainAdapter adapter;
    ArrayList<MainItem> list;
    FloatingActionButton fab;
    MainDBHelper mainDatabase;
    MoneyDBHelper moneyDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mainRecyclerView);
        fab = findViewById(R.id.floatingActionButton);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        fab.setOnClickListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                /*View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && e.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(MainActivity.this, MoneyItemList.class);

                    ArrayList<MoneyItem> moneyList = moneyDatabase.getData(list.get(rv.getChildPosition(childView)).getId());
                    intent.putExtra("list", moneyList);
                    startActivity(intent);
                }*/
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mainDatabase = new MainDBHelper(this);
        moneyDatabase = new MoneyDBHelper(this);
        list = mainDatabase.getData();

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setMoney(moneyDatabase.getMoney(list.get(i).getId()));
        }
        adapter = new MainAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    public void refreshList() {
        list = mainDatabase.getData();

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setMoney(moneyDatabase.getMoney(list.get(i).getId()));
        }
        adapter = new MainAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                Intent intent = new Intent(this, AddItem.class);
                intent.putExtra("list", list);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1) {
            MoneyItem item = (MoneyItem) data.getSerializableExtra("item");
            if (item.getId() >= 0) {
                moneyDatabase.insert(item);
            } else {
                MainItem mainItem = new MainItem(mainDatabase.getNewID(), data.getStringExtra("name"));
                item.setId(mainItem.getId());
                mainDatabase.insert(mainItem);
                moneyDatabase.insert(item);
            }

            refreshList();
        }
    }
}
