package com.VMEDS.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.VMEDS.android.adapter.GridCategoryAdapter;
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.model.CategoryDetail;
import com.VMEDS.android.model.ProductDetail;
import com.VMEDS.android.model.SliderDetail;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class HomeFragment extends Fragment {
    private ViewPager pagerNewProducts, pagerPopularProducts, pagerBestSellingProducts, slider;
    private StaticData static_data;
    private Vector<ProductDetail> vSliderSarees;
    private Vector<SliderDetail> vSliderImages;
    StringRequest mStringRequest;
    RequestQueue mRequestQueue;
    ProductPagerAdapter productPagerAdapter;
    SliderAdapter sliderAdapter;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private int page = 0;
    private GridView gridViewCategories, main;
    private Handler handler = new Handler();
    private int delay = 2000;
    Runnable runnable;
    Global_Typeface global_typeface;
    private int position = 0;
    private TextView txtViewAllProducts;
    Button upload;
    private Vector<CategoryDetail> vCategory;
    ToggleButton toggleButton;
    ImageView imageView;
    CardView cardView;

    LinearLayout linearLayout;
    Context context;

    String[] strings = new String[]{
            "Prescriptions",
            "Non-Prescriptions"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        slider = (ViewPager) v.findViewById(R.id.slider);


        //pagerSarees = (android.support.v4.view.ViewPager) v.findViewById(R.id.pagerSarees);
        pagerPopularProducts = (ViewPager) v.findViewById(R.id.pagerPopularProducts);

        pagerBestSellingProducts = (ViewPager) v.findViewById(R.id.pagerBestSellingProducts);

        pagerNewProducts = (ViewPager) v.findViewById(R.id.pagerNewProducts);
        //pagerKurtas = (android.support.v4.view.ViewPager) v.findViewById(R.id.pagerKurtas);
        pager_indicator = (LinearLayout) v.findViewById(R.id.viewPagerCountDots);

        static_data = new StaticData();
        global_typeface = new Global_Typeface(getActivity());
        getFeaturedProducts();

        TextView tv19f5 = (TextView) v.findViewById(R.id.f5tv19);
        tv19f5.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        TextView tv20f5 = (TextView) v.findViewById(R.id.f5tv20);
        tv20f5.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        TextView tv21f5 = (TextView) v.findViewById(R.id.f5tv21);
        tv21f5.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        txtViewAllProducts = (TextView) v.findViewById(R.id.txtViewAllProducts);
        txtViewAllProducts.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        upload=v.findViewById(R.id.upload);
        upload.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),UserLogin.class);
                startActivity(intent);
            }
        });




        gridViewCategories = (GridView) v.findViewById(R.id.gridViewCategories);
        vCategory = ((VMEDS) getActivity().getApplicationContext()).getvCategoryList();
        gridViewCategories.setAdapter(new GridCategoryAdapter(getActivity(), vCategory));

//        setGridViewHeightBasedOnChildren(gridViewCategories, 2);

        cardView=v.findViewById(R.id.card_view);
        imageView=v.findViewById(R.id.plus);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                cardView.startAnimation(myAnim);

                Snackbar snackbar = Snackbar
                        .make(v, "Why VMEDS ? Why VMEDS ? Why VMEDS\nWhy VMEDS ? Why VMEDS",10000)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Snackbar snackbar1 = Snackbar.make(v, "Message is restored!", Snackbar.LENGTH_SHORT);
