package com.VMEDS.android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.model.CategoryDetail;
import com.VMEDS.android.utils.AddtoCart_List;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private Toolbar mToolbar;
    private ExpandableListView expListView;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mMenuAdapter;
    private ExpandableListAdapter listAdapter;
    private Boolean exit = false;
    private boolean doubleBackToExitPressedOnce = false;
    private long lastPressedTime;
    private static final int PERIOD = 2000;
    private String TAG;
    private Context context;
    private StaticData static_data;
    private Vector<CategoryDetail> vCategory;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private AVLoadingIndicatorView loadingAvi;
    int previousGroup = -1;
    public TextView ctvTitle, textCount;
    public ImageView civSearch, civAddcart;
    public TextView imgLogo;
    public Global_Typeface global_typeface;
    private AddtoCart_List cart_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);
        loadingAvi.setVisibility(View.VISIBLE);
        static_data = new StaticData();
        global_typeface = new Global_Typeface(MainActivity.this);
        // Add code to print out the key hash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.neegambazaar.android",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
        cart_item = new AddtoCart_List(MainActivity.this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        mExpandableListView = (ExpandableListView) findViewById(R.id.navigationmenu);

        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        textCount = (TextView) findViewById(R.id.textCount);
        imgLogo = (TextView) findViewById(R.id.imgLogo);
        imgLogo.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        ctvTitle.setVisibility(View.GONE);
        imgLogo.setVisibility(View.VISIBLE);

        civAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VMEDS) getApplicationContext()).setFromWishList(0);
                Intent i = new Intent(MainActivity.this, MyOrdersActivity.class);
//                finish();
                startActivity(i);
            }
        });
        civSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
//                finish();
                startActivity(i);
            }
        });
        civSearch.setVisibility(View.VISIBLE);
        textCount.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        Vector<AddtoCartDetail> vCartList = cart_item.getCartList();
        if (vCartList.size() > 0) {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(String.valueOf(vCartList.size()));

        } else
            textCount.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }

        if (mNavigationView != null) {
            setupDrawerContenet(mNavigationView);
        }


        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */


        /**
         * Setup click events on the Navigation View Items.
         */
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                previousGroup = -1;
                return false;
            }

        });


        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
//        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        prepareListData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
        //finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "Hello Destroy", Toast.LENGTH_LONG).show();
        // System.exit(0);
        //finish();
    }

    private void prepareListData() {
        vCategory = ((VMEDS) getApplicationContext()).getvCategoryList();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding data header
        listDataHeader.add("Home");

        listDataHeader.add("My Orders");

        listDataHeader.add("My Cart");

        listDataHeader.add("Share App");

        listDataHeader.add("Show Video");

//        SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//        String user_id = pref.getString("user_id", null);
//        if (user_id != null) {
//
//            listDataHeader.add("Logout");
//        }

//        for (int i = 0; i < vCategory.size(); i++) {
//            listDataHeader.add(((CategoryDetail) vCategory.elementAt(i)).category_name);
//            List<String> subCatList = new ArrayList<String>();
//            Vector<CategoryDetail> vSubCatList = ((CategoryDetail) vCategory.elementAt(i)).vSubCat;
//            for (int j = 0; j < vSubCatList.size(); j++) {
//                subCatList.add(((CategoryDetail) vSubCatList.elementAt(j)).sub_category_name);
//            }
//            listDataChild.put(listDataHeader.get(i + 2), subCatList);
//        }

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, mExpandableListView, 0);
        mExpandableListView.setAdapter(listAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    mExpandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;

                if (groupPosition == 0)
                    mDrawerLayout.closeDrawers();
            }
        });
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 1) {
                    Intent intent = new Intent(MainActivity.this, NewMyOrdersActivity.class);
                    startActivity(intent);
                } else if (groupPosition == 2) {
                    ((VMEDS) getApplicationContext()).setFromWishList(0);
                    Intent intent = new Intent(MainActivity.this, MyOrdersActivity.class);
                    startActivity(intent);
                }else if(groupPosition==3){
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                        String extraText = "https://play.google.com/store/apps/details?id=com.neegambazaar.android";
                        share.putExtra(Intent.EXTRA_TEXT, extraText);
                    startActivity(Intent.createChooser(share, "Share App via"));
                } else if (groupPosition==4){
                    Intent intent = new Intent(MainActivity.this, video_view_list.class);
                    startActivity(intent);
                }
//                else if (groupPosition == 4) {
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("NigamBazaar", 0); // 0 - for private mode
//                    SharedPreferences.Editor editor = pref.edit();
//
//                    editor.clear();
//
//                    editor = pref.edit();
//                    editor.putString("user_id", null);
//                    editor.putString("username", null);
//                    editor.putString("user_mobile", null);
//                    editor.putString("user_email", null);
//                    editor.putString("user_notification", null);
//                    editor.putString("user_image", null);
//
//                    editor.commit();
//                    Toast.makeText(getApplicationContext(), "Successfully logged out.", Toast.LENGTH_LONG).show();
//
//
//                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        mExpandableListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ((VMEDS) getApplicationContext()).setFromHome(0);
                if (groupPosition != 0) {
                    ((VMEDS) getApplicationContext()).setObjSubCat(((CategoryDetail) vCategory.elementAt(groupPosition - 1)));
                    ((VMEDS) getApplicationContext()).setCurrentIndex(childPosition);
                    ((VMEDS) getApplicationContext()).setFromWholeSaler(0);
                    Intent intent = new Intent(MainActivity.this, NewCategoryWiseProduct.class);
                    startActivity(intent);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void getCategoryList() {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.CATEGORY_URL;
            Log.i("URL", url);
            mRequestQueue = Volley.newRequestQueue(MainActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vCategory = new Vector<CategoryDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
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


                            loadingAvi.setVisibility(View.GONE);
                        } else {
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
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getCategoryList();
                }
            }, 5000);
        }
    }


    private void setupDrawerContenet(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    public TwoStatePreference menuItem;

                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Press again to close VMEDS.",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                    return false;
            }
        }

        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        Vector<AddtoCartDetail> vCartList = cart_item.getCartList();
        if (vCartList.size() > 0) {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(String.valueOf(vCartList.size()));

        } else
            textCount.setVisibility(View.GONE);


    }
}