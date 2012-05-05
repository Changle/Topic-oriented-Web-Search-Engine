package com.wanderluster.util;

import java.io.File;
import java.io.IOException;

public class DelTempDir {
	
	public static boolean delAllFile(File file) {
		boolean flag = false;
//		File file = new File(path);
		File[] tempList = file.listFiles();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			try {
				temp = new File(tempList[i].getCanonicalPath());
				if (temp.isFile())
					temp.delete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		if (file.isDirectory())
			file.delete();
		return true;
	}

}
