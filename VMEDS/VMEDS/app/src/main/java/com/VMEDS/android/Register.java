package com.VMEDS.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity implements View.OnClickListener {


    public static final String KEY_USERNAME = "email";
    public static final String KEY_MOBILE_NUMBER = "mobile";
    public static final String KEY_PASSWORD = "password";
    private static final String KEY_OTP = "otp";
    private AVLoadingIndicatorView loadingAvi;
    StringRequest mStringRequest;
    RequestQueue mRequestQueue;
    private EditText Emial_id, firstName;
    private EditText Contact_number;
    private EditText Password;
    private EditText Conform_password;
    private Button Submit;
    private HashMap<String, String> map;
    private Button buttonConfirm;
    private EditText editTextConfirmOtp;
    private String email, mobile, password, firstname;
    private RequestQueue requestQueue;
    private StaticData static_data;
    private int REQUEST_CODE = 1;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String userId, userToken;
    private TextView ctvTitle;
    private ImageView civSearch, civAddcart;
    private Global_Typeface global_typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register);
        global_typeface = new Global_Typeface(Register.this);
        static_data = new StaticData();
        Submit = (Button) findViewById(R.id.submit);
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextInputLayout inputLayoutEmail = (TextInputLayout) findViewById(R.id.ilEmail);
        TextInputLayout inputLayoutNumber = (TextInputLayout) findViewById(R.id.ilNumber);
        TextInputLayout inputLayoutPassword = (TextInputLayout) findViewById(R.id.ilPassword);
        TextInputLayout inputLayoutCPassword = (TextInputLayout) findViewById(R.id.cilPassword);
        loginButton = (com.facebook.login.widget.LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        Emial_id = (EditText) findViewById(R.id.email_id);
        firstName = (EditText) findViewById(R.id.firstName);
        Contact_number = (EditText) findViewById(R.id.pnumber);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        ctvTitle.setText("Create New VMEDS Account");
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setVisibility(View.GONE);
        civSearch.setVisibility(View.GONE);
        civAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VMEDS) getApplicationContext()).setFromWishList(0);
                Intent i = new Intent(Register.this, MyOrdersActivity.class);
                finish();
                startActivity(i);
            }
        });
