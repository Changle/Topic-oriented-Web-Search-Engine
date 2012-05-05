package com.wanderluster.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.wanderluster.search.index.CrawledHtmlParser;
import com.wanderluster.search.index.DbParser;
import com.wanderluster.search.index.HtmlParserExtraction;
import com.wanderluster.search.index.InitIndex;
import com.wanderluster.search.index.PDFIndex;
import com.wanderluster.search.index.ParserWordAndToIndex;

public class FileIndexAction extends ActionSupport{
	
	private PDFIndex pdfi;
	private ParserWordAndToIndex pwat;
	private HtmlParserExtraction htmlparser;
	private DbParser dbparser;
	private CrawledHtmlParser webhtmlparser;



	private InitIndex init;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String flag=request.getParameter("flag");
		
	
		if("pdf".equals(flag)){
			
			pdfi.createPDFIndex();	
		}
		else if("doc".equals(flag))
		{
			
			pwat.addIndex();
		}
		else if("html".equals(flag))
		{
			htmlparser.addIndex();
		}
		else if("db".equals(flag))
		{
			dbparser.createDbIndex();
		}
		else if("web".equals(flag))
		{
			webhtmlparser.addIndex();
		}
		else
		{
			
			System.out.println("------------1----------");
			
			init.init();
		}

		return this.SUCCESS;
		
	}

	public ParserWordAndToIndex getPwat() {
		return pwat;
	}

	public void setPwat(ParserWordAndToIndex pwat) {
		this.pwat = pwat;
	}

	public PDFIndex getPdfi() {
		return pdfi;
	}

	public void setPdfi(PDFIndex pdfi) {
		this.pdfi = pdfi;
	}

	public void setInit(InitIndex init) {
		this.init = init;
	}
	
	public HtmlParserExtraction getHtmlparser() {
		return htmlparser;
	}

	public void setHtmlparser(HtmlParserExtraction htmlparser) {
		this.htmlparser = htmlparser;
	}

	public CrawledHtmlParser getWebhtmlparser() {
		return webhtmlparser;
	}

	public void setWebhtmlparser(CrawledHtmlParser webhtmlparser) {
		this.webhtmlparser = webhtmlparser;
	}

	public DbParser getDbparser() {
		return dbparser;
	}

	public void setDbparser(DbParser dbparser) {
		this.dbparser = dbparser;
	}

	
	

}
