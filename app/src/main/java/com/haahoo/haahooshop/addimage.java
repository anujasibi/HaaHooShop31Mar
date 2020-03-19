package com.haahoo.haahooshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class addimage extends AppCompatActivity {
    ImageButton captureBtn = null;
    final int CAMERA_CAPTURE = 1,GALLERY = 2;
    private Uri picUri;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private GridView grid;
    private List<String> listOfImagesPath= new ArrayList<>();
    Context context=this;
    TextView save;
    SessionManager sessionManager;
    ImageView imageView3;
    private Uri uri;
    String imageEncoded;
    List<String> imagesEncodedList;
    private ProgressDialog dialog ;
    Activity activity = this;

    public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridViewDemo/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addimage);

        requestMultiplePermissions();

        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(addimage.this,R.style.MyAlertDialogStyle);

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
        sessionManager=new SessionManager(this);

        listOfImagesPath.clear();

        save=findViewById(R.id.save);
        imageView3=findViewById(R.id.imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,subscription.class));
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Loading");
                dialog.show();

                if(listOfImagesPath.size()==0){
                    Toast.makeText(getApplicationContext(),"Please add images",Toast.LENGTH_SHORT).show();
                }
                if(!(listOfImagesPath.size()==0)) {
                    submituser();
                }
            }
        });

        File imageDirectory = new File(GridViewDemo_ImagePath);
        imageDirectory.mkdirs();
    //    imageDirectory=context.getCacheDir();
        if (imageDirectory.isDirectory()){
            if (imageDirectory.list().length>0) {
                String[] children = imageDirectory.list();
                for (int i = 0; i < children.length; i++) {
                    new File(imageDirectory, children[i]).delete();
                }
            }
        }

        captureBtn = findViewById(R.id.card_view_image);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.card_view_image) {

                    try {
                        requestMultiplePermissions();
//use standard intent to capture an image
                      /*  Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//we will handle the returned data in onActivityResult
                        startActivityForResult(captureIntent, CAMERA_CAPTURE);*/
                        showPictureDialog();
                    } catch(ActivityNotFoundException anfe){
//display an error message
                        String errorMessage = "Whoops – your device doesn’t support capturing images!";
                        Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }


            }
        });
        grid = (GridView) findViewById(R.id.card_view_recycler_list);


      //  listOfImagesPath = RetriveCapturedImagePath();
        if(listOfImagesPath!=null){
            grid.setAdapter(new ImageListAdapter(this,listOfImagesPath));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void onClick(View arg0) {
// TODO Auto-generated method stub

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
        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//user is returning from capturing an image using the camera
            if (requestCode == CAMERA_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                String imgcurTime = dateFormat.format(new Date());

                String _path = GridViewDemo_ImagePath + imgcurTime + ".jpg";
                try {
                    FileOutputStream out = new FileOutputStream(_path);
                    thePic.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                listOfImagesPath.clear();

                File f = new File(GridViewDemo_ImagePath);
                if (f.exists()) {
                    File[] files=f.listFiles();
                    Arrays.sort(files);

                    for(int i=0; i<files.length; i++){
                        File file = files[i];
                        if(file.isDirectory())
                            continue;
//                        Log.d("vvvvvvvv","bkbhj"+file.getPath());
                        if (listOfImagesPath.size()==0){
                            listOfImagesPath.add(file.getPath());
                        }
                       /* if ((listOfImagesPath.get(i).equals(file.getPath()))) {
                             listOfImagesPath.clear();
                        }*/

                        if(listOfImagesPath.contains(file.getPath())){
                            listOfImagesPath.remove(file.getPath());
                        }
                        /*if (!(listOfImagesPath.get(i).equals(file.getPath()))) {
                            listOfImagesPath.add(file.getPath());
                        }*/

                        if(!(listOfImagesPath.contains(file.getPath()))){
                            listOfImagesPath.add(file.getPath());
                        }


                        //listOfImagesPath.add(file.getPath());
                    }
                }


                //listOfImagesPath=RetriveCapturedImagePath();
                Log.d("imagearrasyaise","bkbhj"+listOfImagesPath.size());
                //listOfImagesPath.add(RetriveCapturedImagePath().get(0));
                if (listOfImagesPath != null) {
                    grid.setAdapter(new ImageListAdapter(this, listOfImagesPath));

                    Log.d("IMafggvGASVS","MM"+listOfImagesPath.size());
                }
            }
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    uri = data.getData();
                    listOfImagesPath.add((getRealPathFromURIPath(uri, addimage.this)));
                    try {
                       Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        bitmap = getResizedBitmap(bitmap, 400);
                       // String path = saveImage(bitmap);
                      //  Toast.makeText(addimage.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                        grid.setAdapter(new ImageListAdapter(this, listOfImagesPath));
                        Log.d("IMafggvGASVS","MM"+listOfImagesPath.size());
                   //     submituser();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(addimage.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }
    }


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(contentURI, proj, null, null, null);

        if (cursor == null) {
            cursor.moveToFirst();
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
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




    private List<String> RetriveCapturedImagePath() {
        List<String> tFileList = new ArrayList<String>();
        File f = new File(GridViewDemo_ImagePath);
        if (f.exists()) {
            File[] files=f.listFiles();
            Arrays.sort(files);

            for(int i=0; i<files.length; i++){
                File file = files[i];
                if(file.isDirectory())
                    continue;
                tFileList.add(file.getPath());
                //listOfImagesPath.add(file.getPath());
            }
        }
        return tFileList;
    }

    public class ImageListAdapter extends BaseAdapter
    {
        private Context context;
        private List<String> imgPic;
        public ImageListAdapter(Context c, List<String> thePic)
        {
            context = c;
            imgPic = thePic;
        }
        public int getCount() {
            if(imgPic != null)
                return imgPic.size();
            else
                return 0;
        }

        //—returns the ID of an item—
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //—returns an ImageView view—
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            BitmapFactory.Options bfOptions=new BitmapFactory.Options();
            bfOptions.inDither=false;                     //Disable Dithering mode
            bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
            bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
            bfOptions.inTempStorage=new byte[32 * 1024];
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setPadding(0, 0, 0, 0);
            } else {
                imageView = (ImageView) convertView;
            }
            FileInputStream fs = null;
            Bitmap bm;
            try {
                fs = new FileInputStream(new File(imgPic.get(position).toString()));

                if(fs!=null) {
                    bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                    imageView.setImageBitmap(bm);
                    imageView.setId(position);
                    imageView.setLayoutParams(new GridView.LayoutParams(200, 160));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(fs!=null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return imageView;
        }
    }



    private void submituser(){

        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UploadAPI uploadAPIs = retrofit.create(UploadAPI.class);
        //Create a file object using file path
        MultipartBody.Part[] part = new MultipartBody.Part[listOfImagesPath.size()];
        for (int index = 0; index < listOfImagesPath.size(); index++) {
            File file = new File(listOfImagesPath.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            part[index] = MultipartBody.Part.createFormData("pdt_image", file.getName(), surveyBody);

        }
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        RequestBody pdt_name = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getPdtName() );
        Log.d("pdtname","mm"+sessionManager.getPdtName());
        RequestBody pdt_cat_id = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getcatid() );
        Log.d("catid","mm"+sessionManager.getcatid());
        RequestBody pdt_spec = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getcatName() );
        Log.d("pdtspec","mm"+sessionManager.getcatName());
        RequestBody pdt_price = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getprice() );
        Log.d("pdtprice","mm"+sessionManager.getprice());
        RequestBody pdt_return_period = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getret() );
        Log.d("returnperiod","mm"+sessionManager.getret());
        RequestBody pdt_discount = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getdis() );
        Log.d("discountttt","mm"+sessionManager.getdis());
        RequestBody stock = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getstock() );
        Log.d("stock","mm"+sessionManager.getstock());
        RequestBody pdt_description = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getdes() );
        Log.d("desc","mm"+sessionManager.getdes());
        RequestBody delivery_mode = RequestBody.create(MediaType.parse("text/plain"),  String.valueOf(Global.arrayList));
        Log.d("mode","mm"+Global.arrayList);
        RequestBody distance = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getcatdistance() );
        Log.d("distance","mm"+sessionManager.getcatdistance());
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getradio() );
        Log.d("type","mm"+sessionManager.getradio());
        RequestBody resell = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getcheckn() );
        Log.d("resell","mm"+sessionManager.getcheckn());
        RequestBody max_price = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getreselprice() );
        Log.d("resell","mm"+sessionManager.getreselprice());
        RequestBody subscription = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getsub() );
        Log.d("resell","mm"+sessionManager.getreselprice());
        RequestBody sub_mode = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getsubvalue() );
        Log.d("resell","mm"+sessionManager.getreselprice());
        RequestBody product_owner = RequestBody.create(MediaType.parse("text/plain"),"" );
        Log.d("resell","mm"+sessionManager.gettype());
        RequestBody myshop = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getaddshop() );
        Log.d("resell","mm"+sessionManager.gettype());
        RequestBody incentive = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getincent());
        Log.d("resell","mm"+sessionManager.getincent());
        RequestBody del_charge = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getdelamount());
        Log.d("resell","mm"+sessionManager.getincent());
        RequestBody del_applicable = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getdels());
        Log.d("resell","mm"+sessionManager.getincent());
        RequestBody delvry_date  = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getdelvry_date());
        Log.d("resell","mm"+sessionManager.getdelvry_date());
        RequestBody delvry_tym  = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getdelvry_tym());
        Log.d("resell","mm"+sessionManager.getdelvry_tym());
        RequestBody cancel_tym   = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getcancel_tym());
        Log.d("resell","mm"+sessionManager.getcancel_tym());
        RequestBody pick_tym  = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getpick_tym());
        Log.d("resell","mm"+sessionManager.getpick_tym());
        RequestBody emp_id  = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getempid());
        Log.d("resell","mm"+sessionManager.getempid());
        RequestBody emp_branch  = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getempbranch());
        Log.d("resell","mm"+sessionManager.getempid());
        //
        Call call = uploadAPIs.uploadImage("Token "+sessionManager.getTokens(),part,pdt_name,pdt_cat_id,pdt_spec,pdt_price,pdt_return_period,pdt_discount,stock,pdt_description,delivery_mode,distance,type,resell,max_price,subscription,sub_mode,product_owner,myshop,incentive,del_charge,del_applicable,delvry_date,delvry_tym,cancel_tym,pick_tym,emp_id,emp_branch);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                dialog.dismiss();
                Toast.makeText(context,"Successfully Added The Product",Toast.LENGTH_SHORT).show();
                Log.d("RESPONSERTTT","MM"+response);
                startActivity(new Intent(addimage.this,Navigation.class));

            }
            @Override
            public void onFailure(Call call, Throwable t) {
                dialog.dismiss();
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,subscription.class));
    }
}
