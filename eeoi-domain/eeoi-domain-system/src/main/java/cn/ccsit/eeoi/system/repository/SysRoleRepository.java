package cn.ccsit.eeoi.system.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.entity.SysUser;


@Repository("sysRoleRepository")
public interface SysRoleRepository extends PagingAndSortingRepository<SysRole,String> {
	
	@Query("select c from SysRole c where c.isDelete=0")
	public List<SysRole> findSysRoles();
	
	
	@Query("select c from SysRole c where c.isDelete=0 and c.id=:id  ")
	public SysRole findSysRoleById(@Param("id") String id);
	
	
	
	@Query("select c.id from SysRole c where c.isDelete=0 and c.title=:title ")
	public List<String> findSysRoleByIdAndTitle(@Param("title") String title);

	SysRole findByNameAndIsDelete(String name,Integer isDelete);
	
}
