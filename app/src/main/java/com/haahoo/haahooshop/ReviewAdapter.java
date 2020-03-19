package com.haahoo.haahooshop;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<reviewpojo> dataModelArrayList;
    public Context context1 ;
    public String status="0";
    public String stat="0";
    public String id;
    private String URLline = Global.BASE_URL+"virtual_order_management/shop_order_accpt/";
    SessionManager sessionManager;

    public ReviewAdapter(Context ctx, ArrayList<reviewpojo> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.review_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder holder, final int position) {



        id=dataModelArrayList.get(position).getId();
        holder.uname.setText(dataModelArrayList.get(position).getUsername());
        holder.pdtname.setText(dataModelArrayList.get(position).getPdtname());
        holder.date.setText(dataModelArrayList.get(position).getDate());
        holder.review.setText(dataModelArrayList.get(position).getReview());
        holder.pdtrate.setRating(Float.parseFloat(dataModelArrayList.get(position).getPdtrating()));
        holder.shoprat.setRating(Float.parseFloat(dataModelArrayList.get(position).getShprating()));


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

        TextView uname,pdtname,date,review;
        // LinearLayout linearLayout;
        ImageView iv;
        CardView cardView;
        RatingBar shoprat,pdtrate;


        public MyViewHolder(View itemView) {
            super(itemView);

             uname = (TextView) itemView.findViewById(R.id.cusname);
             pdtname= (TextView) itemView.findViewById(R.id.pdt);
            date = itemView.findViewById(R.id.date);

            review=itemView.findViewById(R.id.review);
            shoprat=itemView.findViewById(R.id.ratingBar);
            pdtrate=itemView.findViewById(R.id.ratingBar1);





        }

    }




}