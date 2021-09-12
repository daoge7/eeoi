package cn.ccsit.common.enums.language;

import cn.ccsit.common.enums.EnumCommon;
import cn.ccsit.common.security.AESEncryptType;
import cn.ccsit.common.security.AESPaddingType;

public enum LangType implements EnumCommon{
	zh_CN(1,"中文"),
	en_US(2,"English");
	
	private int value;  
	private String name;
	private String nameEn;
	
	LangType(int value,String name){
		this.value=value;
		this.name=name;
	}
	
	LangType(int value,String name,String nameEn){
		this.value=value;
		this.name=name;
		this.nameEn=nameEn;
	}
	
	public static LangType setLangType(int value){
		LangType langType = null;
		switch (value) {
		case 1:
			langType = LangType.zh_CN;
			break;
		case 2:
			langType = LangType.en_US;
			break;	
		default:
			langType = LangType.zh_CN;
		}		
		return langType;
	}

	public static LangType setLangType(String value){
		int code=Integer.parseInt(value);
		return setLangType(code);
	}
	
	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public String getValueOfString() {
		return String.valueOf(value) ;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNameEn() {
		return nameEn;
	}
	
}
