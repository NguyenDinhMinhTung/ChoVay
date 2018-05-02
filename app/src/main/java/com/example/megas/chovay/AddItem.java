package com.example.megas.chovay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MainItem> mainList;
    ArrayList<GroupItem> groupList;

    //Spinner spinListName;
    Button btnSave;
    //ArrayAdapter<MainItem> adapter;
    EditText edtMoney, edtNote;
    AutoCompleteTextView edtName, edtGroup;
    GroupDBHelper groupDBHelper;
    MainDBHelper mainDBHelper;
    MoneyDBHelper moneyDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //spinListName = findViewById(R.id.spinListName);
        btnSave = findViewById(R.id.btnSave);
        edtMoney = findViewById(R.id.edtMoney);
        edtName = findViewById(R.id.edtName);
        edtNote = findViewById(R.id.edtNote);
        edtGroup = findViewById(R.id.edtGroup);

        btnSave.setOnClickListener(this);

        moneyDBHelper = new MoneyDBHelper(this);
        mainDBHelper = new MainDBHelper(this);
        groupDBHelper = new GroupDBHelper(this);
        groupList = groupDBHelper.getData();

        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtGroup.setText(groupDBHelper.findGroupNameByID(mainDBHelper.findGroupIDByName(edtName.getText().toString())));
                }
            }
        });

        Intent intent = getIntent();
        mainList = (ArrayList<MainItem>) intent.getSerializableExtra("mainList");

        ArrayAdapter<String> adapterName = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, getListName(mainList));
        edtName.setThreshold(0);
        edtName.setAdapter(adapterName);

        ArrayAdapter<String> adapterGroup = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getListGroup(groupList));
        edtGroup.setThreshold(0);
        edtGroup.setAdapter(adapterGroup);
        
        /*adapter = new ArrayAdapter<MainItem>(this, android.R.layout.simple_spinner_item, mainList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_spinner_item, parent, false);
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(mainList.get(position).getName());
                return convertView;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView = getLayoutInflater().inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(mainList.get(position).getName());
                return convertView;
            }
        };*/

        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //spinListName.setAdapter(adapter);
    }

    public ArrayList<String> getListName(ArrayList<MainItem> mainList) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < mainList.size(); i++) {
            list.add(mainList.get(i).getName());
        }

        return list;
    }

    public ArrayList<String> getListGroup(ArrayList<GroupItem> groupItems) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < groupItems.size(); i++) {
            list.add(groupItems.get(i).getGroupName());
        }

        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                if ((edtName.getText().toString().trim().length() == 0 && edtGroup.getText().toString().trim().length() == 0)
                        || edtMoney.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "未入力項目があります！", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtName.getText().toString().trim().length() == 0) {
                        long groupID = groupDBHelper.insert(edtGroup.getText().toString());

                        ArrayList<MainItem> mainItemsList = mainDBHelper.getDataByGroupID(groupID);

                        for (int i = 0; i < mainItemsList.size(); i++) {
                            MoneyItem moneyItem = new MoneyItem(mainItemsList.get(i).getId(), moneyDBHelper.getNewLocalID(),
                                    Integer.parseInt(edtMoney.getText().toString()), edtNote.getText().toString(), 1);

                            moneyDBHelper.insert(moneyItem);
                        }
                    } else {
                        long mainID = getID(edtName.getText().toString());
                        long groupID = groupDBHelper.insert(edtGroup.getText().toString());
                        MainItem mainItem;

                        if (mainID < 0) {
                            mainItem = new MainItem(mainDBHelper.getNewID(), edtName.getText().toString(), groupID);
                            mainDBHelper.insert(mainItem);
                        } else {
                            mainItem = new MainItem(mainID, edtName.getText().toString(), groupID);
                            mainDBHelper.delete(mainID);

                        }

                        mainDBHelper.insert(mainItem);
                        mainID = mainItem.getId();
                        MoneyItem item = new MoneyItem(mainID, moneyDBHelper.getNewLocalID(),
                                Integer.parseInt(edtMoney.getText().toString()), edtNote.getText().toString(), 1);
                        moneyDBHelper.insert(item);
                    }

                    finish();
                }
                break;
        }
    }

    public long getID(String name) {
        for (int i = 0; i < mainList.size(); i++) {
            if (name.compareTo(mainList.get(i).getName()) == 0) {
                return mainList.get(i).getId();
            }
        }
        return -1;
    }
}
