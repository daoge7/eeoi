package cn.ccsit.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileUtils {

	public FileUtils() {}
	

	private static Logger logger = LogManager.getLogger(FileUtils.class);
	
	/**
	 * 将文件目录和文件名合并成带路径的文件名
	 * @param path 目录
	 * @param fileName  带扩展名的文件名
	 * @return
	 */
	public static String filePathComine(String path,String fileName) {
		return shortFilePathComine(path,null,null,fileName,null);
	}
	
	/**
	 *  将文件目录、文件名及扩展名合并成带路径的文件名
	 * @param path  目录
	 * @param shortFileName 不带扩展名的文件名
	 * @param extName  文件扩展名(doc、pdf 等)
	 * @return
	 */
	public static String shortFilePathComine(String path,String shortFileName,String extName) {
		return shortFilePathComine(path,null,null,shortFileName,extName);
	}
	
	/**
	 * 将文件目录和文件名合并成带路径的文件名
	 * @param path1  目录1
	 * @param path2  目录2
	 * @param fileName 带扩展名的文件名
	 * @return
	 */
	public static String filePathComine(String path1,String path2,String fileName) {
		
		return shortFilePathComine(path1,path2,null,fileName,null);
	}
	
	/**
	 * 将文件夹和文件名合并成带路径的文件名
	 * @param path1  目录1
	 * @param path2  目录2
	 * @param shortFileName 不带扩展名的文件名
	 * @param extName  文件扩展名(doc、pdf 等)
	 * @return
	 */
	public static String shortFilePathComine(String path1,String path2,String shorFileName,String extName) {
		return shortFilePathComine(path1,path2,null,shorFileName,extName);
	}
	
	/**
	 * 将文件目录和文件名合并成带路径的文件名
	 * @param path1  目录1
	 * @param path2  目录2
	 * @param path3  目录3
	 * @param fileName 带扩展名的文件名
	 * @return
	 */
	
	public static String filePathComine(String path1,String path2,String path3,String fileName) {
		return shortFilePathComine(path1,path2,path3,fileName,null);
	}
	
	/**
	 * 将文件夹和文件名合并成带路径的文件名
	 * @param path1  目录1
	 * @param path2  目录2
	 * @param path3  目录3
	 * @param shortFileName 不带扩展名的文件名
	 * @param extName 文件扩展名(doc、pdf 等)
	 * @return
	 */
	public static String shortFilePathComine(String path1,String path2,String path3,String shorFileName,String extName) {
		String path="";
		if (StringUtils.isNullOrEmpty(path1)) {
			logger.error("传入的文件路径错误:path1 is null");
			return "";
		}else
		{
			path=path1;		
		}
		if (!StringUtils.isNullOrEmpty(path2)) {	
			if (path2.startsWith(File.separator)){
				path=path.endsWith(File.separator)?path+path2.substring(1):path+path2;
			}else {
				path=path.endsWith(File.separator)?path+path2:path+File.separator+path2;
			}
		}
		
		if (!StringUtils.isNullOrEmpty(path3)) {
			if (path3.startsWith(File.separator)){
				path=path.endsWith(File.separator)?path+path3.substring(1):path+path3;
			}else {
				path=path.endsWith(File.separator)?path+path3:path+File.separator+path3;
			}					
		}				
		if (StringUtils.isNullOrEmpty(shorFileName)){
			logger.error("传入的文件名错误:fileName is null");
			return "";
		} else {
			if (StringUtils.isNullOrEmpty(extName)){
				if (shorFileName.startsWith(File.separator)){
					return path.endsWith(File.separator)?path+shorFileName.substring(1):path+shorFileName;
				}else {
					return path.endsWith(File.separator)?path+shorFileName:path+File.separator+shorFileName;
				}
			}else {
				if (shorFileName.startsWith(File.separator)){
					return path.endsWith(File.separator)?path+shorFileName.substring(1)+"."+extName:path+File.separator+shorFileName+"."+extName;	
				}else {
					return path.endsWith(File.separator)?path+shorFileName+"."+extName:path+File.separator+shorFileName+"."+extName;
				}
				
			}
			
		}
	}
	
	/**
	 * 两个目录合并
	 * @param path1 目录1
	 * @param path2 目录2
	 * @return
	 */
	public static String PathComine(String path1,String path2) {
		return PathComine(path1,path2,null);
	}
	
	/**
	 * 三个目录合并
	 * @param path1 目录1
	 * @param path2 目录2
	 * @param path2 目录3
	 * @return
	 */
	
	public static String PathComine(String path1,String path2,String path3) {
		String path="";
		if (StringUtils.isNullOrEmpty(path1)) {
			logger.error("传入的文件路径错误:path1 is null");
			return "";
		}else
		{
			path=path1;		
		}
		if (!StringUtils.isNullOrEmpty(path2)) {	
			if (path2.startsWith(File.separator)) {
				path=path.endsWith(File.separator)?path+path2.substring(1):path+path2;			
			}else{
				path=path.endsWith(File.separator)?path+path2:path+File.separator+path2;
			}
		}
		
		if (!StringUtils.isNullOrEmpty(path3)) {			
			if (path3.startsWith(File.separator)) {
				path=path.endsWith(File.separator)?path+path3.substring(1):path+path3;			
			}else{
				path=path.endsWith(File.separator)?path+path3:path+File.separator+path3;
			}
		}
		return path;
	}
	
	
	/**
	 * 根据全路径文件名提取带扩展名的文件名
	 * @param fileName
	 * @return
	 */
	public static String fileName(String fullFileName) {
		String fileName="";
		if (StringUtils.isNullOrEmpty(fullFileName)){
			logger.error("文件名错误:fullFileName is null");
			throw new RuntimeException("文件名为空");
		}
		
		try{
			File file =new File(fullFileName.trim());			
			fileName = file.getName();  		
		}catch(RuntimeException ex) {
			logger.error("提取文件名错误:"+ex.getMessage());
			throw new RuntimeException("提取文件名错误");
		}
		return fileName;
	}
	
	/**
	 * 根据文件名提取不带扩展名的文件名
	 * @param shortFileName
	 * @return
	 */
	public static String shortfileName(String fullFileName) {
		String fileName="";
		if (StringUtils.isNullOrEmpty(fullFileName)){
			logger.error("文件名错误:fullFileName is null");
			throw new RuntimeException("文件名为空");
		}	
		try{
			File file =new File(fullFileName.trim());			
			fileName = file.getName(); 
			int pos=fileName.indexOf(".");
			if (pos>0) {
				fileName=fileName.substring(0,pos);
			}
		}catch(RuntimeException ex) {
			logger.error("提取文件名错误:"+ex.getMessage());
			throw new RuntimeException("提取文件名错误");
		}
		return fileName;
	}
	
	/**
	 * 根据文件名提取扩展名
	 * @param fileName
	 * @return
	 */
	public static String fileExtName(String fileName) {
		String extName="";
		
		if (StringUtils.isNullOrEmpty(fileName)){
			logger.error("文件名错误:fileName is null");
			throw new RuntimeException("文件名为空");
		}				
		try{
			
			int pos=fileName.trim().lastIndexOf(".");
			if (pos>0) {
				extName=fileName.substring(pos+1);
			}
		}catch(RuntimeException ex) {
			logger.error("获取文件名错误:"+ex.getMessage());
			throw new RuntimeException("提取文件扩展名错误");			
		}
		return extName;
	}
	
	/**
	 * 根据带路径的文件名提取文件目录
	 * @param fullFileName
	 * @return
	 */
	public static String filePath(String fullFileName) {
		String filePath="";
		if (StringUtils.isNullOrEmpty(fullFileName)){
			logger.error("文件名错误:fullFileName is null");
			throw new RuntimeException("文件名为空");
		}	
		try{
			
			int pos =fullFileName.trim().lastIndexOf(File.separator);
			if (pos<0) {
				filePath=fullFileName.trim();
			}else {		
				filePath = fullFileName.trim().substring(0,pos); 
			}
			
		}catch(RuntimeException ex) {
			logger.error("获取文件名错误:"+ex.getMessage());
			throw new RuntimeException("提取文件路径错误");
		}
		return filePath;
	}
	
	/**
	 * 创建文件目录
	 * @param filePath 有效的文件目录
	 */
	public static void mkDir(String filePath){
		
		File file=new File(filePath);
		if (!file.exists()) {
			try {
				file.mkdirs();
			}catch (Exception ex) {
				logger.error("创建目录失败:"+ex.getMessage());
				throw new RuntimeException("创建目录失败");
			}
		}
	}
	
	/**
	 * 删除指定目录下的文件
	 * @param filePath 文件目录
	 */
	public static void deleteFiles(String filePath){
		
		File dir=new File(filePath);
		if (!dir.isDirectory()) {
			logger.error("输入的路径不是有效目录:"+filePath);
			throw new RuntimeException("删除目录下文件失败");
		}
		try {
			File[] files=dir.listFiles();
			for(File file:files) {
				file.delete();
			}
		}catch (Exception ex) {
			logger.error("删除目录下文件失败:"+ex.getMessage());
			throw new RuntimeException("删除目录下文件失败");
		}
	};

	public static void downloadExcel(List<Map<String, Object>> list, HttpServletResponse response) throws IOException {
		String tempPath =System.getProperty("java.io.tmpdir") + IdUtil.fastSimpleUUID() + ".xlsx";
		File file = new File(tempPath);
		BigExcelWriter writer= ExcelUtil.getBigWriter(file);
		// 一次性写出内容，使用默认样式，强制输出标题
		writer.write(list, true);
		//response为HttpServletResponse对象
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		response.setHeader("Content-Disposition","attachment;filename=file.xlsx");
		ServletOutputStream out=response.getOutputStream();
		// 终止后删除临时文件
		file.deleteOnExit();
		writer.flush(out, true);
		//此处记得关闭输出Servlet流
		IoUtil.close(out);
	}
	
	public static void main(String args[]) {
		
		//测试
	}
}
