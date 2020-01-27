package com.VMEDS.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.orhanobut.dialogplus.DialogPlus;
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfileFragment extends Fragment {


    private Global_Typeface typeface;
    private TextView myOrder, wishList, editProfile, userLogout, changePassword;
    private RelativeLayout loginFragment, userProfileFragment;
    private static final String PREF_NAME = "name";
    private WebView uWebView;
    private String TAG;
    private TextView userLogin;
    private TextView userSigin;
    private ImageView imageu;
    private com.facebook.login.widget.LoginButton loginButton;
    private String userId, userToken;
    private EditText input_email, input_password;
    private Button btn_login, btnFB, btnGPlus;
    private Global_Typeface global_typeface;
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    public TextView txtCreateAccount, link_forget;
    private AVLoadingIndicatorView loadingAvi;
    private StaticData static_data;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private EditText editTextConfirmMobile, editTextNewPassword;
    private Button buttonConfirm;
    private String mobile_no;
    private CallbackManager callbackManager;
    private DialogPlus dialog;
    private String strMobileNum;
    private int RC_SIGN_IN = -1;
    private GoogleApiClient mGoogleApiClient;
    private String USER_LOGIN = "email";

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String user_id = pref.getString("user_id", null);
        loginFragment = (RelativeLayout) v.findViewById(R.id.loginFragment);
        userProfileFragment = (RelativeLayout) v.findViewById(R.id.userProfileFragment);
        if (user_id != null) {
            loginFragment.setVisibility(View.GONE);
            userProfileFragment.setVisibility(View.VISIBLE);
        } else {
            loginFragment.setVisibility(View.VISIBLE);
            userProfileFragment.setVisibility(View.GONE);
        }
        myOrder = (TextView) v.findViewById(R.id.myorder);
        wishList = (TextView) v.findViewById(R.id.wishlist);
        editProfile = (TextView) v.findViewById(R.id.editProfile);
        userLogout = (TextView) v.findViewById(R.id.userLogout);
        changePassword = (TextView) v.findViewById(R.id.changePassword);
        typeface = new Global_Typeface(getActivity());
        myOrder.setTypeface(typeface.TypeFace_Roboto_Bold());
        wishList.setTypeface(typeface.TypeFace_Roboto_Bold());
        editProfile.setTypeface(typeface.TypeFace_Roboto_Bold());
        userLogout.setTypeface(typeface.TypeFace_Roboto_Bold());
        changePassword.setTypeface(typeface.TypeFace_Roboto_Bold());

        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VMEDS) getActivity().getApplicationContext()).setFromWishList(0);
                Intent i = new Intent(getActivity().getApplication(), NewMyOrdersActivity.class);
                startActivity(i);
            }
        });
        wishList.setVisibility(View.GONE);
        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VMEDS) getActivity().getApplicationContext()).setFromWishList(1);
                Intent i = new Intent(getActivity().getApplication(), MyOrdersActivity.class);
                startActivity(i);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), ProfileActivity.class);
                startActivity(i);
            }
        });
        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), CPassActivity.class);
                startActivity(i);
            }
        });


