
package com.xyz.test;

import com.xyz.connpool.IConnPoolManager;
import com.xyz.connpool.IConnection;
import com.xyz.connpool.impl.ConnPoolManager;

public class Demo{
	public static void main(String [] args) throws Exception{
		final IConnPoolManager pools=new ConnPoolManager(3, 5, 5000);
		for(int i=0;i<9;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					IConnection conn=pools.getConn(0);
					System.out.println(conn);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					conn.close();
				}
			}).start();
		}
		Thread.sleep(6000);
		pools.destory();
	}
}
