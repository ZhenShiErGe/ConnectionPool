package com.xyz.connpool;

public interface IConnPool {
	 /**
	  * ���һ���������ӣ��������������ʱ�̵߳ȴ���ֱ�����������ͷ�ʱ����һ���������ӻ��߳�ʱ����null 
	  * @param maxWaitTime
	  * @return
	  */
	public IConnection getConn();
	/**
	 * ���ͷŵĿ������Ӽ���������ӳ�
	 * @param conn
	 */
	public void releaseConn(IConnection conn);
	/**
	 * �������ӳ�
	 */
	public void destroy();
	/**
	 * ��ȡ��ǰ�̶߳�Ӧ������
	 * @return
	 */
	public IConnection getCurrentConn();
}
