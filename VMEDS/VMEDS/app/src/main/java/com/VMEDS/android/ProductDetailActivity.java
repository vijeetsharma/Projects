package com.VMEDS.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

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
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.model.ProductDetail;
import com.VMEDS.android.model.RGBColor;
import com.VMEDS.android.utils.AddToCart;
import com.VMEDS.android.utils.AddtoCart_List;
import com.VMEDS.android.utils.Global_Typeface;
import com.VMEDS.android.utils.Wish;
import com.VMEDS.android.utils.Wish_List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView txtProductTitle, txtSoldBy, txtCategory, txtProductReview, txtProductPrice, txtProductColors, product_rs, product_sale_rs, product_discount;
    public Global_Typeface global_typeface;
    LinearLayout layoutColors;
    RatingBar productRatingbar;
    private ViewPager slider, pager;
    SlidingDrawer slidingDrawer;
    RelativeLayout layoutMoreDetail;
    TabLayout tabLayout;
    Button btnMoreDetail;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    AVLoadingIndicatorView loadingAviMain;
    private ProductDetail obj;
    private SliderAdapter sliderAdapter;
    private String[] sliderImages;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private int page = 0;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private Vector<RGBColor> vColors;
    static String TWITTER_CONSUMER_KEY = "Pc3E9IYVhLyRAGFF7rxjJ8HoE";
    static String TWITTER_CONSUMER_SECRET = "xJPfwKUPrqp7o7tBG2Y5daB3QnAM54JLZVk8lq7KMeRvEBGnrV";
    private ImageView imgAddtoCart, imgWishList, imgBag, imgShare;
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    private static SharedPreferences mSharedPreferences;
    private Wish_List item;
    AddtoCart_List cart_item;
    private RelativeLayout imagePagerLayout;
    private FrameLayout shareLayout;
    private LinearLayout hPanel;
    private StaticData static_data;
    private String product_price;
    private TextView ctvTitle, textCount;
    private ImageView civSearch, civAddcart;
    private Vector<AddtoCartDetail> vCartList;
    private ScrollView scrProductDetail;
    private String user_id;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);


        static_data = new StaticData();
        vColors = new Vector<RGBColor>();
        global_typeface = new Global_Typeface(ProductDetailActivity.this);
        cart_item = new AddtoCart_List(ProductDetailActivity.this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);

        textCount = (TextView) findViewById(R.id.textCount);
        scrProductDetail = (ScrollView) findViewById(R.id.scrProductDetail);
        ctvTitle.setVisibility(View.VISIBLE);
        // textCount.setVisibility(View.VISIBLE);
        ctvTitle.setText("Detail");
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VMEDS) getApplicationContext()).setFromWishList(0);
                Intent i = new Intent(ProductDetailActivity.this, MyOrdersActivity.class);
                finish();
                startActivity(i);
            }
        });
        civSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailActivity.this, SearchActivity.class);
                finish();
                startActivity(i);
            }
        });
        civSearch.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }
        textCount.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        vCartList = cart_item.getCartList();
        if (vCartList.size() > 0) {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(String.valueOf(vCartList.size()));

        }

        txtProductTitle = (TextView) findViewById(R.id.txtProductTitle);
        txtSoldBy = (TextView) findViewById(R.id.txtSoldBy);
        imgAddtoCart = (ImageView) findViewById(R.id.imgAddtoCart);
        imgBag = (ImageView) findViewById(R.id.imgBag);
        item = new Wish_List(ProductDetailActivity.this);
        imgWishList = (ImageView) findViewById(R.id.imgWishList);
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imagePagerLayout = (RelativeLayout) findViewById(R.id.imagePagerLayout);
        shareLayout = (FrameLayout) findViewById(R.id.shareLayout);
        imagePagerLayout.setVisibility(View.INVISIBLE);
        hPanel = (LinearLayout) findViewById(R.id.hPanel);
        hPanel.setVisibility(View.GONE);
        txtProductTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtSoldBy.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        slider = (ViewPager) findViewById(R.id.slider);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        txtCategory.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        productRatingbar = (RatingBar) findViewById(R.id.productRatingbar);
        txtProductReview = (TextView) findViewById(R.id.txtProductReview);
        txtProductReview.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtProductPrice = (TextView) findViewById(R.id.txtProductPrice);
        txtProductPrice.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        product_discount = (TextView) findViewById(R.id.product_discount);
        imgAddtoCart.setVisibility(View.GONE);
        product_rs = (TextView) findViewById(R.id.product_rs);
        product_rs.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        product_discount.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        product_sale_rs = (TextView) findViewById(R.id.product_sale_rs);
        product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtProductColors = (TextView) findViewById(R.id.txtProductColors);
        txtProductColors.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        layoutColors = (LinearLayout) findViewById(R.id.layoutColors);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
        layoutMoreDetail = (RelativeLayout) findViewById(R.id.layoutMoreDetail);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.pager);
        btnMoreDetail = (Button) findViewById(R.id.btnMoreDetail);
        btnMoreDetail.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        loadingAviMain = (AVLoadingIndicatorView) findViewById(R.id.loadingAviMain);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

