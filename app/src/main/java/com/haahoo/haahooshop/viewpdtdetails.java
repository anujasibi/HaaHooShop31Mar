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

public class viewpdtdetails extends AppCompatActivity {
    ImageView imageView,io;
    ViewPager viewPager;
    Context context = this;
    TextView shopname,location,gstno,catgory,owner,edit,view;
    CardView image;
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
    Activity activity=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        requestMultiplePermissions();
        Bundle bundl = getIntent().getExtras();
        final String[] imagt=bundl.getStringArray("imagea");
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

      //  imageView=findViewById(R.id.img);
        TextView textView3=(TextView)findViewById(R.id.textView3);
        imageSlider=findViewById(R.id.img);
        TextView textView4=(TextView)findViewById(R.id.textView4);

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,addoffer.class));
            }
        });





        image=findViewById(R.id.imgg);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // showPictureDialog();
               Intent intent=new Intent(context,viewimages.class);
               intent.putExtra("imagesa",imagt);
                intent.putExtra("id",id);
                Global.idd=id;
               startActivity(intent);


            }
        });
     //   view=findViewById(R.id.viewk);
        shopname=findViewById(R.id.sname);
        location=findViewById(R.id.location);
        owner=findViewById(R.id.owner);
        gstno=findViewById(R.id.gstno);
        catgory=findViewById(R.id.category);
        edit=findViewById(R.id.edit);
        io=findViewById(R.id.io);

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,viewvariant.class));
            }
        });*/

        sessionManager=new SessionManager(this);

        io.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewpdtdetails.this,viewproduct.class));
            }
        });

        Bundle bundle = getIntent().getExtras();

//Extract the data…
        final String pname = bundle.getString("pname");
        final String image = bundle.getString("image");
        final String[] imag=bundle.getStringArray("imagea");
        Global.imj=imag;
      //  Picasso.with(context).load(image).into(imageView);
     //   Picasso.get().load(image).into(imageView);
        final String price = bundle.getString("price");
        final String stock = bundle.getString("stock");
        final String discount = bundle.getString("discount");
        final String des = bundle.getString("des");
        final String catid=bundle.getString("catid");
        final String display=bundle.getString("display");
        final String memory=bundle.getString("memory");


        assert imag != null;
        for(int i = 0; i<imag.length; i++){
            String split = imag[i].replace("[", "").replace("]","").trim();
            array.add(new SlideModel(Global.BASE_URL+split));
            Log.d("imagertttty","mm"+split);

        }


        imageSlider.setImageList(array,false);

        id=bundle.getString("id");
        sessionManager.setPdtaddvar(id);
        Log.d("mnjbkjbkbj","hjghjghg"+id);

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AddVariant.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });

        //  final String ema=bundle.getString("email");

        shopname.setText(pname);
        location.setText(price);
        owner.setText("₹ "+discount);
        gstno.setText(stock);
        catgory.setText(des);

        edit.setOnClickListener(new View.OnClickListener() {
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
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                uri=data.getData();
                filePath = getRealPathFromURIPath(uri, viewpdtdetails.this);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap = getResizedBitmap(bitmap, 400);
                    String path = saveImage(bitmap);
                    Toast.makeText(viewpdtdetails.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageView.setImageBitmap(bitmap);
                    uploadToServer(filePath);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(viewpdtdetails.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(viewpdtdetails.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
            filePath= (getRealPathFromURI(tempUri));
            uploadToServer(filePath);
            Log.d("filepath","mm"+filePath);
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

        float bitmapRatio = (float)width / (float) height;
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

    private void  requestMultiplePermissions(){
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
                           // Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(viewpdtdetails.this,"Please Upload Image",Toast.LENGTH_SHORT).show();
        }
        if (filePath != null) {
            File immm = new File(filePath);
            Log.d("mmmmmmm", "mmm" + immm.length());
            // Create a request body with file and image media type


            RequestBody photob = RequestBody.create(MediaType.parse("image/*"), immm);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("pdt_image", immm.getName(), photob);
            Log.d("image","mm"+part1);
            Log.d("image","mm"+immm.getName());
            RequestBody id1 = RequestBody.create(MediaType.parse("text/plain"),id );



            //Create request body with text description and text media type
            //
            Call<ResponseBody> call = uploadAPI.uploadImag(part1,"Token "+ sessionManager.getTokens(),id1);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(viewpdtdetails.this,viewproduct.class));
    }
}
