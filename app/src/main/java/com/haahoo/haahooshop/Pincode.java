package com.haahoo.haahooshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Pincode extends AppCompatActivity {


    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    Activity activity = this;
    public String pins="";
    FirebaseAnalytics firebaseAnalytics;
    private String token_firebase;
    String device_id = null;
    Context context=this;
    TextView pass,reset;
    SessionManager sessionManager;
    private String Urline = Global.BASE_URL+"api_shop_app/shop_pin_verify/";

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);

            pins=pin;
            verify();

        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        sessionManager=new SessionManager(this);

        device_id =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        token_firebase = FirebaseInstanceId.getInstance().getToken();
        Log.d("tokkkken","lhykhiyh"+token_firebase);
        pass=findViewById(R.id.pass);
        reset=findViewById(R.id.reset);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);


        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MainActivity.class));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Resetpinphone.class));
            }
        });
    }

    private  void  verify(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urline, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String ot = jsonObject.optString("message");
                    String status=jsonObject.optString("code");
                    String token=jsonObject.optString("Token");
                    String role_type=jsonObject.optString("role_type");
                    String payment_status=jsonObject.optString("payment_status");
                    sessionManager.setTokens(token);




                    Log.d("otp","mm"+token);
                    Log.d("code","mm"+status);
                    if(status.equals("200")&&ot.equals("Success")){
                       // Toast.makeText(Pincode.this, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Pincode.this, Navigation.class);
                        startActivity(intent);
                    }

                    if(payment_status.equals("Payment not done")&&status.equals("200")){
                        Toast.makeText(Pincode.this, "Please do payment", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Pincode.this, Payment.class);
                        startActivity(intent);

                    }
                    if(role_type.equals("")&&payment_status.equals("Payment done")){

                        Toast.makeText(Pincode.this, "Please choose your role for further process", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Pincode.this, choose.class);
                        startActivity(intent);
                    }
//                    if(role_type.equals("")||payment_status.equals("Payment not done")&&status.equals("200")){
//                        Toast.makeText(Pincode.this, "Login Failed.Please do payment", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(Pincode.this, paymentnew.class);
//                        startActivity(intent);
//
//                    }
//                    if(role_type.equals("")&&payment_status.equals("Payment done")){
//
//                        Toast.makeText(Pincode.this, "Login Failed.Please choose your role for further process", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(Pincode.this, choose.class);
//                        startActivity(intent);
//                    }
                    if (status.equals("200")&&ot.equals("Failed")){
                        Toast.makeText(Pincode.this, "Failed.Invalid Pin", Toast.LENGTH_LONG).show();
                        mPinLockView.resetPinLockView();



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

                       // Toast.makeText(Pincode.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("device_id",device_id);
                //  Log.d("name","mm"+device_id);
                params.put("device_id",device_id);
                Log.d("owener","mm"+device_id);
                params.put("pin",pins);
                Log.d("phone","mm"+pins);
                params.put("fire",token_firebase);
                Log.d("fire","mm"+token_firebase);


                return params;
            }

          /*  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                Log.d("token","mm"+sessionManager.getTokens());
                return params;
            }*/

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}