package com.example.jesusify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jeremy Clifton on 3/13/2016.
 */
public class GalleryHorizontalScrollView extends HorizontalScrollView {
    public Integer ImageIndex = 0;
    public static Integer ImageListIndex = 0;

    public Integer ImageWidth = -1;
    public Integer imageCount = -1;
    public Bitmap image1BM = null;
    public Bitmap image2BM = null;
    public Bitmap image3BM = null;
    public static ImageView image1;
    public static ImageView image2;
    public static ImageView image3;
    public static ArrayList<String> galleryList = new ArrayList<String>();
    boolean InDrag = false;
    public GalleryHorizontalScrollView(Context context) {
        super(context);
    }

    public GalleryHorizontalScrollView(Context context, AttributeSet attrs){
        super(context, attrs);
        Log.d("in", "out");
    }
    public GalleryHorizontalScrollView(Context c, AttributeSet attrs, int defStyleAttr) {
        super(c,attrs,defStyleAttr);
    }

    Float startLocation = -1f;
    public String getActiveImagePath() {
        return Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(ImageListIndex);
    }
    public void removeImage() {
        galleryList.remove(ImageListIndex);
        LinearLayout LinearPhotoFrame = (LinearLayout) findViewById(R.id.photoFrame);
        ImageView deletedItem = (ImageView)findViewById(ImageListIndex);
        if(deletedItem == null) {
            Log.d("deleted Item", "is null");
        }
        LinearPhotoFrame.removeView(deletedItem);
        if(ImageListIndex > 0) {
            ImageListIndex--;
        }
        smoothScrollTo(ImageWidth * ImageListIndex, 0);
        imageCount--;
    }
    public void loadImages() {
        File container = new File(Secondary_Activity.storagePath + "/DCIM/Camera/");
        galleryList = new ArrayList<String>();
        Log.d("checking", "files");
        for(String s: container.list()){
            Log.d(s,"file");
            if(s.contains("zeusify_") || s.contains("zeus")) {
                GalleryHorizontalScrollView.galleryList.add(s);
                Log.d(s, "File");
            }
        }
        Collections.reverse(galleryList);
        Log.d(GalleryHorizontalScrollView.galleryList.size() + "", "Size");
        LinearLayout photoFrame = (LinearLayout) findViewById(R.id.photoFrame);
        Integer idContainer = -1;
        for( int i = 0; i < galleryList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setId(idContainer = i);
            Bitmap image = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(i));
            photoFrame.addView(imageView);
            imageView.setImageBitmap(image);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //handle screen events
        Log.d("touch Event", "touch");
        Log.d(ev.getAction() + "", " ACTION");
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                InDrag = true;
                Log.d("down", "down");
                if(getChildCount() == 0) {
                    return false;
                }
                startLocation = ev.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d("MOTION_", ev.getAction() + "MOVE");
                //if((ImageWidth * ImageListIndex) + Math.round(startLocation - ev.getX()) <= ImageWidth * ImageListIndex){
                    scrollTo((ImageWidth * ImageListIndex) + Math.round(startLocation - ev.getX()), 0);
                //}
                break;
            }
            case MotionEvent.ACTION_UP: {
                InDrag = false;
                Log.d("ACTION", ev.getAction() + " UP");
                Log.d("" + Math.abs(startLocation - ev.getX()), ImageWidth + "");
                if(Math.abs(startLocation - ev.getX()) > ImageWidth / 3) {
                    Log.d("greater than", "than");
                    if (startLocation < ev.getX()) {
                        Log.d("greater than", "greather");
                        if (ImageListIndex != 0) {
                            ImageListIndex--;
                        }
                    } else {
                        Log.d("greater than", "less");
                        if (ImageListIndex != galleryList.size() - 1) {
                            ImageListIndex++;
                        }

                    }

                }
                smoothScrollTo(ImageListIndex * ImageWidth, 0);


                break;
            }
        }
        return true;
    }


}
