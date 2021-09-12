package cn.ccsit.common.security;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.utils.StringUtils;

/**
 * BASE64 BASE16 编码处理
 * @author lfj
 *
 */
public class BaseCodeUtils {

	private static Logger logger = LogManager.getLogger(BaseCodeUtils.class);
	
	private static final String base16 = "0123456789ABCDEF";
	private static String charset ="UTF-8";
	
	public BaseCodeUtils() {
		
	}
	
	/**
	 * base16编码
	 * @param data
	 * @return
	 */
	public static String encodeBase16(byte[] data) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		for (int i = 0; i < data.length; ++i) {
			short value = (short) (data[i] & 0xFF);
			byte high = (byte) (value >> 4);
			byte low = (byte) (value & 0xF);
			os.write(base16.charAt(high));
			os.write(base16.charAt(low));
		}
		return new String(os.toByteArray());
	}

	/**
	 * base16解码
	 * @param data
	 * @return
	 */
	public static byte[] decodeBase16(String data) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		byte[] raw = data.getBytes();
		for (int i = 0; i < raw.length; ++i)
			if (!(Character.isWhitespace((char) raw[i]))) {
				bs.write(raw[i]);
			}
		byte[] in = bs.toByteArray();
		if (in.length % 2 != 0) {
			throw new ExplicitException("解码base16错误");
		}

		bs.reset();
		DataOutputStream ds = new DataOutputStream(bs);

		for (int i = 0; i < in.length; i += 2) {
			byte high = (byte) base16.indexOf(Character.toUpperCase((char) in[i]));
			byte low = (byte) base16.indexOf(Character.toUpperCase((char) in[(i + 1)]));
			try {
				ds.writeByte((high << 4) + low);
			} catch (IOException e) {
				throw new ExplicitException("解码base16错误，写位错误");
			}
		}
		return bs.toByteArray();
	}
	  	  
	/**
	 * 将二进制文件转换为Base64位编码字符串;
	 * 
	 * @param fileName 物理文件
	 * @return
	 */
	public static String encodeFileBase64(String fileName) {

		logger.info("执行方法encodeFileBase64");
		if (StringUtils.isNullOrEmpty(fileName)) {
			logger.error("执行encodeFileBase64方法,参数fileName为空");
			throw new ExplicitException("文件名不应为空");
		}
		File f = new File(fileName);
		if (!f.exists()) {
			logger.error("执行encodeFileBase64方法,文件不存在:" + fileName);
			throw new ExplicitException("文件不存在");
		}

		FileInputStream fis = null;
		String outCode = "";
		try {
			fis = new FileInputStream(fileName);

			int bufferLength = 1024;
			final byte[] buffer = new byte[bufferLength];
			int read = fis.read(buffer);
			while (read > -1) {
				String tempCode = null;
				if (read == bufferLength) {
					tempCode = Base64.encodeBase64String(buffer);
				} else {
					tempCode = Base64.encodeBase64String(Arrays.copyOf(buffer, read));
				}
				outCode = outCode + tempCode;
				read = fis.read(buffer, 0, read);
			}
		} catch (Exception ex) {
			logger.error("执行encodeFileBase64方法错误，错误信息：" + ex.getMessage());
			throw new ExplicitException("获取文件base64编码错误");
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception ex) {
					logger.error("执行encodeFileBase64方法的fis.close()错误，错误信息：" + ex.getMessage());
				}
			}
		}
		return outCode;
	}
		
	/**
	 * 将字符串转换为Base64位编码字符串;
	 * 
	 * @param data 字符串
	 * @return
	 */
	public static String encodeBase64(String data) {
		if (StringUtils.isNullOrEmpty(data)) {
			throw new RuntimeException("输入数据为空");
		}
		try {
			return Base64.encodeBase64String(data.getBytes(charset));
		} catch (Exception ex) {
			logger.error("字符串转换为utf8字节数组错误 ,错误信息:" + ex.getMessage());
			throw new RuntimeException("普通字符串转换为utf8字节数组错误");
		}
	}
		
	/**
	 * 将Base64位字符串转换为普通字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String decodeBase64(String data) {
		if (StringUtils.isNullOrEmpty(data)) {
			throw new RuntimeException("输入数据为空");
		}
		try {
			return new String(Base64.decodeBase64(data), charset);
		} catch (Exception ex) {
			logger.error("Base64转换后的字符串转换为utf8错误 ,错误信息:" + ex.getMessage());
			throw new RuntimeException("Base64转换后的字符串转换为utf8错误");
		}
	}
}
