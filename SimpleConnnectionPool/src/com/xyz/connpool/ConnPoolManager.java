package com.xyz.connpool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class ConnPoolManager {
	private int initConnNum;
	private int maxConnNum;
	private long maxWaitTime;
	// 连接池存放
	public Map<Integer,IConnPool> pools=new ConcurrentHashMap<Integer, IConnPool>();
//	public Hashtable<Integer,IConnPool> pools = new Hashtable<Integer, IConnPool>();
	
	public ConnPoolManager(int initConnNum,int maxConnNum, long maxWaitTime){
		this.initConnNum=initConnNum;
		this.maxConnNum=maxConnNum;
		this.maxWaitTime=maxWaitTime;
		init();
	}
	
	// 初始化所有的连接池
	private void init(){
		for(int hostId =0;hostId<3;hostId++){
			ConnPool connPool=new ConnPool(this.initConnNum,this.maxConnNum, this.maxWaitTime);
			if(connPool != null){
				pools.put(hostId,connPool);
				System.out.println("Info:Init connPool successed for hostId ->" +hostId);
			}
		}
	}
	
	/**
	 * 根据主机id获取连接池
	 * @param hostId
	 * @return
	 */
	public IConnPool getPool(int hostId){
		IConnPool pool = null;
		if(pools.containsKey(hostId)){
			 pool = pools.get(hostId);
		}
		return pool;
	}
	
	/**
	 * 获取一个到主机hostId的连接
	 * @param hostId
	 * @return
	 */
	public IConnection getConn(int hostId){
		IConnPool connPool = getPool(hostId);
		if(connPool==null){
			System.out.println("Error:Can't find this connecion pool for hostId->"+hostId);
			return null;
		}
		return connPool.getConn();
	}
	
	/**
	 * 关闭指定主机的某个链接
	 * @param hostId
	 * @param conn
	 */
	public void close(int hostId,IConnection conn){
			IConnPool pool = getPool(hostId);
			if(pool != null)
				pool.releaseConn(conn);
	}
	/**
	 * 注销某个主机的连接池
	 * @param poolName
	 */
	public void destroy(int hostId){
		IConnPool pool = getPool(hostId);
		if(pool != null){
			pool.destroy();
		}
		pools.remove(pool);
	}
	/**
	 * 注销所有连接池
	 */
	public void destory(){
		for(int hostId:pools.keySet()){
			this.destroy(hostId);
		}
	}
}
