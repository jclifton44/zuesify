package com.example.jesusify;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

import com.example.jesusify.Secondary_Activity;

public class Gallery extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }


}
	