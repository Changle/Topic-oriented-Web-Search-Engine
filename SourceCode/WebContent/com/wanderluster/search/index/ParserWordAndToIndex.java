package com.wanderluster.search.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jeasy.analysis.MMAnalyzer;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Test;

import com.wanderluster.db.DocRecord;
import com.wanderluster.db.DocRecordDaoImpl;
import com.wanderluster.util.Constant;
import com.wanderluster.util.StringUtil;

public class ParserWordAndToIndex {

	private DocRecordDaoImpl docDao;

	String indexDir;

	@Test
	public void addIndex() throws IOException {
		
		String temppath = Constant.getRootRealPath("doc");
		
		indexDir= Constant.getRootRealPath("index");
		
		//String temppath = Constant.Docdir;
		
		File[] files = new File(temppath).listFiles();

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
		Directory dir = FSDirectory.getDirectory(indexDir);
		IndexWriter writer = new IndexWriter(dir, analyzer, false,
				IndexWriter.MaxFieldLength.UNLIMITED);
		long ldate = docDao.getLastModify(".doc");
		String strtmp = "";
		try {

			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].lastModified() + "------1--------");
				System.out.println(ldate + "------2--------");
				if (files[i].lastModified() > ldate) {
					System.out.println("--doc----3--------");

					DocRecord drec = new DocRecord();

					// docDao.createDoc(drec);
					strtmp = files[i].getAbsolutePath();

					FileInputStream fis = new FileInputStream(files[i]
							.getAbsolutePath());
					WordExtractor wordExtractor = new WordExtractor(fis);

					Document document = new Document();

					drec.setFilename(StringUtil.gbkToAscii(files[i].getName()));
					drec.setDoctype(".doc");
					drec.setLastmodify(files[i].lastModified());
					// System.out.println(files[i].getName());

					int index = files[i].getName().indexOf(".doc");
					int id = docDao.createDocRec(drec);
					document.add(new Field("id", "" + id, Field.Store.YES,
							Field.Index.ANALYZED));
					System.out.println(id);
					document.add(new Field("type", "doc", Field.Store.YES,
							Field.Index.ANALYZED));
					document.add(new Field("fileName", files[i].getName()
							.substring(0, index), Field.Store.YES,
							Field.Index.ANALYZED));
					document.add(new Field("contents", wordExtractor.getText(),
							Field.Store.YES, Field.Index.ANALYZED));
					writer.addDocument(document);

				}

				// System.out.println("----"+files[i].getName().substring(0,index));

			}
			writer.optimize();
			writer.close();
			dir.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public DocRecordDaoImpl getDocDao() {
		return docDao;
	}

	public void setDocDao(DocRecordDaoImpl docDao) {
		this.docDao = docDao;
	}

}
