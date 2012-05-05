package com.wanderluster.action;

import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanderluster.db.Admin;
import com.wanderluster.db.AdminDao;

public class LoginAction extends ActionSupport {

	private String username;
	private String password;
	private AdminDao adminDao;

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	@Override
	public String execute() throws Exception {
		System.out.println(username);
		System.out.println(password);

		Admin admin = adminDao.getAdmin(username, password);

		
		if (admin != null)
		//if("admin".equals(username)&&"admin".equals(password))
		{
		ActionContext ctx=ActionContext.getContext();
	    SessionMap session = (SessionMap)ctx.getSession();
	    session.put("user",admin);
			return this.SUCCESS;
		}
		else
			return this.ERROR;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
