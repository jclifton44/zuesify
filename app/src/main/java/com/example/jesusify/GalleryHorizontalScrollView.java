package com.example.jesusify;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by Jeremy Clifton on 3/13/2016.
 */
public class GalleryHorizontalScrollView extends HorizontalScrollView {
    GalleryHorizontalScrollView(Context c) {
        super(c);
    }
    GalleryHorizontalScrollView(Context c, AttributeSet attrs){
        super(c, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //handle screen events
        return true;
    }
}
