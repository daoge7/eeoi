package cn.ccsit.common.utils;

import java.util.Random;

public class RandomUtils {
    static final  char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
    
    
	
	public RandomUtils() {
	}
	
	/**
	 * 生成含字母和数字的随机码
	 * 
	 * @param length 随机码长度
	 * @return 随机码
	 */
	public static String randomCode(int length) {
		
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int b = random.nextInt(62);
			builder.append(codeSequence[b]);
		}
		return builder.toString();
	}
}
