package com.iscg7424.assignment3.drawingThreads;

import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iscg7424.assignment3.R;

public class LayerVolcanoThread extends LayerThread{
	private static LayerVolcanoThread layerVolcanoThread;
	
	Bitmap bmpVolcano_0;
	Bitmap bmpVolcano_1;
	ObjectForDrawing ofdVolcano = new ObjectForDrawing();
	int intNumberOfVolcanicBlock = 0;
	Random rnd = new Random();

	public static LayerVolcanoThread getInstance(Resources resources, int canvasWidth,int canvasHeight, int layerNumber){
		if (LayerVolcanoThread.layerVolcanoThread==null){
			LayerVolcanoThread.layerVolcanoThread = new LayerVolcanoThread( resources, canvasWidth, canvasHeight,  layerNumber);
		}
		
		return LayerVolcanoThread.layerVolcanoThread;
	}
	
	private LayerVolcanoThread(Resources resources,int canvasWidth,int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight,layerNumber);
		
		bmpVolcano_0 = BitmapFactory.decodeResource(this.resources, R.drawable.volcano_0);
		bmpVolcano_0 = Bitmap.createScaledBitmap(bmpVolcano_0, 300, 300, false);
		bmpVolcano_1 = BitmapFactory.decodeResource(this.resources, R.drawable.volcano_1);
		bmpVolcano_1 = Bitmap.createScaledBitmap(bmpVolcano_1, 300, 300, false);
		ofdVolcano.setBitmap(bmpVolcano_0);
		ofdVolcano.setX(88);
		ofdVolcano.setY(250);
		this.objectsForDrawing.add(ofdVolcano);
		
	}

	

	@Override
	public void run() {
		while(true){
			if(DrawThread.gameStat == DrawThread.GameStat.QUIT){
				break;
			}
			
			// Volcanic Block
			if (rnd.nextInt(200) == 0)
			{
				int k = rnd.nextInt(7) + 1;
				for (int i=0; i<k; i++)
				{
					ObjectForDrawingVolcanicBlock ofdVolcanicBlock =  new ObjectForDrawingVolcanicBlock(resources, canvasWidth, canvasHeight);
					this.objectsForDrawing.add(ofdVolcanicBlock);
					ofdVolcanicBlock.start();
					intNumberOfVolcanicBlock++;
				}
				ofdVolcano.setBitmap(bmpVolcano_1);
			}
			else
			{
				ofdVolcano.setBitmap(bmpVolcano_0);
			}
			for (int i=0; i<objectsForDrawing.size(); i++)
			{
				if (objectsForDrawing.get(i).getClass() == ObjectForDrawingVolcanicBlock.class)
				{
					ObjectForDrawingVolcanicBlock ofdVolcanicBlock = (ObjectForDrawingVolcanicBlock)this.objectsForDrawing.get(i);
					ofdVolcanicBlock.move();
					if (!ofdVolcanicBlock.isBolVisible())
					{
						objectsForDrawing.remove(i);
						ofdVolcanicBlock = null;
					}
				}
				else if (objectsForDrawing.get(i).getClass() == ObjectForDrawing.class)
				{
					
				}
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
			if (objectsForDrawing.get(i).getClass() == ObjectForDrawing.class)
			{
				// Volcano
				ObjectForDrawing ofdMotherUFO = this.objectsForDrawing.get(i);
			}
			else if (objectsForDrawing.get(i).getClass() == ObjectForDrawingVolcanicBlock.class)
			{
				// Volcanic blocks
				ObjectForDrawingVolcanicBlock ofdVolcanicBlock = (ObjectForDrawingVolcanicBlock)this.objectsForDrawing.get(i);
				posX = ofdVolcanicBlock.getX();
				posY = ofdVolcanicBlock.getY();
			}
			
			// Calculate the distance
			float fltDistance = (float) Math.sqrt(Math.pow(posAircraftX - posX, 2) + Math.pow(posAircraftY - posY, 2));
			if (fltDistance < 20)
			{
				return true;
			}
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
