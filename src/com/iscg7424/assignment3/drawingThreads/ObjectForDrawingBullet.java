package com.iscg7424.assignment3.drawingThreads;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iscg7424.assignment3.R;

public class ObjectForDrawingBullet extends ObjectForDrawing{

	int canvasWidth = 0;
	int canvasHeight = 0;
	float xDistance = 1.0F;
	float yDistance = 1.0F;
	float fltDistance = 0F;
	Bitmap bmpChildBullet;
	ObjectForDrawingUFO ofdChileUFO;
	float poxDefaultX = 0F;
	float poxDefaultY = 0F;
	float poxShipX = 0;
	float poxShipY = 0;
	double dbElapsedTime = 0;
	double dbInitialVelocity = 50;
	boolean bolVisible = true;

	public ObjectForDrawingBullet(Resources resources, int canvasWidth, int canvasHeight, float poxShipX, float poxShipY)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.poxShipX = poxShipX;
		this.poxShipY = poxShipY;
		bmpChildBullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
		bmpChildBullet = Bitmap.createScaledBitmap(bmpChildBullet, 10, 10, false);
		setBitmap(bmpChildBullet);
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
		
		if (y > canvasHeight)
		{
			bolVisible = false;
		}
	}
	
	public void start()
	{
		setX(ofdChileUFO.getX() + this.ofdChileUFO.getBitmap().getWidth() / 2);
		setY(ofdChileUFO.getY() + this.ofdChileUFO.getBitmap().getHeight());

		poxDefaultX = getX();
		poxDefaultY = getY();
		
		fltDistance = (float) Math.sqrt(Math.pow((poxDefaultX-poxShipX),2) + Math.pow((poxDefaultY-poxShipY),2));

		LayerThread.soundPool.play(LayerThread.childUFOShoot, 0.05f, 0.05f, 0, 0, 1);
	}


	public float getPosBulletX() {
		return this.getX() + (bmpChildBullet.getHeight() / 2);
	}
	public float getPosBulletY() {
		return this.getY() + (bmpChildBullet.getWidth() / 2);
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
