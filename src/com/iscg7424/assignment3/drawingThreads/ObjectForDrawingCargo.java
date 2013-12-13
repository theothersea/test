package com.iscg7424.assignment3.drawingThreads;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iscg7424.assignment3.R;

public class ObjectForDrawingCargo extends ObjectForDrawing {

	private String cagoType; 
	int canvasWidth = 0;
	int canvasHeight = 0;
	Random rnd = new Random();
	int ranX = 0;
	int ranY = 0;
	int marginX = 50;
	int marginY = 150;
	Bitmap bmpCargo;
	int xDistance = 1;
	int yDistance = 1;
	float fltLife = 0;
	boolean isVisible = false;
	int cntVisible = 0;

	
	public String getCagoType() {
		return cagoType;
	}

	public void setCagoType(String cagoType) {
		this.cagoType = cagoType;
	}

	public ObjectForDrawingCargo(Resources resources, int canvasWidth, int canvasHeight)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;

		int bmpNo = rnd.nextInt(6);
		if (bmpNo == 0) 
		{
			//cargo for increase life
			bmpCargo = BitmapFactory.decodeResource(resources, R.drawable.s);
			bmpCargo = Bitmap.createScaledBitmap(bmpCargo, 32, 32, false);
			cagoType = "S";
		}
		else if (bmpNo == 1)
		{
			bmpCargo = BitmapFactory.decodeResource(resources, R.drawable.h);
			bmpCargo = Bitmap.createScaledBitmap(bmpCargo, 32, 32, false);
			cagoType = "H";
			fltLife = +0.05F;
			
		}
		else if (bmpNo == 2)
		{
			bmpCargo = BitmapFactory.decodeResource(resources, R.drawable.p);
			bmpCargo = Bitmap.createScaledBitmap(bmpCargo, 32, 32, false);
			cagoType = "P";
		}
		else if (bmpNo == 3)
		{
			//rocks to decrease life bar
			bmpCargo = BitmapFactory.decodeResource(resources, R.drawable.rocka);
			bmpCargo = Bitmap.createScaledBitmap(bmpCargo, 32, 32, false);
			fltLife = -0.01F;
			cagoType = "R";
		}
		else if (bmpNo == 4)
		{
			bmpCargo = BitmapFactory.decodeResource(resources, R.drawable.rockb);
			bmpCargo = Bitmap.createScaledBitmap(bmpCargo, 32, 32, false);
			cagoType = "R";
			fltLife = -0.02F;
		}
		else if (bmpNo == 5)
		{
			bmpCargo = BitmapFactory.decodeResource(resources, R.drawable.rockc);
			bmpCargo = Bitmap.createScaledBitmap(bmpCargo, 32, 32, false);
			cagoType = "R";
			fltLife = -0.03F;
		}

		getPaint().setAlpha(100);
		setBitmap(bmpCargo);
		setX(20 + rnd.nextInt(canvasWidth-50));
		setY(canvasHeight/12);
	}
	
	public ObjectForDrawingCargo(Resources resources, int canvasWidth2,
			int canvasHeight2, int i, int j) {
		// TODO Auto-generated constructor stub
	}

	public void move()
	{
		if (!isVisible)
		{
			if (cntVisible>100)
			{
				isVisible=true;
				getPaint().setAlpha(255);
			}
			cntVisible++;
		}
		ranX = rnd.nextInt(2) - 1;
		ranY = rnd.nextInt(2) - 1;
		setX(getX() + xDistance * ranX);
		setY(getY()+(float)0.5);
		
		if (rnd.nextInt(100) == 0) { xDistance *= -1; }
		if (rnd.nextInt(100) == 0) { yDistance *= -1; }
		
		if((getX() > this.canvasWidth - getBitmap().getWidth() - marginX) || 
				(getX() < marginX))
			xDistance *= -1;
	}
	
	public void start()
	{
	}
	
	public int getxDistance() {
		return xDistance;
	}

	public void setxDistance(int xDistance) {
		this.xDistance = xDistance;
	}

	public int getyDistance() {
		return yDistance;
	}

	public void setyDistance(int yDistance) {
		this.yDistance = yDistance;
	}
	
	public float getFltLife() {
		return fltLife;
	}

	public void setFltLife(float fltLife) {
		this.fltLife = fltLife;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
