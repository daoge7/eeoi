package cn.ccsit.eeoi.portal.vo;

public class PageWrapper {

	public PageWrapper() {

	}

	private int page ;
	private int limit ;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
