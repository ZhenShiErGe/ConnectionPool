package com.xyz.connpool.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xyz.connpool.IConnPool;
import com.xyz.connpool.IConnPoolManager;
import com.xyz.connpool.IConnection;
public class ConnPoolManager implements IConnPoolManager{
	private int initConnNum;
	private int maxConnNum;
	private long maxWaitTime;
	// ���ӳش��
	public Map<Integer,IConnPool> pools=new ConcurrentHashMap<Integer, IConnPool>();
//	public Hashtable<Integer,IConnPool> pools = new Hashtable<Integer, IConnPool>();
	
	public ConnPoolManager(int initConnNum,int maxConnNum, long maxWaitTime){
		this.initConnNum=initConnNum;
		this.maxConnNum=maxConnNum;
		this.maxWaitTime=maxWaitTime;
		init();
	}
	
	// ��ʼ�����е����ӳأ�����Ĭ��ֻ��0�����ӳ�
	private void init(){
		for(int hostId =0;hostId<1;hostId++){
			ConnPool connPool=new ConnPool(this.initConnNum,this.maxConnNum, this.maxWaitTime);
			if(connPool != null){
				pools.put(hostId,connPool);
				System.out.println("Info:Init connPool successed for hostId ->" +hostId);
			}
		}
	}
	
	/**
	 * ��������id��ȡ���ӳ�
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
	 * ��ȡһ��������hostId������
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
	 * �ر�ָ��������ĳ������
	 * @param hostId
	 * @param conn
	 */
	public void close(int hostId,IConnection conn){
			IConnPool pool = getPool(hostId);
			if(pool != null)
				pool.releaseConn(conn);
	}
	/**
	 * ע��ĳ�����������ӳ�
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
	 * ע���������ӳ�
	 */
	public void destory(){
		for(int hostId:pools.keySet()){
			this.destroy(hostId);
		}
	}
}
