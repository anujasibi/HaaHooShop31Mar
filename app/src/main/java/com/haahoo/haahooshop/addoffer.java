package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addoffer extends AppCompatActivity {
    Activity activity=this;
    EditText des,dis;
    CardView cardView;
    Context context=this;
    SessionManager sessionManager;
    private String Urline = Global.BASE_URL+"api_shop_app/add_pdt_offers/";
    ImageView imageView3;
    Spinner spinner;
    private Uri uri;
    public String country;
    ArrayList<String> areas = new ArrayList<String>();
    ArrayList<String> areasid = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addoffer);


        sessionManager=new SessionManager(this);

        imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,viewproduct.class));
            }
        });

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));


        des=findViewById(R.id.des);
        dis=findViewById(R.id.dis);

        cardView=findViewById(R.id.card_view);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submituser();
            }
        });

        spinner = findViewById(R.id.spinner);
        loadSpinnerData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country= spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
            /*    idsp=areas.get(spinner.getSelectedItemPosition());
                Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



    }

    private void loadSpinnerData() {

                    areas.add("Please Choose Your Offer Type");
                    areas.add("Rupees");
                    areas.add("Percentage");
                    areasid.add("0");
                    areasid.add("1");
                    areasid.add("2");
        spinner.setAdapter(new ArrayAdapter<String>(addoffer.this, android.R.layout.simple_spinner_dropdown_item, areas));

                    }




    private  void  submituser(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urline, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String ot = jsonObject.optString("message");
                    String status=jsonObject.optString("code");
                    String token=jsonObject.optString("Token");
                    //    sessionManager.setTokens(token);




                    Log.d("otp","mm"+token);
                    Log.d("code","mm"+status);
                    if(status.equals("200")){
                        Toast.makeText(addoffer.this, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(addoffer.this, viewproduct.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(addoffer.this, "Failed."+ot, Toast.LENGTH_LONG).show();


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

                        Toast.makeText(addoffer.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("device_id",device_id);
                //  Log.d("name","mm"+device_id);
                params.put("pdt_id",sessionManager.getPdtaddvar());
                Log.d("name","mm"+sessionManager.getPdtaddvar());
                params.put("coupons",des.getText().toString());
                Log.d("owener","mm"+des.getText().toString());
                params.put("discount",dis.getText().toString());
                Log.d("phone","mm"+dis.getText().toString());
                params.put("type",country);
                Log.d("phone","mm"+country);


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                Log.d("token","mm"+sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,viewproduct.class));

    }

    }

