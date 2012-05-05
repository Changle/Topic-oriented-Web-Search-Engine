package com.wanderluster.biz;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.wanderluster.db.DocRecordDaoImpl;
import com.wanderluster.util.Constant;



public class DelDocumentAndRecord {
	
	private DocRecordDaoImpl docDao;

	public void delList(String [] strs) throws IOException{
		String indexDir=Constant.getRootRealPath("index");
		
		Directory dir=FSDirectory.getDirectory(indexDir);
		IndexReader reader=IndexReader.open(dir);
		System.out.println("---------2--------");
        for(String id:strs){
        	System.out.println(id);
        	reader.deleteDocuments(new Term("id",id));
        	docDao.delDocById(Integer.valueOf(id));
        }
		reader.close();

		dir.close();

	}

	public DocRecordDaoImpl getDocDao() {
		return docDao;
	}

	public void setDocDao(DocRecordDaoImpl docDao) {
		this.docDao = docDao;
	}
	

	
	

}
