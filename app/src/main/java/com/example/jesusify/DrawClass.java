package com.example.jesusify;

import java.io.IOException;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
public class DrawClass extends SurfaceView implements SurfaceHolder.Callback, Runnable{
	public static SurfaceView surface_view;
    CameraManager cm;
	Camera mCamera = null;
	private boolean locker=true;
	private Thread thread;
    SurfaceHolder.Callback sh_ob = null;
    static SurfaceHolder surface_holder        = null;
    SurfaceHolder.Callback sh_callback  = null;
    private static int camID = -1;
    static boolean front_facing_camera = false;
    static boolean camera_started = false;
	public DrawClass(Context c, SurfaceView surface_view) {
        super(c);

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            //handle NEW camera (We need a device compatible with lollipop
            cm = (CameraManager) c.getSystemService(Context.CAMERA_SERVICE);
            try{
                //   String[] cameraInfo = cm.getCameraIdList();
            } catch (Exception e) {

            }

        }


		//surface_view = (SurfaceView)findViewById(R.id.surface_viewff);
		DrawClass.surface_view = surface_view;
        if (surface_holder == null) {
        	surface_holder = surface_view.getHolder();
        }
        surface_holder.addCallback(this);
        surface_view.setWillNotDraw(false);
        ImageView button = (ImageView) findViewById(R.id.iv2);
        if(findFrontFacingCamera() < 0) {
        	button.setVisibility(View.INVISIBLE);
        }
		// TODO Auto-generated constructor stub
	}

	@Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	Log.d("surface activity", "DESTROYED");
    	stopCamera();

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
                   // draw(canvas);
                    if(surface_holder.getSurface().isValid()){
                        Canvas canvas = surface_holder.lockCanvas();


                        if (canvas != null) {
                            draw(canvas);

                            surface_holder.unlockCanvasAndPost(canvas);
                        }
                    }

                    Log.d("Found a Face", "!");

                }

            }
        };

        c.setFaceDetectionListener(listener);
        c.startFaceDetection();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    		Log.d("surface activity", "CREATED");

        	if(findFrontFacingCamera() < 0){
        		mCamera = Camera.open();

        		camID = -1;
        		front_facing_camera = false;
        	} else {
        		front_facing_camera = true;
        		mCamera = Camera.open(findFrontFacingCamera());
        		camID = findFrontFacingCamera();
            }
        resizeResolutionKitKat(mCamera);
        startFaceDetection(mCamera);


        
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    	Log.d("surface activity", "CHANGED");
    	if(!camera_started){
    		camera_started=true;
    		mCamera.startPreview();
    	//    Thread t = new Thread(this);
    	//    t.start();
    	}

    }	
    public void stopCamera() {
        if (mCamera != null) {
        	mCamera.stopPreview();
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
            resizeResolutionKitKat(mCamera);
            surface_view = (SurfaceView)findViewById(R.id.surface_viewff);
	        //addContentView(surface_view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	
	        if (surface_holder == null) {
	        	surface_holder = surface_view.getHolder();
	        }
	    	try {
	           	mCamera.setPreviewDisplay(surface_holder);  
	           } catch (IOException exception) { 
	           	Log.d("ERROR","THIS HAPPENED");
	           	stopCamera();
	           }

            mCamera.startPreview();
            startFaceDetection(mCamera);

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
        resizeResolutionKitKat(mCamera);

        surface_view = (SurfaceView)findViewById(R.id.surface_viewff);
        //addContentView(surface_view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        if (surface_holder == null) {
        	surface_holder = surface_view.getHolder();
        }
        surface_holder.addCallback(this);
    	try {
           	mCamera.setPreviewDisplay(surface_holder);  
           } catch (IOException exception) { 
           	Log.d("ERROR","THIS HAPPENED");
           	stopCamera();
           }

        mCamera.startPreview();
        startFaceDetection(mCamera);

    }
    private int findFrontFacingCamera() {
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

    @Override
    public void run() {
      //  while(locker){
//        //checks if the lockCanvas() method will be success,and if not, will check this statement again
       // if(!surface_holder.getSurface().isValid()){
       //   continue;
       // }
//        /** Start editing pixels in this surface.*/
      //  if(surface_holder==null) { Log.d("it's null", "null.");}
//        Log.d("it's null", "nulsssssl.");
//
//        //ALL PAINT-JOB MAKE IN draw(canvas); method.
//        draw(canvas);
//
//        // End of painting to canvas. system will paint with this canvas,to the surface.
           // surface_holder.unlockCanvasAndPost(canvas);
//      }

      //  }
    }
    @Override
    public void draw(Canvas canvas) {
        // paint a background color
        canvas.drawColor(Color.WHITE);
        
        // paint a rectangular shape that fill the surface.
        int border = 20;
        RectF r = new RectF(border, border, border-20, border-20);
        Paint paint = new Paint();    
        paint.setARGB(200, 135, 135, 135); //paint color GRAY+SEMY TRANSPARENT 
        canvas.drawRect(r , paint );
        
        /*
         * i want to paint to circles, black and white. one of circles will
         * bounce, tile the button 'swap' pressed and then other circle begin bouncing.
         */

        //paint left circle(black)
        paint.setColor(getResources().getColor(android.R.color.black));
        //canvas.drawCircle(canvas.getWidth()/4, canvas.getHeight()/2, 400, paint);
        
        //paint right circle(white)
        paint.setColor(getResources().getColor(android.R.color.white));
        //canvas.drawCircle(canvas.getWidth()/4*3, canvas.getHeight()/2, 200, paint);
      }
    
    private void resume() {
        //RESTART THREAD AND OPEN LOCKER FOR run();
        locker = true;
        thread = new Thread(this);
        thread.start();
      }

    
    private void pause() {
        //CLOSE LOCKER FOR run();
        locker = false;
        while(true){
          try {
            //WAIT UNTIL THREAD DIE, THEN EXIT WHILE LOOP AND RELEASE a thread
            thread.join();
          } catch (InterruptedException e) {e.printStackTrace();
          }
          break;
        }
        thread = null;
      }

}
