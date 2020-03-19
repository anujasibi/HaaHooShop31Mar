package com.haahoo.haahooshop;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.haahoo.haahooshop.utils.DataTransferInterface;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductSearch extends AppCompatActivity  implements DataTransferInterface{


    GridView grid;
    ArrayList<Itemprod> birdList=new ArrayList<>();
    TextView textView;
    TextInputEditText name;

    RecyclerView recyclerView;
    EditText editTextSearch;
    ArrayList<String> names;
    Activity activity=this;
    public String Url= Global.BASE_URL+"api_shop_app/search_shop_products/";
    ProductSearchAdapter adapter;
    Context context=this;
    SessionManager sessionManager;
    public String pname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);



// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        sessionManager=new SessionManager(this);

        sessionManager.setcatid("");


        textView=findViewById(R.id.save);
        ImageView imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,choosepdtcategory.class));
            }
        });


       // grid=(GridView)findViewById(R.id.grid);
     //   submituser();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextSearch.getText().toString().equals("")){
                    editTextSearch.setError("ProductName is required");
                    //Toast.makeText(addproductnew.this,"All are fields are required",Toast.LENGTH_SHORT).show();
                }

                if(!(editTextSearch.getText().toString().equals(""))) {


                        sessionManager.setPdtName(editTextSearch.getText().toString());
                        // sessionManager.setcatid(idsp);
                        Intent intent = new Intent(ProductSearch.this, addproductnew.class);
                        // sessionManager.setPid(idsp);
                        startActivity(intent);

                }
//                startActivity(new Intent(AddProduct.this,category.class));
            }
        });







        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                recyclerView.setVisibility(View.VISIBLE);

                search();

                //after the change calling the method and passing the search input
                // filter(editable.toString());

            }
        });


        recyclerView.setHasFixedSize(true);



    //    editTextSearch.setText(sessionManager.getsearch());



    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : names) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    private void search(){
        RequestQueue queue = Volley.newRequestQueue(ProductSearch.this);

        //this is the url where you want to send the request


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);

                            // amount.setText(obj.optString("total"));

                            String total=obj.optString("total");
                            JSONArray dataArray  = obj.getJSONArray("data");
                            names = new ArrayList<>();
                            Log.d("mmmm","mmgdfgd"+response);
                            for (int i = 0; i < dataArray.length(); i++) {

                                Item playerModel = new Item();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                //   playerModel.setProductname(dataobj.optSt ring("name"));
                                // ApiClient.productids.add(dataobj.optString("id"));
                                pname=dataobj.optString("pdt_name");
                                Log.d("mmmm","mmgdfgd"+pname);

                                names.add(pname);


                            }


                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            adapter = new ProductSearchAdapter(names,context,ProductSearch.this);
                            recyclerView.setAdapter(adapter);




                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ProductSearch.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String,String>getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("search_key", editTextSearch.getText().toString());
                params.put("cat_id",sessionManager.getcatrid());
                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(ProductSearch.this);

        //this is the url where you want to send the request

        String url = Global.BASE_URL+"api_shop_app/list_pdt_cat/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response","mmmm"+response);

                        try {

                            JSONObject obj = new JSONObject(response);

                            // amount.setText(obj.optString("total"));
                            birdList = new ArrayList<>();
                            String total=obj.optString("total");
                            JSONArray dataArray  = obj.getJSONArray("data");

                            if(dataArray.length() == 0){
                                Toast.makeText(ProductSearch.this,"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                Itemprod playerModel = new Itemprod();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                playerModel.setName(dataobj.optString("name"));
                                Log.d("ssssd", "resp" + dataobj);
                                String images1 = dataobj.getString("image");
                                String[] seperated = images1.split(",");
                                String split = seperated[0].replace("[", "").replace("]","");
                                playerModel.setImage(Global.BASE_URL+split);
                                birdList.add(playerModel);
                                playerModel.setId(dataobj.optString("id"));

                            }
                            CustomGrid myAdapter=new CustomGrid(context,R.layout.gridviewadd,birdList);
                            grid.setAdapter(myAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductSearch.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("cat_id",sessionManager.getcatrid());
                Log.d("gnmnbn","mm"+sessionManager.getcatrid());
                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    @Override
    public void onSetValues(String pdtname) {
        //Toast.makeText(context,pdtname,Toast.LENGTH_SHORT).show();
        editTextSearch.setText(pdtname);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,choosepdtcategory.class));
    }
}