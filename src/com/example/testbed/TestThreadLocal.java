package com.example.testbed;

import java.util.ArrayList;


public class TestThreadLocal {

	private ThreadLocal<TestClass> mThreadLocal = new ThreadLocal<TestClass>();
	private TestClass mTestObj = null;
	private Thread mThread = null;
	public void init()
	{
		mTestObj = new TestClass(2, "1", "2");
		mThreadLocal.set(mTestObj);
		mThread = new Thread()
		{
			@Override
			public void run() {
				mTestObj.num = 3;
				mTestObj.list.add("c");
				invokeTestObj(mTestObj);
			};
		};
		mThread.start();
		try {
			mThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		invokeTestObj(mThreadLocal.get());
		invokeTestObj(mTestObj);
	}
	
	public static void invokeTestObj(TestClass testObj)
	{
		if(testObj == null) return;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < testObj.list.size(); i++) {
			sb.append(i).append(":").append(testObj.list.get(i)).append(";");
		}
		System.out.println("-->invokeTestObj, thread:"+Thread.currentThread()+",mTestObj.num:"+testObj.num+",mTestObj:"+sb.toString());
	}
	
	
	private class TestClass
	{
		public int num = 0;
		public ArrayList<String> list = new ArrayList<String>();
		public TestClass(int n, String... s)
		{
			num = n;
			for (int i = 0; i < s.length; i++) {
				list.add(s[i]);
			}
		}
	}
}
