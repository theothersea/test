package com.iscg7424.assignment3.drawingThreads;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iscg7424.assignment3.R;

public class ObjectForDrawingSoldier extends ObjectForDrawing{

	int canvasWidth = 0;
	int canvasHeight = 0;
	int xDistance = 1;
	int yDistance = 1;
	Random rnd = new Random();
	int ranX = 0;
	int ranY = 0;
	int marginX = 50;
	int marginY = 150;
	Bitmap bmpSoldier;


	public ObjectForDrawingSoldier()
	{
		super();
	}
	
	public ObjectForDrawingSoldier(Resources resources, int canvasWidth, int canvasHeight)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		bmpSoldier = BitmapFactory.decodeResource(resources, R.drawable.soldier);
		bmpSoldier = Bitmap.createScaledBitmap(bmpSoldier, 30, 45, false);
		// Default position of Soldier
		setX(canvasWidth /2 + rnd.nextInt(100) - 50);
		setY(canvasHeight - 60);
		setBitmap(bmpSoldier);
	}
	
	public void move()
	{
		ranX = rnd.nextInt(2) - 1;
		ranY = rnd.nextInt(2) - 1;
		setX(getX() + xDistance * ranX);
		setY(getY());
		
		if (rnd.nextInt(100) == 0) { xDistance *= -1; }
		if (rnd.nextInt(100) == 0) { yDistance *= -1; }
		
		if((getX() > this.canvasWidth - getBitmap().getWidth() - marginX) || 
				(getX() < marginX))
			xDistance *= -1;

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

}
