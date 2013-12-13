package com.iscg7424.assignment3.drawingThreads;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iscg7424.assignment3.R;
import com.iscg7424.assignment3.drawingThreads.DrawThread.GameStat;

public class ObjectForDrawingUFO extends ObjectForDrawing{

	int canvasWidth = 0;
	int canvasHeight = 0;
	static final int UFOWidth = 50;
	static final int UFOHeight = 30;
	Resources resources;
	int xDistance = 1;
	int yDistance = 1;
	Random rnd = new Random();
	int ranX = 0;
	int ranY = 0;
	int marginX = 50;
	int marginY = 200;
	Bitmap bmpChildUFO;
	Bitmap bmpCrashUFO;
	ObjectForDrawing ofdMotherUFO;
	int cntTransparence = 0;
	int cntExplosion = 0;

	boolean blnMoveable = false;
	boolean blnReverse = false;
	double dbElapsedTime = 0;
	float fltDistance = 0F;
	float poxDefaultX = 0F;
	float poxDefaultY = 0F;
	float poxTargetX = 0F;
	float poxTargetY = 0F;
	double dbInitialVelocity = 50;
	
	boolean blnLv1half = false;
	boolean blnLv2half = false;
	boolean blnLv3half = false;
	
	float fltDegree = 0;
	float fltRadian = 0;
	float fltSpeed = 1;
	float fltDegreeToRadian = (float) (Math.PI/180);
	float fltCenterX = 500;
	float fltCenterY = 200;
	float fltRadius = 100;
	float fltScale = (float) 0.75;

	int state = 0;
	public static final int STATE_TRANSPARENCE = 0;
    public static final int STATE_EXISTS = 1;
    public static final int STATE_EXPLOSION = 2;
    public static final int STATE_DISAPPEAR = 3;

    //added by Tony, fix the explosion sound.
    private boolean isExplsionSoundPlayed = false;
    
	public ObjectForDrawingUFO(Resources resources, int canvasWidth, int canvasHeight, float posX, float posY, int intStageDetail)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		if (intStageDetail == 1) {
			bmpChildUFO = BitmapFactory.decodeResource(resources, R.drawable.ufo003);
		} else if (intStageDetail == 2) {
			bmpChildUFO = BitmapFactory.decodeResource(resources, R.drawable.ufo004);
			blnLv1half = true;
		} else if (intStageDetail == 3) {
			bmpChildUFO = BitmapFactory.decodeResource(resources, R.drawable.ufo005);
		} else if (intStageDetail == 4) {
			bmpChildUFO = BitmapFactory.decodeResource(resources, R.drawable.ufo006);
			blnLv2half = true;
		}
		bmpChildUFO = Bitmap.createScaledBitmap(bmpChildUFO, UFOWidth, UFOHeight, false);
		bmpCrashUFO = BitmapFactory.decodeResource(resources, R.drawable.crash);
		bmpCrashUFO = Bitmap.createScaledBitmap(bmpCrashUFO, UFOWidth, UFOHeight, false);
		// Default position of UFO
		setBitmap(bmpChildUFO);
		setX(posX - bmpChildUFO.getWidth()/2);
		setY(posY - bmpChildUFO.getHeight()/2);
		state = STATE_EXISTS;
		
		// Move
		poxDefaultX = posX;
		poxDefaultY = posY;
		poxTargetX = rnd.nextInt(canvasWidth-100)+50;
		poxTargetY = rnd.nextInt(canvasHeight-100)+50;
		
