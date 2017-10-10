package com.example.asus.my_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;



public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Random rand = new Random();
        //int  n = rand.nextInt(50) + 1;
        //Toast.makeText(context, "Hello .. có tin nhắn tới đó"+n, Toast.LENGTH_LONG).show();
        //pdus để lấy gói tin nhắn
        String sms_extra="pdus";
        Bundle bundle=intent.getExtras();
        String sms="";
        String address="";
        String body="";
        //bundle trả về tập các tin nhắn gửi về cùng lúc
        Object []objArr= (Object[]) bundle.get(sms_extra);
        //duyệt vòng lặp để đọc từng tin nhắn
        for(int i=0;i<objArr.length;i++)
        {
            //lệnh chuyển đổi về tin nhắn createFromPdu
            SmsMessage smsMsg=SmsMessage.
                    createFromPdu((byte[]) objArr[i]);
            //lấy nội dung tin nhắn
            body=smsMsg.getMessageBody();
            //lấy số điện thoại tin nhắn
            address=smsMsg.getDisplayOriginatingAddress();
            sms+=address+":\n"+body+"\n";
        }
        //hiển thị lên giao diện
        //Toast.makeText(context, sms, Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("number", address);
        i.putExtra("body", body);

        context.startActivity(i);
    }





}
