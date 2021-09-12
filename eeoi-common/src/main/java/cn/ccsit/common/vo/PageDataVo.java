package cn.ccsit.common.vo;

import java.util.List;

public class PageDataVo<T> {
	private int total;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	private List<T> items;

}
