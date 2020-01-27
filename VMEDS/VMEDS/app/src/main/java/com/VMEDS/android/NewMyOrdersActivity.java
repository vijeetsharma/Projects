package com.VMEDS.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.VMEDS.android.adapter.MyOrderAdapter;
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.model.OrderDetail;
import com.VMEDS.android.utils.Global_Typeface;
import com.VMEDS.android.utils.MyOrder_List;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class NewMyOrdersActivity extends AppCompatActivity {
    private TextView ctvTitle;
    private ListView listMyOrders;
    private Global_Typeface global_typeface;
    private Vector<OrderDetail> vOrders;
    private AVLoadingIndicatorView loadingAvi;
    private StaticData static_data;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private TextView txtBlankData;
    private MyOrder_List myOrder_list;
    private Spinner spinnerStatus;
    private RelativeLayout layoutSpinner;
    private ImageView civSearch, civAddcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        final String user_id = pref.getString("user_id", null);

        setContentView(R.layout.activity_new_my_orders);


        static_data = new StaticData();
        myOrder_list = new MyOrder_List(NewMyOrdersActivity.this);
        global_typeface = new Global_Typeface(NewMyOrdersActivity.this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setText("My Orders");
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);
        txtBlankData = (TextView) findViewById(R.id.txtBlankData);
        layoutSpinner = (RelativeLayout) findViewById(R.id.layoutSpinner);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setVisibility(View.GONE);
        civSearch.setVisibility(View.GONE);
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        listMyOrders = (ListView) findViewById(R.id.listMyOrders);
        if (user_id == null) {
            Intent i = new Intent(NewMyOrdersActivity.this, UserLogin.class);
            startActivity(i);
        } else
            getMyOrdersList();

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


    }

    class SpinnerAdapter extends BaseAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        TextView txtSpinnerItem;


        Global_Typeface globalTypeface;

        public SpinnerAdapter(Context context) {
            mContext = context;
            globalTypeface = new Global_Typeface(context);
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        public int getCount() {
            return 4;
        }

        public Object getItem(int position) {
            String itemText = "";
            if (position == 0)
                itemText = ("Filter By");
            else if (position == 1)
                itemText = ("Pending");
            else if (position == 2)
                itemText = ("Completed");
            else if (position == 3)
                itemText = ("Canceled");
            return itemText;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View itemView, ViewGroup parent) {
            itemView = mLayoutInflater.inflate(R.layout.layout_spinner1, null);

            txtSpinnerItem = (TextView) itemView.findViewById(R.id.txtSpinnerItem);
            txtSpinnerItem.setTextColor(getResources().getColor(R.color.white));
            String itemText = "";
            if (position == 0)
                itemText = ("Filter By");
            else if (position == 1)
                itemText = ("Pending");
            else if (position == 2)
                itemText = ("Completed");
            else if (position == 3)
                itemText = ("Canceled");
            txtSpinnerItem.setText(itemText);
            txtSpinnerItem.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
            itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void getMyOrdersList() {

        if (isNetworkConnected()) {
            loadingAvi.setVisibility(View.VISIBLE);
            String url = static_data.BASE_URL + static_data.MYORDER_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(NewMyOrdersActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vOrders = new Vector<OrderDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        myOrder_list.delete();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("booking_data"));
                            Log.e("Size:", jsonArray.length() + "");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    Log.e("hello", "Data");
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    String orderStatus = "Pending";
                                    if (jsonObject2.getString("status") != null)
                                        orderStatus = resetStatus(jsonObject2.getString("status"));

                                    OrderDetail obj = new OrderDetail(jsonObject2.getString("user_id"), jsonObject2.getString("order_id"), jsonObject2.getString("created_date"), jsonObject2.getString("address"), jsonObject2.getString("offer_apply"), jsonObject2.getString("offer_code"), jsonObject2.getString("total_amount"), orderStatus, jsonObject2.getString("cart_data"));
                                    vOrders.add((OrderDetail) obj);

                                    Log.e("hello", "Data");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                            Log.e("Size:", vOrders.size() + "Dtaa");
                            myOrder_list.insert(vOrders);
                            listMyOrders.setAdapter(new MyOrderAdapter(NewMyOrdersActivity.this, vOrders));
                            loadingAvi.setVisibility(View.GONE);

                            if (vOrders.size() > 0) {
                                layoutSpinner.setVisibility(View.VISIBLE);
                                txtBlankData.setVisibility(View.GONE);
                                listMyOrders.setVisibility(View.VISIBLE);
                            } else {
                                txtBlankData.setVisibility(View.VISIBLE);
                                listMyOrders.setVisibility(View.GONE);
                                layoutSpinner.setVisibility(View.GONE);
                            }

                            spinnerStatus.setAdapter(new SpinnerAdapter(NewMyOrdersActivity.this));
                            spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    vOrders = new Vector<OrderDetail>();
                                    String itemText = "";
                                    if (position == 0)
                                        vOrders = myOrder_list.getMyOrderList();
                                    else {
                                        if (position == 1)
                                            itemText = ("Pending");
                                        else if (position == 2)
                                            itemText = ("Completed");
                                        else if (position == 3)
                                            itemText = ("Cancel");

                                        vOrders = myOrder_list.getMyOrderList(itemText);

                                        if (vOrders.size() > 0) {
                                            layoutSpinner.setVisibility(View.VISIBLE);
                                            txtBlankData.setVisibility(View.GONE);
                                            listMyOrders.setVisibility(View.VISIBLE);
                                        } else {
                                            txtBlankData.setVisibility(View.VISIBLE);
                                            listMyOrders.setVisibility(View.GONE);
                                            layoutSpinner.setVisibility(View.GONE);
                                        }
                                    }
                                    listMyOrders.setAdapter(new MyOrderAdapter(NewMyOrdersActivity.this, vOrders));


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                            listMyOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Vector<AddtoCartDetail> vDetails = new Vector<AddtoCartDetail>();
                                    try {
                                        JSONArray jsonArray = new JSONArray(((OrderDetail) vOrders.elementAt(position)).cart_data);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                            AddtoCartDetail obj = new AddtoCartDetail("cart_id", jsonObject2.getString("product_id"), jsonObject2.getString("quantity"), jsonObject2.getString("price"), jsonObject2.getString("product_name"), jsonObject2.getString("product_image"));
                                            vDetails.add((AddtoCartDetail) obj);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    ((VMEDS) getApplicationContext().getApplicationContext()).setorder_id(((OrderDetail) vOrders.elementAt(position)).ref_id);
                                    ((VMEDS) getApplicationContext().getApplicationContext()).setvDetails(vDetails);
                                    ((VMEDS) getApplicationContext().getApplicationContext()).setFinalTotal(Float.parseFloat(((OrderDetail) vOrders.elementAt(position)).final_totle));
                                    Intent i = new Intent(NewMyOrdersActivity.this, OrderDetailActivity.class);
//                                    finish();
                                    startActivity(i);

                                }
                            });
                        } else {
                            loadingAvi.setVisibility(View.GONE);
                            txtBlankData.setVisibility(View.VISIBLE);
                            listMyOrders.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loadingAvi.setVisibility(View.GONE);
                        txtBlankData.setVisibility(View.VISIBLE);
                        listMyOrders.setVisibility(View.GONE);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loadingAvi.setVisibility(View.GONE);
                    txtBlankData.setVisibility(View.VISIBLE);
                    listMyOrders.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Internet Connection Problem" + volleyError.toString(), Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    String user_id = pref.getString("user_id", null);

                    params.put("user_id", user_id);
                    Log.e("Params", params.toString() + "");
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
                    getMyOrdersList();
                }
            }, 5000);
        }
    }

    public String resetStatus(String status) {
        String statusReset = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        return statusReset;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyOrdersList();
    }

}
