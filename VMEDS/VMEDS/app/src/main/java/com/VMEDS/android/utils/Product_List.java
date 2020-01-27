package com.VMEDS.android.utils;

/**
 * Created by Pratik on 3/1/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.VMEDS.android.model.ProductDetail;

import java.util.Vector;


public class Product_List {


    private DBHelper dbHelper;

    public Product_List(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert(Vector<ProductDetail> vProducts) {
        delete();
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        for (int i = 0; i < vProducts.size(); i++) {
            ProductDetail product = ((ProductDetail) vProducts.elementAt(i));

            //Open connection to write data
            ContentValues values = new ContentValues();
            values.put(Product.PRODUCT_ID, product.product_id);
            values.put(Product.P_ID, product.p_id);
            values.put(Product.PRODUCT_NAME, product.product_name);
            values.put(Product.PRODUCT_STATUS, product.product_status);
            values.put(Product.PRODUCT_IMAGE, product.product_image);
            values.put(Product.DISCOUNT, product.discount);
            values.put(Product.PRODUCT_COLOR, product.product_color);
            values.put(Product.SELLING_PRICE, product.selling_price);
            values.put(Product.PRODUCT_PRICE, product.product_price);
            values.put(Product.CATEGORY_ID, product.category_id);
            values.put(Product.ABOUT_PRODUCT, product.about_product);
            values.put(Product.FEBRIC, product.febric);
            values.put(Product.SHIPPING_CHARGE, product.shipping_charge);
            values.put(Product.WORK, product.work);
            values.put(Product.PRODUCT_QUANTITY, product.product_quantity);
            values.put(Product.PRODUCT_DESCRIPTION, product.product_description);
            values.put(Product.PRODUCT_SPECIFICATION, product.product_specification);


            // Inserting Row
            long product_Id = db.insert(Product.TABLE, null, values);
            // Closing database connection
        }
        db.close();
    }

    public void delete(int product_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Product.TABLE, Product.PRODUCT_ID + "= ?", new String[]{String.valueOf(product_Id)});


        db.close(); // Closing database connection
    }

    public void delete() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.execSQL("delete from " + Product.TABLE);
        db.close(); // Closing database connection
    }
//        public void update(Product product) {
//
//            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            ContentValues values = new ContentValues();
//
//            values.put(Student.KEY_age, student.age);
//            values.put(Student.KEY_email, student.email);
//            values.put(Student.KEY_name, student.name);
//
//            // It's a good practice to use parameter ?, instead of concatenate string
//            db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[]{String.valueOf(student.student_ID)});
//            db.close(); // Closing database connection
//        }

   public Vector<ProductDetail> getProductListByPriceASC() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.P_ID + "," +
                Product.CATEGORY_ID + "," +
                Product.PRODUCT_NAME + "," +
                Product.PRODUCT_IMAGE + "," +
                Product.PRODUCT_COLOR + "," +
                Product.PRODUCT_STATUS + "," +
                Product.SELLING_PRICE + "," +
                Product.DISCOUNT + "," +
                Product.PRODUCT_PRICE + "," +
                Product.ABOUT_PRODUCT + "," +
                Product.PRODUCT_SPECIFICATION +","+
                Product.WORK + "," +
                Product.FEBRIC + "," +
                Product.SHIPPING_CHARGE +","+
                Product.PRODUCT_QUANTITY +","+
                Product.PRODUCT_DESCRIPTION + "," +
                "(" + Product.SELLING_PRICE + "-((" + Product.SELLING_PRICE + "*" + Product.DISCOUNT + ")/100)) as cal_price" +
                " FROM " + Product.TABLE +

                " ORDER BY cal_price";

        Log.e("Asc", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
                ProductDetail obj = new ProductDetail(
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.P_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(Product.SELLING_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.ABOUT_PRODUCT)),
                        cursor.getString(cursor.getColumnIndex(Product.FEBRIC)),
                        cursor.getString(cursor.getColumnIndex(Product.SHIPPING_CHARGE)),
                        cursor.getString(cursor.getColumnIndex(Product.WORK)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(Product.CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_SPECIFICATION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_STATUS)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_COLOR)),
                        cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)));
                vProducts.add((ProductDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }

    public Vector<ProductDetail> getProductListByPriceDESC() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.P_ID + "," +
                Product.CATEGORY_ID + "," +
                Product.PRODUCT_NAME + "," +
                Product.PRODUCT_IMAGE + "," +
                Product.PRODUCT_COLOR + "," +
                Product.PRODUCT_STATUS + "," +
                Product.SELLING_PRICE + "," +
                Product.DISCOUNT + "," +
                Product.PRODUCT_PRICE + "," +
                Product.ABOUT_PRODUCT + "," +
                Product.PRODUCT_SPECIFICATION +","+
                Product.WORK + "," +
                Product.FEBRIC + "," +
                Product.SHIPPING_CHARGE +","+
                Product.PRODUCT_QUANTITY +","+
                Product.PRODUCT_DESCRIPTION + "," +
                "(" + Product.SELLING_PRICE + "-((" + Product.SELLING_PRICE + "*" + Product.DISCOUNT + ")/100)) as cal_price" +
                " FROM " + Product.TABLE +
                              " ORDER BY cal_price DESC";

        Log.e("Desc", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
                ProductDetail obj = new ProductDetail(
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.P_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(Product.SELLING_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.ABOUT_PRODUCT)),
                        cursor.getString(cursor.getColumnIndex(Product.FEBRIC)),
                        cursor.getString(cursor.getColumnIndex(Product.SHIPPING_CHARGE)),
                        cursor.getString(cursor.getColumnIndex(Product.WORK)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(Product.CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_SPECIFICATION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_STATUS)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_COLOR)),
                        cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)));
                vProducts.add((ProductDetail) obj);


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }


   /* public Vector<ProductDetail> getProductListByOldest(String cat_id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.TITLE + "," +
                Product.SUB_CATEGORY + "," +
                Product.PURCHASE_PRICE + "," +
                Product.SALE_PRICE + "," +
                Product.IMAGE_URL + "," +
                Product.DISCOUNT + "," +
                Product.RATING_TOTAL + "," +
                Product.RATING_NUM + "," +
                Product.ADD_TIMESTAMP + "," +
                Product.NUMBER_OF_VIEW +
                " FROM " + Product.TABLE +
                " WHERE " + Product.SUB_CATEGORY + " = " + cat_id +
                " ORDER BY " + Product.ADD_TIMESTAMP;

        Log.e("Asc", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
//                ProductDetail obj = new ProductDetail(cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)), cursor.getString(cursor.getColumnIndex(Product.TITLE)), cursor.getString(cursor.getColumnIndex(Product.SALE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.PURCHASE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)), cursor.getString(cursor.getColumnIndex(Product.NUMBER_OF_VIEW)), cursor.getString(cursor.getColumnIndex(Product.RATING_NUM)), cursor.getString(cursor.getColumnIndex(Product.RATING_TOTAL)), cursor.getString(cursor.getColumnIndex(Product.ADD_TIMESTAMP)), cursor.getString(cursor.getColumnIndex(Product.SUB_CATEGORY)), cursor.getString(cursor.getColumnIndex(Product.IMAGE_URL)));
//                vProducts.add((ProductDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }


    public Vector<ProductDetail> getProductListByNewest(String cat_id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.TITLE + "," +
                Product.SUB_CATEGORY + "," +
                Product.PURCHASE_PRICE + "," +
                Product.IMAGE_URL + "," +
                Product.SALE_PRICE + "," +
                Product.DISCOUNT + "," +
                Product.RATING_TOTAL + "," +
                Product.RATING_NUM + "," +
                Product.ADD_TIMESTAMP + "," +
                Product.NUMBER_OF_VIEW +
                " FROM " + Product.TABLE +
                " WHERE " + Product.SUB_CATEGORY + " = " + cat_id +
                " ORDER BY " + Product.ADD_TIMESTAMP + " DESC";

        Log.e("Desc", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
//                ProductDetail obj = new ProductDetail(cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)), cursor.getString(cursor.getColumnIndex(Product.TITLE)), cursor.getString(cursor.getColumnIndex(Product.SALE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.PURCHASE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)), cursor.getString(cursor.getColumnIndex(Product.NUMBER_OF_VIEW)), cursor.getString(cursor.getColumnIndex(Product.RATING_NUM)), cursor.getString(cursor.getColumnIndex(Product.RATING_TOTAL)), cursor.getString(cursor.getColumnIndex(Product.ADD_TIMESTAMP)), cursor.getString(cursor.getColumnIndex(Product.SUB_CATEGORY)), cursor.getString(cursor.getColumnIndex(Product.IMAGE_URL)));
//                vProducts.add((ProductDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }

    public Vector<ProductDetail> getProductListByMostviewed(String cat_id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.TITLE + "," +
                Product.SUB_CATEGORY + "," +
                Product.PURCHASE_PRICE + "," +
                Product.SALE_PRICE + "," +
                Product.IMAGE_URL + "," +
                Product.DISCOUNT + "," +
                Product.RATING_TOTAL + "," +
                Product.RATING_NUM + "," +
                Product.ADD_TIMESTAMP + "," +
                Product.NUMBER_OF_VIEW +
                " FROM " + Product.TABLE +
                " WHERE " + Product.SUB_CATEGORY + " = " + cat_id +
                " ORDER BY " + Product.NUMBER_OF_VIEW + " DESC";

        Log.e("Desc", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
//                ProductDetail obj = new ProductDetail(cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)), cursor.getString(cursor.getColumnIndex(Product.TITLE)), cursor.getString(cursor.getColumnIndex(Product.SALE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.PURCHASE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)), cursor.getString(cursor.getColumnIndex(Product.NUMBER_OF_VIEW)), cursor.getString(cursor.getColumnIndex(Product.RATING_NUM)), cursor.getString(cursor.getColumnIndex(Product.RATING_TOTAL)), cursor.getString(cursor.getColumnIndex(Product.ADD_TIMESTAMP)), cursor.getString(cursor.getColumnIndex(Product.SUB_CATEGORY)), cursor.getString(cursor.getColumnIndex(Product.IMAGE_URL)));
//                vProducts.add((ProductDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }*/

    public Vector<ProductDetail> getProductList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.P_ID + "," +
                Product.CATEGORY_ID + "," +
                Product.PRODUCT_NAME + "," +
                Product.PRODUCT_IMAGE + "," +
                Product.PRODUCT_COLOR + "," +
                Product.PRODUCT_STATUS + "," +
                Product.SELLING_PRICE + "," +
                Product.DISCOUNT + "," +
                Product.PRODUCT_PRICE + "," +
                Product.ABOUT_PRODUCT + "," +
                Product.PRODUCT_SPECIFICATION + "," +
                Product.WORK + "," +
                Product.FEBRIC + "," +
                Product.SHIPPING_CHARGE + "," +
                Product.PRODUCT_QUANTITY + "," +
                Product.PRODUCT_DESCRIPTION  +
                " FROM " + Product.TABLE;


        //Student student = new Student();
        Log.e("CAt", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
                ProductDetail obj = new ProductDetail(
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.P_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(Product.SELLING_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.ABOUT_PRODUCT)),
                        cursor.getString(cursor.getColumnIndex(Product.FEBRIC)),
                        cursor.getString(cursor.getColumnIndex(Product.SHIPPING_CHARGE)),
                        cursor.getString(cursor.getColumnIndex(Product.WORK)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(Product.CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_SPECIFICATION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_STATUS)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_COLOR)),
                        cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)));
                vProducts.add((ProductDetail) obj);
                vProducts.add((ProductDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }


    public Vector<ProductDetail> getProductListByCategory(String cat_id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.P_ID + "," +
                Product.CATEGORY_ID + "," +
                Product.PRODUCT_NAME + "," +
                Product.PRODUCT_IMAGE + "," +
                Product.PRODUCT_COLOR + "," +
                Product.PRODUCT_STATUS + "," +
                Product.SELLING_PRICE + "," +
                Product.DISCOUNT + "," +
                Product.PRODUCT_PRICE + "," +
                Product.ABOUT_PRODUCT + "," +
                Product.PRODUCT_SPECIFICATION + "," +
                Product.WORK + "," +
                Product.FEBRIC + "," +
                Product.SHIPPING_CHARGE + "," +
                Product.PRODUCT_QUANTITY + "," +
                Product.PRODUCT_DESCRIPTION + "," +
                " FROM " + Product.TABLE +
                " WHERE " + Product.CATEGORY_ID + " = " + cat_id;


        //Student student = new Student();
        Log.e("CAt", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
                ProductDetail obj = new ProductDetail(
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.P_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(Product.SELLING_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Product.ABOUT_PRODUCT)),
                        cursor.getString(cursor.getColumnIndex(Product.FEBRIC)),
                        cursor.getString(cursor.getColumnIndex(Product.SHIPPING_CHARGE)),
                        cursor.getString(cursor.getColumnIndex(Product.WORK)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(Product.CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_SPECIFICATION)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_STATUS)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(Product.PRODUCT_COLOR)),
                        cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)));
                vProducts.add((ProductDetail) obj);
                vProducts.add((ProductDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }


   /* public Vector<ProductDetail> getProductList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Product.PRODUCT_ID + "," +
                Product.TITLE + "," +
                Product.SUB_CATEGORY + "," +
                Product.PURCHASE_PRICE + "," +
                Product.SALE_PRICE + "," +
                Product.DISCOUNT + "," +
                Product.IMAGE_URL + "," +
                Product.RATING_TOTAL + "," +
                Product.RATING_NUM + "," +
                Product.ADD_TIMESTAMP + "," +
                Product.NUMBER_OF_VIEW +
                " FROM " + Product.TABLE;

        //Student student = new Student();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<ProductDetail> vProducts = new Vector<ProductDetail>();

        if (cursor.moveToFirst()) {
            do {
//                ProductDetail obj = new ProductDetail(cursor.getString(cursor.getColumnIndex(Product.PRODUCT_ID)), cursor.getString(cursor.getColumnIndex(Product.TITLE)), cursor.getString(cursor.getColumnIndex(Product.SALE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.PURCHASE_PRICE)), cursor.getString(cursor.getColumnIndex(Product.DISCOUNT)), cursor.getString(cursor.getColumnIndex(Product.NUMBER_OF_VIEW)), cursor.getString(cursor.getColumnIndex(Product.RATING_NUM)), cursor.getString(cursor.getColumnIndex(Product.RATING_TOTAL)), cursor.getString(cursor.getColumnIndex(Product.ADD_TIMESTAMP)), cursor.getString(cursor.getColumnIndex(Product.SUB_CATEGORY)), cursor.getString(cursor.getColumnIndex(Product.IMAGE_URL)));
//                vProducts.add((ProductDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vProducts;

    }*/

//        public Student getStudentById(int Id) {
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            String selectQuery = "SELECT  " +
//                    Student.KEY_ID + "," +
//                    Student.KEY_name + "," +
//                    Student.KEY_email + "," +
//                    Student.KEY_age +
//                    " FROM " + Student.TABLE
//                    + " WHERE " +
//                    Student.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string
//
//            int iCount = 0;
//            Student student = new Student();
//
//            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});
//
//            if (cursor.moveToFirst()) {
//                do {
//                    student.student_ID = cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
//                    student.name = cursor.getString(cursor.getColumnIndex(Student.KEY_name));
//                    student.email = cursor.getString(cursor.getColumnIndex(Student.KEY_email));
//                    student.age = cursor.getInt(cursor.getColumnIndex(Student.KEY_age));
//
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//            db.close();
//            return student;
//        }


}
