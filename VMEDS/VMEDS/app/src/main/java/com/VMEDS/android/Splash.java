package com.VMEDS.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.VMEDS.android.model.CategoryDetail;
import com.VMEDS.android.utils.DBHelper;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;


public class Splash extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Pc3E9IYVhLyRAGFF7rxjJ8HoE";
    private static final String TWITTER_SECRET = "xJPfwKUPrqp7o7tBG2Y5daB3QnAM54JLZVk8lq7KMeRvEBGnrV";
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private StaticData static_data;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private TextView txtLogo;
    private Vector<CategoryDetail> vCategory;
    private Global_Typeface global_typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        static_data = new StaticData();
        setContentView(R.layout.activity_splash);
        global_typeface = new Global_Typeface(Splash.this);
        ((VMEDS) getApplicationContext()).setFromHome(1);
        ((VMEDS) getApplicationContext()).setCurrentIndex(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }

        DBHelper db = new DBHelper(this);
        txtLogo = (TextView) findViewById(R.id.txtLogo);
        txtLogo.setTypeface(global_typeface.TypeFace_Roboto_Bold());
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(Splash.this, MainActivity.class);
//                startActivity(i);
//                finish();
//                // close this activity
//            }
//        }, 3000);
        getCategoryList();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void getCategoryList() {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.CATEGORY_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(Splash.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vCategory = new Vector<CategoryDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("Category_data"));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                //  JSONArray jsonArraySubCat = new JSONArray(jsonObject2.getString("sub_category"));
                                Vector<CategoryDetail> vsubCat = new Vector<CategoryDetail>();
                                JSONArray mainObjects = new JSONArray(jsonObject2.getString("sub_category"));
                                for (int j = 0; j < Integer.parseInt(jsonObject2.getString("total_category")); j++) {
                                    JSONObject mainObject = mainObjects.getJSONObject(j);
                                    // sliderImages[j] = mainObject.getString(String.valueOf(j + 1));
                                    CategoryDetail objSubCat = new CategoryDetail(mainObject.getString("sub_category_id"), mainObject.getString("sub_category_name"));
                                    vsubCat.add((CategoryDetail) objSubCat);
                                }

//                            for (int j = 0; j < jsonArraySubCat.length(); j++) {
//                                JSONObject jsonObjectSubCat = jsonArraySubCat.getJSONObject(j);
//                                CategoryDetail objSubCat = new CategoryDetail(jsonObjectSubCat.getString("sub_category_id"), jsonObjectSubCat.getString("sub_category_name"));
//                                vsubCat.add((CategoryDetail) objSubCat);
//                            }

                                CategoryDetail obj = new CategoryDetail(jsonObject2.getString("category_id"), jsonObject2.getString("category_name"), jsonObject2.getString("images"), vsubCat);
                                vCategory.add((CategoryDetail) obj);



                            }
                            ((VMEDS) getApplicationContext()).setvCategoryList(vCategory);
                            //  prepareListData();
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            });
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getCategoryList();
                }
            }, 5000);
        }
    }

}