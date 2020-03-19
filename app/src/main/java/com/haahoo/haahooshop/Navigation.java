package com.haahoo.haahooshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    Context context=this;
    ActionBar actionBar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView status;
    SessionManager sessionManager;
    public String shopname,location,gstno,catgory,owner,edit,em;
    Activity activity = this;



    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    boolean doubleBackToExitPressedOnce = false;
    private Handler mHandler;

    private String URLline = Global.BASE_URL+"api_shop_app/shop_details_show/";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Switch switchTop;
    CardView carda,cardb,cardc,cardd,carde,cardf,cardg,cardh,cardi,cardj,cardk,cardl,cardm,cardn,card1,card2,card3;
    TextView ear,sub,ord;
    Switch simpleSwitch;
    private String statu;
    TextView trail;
    public String url=Global.BASE_URL+"api_shop_app/shop_logout/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);



        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        notif();


        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sessionManager=new SessionManager(this);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawer,R.string.openDrawer,R.string.closeDrawer);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //actionBarDrawerToggle.getDrawerArrowDrawable().setColor(R.color.blu);
        actionBar=getSupportActionBar();
        actionBar.setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blu)));
        //   actionBar.setTitle(Html.fromHtml("<font color='#1AAEF4'>Name</font>"));
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);

        simpleSwitch = (Switch) navHeader.findViewById(R.id.simpleSwitch);
        simpleSwitch.setOnCheckedChangeListener(this);

        ear=findViewById(R.id.ear);
        sub=findViewById(R.id.sub);
        ord=findViewById(R.id.ord);
        trail=navHeader.findViewById(R.id.traile);

        carda=findViewById(R.id.carda);
        cardb=findViewById(R.id.cardb);
        cardc=findViewById(R.id.cardc);
      //  cardd=findViewById(R.id.cardd);
     //   carde=findViewById(R.id.carde);
        cardf=findViewById(R.id.cardf);
     //   cardg=findViewById(R.id.cardg);
   //     cardh=findViewById(R.id.cardh);
    //    cardi=findViewById(R.id.cardi);
    //    cardj=findViewById(R.id.cardj);
     //   cardk=findViewById(R.id.cardk);
    //    cardl=findViewById(R.id.cardl);
        cardn=findViewById(R.id.cardn);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);
        cardm=findViewById(R.id.cardm);


        carda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ProdMan.class);
                startActivity(intent);
            }
        });
        cardb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,BranchManagement.class);
                startActivity(intent);
            }
        });
        cardc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,profile.class);
                startActivity(intent);
            }
        });
       /* cardd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,viewproduct.class);
                startActivity(intent);
            }
        });*/
     /*   carde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,viewemployee.class);
                startActivity(intent);
            }
        });*/
        cardf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ordman.class);
                startActivity(intent);
            }
        });
       /* cardg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ViewBranches.class);
                startActivity(intent);
            }
        });
        cardh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,viewbran.class);
                startActivity(intent);
            }
        });
        cardi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Uporder.class);
                startActivity(intent);
            }
        });
        cardj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,orderhistory.class);
                startActivity(intent);
            }
        });
        cardk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Subscriptionlist.class);
                startActivity(intent);
            }
        });
        cardl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,cancelsub.class);
                startActivity(intent);
            }
        });*/
        cardn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Earnings.class);
                startActivity(intent);
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Earnings.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Uporder.class);
                intent.putExtra("fromactivity","navigation");
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Subscriptionlist.class);
                startActivity(intent);
            }
        });
        cardm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,review.class);
                startActivity(intent);
            }
        });

        // imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        //    imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources



        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();


    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
      txtName.setText(shopname);
      txtWebsite.setText(em);

        // loading header background image
      /*  Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
*/
        // showing dot next to notifications label
        // navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */


    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void submituser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");

                            JSONArray jsonArray=jsonObject.optJSONArray("data");
                            JSONObject jsonObject1 = jsonArray.optJSONObject(0);

                             shopname=jsonObject1.optString("name");
                             em=jsonObject1.optString("email");



                            Log.d("code","mm"+status);
                            if(status.equals("200")){

                                //Toast.makeText(profile.this, "Successful", Toast.LENGTH_LONG).show();
                                // Intent intent = new Intent(pasteaddress.this, ordersummary.class);
                                // startActivity(intent);
                            }
                            else{

                                Toast.makeText(Navigation.this, "Failed."+ot, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Navigation.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        startActivity(new Intent(Navigation.this, Navigation.class));
                        break;
                    case R.id.nav_movies:
                        //startActivity(new Intent(MainActivity.this,TripHistory.class));
                        startActivity(new Intent(Navigation.this, profile.class));
                        break;
                    case R.id.nav_photos:
                        startActivity(new Intent(Navigation.this, viewbankaccounts.class));
                         break;
                    case R.id.nav_del:
                        startActivity(new Intent(Navigation.this,viewdeliveryplan.class));
                        break;

                    case R.id.nav_notifications:
                        startActivity(new Intent(Navigation.this,Earnings.class));
                        break;
                    case R.id.nav_settings:
                        Intent intent=new Intent(context,Uporder.class);
                        intent.putExtra("fromactivity","navigation");
                        startActivity(intent);
                        break;

                    case R.id.subsc:
                        startActivity(new Intent(Navigation.this,Subscriptionlist.class));
                        break;

                    case R.id.change:
                        startActivity(new Intent(Navigation.this,changepassword.class));
                        break;

                    case R.id.pay:
                        startActivity(new Intent(Navigation.this,Payment.class));
                        break;


                    case R.id.qrr:
                        startActivity(new Intent(Navigation.this,qrcode.class));
                        break;

                    case R.id.cod:
                        startActivity(new Intent(Navigation.this,codlisting.class));
                        break;

                    case R.id.update:
                        startActivity(new Intent(Navigation.this,RealtimeLocation.class));
                        break;

                    case R.id.editc:
                        startActivity(new Intent(Navigation.this,chooseeditcat.class));
                        break;

                    case R.id.nav_privacy_policy:
                        logout();
                        break;


                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                //loadHomeFragment();

                return true;
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
// finish();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
// System.exit(1);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                //  CURRENT_TAG = TAG_HOME;
                //  loadHomeFragment();
                return;
            }
        }

        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
       // Toast.makeText(MainUI.this,"Press again to exit",Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
