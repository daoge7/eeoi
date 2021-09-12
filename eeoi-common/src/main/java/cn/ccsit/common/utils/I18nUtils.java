package cn.ccsit.common.utils;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 国际化
 */
@Component
public class I18nUtils {

    //private static final Logger LOGGER = LoggerFactory.getLogger(I18nService.class);


    /**
     * 获取消息
     * 也可以使用下面的方式：
     * Resource private MessageSource messageSource;
     * return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
     *
     * @param code k
     * @return v
     */
    public static String getMessage(String code) {
        try {
            return SpringContextUtils.getApplicationContext().getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
			 //LOGGER.error("获取国际化资源{}失败" + e.getMessage(), code, e); throw
			 //LogicException.le(ErrorMessage.I18N_RESOURCE_NOT_FOUND, new String[]{code});
        	throw e;
        }
    }
}