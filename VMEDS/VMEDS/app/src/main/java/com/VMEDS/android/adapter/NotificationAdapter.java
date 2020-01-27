package com.VMEDS.android.adapter;

/**
 * Created by Yogesh on 6/8/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VMEDS.android.R;
import com.VMEDS.android.model.NotificationDetail;
import com.VMEDS.android.utils.Global_Typeface;

import java.util.Vector;

public class NotificationAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private Vector<NotificationDetail> vNotification;
    private Global_Typeface globalTypeface;
    private LinearLayout layoutVendorList;
    private TextView txtNotificationTitle, txtNotificationTime, txtNotificationDesc;

    public NotificationAdapter(Context context, Vector<NotificationDetail> vNotification) {
        mContext = context;
        globalTypeface = new Global_Typeface(context);
        this.vNotification = vNotification;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return vNotification.size();
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
        View itemView = mLayoutInflater.inflate(R.layout.notification_row_item, null);
        txtNotificationTitle = (TextView) itemView.findViewById(R.id.txtNotificationTitle);
        txtNotificationDesc = (TextView) itemView.findViewById(R.id.txtNotificationDesc);
        txtNotificationTime = (TextView) itemView.findViewById(R.id.txtNotificationTime);

        txtNotificationTitle.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
        txtNotificationTime.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
        txtNotificationDesc.setTypeface(globalTypeface.TypeFace_Roboto_Regular());

        txtNotificationDesc.setText(((NotificationDetail) vNotification.elementAt(position)).description);
        txtNotificationTitle.setText(((NotificationDetail) vNotification.elementAt(position)).title);
        txtNotificationTime.setText(((NotificationDetail) vNotification.elementAt(position)).time);

        return itemView;
    }

}