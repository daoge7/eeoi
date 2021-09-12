package cn.ccsit.eeoi.portal.vo;


public class PermissionPageVo {

	public String getTitleEn() {
		return titleEn;
	}





	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	private String menuId;
	private String pid;
	private String url;
	private int menuType;
	private String title;
	private String icon;
	private String perms;
	private String titleEn;
	
	private int sort;
	
	public PermissionPageVo(String menuId,String pid,String url,String title,String icon,String perms,int sort,int menuType,String titleEn) {
		this.menuId=menuId;
		this.pid=pid;
		this.url=url;
		this.title=title;
		this.titleEn=titleEn;
		this.menuType=menuType;
		this.icon=icon;
		this.perms=perms;
		this.sort=sort;
	}
	
	
	
	
	
	public int getMenuType() {
		return menuType;
	}





	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}





	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getPerms() {
		return perms;
	}


	public void setPerms(String perms) {
		this.perms = perms;
	}


	public int getSort() {
		return sort;
	}


	public void setSort(int sort) {
		this.sort = sort;
	}

	public PermissionPageVo() {
		
	}
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

	

}
