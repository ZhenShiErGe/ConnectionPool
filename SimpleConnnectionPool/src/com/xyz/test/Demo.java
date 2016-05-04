
package com.xyz.test;

import com.xyz.connpool.IConnPoolManager;
import com.xyz.connpool.IConnection;
import com.xyz.connpool.impl.ConnPoolManager;
import com.xyz.transaction.DataSourceHandler;
import com.xyz.transaction.TransactionTemplate;

public class Demo{
	//类比框架注入的数据源
//	public static IConnPoolManager dataSource=new ConnPoolManager(3, 5, 1000);
	public static void main(String [] args) throws Exception{
//		IConnPoolManager dataSourceProxy=(IConnPoolManager) new DataSourceHandler().bind(dataSource);
//		final IConnection conn=dataSourceProxy.getConn(0);
//		(new TransactionTemplate() {
//			@Override
//			public void doInTransaction() {
//				// TODO Auto-generated method stub
//				new Service().service(conn);
//			}
//		}).doTransaction(conn);
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
