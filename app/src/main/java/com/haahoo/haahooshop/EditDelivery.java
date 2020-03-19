package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.textclassifier.TextLinks;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditDelivery extends AppCompatActivity {

    TextInputEditText defaultv;
    EditText distance;
    EditText disamount;
    EditText ordamount;
    EditText outamount;
    SessionManager sessionManager;
    public String url= Global.BASE_URL+"api_shop_app/edit_delivery_details";
    Context context=this;
    TextView save;
    Activity activity=this;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));


        defaultv=findViewById(R.id.resellprice);
        distance=findViewById(R.id.distance);
        disamount=findViewById(R.id.disamount);
        ordamount=findViewById(R.id.orderamo);
        outamount=findViewById(R.id.outamount);
        imageView3=findViewById(R.id.imageView3);
        save=findViewById(R.id.save);
        sessionManager=new SessionManager(this);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,viewdeliveryplan.class));
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });



    }

    private void submit(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","mmm"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String code=jsonObject.optString("code");
                    if(code.equals("200")){
                        Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context,viewdeliveryplan.class));
                    }
                    else{
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditDelivery.this,"Internal Server Error",Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            public Map<String,String>getHeaders(){
                Map<String,String>params=new HashMap<>();
                params.put("Authorization","Token "+sessionManager.getTokens());
                return params;
            }

            @Override
            protected Map<String,String>getParams(){
                Map<String,String>params=new HashMap<>();
                params.put("default_charge",defaultv.getText().toString());
                params.put("free_distance",distance.getText().toString());
                params.put("cart_value",ordamount.getText().toString());
                params.put("charge",disamount.getText().toString());
                params.put("other_charge",outamount.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
