package com.haahoo.haahooshop;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;


public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<upcomingrow> dataModelArrayList;
    public Context context1 ;
    public String status="0";
    public String stat="0";
    public String id;
    private String URLline = Global.BASE_URL+"virtual_order_management/shop_order_accpt/";
    SessionManager sessionManager;

    public EarningAdapter(Context ctx, ArrayList<upcomingrow> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public EarningAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.earningslist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final EarningAdapter.MyViewHolder holder, final int position) {



        id=dataModelArrayList.get(position).getId();
        holder.price.setText(dataModelArrayList.get(position).getPrice());
        // Picasso.with(context1).load(dataModelArrayList.get(position).getImage()).into(holder.iv);
        Picasso.get().load(dataModelArrayList.get(position).getImage()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.cus.setText(dataModelArrayList.get(position).getCusname());
        holder.amount.setText(dataModelArrayList.get(position).getStatus());


        //   Picasso.with(context1).load(dataModelArrayList.get(position).getQty()).into(holder.iv);
       /* holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1,Orderdetails.class);
                intent.putExtra("order_id",dataModelArrayList.get(position).getProductid());
                ApiClient.sh_order_id = dataModelArrayList.get(position).getShid();
                intent.putExtra("virtual",ApiClient.virtual_order.get(position));
                context1.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, cus;
        // LinearLayout linearLayout;
        ImageView iv;
        CardView cardView;
        SlideToActView sta,sta1;
        TextView price,amount;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.pdt);
            cus = (TextView) itemView.findViewById(R.id.cname);
            iv = itemView.findViewById(R.id.profile_image);

            cardView=itemView.findViewById(R.id.card);

            price=itemView.findViewById(R.id.pdtprice);
            amount=itemView.findViewById(R.id.amountp);
//            qty = (TextView) itemView.findViewById(R.id.non);
        }

    }




}