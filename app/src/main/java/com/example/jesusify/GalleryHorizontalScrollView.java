package com.example.jesusify;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/**
 * Created by Jeremy Clifton on 3/13/2016.
 */
public class GalleryHorizontalScrollView extends HorizontalScrollView {
    public Integer ImageIndex = 0;
    public Integer ImageWidth = -1;
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
                    if(startLocation < ev.getX()) {
                        Log.d("greater than", "greather");

                        if(ImageIndex != 0) {
                            ImageIndex--;
                        }
                        smoothScrollTo(ImageIndex * ImageWidth,0);

                        //animate to left
                    } else {
                        Log.d("greater than", "less");

                        //animate to right
                        if(ImageIndex != 2) {
                            ImageIndex++;
                        }
                        smoothScrollTo(ImageIndex * ImageWidth,0);

                    }

                    //find direction
                    //snap to that image
                }
                break;
            }
        }
        return true;
    }
}
