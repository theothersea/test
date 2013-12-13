package com.iscg7424.assignment3.drawingThreads;

import java.util.List;

import com.iscg7424.assignment3.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

public class DrawThread extends Thread{
	
	public static GameStat gameStat;
	static {
		DrawThread.gameStat = DrawThread.GameStat.OPENING;
	}
	
	public static enum GameStat{
		OPENING, LV1, LV2, LV3, GAMEOVER, ENDING, QUIT, WON;
	}
	
	
	public static Thread drawThread;
	
	private SurfaceHolder holder;
	private Resources resources;
	private Canvas canvas;

//	public Paint paint;
	
	//totally how many layouts will be drawn
	public static final byte TOTAL_LAYERS = 10;
	
	public static LayerThread[] layerArray;
	static {
		
		DrawThread.layerArray = new LayerThread[TOTAL_LAYERS];
	}
	
	//
//	private LayerThread layerOne;
//	private LayerThread layerTwo;
	
	public boolean threadFlag;
	
	public DrawThread(SurfaceHolder holder, Resources resources) {
		this.holder = holder;
		this.resources = resources;
		this.canvas = this.holder.lockCanvas();
		//===========================================================================================================
		//add the layer thread objects here.
		DrawThread.layerArray[0] = LayerBackGroundThread.getInstance(this.resources,this.canvas.getWidth(), this.canvas.getHeight(),0);
		DrawThread.layerArray[2] = LayerUFOsThread.getInstance(this.resources,this.canvas.getWidth(), this.canvas.getHeight(),2);
		DrawThread.layerArray[4] = LayerCargoThread.getInstance(this.resources, this.canvas.getWidth(), this.canvas.getHeight(),3);
		DrawThread.layerArray[6] = LayerMotherUFOThread.getInstance(this.resources, this.canvas.getWidth(), this.canvas.getHeight(),6);
		DrawThread.layerArray[TOTAL_LAYERS-1] = LayerAircraftThread.getInstance(this.resources,this.canvas.getWidth(), this.canvas.getHeight(),TOTAL_LAYERS-1);	
		
		//===========================================================================================================
		this.holder.unlockCanvasAndPost(this.canvas);
		
		this.setPriority(NORM_PRIORITY);
		
		DrawThread.drawThread = this;
		
	//	this.paint = new Paint();
		threadFlag=true;
	}

	@Override
	public void run() {
		if (gameStat.equals(DrawThread.GameStat.OPENING))
		{
			try{
				Paint fPaint = new Paint();
				Paint txtPaint = new Paint();
				RectF rectf;
				float fh = 0F;
				float ty = 0F;

				float volume = 0.2f;
				for (int i=3; i>=1; i--)
				{
						if(i==1) LayerThread.mediaPlayer.start();
						
					
					
					for (float j=0; j<=360; j+=5F)
					{
						this.canvas = this.holder.lockCanvas();
						this.canvas.drawColor(Color.BLACK);
						fPaint.setStyle(Paint.Style.FILL);
						fPaint.setColor(Color.GRAY);
						fPaint.setAlpha(100);
						this.canvas.drawCircle( canvas.getWidth()/2, canvas.getHeight()/2, canvas.getHeight()*0.40F, fPaint );

						rectf = new RectF(
								canvas.getWidth()/2-canvas.getHeight()*0.40F, 
								canvas.getHeight()/2-canvas.getHeight()*0.40F, 
								canvas.getWidth()/2+canvas.getHeight()*0.40F, 
								canvas.getHeight()/2+canvas.getHeight()*0.40F);
						fPaint.setColor(Color.WHITE);
						fPaint.setAlpha(100);
						this.canvas.drawArc(rectf, -90, j, true, fPaint);

						fPaint.setColor(Color.WHITE);
						this.canvas.drawCircle( canvas.getWidth()/2, canvas.getHeight()/2, canvas.getHeight()*0.35F, fPaint );

						txtPaint.setColor(Color.RED);
						txtPaint.setTextSize(canvas.getHeight()*0.50F);
						txtPaint.setTextAlign(Paint.Align.CENTER);
						FontMetrics fm = txtPaint.getFontMetrics();
						fh = fm.descent - fm.ascent;
						ty = (canvas.getHeight()/2f) + (fh/2f) - fm.descent;
						this.canvas.drawText(String.valueOf(i), canvas.getWidth()/2, ty, txtPaint);
						this.holder.unlockCanvasAndPost(this.canvas);
					}
					
					
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		
		for(LayerThread layerThread:layerArray){
			if(layerThread!=null){
				if(layerThread.getState()==Thread.State.NEW)
				layerThread.start();
			}
		}
	
		long drawingTime; 
		long count1 = 0;
		long count2 = 0;
		while(threadFlag){
			long startTime = System.currentTimeMillis();
			try {		
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				Log.v("DrawThread","DrawThread is waken up!");
			}
			
			try{
				this.canvas = this.holder.lockCanvas();
				this.doDrawing();
				this.holder.unlockCanvasAndPost(this.canvas);
			}catch(Exception e){
				e.printStackTrace();
				break;
			}


			for(LayerThread layerThread:layerArray){
				if(layerThread!=null)
					layerThread.interrupt();
			}
			
			drawingTime = System.currentTimeMillis()-startTime;
			count2++;
			if(drawingTime>25){
				count1++;
				Log.v("Drawing Time", drawingTime+" "+count1+"/"+count2);
			}
		}
			
	}

	
	
	private void doDrawing() {
		if (this.canvas==null) return;
		
	//	this.canvas.drawColor(Color.BLACK);  
        
        
		synchronized (DrawThread.layerArray){

			for (LayerThread layerThread : DrawThread.layerArray){
				if(layerThread!=null){
					List<ObjectForDrawing> objectsForDrawing = layerThread.getObjectsForDrawing();
					for(int i=0; i<objectsForDrawing.size(); i++){
						ObjectForDrawing ofd = objectsForDrawing.get(i);
						if(ofd.getBitmap()!=null){
							if(ofd.isDrawMe())
								this.canvas.drawBitmap(ofd.getBitmap(), ofd.getX(),ofd.getY(), ofd.getPaint());
						}else{
							if(ofd instanceof ObjectForDrawingLifeBar){
								ObjectForDrawingLifeBar lifeBar = (ObjectForDrawingLifeBar)ofd;
								
								lifeBar.getPaint().setColor(Color.GRAY);
								this.canvas.drawRect(lifeBar.getX()-5, lifeBar.getY()-5, lifeBar.getX()+lifeBar.getMAX_LENGTH()+5, lifeBar.getY()+lifeBar.HEIGHT+5, ofd.getPaint());
								
								if(lifeBar.getLength()<lifeBar.getMAX_LENGTH()*0.3){
									lifeBar.getPaint().setColor(Color.RED);
								}else{
									lifeBar.getPaint().setColor(Color.GREEN);
								}
								this.canvas.drawRect(lifeBar.getX(), lifeBar.getY(), lifeBar.getX()+lifeBar.getLength(), lifeBar.getY()+lifeBar.HEIGHT, ofd.getPaint());
							}
						}
					}
				}
			}
		}
		
	}

	/*
	public void leftPressed() {
		((LayerAircraftThread)DrawThread.layerArray[3]).goLeft();
	}

	public void upPressed() {
		((LayerAircraftThread)DrawThread.layerArray[3]).goUp();
	}
	
	public void downPressed(){
		((LayerAircraftThread)DrawThread.layerArray[3]).goDown();
	}
	
	public void rightPressed(){
		((LayerAircraftThread)DrawThread.layerArray[3]).goRight();
	}
	*/
}