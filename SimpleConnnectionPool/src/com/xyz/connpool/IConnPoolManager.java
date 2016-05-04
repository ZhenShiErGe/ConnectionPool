package com.xyz.connpool;

public interface IConnPoolManager {
	/**
	 * ��������id��ȡ���ӳ�
	 * @param hostId
	 * @return
	 */
	public IConnPool getPool(int hostId);
	
	/**
	 * ��ȡһ��������hostId������
	 * @param hostId
	 * @return
	 */
	public IConnection getConn(int hostId);
	
	/**
	 * �ر�ָ��������ĳ������
	 * @param hostId
	 * @param conn
	 */
	public void close(int hostId,IConnection conn);
	/**
	 * ע��ĳ�����������ӳ�
	 * @param poolName
	 */
	public void destroy(int hostId);
	/**
	 * ע���������ӳ�
	 */
	public void destory();
}
