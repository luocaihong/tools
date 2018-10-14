package com.mogul.tools.common;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class StringOperator {
	/*
     * 获取文件名称 linux操作系统文件分隔符为/ windows为\
     */
    public static String GetFileName(String fullName){
    	String fileName = fullName.trim();
    	if(fullName.lastIndexOf("/") > 0){
    		fileName = fullName.substring(fullName.lastIndexOf("/") + 1, fullName.length());
    	}
    	else if(fullName.lastIndexOf("\\") > 0){
    		fileName = fullName.substring(fullName.lastIndexOf("\\") + 1, fullName.length());
    	}
    	return fileName;
    }
    
    /*
     * 用系统编码转换字符串
     */
    public static String GetEncodeString(String initStr) throws UnsupportedEncodingException{
    	String encodeStr = initStr;
    	String fileEncode = System.getProperty("file.encoding");
    	encodeStr = new String(initStr.getBytes("UTF-8"), fileEncode);
    	return encodeStr;
    }
    /**
     * 获取证书CN项
     * @param dn
     * @return
     */
    public static String getCN(String dn){
    	//CN = 041@712345678-9@yiqi@00000026,OU = Enterprises,OU = SGCC,O = CFCA TEST CA,C = CN
    	String cn = "";
    	try{
	    	String[] items = dn.split(",");
	    	for(String item : items){
	    		String[] details = item.split("=");
	    		if(details[0].trim().equals("CN")){
	    			cn = details[1].trim();
	    			break;
	    		}
	    	}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return cn;
    }
    
    /**
	 * 获取客户端IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 将@替换成+
	 * @param enValue
	 * @return
	 */
	public static String getUrlValue(String enValue){
		String value = "";
		value = enValue.replaceAll("@", "+");
		return value;
	}
}
