package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class viewvariant extends AppCompatActivity {
    ImageView imageView;
    boolean doubleBackToExitPressedOnce = false;
    CardView cardView;
    Activity activity = this;
    ImageView logout;
    SessionManager sessionManager;
    ImageView back;
    Context context=this;
    String[]ne;
    public String image,pname,price,discount,descr,stock,email;
    private ProgressDialog dialog ;


    // private List<CardRecyclerViewItem> carItemList = null;
    GridView simpleList;
    ArrayList<Item> birdList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewvariant);

        // imageView=findViewById(R.id.img);
        sessionManager = new SessionManager(this);

        cardView=findViewById(R.id.card);

        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewvariant.this,viewproduct.class));
            }
        });
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(viewvariant.this,R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading");
        dialog.show();

        simpleList = (GridView) findViewById(R.id.card_view_recycler_list);

        submituser();

       /* simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(viewvariant.this,viewpdtdetails.class);
                intent.putExtra("pname",birdList.get(i).name);
                intent.putExtra("image",birdList.get(i).getImage());
                intent.putExtra("imagea",birdList.get(i).getImk());
                Log.d("imagearray", String.valueOf(birdList.get(i).getImk()));
                intent.putExtra("price",birdList.get(i).getPrice());
                intent.putExtra("stock",birdList.get(i).getStock());
                intent.putExtra("discount",birdList.get(i).getDiscount());
                intent.putExtra("des",birdList.get(i).getDescription());
                intent.putExtra("id",birdList.get(i).getId());
                intent.putExtra("catid",birdList.get(i).getCategoryid());
                intent.putExtra("display",birdList.get(i).getDisplay());
                intent.putExtra("memory",birdList.get(i).getMemory());
                startActivity(intent);
            }
        });*/





    }
    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(viewvariant.this);

        //this is the url where you want to send the request

        String url = Global.BASE_URL+"api_shop_app/shop_pdt_variants/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();

                        try {

                            JSONObject obj = new JSONObject(response);

                            // amount.setText(obj.optString("total"));
                            birdList = new ArrayList<>();
                            String total=obj.optString("total");
                            JSONArray dataArray  = obj.getJSONArray("data");

                            if(dataArray.length() == 0){
                                Toast.makeText(viewvariant.this,"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                Item playerModel = new Item();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                //   playerModel.setProductname(dataobj.optSt ring("name"));
                                // ApiClient.productids.add(dataobj.optString("id"));
                                pname=dataobj.optString("name");
                                price=dataobj.optString("fixed_price");


                                // Global.spec_headers = spec_headers;
                                //   Global.spec_values = spec_values;
                                stock=dataobj.optString("stock");
                                //  email=dataobj.optString("email");
                                String id=dataobj.getString("id");
                                Log.d("imageurl","bhcbvfc"+id);
                                playerModel.setId(id);
                                sessionManager.setPdtid(id);
                                playerModel.setStock(stock);
                                //    playerModel.setEmail(email);
                                playerModel.setName(dataobj.optString("name"));
                                Log.d("ssssd", "resp" + dataobj);
                                playerModel.setPrice("₹ "+dataobj.optString("fixed_price"));
                                String images1 = dataobj.getString("image");

                               /* ne=images1.split(",");
                                playerModel.setImk(ne);
                                Log.d("imagearray", String.valueOf(playerModel.getImk()));
                                String split = seperated[0].replace("[", "").replace("]","");*/
                                playerModel.setImage(Global.BASE_URL+"media/"+images1);
                                //image=Global.BASE_URL+;

                                /*   JSONObject jsonObject=jsonArray.getJSONObject(0);



                                 */



                                //  images.add(split);
                                //  playerModel.setStatus("");


                                birdList.add(playerModel);

                            }
                            varaadapter myAdapter=new varaadapter(context,R.layout.grid_view_items,birdList);
                            simpleList.setAdapter(myAdapter);



                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(viewvariant.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("device_id",device_id);
                //  Log.d("name","mm"+device_id);
                params.put("pdt_id",sessionManager.getPdtaddvar());
                Log.d("owener","mm"+sessionManager.getPdtaddvar());



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
        startActivity(new Intent(viewvariant.this,viewproduct.class));

    }
}
