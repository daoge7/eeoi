package cn.ccsit.common.security;

import cn.ccsit.common.enums.EnumCommon;

public enum AESPaddingType implements EnumCommon{

	PKCS5Padding(1,"PKCS5"),
	NoPadding(2,"NoPadding"),
	ISO10126Padding(3,"ISO10126Padding"),
	UnKnown(-1,"未知类型");
	
	private int value;  
	private String name;
	private String nameEn;
	
	AESPaddingType(int value,String name){
		this.value=value;
		this.name=name;
	}
	AESPaddingType(int value,String name,String nameEn){
		this.value=value;
		this.name=name;
		this.nameEn=nameEn;
	}
	public static AESPaddingType setAESPaddingType(int value){
		AESPaddingType paddingType;
		switch (value) {
		case 1:
			paddingType = AESPaddingType.PKCS5Padding;
			break;
		case 2:
			paddingType = AESPaddingType.NoPadding;
			break;		
		default:
			paddingType = AESPaddingType.UnKnown;
		}		
		return paddingType;
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
