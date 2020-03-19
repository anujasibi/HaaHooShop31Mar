package com.haahoo.haahooshop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class registernew extends AppCompatActivity {
    EditText shopname,owner,gstno,phone,email,password,distance,empID,promo;
    EditText address;
    TextView submit,show,hide,appl,rem,sta;
    String device_id = null;
    Spinner spinner;
    public String idsp;
    Activity activity = this;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    String filePath;
    private Uri uri;
    ArrayList<String> areas = new ArrayList<String>();
    ArrayList<String> areasid = new ArrayList<String>();
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
 //   String URL="https://testapi.creopedia.com/api_shop_app/list_shop_cat/ ";
 //   String URL="https://haahoo.in/api_shop_app/list_shop_cat/ ";
    String URL=Global.BASE_URL+"api_shop_app/list_shop_cat/ ";
    Context context=this;
    private String URLline = Global.BASE_URL+"api_shop_app/shop_new_reg/";
    public String source_lat="0";
    String source_lng="0";
    ImageView imh;
    public String  phone_no;
    String emailPattern = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";
    private ProgressDialog dialog ;
    ImageView image;
    public String url=Global.BASE_URL+"api_shop_app/registration_coupon/ ";
    RelativeLayout appre,remre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registernew);
        image = findViewById(R.id.image);
        empID = findViewById(R.id.empID);
        Picasso.get().load(Global.BASE_URL+"media/files/events_add/extra_pic/haahoo_logo1.png").into(image);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(registernew.this,R.style.MyAlertDialogStyle);



        owner=findViewById(R.id.owner);
        promo=findViewById(R.id.promo);
        sta=findViewById(R.id.sta);
        appl=findViewById(R.id.app);
        rem=findViewById(R.id.rem);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        appre=findViewById(R.id.appre);
        remre=findViewById(R.id.remre);
        show=findViewById(R.id.show);
        hide=findViewById(R.id.hide);
        device_id =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager=new SessionManager(this);


        Bundle bundle = getIntent().getExtras();
        phone_no = bundle.getString("phone_no");

        phone.setText(phone_no);







        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hide.setVisibility(View.VISIBLE);
                show.setVisibility(View.GONE);

            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hide.setVisibility(View.GONE);
                show.setVisibility(View.VISIBLE);
            }
        });


      /*  appl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("promo","mm"+promo.getText().toString());
                promo.setEnabled(false);
                appl.setVisibility(View.GONE);
                rem.setVisibility(View.VISIBLE);

                if(promo.getText().toString().equals("HAHRATHEESH")){
                    status();

                }
                if(!(promo.getText().toString().equals("HAHRATHEESH"))){

                    sta.setText("Invalid Promocode");
                }
            }
        });*/


      appre.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              promo.setEnabled(false);
              appre.setVisibility(View.GONE);
              remre.setVisibility(View.VISIBLE);
              status();

          }
      });

      /*  rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rem.setVisibility(View.GONE);
                appl.setVisibility(View.VISIBLE);
                promo.setEnabled(true);
                sta.setText("");
            }
        });*/


        remre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sta.setText("");
                promo.setText("");
                remre.setVisibility(View.GONE);
                appre.setVisibility(View.VISIBLE);
                promo.setEnabled(true);

            }
        });








        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // register();
                if(owner.getText().toString().length()==0||phone.getText().toString().length()==0||password.getText().toString().length()==0){
                    Toast.makeText(context,"Some required field is missing",Toast.LENGTH_SHORT).show();
                }

                if(!(owner.getText().toString().length()==0||phone.getText().toString().length()==0||password.getText().toString().length()==0)) {

                        dialog.setMessage("Loading");
                        dialog.show();
                        register();

                }
            }
        });
    }




    private void register(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String token=jsonObject.optString("token");
                            String id=jsonObject.optString("id");
                            sessionManager.setpayid(id);
                            sessionManager.setTokens(token);

                            Log.d("otp","mm"+token);
                            Log.d("code","mm"+status);
                            if(status.equals("200")){
                                //Toast.makeText(registernew.this, "Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(registernew.this, PinRegister.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(registernew.this, "Failed ", Toast.LENGTH_LONG).show();


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
                        Toast.makeText(registernew.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("device_id",device_id);
                Log.d("name","mm"+device_id);
                params.put("name",owner.getText().toString());
                Log.d("owener","mm"+owner.getText().toString());
                params.put("email",email.getText().toString());
                Log.d("email","mm"+email.getText().toString());
                params.put("password",password.getText().toString());
                Log.d("pass","mm"+password.getText().toString());
                params.put("phone_no",phone.getText().toString());
                Log.d("phone","mm"+phone.getText().toString());
                params.put("coupon",promo.getText().toString());
                Log.d("name","mm"+promo.getText().toString());
                if (empID.getText().toString().length()>0){
                    params.put("ref_id",empID.getText().toString());
                }
                if (empID.getText().toString().length() == 0){
                    params.put("ref_id","0");
                }

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(registernew.this,"Press again to exit",Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private void status(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);

                    String ot = jsonObject.optString("message");
                    String status=jsonObject.optString("code");
                   // String token=jsonObject.optString("token");

                    if(status.equals("200")){
                        //Toast.makeText(registernew.this, "Successful", Toast.LENGTH_LONG).show();
                        sta.setText("Applied Successfully");
                    }
                    if(status.equals("203")){
                        sta.setText("Invalid PromoCode");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(registernew.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("coupon",promo.getText().toString());
                Log.d("name","mm"+promo.getText().toString());
                params.put("phone_no",phone.getText().toString());
                Log.d("phone","mm"+phone.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



}
