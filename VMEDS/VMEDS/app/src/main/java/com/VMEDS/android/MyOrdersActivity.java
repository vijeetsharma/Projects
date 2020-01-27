package com.VMEDS.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.utils.AddToCart;
import com.VMEDS.android.utils.AddtoCart_List;
import com.VMEDS.android.utils.Global_Typeface;
import com.VMEDS.android.utils.Wish;
import com.VMEDS.android.utils.Wish_List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MyOrdersActivity extends AppCompatActivity {
    private ListView listMyorders;
    private Vector<AddtoCartDetail> vOrderList;
    private AddtoCart_List addtoCart_list;
    private Wish_List wish_List;
    private Global_Typeface global_typeface;
    private TextView ctvTitle;
    private ImageView civSearch, civAddcart;
    private LinearLayout amountLayout;
    private TextView txtSubTotal, txtTax, txtShipping, txtGrandTotal;
    private TextView txtAmtSubTotal, txtAmtTax, txtAmtShipping, txtAmtGrandTotal;
    private float taxPrice = 0, shippingPrice = 0;
    private Button btnSelectAddress, btnHavePromocode;
    private StaticData static_data;
    private AVLoadingIndicatorView loadingAvi;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String user_id;
    private TextView txtBlankData;
    private Double offerAmount = 0.0;
    private TextView txtAmtOffer, txtOffer, txtSeeOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        global_typeface = new Global_Typeface(MyOrdersActivity.this);
        static_data = new StaticData();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        txtBlankData = (TextView) findViewById(R.id.txtBlankData);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtBlankData.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        civSearch = (ImageView) cView.findViewById(R.id.Custom_search);
        civAddcart = (ImageView) cView.findViewById(R.id.Custom_addcart);
        civSearch.setVisibility(View.GONE);
        addtoCart_list = new AddtoCart_List(MyOrdersActivity.this);
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);
        btnSelectAddress = (Button) findViewById(R.id.btnSelectAddress);
        btnHavePromocode = (Button) findViewById(R.id.btnHavePromocode);
        amountLayout = (LinearLayout) findViewById(R.id.amountLayout);
        txtSubTotal = (TextView) findViewById(R.id.txtSubTotal);
        txtTax = (TextView) findViewById(R.id.txtTax);
        txtShipping = (TextView) findViewById(R.id.txtShipping);
        txtOffer = (TextView) findViewById(R.id.txtOffer);
        txtAmtOffer = (TextView) findViewById(R.id.txtAmtOffer);
        txtGrandTotal = (TextView) findViewById(R.id.txtGrandTotal);
        txtAmtSubTotal = (TextView) findViewById(R.id.txtAmtSubTotal);
        txtAmtTax = (TextView) findViewById(R.id.txtAmtTax);
        txtAmtShipping = (TextView) findViewById(R.id.txtAmtShipping);
        txtSeeOffers = (TextView) findViewById(R.id.txtSeeOffers);
        txtAmtGrandTotal = (TextView) findViewById(R.id.txtAmtGrandTotal);
        txtSeeOffers.setTypeface(global_typeface.TypeFace_Roboto_Regular());


        txtSeeOffers.setText(Html.fromHtml("<u>See Offers</u>"));
        txtSubTotal.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtTax.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtOffer.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtAmtOffer.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtShipping.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        btnHavePromocode.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtGrandTotal.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtAmtSubTotal.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtAmtTax.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtAmtShipping.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtAmtGrandTotal.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        btnSelectAddress.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        civSearch.setVisibility(View.GONE);
        civAddcart.setVisibility(View.GONE);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        final String user_id = pref.getString("user_id", null);
        if (user_id == null) {
            btnSelectAddress.setText("Login To Proceed");
        } else {
            btnSelectAddress.setText("Select Delivery Address");

        }

        btnSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                final String user_id = pref.getString("user_id", null);
                if (user_id == null) {
                    ((VMEDS) getApplicationContext()).setloginFromHome(0);
                    Intent i = new Intent(MyOrdersActivity.this, UserLogin.class);
                    startActivity(i);
                } else {
                    ((VMEDS) getApplicationContext()).setOfferAmount(offerAmount);
                    Intent i = new Intent(MyOrdersActivity.this, DeliveryAddressActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });

        if (((VMEDS) getApplicationContext()).getFromWishList() == 0) {
            ctvTitle.setText("My Cart");
            civAddcart.setImageResource(R.drawable.checout);
            civAddcart.setColorFilter(getResources().getColor(R.color.dblue), PorterDuff.Mode.SRC_IN);
            amountLayout.setVisibility(View.VISIBLE);
            btnSelectAddress.setVisibility(View.VISIBLE);
            calculateTotal();
        } else {
            civAddcart.setVisibility(View.GONE);
            ctvTitle.setText("My WishList");
            amountLayout.setVisibility(View.GONE);
            btnSelectAddress.setVisibility(View.GONE);
        }

        listMyorders = (ListView) findViewById(R.id.listMyorders);
        wish_List = new Wish_List(this);
        addtoCart_list = new AddtoCart_List(this);
        if (((VMEDS) getApplicationContext()).getFromWishList() == 0) {
            vOrderList = addtoCart_list.getCartList();
            if (vOrderList.size() == 0)
                civAddcart.setVisibility(View.GONE);
            listMyorders.setAdapter(new OrderListAdapter(MyOrdersActivity.this, vOrderList));
        } else {
            // vOrderList = wish_List.getWishList();
            getWishListData();
        }

        btnHavePromocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promocodeDialog();
            }
        });

        txtSeeOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyOrdersActivity.this, OffersActivity.class);
                startActivity(i);
            }
        });


