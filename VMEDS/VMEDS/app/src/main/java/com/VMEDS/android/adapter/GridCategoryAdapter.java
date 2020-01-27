package com.VMEDS.android.adapter;

/**
 * Created by Yogesh on 6/7/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.VMEDS.android.VMEDS;
import com.VMEDS.android.NewCategoryWiseProduct;
import com.VMEDS.android.R;
import com.VMEDS.android.model.CategoryDetail;
import com.VMEDS.android.utils.Global_Typeface;

import java.util.Vector;

public class GridCategoryAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    TextView txtService;
    private Vector<CategoryDetail> vCategory;
    Global_Typeface globalTypeface;
    private ImageView imgService;
    //    AVLoadingIndicatorView loadingAvi;
    CardView card_view;

    public GridCategoryAdapter(Context context, Vector<CategoryDetail> vCategory) {
        mContext = context;
        globalTypeface = new Global_Typeface(context);
        this.vCategory = vCategory;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return vCategory.size();
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
        View itemView = mLayoutInflater.inflate(R.layout.row_category, null);
        imgService = (ImageView) itemView.findViewById(R.id.imgService);
        card_view = (CardView) itemView.findViewById(R.id.card_view);
//        loadingAvi = (AVLoadingIndicatorView) itemView.findViewById(R.id.loadingAvi);
//        loadingAvi.setVisibility(View.VISIBLE);
        try {

//                    JSONObject mainObject = new JSONObject(((ProductDetail) vProductList.elementAt(position)).images);
//                    Log.e("full", " " + ((ProductDetail) vProductList.elementAt(position)).images);
//                    Log.e("STr", mainObject.getString("1") + " ");
            //
            String imageStr = ((CategoryDetail) vCategory.elementAt(position)).images;
            Log.e("Hello", imageStr);
            if (!imageStr.contains("http://"))
                imageStr = "http://" + imageStr;
            // imageStr.replace("localhost", "192.168.0.3");
            Glide.with(mContext).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    Log.e("Done", "Done0");
                    Log.d("TAG", "onException: "+e);

//                    loadingAvi.setVisibility(View.GONE);
//                    loadingAvi.smoothToHide();
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    Log.e("Done", "Done1");
//                    loadingAvi.setVisibility(View.GONE);
//                    loadingAvi.smoothToHide();
                    return false;
                }
            }).into(imgService);

        } catch (Exception e) {
            e.printStackTrace();
        }

        txtService = (TextView) itemView.findViewById(R.id.txtService);

        txtService.setText(((CategoryDetail) vCategory.elementAt(position)).category_name);
        txtService.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
        final int pos = position;
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  bkLayout.setBackgroundResource(R.drawable.ripplebk);
                ((VMEDS) mContext.getApplicationContext()).setObjSubCat(((CategoryDetail) vCategory.elementAt(pos)));
                ((VMEDS) mContext.getApplicationContext()).setCurrentIndex(pos);
                ((VMEDS) mContext.getApplicationContext()).setFromWholeSaler(0);
                String pass=vCategory.elementAt(pos).category_id.toString();
                Intent intent = new Intent(mContext, NewCategoryWiseProduct.class);
                intent.putExtra("id",pass);
                mContext.startActivity(intent);
            }
        });
//        bkLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Intent i = new Intent(mContext, SubServicesActivity.class);
//                mContext.startActivi ty(i);
//                return false;
//            }
//        });
        return itemView;
    }

}