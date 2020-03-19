package com.haahoo.haahooshop;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.haahoo.haahooshop.utils.SessionManager;
import com.squareup.picasso.Picasso;


public class BanklistAdapter extends RecyclerView.Adapter<BanklistAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<sublistpojo> dataModelArrayList;
    public Context context1 ;
    public String id;
    SessionManager sessionManager;

    public BanklistAdapter(Context ctx, ArrayList<sublistpojo> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public BanklistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.banklist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BanklistAdapter.MyViewHolder holder, final int position) {


        //  holder.statuss.setText(dataModelArrayList.get(position).getStatus());


        holder.accno.setText(dataModelArrayList.get(position).getPdtname());
        holder.ifsc.setText(dataModelArrayList.get(position).getLocation());
        holder.accname.setText(dataModelArrayList.get(position).getId());


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView iv;
        TextView accno,ifsc,accname;

        public MyViewHolder(View itemView) {
            super(itemView);


            iv = itemView.findViewById(R.id.profile_image);

            // cardView=itemView.findViewById(R.id.card);


            accno=itemView.findViewById(R.id.statuse);
            ifsc=itemView.findViewById(R.id.namee);
            accname=itemView.findViewById(R.id.areaa);

//
        }

    }




}