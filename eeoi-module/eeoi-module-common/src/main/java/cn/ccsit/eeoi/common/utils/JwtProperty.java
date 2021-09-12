package cn.ccsit.eeoi.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 根据属性文件jwt.properties读取配置参数
 * @author lfj
 *
 */

@PropertySource(value = {"classpath:jwt.properties"})
@ConfigurationProperties(prefix = "jwt")
@Component("jwtProperty")
public class JwtProperty {
	
	private static Logger logger = LogManager.getLogger(JwtProperty.class);
	
	public JwtProperty() {
		logger.info("JwtProperties initialize");
	}
	
	/**
	 * 签发者(jwt.issuer)
	 */
	private String issuer; 
	
	/**
	 * 主题(jwt.subject)
	 */
	private String subject;
	
	/**
	 * 将来失效时间，单位分钟 (jwt.future-expiration-time-pc)
	 */
	private Integer futureExpirationTimePc;
	
	/**
	 * 将来失效时间，单位分钟 (jwt.future-expiration-time-app)
	 */
	private Integer futureExpirationTimeApp;
	
	/**
	 * 过去多久令牌失效,单位分钟(jwt.last-expiration-time)
	 */
	private Integer lastExpirationTime;
	
	/**
	 * 允许的时钟偏差,单位分钟(jwt.clock-screw)
	 */
	private Integer clockSkew;
	
	public Integer getClockSkew() {
		return clockSkew;
	}
	public void setClockSkew(Integer clockSkew) {
		this.clockSkew = clockSkew;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public Integer getFutureExpirationTimePc() {
		return futureExpirationTimePc;
	}
	public void setFutureExpirationTimePc(int futureExpirationTimePc) {
		this.futureExpirationTimePc = futureExpirationTimePc;
	}
	public Integer getFutureExpirationTimeApp() {
		return futureExpirationTimeApp;
	}
	public void setFutureExpirationTimeApp(int futureExpirationTimeApp) {
		this.futureExpirationTimeApp = futureExpirationTimeApp;
	}
	public Integer getLastExpirationTime() {
		return lastExpirationTime;
	}
	public void setLastExpirationtime(Integer lastExpirationTime) {
		this.lastExpirationTime = lastExpirationTime;
	}
}
