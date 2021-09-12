package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Repository("oiShipInfoRepository")
public interface OiShipInfoRepository extends PagingAndSortingRepository<OiShipInfo, String>, JpaSpecificationExecutor<OiShipInfo> {
    /**
     * 根据船舶id逻辑删除
     *
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE OI_SHIP_INFO SET IS_DELETE = 1 where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipInfoByShipId(String shipId);

    @Query(value = "SELECT \"COUNT\"(1) from OI_SHIP_INFO WHERE IMONO = ?1 OR REGISTERNO = ?2", nativeQuery = true)
    Integer findByRegisterNoOrImoNu(String imo, String registerno);

    @Query(value = "SELECT id from OI_SHIP_INFO WHERE DOC_ID IN ?1", nativeQuery = true)
    Set<String> findShipIdsByShipCodes(List<String> codes);

    @Query(value = "SELECT id from OI_SHIP_INFO WHERE DOC_IACS IN ?1", nativeQuery = true)
    Set<String> findShipIdsByShipIacs(List<String> iacs);

    @Query(value = "SELECT id from OI_SHIP_INFO WHERE DOC_CUID IN ?1", nativeQuery = true)
    Set<String> findShipIdsByShipCuids(List<String> cuids);

    @Query(value = "SELECT * FROM OI_SHIP_INFO WHERE IMONO = ?1  AND IS_DELETE = 0", nativeQuery = true)
    OiShipInfo findByImonoAndIsDelete(String imono);

    OiShipInfo findByIdAndIsDelete(String shipId, Integer isDelete);

    @Query(value = "SELECT * FROM OI_SHIP_INFO WHERE (IMONO = ?1 or REGISTERNO = ?2) AND IS_DELETE = 0", nativeQuery = true)
//todo
    OiShipInfo findByImonoOrRegisternoAndIsDelete(String imoNo, String registerNo, Integer isDelete);


}
