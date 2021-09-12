package cn.ccsit.eeoi.system.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysActionLog;


@Repository("sysActionLogRepository")
public interface SysActionLogRepository extends PagingAndSortingRepository<SysActionLog,String> {

}
