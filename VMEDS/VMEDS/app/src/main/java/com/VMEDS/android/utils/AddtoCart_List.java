package com.VMEDS.android.utils;

/**
 * Created by Pratik on 3/1/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.VMEDS.android.model.AddtoCartDetail;

import java.util.Vector;


public class AddtoCart_List {


    private DBHelper dbHelper;

    public AddtoCart_List(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(AddToCart product) {
        long cart_Id;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor mCount = db.rawQuery("select " + AddToCart.PRODUCT_QUANTITY + " from " + AddToCart.TABLE + " where "+AddToCart.CART_PRODUCT_ID+"='" + product.product_id + "'", null);

        int count = mCount.getCount();
        Log.e("Count:", count + " ");

        if (count == 0) {
            //Open connection to write data

            ContentValues values = new ContentValues();
            values.put(AddToCart.CART_PRODUCT_ID, product.product_id);
            values.put(AddToCart.PRODUCT_TITLE, product.product_title);
            values.put(AddToCart.PRODUCT_IMAGE_URL, product.image_url);
            values.put(AddToCart.PRODUCT_QUANTITY, product.quantity);
            values.put(AddToCart.PRICE, product.price);

            // Inserting Row
            cart_Id = db.insert(AddToCart.TABLE, null, values);
            db.close(); // Closing database connection
        } else {
            mCount.moveToFirst();
            int quantity = Integer.parseInt(mCount.getString(mCount.getColumnIndex(AddToCart.PRODUCT_QUANTITY)));
            db = dbHelper.getWritableDatabase();
            quantity++;
            Log.e("Heelo",""+quantity);
            ContentValues data = new ContentValues();
            data.put(AddToCart.PRODUCT_QUANTITY, String.valueOf(quantity));

            cart_Id = db.update(AddToCart.TABLE, data, AddToCart.CART_PRODUCT_ID + "='" + product.product_id+"'", null);
            return (int) cart_Id;
        }
        mCount.close();
        return (int) cart_Id;
    }

    public int update(String product_id, String quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(AddToCart.PRODUCT_QUANTITY, quantity);
        Cursor mCount = db.rawQuery("select " + AddToCart.PRODUCT_QUANTITY + " from " + AddToCart.TABLE + " where " + AddToCart.CART_PRODUCT_ID + "='" + product_id + "'", null);

        int count = mCount.getCount();
        Log.e("Count:", count + " ");
        long cart_Id = db.update(AddToCart.TABLE, data, AddToCart.CART_PRODUCT_ID + "='" + product_id+"'", null);
        return (int) cart_Id;
    }

    public void delete(String product_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(AddToCart.TABLE, AddToCart.CART_PRODUCT_ID + "= ?", new String[]{String.valueOf(product_id)});
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

    public Vector<AddtoCartDetail> getCartList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                AddToCart.CART_ID + "," +
                AddToCart.CART_PRODUCT_ID + "," +
                AddToCart.PRODUCT_TITLE + "," +
                AddToCart.PRODUCT_IMAGE_URL + "," +
                AddToCart.PRODUCT_QUANTITY + "," +
                AddToCart.PRICE +
                " FROM " + AddToCart.TABLE;

        Log.e("Asc", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<AddtoCartDetail> vCartDetails = new Vector<AddtoCartDetail>();

        if (cursor.moveToFirst()) {
            do {
                AddtoCartDetail obj = new AddtoCartDetail(cursor.getString(cursor.getColumnIndex(AddToCart.CART_ID)), cursor.getString(cursor.getColumnIndex(AddToCart.CART_PRODUCT_ID)), cursor.getString(cursor.getColumnIndex(AddToCart.PRODUCT_QUANTITY)), cursor.getString(cursor.getColumnIndex(AddToCart.PRICE)), cursor.getString(cursor.getColumnIndex(AddToCart.PRODUCT_TITLE)), cursor.getString(cursor.getColumnIndex(AddToCart.PRODUCT_IMAGE_URL)));
                vCartDetails.add((AddtoCartDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vCartDetails;

    }
    public void delete() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(AddToCart.TABLE, null, null);
        db.close(); // Closing database connection
    }

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
