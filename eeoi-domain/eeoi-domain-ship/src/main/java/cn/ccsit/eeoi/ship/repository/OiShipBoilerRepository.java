package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiIncinerator;
import cn.ccsit.eeoi.ship.entity.OiShipBoiler;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("oiShipBoilerRepository")
public interface OiShipBoilerRepository extends PagingAndSortingRepository<OiShipBoiler, String> {
    /**
     * 根据船舶id逻辑删除设备
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_BOILER SET IS_DELETE = 1,UPDATE_TIME=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipBoilerByShipId(String shipId);
    /**
     * 根据船舶id逻辑删除设备
     * @param oiShipBoilerId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_BOILER SET IS_DELETE = 1,UPDATE_TIME=sysdate where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipBoilerById(String oiShipBoilerId);

    List<OiShipBoiler> findByShipIdAndIsDelete(String shipId,Integer isDelete);
    OiShipBoiler findByIdAndIsDelete(String id, Integer isDelete);
}
