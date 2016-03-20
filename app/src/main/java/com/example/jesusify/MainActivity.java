package com.example.jesusify;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

import com.example.jesusify.Secondary_Activity;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends Activity {
static MainActivity MA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!OpenCVLoader.initDebug()) {
			// Handle initialization error
		}
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();
		
		setContentView(R.layout.activity_main);
		MA = this;
		
		new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
        		Intent mi = new Intent(MainActivity.this, Secondary_Activity.class);
        		startActivity(mi);
        		Log.d("Starting second Ac","Yep");
        		MainActivity.MA.finish();
        		
            }
        }, 2000);

		
	}
	
	
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
	