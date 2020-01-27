package com.VMEDS.android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.VMEDS.android.utils.Global_Typeface;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {
    private TextView ctvTitle, txtUserName, txtUserEmail, txtUserMobile,  txtChangePass, txtUserNotification, txtLogout;
    private Switch switchNotification;
    private Global_Typeface global_typeface;
    private DialogPlus dialog;
    private AVLoadingIndicatorView loadingAvi;
    private StaticData static_data;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private CircularImageView imgProfile;
    private TextView txtChooseImage, txtCamera, txtGallery;
    private LinearLayout layoutChooseImage;
    private static final int REQUEST_IMAGE_CAPTURE = 8;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CAMERA = 9999;
    private String encodedImgStr = "";
    private Bitmap bmp;
    private ImageView civSearch,civAddcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        setContentView(R.layout.activity_profile);
        global_typeface = new Global_Typeface(ProfileActivity.this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setText("Profile");
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setVisibility(View.GONE);
        civSearch.setVisibility(View.GONE);
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);


        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserEmail = (TextView) findViewById(R.id.txtUserEmail);
        txtUserMobile = (TextView) findViewById(R.id.txtUserMobile);
        txtChangePass = (TextView) findViewById(R.id.txtChangePass);
        txtUserNotification = (TextView) findViewById(R.id.txtUserNotification);
        txtLogout = (TextView) findViewById(R.id.txtLogout);
        imgProfile = (CircularImageView) findViewById(R.id.imgProfile);
        switchNotification = (Switch) findViewById(R.id.switchNotification);

        txtUserName.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtUserEmail.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtUserMobile.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtUserNotification.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtLogout.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        txtChangePass.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        final String user_image = pref.getString("user_image", null);
        if (user_image != null) {
            if (user_image.contains("http://")) {
                Glide.with(ProfileActivity.this).load(user_image).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("Done", "Done0");
//                    loadingAvi.setVisibility(View.GONE);
//                    loadingAvi.smoothToHide();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e("Done", "Done1");
//                    loadingAvi.setVisibility(View.GONE);
//                    loadingAvi.smoothToHide();
                        return false;
                    }
                }).into(imgProfile);
            } else {

                byte[] decodedString = Base64.decode(user_image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgProfile.setImageBitmap(decodedByte);
            }
        } else {
            imgProfile.setImageResource(R.drawable.doc);
        }


        txtUserName.setText(pref.getString("username", null));

        txtUserMobile.setText(pref.getString("user_mobile", null));

        txtUserEmail.setText(pref.getString("user_email", null));


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.clear();

                editor = pref.edit();
                editor.putString("user_id", null);
                editor.putString("username", null);
                editor.putString("user_mobile", null);
                editor.putString("user_email", null);
                editor.putString("user_notification", null);
                editor.putString("user_image", null);

                editor.commit();
                Toast.makeText(getApplicationContext(), "Successfully logged out.", Toast.LENGTH_LONG).show();

                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                finish();
                startActivity(i);

            }
        });
        if (pref.getString("user_notification", null) != null) {
            if (Integer.parseInt(pref.getString("user_notification", null)) == 1) {
                switchNotification.setChecked(true);
            } else {
                switchNotification.setChecked(false);
            }
        }
        txtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog(1);
            }
        });

        txtUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog(3);
            }
        });

        txtUserMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog(2);
            }
        });

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    editProfile(5, "1");
                } else {
                    editProfile(5, "2");
                }
            }
        });

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, CPassActivity.class);
                finish();
                startActivity(i);
            }
        });
        imgProfile = (CircularImageView) findViewById(R.id.imgProfile);
        layoutChooseImage = (LinearLayout) findViewById(R.id.layoutChooseImage);

        txtChooseImage = (TextView) findViewById(R.id.txtChooseImage);
        txtChooseImage.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtCamera = (TextView) findViewById(R.id.txtCamera);
        txtCamera.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtGallery = (TextView) findViewById(R.id.txtGallery);
        txtGallery.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        txtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutChooseImage.startAnimation(AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.view_hide));
                layoutChooseImage.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getPermissionToUseCamera();
                } else {

                    dispatchTakePictureIntent();
                }
            }
        });

        txtGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutChooseImage.startAnimation(AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.view_hide));
                layoutChooseImage.setVisibility(View.GONE);
                showFileChooser();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutChooseImage.getVisibility() == View.VISIBLE) {
                    layoutChooseImage.startAnimation(AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.view_hide));
                    layoutChooseImage.setVisibility(View.GONE);
                } else {
                    layoutChooseImage.startAnimation(AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.view_show));
                    layoutChooseImage.setVisibility(View.VISIBLE);
                }
            }
        });
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2);
    }

    public void getPermissionToUseCamera() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        } else {
            dispatchTakePictureIntent();
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(getActivity(), "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
                dispatchTakePictureIntent();
            } else {
                showFileChooser();
                Toast.makeText(ProfileActivity.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void showFileChooser() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            Uri imageUri = data.getData();

            try {
//                Bitmap bitmaps = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//
//                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmaps.compress(Bitmap.CompressFormat.PNG, 90, stream);
//                byte[] byteArray = stream.toByteArray();
//
//                String encodeded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//                byte[] decodedString = Base64.decode(encodeded, Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                bmp = decodeUri(imageUri);
                encodedImgStr = getStringImage(bmp);

                editProfile(6, encodedImgStr);
                // new UploadImage().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            bmp = (Bitmap) data.getExtras().get("data");
            encodedImgStr = getStringImage(bmp);
            editProfile(6, encodedImgStr);
            // imgProfile.setImageBitmap(photo);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkValidation(int flag, EditText edit_info) {
        boolean returnFlag = false;
        if (flag == 1) {
            if (edit_info.getText().toString().length() == 0) {
                edit_info.requestFocus();
                edit_info.setError(Html.fromHtml("<font color='red'>Please Enter Name</font>"));
                returnFlag = false;
            } else
                returnFlag = true;
        } else if (flag == 2) {


            if ((edit_info.getText().toString().length() == 0) || (edit_info.getText().toString().length() != 10)) {
                edit_info.requestFocus();
                returnFlag = false;
                edit_info.setError(Html.fromHtml("<font color='red'>Please Enter Valid Mobile Number</font>"));
            } else {
                returnFlag = true;
            }
        } else if (flag == 3) {


            if ((edit_info.getText().toString().length() == 0) || !isValidEmail(edit_info.getText().toString())) {
                edit_info.requestFocus();
                returnFlag = false;
                edit_info.setError(Html.fromHtml("<font color='red'>Please Enter Valid Email Address</font>"));
            } else {
                returnFlag = true;
            }
        }
        return returnFlag;
    }

    public void showEditProfileDialog(final int flag) {
        ViewHolder vEdit = new ViewHolder(R.layout.layout_editprofile);
        dialog = DialogPlus.newDialog(ProfileActivity.this).setContentHolder(vEdit).setExpanded(true).setGravity(Gravity.CENTER).create();
        dialog.show();

        View viewEdit = dialog.getHolderView();
        final EditText edit_info = (EditText) viewEdit.findViewById(R.id.edit_info);
        edit_info.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        TextView txtEditInfo = (TextView) viewEdit.findViewById(R.id.txtEditInfo);
        txtEditInfo.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        Button btnSubmit = (Button) viewEdit.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                if (checkValidation(flag, edit_info)) {
                    editProfile(flag, edit_info.getText().toString());
                    dialog.dismiss();
                    View view = ProfileActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });
        if (flag == 1) {
            edit_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.usericon, 0, 0, 0);
            edit_info.setHint("Enter Name");
        } else if (flag == 2) {
            edit_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mobile, 0, 0, 0);
            edit_info.setHint("Enter Phone Number");
        } else if (flag == 3) {
            edit_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email, 0, 0, 0);
            edit_info.setHint("Enter Email Address");

        }
        loadingAvi = (AVLoadingIndicatorView) viewEdit.findViewById(R.id.loadingAvi);


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

    public void editProfile(final int flag, final String editString) {
        if (isNetworkConnected()) {
            loadingAvi.setVisibility(View.VISIBLE);
            String url = static_data.BASE_URL + static_data.EDIT_PROFILE_URL;
            Log.i("URL", url + " " + flag);
            mRequestQueue = Volley.newRequestQueue(ProfileActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", "Res " + response);

                        JSONObject jsonObject1 = new JSONObject(response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
//                            if (flag == 1)
//                                editor.putString("username", editString);
//                            else if (flag == 2)
//                                editor.putString("user_mobile", editString);
//                            else if (flag == 3)
//                                editor.putString("user_email", editString);
//                            else if (flag == 4)
//                                editor.putString("user_address", editString);
//                            else if (flag == 5)
//                                editor.putString("user_notification", editString);
//                            else if (flag == 6) {
//                                imgProfile.setImageBitmap(bmp);
//                                editor.putString("user_image", editString);
//                            }

                            if (flag == 1) {
                                txtUserName.setText(editString);
                                editor.putString("username", editString);
                            } else if (flag == 2) {
                                txtUserMobile.setText(editString);

                                editor.putString("user_mobile", editString);
                            } else if (flag == 3) {
                                txtUserEmail.setText(editString);

                                editor.putString("user_email", editString);
                            }  else if (flag == 5) {
                                if (Integer.parseInt(editString) == 1) {
                                    switchNotification.setChecked(true);
                                } else
                                    switchNotification.setChecked(false);
                                editor.putString("user_notification", editString);
                            } else if (flag == 6) {
                                imgProfile.setImageBitmap(bmp);
                                // editor.putString("user_image", editString);
                            }


                            editor.commit();
                            Toast.makeText(ProfileActivity.this, "Successfully Edited..", Toast.LENGTH_LONG).show();

//                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                            startActivity(i);

                            loadingAvi.setVisibility(View.GONE);

                            //  finish();

                        } else {
                            Toast.makeText(ProfileActivity.this, "Error Occured", Toast.LENGTH_LONG).show();
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

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    final String user_id = pref.getString("user_id", null);
                    Log.e("User", "Name up" + flag);
                    params.put("user_id", user_id);
                    if (flag == 1) {
                        Log.e("User", "Name");
                        params.put("name", editString.trim());
                    } else if (flag == 2)
                        params.put("mobile", editString.trim());
                    else if (flag == 3)
                        params.put("email", editString.trim());
                    else if (flag == 4)
                        params.put("address", editString.trim());
                    else if (flag == 5)
                        params.put("notification", editString.trim());
                    else if (flag == 6)
                        params.put("photo", editString.trim());

                    return params;
                }
            };
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(mStringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

}
