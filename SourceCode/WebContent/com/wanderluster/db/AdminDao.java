package com.wanderluster.db;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AdminDao extends SqlMapClientDaoSupport{
	
	public Admin getAdmin(String name,String pwd){
    	Map map=new HashMap();
    	map.put("username", name);
    	map.put("password", pwd);
		
		return (Admin)this.getSqlMapClientTemplate().queryForObject("getByNameAndPwd",map);
	}

}
