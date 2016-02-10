package com.example.jesusify;

import java.io.IOException;

import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.view.View.OnClickListener;
import android.content.Context;
import android.media.Image;
import android.content.res.Resources;
import android.content.res.Configuration;

public class Secondary_Activity extends Activity {
    Camera mCamera = null;
    static int camOnClose = -1;
    static boolean front_facing_camera = false;
    private static Context context;
    private static Resources resources;
    public static RelativeLayout cameraScreen;
    public static SurfaceView cameraSurface;

    public static SurfaceHolder cameraHolder;

    public static Secondary_Activity SA = null;
    public static CameraClass customCamera;
    static Integer cameraInt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("In Create","CREATE");
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();
        CameraClass.mainActivity = this;
		setContentView(R.layout.activity_secondary_);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        cameraScreen = (RelativeLayout) findViewById(R.id.cameraLayout);

        ImageView image;
        image = new ImageView(getApplicationContext());
        //Depends on what button has been pressed
        image.setImageDrawable(getResources().getDrawable(R.drawable.doge_sticker));
        image.setRotation(270);
        image.setId(cameraInt++);
        image.setX(1180);
        image.setY(100);
        cameraScreen.addView(image);


        //surface_view = new SurfaceView(getApplicationContext());
        cameraSurface = (SurfaceView)findViewById(R.id.surface_viewf1);

        cameraHolder = cameraSurface.getHolder();
        cameraHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("surface activity", "A-DESTROYED");
                camOnClose = customCamera.getActiveCamera();
                customCamera.stopCamera();
                customCamera = null;
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("surface activity", "A-CREATED");

                //if(customCamera == null) {
                ///    customCamera = CameraClass.getCustomCameraInstance(cameraHolder, getApplicationContext(), camOnClose);
                //}

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
                Log.d("surface activity", "A-CHANGED");
                if(customCamera == null) {
                    customCamera = CameraClass.getCustomCameraInstance(cameraHolder, getApplicationContext(), camOnClose);
                }

            }

        });

        context = getApplicationContext();
        resources = getResources();
        ImageView button = (ImageView) findViewById(R.id.iv2);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //sh_callback.surfaceDestroyed(surface_holder);
                Secondary_Activity.customCamera.switchCams();
                //Secondary_Activity.cameraSurface.nextCameraSize(Secondary_Activity.cameraSurface.mCamera);
            }


        });
        ImageView blue = (ImageView) findViewById(R.id.iv1);
        blue.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            public void onClick(View v) {
                
            }
        });   
    }
    public ImageView getImageInstance(int percent) {

        ImageView image;
        image = new ImageView(getApplicationContext());
        //Depends on what button has been pressed
        image.setImageDrawable(getResources().getDrawable(R.drawable.doge_sticker));
        image.setRotation(0);
        image.setId(cameraInt++);
        Integer w = getResources().getDrawable(R.drawable.doge_sticker).getMinimumWidth();
        Integer h = getResources().getDrawable(R.drawable.doge_sticker).getMinimumHeight();

        LayoutParams layout = new LayoutParams((int)(h * (percent / 100f)), (int)(w * (percent / 100f)));
        image.setLayoutParams(layout);

        cameraScreen.addView(image);
        image.setVisibility(View.INVISIBLE);


        return image;

    }
    	@Override
    	public void onStart() {
    		super.onStart();  // Always call the superclass method first
    		Log.d("In Start","START");
    	}
        @Override
        public void onPause() {
            super.onPause();  // Always call the superclass method first
            Log.d("In Pause", "PAUSE");
            //cameraSurface.stopCamera();
            if(isFinishing()) {
                Log.d("is finishing true", "true");
            } else {

                Log.d("is finishing fals", "false");
            }
            // Release the Camera because we don't need it when paused
            // and other activities might need to use it.
          //  cameraSurface = null;
            //if(cameraSurface != null){
            	//cameraSurface.stopCamera();
            //}
            //cameraSurface = null;
        }
        @Override
        protected void onStop() {
            super.onStop();  // Always call the superclass method first
            ImageView view;
            while(0 < CameraClass.masks.size()) {
                view = CameraClass.masks.get( CameraClass.masks.size() - 1 );
                view.setVisibility(View.INVISIBLE);
                CameraClass.masks.remove(view);
            }
            Log.d("In Stop","STOP");
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();  // Always call the superclass method first
            Log.d("In Destroy","DESTROY");


        }
        @Override
        public void onConfigurationChanged(Configuration config) {
            super.onConfigurationChanged(config);
            Log.d("config", "change!");

        }
        @Override
        protected void onRestart() {
            super.onRestart();  // Always call the superclass method firs
            Log.d("In Restart","RESTART");

        }
        @Override
        public void onResume() {
            super.onResume();  // Always call the superclass method first
            Log.d("In Resume", "RESUME");
        }





        
        
        
        
        
        
        
        
        
        
        
        

}