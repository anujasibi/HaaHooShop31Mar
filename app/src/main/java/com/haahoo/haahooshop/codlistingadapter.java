package com.haahoo.haahooshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.haahoo.haahooshop.utils.SessionManager;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;


public class codlistingadapter  extends RecyclerView.Adapter<codlistingadapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<codlistitem> dataModelArrayList;
    public Context context1 ;

    SessionManager sessionManager;

    public codlistingadapter(Context ctx, ArrayList<codlistitem> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public codlistingadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.codlistingdet, parent, false);
        codlistingadapter.MyViewHolder holder = new codlistingadapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final codlistingadapter.MyViewHolder holder, final int position) {


        //Picasso.with(context1).load(dataModelArrayList.get(position).getImage()).into(holder.iv);
        Picasso.get().load(dataModelArrayList.get(position).getImage()).into(holder.iv);

        holder.name.setText(dataModelArrayList.get(position).getPdtname());
        holder.amount.setText(dataModelArrayList.get(position).getAmount());
        holder.id.setText(dataModelArrayList.get(position).getOrderid());
        holder.type.setText(dataModelArrayList.get(position).getType());


    }
    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,amount,type,id;
        ImageView iv;
        CardView cardView;

        TextView status,statuss;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.title);

            iv = itemView.findViewById(R.id.profile_image);
            id=itemView.findViewById(R.id.orderid);
            amount=itemView.findViewById(R.id.price);
            type=itemView.findViewById(R.id.type);

            cardView=itemView.findViewById(R.id.card);


        }

    }



}
