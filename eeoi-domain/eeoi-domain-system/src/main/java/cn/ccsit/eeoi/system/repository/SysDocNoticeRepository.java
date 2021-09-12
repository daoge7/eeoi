package cn.ccsit.eeoi.system.repository;


import cn.ccsit.eeoi.system.entity.SysDocNotice;
import cn.ccsit.eeoi.system.entity.SysFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("sysDocNotice")
public interface SysDocNoticeRepository extends JpaRepository<SysDocNotice,String>, JpaSpecificationExecutor<SysDocNotice> {

    public List<SysDocNotice> findTop3ByDocTypeAndIsDeleteOrderByCreateTmDesc(String docType,String isDelete);
    @Query(value = "SELECT * from SYS_DOC_NOTICE WHERE DOC_TYPE=?1 AND IS_DELETE = 0",nativeQuery = true)
    List<SysDocNotice> findByDocType(Integer docType);

    List<SysDocNotice> findAllByIsDelete(String isDeleted);

}
