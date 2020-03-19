package com.haahoo.haahooshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> imageModelArrayList;
    private ArrayList<String> cat;
    private ArrayList<String> imageModelArrayList1;
    JSONArray arr = new JSONArray();
    ArrayList<String> ids = new ArrayList<>();
    JSONObject products = new JSONObject();
    SessionManager sessionManager;
    Context context1;

    public FruitAdapter(Context ctx, ArrayList<String> imageModelArrayList,ArrayList<String>ids){

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.cat = ids;
        this.context1 = ctx;
    }

    @Override
    public FruitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FruitAdapter.MyViewHolder holder, final int position) {

        final ArrayList<String> idss = new ArrayList<>();
        sessionManager = new SessionManager(context1);



            holder.time.setText(imageModelArrayList.get(position));
//        imageModelArrayList1 = new ArrayList<>();
//        imageModelArrayList1.addAll(imageModelArrayList);

            ids.add(imageModelArrayList.get(position));



        for (int i = 0; i < ids.size(); i++) {
            Log.d("kjbbhj","vgfgdf"+ids.get(i));
        }
//        ids.add(0,imageModelArrayList1.get(position).getId());
//        Log.d("json","bhjhjb1"+ids.size());





        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageModelArrayList.remove(position);
               // ids.remove(position);

                cat.remove(position);
                notifyDataSetChanged();
            }
        });





//        JSONObject datas = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//
//
//
//        try {
//            HashMap<String, JSONObject> map1 = new HashMap<String, JSONObject>();
//            for (int i = 0; i < imageModelArrayList1.size(); i++) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("id", imageModelArrayList1.get(i).getId());
//                map1.put("json"+i,jsonObject);
//                jsonArray.put(map1.get("json"+i));
//            }
//            datas.put("categories",jsonArray);
//            Log.d("jsonobkject","mm"+datas);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        String[] array = new String[cat.size()];
        array = cat.toArray(array);
        JSONArray jsonObject=new JSONArray(Arrays.asList(array));
        Log.d("hjvhjv","kjbkjb"+jsonObject);
        sessionManager.setcat(jsonObject.toString());
       // Global.trr.add(jsonObject.toString());
        Log.d("hjvhjv","kjbkjb"+sessionManager.getcat());

       /* try{
        *//*   JSONArray jsonArray =new JSONArray(ids);
           jsonObject.put("categories",jsonArray);
           Log.d("json","bhjhjb1"+jsonObject);*//*

        }catch (JSONException e){
            e.printStackTrace();
        }*/





    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.tv);
            iv = itemView.findViewById(R.id.category);

        }

    }
}