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


public class SublistAdapter extends RecyclerView.Adapter<SublistAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<sublistpojo> dataModelArrayList;
    public Context context1 ;
    public String id;
    SessionManager sessionManager;

    public SublistAdapter(Context ctx, ArrayList<sublistpojo> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public SublistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.sublist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final SublistAdapter.MyViewHolder holder, final int position) {


      //  holder.statuss.setText(dataModelArrayList.get(position).getStatus());

        holder.name.setText(dataModelArrayList.get(position).getPdtname());
        holder.statuss.setText(dataModelArrayList.get(position).getMode());
        holder.uname.setText(dataModelArrayList.get(position).getUname());
        holder.area.setText(dataModelArrayList.get(position).getLocation());
        holder.amount.setText(dataModelArrayList.get(position).getAmount());
        holder.date.setText(dataModelArrayList.get(position).getOrderdate());
        Picasso.get().load(dataModelArrayList.get(position).getImage()).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView iv;
        TextView status,statuss,mode,uname,area,amount,date;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.title);

            iv = itemView.findViewById(R.id.profile_image);

           // cardView=itemView.findViewById(R.id.card);

            status=itemView.findViewById(R.id.status);
            statuss=itemView.findViewById(R.id.statuse);
            uname=itemView.findViewById(R.id.namee);
            area=itemView.findViewById(R.id.areaa);
            amount=itemView.findViewById(R.id.amountt);
            date=itemView.findViewById(R.id.datee);
//
        }

    }




}