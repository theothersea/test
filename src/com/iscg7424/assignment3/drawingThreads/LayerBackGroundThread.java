package com.iscg7424.assignment3.drawingThreads;

import java.util.List;

import com.iscg7424.assignment3.R;
import com.iscg7424.assignment3.drawingThreads.DrawThread.GameStat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LayerBackGroundThread extends LayerThread{
	
	private static LayerBackGroundThread layerBackGroundThread;
	
	public static  LayerBackGroundThread getInstance(Resources resources, int canvasWidth, int canvasHeight,int layerNumber){
		
		if (LayerBackGroundThread.layerBackGroundThread==null){
			LayerBackGroundThread.layerBackGroundThread = new LayerBackGroundThread(resources, canvasWidth, canvasHeight,layerNumber);
		}
		
		return LayerBackGroundThread.layerBackGroundThread;
	}
	
	public static LayerBackGroundThread getInstance(){
		return LayerBackGroundThread.layerBackGroundThread;
	}
	
	private Bitmap bmpBg;
	
	ObjectForDrawingLifeBar lifeBar;
	
	/**
	 * @param resources
	 * @param canvasWidth
	 * @param canvasHeight
	 */
	private LayerBackGroundThread(Resources resources, int canvasWidth, int canvasHeight,int layerNumber) {
		super(resources, canvasWidth, canvasHeight, layerNumber );
		
		/*ObjectForDrawing right =  new ObjectForDrawing();
		right.setBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.right));
		this.objectsForDrawing.add(right);
		
		ObjectForDrawing down =  new ObjectForDrawing();
		down.setBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.down));
		this.objectsForDrawing.add(down);*/
		
		ObjectForDrawing bg = new ObjectForDrawing();
		bmpBg = BitmapFactory.decodeResource(this.resources, R.drawable.space_bg);
	
		bmpBg = Bitmap.createScaledBitmap(bmpBg, canvasWidth, canvasHeight, false);
		bg.setBitmap(bmpBg);
		this.objectsForDrawing.add(bg);
		
		this.lifeBar = new ObjectForDrawingLifeBar(this.canvasWidth*0.3f, 20);
		this.lifeBar.setX(this.canvasWidth/2-lifeBar.getMAX_LENGTH()/2);
		this.lifeBar.setY(this.canvasHeight-lifeBar.HEIGHT-10);
			
		this.objectsForDrawing.add(this.lifeBar);
	}

	
	@Override
	public void run() {
		while (true){
			if(this.isDoStop) return;
			
			if(DrawThread.gameStat == DrawThread.GameStat.QUIT){
				break;
			}
			
			/*ObjectForDrawing right = this.objectsForDrawing.get(0);
			right.setX(right.getX()+1);
			if(right.getX()>this.canvasWidth) right.setX(-1*right.getBitmap().getWidth());
			
			ObjectForDrawing down = this.objectsForDrawing.get(1);
			down.setY(down.getY()+1);
			if(down.getY()>this.canvasHeight) down.setY(-1*down.getBitmap().getHeight());*/
			if(DrawThread.gameStat == GameStat.GAMEOVER) break;
			
			ObjectForDrawing bg = this.objectsForDrawing.get(0);
			bg.setX(0);
			
			
			this.addToArrayAndSleep();
		}
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


	
}
