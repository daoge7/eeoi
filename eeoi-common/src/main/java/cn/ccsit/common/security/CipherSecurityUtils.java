package cn.ccsit.common.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.utils.StringUtils;

/**
 * 加密工具
 */
public class CipherSecurityUtils {

	private static Logger logger = LogManager.getLogger(CipherSecurityUtils.class);

	/**
	 * 加密类型,ECB模式不需要16位向量
	 */
	public static AESEncryptType encryptType = AESEncryptType.ECB;

	/**
	 * 字符填充类型
	 */
	public static AESPaddingType paddingType = AESPaddingType.PKCS5Padding;

	/**
	 * 字符集
	 */
	public static String charset = "UTF-8";

	/**
	 * 初始秘钥
	 */
	public static String privateKey = "CCSEasy711";

	/**
	 * 字符串加密,默认秘钥CCSeasy711,ECB 模式加密 
	 * 
	 * @param data  字符串  ECB模式加密
	 * @return 加密后的字符串，异常时抛出ExplicitException
	 */
	public static String encryptByAES(String data) {
		return encryptByAES(data,null);
	}

	/**
	 * 字符串加密,默认秘钥CCSeasy711,缺省算法AES/ECB/PKCS5Padding
	 * 
	 * @param data  字符串
	 * @param ivKey 16位向量字符串,非ECB模式加密需要设置此值
	 * @return 加密后的字符串，异常时抛出ExplicitException
	 */
	public static String encryptByAES(String data, String ivKey) {
		logger.info("执行方法encryptByAES");
		if (data == null) {
			logger.error("执行encryptByAES方法,参数data为空");
			throw new ExplicitException("输入参数不能为空");
		}
		if (privateKey == null) {
			logger.error("执行encryptByAES方法,秘钥为空");
			throw new ExplicitException("输入的秘钥不应为空");
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(privateKey.getBytes(charset));
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			String cipherType = "AES" + "/" + CipherSecurityUtils.encryptType.name() + "/"
					+ CipherSecurityUtils.paddingType.name();
			Cipher cipher = Cipher.getInstance(cipherType);
			if (ivKey != null) {
				IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes(charset));
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}
			byte[] encrypt = cipher.doFinal(data.getBytes(charset));

			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < encrypt.length; i++) {
				int v = encrypt[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					buffer.append(0);
				}
				buffer.append(hv);
			}
			cipher=null;
			random=null;
			kgen=null;
			return buffer.toString();
		} catch (Exception ex) {
			logger.error("加密字符串失败,错误信息:" + ex.getMessage());
			throw new ExplicitException("加密字符串失败");
		}
	}

	/**
	 * 文件加密,默认秘钥CCSeasy711 加密算法,AES/ECB/PKCS5Padding
	 * 
	 * @param fileName 待加密的文件名
	 * @param replace  是否替换原文件：true 是， false 否
	 * @return 加密成功后的文件名，非替换时，在原有文件名基础上增加扩展名 .pf,异常时抛出ExplicitException
	 */
	public static String encryptFileByAES(String fileName, boolean replace) {
		logger.info("执行方法encryptFileByAES");
		if (privateKey == null) {
			logger.error("执行encryptFileByAES方法,秘钥为空");
			throw new ExplicitException("输入秘钥不应为空");
		}
		if (StringUtils.isNullOrEmpty(fileName)) {
			logger.error("执行encryptFileByAES方法,参数fileName为空");
			throw new ExplicitException("文件名不应为空");
		}
		File f = new File(fileName);
		if (!f.exists()) {
			logger.error("执行encryptFileByAES方法,文件不存在:" + fileName);
			throw new ExplicitException("文件不存在：" + fileName);
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		String fileNameTemp = fileName + ".pf";
		File temp = new File(fileNameTemp);
		if (temp.exists()) {
			temp.delete();
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(privateKey.getBytes(charset));
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			fis = new FileInputStream(fileName);
			fos = new FileOutputStream(fileNameTemp);
			int bufferLength = 1024;
			final byte[] buffer = new byte[bufferLength];
			int read = fis.read(buffer);
			while (read > -1) {
				byte[] encrypt = null;
				if (read == bufferLength) {
					encrypt = cipher.doFinal(buffer);
				} else {
					encrypt = cipher.doFinal(Arrays.copyOf(buffer, read));
				}
				fos.write(encrypt);
				read = fis.read(buffer, 0, read);
			}
			fos.flush();
			cipher=null;
			random=null;
			kgen=null;
		} catch (Exception ex) {
			logger.error("加密字符串失败,错误信息:" + ex.getMessage());
			throw new ExplicitException("加密文件失败");
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					logger.error("fos.close 失败,错误信息:" + ex.getMessage());
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ex) {
					logger.error("fis.close 失败,错误信息:" + ex.getMessage());
				}
			}
		}
		if (replace) {
			logger.info("Files.Move:" + fileNameTemp + "," + fileName);
			try {
				Files.move(Paths.get(fileNameTemp), Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex) {
				logger.error("files.move 失败,错误信息:" + ex.getMessage());
				throw new ExplicitException("加密文件错误");
			}
		}
		return replace == true ? fileName : fileNameTemp;

	}

	/**
	 * 字符串解密,默认秘钥CCSeasy711,缺省算法为AES/ECB/PKCS5Padding
	 * 
	 * @param data  字符串
	 * @param ivKey 16位向量字符串，ECB模式不需要，其它模式则需要设置此值
	 * @return 解密后的字符串，异常时抛出ExplicitException
	 */
	public static String decryptByAES(String data) {
		return decryptByAES(data,null);
	}
	
	/**
	 * 字符串解密,默认秘钥CCSeasy711，缺省算法为AES/ECB/PKCS5Padding,如需修改算法则，通过全局静态变量进行设置
	 * 
	 * @param data  字符串
	 * @param ivKey 16位向量字符串，ECB模式不需要，其它模式则需要设置此值
	 * @return 解密后的字符串，异常时抛出ExplicitException
	 */
	public static String decryptByAES(String data, String ivKey) {
		logger.info("执行方法decryptByAES");
		if (data == null) {
			logger.error("执行decryptByAES方法,参数data为空");
			throw new ExplicitException("输入参数为空错误");
		}
		if (privateKey == null) {
			logger.error("执行decryptByAES方法,秘钥为空");
			throw new ExplicitException("秘钥为空错误");
		}
		try {

			int length = data.length() / 2;
			byte[] byteContent = new byte[length];
			for (int i = 0; i < length; i++) {
				int high = Integer.parseInt(data.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(data.substring(i * 2 + 1, i * 2 + 2), 16);
				byteContent[i] = (byte) (high * 16 + low);
			}
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(privateKey.getBytes(charset));
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			String cipherType = "AES" + "/" + CipherSecurityUtils.encryptType.name() + "/"
					+ CipherSecurityUtils.paddingType.name();
			Cipher cipher = Cipher.getInstance(cipherType);
			if (ivKey != null) {
				IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes(charset));
				cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			}
			byte[] decrypt = cipher.doFinal(byteContent);
			cipher=null;
			random=null;
			kgen=null;
			return new String(decrypt, charset);
		} catch (Exception ex) {
			logger.error("解密字符串失败,错误信息:" + ex.getMessage());
			throw new ExplicitException("解密字符串错误");
		}
	}

	/**
	 * 文件解密,默认秘钥CCSeasy711 加密算法,AES/ECB/PKCS5Padding
	 * 
	 * @param fileName 文件名
	 * @param replace  true替换原文件, false不替换原文件
	 * @return 解密成功后的文件名，非替换时，扩展名增加 .tp,异常时抛出ExplicitException 
	 */
	public static String decryptFileByAES(String fileName, boolean replace) {
		logger.info("执行方法dencryptFileByAES");
		if (privateKey == null) {
			logger.error("执行dencryptFileByAES方法,秘钥为空");
			throw new ExplicitException("输入的秘钥不应为空");
		}
		if ((fileName == null) || ("".equals(fileName))) {
			logger.error("执行dencryptFileByAES方法,参数fileName为空");
			throw new ExplicitException("输入的文件名不应为空");
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		String fileNameTemp = fileName + ".pt";
		File f = new File(fileName);
		if (!f.exists()) {
			logger.error("执行encryptFileAES方法,文件不存在:" + fileName);
			throw new ExplicitException("文件不存在");
		}
		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(privateKey.getBytes("utf8"));
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			fis = new FileInputStream(fileName);
			fos = new FileOutputStream(fileNameTemp);
			int bufferLength = 1040; // 1024+16 解密后 长度 变长
			final byte[] buffer = new byte[bufferLength];
			int read = fis.read(buffer, 0, bufferLength);
			while (read > -1) {
				byte[] dencrypt = null;
				if (read == bufferLength) {
					dencrypt = cipher.doFinal(buffer);
				} else {
					dencrypt = cipher.doFinal(Arrays.copyOf(buffer, read));
				}
				fos.write(dencrypt);
				read = fis.read(buffer, 0, bufferLength);
			}
		} catch (Exception ex) {
			logger.error("文件解密失败,错误信息:" + ex.getMessage());
			throw new ExplicitException("文件解密失败");
		} finally {
			if (fos != null) {
				try {
					fos.flush();
				} catch (IOException ex) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					logger.error("fos.close 失败,错误信息:" + ex.getMessage());
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ex) {
					logger.error("fis.close 失败,错误信息:" + ex.getMessage());
				}
			}
		}
		if (replace) {
			logger.info("Files.Move:" + fileNameTemp + "," + fileName);
			try {
				Files.move(Paths.get(fileNameTemp), Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex) {
				logger.error("files.move 失败,错误信息:" + ex.getMessage());
				throw new ExplicitException("解密文件错误");
			}
		}
		return replace == true ? fileName : fileNameTemp;
	}
}
