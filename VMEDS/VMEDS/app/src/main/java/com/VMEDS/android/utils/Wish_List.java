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


public class Wish_List {


    private DBHelper dbHelper;

    public Wish_List(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int isIteminWishList(String product_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor mCount = db.rawQuery("select * from " + Wish.TABLE + " where " + Wish.PRODUCT_ID + "='" + product_id + "'", null);

        int count = mCount.getCount();
        Log.e("Count:", count + " ");
        return count;
    }

    public int insert(Wish product) {
        long cart_Id;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Wish.PRODUCT_ID, product.product_id);
        values.put(Wish.PRODUCT_TITLE, product.product_title);
        values.put(Wish.PRODUCT_IMAGE_URL, product.image_url);
        values.put(Wish.PRODUCT_QUANTITY, product.quantity);
        values.put(Wish.PRICE, product.price);

        // Inserting Row
        cart_Id = db.insert(Wish.TABLE, null, values);
        db.close(); // Closing database connection

        return (int) cart_Id;
    }

    public int insert(Vector<AddtoCartDetail> vWishList) {
        long cart_Id = -1;
        delete();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < vWishList.size(); i++) {

            AddtoCartDetail obj = (AddtoCartDetail) vWishList.elementAt(i);

            ContentValues values = new ContentValues();
            values.put(Wish.PRODUCT_ID, obj.product_id);
            values.put(Wish.PRODUCT_TITLE, obj.product_title);
            values.put(Wish.PRODUCT_IMAGE_URL, obj.image_url);
            values.put(Wish.PRODUCT_QUANTITY, obj.quantity);
            values.put(Wish.PRICE, obj.price);

            // Inserting Row
            cart_Id = db.insert(Wish.TABLE, null, values);
        }
        db.close(); // Closing database connection

        return (int) cart_Id;
    }

    public int update(String product_id, String quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(AddToCart.PRODUCT_QUANTITY, quantity);

        long cart_Id = db.update(AddToCart.TABLE, data, AddToCart.CART_PRODUCT_ID + "=" + product_id, null);
        return (int) cart_Id;
    }

    public void delete() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.execSQL("delete from " + Wish.TABLE);
        db.close(); // Closing database connection
    }

    public void delete(String product_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Wish.TABLE, Wish.PRODUCT_ID + "= ?", new String[]{String.valueOf(product_id)});
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

    public Vector<AddtoCartDetail> getWishList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +

                Wish.PRODUCT_ID + "," +
                Wish.PRODUCT_TITLE + "," +
                Wish.PRODUCT_IMAGE_URL + "," +
                Wish.PRODUCT_QUANTITY + "," +
                Wish.PRICE +
                " FROM " + Wish.TABLE;

        Log.e("Asc", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<AddtoCartDetail> vCartDetails = new Vector<AddtoCartDetail>();

        if (cursor.moveToFirst()) {
            do {
                AddtoCartDetail obj = new AddtoCartDetail("0", cursor.getString(cursor.getColumnIndex(Wish.PRODUCT_ID)), cursor.getString(cursor.getColumnIndex(Wish.PRODUCT_QUANTITY)), cursor.getString(cursor.getColumnIndex(Wish.PRICE)), cursor.getString(cursor.getColumnIndex(Wish.PRODUCT_TITLE)), cursor.getString(cursor.getColumnIndex(Wish.PRODUCT_IMAGE_URL)));
                vCartDetails.add((AddtoCartDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vCartDetails;

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
