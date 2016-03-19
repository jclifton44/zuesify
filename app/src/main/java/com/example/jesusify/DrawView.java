package com.example.jesusify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.opengl.GLES20;


/**
 * Created by Jeremy Clifton on 2/11/2016.
 */
public class DrawView extends GLSurfaceView {
    static int camOnClose = -1;
    public static CameraClass customCamera;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
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
            customCamera = CameraClass.getCustomCameraInstance(holder, Secondary_Activity.SA.getApplicationContext(), camOnClose);
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(CameraClass.staticRect, new Paint());
        Log.d("on draw", "in on draw");
    }


}
