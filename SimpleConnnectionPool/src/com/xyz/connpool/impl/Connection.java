package com.xyz.connpool.impl;

import com.xyz.connpool.IConnPool;
import com.xyz.connpool.IConnection;

public class Connection implements IConnection {
	private String name;
	private IConnPool connPool;
	public Connection(IConnPool connPool,String name){
		this.connPool=connPool;
		this.name=name;
	}
	@Override
	public void close() {
		this.connPool.releaseConn(this);
	}
	@Override
	public void destroy() {
		System.out.println("destroy connection---"+name);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

}
