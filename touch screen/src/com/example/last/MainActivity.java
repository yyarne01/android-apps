package com.example.last;

import android.media.MediaPlayer;
import android.os.Bundle;





import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
 
public class MainActivity extends Activity implements OnClickListener{
        private View selected_item = null;
        private int offset_x = 0;
        private int offset_y = 0;
		private int windowwidth;
		private int windowheight;
		  private ImageView tv1;
		  private ImageView tv2;
		private   MediaPlayer mPlayer1,mPlayer2;
	
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        windowwidth=getWindowManager().getDefaultDisplay().getWidth();
        windowheight=getWindowManager().getDefaultDisplay().getHeight();

        tv1 = (ImageView)findViewById(R.id.imageView1);
        tv2 = (ImageView)findViewById(R.id.imageView2);
      
   	 mPlayer1 = MediaPlayer.create(getBaseContext(), com.example.last.R.raw.msg);
	 mPlayer2 = MediaPlayer.create(getBaseContext(), com.example.last.R.raw.alarm);
	 
		Button r=(Button)findViewById(R.id.reset);
		r.setOnClickListener(this);

 
       tv1.setOnTouchListener(new View.OnTouchListener() {    
    	   
    	
          

			@Override
            public boolean onTouch(View v, MotionEvent event) {
               LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) tv1.getLayoutParams();
          
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    	   v.setBackgroundResource(R.drawable.border);
                    	   mPlayer1.start();
                    	
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();
                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight) {
                            y_cord = windowheight;
                        }
                        layoutParams1.leftMargin = x_cord - 25;
                        layoutParams1.topMargin = y_cord - 75;
                        tv1.setLayoutParams(layoutParams1);
                     
                        break;
                   
                
                    case MotionEvent.ACTION_UP:
                    	   //stopPlaying();
                        v.setBackgroundResource(R.drawable.red);
                
                        mPlayer2.start();
                    break;

             
                }
                return true;        
                
		    }
		});
       
      
        
    

       final float scale = getResources().getDisplayMetrics().density;
       // Convert the dps to pixels, based on density scale
      int dimension= (int) (60 * scale + 0.5f);      
  
       
     
     final  LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dimension, dimension);

  

tv2.setOnTouchListener(new View.OnTouchListener() {



public boolean onTouch(View v, MotionEvent event) {
  // LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();

   
   switch (event.getAction())
   {
       case MotionEvent.ACTION_DOWN:
    	  
    	   v.setBackgroundResource(R.drawable.border);
    	   mPlayer1.start();
    	       
           break;
case MotionEvent.ACTION_MOVE:
           int x_cord = (int) event.getRawX();
           int y_cord = (int) event.getRawY();
         
           if (x_cord > windowwidth) {
               x_cord = windowwidth;
               
               
               
           }
           if (y_cord > windowheight) {
               y_cord = windowheight;
              
              
           }
         
           layoutParams2.leftMargin = x_cord - 25;
           layoutParams2.topMargin = y_cord - 75;
        
          tv2.setLayoutParams(layoutParams2);
          tv2.setScaleType(ScaleType.MATRIX);
           break;
       case MotionEvent.ACTION_UP:
    	 /*  mPlayer3.stop();
           mPlayer3.release();*/
    	  // stopPlaying();
           v.setBackgroundResource(R.drawable.blue);
  
           mPlayer2.start();
       break;
       }
   
   return true;

}
});
;
}
    private void stopPlaying() {
        if (mPlayer1 != null) {
        	mPlayer1.stop();
        	mPlayer1.release();
        	mPlayer1 = null;
       }    

}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		LayoutParams params = (RelativeLayout.LayoutParams)tv2.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// params.rightMargin = width-25;
         //params.bottomMargin = height-75;
		
		tv2.setLayoutParams(params);
		
		
		LayoutParams params2 = (RelativeLayout.LayoutParams)tv1.getLayoutParams();
		//params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		//params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		   params2.leftMargin = 0;
           params2.topMargin = 0;
		//r.addView(tv1, params2);
		tv1.setLayoutParams(params2);
		
		
	}

}


 

