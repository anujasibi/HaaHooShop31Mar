package com.haahoo.haahooshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import java.util.ArrayList;

public class subscriptionmode extends AppCompatActivity {
    EditText shopname,des;
    TextView submit;
    SessionManager sessionManager;
    ArrayList<Subpojo> specpojos = new ArrayList<>();
    RecyclerView recyclerView;
    Subadapter specAdapter;
    Context context = this;
    String[] value = null;
    ImageView imageView3;
    Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptionmode);

        recyclerView = findViewById(R.id.list);

       // shopname=findViewById(R.id.shopname);
      //  des=findViewById(R.id.des);
        submit=findViewById(R.id.save);
        sessionManager=new SessionManager(this);
        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        sessionManager.setsubvalue("");
        imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,subscription.class));
            }
        });


        Subadapter upcomingAdapter=new Subadapter(Global.value, context);
        recyclerView.setAdapter(upcomingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.getsubvalue().length()==0){
                    Toast.makeText(context,"Please Add Your Plan",Toast.LENGTH_SHORT).show();
                }
                if (sessionManager.getsubvalue().length()!=0){
                    startActivity(new Intent(context,addimage.class));
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,subscription.class));
    }
}
