package com.VMEDS.android.utils;

/**
 * Created by Pratik on 3/1/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.VMEDS.android.model.OrderDetail;

import java.util.Vector;


public class MyOrder_List {

    private DBHelper dbHelper;

    public MyOrder_List(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Vector<OrderDetail> vOrderDetail) {
        long cart_Id=0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < vOrderDetail.size(); i++) {
            OrderDetail product = (OrderDetail) vOrderDetail.elementAt(i);
            ContentValues values = new ContentValues();

            values.put(MyOrder.USER_ID, product.user_id);
            values.put(MyOrder.REF_ID, product.ref_id);
            values.put(MyOrder.ORDER_DATE, product.order_date);
            values.put(MyOrder.ADDRESS, product.address);

            values.put(MyOrder.OFFER_APPLY, product.offer_apply);
            values.put(MyOrder.OFFER_CODE, product.offer_code);
            values.put(MyOrder.FINAL_TOTLE, product.final_totle);
            values.put(MyOrder.STATUS, product.status);
            values.put(MyOrder.CART_DATA, product.cart_data);


            // Inserting Row
            cart_Id = db.insert(MyOrder.TABLE, null, values);
        }
        db.close(); // Closing database connection

        return (int) cart_Id;
    }

//    public int update(String product_id, String quantity) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues data = new ContentValues();
//        data.put(MyOrder.PRODUCT_QUANTITY, quantity);
//
//        long cart_Id = db.update(MyOrder.TABLE, data, MyOrder.CART_PRODUCT_ID + "=" + product_id, null);
//        return (int) cart_Id;
//    }

    public void delete() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(MyOrder.TABLE, null, null);
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

    public Vector<OrderDetail> getMyOrderList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + MyOrder.TABLE;

        Log.e("Asc", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<OrderDetail> vOrderDetails = new Vector<OrderDetail>();

        if (cursor.moveToFirst()) {
            do {

                OrderDetail obj = new OrderDetail(cursor.getString(cursor.getColumnIndex(MyOrder.USER_ID)), cursor.getString(cursor.getColumnIndex(MyOrder.REF_ID)), cursor.getString(cursor.getColumnIndex(MyOrder.ORDER_DATE)), cursor.getString(cursor.getColumnIndex(MyOrder.ADDRESS)), cursor.getString(cursor.getColumnIndex(MyOrder.OFFER_APPLY)), cursor.getString(cursor.getColumnIndex(MyOrder.OFFER_CODE)), cursor.getString(cursor.getColumnIndex(MyOrder.FINAL_TOTLE)), cursor.getString(cursor.getColumnIndex(MyOrder.STATUS)), cursor.getString(cursor.getColumnIndex(MyOrder.CART_DATA)));
                vOrderDetails.add((OrderDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vOrderDetails;

    }

    public Vector<OrderDetail> getMyOrderList(String status) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + MyOrder.TABLE + " where " + MyOrder.STATUS + " LIKE '" + status + "'";

        Log.e("Asc", selectQuery);


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        Vector<OrderDetail> vOrderDetails = new Vector<OrderDetail>();

        if (cursor.moveToFirst()) {
            do {

                OrderDetail obj = new OrderDetail(cursor.getString(cursor.getColumnIndex(MyOrder.USER_ID)), cursor.getString(cursor.getColumnIndex(MyOrder.REF_ID)), cursor.getString(cursor.getColumnIndex(MyOrder.ORDER_DATE)), cursor.getString(cursor.getColumnIndex(MyOrder.ADDRESS)), cursor.getString(cursor.getColumnIndex(MyOrder.OFFER_APPLY)), cursor.getString(cursor.getColumnIndex(MyOrder.OFFER_CODE)), cursor.getString(cursor.getColumnIndex(MyOrder.FINAL_TOTLE)), cursor.getString(cursor.getColumnIndex(MyOrder.STATUS)), cursor.getString(cursor.getColumnIndex(MyOrder.CART_DATA)));
                vOrderDetails.add((OrderDetail) obj);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vOrderDetails;

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
