package com.haahoo.haahooshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class addempnew extends AppCompatActivity {
    ImageView imh,imgp;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2,CGALLERY=3,CCAMERA=4;
    String filePath="";
    String files="";
    private Uri uri;
    SessionManager sessionManager;
    Context context=this;
    public String phone_no,email;
    TextView save;
    private ProgressDialog dialog ;
    Activity activity = this;
    private String URLline = Global.BASE_URL+"api_shop_app/shop_det_reg/";
    public String idf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addempnew);
        sessionManager=new SessionManager(this);
        imh=findViewById(R.id.img);
        imgp=findViewById(R.id.imgp);

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(addempnew.this,R.style.MyAlertDialogStyle);
        requestMultiplePermissions();

        Bundle bundle = getIntent().getExtras();
        idf=bundle.getString("id");
        Log.d("fghjk","mm"+idf);

        imh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        imgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialo();
            }
        });

        save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(addshopim.this, Payment.class);
                intent.putExtra("email",email);
                intent.putExtra("number",phone_no);
                startActivity(intent);*/
                if(filePath.equals("")||files.equals("")){
                    Toast.makeText(context,"Please Choose Employee Photo and also the Id Proof",Toast.LENGTH_SHORT).show();
                }
                if(!(filePath.equals("")||files.equals(""))){
                    startActivity(new Intent(context,Navigation.class));

                }

            }
        });




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
                filePath = getRealPathFromURIPath(uri, addempnew.this);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap = getResizedBitmap(bitmap, 400);
                    String path = saveImage(bitmap);
                    //   Toast.makeText(addshopim.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imh.setImageBitmap(bitmap);
                    uploadToServer(filePath);


                } catch (IOException e) {
                    e.printStackTrace();
                    //    Toast.makeText(addshopim.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }


        else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imh.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            //   Toast.makeText(addshopim.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
            filePath = (getRealPathFromURI(tempUri));
            uploadToServer(filePath);
            Log.d("filepath", "mm" + filePath);
        }
        else if (requestCode == CCAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgp.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            //   Toast.makeText(addshopim.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
            files = (getRealPathFromURI(tempUri));
            uploadToServe(files);
            Log.d("filepath", "mm" + files);
        }
        else if (requestCode == CGALLERY) {
            Uri contentURI = data.getData();
            uri = data.getData();
            files = getRealPathFromURIPath(uri, addempnew.this);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                bitmap = getResizedBitmap(bitmap, 400);
                String path = saveImage(bitmap);
                //   Toast.makeText(addshopim.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                imgp.setImageBitmap(bitmap);
                uploadToServe(files);


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
                            //     Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }



    private void uploadToServer(String filePath) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UploadImage uploadAPI = retrofit.create(UploadImage.class);

//        Log.d("url","mmm"+filePath);
        //Create a file object using file path
        if (filePath == null){
            Toast.makeText(addempnew.this,"Please Upload Image",Toast.LENGTH_SHORT).show();
        }
        if (filePath != null) {
            File immm = new File(filePath);
            Log.d("mmmmmmm", "mmm" + immm.length());
            // Create a request body with file and image media type


            RequestBody photob = RequestBody.create(MediaType.parse("image/*"), immm);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("photo", immm.getName(), photob);
            Log.d("image","mm"+part1);
            Log.d("image","mm"+immm.getName());
            RequestBody id1 = RequestBody.create(MediaType.parse("text/plain"),idf );



            //Create request body with text description and text media type
            //
            Call<ResponseBody> call = uploadAPI.uploadIp(part1,"Token "+ sessionManager.getTokens(),id1);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
                    Log.d("recyfvggbhh","mm"+response);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                }


            });
        }
    }

    private void uploadToServe(String files) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UploadImage uploadAPI = retrofit.create(UploadImage.class);

//        Log.d("url","mmm"+filePath);
        //Create a file object using file path
        if (files == null){
            Toast.makeText(addempnew.this,"Please Upload Image",Toast.LENGTH_SHORT).show();
        }
        if (files != null) {
            File immm = new File(files);
            Log.d("mmmmmmm", "mmm" + immm.length());
            // Create a request body with file and image media type


            RequestBody photob = RequestBody.create(MediaType.parse("image/*"), immm);
            RequestBody shop_id = RequestBody.create(MediaType.parse("text/plain"),idf);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("shop_image", immm.getName(), photob);
            Log.d("image","mm"+part1);
            Log.d("image","mm"+immm.getName());



            //Create request body with text description and text media type
            //
            Call<ResponseBody> call = uploadAPI.uploadIpp(part1,"Token "+ sessionManager.getTokens(),shop_id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
                    Log.d("recyfvggbhh","mm"+response);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                }


            });
        }

    }
}
