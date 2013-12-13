package com.iscg7424.assignment3;


import com.iscg7424.assignment3.drawingThreads.LayerThread;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.Service;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
//test
public class MainActivity extends Activity {
//	private FirstStageSfview firstStageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		
	//	firstStageView = (FirstStageSfview)this.findViewById(R.id.firstStageSfview1);
		
		
		if(LayerThread.vibrator==null){
			Vibrator vt =(Vibrator)this.getApplication().getSystemService(Service.VIBRATOR_SERVICE);
			LayerThread.vibrator = vt;
			
			
		}
		Log.e("Vibrator", "Vibrator="+LayerThread.vibrator);
		
		if(LayerThread.soundPool == null){
			LayerThread.soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		}
		
		if(LayerThread.mediaPlayer == null){
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


}
