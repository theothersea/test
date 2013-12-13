package com.iscg7424.assignment3.drawingThreads;

import android.graphics.Bitmap;
import android.graphics.Paint;

public class ObjectForDrawing {
	private float x;
	private float y;
	private Bitmap bitmap;
	private boolean isDrawMe = true;
	private Paint paint = new Paint();
	
	
	public Paint getPaint() {
		return paint;
	}
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public boolean isDrawMe() {
		return isDrawMe;
	}
	public void setDrawMe(boolean isDrawMe) {
		this.isDrawMe = isDrawMe;
	}
	
	
	
}
