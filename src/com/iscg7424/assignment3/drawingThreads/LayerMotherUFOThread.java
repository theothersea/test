package com.iscg7424.assignment3.drawingThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.iscg7424.assignment3.R;
import com.iscg7424.assignment3.drawingThreads.DrawThread.GameStat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class LayerMotherUFOThread extends LayerThread implements Touchable {
	public boolean  nfh = true;
	
	public static LayerMotherUFOThread layerMotherUFOThread;
	
	public static LayerMotherUFOThread getInstance(){
		return LayerMotherUFOThread.layerMotherUFOThread;
	}
	
	public static LayerMotherUFOThread getInstance(Resources resources, int canvasWidth,
			int canvasHeight, int layerNumber) {
		
		if (LayerMotherUFOThread.layerMotherUFOThread==null){
			LayerMotherUFOThread.layerMotherUFOThread = new LayerMotherUFOThread(resources, canvasWidth, canvasHeight, layerNumber);
		}
		
		return LayerMotherUFOThread.layerMotherUFOThread;
	}

	private ObjectForDrawingTracingSlug tracingSlug;
	private ObjectForDrawing icLauncher;
	private List<GunBullet> machineGunBullets;  
	private List<GunBullet> machineGunBulletCase;
	private List<GunBullet[]> shotGunBullets;
	private List<GunBullet[]> shotGunBulletCase;
	
	public ObjectForDrawingMotherUFOLifeBar lifeBar = new ObjectForDrawingMotherUFOLifeBar(this.canvasWidth/2f, 20);
	private ObjectForDrawing crash;
	private ArrayList<ObjectForDrawing> crashes;
	private ObjectForDrawing wonLogo;
	
	private class GunBullet extends ObjectForDrawing{
		private double angle;
		public int xDirect;
		public int yDirect;
		
		public double getAngle() {
			return angle;
		}

		public void setAngle(double angle) {
			this.angle = angle;
		}
		
		
		
	}
	
	private LayerMotherUFOThread(Resources resources, int canvasWidth,
			int canvasHeight, int layerNumber) {
		super(resources, canvasWidth, canvasHeight, layerNumber);

		Bitmap bmpTracingSlug = BitmapFactory.decodeResource(this.resources, R.drawable.light_ball);
	//	Bitmap aircraftBm = LayerAircraftThread.getInstance().aircraft.getBitmap();
	//	bmpTracingSlug = Bitmap.createScaledBitmap(bmpTracingSlug, aircraftBm.getWidth()*2, aircraftBm.getHeight()*2, true);
		bmpTracingSlug = Bitmap.createScaledBitmap(bmpTracingSlug, this.getPercentageWidth(0.08f), this.getPercentageWidth(0.08f), true);
		
		this.tracingSlug = new ObjectForDrawingTracingSlug();
		this.tracingSlug.setBitmap(bmpTracingSlug);		
		
		Bitmap bmpIcLauncher = BitmapFactory.decodeResource(this.resources, R.drawable.iclauncher);
		bmpIcLauncher = Bitmap.createScaledBitmap(bmpIcLauncher, 360, 600, true);
		this.icLauncher = new ObjectForDrawing();
		this.icLauncher.setBitmap(bmpIcLauncher);
		 
		Bitmap bmpMachineGunBullet = BitmapFactory.decodeResource(this.resources, R.drawable.lightning);
	//	bmpMachineGunBullet = Bitmap.createScaledBitmap(bmpMachineGunBullet, 10, 10, true);
		this.machineGunBullets = new ArrayList<GunBullet>();  
		this.machineGunBulletCase = new ArrayList<GunBullet>();
		for(int i=0; i<25; i++){
			GunBullet machineGunBullet = new GunBullet();
			machineGunBullet.setBitmap(bmpMachineGunBullet);
			this.machineGunBulletCase.add(machineGunBullet);
		}
		
		Bitmap bmpShotGunBullet = BitmapFactory.decodeResource(this.resources, R.drawable.slug);
		bmpShotGunBullet = Bitmap.createScaledBitmap(bmpShotGunBullet, 25, 25, true);
		this.shotGunBullets = new ArrayList<GunBullet[]>();
		this.shotGunBulletCase = new ArrayList<GunBullet[]>();
		for(int i=0; i< 20 ; i++){
			GunBullet[] shotGunBullets = new GunBullet[7];
			for(int j=0; j<shotGunBullets.length; j++){
				shotGunBullets[j] = new GunBullet();
				shotGunBullets[j].setAngle(j*30);
				shotGunBullets[j].setBitmap(bmpShotGunBullet);
			}
			
			this.shotGunBulletCase.add(shotGunBullets);
		}

		this.lifeBar.setX(20);
		this.lifeBar.setY(20);
		
		Bitmap bmpCrash = BitmapFactory.decodeResource(this.resources, R.drawable.crash);
		this.crashes = new ArrayList<ObjectForDrawing>();
		Random rnd = new Random();
		for(int i=0; i< 15; i++){
			crashes.add(new ObjectForDrawing());
			crashes.get(i).setBitmap(Bitmap.createScaledBitmap(bmpCrash, rnd.nextInt(80)+20, rnd.nextInt(80)+20, true));
		}

		this.wonLogo = new ObjectForDrawing();
		Bitmap bmpWonLogo = BitmapFactory.decodeResource(this.resources, R.drawable.won);
		bmpWonLogo = Bitmap.createScaledBitmap(bmpWonLogo, (int)this.canvasWidth/2, (int)(this.canvasHeight*0.4f), true);
		this.wonLogo.setBitmap(bmpWonLogo);
		this.wonLogo.setX((this.canvasWidth-bmpWonLogo.getWidth())/2);
		this.wonLogo.setY(this.canvasHeight*0.1f);
	}

	@Override
	public boolean doTouchDetection(float x, float y) {
		float ufoX = LayerUFOsThread.getInstance().getMotherCentralX();
		float ufoY = LayerUFOsThread.getInstance().getMotherCentralY();
		
		if(ufoY-y>=LayerUFOsThread.getInstance().motherUFOHeight/2/2&&Math.abs(x-ufoX)<LayerUFOsThread.getInstance().motherUFOWidth/2)
			return true;
//		
//		double distance = Math.pow((Math.pow((x-ufoX),2)+Math.pow((y-ufoY),2)),0.5);
//		
//		if (distance<LayerUFOsThread.getInstance().ofdMotherUFO.getBitmap().getWidth()/2)
//			return true;
		
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

	private class crashTimerTask extends TimerTask{
	//	int count = 0;
		Random rnd = new Random();
		@Override
		public void run() {
			if(!LayerMotherUFOThread.this.crashes.isEmpty()){
				ObjectForDrawing crash = LayerMotherUFOThread.this.crashes.remove(0);
				
				crash.setX(LayerUFOsThread.getInstance().ofdMotherUFO.getX()+ LayerUFOsThread.getInstance().motherUFOWidth*rnd.nextFloat());
				crash.setY(LayerUFOsThread.getInstance().ofdMotherUFO.getY()+ LayerUFOsThread.getInstance().motherUFOHeight*rnd.nextFloat());
				
				LayerMotherUFOThread.this.objectsForDrawing.add(crash);
				LayerThread.soundPool.play(LayerThread.airCraftExplosion, 2f, 2f, 0, 0, 1);
			}else{
				LayerMotherUFOThread.this.objectsForDrawing.add(LayerMotherUFOThread.this.wonLogo);
				LayerThread.soundPool.play(LayerThread.wonSound, 2f, 2f, 0, 0, 1);
				this.cancel();
			}
			
			
		}

		
	}
	private Timer crashExplosionTimer;
	@Override
	public void run() {		
		
		Timer attackTimer = new Timer();
		
		boolean isTimerActived = false;
	
	//	LayerUFOsThread.getInstance().getOfdMotherUFO().getBitmap().getWidth()-this.
		while (true){
			if(this.isDoStop) return;
			
			if(DrawThread.gameStat == GameStat.GAMEOVER){
				break;
			
			}else if(DrawThread.gameStat == DrawThread.GameStat.WON){
				if(this.crashExplosionTimer==null){
					this.crashExplosionTimer = new Timer();
					this.crashExplosionTimer.schedule( new crashTimerTask(),0,800);
				}
				
				this.addToArrayAndSleep();
				
			}else if(DrawThread.gameStat != DrawThread.GameStat.LV3){
		//	}else if(DrawThread.gameStat == DrawThread.GameStat.LV3){
				this.addToArrayAndSleep();
				
				continue;
			}else{
			
			float airCraftX = LayerAircraftThread.getInstance().getAircraftCentralX();
			float airCraftY = LayerAircraftThread.getInstance().getAircraftCentralY();
			
			
			
			if(!isTimerActived){
				attackTimer.schedule(new ICLauncherTimerTask(), 15000,20000);
				attackTimer.schedule(new WarningICLauncherTimerTask(), 14500,20000);
				
				attackTimer.schedule(new MachineGunLaunchTimerTask(), 10000, 15000);
				
				attackTimer.schedule(new ShotGunLaunchTimerTask(), 5000,10000);
				
				attackTimer.schedule(new TracingSlugTimerTask(), 2000, 20100);
				
				isTimerActived = true;
			}
			
			long startTime = System.currentTimeMillis();
		//	Log.v("TraceSlug", this.tracingSlug.getX()+","+this.tracingSlug.getY());
			
			
		//	this.tracingSlug.getPaint().setAlpha(50);
			
			this.objectsForDrawing.clear();
			
			if(this.tracingSlug.isMoving()){
				float[] traceingSlugPos = this.tracingSlug(this.tracingSlug.getX(), this.tracingSlug.getY());
				this.tracingSlug.setX(traceingSlugPos[0]);
				this.tracingSlug.setY(traceingSlugPos[1]);
			}
			this.objectsForDrawing.add(this.tracingSlug);
			if(this.tracingSlug.isDrawMe()){
				try{
					float orgX = this.tracingSlug.getBitmap().getWidth()/2+this.tracingSlug.getX();
					float orgY = this.tracingSlug.getBitmap().getHeight()/2+this.tracingSlug.getY();
					double distance =Math.pow( Math.pow(orgX-LayerAircraftThread.getInstance().getAircraftCentralX(), 2)+Math.pow(orgY-LayerAircraftThread.getInstance().getAircraftCentralY(), 2), 0.5);
					
					
					if(distance < ((LayerAircraftThread.getInstance().aircraft.getBitmap().getWidth()+this.tracingSlug.getBitmap().getWidth())/3)){
						//hit by tracing slug
						LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(-0.5f*(this.tracingSlug.getPaint().getAlpha()/250));
							
							LayerThread.vibrator.cancel();
							LayerThread.vibrator.vibrate(100);
					}
					}catch(NullPointerException e){
						continue;
					}
			}
			
			//=================================================
			if(this.isIcLauncher){
				this.objectsForDrawing.add(this.icLauncher);
				float mUOFx = LayerUFOsThread.getInstance().getMotherCentralX();
				float mUOFy = LayerUFOsThread.getInstance().getMotherCentralY();
				this.icLauncher.setX(mUOFx-this.icLauncher.getBitmap().getWidth()/2);
				this.icLauncher.setY(mUOFy-20);
				
				if(airCraftY>=this.icLauncher.getY()){
					if(airCraftX>this.icLauncher.getX()+(this.icLauncher.getBitmap().getWidth()*0.4)&&airCraftX<this.icLauncher.getX()+(this.icLauncher.getBitmap().getWidth()*0.6)){
						//hit by ic gun.
						LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(-0.6f);
						LayerThread.vibrator.cancel();
						LayerThread.vibrator.vibrate(1000);
					}
				}
			}
			
			//===================================================
			synchronized (this.machineGunBullets) {
				if (this.isLaunchMachineBullet && this.machineGunBulletCase.size() > 0) {
					GunBullet machineGunbullet = this.machineGunBulletCase.remove(this.machineGunBulletCase.size() - 1);
					float x1 = LayerAircraftThread.getInstance().getAircraftCentralX();
					float y1 = LayerAircraftThread.getInstance().getAircraftCentralY();

					float x0 = LayerUFOsThread.getInstance().getMotherCentralX();
					float y0 = LayerUFOsThread.getInstance().getMotherCentralY();
					machineGunbullet.setX(x0 - machineGunbullet.getBitmap().getWidth() / 2);
					machineGunbullet.setY(y0 - machineGunbullet.getBitmap().getHeight() / 2);
					machineGunbullet.setDrawMe(true);
					
					Double angle = Math.atan((y1 - y0) / (x1 - x0));
					machineGunbullet.setAngle(angle);

					machineGunbullet.xDirect = (x1 - x0 > 0) ? 1 : -1;
					machineGunbullet.yDirect = (y1 - y0 > 0) ? 1 : -1;

					this.machineGunBullets.add(machineGunbullet);

					this.isLaunchMachineBullet = false;
				}

				if (this.machineGunBulletCase.size() == 0) {
					Log.v("SetFalse", this.machineGunBulletCase.size() + ""
							+ this.isLaunchMachineBullet);
					this.isLaunchMachineGun = false;
				}

				for (int i = 0; i < this.machineGunBullets.size(); i++) {
					GunBullet bullet = this.machineGunBullets.get(i);

					if ((bullet.getY() > this.canvasHeight * 2)
							|| (bullet.getY() + bullet.getBitmap().getHeight() < -this.canvasHeight)
							|| (bullet.getX() > this.canvasWidth * 2)
							|| (bullet.getX() + bullet.getBitmap().getWidth() < -this.canvasWidth)) {
						this.machineGunBulletCase.add(this.machineGunBullets.remove(i));

					} else {
						final int speed = 5;
						float deltaX = Math.abs((float) Math.cos(bullet
								.getAngle()) * speed)
								* bullet.xDirect;
						float deltaY = Math.abs((float) Math.sin(bullet
								.getAngle()) * speed)
								* bullet.yDirect;

						bullet.setX(bullet.getX() + deltaX);
						bullet.setY(bullet.getY() + deltaY);
						this.objectsForDrawing.add(bullet);
						
						float orgX = bullet.getX()+(bullet.getBitmap().getWidth()/2);
						float orgY = bullet.getY()+(bullet.getBitmap().getHeight()/2);
						
						if(bullet.isDrawMe()){
							try{
								if(Math.abs(orgX-LayerAircraftThread.getInstance().getAircraftCentralX())<LayerAircraftThread.getInstance().aircraft.getBitmap().getWidth()/2
								&&
								Math.abs(orgY-LayerAircraftThread.getInstance().getAircraftCentralY())<LayerAircraftThread.getInstance().aircraft.getBitmap().getHeight()/2){
									//hit by machine gun	
									LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(-0.01f);
									bullet.setDrawMe(false);
								
									LayerThread.vibrator.cancel();
									LayerThread.vibrator.vibrate(200);
						}
						}catch(NullPointerException e){
							continue;
						}
						}
					}
					
				}
				
			}
			//==================================================
			synchronized (this.shotGunBullets) {
				final int spd = 5;
				if (this.isLaunchShotGunBullet && this.shotGunBulletCase.size() > 0) {
					GunBullet[] bullets = this.shotGunBulletCase.remove(this.shotGunBulletCase.size() - 1);
				//	float x1 = LayerAircraftThread.getInstance().getAircraftCentralX();
				//	float y1 = LayerAircraftThread.getInstance().getAircraftCentralY();

					float x0 = LayerUFOsThread.getInstance().getMotherCentralX();
					float y0 = LayerUFOsThread.getInstance().getMotherCentralY();
					for(GunBullet bullet : bullets){
						bullet.setX(x0 - bullet.getBitmap().getWidth() / 2);
						bullet.setY(y0 - bullet.getBitmap().getHeight() / 2);
					}

					this.shotGunBullets.add(bullets);

					this.isLaunchShotGunBullet = false;
				}

				if (this.shotGunBulletCase.size() == 0) {
					
					this.isLaunchShotGun = false;
				}

				for (int i = 0; i < this.shotGunBullets.size(); i++) {
					GunBullet[] bullets = this.shotGunBullets.get(i);
					String angleStr = "";
					for(GunBullet bullet : bullets){
						angleStr+=","+bullet.getAngle();
					}
					Log.v("angle", angleStr);
					
					for(int j =0; j<bullets.length; j++){	
						GunBullet bullet = bullets[j];

							double radius = bullet.getAngle()*(Math.PI/180);
							float deltaX = (float)(spd*Math.cos(radius));
							float deltaY = (float)(spd*Math.sin(radius));
					
							bullet.setX(bullet.getX()+deltaX);
							bullet.setY(bullet.getY()+deltaY);
							
							Log.v("remove1", bullet.getX()+"");
						//	Log.v("ShotGun","add="+shotGunbullet.getBitmap());
							this.objectsForDrawing.add(bullet);
							
							float orgX = bullet.getX()+(bullet.getBitmap().getWidth()/2);
							float orgY = bullet.getY()+(bullet.getBitmap().getHeight()/2);
							
							if (bullet.isDrawMe()){
								try{
									if(Math.abs(orgX-LayerAircraftThread.getInstance().getAircraftCentralX())<LayerAircraftThread.getInstance().aircraft.getBitmap().getWidth()/3
									&&
									Math.abs(orgY-LayerAircraftThread.getInstance().getAircraftCentralY())<LayerAircraftThread.getInstance().aircraft.getBitmap().getHeight()/3){
									//hit by shot gun	
										LayerBackGroundThread.getInstance().lifeBar.resizeLifeBar(-0.01f);
										bullet.setDrawMe(false);
									
										LayerThread.vibrator.cancel();
										LayerThread.vibrator.vibrate(200);
									}
								}catch(NullPointerException e){
									continue;
								}
							}		
					}
					
					
				}
			}
			//================================================
			/*
			Log.v("ShotGun",this.isLaunchShotGun+"===="+this.isLaunchShotGunBullet);
			if(this.isLaunchShotGun&&this.isLaunchShotGunBullet){
				this.isLaunchShotGunBullet = false;
				
				final int spd = 3;
			//	for(int i=0; i< this.shotGunBullets.length ; i++){
				if(this.shotGunShootCount<this.shotGunBullets.length){
					MachineGunBullet[] shotGunbullets = this.shotGunBullets[this.shotGunShootCount++];
					for(MachineGunBullet shotGunbullet : shotGunbullets){
						double radius = shotGunbullet.getAngle()*(Math.PI/180);
						float deltaX = (float)(spd*Math.cos(radius));
						float deltaY = (float)(spd*Math.sin(radius));
						shotGunbullet.setX(shotGunbullet.getX()+deltaX);
						shotGunbullet.setY(shotGunbullet.getY()+deltaY);
						
						Log.v("ShotGun","add="+shotGunbullet.getBitmap());
						this.objectsForDrawing.add(shotGunbullet);
						
						if(this.shotGunShootCount==(this.shotGunBullets.length-1)){
							this.isLaunchShotGun = false;
							if(
									(shotGunbullet.getX()>0)&&
									(shotGunbullet.getX()<(this.canvasWidth-shotGunbullet.getBitmap().getWidth()))&&
									(shotGunbullet.getY()>0)&&
									(shotGunbullet.getY()<(this.canvasHeight-shotGunbullet.getBitmap().getHeight()))
							){
									this.isLaunchShotGun = true;	
							}
						}
						
					}
				}else{
				//	this.isLaunchShotGun = false;
				}
			}
				*/
			
			this.objectsForDrawing.add(this.lifeBar);
			this.addToArrayAndSleep();
			
			Log.v("vvv", System.currentTimeMillis()-startTime+"");
			
		}
		}
		
	}

	//====================================================================================
	//===============================tracing slug=========================================
	
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
	
//	private boolean isTracingSlug = false;
	private class TracingSlugTimerTask extends TimerTask{
		@Override
		public void run() {
			LayerMotherUFOThread.this.tracingSlug.setMoving(true);
			LayerMotherUFOThread.this.tracingSlug.getPaint().setAlpha(250);
			LayerMotherUFOThread.this.tracingSlug.setDrawMe(true);
			
			LayerMotherUFOThread.this.tracingSlug.setX(LayerUFOsThread.getInstance().getMotherCentralX()-LayerMotherUFOThread.this.tracingSlug.getBitmap().getWidth()/2);
			LayerMotherUFOThread.this.tracingSlug.setY(LayerUFOsThread.getInstance().getMotherCentralY()-LayerMotherUFOThread.this.tracingSlug.getBitmap().getHeight()/2);
			
			Timer timer = new Timer();
			timer.schedule(new TracingSlugStopMovingTimerTask() , 15000);
		}
	}
	private class TracingSlugStopMovingTimerTask extends TimerTask{

		@Override
		public void run() {
			LayerMotherUFOThread.this.tracingSlug.setMoving(false);
			Timer timer = new Timer();
			timer.schedule(new TracingSlugDisappearTimerTask() , 0);
		}
	
	}
	private class TracingSlugDisappearTimerTask extends TimerTask{
		@Override
		public void run() {
			
			int alpha = LayerMotherUFOThread.this.tracingSlug.getPaint().getAlpha();
			LayerMotherUFOThread.this.tracingSlug.getPaint().setAlpha(alpha-=50);
			if(LayerMotherUFOThread.this.tracingSlug.getPaint().getAlpha()>0){
				Timer timer = new Timer();
				timer.schedule(new TracingSlugDisappearTimerTask() , 1000);
			}else{
				LayerMotherUFOThread.this.tracingSlug.setDrawMe(false);
			}
			
			
		}
	}
	//===============================================================
	//===================ic launcher=================================
	private boolean isIcLauncher = false;

	public boolean isMotherUFOMoving = true;
	private class ICLauncherTimerTask extends TimerTask{

		@Override
		public void run() {
			LayerMotherUFOThread.this.isIcLauncher = true;
			
			Timer timer = new Timer();
			timer.schedule(new StopICLauncherTimerTask(), 5000);
		}
	}
	private class StopICLauncherTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			LayerMotherUFOThread.this.isIcLauncher = false;
			LayerMotherUFOThread.this.isMotherUFOMoving = true;
		}
	}
	private class WarningICLauncherTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(DrawThread.gameStat != GameStat.LV3) return;
			
			LayerMotherUFOThread.this.isMotherUFOMoving  = false;
			LayerThread.soundPool.play(LayerThread.icLauncherWarning, 2f, 2f, 0, 0, 1);
		}
	}
	//====================================================================================
	//=========================machine gun================================================
	private boolean isLaunchMachineBullet = false;
	private boolean isLaunchMachineGun = false;
	private class MachineGunLaunchTimerTask extends TimerTask{
		private Timer timer;
		
		@Override
		public void run() {
			
			LayerMotherUFOThread.this.isLaunchMachineGun = true;
			
			if (this.timer==null){
				this.timer = new Timer();
				this.timer.schedule(new MachineGunTimerTask(), 0, 150);
			}
		}
		
	}
	private class MachineGunTimerTask extends TimerTask{
		
		@Override
		public void run() {
			Log.v("subTimer", LayerMotherUFOThread.this.isLaunchMachineGun+"");
			if(LayerMotherUFOThread.this.isLaunchMachineGun == true)
				LayerMotherUFOThread.this.isLaunchMachineBullet = true;
		}
		
	}
	
	//=======================================================================
	//============================shot gun===================================
	private int shotGunMode = 0;
	private boolean isLaunchShotGun = false;
	private boolean isLaunchShotGunBullet = false;
//	private int shotGunShootCount = 0;
	private class ShotGunLaunchTimerTask extends TimerTask{
		Timer timer;
		@Override
		public void run() {	
			for(int i=0; i<LayerMotherUFOThread.this.shotGunBullets.size(); i++){
				LayerMotherUFOThread.this.shotGunBulletCase.add(LayerMotherUFOThread.this.shotGunBullets.remove(LayerMotherUFOThread.this.shotGunBullets.size()-1));
			}
			
			LayerMotherUFOThread.this.isLaunchShotGun = true;
			
			if (this.timer==null){
				this.timer = new Timer();
				this.timer.schedule(new ShotGunTimerTask(), 100, 150);
			}
			
		}
		
	}
	
	private class ShotGunTimerTask extends TimerTask{	
		@Override
		public void run() {
		//	Log.v("subTimer", LayerMotherUFOThread.this.isLaunchMachinGun+"");
			if(LayerMotherUFOThread.this.isLaunchShotGun == true)
				LayerMotherUFOThread.this.isLaunchShotGunBullet = true;
		}
		
	}
}
