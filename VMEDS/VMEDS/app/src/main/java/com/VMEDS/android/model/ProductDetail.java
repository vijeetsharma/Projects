package com.VMEDS.android.model;

/**
 * Created by Pratik on 2/20/2017.
 */
public class ProductDetail {

    //public String  sold_by, unit, title, sale_price, purchase_price, num_of_imgs, images, discount, number_of_view, rating_num, rating_total, discount_type, category, sub_category, color, description, additional_fields, share_link, add_timestamp;
    public String product_id,title,unit,sale_price,purchase_price,num_of_imgs,images,category,sub_category,sub_category_id,sub_category_name,color,description,additional_fields,share_link,number_of_view,
            rating_num,rating_total,discount_type,sold_by,discount,p_id,product_status, product_image, product_color, product_name, selling_price, product_price, category_id, about_product, febric, shipping_charge, work, product_quantity, product_description, product_specification;

    public ProductDetail(String product_id, String p_id, String product_name, String selling_price, String product_price, String about_product, String febric, String shipping_charge, String work, String product_quantity, String category_id, String product_description, String product_specification, String product_status, String product_image, String product_color,String discount) {
        this.product_id = product_id;
        this.p_id = p_id;
        this.product_name = product_name;
        this.selling_price = selling_price;
        this.product_price = product_price;
        this.about_product = about_product;
        this.febric = febric;
        this.shipping_charge = shipping_charge;
        this.work = work;
        this.product_quantity = product_quantity;
        this.product_description = product_description;
        this.product_specification = product_specification;
        this.product_status = product_status;
        this.product_image = product_image;
        this.product_color = product_color;
        this.category_id = category_id;
        this.discount=discount;
    }

    public ProductDetail(String sub_category_id,String sub_category_name ) {
        this.sub_category_id=sub_category_id;
        this.sub_category_name=sub_category_name;
    }


    public ProductDetail(String product_id, String title, String sale_price, String purchase_price, String num_of_imgs, String images, String category, String sub_category, String color, String description, String additional_fields, String share_link, String discount, String number_of_view, String rating_num, String rating_total, String discount_type, String unit, String sold_by) {
        this.product_id = product_id;
        this.title = title;
        this.unit = unit;
        this.sale_price = sale_price;
        this.purchase_price = purchase_price;
        this.num_of_imgs = num_of_imgs;
        this.images = images;
        this.category = category;
        this.sub_category = sub_category;
        this.color = color;
        this.description = description;
        this.additional_fields = additional_fields;
        this.share_link = share_link;
        this.discount = discount;
        this.number_of_view = number_of_view;
        this.rating_num = rating_num;
        this.rating_total = rating_total;
        this.discount = discount;
        this.discount_type = discount_type;
        this.sold_by = sold_by;
    }
/*//    public ProductDetail(String product_id, String title, String sale_price, String purchase_price, String num_of_imgs, String images, String discount, String number_of_view, String rating_num, String rating_total, String discount_type, String unit, String add_timestamp, String sub_category) {
//        this.product_id = product_id;
//        this.title = title;
//        this.sale_price = sale_price;
//        this.purchase_price = purchase_price;
//        this.num_of_imgs = num_of_imgs;
//        this.images = images;
//        this.discount = discount;
//        this.number_of_view = number_of_view;
//        this.rating_num = rating_num;
//        this.rating_total = rating_total;
//        this.add_timestamp = add_timestamp;
//        this.discount_type = discount_type;
//        this.sub_category = sub_category;
//        this.unit = unit;
//    }
//
//    public ProductDetail(String product_id, String title, String sale_price, String purchase_price, String discount, String number_of_view, String rating_num, String rating_total, String add_timestamp, String sub_category, String images) {
//        this.product_id = product_id;
//        this.title = title;
//        this.sale_price = sale_price;
//        this.purchase_price = purchase_price;
//        this.add_timestamp = add_timestamp;
//        this.discount = discount;
//        this.number_of_view = number_of_view;
//        this.rating_num = rating_num;
//        this.rating_total = rating_total;
//        this.sub_category = sub_category;
//        this.images = images;
//    }*/
//

}
