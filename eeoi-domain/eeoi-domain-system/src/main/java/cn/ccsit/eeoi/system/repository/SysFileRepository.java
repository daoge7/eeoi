package cn.ccsit.eeoi.system.repository;


import cn.ccsit.eeoi.system.entity.SysDocNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysFile;

import java.util.List;


@Repository("sysFileRepository")
public interface SysFileRepository extends JpaRepository<SysFile, String>, JpaSpecificationExecutor<SysFile> {

    List<SysFile> findByFilePath(String filePath);

    SysFile findFirstByFilePath(String filePath);

    @Query(value = "SELECT * from SYS_FILE WHERE \"ID\" = ?1", nativeQuery = true)
    SysFile selectById(String fileId);


}
