package com.haahoo.haahooshop;

import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.haahoo.haahooshop.utils.Global;
import com.haahoo.haahooshop.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Specad extends RecyclerView.Adapter<Specad.ViewHolder> {

  public ArrayList<EditPojo> downloadPojos;
  Context context1 ;
  JSONArray arr = new JSONArray();
  JSONObject products = new JSONObject();
  ArrayList<String> areas = new ArrayList<String>();
  public String country;
  SessionManager sessionManager;
  TextWatcher addOntext = null;
  private ArrayAdapter<String> adapter ;

  public Specad(ArrayList<EditPojo> productPojo, Context context) {
    this.downloadPojos = productPojo;
    this.context1 = context;



//        for(int i=0;i<Global.row.size();i++){
//            Log.d("testtttt","jubhkjuibh"+Global.row.get(i).get(0));

//        }

    //   Log.d("kjibkjbb","jubhkjuibh"+Global.row.get(0).get(0));
    //  areas.add(Global.row.get(position).get(0));

    //   Log.d("jhgfghf","se"+downloadPojos.get(position).getValuess().get(0));
    //    areas.add(downloadPojos.get(position).getValuess().get(0));
//        for (int i=0;i<seperated.length;i++){
//         String name=seperated[i].replace("[","").replace("'","").replace("]","");
//         areas.add(name);
//     }

    //  holder.spinner.setAdapter(new ArrayAdapter<String>(context1, android.R.layout.simple_spinner_dropdown_item, areas));

  }

  @Override
  public Specad.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View listItem= layoutInflater.inflate(R.layout.editcat, parent, false);
    Specad.ViewHolder viewHolder = new Specad.ViewHolder(listItem);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final Specad.ViewHolder holder, final int position) {
    sessionManager = new SessionManager(context1);
    //  holder.prev.setText(downloadPojos.get(position).getHval());
    Log.d("dddffftftftftf","mmm"+Global.spec_headers);
    Log.d("dddffftftftftf","mmm"+Global.spec_values);
    ArrayList<String>newli=Global.row1.get(position);
    ArrayList<String> finallis = new ArrayList<>();
    String[] lis =null;
    for (String s: newli){
      lis = s.split(",");

    }
    for (int i =0 ;i<lis.length;i++){
      finallis.add(lis[i].replace("[","").replace("]","").replace("'",""));
      Log.d("RSDRFGHJ","MM"+finallis.get(i));
      //  holder.prev.setText(finallis.get(position));
    }

    holder.prev.setText(finallis.get(0));


    ArrayList<String> newlist = Global.row.get(position);
    ArrayList<String> finallist = new ArrayList<>();
    String[] list =null;
    for (String s: newlist){
      list = s.split(",");

    }
    for (int i =0 ;i<list.length;i++){
      finallist.add(list[i].replace("[","").replace("]","").replace("'",""));
    }


    holder.check.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        holder.check.setVisibility(View.GONE);
        holder.spinner.setVisibility(View.VISIBLE);
        holder.prev.setVisibility(View.GONE);
        holder.apply.setVisibility(View.VISIBLE);

      }
    });

    adapter = new ArrayAdapter<String>(context1,android.R.layout.simple_spinner_item,finallist);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    areas.add("Please Choose the Value");
    holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        country = holder.spinner.getItemAtPosition(holder.spinner.getSelectedItemPosition()).toString();

//                areas.add(Global.row.get(i).get(0));
        // idsp = areasid.get(spinner.getSelectedItemPosition());
        // Toast.makeText(context1, holder.spinner.getSelectedItemPosition()+country, Toast.LENGTH_LONG).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {
        // DO Nothing here
      }
    });









    holder.spinner.setAdapter(adapter);
       /* areas.add("Please Choose the Value");
        String[]seperated=downloadPojos.get(position).getValuesss();

        for(int i=0;i<Global.row.size();i++){
            Log.d("testtttt","jubhkjuibh"+Global.row.get(i).get(0));
            areas.add(Global.row.get(i).get(0));
        }

     //   Log.d("kjibkjbb","jubhkjuibh"+Global.row.get(0).get(0));
      //  areas.add(Global.row.get(position).get(0));

     //   Log.d("jhgfghf","se"+downloadPojos.get(position).getValuess().get(0));
    //    areas.add(downloadPojos.get(position).getValuess().get(0));
//        for (int i=0;i<seperated.length;i++){
//         String name=seperated[i].replace("[","").replace("'","").replace("]","");
//         areas.add(name);
//     }

        holder.spinner.setAdapter(new ArrayAdapter<String>(context1, android.R.layout.simple_spinner_dropdown_item, areas));
        Log.d("valuess","mm"+seperated[0].replace("[","").replace("'","").replace("]",""));
*/

    holder.spec1.setText(downloadPojos.get(position).getName());
    holder.apply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        holder.apply.setBackgroundColor(Color.parseColor("#008000"));
        if (country.length() == 0) {
          Toast.makeText(context1, "Spec cannot be empty"+products.length(), Toast.LENGTH_SHORT).show();
        }
        if (country.length()!= 0) {
          // holder.remove.setVisibility(View.VISIBLE);
          //   holder.apply.setVisibility(View.GONE);
          holder.apply.setText("Applied");
          // holder.value.setEnabled(false);
          holder.spinner.setEnabled(false);
          ArrayList<String> names = new ArrayList<>();
          // names.clear();
          names.add(downloadPojos.get(position).getName());
          HashMap<String, JSONObject> map1 = new HashMap<String, JSONObject>();
          JSONObject json = new JSONObject();
          try {

            json.put("name", names.get(0));
            json.put("value", country);
            map1.put("json", json);
            arr.put(map1.get("json"));


                       /* if (arr.length() > 3) {
                            arr.remove(0);
                        }*/

            products.put("spec", arr);
            sessionManager.setcatName(products.toString());


            Log.d("fff", "mm" + sessionManager.getcatName());

            Log.d("fff", "mm" + products.length());
            //Log.d("sizedfgdfgfg11", "mm" + arr.getJSONObject(0).getString("name"));

          } catch (JSONException e) {
            e.printStackTrace();
          }


        }
      }
    });



  }

  @Override
  public int getItemCount() {
    return downloadPojos.size();
  }


  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView spec1;
    public Spinner spinner;
    public EditText value;
    public TextView apply;
    public EditText check;
    public TextView prev;
    public TextView remove;
    public ViewHolder(View itemView) {
      super(itemView);

      spec1 = itemView.findViewById(R.id.spec1);

      spinner = itemView.findViewById(R.id.spinner);
      value = itemView.findViewById(R.id.value);
      check=itemView.findViewById(R.id.editnew);
      apply=itemView.findViewById(R.id.apply);
      remove=itemView.findViewById(R.id.remove);
      prev=itemView.findViewById(R.id.prval);



    }

  }

}