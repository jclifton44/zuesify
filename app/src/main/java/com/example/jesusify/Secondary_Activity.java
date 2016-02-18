package com.example.jesusify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.HorizontalScrollView;
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
import android.opengl.GLES20;

public class Secondary_Activity extends Activity  {
    public static int sticker = R.drawable.doge_sticker;
    public static Boolean fileSaved = false;
    Camera mCamera = null;
    static int act = -1;
    public static HorizontalScrollView faceSelect = null;
    public static ImageView takePhoto;
    public static ImageView switchCams;
    public static ImageView shareIcon;
    public static ImageView saveIcon;
    public static ImageView backIcon;

    static boolean front_facing_camera = false;
    private static Context context;
    private static Resources resources;
    public static RelativeLayout cameraScreen;
    public static DrawView cameraSurface;

    public static SurfaceHolder cameraHolder;

    public static Secondary_Activity SA = null;
    public static CameraClass customCamera;
    static Integer cameraInt = 0;
    static String storagePath = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SA = this;
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
        image.setImageDrawable(getResources().getDrawable(sticker));
        image.setRotation(270);
        image.setId(cameraInt++);
        image.setX(1180);
        image.setY(100);
        cameraScreen.addView(image);


        //surface_view = new SurfaceView(getApplicationContext());
        cameraSurface = (DrawView)findViewById(R.id.surface_viewf1);

        cameraHolder = cameraSurface.getHolder();
        //cameraHolder.addCallback(new SurfaceHolder.Callback() {


        //});

        context = getApplicationContext();
        resources = getResources();
        switchCams = (ImageView) findViewById(R.id.iv2);
        shareIcon = (ImageView) findViewById(R.id.iv3);
        saveIcon = (ImageView) findViewById(R.id.iv4);
        backIcon = (ImageView) findViewById(R.id.iv5);

        faceSelect = (HorizontalScrollView)findViewById(R.id.faceSelect);
        shareIcon.setVisibility(View.INVISIBLE);
        saveIcon.setVisibility(View.INVISIBLE);
        backIcon.setVisibility(View.INVISIBLE);



        ImageView selector1 = (ImageView) findViewById(R.id.s1_doge);
        ImageView selector2 = (ImageView) findViewById(R.id.s2_hera);
        ImageView selector3 = (ImageView) findViewById(R.id.s3_zeus);
        ImageView selector4 = (ImageView) findViewById(R.id.s4_grumpycat);

        selector1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sticker = R.drawable.doge_sticker;
            }


        });
        selector2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sticker = R.drawable.hera_sticker;

            }


        });
        selector3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sticker = R.drawable.zeus_sticker;

            }


        });
        selector4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sticker = R.drawable.grumpycat_sticker;
            }


        });
        switchCams.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //sh_callback.surfaceDestroyed(surface_holder);
                DrawView.customCamera.switchCams();
                //Secondary_Activity.cameraSurface.nextCameraSize(Secondary_Activity.cameraSurface.mCamera);
            }


        });


        shareIcon.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            public void onClick(View v) {

            }
        });
        saveIcon.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            public void onClick(View v) {
                if(fileSaved) {
                    Log.d("filesaved...","saved");
                    fileSaved = false;

                    //open temp file

                    //save in best spot
                }
            }
        });
        backIcon.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            public void onClick(View v) {
                //delete tmp.png
                //deleteFile(CameraClass.storagePath + "/TMP.png");
                cameraOn();
                DrawView.customCamera.restartPreviewDisplay();
            }
        });


        takePhoto = (ImageView) findViewById(R.id.iv1);
        takePhoto.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("unused")
            public void onClick(View v) {
                DrawView.customCamera.takePhoto();
                Secondary_Activity.faceSelect.setVisibility(View.INVISIBLE);
                Secondary_Activity.takePhoto.setVisibility(View.INVISIBLE);
                Secondary_Activity.switchCams.setVisibility(View.INVISIBLE);
                Secondary_Activity.shareIcon.setVisibility(View.VISIBLE);
                Secondary_Activity.saveIcon.setVisibility(View.VISIBLE);
                Secondary_Activity.backIcon.setVisibility(View.VISIBLE);
                Secondary_Activity.saveIcon.setImageAlpha(100);


                //if(isValid)
//                Bitmap map = Bitmap.createBitmap(cameraSurface.getWidth(), cameraSurface.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(map);
//                cameraSurface.draw(canvas);
//                FileOutputStream fout = null;
//                File file = new File(storagePath, "FacePicture.png");
//                Log.d(storagePath.toString(), "Storage String");
//                try {
//                    fout = new FileOutputStream(file);
//                    map.compress(Bitmap.CompressFormat.PNG,100,fout);
//                    fout.close();
//                } catch (Exception e) {
//                    //exception
//                }
//                Canvas canvas = cameraHolder.lockCanvas(null);
//                cameraSurface.onDraw(canvas);
//                cameraHolder.unlockCanvasAndPost(canvas);
            }
        });

    }
    public void cameraOn() {
        Secondary_Activity.faceSelect.setVisibility(View.VISIBLE);
        Secondary_Activity.takePhoto.setVisibility(View.VISIBLE);
        Secondary_Activity.switchCams.setVisibility(View.VISIBLE);
        Secondary_Activity.shareIcon.setVisibility(View.INVISIBLE);
        Secondary_Activity.saveIcon.setVisibility(View.INVISIBLE);
        Secondary_Activity.backIcon.setVisibility(View.INVISIBLE);

    }
    public ImageView getImageInstance(int percent) {

        ImageView image;
        image = new ImageView(getApplicationContext());
        //Depends on what button has been pressed
        image.setImageDrawable(getResources().getDrawable(sticker));
        image.setRotation(0);
        image.setId(cameraInt++);
        Integer w = getResources().getDrawable(sticker).getMinimumWidth();
        Integer h = getResources().getDrawable(sticker).getMinimumHeight();

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