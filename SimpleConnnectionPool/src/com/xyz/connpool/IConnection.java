package com.xyz.connpool;

public interface IConnection {
	/**
	 * 关闭当前连接
	 */
	public void close();
	/**
	 * 销毁当前连接
	 */
	public void destroy();
	//应该具备的其他方法
}
