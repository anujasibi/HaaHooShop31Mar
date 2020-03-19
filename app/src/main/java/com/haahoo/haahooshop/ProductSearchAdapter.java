package com.haahoo.haahooshop;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.haahoo.haahooshop.utils.DataTransferInterface;
import com.haahoo.haahooshop.utils.SessionManager;

import java.util.ArrayList;


public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ViewHolder> {

    private ArrayList<String> names;

    DataTransferInterface dtInterface;

    private OnItemClicked onClick;
    Context context;
    SessionManager sessionManager;
    Activity mActivity;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }




    public ProductSearchAdapter(ArrayList<String> names,Context context,DataTransferInterface dataTransferInterface) {
        this.context=context;
        this.names = names;
        sessionManager=new SessionManager(context);
        this.dtInterface = dataTransferInterface;

    }
    public void filterList(ArrayList<String> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textViewName.setText(names.get(position));
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sessionManager.setsearch(holder.textViewName.getText().toString());
              // dtInterface.onSetValues(holder.textViewName.getText().toString());
                dtInterface.onSetValues(holder.textViewName.getText().toString());
            //   Toast.makeText(context,"You clicked "+holder.textViewName.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }
    }
}