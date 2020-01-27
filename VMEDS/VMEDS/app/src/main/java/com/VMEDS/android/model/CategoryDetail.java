package com.VMEDS.android.model;

import java.util.Vector;

/**
 * Created by Pratik on 2/21/2017.
 */
public class CategoryDetail {
    public String sub_category_id, sub_category_name;
    public String category_id, category_name, images;
    public Vector<CategoryDetail> vSubCat;

    public CategoryDetail(String sub_category_id, String sub_category_name) {
        this.sub_category_id = sub_category_id;
        this.sub_category_name = sub_category_name;
    }

    public CategoryDetail(String category_id, String category_name, String images, Vector<CategoryDetail> vSubCat) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.vSubCat = vSubCat;
        this.images = images;
    }
}
