package com.xyz.connpool;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnPool implements IConnPool {
	private int initNum;
	private int maxNum;
	private int hasAlready;
	private long maxWaitTime;
	private final List<IConnection> freeConnList;
	private final List<IConnection> activeConnList;
	private static ThreadLocal<IConnection> threadLocalVar=new ThreadLocal<IConnection>(){
		protected IConnection initialValue() {
			return null;
		};
	};
	public ConnPool(){
		this.initNum=3;
		this.maxNum=5;
		this.hasAlready=0;
		this.maxWaitTime=1000;
		this.freeConnList=new ArrayList<IConnection>(maxNum);
		this.activeConnList=new ArrayList<IConnection>(maxNum);
		init();
	}
	public ConnPool(int initNum,int maxNum,long maxWaitTime){
		this.initNum=initNum;
		this.maxNum=maxNum;
		this.hasAlready=0;
		this.maxWaitTime=maxWaitTime;
		this.freeConnList=new ArrayList<IConnection>(maxNum);
		this.activeConnList=new ArrayList<IConnection>(maxNum);
		init();
	}
	private void init(){
		for(int i=0;i<this.initNum;i++){
			synchronized (this) {
				this.freeConnList.add(new Connection(this,UUID.randomUUID().toString()));
				hasAlready++;
			}
		}
	}
	@Override
	public synchronized IConnection getConn()  {
			IConnection conn=null;
			if(!this.freeConnList.isEmpty()){
				conn=this.freeConnList.remove(0);
				if(conn!=null)
					threadLocalVar.set(conn);//为线程绑定连接
				this.activeConnList.add(conn);
				return conn;
			}
			if(hasAlready<maxNum){
				conn = new Connection(this,UUID.randomUUID().toString());
				hasAlready++;
				if(conn!=null)
					threadLocalVar.set(conn);//为线程绑定连接
				this.activeConnList.add(conn);
				return conn;
			}
			try {
				this.wait(maxWaitTime);
				if(!this.freeConnList.isEmpty()){
					conn=this.freeConnList.remove(0);
					if(conn!=null)
						threadLocalVar.set(conn);//为线程绑定连接
					this.activeConnList.add(conn);
					return conn;
				}
				return null;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
	}
	@Override
	public synchronized void releaseConn(IConnection conn) {
		if(this.activeConnList.contains(conn)){
			this.freeConnList.add(conn);
			this.activeConnList.remove(conn);
			threadLocalVar.remove();
			this.notify();
			System.out.println("释放连接"+conn);
		}
	}
	@Override
	public synchronized void destroy() {
		for(IConnection conn:this.freeConnList){
			conn.destroy();
		}
		for(IConnection conn:this.activeConnList){
			conn.destroy();
		}
		this.hasAlready=0;
	}
	@Override
	public IConnection getCurrentConn() {
		return threadLocalVar.get();
	}
	
}
