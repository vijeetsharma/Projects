package com.VMEDS.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.VMEDS.android.adapter.GridCategoryAdapter;
import com.VMEDS.android.adapter.VideoCategoryAdapter;

import java.util.Vector;

public class video_view_list extends Activity {

    GridView gridView;

    Toolbar toolbar;


    String[] items = new String[]{
            "http://delegatesolution.com/whats_app/upload/video/c1_v49.mp4",
            "http://delegatesolution.com/whats_app/upload/video/c1_v46.mp4",
            "http://delegatesolution.com/whats_app/upload/video/c1_v43.mp4",
            "http://delegatesolution.com/whats_app/upload/video/c1_v39.mp4",
            "http://delegatesolution.com/whats_app/upload/video/c1_v34.mp4"
    };

//    int[] imageId = {
//            R.drawable.arrowdd,
//            R.drawable.bag,
//            R.drawable.camera,
//            R.drawable.cast_ic_mini_controller_pause,
//            R.drawable.cast_ic_mini_controller_play
//
//    };

    Vector<String[]> vCategory=new Vector<String[]>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_list);

        toolbar=findViewById(R.id.toolbar3);
//        setActionBar(toolbar);
        toolbar.setTitle("Videos");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        gridView=findViewById(R.id.video_list);

        VideoCategoryAdapter adapter = new VideoCategoryAdapter(video_view_list.this, items);

        gridView.setAdapter(adapter);


    }
}
