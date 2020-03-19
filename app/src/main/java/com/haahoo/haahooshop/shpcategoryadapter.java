package com.haahoo.haahooshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.haahoo.haahooshop.utils.SessionManager;
import java.util.ArrayList;

public class shpcategoryadapter extends RecyclerView.Adapter<shpcategoryadapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> dataModelArrayList;
    public Context context1 ;
    public String id;
    SessionManager sessionManager;

    public shpcategoryadapter(Context ctx, ArrayList<String> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.context1 = ctx;
        this.dataModelArrayList = dataModelArrayList;
        sessionManager=new SessionManager(context1);
    }

    @Override
    public shpcategoryadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.shpcategory, parent, false);
        shpcategoryadapter.MyViewHolder holder = new shpcategoryadapter.MyViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(final shpcategoryadapter.MyViewHolder holder, final int position) {


        //  holder.statuss.setText(dataModelArrayList.get(position).getStatus());
        holder.category.setText(dataModelArrayList.get(position));



    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  category;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.category = itemView.findViewById(R.id.category);


//
        }

    }




}
