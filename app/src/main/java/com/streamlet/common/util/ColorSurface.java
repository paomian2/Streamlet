package com.streamlet.common.util;

import android.content.Context;
import android.view.SurfaceView;

public class ColorSurface extends SurfaceView {
	
	public ColorSurface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/*private int R,G,B;

	public ColorSurface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void redraw(){

		Canvas c = getHolder().lockCanvas();

		if(c == null)

		return;

		Paint paint = new Paint();

		paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		for(int i=0; i<COL; ++i){

		for(int j=0; j<COL; ++j){

		randomRGB();

		matrix[i][j].setR(R);

		matrix[i][j].setG(G);

		matrix[i][j].setB(B);

		paint.setColor(changeColor(R, G, B));

		c.drawRect(new RectF(i*WIDTH,j*HEIGHT,

		(i+1)*WIDTH,(j+1)*HEIGHT), paint);

		}

		}

		getHolder().unlockCanvasAndPost(c);

		cc.setRunning(false);


		}
	
	
	private void randomRGB(){

		R = (int)(Math.random()*255);

		G = (int)(Math.random()*255);

		B = (int)(Math.random()*255);

		}


		private int changeColor(int R, int G, int B){

		int color=0, t1, t2;

		color = 15*(int)Math.pow(16, 7) + 15*(int)Math.pow(16, 6);

		t1 = R/16;

		t2 = R%16;

		color = color + t1*(int)Math.pow(16, 5) + t2*(int)Math.pow(16, 4);

		t1 = G/16;

		t2 = G%16;

		color = color + t1*(int)Math.pow(16, 3) + t2*(int)Math.pow(16, 2);

		t1 = B/16;

		t2 = B%16;

		color = color + t1*(int)Math.pow(16, 1) + t2*(int)Math.pow(16, 0);

		return color;

		}*/

}
