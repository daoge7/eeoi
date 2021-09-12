package cn.ccsit.common.config;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jose4j.jwt.JwtClaims;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;



public class MyLocaleResolver implements LocaleResolver{
	
	  private static final String I18N_LANGUAGE = "lang";  
	  private static final String I18N_LANGUAGE_SESSION = "locale";  
	  
	  @Override
      public Locale resolveLocale(HttpServletRequest request) {
		  String i18n_language = request.getParameter(I18N_LANGUAGE);
		  Locale locale = Locale.getDefault();
		  if(!StringUtils.isEmpty(i18n_language)) {  
	            String[] language = i18n_language.split("_");  
	            locale = new Locale(language[0], language[1]);  
	            //将国际化语言保存到session  
	            HttpSession session = request.getSession();  
	            session.setAttribute(I18N_LANGUAGE_SESSION, locale);  
	        }else {
	        	  HttpSession session = request.getSession();  
	              Locale localeInSession = (Locale) session.getAttribute(I18N_LANGUAGE_SESSION);  
	              if(localeInSession != null) {  
	                  locale = localeInSession;  
	              }  
	        }
          return locale;
//		  Locale locale = Locale.getDefault();
//		  Cookie[] cookies = request.getCookies();
//		  Cookie cookie = null;
//		  String jwtToken="";
//		  for (int j = 0; j < cookies.length; j++) {
//	            cookie = cookies[j];
//	            if (cookie.getName().equals("jwtToken")) {
//	            	jwtToken = cookie.getValue();
//	            }
//	        }
//		  if (jwtToken!= null && !StringUtils.isEmpty(jwtToken)){
//			try {
//				 JwtClaims jwtClaims = JWTUtil.decryptToken(jwtToken);
//				String languge=(String)jwtClaims.getClaimValue("lang");
//				locale = new Locale(I18N_LANGUAGE,languge);  
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			  
//		  }
//		  return locale;
      }

      @Override
      public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

      }

}
