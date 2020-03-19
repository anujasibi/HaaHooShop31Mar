package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.haahoo.haahooshop.utils.Global;
import com.squareup.picasso.Picasso;

public class Executive extends AppCompatActivity {
    Activity activity=this;
    ImageView imageView;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive);
        Window window = activity.getWindow();

        imageView=findViewById(R.id.img);

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        Picasso.get().load(Global.BASE_URL+"media/files/events_add/CustomerService.jpg").into(imageView);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MainActivity.class));
    }
}
