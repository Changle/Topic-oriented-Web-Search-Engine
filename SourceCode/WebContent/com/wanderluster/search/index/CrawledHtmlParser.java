package com.wanderluster.search.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.htmlparser.util.ParserException;
import org.junit.Test;

import com.wanderluster.db.DocRecord;
import com.wanderluster.db.DocRecordDaoImpl;
import com.wanderluster.parsehtml.HtmlParser;
import com.wanderluster.util.Constant;
import com.wanderluster.util.StringUtil;

public class CrawledHtmlParser {

	private DocRecordDaoImpl docDao;

	String indexDir;

	long ldate;

	/**
	 * 
	 * 
	 * @throws IOException
	 * @throws ParserException
	 */
	public void addIndex() throws IOException, ParserException {

		String temppath = Constant.getRootRealPath("webDir");
		
		System.out.println(temppath);

		File localFile = new File(temppath);
		
		indexDir= Constant.getRootRealPath("index");
		
		System.out.println(indexDir);

		String stopDir = Constant.getRootRealPath("stop");
		BufferedReader reader = new BufferedReader(new FileReader(stopDir));
		ArrayList<String> as = new ArrayList<String>();
		String oneline;
		while ((oneline = reader.readLine()) != null) {
			as.add(oneline);
		}
		String[] stopWords = new String[as.size()];
		as.toArray(stopWords);
		Analyzer analyzer = new SnowballAnalyzer("English", stopWords);

		Directory dir = FSDirectory.getDirectory(indexDir);

		ldate = docDao.getLastModify(".WebHtml");

		IndexWriter writer = new IndexWriter(dir, analyzer, false,
				IndexWriter.MaxFieldLength.UNLIMITED);

		SubIndexBuilder(writer, localFile);

		writer.optimize();
		writer.close();

	}

	public void SubIndexBuilder(IndexWriter fswriter, File subPath)
			throws IOException, ParserException {

		File[] filelist = subPath.listFiles();
		for (int i = 0; i < filelist.length; i++) {
			File file = filelist[i];
			if (file.isDirectory()) {
				SubIndexBuilder(fswriter, file);
			} else if (IsValidType(file.getAbsolutePath())) {
				fileindexBuilder(fswriter, file);
			}
		}

	}

	private boolean IsValidType(String name) {
		
		if(name.indexOf(".htm")!=-1||
				  name.indexOf(".html")!=-1||
				  name.indexOf(".asp")!=-1||
				  name.indexOf(".aspx")!=-1||
				  name.indexOf(".php")!=-1||
				  name.indexOf(".jsp")!=-1||
				  name.indexOf(".shtm")!=-1||
				  name.indexOf(".shtml")!=-1)
					
					return true;
				else
					return false;
		
		/*
		if (name.indexOf(".jpg") != -1 || name.indexOf(".jpeg") != -1
				|| name.indexOf(".gif") != -1 || name.indexOf(".png") != -1
				|| name.indexOf(".bmp") != -1 || name.indexOf(".JPG") != -1 || name.indexOf(".JPEG") != -1
				|| name.indexOf(".GIF") != -1 || name.indexOf(".PNG") != -1
				|| name.indexOf(".BMP") != -1 )

			return false;

		else
			return true;
			
			*/

	}

	public void fileindexBuilder(IndexWriter fswriter, File subfile)
			throws IOException, ParserException {

		if (subfile.lastModified() > ldate) {

			DocRecord drec = new DocRecord();

			String strtmp = subfile.getAbsolutePath();

			HtmlParser parser = new HtmlParser();

			String title = parser.GetTitle(strtmp, "UTF-8");

			Document document = new Document();

			drec.setFilename(title);

			drec.setDoctype(".WebHtml");

			drec.setLastmodify(subfile.lastModified());

			int id = docDao.createDocRec(drec);

			String link = getRealLink(strtmp);
			
			System.out.println("Index "+link);

			document.add(new Field("id", "" + id, Field.Store.YES,
					Field.Index.ANALYZED));

			document.add(new Field("type", "WebHtml", Field.Store.YES,
					Field.Index.ANALYZED));

			document.add(new Field("link", link, Field.Store.YES,
					Field.Index.NO));

			document.add(new Field("fileName", title, Field.Store.YES,
					Field.Index.ANALYZED));

			document.add(new Field("contents", parser.ParseHtmlText(strtmp,
					"UTF-8"), Field.Store.YES, Field.Index.ANALYZED));

			fswriter.addDocument(document);
			
		
			//fswriter.optimize();
		}

	}

	public String getRealLink(String temppath) {

		int start = temppath.indexOf("mirror") + 7;

		String link = "http://" + temppath.substring(start);

		return link;

	}

	public DocRecordDaoImpl getDocDao() {
		return docDao;
	}

	public void setDocDao(DocRecordDaoImpl docDao) {
		this.docDao = docDao;
	}
	


}
