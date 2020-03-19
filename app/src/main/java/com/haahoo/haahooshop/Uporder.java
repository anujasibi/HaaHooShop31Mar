package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class Uporder extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView imageView3;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uporder);
        intent = getIntent();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Today's Orders");
        adapter.addFragment(new Tab2Fragment(), "Last Week");
       // adapter.addFragment(new Tab3Fragment(), "Past Orders");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (intent.hasExtra("fromactivity")) {
                  Uporder.super.onBackPressed();
                }
                if (!(intent.hasExtra("fromactivity"))) {
                    startActivity(new Intent(Uporder.this, ordman.class));

                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (intent.hasExtra("fromactivity")) {
            super.onBackPressed();
        }
        if (!(intent.hasExtra("fromactivity"))) {

            startActivity(new Intent(Uporder.this, ordman.class));
        }
    }
}
