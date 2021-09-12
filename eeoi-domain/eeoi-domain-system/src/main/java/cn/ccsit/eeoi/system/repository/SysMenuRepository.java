package cn.ccsit.eeoi.system.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysMenu;
import cn.ccsit.eeoi.system.entity.SysUser;


@Repository("sysMenuRepository")
public interface SysMenuRepository extends PagingAndSortingRepository<SysMenu,String> {

	
	@Query("select u from SysMenu u where u.isDelete=0")
	public List<SysMenu> findAllSysMenu();
}
