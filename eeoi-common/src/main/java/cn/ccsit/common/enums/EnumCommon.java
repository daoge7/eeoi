package cn.ccsit.common.enums;

public interface EnumCommon {

	/**
	 * 获取枚举值
	 * @return
	 */
	public int getValue();
	
	/**
	 * 枚举值转换为字符串
	 * @return
	 */
	public String getValueOfString();
	
	/**
	 * 获取中文枚举名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 获取英枚举描述
	 * @return
	 */
	public String getNameEn();
	
}
