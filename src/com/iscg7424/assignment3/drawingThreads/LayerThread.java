package com.iscg7424.assignment3.drawingThreads;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.Log;
//import android.util.Log;

public abstract class LayerThread extends Thread  implements Touchable,Runnable {
	protected Resources resources;
	protected  int layerNumber;
	
	protected int canvasWidth;
	protected int canvasHeight;
	
	protected List<ObjectForDrawing> objectsForDrawing;
	
	public static Vibrator vibrator;
	public static SoundPool soundPool;
	public static MediaPlayer mediaPlayer;
	public static MediaPlayer mediaPlayerLv1;
	public static MediaPlayer mediaPlayerLv2;
	public static MediaPlayer mediaPlayerLv3;
	public static int laser;
	public static int childUFOShoot;
	public static int childUFOExplosion;
	public static int icLauncherWarning;
	public static int airCraftExplosion;
	public static int wonSound;
	
	protected int getPercentageWidth(float percentage){
		int i = Math.round(this.canvasWidth * percentage);
		
		Log.v("per", percentage+"="+i);
		if (i==0) i = 1;
		
		return i;
	}
	
	protected int getPercentageHeight(float percentage){
		int i = Math.round(this.canvasHeight * percentage);
		
		if (i==0) i = 1;
		
		return i;
	}
	/**
	 * @param resources
	 * @param outputBitmap
	 * @param canvasWidth
	 * @param canvasHeight
	 */
	protected LayerThread(Resources resources, int canvasWidth, int canvasHeight, int layerNumber) {
		super();
		this.resources = resources;
		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		
		this.layerNumber = layerNumber;
		
		this.objectsForDrawing = new ArrayList<ObjectForDrawing>();
		
		this.setPriority(MAX_PRIORITY);
		
	}


	public int getLayerNumber() {
		return layerNumber;
	}


	protected void addToArrayAndSleep(){
		
		DrawThread.layerArray[this.layerNumber]= this;
				
		if (this.layerNumber==DrawThread.TOTAL_LAYERS-1)
			DrawThread.drawThread.interrupt();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			Log.v("LayerThread","DrawThread"+this.layerNumber+" is waken up!");
		}
	}
	
	public List<ObjectForDrawing> getObjectsForDrawing(){
		return this.objectsForDrawing;
	}

	protected boolean isDoStop = false; 
	public void doStop(){
		this.isDoStop = true;
	}

	@Override
	public boolean doTouchDetection(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float[] doPointTouchDetection(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doPointTouchDetection(List<ObjectForDrawing> objectList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		if(this.isDoStop) return;
	}
	
	
}
