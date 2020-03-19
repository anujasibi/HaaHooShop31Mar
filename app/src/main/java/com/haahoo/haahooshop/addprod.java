package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haahoo.haahooshop.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class addprod extends AppCompatActivity {
    TextInputEditText name,address,pincode,stock,incent;
    EditText des;
    SessionManager sessionManager;
    Context context1=this;
    TextView save;
    ImageView imageView3;
    private ProgressDialog dialog ;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprod);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(addprod.this,R.style.MyAlertDialogStyle);

        sessionManager = new SessionManager(context1);

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        pincode=findViewById(R.id.pincode);
        stock=findViewById(R.id.stock);
        save = findViewById(R.id.save);
        incent=findViewById(R.id.incen);
        imageView3=findViewById(R.id.imageView3);




        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProductSearch.class));
            }
        });

        des=findViewById(R.id.des);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(pincode.getText().toString())>=Integer.parseInt(name.getText().toString())){
                    pincode.setError("Discount amount should be less than Orginal Price");
                }

                if(name.getText().length()==0||address.getText().length()==0||pincode.getText().length()==0||stock.getText().length()==0|des.getText().length()==0){
                    Toast.makeText(addprod.this,"All are fields are required",Toast.LENGTH_SHORT).show();
                }
                if(!(name.getText().length()==0||address.getText().length()==0||pincode.getText().length()==0||stock.getText().length()==0|des.getText().length()==0)) {

                    sessionManager.setprice(name.getText().toString());
                    Log.d("name", "mm" + sessionManager.getprice());
                    sessionManager.setret(address.getText().toString());
                    Log.d("name", "mm" + sessionManager.getret());
                    sessionManager.setdis(pincode.getText().toString());
                    Log.d("name", "mm" + sessionManager.getdis());
                    sessionManager.setstock(stock.getText().toString());
                    Log.d("name", "mm" + sessionManager.getstock());
                    sessionManager.setdes(des.getText().toString());
                    Log.d("name", "mm" + sessionManager.getdes());
                    sessionManager.setincent(incent.getText().toString());
                    Log.d("name", "mm" + sessionManager.getincent());
                    startActivity(new Intent(addprod.this, finaladd.class));
                }
            }
        });






    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ProductSearch.class));
    }
}