//        getProductDetail(((VMEDS) getApplicationContext()).getProduct_id());

        setDetail();

//        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
//            @Override
//            public void onDrawerOpened() {
//                scrProductDetail.setVisibility(View.GONE);
//            }
//        });
//
//        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
//            @Override
//            public void onDrawerClosed() {
//                scrProductDetail.setVisibility(View.VISIBLE);
//            }
//        });

        if (item.isIteminWishList(((VMEDS) getApplicationContext()).getProduct_id()) == 1) {
            imgWishList.setImageResource(R.drawable.wishlist_sel);
            imgWishList.setColorFilter(getResources().getColor(R.color.dblue), PorterDuff.Mode.SRC_IN);
        } else {
            imgWishList.setImageResource(R.drawable.wishlist);
            imgWishList.setColorFilter(getResources().getColor(R.color.dblue), PorterDuff.Mode.SRC_IN);
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        user_id = pref.getString("user_id", null);
        String wishList = pref.getString("user_wishlist", null);
        if (wishList != null) {
            try {
                JSONArray itemArray = new JSONArray(wishList);
                for (int i = 0; i < itemArray.length(); i++) {
                    String value = (String) itemArray.get(i);
                    if (Integer.parseInt(value) == Integer.parseInt(((VMEDS) getApplicationContext()).getProduct_id())) {
                        imgWishList.setImageResource(R.drawable.wishlist_sel);
                        imgWishList.setColorFilter(getResources().getColor(R.color.dblue), PorterDuff.Mode.SRC_IN);
                        flag = 1;
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }

        imgShare.setVisibility(View.VISIBLE);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = obj.product_image;
//                try {
//                    String string = obj.product_image;
//                    URL url = new URL(string);
//                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                } catch (IOException e) {
//                    System.out.println(e);
//                }
//
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("text/plain");
//                String extraText = obj.product_name;
//                share.putExtra(Intent.EXTRA_TEXT, extraText);
//                startActivity(Intent.createChooser(share, "Share content via"));


                Uri imageUri = Uri.parse(string);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, obj.product_name);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/jpeg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "send"));

            }
        });
        /*imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(ProductDetailActivity.this);
                //Creating a view to get the dialog box
                View shareByDialog = li.inflate(R.layout.layout_share, null);

                final DialogPlus alertDialog = DialogPlus.newDialog(ProductDetailActivity.this)
                        .setContentHolder(new ViewHolder(shareByDialog))
                        .setInAnimation(R.anim.fade_in_center)
                        .setBackgroundColorResId(Color.TRANSPARENT)
                        .setOutAnimation(R.anim.fade_out_center)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                shareByDialog.setBackgroundResource(R.drawable.layout_rounded);
                if (Build.VERSION.SDK_INT >= 21)
                    shareByDialog.setClipToOutline(true);
                alertDialog.show();

                ImageView fbShare = (ImageView) shareByDialog.findViewById(R.id.fbShare);
                fbShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FacebookSdk.sdkInitialize(getApplicationContext());
                        callbackManager = CallbackManager.Factory.create();
                        shareDialog = new ShareDialog(ProductDetailActivity.this);
                        // this part is optional
                        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                            @Override
                            public void onSuccess(Sharer.Result result) {
                                Log.i("Hello", "Share Success");
                                Toast.makeText(ProductDetailActivity.this, "Successfully Shared on Facebook..", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel() {
                                Log.i("Hello", "Share Cancel");
                                Toast.makeText(ProductDetailActivity.this, "Error in Sharing on Facebook..", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onError(FacebookException error) {
                                Log.i("Hello", "Share Error");
                                Toast.makeText(ProductDetailActivity.this, "Error in Sharing on Facebook..", Toast.LENGTH_LONG).show();

                            }


                        });
                        if (ShareDialog.canShow(ShareLinkContent.class)) {
////                            String title = obj.title;
////                            String desc = obj.category + " || " + obj.sub_category;
//
//                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                                    .setContentTitle(title)
//                                    .setContentDescription(desc)
//                                    .setContentUrl(Uri.parse(obj.share_link))
//                                    .build();
//
//                            shareDialog.show(linkContent);
                        }

                    }
                });


                ImageView whatsAppShare = (ImageView) shareByDialog.findViewById(R.id.whatsAppShare);
                whatsAppShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
//                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, obj.share_link);
                        try {
                            startActivity(whatsappIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(), "WhatsApp not installed on Device..", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                ImageView moreShare = (ImageView) shareByDialog.findViewById(R.id.moreShare);
                moreShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
//                        String extraText = obj.share_link;
//                        share.putExtra(Intent.EXTRA_TEXT, extraText);
                        startActivity(Intent.createChooser(share, "Share content via"));
                    }
                });

            }
        });*/
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareLayout.getVisibility() == View.VISIBLE) {
                    shareLayout.animate()
                            .translationY(shareLayout.getHeight())
                            .alpha(0.0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    shareLayout.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
        imgAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddToCart addToCart = new AddToCart();
                addToCart.product_id = obj.product_id;
                addToCart.product_title = obj.product_name;
                addToCart.quantity = "1";
                addToCart.price = obj.selling_price;
                addToCart.image_url = sliderImages[0];
                cart_item.insert(addToCart);
                setCount();

                // Intent i = new Intent(ProductDetailActivity.this, MainActivity.class);
                // finish();
                //startActivity(i);

            }
        });

        imgWishList.setVisibility(View.GONE);

        imgWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0)
                    addWishlist();

