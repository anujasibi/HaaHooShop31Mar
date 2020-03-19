package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naylalabs.semiradialmenu.MenuItemView;
import com.naylalabs.semiradialmenu.RadialMenuView;
import com.naylalabs.semiradialmenu.Utils;

import java.util.ArrayList;

import android.os.Bundle;

public class BranchManagement extends AppCompatActivity implements RadialMenuView.RadialMenuListener {

    RadialMenuView radialMenuView;
    Button button;
    Activity activity=this;
    ImageView imageView3;
    TextView addb,adde,viewb,viewe,viewbp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_management);


        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BranchManagement.this,Navigation.class));
            }
        });

        addb=findViewById(R.id.addb);
        adde=findViewById(R.id.adde);
        viewb=findViewById(R.id.viewb);
        viewe=findViewById(R.id.viewe);
        viewbp=findViewById(R.id.viewbp);

        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BranchManagement.this,AddShopBranch.class));
            }
        });

        adde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BranchManagement.this,AddEmployee.class));
            }
        });

        viewb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BranchManagement.this,ViewBranches.class));
            }
        });

        viewe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BranchManagement.this,viewemployee.class));
            }
        });

        viewbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BranchManagement.this,viewbran.class));
            }
        });

        radialMenuView = findViewById(R.id.radial_menu_view);
        radialMenuView.show();
        button = findViewById(R.id.button);

        MenuItemView itemOne = new MenuItemView(this ,"Soru Sor",R.drawable.branch, R.color.blu);
        MenuItemView itemTwo = new MenuItemView(this,"Arkadaşlar",R.drawable.addemv, R.color.blu);
        MenuItemView itemThree = new MenuItemView(this,"Galeri", R.drawable.eye, R.color.blu);
        MenuItemView itemFour = new MenuItemView(this,"Naber", R.drawable.viewemp, R.color.blu);
        MenuItemView itemFive = new MenuItemView(this, "Selam", R.drawable.branpv, R.color.blu);
        ArrayList<MenuItemView> items = new ArrayList<>();
        items.add(itemOne);
        items.add(itemTwo);
        items.add(itemThree);
        items.add(itemFour);
        items.add(itemFive);
        radialMenuView.setListener(this).setMenuItems(items).setCenterView(button).setInnerCircle(true, R.color.white).setOffset(10).build();
    }

    public void showClose(View view) {
        radialMenuView.show();
    }

    @Override
    public void onItemClicked(int i) {

        if(i==0){
            Toast.makeText(this, "You clicked to add branch" , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BranchManagement.this,AddShopBranch.class));

        }
        if(i==1){
            Toast.makeText(this, "You clicked to add employee" , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BranchManagement.this,AddEmployee.class));

        }

        if(i==2){
            Toast.makeText(this, "You clicked to view branches" , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BranchManagement.this,ViewBranches.class));

        }
        if(i==3){
            Toast.makeText(this, "You clicked to view employee" , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BranchManagement.this,viewemployee.class));

        }
        if(i==4){
            Toast.makeText(this, "You clicked to view branch products" , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BranchManagement.this,viewbran.class));

        }



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BranchManagement.this,Navigation.class));
    }
}
