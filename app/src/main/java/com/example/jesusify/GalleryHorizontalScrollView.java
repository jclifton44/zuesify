package com.example.jesusify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jeremy Clifton on 3/13/2016.
 */
public class GalleryHorizontalScrollView extends HorizontalScrollView {
    public Integer ImageIndex = 0;
    public Integer ImageListIndex = 0;

    public Integer ImageWidth = -1;
    public Integer imageCount = -1;
    public Bitmap image1BM = null;
    public Bitmap image2BM = null;
    public Bitmap image3BM = null;
    public static ImageView image1;
    public static ImageView image2;
    public static ImageView image3;
    public static ArrayList<String> galleryList = new ArrayList<String>();
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
        Log.d(GalleryHorizontalScrollView.galleryList.size() + "", "Size");
        GalleryHorizontalScrollView.image1 = (ImageView) findViewById(R.id.image1);
        GalleryHorizontalScrollView.image2 = (ImageView) findViewById(R.id.image2);
        GalleryHorizontalScrollView.image3 = (ImageView) findViewById(R.id.image3);
        Bitmap image1BM = null;
        Bitmap image2BM = null;
        Bitmap image3BM = null;
        imageCount = galleryList.size();
        if(galleryList.size() == 0) {
            //set display to 'no photos'

        }
        if(galleryList.size() == 1) {
            image1BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(0));
            GalleryHorizontalScrollView.image1.setImageBitmap(image1BM);
            ((ViewGroup)GalleryHorizontalScrollView.image1.getParent()).removeView(image2);
            ((ViewGroup)GalleryHorizontalScrollView.image1.getParent()).removeView(image3);
            //take two images off
        }
        if(galleryList.size() == 2) {
            image1BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(0));
            image2BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(1));
            GalleryHorizontalScrollView.image1.setImageBitmap(image1BM);
            GalleryHorizontalScrollView.image2.setImageBitmap(image2BM);
            ((ViewGroup)GalleryHorizontalScrollView.image1.getParent()).removeView(image3);

        }
        if (galleryList.size() >= 3) {
            image1BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(0));
            image2BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(1));
            image3BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(2));
            GalleryHorizontalScrollView.image1.setImageBitmap(image1BM);
            GalleryHorizontalScrollView.image2.setImageBitmap(image2BM);
            GalleryHorizontalScrollView.image3.setImageBitmap(image3BM);

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //handle screen events
        Log.d("touch Event", "touch");
        Log.d(ev.getAction() + "", " ACTION");
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d("down", "down");
                if(getChildCount() == 0) {
                    return false;
                }
                startLocation = ev.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d("MOTION_", ev.getAction() + "MOVE");
                scrollTo((ImageWidth * ImageIndex) + Math.round(startLocation - ev.getX()), 0);
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.d("ACTION", ev.getAction() + " UP");
                Log.d("" + Math.abs(startLocation - ev.getX()), ImageWidth + "");
                if(Math.abs(startLocation - ev.getX()) > ImageWidth / 2) {
                    Log.d("greater than", "than");
                    if (startLocation < ev.getX()) {
                        Log.d("greater than", "greather");
                        if (ImageListIndex != 0) {
                            ImageListIndex--;
                        }
                        if (ImageIndex != 0) {
                            ImageIndex--;
                        }
                        smoothScrollTo(ImageIndex*ImageWidth,0);

                        //animate to left
                    } else {
                        Log.d("greater than", "less");
                        if (ImageListIndex != imageCount) {
                            ImageListIndex++;
                        }
                        smoothScrollTo(ImageIndex*ImageWidth,0);
                        //animate to right
                        if (ImageIndex != 2) {
                            ImageIndex++;
                        }
                        if (ImageIndex == 2) {
                            ImageIndex = 1;
                            image1.setImageBitmap(image2BM);
                            image1BM = image2BM;
                            image2.setImageBitmap(image3BM);
                            image2BM = image3BM;
                            image3BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(ImageListIndex));
                            //scrollTo(ImageIndex * ImageWidth, 0);
                            image3.setImageBitmap(image3BM);
                        }
                    }
                }

                break;
            }
        }
        return true;
    }
}
