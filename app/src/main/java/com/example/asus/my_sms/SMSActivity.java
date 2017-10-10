package com.example.asus.my_sms;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 9/30/2017.
 */

public class SMSActivity extends AppCompatActivity {

    MyDatabaseHelper db;

    private List<String> listdata = new ArrayList<>();
    RecyclerView mRecyclerView;
    MessageAdapter MessAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        db = new MyDatabaseHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_all);
        MessAdapter = new MessageAdapter(listdata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(MessAdapter);

        TextView phone_txt = (TextView) findViewById(R.id.txtPhone);
        TextView name_txt = (TextView) findViewById(R.id.txtName);



        SharedPreferences pre = getSharedPreferences("MyData",MODE_PRIVATE);
        String phone = pre.getString("Phone","");
        String name = db.readPhoneToName(phone);

        if(name.equals(phone)) {
            name_txt.setText("Name: " + name);
        }else {
            name_txt.setText("Name: " + phone);
            phone_txt.setText("Phone: " + name);
        }




       // runOnUiThread();

        Cursor list = db.getMessage(name);
        if (list != null && list.moveToFirst()) {
            do {
                String m = list.getString(list.getColumnIndex("message"));
                listdata.add(m);
            } while (list.moveToNext());
        }
    }


}
