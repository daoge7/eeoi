package cn.ccsit.common.utils;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ccsit.common.exception.ExplicitException;


public class StringUtils {
	
	private static Logger logger = LogManager.getLogger(StringUtils.class);
	
	public static final String numberChar = "0123456789";
	
	/**
	 * 判断某个字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
	}

	/**
	 * 生成定长的随机数
	 * @param length 指定随机数长度
	 * @return
	 */
	public static String getRandomString(int length) {		 
		StringBuffer sb = new StringBuffer(); 
		Random random = new Random(); 
		for (int i = 0; i < length; i++) { 
			sb.append(numberChar.charAt(random.nextInt(numberChar.length()))); 
		} 
		return sb.toString(); 
	} 
	
	
	/**
	 * 从字符串中过滤掉非字母和数据
	 * @param da'ta
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String data) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		if (data == null)
			return "";
		String regEx = "[`~!@#$%^&*+|{};//<>/?~！@#￥%……（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(data);
		return m.replaceAll("").trim();
	}
	

	/**
	 * 简单的字符串格式化，性能较好。支持不多于10个占位符，从%1开始计算，数目可变。参数类型可以是字符串、Integer、Object，
	 * 甚至int等基本类型
	 * 、以及null，但只是简单的调用toString()，较复杂的情况用String.format()。每个参数可以在表达式出现多次。
	 * @param msgWithFormat
	 * @param autoQuote
	 * @param args
	 * @return
	 */
	public static StringBuilder formatMsg(CharSequence msgWithFormat, boolean autoQuote, Object... args) {
		int argsLen = args.length;
		boolean markFound = false;

		StringBuilder sb = new StringBuilder(msgWithFormat);

		if (argsLen > 0) {
			for (int i = 0; i < argsLen; i++) {
				String flag = "%" + (i + 1);
				int idx = sb.indexOf(flag);
				// 支持多次出现、替换的代码
				while (idx >= 0) {
					markFound = true;
					sb.replace(idx, idx + 2, toString(args[i], autoQuote));
					idx = sb.indexOf(flag);
				}
			}

			if (args[argsLen - 1] instanceof Throwable) {
				StringWriter sw = new StringWriter();
				((Throwable) args[argsLen - 1]).printStackTrace(new PrintWriter(sw));
				sb.append("\n").append(sw.toString());
			} else if (argsLen == 1 && !markFound) {
				sb.append(args[argsLen - 1].toString());
			}
		}
		return sb;
	}

