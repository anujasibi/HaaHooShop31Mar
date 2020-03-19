package com.haahoo.haahooshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddBankDetails extends AppCompatActivity {
    EditText empname,branch,phone,email;
    TextView submit,show,hide;
    private String Urline = Global.BASE_URL+"shop_bank_details/shop_bank_det/";
    Context context=this;
    SessionManager sessionManager;
    ImageView imageView;
    private ProgressDialog dialog ;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_details);
        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(AddBankDetails.this,R.style.MyAlertDialogStyle);

        empname=findViewById(R.id.shopname);
        branch=findViewById(R.id.distance);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);


        imageView=findViewById(R.id.imageView3);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Navigation.class));
            }
        });

        sessionManager=new SessionManager(this);

        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(empname.getText().toString().length()==0||branch.getText().toString().length()==0||phone.getText().toString().length()==0||email.getText().toString().length()==0){
                    Toast.makeText(context,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                if(!(empname.getText().toString().length()==0||branch.getText().toString().length()==0||phone.getText().toString().length()==0)||email.getText().toString().length()==0)
                {
                    dialog.setMessage("Loading");
                    dialog.show();
                    submituser();
                }
            }
        });
    }

    private void submituser(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urline, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String ot = jsonObject.optString("message");
                    String status=jsonObject.optString("code");
                    String token=jsonObject.optString("Token");
                    //    sessionManager.setTokens(token);




                    Log.d("otp","mm"+token);
                    Log.d("code","mm"+status);
                    if(status.equals("200")){
                        Toast.makeText(AddBankDetails.this, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddBankDetails.this, Navigation.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddBankDetails.this, "Failed."+ot, Toast.LENGTH_LONG).show();


                    }

                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
                   Log.d("response","hhh"+response);


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(AddBankDetails.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("device_id",device_id);
                //  Log.d("name","mm"+device_id);
                params.put("acc_name",empname.getText().toString());
                Log.d("name","mm"+empname.getText().toString());
                params.put("ifsc",branch.getText().toString());
                Log.d("owener","mm"+branch.getText().toString());
                params.put("acc_no",phone.getText().toString());
                Log.d("phone","mm"+phone.getText().toString());
                params.put("email",email.getText().toString());
                Log.d("phone","mm"+email.getText().toString());


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
        startActivity(new Intent(context,Navigation.class));

    }
}
