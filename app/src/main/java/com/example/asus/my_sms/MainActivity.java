package com.example.asus.my_sms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NetworkChangeReceiver receiver;
    private List<sms> smslist = new ArrayList<>();
    RecyclerView mRecyclerView;
    MyAdapter mRcvAdapter;

    MyDatabaseHelper db = new MyDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRcvAdapter = new MyAdapter(smslist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);
        db.insert();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 2);
        }else {
            receiver = new NetworkChangeReceiver();
            final IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver(receiver, filter);
        }




        mRcvAdapter.setOnItemClickedListener(new MyAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(String phone) {

                SharedPreferences pre = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("Phone", phone);
                editor.commit();
                Intent inms = new Intent(MainActivity.this, SMSActivity.class);
                startActivity(inms);
            }
        });


        Cursor list = db.readMessage();
        if (list != null && list.moveToFirst()) {
            do {
                String phone = list.getString(list.getColumnIndex("phone"));
                String message = list.getString(list.getColumnIndex("message"));
                add_data(phone, message);

            } while (list.moveToNext());
        }





    }


    protected  void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        String n = intent.getStringExtra("number");
        String b = intent.getStringExtra("body");

        new Thread(new InsertRunable(n,b)).start();
        new Thread(new ShowRunable(n)).start();
        new Thread(new AddRunable(n)).start();

        String name = db.getContact(n);

        for(int i=0; i<smslist.size(); i++)
        {
            if(smslist.get(i).getName().equals(name)) {
                smslist.remove(i);
            }
        }
        db.insert_message(n,b);
        add_data(name,b);

    }

    public  class AddRunable implements  Runnable{

       public String num;
        public  String mess;

        public AddRunable(String n)
        {
            num=n;

        }

        @Override

        public void run()
        {
            Log.i("CTV","Add thanh cong");
            db.readPhoneToName(num);
        }

    }

    public class  ShowRunable implements Runnable{

        public String num;

        public  ShowRunable(String a)
        {
            num=a;
        }

        @Override

        public void run()
        {
            Log.i("ACT","SHOW SMS");
            db.getContact(num);
        }

    }



    public class InsertRunable implements Runnable{
        public String number;
        public String messager;
        public InsertRunable(String a,String b){
            number = a;
            messager = b;
        }
        @Override
        public void run() {
            Log.i("ACT","Đã chạy");
            db.insert_message(number,messager);
        }


    }





    

    public void add_data(String number,String body) {
        sms sms = new sms( number,body );
        smslist.add(sms);
        mRcvAdapter.notifyDataSetChanged();

    }





}
