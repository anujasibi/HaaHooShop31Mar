package com.haahoo.haahooshop;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AddShopBranch extends AppCompatActivity {

    EditText shopname, gst, phone, email, distance, address, password;
    ImageView imageView,imgp;
    Context context=this;
    TextView submit, show, hide;
    public String idsp;
    String files;
    Spinner spinner;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2,CGALLERY=3,CCAMERA=4;;
    String filePath;
    private Uri uri;
    ArrayList<String> areas = new ArrayList<String>();
    ArrayList<String> areasid = new ArrayList<String>();
  //  String URL = "https://testapi.creopedia.com/api_shop_app/list_shop_cat/ ";
 //   String URL = "https://haahoo.in/api_shop_app/list_shop_cat/ ";
    String URL = Global.BASE_URL+"api_shop_app/list_shop_cat/ ";
    SessionManager sessionManager;
    String device_id = null;
    public String source_lat,source_lng;
    ImageView imagen;
    private ProgressDialog dialog ;
    Activity activity = this;
    String emailPattern = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_branch);


        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(AddShopBranch.this,R.style.MyAlertDialogStyle);


        sessionManager = new SessionManager(this);
        imgp=findViewById(R.id.imgp);

        shopname = findViewById(R.id.shopname);
        gst = findViewById(R.id.gst);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        distance = findViewById(R.id.distance);
        address = findViewById(R.id.des);
        password = findViewById(R.id.password);
        imageView = findViewById(R.id.img);
        show = findViewById(R.id.show);
        hide = findViewById(R.id.hide);
        imagen=findViewById(R.id.imageView3);

        imgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialo();
            }
        });

        gst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (gst.getText().toString().matches(emailPattern) && gst.getText().toString().length() > 0)
                {
                    //Toast.makeText(getApplicationContext(),"valid gst no",Toast.LENGTH_SHORT).show();

                }
                if (!(gst.getText().toString().matches(emailPattern) && gst.getText().toString().length() > 0))
                {
                    //Toast.makeText(getApplicationContext(),"Invalid GST Number",Toast.LENGTH_SHORT).show();
                    gst.setError("Invalid GST Number");

                }

            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address.getText().toString(),
                        getApplicationContext(), new AddShopBranch.GeocoderHandler());
            }
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,BranchManagement.class));
            }
        });

        submit = findViewById(R.id.submit);
        device_id =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hide.setVisibility(View.VISIBLE);
                show.setVisibility(View.GONE);

            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hide.setVisibility(View.GONE);
                show.setVisibility(View.VISIBLE);
            }
        });

        spinner = findViewById(R.id.spinner);
        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                idsp = areasid.get(spinner.getSelectedItemPosition());
            //    Toast.makeText(getApplicationContext(), country, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Loading");
                dialog.show();
                submituser();
            }
        });


    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String lat,lonh;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    lat= bundle.getString("lat");
                    lonh=bundle.getString("long");
                    Log.d("source","mm"+lat);
                    Log.d("longitude","mm"+lonh);
                    source_lat = lat;
                    source_lng = lonh;
                    Toast.makeText(AddShopBranch.this,source_lat+source_lng,Toast.LENGTH_SHORT).show();
                    //  sessionManager.setDestLong(lonh);
                    break;
                default:
                    locationAddress = null;
            }
            //  latLongTV.setText(locationAddress);
            Log.d("locationaddress","mm"+locationAddress);
        }

    }
    private void showPictureDialo() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallar();
                                break;
                            case 1:
                                takePhotoFromCamer();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }



    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    public void choosePhotoFromGallar() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, CGALLERY);
    }

    private void takePhotoFromCamer() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CCAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                uri = data.getData();
                filePath = getRealPathFromURIPath(uri, AddShopBranch.this);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap = getResizedBitmap(bitmap, 400);
                    String path = saveImage(bitmap);
                    Toast.makeText(AddShopBranch.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageView.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddShopBranch.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(AddShopBranch.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
            filePath = (getRealPathFromURI(tempUri));
            Log.d("filepath", "mm" + filePath);
        }
        else if (requestCode == CCAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgp.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            //   Toast.makeText(addshopim.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
            files = (getRealPathFromURI(tempUri));
            Log.d("filepath", "mm" + files);
        }
        else if (requestCode == CGALLERY) {
            Uri contentURI = data.getData();
            uri = data.getData();
            files = getRealPathFromURIPath(uri, AddShopBranch.this);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                bitmap = getResizedBitmap(bitmap, 400);
                String path = saveImage(bitmap);
                //   Toast.makeText(addshopim.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                imgp.setImageBitmap(bitmap);



            } catch (IOException e) {
                e.printStackTrace();
                //    Toast.makeText(addshopim.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        }

        }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                         //   Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(contentURI, proj, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void loadSpinnerData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("ressssssssss","mm"+response);
                    JSONObject jsonObject = new JSONObject(response);
                    areas.add("Please Select Your Category");
                    areasid.add("0");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String country = jsonObject1.getString("name");
                        String id = jsonObject1.getString("id");
                        areas.add(country);
                        areasid.add(id);

                    }

                    spinner.setAdapter(new ArrayAdapter<String>(AddShopBranch.this, android.R.layout.simple_spinner_dropdown_item, areas));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void submituser() {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UploadAPI uploadAPIs = retrofit.create(UploadAPI.class);

        if (filePath == null) {
            Toast.makeText(AddShopBranch.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
        }
        if (filePath != null) {
            File immm = new File(filePath);
            File im=new File(files);
            Log.d("mmmmmmm", "mmm" + immm.length());
            Log.d("mmmmmmm", "mmm" + im.length());
            // Create a request body with file and image media type


            RequestBody photob = RequestBody.create(MediaType.parse("image/*"), immm);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("shop_image", immm.getName(), photob);
            Log.d("image", "mm" + part1);
            Log.d("image", "mm" + immm.getName());
            RequestBody photobv = RequestBody.create(MediaType.parse("image/*"), im);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part2 = MultipartBody.Part.createFormData("cover_image", im.getName(), photobv);
            Log.d("image", "mm" + part2);
            Log.d("image", "mm" + im.getName());


            //Create request body with text description and text media type
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), shopname.getText().toString());
            Log.d("nameee","mm"+shopname.getText().toString());
            RequestBody gst_no = RequestBody.create(MediaType.parse("text/plain"), gst.getText().toString());
            Log.d("nameee","mm"+gst.getText().toString());
            RequestBody phone_no = RequestBody.create(MediaType.parse("text/plain"), phone.getText().toString());
            Log.d("nameee","mm"+phone.getText().toString());
            RequestBody emails = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
            Log.d("nameee","mm"+email.getText().toString());
            RequestBody passwords = RequestBody.create(MediaType.parse("text/plain"), password.getText().toString());
            Log.d("nameee","mm"+password.getText().toString());
            RequestBody addresss = RequestBody.create(MediaType.parse("text/plain"), address.getText().toString());
            Log.d("nameee","mm"+address.getText().toString());
            RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), source_lat);
            Log.d("nameee","mm"+source_lat);
            RequestBody log = RequestBody.create(MediaType.parse("text/plain"), source_lng);
            Log.d("nameee","mm"+source_lng);
            RequestBody distances = RequestBody.create(MediaType.parse("text/plain"), distance.getText().toString());
            Log.d("nameee","mm"+distance.getText().toString());
            RequestBody category = RequestBody.create(MediaType.parse("text/plain"), idsp);
            Log.d("nameee","mm"+idsp);
            RequestBody device = RequestBody.create(MediaType.parse("text/plain"), device_id);
            Log.d("nameee","mm"+device_id);

            //
            Call call = uploadAPIs.uploadI("Token " + sessionManager.getTokens(),part1,part2, name, gst_no, emails, passwords, phone_no, addresss,lat,log,category,distances,device);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, retrofit2.Response response) {
                    dialog.dismiss();
                    Toast.makeText(context, "Successfully updated" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,Navigation.class));
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    dialog.dismiss();
                }
            });


        }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,BranchManagement.class));
    }
}
