package cn.ccsit.common.vo.page;

public abstract class PageVo {

	private int concurrentPage;
	
	//每页大小
	private int pageSize;
	//当前页码
	private int currentPage;
	
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
	@Deprecated()
	public int getConcurrentPage() {
		return concurrentPage;
	}
	
	@Deprecated
	public void setConcurrentPage(int concurrentPage) {
		this.concurrentPage = concurrentPage;
	}
	
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
