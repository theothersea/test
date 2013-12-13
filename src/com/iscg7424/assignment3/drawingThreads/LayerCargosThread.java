package com.iscg7424.assignment3.drawingThreads;

import java.util.List;
import java.util.Random;

import android.content.res.Resources;

public class LayerCargosThread extends LayerThread {
	
	int intNumberOfCargo = 6;
	Random rnd = new Random();
	
	public static LayerCargosThread layerCargosThread;
	
	public static LayerCargosThread getInstance(Resources resources, int canvasWidth,
			int canvasHeight, int layerNumber){
		
		return LayerCargosThread.layerCargosThread;
	}

	protected LayerCargosThread(Resources resources, int canvasWidth,int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight, layerNumber);
		// TODO Auto-generated constructor stub
		
		for (int i=0; i<intNumberOfCargo; i++)
		{	
			ObjectForDrawingCargo ofdCargo =  new ObjectForDrawingCargo(resources, canvasWidth, canvasHeight,20 + rnd.nextInt(canvasWidth-50) ,-i*80);
			this.objectsForDrawing.add(ofdCargo);
		}
	
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){

			float aircraftX = LayerAircraftThread.getInstance().getAircraftCentralX();
			float aircraftY = LayerAircraftThread.getInstance().getAircraftCentralY();
		
			// Cargo
			for (int i=0; i<this.objectsForDrawing.size(); i++)
			{				
				if (this.objectsForDrawing.get(i).getClass() == ObjectForDrawingCargo.class){
						
					ObjectForDrawingCargo ofdCargo = (ObjectForDrawingCargo)this.objectsForDrawing.get(i);
					if(aircraftX>(ofdCargo.getX()-10)&& aircraftX<(ofdCargo.getX()+10) && 
					   aircraftY>ofdCargo.getY()-ofdCargo.getBitmap().getHeight()/2 && aircraftY<ofdCargo.getY()+ofdCargo.getBitmap().getHeight())
					{
						this.objectsForDrawing.remove(i);
						continue;
					}				
					ofdCargo.move();
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
