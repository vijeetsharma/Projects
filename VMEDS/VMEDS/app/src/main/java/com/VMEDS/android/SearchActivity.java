package com.VMEDS.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.wang.avi.AVLoadingIndicatorView;
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.model.CategoryDetail;
import com.VMEDS.android.model.ProductDetail;
import com.VMEDS.android.utils.AddtoCart_List;
import com.VMEDS.android.utils.Global_Typeface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SearchActivity extends AppCompatActivity {

    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private Vector<ProductDetail> vProductList;
    private AVLoadingIndicatorView loadingAvi;
    private TextView blankData;
    private GridView gridViewProduct;
    private ProductPagerAdapter productPagerAdapter;
    private StaticData static_data;
    public TextView ctvTitle, textCount;
    public ImageView civSearch, civAddcart, imgLogo;
    public Global_Typeface global_typeface;
    private AddtoCart_List cart_item;
    private Button btnSearch;
    private EditText edSearch;
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
    private Vector<CategoryDetail> vCategoryList;
    int previousGroup = -1;
    private RadioGroup radioGroupSearch;
    private RadioButton radioSearchByProduct, radioSearchByCategory;
    private String PARAM_NAME = "category";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        static_data = new StaticData();
        global_typeface = new Global_Typeface(SearchActivity.this);
        cart_item = new AddtoCart_List(SearchActivity.this);
        gridViewProduct = (GridView) findViewById(R.id.gridViewProduct);
        loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);
        blankData = (TextView) findViewById(R.id.blankData);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        edSearch = (EditText) findViewById(R.id.edSearch);
        blankData.setVisibility(View.GONE);
        loadingAvi.setVisibility(View.GONE);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        textCount = (TextView) findViewById(R.id.textCount);
        civAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VMEDS) getApplicationContext()).setFromWishList(0);
                Intent i = new Intent(SearchActivity.this, MyOrdersActivity.class);
                finish();
                startActivity(i);
            }
        });
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (edSearch.getText().toString().length() == 0) {
                        edSearch.setError(Html.fromHtml("<font color='red'>Please enter search text</font>"));
                    } else
                        getDatafromSearchText(edSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        edSearch.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        btnSearch.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        ctvTitle.setText("Search");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edSearch.getText().toString().length() == 0) {
                    edSearch.setError(Html.fromHtml("<font color='red'>Please enter search text</font>"));
                } else
                    getDatafromSearchText(edSearch.getText().toString());
            }
        });
        civSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, SearchActivity.class);
                finish();
                startActivity(i);
            }
        });
        textCount.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        Vector<AddtoCartDetail> vCartList = cart_item.getCartList();
        if (vCartList.size() > 0) {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(String.valueOf(vCartList.size()));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        mExpandableListView = (ExpandableListView) findViewById(R.id.navigationmenu);
        prepareListData();
        ViewTreeObserver vto = mExpandableListView.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mExpandableListView.setIndicatorBounds(mExpandableListView.getRight() - 100, mExpandableListView.getWidth());
                // mExpandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.arrow_bk));
            }
        });

        radioGroupSearch = (RadioGroup) findViewById(R.id.radioGroupSearch);
        radioSearchByProduct = (RadioButton) findViewById(R.id.radioSearchByProduct);
        radioSearchByCategory = (RadioButton) findViewById(R.id.radioSearchByCategory);
        radioSearchByProduct.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        radioSearchByCategory.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        radioGroupSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioSearchByCategory.getId()) {
                    PARAM_NAME = "categoty";
                } else if (checkedId == radioSearchByProduct.getId()) {
                    PARAM_NAME = "product_detail";
                }
                getDatafromSearchText(edSearch.getText().toString());
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding data header
        listDataHeader.add("Home");

        listDataHeader.add("My Orders");

        listDataHeader.add("My Cart");

        listDataHeader.add("Share App");

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
                if (groupPosition == 0) {
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                } else if (groupPosition == 1) {
                    Intent intent = new Intent(SearchActivity.this, NewMyOrdersActivity.class);
                    finish();
                    startActivity(intent);
                } else if (groupPosition == 2) {
                    ((VMEDS) getApplicationContext()).setFromWishList(0);
                    Intent intent = new Intent(SearchActivity.this, MyOrdersActivity.class);
                    finish();
                    startActivity(intent);
                } else if (groupPosition == 3) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    String extraText = "https://play.google.com/store/apps/details?id=com.neegambazaar.android";
                    share.putExtra(Intent.EXTRA_TEXT, extraText);
                    startActivity(Intent.createChooser(share, "Share App via"));
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });


    }

    public void onResume() {
        super.onResume();
        Vector<AddtoCartDetail> vCartList = cart_item.getCartList();
        if (vCartList.size() > 0) {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(String.valueOf(vCartList.size()));

        } else
            textCount.setVisibility(View.GONE);

    }

    class ProductPagerAdapter extends BaseAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        Vector<ProductDetail> vProductList;
        AVLoadingIndicatorView loadingAvi;
        ImageView product_image;
        LinearLayout layoutProduct;
        Global_Typeface globalTypeface;

        public ProductPagerAdapter(Context context, Vector<ProductDetail> vProductList) {
            mContext = context;
            this.vProductList = vProductList;
            globalTypeface = new Global_Typeface(context);
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        public int getCount() {
            return vProductList.size();
        }

        public Object getItem(int position) {
            return vProductList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View itemView, ViewGroup parent) {
            itemView = mLayoutInflater.inflate(R.layout.slider_item, null);

            product_image = (ImageView) itemView.findViewById(R.id.product_image);
            layoutProduct = (LinearLayout) itemView.findViewById(R.id.layoutProduct);
            layoutProduct.setBackgroundResource(R.drawable.product_bk);
            loadingAvi = (AVLoadingIndicatorView) itemView.findViewById(R.id.loadingAvi);
            loadingAvi.setVisibility(View.VISIBLE);
            try {

//                    JSONObject mainObject = new JSONObject(((ProductDetail) vProductList.elementAt(position)).images);
//                    Log.e("full", " " + ((ProductDetail) vProductList.elementAt(position)).images);
//                    Log.e("STr", mainObject.getString("1") + " ");
                //
                String imageStr = ((ProductDetail) vProductList.elementAt(position)).product_image;
                Log.e("Log STr", imageStr + "");
                if (!imageStr.contains("http://"))
                    imageStr = "http://" + imageStr;
                Glide.with(SearchActivity.this).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("Done", "Done0");
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e("Done", "Done1");
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        return false;
                    }
                }).into(product_image);


            } catch (Exception e) {
                e.printStackTrace();
            }


            TextView product_name = (TextView) itemView.findViewById(R.id.product_name);
            product_name.setText(((ProductDetail) vProductList.elementAt(position)).product_name);
            product_name.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
            TextView product_rs = (TextView) itemView.findViewById(R.id.product_rs);
            TextView product_sale_rs = (TextView) itemView.findViewById(R.id.product_sale_rs);
            TextView product_discount = (TextView) itemView.findViewById(R.id.product_discount);
            product_rs.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
            product_sale_rs.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
            product_discount.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
            if (((ProductDetail) vProductList.elementAt(position)).discount == null) {
                product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
                product_rs.setVisibility(View.GONE);
                product_discount.setVisibility(View.GONE);
                product_sale_rs.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
            } else {
                if (((ProductDetail) vProductList.elementAt(position)).discount.length() == 0) {
                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
                    product_rs.setVisibility(View.GONE);
                    product_discount.setVisibility(View.GONE);
                    product_sale_rs.setTypeface(globalTypeface.TypeFace_Roboto_Bold());

                } else if (Integer.parseInt(((ProductDetail) vProductList.elementAt(position)).discount) == 0) {
                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
                    product_rs.setVisibility(View.GONE);
                    product_discount.setVisibility(View.GONE);
                    product_sale_rs.setTypeface(globalTypeface.TypeFace_Roboto_Bold());
                } else {
                    // product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).purchase_price);
                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
                    product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    int discount = Integer.parseInt(((ProductDetail) vProductList.elementAt(position)).discount);
                    float sale_price = Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).selling_price);
                    float discount_price = ((int) sale_price * discount) / 100;
                    // float sale_price = Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).sale_price) - Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).sale_price);
                    product_rs.setText(getString(R.string.Rs) + " " + (sale_price - discount_price));
                    product_discount.setText("Discount " + ((ProductDetail) vProductList.elementAt(position)).discount + "%");
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((VMEDS) getApplicationContext()).setProduct_id(((ProductDetail) vProductList.elementAt(position)).product_id);
                    ((VMEDS) getApplicationContext()).setObjProductDetail(((ProductDetail) vProductList.elementAt(position)));

                    Intent i = new Intent(SearchActivity.this, com.VMEDS.android.ProductDetailActivity.class);
