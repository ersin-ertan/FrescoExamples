package com.nullcognition.frescoexamples;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;


public class MainActivity extends ActionBarActivity {

   boolean okExample = true;

   @Override
   protected void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);

	  if(okExample){
		 new OkHttpPipeline(this); // can also use your own network fetcher for complete control on the networking layer
	  }
	  else{
		 Fresco.initialize(this); // initialization to be done before the content view is set
	  }

	  draweesInCode();

	  setContentView(R.layout.activity_main); // after Fresco is initialized

   }

   private void draweesInCode(){
	  // customizing the hierarchy, could be done via xml, this is done via code
	  List<Drawable> backgrounds;
	  List<Drawable> overlays;

	  GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
	  GenericDraweeHierarchy hierarchy = builder.setFadeDuration(1000)
												.setPlaceholderImage(getResources().getDrawable(R.drawable.smallimage), ScalingUtils.ScaleType.CENTER_CROP)
												.build();


	  GenericDraweeHierarchy getHierarchy = draweeView.getHierarchy(); // to get the h

	  ColorFilter pd = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
	  hierarchy.setActualImageColorFilter(pd);
	  RoundingParams roundingParams = hierarchy.getRoundingParams();
	  roundingParams.setCornersRadius(10);
	  hierarchy.setRoundingParams(roundingParams);

	  draweeView.setHierarchy(hierarchy); // do not call this method more than once on the same view, use setController/setImageURI to change the image

   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu){
	  getMenuInflater().inflate(R.menu.menu_main, menu);
	  return true;
   }

   SimpleDraweeView draweeView;

   @Override
   public boolean onOptionsItemSelected(MenuItem item){
	  int id = item.getItemId();
	  if(id == R.id.action_simpledrawee){
		 draweeView = (SimpleDraweeView)findViewById(R.id.my_image_view); // use simpledraweeview for setImageUri, and with
		 // imageview, else see ControllerBuilder

		 draweeView.setImageURI(Uri.parse("http://maps.google.com/mapfiles/kml/pal3/icon55.png"));
		 // does a fade in when the image is acquired, cool
		 return true;
	  }
	  else if(id == R.id.action_withxmlmods){
		 SimpleDraweeView sdv = (SimpleDraweeView)findViewById(R.id.my_xmlmod_view); // xml modifications
		 sdv.setAspectRatio(3.00f); // test this further, currently not doing anything visible, unless undetected...
		 sdv.setImageURI(Uri.parse("http://maps.google.com/mapfiles/kml/pal3/icon55.png"));
		 return true;
	  }

	  return super.onOptionsItemSelected(item);
   }
}

class OkHttpPipeline {

   OkHttpClient ok = new OkHttpClient();

   public OkHttpClient getOk(){return ok;}

   OkHttpPipeline(Context context){

	  // instead of using ImagePipelineConfig.newBuilder, use OkHttpImagePipelineConfigFactory instead
	  ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(context, ok) //.set... // other setters
		// setNetworkFetchProducer is already called for you
		.build();
	  Fresco.initialize(context, config);
   }
}
