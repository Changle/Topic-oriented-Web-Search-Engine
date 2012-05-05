package com.wanderluster.search.index;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.wanderluster.db.DbRecord;
import com.wanderluster.db.DbRecordDaoImpl;
import com.wanderluster.db.DocRecord;
import com.wanderluster.db.DocRecordDaoImpl;
import com.wanderluster.util.Constant;
import com.wanderluster.util.StringUtil;

public class DbParser {
	
	private DocRecordDaoImpl docDao;
	private DbRecordDaoImpl dbDao;
	public DocRecordDaoImpl getDocDao() {
		return docDao;
	}
	public void setDocDao(DocRecordDaoImpl docDao) {
		this.docDao = docDao;
	}
	public DbRecordDaoImpl getDbDao() {
		return dbDao;
	}
	public void setDbDao(DbRecordDaoImpl dbDao) {
		this.dbDao = dbDao;
	}
	
	public void createDbIndex() throws Exception {
	//XpdfParser xpdfp=new XpdfParser();
	//	String dataDir = Constant.PDFdir;
	//	String dataDir = Constant.getRootRealPath("pdf");
		
		String indexDir = Constant.getRootRealPath("index");
		
		List<DbRecord> dblist=dbDao.getAllDb();

		Analyzer analyzer = new StandardAnalyzer();

		Directory dir = FSDirectory.getDirectory(indexDir);
		
		IndexWriter writer = new IndexWriter(dir, analyzer, false,
				IndexWriter.MaxFieldLength.UNLIMITED);
		
		long ldate = docDao.getLastModify(".db");
		
		
		for (int i = 0; i < dblist.size(); i++) {
			
			DbRecord tmp=dblist.get(i);
			long lasttime=tmp.getLastmodify().getTime();
			
			if (lasttime> ldate) {
					
				DocRecord drec = new DocRecord();
				drec.setDoctype(".db");
				drec.setLastmodify(lasttime);
				
				drec.setFilename(StringUtil.gbkToAscii(tmp.getTitle()));
                
				Document document = new Document();
				
				int id = docDao.createDocRec(drec);
				
				
				document.add(new Field("id", "" + id, Field.Store.YES,
						Field.Index.ANALYZED));
				
				document.add(new Field("cid", "" +tmp.getCid() , Field.Store.YES,
						Field.Index.NO));
				
				document.add(new Field("type", "db", Field.Store.YES,
						Field.Index.ANALYZED));
				
				document.add(new Field("fileName", tmp.getTitle(), Field.Store.YES,
						Field.Index.ANALYZED));
				
				document.add(new Field("contents", tmp.getContent(),
						Field.Store.YES, Field.Index.ANALYZED));
				
				writer.addDocument(document);

		
			}
		
		}

		writer.optimize();
		writer.close();
		
		dir.close();
	}
	

}
