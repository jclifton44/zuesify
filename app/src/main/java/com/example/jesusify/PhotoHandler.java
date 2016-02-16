package com.example.jesusify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jesusify.Secondary_Activity;

public class PhotoHandler extends Activity {
  @Override

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //ActionBar actionBar = getActionBar();
    //actionBar.hide();
    Bundle filePath = getIntent().getExtras();

    setContentView(R.layout.activity_preview);
    ImageView imagePreview = (ImageView) findViewById(R.id.imagePreview);
    Bitmap bm = BitmapFactory.decodeFile((String)filePath.get("path"));
    BitmapDrawable picture = new BitmapDrawable(getResources(), bm);
    imagePreview.setBackground(picture);




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
	