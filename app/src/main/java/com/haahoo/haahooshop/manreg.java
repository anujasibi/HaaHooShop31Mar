package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class manreg extends AppCompatActivity {
    Activity activity = this;
    EditText shopname,owner,gstno,phone,email,password,distance,pinc,upi;
    EditText address;
    TextView submit,show,hide;
    String device_id = null;
    Spinner spinner,spinnerd;
    public String idsp="null";
    ImageView imageView;

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    String filePath;
    private Uri uri;
    ArrayList<String> areas = new ArrayList<String>();
    ArrayList<String> areasid = new ArrayList<String>();
    ArrayList<String> areas1 = new ArrayList<String>();
    ArrayList<String> areasid1 = new ArrayList<String>();
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
    //String URL="https://testapi.creopedia.com/api_shop_app/shop_distances_det/";
    //   String URL="https://haahoo.in/api_shop_app/shop_distances_det/";
    String URL= Global.BASE_URL+"api_shop_app/shop_distances_det/";
    Context context=this;
    private String URLline = Global.BASE_URL+"api_shop_app/shop_registeration/";
    public String source_lat="0";
    String source_lng="0";
    ImageView imh;
    public String  phone_no;
    String emailPattern = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";
    private ProgressDialog dialog ;
    public String country1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manreg);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        dialog=new ProgressDialog(manreg.this,R.style.MyAlertDialogStyle);

        shopname=findViewById(R.id.shopname);

        upi=findViewById(R.id.upiid);
        owner=findViewById(R.id.owner);
        pinc=findViewById(R.id.pinc);
        gstno=findViewById(R.id.gst);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        address=findViewById(R.id.des);
        distance=findViewById(R.id.distance);
        show=findViewById(R.id.show);
        imageView=findViewById(R.id.submit);
        hide=findViewById(R.id.hide);
        device_id =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager=new SessionManager(this);


        spinnerd = findViewById(R.id.spinnerd);
        loadSpinnerDat();
        spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country1= spinnerd.getItemAtPosition(spinnerd.getSelectedItemPosition()).toString();
                sessionManager.setdistrict(country1);
                Log.d("districtfgh","mm"+sessionManager.getdistrict());
            /*    idsp=areas.get(spinner.getSelectedItemPosition());
                Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });










        gstno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (gstno.getText().toString().matches(emailPattern) && gstno.getText().toString().length() > 0)
                {
                    //Toast.makeText(getApplicationContext(),"valid gst no",Toast.LENGTH_SHORT).show();

                }
                if (!(gstno.getText().toString().matches(emailPattern) && gstno.getText().toString().length() > 0))
                {
                    //Toast.makeText(getApplicationContext(),"Invalid GST Number",Toast.LENGTH_SHORT).show();
                    gstno.setError("Invalid GST Number");

                }

            }
        });






        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address.getText().toString(),
                        getApplicationContext(), new manreg.GeocoderHandler());
            }
        });



        pinc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address.getText().toString(),
                        getApplicationContext(), new manreg.GeocoderHandler());

                return false;
            }
        });


        spinner = findViewById(R.id.spinner);
        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country= spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                idsp=areasid.get(spinner.getSelectedItemPosition());
                //    Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        shopname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(shopname.getText().length()==0) {
                    shopname.setError("Distributor Name is required");
                }
            }
        });
        owner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(owner.getText().length()==0) {
                    owner.setError("Owner Name is required");
                }
            }
        });
        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(address.getText().length()==0) {
                    address.setError("Address is required");
                }

            }
        });
        pinc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(pinc.getText().length()==0) {
                    pinc.setError("Pincode is required");
                }
                if(pinc.getText().length()<6){
                    pinc.setError("Invalid Pincode");
                }
            }
        });





        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idsp.equals("0")){
                    Toast.makeText(context,"Please choose the distance",Toast.LENGTH_SHORT).show();
                }
                if(pinc.getText().length()<6){
                    pinc.setError("Invalid Pincode");
                }

                if(shopname.getText().toString().length()==0||owner.getText().toString().length()==0||address.getText().toString().length()==0||idsp.equals("0")||pinc.getText().length()==0){
                    Toast.makeText(context,"Some required field is missing",Toast.LENGTH_SHORT).show();
                }
                if(source_lng.equals("0")||source_lat.equals("0")){
                    //
                    GeocodingLocation locationAddress = new GeocodingLocation();
                    locationAddress.getAddressFromLocation(address.getText().toString(),
                            getApplicationContext(), new manreg.GeocoderHandler());
                    Toast.makeText(context,"Please enter the complete address",Toast.LENGTH_SHORT).show();

                }
                if(!(shopname.getText().toString().length()==0||owner.getText().toString().length()==0||address.getText().toString().length()==0||idsp.equals("0")||source_lng.equals("0")||source_lat.equals("0")||pinc.getText().length()<6)) {
                    sessionManager.setshopname(shopname.getText().toString());
                    sessionManager.setown(owner.getText().toString());
                    sessionManager.setgst(gstno.getText().toString());
                    sessionManager.setadd(address.getText().toString());
                    sessionManager.setdis(idsp);
                    sessionManager.setlat(source_lat);
                    sessionManager.setlog(source_lng);
                    sessionManager.setpincode(pinc.getText().toString());
                    sessionManager.setupiid(upi.getText().toString());

                    Log.d("cvgbnm","mmm"+sessionManager.getshopname());
                    Log.d("cvgbnm","mmm"+sessionManager.getown());
                    Log.d("cvgbnm","mmm"+sessionManager.getgst());
                    Log.d("cvgbnm","mmm"+sessionManager.getadd());
                    Log.d("cvgbnm","mmm"+sessionManager.getdis());
                    Log.d("cvgbnm","mmm"+sessionManager.getlat());
                    Log.d("cvgbnm","mmm"+sessionManager.getlog());
                    Log.d("cvgbnm","mmm"+sessionManager.getupiid());
                    startActivity(new Intent(context,shpcategory.class));
                }


                if(!(source_lng.equals("0")||source_lat.equals("0"))) {
                    sessionManager.setshopname(shopname.getText().toString());
                    sessionManager.setown(owner.getText().toString());
                    sessionManager.setgst(gstno.getText().toString());
                    sessionManager.setadd(address.getText().toString());
                    sessionManager.setdis(idsp);
                    sessionManager.setlat(source_lat);
                    sessionManager.setlog(source_lng);
                    sessionManager.setpincode(pinc.getText().toString());
                    sessionManager.setupiid(upi.getText().toString());

                    Log.d("cvgbnm","mmm"+sessionManager.getshopname());
                    Log.d("cvgbnm","mmm"+sessionManager.getown());
                    Log.d("cvgbnm","mmm"+sessionManager.getgst());
                    Log.d("cvgbnm","mmm"+sessionManager.getadd());
                    Log.d("cvgbnm","mmm"+sessionManager.getdis());
                    Log.d("cvgbnm","mmm"+sessionManager.getlat());
                    Log.d("cvgbnm","mmm"+sessionManager.getlog());
                    Log.d("cvgbnm","mmm"+sessionManager.getupiid());
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("lat", source_lat);
                    intent.putExtra("long", source_lng);
                    startActivity(intent);
                }







            }
        });
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String lat,lonh;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    lat= bundle.getString("lat");
                    lonh=bundle.getString("long");
                    Log.d("source","mm"+lat);
                    Log.d("longitude","mm"+lonh);
                    if (lat == null){
                        source_lat="0";
                        source_lng="0";
                    }
                    if (lat != null) {
                        source_lat = lat;
                        source_lng = lonh;
                    }
                    //       Toast.makeText(Register.this,source_lat+source_lng,Toast.LENGTH_SHORT).show();
                    //  sessionManager.setDestLong(lonh);
                    break;
                default:
                    locationAddress = null;
            }
            //  latLongTV.setText(locationAddress);
            Log.d("locationaddress","mm"+locationAddress);
        }

    }


    private void loadSpinnerData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    areas.add("Delivery distance in kms");
                    areasid.add("0");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("dis");
                        String id=jsonObject1.getString("id");

                        areas.add(country);
                        areasid.add(id);

                    }

                    spinner.setAdapter(new ArrayAdapter<String>(manreg.this, android.R.layout.simple_spinner_dropdown_item, areas));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadSpinnerDat() {

        areas1.add("Please Choose Your District");
        areas1.add("Thiruvananthapuram");
        areas1.add("Kollam");
        areas1.add("Alappuzha");
        areas1.add("Pathanamthitta");
        areas1.add("Kottayam");
        areas1.add("Idukki");
        areas1.add("Ernakulam");
        areas1.add("Thrissur");
        areas1.add("Palakkad");
        areas1.add("Malappuram");
        areas1.add("Kozhikode");
        areas1.add("Wayanadu");
        areas1.add("Kannur ");
        areas1.add("Kasaragod");


        areasid1.add("0");
        areasid1.add("1");
        areasid1.add("2");
        areasid1.add("3");
        areasid1.add("4");
        areasid1.add("5");
        areasid1.add("6");
        areasid1.add("7");
        areasid1.add("8");
        areasid1.add("9");
        areasid1.add("10");
        areasid1.add("11");
        areasid1.add("12");
        areasid1.add("13");
        areasid1.add("14");


        spinnerd.setAdapter(new ArrayAdapter<String>(manreg.this, android.R.layout.simple_spinner_dropdown_item, areas1));

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,choose.class));
    }
}




