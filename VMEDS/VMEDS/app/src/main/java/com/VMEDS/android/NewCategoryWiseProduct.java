package com.VMEDS.android;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.TwoStatePreference;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.model.CategoryDetail;
import com.VMEDS.android.model.ProductDetail;
import com.VMEDS.android.utils.AddtoCart_List;
import com.VMEDS.android.utils.Global_Typeface;
import com.VMEDS.android.utils.Product_List;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

public class NewCategoryWiseProduct extends AppCompatActivity {
    int currentIndex = 0;
    public Vector<CategoryDetail> vCategorys;
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;
    private AVLoadingIndicatorView loadingAviCategory;
    public Global_Typeface global_typeface;
    private TabLayout tabLayout;
    private Vector<ProductDetail> vAllProducts;
    public Product_List product_list;
    public ViewPager viewPager;
    public TextView ctvTitle, textCount;
    public ImageView civSearch, civAddcart;
    public String category_display_name;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ExpandableListView mExpandableListView;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, mExpandableListView, selectedPos + 1);
    private AddtoCart_List cart_item;
    private Vector<CategoryDetail> vCategoryList;
    int previousGroup = -1;
    private CategoryDetail objCat;
    private int selectedPos = 0;
    private GridView gridViewProduct;
    private Global_Typeface typeface;
    private String category_id;
    private StringRequest mStringRequest1;
    private RequestQueue mRequestQueue1;
    private Vector<ProductDetail> vProductList;
    public ProductPagerAdapter productPagerAdapter;
    public AVLoadingIndicatorView loadingAvi;
    public TextView blankData, txtSortBy;
    public StaticData static_data;
    String id;
    SearchView editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category_wise_product);
        global_typeface = new Global_Typeface(NewCategoryWiseProduct.this);
        typeface = new Global_Typeface(NewCategoryWiseProduct.this);

        cart_item = new AddtoCart_List(NewCategoryWiseProduct.this);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
//        getSupportActionBar().setCustomView(cView);


        //for grtting category id from category name which has been clicked....................
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

//        inputSearch = (EditText) findViewById(R.id.inputSearch);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        textCount = (TextView) findViewById(R.id.textCount);
        civAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VMEDS) getApplicationContext()).setFromWishList(0);
                Intent i = new Intent(NewCategoryWiseProduct.this, MyOrdersActivity.class);
                finish();
                startActivity(i);
            }
        });
        civSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewCategoryWiseProduct.this, SearchActivity.class);
                finish();
                startActivity(i);
            }
        });
//        civSearch.setVisibility(View.GONE);
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
//                productPagerAdapter.filter(text);
                return false;
            }
        });

        Vector<AddtoCartDetail> vCartList = cart_item.getCartList();
        textCount.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        if (vCartList.size() > 0) {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(String.valueOf(vCartList.size()));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dblue));
        }
        //  ((VMEDS) getApplicationContext()).setObjSubCat();
        vCategoryList = ((VMEDS) getApplicationContext()).getvCategoryList();
        objCat = ((VMEDS) getApplicationContext()).getObjSubCat();

        static_data = new StaticData();
        loadingAviCategory = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);

        // getSubCategoryList();
        if (objCat != null) {
//        category_id = "25";
            vCategorys = objCat.vSubCat;
            category_display_name = objCat.category_name;
            category_id = objCat.category_id;
        }

        getAllCategoryProductData(category_id);

        //getSubCategoryList(category_name);

        ctvTitle.setText(category_display_name);

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

//            vCategory = objCat.vSubCat;
//            for (int j = 0; j < vCategory.size(); j++) {
//                Log.e("Hello Cat", ((CategoryDetail) vCategory.elementAt(j)).sub_category_id + " ");
//            }
//            loadingAviCategory.setVisibility(View.GONE);
//            loadingAviCategory.smoothToHide();
//            getAllCategoryProductData(objCat.category_name);
        //setuptabs();
//            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//            for (int i = 0; i < vCategory.size(); i++) {
//                tabLayout.addTab(tabLayout.newTab().setText(((CategoryDetail) vCategory.elementAt(i)).sub_category_name));
//            }
//
//
//            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//            final PagerAdapter adapter = new PagerAdapter
//                    (getSupportFragmentManager(), tabLayout.getTabCount());
//            viewPager.setAdapter(adapter);
//            viewPager.setOffscreenPageLimit(3);
//            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//            if (((VMEDS) getApplicationContext()).getFromHome() == 0)
//                currentIndex = ((VMEDS) getApplicationContext()).getCurrentIndex();
//            else
//                currentIndex = 0;
//            viewPager.setCurrentItem(currentIndex);
//            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(TabLayout.Tab tab) {
//                    viewPager.setCurrentItem(tab.getPosition());
//                }
//
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//
//                }
//
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//
//                }
//            });
//            changeTabsFont();
        // }
        ViewTreeObserver vto = mExpandableListView.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mExpandableListView.setIndicatorBounds(mExpandableListView.getRight() - 100, mExpandableListView.getWidth());
                // mExpandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.arrow_bk));
            }
        });
