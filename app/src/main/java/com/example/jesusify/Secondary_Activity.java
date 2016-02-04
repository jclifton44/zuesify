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
    public static DrawClass thisDC; 
    public static Secondary_Activity SA = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        Log.d("InNewAc","Yep");
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_secondary_);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);


        //surface_view = new SurfaceView(getApplicationContext());
        DrawClass thisDC = new DrawClass(getApplicationContext(), (SurfaceView)findViewById(R.id.surface_viewff));
        Secondary_Activity.thisDC = thisDC;
        //addContentView(surface_view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
   /*     try {
        	mCamera.setPreviewDisplay(surface_holder);  
        	Log.d("ONCREATE","CAMERA");
        } catch (IOException exception) { 
        	Log.d("ONCREATE","CAMERA");
            mCamera.release();  
            mCamera = null;  
        }*/
        ImageView button = (ImageView) findViewById(R.id.iv2);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//sh_callback.surfaceDestroyed(surface_holder);
            	Secondary_Activity.thisDC.switchCams();
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
    		// Release the Camera because we don't need it when paused
    		// and other activities might need to use it.
    		if(thisDC != null){
    		//	thisDC.startCamera();
    		}	
    	}
        @Override
        public void onPause() {
            super.onPause();  // Always call the superclass method first
            Log.d("In Pause","PAUSE");
            // Release the Camera because we don't need it when paused
            // and other activities might need to use it.
            super.onPause();
            if(thisDC != null){
            	thisDC.stopCamera();
            }
            //thisDC = null;
        }
        @Override
        protected void onStop() {
            super.onStop();  // Always call the superclass method first
            Log.d("In Stop","STOP");
            // Save the note's current draft, because the activity is stopping
            // and we want to be sure the current note progress isn't lost.

            if(thisDC != null){
            	thisDC.stopCamera();
            }
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();  // Always call the superclass method first
            if(thisDC != null) {
            	thisDC.stopCamera();
            }
            Log.d("In Destroy","DESTROY");
            //

        }
        @Override
        protected void onRestart() {
            super.onRestart();  // Always call the superclass method first
            
            Log.d("In Restart","RESTART");
            if (thisDC != null && thisDC.mCamera == null) {
             //   thisDC.startCamera(); // Local method to handle camera init
            } else {
            	if(((SurfaceView)findViewById(R.id.surface_viewff)) == null) {Log.d("OMG","OMG");}
            //	thisDC = new DrawClass(getApplicationContext(), (SurfaceView)findViewById(R.id.surface_viewff));
            }
                
                // Activity being restarted from stopped state    
        }
        @Override
        public void onResume() {
            super.onResume();  // Always call the superclass method first
            Log.d("In Resume","RESUME");

            // Get the Camera instance as the activity achieves full user focus
            if (thisDC != null) {
               // thisDC.startCamera(); // Local method to handle camera init
           //     try {
    			//	thisDC.mCamera.setPreviewDisplay(thisDC.surface_holder);
    			//	thisDC.mCamera.startPreview();
    			//} catch (IOException e) {
    				// TODO Auto-generated catch block
    			//	e.printStackTrace();
    		//	}
            } else if(thisDC == null) {
           // 	new DrawClass(getApplicationContext(), (SurfaceView)findViewById(R.id.surface_viewff));
            }
        }


        
        
        
        
        
        
        
        
        
        
        
        

}