//        civSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Register.this, SearchActivity.class);
//                finish();
//                startActivity(i);
//            }
//        });
        civSearch.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }

        Selection.setSelection(Contact_number.getText(), Contact_number.getText().length());
        Password = (EditText) findViewById(R.id.upass);
        Conform_password = (EditText) findViewById(R.id.cupass);
        Submit.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        // add click listener to Button "POS
        Submit.setOnClickListener(this);

               loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                userId = loginResult.getAccessToken().getUserId();
                userToken = loginResult.getAccessToken().getToken();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("user_id", userId);

                editor.commit();
                registerAsFacebookUser(userId);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    public void registerAsFacebookUser(final String userId) {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.FB_REGISTER_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(Register.this);
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
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("Category_data"));

                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("fb_id", userId);
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
                    registerAsFacebookUser(userId);
                }
            }, 5000);
        }
    }

    private void registerUser() {
        if (isNetworkConnected()) {
            email = Emial_id.getText().toString().trim();
            password = Password.getText().toString().trim();
            mobile = Contact_number.getText().toString().trim();
            firstname = firstName.getText().toString().trim();
            loadingAvi.setVisibility(View.VISIBLE);
            final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            final String PhoneNo = "+123-456-7890";

            String url = static_data.BASE_URL + static_data.REGISTER_URL;
            mRequestQueue = Volley.newRequestQueue(Register.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {


                public void onResponse(String response) {
                    loadingAvi.setVisibility(View.GONE);

                    try {

                        JSONObject jsonObject1 = new JSONObject(response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            Toast.makeText(Register.this, "Registered Successfully...", Toast.LENGTH_LONG).show();
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("user_id", jsonObject1.getString("user_id"));
                            editor.putString("user_email", email);
                            String phonenum = mobile;
                            if (phonenum != null) {
                                if (phonenum.length() != 0) {
                                    if (phonenum.contains("+91"))
                                        phonenum = phonenum.substring(3);

                                }
                            }
                            editor.putString("user_mobile", phonenum);
                            editor.putString("user_password", password);
                            editor.putString("user_fname", firstname);

                            editor.commit();
                            finish();
//                                Intent i = new Intent(Register.this, MainActivity.class);
//                                finish();
//                                startActivity(i);


                            //confirmOtp();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
                    ,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingAvi.setVisibility(View.GONE);

                            Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

            )

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_USERNAME, email);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_MOBILE_NUMBER, mobile);
                    params.put("username", firstname);

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
                    registerUser();
                }
            }, 5000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onClick(View v) {
        if (v == Submit) {
            if (firstName.getText().toString().length() == 0) {
                firstName.requestFocus();
                firstName.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
            } else if (Emial_id.getText().toString().length() == 0) {
                Emial_id.requestFocus();
                Emial_id.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
            } else if (Contact_number.getText().toString().length() == 0) {
                Contact_number.requestFocus();
                Contact_number.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
            } else if (Password.getText().toString().length() == 0) {
                Password.requestFocus();
                Password.setError(Html.fromHtml("<font color='red'>FIELD CANNOT BE EMPTY</font>"));
            } else if (!isValidEmail(Emial_id.getText().toString())) {
                Emial_id.requestFocus();
                Emial_id.setError(Html.fromHtml("<font color='red'>Invalid Email ID</font>"));
            } else if ((Contact_number.getText().toString().length()) != 10) {
                Contact_number.requestFocus();
                Contact_number.setError(Html.fromHtml("<font color='red'>Invalid Contact No</font>"));
            } else if (!isPasswordMatching(Password.getText().toString(), Conform_password.getText().toString())) {
                Log.e("Hello", Password.getText().toString().trim() + " " + Conform_password.getText().toString().trim());
                Conform_password.requestFocus();
                Conform_password.setError(Html.fromHtml("<font color='red'>Password & Confirm Password should be same</font>"));
            } else {
                registerUser();
            }
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

    public boolean isPasswordMatching(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        if (!matcher.matches()) {
            // do your Toast("passwords are not matching");

            return false;
        }

        return true;
    }

    //This method would confirm the otp
    private void confirmOtp() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm = (Button) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCancelable(false);
        final String url = static_data.BASE_URL + static_data.CONFIRM_OTP_URL;

        //On the click of the confirm button from alert dialog
        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    //Hiding the alert dialog
                    alertDialog.dismiss();

                    //Displaying a progressbar
                    final ProgressDialog loading = ProgressDialog.show(Register.this, "Authenticating", "Please wait while we check the entered code", false, false);

                    //Getting the user entered otp from edittext
                    final String otp = editTextConfirmOtp.getText().toString().trim();

                    mRequestQueue = Volley.newRequestQueue(Register.this);
                    mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {

                        public void onResponse(String response) {
                            //if the server response is success
                            try {
                                JSONObject jsonObject1 = new JSONObject(response);
                                if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                                    loading.dismiss();
                                    //Starting a new activity
                                    startActivity(new Intent(Register.this, MainActivity.class));
                                } else {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.commit();
                                    loading.dismiss();
                                    Toast.makeText(getApplicationContext(), "Wrong Otp ", Toast.LENGTH_LONG).show();
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
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            //Adding the parameters otp and username
                            params.put("otp", otp);
                            params.put("mobile", Contact_number.getText().toString());
                            return params;
                        }
                    };

                    //Adding the request to the queue
                    mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mRequestQueue.add(mStringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            confirmOtp();
//
//                        }
//                    }, 5000);
                }
            }
        });
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                //Do whatever you want with the code here
            }
        }
    };
}