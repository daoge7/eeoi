package cn.ccsit.common.vo;

import java.util.List;

import org.springframework.data.domain.Page;

public class ResultPageVo extends ResultVo {

	/**
	 *  直接构造
	 * @param <T>   
	 * @param pageable   
	 */
	public <T> ResultPageVo(Page<T> pageable) {
		this.setResult(true);
		this.setData(new PageVo<T>(pageable.getTotalElements(), pageable.getContent()));
	}

	/**
	 * 直接构造
	 * @param <T>
	 * @param total long类型记录总行数
	 * @param content 记录内容
	 */
	public <T> ResultPageVo(long total,List<T> content) {
		this.setResult(true);
		this.setData(new PageVo<T>(total,content));
	}
	
	/**
	 * 直接构造
	 * @param <T>
	 * @param total  int类型记录总行数
	 * @param content  记录内容
	 */
	public <T> ResultPageVo(int total,List<T> content) {
		this.setResult(true);
		this.setData(new PageVo<T>(total,content));
	}
	
	class PageVo<T> {

		public PageVo(long total, List<T> items) {
			this.total = total;
			this.items = items;
		}
		private long total;

		public long getTotal() {
			return total;
		}
		private List<T> items;
		public List<T> getItems() {
			return items;
		}
	}
}
