package cn.ccsit.eeoi.system.vo;

import cn.ccsit.common.vo.ResultVo;

public class ServiceResultVo extends ResultVo {

	public <T> ServiceResultVo(Boolean result,T data) { 
		super(result,data); 
	}
}
