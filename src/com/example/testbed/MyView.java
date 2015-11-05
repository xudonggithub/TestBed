package com.example.testbed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class MyView extends View{
	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.GREEN);
		 int measuredWidth = getMeasuredWidth();
		 int easuredHeight = getMeasuredHeight();
		 int canvasW = canvas.getWidth();
		 int canvasH = canvas.getHeight();
		 System.out.println("..cxd 1 measuredWidth="+measuredWidth+",easuredHeight="+easuredHeight+
				 ",canvasW="+canvasW+",canvasH="+canvasH);
	}
}
