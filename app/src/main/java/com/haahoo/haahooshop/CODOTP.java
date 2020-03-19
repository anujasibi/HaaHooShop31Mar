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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class CODOTP extends AppCompatActivity {

    ImageView image;
    TextInputEditText employee_id,employee_phone,otp,receipt;
    Button verify,verifyotp;
    RelativeLayout employeeotprel;
    Context context = this;
    Activity activity = this;
    String received_otp = "null";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codotp);
        image = findViewById(R.id.image);
        Picasso.get().load("https://www.tpghrservices.com/wp-content/uploads/employee-relations-icon-color.png").into(image);
        employee_id = findViewById(R.id.empID);
        employee_phone = findViewById(R.id.empPhone);
        verify = findViewById(R.id.verify);
        employeeotprel = findViewById(R.id.employeeotprel);
        otp = findViewById(R.id.otp);
        receipt=findViewById(R.id.receipt);
        sessionManager = new SessionManager(context);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        verifyotp = findViewById(R.id.verifyotp);

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Global.BASE_URL+"api_shop_app/emp_shop_otp_verification/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("stauts","hjgffdg"+jsonObject);
                                    String message = jsonObject.optString("message");
                                    if (message.equals("failed")){
                                        otp.setError("Invalid OTP");
                                    }
                                    if (message.equals("verify")){
                                        startActivity(new Intent(context,MainActivity.class));
                                    }

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Authorization","Token "+sessionManager.getTokens());
                       // params.put("otp",otp.getText().toString());

                        return params;
                    }

                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("phone_no",employee_phone.getText().toString());
                        params.put("otp",otp.getText().toString());
                        params.put("receipt_id",receipt.getText().toString());

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Global.BASE_URL+"api_shop_app/shop_employee_verification/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("stauts","hjgffdg"+jsonObject);
                                    String message = jsonObject.optString("message");
                                    if (message.equals("failed")){
                                        Toast.makeText(context,"Invalid details",Toast.LENGTH_SHORT).show();
                                    }
                                    if(!(message.equals("failed"))){
                                        employeeotprel.setVisibility(View.VISIBLE);
                                        received_otp = message;

                                    }


                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("emp_phone",employee_phone.getText().toString());
                        params.put("id",employee_id.getText().toString());

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });

    }
}
