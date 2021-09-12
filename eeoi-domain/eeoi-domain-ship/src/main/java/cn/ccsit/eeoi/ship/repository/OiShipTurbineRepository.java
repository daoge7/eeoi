package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiIncinerator;
import cn.ccsit.eeoi.ship.entity.OiShipTurbine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("oiShipTurbineRepository")
public interface OiShipTurbineRepository extends PagingAndSortingRepository<OiShipTurbine, String> {
    /**
     * 根据船舶id逻辑删除设备
     *
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_TURBINE SET IS_DELETE = 1,UPDATEDATE=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipTurbineByShipId(String shipId);

    /**
     * 根据船舶id逻辑删除设备
     *
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_TURBINE SET IS_DELETE = 1,UPDATEDATE=sysdate where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipTurbineById(String id);

    List<OiShipTurbine> findByShipIdAndIsDelete(String shipIds, Integer isDelete);

    OiShipTurbine findByIdAndIsDelete(String id, Integer isDelete);
}
