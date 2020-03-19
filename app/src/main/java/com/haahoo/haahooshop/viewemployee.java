package com.haahoo.haahooshop;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

public class viewemployee extends AppCompatActivity implements
        ItemClickSupport.OnItemClickListener {

    GridView simpleList;
    ArrayList<RowItem> birdList=new ArrayList<>();
    Activity activity = this;
    Context context=this;
    SessionManager sessionManager;
    ListView listView;
    List<RowItem> rowItems;
    ImageView imageView;
    private ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewemployee);

        sessionManager=new SessionManager(this);

        rowItems = new ArrayList<RowItem>();
        listView = (ListView) findViewById(R.id.list);
        imageView=findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,BranchManagement.class));
            }
        });

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        dialog=new ProgressDialog(viewemployee.this,R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading");
        dialog.show();


        submituser();
    }

    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(viewemployee.this);

        //this is the url where you want to send the request

        String url = Global.BASE_URL+"api_shop_app/employee_list/";

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
                                Toast.makeText(viewemployee.this,"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                RowItem playerModel = new RowItem();
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
                                Log.d("ssssd", "resp" + dataobj);
                                playerModel.setDesc(dataobj.optString("branch"));
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
                                CustomListViewAdapter adapter = new CustomListViewAdapter(context,
                                        R.layout.list_item, birdList);
                                listView.setAdapter(adapter);

                            }




                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(viewemployee.this,"Internal Server Error",Toast.LENGTH_LONG).show();


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

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,BranchManagement.class));
    }
}
