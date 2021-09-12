package cn.ccsit.eeoi.common.utils;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cn.ccsit.common.enums.EnumUtils;
import cn.ccsit.common.enums.language.LangType;
import cn.ccsit.common.utils.DateUtils;

@Component("jwtUtils")
@DependsOn("jwtProperty")
public class JwtUtils {
	
	private static Logger logger = LogManager.getLogger(JwtUtils.class);
	
	private static RsaJsonWebKey rsaJsonWebKey = getInstance();
	
	@Autowired 
	@Lazy
	private JwtProperty jwtProperty;
	
	/**
	 * 缺省keyId
	 */
	private String defaultKeyId="CCSeasy711";
	
	
	/**
	 * 重新签署 token
	 * @param jwtToken  原始token
 	 * @return
	 */
	public String reSign(String jwtToken){
		String userName=JwtUtils.getUserName(jwtToken);
		LangType langType=JwtUtils.getLangType(jwtToken);
		Date expTm = JwtUtils.getExpTime(jwtToken);
		Date issuedTm = JwtUtils.getIssuedAt(jwtToken);
		long diff = expTm.getTime()-issuedTm.getTime();
		return this.sign(userName,this.defaultKeyId,langType, (int)diff/1000/60);
	}
	
	/**
	 * 根据userName 签署 token
	 * @param userName  用戶名
	 * @param terminalType  0或空：PC端，1:手机app端
 	 * @return
	 */
	public String sign(String userName, String terminalType,LangType langType){
		Integer futureExpMinutes=0;
		if("app".equals(terminalType)) {
			futureExpMinutes=jwtProperty.getFutureExpirationTimeApp()==null?0:jwtProperty.getFutureExpirationTimeApp();
		}else {
			futureExpMinutes=jwtProperty.getFutureExpirationTimePc()==null?0:jwtProperty.getFutureExpirationTimePc();
		}
		return this.sign(userName,this.defaultKeyId,langType, futureExpMinutes);
	}
	
	/**
	 * 根据userName 签署 token
	 * @param userName  用戶名
	 * @param langType  国际化
 	 * @return
	 */
	public String sign(String userName,LangType langType, Integer futureExpMinutes){
		return this.sign(userName,this.defaultKeyId,langType,  futureExpMinutes);
	}
	
	/**
	 * 根据userName及keyId 签署 token
	 * @param userName  用戶名
	 * @param keyId   key
	 * @param langType  国际化
 	 * @return
	 */
	public String sign(String userName,String keyId,LangType langType, Integer futureExpMinutes){
		if (jwtProperty==null) {
			throw new RuntimeException("Getting jwt property error:jwtProperty is null");
		}
		if(futureExpMinutes==null || futureExpMinutes==0) {
			futureExpMinutes=30; //默认30分钟
		}
		if (logger.isTraceEnabled()) {
			logger.trace("issuer:"+jwtProperty.getIssuer());
			logger.trace("subject:"+jwtProperty.getSubject());
			logger.trace("futureExpirationTime:"+futureExpMinutes);
			logger.trace("lastExpirationTime:"+jwtProperty);
		}
		
		JwtClaims claims = new JwtClaims();
		claims.setJwtId(userName);
		claims.setIssuer(jwtProperty.getIssuer()); // 令牌德创建者
		//claims.setAudience(userName); // 令牌将被发送给谁
		claims.setExpirationTimeMinutesInTheFuture(futureExpMinutes); //令牌签发后多长时间后失效
		//claims.setGeneratedJwtId(); // 令牌的唯一标识符
		claims.setIssuedAtToNow(); // 令牌创建时间
		claims.setNotBeforeMinutesInThePast(jwtProperty.getLastExpirationTime()==null?0:jwtProperty.getLastExpirationTime()); //令牌签发后，过去多长时间内令牌有效
		claims.setSubject(jwtProperty.getSubject()); // 主题
		claims.setClaim("langType", langType.getValueOfString());//语言
		rsaJsonWebKey.setKeyId(keyId);  //设置公钥
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(rsaJsonWebKey.getPrivateKey());
		jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		String jwt = null;
		try {
			jwt = jws.getCompactSerialization();
		} catch (JoseException e) {
			logger.error(e.getMessage());
		}
		return jwt;
	}
	
	/**
	 * 验证token有效性
	 * @param token
	 * @param userName
	 * @return
	 * @throws InvalidJwtException 
	 * @throws MalformedClaimException 
	 */
	public  boolean verify(String token,String userName) throws InvalidJwtException, MalformedClaimException {
		return this.verify(token, userName, this.defaultKeyId);
	}
	
