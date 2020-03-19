package com.haahoo.haahooshop;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class editchoosepdtadapter extends
        RecyclerView.Adapter<editchoosepdtadapter.ViewHolder> {

    private List<OffersModel> offersList;
    private Context context;
    public String id;
    SessionManager sessionManager;
    private String url = Global.BASE_URL+"api_shop_app/del_shop_cat/";

    private int lastSelectedPosition = -1;

    public editchoosepdtadapter(List<OffersModel> offersListIn
            , Context ctx) {
        offersList = offersListIn;
        context = ctx;
        sessionManager=new SessionManager(ctx);
    }

    @Override
    public editchoosepdtadapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editcatvieww, parent, false);

        editchoosepdtadapter.ViewHolder viewHolder =
                new editchoosepdtadapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final editchoosepdtadapter.ViewHolder holder,
                                 final int position) {
        final OffersModel offersModel = offersList.get(position);

        holder.offerAmount.setText("" + offersModel.getName());

      //  id=offersModel.getRadio();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.d("idssd","mm"+offersModel.getRadio());
      //          Log.d("first","mm"+Global.trr.get(position));

                for (int i=0;i<Global.trr.size();i++){

                        Global.trr.remove(i);
                        id=offersList.get(position).getRadio();
                        Log.d("first","mm"+id);
                        remove();
                        holder.cardView.setVisibility(View.GONE);
                        if(Global.trr.size()==0){
                            Toast.makeText(context,"Please add atleast one category",Toast.LENGTH_SHORT).show();

                    }


                    Log.d("ghjklkj","mm"+Global.trr);

                }


            }
        });

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView offerAmount;
        public ImageView imageView;
        public CardView cardView;

        public ViewHolder(final View view) {
            super(view);
            offerAmount = (TextView) view.findViewById(R.id.cat);
            imageView=view.findViewById(R.id.img);
            cardView=view.findViewById(R.id.card);



        }
    }

    private void remove(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String ot = jsonObject.optString("message");
                    String status=jsonObject.optString("code");
                    String token=jsonObject.optString("Token");
                    //    sessionManager.setTokens(token);
                    Log.d("otp","mm"+token);
                    Log.d("code","mm"+status);
                    if(status.equals("200")&&(!(ot.equals("verify")))){
                        Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, chooseeditcat.class);
                        context.startActivity(intent);
                    }
                    if(!(status.equals("200"))){
                        Toast.makeText(context, "Failed."+ot, Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("cat_id",id);
                Log.d("idlllll","mm"+id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }


        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}