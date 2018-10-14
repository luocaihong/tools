package com.mogul.tools.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件工具类
 */
public class FileOperator {
	private final static Logger logger = LoggerFactory.getLogger(FileOperator.class);
	
	public static String path = "";
	public static int count = 0;
	
	public FileOperator(){
	}

	/**
	 * 读取桌面路径
	 */
	public static String getHomePath(){
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com=fsv.getHomeDirectory();
		return com.getPath();
	}

    /**
     * 文件上传
     * @param filePath 保存路径
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    public static void FileUpload(String filePath, HttpServletRequest request)throws ServletException, IOException{
        OutputStream out = null;
        InputStream ins = null;
        try {
            ins = request.getInputStream();
            out = new FileOutputStream(filePath);
            
            byte[] fileData = new byte[1024];
            int readCount = 0 ;
            count = 0;
            
            while((readCount=ins.read(fileData,0,1024)) != -1){
                    out.write(fileData,0,readCount);
                    count += readCount;
            }
            out.flush();
            logger.info("[FileUpload] - read file size:"+count + "=======" + request.getClass() + ":" + filePath);
        }catch(Exception ex){
        	logger.error("[FileUpload] - " + ex + "=======" + request.getClass());
        }finally {            
        	if(out!=null)
        		out.close();
        	if(ins!=null)
        		ins.close();
        }
    }

    /**
     * 文件下载
     * @param filePath 下载路径
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void FileDownload(String filePath, HttpServletResponse response)throws ServletException, IOException{
        if("".equals(filePath))
            return;
        OutputStream out = null;
        InputStream ins = null;
        try {
            String fileName = StringOperator.GetFileName(filePath);
            File file = new File(filePath);
            long fileLength = file.length();
            
            ins = new FileInputStream(filePath);
            out = response.getOutputStream();
            
            byte[] fileData = new byte[1024];
            int readCount = 0 ;
            count = 0;
            //response.setHeader("Content-Type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(fileName,"UTF-8"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            while((readCount=ins.read(fileData,0,1024)) != -1){
                    out.write(fileData,0,readCount);
                    count += readCount;
            }
            out.flush();
            response.flushBuffer();
            logger.info("[FileDownload] - write file size:"+count + "=======" + response.getClass() + ":" + filePath);
        }catch(SocketException e){
        	logger.error("[FileDownload] -" + e.getMessage());
        }catch(Exception ex){
        	logger.error("[FileDownload] -" + ex.getMessage()+ "=======" + response.getClass());
        }
        finally {    
        	if(out!=null)
        		out.close();
        	if(ins!=null)
        		ins.close();
        }
    }

    /**
     * 文件上传
     * @param filePath 保存路径
     * @param fileName 保存文件名
     * @param fileSize 上传的文件大小
     * @param inputStream
     * @return
     */
    public static boolean FileUpload(String filePath, String fileName, long fileSize, InputStream inputStream){
    	boolean flag = false;
    	InputStream in = null;
    	FileOutputStream bos = null;
    	try{
    		logger.info("[FileUploadEx] - filePath="+filePath+" fileName="+fileName);
    		
    		in = inputStream;
			File filed = new File(filePath);
			if (!filed.exists()) {
				filed.mkdirs();
			}
			byte[] buffer = new byte[4096];
			File outFile = new File(filePath + File.separator + fileName);
			bos = new FileOutputStream(outFile);
			int read;
			long yx = 0;
			
			while ((read = in.read(buffer)) != -1) {
				yx = yx + read;
				bos.write(buffer, 0, read);
			}
			bos.flush();
			logger.info("[FileUploadEx] - file size:{" + fileSize + "}, read:{" + yx + "}");
			flag = true;
			
    	}catch(SocketException e){
    		logger.error("[FileUploadEx] -" + e.getMessage());
    	}catch(Exception ex){
    		logger.error("[FileUploadEx] -" + ex.getMessage()+ "=======");
    	}finally{
    		try {
	    		if(in!=null){
					in.close();
	    		}
	    		if(bos!=null){
	    			bos.close();
	    		}
    		} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return flag;
    }
    
    
}
