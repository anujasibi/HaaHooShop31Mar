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
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Imagepojo> dataModelArrayList;
    public Context context1 ;
    public String status="0";
    public String stat="0";
    public String id;

    private String URLlin = Global.BASE_URL+"api_shop_app/shop_pdt_img_remove/";
    SessionManager sessionManager;

    public String idim;

    public ImageAdapter(Context ctx, ArrayList<Imagepojo> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.image_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ImageAdapter.MyViewHolder holder, final int position) {




        //Picasso.with(context1).load(dataModelArrayList.get(position).getImage()).into(holder.iv);

Log.d("yugyug","yghfghf"+Global.imj[position]);
            String split = Global.imj[position].replace("[", "").replace("]","").trim();
            Picasso.get().load(Global.BASE_URL+split).into(holder.iv);

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context1);
                    builder1.setMessage("Do you want to delete this image??");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    idim=Global.ik.get(position);
                                    Log.d("dwnfwnf","mm"+Global.ik.get(position));
                                    removenew();

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();



                }
            });




    }

    @Override
    public int getItemCount() {
        return Global.imj.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView iv,img;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);


            iv = itemView.findViewById(R.id.imgg);
            img = itemView.findViewById(R.id.img);
            cardView=itemView.findViewById(R.id.card);


//            qty = (TextView) itemView.findViewById(R.id.non);
        }

    }


    private void removenew(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
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
                            if(status.equals("200")){
                                Toast.makeText(context1, "Successful", Toast.LENGTH_LONG).show();
                                context1.startActivity(new Intent(context1,viewproduct.class));



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
                params.put("image_id",idim);

                Log.d("idlllll","mm"+idim);

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