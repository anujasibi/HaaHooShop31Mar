package com.haahoo.haahooshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shpcategory extends AppCompatActivity {
    ArrayList<RowItem> birdList=new ArrayList<>();
    Activity activity = this;
    ImageView imj;
    Context context=this;
    SessionManager sessionManager;
    RecyclerView listView;
    List<RowItem> rowItems;
    ImageView imageView;
    private ProgressDialog dialog ;
   ArrayList<String> mItems = new ArrayList<String>();

    private RecyclerView recyclerView;
    private ArrayList<FruitModel> imageModelArrayList;
    private FruitAdapter adapter;
    shpcategoryadapter shpcategoryadapter;
    private int[] myImageList = new int[]{R.drawable.person, R.drawable.person,R.drawable.person, R.drawable.person,R.drawable.person,R.drawable.person,R.drawable.person};
    private String[] myImageNameList = new String[]{"Apple","Mango" ,"Strawberry","Pineapple","Orange","Blueberry","Watermelon"};
    private String[] phonelist = new String[]{"Xiaomi","Apple" ,"Samsung"};

    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<sublistpojo>pojo = new ArrayList<>();
    ArrayList<String> list1 = new ArrayList<>();
    ArrayList<String> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpcategory);


/*
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout2);
        TextView tv = new TextView(this);
        tv.setText("Dynamic Text!");
        ll.addView(tv);
        TextView tv1 = new TextView(this);
        tv.setText("Dynamic Text!!!!");
        ll.addView(tv1);*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

      //  imageModelArrayList =   eatFruits();
        adapter = new FruitAdapter(this, list,id);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        sessionManager=new SessionManager(this);

        rowItems = new ArrayList<RowItem>();
        listView =  findViewById(R.id.list);

        imj=findViewById(R.id.imj);
        sessionManager.setcat("");

        imj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getcat().equals("")){
                    Toast.makeText(context,"Please choose atleast one category",Toast.LENGTH_SHORT).show();
                }
                if(!(sessionManager.getcat().equals(""))){
                    startActivity(new Intent(context,addshopim.class));
                }

            }
        });



        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(shpcategory.this,R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading");
        dialog.show();


        ItemClickSupport.addTo(listView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
          //      Toast.makeText(context,"clck",Toast.LENGTH_SHORT).show();
//                list2.add(list1.get(position));
                showphones(list1.get(position),list2.get(position));
                adapter.notifyDataSetChanged();
            }
        });

        submituser();
    }

    private ArrayList<String> eatFruits(){


//        for(int i = 0; i < 1; i++){
//            FruitModel fruitModel = new FruitModel();
//            fruitModel.setName(myImageNameList[i]);
//            fruitModel.setImage_drawable(myImageList[i]);
//            list.add(fruitModel);
//        }

        return list;
    }




    private ArrayList<String> showphones(String data,String id1){


            if (id.contains(id1)){
          //      Toast.makeText(context,"exists",Toast.LENGTH_SHORT).show();
            }
        if (!(id.contains(id1))) {
            id.add(id1);
            String fruitModel = data;
            list.add(fruitModel);
        }

        return list;
    }

    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(shpcategory.this);

        //this is the url where you want to send the request

     //  String url = "https://testapi.creopedia.com/api_shop_app/list_shop_cat/";
     //   String url = "https://haahoo.in/api_shop_app/list_shop_cat/";
        String url = Global.BASE_URL+"api_shop_app/list_shop_cat/";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();

                        try {

                            JSONObject obj = new JSONObject(response);

                            // amount.setText(obj.optString("total"));
                            birdList = new ArrayList<>();
                            String total=obj.optString("total");
                            JSONArray dataArray  = obj.getJSONArray("data");

                            if(dataArray.length() == 0){
                                Toast.makeText(shpcategory.this,"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                final RowItem playerModel = new RowItem();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                              /*  Global.spec_headers.clear();
                                Global.spec_values.clear();
                                //   playerModel.setProductname(dataobj.optSt ring("name"));
                                // ApiClient.productids.add(dataobj.optString("id"));
                                pname=dataobj.optString("name");
                                price=dataobj.optString("price");
                                descr=dataobj.optString("description");
                                discount=dataobj.optString("discount");
                                ArrayList<String>spec_headers = new ArrayList<>();
                                JSONObject specification_headers = dataobj.optJSONObject("specification_headers");
                                for (int j = 0 ; j < specification_headers.length() ; j++){
                                    spec_headers.add(specification_headers.optString("spec"+j));
                                    Log.d("specheaders","bhjb"+spec_headers.get(j));
                                }
                                ArrayList<String>spec_values = new ArrayList<>();
                                JSONObject specifications = dataobj.optJSONObject("specifications");
                                for (int k = 0 ; k <spec_headers.size();k++){
                                    spec_values.add(specifications.optString(spec_headers.get(k)));
                                    Log.d("specvalues","bhjb"+spec_values.get(k));
                                }
                                // Global.spec_headers = spec_headers;
                                //   Global.spec_values = spec_values;
                                stock=dataobj.optString("stock");
                                //  email=dataobj.optString("email");
                                String id=dataobj.getString("id");
                                Log.d("imageurl","bhcbvfc"+id);
                                playerModel.setId(id);
                                sessionManager.setPdtid(id);
                                String catid=dataobj.optString("category_id");
                                playerModel.setCategoryid(catid);
                                playerModel.setDescription(descr);

                                playerModel.setDiscount(discount);
                                playerModel.setStock(stock);*/
                                //    playerModel.setEmail(email);
                                playerModel.setTitle(dataobj.optString("name"));
                                FruitModel fruitModel = new FruitModel();
                                fruitModel.setId(dataobj.optString("id"));
                                fruitModel.setName(dataobj.optString("name"));
//                                list.add(fruitModel);
                                list1.add(dataobj.optString("name"));
                                list2.add(dataobj.optString("id"));
                                Log.d("ssssd", "resp" + dataobj);
                              //  playerModel.setDesc(dataobj.optString("branch"));
                                playerModel.setId(dataobj.optString("id"));
                               /* String images1 = dataobj.getString("image");
                                String[] seperated = images1.split(",");
                                String split = seperated[0].replace("[", "").replace("]","");
                                playerModel.setImage(Global.BASE_URL+split);*/
                                //  image=Global.BASE_URL+split;

                                /*JSONObject jsonArray=dataobj.optJSONObject("specifications");
                                Log.d("specifications","mm"+jsonArray);
                                String display=jsonArray.optString("Display");
                                Log.d("specifications","mm"+display);
                                String Memory=jsonArray.optString("Memory");
                                Log.d("specifications","mm"+Memory);*/

                                // playerModel.setDisplay(display);
                                //playerModel.setMemory(Memory);
                                /*   JSONObject jsonObject=jsonArray.getJSONObject(0);



                                 */



                                //  images.add(split);
                                //  playerModel.setStatus("");


                                birdList.add(playerModel);
                                shpcategoryadapter = new shpcategoryadapter(context,list1);
                                listView.setAdapter(shpcategoryadapter);
                                listView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

//                                Customad adapter = new Customad(context,
//                                        R.layout.list_new, birdList);
//                                listView.setAdapter(adapter);



                            }


//                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
////                                    Toast.makeText(context,"value"+birdList.get(position).getTitle()+birdList.get(position).getId(),Toast.LENGTH_SHORT).show();
////                                    sessionManager.setshopid(birdList.get(position).getId());
////                                    mItems.add(birdList.get(position).getTitle());
////                                    startActivity(new Intent(context,addshopim.class));
//
//
//                                }
//                            });








                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(shpcategory.this,"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }





}
