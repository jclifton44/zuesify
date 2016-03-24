package com.example.jesusify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.opengl.GLES20;
import android.widget.ImageView;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by Jeremy Clifton on 2/11/2016.
 */
public class DrawView extends GLSurfaceView implements Camera.PreviewCallback {
    static int camOnClose = -1;
    CascadeClassifier eyeClassifier = null;
    CascadeClassifier faceClassifier = null;

    public static CameraClass customCamera;
    public static String haar_frontal = "";
    public static String haar_eyes = "";
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        loadHaarFiles();


    }
    public void loadHaarFiles() {
        InputStream in = getResources().openRawResource(R.raw.haarcascade_frontalface_default);
        File cascadeDir = Secondary_Activity.SA.getDir("cascade", Context.MODE_PRIVATE);
        File cascadeFile = new File(cascadeDir,"haarcascade_frontalface_default.xml");
        FileOutputStream out;
        try {
            out = new FileOutputStream(cascadeFile);
            byte[] buffer= new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            out.close();
        } catch(IOException e) {
        } finally {

        }

        haar_frontal = cascadeFile.getAbsolutePath();
        in = getResources().openRawResource(R.raw.haarcascade_eye);
        cascadeDir = Secondary_Activity.SA.getDir("cascade", Context.MODE_PRIVATE);
        cascadeFile = new File(cascadeDir,"haarcascade_eyes.xml");

        try {
            out = new FileOutputStream(cascadeFile);
            byte[] buffer= new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            out.close();
        } catch(IOException e) {
        } finally {

        }
        haar_eyes = cascadeFile.getAbsolutePath();


    }
    public static boolean testVar = false;
    @Override
    public void onPreviewFrame(byte[] arr, Camera c) {
        Log.d("onPreviewFrame", "Inbuffer");
        if(eyeClassifier == null) {
            eyeClassifier = new CascadeClassifier(haar_eyes);
        }
        if(faceClassifier == null) {
            faceClassifier = new CascadeClassifier(haar_frontal);
        }
        //if(faceClassifier.load("file:///android_asset/frontalface_default.xml")){
        //   Log.d("file", "loaded");
        //} else {
        //    Log.d("file", "Not loaded!");
        //}
        Mat mat = new Mat(getHeight(), getWidth(), CvType.CV_8UC1);
        Mat mat_rgb = new Mat();
        Mat mat_gray = new Mat();
        mat.put(0,0,arr);

        Imgproc.cvtColor(mat, mat_rgb, Imgproc.COLOR_YUV420p2RGB);
        Imgproc.cvtColor(mat_rgb, mat_gray, Imgproc.COLOR_RGB2GRAY);


        MatOfRect faces = new MatOfRect();
        faceClassifier.detectMultiScale(mat_gray, faces, 1.1, 2, 2, new Size(0,0), new Size());

        Log.d("Face detector results:", faces.toArray().length + " faces found");
    }
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
            customCamera = CameraClass.getCustomCameraInstance(this, holder, Secondary_Activity.SA.getApplicationContext(), camOnClose);

        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        //get image bitmap
        //run opencv to find faces (eyes, mouths too)
        //draw appropriate image onto faces
        canvas.drawRect(CameraClass.staticRect, new Paint());
        //Log.d("on draw", "in on draw");
    }


}
