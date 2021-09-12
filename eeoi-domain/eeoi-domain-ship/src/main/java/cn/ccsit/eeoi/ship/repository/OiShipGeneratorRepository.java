package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiIncinerator;
import cn.ccsit.eeoi.ship.entity.OiShipGenerator;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("oiShipGeneratorRepository")
public interface OiShipGeneratorRepository extends PagingAndSortingRepository<OiShipGenerator,String>,JpaSpecificationExecutor<OiShipGenerator> {
    /**
     * 根据船舶id逻辑删除设备
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_GENERATOR SET IS_DELETE = 1,OPDATE=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipGeneratorByShipId(String shipId);
    /**
     * 逻辑删除设备
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_GENERATOR SET IS_DELETE = 1,OPDATE=SYSDATE where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipGeneratorById(String id);

    List<OiShipGenerator> findByShipIdAndIsDelete(String shipId,Integer isDelete);
    OiShipGenerator findByIdAndIsDelete(String id, Integer isDelete);
}
