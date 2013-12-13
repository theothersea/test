package com.iscg7424.assignment3.drawingThreads;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iscg7424.assignment3.R;

public class ObjectForDrawingLightning extends ObjectForDrawing{

	int canvasWidth = 0;
	int canvasHeight = 0;
	float xDistance = 1.0F;
	float yDistance = 1.0F;
	float fltDistance = 0F;
	Bitmap bmpLightning;
	float poxDefaultX = 0;
	float poxDefaultY = 0;
	float poxShipX = 0;
	float poxShipY = 0;
	double dbElapsedTime = 0;
	double dbInitialVelocity = 50;
	boolean bolVisible = true;
	
	Random rnd = new Random();
	int ranX = 0;
	int ranY = 0;
	
	public ObjectForDrawingLightning(Resources resources, int canvasWidth, int canvasHeight, 
									float lightningX, float lightningY, float poxShipX, float poxShipY)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		//target direction is airplane		
//		this.poxShipX = poxShipX;
//		this.poxShipY = poxShipY;
		//target direction is random
		this.poxShipX = rnd.nextInt(canvasWidth);
		this.poxShipY = lightningY + rnd.nextInt(canvasHeight-(int)lightningY);		
		bmpLightning = BitmapFactory.decodeResource(resources, R.drawable.lightning);
		bmpLightning = Bitmap.createScaledBitmap(bmpLightning, 40, 40, false); 
		setX(lightningX);
		setY(lightningY);		
		setBitmap(bmpLightning);
	}
	
	public void move()
	{
		
		dbElapsedTime += 0.05;
//		setX(getX() + xDistance);
//		setY(getY() + yDistance);
//		float x = - ((poxDefaultY - getY()) * (poxDefaultX - poxShipX)) / (poxDefaultY - poxShipY) + poxDefaultX;
		
		float x = (float) (poxDefaultX - (poxDefaultX-poxShipX) * dbInitialVelocity * dbElapsedTime / fltDistance);
		float y = (float) (poxDefaultY - (poxDefaultY-poxShipY) * dbInitialVelocity * dbElapsedTime / fltDistance);

		setX(x);
		setY(y);
		
		if (y<0 || y > canvasHeight || x<0 || x > canvasWidth)
		{
			bolVisible = false;
		}
	}

	public void start()
	{

		poxDefaultX = getX();
		poxDefaultY = getY();
		
		fltDistance = (float) Math.sqrt(Math.pow((poxDefaultX-poxShipX),2) + Math.pow((poxDefaultY-poxShipY),2));
	}
	
	public boolean isBolVisible() {
		return bolVisible;
	}
}
