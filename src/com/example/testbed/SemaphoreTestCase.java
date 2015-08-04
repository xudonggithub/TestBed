package com.example.testbed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
public class SemaphoreTestCase {

	    public static void Test(String[] args) {
	        // �̳߳�
	        ExecutorService exec = Executors.newCachedThreadPool();
	        // ֻ��5���߳�ͬʱ����
	        final Semaphore semp = new Semaphore(5);
	        // ģ��20���ͻ��˷���
	        for (int index = 0; index < 50; index++) {
	            final int NO = index;
	            Runnable run = new Runnable() {
	                public void run() {
	                    try {
	                        // ��ȡ���
	                        semp.acquire();
	                        System.out.println("Accessing: " + NO+", availablePermits:"+semp.availablePermits() );
	                        Thread.sleep(1500);
	                        // ��������ͷ�
	                        semp.release();
	                        //availablePermits()ָ���ǵ�ǰ�źŵƿ����ж��ٸ����Ա�ʹ��
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }
	            };
	            exec.execute(run);
	        }
	        // �˳��̳߳�
	        exec.shutdown();
	    }
}
