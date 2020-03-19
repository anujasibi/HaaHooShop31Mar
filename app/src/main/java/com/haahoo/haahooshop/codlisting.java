package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class codlisting extends AppCompatActivity {

    ArrayList<codlistitem> birdList=new ArrayList<>();
    Activity activity = this;
    Context context=this;
    SessionManager sessionManager;
    TextView editsearch;
    RecyclerView listView;
    ImageView back;
    ArrayList<upcomingrow> rowItems;
    private ProgressDialog dialog ;
    private Calendar myCalendar;
    ImageView imageView;
    ImageView img;
    TextView textView;
    private String url= Global.BASE_URL+"api_shop_app/list_all_cod/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codlisting);

        listView = (RecyclerView) findViewById(R.id.recycler);

        img=findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,Navigation.class));
            }
        });

        sessionManager=new SessionManager(this);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        submit();
    }

    private void submit(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","mmm"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    String message=jsonObject.getString("message");
                    String code=jsonObject.getString("code");
                    //String data=jsonObject.getString("data");
                    JSONArray dataArray  = jsonObject.getJSONArray("data");
                    Log.d("dataaa","mmm"+dataArray);

                    if(dataArray.length()==0){
                        Toast.makeText(context,"There is no order with cod found",Toast.LENGTH_SHORT).show();
                    }
                    if (!(dataArray.length() == 0)) {

                        for (int i=0;i<dataArray.length();i++){
                            codlistitem playermodel=new codlistitem();
                            JSONObject jsonObject1=dataArray.getJSONObject(i);

                            playermodel.setPdtname(jsonObject1.optString("pdt_name"));
                            playermodel.setAmount(jsonObject1.optString("amount"));
                            playermodel.setOrderid(jsonObject1.optString("order_id"));
                            Log.d("orderid","mm"+jsonObject1.optString("order_id"));
                            playermodel.setType(jsonObject1.optString("customer_name"));
                            Log.d("orderid","mm"+jsonObject1.optString("customer_name"));
                            String images1 = jsonObject1.getString("pdt_img");
                            String[] seperated = images1.split(",");
                            String split = seperated[0].replace("[", "").replace("]","");
                            playermodel.setImage(Global.BASE_URL+split);


                            birdList.add(playermodel);

                            codlistingadapter upcomingAdapter=new codlistingadapter(context,birdList);
                            listView.setAdapter(upcomingAdapter);
                            listView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));



                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }


        ){
            @Override

            public Map<String,String>getHeaders(){
                Map<String,String>params=new HashMap<>();
                params.put("Authorization","Token " + sessionManager.getTokens());
                return params;
            }


        };

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,Navigation.class));
    }
}
