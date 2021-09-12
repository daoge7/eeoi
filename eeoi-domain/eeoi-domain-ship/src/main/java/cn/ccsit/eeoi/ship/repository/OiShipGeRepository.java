package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiIncinerator;
import cn.ccsit.eeoi.ship.entity.OiShipGe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("0iShipGeRepository")
public interface OiShipGeRepository extends PagingAndSortingRepository<OiShipGe, String> {
    /**
     * 根据船舶id逻辑删除设备
     *
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_GE SET IS_DELETE = 1,OPDATE=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipGeByShipId(String shipId);

    /**
     * 获取副机设备数量
     *
     * @param shipId
     * @return
     */
    @Query(value = "SELECT \"COUNT\"(1) from OI_SHIP_GE WHERE SHIP_ID = ?1 AND IS_DELETE = 0", nativeQuery = true)
    Integer findByShipId(String shipId);

    /**
     * 逻辑删除设备
     *
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_GE SET IS_DELETE = 1,OPDATE=sysdate where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipGeById(String id);

    List<OiShipGe> findByShipIdAndIsDelete(String shipId, Integer isDelete);

    OiShipGe findByCcsnoAndXhAndIsDelete(String ccsno, String xh, Integer isDelete);
    OiShipGe findByIdAndIsDelete(String id, Integer isDelete);
}
