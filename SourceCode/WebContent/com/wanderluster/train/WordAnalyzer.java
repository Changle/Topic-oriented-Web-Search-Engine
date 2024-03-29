package com.wanderluster.train;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;

import com.wanderluster.util.XmlUtil;



public class WordAnalyzer {
	
	public String RESOURCE_PATH;
	public String STOP_PATH;
	public String TRAINING_TEXT_ROOT_PATH;
	
	public WordAnalyzer(String resoucePath, String stopPath,String trainPath)
	{
		
		RESOURCE_PATH= resoucePath;
		STOP_PATH=stopPath;
		TRAINING_TEXT_ROOT_PATH=trainPath;
		
	}
	
	private String[] getStopWords() {
		try {
			ArrayList<String> stopwordsArray = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(
					STOP_PATH));
			String line = null;
			while (null != (line = br.readLine())) {
				stopwordsArray.add(line);
			}
			String[] stopwords = new String[stopwordsArray.size()];
			return stopwordsArray.toArray(stopwords);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private TokenStream getTokenStream(String text) {
		// p(text);
		Analyzer az = new SnowballAnalyzer("English", getStopWords());
		return az.tokenStream("contents", new StringReader(text));
	}

	public ArrayList<String> getDoc(String text) {
		TokenStream ts = getTokenStream(text);
		ArrayList<String> alist = new ArrayList<String>();
		try {
			while (true) {
				Token token = ts.next();
				if (token == null)
					break;
				String word = token.termText();
				Pattern p = Pattern.compile("[1-9]", Pattern.CASE_INSENSITIVE
						| Pattern.DOTALL | Pattern.MULTILINE);
				Matcher m = p.matcher(word);
				if (!m.find()) {
					alist.add(word);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return alist;
	}

	public HashMap<String, Integer> appendWordFreq(String text,
			HashMap<String, Integer> old) {
		// System.out.println(text);
		TokenStream ts = getTokenStream(text);
		// System.out.println("asdf");
		try {
			while (true) {
				Token token = ts.next();
				if (token == null)
					break;
				String word = token.termText();
				if (!old.containsKey(word)) {
					old.put(word, 1);
				} else {
					old.put(word, old.get(word) + 1);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return old;
	}

	public String readFile(String path) {

		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = null;
			while (null != (line = br.readLine())) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public List<Map.Entry<String, Integer>> convertMapToSortedList(
			HashMap<String, Integer> s) {
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
				s.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1,
					Map.Entry<String, Integer> e2) {
				Integer i1 = (Integer) e1.getValue();
				Integer i2 = (Integer) e2.getValue();
				return i2.compareTo(i1);
			}
		});
		return list;
	}

	public void p(String str) {
		System.out.println(str);
	}

	public void addTokenStream(File root) {
		XmlUtil xu = new XmlUtil();
		if (!root.isHidden()) {
			if (!root.isDirectory()) {
				String raw = readFile(root.getAbsolutePath());
				String text = xu.extractContent(raw);
				TokenStream ts = getTokenStream(text);
				// allDocsTokenStream.add(ts);
				try {
					ArrayList<String> alist = new ArrayList<String>();

					while (true) {
						Token token = ts.next();
						if (token == null)
							break;
						String word = token.termText();
						Pattern p = Pattern.compile("[1-9]",
								Pattern.CASE_INSENSITIVE | Pattern.DOTALL
										| Pattern.MULTILINE);
						Matcher m = p.matcher(word);
						if (!m.find()) {
							alist.add(word);
						}
					}
					docList.add(alist);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else {
				File[] files = root.listFiles();
				for (int i = 0; i < files.length; i++) {
					addTokenStream(files[i]);
				}
			}
		}
	}

	public void initialDocList(File root) {
		docList.clear();
		addTokenStream(root);
	}

	public void initialTotalTermFreqMap() {
		for (ArrayList<String> ts : docList) {
			for (int i = 0; i < ts.size(); i++) {
				String word = ts.get(i);
				if (!totalTermFreq.containsKey(word)) {
					totalTermFreq.put(word, 1);
				} else {
					totalTermFreq.put(word, totalTermFreq.get(word) + 1);
				}
			}

		}

	}

	public void initialGroupAvgVector() {
		for (String s : groupPool) {
			initialDocList(new File(TRAINING_TEXT_ROOT_PATH + s));

			ArrayList<HashMap<String, Double>> mean = new ArrayList<HashMap<String, Double>>();
			for (int i = 0; i < docList.size(); i++) {
				ArrayList<String> list = docList.get(i);
				mean.add(generateDocVector(list));
			}
			HashMap<String, Double> m = getAverageVector(mean);
			groupAvgVector.put(s, m);
		}
	}

	public void writeFile(String filename, String context) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			bw.write(context);
			bw.close();
		} catch (Exception e) {
		}
	}

	public void generateFeature() {
		List<Entry<String, Integer>> list = convertMapToSortedList(totalTermFreq);
		System.out.println(totalTermFreq.size());
		if (list.size() > VECTOR_SIZE) {
			list = list.subList(0, VECTOR_SIZE);
			for (Entry<String, Integer> e : list) {
				feature.add(e.getKey());
			}
		} else {
			for (Entry<String, Integer> e : list) {
				feature.add(e.getKey());
			}
		}
	}

	public void calculateTermFreq(String term) {
		for (ArrayList<String> ts : docList) {
			for (int i = 0; i < ts.size(); i++) {
				String word = ts.get(i);
				if (word.equals(term)) {
					if (!termFreqInAllArticles.containsKey(word)) {
						termFreqInAllArticles.put(word, 1);
					} else {
						termFreqInAllArticles.put(word,
								termFreqInAllArticles.get(word) + 1);
					}
					break;
				}
			}
		}

	}

	public void calculateTermFreqInAllArticles() {
		for (String e : feature) {
			calculateTermFreq(e);
		}
	}

	public void calculateIDF() {
		initialDocList(new File(TRAINING_TEXT_ROOT_PATH));
		initialTotalTermFreqMap();
		generateFeature();
		calculateTermFreqInAllArticles();
		for (String term : feature) {
			System.out.println(term
					+ "\t"
					+ Math.log((double) 1702
							/ (double) termFreqInAllArticles.get(term) + 0.01));
		}
	}

	public void loadIDF() {
		String idfFile = readFile(RESOURCE_PATH + "idf");
		String line[] = idfFile.split("\n");
		for (int i = 0; i < line.length; i++) {
			// p(line[i]);
			String words[] = line[i].split("\t");
			idf.put(words[0], Double.parseDouble(words[1]));
		}
		// System.out.println(idf.size());
	}

	public HashMap<String, Double> generateDocVector(ArrayList<String> text) {
		HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
		// calculate word frequency in this text
		for (int i = 0; i < text.size(); i++) {
			String word = text.get(i);
			if (!freqMap.containsKey(word)) {
				freqMap.put(word, 1);
			} else {
				freqMap.put(word, freqMap.get(word) + 1);
			}
		}
		HashMap<String, Double> vectorMap = new HashMap<String, Double>();
		for (String f : feature) {
			Integer freq = freqMap.get(f);
			if (freq == null) {
				freq = 0;
				continue;
			}
			double tfidf = ((double) freq) * idf.get(f);
			vectorMap.put(f, tfidf);
		}
		// calculate weight:
		double sum = 0;
		for (Entry<String, Double> e : vectorMap.entrySet()) {
			sum += e.getValue();
		}
		for (Entry<String, Double> e : vectorMap.entrySet()) {
			double tmp = e.getValue();
			vectorMap.put(e.getKey(), Math.sqrt((tmp) / (sum)));
		}
		return vectorMap;
	}

	public HashMap<String, Double> getAverageVector(
			ArrayList<HashMap<String, Double>> vectors) {
		HashMap<String, Double> sum = new HashMap<String, Double>();
		for (int i = 0; i < vectors.size(); i++) {
			HashMap<String, Double> tmp = vectors.get(i);
			for (Entry<String, Double> e : tmp.entrySet()) {
				String key = e.getKey();
				Double value = e.getValue();
				if (sum.containsKey(key)) {
					sum.put(key, sum.get(key) + value);
				} else {
					sum.put(key, value);
				}
			}
		}
		for (Entry<String, Double> e : sum.entrySet()) {
			sum.put(e.getKey(), e.getValue() / (double) vectors.size());
		}
		return sum;
	}

	// public static String group2[] =
	// {"investing_ideas_strategies","mutual_funds"};

	public String matchArticle(String text) {
		ArrayList<String> a = getDoc(text);
		HashMap<String, Double> h = generateDocVector(a);
		double min = Double.MAX_VALUE;
		String minGroup = null;
		for (Entry<String, HashMap<String, Double>> e : groupAvgVector
				.entrySet()) {
			String groupName = e.getKey();
			HashMap<String, Double> groupVector = e.getValue();
			double diff = difference(groupVector, h);
			if (diff < min) {
				min = diff;
				minGroup = groupName;
			}
		}
		return minGroup;
	}

	public double difference(HashMap<String, Double> h1,
			HashMap<String, Double> h2) {
		double sum = 0.0;
		for (Entry<String, Double> e : h1.entrySet()) {
			String key = e.getKey();
			double value = e.getValue();
			double value2 = 0.0;
			if (h2.containsKey(key)) {
				value2 = h2.get(key);
			}
			sum += Math.pow((value - value2), 2);
		}
		return Math.sqrt(sum) / (double) h1.size();
	}

	public void saveGroupVectorToFile() {
		for (Entry<String, HashMap<String, Double>> e : groupAvgVector
				.entrySet()) {
			String groupName = e.getKey();
			p("saving group: " + groupName);
			StringBuffer groupVector = new StringBuffer();
			for (Entry<String, Double> e2 : e.getValue().entrySet()) {
				groupVector.append(e2.getKey() + "\t" + e2.getValue() + "\n");
			}
			writeFile(RESOURCE_PATH + groupName, groupVector.toString());
		}
	}

	public void saveFeatureToFile() {

		StringBuffer sb = new StringBuffer();
		for (String s : feature) {
			sb.append(s + "\n");
		}
		writeFile(RESOURCE_PATH + "feature", sb.toString());
	}

	public void loadFeatureFromFile() {
		feature.clear();
		String str = readFile(RESOURCE_PATH + "feature");
		String features[] = str.split("\n");
		for (int i = 0; i < features.length; i++) {
			feature.add(features[i]);
		}
	}

	public void loadGroupVectorFromFile() {
		groupAvgVector.clear();
		for (String s : groupPool) {
			String temp = readFile(RESOURCE_PATH + s);
			String features[] = temp.split("\n");
			HashMap<String, Double> hm = new HashMap<String, Double>();
			for (int i = 0; i < features.length; i++) {
				String group[] = features[i].split("\t");
				hm.put(group[0], Double.parseDouble(group[1]));
			}
			groupAvgVector.put(s, hm);
		}
	}

	HashMap<String, Integer> totalTermFreq = new HashMap<String, Integer>();
	public ArrayList<ArrayList<String>> docList = new ArrayList<ArrayList<String>>();
	public HashMap<String, Integer> termFreqInAllArticles = new HashMap<String, Integer>();
	// public List<Map.Entry<String, Integer>> featureList;
	public Set<String> feature = new HashSet<String>();
	public final static int VECTOR_SIZE = 2000;

	public HashMap<String, Double> idf = new HashMap<String, Double>();

	public HashMap<String, HashMap<String, Double>> groupAvgVector = new HashMap<String, HashMap<String, Double>>();

	public static String groupPool[] = { "bonds", "commodities", "stocks",
			"currencies", "options", };

	/*
	 * "earnings", "eco_policy", "ETFs", "international", , "IPOs", "legal",
	 * "mergers", , "pe_hf", "upgrades_and_downgrades", "venture" };
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		WordAnalyzer a = new WordAnalyzer("","","");
		// Initialize:
		// a.calculateIDF();
		// a.initialDocList(new File(TRAINING_TEXT_ROOT_PATH));
		// a.initialTotalTermFreqMap();
		// a.generateFeature();
		// a.saveFeatureToFile();
		// a.initialGroupAvgVector();
		// a.saveGroupVectorToFile();

		a.loadIDF();
		a.loadFeatureFromFile();
		a.loadGroupVectorFromFile();

		String s1 = "The conduct of UBS and its employees �corrupted the competitive process and harmed municipalities, and ultimately taxpayers, nationwide,� said Assistant Attorney General Christine A. Varney, who oversees the federal antitrust division. Today�s agreements with UBS ensure that restitution is paid to the victims of the anticompetitive conduct, that UBS pays penalties and disgorges its ill-gotten gains. UBS said in a statement that it was pleased to have resolved the matter. �The underlying transactions were entered into in a business that no longer exists at UBS, and involved employees who are not longer with the firm,� the statement said. The company also said that it had made provisions for the settlement in prior quarters and it therefore will have no effect on the firm�s future financial results or on any current business of UBS.UBS is the second big banking institution to settle accusations of bid-rigging in the municipal bond derivatives market. In December, Bank of America agreed to pay $137 million in restitution after voluntarily disclosing its anticompetitive conduct and agreeing to cooperate with authorities in further investigations. As part of the broader investigation of bid-rigging in the municipal securities market, the Justice Department has brought criminal charges against 18 former executives of various financial services companies. Nine of the 18 have pleaded guilty, including one former UBS employee. Three other UBS employees also have been charged with criminal activities related to the municipal market.";
		System.out.println(a.matchArticle(s1));

	}
}
