package com.wanderluster.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlUtil {

	public String extractTitle(String source) {
		Pattern p = Pattern.compile("<title>(.*?)</title>",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return m.group(1);
		} else
			return null;
	}

	public String extractContent(String source) {
		Pattern p = Pattern.compile("<content>(.*?)</content>",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return m.group(1);
		} else
			return null;
	}
}
