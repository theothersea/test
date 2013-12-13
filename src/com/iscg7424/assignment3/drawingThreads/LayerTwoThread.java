package com.iscg7424.assignment3.drawingThreads;

import java.util.List;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.iscg7424.assignment3.R;

public class LayerTwoThread extends LayerThread{
	protected LayerTwoThread(Resources resources, int canvasWidth,int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight,layerNumber);
		
		ObjectForDrawing go =  new ObjectForDrawing();
		go.setBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.go));
		this.objectsForDrawing.add(go);
	}

	

	@Override
	public void run() {
		int xDistance = 1;
		int yDistance = 1;
		while(true){
			if(DrawThread.gameStat == DrawThread.GameStat.QUIT){
				break;
			}
			ObjectForDrawing go = this.objectsForDrawing.get(0);
			
			go.setX(go.getX()+xDistance);
			if((go.getX()>this.canvasWidth-go.getBitmap().getWidth())||(go.getX()<0 ))
				xDistance *= -1;
			
			go.setY(go.getY()+yDistance);
			if((go.getY()>this.canvasHeight-go.getBitmap().getHeight())||(go.getY()<0 ))
				yDistance *= -1;
		
		
			
			
			
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
