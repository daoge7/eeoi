package cn.ccsit.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ccsit.common.exception.ExplicitException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtils {
	
	private static Logger logger = LogManager.getLogger(ZipUtils.class);
	
	public ZipUtils() {
				
	}
	
	/**
	 * 压缩指定目录下的文件到指定文件名
	 * @param srcFilePath  源文件目录
	 * @param pwd 密码
	 * @param zipFileName  压缩后的带目录的文件名
	 * @return
	 */
	public static String zip(String srcFilePath,String pwd,String zipFileName) {		
		try {			
			File srcFile=new File(srcFilePath);
			File file=new File(zipFileName) ;
			if (file.exists()){
				file.delete();
			}
			ZipFile zipFile = new ZipFile(zipFileName);
			ZipParameters parameters=new  ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			if (!StringUtils.isNullOrEmpty(pwd)){
				parameters.setEncryptFiles(true);
				parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
				parameters.setPassword(pwd);
			}
			zipFile.setFileNameCharset("UTF-8");
			File[] files = srcFile.listFiles();
			if (files.length < 1) {
				throw new ExplicitException("没有发现待压缩的文件");
			}
			zipFile.addFiles(new ArrayList<File>(Arrays.asList(files)), parameters);			
			Thread.sleep(1000);
			return zipFileName;
			
		}catch (ExplicitException ex) {
			throw ex;
		}catch (Exception ex) {
			logger.error("压缩失败:"+ex.getMessage());
			throw new ExplicitException("压缩失败");
		}		
	}
		
	/**
	 * 解压缩文件到指定目录下
	 * @param zipFileName 压缩文件
	 * @param pwd   密码 
	 * @param desPath  解压后的文件目录
	 * @return
	 */
	public static boolean unzip(String zipFileName,String pwd,String desPath) {			
		File desFilePath=new File(desPath);
		if (desFilePath.isDirectory()) {
			if (!desFilePath.exists()){
				desFilePath.mkdirs();
			}
		} else {
			logger.error("desPath无效");
			throw new ExplicitException("解压缩目录无效");		
		}		
		ZipParameters parameters=new  ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		if (!StringUtils.isNullOrEmpty(pwd)){
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(pwd);
		}
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			if (!zipFile.isValidZipFile()) {
				throw new ExplicitException("不是有效的压缩文件");
			}
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(pwd);
			}
			zipFile.extractAll(desPath);
			Thread.sleep(1000);
			return true;
			
		}catch (ExplicitException ex) {
			throw ex;
		}
		catch (Exception ex) {
			logger.error("解压缩失败:"+ex.getMessage());
			throw new ExplicitException("解压缩文件失败");
		}		
	
	}
    public static void main(String args[]) {
		
	}
}
