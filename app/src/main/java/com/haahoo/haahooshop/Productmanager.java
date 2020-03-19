package com.haahoo.haahooshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.circlemenu.CircleMenuView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Productmanager extends AppCompatActivity {
    Activity activity=this;
    TextView upor,vioh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productmanager);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        ImageView imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Productmanager.this,Navigation.class));
            }
        });

        upor=findViewById(R.id.upor);
        vioh=findViewById(R.id.vioh);

        upor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Productmanager.this,choosepdtcategory.class));
            }
        });
        vioh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Productmanager.this,viewproduct.class));
            }
        });

        final CircleMenuView menu = findViewById(R.id.circle_menu);


        menu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationStart");
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationEnd");
            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationStart");
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationEnd");
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationStart| index: " + index);
            }


            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd| index: " + index);
                if(index==0){
                   // Toast.makeText(Productmanager.this,"You choosed to add product",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Productmanager.this,choosepdtcategory.class));
                }
                if(index==1){
                    Toast.makeText(Productmanager.this,"Coming Soon",Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(Productmanager.this,cloudproductcategory.class));
                }
                if(index==3){
                   // Toast.makeText(Productmanager.this,"You choosed to view product",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Productmanager.this,viewproduct.class));
                }
                if(index==2){
                    Toast.makeText(Productmanager.this,"Coming Soon",Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(Productmanager.this,cloudproductcategory.class));
                }
            }
//
//            @Override
//            public boolean onButtonLongClick(@NonNull CircleMenuView view, int index) {
//                Log.d("D", "onButtonLongClick| index: " + index);
//                return true;
//            }
//
//            @Override
//            public void onButtonLongClickAnimationStart(@NonNull CircleMenuView view, int index) {
//                Log.d("D", "onButtonLongClickAnimationStart| index: " + index);
//            }
//
//            @Override
//            public void onButtonLongClickAnimationEnd(@NonNull CircleMenuView view, int index) {
//                Log.d("D", "onButtonLongClickAnimationEnd| index: " + index);
//            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Productmanager.this,Navigation.class));
    }
}