//        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
//        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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

    private void prepareListData() {
        vCategoryList = ((VMEDS) getApplicationContext()).getvCategoryList();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding data header
        listDataHeader.add("Home");

        for (int i = 0; i < vCategoryList.size(); i++) {
            listDataHeader.add(((CategoryDetail) vCategoryList.elementAt(i)).category_name);
            if (category_display_name.equals(((CategoryDetail) vCategoryList.elementAt(i)).category_name))
                selectedPos = i;
            List<String> subCatList = new ArrayList<String>();
            Vector<CategoryDetail> vSubCatList = ((CategoryDetail) vCategoryList.elementAt(i)).vSubCat;
            for (int j = 0; j < vSubCatList.size(); j++) {
                subCatList.add(((CategoryDetail) vSubCatList.elementAt(j)).sub_category_name);
            }
            listDataChild.put(listDataHeader.get(i + 1), subCatList);
        }

        mExpandableListView.setAdapter(listAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    mExpandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;

                if (groupPosition == 0) {
                    Intent intent = new Intent(NewCategoryWiseProduct.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ((VMEDS) getApplicationContext()).setFromHome(0);
                if (groupPosition == 0) {
                    Intent intent = new Intent( NewCategoryWiseProduct.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    if (selectedPos != (groupPosition - 1)) {
                        ((VMEDS) getApplicationContext()).setObjSubCat(((CategoryDetail) vCategoryList.elementAt(groupPosition - 3)));
                        ((VMEDS) getApplicationContext()).setCurrentIndex(childPosition);

                        Intent intent = new Intent(NewCategoryWiseProduct.this, NewCategoryWiseProduct.class);
                        startActivity(intent);
                    }
                    viewPager.setCurrentItem(childPosition);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        mExpandableListView.expandGroup(selectedPos + 1);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {
                    Intent intent = new Intent(NewCategoryWiseProduct.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    selectedPos = groupPosition - 1;
                    ((VMEDS) getApplicationContext()).setObjSubCat(((CategoryDetail) vCategoryList.elementAt(groupPosition - 1)));
                    objCat = ((CategoryDetail) vCategoryList.elementAt(groupPosition - 1));
                    if (objCat != null) {
//        category_id = "25";
                        vCategorys = objCat.vSubCat;
                        category_display_name = objCat.category_name;
                        category_id = objCat.category_id;
                        ctvTitle.setText(category_display_name);
                    }

                    getAllCategoryProductData(category_id);
                }


                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void getAllCategoryProductData(final String cat_name) {
        if (isNetworkConnected()) {
            String url = "";
            if (((VMEDS) getApplicationContext()).getFromWholeSaler() == 0)
                url = static_data.BASE_URL + static_data.CATEGORY_URL;


            Log.i("URL", url + " " + cat_name);
            mRequestQueue = Volley.newRequestQueue(NewCategoryWiseProduct.this);
            mStringRequest = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Res", response);
                        vAllProducts = new Vector<ProductDetail>();
                        JSONObject jsonObject1 = new JSONObject(response);
                        loadingAviCategory.setVisibility(View.GONE);
                        loadingAviCategory.smoothToHide();
                        product_list = new Product_List(NewCategoryWiseProduct.this);
                        product_list.delete();
                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("Category_data"));
                            // txtSortBy.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String s = jsonObject2.getString("category_id");

                                //for finding actually category id....
                                if (id.equals(s)) {

                                    JSONArray jsonArray1 = new JSONArray(jsonObject2.getString("sub_category"));
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                        ProductDetail obj = new ProductDetail(jsonObject3.getString("sub_category_id"), jsonObject3.getString("sub_category_name"));

                                        vAllProducts.add((ProductDetail) obj);
                                    }
                                }

//                                String discount = jsonObject2.getString("discount");
//                                if (discount == null)
//                                    discount = "0";
//                                else if (discount.length() == 0)
//                                    discount = "0";

//                                ProductDetail obj = new ProductDetail(jsonObject2.getString("product_id"), jsonObject2.getString("p_id"), jsonObject2.getString("product_name"), jsonObject2.getString("selling_price"), jsonObject2.getString("product_price"), jsonObject2.getString("about_product"), jsonObject2.getString("febric"), jsonObject2.getString("shipping_charge"), jsonObject2.getString("work"), jsonObject2.getString("product_quantity"), jsonObject2.getString("category_id"), jsonObject2.getString("product_description"), jsonObject2.getString("product_specification"), jsonObject2.getString("product_status"), jsonObject2.getString("product_image"), jsonObject2.getString("product_color"), "0");
//
//                                vAllProducts.add((ProductDetail) obj);
                            }
                            product_list.delete();
                            product_list.insert(vAllProducts);


                            gridViewProduct = ((GridView) findViewById(R.id.gridViewProduct));
                            loadingAvi = (AVLoadingIndicatorView) findViewById(R.id.loadingAvi);

                            blankData = (TextView) findViewById(R.id.blankData);
                            blankData.setTypeface(typeface.TypeFace_Roboto_Bold());

                            txtSortBy = (TextView) findViewById(R.id.txtSortBy);
                            txtSortBy.setTypeface(typeface.TypeFace_Roboto_Bold());


                            Vector<ProductDetail> vProducts = product_list.getProductList();
                            if (vProducts == null || vProducts.size() == 0) {
                                blankData.setVisibility(View.VISIBLE);
                                gridViewProduct.setVisibility(View.GONE);
                                txtSortBy.setVisibility(View.GONE);
                                loadingAvi.setVisibility(View.GONE);
                                loadingAvi.smoothToHide();
                            } else {
                                blankData.setVisibility(View.GONE);
                                txtSortBy.setVisibility(View.VISIBLE);
                                gridViewProduct.setVisibility(View.VISIBLE);
                                productPagerAdapter = new ProductPagerAdapter(NewCategoryWiseProduct.this, vAllProducts);
                                // spCountry.setAdapter(null);

                                gridViewProduct.setAdapter(productPagerAdapter);
                                loadingAvi.setVisibility(View.GONE);
                                loadingAvi.smoothToHide();
                            }

                            txtSortBy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LayoutInflater li = LayoutInflater.from(NewCategoryWiseProduct.this);
                                    //Creating a view to get the dialog box
                                    View sortByDialog = li.inflate(R.layout.layout_sortby, null);

                                    final DialogPlus alertDialog = DialogPlus.newDialog(NewCategoryWiseProduct.this)
                                            .setContentHolder(new ViewHolder(sortByDialog))

                                            .setBackgroundColorResId(Color.TRANSPARENT)
                                            .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                                            .create();

                                    sortByDialog.setBackgroundResource(R.drawable.layout_rounded);

                                    if (Build.VERSION.SDK_INT >= 21)
                                        sortByDialog.setClipToOutline(true);
                                    alertDialog.show();

                                    RadioGroup rgSortby = (RadioGroup) sortByDialog.findViewById(R.id.rgSortby);
                                    RadioButton rbPriceltoh = (RadioButton) sortByDialog.findViewById(R.id.rbPriceltoh);
                                    RadioButton rbPricehtol = (RadioButton) sortByDialog.findViewById(R.id.rbPricehtol);

                                    RadioButton rbOldest = (RadioButton) sortByDialog.findViewById(R.id.rbOldest);

                                    RadioButton rbNewest = (RadioButton) sortByDialog.findViewById(R.id.rbNewest);

                                    RadioButton rbMostViewed = (RadioButton) sortByDialog.findViewById(R.id.rbMostViewed);

                                    rbPriceltoh.setTypeface(typeface.TypeFace_Roboto_Regular());
                                    rbMostViewed.setTypeface(typeface.TypeFace_Roboto_Regular());

                                    rbPricehtol.setTypeface(typeface.TypeFace_Roboto_Regular());

                                    rbOldest.setTypeface(typeface.TypeFace_Roboto_Regular());
                                    rbNewest.setTypeface(typeface.TypeFace_Roboto_Regular());

                                    rbMostViewed.setTypeface(typeface.TypeFace_Roboto_Regular());

                                    //Initizliaing confirm button fo dialog box and edittext of dialog box

                                    //Creating an alertdialog builder
                                    //AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                                    //Adding our dialog box to the view of alert dialog
                                    //  alert.setView(sortByDialog);

                                    //Creating an alert dialog
                                    //final AlertDialog alertDialog = alert.create();

                                    //Displaying the alert dialog
                                    //alertDialog.show();
                                    // alertDialog.setCancelable(true);

                                    rbPriceltoh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            vProductList = product_list.getProductListByPriceASC();
                                            gridViewProduct.setAdapter(null);
                                            productPagerAdapter = new ProductPagerAdapter(NewCategoryWiseProduct.this, vProductList);
                                            // spCountry.setAdapter(null);

                                            gridViewProduct.setAdapter(productPagerAdapter);
                                            alertDialog.dismiss();
                                        }
                                    });

                                    rbPricehtol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            vProductList = product_list.getProductListByPriceDESC();
                                            gridViewProduct.setAdapter(null);
                                            productPagerAdapter = new ProductPagerAdapter(NewCategoryWiseProduct.this, vProductList);
                                            // spCountry.setAdapter(null);

                                            gridViewProduct.setAdapter(productPagerAdapter);
                                            alertDialog.dismiss();
                                        }
                                    });
                   /* rbOldest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            vProductList = product_list.getProductListByOldest(category_id);
                            gridViewProduct.setAdapter(null);
                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
                            // spCountry.setAdapter(null);

                            gridViewProduct.setAdapter(productPagerAdapter);
                            alertDialog.dismiss();
                        }
                    });
                    rbNewest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            vProductList = product_list.getProductListByNewest(category_id);
                            gridViewProduct.setAdapter(null);
                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
                            // spCountry.setAdapter(null);

                            gridViewProduct.setAdapter(productPagerAdapter);
                            alertDialog.dismiss();
                        }
                    });
                    rbMostViewed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            vProductList = product_list.getProductListByMostviewed(category_id);
                            gridViewProduct.setAdapter(null);
                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
                            // spCountry.setAdapter(null);

                            gridViewProduct.setAdapter(productPagerAdapter);
                            alertDialog.dismiss();
                        }
                    });*/


                                }
                            });


                        } else {

                            //blankData.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        //blankData.setVisibility(View.VISIBLE);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loadingAviCategory.setVisibility(View.GONE);
                    loadingAviCategory.smoothToHide();
                    //blankData.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
                }
            }) {

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("category_id", category_id);

                    Log.e("Params", params.toString() + " ");

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
                    getAllCategoryProductData(category_id);
                }
            }, 5000);
        }
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
        int discount;
        float discount_price;
        private Optional filter;

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
//            try {
//
////                    JSONObject mainObject = new JSONObject(((ProductDetail) vProductList.elementAt(position)).images);
////                    Log.e("full", " " + ((ProductDetail) vProductList.elementAt(position)).images);
////                    Log.e("STr", mainObject.getString("1") + " ");
//                //
////                String imageStr = ((ProductDetail) vProductList.elementAt(position)).product_image;
////                Log.e("Log STr", imageStr + "");
////                if (!imageStr.contains("http://"))
////                    imageStr = "http://" + imageStr;
////                Glide.with(NewCategoryWiseProduct.this).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
////                    @Override
////                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
////                        Log.e("Done", "Done0");
////                        loadingAvi.setVisibility(View.GONE);
////                        loadingAvi.smoothToHide();
////                        return false;
////                    }
////
////                    @Override
////                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
////                        Log.e("Done", "Done1");
////                        loadingAvi.setVisibility(View.GONE);
////                        loadingAvi.smoothToHide();
////                        return false;
////                    }
////                }).into(product_image);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


            TextView product_name = (TextView) itemView.findViewById(R.id.product_name);
            product_name.setText(((ProductDetail) vProductList.elementAt(position)).sub_category_name);
            product_name.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
