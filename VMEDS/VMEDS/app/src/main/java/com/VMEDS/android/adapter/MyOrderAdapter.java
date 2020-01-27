package com.VMEDS.android.adapter;

/**
 * Created by Yogesh on 6/8/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.VMEDS.android.R;
import com.VMEDS.android.model.OrderDetail;
import com.VMEDS.android.utils.Global_Typeface;

import java.util.Vector;

public class MyOrderAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private Vector<OrderDetail> vOrders;
    private Global_Typeface globalTypeface;
    private TextView txtOrderDate, txtOrderID, txtOrderStatus;

    public MyOrderAdapter(Context context, Vector<OrderDetail> vOrders) {
        mContext = context;
        globalTypeface = new Global_Typeface(context);
        this.vOrders = vOrders;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return vOrders.size();
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
        View itemView = mLayoutInflater.inflate(R.layout.row_order_item, null);
        txtOrderDate = (TextView) itemView.findViewById(R.id.txtOrderDate);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.txtOrderStatus);
        txtOrderID = (TextView) itemView.findViewById(R.id.txtOrderID);

        txtOrderID.setTypeface(globalTypeface.TypeFace_Roboto_Regular());

        txtOrderDate.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
        txtOrderStatus.setTypeface(globalTypeface.TypeFace_Roboto_Regular());

        txtOrderDate.setText(Html.fromHtml("<b>Order Date: </b>" + ((OrderDetail) vOrders.elementAt(position)).order_date));
        txtOrderID.setText(Html.fromHtml("<b>Order ID: </b>" + ((OrderDetail) vOrders.elementAt(position)).ref_id));
        txtOrderStatus.setText(((OrderDetail) vOrders.elementAt(position)).status);

        if(((OrderDetail) vOrders.elementAt(position)).status.equalsIgnoreCase("pending"))
            txtOrderStatus.setTextColor(Color.parseColor("#e04f5f"));
        else if(((OrderDetail) vOrders.elementAt(position)).status.equalsIgnoreCase("completed"))
            txtOrderStatus.setTextColor(Color.parseColor("#7fb714"));
        else if(((OrderDetail) vOrders.elementAt(position)).status.equalsIgnoreCase("ongoing"))
            txtOrderStatus.setTextColor(Color.parseColor("#02bee1"));
        else if(((OrderDetail) vOrders.elementAt(position)).status.equalsIgnoreCase("cancel"))
            txtOrderStatus.setTextColor(Color.parseColor("#c60505"));
        return itemView;
    }

}