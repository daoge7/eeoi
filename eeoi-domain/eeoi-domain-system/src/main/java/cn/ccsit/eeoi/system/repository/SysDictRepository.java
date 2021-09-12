package cn.ccsit.eeoi.system.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.system.entity.SysDict;


@Repository("sysDictRepository")
public interface SysDictRepository extends PagingAndSortingRepository<SysDict, String> {
//    @Query(value = "SELECT * from SYS_DICT WHERE DIC_TYPE = 'ropa' AND IS_DELETE = 0",nativeQuery = true)
    SysDict findByDicTypeAndIsDelete(String type,Integer isDelete);
}
