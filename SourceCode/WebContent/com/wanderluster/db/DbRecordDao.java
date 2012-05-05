package com.wanderluster.db;

import java.util.List;

public interface DbRecordDao {
	
	public int createDbRec(DbRecord doc);
	public DbRecord getDbById(int id);
	//public long getLastmodifyById(int id);
	public List<DbRecord> getAllDb();
	public int delDbById(int id);
	public int delAllDb();

}
