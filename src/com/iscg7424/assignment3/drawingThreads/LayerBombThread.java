package com.iscg7424.assignment3.drawingThreads;

import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iscg7424.assignment3.R;

public class LayerBombThread extends LayerThread {

	private static LayerBombThread layerBombThread;
	
	public static LayerBombThread getInstance (Resources resources, int canvasWidth,
			int canvasHeight, int layerNumber) {
		// TODO Auto-generated constructor stub
		if(LayerBombThread.layerBombThread==null)
		{
			LayerBombThread.layerBombThread = new LayerBombThread(resources, canvasWidth, canvasHeight, layerNumber);
		}
		return LayerBombThread.layerBombThread;
	}
	
	public static LayerBombThread getInstance()
	{
		return LayerBombThread.layerBombThread;
	}

	int numberOfBomb = 1;
	Random rnd = new Random();
//	float aircraftX = 0;
//	float aircraftY = 0;
	
	
	private LayerBombThread(Resources resources, int canvasWidth, int canvasHeight, int layerNumber) 
	{
		super(resources, canvasWidth, canvasHeight, layerNumber);
		// TODO Auto-generated constructor stub
		
		for (int i=0; i < numberOfBomb; i++)
		{
			ObjectForDrawingBomb ofdBomb = new ObjectForDrawingBomb(resources, canvasWidth, canvasHeight, 20 + rnd.nextInt(canvasWidth-50), -i*80);
			this.objectsForDrawing.add(ofdBomb);
		}
	}
	
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		//super.run();
		while (true)
		{
			if(DrawThread.gameStat == DrawThread.GameStat.QUIT){
				break;
			}
			float aircraftX = LayerAircraftThread.getInstance().getAircraftCentralX();
			float aircraftY = LayerAircraftThread.getInstance().getAircraftCentralY();
		
		
			//Bomb
			for(int i=0; i < this.objectsForDrawing.size(); i++)
			{
				if(this.objectsForDrawing.get(i).getClass()== ObjectForDrawingBomb.class)
				{
					ObjectForDrawingBomb ofdBomb = (ObjectForDrawingBomb)this.objectsForDrawing.get(i);
					if (aircraftX > (ofdBomb.getX()-10) && aircraftX < (ofdBomb.getX() + 10) && 
							aircraftY > ofdBomb.getY() - ofdBomb.getBitmap().getHeight()/2 &&
							aircraftY < ofdBomb.getY() + ofdBomb.getBitmap().getHeight())
					{
						this.objectsForDrawing.remove(i);
						continue;
					}
					ofdBomb.move();
	
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
