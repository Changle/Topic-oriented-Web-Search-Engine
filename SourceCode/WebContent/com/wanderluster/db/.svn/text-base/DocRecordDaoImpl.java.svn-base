package com.wanderluster.db;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DocRecordDaoImpl extends SqlMapClientDaoSupport implements DocRecordDao{

	public int createDocRec(DocRecord doc) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert("createDocRec", doc);
	
		return doc.getId();
	}

	public DocRecord getByFileName(String fname) {
		// TODO Auto-generated method stub
		return (DocRecord)getSqlMapClientTemplate().queryForObject("getByFileName", fname);
	}

	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("deleteDoc", id);
		
	}

	@Override
	public long getLastModify(String docType) {
 
		DocRecord dr=(DocRecord)getSqlMapClientTemplate().queryForObject("getDocTop1", docType);
		if(dr==null)
			return 0;
		else
			return dr.getLastmodify();
	}

	@Override
	public int delAllDoc() {
	

		return  this.getSqlMapClientTemplate().delete("deleteAllDoc");
	}

	@Override
	public int delDocById(int id) {
		
		return this.getSqlMapClientTemplate().delete("deleteDoc", id);
		
	}
	

}
