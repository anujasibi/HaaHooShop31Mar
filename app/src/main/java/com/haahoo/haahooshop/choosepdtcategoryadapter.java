package com.haahoo.haahooshop;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.haahoo.haahooshop.utils.SessionManager;

import java.util.List;

public class choosepdtcategoryadapter extends
        RecyclerView.Adapter<choosepdtcategoryadapter.ViewHolder> {

    private List<OffersModel> offersList;
    private Context context;
    public String id;
    SessionManager sessionManager;

    private int lastSelectedPosition = -1;

    public choosepdtcategoryadapter(List<OffersModel> offersListIn
            , Context ctx) {
        offersList = offersListIn;
        context = ctx;
        sessionManager=new SessionManager(ctx);
    }

    @Override
    public choosepdtcategoryadapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.radio, parent, false);

        choosepdtcategoryadapter.ViewHolder viewHolder =
                new choosepdtcategoryadapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(choosepdtcategoryadapter.ViewHolder holder,
                                 int position) {
        OffersModel offersModel = offersList.get(position);

        holder.offerAmount.setText("" + offersModel.getName());
        holder.idn.setText(offersModel.getRadio());



       id=offersModel.getRadio();
        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        holder.selectionState.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView offerAmount,idn;
        public RadioButton selectionState;

        public ViewHolder(final View view) {
            super(view);
            offerAmount = (TextView) view.findViewById(R.id.offer_amount_txt);
            selectionState = (RadioButton) view.findViewById(R.id.offer_select);
            idn=(TextView) view.findViewById(R.id.id);

            selectionState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    /*Toast.makeText(choosepdtcategoryadapter.this.context,
                            "selected offer is " + offerAmount.getText()+idn.getText(),
                            Toast.LENGTH_LONG).show();*/

                    sessionManager.setcatrid(idn.getText().toString());
                 //   Log.d("mmmmmmmmmm","mm"+sessionManager.getcatrid());

                    context.startActivity(new Intent(context,ProductSearch.class));





                }
            });
        }
    }
}