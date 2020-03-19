package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.haahoo.haahooshop.utils.SessionManager;

import java.util.ArrayList;

public class notification extends AppCompatActivity {
    ArrayList<NotificationPojo> birdList=new ArrayList<>();
    Activity activity = this;
    Context context=this;
    SessionManager sessionManager;
    RecyclerView listView;
    ImageView back;
    ArrayList<NotificationPojo> rowItems;
    private ProgressDialog dialog ;
    TextView not,noti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        dialog=new ProgressDialog(notification.this,R.style.MyAlertDialogStyle);
  //      dialog.setMessage("Loading");
   //     dialog.show();

        sessionManager=new SessionManager(this);

        back=findViewById(R.id.back);





        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        Bundle bundle = getIntent().getExtras();

        String shop = bundle.getString("subcount");
        String own=bundle.getString("order");

        not=findViewById(R.id.title);
        noti=findViewById(R.id.titles);

        not.setText("You have received "+own+" Orders");
        noti.setText("You have received "+shop+" Subscriptions");

        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Uporder.class));
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Subscriptionlist.class));
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MainUI.class));
            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MainUI.class));
    }
}
