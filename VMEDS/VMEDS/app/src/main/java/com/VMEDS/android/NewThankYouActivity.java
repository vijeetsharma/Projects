package com.VMEDS.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.VMEDS.android.model.AddtoCartDetail;
import com.VMEDS.android.utils.Global_Typeface;

import java.util.Vector;

public class NewThankYouActivity extends AppCompatActivity {
    private TextView txtThankyou, txtThankyouInfo, txtOrderNumber, ctvTitle;
    private Button btnTrackOrder,btnBookAnotherService;
    private Global_Typeface global_typeface;
    private TextView txtTotal, txtRsTotal, txtCartItemTitle, txtCartItemX, txtCartItemQty, txtCartItemRS, txtOrderSummary, txtScheduleDateTime, txtLblName, txtName, txtLblMobileNumber, txtMobileNumber, txtLblSavedAddress;
    private LinearLayout listCartItems;
    private Vector<AddtoCartDetail> vDetails;
    private ImageView civSearch,civAddcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thank_you);
        global_typeface = new Global_Typeface(NewThankYouActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.title_bar_view, null);
        getSupportActionBar().setCustomView(cView);
        ctvTitle = (TextView) findViewById(R.id.Custom_title);
        ctvTitle.setText("Order Placed");
        ctvTitle.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        civSearch = (ImageView) findViewById(R.id.Custom_search);
        civAddcart = (ImageView) findViewById(R.id.Custom_addcart);
        civAddcart.setVisibility(View.GONE);
        civSearch.setVisibility(View.GONE);
        vDetails = ((VMEDS) getApplicationContext()).getvDetails();
        listCartItems = (LinearLayout) findViewById(R.id.listCartItems);
        txtThankyou = (TextView) findViewById(R.id.txtThankyou);
        txtThankyouInfo = (TextView) findViewById(R.id.txtThankyouInfo);
        btnTrackOrder = (Button) findViewById(R.id.btnTrackOrder);
        txtOrderNumber = (TextView) findViewById(R.id.txtOrderNumber);
        txtOrderSummary = (TextView) findViewById(R.id.txtOrderSummary);
        txtOrderSummary.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtOrderNumber.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtOrderNumber.setText("Order ID : " + ((VMEDS) getApplicationContext()).getorder_id());
        txtThankyou.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtThankyouInfo.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        btnTrackOrder.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtRsTotal = (TextView) findViewById(R.id.txtRsTotal);
        txtTotal.setTypeface(global_typeface.TypeFace_Roboto_Regular());
        txtRsTotal.setTypeface(global_typeface.TypeFace_Roboto_Regular());

        txtRsTotal.setText(getResources().getString(R.string.Rs) + " " + ((VMEDS) getApplicationContext()).getFinalTotal());
        btnBookAnotherService=(Button)findViewById(R.id.btnBookAnotherService);
        btnBookAnotherService.setTypeface(global_typeface.TypeFace_Roboto_Bold());
        btnBookAnotherService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewThankYouActivity.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        });
        btnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewThankYouActivity.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        });
        btnTrackOrder.setVisibility(View.GONE);

        for (int position = 0; position < vDetails.size(); position++) {
            View itemView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)
            ).inflate(R.layout.layout_cart_item_row, null);
            txtCartItemX = (TextView) itemView.findViewById(R.id.txtCartItemX);
            txtCartItemTitle = (TextView) itemView.findViewById(R.id.txtCartItemTitle);
            txtCartItemQty = (TextView) itemView.findViewById(R.id.txtCartItemQty);
            txtCartItemRS = (TextView) itemView.findViewById(R.id.txtCartItemRS);

            txtCartItemX.setTypeface(global_typeface.TypeFace_Roboto_Regular());
            txtCartItemTitle.setTypeface(global_typeface.TypeFace_Roboto_Regular());
            txtCartItemQty.setTypeface(global_typeface.TypeFace_Roboto_Regular());
            txtCartItemRS.setTypeface(global_typeface.TypeFace_Roboto_Regular());


            txtCartItemTitle.setText(((AddtoCartDetail) vDetails.elementAt(position)).product_title);
            txtCartItemQty.setText(((AddtoCartDetail) vDetails.elementAt(position)).quantity);

            Double totalPrice = 0.0;
            try {
                totalPrice = Double.parseDouble(((AddtoCartDetail) vDetails.elementAt(position)).price) * Integer.parseInt(((AddtoCartDetail) vDetails.elementAt(position)).quantity);
            } catch (Exception e) {

            }
            txtCartItemRS.setText(getResources().getString(R.string.Rs) + " " + String.valueOf(totalPrice));
            listCartItems.addView(itemView);
        }
    }

    @Override
    public void onBackPressed() {

        return;
    }
}
