package com.example.jesusify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.FileOutputStream;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Face;
import java.util.TreeMap;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.hardware.camera2.*;
import android.os.Environment;
public class CameraClass {
    static Canvas mCanvas;
	private Camera mCamera = null;
    public static ImageView trackingImage;
    private boolean locker=true;
    private static int cameraRotation = 0;
    private static int cameraOrientation = 0;
	private Thread thread;
    static Secondary_Activity mainActivity = null;
    private SurfaceHolder surface_holder = null;
    SurfaceHolder.Callback sh_callback  = null;
    static ArrayList<ImageView> masks = new ArrayList<ImageView>();
    private static int camID = -1;
    private static Context cameraContext = null;
    private static int maskID = 0;
    private static boolean front_facing_camera = false;
    static boolean camera_started = false;
    public static Integer MOTIONTHRESHOLD = 100;
    static  Map<Integer, Point> maskCoordinates = new TreeMap();
    public static Camera.ShutterCallback shutter;
    static Camera.PictureCallback picture_raw;
    static Camera.PictureCallback picture_postview;
    static Camera.PictureCallback picture_jpeg;
    static String storagePath = Environment.getExternalStorageDirectory().toString();


    public CameraClass(Camera c, SurfaceHolder sh) {
        mCamera  = c;
        surface_holder = sh;
//        ImageView button = (ImageView) findViewById(R.id.iv2);
//        if(findFrontFacingCamera() < 0) {
//        	button.setVisibility(View.INVISIBLE);
//        }
		// TODO Auto-generated constructor stub
	}
    public void takePhoto() {
        mCamera.takePicture(shutter,picture_raw,picture_postview,picture_jpeg);
    }
    public static CameraClass getCustomCameraInstance(SurfaceHolder sh, Context c, int camOnClose) {
        cameraContext = c;

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
            camID = -1;
            front_facing_camera = false;
        } else {
            if(camOnClose == findBackFacingCamera()) {
                mcam = Camera.open(findBackFacingCamera());
                camID = findBackFacingCamera();
                front_facing_camera = false;
            } else {
                mcam = Camera.open(findFrontFacingCamera());
                camID = findFrontFacingCamera();
                front_facing_camera = true;

            }
        }
        CameraClass returnC = new CameraClass(mcam,sh);
        returnC.startPreviewDetection();
        return returnC;
    }

    public ImageView resizeImage(ImageView image, Rect face) {
        return null;
    }
    public void resizeResolutionKitKat(Camera c) {
        Camera.Parameters cp = c.getParameters();
        List<Camera.Size> cameraParameterList = cp.getSupportedPreviewSizes();
        cp.setPreviewSize(cameraParameterList.get(0).width, cameraParameterList.get(0).height);
        cp.setPictureSize(cameraParameterList.get(0).width, cameraParameterList.get(0).height);
        c.setParameters(cp);
    }
    public void rotateCamera(int orientation, int rotation) {
        Log.d("rotation", rotation + "");

    if(mCamera != null) {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if(rotation == 3) {
                    mCamera.setDisplayOrientation(180);
                } else {
                    mCamera.setDisplayOrientation(0);

                }
            }
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                mCamera.setDisplayOrientation(90);
            }
        }
    }
    public void startFaceDetection(Camera c) {
        FaceDetectionListener listener = new FaceDetectionListener() {
            Map<Integer, ImageView> newMasks = new TreeMap<>();
            @Override
            public void onFaceDetection(Face[] faces, Camera c) {

                //Log.d("found faces:", faces.length + "");
                ImageView view;
                if(faces.length > 0) {


                    Integer xValue = (int)( (double)((faces[0].rect.centerX() + 1000) * (double)((double)mainActivity.getWindowManager().getDefaultDisplay().getHeight() / (double)2000)));
                    Integer yValue = (int) ( (double) ((faces[0].rect.centerY() + 1000) * (double)((double)mainActivity.getWindowManager().getDefaultDisplay().getWidth() / (double)2000)));
                    Log.d("" + xValue, "X");
                    Log.d("" + yValue, "Y");
                }
                while(0 < masks.size()) {
                    view = masks.get( masks.size() - 1 );
                    view.setVisibility(View.INVISIBLE);
                    masks.remove(view);
                }
                for(Integer i = 0; i < faces.length; i++) {
                    int ratio = (int) (100f * (float) faces[i].rect.height() / 1500f  );
                    if (masks.size() < i+1) {
                        masks.add(view = mainActivity.getImageInstance(ratio));
                        view.setVisibility(View.VISIBLE);

                    }
                    view = masks.get(i);
                    Integer xValue;
                    Integer yValue;
                    xValue = -view.getLayoutParams().height / 2 + (int) -((double) ((faces[i].rect.exactCenterY() - 1000) * (double) ((double) mainActivity.getWindowManager().getDefaultDisplay().getWidth() / (double) 2000)));
                    yValue = -view.getLayoutParams().width / 2 + (int)- ((double) ((faces[i].rect.exactCenterX() + 1000) * (double) ((double) mainActivity.getWindowManager().getDefaultDisplay().getHeight() / (double) 2000))) + mainActivity.getWindowManager().getDefaultDisplay().getHeight();



                    view.setVisibility(View.VISIBLE);
                    view.setX(xValue);
                    view.setY(yValue);
                }
            }

        };

        c.setFaceDetectionListener(listener);

        c.startFaceDetection();
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
        shutter = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        };
        picture_raw = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera c) {

            }
        };
        picture_postview = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera c) {

            }
        };
        picture_jpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera c) {
                Log.d("Camera","photo");
                FileOutputStream fout = null;
                File file = new File(storagePath, "FacePicture.jpg");
                Log.d(storagePath.toString(), "Storage String");
                Bitmap map = Bitmap.createBitmap(Secondary_Activity.cameraSurface.getWidth(), Secondary_Activity.cameraSurface.getHeight(), Bitmap.Config.ARGB_8888);
                Bitmap sticker = BitmapFactory.decodeResource(cameraContext.getResources(), Secondary_Activity.sticker);

                Matrix m = new Matrix();
                m.postRotate(270);
                try {
                    fout = new FileOutputStream(file);
                    Bitmap photo = BitmapFactory.decodeByteArray(data, 0, data.length);
                    photo = Bitmap.createScaledBitmap(photo,photo.getWidth()/2, photo.getHeight() / 2, false);
                    Bitmap rotatedPhoto = Bitmap.createBitmap(photo,0,0,photo.getWidth(), photo.getHeight(), m, true);

                    Canvas canvas = new Canvas(map);
                    canvas.drawBitmap(rotatedPhoto, 0,0, null);
                    canvas.drawBitmap(sticker, 500, 500, null);
                    map.compress(Bitmap.CompressFormat.PNG, 100, fout);
                    fout.close();
                } catch (Exception e) {
                    //exception
                }
            }
        };
        resizeResolutionKitKat(mCamera);
        mCamera.setDisplayOrientation(90);
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
	    	if(front_facing_camera){
	    		if(camID == findFrontFacingCamera()) {
	    			mCamera = Camera.open(findFrontFacingCamera());
	    		} else if(camID == -1) {
	    			mCamera = Camera.open();
	    		}
	    	} else {
	    		mCamera = Camera.open();
	    	}


        }
    }
    public int getActiveCamera() {
        return camID;
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
    		if(camID == findBackFacingCamera()) {
    			mCamera = Camera.open(findFrontFacingCamera());
    			camID=findFrontFacingCamera();
    		} else if(camID == findFrontFacingCamera()) {
    			mCamera = Camera.open();
    			camID = findBackFacingCamera();
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
             canvas.drawRect(r, paint);
        
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
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }
    public static int findBackFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }



}
