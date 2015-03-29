package com.nullcognition.frescoexamples;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;


public class MainActivity extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);

	  Fresco.initialize(this); // initialization to be done before the content view is set

	  setContentView(R.layout.activity_main); // after Fresco is initialized

   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu){
	  getMenuInflater().inflate(R.menu.menu_main, menu);
	  return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item){
	  int id = item.getItemId();
	  if(id == R.id.action_simpledrawee){
		 SimpleDraweeView draweeView = (SimpleDraweeView)findViewById(R.id.my_image_view); // use simpledraweeview for setImageUri, and with
		 // imageview, else see ControllerBuilder

		 draweeView.setImageURI(Uri.parse("http://maps.google.com/mapfiles/kml/pal3/icon55.png"));
		 // does a fade in when the image is acquired, cool
		 return true;
	  }
	  else if(id == R.id.action_withxmlmods){
		 SimpleDraweeView sdv = (SimpleDraweeView)findViewById(R.id.my_xmlmod_view); // xml modifications
		 sdv.setImageURI(Uri.parse("http://maps.google.com/mapfiles/kml/pal3/icon55.png"));
		 return true;
	  }
	  return super.onOptionsItemSelected(item);
   }
}
