package com.example.testbed;

import android.R.integer;


public class ArrayRotate {
	
	public static void  test() {
		int width = 8; 
		int height = 4;
		int size = (int)(width * height);
		int hhSize = (int)(width * height * 0.25);
		String[] srcByte = new String[(int)(size * 1.5)];
		String[] desByte = new String[(int)(size * 1.5)];
		for (int i = 0; i < width * height; i++) {
			if(i <9)
				srcByte[i] = " Y"+String.valueOf(i+1)+"  ";
			else
				srcByte[i] = " Y"+String.valueOf(i+1)+" ";
		}
		int i = 0;
		while(i < hhSize) {
			srcByte[size+i] = " U"+String.valueOf(++i)+"  ";
		}
		 i = 0;
		while(i < hhSize) {
			srcByte[size+hhSize+i] = " V"+String.valueOf(++i)+"  ";
		}
		print(srcByte, width, height);
		System.out.println("--------------------------------------------------------------");
		rotateYUV420PDegree90(srcByte, desByte, width, height);
		print(desByte, height, width);
	}
	
	private static void print(String[] src, int width, int height) {
		for (int i = 0; i <height*1.5; i++) {
			String line = "";
			for (int j = 0; j < width; j++) {
				line = line.concat(src[i*width + j]);
			}
			System.out.println(line);
		}
	}
	
	 public static String[] rotateYUV420SPDegree90(String[] data, String[] outBuffer, int imageWidth, int imageHeight) 
	    {
	        // Rotate the Y luma
	        int i = 0;
	        for(int x = 0;x < imageWidth;x++)
	        {
	            for(int y = imageHeight-1;y >= 0;y--)                               
	            {
	                outBuffer[i] = data[y*imageWidth+x];
	                i++;
	            }
	        }
	        // Rotate the U and V color components 
	        i = imageWidth*imageHeight*3/2-1;
	        for(int x = imageWidth-1;x > 0;x=x-2)
	        {
	            for(int y = 0;y < imageHeight/2;y++)                                
	            {
	                outBuffer[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+x];
	                i--;
	                outBuffer[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+(x-1)];
	                i--;
	            }
	        }
	        return outBuffer;
	    }
		
		public static String[]  rotateYUV420PDegree90(String[] src, String[] des, int width, int height)
		{  
		    int n = 0;  
		    int hw = width / 2;  
		    int hh = height / 2;  
		    //copy y  
		    for(int j = 0; j < width;j++)  
		    {  
		        for(int i = height - 1; i >= 0; i--)  
		        {  
		            des[n++] = src[width * i + j];  
		        }  
		    }  
		  
		    //copy u  
		    int offset = width * height;  
		    for(int j = 0;j < hw;j++)  
		    {  
		        for(int i = hh - 1;i >= 0;i--)  
		        {  
		            des[n++] = src[offset + hw*i + j ];  
		        }  
		    }  
		  
		    //copy v  
		    offset += width * height / 4;  
		    for(int j = 0; j < hw; j++)  
		    {  
		        for(int i = hh - 1;i >= 0;i--)  
		        {  
		            des[n++] = src[offset + hw*i + j];  
		        }  
		    }  
		    return des;
		}  
}
