package com.iscg7424.assignment3.drawingThreads;

import com.iscg7424.assignment3.drawingThreads.DrawThread.GameStat;

public class ObjectForDrawingLifeBar extends ObjectForDrawing {
	protected float MAX_LENGTH = 400;
	protected int HEIGHT = 20;
	
	protected float length;
//	protected int height;
	public float getLength() {
		return length;
	}
	

	public ObjectForDrawingLifeBar() {
		this.length = this.MAX_LENGTH;
	}
	/**
	 * @param length
	 * @param height
	 */
	public ObjectForDrawingLifeBar(float maxLength, int height) {
		super();
		this.MAX_LENGTH = maxLength;
		this.length = this.MAX_LENGTH;
		this.HEIGHT = height;
	}
	
	
	public void resizeLifeBar(float value){
		
	//	if(Math.abs(value)!=0) return;
		if(LayerAircraftThread.getInstance().isShielded()) return;
		
		if(DrawThread.gameStat == GameStat.WON) return;
		
		this.length += (value*this.MAX_LENGTH);
		
		if(this.length>this.MAX_LENGTH){
			this.length = this.MAX_LENGTH;
		}
		if(this.length<0){
		//	this.length = this.LENGTH;
			this.length = 0;
			DrawThread.gameStat = GameStat.GAMEOVER;
			LayerThread.mediaPlayer.stop();
			
		}
	}
	public float getMAX_LENGTH() {
		return MAX_LENGTH;
	}
	
}