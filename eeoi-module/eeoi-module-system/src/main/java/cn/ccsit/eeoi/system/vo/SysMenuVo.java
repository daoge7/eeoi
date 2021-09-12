package cn.ccsit.eeoi.system.vo;

import java.util.List;

public class SysMenuVo {

	public String id;
	
	public String title;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSeqNo() {
		return SeqNo;
	}

	public void setSeqNo(Integer seqNo) {
		SeqNo = seqNo;
	}

	public List<SysMenuVo> getButtons() {
		return buttons;
	}

	public void setButtons(List<SysMenuVo> buttons) {
		this.buttons = buttons;
	}

	public String titleEn;
	
	public String url;
	
	public String pid;
	
	public String icon;
	
	public Integer SeqNo;
	
	public List<SysMenuVo> buttons;
}
