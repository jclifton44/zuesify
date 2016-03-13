package com.example.jesusify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnDragListener;

import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.example.jesusify.Secondary_Activity;

import java.io.File;
import java.util.ArrayList;

public class Gallery extends Activity {
    private Integer imageCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.galleryPhotoView);
        int scrollViewWidth = hsv.getWidth();
        loadImages();
        OnScrollListener flipper = new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view ==, int scrollState) {
                if(scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_IDLE) {
                    //if(lastScrollPosition == -1) {
                    //    lastScrollPosition = super.hsv.scroll
                    //}
                    //if in idle, scroll to nearest image
                    //if in fling find direction of fling
                    //      animate to nearest imge in that particular direction
                } else if(scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    //if scrollstate in touch
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        }
        if(imageCount == 0 ) {

        } else if(imageCount == 1) {
            int imageDistance1 = 0;
            int imageDistance2 = scrollViewWidth;
            int imageDistance3 = scrollViewWidth;

        } else if (imageCount == 2) {
            int imageDistance1 = 0;
            int imageDistance2 = scrollViewWidth/2;
            int imageDistance3 = scrollViewWidth;

        } else if (imageCount >= 3) {
            int imageDistance1 = 0;
            int imageDistance2 = scrollViewWidth/3;
            int imageDistance3 = 2*scrollViewWidth/3;
        }



        //image1  0
        //image2 w/3
        //image3 2*w/3
        /*
        populate list of all images in a list for zuesify
        set image1 to .get(0), image2 to .get(1) image3 to .get(2)
        slide to image closest
        if sliding from image2 to image3 AND image3 doesn't have the last image
        slide to image closest, set image2 to what image3 is
        set scroll to w/3
        update image3 for nextImage

         */

    }

    public void loadImages() {
        File container = new File(Secondary_Activity.storagePath + "/DCIM/Camera/");
        ArrayList<String> galleryList = new ArrayList<String>();
        Log.d("checking", "files");
        for(String s: container.list()){
            Log.d(s,"file");
            if(s.contains("zeusify_") || s.contains("zeus")) {
                galleryList.add(s);
                Log.d(s, "File");
            }
        }
        Log.d(galleryList.size() + "", "Size");
        ImageView image1 = (ImageView) findViewById(R.id.image1);
        ImageView image2 = (ImageView) findViewById(R.id.image2);
        ImageView image3 = (ImageView) findViewById(R.id.image3);
        Bitmap image1BM = null;
        Bitmap image2BM = null;
        Bitmap image3BM = null;
        imageCount = galleryList.size();
        if(galleryList.size() == 0) {
            //set display to 'no photos'

        }
        if(galleryList.size() == 1) {
            image1BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(0));
            image1.setImageBitmap(image1BM);
            ((ViewGroup)image1.getParent()).removeView(image2);
            ((ViewGroup)image1.getParent()).removeView(image3);
            //take two images off
        }
        if(galleryList.size() == 2) {
            image1BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(0));
            image2BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(1));
            image1.setImageBitmap(image1BM);
            image2.setImageBitmap(image2BM);
            ((ViewGroup)image1.getParent()).removeView(image3);

        }
        if (galleryList.size() >= 3) {
            image1BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(0));
            image2BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(1));
            image3BM = BitmapFactory.decodeFile(Secondary_Activity.storagePath + "/DCIM/Camera/" + galleryList.get(2));
            image1.setImageBitmap(image1BM);
            image2.setImageBitmap(image2BM);
            image3.setImageBitmap(image3BM);

        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }


}
	