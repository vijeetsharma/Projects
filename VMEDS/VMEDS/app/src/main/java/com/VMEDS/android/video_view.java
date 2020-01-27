package com.VMEDS.android;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class video_view extends AppCompatActivity {


    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    ArrayList<String> listOfVideo;
    int songIndex;


    SeekBar sbVolume, sbBrightness;
    private AudioManager audioManager;
    //Variable to store brightness value
    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;
    int maxVolume = 1;
    AVLoadingIndicatorView loadingAvi;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        initializeControls();
        videoView = (VideoView) findViewById(R.id.videoView);
        loadingAvi=findViewById(R.id.loadingAvi);


        Intent intent=getIntent();
        String url=intent.getStringExtra("position");


        if (mediaController == null) {
            mediaController = new MediaController(video_view.this);


            videoView.setMediaController(mediaController);
            videoView.setVideoURI(Uri.parse(url));
            videoView.seekTo(100);
            videoView.start();


            // Set the videoView that acts as the anchor for the MediaController.
//            mediaController.setAnchorView(videoView);


            // Set MediaController for VideoView
            videoView.setMediaController(mediaController);
        }

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {


                videoView.seekTo(position);
                if (position == 0) {
                    loadingAvi.setVisibility(View.GONE);
                    videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {


                        // Re-Set the videoView that acts as the anchor for the MediaController
//                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {

                    loadingAvi.setVisibility(View.GONE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {

                    loadingAvi.setVisibility(View.GONE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                    loadingAvi.setVisibility(View.GONE);
                }
                return false;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }


    private void initializeControls() {
        //get reference of the UI Controls
//        sbVolume = (SeekBar) findViewById(R.id.volume);
//        sbBrightness = (SeekBar) findViewById(R.id.brightness);
//        tVVolume=(TextView)findViewById(R.id.tVVolume);
//        tVBrightness = (TextView) findViewById(R.id.tVBrightness);

//        try {
//
//            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            //set max progress according to volume
//            sbVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
//            //get current volume
//            sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
//            //Set the seek bar progress to 1
//            sbVolume.setKeyProgressIncrement(1);
//            //get max volume
//            maxVolume = sbVolume.getMax();
//            sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress,
//                                              boolean fromUser) {
//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
//                    //Calculate the brightness percentage
//                    float perc = (progress / (float) maxVolume) * 100;
//                    //Set the brightness percentage
////                    tVVolume.setText("Volume: "+(int)perc +" %");
//                }
//            });
//
//        } catch (Exception e) {
//
//        }


        //Get the content resolver
        cResolver = getContentResolver();

        //Get the current window
        window = getWindow();

//        //Set the seekbar range between 0 and 255
//        sbBrightness.setMax(255);
//        //Set the seek bar progress to 1
//        sbBrightness.setKeyProgressIncrement(1);
//
//        try {
//            //Get the current system brightness
//            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
//        } catch (Settings.SettingNotFoundException e) {
//            //Throw an error case it couldn't be retrieved
//            Log.e("Error", "Cannot access system brightness");
//            e.printStackTrace();
//        }
//
//        //Set the progress of the seek bar based on the system's brightness
//        sbBrightness.setProgress(brightness);

        //Register OnSeekBarChangeListener, so it can actually change values
//        sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                //Set the system brightness using the brightness variable value
//                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
//                //Get the current window attributes
//                ViewGroup.LayoutParams layoutpars = window.getAttributes();
//                //Set the brightness of this window
//                // layoutpars.screenBrightness = brightness / (float)255;
//                //Apply attribute changes to this window
//                //window.setAttributes(layoutpars);
//            }
//
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                //Nothing handled here
//            }
//
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                //Set the minimal brightness level
//                //if seek bar is 20 or any value below
//                if (progress <= 20) {
//                    //Set the brightness to 20
//                    brightness = 20;
//                } else //brightness is greater than 20
//                {
//                    //Set brightness variable based on the progress bar
//                    brightness = progress;
//                }
//                //Calculate the brightness percentage
//                float perc = (brightness / (float) 255) * 100;
//                //Set the brightness percentage
////                tVBrightness.setText("Brightness: "+(int)perc +" %");
//            }
//        });
    }




}
