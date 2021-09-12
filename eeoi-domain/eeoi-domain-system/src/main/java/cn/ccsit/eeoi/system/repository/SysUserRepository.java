package cn.ccsit.eeoi.system.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysUser;


@Repository("sysUserRepository")
public interface SysUserRepository extends PagingAndSortingRepository<SysUser,String> {
	/**
	 * 根据账户获取对应的用户
	 * @param account
	 * @return
	 */
	@Query("select u from SysUser u where  u.username=:username and u.isDelete=0")
	public SysUser findSysUserByUsername(@Param("username") String username);
	
	@Query("select u from SysUser u where  u.id=:id and u.isDelete=0")
	public SysUser findSysUserById(@Param("id") String id);
}
