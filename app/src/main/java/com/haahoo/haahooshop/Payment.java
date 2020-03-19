package com.haahoo.haahooshop;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.JsonObject;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payment extends Activity implements PaymentResultListener {
    private static final String TAG = Payment.class.getSimpleName();
    private static final int REQUEST_PHONE_CALL =1 ;
    private String number, email;
    ImageSlider imageSlider;
    String razorid = "null";
    //  private String URLlin = "https://testapi.creopedia.com/api_shop_app/shop_payment_det/";
    //   private String URLli = "https://testapi.creopedia.com/api_shop_app/shop_offer_images/";
    //  private String URLlin = "https://haahoo.in/api_shop_app/shop_payment_det/";
    //   private String URLli = "https://haahoo.in/api_shop_app/shop_offer_images/";
    private String URLlin = Global.BASE_URL + "api_shop_app/shop_payment_det/";
    private String URLli = Global.BASE_URL + "api_shop_app/shop_offer_images/";
    private String url=Global.BASE_URL+"api_shop_app/set_payment_method/";
    SessionManager sessionManager;
    Context context = this;
    Activity activity = this;
    List<SlideModel> array = new ArrayList<>();
    Button cod;
    private String ppp="null";
    TextView num;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sessionManager = new SessionManager(this);
        Window window = activity.getWindow();
        //offer();
        cod = findViewById(R.id.cod);
        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ppp="COD";
                newin();
                //Intent intent = new Intent(context, CODOTP.class);
               // startActivity(intent);

            }
        });

        // imageSlider=findViewById(R.id.img);
       /* ArrayList<SlideModel>imagelist=new ArrayList<>();
        imagelist.add(new SlideModel("https://1.bp.blogspot.com/-GUZsgr8my50/XJUWOhyHyaI/AAAAAAAABUo/bljp3LCS3SUtj-judzlntiETt7G294WcgCLcBGAs/s1600/fox.jpg", "Foxes live wild in the city.", true));
        imagelist.add(new SlideModel("https://1.bp.blogspot.com/-GUZsgr8my50/XJUWOhyHyaI/AAAAAAAABUo/bljp3LCS3SUtj-judzlntiETt7G294WcgCLcBGAs/s1600/fox.jpg", "Foxes live wild in the city.", true));
        imageSlider.setImageList(imagelist,false);*/

        //    imageSlider.setImageList(R.drawable.person,false);

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        Button skip = findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context,"You skipped payment",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Payment.this, MainActivity.class));

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ppp="Online";
                newin();
                startPayment();

            }
        });


        num = findViewById(R.id.num);

        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + num.getText().toString()));
                if (ContextCompat.checkSelfPermission(Payment.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Payment.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(intent);
                }
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "HaaHoo Business");
            options.put("description", "Registration Fee");
            //You can omit the image option to fetch the image from dashboard

            options.put("currency", "INR");
            options.put("amount", "200000");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact","");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: ", Toast.LENGTH_SHORT).show();
            Log.d("paymentidmmmm","mm"+razorpayPaymentID);

            razorid=razorpayPaymentID;
            boouser();

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
    private void offer(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLli,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  dialog.dismiss();
                        //  Toast.makeText(Payment.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String data= obj.optString("data");

                                String[] seperated = data.split(",");
                            for(int i = 0; i<seperated.length; i++){
                                String split = seperated[i].replace("[", "").replace("]","").trim();
                                array.add(new SlideModel(Global.BASE_URL+split));
                                Log.d("sssds", "mm" + split);

                            }

                            imageSlider.setImageList(array,false);


                            } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //  Log.d("response","hhh"+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){



        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    private void boouser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  dialog.dismiss();
                      //  Toast.makeText(Payment.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("status");
                            String code=jsonObject.optString("code");

                          //  Log.d("otp","mm"+ot);
                            if(code.equals("200")) {
                                Toast.makeText(Payment.this, ot, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Payment.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(Payment.this, ot, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      //  Log.d("response","hhh"+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                /*params.put("shop_id", sessionManager.getpayid());
                Log.d("driver","mm"+sessionManager.getpayid());*/
                params.put("payment_amount","200000");
                params.put("payment_id",razorid);
                Log.d("paymentid",razorid);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                Log.d("paymentid",sessionManager.getTokens());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(context,MainActivity.class));
    }

    private void newin(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", "mm" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");

                    if (message.equals("Admin will contact you soon")) {
                        //Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context,Executive.class));
                    }
                    if (message.equals("success")){
                       // Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, CODOTP.class);
                        startActivity(intent);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }




        ){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>params=new HashMap<>();
                params.put("pay_method",ppp);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String>params=new HashMap<>();
                params.put("Authorization","Token "+sessionManager.getTokens());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}