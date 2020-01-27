package com.VMEDS.android.model;

/**
 * Created by Pratik on 3/1/2017.
 */
public class AddtoCartDetail {
    public String cart_id;
    public String product_id, quantity, price, product_title, image_url;

    public AddtoCartDetail(String cart_id, String product_id, String quantity, String price, String product_title, String image_url) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.product_title = product_title;
        this.image_url = image_url;
    }
}
