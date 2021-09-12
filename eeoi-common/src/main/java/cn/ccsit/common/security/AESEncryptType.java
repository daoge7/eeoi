package cn.ccsit.common.security;

import cn.ccsit.common.enums.EnumCommon;

public enum AESEncryptType implements EnumCommon{

	ECB(1,"Electronic Codebook Book(电码本模式)"),
	CBC(2,"Cipher Block Chaining(密码分组链接模式)"),
	CTR(3,"Counter(计算器模式)"),
	CFB(4,"Cipher FeedBack(密码反馈模式)"),
	OFB(5,"Output FeedBack(输出反馈模式)"),
	UnKnown(-1,"未知类型");
	
	private int value;  
	private String name;
	private String nameEn;
	
	AESEncryptType(int value,String name){
		this.value=value;
		this.name=name;
	}
	AESEncryptType(int value,String name,String nameEn){
		this.value=value;
		this.name=name;
		this.nameEn=nameEn;
	}
	public static AESEncryptType setAESEncryptType(int value){
		AESEncryptType encryptType;
		switch (value) {
		case 1:
			encryptType = AESEncryptType.ECB;
			break;
		case 2:
			encryptType = AESEncryptType.CBC;
			break;
		case 3:
			encryptType = AESEncryptType.CTR;
			break;
		case 4:
			encryptType = AESEncryptType.CFB;
			break;
		case 5:
			encryptType = AESEncryptType.OFB;
			break;
		default:
			encryptType = AESEncryptType.UnKnown;
		}
		
		return encryptType;
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
