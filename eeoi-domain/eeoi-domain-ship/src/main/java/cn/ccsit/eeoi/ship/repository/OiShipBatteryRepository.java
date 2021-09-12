package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiShipBattery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("0iShipBatteryRepository")
public interface OiShipBatteryRepository extends PagingAndSortingRepository<OiShipBattery, String>{
    /**
     * 根据船舶id逻辑删除设备
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_BATTERY SET IS_DELETE = 1 where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiMainEngineByShipId(String shipId);

    OiShipBattery findByIdAndIsDelete(String id,Integer isDelete);

    List<OiShipBattery> findByShipIdAndIsDelete(String shipId,Integer isDelete);
}
