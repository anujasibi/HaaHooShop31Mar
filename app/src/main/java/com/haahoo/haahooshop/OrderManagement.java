package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sa90.materialarcmenu.ArcMenu;
import com.sa90.materialarcmenu.StateChangeListener;


public class OrderManagement extends AppCompatActivity {
    ArcMenu arcMenu;
    Activity activity=this;
    ImageView imageView3;
    TextView upor,vioh,sub,cans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        upor=findViewById(R.id.upor);
        vioh=findViewById(R.id.vioh);
        sub=findViewById(R.id.sub);
        cans=findViewById(R.id.cans);


        upor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderManagement.this, Uporder.class);
                startActivity(intent);
            }
        });
        vioh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderManagement.this, orderhistory.class);
                startActivity(intent);
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderManagement.this, Subscriptionlist.class);
                startActivity(intent);
            }
        });
        cans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderManagement.this, cancelsub.class);
                startActivity(intent);
            }
        });

        imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderManagement.this,Navigation.class));
            }
        });


        arcMenu = findViewById(R.id.arcMenu);
    //    arcMenu.setRadius(getResources().getDimension(R.dimen.radius));

        arcMenu.isMenuOpened();


        arcMenu.setStateChangeListener(new StateChangeListener() {
            @Override
            public void onMenuOpened() {
               // Snackbar.make(arcMenu, "Menu Opened", Snackbar.LENGTH_SHORT).show();

                if(arcMenu.mDrawable instanceof Animatable)
                    ((Animatable) arcMenu.mDrawable).start();
            }

            @Override
            public void onMenuClosed() {
             //   Snackbar.make(arcMenu, "Menu Closed", Snackbar.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.fab1).setOnClickListener(subMenuClickListener);
        findViewById(R.id.tvNext).setOnClickListener(mNextClickListener);
        findViewById(R.id.tvAnimationDemo).setOnClickListener(mAnimationTimeDemoClickListener);
        findViewById(R.id.tvTopPlacement).setOnClickListener(mTopPlacementClickListener);
    }

    private View.OnClickListener subMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(OrderManagement.this, Uporder.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mNextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(OrderManagement.this, orderhistory.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mAnimationTimeDemoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(OrderManagement.this, Subscriptionlist.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mTopPlacementClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(OrderManagement.this, cancelsub.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OrderManagement.this,Navigation.class));
    }
}
