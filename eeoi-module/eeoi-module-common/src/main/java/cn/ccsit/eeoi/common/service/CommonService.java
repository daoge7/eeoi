package cn.ccsit.eeoi.common.service;

import cn.ccsit.eeoi.common.vo.CurrentUserVo;

import java.util.Locale;

//import cn.org.ccs.stems.common.util.OperateType;
//import cn.org.ccs.stems.filemanage.entity.UserOperateLog;

public interface CommonService {
	
	/**
	 * 业务模块调用时使用，记录操作日志
	 * @param sysFunc
	 * @return
	 */	
	
	///public UserOperateLog saveUserOperateLog(String sysFunc);
	
	
	/**
	 * 业务模块调用时使用，记录操作日志
	 * @param sysFunc
	 * @param operateType
	 * @param remark
	 * @param bizTable
	 * @param bizId
	 * @return
	 */
	//public UserOperateLog saveUserOperateLog(String sysFunc,OperateType operateType,String remark,String bizTable,String bizId);
	
	
	
/**
	 * 获取当前登录人的用户名
	 * 
	 * 
	 * @return 返回有效值
	 */
	public String getUserName();

	/**
	 * 获取当前登录人的用户id
	 * 
	 * @return 返回有效值
	 */
	public String getUserId();



	/**
	 * 获取当前登录人的信息
	 * 
	 * @return 返回有效值
	 */
	public CurrentUserVo getCurrentUser();
	
	
	
	/***
	 * 生成36位UUID
	 * @return
	 */
	public String UUID36();
	/***
	 * 生成32位UUID
	 * @return
	 */
	public String UUID32();
	
	/**
	 * 根据语言及传入参数返回具体语言环境的值
	 * @param nameCn  中文
	 * @param nameEn  英文
	 * @return
	 */
	public String getLocaleName(String nameCn, String nameEn);

	
	/**
	 * 获取国际化信息
	 * 
	 * @param key 键值
	 * @return 返回有效值
	 */
	public String msg(String key);

	/**
	 * 获取国际化信息
	 * 
	 * @param key  键值
	 * @param args 参数
	 * @return
	 */
	public String msg(String key, Object[] args);
}
