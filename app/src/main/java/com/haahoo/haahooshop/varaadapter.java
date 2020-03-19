package com.haahoo.haahooshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class varaadapter extends ArrayAdapter {

    ArrayList<Item> birdList = new ArrayList<>();
    private String URLlin = Global.BASE_URL+"api_shop_app/shop_pdt_var_count_update/";
    public String ids;
    private SessionManager sessionManager;
    Context context;
    public int quantity = 0;

    public varaadapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        birdList = objects;
        this.context=context;
        sessionManager=new SessionManager(getContext());
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_view_variant, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        TextView textView1=(TextView)v.findViewById(R.id.textView1);
        final TextView viewk=v.findViewById(R.id.sr);
        TextView minus = v.findViewById(R.id.minus);
        final TextView addD = v.findViewById(R.id.add);
        TextView plus = v.findViewById(R.id.plus);
        //   TextView textView3=(TextView)v.findViewById(R.id.textView3);


        viewk.setText("Stock : "+birdList.get(position).getStock());
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        ImageView delete = (ImageView) v.findViewById(R.id.delete);
        textView.setText(birdList.get(position).getName());
        //  Picasso.with(getContext()).load(birdList.get(position).getImage()).into(imageView);
        Picasso.get().load(birdList.get(position).getImage()).into(imageView);
        textView1.setText(birdList.get(position).getPrice());
        // textView3.setText(birdList.get(position).getStock());

        ids=birdList.get(position).getId();
        addD.setText(birdList.get(position).getStock());

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity >= 0) {


                    quantity = Integer.parseInt(birdList.get(position).getStock()) + 1;
                    birdList.get(position).setStock(String.valueOf(quantity));
                    //     ApiClient.directsell_cartcount = String.valueOf(quantity);
                    addD.setText(birdList.get(position).getStock());
                    sessionManager.setSto(birdList.get(position).getStock());
                    viewk.setText("Stock : "+birdList.get(position).getStock());
                    updatestock(birdList.get(position).getId());
                    /*ApiClient.productB2BPojo = bookingPojos;

                    ApiClient.ids.add(bookingPojos.get(position).getId());
                    ApiClient.quantity.add(holder.add.getText().toString());*/

//                ApiClient.ids.add(productPojo.get(position).getId());
//                ApiClient.quantity.add(holder.add.getText().toString());
//                Log.d("beffrrr","hgghfgh"+ApiClient.quantity.get(position));
                    // if (!(ApiClient.ids.contains(productPojo.get(position).getId()))){

                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>0) {

                    quantity = Integer.parseInt(birdList.get(position).getStock())-1;
                    //  ApiClient.directsell_cartcount = String.valueOf(quantity);
                    birdList.get(position).setStock(String.valueOf(quantity));
                    addD.setText(birdList.get(position).getStock());
                    sessionManager.setSto(birdList.get(position).getStock());
                    viewk.setText("Stock : "+birdList.get(position).getStock());
                    updatestock(birdList.get(position).getId());
                    //  ApiClient.productB2BPojo = bookingPojos;
                }
                if (quantity==0){
                    // ApiClient.directsell_cartcount = String.valueOf(quantity);
                    addD.setText("0");
                    //minus.setVisibility(View.GONE);
                    birdList.get(position).setStock("0");
                    sessionManager.setSto(birdList.get(position).getStock());
                    viewk.setText("Stock : "+birdList.get(position).getStock());
                    updatestock(birdList.get(position).getId());
                }
            }
        });




        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Do you want to delete this product??");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                deleteuser(birdList.get(position).getId());

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
        });*/
        return v;

    }

  /*  private void deleteuser(final String product_id){
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
                                Toast.makeText(getContext(), "Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(), viewproduct.class);
                                getContext().startActivity(intent);
                            }


                            if(!(status.equals("200"))){
                                Toast.makeText(getContext(), "Failed."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("pdt_id",product_id);
                Log.d("idlllll","mm"+product_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }*/

    private void updatestock(final String product_id){
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

                            //    sessionManager.setTokens(token);





                            Log.d("code","mm"+status);
                            if(status.equals("200")&&(!(ot.equals("verify")))){
                                Toast.makeText(getContext(), "Successful", Toast.LENGTH_LONG).show();
                            }


                            if(!(status.equals("200"))){
                                Toast.makeText(getContext(), "Failed."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("variant_id",product_id);
                Log.d("idlllll","mm"+product_id);
                params.put("count",sessionManager.getSto());
                Log.d("idlllll","mm"+sessionManager.getSto());

                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

}