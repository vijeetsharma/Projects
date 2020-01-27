package com.VMEDS.android;

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
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPassActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_OLDPASS = "old_password";
    private static final String KEY_NEWPASS = "new_password";
    private static final String KEY_USER_ID = "user_id";
    private EditText etCPNewP;
    private EditText etCPOldP;
    private AppCompatButton btnCPSubmit;
    private String user_id;
    private EditText etCPReNewP;
    private TextView ctvTitle;
    private ImageView civSearch;
    private ImageView civAddcart;
    private StaticData static_data;
    StringRequest mStringRequest;
    RequestQueue mRequestQueue;
    private Global_Typeface global_typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpass);
        static_data = new StaticData();
        global_typeface = new Global_Typeface(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setText("Change Password");
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setVisibility(View.GONE);
        civSearch.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }

        btnCPSubmit = (AppCompatButton) findViewById(R.id.btn_CPSubmmit);
        btnCPSubmit.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        etCPOldP = (EditText) findViewById(R.id.CPOld);
        etCPOldP.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        etCPNewP = (EditText) findViewById(R.id.CPNew);
        etCPNewP.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        etCPReNewP = (EditText) findViewById(R.id.CPReNew);
        etCPReNewP.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        btnCPSubmit.setOnClickListener(this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        user_id = pref.getString("user_id", null);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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

    public void changePassword() {
        if (isNetworkConnected()) {
            final String old_pass = etCPOldP.getText().toString().trim();
            final String new_pass = etCPNewP.getText().toString().trim();

            String url = static_data.BASE_URL + static_data.CHANGE_PASSWORD_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(CPassActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    try {
                        // Toast.makeText(CPassActivity.this, response, Toast.LENGTH_LONG).show();
                        JSONObject jsonObject1 = new JSONObject(response);

                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            Toast.makeText(CPassActivity.this, jsonObject1.getString("message"), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CPassActivity.this, MainActivity.class);
                            finish();
                            startActivity(i);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
                    ,
                    new Response.ErrorListener()

                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CPassActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

            )

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_OLDPASS, old_pass);
                    params.put(KEY_NEWPASS, new_pass);
                    params.put(KEY_USER_ID, user_id);
                    Log.e("Params", params.toString());
                    return params;
                }

            };

            mRequestQueue = Volley.newRequestQueue(this);
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    changePassword();
                }
            }, 5000);
        }
    }

    @Override
    public void onClick(View v) {
        if (etCPOldP.getText().toString().length() == 0) {
            etCPOldP.requestFocus();
            etCPOldP.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
        } else if (etCPNewP.getText().toString().length() == 0) {
            etCPNewP.requestFocus();
            etCPNewP.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
        } else if (etCPReNewP.getText().toString().length() == 0) {
            etCPReNewP.requestFocus();
            etCPReNewP.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
        } else if (!isPasswordMatching(etCPReNewP.getText().toString(), etCPNewP.getText().toString())) {
            etCPNewP.requestFocus();
            etCPNewP.setError(Html.fromHtml("<font color='red'>Password & Confirm Password should be same</font>"));
        } else {
            changePassword();
        }

    }

    public boolean isPasswordMatching(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        if (!matcher.matches()) {
            // do your Toast("passwords are not matching");

            return false;
        }

        return true;
    }
}
