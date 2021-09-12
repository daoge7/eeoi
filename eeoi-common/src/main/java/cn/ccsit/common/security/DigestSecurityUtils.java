package cn.ccsit.common.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.ccsit.common.exception.ExplicitException;


/**
 * 加密工具
 */
public class DigestSecurityUtils {

	private static Logger logger = LogManager.getLogger(DigestSecurityUtils.class);

	/**
	 * 签名算法SHA-256 等
	 */
	private static final String HASH_ALGORITHM_NAME = "SHA-256";

	/**
	 ** Hash次数
	 */
	private static final int HASH_ITERATIONS = 8;

	/**
	 ** 字符集
	 */
	private static final String charset = "UTF-8";

	/**
	 ** 签名处理
	 * @param data  数据
	 * @param salt  盐
	 * @return  摘要
	 */
	public static String sha256Digest(String data, String salt) {
		return digest(data, salt, HASH_ALGORITHM_NAME, HASH_ITERATIONS);
	}

	/**
	 * 加密处理
	 * 
	 * @param data              密码
	 * @param salt              密码盐
	 * @param hashAlgorithmName 加密算法
	 * @param hashIterations    加密循环次数
	 */
	public static String digest(String data, String salt, String hashAlgorithmName, int hashIterations) {
		byte[] byteSalt = null;
		byte[] byteData = null;
		try {
			byteSalt = salt.getBytes(charset);
			byteData = data.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("提取字节码错误："+e.getMessage());
			throw new ExplicitException("提取字节码错误");
		}
		MessageDigest digest = DigestUtils.getDigest(hashAlgorithmName);
		try {
			digest.update(byteSalt);
			byte[] hashed = digest.digest(byteData);
			for (int i = 0; i < hashIterations - 1; ++i) {
				digest.reset();
				hashed = digest.digest(hashed);
			}
			digest=null;
			return Hex.encodeHexString(hashed);
		} catch (Exception e) {	
			logger.error("编码数据错误："+e.getMessage());
			throw new ExplicitException("获取摘要错误");
		}
	}
	
	/**
     * Salt  生成盐
     * @param length  盐字符串长度
     * @return 盐
     */
	public static String getSalt(int length) {

		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
				'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' ,'@','#','￥','%','&','*'};
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int b = random.nextInt(68);
			builder.append(codeSequence[b]);
		}
		String salt=builder.toString();
		if (logger.isTraceEnabled()){
			logger.trace("Generatting salt:"+salt);
		}
		return salt;
	}
	
	/**
	 * 根据输入字符串计算32位MD5码
	 * 
	 * @param data 字符串
	 * @return CommonResult.result为false时,msg返回错误描述;CommonResult.result为true时,msg返回md5码
	 */
	public static String  md5Digest(String data) {
		
		if (data == null) {
			logger.error("执行方法md5Digest,输入参数为空");
			throw new ExplicitException("方法错误：输入参数为空");
		}	
		return DigestUtils.md5Hex(data);
	}

	/**
	 * 根据输入字符串计算32位MD5码
	 * 
	 * @param fileName 文件名
	 * @return CommonResult.result为false时,msg返回错误描述;CommonResult.result为true时,msg返回md5码
	 */
	public static String md5DigestFile(String fileName) {
		logger.info("执行方法md5DigestFile");
		FileInputStream fis = null;
		if ((fileName == null) || ("".equals(fileName))) {
			logger.error("执行md5DigestFile方法,参数fileName为空");
			throw new ExplicitException("输入参数为空");
		}
		File f = new File(fileName);
		try {
			if (!f.exists()) {
				logger.error("执行md5DigestFile方法,文件不存在:" + fileName);
				throw new Exception("file not found");
			} else {
				fis = new FileInputStream(fileName);
				String md5Digest = DigestUtils.md5Hex(fis);
				if (md5Digest == null) {
					logger.error("执行DigestUtils.md5Hex返回空");
				}
				return md5Digest;
			}
		} catch (Exception ex) {
			logger.error("获取文件MD5摘要错误：" + fileName + ",错误信息：" + ex.getMessage());
			throw new ExplicitException("获取文件摘要错误");
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ex) {
					logger.error("fis.close 失败，错误信息:" + ex.getMessage());
				}
			}
			f=null;
		}
	}
}