//                                snackbar1.show();
                            }
                        });

                snackbar.show();
            }
        });

        linearLayout=v.findViewById(R.id.info_layout);

        toggleButton=v.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (toggleButton.isChecked()) {

                   linearLayout.setVisibility(View.VISIBLE);
                } else {

                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        return v;
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void setGridViewHeightBasedOnChildren(final GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;
//
//        View listItem = listAdapter.getView(0, null, gridView);
//        listItem.measure(0, 0);
//        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        items = vCategory.size();
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            //totalHeight *= rows;
        }

        Log.e("Hello", "Hi" + rows);
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = rows * convertPixelsToDp((float) 118);
        gridView.setLayoutParams(params);

//        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                gridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                View lastChild = gridView.getChildAt(gridView.getChildCount() - 1);
//                gridView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, lastChild.getBottom()));
//            }
//        });

    }

    public int convertPixelsToDp(float dp) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }


    public class SliderAdapter extends PagerAdapter {

        private Context mContext;
        private Vector<SliderDetail> vSliderImages;
        private ImageView product_image;
        private AVLoadingIndicatorView loadingAvi;

        public SliderAdapter(Context mContext, Vector<SliderDetail> vSliderImages) {
            this.mContext = mContext;
            this.vSliderImages = vSliderImages;
        }

        @Override
        public int getCount() {
            return vSliderImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ViewGroup) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.main_home_slider, container, false);

            product_image = (ImageView) itemView.findViewById(R.id.product_image);
            loadingAvi = (AVLoadingIndicatorView) itemView.findViewById(R.id.loadingAvi);
            loadingAvi.setVisibility(View.VISIBLE);
            try {


                String imageStr = ((SliderDetail) vSliderImages.elementAt(position)).image;

                if (!imageStr.contains("http://"))
                    imageStr = "http://" + imageStr;
                Glide.with(getActivity()).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("Done", "Done0");
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e("Done", "Done1");
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        return false;
                    }
                }).into(product_image);

            } catch (Exception e) {
                e.printStackTrace();
            }

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ViewGroup) object);
        }
    }

    class ProductPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        Vector<ProductDetail> vSliderSarees;
        AVLoadingIndicatorView loadingAvi;
        ImageView product_image;
        Global_Typeface typeface;

        public ProductPagerAdapter(Context context, Vector<ProductDetail> vSliderSarees) {
            mContext = context;
            this.vSliderSarees = vSliderSarees;
            typeface = new Global_Typeface(context);
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return vSliderSarees.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ViewGroup) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.slider_item, container, false);

            product_image = (ImageView) itemView.findViewById(R.id.product_image);
            loadingAvi = (AVLoadingIndicatorView) itemView.findViewById(R.id.loadingAvi);
            loadingAvi.setVisibility(View.VISIBLE);
            LinearLayout layoutProduct = (LinearLayout) itemView.findViewById(R.id.layoutProduct);
            layoutProduct.setPadding(10, 0, 0, 0);
            try {

                JSONObject mainObject = new JSONObject(((ProductDetail) vSliderSarees.elementAt(position)).product_image);
                Log.e("STr", mainObject.getString("1") + " ");
                //
                String imageStr = mainObject.getString("1");

                if (!imageStr.contains("http://"))
                    imageStr = "http://" + imageStr;
                Glide.with(getActivity()).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("Done", "Done0");
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e("Done", "Done1");
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        return false;
                    }
                }).into(product_image);

