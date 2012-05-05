package com.wanderluster.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanderluster.train.trainData;
import com.wanderluster.util.Constant;

public class Classification extends ActionSupport{
	
	
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String featurePath = Constant.getRootRealPath("term");
		String modelPath = Constant.getRootRealPath("model");
		String rangePath = Constant.getRootRealPath("range");
		String stopPath=Constant.getRootRealPath("stop");
		
		//System.out.println(content);
		
		String vc=trainData.convertToVector(content, stopPath, featurePath, rangePath);
		int out=trainData.predict(vc,modelPath);
	
		//System.out.println(out);
		
		
		//request.setAttribute("pageNumBean", pageBean);
	
		//ActionContext.getContext().getSession().put("pageUrl","SearchAction.action?page=");
		//ActionContext.getContext().getSession().put("rsize",recordCount);
		ActionContext.getContext().getSession().put("result",out);

		//*/
		return SUCCESS;
	}


	private String content;

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}



}
