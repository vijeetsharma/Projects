package com.VMEDS.android.model;

/**
 * Created by Yogesh on 6/8/2017.
 */

public class OrderDetail {

    public String user_id, ref_id, order_date, address, offer_apply, offer_code, final_totle,  status;
    public String cart_data;

    public OrderDetail(String user_id, String ref_id, String order_date, String address, String offer_apply, String offer_code, String final_totle,  String status,String cart_data) {
        this.user_id = user_id;
        this.ref_id = ref_id;
        this.order_date = order_date;
        this.address = address;
        this.offer_apply = offer_apply;
        this.offer_code = offer_code;
        this.final_totle = final_totle;
        this.status = status;
        this.cart_data = cart_data;
    }
}