//        listMyorders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(MyOrdersActivity.this, DeliveryAddressActivity.class);
//                startActivity(i);
//            }
//        });
    }

    //This method would confirm the otp
    private void promocodeDialog() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(MyOrdersActivity.this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.layout_promo, null);
        TextView txtPromocode = (TextView) confirmDialog.findViewById(R.id.txtPromocode);
        final RelativeLayout errorPromoLayout = (RelativeLayout) confirmDialog.findViewById(R.id.errorPromoLayout);
        txtPromocode.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        //Initizliaing confirm button fo dialog box and edittext of dialog box
        Button btnOK = (Button) confirmDialog.findViewById(R.id.btnOK);
        final EditText edPromocode = (EditText) confirmDialog.findViewById(R.id.edPromocode);
        // editTextConfirmMobile.setText(mobile_no);

        edPromocode.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(MyOrdersActivity.this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final android.app.AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCancelable(true);
        final String url = static_data.BASE_URL + static_data.OFFER_APPLIED_URL;
        btnOK.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        //On the click of the confirm button from alert dialog
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                if (edPromocode.getText().toString().length() == 0)
                    edPromocode.setError(Html.fromHtml("<font color='red'>Please Enter Offercode</font>"));
                else if (isNetworkConnected()) {
                    //Displaying a progressbar
                    final ProgressDialog loading = ProgressDialog.show(MyOrdersActivity.this, "Authenticating", "Please wait while we check the entered code", false, false);

                    //Getting the user entered otp from edittext
                    final String promocode = edPromocode.getText().toString().trim();

                    mRequestQueue = Volley.newRequestQueue(MyOrdersActivity.this);
                    mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {

                        public void onResponse(String response) {
                            //if the server response is success
                            try {
                                JSONObject jsonObject1 = new JSONObject(response);
                                if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                                    offerAmount = Double.parseDouble(jsonObject1.getString("amount"));
                                    txtAmtOffer.setText(getString(R.string.Rs) + offerAmount);
                                    ((VMEDS) getApplicationContext()).setOfferCode(edPromocode.getText().toString());
                                    loading.dismiss();
                                    alertDialog.dismiss();
                                    errorPromoLayout.setVisibility(View.GONE);
                                    Toast.makeText(MyOrdersActivity.this.getApplicationContext(), "Your promocode has been successfully applied..", Toast.LENGTH_LONG).show();
                                    btnHavePromocode.setText("Offer successfully applied");
                                    btnHavePromocode.setEnabled(false);
                                    calculateTotal();
                                    //Starting a new activity
                                    //startActivity(new Intent(Login.this, HomeActivity.class));
                                } else {
                                    loading.dismiss();
                                    errorPromoLayout.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "Please enter valid offercode ", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alertDialog.dismiss();
                                    loading.dismiss();

                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            //Adding the parameters otp and username

                            params.put("offer_code", edPromocode.getText().toString());

                            return params;
                        }
                    };

                    //Adding the request to the queue
                    mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mRequestQueue.add(mStringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void getWishListData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        final String user_id = pref.getString("user_id", null);
        if (user_id == null) {
            ((VMEDS) getApplicationContext()).setloginFromHome(0);
            Intent i = new Intent(MyOrdersActivity.this, UserLogin.class);
            //finish();
            startActivity(i);
        } else if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.WISHLIST_URL;
            Log.i("URL", url);
            loadingAvi.setVisibility(View.VISIBLE);
            mRequestQueue = Volley.newRequestQueue(MyOrdersActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vOrderList = new Vector<AddtoCartDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("product_data"));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String images = jsonObject2.getString("images");
                                JSONObject mainObject = new JSONObject(images);
                                String imageStr = mainObject.getString("1");

                                if (!imageStr.contains("http://"))
                                    imageStr = "http://" + imageStr;
                                AddtoCartDetail obj = new AddtoCartDetail("0", jsonObject2.getString("product_id"), "1", jsonObject2.getString("sale_price"), jsonObject2.getString("title"), imageStr);

                                vOrderList.add((AddtoCartDetail) obj);
                            }
                            wish_List.insert(vOrderList);
                            listMyorders.setAdapter(new OrderListAdapter(MyOrdersActivity.this, vOrderList));

                        } else if (jsonObject1.getString("status").equalsIgnoreCase("404")) {
                            loadingAvi.setVisibility(View.GONE);
                            loadingAvi.smoothToHide();

                            Toast.makeText(getApplicationContext(), "No Wishlist Found", Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();

                        Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loadingAvi.setVisibility(View.GONE);
                    loadingAvi.smoothToHide();

                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", user_id);

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
                    getWishListData();
                }
            }, 5000);
        }
    }

    public void calculateTotal() {
        Vector<AddtoCartDetail> vDetails = addtoCart_list.getCartList();
        float subTotal = 0;
        if (vDetails.size() > 0) {
            for (int j = 0; j < vDetails.size(); j++) {
                subTotal += Float.parseFloat(((AddtoCartDetail) vDetails.elementAt(j)).price) * Float.parseFloat(((AddtoCartDetail) vDetails.elementAt(j)).quantity);
            }
            txtAmtTax.setText(getString(R.string.Rs) + " " + String.valueOf("0"));
            txtAmtShipping.setText(getString(R.string.Rs) + " " + String.valueOf("0"));
            txtAmtSubTotal.setText(getString(R.string.Rs) + " " + String.valueOf(subTotal));
            Double totalAmount = subTotal - offerAmount;
            txtAmtOffer.setText(getString(R.string.Rs) + " " + String.valueOf(offerAmount));
            txtAmtGrandTotal.setText(getString(R.string.Rs) + " " + String.valueOf(totalAmount));

        } else {
            amountLayout.setVisibility(View.GONE);
            btnSelectAddress.setVisibility(View.GONE);
            txtBlankData.setVisibility(View.VISIBLE);
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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
            if (((VMEDS) getApplicationContext()).getFromWishList() == 0)
                viewHolder.quantity_layout.setVisibility(View.VISIBLE);
            else
                viewHolder.quantity_layout.setVisibility(View.GONE);

            if (((VMEDS) getApplicationContext()).getFromWishList() == 0)
                viewHolder.txtMoveToWishList.setText("Move to WishList");
            else
                viewHolder.txtMoveToWishList.setText("Move to Cart");

            try {

                //
                String imageStr = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).image_url;

                if (!imageStr.contains("http://"))
                    imageStr = "http://" + imageStr;
                Glide.with(MyOrdersActivity.this).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
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
            viewHolder.product_quantity.setText(((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity);
            viewHolder.product_total_price.setText(getString(R.string.Rs) + " " + Float.parseFloat(((AddtoCartDetail) vAddtoCartList.elementAt(position)).price) * Float.parseFloat(((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity) + "");

            viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Hello", "Delete");
                    if (((VMEDS) getApplicationContext()).getFromWishList() == 0) {


                        addtoCart_list.delete(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id);
                        listMyorders.setAdapter(null);

                        vOrderList = addtoCart_list.getCartList();

                        calculateTotal();
                        listMyorders.setAdapter(new OrderListAdapter(MyOrdersActivity.this, vOrderList));
                        Toast.makeText(MyOrdersActivity.this, "Item Sucessfully removed ", Toast.LENGTH_SHORT).show();
                    } else {

                        deleteWishlist(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id);


                    }


                }
            });
            viewHolder.txtMoveToWishList.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
            viewHolder.txtMoveToWishList.setVisibility(View.GONE);
            viewHolder.txtMoveToWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (((VMEDS) getApplicationContext()).getFromWishList() == 0) {
                        addtoCart_list.delete(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id);

                        Wish product_item = new Wish();
                        product_item.product_id = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id;
                        product_item.product_title = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_title;
                        product_item.quantity = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity;
                        product_item.price = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).price;
                        product_item.image_url = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).image_url;

                        wish_List.insert(product_item);
                        listMyorders.setAdapter(null);

                        vOrderList = addtoCart_list.getCartList();


                    } else {
                        wish_List.delete(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id);

                        AddToCart addToCart = new AddToCart();
                        addToCart.product_id = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id;
                        addToCart.product_title = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_title;
                        addToCart.quantity = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity;
                        addToCart.price = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).price;
                        addToCart.image_url = ((AddtoCartDetail) vAddtoCartList.elementAt(position)).image_url;
                        addtoCart_list.insert(addToCart);
                        listMyorders.setAdapter(null);

                        vOrderList = wish_List.getWishList();

                    }

                    listMyorders.setAdapter(new OrderListAdapter(MyOrdersActivity.this, vOrderList));
                    calculateTotal();
                    Toast.makeText(MyOrdersActivity.this, "Item Sucessfully moved ", Toast.LENGTH_SHORT).show();

                }
            });

