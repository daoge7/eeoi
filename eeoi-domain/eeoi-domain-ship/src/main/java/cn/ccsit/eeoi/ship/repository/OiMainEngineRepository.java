package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiIncinerator;
import cn.ccsit.eeoi.ship.entity.OiMainEngine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("oiMainEngineRepository")
public interface OiMainEngineRepository extends PagingAndSortingRepository<OiMainEngine, String>{
    /**
     * 根据船舶id逻辑删除设备
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_MAIN_ENGINE SET IS_DELETE = 1,UPDATE_TIME=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiMainEngineByShipId(String shipId);
    /**
     * 逻辑删除设备
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_MAIN_ENGINE SET IS_DELETE = 1,UPDATE_TIME=sysdate where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiMainEngineById(String id);

    /**
     * 获取主机设备数量
     * @param shipId
     * @return
     */
    @Query(value = "SELECT \"COUNT\"(1) from OI_MAIN_ENGINE WHERE SHIP_ID = ?1 AND IS_DELETE = 0",nativeQuery = true)
    Integer findByShipId(String shipId);

    List<OiMainEngine> findByShipIdAndIsDelete(String shipId,Integer isDelete);

    List<OiMainEngine> findByCcsnoAndIsDelete(String ccsno,Integer isDelete);

    OiMainEngine findByIdAndIsDelete(String id, Integer isDelete);
}
