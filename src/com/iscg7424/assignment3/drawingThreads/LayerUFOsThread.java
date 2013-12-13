package com.iscg7424.assignment3.drawingThreads;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.iscg7424.assignment3.R;
import com.iscg7424.assignment3.drawingThreads.DrawThread.GameStat;

public class LayerUFOsThread extends LayerThread{
	private static LayerUFOsThread layerUFOsThread;
	
	public static LayerUFOsThread getInstance(Resources resources, int canvasWidth,int canvasHeight, int layerNumber){
		if (LayerUFOsThread.layerUFOsThread==null){
			LayerUFOsThread.layerUFOsThread = new LayerUFOsThread( resources,  canvasWidth, canvasHeight,  layerNumber);
		}
		return LayerUFOsThread.layerUFOsThread;
	}
	
	public static LayerUFOsThread getInstance(){
		return LayerUFOsThread.layerUFOsThread;
	}
	
	Bitmap bmpMotherUFO;
	float poxMotherUFOX = 0;
	float poxMotherUFOY = 0;
	int motherUFOWidth = 0;
	int motherUFOHeight = 0;
	float scale = 1.0f;
	int intMaxNumberOfUFO = 15;
	int intCreatedNumberOfUFO = 0;
	int intDestroyedNumberOfUFO = 0;
	int intNumberOfBullet = 0;
	int intNumberOfVolcanicBlock = 0;
	Random rnd = new Random();
	ObjectForDrawing ofdMotherUFO;

	float fltDegree = 0;
	float fltRadian = 0;
	float fltSpeed = 0;
	float fltDegreeToRadian = (float) (Math.PI/180);
	float fltRadius = 0;

	private boolean isMotherUFOStartMove = false;

	boolean blnLv1half = false;
	boolean blnLv2half = false;
	boolean blnLv3half = false;
	
	private LayerUFOsThread(Resources resources, int canvasWidth,int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight,layerNumber);

		motherUFOWidth = canvasWidth / 4;
		motherUFOHeight = canvasHeight / 4;
		if (motherUFOWidth < 100)
		{
			motherUFOWidth = 100;
		}
		if (motherUFOHeight < 50)
		{
			motherUFOHeight = 50;
		}
		
		ofdMotherUFO =  new ObjectForDrawing();
		bmpMotherUFO = BitmapFactory.decodeResource(this.resources, R.drawable.ufo018);
		bmpMotherUFO = Bitmap.createScaledBitmap(bmpMotherUFO, motherUFOWidth, motherUFOHeight, false);
		ofdMotherUFO.setBitmap(bmpMotherUFO);
		this.objectsForDrawing.add(ofdMotherUFO);

