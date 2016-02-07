package com.example.jesusify;

import java.io.IOException;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Face;

import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.hardware.camera2.*;
public class CameraClass {
    static Canvas mCanvas;
	private Camera mCamera = null;
    public static ImageView trackingImage;
    private boolean locker=true;
	private Thread thread;
    SurfaceHolder.Callback sh_ob = null;
    private SurfaceHolder surface_holder = null;
    SurfaceHolder.Callback sh_callback  = null;
    private static int camID = -1;
    private static boolean front_facing_camera = false;
    static boolean camera_started = false;
	public CameraClass(Camera c, SurfaceHolder sh) {
        mCamera  = c;
        surface_holder = sh;
//        ImageView button = (ImageView) findViewById(R.id.iv2);
//        if(findFrontFacingCamera() < 0) {
//        	button.setVisibility(View.INVISIBLE);
//        }
		// TODO Auto-generated constructor stub
	}
    public static CameraClass getCustomCameraInstance(SurfaceHolder sh, Context c) {
        Camera mcam;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            //handle NEW camera (We need a device compatible with lollipop
            CameraManager cm = (CameraManager) c.getSystemService(Context.CAMERA_SERVICE);
            try{
                //   String[] cameraInfo = cm.getCameraIdList();
            } catch (Exception e) {

            }

        }

        if (findFrontFacingCamera() < 0) {
            mcam = Camera.open();
            Log.d("found front facing -1", "-1");
            camID = -1;
            front_facing_camera = false;
        } else {
            Log.d("Found front camera", "" + findFrontFacingCamera());
            front_facing_camera = true;
            mcam = Camera.open(findFrontFacingCamera());
            camID = findFrontFacingCamera();
        }
        CameraClass returnC = new CameraClass(mcam,sh);
        returnC.startPreviewDetection();
        return returnC;
    }


    public void resizeResolutionKitKat(Camera c) {
        Camera.Parameters cp = c.getParameters();
        List<Camera.Size> cameraParameterList = cp.getSupportedPreviewSizes();
        cp.setPreviewSize(cameraParameterList.get(0).width, cameraParameterList.get(0).height);
        c.setParameters(cp);
    }
    public void startFaceDetection(Camera c) {
        FaceDetectionListener listener = new FaceDetectionListener() {
            @Override
            public void onFaceDetection(Face[] faces, Camera c) {
                if(faces.length > 0) {
                    Canvas can;
                   // if((can = DrawClass.surface_holder.lockCanvas()) == null) {
                   // Log.d("Can", "is Null");
                   // } else {
                   // surface_holder.unlockCanvasAndPost(can);
                   // }
                   // draw(canvas);
                   // Log.d("Tracking Image" + faces[0].rect.flattenToString(), "Is Null");

                    if (faces[0].rect != null) {
                         //DrawClass.trackingImage.setX(400);

                        trackingImage.setX((int)-(faces[0].rect.centerX() / 1.5)+400);
                        trackingImage.setY((int)(faces[0].rect.centerY()/1.9)+360);
                        Log.d(String.valueOf(faces[0].rect.centerX()),String.valueOf(faces[0].rect.centerY()));

                    }


                    Log.d("Found a Face", "!");

                }

            }
        };

        c.setFaceDetectionListener(listener);

        c.startFaceDetection();
    }
    public void uniqueMethod() {

        Log.d("This is", "My unique Method");
    }
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        Log.d("surface activity", "A-CREATED");
//        if(findFrontFacingCamera() < 0){
//            mCamera = Camera.open();
//            Log.d("found front facing -1", "-1");
//            camID = -1;
//            front_facing_camera = false;
//        } else {
//            Log.d("Found front camera", "" + findFrontFacingCamera());
//            front_facing_camera = true;
//            mCamera = Camera.open(findFrontFacingCamera());
//            camID = findFrontFacingCamera();
//        }
//        startPreviewDetection();
//
//    }
    public void startPreviewDetection() {
        resizeResolutionKitKat(mCamera);
        try {
            mCamera.setPreviewDisplay(surface_holder);
        } catch (IOException exception) {
            stopCamera();
        }

        mCamera.startPreview();
        startFaceDetection(mCamera);
    }

    public void stopCamera() {
        if (mCamera != null) {
        	mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    
    }
    public void startCamera() {

    	if(mCamera == null) {
            Log.d("Camera is null", "null");
	    	if(front_facing_camera){
	    		if(camID == findFrontFacingCamera()) {
	    			mCamera = Camera.open(findFrontFacingCamera());
	    		} else if(camID == -1) {
	    			mCamera = Camera.open();
	    		}
	    	} else {
	    		mCamera = Camera.open();
	    	}


        } else {
            Log.d("Camera is not null", "not null");
        }
    }
    public static List<Camera.Size> cameraParameterList = null;
    static Integer cplIndex = 0;
    public void nextCameraSize(Camera c) {
        Camera.Parameters cp = c.getParameters();
        if(cameraParameterList == null) {
            cameraParameterList = cp.getSupportedPreviewSizes();
        }
        if( cplIndex++ == cameraParameterList.size()-1){  cplIndex = 0; }
        c.stopPreview();
        cp.setPreviewSize(cameraParameterList.get(cplIndex).width, cameraParameterList.get(cplIndex).height);
        c.setParameters(cp);
        c.startPreview();
    }
    public void switchCams() {
    	if(front_facing_camera){
    		stopCamera();
    		if(camID == -1) {
    			mCamera = Camera.open(findFrontFacingCamera());
    			camID=findFrontFacingCamera();
    		} else if(camID == findFrontFacingCamera()) {
    			mCamera = Camera.open();
    			camID = -1;
    		}
    	}
        startPreviewDetection();
    }




    public void draw(Canvas canvas) {
        // paint a background color
        //canvas.drawColor(Color.WHITE);
        
        // paint a rectangular shape that fill the surface.
        int border = 20;
        RectF r = new RectF(border, border, border-20, border-20);
        Paint paint = new Paint();    
        paint.setARGB(200, 135, 135, 135); //paint color GRAY+SEMY TRANSPARENT 
       // canvas.drawRect(r , paint );
        
        /*
         * i want to paint to circles, black and white. one of circles will
         * bounce, tile the button 'swap' pressed and then other circle begin bouncing.
         */

        //paint left circle(black)
        //paint.setColor(getResources().getColor(android.R.color.black));
        //canvas.drawCircle(canvas.getWidth()/4, canvas.getHeight()/2, 400, paint);
        
        //paint right circle(white)
        //paint.setColor(getResources().getColor(android.R.color.white));
        //canvas.drawCircle(canvas.getWidth()/4*3, canvas.getHeight()/2, 200, paint);
      }



    public static int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                Log.d("THIS", "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }


}
