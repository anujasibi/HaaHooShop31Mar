package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class viewdeliveryplan extends AppCompatActivity {
    TextView defcharge,freedistance,freediscart,freeordcart,out;
    Context context=this;
    SessionManager sessionManager;
    public String url= Global.BASE_URL+"api_shop_app/show_delivery_details/";
    Activity activity=this;
    TextView edit;
    public String def,dis,disc,ordc,outu;
    CardView cardView;
    TextView add;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdeliveryplan);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        add=findViewById(R.id.add);

        defcharge=findViewById(R.id.defcharge);
        freedistance=findViewById(R.id.freedis);
        freediscart=findViewById(R.id.freediscart);
        freeordcart=findViewById(R.id.freeordcart);
        out=findViewById(R.id.out);
        cardView=findViewById(R.id.card);
        imageView3=findViewById(R.id.imageView3);
        sessionManager=new SessionManager(this);

        submit();

        edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,EditDelivery.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,DeliveryPlans.class));
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Navigation.class));
            }
        });
    }

    private void submit(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    String data=jsonObject.optString("data");
                    Log.d("data","mmm"+data);


                        //  Log.d("data","mmm"+data);
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        JSONObject dataobj = dataArray.getJSONObject(0);
                        def = dataobj.optString("default_charge");
                        defcharge.setText("₹ " + def);
                        dis = dataobj.optString("free_distance");
                        freedistance.setText(dis + " KM");
                        ordc = dataobj.optString("cart_value");
                        freeordcart.setText("₹ " + ordc);
                        disc = dataobj.optString("charge");
                        freediscart.setText("₹ " + disc);
                        outu = dataobj.optString("other_charge");
                        out.setText("₹ " + outu);

                        if(def.equals("0")&&ordc.equals("0")&&disc.equals("0")&&outu.equals("0")){
                            cardView.setVisibility(View.GONE);
                        }
                  /*  if(!(def.equals("0")&&ordc.equals("0")&&disc.equals("0")&&outu.equals("0"))){
                        cardView.setVisibility(View.GONE);
                    }*/

                        //  Log.d("data","mmm"+def);


                } catch (Exception e) {
                  e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Internal Server Error",Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            public Map<String,String>getHeaders(){
                Map<String,String>params=new HashMap<>();
                params.put("Authorization","Token "+sessionManager.getTokens());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
