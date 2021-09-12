package cn.ccsit.eeoi.system.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysRoleMenu;
import cn.ccsit.eeoi.system.entity.SysUserClientMap;


@Repository("sysRoleMenuRepository")
public interface SysRoleMenuRepository extends PagingAndSortingRepository<SysRoleMenu,String> {

	
	/**
	 * 根据用户获取对应的船公司
	 * @param account
	 * @return
	 */
	@Query("select u from SysRoleMenu u where u.roleId=:roleId and u.isDelete=0")
	public List<SysRoleMenu> findSysRoleMenuByUserId(@Param("roleId") String roleId);
}
