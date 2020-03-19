package com.haahoo.haahooshop;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Map;

public class Tab3Fragment extends Fragment {
    ArrayList<upcomingrow> birdList=new ArrayList<>();

    Tab3Fragment context=this;
    SessionManager sessionManager;
    RecyclerView listView;
    ArrayList<upcomingrow> rowItems;
    ImageView back;
    private ProgressDialog dialog ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tab2_fragment, container, false);
        sessionManager=new SessionManager(getContext());

        rowItems = new ArrayList<upcomingrow>();
        listView = view.findViewById(R.id.list);

        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.black));

        /*back=view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainUI.class));
            }
        });
*/
        dialog=new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading");
        dialog.show();



        submituser();


        return view;
    }
    private void submituser(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        //this is the url where you want to send the request

        String url = Global.BASE_URL+"virtual_order_management/past_orders/";

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

                                upcomingrow playerModel = new upcomingrow();
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
                                playerModel.setName(dataobj.optString("pdt_name"));
                                Log.d("ssssd", "resp" + dataobj);
                                playerModel.setLocation(dataobj.optString("landmark"));
                                playerModel.setId(dataobj.optString("id"));
                                String images1 = dataobj.getString("pdt_image");
                                String[] seperated = images1.split(",");
                                String split = seperated[0].replace("[", "").replace("]","");
                                playerModel.setImage(Global.BASE_URL+split);
                                playerModel.setCusname(dataobj.optString("cus_name"));
                                playerModel.setNumber(dataobj.optString("cus_phone"));
                                playerModel.setHouse(dataobj.optString("house_no"));
                                playerModel.setCity(dataobj.optString("city"));
                                playerModel.setState(dataobj.optString("state"));
                                playerModel.setPincode(dataobj.optString("pin"));
                                playerModel.setPrice(dataobj.optString("pdt_price"));
                                playerModel.setStatus(dataobj.optString("status"));



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
                                Log.d("ADESGVDTHYDRTY","MM"+playerModel.getCusname());




                            }

                            UpcomingAdapter upcomingAdapter=new UpcomingAdapter(getContext(),birdList);
                            listView.setAdapter(upcomingAdapter);
                            listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));



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
