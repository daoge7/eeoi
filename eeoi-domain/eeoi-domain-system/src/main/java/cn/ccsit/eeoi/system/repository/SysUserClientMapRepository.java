package cn.ccsit.eeoi.system.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysUserClientMap;


@Repository("sysUserClientMapRepository")
public interface SysUserClientMapRepository extends PagingAndSortingRepository<SysUserClientMap,String> {
	
	/**
	 * 根据用户获取对应的船公司
	 * @param account
	 * @return
	 */
	@Query("select u from SysUserClientMap u where u.userId=:userId and u.isDelete=0")
	public List<SysUserClientMap> findSysUserCompanyByUserId(@Param("userId") String userId);
	
	@Query("select u from SysUserClientMap u where u.userId=:userId and u.gcid=:companyId and u.isDelete=0 order by u.id")
	public List<SysUserClientMap> findSysUserCompanyByUserIdAndRoleId(@Param("userId") String userId,@Param("companyId") String companyId);
	
	@Query("select u from SysUserClientMap u where u.gcid=:companyId and u.isDelete=0")
	public List<SysUserClientMap>  findSysUserCompanyByRoleId(@Param("companyId") String companyId);

	@Query("select u.gcid from SysUserClientMap u where u.userId=:userId and u.isDelete=0")
	public List<String> findGcidCompanyByUserId(@Param("userId") String userId);
}