		fltDistance = (float) Math.sqrt(Math.pow((posX-poxTargetX),2) + Math.pow((posY-poxTargetY),2));
	}
	
	public ObjectForDrawingUFO(Resources resources, int canvasWidth, int canvasHeight, ObjectForDrawing ofdMotherUFO)
	{
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
//		this.resources = resources;
		this.ofdMotherUFO = ofdMotherUFO;
		bmpChildUFO = BitmapFactory.decodeResource(resources, R.drawable.ufo002);
		bmpChildUFO = Bitmap.createScaledBitmap(bmpChildUFO, UFOWidth, UFOHeight, false);
		bmpCrashUFO = BitmapFactory.decodeResource(resources, R.drawable.crash);
		bmpCrashUFO = Bitmap.createScaledBitmap(bmpCrashUFO, UFOWidth, UFOHeight, false);
		// Default position of UFO
//		setX(canvasWidth /2 + rnd.nextInt(100) - 50);
//		setY(canvasHeight / 2 + rnd.nextInt(60) - 30);
		fltCenterX = canvasWidth/2 + rnd.nextInt(500) - 250;
		fltCenterY = canvasHeight/2 + rnd.nextInt(80) - 40;
		fltRadius = rnd.nextInt(100) + 150;
		fltScale = (float) ((rnd.nextInt(50) + 50) * 0.01);
		if (rnd.nextInt(2)==0) fltSpeed *= -1;
		fltSpeed *= rnd.nextFloat();
		setBitmap(bmpChildUFO);
		this.getPaint().setAlpha(100);
	}
	
	public void move(GameStat gameStat)
	{
//		ranX = rnd.nextInt(2) - 1;
//		ranY = rnd.nextInt(2) - 1;
//		setX(getX() + xDistance * ranX);
//		setY(getY() + yDistance * ranY);
//		
//		
//		if((getX() > this.canvasWidth - getBitmap().getWidth() - marginX) || 
//				(getX() < marginX))
//		{
//			xDistance *= -1;
//		}
//		else
//		{
//			if (rnd.nextInt(500) == 0) { xDistance *= -1; }
//		}
//		if((getY() > this.canvasHeight - getBitmap().getHeight() - marginY) || 
//				(getY() < ofdMotherUFO.getY() + ofdMotherUFO.getBitmap().getHeight()))
//		{
//			yDistance *= -1;
//		}
//		else
//		{
//			if (rnd.nextInt(500) == 0) { yDistance *= -1; }
//		}
		if (state == STATE_EXPLOSION)
		{
			if (cntExplosion++ > 50)
			{
				state = STATE_DISAPPEAR;
			}
			if(!this.isExplsionSoundPlayed){
				LayerThread.soundPool.play(LayerThread.childUFOExplosion, 0.1f, 0.1f, 0, 0, 1);
				this.isExplsionSoundPlayed = true;
			}
			setBitmap(bmpCrashUFO);
			
			
			
			return;
		}

		if (gameStat == DrawThread.GameStat.LV1 && blnMoveable)
		{
			if (Math.sqrt(Math.pow((getX()-poxTargetX),2) + Math.pow((getY()-poxTargetY),2)) < 5)
			{
				if (blnReverse)
				{
					blnMoveable = false;
					blnReverse = false;
					dbElapsedTime = 0;
					return;
				}
				dbElapsedTime = 0;
				poxTargetX = poxDefaultX;
				poxTargetY = poxDefaultY;
				poxDefaultX = getX();
				poxDefaultY = getY();
				fltDistance = (float) Math.sqrt(Math.pow((getX()-poxTargetX),2) + Math.pow((getY()-poxTargetY),2));
				blnReverse = true;
			}
			
			dbElapsedTime += 0.05;
			float x = (float) (poxDefaultX - (poxDefaultX-poxTargetX) * dbInitialVelocity * dbElapsedTime / fltDistance);
			float y = (float) (poxDefaultY - (poxDefaultY-poxTargetY) * dbInitialVelocity * dbElapsedTime / fltDistance);
			setX(x);
			setY(y);
		}

		if (gameStat == DrawThread.GameStat.LV2)
		{
			if (!blnLv2half)
			{
				if (Math.sqrt(Math.pow((getX()-poxTargetX),2) + Math.pow((getY()-poxTargetY),2)) < 5)
				{
					dbElapsedTime = 0;
					poxDefaultX = getX();
					poxDefaultY = getY();
					poxTargetX = rnd.nextInt(canvasWidth-100)+50;
					poxTargetY = rnd.nextInt(canvasHeight-100)+50;
					fltDistance = (float) Math.sqrt(Math.pow((getX()-poxTargetX),2) + Math.pow((getY()-poxTargetY),2));
				}
				
				dbElapsedTime += 0.05;
				float x = (float) (poxDefaultX - (poxDefaultX-poxTargetX) * dbInitialVelocity * dbElapsedTime / fltDistance);
				float y = (float) (poxDefaultY - (poxDefaultY-poxTargetY) * dbInitialVelocity * dbElapsedTime / fltDistance);
				setX(x);
				setY(y);
			}
			else
			{
				float airCraftX = LayerAircraftThread.getInstance().getAircraftCentralX()-20;
				float airCraftY = LayerAircraftThread.getInstance().getAircraftCentralY()-20;
				fltDistance = (float) Math.sqrt(Math.pow((getX()-airCraftX),2) + Math.pow((getY()-airCraftY),2));
				dbElapsedTime = 0.05;
				float x = (float) (getX() - (getX()-airCraftX) * dbInitialVelocity * dbElapsedTime / fltDistance);
				float y = (float) (getY() - (getY()-airCraftY) * dbInitialVelocity * dbElapsedTime / fltDistance);
				setX(x);
				setY(y);
			}
		}
		
		if (gameStat == DrawThread.GameStat.LV3)
		{
			fltDegree += fltSpeed;
			fltDegree = (fltDegree%360+360)%360;
			fltRadian = fltDegree*fltDegreeToRadian;
			setX((float)(fltCenterX+Math.cos(fltRadian)*fltRadius));
			setY((float)(fltCenterY+Math.sin(fltRadian)*fltRadius)*fltScale);

			if (state == STATE_TRANSPARENCE && cntTransparence++ > 100)
			{
				state = STATE_EXISTS;
				this.getPaint().setAlpha(255);
			}
		}
	}

	public float getPosUFOX() {
		return this.getX() + (bmpChildUFO.getHeight() / 2);
	}
	public float getPosUFOY() {
		return this.getY() + (bmpChildUFO.getWidth() / 2);
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
	
    public int getState() {
		return state;
	}
    
	public void setState(int state) {
		this.state = state;
	}

	public int getUFOWidth() {
		return UFOWidth;
	}

	public int getUFOHeight() {
		return UFOHeight;
	}
	
	public boolean isBlnMoveable() {
		return blnMoveable;
	}

	public void setBlnMoveable(boolean blnMoveable) {
		this.blnMoveable = blnMoveable;
	}
}