	/**
	 * 将CLOB转成String
	 * 
	 * @param clob 非空字段
	 * @return 内容字串，如果出现错误，返回null
	 */
	public final static String clobToString(Object data) {
		if (data == null) {
			logger.error("执行方法clobToString,输入参数为空");
			
		}
		if (!(data instanceof Clob)) {
			logger.error("执行方法clobToString,输入参数非clob类型");
			throw new ExplicitException("输入参数非clob类型");	
		}
		Clob clob = (Clob) data;
		StringBuffer buffer = new StringBuffer(65535);// 64K
		Reader clobStream = null;// 创建一个输入流对象
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];// 每次获取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				buffer.append(b, 0, i);
			}
			return buffer.toString();
		} catch (Exception ex) {
			logger.error("执行方法clobToString失败,错误信息:"+ex.getMessage());
			throw new ExplicitException("Clob To String 错误");			
		} finally {
			try {
				if (clobStream != null) {
					clobStream.close();
				}
			} catch (Exception e) {

			}
			buffer = null;
		}
	}
	
	public static StringBuilder formatMsg(String msgWithFormat, Object... args) {
		return formatMsg(new StringBuilder(msgWithFormat), true, args);
	}

	public static String toString(Object obj, boolean autoQuote) {
		StringBuilder sb = new StringBuilder();
		if (obj == null) {
			sb.append("NULL");
		} else {
			if (obj instanceof Object[]) {
				for (int i = 0; i < ((Object[]) obj).length; i++) {
					sb.append(((Object[]) obj)[i]).append(", ");
				}
				if (sb.length() > 0) {
					sb.delete(sb.length() - 2, sb.length());
				}
			} else {
				sb.append(obj.toString());
			}
		}
		if (autoQuote && sb.length() > 0 && !((sb.charAt(0) == '[' && sb.charAt(sb.length() - 1) == ']') || (sb.charAt(0) == '{' && sb.charAt(sb.length() - 1) == '}'))) {
			sb.insert(0, "[").append("]");
		}
		return sb.toString();
	}

	/**
	 * 把字符串中的带‘与"转成\'与\"
	 * @param orgStr
	 * @return
	 */
	public static String convertQuot(String orgStr) {
		return orgStr.replace("'", "\\'").replace("\"", "\\\"");
	}

	/**
	 * 获取字符串SHA-256散列值
	 * @param inputStr
	 * @return  错误时返回null
	 */
	public static synchronized String encryptSha256(String data) {
		try {
			if (data==null) {
				return null;
			}
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte digest[] = md.digest(data.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception ex) {
			logger.error("执行方法encryptSha256 错误,"+ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 字符串 转unicode
	 * @param s
	 * @return
	 */
	public static String stringToUnicode(String s) {
		String unicode = "";
		char[] charAry = new char[s.length()];
		for (int i = 0; i < charAry.length; i++) {
			charAry[i] = (char) s.charAt(i);
			unicode += "\\u" + Integer.toString(charAry[i], 16);
		}
		return unicode;
	}

	/**
	 * unicode转字符串
	 * @param unicodeStr
	 * @return
	 */
	public static String unicodeToString(String unicodeStr) {
		StringBuffer sb = new StringBuffer();
		String str[] = unicodeStr.toUpperCase().split("\\\\U");
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(""))
				continue;
			char c = (char) Integer.parseInt(str[i].trim(), 16);
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 判断字符串是否为null
	 * @param data   字符串
	 * @return
	 */
	public static boolean isNull(String data) {
		return (data == null);
	}
	
	/**
	 * 判断字符串是否为null 或者去掉空格后为空串 
	 * @param data   字符串
	 * @return
	 */
	public static boolean isNullOrEmpty(String data) {
		return (data == null || data.trim().equals(""));
	}
	
	
	 /**
	  * 判断 字符串是否不为空
	  * @param str
	  * @return
	  */
	public static boolean isNotEmpty(String data) {
		return (data != null && !data.trim().equals(""));
	}
	
	public static String formatMoney(String money){
		if(StringUtils.isNullOrEmpty(money))return "0.00";
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,##0.00");
		return myformat.format(new Double(money));
	}
	public static String formatRate(String money){
		if(StringUtils.isNullOrEmpty(money))return "0";
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,##0.0000");
		return myformat.format(new Double(money));
	}
	
	
	 /**  
     * 按字节截取字符串  
     * @param orignal  
     *            原始字符串  
     * @param count  
     *            截取位数  
     * @return 截取后的字符串  
     * @throws UnsupportedEncodingException 
     * @throws UnsupportedEncodingException  
     *             使用了JAVA不支持的编码格式  
     */  
    public static String substring(String orignal, int count) throws UnsupportedEncodingException  {   
        // 原始字符不为null，也不是空字符串   
        if (orignal != null && !"".equals(orignal)) {   
            // 将原始字符串转换为GBK编码格式   
            orignal = new String(orignal.getBytes(), "utf-8");   
            // 要截取的字节数大于0，且小于原始字符串的字节数   
            if (count > 0 && count < orignal.getBytes("utf-8").length) {   
                StringBuffer buff = new StringBuffer();   
                char c;   
               for (int i = 0; i < count; i++) {   
                   c = orignal.charAt(i);   
                    buff.append(c); 
                    
                    if (StringUtils.isChineseChar(c)) {   
                        // 遇到中文汉字，截取字节总数减1   
                        count--;
                        count--;
                    }   
                }   
                return buff.toString();   
            }   
        }   
        return orignal;   
    }  
    
    
    /**  
     * 判断是否是一个中文汉字  
     *   
     * @param c  
     *            字符  
     * @return true表示是中文汉字，false表示是英文字母  
	 * @throws UnsupportedEncodingException 
     * @throws UnsupportedEncodingException  
     *             使用了JAVA不支持的编码格式  
     */  
    public static boolean isChineseChar(char c) throws UnsupportedEncodingException  {   
        // 如果字节数大于1，是汉字   
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了   
        return String.valueOf(c).getBytes("utf-8").length > 1;   
    }   
    
    
	
	public static boolean regular(String str) {
		boolean findIllegalChar = true;
		if (str.replaceAll("[a-z]*[A-Z]*[0-9]*[\u4e00-\u9fa5]*-*.*,*_*%*&*@*=*\\(*\\)*'*", "").trim().length() == 0) {
			findIllegalChar = false;
		}
		return findIllegalChar;
	}
	
	/**
	 * 返回32位Uuid
	 * @return
	 */
	public static String getUuid(){
		String uuid = UUID.randomUUID().toString(); //获取UUID并转化为String对象  
        uuid = uuid.replace("-", "");               //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可  
        return uuid;
	}
}
