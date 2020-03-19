package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class finaladd extends AppCompatActivity {
    TextInputEditText distance;
    Spinner spinner;
    public String status="";
    public String status1="";
    public String statu="";
    LinearLayout ln;
    ArrayList<String> areas = new ArrayList<String>();
    String delivery_type = "null";
    //String URL="https://testapi.creopedia.com/api_shop_app/list_shop_cat/ ";
    //String URL="https://haahoo.in/api_shop_app/list_shop_cat/ ";
    String URL= Global.BASE_URL+"api_shop_app/list_shop_cat/ ";
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,check,checkm,checks1,checks2,free,notfree;
    private RadioGroup radioSexGroup;
    private RadioButton one,two,three;
    SessionManager sessionManager;
    ArrayList<String> areasid = new ArrayList<String>();
    public String idsp;
    ImageView imageView;
    TextInputLayout ress,paida;
    TextInputEditText resell,paidamount;
    TextView save,res,paid;
    Activity activity = this;
    ArrayList<String>arrayList=new ArrayList<>();
    public String urlnn= Global.BASE_URL+"api_shop_app/list_branches_main/ ";
    public String url= Global.BASE_URL+"api_shop_app/employee_verification/";
    EditText empid;
    TextView Verify,verified,verifyf,edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finaladd);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        res=findViewById(R.id.res);
        ress=findViewById(R.id.ress);
        resell=findViewById(R.id.resellprice);
        empid=findViewById(R.id.empid);
        Verify=findViewById(R.id.veri);
        verified=findViewById(R.id.verid);
        verifyf=findViewById(R.id.veridf);


        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox1);
     //   checkBox3 = findViewById(R.id.checkBox2);
        checkBox4 = findViewById(R.id.checkBox3);
        check=findViewById(R.id.checkBo);
        checkm=findViewById(R.id.checkBo1);
        checks1=findViewById(R.id.checkBot);
        checks2=findViewById(R.id.checkBo1t);
        ln=findViewById(R.id.ln);

      //  radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
       // one=findViewById(R.id.radioMale);
    //    two=findViewById(R.id.radioFemale);
    //    three=findViewById(R.id.radioFe);
        save=findViewById(R.id.save);
        distance=findViewById(R.id.name);
        sessionManager=new SessionManager(this);
        imageView=findViewById(R.id.imageView3);
edit=findViewById(R.id.edit);
edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        empid.setEnabled(true);
        Verify.setVisibility(View.VISIBLE);
        verified.setVisibility(View.GONE);
        verifyf.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
    }
});


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(finaladd.this,addprod.class));
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkm.setChecked(false);
                status="1";
                res.setVisibility(View.VISIBLE);
                ress.setVisibility(View.VISIBLE);
                sessionManager.setcheckn(status);


            }
        });


        checkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check.setChecked(false);
                status="0";
                sessionManager.setcheckn(status);
                res.setVisibility(View.GONE);
                ress.setVisibility(View.GONE);
                resell.setText("0");
            }
        });

      /*  free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notfree.setChecked(false);
                statu="0";
                sessionManager.setdels(statu);
                paid.setVisibility(View.GONE);
                paida.setVisibility(View.GONE);
                paidamount.setText("0");
            }
        });
        notfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                free.setChecked(false);
                statu="1";
                paid.setVisibility(View.VISIBLE);
                paida.setVisibility(View.VISIBLE);
                sessionManager.setdels(statu);

            }
        });*/

        checks1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checks2.setChecked(false);
                status1="1";
                sessionManager.setaddshop(status1);
            }
        });
        checks2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checks1.setChecked(false);
                status1="0";
                sessionManager.setaddshop(status1);
            }
        });




        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    /*checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);*/
                    delivery_type = checkBox1.getText().toString();
              //      sessionManager.setcheck(delivery_type);
                    arrayList.add(delivery_type);
                    sessionManager.setArrayList(arrayList);
                    Global.arrayList=arrayList;
                    Log.d("mmm","dcfvgbh"+arrayList);
                    Log.d("mmm","dcfvgbh"+ Global.arrayList);
                 //   Toast.makeText(finaladd.this,"bhnjv"+checkBox1.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
