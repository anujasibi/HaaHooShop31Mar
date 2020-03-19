package com.haahoo.haahooshop;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addproductnew extends AppCompatActivity {
    GridView grid;
    Activity activity = this;
    ArrayList<Itemprod> birdList=new ArrayList<>();
    Context context=this;
    SessionManager sessionManager;
    TextView textView;
    TextInputEditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproductnew);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        sessionManager=new SessionManager(this);
     //   name=findViewById(R.id.name);
        sessionManager.setcatid("");


// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        textView=findViewById(R.id.save);
        ImageView imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,ProductSearch.class));
            }
        });


        grid=(GridView)findViewById(R.id.grid);
        submituser();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             /*   if(name.getText().toString().equals("")){
                    name.setError("ProductName is required");
                    //Toast.makeText(addproductnew.this,"All are fields are required",Toast.LENGTH_SHORT).show();
                }*/
                if(sessionManager.getcatid().length()==0){
                    Toast.makeText(addproductnew.this,"Should requires at least one category",Toast.LENGTH_SHORT).show();
                }

                    if (!(sessionManager.getcatid().length() == 0)) {

                        //sessionManager.setPdtName(name.getText().toString());
                        // sessionManager.setcatid(idsp);
                        Intent intent = new Intent(addproductnew.this, category.class);
                        intent.putExtra("category", sessionManager.getcatid());
                        // sessionManager.setPid(idsp);
                        startActivity(intent);

                }
//                startActivity(new Intent(AddProduct.this,category.class));
            }
        });
       /* grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(addproductnew.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });*/

    }

    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(addproductnew.this);

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
                                Toast.makeText(addproductnew.this,"Nothing to display",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(addproductnew.this,"Internal Server Error",Toast.LENGTH_LONG).show();


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
    public void onBackPressed() {
        startActivity(new Intent(addproductnew.this,ProductSearch.class));

    }

}