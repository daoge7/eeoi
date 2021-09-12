package cn.ccsit.common.enums;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ccsit.common.enums.language.LangType;

public class EnumUtils {

	private static Logger logger = LogManager.getLogger(EnumUtils.class);
	
	/**
     * 将枚举转成List集合
     * @param enumClass 枚举类
     * 
     */
	
    public static Map<Long, String> enumToMap(Class<?> enumClass,LangType langType){
		Map<Long, String> map = new LinkedHashMap<>();
		try {
			Object[] objects = enumClass.getEnumConstants();
			Method getValue = enumClass.getMethod("getValue");
			Method getName = enumClass.getMethod("getName");
			Method getNameEn = enumClass.getMethod("getNameEn");
			for (Object obj : objects) {
				Object value = getValue.invoke(obj);
				Object name = getName.invoke(obj);
				Object nameEn = getNameEn.invoke(obj);
				if (langType == LangType.zh_CN) {
					map.put(Long.valueOf(String.valueOf(value)), String.valueOf(name));
				} else {
					if (nameEn == null) {
						nameEn = name;
					}
					map.put(Long.valueOf(String.valueOf(value)), String.valueOf(nameEn));
				}
			}
			objects=null;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return map;
    }

    /**
     * 根据枚举value获取枚举对象
     * @param enumClass 枚举类
     * @param value value值
     */
    public static Object enumCode(Class<?> enumClass, Object value){
        try {
            Object[] objects = enumClass.getEnumConstants();
            Method getValue = enumClass.getMethod("getValue");
            for (Object obj : objects) {
                Object iValue = getValue.invoke(obj);
                if(iValue.equals(value)){
                	objects=null;
                    return obj;
                }
            }
        } catch (Exception ex) {
        	logger.error(ex.getMessage());
        }
        return null;
    }
}