//                    checkBox1.setChecked(false);
//                    checkBox3.setChecked(false);
//                    checkBox4.setChecked(false);
                    delivery_type = checkBox2.getText().toString();
                //    sessionManager.setcheck(delivery_type);
                    arrayList.add(delivery_type);
                    sessionManager.setArrayList(arrayList);
                    Global.arrayList=arrayList;
                    Log.d("mmm","dcfvgbh"+arrayList);
                    Log.d("mmm","dcfvgbh"+ Global.arrayList);
                 //   Toast.makeText(finaladd.this,"bhnjv"+checkBox2.getText().toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });

      /*  checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    delivery_type = checkBox3.getText().toString();
                    sessionManager.setcheck(delivery_type);
                //    Toast.makeText(finaladd.this,"bhnjv"+checkBox3.getText().toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });*/

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    /*checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);*/
                    delivery_type = checkBox4.getText().toString();
                    //sessionManager.setcheck(delivery_type);
                    arrayList.add(delivery_type);
                    Log.d("mmm","dcfvgbh"+arrayList);
                    Global.arrayList=arrayList;
                    sessionManager.setArrayList(arrayList);
                    Log.d("mmm","dcfvgbh"+   Global.arrayList);
                    ln.setVisibility(View.VISIBLE);
                    empid.setVisibility(View.VISIBLE);
                    Verify.setVisibility(View.VISIBLE);
                    //    Toast.makeText(finaladd.this,"bhnjv"+checkBox3.getText().toString(),Toast.LENGTH_SHORT).show();

                }
                if (!(((CheckBox) view).isChecked())) {
                    ln.setVisibility(View.GONE);
                    empid.setVisibility(View.GONE);
                    Verify.setVisibility(View.GONE);
                }
            }
        });

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemp();
            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(distance.getText().length()==0||delivery_type.equals("null")||status.equals("")||(resell.getText().toString().length() == 0)) {
                    Toast.makeText(finaladd.this, "All are fields are required", Toast.LENGTH_SHORT).show();
                }
                if(!(distance.getText().length()==0||delivery_type.equals("null")||status.equals(""))) {
                    if (!(resell.getText().toString().length() == 0)) {
                       // if(!(paidamount.getText().toString().length() == 0)){
                        //    sessionManager.setdelamount(paidamount.getText().toString());

                        sessionManager.setreselprice(resell.getText().toString());
                        Log.d("mmmmmmmmmmmm", "mm" + sessionManager.getreselprice());
                        sessionManager.setcatdistance(distance.getText().toString());
                //        int selectedId = radioSexGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                     //   one = (RadioButton) findViewById(selectedId);

                     //   sessionManager.setradio(one.getText().toString());
                        Log.d("gvggxxsxsssxss","mm"+ Global.arrayList);

                     /*   Toast.makeText(finaladd.this,
                                one.getText(), Toast.LENGTH_SHORT).show();*/

                        startActivity(new Intent(finaladd.this, DeliveryOptions.class));
                  //  }
                }
            }
            }
        });


//        if (checkBox1.isChecked()){
//            delivery_type = checkBox1.getText().toString();
//            Toast.makeText(finaladd.this,"hjvgjh"+delivery_type,Toast.LENGTH_SHORT).show();
//            checkBox2.setChecked(false);
//            checkBox3.setChecked(false);
//        }
//        if (checkBox2.isChecked()){
//            delivery_type = checkBox2.getText().toString();
//            Toast.makeText(finaladd.this,"hjvgjh"+delivery_type,Toast.LENGTH_SHORT).show();
//            checkBox1.setChecked(false);
//            checkBox3.setChecked(false);
//        }
//        if (checkBox3.isChecked()){
//            delivery_type = checkBox3.getText().toString();
//            Toast.makeText(finaladd.this,"hjvgjh"+delivery_type,Toast.LENGTH_SHORT).show();
//            checkBox1.setChecked(false);
//            checkBox2.setChecked(false);
//        }


        spinner = findViewById(R.id.spinner);
        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country= spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                idsp=areasid.get(spinner.getSelectedItemPosition());
                Toast.makeText(getApplicationContext(),idsp,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }



    private void loadSpinnerData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlnn, new Response.Listener<String>() {
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

                    spinner.setAdapter(new ArrayAdapter<String>(finaladd.this, android.R.layout.simple_spinner_dropdown_item, areas));
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
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
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

    public void checkemp(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("RESPONSE","MMNKBH"+response);
                    String code=jsonObject.getString("code");
                    if(code.equals("200")){
                        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                        verified.setVisibility(View.VISIBLE);
                        empid.setEnabled(false);
                        verified.setText("Verified");
                        sessionManager.setempid(empid.getText().toString());
                        sessionManager.setempbranch(idsp);
                        edit.setVisibility(View.VISIBLE);


                    }else {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        verified.setVisibility(View.VISIBLE);
                        verified.setText("Not Verified");
                        empid.setEnabled(false);
                        edit.setVisibility(View.VISIBLE);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String,String>getParams(){
                Map<String,String>params=new HashMap<>();
                params.put("emp_id",empid.getText().toString());
                params.put("emp_branch",idsp);
                return params;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(finaladd.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(finaladd.this,addprod.class));
    }
}
