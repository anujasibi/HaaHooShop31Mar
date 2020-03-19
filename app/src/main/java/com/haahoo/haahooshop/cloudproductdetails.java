package com.haahoo.haahooshop;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class cloudproductdetails extends AppCompatActivity {
    ImageView imageView,io;
    ViewPager viewPager;
    Context context = this;
    TextView shopname,location,gstno,catgory,owner,image;
    String id = "null";
    SessionManager sessionManager;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    String filePath;
    private Uri uri;
    List<SlideModel>array=new ArrayList<>();
    private ImagePagerAdapter imagePagerAdapter;
    ImageSlider imageSlider;
    String[] imk;
    CardView edit;
    Activity activity=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudproductdetails);


        Bundle bundl = getIntent().getExtras();
        final String[] imagt = bundl.getStringArray("imagea");
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        //  imageView=findViewById(R.id.img);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        imageSlider = findViewById(R.id.img);


        shopname = findViewById(R.id.sname);
        location = findViewById(R.id.location);
        owner = findViewById(R.id.owner);
        gstno = findViewById(R.id.gstno);
        catgory = findViewById(R.id.category);
        io = findViewById(R.id.io);
        edit=findViewById(R.id.edit);

       /* edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,addprod.class));
            }
        });*/

        sessionManager = new SessionManager(this);

        io.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cloudproductdetails.this, cloudproductview.class));
            }
        });

        Bundle bundle = getIntent().getExtras();

//Extract the data…
        final String pname = bundle.getString("pname");
        final String image = bundle.getString("image");
        final String[] imag = bundle.getStringArray("imagea");
        Global.imj = imag;
        //  Picasso.with(context).load(image).into(imageView);
        //   Picasso.get().load(image).into(imageView);
        final String price = bundle.getString("price");
        final String stock = bundle.getString("stock");
        final String discount = bundle.getString("discount");
        final String des = bundle.getString("des");
        final String catid = bundle.getString("catid");
        final String display = bundle.getString("display");
        final String memory = bundle.getString("memory");

        assert imag != null;
        for (int i = 0; i < imag.length; i++) {
            String split = imag[i].replace("[", "").replace("]", "").trim();
            array.add(new SlideModel(Global.BASE_URL + split));
            Log.d("imagertttty", "mm" + split);

        }


        imageSlider.setImageList(array, false);

        id = bundle.getString("id");
        Log.d("mnjbkjbkbj", "hjghjghg" + id);
        //  final String ema=bundle.getString("email");

        shopname.setText(pname);
        location.setText(price);
        owner.setText(discount);
        gstno.setText(stock);
        catgory.setText(des);

       /* edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,edit_product.class);
                intent.putExtra("pname",pname);
                intent.putExtra("image",image);
                intent.putExtra("price",price);
                intent.putExtra("stock",stock);
                intent.putExtra("discount",discount);
                intent.putExtra("desc",des);
                intent.putExtra("id",id);
                intent.putExtra("catid",catid);
                intent.putExtra("display",display);
                intent.putExtra("memory",memory);

                //   intent.putExtra("email",ema);
                startActivity(intent);

                //startActivity(new Intent(viewpdtdetails.this,edit_product.class));
            }
        });

    }
   */

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(cloudproductdetails.this,cloudproductview.class));
    }
}
