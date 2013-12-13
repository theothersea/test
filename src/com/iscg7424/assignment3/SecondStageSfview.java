package com.iscg7424.assignment3;

import com.iscg7424.assignment3.drawingThreads.DrawThread;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SecondStageSfview extends SurfaceView implements SurfaceHolder.Callback,OnTouchListener{

	private SurfaceHolder  holder;
	private DrawThread drawThread;
	
	public SecondStageSfview(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		
		this.setOnTouchListener(this);
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

}
