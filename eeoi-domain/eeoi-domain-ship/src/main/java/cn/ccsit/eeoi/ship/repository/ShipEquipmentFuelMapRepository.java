package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import cn.ccsit.eeoi.ship.entity.ShipEquipmentFuelMap;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Repository("shipEquipmentFuelMapRepository")
public interface ShipEquipmentFuelMapRepository extends PagingAndSortingRepository<ShipEquipmentFuelMap, String>, JpaSpecificationExecutor<ShipEquipmentFuelMap> {
    @Modifying
    @Query(value = "DELETE FROM SHIP_EQUIPMENT_FUEL_MAP WHERE SHIP_ID = ?1 AND EQUIPMENT_ID IS NULL ", nativeQuery = true)
    @Transactional
    void deleteByShipIdAAndEquipmentIdIsNull(String shipId);

    @Modifying
    @Query(value = "DELETE FROM SHIP_EQUIPMENT_FUEL_MAP WHERE EQUIPMENT_ID = ?1", nativeQuery = true)
    @Transactional
    void deleteByEquipmentId(String equipmentId);

    @Query(value = "SELECT DISTINCT(FUEL_ID) from SHIP_EQUIPMENT_FUEL_MAP WHERE SHIP_ID = ?1 AND IS_DELETE=0", nativeQuery = true)
    List<String> findByShipIdAndIsDelete(String shipId);

    @Query(value = "SELECT * from SHIP_EQUIPMENT_FUEL_MAP WHERE SHIP_ID = ?1 AND IS_DELETE=0", nativeQuery = true)
    List<ShipEquipmentFuelMap> findUseMethod(String shipId);
    @Query(value = "select DISTINCT(CONS_METHOD) from SHIP_EQUIPMENT_FUEL_MAP WHERE IS_DELETE = 0 AND SHIP_ID = '' AND CONS_METHOD IS NOT NULL",nativeQuery = true)
    List<String> findByConsMethod(String shipId);
}
