package com.xyz.connpool;

public interface IConnPool {
	 /**
	  * 获得一个可用连接，超过最大连接数时线程等待，直到有有连接释放时返回一个可用连接或者超时返回null 
	  * @param maxWaitTime
	  * @return
	  */
	public IConnection getConn();
	/**
	 * 将释放的空闲连接加入空闲连接池
	 * @param conn
	 */
	public void releaseConn(IConnection conn);
	/**
	 * 销毁连接池
	 */
	public void destroy();
	/**
	 * 获取当前线程对应的连接
	 * @return
	 */
	public IConnection getCurrentConn();
}
