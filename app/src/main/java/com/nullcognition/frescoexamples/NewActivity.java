package com.nullcognition.frescoexamples;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;

// using the ControllerBuilder

public class NewActivity extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState){

	  super.onCreate(savedInstanceState);
	  Fresco.initialize(this);

	  setContentView(R.layout.activity_new);

	  initControllerListener();
   }

   SimpleDraweeView draweeView;

   private void initControllerListener(){

	  // lisnten to execute actions when image arrives, or on network failure
	  ControllerListener listener = new BaseControllerListener<ImageInfo>() {

		 @Override
		 public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim){

			if(imageInfo == null){
			   return;
			}
			QualityInfo qualityInfo = imageInfo.getQualityInfo();
			FLog.d("Final image received! " + "Size %d x %d", "Quality level %d, good enough: %s, full quality: %s", imageInfo.getWidth(), imageInfo.getHeight(), qualityInfo.getQuality(), qualityInfo.isOfGoodEnoughQuality(), qualityInfo.isOfFullQuality());
		 }

		 @Override
		 public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo){

			FLog.d("Intermediate image received", "done");
		 }

		 @Override
		 public void onFailure(String id, Throwable throwable){

			FLog.e(getClass(), throwable, "Error loading %s", id);
		 }
	  };

	  draweeView = (SimpleDraweeView)findViewById(R.id.controlledView); // xml modifications

	  DraweeController controller = Fresco.newDraweeControllerBuilder()
										  .setUri(Uri.parse("http://maps.google.com/mapfiles/kml/pal3/icon55.png"))
										  .setTapToRetryEnabled(true)
										  .setOldController(draweeView.getController()) // prevents unneeded memory allocation
										  .setControllerListener(listener)
										  .build();

	  draweeView.setController(controller);

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu){

	  getMenuInflater().inflate(R.menu.menu_new, menu);
	  return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item){

	  int id = item.getItemId();
	  if(id == R.id.action_settings){

		 return true;
	  }

	  return super.onOptionsItemSelected(item);
   }
}
