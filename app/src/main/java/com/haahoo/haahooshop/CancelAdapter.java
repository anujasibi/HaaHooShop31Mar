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


public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<CancelPojo> dataModelArrayList;
    public Context context1 ;
    public String id;
    SessionManager sessionManager;

    public CancelAdapter(Context ctx, ArrayList<CancelPojo> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public CancelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cancellist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CancelAdapter.MyViewHolder holder, final int position) {


        //  holder.statuss.setText(dataModelArrayList.get(position).getStatus());

        holder.name.setText(dataModelArrayList.get(position).getPdtname());
        holder.phone.setText(dataModelArrayList.get(position).getPhone());
        holder.uname.setText(dataModelArrayList.get(position).getUname());
        holder.date.setText(dataModelArrayList.get(position).getOrderdate());
        holder.reason.setText(dataModelArrayList.get(position).getReason());

        Picasso.get().load(dataModelArrayList.get(position).getImage()).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView iv;
        TextView status,uname,phone,date,reason;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.title);

            iv = itemView.findViewById(R.id.profile_image);

            // cardView=itemView.findViewById(R.id.card);


            uname=itemView.findViewById(R.id.statuse);
            phone=itemView.findViewById(R.id.namee);
            date=itemView.findViewById(R.id.areaa);
            reason=itemView.findViewById(R.id.amountt);

//
        }

    }




}