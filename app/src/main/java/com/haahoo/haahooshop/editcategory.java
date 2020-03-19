package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import java.util.List;
import java.util.Map;

public class editcategory extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<EditPojo> specpojos = new ArrayList<>();
    Specad specAdapter;
    Context context = this;
    String[] value = null;
    SessionManager sessionManager;
    ImageView imageView3;
    BroadcastReceiver broadcastReceiver;
    TextView save;
    Activity activity = this;
    // String url = "https://testapi.creopedia.com/api_shop_app/list_pdt_cat_spec/";
    //String url = "https://haahoo.in/api_shop_app/list_pdt_cat_spec/";
    String url =Global.BASE_URL+ "api_shop_app/list_pdt_cat_spec/";
    String urln =Global.BASE_URL+ "api_shop_app/product_specification_list/";
    public String header,val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcategory);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));


        recyclerView = findViewById(R.id.recycle);
        imageView3=findViewById(R.id.imageView3);
        save=findViewById(R.id.save);
        sessionManager = new SessionManager(this);
        sessionManager.setcatName("");
        Bundle bundle =getIntent().getExtras();
        final String category = bundle.getString("category");
        loadspecs(category);
        previous();
        ArrayList<String> vals = new ArrayList<>();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,viewproduct.class));
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context,"kjnkj"+ Global.category,Toast.LENGTH_SHORT).show();

//                if(Global.category.equals("Please Select ...")){
//                    Toast.makeText(context,"Please Select All Specifications"+ Global.category,Toast.LENGTH_SHORT).show();
//                }
//                if(!(Global.category.equals("Please Select ..."))){
//                    //Toast.makeText(context,"Please Select All Specifications"+ Global.category,Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(context,addprod.class));
//                }
                //Toast.makeText(context,"y"+sessionManager.getcatName(),Toast.LENGTH_SHORT).show();
                if (sessionManager.getcatName().length()==0){
                    Toast.makeText(context,"Specifications cannot be left empty",Toast.LENGTH_SHORT).show();
                }
                if (sessionManager.getcatName().length()!=0){
                    submit();

                }
                //
            }
        });



    }


    private void loadspecs(final String category){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String message=jsonObject.optString("message");

                            if(message.equals("No Specifications")){
                                Toast.makeText(context,"Currently no specifications available for the choosen category",Toast.LENGTH_SHORT).show();
                                sessionManager.setcatName("");
                                startActivity(new Intent(context,addprod.class));
                            }
                            if(message.equals("success")){



                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                ArrayList<String> vals = new ArrayList<String>();
                                ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>();


                                Log.d("ghcghdata","ghfvghf"+jsonArray);
                                for (int i =0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                    EditPojo specpojo = new EditPojo();
                                    specpojo.setName(jsonObject1.optString("name"));
                                    specpojo.setId(jsonObject1.optString("id"));
                                    String values1 = jsonObject1.optString("values");
                                    ArrayList<String> column = new ArrayList<String>();
                                    column.add(values1);
                                    row.add(column);

                                    String[]seperated=values1.split(",");
                                    specpojo.setValuesss(seperated);

                                    vals.add(values1);
                                    specpojo.setValuess(vals);
                                    for (int j =0;j<seperated.length;j++){
                                        Log.d("sepee","hjbhjb"+values1);
                                    }




//                      for (int l = 0;l<seperated.length;l++){
//
//                      }
                                    value = jsonObject1.optString("values").split(",");
                                    ArrayList<String> values = new ArrayList<>();
                                    values.add("Please Select ...");
                                    for (int j = 0 ;j<value.length;j++){
                                        values.add(value[j].replace("[","").replace("]",""));
                                    }
                                    specpojo.setValues(values);
                                    // Log.d("jsonresponse","hgf"+value[0]+"kkk"+value[1]+value.length);
                                    specpojos.add(specpojo);
                                }


                                Global.row = row;
                                /*   for (int j = 0 ;j<vals.size();j++){*/
                                Log.d("valss","hjghj"+row.get(0).get(0));
                                Log.d("valssyyy+","hjghj"+row.get(1).get(0));

                                /*  }*/
                                specAdapter = new Specad(specpojos, context);
                                recyclerView.setAdapter(specAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
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
                        Toast.makeText(editcategory.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("cat_id",sessionManager.getcatid());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization","Token "+sessionManager.getTokens());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void previous(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urln,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String message=jsonObject.optString("message");
                            JSONArray jsonArray = jsonObject.optJSONArray("data");
                            ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>();

                            Log.d("ghcghdata","ghfvghf"+jsonArray);
                            for (int i =0;i<jsonArray.length();i++){

                                EditPojo specpojo = new EditPojo();

                                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                JSONObject jsonObject2=jsonObject1.optJSONObject("specification");
                                JSONObject jsonObject3=jsonObject1.optJSONObject("specification_header");
                                for(int k=0; k<jsonObject3.length();k++){
                                    header=jsonObject3.optString("spec"+k);
                                    Log.d("header","ghfvghf"+header);
                                    specpojo.setHeader(header);
                                    val=jsonObject2.optString(header);
                                    specpojo.setHval(val);

                                    ArrayList<String> column = new ArrayList<String>();
                                    column.add(val);
                                    row.add(column);

                                    Log.d("value","ghfvghf"+value);
                                    Global.spec_headers.add(header);
                                    Global.spec_values.add(val);
                                    //    specpojos.add(specpojo);
                                }


                                Global.row1=row;


                                Log.d("jsonObject2","ghfvghf"+jsonObject2);
                                Log.d("jsonObject3","ghfvghf"+jsonObject3);








//                      for (int l = 0;l<seperated.length;l++){
//
//                      }

                                // Log.d("jsonresponse","hgf"+value[0]+"kkk"+value[1]+value.length);
                                //  specpojos.add(specpojo);
                            }




                            /*  }*/
                            //     specAdapter = new Specad(specpojos, context);
                            //    recyclerView.setAdapter(specAdapter);
                            //        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(editcategory.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("product_id",sessionManager.getPdtid());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization","Token "+sessionManager.getTokens());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    private void submit(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String code=jsonObject.optString("code");

                    if(code.equals("200")){
                        Toast.makeText(context,"Successfully Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context,viewproduct.class));
                    }
                    if(!(code.equals("200"))){
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(editcategory.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pdt_id", sessionManager.getPdtid());
                params.put("spec", sessionManager.getcatName());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }


        };

        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,viewproduct.class));
    }
}