package com.wanderluster.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class Constant {
	// public static String basedir="\\";

	public static String basedir = "";

	public static String CONTEXT = "/SentimentSearch/";

	public static String indexDir = basedir+ "luceneindex";
	
	public static String webDir =basedir+ "nyucs/mirror/";

	public static String PDFdir = basedir + "datadir/pdfdir/";

	public static String PDFTxtdir = "/home/ethan/datadir/pdftxtdir/";

	public static String ConvertorPATH = "/home/ethan/workshop/xpdf/pdftotext";

	public static String Docdir = basedir + "datadir/worddir/";

	public static String Htmldir = basedir + "datadir/htmldir/";
	
	public static String TermDir = basedir + "corpus/featureWeight";
	
	public static String StopDir = basedir + "corpus/english.stop";
	
	public static String RangeDir = basedir + "corpus/svmRange";
	
	public static String ModelDir = basedir + "corpus/trainVectors.scale.model";
	
	public static String ClusterDir = basedir + "clustering";

	public static String BlogContext = "/blog/showContent.jsp?cid=";

	public static String getRootRealPath(String str){
		
		HttpServletRequest request = ServletActionContext.getRequest();

		basedir=request.getContextPath();
		
		
	   if("pdf".equals(str))
		   
			return request.getRealPath(PDFdir);

	   else if("doc".equals(str))
		   
			return request.getRealPath(Docdir);
	   
	   else if("html".equals(str))
		   
			return request.getRealPath(Htmldir);
	   else if("term".equals(str))
		   return request.getRealPath(TermDir);
	   else if("stop".equals(str))
		   return request.getRealPath(StopDir);
	   else if("range".equals(str))
		   return request.getRealPath(RangeDir);
	   else if("model".equals(str))
		   return request.getRealPath(ModelDir);
	   else if("index".equals(str))
		   return request.getRealPath(indexDir);
	   else if("webDir".equals(str))
		   return request.getRealPath(webDir);
	   else if("cluster".equals(str))
		   return request.getRealPath(ClusterDir);
	   
	   return basedir;
	   
	}

}