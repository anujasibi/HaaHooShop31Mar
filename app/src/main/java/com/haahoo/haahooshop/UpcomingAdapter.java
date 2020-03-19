package com.haahoo.haahooshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<upcomingrow> dataModelArrayList;
    public Context context1 ;
    public String status="0";
    public String stat="0";
    public String id;
    private String URLline = Global.BASE_URL+"virtual_order_management/shop_order_accpt/";
    SessionManager sessionManager;

    public UpcomingAdapter(Context ctx, ArrayList<upcomingrow> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public UpcomingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.upcominglist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final UpcomingAdapter.MyViewHolder holder, final int position) {

        id=dataModelArrayList.get(position).getId();
        holder.statuss.setText(dataModelArrayList.get(position).getStatus());

        if(dataModelArrayList.get(position).getPay().equals("0")){
            holder.pay.setText("Not Done");
        }
        if(dataModelArrayList.get(position).getPay().equals("1")){
            holder.pay.setText("Done");
        }


        if(dataModelArrayList.get(position).getStatus().equals("accepted")){
            holder.sta.setVisibility(View.GONE);
            holder.sta1.setVisibility(View.GONE);
            holder.statuss.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.VISIBLE);
            if(dataModelArrayList.get(position).getPay().equals("0")){
                holder.pay.setText("Not Done");
            }
            if(dataModelArrayList.get(position).getPay().equals("1")){
                holder.pay.setText("Done");
            }
        }
        if(dataModelArrayList.get(position).getStatus().equals("Rejected")){
            holder.sta.setVisibility(View.GONE);
            holder.sta1.setVisibility(View.GONE);
            holder.pay.setVisibility(View.GONE);
            holder.statuss.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.VISIBLE);
          //  holder.cardView.setVisibility(View.GONE);
        }
        if(dataModelArrayList.get(position).getStatus().equals("Dispatched")){
         holder.cardView.setVisibility(View.GONE);

        }

        //Picasso.with(context1).load(dataModelArrayList.get(position).getImage()).into(holder.iv);
        Picasso.get().load(dataModelArrayList.get(position).getImage()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
       holder.location.setText(dataModelArrayList.get(position).getLocation());
       holder.sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
           @Override
           public void onSlideComplete(@NonNull final SlideToActView view) {
               Toast.makeText(context1,"Accepted the request",Toast.LENGTH_SHORT).show();

               AlertDialog.Builder builder1 = new AlertDialog.Builder(context1);
               builder1.setMessage("Do you want to accept this order??");
               builder1.setCancelable(true);

               builder1.setPositiveButton(
                       "Yes",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                               status="1";
                               accept();
                               holder.sta.setVisibility(View.GONE);
                               holder.sta1.setVisibility(View.GONE);
                               Intent intent=new Intent(context1,orderdetails.class);
                               intent.putExtra("pdtname",dataModelArrayList.get(position).getName());
                               intent.putExtra("pdtimage",dataModelArrayList.get(position).getImage());
                               intent.putExtra("id",dataModelArrayList.get(position).getId());
                               intent.putExtra("cusname",dataModelArrayList.get(position).getCusname());
                               intent.putExtra("phone",dataModelArrayList.get(position).getNumber());
                               intent.putExtra("house",dataModelArrayList.get(position).getHouse());
                               intent.putExtra("city",dataModelArrayList.get(position).getCity());
                               intent.putExtra("state",dataModelArrayList.get(position).getState());
                               intent.putExtra("landmark",dataModelArrayList.get(position).getLocation());
                               intent.putExtra("price",dataModelArrayList.get(position).getPrice());
                               intent.putExtra("pincode",dataModelArrayList.get(position).getPincode());
                               context1.startActivity(intent);


                           }
                       });

               builder1.setNegativeButton(
                       "No",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                               view.resetSlider();
                           }
                       });

               AlertDialog alert11 = builder1.create();
               alert11.show();

           }
       });

       holder.sta1.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
           @Override
           public void onSlideComplete(final SlideToActView slideToActView) {

               Toast.makeText(context1,"Rejected the request",Toast.LENGTH_SHORT).show();

               AlertDialog.Builder builder1 = new AlertDialog.Builder(context1);
               builder1.setMessage("Do you want to reject this order??");
               builder1.setCancelable(true);

               builder1.setPositiveButton(
                       "Yes",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                               status="0";
                               accept();
                               holder.sta.setVisibility(View.GONE);
                               holder.sta1.setVisibility(View.GONE);
                               context1.startActivity(new Intent(context1,Uporder.class));

                           }
                       });

               builder1.setNegativeButton(
                       "No",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                               slideToActView.resetSlider();
                           }
                       });

               AlertDialog alert11 = builder1.create();
               alert11.show();


           }
       });

        if(dataModelArrayList.get(position).getStatus().equals("accepted")) {

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context1, orderdetails.class);
                    intent.putExtra("pdtname", dataModelArrayList.get(position).getName());
                    intent.putExtra("pdtimage", dataModelArrayList.get(position).getImage());
                    intent.putExtra("id", dataModelArrayList.get(position).getId());
                    intent.putExtra("cusname", dataModelArrayList.get(position).getCusname());
                    intent.putExtra("phone", dataModelArrayList.get(position).getNumber());
                    intent.putExtra("house", dataModelArrayList.get(position).getHouse());
                    intent.putExtra("city", dataModelArrayList.get(position).getCity());
                    intent.putExtra("state", dataModelArrayList.get(position).getState());
                    intent.putExtra("landmark", dataModelArrayList.get(position).getLocation());
                    intent.putExtra("price", dataModelArrayList.get(position).getPrice());
                    intent.putExtra("pincode", dataModelArrayList.get(position).getPincode());
                    context1.startActivity(intent);
                }
            });

        }


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

        TextView name, location,pay;
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

            sta  = (SlideToActView) itemView.findViewById(R.id.example);
            sta1  = (SlideToActView) itemView.findViewById(R.id.examplen);
            status=itemView.findViewById(R.id.status);
            statuss=itemView.findViewById(R.id.statuse);
            pay=itemView.findViewById(R.id.paye);
//            qty = (TextView) itemView.findViewById(R.id.non);
        }

    }

    private void accept(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String token=jsonObject.optString("Token");
                            //    sessionManager.setTokens(token);




                            Log.d("otp","mm"+token);
                            Log.d("code","mm"+status);
                            if(status.equals("200")&&(!(ot.equals("verify")))){
                                Toast.makeText(context1, "Successful", Toast.LENGTH_LONG).show();



                            }


                            if(!(status.equals("200"))){
                                Toast.makeText(context1, "Failed."+ot, Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context1,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("order_id",id);
                params.put("accepted",status);
                Log.d("idlllll","mm"+status);

                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context1);
        requestQueue.add(stringRequest);

    }


}