package com.wanderluster.action;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanderluster.search.AdvanceGetSearcheResult;
import com.wanderluster.search.DocumentEntity;
import com.wanderluster.util.PageNumBean;

public class AdvanceSearchAction extends ActionSupport{
	
	
	
	@Override
	public String execute() throws Exception {
		
/*	System.out.println(this.fieldname);	
	System.out.println(this.qtype);
	System.out.println(this.pagetype);
	System.out.println(this.totalpage);
	System.out.println(this.filetype);
		*/
		
      
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		
		int currentNum=1;
		AdvanceGetSearcheResult gsr=new AdvanceGetSearcheResult(this.pagetype,this.filetype,this.qtype,this.fieldname,this.totalpage);
		List<DocumentEntity> list=gsr.getResult(1);
		int recordCount=gsr.getScoreDocs().length;
		System.out.println("----advance-- ---"+list.size()+"-------------");
		PageNumBean pageBean=null;
		System.out.println("------ "+recordCount);
		pageBean=(PageNumBean)request.getAttribute("pageNumBean");
		if(pageBean==null){
			pageBean=new PageNumBean(1,recordCount,this.pagetype,this.pagetype);
			request.setAttribute("pageNumBean", pageBean);
		}
		    
		Integer downPageNum =currentNum+1;
		if(downPageNum>pageBean.getPageCount())downPageNum=null;
		Integer upPageNum =currentNum-1;
		if(upPageNum==0)upPageNum=null;
		pageBean.setUpPageNum(upPageNum);
		pageBean.setDownPageNum(downPageNum);
		pageBean.setCurrentNum(currentNum);
		request.setAttribute("pageNumBean", pageBean);
		request.setAttribute("sk",this.fieldname);
		request.setAttribute("sk1",URLEncoder.encode(fieldname,"UTF-8"));
		ActionContext.getContext().getSession().put("pageUrl","SearchAction.action?page=");
		ActionContext.getContext().getSession().put("rsize",recordCount);
		ActionContext.getContext().getSession().put("rlist",list);

		
		return SUCCESS;

	}
	private String fieldname;
	private String qtype;
	private int pagetype;
	private int totalpage;
	private String filetype;
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public int getPagetype() {
		return pagetype;
	}
	public void setPagetype(int pagetype) {
		this.pagetype = pagetype;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
}
