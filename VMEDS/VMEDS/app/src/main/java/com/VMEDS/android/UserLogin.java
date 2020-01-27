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
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextEmail;
    private EditText editTextPassword;
    private AppCompatButton buttonLogin;
    StringRequest mStringRequest;
    RequestQueue mRequestQueue;
    StaticData static_data;
    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;
    private String TAG;
    public String mStatus;
    private TextView tvForget;
    private TextView tvSigin;
    private SharedPreferences sharedPreferences;
    private EditText wordEditText;
    private TextView btnClosePopup;
    private TextView btnFEmail;
    private TextView btnFNumber;
    Global_Typeface global_typeface;
    private TextView ctvTitle;
    private ImageView civSearch, civAddcart;
    private EditText editTextConfirmMobile, editTextNewPassword;
    private Button buttonConfirm;
    private AVLoadingIndicatorView loadingAvi;
    private TextView txtLogo;
    public final static int QRcodeWidth = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        static_data = new StaticData();
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);
        buttonLogin = (AppCompatButton) findViewById(R.id.btn_login);
        tvForget = (TextView) findViewById(R.id.link_forget);
        tvSigin = (TextView) findViewById(R.id.link_signup);
        wordEditText = (EditText) findViewById(R.id.input_email);
        wordEditText = (EditText) findViewById(R.id.input_password);
        txtLogo = (TextView) findViewById(R.id.txtLogo);
        global_typeface = new Global_Typeface(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setText("Login");
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setVisibility(View.GONE);
        civSearch.setVisibility(View.GONE);
        txtLogo.setTypeface(global_typeface.TypeFace_Roboto_Bold());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        //Adding click listener
        tvSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLogin.this, Register.class);
                startActivity(i);
            }
        });
        tvForget.setOnClickListener(new View.OnClickListener() {

            public PopupWindow pwindo;

            @Override
            public void onClick(View v) {
                //initiatePopupwindow();
//                Intent i = new Intent(Login.this, FOtherActivity.class);
//                startActivity(i);
                confirmMobileNumber();
            }

        });
        buttonLogin.setOnClickListener(this);


    }

    /*@Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(LConfig.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(LConfig.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Main Activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }
    */

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
    public void loginUser() {
        if (isNetworkConnected()) {
            loadingAvi.setVisibility(View.VISIBLE);
            String url = static_data.BASE_URL + static_data.LOGIN_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(UserLogin.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);

                        JSONObject jsonObject1 = new JSONObject(response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("user_data"));
                            JSONObject jsonArray = jsonArray1.getJSONObject(0);
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("user_id", jsonArray.getString("id"));
                            editor.putString("username", jsonArray.getString("username"));
                            editor.putString("user_mobile", jsonArray.getString("phone"));
                            editor.putString("user_email", jsonArray.getString("email"));
                            editor.putString("user_image", jsonArray.getString("photo"));

                            editor.commit();

                            if (((VMEDS) getApplicationContext()).getloginFromHome() == 1) {
                                Intent i = new Intent(UserLogin.this, MainActivity.class);
                                ((VMEDS) getApplicationContext()).setFragmentIndex(2);
                                startActivity(i);
                            }
                            loadingAvi.setVisibility(View.GONE);

                            finish();

                        } else {
                            Toast.makeText(UserLogin.this, "Please Enter Valid Credentials...", Toast.LENGTH_LONG).show();
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
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("password", editTextPassword.getText().toString());
                    try {
                        Integer.parseInt(editTextEmail.getText().toString());
                        params.put("mobile", editTextEmail.getText().toString());
                    } catch (Exception e) {
                        params.put("email", editTextEmail.getText().toString());

                    }

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
                    loginUser();
                }
            }, 5000);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        smsVerifyCatcher.onStop();
