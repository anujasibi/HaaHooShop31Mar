package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Confirmresetpin extends AppCompatActivity {


    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    Activity activity = this;
    SessionManager sessionManager;
    private String Urline = Global.BASE_URL+"api_shop_app/shop_forget_login_pin_set/";
    public String pins="";
    String device_id = null;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);

            pins=pin;
            Log.d(TAG, "Pin completefdafaf: " + pins);

            if(sessionManager.getPins().equals(pin)){
                Toast.makeText(Confirmresetpin.this,"verified",Toast.LENGTH_SHORT).show();
                submituser();
            }
            if(!(sessionManager.getPins().equals(pin))){
                Toast.makeText(Confirmresetpin.this,"Pin mismatch",Toast.LENGTH_SHORT).show();
            }
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
        setContentView(R.layout.activity_confirmresetpin);

        sessionManager=new SessionManager(this);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        device_id =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
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
                        Toast.makeText(Confirmresetpin.this, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Confirmresetpin.this, Pincode.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Confirmresetpin.this, "Failed."+ot, Toast.LENGTH_LONG).show();


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

                        Toast.makeText(Confirmresetpin.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("device_id",device_id);
                //  Log.d("name","mm"+device_id);
                params.put("phone_no",sessionManager.getresetphn());
                Log.d("owener","mm"+sessionManager.getresetphn());
                params.put("login_pin",pins);
                Log.d("phone","mm"+pins);


                return params;
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                Log.d("token","mm"+sessionManager.getTokens());
                return params;
            }
*/
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Confirmresetpin.this);
        requestQueue.add(stringRequest);

    }
}