//                Intent i = new Intent(ProductDetailActivity.this, MyOrdersActivity.class);
//                finish();
//                startActivity(i);

            }
        });

        imgBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((VMEDS) getApplicationContext()).setFromWishList(0);

                Intent i = new Intent(ProductDetailActivity.this, MyOrdersActivity.class);
                finish();
                startActivity(i);

            }
        });
        // applyFontForToolbarTitle(ProductDetailActivity.this);
    }

    public void setCount() {
        vCartList = cart_item.getCartList();
        if (vCartList.size() > 0) {

            textCount.setVisibility(View.VISIBLE);
            textCount.setText(String.valueOf(vCartList.size()));

        } else
            textCount.setVisibility(View.GONE);

        Toast.makeText(ProductDetailActivity.this, "Item Sucessfully Added to cart ", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    //    private void shareAppLinkViaFacebook() {
//        String urlToShare = obj.share_link;
//
//        try {
//            Intent intent1 = new Intent();
//            intent1.setClassName("com.facebook.katana", "com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
//            intent1.setAction("android.intent.action.SEND");
//            intent1.setType("text/plain");
//            intent1.putExtra("android.intent.extra.TEXT", urlToShare);
//            startActivity(intent1);
//        } catch (Exception e) {
//            // If we failed (not native FB app installed), try share through SEND
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
//            startActivity(intent);
//        }
//    }
    public void addWishlist() {

        if (user_id == null) {
            ((VMEDS) getApplicationContext()).setloginFromHome(0);
            Intent i = new Intent(ProductDetailActivity.this, UserLogin.class);
            //finish();
            startActivity(i);
        } else if (isNetworkConnected()) {
            loadingAviMain.setVisibility(View.VISIBLE);
            imgWishList.setVisibility(View.INVISIBLE);
            String url = static_data.BASE_URL + static_data.WISHLIST_INSERT_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(ProductDetailActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAviMain.setVisibility(View.GONE);
                        loadingAviMain.smoothToHide();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            Wish product_item = new Wish();
                            product_item.product_id = obj.product_id;
                            product_item.product_title = obj.product_name;
                            product_item.quantity = "1";
                            product_item.price = obj.selling_price;
                            product_item.image_url = sliderImages[0];
                            item.insert(product_item);
                            imgWishList.setVisibility(View.VISIBLE);

                            Toast.makeText(ProductDetailActivity.this, "Item Sucessfully Added to WishList ", Toast.LENGTH_SHORT).show();
                            imgWishList.setImageResource(R.drawable.wishlist_sel);
                            imgWishList.setColorFilter(getResources().getColor(R.color.dblue), PorterDuff.Mode.SRC_IN);


                        } else {
                            imgWishList.setVisibility(View.VISIBLE);
                            loadingAviMain.setVisibility(View.GONE);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        imgWishList.setVisibility(View.VISIBLE);
                        loadingAviMain.setVisibility(View.GONE);

                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    imgWishList.setVisibility(View.VISIBLE);
                    loadingAviMain.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", user_id);
                    params.put("product_id", ((VMEDS) getApplicationContext()).getProduct_id());
                    return params;
                }
            };
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addWishlist();
                }
            }, 5000);
        }
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

    public class SliderAdapter extends android.support.v4.view.PagerAdapter {

        private Context mContext;

        private ImageView product_image;
        private AVLoadingIndicatorView loadingAvi;

        public SliderAdapter(Context mContext) {
            this.mContext = mContext;

        }

        @Override
        public int getCount() {
            return sliderImages.length;
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


                String imageStr = sliderImages[position];

                if (!imageStr.contains("http://"))
                    imageStr = "http://" + imageStr;
                Glide.with(ProductDetailActivity.this).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
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


    private void setDetail() {
        loadingAviMain.setVisibility(View.GONE);
        loadingAviMain.smoothToHide();
        obj = ((VMEDS) getApplicationContext()).getObjProductDetail();
        txtProductTitle.setText(obj.product_name);
        txtCategory.setVisibility(View.GONE);
//        txtCategory.setText(obj.category + " || " + obj.sub_category);
        txtProductReview.setText("100 Review(s)");
//        txtSoldBy.setText(Html.fromHtml("Sold By : <u><font color=\"#4ab6ff\">" + obj.sold_by + "</font></u>"));
        imgAddtoCart.setVisibility(View.VISIBLE);
        imagePagerLayout.setVisibility(View.VISIBLE);
        hPanel.setVisibility(View.VISIBLE);
        product_discount.setVisibility(View.GONE);
        product_sale_rs.setText(getString(R.string.Rs) + " " + obj.product_price);
        product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        product_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);

//        if (obj.discount == null) {
//            product_sale_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);
//            product_rs.setVisibility(View.GONE);
//            product_discount.setVisibility(View.GONE);
//            product_price = obj.selling_price;
//            product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
//        } else {
//            if (obj.discount.length() == 0) {
//                product_sale_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);
//                product_rs.setVisibility(View.GONE);
//                product_discount.setVisibility(View.GONE);
//                product_price = obj.selling_price;
//                product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
//
//            } else if (Integer.parseInt(obj.discount) == 0) {
//                product_sale_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);
//                product_rs.setVisibility(View.GONE);
//                product_discount.setVisibility(View.GONE);
//                product_price = obj.selling_price;
//                product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
//            } else {
//                // product_rs.setText(getString(R.string.Rs) + " " + obj.purchase_price);
//                product_sale_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);
//                product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                int discount = Integer.parseInt(obj.discount);
//                float sale_price = Float.parseFloat(obj.selling_price);
//                float discount_price = ((int) sale_price * discount) / 100;
//                // float sale_price = Float.parseFloat(obj.sale_price) - Float.parseFloat(obj.sale_price);
//                product_price = String.valueOf(sale_price - discount_price);
//                product_rs.setText(getString(R.string.Rs) + " " + product_price);
//                product_discount.setText("Discount " + obj.discount + "%");
//            }
//        }
//
//
//        if (obj.discount == null) {
//            product_sale_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);
//            product_rs.setVisibility(View.GONE);
//            product_discount.setVisibility(View.GONE);
//            product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
//        } else {
//            if (obj.discount.length() == 0) {
//                product_sale_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);
//                product_rs.setVisibility(View.GONE);
//                product_discount.setVisibility(View.GONE);
//                product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
//
//            } else {
//                product_rs.setText(getString(R.string.Rs) + " " + obj.product_price);
//                product_sale_rs.setText(getString(R.string.Rs) + " " + obj.selling_price);
//                product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                product_discount.setText("Discount " + obj.discount + "%");
//            }
//        }


        ImageView imgAvailableColors[] = new ImageView[1];

        int k = 0;

        imgAvailableColors[k] = new ImageView(ProductDetailActivity.this);
//        Drawable drawable = getResources().getDrawable(R.drawable.color_bk);
//        drawable.setColorFilter(Color.parseColor(obj.product_color), PorterDuff.Mode.SRC_IN);

//        imgAvailableColors[k].setImageDrawable(drawable);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(4, 0, 4, 0);

        layoutColors.addView(imgAvailableColors[k], params);

        tabLayout.addTab(tabLayout.newTab().setText("Description"));
        tabLayout.addTab(tabLayout.newTab().setText("Specification"));
//        tabLayout.addTab(tabLayout.newTab().setText("Shipment"));
//        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));


        // txtProductColors.setBackgroundColor(Color.argb(1, 230, 9, 9));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        final PagerAdapter adapter = new PagerAdapter
//                (getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(3);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        if (((VMEDS) getApplicationContext()).getFromHome() == 0)
//            currentIndex = ((VMEDS) getApplicationContext()).getCurrentIndex();
//        else
//            currentIndex = 0;
//        viewPager.setCurrentItem(currentIndex);
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        changeTabsFont();


        WebView detailWebView = (WebView) findViewById(R.id.detailWebView);
        detailWebView.getSettings().setJavaScriptEnabled(true);
        detailWebView.loadDataWithBaseURL("", obj.product_description, "text/html", "UTF-8", "");

        sliderImages = new String[1];

        sliderImages[0] = obj.product_image;
//
//        try{
//            sliderImages = new String[Integer.parseInt(obj.num_of_imgs)];
//            JSONObject mainObject = new JSONObject(obj.images);
//
//            for (int j = 0; j < Integer.parseInt(obj.num_of_imgs); j++) {
//                sliderImages[j] = mainObject.getString(String.valueOf(j + 1));
//            }
//
//        }catch (Exception e){
//            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
//        }

        sliderAdapter = new SliderAdapter(ProductDetailActivity.this);
        slider.setAdapter(sliderAdapter);
        setUiPageViewController();


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
    }

    private void getProductDetail(final String product_id) {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.PRODUCT_DETAIL_URL;
            Log.i("URL", url + " " + product_id);
            mRequestQueue = Volley.newRequestQueue(ProductDetailActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);

                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAviMain.setVisibility(View.GONE);
                        loadingAviMain.smoothToHide();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("product_data"));

                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                            obj = new ProductDetail(jsonObject2.getString("product_id"), jsonObject2.getString("title"), jsonObject2.getString("sale_price"), jsonObject2.getString("purchase_price"), jsonObject2.getString("num_of_imgs"), jsonObject2.getString("images"), jsonObject2.getString("category"), jsonObject2.getString("sub_category"), jsonObject2.getString("color"), jsonObject2.getString("description"), jsonObject2.getString("additional_fields"), jsonObject2.getString("share_link"), jsonObject2.getString("discount"), jsonObject2.getString("number_of_view"), jsonObject2.getString("rating_num"), jsonObject2.getString("rating_total"), jsonObject2.getString("discount_type"), jsonObject2.getString("unit"), jsonObject2.getString("sold_by"));
                            txtCategory.setText(obj.category + " || " + obj.sub_category);
                            txtProductReview.setText(obj.number_of_view + " Review(s)");
                            txtSoldBy.setText(Html.fromHtml("Sold By : <u><font color=\"#4ab6ff\">" + obj.sold_by + "</font></u>"));
                            imgAddtoCart.setVisibility(View.VISIBLE);
                            imagePagerLayout.setVisibility(View.VISIBLE);
                            hPanel.setVisibility(View.VISIBLE);

                            if (obj.discount == null) {
                                product_sale_rs.setText(getString(R.string.Rs) + " " + obj.sale_price);
                                product_rs.setVisibility(View.GONE);
                                product_discount.setVisibility(View.GONE);
                                product_price = obj.sale_price;
                                product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
                            } else {
                                if (obj.discount.length() == 0) {
                                    product_sale_rs.setText(getString(R.string.Rs) + " " + obj.sale_price);
                                    product_rs.setVisibility(View.GONE);
                                    product_discount.setVisibility(View.GONE);
                                    product_price = obj.sale_price;
                                    product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());

                                } else if (Integer.parseInt(obj.discount) == 0) {
                                    product_sale_rs.setText(getString(R.string.Rs) + " " + obj.sale_price);
                                    product_rs.setVisibility(View.GONE);
                                    product_discount.setVisibility(View.GONE);
                                    product_price = obj.sale_price;
                                    product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
                                } else {
                                    // product_rs.setText(getString(R.string.Rs) + " " + obj.purchase_price);
                                    product_sale_rs.setText(getString(R.string.Rs) + " " + obj.sale_price);
                                    product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                                    int discount = Integer.parseInt(obj.discount);
                                    float sale_price = Float.parseFloat(obj.sale_price);
                                    float discount_price = ((int) sale_price * discount) / 100;
                                    // float sale_price = Float.parseFloat(obj.sale_price) - Float.parseFloat(obj.sale_price);
                                    product_price = String.valueOf(sale_price - discount_price);
                                    product_rs.setText(getString(R.string.Rs) + " " + product_price);
                                    product_discount.setText("Discount " + obj.discount + "%");
                                }
                            }


                            if (obj.discount == null) {
                                product_sale_rs.setText(getString(R.string.Rs) + " " + obj.sale_price);
                                product_rs.setVisibility(View.GONE);
                                product_discount.setVisibility(View.GONE);
                                product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());
                            } else {
                                if (obj.discount.length() == 0) {
                                    product_sale_rs.setText(getString(R.string.Rs) + " " + obj.sale_price);
                                    product_rs.setVisibility(View.GONE);
                                    product_discount.setVisibility(View.GONE);
                                    product_sale_rs.setTypeface(global_typeface.TypeFace_Roboto_Bold());

                                } else {
                                    product_rs.setText(getString(R.string.Rs) + " " + obj.purchase_price);
                                    product_sale_rs.setText(getString(R.string.Rs) + " " + obj.sale_price);
                                    product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                    product_discount.setText("Discount " + obj.discount + "%");
                                }

//                            vColors = getvColors(obj.color);
//                            if (vColors.size() > 0) {
//                                Log.e("Color", "s" + vColors.size());
//                                ImageView imgAvailableColors[] = new ImageView[vColors.size()];
//                                for (int k = 0; k < vColors.size(); k++) {
//
//
//                                    imgAvailableColors[k] = new ImageView(ProductDetailActivity.this);
//                                    RGBColor colorObj = vColors.elementAt(k);
//                                    Drawable drawable = getResources().getDrawable(R.drawable.color_bk);
//                                    drawable.setColorFilter(Color.argb((int) (colorObj.a_value * 255.0f), (int) colorObj.r_value, (int) colorObj.g_value, (int) colorObj.b_value), PorterDuff.Mode.SRC_IN);
//
//                                    imgAvailableColors[k].setImageDrawable(drawable);
//
//                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                                            LinearLayout.LayoutParams.WRAP_CONTENT,
//                                            LinearLayout.LayoutParams.WRAP_CONTENT
//                                    );
//
//                                    params.setMargins(4, 0, 4, 0);
//
//                                    layoutColors.addView(imgAvailableColors[k], params);
//
//
//                                }
                            }

                            sliderImages = new String[Integer.parseInt(obj.num_of_imgs)];
                            JSONObject mainObject = new JSONObject(obj.images);

                            for (int j = 0; j < Integer.parseInt(obj.num_of_imgs); j++) {
                                sliderImages[j] = mainObject.getString(String.valueOf(j + 1));
                            }
                            sliderAdapter = new SliderAdapter(ProductDetailActivity.this);
                            slider.setAdapter(sliderAdapter);
                            setUiPageViewController();


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

                            tabLayout.addTab(tabLayout.newTab().setText("Description"));
                            tabLayout.addTab(tabLayout.newTab().setText("Specification"));
                            tabLayout.addTab(tabLayout.newTab().setText("Shipment"));
                            tabLayout.addTab(tabLayout.newTab().setText("Reviews"));


                            // txtProductColors.setBackgroundColor(Color.argb(1, 230, 9, 9));

                            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//                            final PagerAdapter adapter = new PagerAdapter
