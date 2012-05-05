package com.wanderluster.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.opensymphony.xwork2.ActionSupport;
import com.wanderluster.search.index.HtmlParserExtraction;
import com.wanderluster.search.index.PDFIndex;
import com.wanderluster.search.index.ParserWordAndToIndex;
import com.wanderluster.util.Constant;

public class FileUploadAction extends ActionSupport{
	
	private File file;
	private String fileContentType;
	private String fileFileName;
	
	private PDFIndex pdfi;
	private ParserWordAndToIndex pwat;
	private HtmlParserExtraction htmlparser;
	
	public PDFIndex getPdfi() {
		return pdfi;
	}
	public void setPdfi(PDFIndex pdfi) {
		this.pdfi = pdfi;
	}
	public ParserWordAndToIndex getPwat() {
		return pwat;
	}
	public void setPwat(ParserWordAndToIndex pwat) {
		this.pwat = pwat;
	}
	public HtmlParserExtraction getHtmlparser() {
		return htmlparser;
	}
	public void setHtmlparser(HtmlParserExtraction htmlparser) {
		this.htmlparser = htmlparser;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public String execute() throws Exception{
		
		byte[] buffer=new byte[1024];
		InputStream in=new FileInputStream(file);
		String folder="";
		if("text/html".equals(getFileContentType()))
			folder=Constant.getRootRealPath("html")+"/";
			
		else if ("application/pdf".equals(getFileContentType()))
			folder=Constant.getRootRealPath("pdf")+"/";

		else if ("application/msword".equals(getFileContentType()))
			folder=Constant.getRootRealPath("doc")+"/";

		File input=new File(folder);
		
		if(!input.exists())
			input.mkdir();
		//folder="/home/ethan/workshop/fileUpload/";
		
		OutputStream out=new FileOutputStream(new File(folder+fileFileName));
		
		int length=in.read(buffer);
		while(length>0){
			out.write(buffer);
			length=in.read(buffer);
		}
		in.close();
		out.flush();
		out.close();
		
		if("application/pdf".equals(getFileContentType())){
			
			pdfi.createPDFIndex();	
		}
		else if("application/msword".equals(getFileContentType()))
		{
			
			pwat.addIndex();
		}
		else if("text/html".equals(getFileContentType()))
		{
			htmlparser.addIndex();
		}
		
		return this.SUCCESS;
	}
	

}
