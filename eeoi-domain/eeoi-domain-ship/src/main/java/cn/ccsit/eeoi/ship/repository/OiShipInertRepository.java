package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiIncinerator;
import cn.ccsit.eeoi.ship.entity.OiShipInert;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("oiShipInertRepository")
public interface OiShipInertRepository extends PagingAndSortingRepository<OiShipInert,String> {
    /**
     * 根据船舶id逻辑删除设备
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_INERT SET IS_DELETE = 1,OPDATE=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipInertByShipId(String shipId);
    /**
     * id逻辑删除设备
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_INERT SET IS_DELETE = 1,OPDATE=sysdate where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipInertById(String id);

    List<OiShipInert> findByShipIdAndIsDelete(String shipId,Integer isDelete);
    OiShipInert findByIdAndIsDelete(String id, Integer isDelete);
}
