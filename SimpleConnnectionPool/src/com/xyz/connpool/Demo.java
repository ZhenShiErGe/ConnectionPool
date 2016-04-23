package com.xyz.connpool;
import java.util.concurrent.CountDownLatch;

public class Demo {
	public final static int REQUESTNUM=200;
	public final static int DURATION=20;
	 
	public static void main(String [] args) throws Exception{
		final CountDownLatch startGate=new CountDownLatch(1);
		final CountDownLatch endGate=new CountDownLatch(REQUESTNUM);
		
		final ConnPoolManager cpm=new ConnPoolManager(5, 10, 1000);
		final IConnection conns[] =new IConnection[REQUESTNUM];
		for(int i=0;i<REQUESTNUM;i++){
			final int flag=i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						startGate.await();
					} catch (InterruptedException e1) {}
					
					conns[flag]=cpm.getConn(0);
					System.out.println(conns[flag]);
					try {
						Thread.sleep(DURATION);
					} catch (InterruptedException e) {}
					cpm.close(0, conns[flag]);
					
					endGate.countDown();
				}
			}).start();
		}

		long begin = System.currentTimeMillis();
		startGate.countDown();
		endGate.await();
		long over = System.currentTimeMillis();
		System.out.println(over-begin);
//	    Thread.sleep(5000);
	    cpm.destory();
	}
}