//            viewHolder.cart_plus.setVisibility(View.GONE);
            viewHolder.cart_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Hello", ((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity + " ");
                    int qua = Integer.parseInt(((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity.trim());
                    qua++;
                    Log.e("Hello", qua + " ");

                    viewHolder.product_quantity.setText(String.valueOf(qua));
                    Float price_item = Float.parseFloat(((AddtoCartDetail) vAddtoCartList.elementAt(position)).price);
                    price_item = price_item * qua;
                    Log.e("Hello", price_item + " DB " + addtoCart_list.update(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id, String.valueOf(qua)));
                    viewHolder.product_total_price.setText(getString(R.string.Rs) + " " + String.valueOf(price_item));
                    //notifyDataSetChanged();
                    listMyorders.setAdapter(null);
                    vOrderList = addtoCart_list.getCartList();

                    listMyorders.setAdapter(new OrderListAdapter(MyOrdersActivity.this, vOrderList));
                    calculateTotal();

                }
            });
//            viewHolder.cart_minus.setVisibility(View.GONE);
            viewHolder.cart_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Hello Minus", ((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity + " ");

                    if (Integer.parseInt(((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity) > 1) {
                        Log.e("Hello", ((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity + " ");
                        int qua = Integer.parseInt(((AddtoCartDetail) vAddtoCartList.elementAt(position)).quantity.trim());
                        qua--;
                        Log.e("Hello", qua + " ");

                        viewHolder.product_quantity.setText(String.valueOf(qua));
                        Float price_item = Float.parseFloat(((AddtoCartDetail) vAddtoCartList.elementAt(position)).price);
                        price_item = price_item * qua;
                        Log.e("Hello", price_item + " DB " + addtoCart_list.update(((AddtoCartDetail) vAddtoCartList.elementAt(position)).product_id, String.valueOf(qua)));
                        viewHolder.product_total_price.setText(getString(R.string.Rs) + " " + String.valueOf(price_item));
                        //notifyDataSetChanged();
                        listMyorders.setAdapter(null);
                        vOrderList = addtoCart_list.getCartList();

                        listMyorders.setAdapter(new OrderListAdapter(MyOrdersActivity.this, vOrderList));
                        calculateTotal();
                    }
                    //viewHolder.product_total_price.setText(getString(R.string.Rs) + " " + Float.parseFloat(((AddtoCartDetail) vAddtoCartList.elementAt(position)).price) * Integer.parseInt(viewHolder.product_quantity.getText().toString()) + "");

                }
            });

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

    public void deleteWishlist(final String product_id) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        user_id = pref.getString("user_id", null);
        if (user_id == null) {
            ((VMEDS) getApplicationContext()).setloginFromHome(0);
            Intent i = new Intent(MyOrdersActivity.this, UserLogin.class);
            //finish();
            startActivity(i);
        } else if (isNetworkConnected()) {
            loadingAvi.setVisibility(View.VISIBLE);
            String url = static_data.BASE_URL + static_data.WISHLIST_INSERT_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(MyOrdersActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();

                            wish_List.delete(product_id);
                            listMyorders.setAdapter(null);

                            vOrderList = wish_List.getWishList();
                            calculateTotal();
                            listMyorders.setAdapter(new OrderListAdapter(MyOrdersActivity.this, vOrderList));
                            Toast.makeText(MyOrdersActivity.this, "Item Sucessfully removed ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet Connection Problem Please Do it Later", Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loadingAvi.setVisibility(View.GONE);

                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loadingAvi.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", user_id);
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
                    deleteWishlist(product_id);
                }
            }, 5000);
        }
    }

    public void onResume() {
        super.onResume();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        final String user_id = pref.getString("user_id", null);
        if (user_id == null) {
            btnSelectAddress.setText("Login To Proceed");
        } else {
            btnSelectAddress.setText("Select Delivery Address");

        }
    }
}