//                                    (getSupportFragmentManager(), tabLayout.getTabCount());
//                            viewPager.setAdapter(adapter);
//                            viewPager.setOffscreenPageLimit(3);
//                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                            if (((VMEDS) getApplicationContext()).getFromHome() == 0)
//            currentIndex = ((VMEDS) getApplicationContext()).getCurrentIndex();
//        else
//            currentIndex = 0;
//        viewPager.setCurrentItem(currentIndex);
                                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {
                                        viewPager.setCurrentItem(tab.getPosition());
                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });

                            changeTabsFont();

                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loadingAviMain.setVisibility(View.GONE);
                    loadingAviMain.smoothToHide();

                    volleyError.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("product_id", product_id);

                    return params;
                }
            };
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getProductDetail(((VMEDS) getApplicationContext()).getProduct_id());
                }
            }, 5000);
        }
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(global_typeface.TypeFace_Roboto_Bold());
                }
            }
        }
    }

    private void setUiPageViewController() {

        dotsCount = sliderAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(ProductDetailActivity.this);
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

//    public class PagerAdapter extends FragmentStatePagerAdapter {
//        int mNumOfTabs;
//
//        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
//            super(fm);
//            this.mNumOfTabs = NumOfTabs;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//
//            switch (position) {
//                case 0:
//                    CategoryFragment tab1 = new CategoryFragment(obj.product_description);
//                    return tab1;
//
//                case 1:
//                    CategoryFragment tab2 = new CategoryFragment(obj.product_specification);
//                    return tab2;
//
////                case 2:
////                    CategoryFragment tab3 = new CategoryFragment(obj.product_description);
////                    return tab3;
////                case 3:
////                    CategoryFragment tab4 = new CategoryFragment(obj.product_description);
////                    return tab4;
//                default:
//                    CategoryFragment tab5 = new CategoryFragment(obj.product_description);
//                    return tab5;
//            }
//
//
//        }
//
//        @Override
//        public int getCount() {
//            return mNumOfTabs;
//        }
//    }

//    public class CategoryFragment extends Fragment {
//
//
//        private String detail_desc;
//
//        public CategoryFragment() {
//
//        }
//
//        public CategoryFragment(String detail_desc) {
//            this.detail_desc = detail_desc;
//
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View v = inflater.inflate(R.layout.product_more_detail_webview, container, false);
//            WebView detailWebView = (WebView) v.findViewById(R.id.detailWebView);
//            detailWebView.getSettings().setJavaScriptEnabled(true);
//            detailWebView.loadDataWithBaseURL("", detail_desc, "text/html", "UTF-8", "");
//
//            return v;
//        }
//
//
//    }

}