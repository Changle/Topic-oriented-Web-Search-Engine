package com.wanderluster.parsehtml;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

public class HtmlParser {

	public String ParseHtmlText(String url, String pageEncoding)
			throws ParserException {

		Parser parser = new Parser(url);

		parser.setEncoding(pageEncoding);

		String contents = "";

		TextExtractingVisitor visitor = new TextExtractingVisitor();

		NodeFilter textFilter = new NodeClassFilter(TextNode.class);

		NodeList nodes = parser.extractAllNodesThatMatch(textFilter);

		for (int i = 0; i < nodes.size(); i++) {

			TextNode textnode = (TextNode) nodes.elementAt(i);

			String line = textnode.toPlainTextString().trim();

			if ("".equals(line))
				continue;
			else
				contents = contents + " " + line;

		}
		parser.visitAllNodesWith(visitor);

		return contents;

	}

	public String GetTitle(String url, String pageEncoding)
			throws ParserException {

		Parser parser = new Parser(url);

		parser.setEncoding(pageEncoding);

		NodeList nodeList = parser.parse(new NodeClassFilter(TitleTag.class));

		String title = "";

		if (nodeList != null && nodeList.size() > 0) {
			for (int i = 0; i < nodeList.size(); i++) {
				TitleTag titleTag = (TitleTag) nodeList.elementAt(i);
				title = title + titleTag.getTitle();
			}
		}

		return title;

	}

}
