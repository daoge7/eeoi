package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiShipCmsaMap;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Repository("OiShipCmsaMapRepository")
public interface OiShipCmsaMapRepository extends PagingAndSortingRepository<OiShipCmsaMap,String> ,JpaSpecificationExecutor<OiShipCmsaMap> {
    /**
     * 删除和船舶有关的内河映射表
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_CMSA_MAP SET IS_DELETE = 1 where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipCmsaMapByShipId(String shipId);

    /**
     * 查询船舶id
     * @param clientId
     * @return
     */
    @Query(value = "SELECT SHIP_ID FROM OI_SHIP_CMSA_MAP WHERE CLIENT_ID = ?1",nativeQuery = true)
    Set<String> findByClientId(String clientId);
    /**
     * 查询船舶id
     * @param clientIds
     * @return
     */
    @Query(value = "SELECT SHIP_ID FROM OI_SHIP_CMSA_MAP WHERE CLIENT_ID IN ?1",nativeQuery = true)
    Set<String> findByClientIds(List<String> clientIds);

    @Modifying
    @Query(value = "DELETE FROM OI_SHIP_CMSA_MAP where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    void deleteByShipId(String shipId);

    List<OiShipCmsaMap> findByShipIdAndIsDelete(String shipId,Integer isDelete);
}
