package cn.ccsit.common.vo;


/**
 * 返回对象数据，result 永远为true，表示返回了预期的结果 
 */
public class ResultObjectVo<T> extends ResultVo {
	
	public ResultObjectVo(T data){
		super(true,data);
	}
	
}
