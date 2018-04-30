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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //spinListName = findViewById(R.id.spinListName);
        btnSave = findViewById(R.id.btnSave);
        edtMoney = findViewById(R.id.edtMoney);
        edtName = findViewById(R.id.edtName);
        edtNote = findViewById(R.id.edtNote);
        edtGroup=findViewById(R.id.edtGroup);

        btnSave.setOnClickListener(this);

        groupDBHelper=new GroupDBHelper(this);
        groupList=groupDBHelper.getData();

        Intent intent = getIntent();
        mainList = (ArrayList<MainItem>) intent.getSerializableExtra("mainList");

        ArrayAdapter<String> adapterName = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getListName(mainList));
        edtName.setThreshold(1);
        edtName.setAdapter(adapterName);

        ArrayAdapter<String> adapterGroup = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getListGroup(groupList));
        edtGroup.setThreshold(1);
        edtName.setAdapter(adapterGroup);
        
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
                if (edtName.getText().toString().trim().length() == 0 || edtMoney.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "未入力項目があります！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    //MoneyItem item = new MoneyItem(mainList.get(spinListName.getSelectedItemPosition()).getId(), Integer.parseInt(edtMoney.getText().toString()));
                    MoneyItem item = new MoneyItem(getID(edtName.getText().toString()), 0, Integer.parseInt(edtMoney.getText().toString()), edtNote.getText().toString());
                    intent.putExtra("item", item);
                    intent.putExtra("name", edtName.getText().toString());
                    setResult(1, intent);

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
