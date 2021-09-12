package cn.ccsit.common.vo;

/**
 * 返回字符串型数据，result 永远为true，表示返回了预期的结果
 * @author lfj
 *
 */
public class ResultStringVo extends ResultVo {

	public ResultStringVo(String data) {
		super(true,data);
	}
}
