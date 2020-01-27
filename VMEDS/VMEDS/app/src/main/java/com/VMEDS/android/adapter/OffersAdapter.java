package com.VMEDS.android.adapter;

/**
 * Created by Yogesh on 6/8/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.VMEDS.android.R;
import com.VMEDS.android.model.OfferDetail;
import com.VMEDS.android.utils.Global_Typeface;

import java.util.Vector;

public class OffersAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private Vector<OfferDetail> vOffer;
    private Global_Typeface globalTypeface;
    private TextView txtOffer, txtOfferCode;


    public OffersAdapter(Context context, Vector<OfferDetail> vOffer) {
        mContext = context;
        globalTypeface = new Global_Typeface(context);
        this.vOffer = vOffer;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return vOffer.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = mLayoutInflater.inflate(R.layout.row_offer_item, null);
        txtOffer = (TextView) itemView.findViewById(R.id.txtOffer);
        txtOfferCode = (TextView) itemView.findViewById(R.id.txtOfferCode);
        txtOffer.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
        txtOfferCode.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
        txtOffer.setText(((OfferDetail) vOffer.elementAt(position)).description);
        txtOfferCode.setText(((OfferDetail) vOffer.elementAt(position)).name);
        return itemView;
    }

}