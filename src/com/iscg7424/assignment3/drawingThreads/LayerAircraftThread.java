package com.iscg7424.assignment3.drawingThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.iscg7424.assignment3.R;
import com.iscg7424.assignment3.drawingThreads.DrawThread.GameStat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class LayerAircraftThread extends LayerThread implements Touchable{
	private final int AIRCRAFT_SPEED = 4;
	private final int LASER_GUN_SPEED = 6;
	private static LayerAircraftThread layerAircraftThread;
	
	public static LayerAircraftThread getInstance(Resources resources, int canvasWidth,
			int canvasHeight, int layerNumber){
		
		if(LayerAircraftThread.layerAircraftThread==null)
			LayerAircraftThread.layerAircraftThread  = new LayerAircraftThread( resources, canvasWidth, canvasHeight,  layerNumber);
		
		return LayerAircraftThread.layerAircraftThread;
						
	}
	
	public static LayerAircraftThread getInstance(){
		return LayerAircraftThread.layerAircraftThread;
	}
	
	private Timer missileLaunchTimer;
	private boolean isLaunch;
	private List<ObjectForDrawing> missileList; //missiles that are waiting to be launched.
	private List<ObjectForDrawing> launchedMissileList;//missiles that have been launched.
	
	private float ctrlKnobRadius;
	private float ctrlBaseRadius;
	
	private float ctrlBaseCentralX;
	private float ctrlBaseCentralY;
	private float ctrlKnobCentralX;
	private float ctrlKnobCentralY;
	
	private final int CONTROL_SENSITIVITY = 5;
	
	public boolean isGoingRight;
	public boolean isGoingLeft;
	public boolean isGoingUp;
	public boolean isGoingDown;
	
	private float ctrlPadOrginX;
	private float ctrlPadOrginY;
	private float ctrlPadPointX;
	private float ctrlPadPointY;
	
	public boolean isDrawCtrlPad = false;
	
	private ObjectForDrawing gamever;
	
	ObjectForDrawing aircraft;
	private Bitmap bmpAircraft;
	private Bitmap bmpAircraftLeft;
	private Bitmap bmpAircraftRight;
	private ObjectForDrawing explosion;
	private Bitmap bmpExplosion;
	
	private ObjectForDrawing knob;
	private ObjectForDrawing base;
	private Bitmap bmpMissile;
	private ObjectForDrawing shield;
	protected float getCtrlPadOrginX() {
		return ctrlPadOrginX;
	}

	public void setCtrlPadOrginX(float ctrlPadOrginX) {
		this.ctrlPadOrginX = ctrlPadOrginX - this.ctrlBaseRadius;
		this.ctrlBaseCentralX = ctrlPadOrginX;
	}

	protected float getCtrlPadOrginY() {
		return ctrlPadOrginY;
	}

	public void setCtrlPadOrginY(float ctrlPadOrginY) {
		this.ctrlPadOrginY = ctrlPadOrginY - this.ctrlBaseRadius;
		this.ctrlBaseCentralY = ctrlPadOrginY;
	}

	protected float getCtrlPadPointX() {
		return ctrlPadPointX;
	}

	public void setCtrlPadPointX(float ctrlPadPointX) {
		this.ctrlPadPointX = ctrlPadPointX - this.ctrlKnobRadius;
		this.ctrlKnobCentralX = ctrlPadPointX;
	}

	protected float getCtrlPadPointY() {
		return ctrlPadPointY;
	}

	public void setCtrlPadPointY(float ctrlPadPointY) {
		this.ctrlPadPointY = ctrlPadPointY - this.ctrlKnobRadius;
		this.ctrlKnobCentralY = ctrlPadPointY;
	}

	
	public float getAircraftCentralX(){
		try{
			return this.aircraft.getX()+ (this.aircraft.getBitmap().getWidth()/2);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public float getAircraftCentralY(){
		try{
			return this.aircraft.getY()+ (this.aircraft.getBitmap().getHeight()/2);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	private LayerAircraftThread(Resources resources, int canvasWidth,
			int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight, layerNumber);

		this.gamever = new ObjectForDrawing();
		Bitmap bmpGameOver = BitmapFactory.decodeResource(this.resources, R.drawable.gameover);
		bmpGameOver = Bitmap.createScaledBitmap(bmpGameOver, this.getPercentageWidth(0.4f), this.getPercentageHeight(0.45f), true);
		this.gamever.setBitmap(bmpGameOver);
		this.gamever.setX((this.canvasWidth-bmpGameOver.getWidth())/2);
		this.gamever.setY((this.canvasHeight-bmpGameOver.getHeight())/2);
		
		this.aircraft = new ObjectForDrawing();
		this.bmpAircraft = BitmapFactory.decodeResource(this.resources, R.drawable.aircraft);
		this.bmpAircraftLeft = BitmapFactory.decodeResource(this.resources, R.drawable.aircraft_left);
		this.bmpAircraftRight = BitmapFactory.decodeResource(this.resources, R.drawable.aircraft_right);
		
		this.bmpAircraft = Bitmap.createScaledBitmap(bmpAircraft, this.getPercentageWidth(0.05f), this.getPercentageWidth(0.05f), true);
		this.bmpAircraftLeft = Bitmap.createScaledBitmap(bmpAircraftLeft, this.getPercentageWidth(0.05f), this.getPercentageWidth(0.05f), true);
		this.bmpAircraftRight = Bitmap.createScaledBitmap(bmpAircraftRight, this.getPercentageWidth(0.05f), this.getPercentageWidth(0.05f), true);
		this.aircraft.setBitmap(bmpAircraft);
		this.aircraft.setX(this.canvasWidth/2);
		this.aircraft.setY(this.canvasHeight-bmpAircraft.getHeight());
	//	this.objectsForDrawing.add(aircraft);
		this.bmpExplosion = BitmapFactory.decodeResource(this.resources, R.drawable.crash);
		this.bmpExplosion = Bitmap.createScaledBitmap(this.bmpExplosion, 20, 20, true);
		this.explosion = new ObjectForDrawing();
		this.explosion.setBitmap(bmpExplosion);
		
		this.shield = new ObjectForDrawing();
		Bitmap bmpShield= BitmapFactory.decodeResource(this.resources, R.drawable.shield);
		bmpShield = Bitmap.createScaledBitmap(bmpShield, this.bmpAircraft.getWidth(), this.bmpAircraft.getHeight(), true);
		this.shield.setBitmap(bmpShield);
		this.shield.getPaint().setAlpha(100);
		
		this.knob = new ObjectForDrawing();
		Bitmap bmpKnob = BitmapFactory.decodeResource(this.resources, R.drawable.control_knob);
		knob.setBitmap(bmpKnob);
		this.ctrlKnobRadius = bmpKnob.getWidth()/2;
		
		this.base = new ObjectForDrawing();
		Bitmap bmpBase = BitmapFactory.decodeResource(this.resources, R.drawable.control_base);
		base.setBitmap(bmpBase);
		this.ctrlBaseRadius = bmpBase.getWidth()/2;
		
		this.missileLaunchTimer = new Timer();	
		this.launchedMissileList = new ArrayList<ObjectForDrawing>();
		this.missileList = new ArrayList<ObjectForDrawing>();
		this.bmpMissile = BitmapFactory.decodeResource(this.resources, R.drawable.laser);
		bmpMissile = Bitmap.createScaledBitmap(bmpMissile, 12, 28, true);
		for(int i=0; i<40; i++){
			ObjectForDrawing missile = new ObjectForDrawing();
			missile.setBitmap(bmpMissile);
			this.missileList.add(missile);
		}
		
	}

	@Override
	public boolean doTouchDetection(float x, float y) {
		synchronized(this.launchedMissileList){
			final float missileOffsetX = this.bmpMissile.getWidth()/2;
			final float missileOffsetY = this.bmpMissile.getHeight()/2; 
			
			final float distanceX = missileOffsetX+ LayerUFOsThread.getInstance().getChildUFOWidth()/2;
			final float distanceY = missileOffsetY+ LayerUFOsThread.getInstance().getChildUFOHeight()/2;
		//	LayerUFOsThread.getInstance().get
			
			for(int i = 0; i<this.launchedMissileList.size(); i++){
				ObjectForDrawing missile = this.launchedMissileList.get(i);
				
				float xOffset = Math.abs(x-(missile.getX()+missileOffsetX));
				float yOffset = Math.abs(y-(missile.getY()+missileOffsetY));
						
				
				if (xOffset<=distanceX&&yOffset<=distanceY){
					
					this.missileList.add(this.launchedMissileList.remove(i));
					
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public float[] doPointTouchDetection(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	private Bitmap crashBm = null;
	public boolean isAircraftCrashSoundPlayed = false;
	public boolean isDrawGameOver = false;
	private class CrashTimerTask extends TimerTask{
		private Bitmap[] crashBms = new Bitmap[6];
		private CrashTimerTask(){
			crashBms[0] = BitmapFactory.decodeResource(LayerAircraftThread.this.resources, R.drawable.crasha);	
			crashBms[1] = BitmapFactory.decodeResource(LayerAircraftThread.this.resources, R.drawable.crashb);	
			crashBms[2] = BitmapFactory.decodeResource(LayerAircraftThread.this.resources, R.drawable.crashc);	
			crashBms[3] = BitmapFactory.decodeResource(LayerAircraftThread.this.resources, R.drawable.crashd);	
			crashBms[4] = BitmapFactory.decodeResource(LayerAircraftThread.this.resources, R.drawable.crashe);	
			crashBms[5] = BitmapFactory.decodeResource(LayerAircraftThread.this.resources, R.drawable.crashf);	
			
			LayerAircraftThread.this.crashBm = crashBms[0];
		}
		int count = 1;

		@Override
		public void run() {
			if(!LayerAircraftThread.this.isAircraftCrashSoundPlayed ){
				LayerThread.soundPool.play(LayerThread.airCraftExplosion, 0.1f, 0.1f, 0, 0, 1);
				LayerAircraftThread.this.isAircraftCrashSoundPlayed  = true;
			}
			
			if(this.count >= this.crashBms.length){
				LayerAircraftThread.this.crashBm = null;
				LayerAircraftThread.this.aircraft.setDrawMe(false);
				this.cancel();
				
				Timer timer = new Timer();
				timer.schedule(new TimerTask(){

					@Override
					public void run() {
						LayerAircraftThread.this.isDrawGameOver  = true;
					}}, 300);
				
				
				return;
			}
			LayerAircraftThread.this.crashBm = crashBms[count++];
		}
		
	}
	private class MissileLaunchTask extends TimerTask{
		@Override
		public void run() {
		
			if(DrawThread.gameStat==DrawThread.GameStat.LV1||
					DrawThread.gameStat==DrawThread.GameStat.LV2||
						DrawThread.gameStat==DrawThread.GameStat.LV3){
				LayerAircraftThread.this.isLaunch = true;
				
				LayerThread.soundPool.play(LayerThread.laser, 0.05f, 0.05f, 0, 0, 1);
			}else{
			//	this.cancel();
			}		
		}
	};
	@Override
	public void run() {
		
		
		final float RIGHT_BOUNDARY = this.canvasWidth-this.aircraft.getBitmap().getWidth();
		final float BOTTOM_BOUNDARY = this.canvasHeight-this.aircraft.getBitmap().getHeight();
		final float AIRCRAFT_MIDDLE_OFFSET = 5+this.aircraft.getBitmap().getWidth()/2-this.missileList.get(0).getBitmap().getWidth();
		float largest = 10.0f;
		
		Timer crashDrawTimer = new Timer();
		while (true){
			if(this.isDoStop) return;
			
			if(DrawThread.gameStat == DrawThread.GameStat.QUIT){
				break;
			}
			if(DrawThread.gameStat == DrawThread.GameStat.OPENING){
				if(largest<=1){
					this.aircraft.setBitmap(this.bmpAircraft);
					DrawThread.gameStat=DrawThread.GameStat.LV1;
					
					this.missileLaunchTimer.schedule(new MissileLaunchTask(), 100, 500);
					
					continue;
				}
					
				largest-=0.2f;	
				Matrix matrix = new Matrix();
				matrix.postScale(largest, largest);
				//===========================================================================
				//work out the updated face bitmap 
				Bitmap bm = Bitmap.createBitmap(this.bmpAircraft,0,0,this.bmpAircraft.getWidth(),this.bmpAircraft.getHeight(),matrix,true);		
				
				this.aircraft.setBitmap(bm);
			//	float x = this.aircraft.getX();
				float y = this.aircraft.getY();
				
				int offsetY = 2;
		//		this.aircraft.setX(x+offsetX);
				this.aircraft.setY(y-offsetY);
				
				this.objectsForDrawing.add(this.aircraft);
					
				this.addToArrayAndSleep();
				
				continue;
			}else if(DrawThread.gameStat == GameStat.GAMEOVER){		
				this.objectsForDrawing.clear();
				this.aircraft.setBitmap(this.crashBm);
				this.objectsForDrawing.add(this.aircraft);
				crashDrawTimer.schedule(new CrashTimerTask(), 25, 500);
			
				if(this.isDrawGameOver){
					this.objectsForDrawing.add(this.gamever);
				}
				this.addToArrayAndSleep();
			}else{
			
			this.objectsForDrawing.clear();
		//	double movingAngle = 0;
			//===========determine the moving direction===========================
			if(this.isDrawCtrlPad){	
		//		LayerAircraftThread.getInstance().setShielded(true);
				if(this.ctrlKnobCentralX>this.ctrlBaseCentralX+CONTROL_SENSITIVITY){
					this.isGoingRight = true;
					this.isGoingLeft = false;
					this.aircraft.setBitmap(this.bmpAircraftRight);
				}else if(this.ctrlKnobCentralX<this.ctrlBaseCentralX-CONTROL_SENSITIVITY){
					this.isGoingRight = false;
					this.isGoingLeft = true;
					this.aircraft.setBitmap(this.bmpAircraftLeft);
				}else{
					this.isGoingRight = false;
					this.isGoingLeft = false;
					this.aircraft.setBitmap(this.bmpAircraft);
				}
				
				if(this.ctrlKnobCentralY>this.ctrlBaseCentralY+CONTROL_SENSITIVITY){
					this.isGoingDown = true;
					this.isGoingUp = false;
				}else if(this.ctrlKnobCentralY<this.ctrlBaseCentralY-CONTROL_SENSITIVITY){
					this.isGoingDown = false;
					this.isGoingUp = true;
				}else{
					this.isGoingDown = false;
					this.isGoingUp = false;
				}
				
				//===============================================================
				float CTRLPAD_OFFSET_X = (this.ctrlBaseCentralX-this.ctrlKnobCentralX);
				float CTRLPAD_OFFSET_Y = (this.ctrlBaseCentralY-this.ctrlKnobCentralY);
				
				double a2 = Math.pow(CTRLPAD_OFFSET_X,2);
				double b2 = Math.pow(CTRLPAD_OFFSET_Y,2);
				double distance = Math.pow(a2+b2, 0.5);		
				
				if(distance>ctrlBaseRadius){
					double  r = Math.atan(Math.abs(CTRLPAD_OFFSET_Y)/Math.abs(CTRLPAD_OFFSET_X));
				//	movingAngle = r;
					//	double degree = d*(180/Math.PI);		
					float deltaX = Math.abs((float) (Math.cos(r) * this.ctrlBaseRadius));
					float deltaY = Math.abs((float) (Math.sin(r) * this.ctrlBaseRadius));
					
					deltaX-=15;
					deltaY-=15;
				//	Log.v("distance", x+"--"+y);
					if(this.isGoingLeft){
						this.knob.setX(this.ctrlBaseCentralX-this.ctrlKnobRadius-deltaX);
					}else if (this.isGoingRight){
						this.knob.setX(this.ctrlBaseCentralX-this.ctrlKnobRadius+deltaX);
					}else{
						this.knob.setX(this.ctrlBaseCentralX-this.ctrlKnobRadius);
					}
					
					if(this.isGoingUp){
						this.knob.setY(this.ctrlBaseCentralY-this.ctrlKnobRadius-deltaY);
					}else if (this.isGoingDown){
						this.knob.setY(this.ctrlBaseCentralY-this.ctrlKnobRadius+deltaY);
					}else{
						this.knob.setY(this.ctrlBaseCentralY-this.ctrlKnobRadius);		
					}
				}else{
					this.knob.setX(this.ctrlPadPointX);
					this.knob.setY(this.ctrlPadPointY);
				}
				
				this.base.setX(this.ctrlPadOrginX);
				this.base.setY(this.ctrlPadOrginY);
				
				
				
			//	this.objectsForDrawing.add(aircraft);
				this.objectsForDrawing.add(base);
				this.objectsForDrawing.add(knob);
			
			}else{
				this.isGoingDown = false;
				this.isGoingUp = false;
				this.isGoingLeft = false;
				this.isGoingRight = false;
				this.aircraft.setBitmap(this.bmpAircraft);
			}
			
			//determine the position of aircraft
			double distanceX = 0;
			double distanceY = 0;
			if(this.isGoingRight){
			//	distanceX = Math.abs(Math.cos(movingAngle)*this.AIRCRAFT_SPEED);
				distanceX = this.AIRCRAFT_SPEED;
			}else if (this.isGoingLeft){
			//	distanceX = -Math.abs(Math.cos(movingAngle)*this.AIRCRAFT_SPEED);
				distanceX = -this.AIRCRAFT_SPEED;
			}
			
			if(this.isGoingDown){
			//	distanceY = Math.abs(Math.sin(movingAngle)*this.AIRCRAFT_SPEED);
				distanceY = this.AIRCRAFT_SPEED;
			}else if (this.isGoingUp){
			//	distanceY = -Math.abs(Math.sin(movingAngle)*this.AIRCRAFT_SPEED);
				distanceY = -this.AIRCRAFT_SPEED;
			}
			
			float deltaX = aircraft.getX()+(float)distanceX;	
			if(deltaX <0) deltaX = 0;
			else if(deltaX>RIGHT_BOUNDARY)
				deltaX=(RIGHT_BOUNDARY);
			
			float deltaY = aircraft.getY()+(float)distanceY;
			if(deltaY<0) deltaY = 0;
			else if (deltaY>BOTTOM_BOUNDARY)
				deltaY = (BOTTOM_BOUNDARY);
			
			aircraft.setX(deltaX);
			aircraft.setY(deltaY);
			
			
			synchronized (this.launchedMissileList) {
				if (this.isLaunch && this.missileList.size() > 0) {
					ObjectForDrawing missile = this.missileList.remove(this.missileList.size() - 1);
					missile.setDrawMe(true);
					missile.setX(this.aircraft.getX() + AIRCRAFT_MIDDLE_OFFSET);
					missile.setY(this.aircraft.getY() + missile.getBitmap().getHeight());
					this.launchedMissileList.add(missile);

					this.isLaunch = false;
				}

				for (int i = 0; i < this.launchedMissileList.size(); i++) {
					ObjectForDrawing missile = this.launchedMissileList.get(i);

					if (missile.getY() + missile.getBitmap().getHeight() < 0) {
						this.missileList.add(this.launchedMissileList.remove(i));

					} else {
						float y = missile.getY();
						y = y - LASER_GUN_SPEED;
						missile.setY(y);
						
						if(DrawThread.gameStat == GameStat.LV3){
							if (missile.isDrawMe()){
								if(LayerMotherUFOThread.getInstance().doTouchDetection(missile.getX()+missile.getBitmap().getWidth()/2, y)){
									missile.setDrawMe(false);
									LayerMotherUFOThread.getInstance().lifeBar.resizeLifeBar(-0.05f);
								}else{
									this.objectsForDrawing.add(missile);
								}
							}
						}else{
							this.objectsForDrawing.add(missile);
						}
						
						
					}
				}
			}
			
			this.objectsForDrawing.add(this.aircraft);
			if(this.isShielded){
				this.shield.setX(this.aircraft.getX());
				this.shield.setY(this.aircraft.getY());
				this.objectsForDrawing.add(this.shield);
			}
			if(LayerUFOsThread.getInstance().doTouchDetection(this.getAircraftCentralX(), this.getAircraftCentralY())){
				this.explosion.setX(this.aircraft.getX()+20);
				this.explosion.setY(this.aircraft.getY()+25);
				
				this.objectsForDrawing.add(this.explosion);
				LayerThread.vibrator.cancel();
				LayerThread.vibrator.vibrate(100);
				
			//	LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(-0.2f);
				
			}
			
			
			
			this.addToArrayAndSleep();
		}
		}
	}

	@Override
	public int doPointTouchDetection(List<ObjectForDrawing> objectList) {
		// TODO Auto-generated method stub
		return 0;
	}


	private boolean isShielded = false;
	private boolean isPowered = false;

	public boolean isShielded() {
		return isShielded;
	}

	private Timer shieldTimer = new Timer();
	private class ShieldTimerTask extends TimerTask{

		@Override
		public void run() {
			LayerAircraftThread.this.setShielded(false);
		}}
	
	public void setShielded(boolean isShielded) {
		this.isShielded = isShielded;
		
		if(this.isShielded){
			this.shieldTimer.cancel();
			this.shieldTimer = new Timer();
			this.shieldTimer.schedule(new ShieldTimerTask() , 5000);
		}
	}

	public boolean isPowered() {
		
		return isPowered;
	}

	public void setPowered(boolean isPowered) {
	//	if (this.isPowered==isPowered) return;
		
		if (isPowered){
			Log.v("powerup", "1");
			this.missileLaunchTimer.cancel();
			Log.v("powerup", "2");
			this.missileLaunchTimer = new Timer();
			Log.v("powerup", "3");
			MissileLaunchTask missileLaunchTask = new MissileLaunchTask();
			Log.v("powerup", "4");
			this.missileLaunchTimer.schedule(missileLaunchTask, 100, 200);
			Log.v("powerup", "5");
			
			this.missileLaunchTimer.schedule(new TimerTask(){

				@Override
				public void run() {
					LayerAircraftThread.this.setPowered(false);
				}}, 5000);
		}else{
			Log.v("powerup", "1");
			this.missileLaunchTimer.cancel();
			Log.v("powerup", "2");
			this.missileLaunchTimer = new Timer();
			Log.v("powerup", "3");
			MissileLaunchTask missileLaunchTask = new MissileLaunchTask();
			Log.v("powerup", "4");
			this.missileLaunchTimer.schedule(missileLaunchTask, 100, 500);
			Log.v("powerup", "5");
			
		}
		this.isPowered = isPowered;
	}
	
}
