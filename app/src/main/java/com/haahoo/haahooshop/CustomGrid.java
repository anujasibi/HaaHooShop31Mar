package com.haahoo.haahooshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.haahoo.haahooshop.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGrid extends ArrayAdapter {
    ArrayList<Itemprod> birdList = new ArrayList<>();
    private Context mContext;
    private int selected_position = -1;
    SessionManager sessionManager;


    public CustomGrid(Context context, int textViewResourceId, ArrayList<Itemprod> objects) {
        super(context, textViewResourceId, objects);
        birdList = objects;
        sessionManager=new SessionManager(getContext());
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub

            return super.getCount();
    }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


            View grid = convertView;

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.gridviewadd, null);


            TextView textView = (TextView) grid.findViewById(R.id.textView);
            ImageView imageView = (ImageView)grid.findViewById(R.id.profile_image);
            textView.setText(birdList.get(position).getName());
          //  Picasso.with(getContext()).load(birdList.get(position).getImage()).into(imageView);
            Picasso.get().load(birdList.get(position).getImage()).into(imageView);
            final  CheckBox checkBox=(CheckBox)grid.findViewById(R.id.checkBox3);
/*checkBox.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


            checkBox.setTag(position);

           // checkBox.setChecked(selectedPosition == position);

           // selectedPosition = position;
        if (checkBox.getTag().equals(position)){
            checkBox.setChecked(true);
        }


            Log.d("selected","bnkjbkjb"+checkBox.getTag()+position);
//            if(selectedPosition == position){
//                checkBox.setChecked(true);
//            }
//        if(!(selectedPosition == position)){
//                checkBox.setChecked(false);
//            }
    }
});*/
            if(selected_position==position)
            {
                checkBox.setChecked(true);

            }
            else
            {
                checkBox.setChecked(false);

            }

            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {



                    if (((CheckBox) view).isChecked())
                    {
                        selected_position= position;
                        sessionManager.setPid(birdList.get(position).getId());
                        sessionManager.setcatid(birdList.get(position).getId());
                        Log.d("mmmmmid","mm"+sessionManager.getPid());
                        Log.d("mmmmmid","mm"+sessionManager.getPid());
                     //   Toast.makeText(getContext(),"Checkedname"+sessionManager.getPid(),Toast.LENGTH_SHORT).show();


                    }
                    /*if (!(((CheckBox) view).isChecked())){
                        sessionManager.setcatid("");
                    }*/

                    else
                    {
                        selected_position=-1;
                        sessionManager.setcatid("");
                    }


                    notifyDataSetChanged();


                }
            });
        return grid;
    }
}