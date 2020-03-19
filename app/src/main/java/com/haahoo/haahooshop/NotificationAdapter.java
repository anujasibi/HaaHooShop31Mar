package com.haahoo.haahooshop;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.haahoo.haahooshop.utils.SessionManager;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<NotificationPojo> dataModelArrayList;
    public Context context1 ;
    public String id;
    SessionManager sessionManager;

    public NotificationAdapter(Context ctx, ArrayList<NotificationPojo> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.notificationrow, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.MyViewHolder holder, final int position) {


        //  holder.statuss.setText(dataModelArrayList.get(position).getStatus());



    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView notific,noi;


        public MyViewHolder(View itemView) {
            super(itemView);

            notific = (TextView) itemView.findViewById(R.id.title);
            noi=(TextView) itemView.findViewById(R.id.titles);

//
        }

    }




}