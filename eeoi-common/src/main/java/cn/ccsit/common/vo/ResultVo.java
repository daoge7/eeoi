package cn.ccsit.common.vo;

public abstract  class ResultVo {

	public ResultVo() {
		
	}
	
	public <T> ResultVo(Boolean result,T data) {	
		this.result=result;
		this.data=data;
	}
	
	/**
	 * 返回boolean 结果 
	 */	
	private boolean result=true;
	
	/**
	 * 返回对象结果
	 */
	private Object data;
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	//public static <M> ResultVo getObject(Boolean result,M data) {
		//return new ResultVo(result,data);
	//}
}
