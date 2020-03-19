package com.haahoo.haahooshop;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.haahoo.haahooshop.utils.SessionManager;

public class productnew extends AppCompatActivity {
    private RadioGroup radioSexGroup;
    private RadioButton one,two,three;
    TextView save,res;
    SessionManager sessionManager;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productnew);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        save=findViewById(R.id.save);
        sessionManager=new SessionManager(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                one = (RadioButton) findViewById(selectedId);

                sessionManager.settype(one.getText().toString());

                     /*   Toast.makeText(finaladd.this,
                                one.getText(), Toast.LENGTH_SHORT).show();*/

                startActivity(new Intent(productnew.this, addproductnew.class));
            }
        });
    }
}
