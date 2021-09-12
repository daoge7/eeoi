package cn.ccsit.common.vo;

/**
 * 返回字符串型错误消息，永远为false，表示返回了错误提示
 * @author lfj
 *
 */
public class ResultErrorVo extends ResultVo {
	
	public ResultErrorVo(String error) { 
		super(false,error); 
	}
}
