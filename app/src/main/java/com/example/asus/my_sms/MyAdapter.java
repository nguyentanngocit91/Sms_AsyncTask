package com.example.asus.my_sms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    List<sms> datalist = new ArrayList<>();


    MyAdapter(List<sms> data){
        datalist = data;
    }
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyAdapter.MyViewHolder holder, int position) {
        sms  T = datalist.get(datalist.size()-position-1);
        holder.mFrom.setText(String.valueOf(T.getName()));
        holder.mBody.setText(String.valueOf(T.getInfo()));



        holder.relative.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(holder.mFrom.getText().toString());
                }
            }
        });
    }

    public interface OnItemClickedListener {
        void onItemClick(String phone);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mFrom,mBody;
        RelativeLayout relative;

        public MyViewHolder(View view) {
            super(view);
            mFrom = (TextView) itemView.findViewById(R.id.txt_from);
            mBody = (TextView) itemView.findViewById(R.id.txt_body);
            relative=(RelativeLayout) itemView.findViewById(R.id.relative);
        }
    }
}