//            TextView product_rs = (TextView) itemView.findViewById(R.id.product_rs);
//            TextView product_sale_rs = (TextView) itemView.findViewById(R.id.product_sale_rs);
//            TextView product_discount = (TextView) itemView.findViewById(R.id.product_discount);
//            product_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//            product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Regular());
//            product_discount.setTypeface(typeface.TypeFace_Roboto_Bold());

//            if (((ProductDetail) vProductList.elementAt(position)).discount == null) {
//                product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
//                product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).product_price);
//                product_rs.setPaintFlags(product_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                discount= Integer.parseInt(vProductList.elementAt(position).selling_price)-Integer.parseInt(vProductList.elementAt(position).product_price);
//                product_discount.setText("Discount " + discount + "%");
//
//                product_rs.setVisibility(View.VISIBLE);
//
//                if (product_rs.getText().toString().equals(product_sale_rs.getText().toString())) {
//                    product_discount.setVisibility(View.GONE);
//                } else product_discount.setVisibility(View.VISIBLE);
//
//                product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//                product_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//            } else {
//                if (((ProductDetail) vProductList.elementAt(position)).discount.length() == 0) {
//                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
//                    product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).product_price);
//                    product_rs.setPaintFlags(product_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//
//                    product_discount.setText("Discount " + discount + "%");
//
//
//                    product_rs.setVisibility(View.VISIBLE);
//                    product_discount.setVisibility(View.VISIBLE);
//
//                    if (product_rs.getText().toString().equals(product_sale_rs.getText().toString())) {
//                        product_discount.setVisibility(View.GONE);
//                    } else product_discount.setVisibility(View.VISIBLE);
//
//                    product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//                    product_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//
//                } else if (Integer.parseInt(((ProductDetail) vProductList.elementAt(position)).discount) == 0) {
//                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
//                    product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).product_price);
//                    product_rs.setPaintFlags(product_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                    product_discount.setText("Discount " + discount + "%");
//
//                    product_rs.setVisibility(View.VISIBLE);
//                    product_discount.setVisibility(View.VISIBLE);
//
//                    if (product_rs.getText().toString().equals(product_sale_rs.getText().toString())) {
//                        product_discount.setVisibility(View.GONE);
//                    } else product_discount.setVisibility(View.VISIBLE);
//
//                    product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//                    product_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//                } else {
            // product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).purchase_price);




         /*
            product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
            // product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).product_price);
            product_rs.setPaintFlags(product_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            int prodt_price = Integer.parseInt(((ProductDetail) vProductList.elementAt(position)).product_price);
            int sale_price = Integer.parseInt(((ProductDetail) vProductList.elementAt(position)).selling_price);

            discount_price = sale_price * 100 / prodt_price;
            int discount_price_final = 100 - (int) discount_price;


            // float sale_price = Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).sale_price) - Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).sale_price);
            // product_rs.setText(getString(R.strinbg.Rs) + " " + (sale_price - discount_price));
            product_discount.setText(discount_price_final + "% " + "\noff");

            if (product_rs.getText().toString().equals(product_sale_rs.getText().toString())) {
                product_discount.setVisibility(View.GONE);
            } else product_discount.setVisibility(View.VISIBLE);
*/
//                }
//            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((VMEDS) getApplicationContext()).setProduct_id(((ProductDetail) vProductList.elementAt(position)).sub_category_id);
                    ((VMEDS) getApplicationContext()).setObjProductDetail(((ProductDetail) vProductList.elementAt(position)));

                    Intent i = new Intent(NewCategoryWiseProduct.this, com.VMEDS.android.ProductDetailActivity.class);
