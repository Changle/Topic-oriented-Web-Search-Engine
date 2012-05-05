package com.wanderluster.parsepdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.wanderluster.util.Constant;

public class XpdfParser {

	public XpdfParser() {
		File fileTxt = new File(Constant.PDFTxtdir);
		if (!fileTxt.exists())
			fileTxt.mkdir();
	}

	public String getPDFFileContent(String strtmp) throws InterruptedException {

		File file = new File(strtmp);

		String name = file.getName();

		String canonicalPath = xpdfParser(strtmp, name.substring(0, name
				.indexOf(".pdf")));

		Thread.sleep(150);
		String strRet = getTxtContents(canonicalPath);
		// System.out.println("-------20--------"+strRet);
		return strRet;

	}

	// @Test
	public String xpdfParser(String getSoure, String fileName) {

		XpdfParams xparam = new XpdfParams();
		
		xparam.setConvertor(Constant.ConvertorPATH);
		
		xparam.setEncoding("-enc UTF-8");

		xparam.setSource(getSoure);

		xparam.setTarget(Constant.PDFTxtdir + fileName + ".txt");

		String cmd = xparam.getCMD();
		try {
			// System.out.println("------2222-------" + cmd);
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

		return xparam.getTarget();
	}

	public String getTxtContents(String canonicalPath) {
		StringBuffer strBuffer = new StringBuffer();
		try {

			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(canonicalPath), "UTF-8"));
			line = reader.readLine();
			while (line != null) {
				if (line.length() > 0) {
					strBuffer.append(line.trim());
					// strBuffer.append("\n");
				}
				line = reader.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return strBuffer.toString();
	}

}
