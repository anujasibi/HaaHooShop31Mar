package com.haahoo.haahooshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class AddEmployee extends AppCompatActivity {
    EditText empname,branch,phone,password,empid;
    TextView submit,show,hide;
    private String Urline = Global.BASE_URL+"api_shop_app/employee_registration/";
    Context context=this;
    SessionManager sessionManager;
    ImageView imageView,imgp,imageView1;
    private ProgressDialog dialog ;
    Activity activity = this;

    String files;
    Spinner spinner;
    public String idsp;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2,CGALLERY=3,CCAMERA=4;
    private RadioGroup radioSexGroup;
    private RadioButton one,two,three;
    String filePath;
    private Uri uri;
    ArrayList<String> areas = new ArrayList<String>();
    ArrayList<String> areasid = new ArrayList<String>();
   // String URL = "https://testapi.creopedia.com/api_shop_app/list_branches_main/ ";
   // String URL = "https://haahoo.in/api_shop_app/list_branches_main/ ";
    String URL = Global.BASE_URL+"api_shop_app/list_branches_main/ ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(AddEmployee.this,R.style.MyAlertDialogStyle);



        empname=findViewById(R.id.shopname);
       // branch=findViewById(R.id.distance);
        phone=findViewById(R.id.phone);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        one=findViewById(R.id.radioMale);
        two=findViewById(R.id.radioFemale);
        three=findViewById(R.id.radiojj);
        password=findViewById(R.id.password);
        imageView1 = findViewById(R.id.img);
        imgp=findViewById(R.id.imgp);
        empid=findViewById(R.id.empid);




      //  Toast.makeText(AddEmployee.this,one.getText().toString(), Toast.LENGTH_SHORT).show();


        show = findViewById(R.id.show);
        hide = findViewById(R.id.hide);

        spinner = findViewById(R.id.spinner);
        loadSpinnerData(URL);
       /* spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String country = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                idsp = areasid.get(spinner.getSelectedItemPosition());
                Toast.makeText(getApplicationContext(), country, Toast.LENGTH_LONG).show();
            }
        });*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
               idsp = areasid.get(spinner.getSelectedItemPosition());
          //   Toast.makeText(getApplicationContext(), spinner.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        imageView=findViewById(R.id.imageView3);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,BranchManagement.class));
            }
        });

        sessionManager=new SessionManager(this);

        submit = findViewById(R.id.submit);

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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(empname.getText().toString().length()==0||phone.getText().toString().length()==0||password.getText().toString().length()==0){
                  Toast.makeText(context,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                if(!(empname.getText().toString().length()==0||phone.getText().toString().length()==0||password.getText().toString().length()==0))
                {
                    dialog.setMessage("Loading");
                    dialog.show();
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    one = (RadioButton) findViewById(selectedId);
                    submituser();
                }
            }
        });
    }




    private void loadSpinnerData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("ressssssssss","mm"+response);
                    JSONObject jsonObject = new JSONObject(response);
                    areas.add("Please Choose Branch");
                    areasid.add("0");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String country = jsonObject1.getString("name");
                        String id = jsonObject1.getString("id");
                        areas.add(country);
                        areasid.add(id);

                    }

                    spinner.setAdapter(new ArrayAdapter<String>(AddEmployee.this, android.R.layout.simple_spinner_dropdown_item, areas));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                Log.d("token","mm"+sessionManager.getTokens());
                return params;
            }


        };


        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

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
                    String id=jsonObject.optString("id");
                    //    sessionManager.setTokens(token);




                    Log.d("otp","mm"+token);
                    Log.d("code","mm"+status);
                    if(status.equals("200")){
                        Toast.makeText(AddEmployee.this, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddEmployee.this, addempnew.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddEmployee.this, "Failed."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(AddEmployee.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
               // params.put("device_id",device_id);
              //  Log.d("name","mm"+device_id);
                params.put("name",empname.getText().toString());
                Log.d("name","mm"+empname.getText().toString());
                params.put("branch_name",idsp);
                Log.d("owener","mm"+idsp);
                params.put("password",password.getText().toString());
                Log.d("pass","mm"+password.getText().toString());
                params.put("phone",phone.getText().toString());
                Log.d("phone","mm"+phone.getText().toString());
                params.put("type",one.getText().toString());
                params.put("id",empid.getText().toString());
                Log.d("phone","mm"+one.getText().toString());
                Log.d("id","mm"+empid.getText().toString());
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
        startActivity(new Intent(context,BranchManagement.class));

    }
}
