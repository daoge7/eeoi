package cn.ccsit.eeoi.system.vo;

import java.util.List;

import cn.ccsit.eeoi.system.entity.SysRole;
import lombok.Data;

@Data
public class SysRoleShowVo {
	private String id;
	private String title;
	private String remark;
	private int status;
	private List<String> menuIds;
		
	
	public SysRoleShowVo(){
		
	}
	public SysRoleShowVo(SysRole role){
		this.id=role.getId();
		this.title=role.getTitle();
		this.status=role.getStatus();
		this.remark=role.getRemark();
	}	
}
