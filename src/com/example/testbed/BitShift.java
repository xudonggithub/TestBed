package com.example.testbed;


public class BitShift {
	public static void test() {
		int k = 16 >>2;
		int n = 16 >>>2;
		int m = -16 >> 2;
		int t = -16 >>> 2;
		int y = -1 >> 2;
		int x = -1 >>> 2;
		System.out.println("-16:"+Integer.toBinaryString(-16)+", -1:"+Integer.toBinaryString(-1));//-16:11111111111111111111111111110000, -1:11111111111111111111111111111111

		//16 >>2:4, 16 >>>2:4, -16 >> 2:-4, -16 >>> 2 :1073741820, -1 >> 2:-1, -1 >>> 2:1073741823
		System.out.println("16 >>2:"+k+", 16 >>>2:"+n+", -16 >> 2:"+m+", -16 >>> 2 :"+t+", -1 >> 2:"+y+", -1 >>> 2:"+x);

		
	}
}
