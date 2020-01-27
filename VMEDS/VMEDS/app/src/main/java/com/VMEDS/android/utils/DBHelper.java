package com.VMEDS.android.utils;

/**
 * Created by Pratik on 3/1/2017.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "neegambazaar.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + Product.TABLE + "("
                + Product.PRODUCT_ID + " TEXT, "
                + Product.P_ID + " TEXT, "
                + Product.PRODUCT_NAME + " TEXT, "
                + Product.PRODUCT_IMAGE + " TEXT, "
                + Product.PRODUCT_STATUS + " TEXT, "
                + Product.PRODUCT_QUANTITY + " TEXT, "
                + Product.PRODUCT_DESCRIPTION + " TEXT, "
                + Product.PRODUCT_SPECIFICATION + " TEXT, "
                + Product.PRODUCT_COLOR + " TEXT, "
                + Product.PRODUCT_PRICE + " REAL, "
                + Product.SELLING_PRICE + " REAL, "
                + Product.DISCOUNT + " INTEGER ,"
                + Product.FEBRIC + " TEXT, "
                + Product.ABOUT_PRODUCT + " TEXT, "
                + Product.CATEGORY_ID + " TEXT, "
                + Product.SHIPPING_CHARGE + " REAL, "
                + Product.WORK + " TEXT  )";

        db.execSQL(CREATE_TABLE_PRODUCT);

        String CREATE_TABLE_ADDTOCART = "CREATE TABLE " + AddToCart.TABLE + "("
                + AddToCart.CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + AddToCart.CART_PRODUCT_ID + " TEXT, "
                + AddToCart.PRODUCT_TITLE + " TEXT, "
                + AddToCart.PRODUCT_IMAGE_URL + " TEXT, "
                + AddToCart.PRODUCT_QUANTITY + " INTEGER, "
                + AddToCart.PRICE + " NUMERIC )";

        db.execSQL(CREATE_TABLE_ADDTOCART);



        String CREATE_TABLE_WISHLIST = "CREATE TABLE " + Wish.TABLE + "("

                + Wish.PRODUCT_ID + " TEXT, "
                + Wish.PRODUCT_TITLE + " TEXT, "
                + Wish.PRODUCT_IMAGE_URL + " TEXT, "
                + Wish.PRODUCT_QUANTITY + " INTEGER, "
                + Wish.PRICE + " NUMERIC )";

        db.execSQL(CREATE_TABLE_WISHLIST);

        String CREATE_TABLE_MYORDER = "CREATE TABLE " + MyOrder.TABLE + "("
                + MyOrder.USER_ID + " TEXT ,"
                + MyOrder.REF_ID + " TEXT, "
                + MyOrder.ORDER_DATE + " TEXT ,"
                + MyOrder.ADDRESS + " TEXT, "
                + MyOrder.OFFER_APPLY + " TEXT ,"
                + MyOrder.OFFER_CODE + " TEXT, "
                + MyOrder.FINAL_TOTLE + " TEXT ,"
                + MyOrder.STATUS + " TEXT, "
                + MyOrder.CART_DATA + " TEXT )";

        db.execSQL(CREATE_TABLE_MYORDER);

        Log.e("Table Created", "Hello");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AddToCart.TABLE);

        // Create tables again
        onCreate(db);

    }

}
