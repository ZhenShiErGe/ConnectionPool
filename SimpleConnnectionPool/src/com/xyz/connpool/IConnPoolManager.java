package com.xyz.connpool;

public interface IConnPoolManager {
	/**
	 * 根据主机id获取连接池
	 * @param hostId
	 * @return
	 */
	public IConnPool getPool(int hostId);
	
	/**
	 * 获取一个到主机hostId的连接
	 * @param hostId
	 * @return
	 */
	public IConnection getConn(int hostId);
	
	/**
	 * 关闭指定主机的某个链接
	 * @param hostId
	 * @param conn
	 */
	public void close(int hostId,IConnection conn);
	/**
	 * 注销某个主机的连接池
	 * @param poolName
	 */
	public void destroy(int hostId);
	/**
	 * 注销所有连接池
	 */
	public void destory();
}