		for (int i=1; i<9; i++)
		{
			ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/9*i, canvasHeight*0.3f, 1);
			this.objectsForDrawing.add(mOfdChildUFO);
			intCreatedNumberOfUFO++;
		}
		for (int i=1; i<8; i++)
		{
			ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/8*i, canvasHeight*0.4f, 1);
			this.objectsForDrawing.add(mOfdChildUFO);
			intCreatedNumberOfUFO++;
		}
	}
	
	public void init()
	{
		intCreatedNumberOfUFO = 0;
		intDestroyedNumberOfUFO = 0;
	}

	@Override
	public void run() {
		while(true){
			if(this.isDoStop) return;
			
			if(DrawThread.gameStat == GameStat.GAMEOVER) break;
			
			long startTime = System.currentTimeMillis();
			
			// Opening appear the mother UFO
			if(DrawThread.gameStat == DrawThread.GameStat.OPENING){
				// Mother UFO
				this.ofdMotherUFO = this.objectsForDrawing.get(0);
				poxMotherUFOX = (float)((canvasWidth - bmpMotherUFO.getWidth()) * 0.5F);
				ofdMotherUFO.setX(poxMotherUFOX);
				if (poxMotherUFOY < (float)((canvasHeight - bmpMotherUFO.getHeight()) * 0.1F))
				{
					poxMotherUFOY += 1.0;
				}
				ofdMotherUFO.setY(poxMotherUFOY);
				ofdMotherUFO.getPaint().setAlpha(100);
				this.addToArrayAndSleep();
				continue;
			}
			
			// Level1 is cleared
			if (intDestroyedNumberOfUFO == intCreatedNumberOfUFO && DrawThread.gameStat == DrawThread.GameStat.LV1)
			{
				if (!blnLv1half)
				{
					init();
					for (int i=1; i<9; i++)
					{
						ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/9*i, canvasHeight*0.3f, 2);
						mOfdChildUFO.getPaint().setAlpha(100);
						this.objectsForDrawing.add(mOfdChildUFO);
						intCreatedNumberOfUFO++;
					}
					for (int i=7; i>=1; i--)
					{
						ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/8*i, canvasHeight*0.4f, 2);
						mOfdChildUFO.getPaint().setAlpha(100);
						this.objectsForDrawing.add(mOfdChildUFO);
						intCreatedNumberOfUFO++;
					}
					
					for (int i=0; i<this.objectsForDrawing.size(); i++)
					{
						if (objectsForDrawing.get(i).getClass() == ObjectForDrawingUFO.class)
						{
							// Child UFOs
							ObjectForDrawingUFO ofdChildUFO = (ObjectForDrawingUFO)this.objectsForDrawing.get(i);
							ofdChildUFO.getPaint().setAlpha(255);
						}
					}
					
					blnLv1half = true;
				}
				
				if (bmpMotherUFO.getWidth() < 10)
				{
					// disappear motherUFO
					this.ofdMotherUFO.getPaint().setAlpha(0);
					// change to Lv2
					DrawThread.gameStat=DrawThread.GameStat.LV2;
					//added by Tony , change background music
					LayerThread.mediaPlayer.stop();
					LayerThread.mediaPlayer = LayerThread.mediaPlayerLv2;
					LayerThread.mediaPlayer.setLooping(true);
					LayerThread.mediaPlayer.start();
					
					// initialize
					init();
					for (int i=1; i<9; i++)
					{
						ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/9*i, canvasHeight*0.3f, 3);
						mOfdChildUFO.getPaint().setAlpha(100);
						this.objectsForDrawing.add(mOfdChildUFO);
						intCreatedNumberOfUFO++;
					}
					for (int i=7; i>=1; i--)
					{
						ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/8*i, canvasHeight*0.4f, 3);
						mOfdChildUFO.getPaint().setAlpha(100);
						this.objectsForDrawing.add(mOfdChildUFO);
						intCreatedNumberOfUFO++;
					}
					for (int i=0; i<this.objectsForDrawing.size(); i++)
					{
						if (objectsForDrawing.get(i).getClass() == ObjectForDrawingUFO.class)
						{
							// Child UFOs
							ObjectForDrawingUFO ofdChildUFO = (ObjectForDrawingUFO)this.objectsForDrawing.get(i);
							ofdChildUFO.getPaint().setAlpha(255);
						}
					}
					this.addToArrayAndSleep();
					continue;
				}
				
				if (scale > 0.5) { scale -= 0.01f; };
				Matrix matrix = new Matrix();
				matrix.postScale(scale, scale);
				bmpMotherUFO = Bitmap.createBitmap(this.bmpMotherUFO,0,0,this.bmpMotherUFO.getWidth(),this.bmpMotherUFO.getHeight(),matrix,true);	
				
				ofdMotherUFO.setBitmap(bmpMotherUFO);
				this.ofdMotherUFO = this.objectsForDrawing.get(0);
				this.ofdMotherUFO.getPaint().setAlpha(200);
				this.addToArrayAndSleep();
				continue;
			}
			
			// Level2 is cleared
			if (intDestroyedNumberOfUFO == intCreatedNumberOfUFO && DrawThread.gameStat == DrawThread.GameStat.LV2)
			{
				if (!blnLv2half)
				{
					// initialize
					init();
					for (int i=1; i<9; i++)
					{
						ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/9*i, canvasHeight*0.3f, 4);
						mOfdChildUFO.getPaint().setAlpha(100);
						this.objectsForDrawing.add(mOfdChildUFO);
						intCreatedNumberOfUFO++;
					}
					for (int i=7; i>=1; i--)
					{
						ObjectForDrawingUFO mOfdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, canvasWidth/8*i, canvasHeight*0.4f, 4);
						mOfdChildUFO.getPaint().setAlpha(100);
						this.objectsForDrawing.add(mOfdChildUFO);
						intCreatedNumberOfUFO++;
					}
					for (int i=0; i<this.objectsForDrawing.size(); i++)
					{
						if (objectsForDrawing.get(i).getClass() == ObjectForDrawingUFO.class)
						{
							// Child UFOs
							ObjectForDrawingUFO ofdChildUFO = (ObjectForDrawingUFO)this.objectsForDrawing.get(i);
							ofdChildUFO.getPaint().setAlpha(255);
						}
					}
					blnLv2half = true;
					this.addToArrayAndSleep();
					continue;
				}
				
				bmpMotherUFO = BitmapFactory.decodeResource(this.resources, R.drawable.ufo018);
				bmpMotherUFO = Bitmap.createScaledBitmap(bmpMotherUFO, motherUFOWidth, motherUFOHeight, false);
				ofdMotherUFO.setBitmap(bmpMotherUFO);
				for (int i=0; i<=255; i++)
				{
					try{
						Thread.sleep(20);
						}catch(InterruptedException e){
						}
					this.ofdMotherUFO.getPaint().setAlpha(i);
				}
				
				// change to Lv3
				DrawThread.gameStat=DrawThread.GameStat.LV3;
				//added by Tony , change background music
				LayerThread.mediaPlayer.stop();
				LayerThread.mediaPlayer = LayerThread.mediaPlayerLv3;
				LayerThread.mediaPlayer.setLooping(true);
				LayerThread.mediaPlayer.start();
				init();

				Timer timer= new Timer();
				timer.schedule(new TimerTask(){
					@Override
					public void run() {
						isMotherUFOStartMove = true;
					}}, 15000);

				this.ofdMotherUFO = this.objectsForDrawing.get(0);
				this.addToArrayAndSleep();
				continue;
			}
			
			// Mother UFO
			this.ofdMotherUFO = this.objectsForDrawing.get(0);
			
			// Level1
			if(DrawThread.gameStat == DrawThread.GameStat.LV1)
			{
				fltRadius = 30;
				fltSpeed = 0.5F;
				fltDegree += fltSpeed;
				fltDegree = (fltDegree%360+360)%360;
				fltRadian = fltDegree*fltDegreeToRadian;
				ofdMotherUFO.setY((float)(poxMotherUFOY+Math.cos(fltRadian)*fltRadius));
			}

			// Level3
			if(DrawThread.gameStat == DrawThread.GameStat.LV3 && isMotherUFOStartMove && LayerMotherUFOThread.getInstance().isMotherUFOMoving)
			{
				fltRadius = canvasWidth/2 - ofdMotherUFO.getBitmap().getWidth()/2;
				fltSpeed = 1.0F;
				fltDegree += fltSpeed;
				fltDegree = (fltDegree%360+360)%360;
				fltRadian = fltDegree*fltDegreeToRadian;
				ofdMotherUFO.setX((float)(poxMotherUFOX+Math.sin(fltRadian)*fltRadius));
			}

			// Level1
			if(DrawThread.gameStat == DrawThread.GameStat.LV1)
			{
				for (int i=0; i<objectsForDrawing.size(); i++)
				{
					if (objectsForDrawing.get(i).getClass() == ObjectForDrawingUFO.class)
					{
						ObjectForDrawingUFO ofdChildUFO = (ObjectForDrawingUFO)this.objectsForDrawing.get(i);
						ofdChildUFO.move(DrawThread.gameStat);

						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS)
						{
							if (LayerAircraftThread.getInstance().doTouchDetection(ofdChildUFO.getPosUFOX(), ofdChildUFO.getPosUFOY()))
							{
								ofdChildUFO.setState(ObjectForDrawingUFO.STATE_EXPLOSION);
							}
						}
						
						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS && rnd.nextInt(500) == 0 && blnLv1half)
						{
							ofdChildUFO.setBlnMoveable(true);
						}
						
						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS && rnd.nextInt(500) == 0)
						{
							float poxShipX = LayerAircraftThread.getInstance().getAircraftCentralX();
							float poxShipY = LayerAircraftThread.getInstance().getAircraftCentralY();
							ObjectForDrawingBullet ofdBullet =  new ObjectForDrawingBullet(resources, canvasWidth, canvasHeight, poxShipX, poxShipY);
							this.objectsForDrawing.add(ofdBullet);
							ofdBullet.setOfdChileUFO(ofdChildUFO);
							ofdBullet.start();
							intNumberOfBullet++;
						}

						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_DISAPPEAR)
						{
							objectsForDrawing.remove(i);
							ofdChildUFO = null;
							intDestroyedNumberOfUFO++;
						}
					}
				}
				
			}
			

			// Level2
			if(DrawThread.gameStat == DrawThread.GameStat.LV2)
			{
				for (int i=0; i<objectsForDrawing.size(); i++)
				{
					if (objectsForDrawing.get(i).getClass() == ObjectForDrawingUFO.class)
					{
						ObjectForDrawingUFO ofdChildUFO = (ObjectForDrawingUFO)this.objectsForDrawing.get(i);
						ofdChildUFO.move(DrawThread.gameStat);

						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS)
						{
							if (LayerAircraftThread.getInstance().doTouchDetection(ofdChildUFO.getPosUFOX(), ofdChildUFO.getPosUFOY()))
							{
								ofdChildUFO.setState(ObjectForDrawingUFO.STATE_EXPLOSION);
							}
						}
						
						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS && rnd.nextInt(300) == 0)
						{
							float poxShipX = LayerAircraftThread.getInstance().getAircraftCentralX();
							float poxShipY = LayerAircraftThread.getInstance().getAircraftCentralY();
							ObjectForDrawingBullet ofdBullet =  new ObjectForDrawingBullet(resources, canvasWidth, canvasHeight, poxShipX, poxShipY);
							this.objectsForDrawing.add(ofdBullet);
							ofdBullet.setOfdChileUFO(ofdChildUFO);
							ofdBullet.start();
							intNumberOfBullet++;
						}

						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_DISAPPEAR)
						{
							objectsForDrawing.remove(i);
							ofdChildUFO = null;
							intDestroyedNumberOfUFO++;
						}
					}
				}
			}
			

			// Level3
			if(DrawThread.gameStat == DrawThread.GameStat.LV3){
				// Child UFO
				if ((intCreatedNumberOfUFO < intMaxNumberOfUFO) && (rnd.nextInt(100) == 0))
				{
					ObjectForDrawingUFO ofdChildUFO =  new ObjectForDrawingUFO(resources, canvasWidth, canvasHeight, ofdMotherUFO);
					this.objectsForDrawing.add(ofdChildUFO);
					intCreatedNumberOfUFO++;
				}
				
				for (int i=0; i<objectsForDrawing.size(); i++)
				{
					if (objectsForDrawing.get(i).getClass() == ObjectForDrawingUFO.class)
					{
						ObjectForDrawingUFO ofdChildUFO = (ObjectForDrawingUFO)this.objectsForDrawing.get(i);
						ofdChildUFO.move(DrawThread.gameStat);

						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS)
						{
							if (LayerAircraftThread.getInstance().doTouchDetection(ofdChildUFO.getX(), ofdChildUFO.getY()))
//							if (LayerAircraftThread.getInstance().doTouchDetection(ofdChildUFO.getPosUFOX(), ofdChildUFO.getPosUFOY()))
							{
								ofdChildUFO.setState(ObjectForDrawingUFO.STATE_EXPLOSION);
							}
						}
						// Create a bullet
						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS && rnd.nextInt(100) == 0)
						{
							float poxShipX = LayerAircraftThread.getInstance().getAircraftCentralX();
							float poxShipY = LayerAircraftThread.getInstance().getAircraftCentralY();
							ObjectForDrawingBullet ofdBullet =  new ObjectForDrawingBullet(resources, canvasWidth, canvasHeight, poxShipX, poxShipY);
							this.objectsForDrawing.add(ofdBullet);
							ofdBullet.setOfdChileUFO(ofdChildUFO);
							ofdBullet.start();
							intNumberOfBullet++;
						}

						if (ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_DISAPPEAR)
						{
							objectsForDrawing.remove(i);
							ofdChildUFO = null;
							intDestroyedNumberOfUFO++;
						}
					}
				}
			}

			// Bullet
			for (int i=0; i<objectsForDrawing.size(); i++)
			{
				if (objectsForDrawing.get(i).getClass() == ObjectForDrawingBullet.class)
				{
					ObjectForDrawingBullet ofdBullet = (ObjectForDrawingBullet)this.objectsForDrawing.get(i);
					ofdBullet.move();
					if (!ofdBullet.isBolVisible())
					{
						objectsForDrawing.remove(i);
						ofdBullet = null;
					}
				}
			}			
			
			//added by Tony: set mother ufo transparent when lv1 & lv3
			if(DrawThread.gameStat != GameStat.LV3){
				ofdMotherUFO.getPaint().setAlpha(100);
			}else{
				ofdMotherUFO.getPaint().setAlpha(255);
			}
			
			this.addToArrayAndSleep();
			Log.v("vv", System.currentTimeMillis()-startTime+"");
		}
		boolean bolIgnore = false;
	}

	@Override
	public boolean doTouchDetection(float posAircraftX, float posAircraftY) {
		
		float posX = 0;
		float posY = 0;	
//		boolean bolIgnore = false;


		for (int i=0; i<this.objectsForDrawing.size(); i++)
		{
			try {
				if (objectsForDrawing.get(i).getClass() == ObjectForDrawing.class)
				{
					// Mother UFO
					ObjectForDrawing ofdMotherUFO = this.objectsForDrawing.get(i);
				}
				else if (objectsForDrawing.get(i).getClass() == ObjectForDrawingUFO.class)
				{
					// Child UFOs
					ObjectForDrawingUFO ofdChildUFO = (ObjectForDrawingUFO)this.objectsForDrawing.get(i);
					posX = ofdChildUFO.getPosUFOX();
					posY = ofdChildUFO.getPosUFOY();
					if ((isTouched(posX, posY, posAircraftX, posAircraftY)) && ofdChildUFO.getState() == ObjectForDrawingUFO.STATE_EXISTS)
					{
						ofdChildUFO.setState(ObjectForDrawingUFO.STATE_EXPLOSION);
						//added by Tony , decrease life bar when crash a childufo.
						LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(-0.1f);
						
						return true;
					}
				}
				else if (objectsForDrawing.get(i).getClass() == ObjectForDrawingBullet.class)
				{
					// Bullets from UFOs
					ObjectForDrawingBullet ofdBullet = (ObjectForDrawingBullet)this.objectsForDrawing.get(i);
					posX = ofdBullet.getPosBulletX();
					posY = ofdBullet.getPosBulletY();
					if (isTouched(posX, posY, posAircraftX, posAircraftY))
					{
						objectsForDrawing.remove(i);
						ofdBullet = null;
						//added by Tony , decrease life bar when hit by bullet;
						LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(-0.05f);
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

	public int getChildUFOWidth() {
		return ObjectForDrawingUFO.UFOWidth;
	}
	
	public int getChildUFOHeight() {
		return ObjectForDrawingUFO.UFOHeight;
	}
	
	public ObjectForDrawing getOfdMotherUFO() {
		return ofdMotherUFO;
	}

	public void setOfdMotherUFO(ObjectForDrawing ofdMotherUFO) {
		this.ofdMotherUFO = ofdMotherUFO;
	}
	
	public float getMotherCentralX() {
		return this.objectsForDrawing.get(0).getX() + this.objectsForDrawing.get(0).getBitmap().getWidth()/2;
	}
	
	public float getMotherCentralY() {
		return (float) ((this.objectsForDrawing.get(0).getY() + this.objectsForDrawing.get(0).getBitmap().getHeight()/2) * 1.55F);
	}

	//tracing slug position.
	
		public  float[] tracingSlug(float slugX, float slugY){	
			float[] result = new float[] {slugX, slugY};
			
			float slugSpeed = 1.0f;
			
			float airCraftX = LayerAircraftThread.getInstance().getAircraftCentralX()-20;
			float airCraftY = LayerAircraftThread.getInstance().getAircraftCentralY()-20;
			
			float deltaX = airCraftX - slugX;
			float deltaY = airCraftY - slugY;
			
			if(deltaX == 0)
			{
			    if( airCraftY >= slugY )             // slug goes down
			        deltaX = 0.0000001f;
			    else                                 // slug goes up
			        deltaX = -0.0000001f;
			}
			
			if(deltaY == 0)
			{
			    if(airCraftX >= slugX )             // slug goes right
			        deltaY = 0.0000001f;
			    else                                 // slug goes left
			        deltaY = -0.0000001f;
			}
			 

			double angle = Math.atan(Math.abs(deltaY/deltaX));          

			
			if(deltaX>0)
				result[0] += slugSpeed * Math.cos(angle);
			else
				result[0] -= slugSpeed * Math.cos(angle);
			
			if(deltaY>0)
				result[1] += slugSpeed * Math.sin(angle);
			else
				result[1] -= slugSpeed * Math.sin(angle);
			
			
			return result;
		}
}
