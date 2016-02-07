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


public class Secondary_Activity extends Activity {
    Camera mCamera = null;
    private static int camID = -1;
    static boolean front_facing_camera = false;

    public static SurfaceView drawSurface;
    public static SurfaceHolder drawHolder;
    public static Secondary_Activity SA = null;
    public static CameraClass customCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("In Create","CREATE");
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_secondary_);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        RelativeLayout cameraScreen = (RelativeLayout) findViewById(R.id.cameraLayout);

        //surface_view = new SurfaceView(getApplicationContext());
        drawSurface = (SurfaceView)findViewById(R.id.surface_viewff);
        drawHolder = drawSurface.getHolder();
        drawHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("surface activity", "A-DESTROYED");
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("surface activity", "A-CREATED");
                if(customCamera == null) {
                    customCamera = CameraClass.getCustomCameraInstance(drawHolder, getApplicationContext());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
                Log.d("surface activity", "A-CHANGED");
            }

        });

        ImageView image;
        image = new ImageView(getApplicationContext());
        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        CameraClass.trackingImage = image;

        cameraScreen.addView(image);
        ImageView button = (ImageView) findViewById(R.id.iv2);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //sh_callback.surfaceDestroyed(surface_holder);
                Secondary_Activity.customCamera.switchCams();
                //Secondary_Activity.drawSurface.nextCameraSize(Secondary_Activity.drawSurface.mCamera);
            }


        });
        ImageView blue = (ImageView) findViewById(R.id.iv1);
        blue.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            public void onClick(View v) {
                
            }
        });   
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
            //drawSurface.stopCamera();
            if(isFinishing()) {
                Log.d("is finishing true", "true");
            } else {

                Log.d("is finishing fals", "false");
            }
            // Release the Camera because we don't need it when paused
            // and other activities might need to use it.
          //  drawSurface = null;
            //if(drawSurface != null){
            	//drawSurface.stopCamera();
            //}
            //drawSurface = null;
        }
        @Override
        protected void onStop() {
            super.onStop();  // Always call the superclass method first
            Log.d("In Stop","STOP");
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();  // Always call the superclass method first
            Log.d("In Destroy","DESTROY");


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