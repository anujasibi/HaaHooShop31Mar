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


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<upcomingrow> dataModelArrayList;
    public Context context1 ;
    public String status="0";
    public String stat="0";
    public String id;
    private String URLline = Global.BASE_URL+"virtual_order_management/shop_order_accpt/";
    SessionManager sessionManager;

    public HistoryAdapter(Context ctx, ArrayList<upcomingrow> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.history_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.MyViewHolder holder, final int position) {



        id=dataModelArrayList.get(position).getId();
        holder.statuss.setText(dataModelArrayList.get(position).getStatus());
       // Picasso.with(context1).load(dataModelArrayList.get(position).getImage()).into(holder.iv);
        Picasso.get().load(dataModelArrayList.get(position).getImage()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.location.setText(dataModelArrayList.get(position).getLocation());


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

        TextView name, location;
        // LinearLayout linearLayout;
        ImageView iv;
        CardView cardView;
        SlideToActView sta,sta1;
        TextView status,statuss;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.title);
            location = (TextView) itemView.findViewById(R.id.desc);
            iv = itemView.findViewById(R.id.profile_image);

            cardView=itemView.findViewById(R.id.card);

            status=itemView.findViewById(R.id.status);
            statuss=itemView.findViewById(R.id.statuse);
//            qty = (TextView) itemView.findViewById(R.id.non);
        }

    }




}