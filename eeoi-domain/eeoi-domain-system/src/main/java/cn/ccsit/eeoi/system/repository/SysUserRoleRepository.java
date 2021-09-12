package cn.ccsit.eeoi.system.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysUserRole;


@Repository("sysUserRoleRepository")
public interface SysUserRoleRepository extends PagingAndSortingRepository<SysUserRole,String> {
	/**
	 * 根据用户获取对应的角色
	 * @param account
	 * @return
	 */
	@Query("select u from SysUserRole u where u.userId=:userId and u.isDelete=0")
	public List<SysUserRole> findSysUserRoleByUserId(@Param("userId") String userId);
	
	@Query("select u from SysUserRole u where u.userId=:userId and u.roleId=:roleId and u.isDelete=0")
	public SysUserRole findSysUserRoleByUserIdAndRoleId(@Param("userId") String userId,@Param("roleId") String roleId);
	
	@Query("select u from SysUserRole u where u.roleId=:roleId and u.isDelete=0")
	public List<SysUserRole>  findSysUserRoleByRoleId(@Param("roleId") String roleId);
	@Query("select u.userId from SysUserRole u where u.roleId=:roleId and u.isDelete=0")
	public List<String>  findSysUserIdsByRoleId(@Param("roleId") String roleId);
}
