package cn.ccsit.eeoi.common.aop;

import cn.ccsit.eeoi.common.service.CommonService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: eeoi
 * @description: 对CrudRepository类进行AOP操作基础字段
 * @author: luhao
 * @create: 2020-04-27 11:20
 */

@Aspect
@Component

public class CrudRepositoryAspect {

	private static final String ID = "id";
	private static final String CREATOR = "creator";
	private static final String CREATETM = "createTm";
	private static final String OPUSER = "opuser";
	private static final String OPDATE = "opdate";
	private static final String isDelete = " isDelete";

	@Autowired
	CommonService commonService;

	@Pointcut("execution(* org.springframework.data.repository.CrudRepository.save*(..))")
	public void save() {
	}

	@Before(value = "save()")
	public void updateCommonProperties(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			Object argument = args[0];
			if (argument instanceof List<?>) {
				List<Object> arguments = castList(argument, Object.class);
				for (Object item : arguments) {
					setEntityData(item);
				}
				;

			} else {
				setEntityData(argument);
			}
		}
	}

	private void setEntityData(Object argument) {
		String username = "";
		BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
		if (beanWrapper.getPropertyValue(ID) != null) {
			if (beanWrapper.isWritableProperty(OPDATE)) {
				beanWrapper.setPropertyValue(OPDATE, new Date());
			}
			if (beanWrapper.isWritableProperty(OPUSER)) {
				try {
					//zxh 校正，每次保存都提取当前用户的全部信息，负荷过大，修改为仅保存当前用户的登录名。
//					username = commonService.getCurrentUser().getNameCn();
					username = commonService.getUserName();
				} catch (Exception e) {
					username = "";
				}
				beanWrapper.setPropertyValue(OPUSER, username);
			}

		} else {
			if (beanWrapper.isWritableProperty(CREATETM)) {
				beanWrapper.setPropertyValue(CREATETM, new Date());
			}
			if (beanWrapper.isWritableProperty(CREATOR)) {
				try {
					//zxh 校正，每次保存都提取当前用户的全部信息，负荷过大，修改为仅保存当前用户的登录名。
//					username = commonService.getCurrentUser().getNameCn();
					username = commonService.getUserName();
				} catch (Exception e) {
					username = "";
				}
				beanWrapper.setPropertyValue(CREATOR, username);
			}
		}
	}

	private static <T> List<T> castList(Object obj, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		if (obj instanceof List<?>) {
			for (Object o : (List<?>) obj) {
				result.add(clazz.cast(o));
			}
			return result;
		}
		return null;
	}
}
