package com.iscg7424.assignment3;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.iscg7424.assignment3.drawingThreads.DrawThread;
import com.iscg7424.assignment3.drawingThreads.LayerAircraftThread;
import com.iscg7424.assignment3.drawingThreads.LayerThread;


public class FirstStageSfview extends SurfaceView implements SurfaceHolder.Callback,OnTouchListener{
	private SurfaceHolder  holder;
	private DrawThread drawThread;
	
	
	public FirstStageSfview(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		
		this.setOnTouchListener(this);
		
	}



	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
		LayerThread.mediaPlayerLv1 = MediaPlayer.create(this.getContext(), R.raw.lv1);
		LayerThread.mediaPlayerLv2 = MediaPlayer.create(this.getContext(), R.raw.lv2);
		LayerThread.mediaPlayerLv3 = MediaPlayer.create(this.getContext(), R.raw.lv3);
		
		LayerThread.mediaPlayer = LayerThread.mediaPlayerLv1;
		LayerThread.mediaPlayer.setLooping(true);
		LayerThread.mediaPlayer.setVolume(0.4f, 0.4f);
		
		
		
		LayerThread.soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
		LayerThread.laser = LayerThread.soundPool.load(this.getContext(), R.raw.laser, 0);
	    LayerThread.childUFOShoot = LayerThread.soundPool.load(this.getContext(), R.raw.shooting_child_ufo, 0);
	    LayerThread.childUFOExplosion = LayerThread.soundPool.load(this.getContext(), R.raw.explosion2, 0);
	    LayerThread.icLauncherWarning = LayerThread.soundPool.load(this.getContext(), R.raw.thruster, 0);
	    LayerThread.airCraftExplosion = LayerThread.soundPool.load(this.getContext(), R.raw.explosion, 0);
	    LayerThread.wonSound = LayerThread.soundPool.load(this.getContext(), R.raw.won2, 0);
	    		
		if(this.drawThread==null){
			this.drawThread = new DrawThread(this.holder,this.getResources());
		}
		this.drawThread.start();
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
     
        this.drawThread.threadFlag=false;
        
        if(LayerThread.mediaPlayer.isPlaying())
        LayerThread.mediaPlayer.stop();
        
        LayerThread.mediaPlayer.release();
        LayerThread.soundPool.release();
        
        this.drawThread = null;
        
        for(LayerThread t : DrawThread.layerArray)
        {
        	if(t!=null){
        		try {
        			t.doStop();
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        }
	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {
		LayerAircraftThread layerAircraftThread = LayerAircraftThread.getInstance();
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				
				if(!layerAircraftThread.isDrawCtrlPad){
					layerAircraftThread.setCtrlPadOrginX(event.getX());
					layerAircraftThread.setCtrlPadOrginY(event.getY());
					
					layerAircraftThread.setCtrlPadPointX(event.getX());
					layerAircraftThread.setCtrlPadPointY(event.getY());
					
				//	Log.v("isDrawCtrlPad","("+LayerAircraftThread.ctrlPadOrginX+","+LayerAircraftThread.ctrlPadOrginY+")");
					layerAircraftThread.isDrawCtrlPad = true;
				}
				break;
			case MotionEvent.ACTION_UP:
				layerAircraftThread.isDrawCtrlPad = false;
				
				break;
			case MotionEvent.ACTION_MOVE:
				layerAircraftThread.setCtrlPadPointX(event.getX());
				layerAircraftThread.setCtrlPadPointY(event.getY());
		}
			
		
		return true;
	}



	public void pushAircfaftDown(boolean switcher) {
		LayerAircraftThread.getInstance().isGoingDown = switcher;
	}



	public void pushAircfaftRight(boolean switcher) {
		LayerAircraftThread.getInstance().isGoingRight = switcher;
	}



	public void pushAircfaftLeft(boolean switcher) {
		LayerAircraftThread.getInstance().isGoingLeft = switcher;
	}



	public void pushAircfaftUp(boolean switcher) {
		LayerAircraftThread.getInstance().isGoingUp = switcher;
	}


	
	
}
