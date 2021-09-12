package cn.ccsit.eeoi.portal.vo;

public class CompanyQueryVo extends PageWrapper {

	public CompanyQueryVo() {
	}

	private String name;
	private String address;   //页面中写成了adress 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