//        static_data = new StaticData();
//        userLogin = (TextView) v.findViewById(R.id.user_l);
//        userSigin = (TextView) v.findViewById(R.id.user_reg);
//        imageu = (ImageView) v.findViewById(R.id.user_profile_photo);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(),
//                R.drawable.profile);
//        loadingAvi = (AVLoadingIndicatorView) v.findViewById(R.id.loadingAvi);
//        loadingAvi.setVisibility(View.GONE);
//        imageu.setImageBitmap(getCircleBitmap(bm));
//        loginButton = (com.facebook.login.widget.LoginButton) v.findViewById(R.id.login_button);
//
//        callbackManager = CallbackManager.Factory.create();
//
////        TextView awishList=(TextView)v.findViewById(R.id.awishlist);
////        TextView about=(TextView)v.findViewById(R.id.about);
////        TextView contactUs=(TextView)v.findViewById(R.id.contactus);
//
//        userLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((VMEDS) getActivity().getApplicationContext()).setloginFromHome(1);
//
//                Intent i = new Intent(getActivity().getApplication(), UserLogin.class);
//                startActivity(i);
//            }
//        });
//        userSigin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity().getApplication(), Register.class);
//                startActivity(i);
//            }
//        });
//        loginButton.setFragment(this);
//        loginButton.setReadPermissions("public_profile", "user_friends", "user_photos", "email", "user_birthday", "public_profile", "contact_email");
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email"));
//
//                userId = loginResult.getAccessToken().getUserId();
//                userToken = loginResult.getAccessToken().getToken();
//                Log.e("Hello", userId + "");
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//
//                            @Override
//                            public void onCompleted(JSONObject jsonArray, GraphResponse response) {
//                                try {
//                                    Log.v("Main", jsonArray.toString());
//
//                                    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
//                                    Log.v("Main", jsonArray.toString());
//                                    SharedPreferences.Editor editor = pref.edit();
//                                    Log.v("Main", jsonArray.toString());
//                                    editor.putString("user_id", jsonArray.getString("id"));
//                                    Log.v("Main", jsonArray.toString());
//
//                                    editor.putString("user_fname", jsonArray.getString("first_name"));
//                                    Log.v("Main", jsonArray.toString());
//                                    editor.putString("user_lname", jsonArray.getString("last_name"));
//                                    Log.e("Hello URL", jsonArray.getString("picture") + " ");
//                                    JSONObject data = response.getJSONObject();
//                                    if (data.has("picture")) {
//                                        Log.e("Hello", "Pic");
//                                        String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
//                                        editor.putString("user_photo", profilePicUrl);
//                                        Log.e("URL", profilePicUrl + "");
//                                    }
//                                    editor.commit();
//                                    ((VMEDS) getActivity().getApplicationContext()).setFragmentIndex(2);
//                                    loginFragment.setVisibility(View.GONE);
//                                    userProfileFragment.setVisibility(View.VISIBLE);
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender, birthday,last_name,first_name,picture.type(large)");
//                request.setParameters(parameters);
//                request.executeAsync();
////                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
////                SharedPreferences.Editor editor = pref.edit();
////                editor.putString("user_id", userId);
////
////                editor.commit();
//                // registerAsFacebookUser(userId);
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//
//            }
//        });


        static_data = new StaticData();
        loadingAvi = (AVLoadingIndicatorView) v.findViewById(R.id.loadingAvi);
        global_typeface = new Global_Typeface(getActivity());
        input_email = (EditText) v.findViewById(R.id.input_email);
        input_password = (EditText) v.findViewById(R.id.input_password);
        btn_login = (Button) v.findViewById(R.id.btn_login);
        //  btnFB = (Button) v.findViewById(R.id.btnFB);
        btnGPlus = (Button) v.findViewById(R.id.btnGPlus);
        txtCreateAccount = (TextView) v.findViewById(R.id.txtCreateAccount);
        link_forget = (TextView) v.findViewById(R.id.link_forget);
        link_forget.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        input_email.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        input_password.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtCreateAccount.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        btnGPlus.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        // btnFB.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        btn_login.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage((FragmentActivity) getActivity() /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                }/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        callbackManager = CallbackManager.Factory.create();


        com.facebook.login.widget.LoginButton loginButton = (com.facebook.login.widget.LoginButton) v.findViewById(R.id.btnFB);
        loginButton.setReadPermissions("public_profile", "user_friends", "user_photos", "email", "user_birthday", "public_profile");
        loginButton.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        String userId = loginResult.getAccessToken().getUserId();
                        String userToken = loginResult.getAccessToken().getToken();
                        Log.e("Hello", userId + " " + userToken);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject jsonArray, GraphResponse response) {
                                        try {
                                            RC_SIGN_IN = 1;
                                            Log.e("Facebook", jsonArray.getString("id"));
                                            Log.e("Facebook", jsonArray.getString("first_name"));
                                            Log.e("Facebook", jsonArray.getString("last_name"));
                                            Log.e("Facebook", jsonArray.getString("email"));
                                            //Log.e("Facebook", jsonArray.getString("user_mobile_phone"));
                                            //  registerAsFacebookUser(jsonArray);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday,last_name,first_name,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();


                        /*System.out.println("onSuccess");

                        String accessToken = loginResult.getAccessToken()
                                .getToken();
                        Log.i("accessToken", accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {

                                        Log.i("LoginActivity",
                                                response.toString());
                                        try {
                                            String id = object.getString("id");
                                            try {
                                                URL profile_pic = new URL(
                                                        "http://graph.facebook.com/" + id + "/picture?type=large");
                                                Log.i("profile_pic",
                                                        profile_pic + "");

                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }
                                            String name = object.getString("name");
                                            String email = object.getString("email");
                                            String gender = object.getString("gender");
                                            String birthday = object.getString("birthday");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();*/
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });

        btn_login.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (input_email.getText().toString().length() == 0)
                                                 input_email.setError(Html.fromHtml("<font color='red'>Field can not be empty</font>"));
                                             else if (input_password.getText().toString().length() == 0)
                                                 input_password.setError(Html.fromHtml("<font color='red'>Field can not be empty</font>"));
                                             else {
                                                 if (isValidEmail(input_email.getText().toString())) {
                                                     USER_LOGIN = "email";
                                                     loginUser();
                                                 } else if (input_email.getText().toString().trim().length() == 10) {
                                                     USER_LOGIN = "mobile";
                                                     loginUser();
                                                 } else
                                                     Toast.makeText(getActivity().getApplicationContext(), "Please Enter Valid data", Toast.LENGTH_LONG).show();
                                             }
                                         }
                                     }

        );

        txtCreateAccount.setOnClickListener(new View.OnClickListener()

                                            {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent i = new Intent(getActivity(), Register.class);
                                                    //getActivity().finish();
                                                    startActivity(i);
                                                }
                                            }

        );

        link_forget.setOnClickListener(new View.OnClickListener()

                                       {

                                           public PopupWindow pwindo;

                                           @Override
                                           public void onClick(View v) {
                                               //initiatePopupwindow();
//                Intent i = new Intent(Login.this, FOtherActivity.class);
//                startActivity(i);
                                               confirmMobileNumber();
                                           }

                                       }

        );

        btnGPlus.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {
                                            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                                            RC_SIGN_IN = 2;
                                            loadingAvi.setVisibility(View.VISIBLE);
                                            startActivityForResult(signInIntent, RC_SIGN_IN);
                                        }
                                    }

        );
        return v;

    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("Hello", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            loadingAvi.setVisibility(View.VISIBLE);
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    loadingAvi.setVisibility(View.GONE);
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (RC_SIGN_IN == 2) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//            loadingAvi.setVisibility(View.GONE);
//        } else if (RC_SIGN_IN == 1)
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Hello", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//
//            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Bhangarikaka", 0); // 0 - for private mode
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("user_id", personId);
//
//            editor.putString("user_fname", personGivenName);
//            editor.putString("user_lname", personFamilyName);
//            editor.putString("user_email", personEmail);
//            editor.putString("user_photo", personPhoto.toString());
//            editor.commit();
//
//            Log.e("Hello", " " + personEmail + personFamilyName + personGivenName + personId + personName);
//

        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    private void confirmMobileNumber() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(getActivity());
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        TextView txtConfirm = (TextView) confirmDialog.findViewById(R.id.txtConfirm);
        txtConfirm.setText("Confirm Mobile Number");
        txtConfirm.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        buttonConfirm = (Button) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmMobile = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
        editTextConfirmMobile.setHint("Enter Mobile Number");
        //editTextConfirmMobile.setText(input_email.getText().toString());

        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final android.app.AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCancelable(true);
        final String url = static_data.BASE_URL + static_data.FORGET_PASSWORD_OTP_URL;
        Log.e("Hello", url);
        //On the click of the confirm button from alert dialog
        buttonConfirm.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Hiding the alert dialog
                if (editTextConfirmMobile.getText().toString().length() == 0)
                    editTextConfirmMobile.setError(Html.fromHtml("<font color='red'>Please Enter Mobile Number</font>"));
                else if (isNetworkConnected()) {
                    alertDialog.dismiss();

                    //Displaying a progressbar
                    final ProgressDialog loading = ProgressDialog.show(getActivity(), "Authenticating", "Please wait ", false, false);

                    //Getting the user entered otp from edittext
                    final String otp = editTextConfirmMobile.getText().toString().trim();

                    mRequestQueue = Volley.newRequestQueue(getActivity());
                    mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {

                        public void onResponse(String response) {
                            //if the server response is success
                            try {
                                JSONObject jsonObject1 = new JSONObject(response);
                                if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                                    loading.dismiss();
                                    alertDialog.dismiss();
                                    mobile_no = editTextConfirmMobile.getText().toString();
                                    confirmMobileOtp();
                                    //Starting a new activity
                                    //startActivity(new Intent(Login.this, HomeActivity.class));
                                } else {
                                    loading.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "Your Mobile Number is not registered.", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                loading.dismiss();
                                alertDialog.dismiss();
                            }


                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alertDialog.dismiss();
                                    loading.dismiss();

                                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            //Adding the parameters otp and username

                            params.put("mobile_no", editTextConfirmMobile.getText().toString());
                            return params;
                        }
                    };

                    //Adding the request to the queue
                    mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mRequestQueue.add(mStringRequest);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    public void registerAsFacebookUser(final JSONObject jsonArray) {
        try {
            if (isNetworkConnected()) {
                loadingAvi.setVisibility(View.VISIBLE);
                String url = static_data.BASE_URL + static_data.FB_REGISTER_URL;
                Log.i("URL", url);
                mRequestQueue = Volley.newRequestQueue(getActivity());
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
//                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("Category_data"));
//
//                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
//                                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("HouseHold", 0); // 0 - for private mode
//                                SharedPreferences.Editor editor = pref.edit();
//                                editor.putString("user_id", jsonArray.getString("id"));
//                                editor.putString("username", jsonArray.getString("first_name"));
//                                editor.putString("user_email", jsonArray.getString("email"));
//                                JSONObject data = response.getJSONObject();
//                                if (data.has("picture")) {
//                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
//                                    editor.putString("user_photo", profilePicUrl);
//                                }
//                                editor.commit();

//                                Intent i = new Intent(getActivity(), LocationActivity.class);
//                                getActivity().finish();
//                                startActivity(i);

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
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        //Adding the parameters otp and username
                        try {
                            params.put("email", jsonArray.getString("email"));
                            params.put("fb_id", jsonArray.getString("id"));
                            params.put("mobile", strMobileNum);
                            params.put("username", jsonArray.getString("first_name"));
                            params.put("address", "address");

                        } catch (Exception e) {

                        }
                        return params;
                    }
                };
                mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mRequestQueue.add(mStringRequest);
            } else
                Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //This method would confirm the otp
    private void confirmMobileOtp() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(getActivity());
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
        editTextConfirmMobile.setHint("Enter OTP");
        editTextNewPassword.setHint("Enter New Password");

        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final android.app.AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCancelable(true);
        final String url = static_data.BASE_URL + static_data.FORGET_PASSWORD_CONFIRM_URL;
        buttonConfirm.setTypeface(global_typeface.TypeFace_Roboto_Regular());

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
                    final ProgressDialog loading = ProgressDialog.show(getActivity(), "Authenticating", "Please wait while we check the entered code", false, false);

                    //Getting the user entered otp from edittext
                    final String otp = editTextConfirmMobile.getText().toString().trim();

                    mRequestQueue = Volley.newRequestQueue(getActivity());
                    mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {

                        public void onResponse(String response) {
                            //if the server response is success
                            try {
                                JSONObject jsonObject1 = new JSONObject(response);
                                if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                                    loading.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "Your password has been successfully changed..", Toast.LENGTH_LONG).show();

                                    //Starting a new activity
                                    //startActivity(new Intent(Login.this, HomeActivity.class));
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "Please enter valid otp ", Toast.LENGTH_LONG).show();
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

                                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            //Adding the parameters otp and username
                            params.put("mobile_no", mobile_no);

                            params.put("otp", editTextConfirmMobile.getText().toString());
                            params.put("password", editTextNewPassword.getText().toString());

                            return params;
                        }
                    };

                    //Adding the request to the queue
                    mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mRequestQueue.add(mStringRequest);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    public void loginUser() {
        if (isNetworkConnected()) {
            loadingAvi.setVisibility(View.VISIBLE);
            String url = static_data.BASE_URL + static_data.LOGIN_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(getActivity());
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
                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("user_id", jsonArray.getString("id"));
                            editor.putString("username", jsonArray.getString("username"));
                            editor.putString("user_mobile", jsonArray.getString("phone"));
                            editor.putString("user_email", jsonArray.getString("email"));

                            editor.putString("user_image", jsonArray.getString("photo"));


                            editor.commit();

//                            Intent i = new Intent(getActivity(), HomeActivity.class);
//                            startActivity(i);

                            loadingAvi.setVisibility(View.GONE);

                            loginFragment.setVisibility(View.GONE);
                            userProfileFragment.setVisibility(View.VISIBLE);

                            // getActivity().finish();

                        } else {
                            Toast.makeText(getActivity(), "Please Enter Valid Credentials...", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(USER_LOGIN, input_email.getText().toString());
                    params.put("password", input_password.getText().toString());

                    return params;
                }
            };
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginUser();
                }
            }, 5000);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String user_id = pref.getString("user_id", null);

        if (user_id != null) {
            loginFragment.setVisibility(View.GONE);
            userProfileFragment.setVisibility(View.VISIBLE);
        } else {
            loginFragment.setVisibility(View.VISIBLE);
            userProfileFragment.setVisibility(View.GONE);
        }
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == 2) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//            loadingAvi.setVisibility(View.GONE);
//        } else {
        callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
    }


    public void registerAsFacebookUser(final String userId) {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.FB_REGISTER_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(getActivity());
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
//                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("Category_data"));
//
//                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

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
                    params.put("fb_id", userId);

                    return params;
                }
            };
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else
            Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

}
