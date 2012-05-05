package com.wanderluster.util;

import org.junit.Ignore;
import org.junit.Test;

public class StringUtil {
	
	public static String getFileName(String strtmp,String flag){
		int beginIndex=strtmp.indexOf("pdftemp")+8;
		int endIndex =strtmp.indexOf(flag);	

		return strtmp.substring(beginIndex, endIndex);
		
	}
   @Test
   @Ignore
   public void test1(){
	   
	   String str=getFileName("HITS�㷨��Web�ھ��е�Ӧ����Ľ�.txt",".txt");
	  
	   String str1=gbkToAscii(str);
	   System.out.println(str1); 
	   System.out.println("------2------" +str1);
	   String str2=asciiToGbk(str1);
	   System.out.println(str2); 
   }
   
   
	/**
	 * 中文转ASCII码
	 * @param gbkStr
	 * @return
	 */
	public static String gbkToAscii(String gbkStr){


           
		  char[]chars=gbkStr.toCharArray(); 
         StringBuffer sb=new  StringBuffer();

         for(int i=0;i<chars.length;i++)
         {
//       	  System.out.println(" "+chars[i]+" "+(int)chars[i]);
       	   sb.append("%"+(int)chars[i]);
         }
        
		    return sb.toString();
		 }
   
	/**
	 * ASCII转GBK
	 * @param asciiStr
	 * @return
	 */
	public static String asciiToGbk(String asciiStr){


		  System.out.println("ASCII ���� \n----------1111------------");

		  String[]chars=asciiStr.split("%");
		  StringBuffer sb=new StringBuffer();
//		  System.out.println("ASCII ���� \n----------222------------");
		        for(int i=1;i<chars.length;i++){ 
		        	sb.append((char)Integer.parseInt(chars[i]));
//		            System.out.println((char)Integer.parseInt(chars[i]));
		        } 
		        return sb.toString();
		 }

}