	/**
	 * 验证token有效性
	 * @param token
	 * @param userName
	 * @param keyId
	 * @return
	 * @throws InvalidJwtException 
	 * @throws MalformedClaimException 
	 */
	public boolean verify(String token,String userName,String keyId) throws InvalidJwtException, MalformedClaimException {

		if (jwtProperty==null) {
			throw new RuntimeException("error:jwt property is null");
		}
		if (logger.isTraceEnabled()) {
			logger.trace("issuer:"+jwtProperty.getIssuer());
			logger.trace("subject:"+jwtProperty.getSubject());			
			logger.trace("futureExpirationTimeApp:"+jwtProperty.getFutureExpirationTimeApp());
			logger.trace("futureExpirationTimePc:"+jwtProperty.getFutureExpirationTimePc());
			logger.trace("lastExpirationTime:"+jwtProperty);
		}
		rsaJsonWebKey.setKeyId(keyId); 
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() 
				.setAllowedClockSkewInSeconds(jwtProperty.getClockSkew()==null?30:jwtProperty.getClockSkew()) //  允许的时钟偏差,单位分钟  缺省30
				.setExpectedSubject(jwtProperty.getSubject())
				.setExpectedIssuer(jwtProperty.getIssuer())
				//.setExpectedAudience(userName)
				.setRequireJwtId()
				.setVerificationKey(rsaJsonWebKey.getKey())
				.setJwsAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.WHITELIST,AlgorithmIdentifiers.RSA_USING_SHA256))
				.build(); 
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);			
			if (logger.isTraceEnabled()) {
				logger.trace("JwtToken issued at:"+jwtClaims.getIssuedAt());
				logger.trace("JwtToken subject :"+jwtClaims.getSubject());
				logger.trace("JwtToken issuer :"+jwtClaims.getIssuer());
				logger.trace("JwtToken expiration time :"+jwtClaims.getExpirationTime());
			}
			logger.debug("token verify success");
			return true ;
	}
	
	/**
	 * 根据token 提取 用户名
	 * @param token
	 * @return
	 */
	public static LangType getLangType(String jwtToken) {		
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	            .setSkipAllValidators()
	            .setDisableRequireSignature()
	            .setSkipSignatureVerification()
	            .build();
		 try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
			
			String langValue= jwtClaims.getClaimValueAsString("langType");
			return LangType.setLangType(langValue);			
		} catch (InvalidJwtException ex) {
			logger.debug("token invalid:" + ex.getMessage());
			return LangType.zh_CN;
		} 
	}
	
	/**
	 * 根据token 提取 用户名
	 * @param token
	 * @return
	 */
	public static String getUserName(String jwtToken) {		
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	            .setSkipAllValidators()
	            .setDisableRequireSignature()
	            .setSkipSignatureVerification()
	            .build();
		 try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
			return jwtClaims.getJwtId();
			
		} catch (InvalidJwtException ex) {
			logger.info("token invalid:" + ex.getMessage());
			return null;
		} catch (MalformedClaimException ex) {
			logger.info("token invalid:" + ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据token 提取token失效时间
	 * @param token
	 * @return
	 */
	public static Date getExpTime(String jwtToken) {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			    .setSkipSignatureVerification()
				.setRequireIssuedAt()
	            .build();
		 try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
			return DateUtils.formatDate(jwtClaims.getExpirationTime().getValueInMillis());			
		} catch (Exception ex) {
			logger.info("token invalid or issuedAt invalid :" + ex.getMessage());
			return null;		
		}
	}
	/**
	 * 根据token 提取token签发时间
	 * @param token
	 * @return
	 */
	public static Date getIssuedAt(String jwtToken) {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			    .setSkipSignatureVerification()
				.setRequireIssuedAt()
	            .build();
		 try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
			return DateUtils.formatDate(jwtClaims.getIssuedAt().getValueInMillis());			
		} catch (Exception ex) {
			logger.info("token invalid or issuedAt invalid :" + ex.getMessage());
			return null;		
		}
	}
	/**
	 * 生成jsonwebkey
	 * @return
	 */
	private static RsaJsonWebKey getInstance() {
		logger.info("RsaJsonWebKey getInstance");
		if (rsaJsonWebKey == null) {
		try {
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
			rsaJsonWebKey.setKeyId("ccsitjwt");
			} catch (JoseException e) {
				logger.error("Generating Jwk error:" + e.getMessage());
			}
		}
		return rsaJsonWebKey;
	}
}
