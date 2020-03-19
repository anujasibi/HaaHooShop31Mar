package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import com.haahoo.haahooshop.utils.SessionManager;

import java.util.ArrayList;

public class chooseimages extends AppCompatActivity {

    ImageView imageView;
    boolean doubleBackToExitPressedOnce = false;
    CardView cardView;

    ImageView logout;
    SessionManager sessionManager;
    ImageView back;

    Activity activity=this;

    String[]ne;
    public String image,pname,price,discount,descr,stock,email;
    private ProgressDialog dialog;
    GridView simpleList;
    ArrayList<Item> birdList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseimages);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.black));

        simpleList = (GridView) findViewById(R.id.card_view_recycler_list);


    }
}
