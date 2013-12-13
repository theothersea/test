package com.iscg7424.assignment3.drawingThreads;

import com.iscg7424.assignment3.drawingThreads.DrawThread.GameStat;

public class ObjectForDrawingMotherUFOLifeBar extends ObjectForDrawingLifeBar {
	private float MAX_LENGTH = 800;
	public float HEIGHT = 10;
	
	public ObjectForDrawingMotherUFOLifeBar(float maxLength, int height) {
		super();
		this.MAX_LENGTH = maxLength;
		this.length = this.MAX_LENGTH;
		this.HEIGHT = height;
	}

	public float getMAX_LENGTH() {
		return MAX_LENGTH;
	}
	
	public void resizeLifeBar(float value){
		
		//	if(Math.abs(value)!=0) return;
			
			this.length += (value*this.MAX_LENGTH);
			
			if(this.length<0){
			//	this.length = this.LENGTH;
				this.length = 0;
				
				DrawThread.gameStat = GameStat.WON;
				LayerThread.mediaPlayer.stop();
				
			}
		}
}
