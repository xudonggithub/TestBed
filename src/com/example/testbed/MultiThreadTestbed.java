package com.example.testbed;

public class MultiThreadTestbed {
	public  void Test() {
		new Maker().start();
		new Consumer("Consumer 1").start();
		new Consumer("Consumer 2").start();
	}
	
	private int production = 0;
	private Object lock = new Object();
	class Maker extends Thread {
		private boolean stop = false;
		@Override
		public void run() {
			while(!stop) {
				synchronized (lock) {
				if(production < 5){
					production ++;
					System.out.println("cxd, Maker make production:"+production);
				}
				else {
					System.out.println("cxd, Maker notifyAll start");
						lock.notifyAll();
						//用notifyAll()，consumer1, consumer2都会被挨个唤醒，顺序不定，一个执行完另一个执行。
						//用notify()，consumer1, consumer2被唤醒一个，感觉是顺序是轮流的。
				}
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	class Consumer extends Thread {
		private boolean stop = false;
		String threadName;
		public Consumer(String name) {
			threadName = name;
		}
		@Override
		public void run() {
			while (!stop) {
				synchronized (lock) {
					if(production > 0){
						production --;
						System.out.println("cxd, Cosumer "+threadName+" consumed production:"+production);
					
						try {
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
