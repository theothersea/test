package com.iscg7424.assignment3.drawingThreads;

import java.util.List;
import java.util.Random;

import android.content.res.Resources;

public class LayerCargoThread extends LayerThread{
	private static LayerCargoThread layerCargoThread;
	
	public static LayerCargoThread getInstance(Resources resources, int canvasWidth,int canvasHeight, int layerNumber){
		if (LayerCargoThread.layerCargoThread==null){
			LayerCargoThread.layerCargoThread = new LayerCargoThread( resources,  canvasWidth, canvasHeight,  layerNumber);
		}
		return LayerCargoThread.layerCargoThread;
	}
	
	public static LayerCargoThread getInstance(){
		return LayerCargoThread.layerCargoThread;
	}

	int intNumberOfCargo = 6;
	int intCreatedCargo = 0;
	Random rnd = new Random();
	float aircraftX = 0;
	float aircraftY = 0;
	float cargoX = 0;
	float cargoY = 0;
	float fltDistance = 0;
	
	private LayerCargoThread(Resources resources, int canvasWidth,int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight,layerNumber);
		
	}
	
	@Override
	public void run() {
		while(true){
			if(DrawThread.gameStat == DrawThread.GameStat.LV2 || DrawThread.gameStat == DrawThread.GameStat.LV3)			
			{
				aircraftX = LayerAircraftThread.getInstance().getAircraftCentralX();
				aircraftY = LayerAircraftThread.getInstance().getAircraftCentralY();

				// Create Cargo
				if ((intNumberOfCargo > intCreatedCargo) && (rnd.nextInt(100) == 0))
				{
					ObjectForDrawingCargo mOfdCargo =  new ObjectForDrawingCargo(resources, canvasWidth, canvasHeight);
					this.objectsForDrawing.add(mOfdCargo);
					intCreatedCargo++;
				}


				// Cargo
				for (int i=0; i<this.objectsForDrawing.size(); i++)
				{				
					if (this.objectsForDrawing.get(i).getClass() == ObjectForDrawingCargo.class){

						ObjectForDrawingCargo ofdCargo = (ObjectForDrawingCargo)this.objectsForDrawing.get(i);
						cargoX = ofdCargo.getX() + ofdCargo.getBitmap().getWidth()/2;
						cargoY = ofdCargo.getY() + ofdCargo.getBitmap().getHeight()/2;
						fltDistance = (float) Math.sqrt(Math.pow(aircraftX - cargoX, 2) + Math.pow(aircraftY - cargoY, 2));
						if(fltDistance < 30 && ofdCargo.isVisible())
						{
							if(ofdCargo.getCagoType().equals("H"))  LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(ofdCargo.getFltLife());
							if(ofdCargo.getCagoType().equals("P"))  LayerAircraftThread.getInstance().setPowered(true);
							if(ofdCargo.getCagoType().equals("S"))  LayerAircraftThread.getInstance().setShielded(true);
							if(ofdCargo.getCagoType().equals("R"))  LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(ofdCargo.getFltLife());
							this.objectsForDrawing.remove(i);
							intCreatedCargo--;
							continue;
						}
						if(ofdCargo.getY() > canvasHeight)
						{
							this.objectsForDrawing.remove(i);
							intCreatedCargo--;
							continue;
						}		
						ofdCargo.move();
					}
				}
			}
			if(DrawThread.gameStat == DrawThread.GameStat.LV3)			
			{
//				// Cargo
//				for (int i=0; i<this.objectsForDrawing.size(); i++)
//				{				
//					if (this.objectsForDrawing.get(i).getClass() == ObjectForDrawingCargo.class){
//						this.objectsForDrawing.remove(i);
//						intCreatedCargo--;							
//					}
//				}
				intNumberOfCargo = 12;
			}
			this.addToArrayAndSleep();
		}
	}

	@Override
	public boolean doTouchDetection(float posAircraftX, float posAircraftY) {
		
		float posX = 0;
		float posY = 0;	

		for (int i=0; i<this.objectsForDrawing.size(); i++)
		{
			try {
				if (objectsForDrawing.get(i).getClass() == ObjectForDrawingCargo.class)
				{
					// Cargo
					ObjectForDrawingCargo ofdCargo = (ObjectForDrawingCargo)this.objectsForDrawing.get(i);
					posX = ofdCargo.getX() + ofdCargo.getBitmap().getWidth()/2;
					posY = ofdCargo.getY() + ofdCargo.getBitmap().getHeight()/2;
					if ((isTouched(posX, posY, posAircraftX, posAircraftY)) 
							&& ofdCargo.getCagoType().equals("R")
							&& ofdCargo.isVisible())
					{
						return true;
					}
				}
			} catch (ClassCastException ex){
			} catch (IndexOutOfBoundsException ex){
			} catch (NullPointerException ex){
			}
		}
		return false;
	}
	
	public boolean isTouched(float posX, float posY, float posAircraftX, float posAircraftY)
	{
		// Calculate the distance
		float fltDistance = (float) Math.sqrt(Math.pow(posAircraftX - posX, 2) + Math.pow(posAircraftY - posY, 2));
		if (fltDistance <25)
		{
			return true;
		}
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