//                    finish();
                    startActivity(i);
                }
            });

            return itemView;
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void getDatafromSearchText(final String searchString) {
        if (isNetworkConnected()) {
            String url = static_data.BASE_URL + static_data.SEARCH_URL;
            Log.i("URL", url);
            loadingAvi.setVisibility(View.VISIBLE);
            blankData.setVisibility(View.GONE);
            mRequestQueue = Volley.newRequestQueue(SearchActivity.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vProductList = new Vector<ProductDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAvi.setVisibility(View.GONE);
                        loadingAvi.smoothToHide();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("product_data"));
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    ProductDetail obj = new ProductDetail(jsonObject2.getString("product_id"), jsonObject2.getString("p_id"), jsonObject2.getString("product_name"), jsonObject2.getString("selling_price"), jsonObject2.getString("product_price"), jsonObject2.getString("about_product"), jsonObject2.getString("febric"), jsonObject2.getString("shipping_charge"), jsonObject2.getString("work"), jsonObject2.getString("product_quantity"), jsonObject2.getString("category_id"), jsonObject2.getString("product_description"), jsonObject2.getString("product_specification"), jsonObject2.getString("product_status"), jsonObject2.getString("product_image"), jsonObject2.getString("product_color"), "0");

                                    vProductList.add((ProductDetail) obj);
                                }

                                productPagerAdapter = new ProductPagerAdapter(SearchActivity.this, vProductList);
                                // spCountry.setAdapter(null);

                                gridViewProduct.setAdapter(productPagerAdapter);
                                // spCountry.setAdapter(null);
                                gridViewProduct.setVisibility(View.VISIBLE);

                            } else {

                                blankData.setVisibility(View.VISIBLE);
                                gridViewProduct.setAdapter(null);
                                gridViewProduct.setVisibility(View.GONE);
                            }
                        } else {

                            blankData.setVisibility(View.VISIBLE);
                            gridViewProduct.setAdapter(null);
                            gridViewProduct.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        gridViewProduct.setAdapter(null);
                        gridViewProduct.setVisibility(View.GONE);
                        blankData.setVisibility(View.VISIBLE);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loadingAvi.setVisibility(View.GONE);
                    loadingAvi.smoothToHide();
                    gridViewProduct.setAdapter(null);
                    gridViewProduct.setVisibility(View.GONE);
                    blankData.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(PARAM_NAME, searchString);

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
                    getDatafromSearchText(searchString);
                }
            }, 5000);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        // Inflate menu to add items to action bar if it is present.
//        inflater.inflate(R.menu.search, menu);
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                getDatafromSearchText(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        return true;
//    }

}