package com.wanderluster.search.index;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import com.wanderluster.db.DocRecord;
import com.wanderluster.db.DocRecordDaoImpl;
import com.wanderluster.parsepdf.XpdfParser;
import com.wanderluster.util.Constant;
import com.wanderluster.util.StringUtil;



public class PDFIndex {

	private DocRecordDaoImpl docDao;

	
	public void setDocDao(DocRecordDaoImpl docDao) {
		this.docDao = docDao;
	}
    

	
	@Test
	public void createPDFIndex() throws Exception {
		XpdfParser xpdfp=new XpdfParser();
	//	String dataDir = Constant.PDFdir;
		String dataDir = Constant.getRootRealPath("pdf");
  //String dats=Constant.getRootRealPath("pdf");
		String indexDir = Constant.getRootRealPath("index");
        String txtContents="";
		File[] files = new File(dataDir).listFiles();
		System.out.println(files.length);
		Analyzer analyzer = new SnowballAnalyzer("english",StopAnalyzer.ENGLISH_STOP_WORDS);
		//Analyzer analyzer=new MMAnalyzer();
		Directory dir = FSDirectory.getDirectory(indexDir);
		IndexWriter writer = new IndexWriter(dir, analyzer, false,
				IndexWriter.MaxFieldLength.UNLIMITED);
		long ldate = docDao.getLastModify(".pdf");
		for (int i = 0; i < files.length; i++) {
			if (files[i].lastModified()> ldate) {
			
	
			    txtContents=xpdfp.getPDFFileContent(files[i].getCanonicalPath());
		      /*  FileInputStream instream=new FileInputStream(files[i].getCanonicalPath());
		        PDFParser parser=new PDFParser(instream);
		        parser.parse();
		        PDDocument pdfdocument=parser.getPDDocument();
		        PDFTextStripper pdfstripper=new PDFTextStripper("UTF-8");
		        txtContents=pdfstripper.getText(pdfdocument);*/
		        
				DocRecord drec = new DocRecord();
				drec.setDoctype(".pdf");
				drec.setLastmodify(files[i].lastModified());
				drec.setFilename(StringUtil.gbkToAscii(files[i].getName()));
                
				Document document = new Document();
				int id = docDao.createDocRec(drec);
				int index = files[i].getName().indexOf(".pdf");
				document.add(new Field("id", "" + id, Field.Store.YES,
						Field.Index.ANALYZED));
				document.add(new Field("type", "pdf", Field.Store.YES,
						Field.Index.ANALYZED));
				document.add(new Field("fileName", files[i].getName()
						.substring(0, index), Field.Store.YES,
						Field.Index.ANALYZED));
				document.add(new Field("contents", txtContents,
						Field.Store.YES, Field.Index.ANALYZED));
				writer.addDocument(document);

		
			}
		
		}
		File file = new File(Constant.PDFTxtdir);
		if (file.exists())
			file.delete();
		writer.optimize();
		writer.close();
		
		dir.close();
	}

}
