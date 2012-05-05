package com.wanderluster.search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import com.wanderluster.util.Constant;



public class GetSearcheResult {

	public static int eachePageNum = 10;//
	/**
	 * 
	 * @param queryStr
	 * @param headSearchNum
	 * @return
	 * @throws Exception
	 */
	public ScoreDoc[] getScoreDocs(String queryStr, int headSearchNum)
			throws Exception {

		Searcher searcher = new IndexSearcher(Constant.getRootRealPath("index"));		
		TopDocCollector collector = new TopDocCollector(headSearchNum);
		Query query = this.getQuery(queryStr, headSearchNum);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}

	public Query getQuery(String queryStr, int headSearchNum) throws Exception {
		//PaodingAnalyzer analyzer = new PaodingAnalyzer();
		
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
		
		BooleanQuery boolquery=new BooleanQuery();
		String field_fields[] = {"fileName","contents"};
		for(int i=0;i<2;i++)
		{
		QueryParser parser = new QueryParser(field_fields[i], analyzer);
		Query query = parser.parse(queryStr);
		boolquery.add(query,BooleanClause.Occur.SHOULD);
		}
		return boolquery;
	}

	public List<DocumentEntity> getResult(String queryStr, int currentPageNum,int topNum)
			throws Exception {

		List<DocumentEntity> list = new ArrayList<DocumentEntity>();
		Searcher searcher = new IndexSearcher(Constant.getRootRealPath("index"));


		Highlighter highlighter = null;

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
				"<font color='red'><b>", "</b></font>");

		Highlighter highlighterTitle = null;

		SimpleHTMLFormatter formatTitle = new SimpleHTMLFormatter(
				"<FONT color=#c60a00>", "</FONT>");

		ScoreDoc[] hits = this.getScoreDocs(queryStr, topNum);
		Query query = this.getQuery(queryStr, topNum);
		highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(
				query));
		highlighterTitle = new Highlighter(formatTitle, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(200));
		Document doc;
        String fileName="";
		int totalNumber = currentPageNum * eachePageNum;
		if (totalNumber > hits.length)
			totalNumber = hits.length;
		for (int i = (currentPageNum - 1) * eachePageNum; i < totalNumber; i++) {


			doc = searcher.doc(hits[i].doc);
			//			 System.out.println(doc.toString());

			//ʾ
			DocumentEntity docEntity = new DocumentEntity();
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
			TokenStream tokenStream = analyzer.tokenStream(
					"contents", new StringReader(doc.get("contents")));
			docEntity.setContents(highlighter.getBestFragment(tokenStream, doc
					.get("contents")));

			fileName=doc.get("fileName");
			tokenStream = analyzer.tokenStream("fileName",
		    			new StringReader(fileName));
			String forMatt=highlighterTitle.getBestFragment(tokenStream,
					fileName);//
			if(forMatt==null)
				docEntity.setFilename(fileName);
			else docEntity.setFilename(forMatt);
			
			
		    String type1=doc.get("type");
			docEntity.setType(type1);
			docEntity.setId(doc.get("id"));
			if("pdf".equalsIgnoreCase(type1)){
				
				fileName=Constant.CONTEXT+Constant.PDFdir+fileName+"."+type1;
				docEntity.setOriginalFileName(fileName);
			}
			else if("doc".equalsIgnoreCase(type1))
			{
				fileName=Constant.CONTEXT+Constant.Docdir+fileName+"."+type1;
				docEntity.setOriginalFileName(fileName);
				
			}
			else if("html".equalsIgnoreCase(type1))
			{
				String realName=doc.get("realName");
				fileName=Constant.CONTEXT+Constant.Htmldir+realName;
				docEntity.setOriginalFileName(fileName);
				
			}
			else if("db".equalsIgnoreCase(type1))
			{
				String cid=doc.get("cid");
				fileName=Constant.BlogContext+cid;
				docEntity.setOriginalFileName(fileName);
				
			}
			else if("WebHtml".equalsIgnoreCase(type1))
			{
				String link=doc.get("link");
				//fileName=Constant.BlogContext+cid;
				docEntity.setOriginalFileName(link);
				
			}
		

			list.add(docEntity);
		}
		return list;
	}

}