//                    finish();
                    startActivity(i);
                }
            });

            return itemView;
        }

//        // Filter Class
//        public void filter(String charText) {
//            charText = charText.toLowerCase(Locale.getDefault());
//            animalNamesList.clear();
//            if (charText.length() == 0) {
//                animalNamesList.addAll(arraylist);
//            } else {
//                for (AnimalNames wp : arraylist) {
//                    if (wp.getAnimalName().toLowerCase(Locale.getDefault()).contains(charText)) {
//                        animalNamesList.add(wp);
//                    }
//                }
//            }
//            notifyDataSetChanged();
//        }

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

//    public class CategoryFragment extends Fragment {
//
//
//        private GridView gridViewProduct;
//        private Global_Typeface typeface;
//        private String category_id;
//        private StringRequest mStringRequest1;
//        private RequestQueue mRequestQueue1;
//        private Vector<ProductDetail> vProductList;
//        public ProductPagerAdapter productPagerAdapter;
//        public AVLoadingIndicatorView loadingAvi;
//        public TextView blankData, txtSortBy;
//        public StaticData static_data;
//
//        public CategoryFragment() {
//
//        }
//
//        public CategoryFragment(String category_id) {
//            this.category_id = category_id;
//            typeface = new Global_Typeface(getActivity());
//            static_data = new StaticData();
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View v = inflater.inflate(R.layout.fragment_category, container, false);
//            gridViewProduct = ((GridView) v.findViewById(R.id.gridViewProduct));
//            loadingAvi = (AVLoadingIndicatorView) v.findViewById(R.id.loadingAvi);
//
//            blankData = (TextView) v.findViewById(R.id.blankData);
//            blankData.setTypeface(typeface.TypeFace_Roboto_Bold());
//
//            txtSortBy = (TextView) v.findViewById(R.id.txtSortBy);
//            txtSortBy.setTypeface(typeface.TypeFace_Roboto_Bold());
////            gridViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                @Override
////                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                    HashMap<String, String> persons = personList.get(position);
////
////                    Toast.makeText(getContext().getApplicationContext(), "Item Id:" + persons.get(TAG_ID) + " Name:" + persons.get(TAG_NAME), Toast.LENGTH_LONG).show();
////                }
////            });
//
//            // getWomanFashionCategoryProductData(category_name);
//            final Product_List product_list = new Product_List(getActivity());
//
//            Vector<ProductDetail> vProducts = product_list.getProductListByCategory(category_id);
//            if (vProducts == null || vProducts.size() == 0) {
//                blankData.setVisibility(View.VISIBLE);
//                gridViewProduct.setVisibility(View.GONE);
//                txtSortBy.setVisibility(View.GONE);
//                loadingAvi.setVisibility(View.GONE);
//                loadingAvi.smoothToHide();
//            } else {
//                blankData.setVisibility(View.GONE);
//                txtSortBy.setVisibility(View.VISIBLE);
//                gridViewProduct.setVisibility(View.VISIBLE);
//                productPagerAdapter = new ProductPagerAdapter(getActivity(), vProducts);
//                // spCountry.setAdapter(null);
//
//                gridViewProduct.setAdapter(productPagerAdapter);
//                loadingAvi.setVisibility(View.GONE);
//                loadingAvi.smoothToHide();
//            }
//
//            txtSortBy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LayoutInflater li = LayoutInflater.from(getActivity());
//                    //Creating a view to get the dialog box
//                    View sortByDialog = li.inflate(R.layout.layout_sortby, null);
//
//                    final DialogPlus alertDialog = DialogPlus.newDialog(NewCategoryWiseProduct.this)
//                            .setContentHolder(new ViewHolder(sortByDialog))
//
//                            .setBackgroundColorResId(Color.TRANSPARENT)
//                            .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
//                            .create();
//
//                    sortByDialog.setBackgroundResource(R.drawable.layout_rounded);
//
//                    if (Build.VERSION.SDK_INT >= 21)
//                        sortByDialog.setClipToOutline(true);
//                    alertDialog.show();
//
//                    RadioGroup rgSortby = (RadioGroup) sortByDialog.findViewById(R.id.rgSortby);
//                    RadioButton rbPriceltoh = (RadioButton) sortByDialog.findViewById(R.id.rbPriceltoh);
//                    RadioButton rbPricehtol = (RadioButton) sortByDialog.findViewById(R.id.rbPricehtol);
//
//                    RadioButton rbOldest = (RadioButton) sortByDialog.findViewById(R.id.rbOldest);
//
//                    RadioButton rbNewest = (RadioButton) sortByDialog.findViewById(R.id.rbNewest);
//
//                    RadioButton rbMostViewed = (RadioButton) sortByDialog.findViewById(R.id.rbMostViewed);
//
//                    rbPriceltoh.setTypeface(typeface.TypeFace_Roboto_Regular());
//                    rbMostViewed.setTypeface(typeface.TypeFace_Roboto_Regular());
//
//                    rbPricehtol.setTypeface(typeface.TypeFace_Roboto_Regular());
//
//                    rbOldest.setTypeface(typeface.TypeFace_Roboto_Regular());
//                    rbNewest.setTypeface(typeface.TypeFace_Roboto_Regular());
//
//                    rbMostViewed.setTypeface(typeface.TypeFace_Roboto_Regular());
//
//                    //Initizliaing confirm button fo dialog box and edittext of dialog box
//
//                    //Creating an alertdialog builder
//                    //AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//
//                    //Adding our dialog box to the view of alert dialog
//                    //  alert.setView(sortByDialog);
//
//                    //Creating an alert dialog
//                    //final AlertDialog alertDialog = alert.create();
//
//                    //Displaying the alert dialog
//                    //alertDialog.show();
//                    // alertDialog.setCancelable(true);
//
//                  /*  rbPriceltoh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            vProductList = product_list.getProductListByPriceASC(category_id);
//                            gridViewProduct.setAdapter(null);
//                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
//                            // spCountry.setAdapter(null);
//
//                            gridViewProduct.setAdapter(productPagerAdapter);
//                            alertDialog.dismiss();
//                        }
//                    });
//
//                    rbPricehtol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            vProductList = product_list.getProductListByPriceDESC(category_id);
//                            gridViewProduct.setAdapter(null);
//                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
//                            // spCountry.setAdapter(null);
//
//                            gridViewProduct.setAdapter(productPagerAdapter);
//                            alertDialog.dismiss();
//                        }
//                    });
//                    rbOldest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            vProductList = product_list.getProductListByOldest(category_id);
//                            gridViewProduct.setAdapter(null);
//                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
//                            // spCountry.setAdapter(null);
//
//                            gridViewProduct.setAdapter(productPagerAdapter);
//                            alertDialog.dismiss();
//                        }
//                    });
//                    rbNewest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            vProductList = product_list.getProductListByNewest(category_id);
//                            gridViewProduct.setAdapter(null);
//                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
//                            // spCountry.setAdapter(null);
//
//                            gridViewProduct.setAdapter(productPagerAdapter);
//                            alertDialog.dismiss();
//                        }
//                    });
//                    rbMostViewed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            vProductList = product_list.getProductListByMostviewed(category_id);
//                            gridViewProduct.setAdapter(null);
//                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
//                            // spCountry.setAdapter(null);
//
//                            gridViewProduct.setAdapter(productPagerAdapter);
//                            alertDialog.dismiss();
//                        }
//                    });*/
//
//
//                }
//            });
//
//
//            return v;
//        }
//
//        private void getWomanFashionCategoryProductData(final String category) {
//
//            String url = static_data.BASE_URL + static_data.CATEGORY_DATA_URL;
//            Log.i("URL", url);
//            mRequestQueue1 = Volley.newRequestQueue(getActivity());
//            mStringRequest1 = new StringRequest(Request.Method.POST, url.replace(" ", "%20"), new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        Log.e("Res", response);
//                        vProductList = new Vector<ProductDetail>();
//                        JSONObject jsonObject1 = new JSONObject(response);
//                        loadingAvi.setVisibility(View.GONE);
//                        loadingAvi.smoothToHide();
//                        final Product_List product_list = new Product_List(getActivity());
//                        if (jsonObject1.getString("status").equalsIgnoreCase("200")) {
//                            // Toast.makeText(MyProfile_Activity.this, "Validation success.", Toast.LENGTH_LONG).show();
//                            JSONArray jsonArray = new JSONArray(jsonObject1.getString("product_data"));
//                            txtSortBy.setVisibility(View.VISIBLE);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                                String discount = jsonObject2.getString("discount");
//                                if (discount == null)
//                                    discount = "0";
//                                else if (discount.length() == 0)
//                                    discount = "0";
//
//                                ProductDetail obj = new ProductDetail(jsonObject2.getString("product_id"), jsonObject2.getString("p_id"), jsonObject2.getString("product_name"), jsonObject2.getString("selling_price"), jsonObject2.getString("product_price"), jsonObject2.getString("about_product"), jsonObject2.getString("febric"), jsonObject2.getString("shipping_charge"), jsonObject2.getString("work"), jsonObject2.getString("product_quantity"), jsonObject2.getString("category_id"), jsonObject2.getString("product_description"), jsonObject2.getString("product_specification"), jsonObject2.getString("product_status"), jsonObject2.getString("product_image"), jsonObject2.getString("product_color"), "0");
//
//                                vProductList.add((ProductDetail) obj);
//                            }
//                            product_list.insert(vProductList);
//                            productPagerAdapter = new ProductPagerAdapter(getActivity(), vProductList);
//                            // spCountry.setAdapter(null);
//
//                            gridViewProduct.setAdapter(productPagerAdapter);
//                            // spCountry.setAdapter(null);
//
//                        } else {
//
//                            blankData.setVisibility(View.VISIBLE);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                        blankData.setVisibility(View.VISIBLE);
//                    }
//                }
//
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//                    loadingAvi.setVisibility(View.GONE);
//                    loadingAvi.smoothToHide();
//                    blankData.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Problem", Toast.LENGTH_LONG).show();
//                }
//            }) {
//
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("category", category);
//
//                    return params;
//                }
//            };
//            mStringRequest1.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            mRequestQueue1.add(mStringRequest1);
//        }
//
//        class ProductPagerAdapter extends BaseAdapter {
//
//            Context mContext;
//            LayoutInflater mLayoutInflater;
//            Vector<ProductDetail> vProductList;
//            AVLoadingIndicatorView loadingAvi;
//            ImageView product_image;
//            LinearLayout layoutProduct;
//            Global_Typeface globalTypeface;
//
//            public ProductPagerAdapter(Context context, Vector<ProductDetail> vProductList) {
//                mContext = context;
//                this.vProductList = vProductList;
//                globalTypeface = new Global_Typeface(context);
//                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            }
//
//
//            public int getCount() {
//                return vProductList.size();
//            }
//
//            public Object getItem(int position) {
//                return vProductList.get(position);
//            }
//
//            public long getItemId(int position) {
//                return position;
//            }
//
//            public View getView(final int position, View itemView, ViewGroup parent) {
//                itemView = mLayoutInflater.inflate(R.layout.slider_item, null);
//
//                product_image = (ImageView) itemView.findViewById(R.id.product_image);
//                layoutProduct = (LinearLayout) itemView.findViewById(R.id.layoutProduct);
//                layoutProduct.setBackgroundResource(R.drawable.product_bk);
//                loadingAvi = (AVLoadingIndicatorView) itemView.findViewById(R.id.loadingAvi);
//                loadingAvi.setVisibility(View.VISIBLE);
//                try {
//
////                    JSONObject mainObject = new JSONObject(((ProductDetail) vProductList.elementAt(position)).images);
////                    Log.e("full", " " + ((ProductDetail) vProductList.elementAt(position)).images);
////                    Log.e("STr", mainObject.getString("1") + " ");
//                    //
//                    String imageStr = ((ProductDetail) vProductList.elementAt(position)).product_image;
//                    Log.e("Log STr", imageStr + "");
//                    if (!imageStr.contains("http://"))
//                        imageStr = "http://" + imageStr;
//                    Glide.with(getActivity()).load(imageStr).listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                            Log.e("Done", "Done0");
//                            loadingAvi.setVisibility(View.GONE);
//                            loadingAvi.smoothToHide();
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            Log.e("Done", "Done1");
//                            loadingAvi.setVisibility(View.GONE);
//                            loadingAvi.smoothToHide();
//                            return false;
//                        }
//                    }).into(product_image);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                TextView product_name = (TextView) itemView.findViewById(R.id.product_name);
//                product_name.setText(((ProductDetail) vProductList.elementAt(position)).product_name);
//                product_name.setTypeface(globalTypeface.TypeFace_Roboto_Regular());
//                TextView product_rs = (TextView) itemView.findViewById(R.id.product_rs);
//                TextView product_sale_rs = (TextView) itemView.findViewById(R.id.product_sale_rs);
//                TextView product_discount = (TextView) itemView.findViewById(R.id.product_discount);
//                product_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//                product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Regular());
//                product_discount.setTypeface(typeface.TypeFace_Roboto_Bold());
//                if (((ProductDetail) vProductList.elementAt(position)).discount == null) {
//                    product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
//                    product_rs.setVisibility(View.GONE);
//                    product_discount.setVisibility(View.GONE);
//                    product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//                } else {
//                    if (((ProductDetail) vProductList.elementAt(position)).discount.length() == 0) {
//                        product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
//                        product_rs.setVisibility(View.GONE);
//                        product_discount.setVisibility(View.GONE);
//                        product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//
//                    } else if (Integer.parseInt(((ProductDetail) vProductList.elementAt(position)).discount) == 0) {
//                        product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
//                        product_rs.setVisibility(View.GONE);
//                        product_discount.setVisibility(View.GONE);
//                        product_sale_rs.setTypeface(typeface.TypeFace_Roboto_Bold());
//                    } else {
//                        // product_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).purchase_price);
//                        product_sale_rs.setText(getString(R.string.Rs) + " " + ((ProductDetail) vProductList.elementAt(position)).selling_price);
//                        product_sale_rs.setPaintFlags(product_sale_rs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                        int discount = Integer.parseInt(((ProductDetail) vProductList.elementAt(position)).discount);
//                        float sale_price = Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).selling_price);
//                        float discount_price = ((int) sale_price * discount) / 100;
//                        // float sale_price = Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).sale_price) - Float.parseFloat(((ProductDetail) vProductList.elementAt(position)).sale_price);
//                        product_rs.setText(getString(R.string.Rs) + " " + (sale_price - discount_price));
//                        product_discount.setText("Discount " + ((ProductDetail) vProductList.elementAt(position)).discount + "%");
//                    }
//                }
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((VMEDS) getActivity().getApplicationContext()).setProduct_id(((ProductDetail) vProductList.elementAt(position)).product_id);
//                        Intent i = new Intent(getActivity(), com.neegambazaar.android.ProductDetailActivity.class);
//                        getActivity().finish();
//                        startActivity(i);
//                    }
//                });
//
//                return itemView;
//            }
//
//        }
//    }*/


}
