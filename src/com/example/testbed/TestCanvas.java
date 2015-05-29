package com.example.testbed;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.View;

public class TestCanvas {
	
	public static View getCustomView(Context context){
		final Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		return new View(context){
			@Override
			protected void onDraw(Canvas canvas) {
				int pw = getMeasuredWidth();
				int ph = getMeasuredHeight();
				int cw = canvas.getWidth();
				int ch = canvas.getHeight();
				System.out.println("..cxd 1 pw="+pw+",ph="+ph+",cw="+cw+",ch="+ch);
				canvas.rotate(90, pw/2, ph/2);
				canvas.drawRect(new Rect(0,0,cw,ch), paint);
				
				System.out.println("..cxd 2 pw="+getMeasuredWidth()+",ph="+getMeasuredHeight()+",cw="+canvas.getWidth()+",ch="+canvas.getHeight());
			}
		};
	}
	
	public static void testRotateImage() {
		Bitmap src = loadImageBitmap();
		adjustPhotoRotation2(src, 90);
		adjustPhotoRotation2(src, 180);
		adjustPhotoRotation2(src, 270);
	}
	
	private static Bitmap loadImageBitmap() {
		Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath().concat("/sourceImg_s.jpg"));
		return bmp;
	}
	private static Bitmap adjustPhotoRotation2(Bitmap src, 
			 int orientationDegree) {
		if(orientationDegree % 90 != 0)
			return null;
		orientationDegree = orientationDegree % 360;

		//=============== matrix =================
		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) src.getWidth() / 2, (float) src.getHeight() / 2);
		final float[] values = new float[9];
		m.getValues(values);
		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];
		
		int targetX, targetY;
		float transX = 0 , transY = 0;
		if (orientationDegree % 180 == 90) {
			targetX = src.getHeight();
			targetY = src.getWidth();
			if(orientationDegree == 90) {
				transX = targetX - x1;
				transY = 0 - y1;
			}
			else {
				transX = 0 - x1;
				transY = targetY - y1;
			}
		} else {
			targetX = src.getWidth();
			targetY = src.getHeight();
		}

		m.postTranslate(transX, transY);
		System.out.println("cxd,orientationDegree="+orientationDegree+"---> x1="+x1+",y1="+y1+",transX="+transX+",transY="+transY);
		
		//========

		Bitmap des = Bitmap.createBitmap(targetX, targetY,
				Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(des);
		canvas.drawBitmap(src, m, null);
//		try{
//			FileOutputStream fosDes = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath().concat("/desOutput_"+orientationDegree+".jpg")));
//			des.compress(CompressFormat.JPEG, 90, fosDes);
//			fosDes.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		return des;
	}

}