//    }

    @Override
    public void onClick(View v) {
        //Calling the login function
        if (editTextEmail.length() == 0) {
            editTextEmail.requestFocus();
            editTextEmail.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
        } else if (editTextPassword.length() == 0) {
            editTextPassword.requestFocus();
            editTextPassword.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</fonr>"));
        } else {
            loginUser();
        }
    }

    //This method would confirm the otp
    private void confirmMobileNumber() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        TextView txtConfirm = (TextView) confirmDialog.findViewById(R.id.txtConfirm);
        txtConfirm.setText("Confirm Mobile Number");
        txtConfirm.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        buttonConfirm = (Button) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmMobile = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
        editTextConfirmMobile.setText(editTextEmail.getText().toString());
        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final android.app.AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCancelable(true);
        final String url = static_data.BASE_URL + static_data.FORGET_PASSWORD_OTP_URL;

        //On the click of the confirm button from alert dialog
        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                if (editTextConfirmMobile.getText().toString().length() == 0)
                    editTextConfirmMobile.setError(Html.fromHtml("<font color='red'>Please Enter Mobile Number</font>"));
                else if (isNetworkConnected()) {
                    alertDialog.dismiss();

                    //Displaying a progressbar
                    final ProgressDialog loading = ProgressDialog.show(UserLogin.this, "Authenticating", "Please wait while we check the entered code", false, false);

                    //Getting the user entered otp from edittext
                    final String otp = editTextConfirmMobile.getText().toString().trim();

                    mRequestQueue = Volley.newRequestQueue(UserLogin.this);
                    mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {

                        public void onResponse(String response) {
                            //if the server response is success
                            try {
                                JSONObject jsonObject1 = new JSONObject(response);
                                if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                                    loading.dismiss();
                                    alertDialog.dismiss();
                                    confirmMobileOtp();
                                    //Starting a new activity
                                    //startActivity(new Intent(Login.this, MainActivity.class));
                                } else {

                                    Toast.makeText(getApplicationContext(), "Your Mobile Number is not registered with VMEDS.in ", Toast.LENGTH_LONG).show();
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

                                    Toast.makeText(UserLogin.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            //Adding the parameters otp and username

                            params.put("mobile", editTextConfirmMobile.getText().toString());
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    //This method would confirm the otp
    private void confirmMobileOtp() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);
        TextView txtConfirm = (TextView) confirmDialog.findViewById(R.id.txtConfirm);
        txtConfirm.setText("Confirm OTP");
        txtConfirm.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm = (Button) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmMobile = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
        // editTextConfirmMobile.setText(mobile_no);
        editTextNewPassword = (EditText) confirmDialog.findViewById(R.id.editTextNewPassword);
        editTextNewPassword.setVisibility(View.VISIBLE);
        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final android.app.AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCancelable(true);
        final String url = static_data.BASE_URL + static_data.FORGET_PASSWORD_CONFIRM_URL;

        //On the click of the confirm button from alert dialog
        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                if (editTextConfirmMobile.getText().toString().length() == 0)
                    editTextConfirmMobile.setError(Html.fromHtml("<font color='red'>Please Enter Mobile Number</font>"));
                else if (editTextNewPassword.getText().toString().length() == 0)
                    editTextNewPassword.setError(Html.fromHtml("<font color='red'>Please Enter New Password</font>"));
                else if (isNetworkConnected()) {
                    //Displaying a progressbar
                    final ProgressDialog loading = ProgressDialog.show(UserLogin.this, "Authenticating", "Please wait while we check the entered code", false, false);

                    //Getting the user entered otp from edittext
                    final String otp = editTextConfirmMobile.getText().toString().trim();

                    mRequestQueue = Volley.newRequestQueue(UserLogin.this);
                    mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {

                        public void onResponse(String response) {
                            //if the server response is success
                            try {
                                JSONObject jsonObject1 = new JSONObject(response);
                                if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                                    loading.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Your password has been successfully changed..", Toast.LENGTH_LONG).show();

                                    //Starting a new activity
                                    //startActivity(new Intent(Login.this, MainActivity.class));
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(getApplicationContext(), "Please enter valid otp ", Toast.LENGTH_LONG).show();
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

                                    Toast.makeText(UserLogin.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            //Adding the parameters otp and username

                            params.put("otp", editTextConfirmMobile.getText().toString());
                            params.put("new_password", editTextNewPassword.getText().toString());

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

    public boolean checkEmptyDataPassword() {
        boolean flag = false;
        if (editTextNewPassword.getText().toString().length() == 0 || editTextConfirmMobile.getText().toString().length() == 0)
            flag = false;
        return flag;
    }
}