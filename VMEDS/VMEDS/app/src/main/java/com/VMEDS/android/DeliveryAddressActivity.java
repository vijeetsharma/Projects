package com.VMEDS.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.utils.AddtoCart_List;
import com.VMEDS.android.utils.Global_Typeface;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.Vector;

public class DeliveryAddressActivity extends AppCompatActivity {
    private EditText email_id, mobile_no, editTextFName, editTextLName, epAdressL1, epAdressL2, epZipCode;
    private Button btnSubmit;
    private Global_Typeface global_typeface;
    private TextView ctvTitle;
    private ImageView civSearch, civAddcart;
    private AVLoadingIndicatorView loadingAvi;
    private Vector<AddtoCartDetail> vDetails;
    private AddtoCart_List addtoCart_list;
    private int offer_applied = 2;
    private String offer_code = "";
    private String address;
    private StaticData static_data;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        static_data = new StaticData();
        global_typeface = new Global_Typeface(DeliveryAddressActivity.this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        civSearch = (ImageView) cView.findViewById(R.id.Custom_search);
        civAddcart = (ImageView) cView.findViewById(R.id.Custom_addcart);
        civSearch.setVisibility(View.GONE);
        civAddcart.setVisibility(View.GONE);
        ctvTitle.setText("Delivery Address");
        editTextFName = (EditText) findViewById(R.id.editTextFName);
        editTextLName = (EditText) findViewById(R.id.editTextLName);
        epAdressL1 = (EditText) findViewById(R.id.epAdressL1);
        epAdressL2 = (EditText) findViewById(R.id.epAdressL2);
        email_id = (EditText) findViewById(R.id.email_id);
        mobile_no = (EditText) findViewById(R.id.mobile_no);
        epZipCode = (EditText) findViewById(R.id.epZipCode);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);

        if (((VMEDS) getApplicationContext()).getOfferAmount() != 0.0) {

            offer_applied = 1;
            offer_code = ((VMEDS) getApplicationContext()).getOfferCode();


        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0);
        editTextFName.setText(pref.getString("user_fname", ""));
        editTextLName.setText(pref.getString("user_lname", ""));
        epAdressL1.setText(pref.getString("user_add1", ""));
        epAdressL2.setText(pref.getString("user_add2", ""));
        email_id.setText(pref.getString("user_email", ""));
        mobile_no.setText(pref.getString("user_mobile", ""));
        epZipCode.setText(pref.getString("user_zip", ""));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFName.getText().toString().length() == 0) {
                    editTextFName.requestFocus();
                    editTextFName.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
                } else if (editTextLName.getText().toString().length() == 0) {
                    editTextLName.requestFocus();
                    editTextLName.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
                } else if (epAdressL1.getText().toString().length() == 0) {
                    epAdressL1.requestFocus();
                    epAdressL1.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
                } else if (email_id.getText().toString().length() == 0) {
                    email_id.requestFocus();
                    email_id.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
                } else if (mobile_no.getText().toString().length() == 0) {
                    mobile_no.requestFocus();
                    mobile_no.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
                } else if (epZipCode.getText().toString().length() == 0) {
                    epZipCode.requestFocus();
                    epZipCode.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
                } else {

                    address = epAdressL1.getText().toString() + epAdressL2.getText().toString();
                    insertBookingDetail();
//                    Intent i = new Intent(DeliveryAddressActivity.this, CYPayment.class);
//                    finish();
//                    startActivity(i);
                }
            }
        });

        addtoCart_list = new AddtoCart_List(DeliveryAddressActivity.this);
        vDetails = addtoCart_list.getCartList();

    }

    public float calculateTotal() {
        Vector<AddtoCartDetail> vDetails = addtoCart_list.getCartList();
        float subTotal = 0;
        if (vDetails.size() > 0) {
            for (int j = 0; j < vDetails.size(); j++) {
                subTotal += Float.parseFloat(((AddtoCartDetail) vDetails.elementAt(j)).price) * Float.parseFloat(((AddtoCartDetail) vDetails.elementAt(j)).quantity);
            }
            if (((VMEDS) getApplicationContext()).getOfferAmount() != 0.0) {
                offer_applied = 1;
                subTotal -= ((VMEDS) getApplicationContext()).getOfferAmount();
            }
            return subTotal;
        }
        return 0;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void insertBookingDetail() {
//        strAddress =
        if (isNetworkConnected()) {
            loadingAvi.setVisibility(View.VISIBLE);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode

            String serviceparams = "";

            String user_id = pref.getString("user_id", null);
            if (user_id == null)
                user_id = "4";

            if (vDetails.size() > 0) {

                for (int j = 0; j < vDetails.size(); j++) {
                    serviceparams += "product_id" + String.valueOf(j + 1) + "=" + ((AddtoCartDetail) vDetails.elementAt(j)).product_id + "&";
                    serviceparams += "quantity" + String.valueOf(j + 1) + "=" + ((AddtoCartDetail) vDetails.elementAt(j)).quantity + "&";
                    serviceparams += "price" + String.valueOf(j + 1) + "=" + ((AddtoCartDetail) vDetails.elementAt(j)).price + "&";

                }
            }

            if (offer_applied == 1)
                serviceparams += "offer_code=" + offer_code.toString() + "&";
            String getparams = "address=" + address + "&total_amount=" + String.valueOf(calculateTotal()) + "&offer_apply=" + offer_applied + "&item_length=" + vDetails.size() + "&user_id=" + user_id + "&" + serviceparams;
            String url = static_data.BASE_URL + static_data.BOOKING_URL + "?" + getparams.replace(" ", "%20");
            Log.i("URL", url);

            mRequestQueue = Volley.newRequestQueue(DeliveryAddressActivity.this);
            mStringRequest = new StringRequest(Request.Method.GET, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);

                        JSONObject jsonObject1 = new JSONObject(response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            loadingAvi.setVisibility(View.GONE);

                            if (jsonObject1.getString("order_id") != null) {
                                Toast.makeText(getApplicationContext(), "Your order has been successfully placed", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(DeliveryAddressActivity.this, NewThankYouActivity.class);
                                finish();
                                startActivity(i);
                                ((VMEDS) getApplicationContext().getApplicationContext()).setorder_id(jsonObject1.getString("order_id"));
                                ((VMEDS) getApplicationContext().getApplicationContext()).setvDetails(vDetails);
                                ((VMEDS) getApplicationContext().getApplicationContext()).setFinalTotal(calculateTotal());

                                AddtoCart_List addtoCart_list = new AddtoCart_List(DeliveryAddressActivity.this);
                                addtoCart_list.delete();
                            }

                        } else {
                            Toast.makeText(DeliveryAddressActivity.this, "Internet connection problem", Toast.LENGTH_LONG).show();
                            loadingAvi.setVisibility(View.GONE);

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


            });

            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    insertBookingDetail();
                }
            }, 5000);
        }
    }

}
