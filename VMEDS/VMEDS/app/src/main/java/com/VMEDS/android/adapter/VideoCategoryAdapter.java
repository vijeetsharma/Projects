package com.VMEDS.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.VMEDS.android.R;
import com.VMEDS.android.video_view;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by dell on 12/10/2017.
 */

public class VideoCategoryAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    //    private final int[] Imageid;
    CardView cardView;
    Bitmap bitmap;

    public VideoCategoryAdapter(Context c, String[] web) {
        mContext = c;
//        this.Imageid = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.video_list_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.video_name);
            ImageView imageView = (ImageView) grid.findViewById(R.id.video_image);
            textView.setText(web[position]);


            try {
                FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
                mmr.setDataSource(web[position]);
                mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
                mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
                Bitmap b = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
                byte[] artwork = mmr.getEmbeddedPicture();

                mmr.release();
                imageView.setImageBitmap(b);



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Please wait...", Toast.LENGTH_SHORT).show();
            }


//            if (web[position].contains(".mp4")) {
//                bitmap = ThumbnailUtils.createVideoThumbnail(web[position], MediaStore.Video.Thumbnails.MINI_KIND);
//            }


            cardView = grid.findViewById(R.id.layoutVideo);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String pos = web[position];
                    Intent intent = new Intent(mContext, video_view.class);
                    intent.putExtra("position", pos);
                    mContext.startActivity(intent);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}
