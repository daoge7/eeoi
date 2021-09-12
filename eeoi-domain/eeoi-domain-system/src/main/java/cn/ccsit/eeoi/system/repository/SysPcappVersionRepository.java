package cn.ccsit.eeoi.system.repository;


import cn.ccsit.eeoi.system.entity.SysPcappVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("sysPcappVersion")
public interface SysPcappVersionRepository extends JpaRepository<SysPcappVersion, String>, JpaSpecificationExecutor<SysPcappVersion> {

    List<SysPcappVersion> findByNewVersionGreaterThanAndSysCodeAndRecStatusAndIsDelete(String preVersion, String sysCode, String recStatus,String isDelete);
}
