package cn.ccsit.common.vo;


import cn.ccsit.common.vo.ResultVo;

/**
 * 返回对象数据，result 永远为true，表示返回了预期的结果 
 */
public class ResultErrorObjectVo<T> extends ResultVo {

	public ResultErrorObjectVo(T data){
		super(false,data);
	}
	
}
