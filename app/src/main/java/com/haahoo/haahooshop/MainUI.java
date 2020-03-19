package com.haahoo.haahooshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainUI extends AppCompatActivity {

    ImageView imageView;
    boolean doubleBackToExitPressedOnce = false;
    CardView cardView;
    Activity activity = this;
    ImageView logout;
    SessionManager sessionManager;
    TextView mBadge;
    ImageView not;
    public String data,order,sub;
    public int num;

    private List<CardRecyclerViewItem> carItemList = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

      //  imageView=findViewById(R.id.);
       sessionManager = new SessionManager(this);
       mBadge=findViewById(R.id.badge);
       not=findViewById(R.id.noti);

       not.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(MainUI.this,notification.class);
               intent.putExtra("subcount",sub);
               intent.putExtra("order",order);
               startActivity(intent);

           }
       });

        cardView=findViewById(R.id.card);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        notif();

       // num= Integer.parseInt(data);


        //mBadge.setNumber(num);





        initializeCarItemList();

        // Create the recyclerview.
        RecyclerView carRecyclerView = (RecyclerView)findViewById(R.id.card_view_recycler_list);
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        // Set layout manager.
        carRecyclerView.setLayoutManager(gridLayoutManager);

        // Create car recycler view data adapter with car item list.
        RecyclerViewDataAdapter carDataAdapter = new RecyclerViewDataAdapter(carItemList);
        // Set data adapter.
        carRecyclerView.setAdapter(carDataAdapter);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setTokens(null);
                startActivity(new Intent(MainUI.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Successfully logged out",Toast.LENGTH_SHORT).show();
                finish();


            }
        });






    }

    /* Initialise car items in list. */
    private void initializeCarItemList()
    {
        if(carItemList == null)
        {
            carItemList = new ArrayList<CardRecyclerViewItem>();
            carItemList.add(new CardRecyclerViewItem("Add Products", R.drawable.add));
            carItemList.add(new CardRecyclerViewItem("View/Modify Products", R.drawable.eye));
            carItemList.add(new CardRecyclerViewItem("View/Modify Profile", R.drawable.person));
            carItemList.add(new CardRecyclerViewItem("Add Your Employee", R.drawable.groupadd));
            carItemList.add(new CardRecyclerViewItem("View Employee Details", R.drawable.viewemp));
            carItemList.add(new CardRecyclerViewItem("Add Your Shop Branches", R.drawable.shopbranch));
            carItemList.add(new CardRecyclerViewItem("My Branches", R.drawable.branch));
            carItemList.add(new CardRecyclerViewItem("View Branch Products", R.drawable.view));
            carItemList.add(new CardRecyclerViewItem("View Upcoming Orders", R.drawable.ordersu));
            carItemList.add(new CardRecyclerViewItem("View Order History", R.drawable.orderhistory));
            carItemList.add(new CardRecyclerViewItem("View Subscription List", R.drawable.sublist));
            carItemList.add(new CardRecyclerViewItem("Cancelled Subscription List", R.drawable.cancel));
            carItemList.add(new CardRecyclerViewItem("Earnings", R.drawable.earnings));
            carItemList.add(new CardRecyclerViewItem("About Us", R.drawable.about));




        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainUI.this,"Press again to exit",Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void notif(){
        RequestQueue queue = Volley.newRequestQueue(MainUI.this);

        //this is the url where you want to send the request

        String url = Global.BASE_URL+"virtual_order_management/shop_order_count/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // dialog.dismiss();

                        try {

                            JSONObject obj = new JSONObject(response);

                            // amount.setText(obj.optString("total"));
                            String total=obj.optString("total");
                            data=obj.optString("total_count");
                            sub=obj.optString("sub_count");
                            order=obj.optString("vir_count");
                            mBadge.setText(data);
                            Log.d("notify","mm"+response);



                        } catch (JSONException e) {
                           // dialog.dismiss();
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // dialog.dismiss();
                Toast.makeText(MainUI.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

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


}