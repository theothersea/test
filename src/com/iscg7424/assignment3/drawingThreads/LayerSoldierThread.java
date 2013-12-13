package com.iscg7424.assignment3.drawingThreads;

import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iscg7424.assignment3.R;

public class LayerSoldierThread extends LayerThread{
	
	Bitmap bmpCamp;
	int intNumberOfSoldier = 3;
	Random rnd = new Random();
	
	protected LayerSoldierThread(Resources resources,int canvasWidth,int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight,layerNumber);

		ObjectForDrawing ofdCamp =  new ObjectForDrawing();
		bmpCamp = BitmapFactory.decodeResource(this.resources, R.drawable.camp);
		bmpCamp = Bitmap.createScaledBitmap(bmpCamp, 200, 90, false);
		ofdCamp.setBitmap(bmpCamp);
		this.objectsForDrawing.add(ofdCamp); 

		for (int i=0; i<intNumberOfSoldier; i++)
		{	
			ObjectForDrawingSoldier ofdSoldier =  new ObjectForDrawingSoldier(resources, canvasWidth, canvasHeight);
			this.objectsForDrawing.add(ofdSoldier);
		}

	}

	

	@Override
	public void run() {
		while(true){
			if(DrawThread.gameStat == DrawThread.GameStat.QUIT){
				break;
			}

			// Camp
			ObjectForDrawing ofdCamp = this.objectsForDrawing.get(0);
			ofdCamp.setX(0);
			ofdCamp.setY((float)((canvasHeight - bmpCamp.getHeight() +10)));
						
			float aircraftX = LayerAircraftThread.getInstance().getAircraftCentralX();
			float aircraftY = LayerAircraftThread.getInstance().getAircraftCentralY();
		
			// Soldier
			for (int i=0; i<this.objectsForDrawing.size(); i++)
			{				
				if (this.objectsForDrawing.get(i).getClass() == ObjectForDrawingSoldier.class){
					ObjectForDrawingSoldier ofdSoldier = (ObjectForDrawingSoldier)this.objectsForDrawing.get(i);
					if(aircraftX>(ofdSoldier.getX()-10)&& aircraftX<(ofdSoldier.getX()+10) && aircraftY>ofdSoldier.getY()-ofdSoldier.getBitmap().getHeight()/2){
						this.objectsForDrawing.remove(i);
						continue;
					}				
					ofdSoldier.move();
				}
			}			
			
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
