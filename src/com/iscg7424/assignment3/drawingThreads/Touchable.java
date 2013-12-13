package com.iscg7424.assignment3.drawingThreads;

import java.util.List;

/*
 * 
 * 
 */
public interface Touchable {
	public boolean doTouchDetection(float x, float y);
	
	public float[] doPointTouchDetection(float x, float y);

	public int doPointTouchDetection(List<ObjectForDrawing> objectList);
}
