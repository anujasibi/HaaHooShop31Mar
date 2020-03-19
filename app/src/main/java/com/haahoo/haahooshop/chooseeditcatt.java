package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class chooseeditcatt extends AppCompatActivity {

    private RecyclerView offerRecyclerView;
    ArrayList<OffersModel> birdList=new ArrayList<>();
    Activity activity=this;
    ImageView imageView3;
    Context context=this;
    TextView add;
    // String URL="https://testapi.creopedia.com/api_shop_app/all_cat_shop/ ";
    // String URL="https://haahoo.in/api_shop_app/all_cat_shop/ ";
    String URL= Global.BASE_URL+"api_shop_app/all_cat_shop/ ";
    SessionManager sessionManager;
    ArrayList<String>idn=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseeditcatt);

        sessionManager=new SessionManager(this);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        imageView3=findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,choosepdtcategory.class));
            }
        });


        add=findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chooseeditcatt.this,editmaincatee.class));
            }
        });

        offerRecyclerView = (RecyclerView) findViewById(R.id.offers_lst);


        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        offerRecyclerView.setLayoutManager(recyclerLayoutManager);

        loadSpinnerData();

     /*   DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(offerRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        offerRecyclerView.addItemDecoration(dividerItemDecoration);*/


        /*choosepdtcategoryadapter recyclerViewAdapter = new
                choosepdtcategoryadapter(getBrands(),this);
        offerRecyclerView.setAdapter(recyclerViewAdapter);*/
    }



    private void loadSpinnerData() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    if(jsonArray.length() == 0){
                        Toast.makeText(chooseeditcatt.this,"Nothing to display",Toast.LENGTH_SHORT).show();
                    }
                    for(int i=0;i<jsonArray.length();i++){
                        OffersModel playerModel = new OffersModel();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("name");
                        String id=jsonObject1.getString("id");
                        idn.add("\"" +id+"\"" );
                        Global.hashmap.put("categoryfirst"+String.valueOf(i),id);
                        Log.d("ghjklkj","mm"+idn);
                        Global.trr=idn;
                        playerModel.setName(country);
                        playerModel.setRadio(id);

                        birdList.add(playerModel);


                        editchoosepdtadapter upcomingAdapter=new editchoosepdtadapter(birdList, context);
                        offerRecyclerView.setAdapter(upcomingAdapter);
                        offerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));


                    }

                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }


        }) {

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,choosepdtcategory.class));
    }
}
