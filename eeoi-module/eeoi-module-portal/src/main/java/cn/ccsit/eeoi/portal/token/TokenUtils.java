package cn.ccsit.eeoi.portal.token;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;

import cn.ccsit.common.exception.ExplicitException;


public class TokenUtils {

	private String id="userId";
	public TokenUtils() {
		
	}
	
	public static  boolean verifyToken(HttpServletRequest request ) {
			String token=request.getHeader("Authorization");
			if (null==token) {
				throw new ExplicitException("Authorization Invalid");
			}
			
			///日期  +研究+ID+是否存在
			
			try {
				System.out.println(Hex.encodeHexString("abcd".getBytes("utf8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Hex xx=new Hex();
			//秘钥采用公共+八位随机数
			
			//XXX=8位随机数前四位+3DES-BASE64(ID-时间YYYYMMDDHHMMSS-8位随机数)+8位随机数后四位;
			
			return true ;
		
	}
	
	public static void main(String[] args) {
				
	}
}
