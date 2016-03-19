package com.example.jesusify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    ImageView share;
    ImageView delete;
    public static GalleryHorizontalScrollView hsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        hsv = (GalleryHorizontalScrollView) findViewById(R.id.galleryPhotoView);
        int scrollViewWidth = hsv.getWidth();
        share = (ImageView)findViewById(R.id.shareButton);
        delete = (ImageView)findViewById(R.id.deleteButton);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imageFile = new File(Gallery.hsv.getActiveImagePath());

                Uri uri = Uri.fromFile(imageFile);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(shareIntent, "Share Photo"));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imageFile = new File(Gallery.hsv.getActiveImagePath());
                imageFile.delete();
                hsv.removeImage();
            }
        });

        hsv.ImageWidth = getWindowManager().getDefaultDisplay().getWidth();

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
        hsv.loadImages();
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }


}
	