//        getMenuInflater().inflate(R.menu.main, menu);
//        final MenuItem item = (MenuItem) menu.findItem(R.id.switchT);
//        item.setActionView(R.layout.toggle_top_button);
//        status = (TextView) findViewById(R.id.ondt);
//        switchTop = item.getActionView().findViewById(R.id.switchTop);
//        if (Global.flag){
////            status = (TextView) findViewById(R.id.ondt);
////            status.setText("online");
//            switchTop.setChecked(true);
//        }
//        if (!Global.flag){
////            status = (TextView) findViewById(R.id.ondt);
////            status.setText("offline");
//            switchTop.setChecked(false);
//        }
//
//
//        switchTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//
//
//                if (isChecked) {
//
//                    status = findViewById(R.id.ondt);
//                    status.setText("online");
//                    sessionManager.setID(status.getText().toString());
//                    //sessionManager.setCard(true);
//                    callonline();
//
//
//
//                } if (!isChecked){
//
//
//
//                    status = findViewById(R.id.ondt);
//                    status.setText("offline");
//                    Toast.makeText(MainUI.this,"Please be online to get the request",Toast.LENGTH_SHORT).show();
//                    sessionManager.setID(status.getText().toString());
//                    //sessionManager.setCard(false);
//                    callonline();
//
//
//
//
//
//                }
//            }
//        });
//        if (navItemIndex == 0) {
//            getMenuInflater().inflate(R.menu.main, menu);
//        }
//
//        // when fragment is notifications, load the menu created for notifications
//        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return  true;
        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }*/

        // user is in notifications fragment
        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }

        // user is in notifications fragment
        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    // show or hide the fab

