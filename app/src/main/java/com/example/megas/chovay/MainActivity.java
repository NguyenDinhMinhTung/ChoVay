package com.example.megas.chovay;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    MainAdapter adapter;
    ArrayList<MainItem> list;
    FloatingActionButton fab;
    MainDBHelper mainDatabase;
    MoneyDBHelper moneyDatabase;
    Button btnDelete;
    TextView txtSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mainRecyclerView);
        fab = findViewById(R.id.floatingActionButton);
        btnDelete = findViewById(R.id.btnDelete);
        txtSum = findViewById(R.id.txtSum);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        fab.setOnClickListener(this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked()) {
                        mainDatabase.delete(list.get(i).getId());
                        moneyDatabase.deleteByMainId(list.get(i).getId());
                    }
                }

                refreshList();
            }
        });

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


        refreshList();
    }

    public void setSum() {
        long sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getMoney();
        }

        txtSum.setText(String.valueOf(sum) + "円");
    }

    public void refreshList() {
        list = mainDatabase.getData();

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setMoney(moneyDatabase.getMoney(list.get(i).getId()));
            if (list.get(i).getMoney() == 0) {
                mainDatabase.delete(list.get(i).getId());
                list.remove(i);
                i--;
            }
        }

        setSum();

        adapter = new MainAdapter(list, new MainAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {
                Intent intent = new Intent(MainActivity.this, MoneyItemList.class);

                intent.putExtra("mainID", list.get(position).getId());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
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

            if (item.getMainID() < 0) {
                MainItem mainItem = new MainItem(mainDatabase.getNewID(), data.getStringExtra("name"),0);
                item.setMainID(mainItem.getId());
                mainDatabase.insert(mainItem);
            }

            item.setLocalID(moneyDatabase.getNewLocalID());
            moneyDatabase.insert(item);

            refreshList();
        }
    }
}
