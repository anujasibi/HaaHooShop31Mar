package com.haahoo.haahooshop;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haahoo.haahooshop.utils.SessionManager;

public class Customad extends ArrayAdapter<RowItem> {

    Context context;
    public String id;
    SessionManager sessionManager;

    public Customad(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;

        sessionManager=new SessionManager(context);

    }

    /*private view holder class*/
    private class ViewHolder {

        TextView txtTitle;
        TextView txtDesc;
        ImageView imageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_new, null);
            holder = new ViewHolder();

            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItem.getTitle());
        id=rowItem.getId();
      /*  sessionManager.setshopid(rowItem.getId());
        Log.d("dasdasd","mm"+sessionManager.getshopid());*/



        return convertView;
    }

}