//    private void callonline(){
//        Toast.makeText(MainUI.this,"",Toast.LENGTH_SHORT).show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //  dialog.dismiss();
//                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
//                        //parseData(response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String ot = jsonObject.optString("message");
//                            String status1=jsonObject.optString("code");
//                            String token=jsonObject.optString("Token");
//
//
//
//
//
//                            Log.d("otp","mm"+token);
//                            Log.d("code","mm"+status1);
//                            if(status1.equals("200")){
//                                // Toast.makeText(MainUI.this, "Successful", Toast.LENGTH_LONG).show();
////                                Intent intent = new Intent(Login.this, MainUI.class);
////                                startActivity(intent);
//                                if (status.getText().toString().equals("offline")){
//                                    switchTop.setChecked(false);
//                                    sessionManager.setCard(false);
//                                    Global.flag = false;
//                                    status.setText("offline");
//
//                                    startActivity(new Intent(MainUI.this,MainUI.class));
//                                    Toast.makeText(MainUI.this,"Please be online to get the request",Toast.LENGTH_SHORT).show();
//                                }
//                                if (status.getText().toString().equals("online")){
//                                    switchTop.setChecked(true);
//                                    sessionManager.setCard(true);
//                                    Global.flag = true;
//                                    status.setText("online");
//                                    startActivity(new Intent(MainUI.this,MainUI.class));
//                                }
//
//                            }
//                            else{
//                                Toast.makeText(MainUI.this, "Failed."+ot, Toast.LENGTH_LONG).show();
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        //   Log.d("response","hhh"+response);
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainUI.this,error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("online_status",status.getText().toString());
//
//
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("Authorization","Token "+sessionManager.getTokens());
//                Log.d("Tokenccccc","mm"+sessionManager.getTokens());
//                return params;
//
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//
//
//    }
private void notif(){
    RequestQueue queue = Volley.newRequestQueue(Navigation.this);

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
                        String data=obj.optString("total_count");
                        String pay=obj.optString("payment_status");
                        if(pay.equals("0")){
                            trail.setText("Trial Mode");

                        }
                        if(pay.equals("1")){
                            navigationView = (NavigationView) findViewById(R.id.nav_view);
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.pay).setVisible(false);
                        }
                        String subg=obj.optString("sub_count");
                        sub.setText(subg);
                        String order=obj.optString("vir_count");
                        ord.setText(order);
                        String Earnings=obj.optString("Total_earn");
                        ear.setText("₹ "+Earnings);
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
            Toast.makeText(Navigation.this,"Internal Server Error",Toast.LENGTH_LONG).show();


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


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (simpleSwitch.isChecked()) {
            simpleSwitch.setText("Online");
            statu ="1";
           // statusSwitch1 = simpleSwitch.getTextOn().toString();
            online();
            //    Toast.makeText(getApplicationContext(), simpleSwitch.getText() , Toast.LENGTH_LONG).show();


        } else {

            simpleSwitch.setText("Offline");
            statu ="0";
          //  statusSwitch1 = simpleSwitch.getTextOn().toString();
            online();
            // Toast.makeText(getApplicationContext(), simpleSwitch.getText() , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void online(){
        RequestQueue queue=Volley.newRequestQueue(Navigation.this);

        String url=Global.BASE_URL+"api_shop_app/shop_status_update/";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String ot = jsonObject.optString("message");
                    String status=jsonObject.optString("code");
                    String token=jsonObject.optString("Token");
                    //    sessionManager.setTokens(token);




                    Log.d("otp","mm"+token);
                    Log.d("code","mm"+status);
                    if(status.equals("200")){
                        //    Toast.makeText(Navigation.this, "Successful", Toast.LENGTH_LONG).show();

                    }
                    else{
                        //   Toast.makeText(Navigation.this, "Failed."+ot, Toast.LENGTH_LONG).show();


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // dialog.dismiss();
                Toast.makeText(Navigation.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        })

        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("mode",statu);

                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void logout(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response","mmm"+response);
                    String code=jsonObject.getString("code");

                    if(code.equals("200")){
                        Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
                        sessionManager.setTokens("");
                        startActivity(new Intent(Navigation.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String,String>getHeaders(){
                Map<String,String>params=new HashMap<>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
