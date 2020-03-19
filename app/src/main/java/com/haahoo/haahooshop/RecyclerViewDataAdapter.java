package com.haahoo.haahooshop;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;


/**
 * Created by Jerry on 3/17/2018.
 */

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewItemHolder> {
    private Context context ;

    private List<CardRecyclerViewItem> carItemList;


    public RecyclerViewDataAdapter(List<CardRecyclerViewItem> carItemList) {
        this.carItemList = carItemList;

    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        context=parent.getContext();
        // Inflate the RecyclerView item layout xml.
        View carItemView = layoutInflater.inflate(R.layout.activity_card_view_item, parent, false);

        // Get car title text view object.
        final TextView carTitleView = (TextView)carItemView.findViewById(R.id.card_view_image_title);
        // Get car image view object.
        final ImageView carImageView = (ImageView)carItemView.findViewById(R.id.card_view_image);

        final CardView cardView=(CardView) carItemView.findViewById(R.id.card);
        // When click the image.
        carImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String carTitle = carTitleView.getText().toString();
                // Create a snackbar and show it.
                Snackbar snackbar = Snackbar.make(carImageView, "You click " + carTitle +" image", Snackbar.LENGTH_LONG);
                snackbar.show();

                if (carTitle.equals("Add Products")){
                    Intent intent=new Intent(context,addproductnew.class);
                    context.startActivity(intent);
                }

                if (carTitle.equals("View/Modify Products")){
                    Intent intent=new Intent(context,viewproduct.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View/Modify Profile")){
                    Intent intent=new Intent(context,profile.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Add Your Shop Branches")){
                    Intent intent=new Intent(context,AddShopBranch.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Add Your Employee")){
                    Intent intent=new Intent(context,AddEmployee.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("My Branches")){
                    Intent intent=new Intent(context,ViewBranches.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Employee Details")){
                    Intent intent=new Intent(context,viewemployee.class);
                    context.startActivity(intent);
                }

                if (carTitle.equals("View Branch Products")){
                    Intent intent=new Intent(context,viewbran.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Upcoming Orders")){
                    Intent intent=new Intent(context,Uporder.class);
                    context.startActivity(intent);
                }

                if (carTitle.equals("View Order History")){
                    Intent intent=new Intent(context,orderhistory.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Subscription List")){
                    Intent intent=new Intent(context,Subscriptionlist.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Cancelled Subscription List")){
                    Intent intent=new Intent(context,cancelsub.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Earnings")){
                    Intent intent=new Intent(context,Earnings.class);
                    context.startActivity(intent);
                }




                /*if (carTitle.equals("Manual Orders List")){
                    Intent intent=new Intent(context,orderlist.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Earnings")){
                    Intent intent=new Intent(context,Earnings.class);
                    context.startActivity(intent);
                }*/
                // Get car title text.

            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carTitle = carTitleView.getText().toString();
                // Create a snackbar and show it.
                Snackbar snackbar = Snackbar.make(carImageView, "You click " + carTitle +" image", Snackbar.LENGTH_LONG);
                snackbar.show();

                if (carTitle.equals("Add Products")){
                    Intent intent=new Intent(context,addproductnew.class);
                    context.startActivity(intent);
                }

                if (carTitle.equals("View/Modify Products")){
                    Intent intent=new Intent(context,viewproduct.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View/Modify Profile")){
                    Intent intent=new Intent(context,profile.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Add Your Shop Branches")){
                    Intent intent=new Intent(context,AddShopBranch.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("My Branches")){
                    Intent intent=new Intent(context,ViewBranches.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Add Your Employee")){
                    Intent intent=new Intent(context,AddEmployee.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Employee Details")){
                    Intent intent=new Intent(context,viewemployee.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Branch Products")){
                    Intent intent=new Intent(context,viewbran.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Upcoming Orders")){
                    Intent intent=new Intent(context,Uporder.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Order History")){
                    Intent intent=new Intent(context,orderhistory.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("View Subscription List")){
                    Intent intent=new Intent(context,Subscriptionlist.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Cancelled Subscription List")){
                    Intent intent=new Intent(context,cancelsub.class);
                    context.startActivity(intent);
                }
                if (carTitle.equals("Earnings")){
                    Intent intent=new Intent(context,Earnings.class);
                    context.startActivity(intent);
                }

               /* if (carTitle.equals("Earnings")){
                    Intent intent=new Intent(context,Earnings.class);
                    context.startActivity(intent);
                }*/

            }
        });





        // Create and return our custom Car Recycler View Item Holder object.
        RecyclerViewItemHolder ret = new RecyclerViewItemHolder(carItemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder holder, int position) {
        if(carItemList!=null) {
            // Get car item dto in list.
            CardRecyclerViewItem carItem = carItemList.get(position);

            if(carItem != null) {
                // Set car item title.
                holder.getCarTitleText().setText(carItem.getName());
                // Set car image resource id.
                holder.getCarImageView().setImageResource(carItem.getImageId());






            }

            /*holder.getCarImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context,ManualOrders.class);
                    context.startActivity(intent);

                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(carItemList!=null)
        {
            ret = carItemList.size();
        }
        return ret;
    }
}