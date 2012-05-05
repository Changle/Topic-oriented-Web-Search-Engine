package com.wanderluster.db;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DbRecordDaoImpl extends SqlMapClientDaoSupport implements DbRecordDao{

	@Override
	public int createDbRec(DbRecord doc) {
		
		this.getSqlMapClientTemplate().insert("createDbRec", doc);
		
		return doc.getCid();
	}

	@Override
	public int delAllDb() {
		
		return  this.getSqlMapClientTemplate().delete("deleteAllDb");
	}

	@Override
	public int delDbById(int id) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("deleteDb", id);
	}

	@Override
	public DbRecord getDbById(int id) {
		// TODO Auto-generated method stub
		return (DbRecord)getSqlMapClientTemplate().queryForObject("getDbById", id);
	}

	@Override
	public List<DbRecord> getAllDb() {
		// TODO Auto-generated method stub
		return (List<DbRecord>)getSqlMapClientTemplate().queryForList("getAllDb");
	}



	

}
