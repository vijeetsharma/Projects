package com.VMEDS.android;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.utils.Global_Typeface;

import java.util.Vector;

public class OrderDetailActivity extends AppCompatActivity {
    private ListView listMyOrders;
    private Vector<AddtoCartDetail> vDetails;
    private TextView ctvTitle;
    private ImageView civSearch,civAddcart;
    private Global_Typeface global_typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        global_typeface = new Global_Typeface(OrderDetailActivity.this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setVisibility(View.VISIBLE);
        // textCount.setVisibility(View.VISIBLE);
        ctvTitle.setText("Order Detail");
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setVisibility(View.GONE);
        civSearch.setVisibility(View.GONE);
        vDetails = ((VMEDS) getApplicationContext()).getvDetails();

        listMyOrders = (ListView) findViewById(R.id.listMyOrders);
        listMyOrders.setAdapter(new OrderListAdapter(OrderDetailActivity.this, vDetails));

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class OrderListAdapter extends BaseAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        Vector<AddtoCartDetail> vAddtoCartList;
        private ViewHolder viewHolder;

        Global_Typeface globalTypeface;

        public OrderListAdapter(Context context, Vector<AddtoCartDetail> vAddtoCartList) {
            mContext = context;
            this.vAddtoCartList = vAddtoCartList;
            globalTypeface = new Global_Typeface(context);
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public class ViewHolder {
            TextView product_quantity;
            TextView product_title, product_price, product_total_price, txtMoveToWishList;
            ImageView product_image, cart_minus, cart_plus, imgDelete;
            LinearLayout quantity_layout;
        }

        public int getCount() {
            return vAddtoCartList.size();
        }

        public Object getItem(int position) {
            return vAddtoCartList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View itemView, ViewGroup parent) {
            itemView = mLayoutInflater.inflate(R.layout.layout_my_order, null);
            viewHolder = new ViewHolder();
            viewHolder.product_image = (ImageView) itemView.findViewById(R.id.product_image);
            viewHolder.cart_plus = (ImageView) itemView.findViewById(R.id.cart_plus);
            viewHolder.cart_minus = (ImageView) itemView.findViewById(R.id.cart_minus);
            viewHolder.product_title = (TextView) itemView.findViewById(R.id.product_title);
            viewHolder.imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            viewHolder.txtMoveToWishList = (TextView) itemView.findViewById(R.id.txtMoveToWishList);
            viewHolder.product_price = (TextView) itemView.findViewById(R.id.product_price);
            viewHolder.product_quantity = (TextView) itemView.findViewById(R.id.product_quantity);
            viewHolder.product_total_price = (TextView) itemView.findViewById(R.id.product_total_price);
            viewHolder.quantity_layout = (LinearLayout) itemView.findViewById(R.id.quantity_layout);
//            if (((VMEDS) getApplicationContext()).getFromWishList() == 0)
                viewHolder.quantity_layout.setVisibility(View.VISIBLE);
//            else
//                viewHolder.quantity_layout.setVisibility(View.GONE);

            if (((VMEDS) getApplicationContext()).getFromWishList() == 0)
                viewHolder.txtMoveToWishList.setText("Move to WishList");
            else
                viewHolder.txtMoveToWishList.setText("Move to Cart");

            try {

                //
                String imageStr = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).image_url;

                if (!imageStr.contains("http://"))
                    imageStr = "http://" + imageStr;
                Glide.with(OrderDetailActivity.this).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("Done", "Done0");

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e("Done", "Done1");

                        return false;
                    }
                }).into(viewHolder.product_image);


            } catch (Exception e) {
                e.printStackTrace();
            }

            viewHolder.product_title.setText(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_title);
            viewHolder.product_title.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
            viewHolder.product_price.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
            viewHolder.product_total_price.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
            viewHolder.product_quantity.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
            viewHolder.product_price.setText(getString(R.string.Rs) + " " + ((AddtoCartDetail) vAddtoCartList.elementAt(position)).price);
            viewHolder.product_quantity.setText("Items: " + ((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity);
            viewHolder.product_total_price.setText(getString(R.string.Rs) + " " + Float.parseFloat(((AddtoCartDetail) vAddtoCartList.elementAt(position)).price) * Float.parseFloat(((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity) + "");
            viewHolder.imgDelete.setVisibility(View.GONE);
            viewHolder.cart_plus.setVisibility(View.GONE);
            viewHolder.cart_minus.setVisibility(View.GONE);

            viewHolder.txtMoveToWishList.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
            viewHolder.txtMoveToWishList.setVisibility(View.GONE);

//            viewHolder.cart_plus.setVisibility(View.GONE);
//            viewHolder.cart_minus.setVisibility(View.GONE);

//            viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    addtoCart_list.delete(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id);
//                }
//            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((VMEDS) getActivity().getApplicationContext()).setProduct_id(((ProductDetail) vProductList.elementAt(position)).product_id);
//                    Intent i = new Intent(getActivity(), com.neegambazaar.android.ProductDetailActivity.class);
//                    getActivity().finish();
//                    startActivity(i);
//                }
//            });
            itemView.setTag(viewHolder);

            return itemView;
        }


    }

}
