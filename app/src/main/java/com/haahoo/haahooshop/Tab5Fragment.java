package com.haahoo.haahooshop;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
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
import java.util.Map;

public class Tab5Fragment extends Fragment {

    ImageView imageView;
    boolean doubleBackToExitPressedOnce = false;
    CardView cardView;

    ImageView logout;
    SessionManager sessionManager;
    ImageView back;

    String[]ne;
    public String image,pname,price,discount,descr,stock,email;
    private ProgressDialog dialog ;
    GridView simpleList;
    ArrayList<Item> birdList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_tab5_fragment, container, false);

        sessionManager = new SessionManager(getContext());

        cardView=view.findViewById(R.id.card);

      /*  back=view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainUI.class));
            }
        });*/
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.black));
        dialog=new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading");
        dialog.show();

        simpleList = (GridView) view.findViewById(R.id.card_view_recycler_list);

        submituser();

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),viewpdtdetails.class);
                intent.putExtra("pname",birdList.get(i).name);
                intent.putExtra("image",birdList.get(i).getImage());
                intent.putExtra("imagea",birdList.get(i).getImk());
                Log.d("imagearray", String.valueOf(birdList.get(i).getImk()));
                intent.putExtra("price",birdList.get(i).getPrice());
                intent.putExtra("stock",birdList.get(i).getStock());
                intent.putExtra("discount",birdList.get(i).getDiscount());
                intent.putExtra("des",birdList.get(i).getDescription());
                intent.putExtra("id",birdList.get(i).getId());
                intent.putExtra("catid",birdList.get(i).getCategoryid());
                intent.putExtra("display",birdList.get(i).getDisplay());
                intent.putExtra("memory",birdList.get(i).getMemory());
                startActivity(intent);
            }
        });



        return view;
    }
    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        //this is the url where you want to send the request

        String url = Global.BASE_URL+"api_shop_app/distributor_pdt_details/";

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
                                Toast.makeText(getContext(),"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                Item playerModel = new Item();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                Global.spec_headers.clear();
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
                                playerModel.setStock(stock);
                                //    playerModel.setEmail(email);
                                playerModel.setName(dataobj.optString("name"));
                                Log.d("ssssd", "resp" + dataobj);
                                playerModel.setPrice("₹ "+dataobj.optString("price"));
                                String images1 = dataobj.getString("image");
                                String[] seperated = images1.split(",");
                                ne=images1.split(",");
                                playerModel.setImk(ne);
                                Log.d("imagearray", String.valueOf(playerModel.getImk()));
                                String split = seperated[0].replace("[", "").replace("]","");
                                playerModel.setImage(Global.BASE_URL+split);
                                image=Global.BASE_URL+split;
                                JSONObject jsonArray=dataobj.optJSONObject("specifications");
                                Log.d("specifications","mm"+jsonArray);
                                String display=jsonArray.optString("Display");
                                Log.d("specifications","mm"+display);
                                String Memory=jsonArray.optString("Memory");
                                Log.d("specifications","mm"+Memory);

                                playerModel.setDisplay(display);
                                playerModel.setMemory(Memory);
                                /*   JSONObject jsonObject=jsonArray.getJSONObject(0);



                                 */



                                //  images.add(split);
                                //  playerModel.setStatus("");


                                birdList.add(playerModel);

                            }
                            MyAdapter myAdapter=new MyAdapter(getContext(),R.layout.grid_view_items,birdList);
                            simpleList.setAdapter(myAdapter);



                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getContext(),"Internal Server Error",Toast.LENGTH_LONG).show();


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


    public void onBackPressed() {
        startActivity(new Intent(getContext(),Navigation.class));

    }
}
