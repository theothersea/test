package com.iscg7424.assignment3.drawingThreads;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iscg7424.assignment3.R;

public class ObjectForDrawingVolcanicBlock extends ObjectForDrawing {

	int canvasWidth = 0;
	int canvasHeight = 0;
	float xDistance = 1.0F;
	float yDistance = 1.0F;
	Bitmap bmpVolcanicBlock;
	ObjectForDrawingUFO ofdChileUFO;
	float poxDefaultX = 0;
	float poxDefaultY = 0;
	float posTopOfVolcanoX = 0;	// ToDo:
	float posTopOfVolcanoY = 0;	// ToDo:
	boolean bolVisible = true;
	double dbElapsedTime = 0;
	double dbAngle = 0;
	double dbInitialVelocity = 0;
	double dbAccelerationOfGravity = 0;
	Random rnd = new Random();

	public ObjectForDrawingVolcanicBlock(Resources resources, int canvasWidth, int canvasHeight)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		bmpVolcanicBlock = BitmapFactory.decodeResource(resources, R.drawable.volcano);
		bmpVolcanicBlock = Bitmap.createScaledBitmap(bmpVolcanicBlock, 10 + rnd.nextInt(20), 10 + rnd.nextInt(20), false);
		setBitmap(bmpVolcanicBlock);
		posTopOfVolcanoX = canvasWidth / 6;		// ToDo:
		posTopOfVolcanoY = canvasHeight - 160;	// ToDo:
	}
	
	public void move()
	{
		dbElapsedTime += 0.05;;
//		‚wF ‚wi ‚u0 ‚b‚n‚rƒÆj ‚”
		float x = (float) (posTopOfVolcanoX + (dbInitialVelocity * Math.cos(Math.toRadians(dbAngle))) * dbElapsedTime);
//		‚xF ‚xi ‚u0 ‚r‚h‚mƒÆj ‚” |‚f ‚”^‚Q / ‚Q
		float y = (float) (posTopOfVolcanoY - ((dbInitialVelocity * Math.sin(Math.toRadians(dbAngle))) * dbElapsedTime) - dbAccelerationOfGravity * Math.pow(dbElapsedTime, 2) * 0.5);
		
		setX(x);
		setY(y);
		
		if (y > canvasHeight)
		{
			bolVisible = false;
		}
		
		
	}
	
	public void start()
	{
		dbAngle = 45.0 + rnd.nextInt(70);
		dbInitialVelocity = 50 +  + rnd.nextInt(50);
		dbAccelerationOfGravity = - 9.8;
		setX(posTopOfVolcanoX);
		setY(posTopOfVolcanoY);
	}

	public ObjectForDrawingUFO getOfdChileUFO() {
		return ofdChileUFO;
	}

	public void setOfdChileUFO(ObjectForDrawingUFO ofdChileUFO) {
		this.ofdChileUFO = ofdChileUFO;
	}

	public boolean isBolVisible() {
		return bolVisible;
	}
}
