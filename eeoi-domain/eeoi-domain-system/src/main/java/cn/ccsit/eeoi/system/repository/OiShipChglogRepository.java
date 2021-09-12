package cn.ccsit.eeoi.system.repository;


import cn.ccsit.eeoi.system.entity.OiShipChglog;
import cn.ccsit.eeoi.system.entity.SysActionLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("oiShipChglogRepository")
public interface OiShipChglogRepository extends PagingAndSortingRepository<OiShipChglog,String> {
    /**
     * 根据改变类型和船舶id查询
     * @param shipId
     * @param isDelete
     * @return
     */
    List<OiShipChglog> findByShipIdAndIsDelete(String shipId,Integer isDelete);

    List<OiShipChglog> findByShipIdAndIsDeleteAndChgType(String shipId,Integer isDelete,Integer chaType);
}
