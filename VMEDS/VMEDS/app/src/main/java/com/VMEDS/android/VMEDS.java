package com.VMEDS.android;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.model.CategoryDetail;
import com.VMEDS.android.model.ProductDetail;

import java.util.Vector;

/**
 * Created by Pratik on 2/18/2017.
 */
public class VMEDS extends Application {

    private int fromHome = 1, fromwishList = 1, fragmentIndex = 0, loginFromHome = 0, qrGenerated = 0, fromWholeSaler = 0;
    private int currentIndex = 0;
    private String product_id, category_name, qrGeneratedName, qrGeneratedNumber, order_id, offerCode;
    private CategoryDetail objSubCat;
    private Vector<CategoryDetail> vCategoryList;
    private ProductDetail objProductDetail;
    public Vector<AddtoCartDetail> vDetails;
    private float finalTotal;
    private double offerAmount;

    public int getFromHome() {

        return fromHome;
    }

    public void setvDetails(Vector<AddtoCartDetail> vDetails) {
        this.vDetails = vDetails;
    }

    public void setOfferAmount(double offerAmount) {
        this.offerAmount = offerAmount;
    }

    public double getOfferAmount() {
        return offerAmount;
    }

    public void setFinalTotal(float finalTotal) {
        this.finalTotal = finalTotal;
    }

    public float getFinalTotal() {
        return finalTotal;
    }

    public Vector<AddtoCartDetail> getvDetails() {
        return vDetails;
    }

    public void setFromHome(int fromHome) {

        this.fromHome = fromHome;

    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setorder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getorder_id() {
        return order_id;
    }

    public int getFromWholeSaler() {

        return fromWholeSaler;
    }

    public void setFromWholeSaler(int fromWholeSaler) {

        this.fromWholeSaler = fromWholeSaler;

    }

    public int getQRGenerated() {

        return qrGenerated;
    }

    public void setQRGenerated(int qrGenerated) {

        this.qrGenerated = qrGenerated;

    }

    public String getQRGeneratedNumber() {

        return qrGeneratedNumber;
    }

    public void setQRGeneratedNumber(String qrGeneratedNumber) {

        this.qrGeneratedNumber = qrGeneratedNumber;

    }

    public String getQRGeneratedName() {

        return qrGeneratedName;
    }

    public void setQRGeneratedName(String qrGeneratedName) {

        this.qrGeneratedName = qrGeneratedName;

    }


    public int getloginFromHome() {

        return loginFromHome;
    }

    public void setloginFromHome(int loginFromHome) {

        this.loginFromHome = loginFromHome;

    }


    public int getCurrentIndex() {

        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {

        this.currentIndex = currentIndex;
    }

    public int getFragmentIndex() {

        return fragmentIndex;
    }

    public void setFragmentIndex(int fragmentIndex) {

        this.fragmentIndex = fragmentIndex;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setObjSubCat(CategoryDetail objSubCat) {
        this.objSubCat = objSubCat;
    }

    public CategoryDetail getObjSubCat() {
        return objSubCat;
    }

    public void setObjProductDetail(ProductDetail objProductDetail) {
        this.objProductDetail = objProductDetail;
    }

    public ProductDetail getObjProductDetail() {
        return objProductDetail;
    }


    public int getFromWishList() {

        return fromwishList;
    }

    public void setFromWishList(int fromwishList) {

        this.fromwishList = fromwishList;

    }


    public Vector<CategoryDetail> getvCategoryList() {
        return vCategoryList;
    }

    public void setvCategoryList(Vector<CategoryDetail> vCategoryList) {
        this.vCategoryList = vCategoryList;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}
