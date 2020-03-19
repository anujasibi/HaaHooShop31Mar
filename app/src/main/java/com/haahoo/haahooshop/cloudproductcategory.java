package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.Map;


public class cloudproductcategory extends AppCompatActivity {

    Activity activity=this;
    ArrayList<Itemprod> birdList=new ArrayList<>();
    GridView grid;
    Context context=this;
    SessionManager sessionManager;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudproductcategory);

        grid=(GridView)findViewById(R.id.card_view_recycler_list);
        sessionManager=new SessionManager(this);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        submituser();

        back=findViewById(R.id.imageView3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cloudproductcategory.this,Productmanager.class));
            }
        });


    }

    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(cloudproductcategory.this);

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
                                Toast.makeText(cloudproductcategory.this,"Nothing to display",Toast.LENGTH_SHORT).show();
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
                            CustommGrid myAdapter=new CustommGrid(context,R.layout.gridcloudcat,birdList);
                            grid.setAdapter(myAdapter);

                            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                                   startActivity(new Intent(context,cloudproductview.class));
                                    //Toast.makeText(cloudproductcategory.this, "" + position, Toast.LENGTH_SHORT).show();

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cloudproductcategory.this,"Internal Server Error",Toast.LENGTH_LONG).show();


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
        startActivity(new Intent(cloudproductcategory.this,Productmanager.class));

    }
}
