package com.haahoo.haahooshop;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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


public class splashscreen extends AppCompatActivity implements ForceUpdateChecker.OnUpdateNeededListener{
    Activity activity = this;
    SessionManager sessionManager;
    Handler handler;
    String device_id = null;
    private String Urline = Global.BASE_URL+"api_shop_app/shop_model_id_verification/";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        sessionManager = new SessionManager(this);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        device_id =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

       checkdeviceid();


        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();





       /* if (sessionManager.getTokens().length() == 0){

            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);

        }
        if (!(sessionManager.getTokens().length() == 0)){
           // checktype();

            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);

        }*/

    }
    private  void checkdeviceid(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            /*String status=jsonObject.optString("role_type");
                            String token=jsonObject.optString("Token");*/
                            //    sessionManager.setTokens(token);
                         /*   Log.d("otp","mm"+token);
                            Log.d("code","mm"+status);*/
                            if(ot.equals("Device already exist")){
                                handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(splashscreen.this,Pincode.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },3000);
                            }
                            if(!(ot.equals("Device already exist"))){
                                handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(splashscreen.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },3000);

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

                        Toast.makeText(splashscreen.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("device_id",device_id);
                Log.d("name","mm"+device_id);
                return params;
            }





        };

        RequestQueue requestQueue = Volley.newRequestQueue(splashscreen.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue reposting.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}