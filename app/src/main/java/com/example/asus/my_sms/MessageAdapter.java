package com.example.asus.my_sms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 9/30/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<String> datalistmes = new ArrayList<>();




    public MessageAdapter(List<String> datalistmes){
        this.datalistmes = datalistmes;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String str = datalistmes.get(position);
        holder.mBody.setText(str);


    }

    @Override
    public int getItemCount() {
        return datalistmes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mBody;



        public MyViewHolder(View view) {
            super(view);
            mBody =  (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}