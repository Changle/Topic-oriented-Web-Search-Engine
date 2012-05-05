package com.wanderluster.search.index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.wanderluster.db.DocRecordDaoImpl;
import com.wanderluster.util.Constant;

public class InitIndex {
	
    private DocRecordDaoImpl docDao;

	
	public DocRecordDaoImpl getDocDao() {
		return docDao;
	}

	public void setDocDao(DocRecordDaoImpl docDao) {
		this.docDao = docDao;
	}

	public void init() throws IOException {

		String stopDir=Constant.getRootRealPath("stop");
		BufferedReader reader = new BufferedReader(new FileReader(
				stopDir));
		ArrayList<String> as = new ArrayList<String>();
		String oneline;
		while((oneline=reader.readLine())!=null)
		{
			as.add(oneline);
		}
		String[] stopWords = new String[as.size()];
		as.toArray(stopWords);
		Analyzer analyzer = new SnowballAnalyzer("English", stopWords);
		String indexDir = Constant.getRootRealPath("index");
		Directory dir = FSDirectory.getDirectory(indexDir);

		IndexWriter writer = new IndexWriter(dir, analyzer, true,
				IndexWriter.MaxFieldLength.LIMITED);

		Document document = new Document();
		
		writer.addDocument(document);

		writer.optimize();

		writer.close();
		
		docDao.delAllDoc();
	}

}