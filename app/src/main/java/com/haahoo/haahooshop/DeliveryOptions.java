package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import java.util.Calendar;

public class DeliveryOptions extends AppCompatActivity  implements
        AdapterView.OnItemSelectedListener {
    String[] country = { "Please Choose Cancellation Period", "No Cancel", "0-10 min", "10-20 min", "20-30 min","30-40 min","40-50 min","1 hour","Always"};

    private RadioGroup radioSexGroup;
    private RadioButton one,two,three,four;
    EditText etChooseTime;
    LinearLayout ln;
    String amPm;
    DatePickerDialog picker;
    EditText eText;
    EditText datt;
    SessionManager sessionManager;
    TextView save;
    Activity activity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_options);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        one=findViewById(R.id.radioMale);
        two=findViewById(R.id.radioFemale);
        sessionManager=new SessionManager(this);
        three=findViewById(R.id.radioFe);
        four=findViewById(R.id.radioF);
        etChooseTime=findViewById(R.id.etChooseTime);
        ln=findViewById(R.id.ln);
        datt=findViewById(R.id.datt);

        save=findViewById(R.id.save);

        sessionManager.setdelvry_tym("");
        sessionManager.setdelvry_date("");
        sessionManager.setcancel_tym("");

        etChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(DeliveryOptions.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        etChooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                        sessionManager.setdelvry_tym(etChooseTime.getText().toString());

                    }
                }, 0, 0, false);
                timePickerDialog.show();

            }

        });

        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(DeliveryOptions.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                sessionManager.setdelvry_date(eText.getText().toString());
                            }
                        }, year, month, day);

                picker.show();
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datt.setVisibility(View.GONE);
                ln.setVisibility(View.GONE);
                etChooseTime.setVisibility(View.GONE);
                eText.setVisibility(View.GONE);
                sessionManager.setdelvry_tym("");
                sessionManager.setdelvry_date("");
                sessionManager.setcancel_tym("");
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datt.setVisibility(View.GONE);
                ln.setVisibility(View.GONE);
                etChooseTime.setVisibility(View.GONE);
                eText.setVisibility(View.GONE);
                sessionManager.setdelvry_tym("");
                sessionManager.setdelvry_date("");
                sessionManager.setcancel_tym("");
            }
        });




        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datt.setVisibility(View.VISIBLE);
                ln.setVisibility(View.VISIBLE);
                etChooseTime.setVisibility(View.GONE);
                eText.setVisibility(View.GONE);
                sessionManager.setdelvry_tym("");
                sessionManager.setdelvry_date("");


            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etChooseTime.setVisibility(View.VISIBLE);
                eText.setVisibility(View.VISIBLE);
                datt.setVisibility(View.GONE);
                ln.setVisibility(View.GONE);
                sessionManager.setcancel_tym("");
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                        int selectedId = radioSexGroup.getCheckedRadioButtonId();
                        one = (RadioButton) findViewById(selectedId);
                        sessionManager.setradio(one.getText().toString());
                        sessionManager.setpick_tym(datt.getText().toString());
                        startActivity(new Intent(DeliveryOptions.this, subscription.class));
                        Log.d("asdfghj","mmmmm"+sessionManager.getradio());
                        Log.d("asdfghj","mmmmm"+sessionManager.getdelvry_date());
                        Log.d("asdfghj","mmmmm"+sessionManager.getdelvry_tym());
                        Log.d("asdfghj","mmmmm"+sessionManager.getcancel_tym());
                        Log.d("asdfghj","mmmmm"+sessionManager.getpick_tym());


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sessionManager.setcancel_tym(country[position].toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
