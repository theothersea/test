package com.iscg7424.assignment3.drawingThreads;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iscg7424.assignment3.R;

public class ObjectForDrawingBomb extends ObjectForDrawing {
	
	int canvasWidth = 0;
	int canvasHeight = 0;
	int xDistance = 1;
	int yDistance = 100;
	Random rnd = new Random();
	int ranX = 0;
	int ranY = 0;
	int marginX = 50;
	int marginY = 150;
	Bitmap bmpBomb;
	
	float fltDegree = 0;
	float fltRadian = 0;
	float fltSpeed = 1;
	float fltDegreeToRadian = (float) (Math.PI/180);
	float fltCenterX = 500;
	float fltCenterY = 200;
	float fltRadius = 100;
	float fltScale = (float) 0.75;
	
	public ObjectForDrawingBomb()
	{
		super();
	}
	
	public ObjectForDrawingBomb(Resources resources, int canvasWidth, int canvasHeight, 
			float bombX, float bombY)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		bmpBomb = BitmapFactory.decodeResource(resources, R.drawable.bomb1);
		bmpBomb = Bitmap.createScaledBitmap(bmpBomb, 50, 50, false);
		
		/*fltCenterX = canvasWidth/2;// + rnd.nextInt(500) - 250;
		fltCenterY = canvasHeight/3 + 40; //+ rnd.nextInt(80) - 40;
		fltRadius = rnd.nextInt(100) + 100;
		fltScale = (float) ((rnd.nextInt(100) + 20) * 0.01);
		if (rnd.nextInt(2)==0) fltSpeed *= -1;
		fltSpeed *= rnd.nextFloat();*/
		setX(bombX);
		setY(bombY);
		setBitmap(bmpBomb);
	
	}
	
	public void move()
	{
		//ranX = rnd.nextInt(2) - 1;
		ranY = rnd.nextInt(2) - 2; //drop
		setX(getX() + xDistance * ranX);
		setY(getY()+(float)1);
		
		if (rnd.nextInt(100) == 0) 
		{ 
			xDistance *= -1; 
		}
		if (rnd.nextInt(100) == 0) 
		{ 
			yDistance *= -1; 
		}
		
		
		/*if((getX() > this.canvasWidth - getBitmap().getWidth() - marginX) || 
				(getX() < marginX))
			xDistance *= -1;*/
		
		if((yDistance)==this.canvasHeight - 50)
		{
			setY(getY()+ (float)1);
		}
		  /*fltDegree += fltSpeed;
		  //fltDegree = (fltDegree%180+180)%180;
		  //fltRadian = fltDegree*fltDegreeToRadian;
		  setX((float)(fltCenterX));//+Math.cos(fltRadian)*fltRadius));
		  setY((float)(fltCenterY+Math.sin(fltRadian)*fltRadius)*fltScale);*/
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