//                Glide.with(mContext)
//                        // .load(mainObject.getString("1"))
//                        .load("http://NeegamBazaar.in/uploads/product_image/product_116_1.jpg")
//                                // use dontAnimate and not crossFade to avoid a bug with custom views
//
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .listener(new RequestListener<String, GlideDrawable>() {
//                            @Override
//                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                // do something
//                                loadingAvi.setVisibility(View.GONE);
//                                Log.e("Done", "Done0");
//                                loadingAvi.hide();
//                                product_image.setImageResource(R.mipmap.ic_launcher);
//                                return true;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                // do something
//                                Log.e("Done", "Done0Res");
//                                loadingAvi.setVisibility(View.GONE);
//                                return true;
//                            }
//                        })
//                        .into(product_image);

            } catch (Exception e) {
                e.printStackTrace();
            }


            TextView product_name = (TextView) itemView.findViewById(R.id.product_name);
            product_name.setText(((ProductDetail) vSliderSarees.elementAt(position)).product_name);
            product_name.setTypeface(typeface.TypeFace_Roboto_Regular());

            TextView product_rs = (TextView) itemView.findViewById(R.id.product_rs);
            TextView product_sale_rs = (TextView) itemView.findViewById(R.id.product_sale_rs);
            TextView product_discount = (TextView) itemView.findViewById(R.id.product_discount);
            product_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
            product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Regular());
            product_discount.setTypeface(typeface.TypeFace_Roboto_Bold());
            if (((ProductDetail) vSliderSarees.elementAt(position)).discount == null) {
                product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vSliderSarees.elementAt(position)).selling_price);
                product_rs.setVisibility(View.GONE);
                product_discount.setVisibility(View.GONE);
                product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
            } else {
                if (((ProductDetail) vSliderSarees.elementAt(position)).discount.length() == 0) {
                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vSliderSarees.elementAt(position)).selling_price);
                    product_rs.setVisibility(View.GONE);
                    product_discount.setVisibility(View.GONE);
                    product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());

                } else {
                    product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vSliderSarees.elementAt(position)).product_price);
                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vSliderSarees.elementAt(position)).product_price);
                    product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    product_discount.setText("Discount " + ((ProductDetail) vSliderSarees.elementAt(position)).discount + "%");
                }
            }
            container.addView(itemView);
            Log.e("Hello", ((ProductDetail) vSliderSarees.elementAt(position)).product_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((VMEDS) getActivity().getApplicationContext()).setProduct_id(((ProductDetail) vSliderSarees.elementAt(position)).category_id);
                    Intent i = new Intent(getActivity(), ProductDetailActivity.class);
                    //getActivity().finish();
                    startActivity(i);
                }
            });

            return itemView;


        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ViewGroup) object);
        }

        @Override
        public float getPageWidth(int position) {
            return (0.5f);
        }
    }

    private void setUiPageViewController() {

        dotsCount = sliderAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    public void getHomeImageSliderData() {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.HOME_IMAGE_SLIDER_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vSliderImages = new Vector<SliderDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("slider_data"));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                SliderDetail obj = new SliderDetail(jsonObject2.getString("img_id"), jsonObject2.getString("product_id"), jsonObject2.getString("product_discription"), jsonObject2.getString("images"));
                                vSliderImages.add((SliderDetail) obj);
                            }
                            sliderAdapter = new SliderAdapter(getActivity(), vSliderImages);
                            slider.setAdapter(sliderAdapter);
                            setUiPageViewController();
                            handler = new Handler();
                            runnable = new Runnable() {
                                public void run() {
                                    if (sliderAdapter.getCount() == page) {
                                        page = 0;
                                    } else {
                                        page++;
                                    }
                                    slider.setCurrentItem(page, true);
                                    handler.postDelayed(this, delay);
                                }
                            };

                            slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < dotsCount; i++) {
                                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                                    }

                                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                            // spCountry.setAdapter(null);


                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getHomeImageSliderData();
                }
            }, 5000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

   /* public void getRechargeData() {

        String url =static_data.BASE_URL+ static_data.RECHARGE_DATA_API_URL;
        Log.i("URL", url);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Res", response);
                    vSliderSarees = new Vector<ProductDetail>();
                    JSONObject jsonObject1 = new JSONObject(response);
                    if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                        // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                        JSONArray jsonArray = new JSONArray(jsonObject1.getString("product_data"));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            ProductDetail obj = new ProductDetail(jsonObject2.getString("product_id"), jsonObject2.getString("title"), jsonObject2.getString("sale_price"), jsonObject2.getString("purchase_price"), jsonObject2.getString("num_of_imgs"), jsonObject2.getString("images"), jsonObject2.getString("discount"), jsonObject2.getString("number_of_view"), jsonObject2.getString("rating_num"), jsonObject2.getString("rating_total"), jsonObject2.getString("discount"), jsonObject2.getString("discount_type"), jsonObject2.getString("add_timestamp"));

                            vSliderSarees.add((ProductDetail) obj);
                        }
                        productPagerAdapter = new ProductPagerAdapter(getActivity(), vSliderSarees);
                        // spCountry.setAdapter(null);

                        //pagerSarees.setAdapter(productPagerAdapter);
                        //pagerKurtas.setAdapter(productPagerAdapter);
                        pagerPopularProducts.setAdapter(productPagerAdapter);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
            }
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("category", "lehengas");

                return params;
            }
        };
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);
    }*/


    public void getFeaturedProducts() {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.FEATURED_PRODUCT_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vSliderSarees = new Vector<ProductDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("product_data"));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ProductDetail obj = new ProductDetail(jsonObject2.getString("product_id"), jsonObject2.getString("p_id"), jsonObject2.getString("product_name"), jsonObject2.getString("selling_price"), jsonObject2.getString("product_price"), jsonObject2.getString("about_product"), jsonObject2.getString("febric"), jsonObject2.getString("shipping_charge"), jsonObject2.getString("work"), jsonObject2.getString("product_quantity"), jsonObject2.getString("category_id"), jsonObject2.getString("product_description"), jsonObject2.getString("product_specification"), jsonObject2.getString("product_status"), jsonObject2.getString("product_image"), jsonObject2.getString("product_color"), "0");

                                vSliderSarees.add((ProductDetail) obj);
                            }
                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vSliderSarees);
                            // spCountry.setAdapter(null);

                            //pagerSarees.setAdapter(productPagerAdapter);
                            //pagerKurtas.setAdapter(productPagerAdapter);
                            pagerPopularProducts.setAdapter(productPagerAdapter);
                            pagerBestSellingProducts.setAdapter(productPagerAdapter);
                            pagerNewProducts.setAdapter(productPagerAdapter);

                        } else {

                        }
                        getHomeImageSliderData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();


                    return params;
                }
            };
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getFeaturedProducts();
                }
            }, 5000);
        }
    }
}
