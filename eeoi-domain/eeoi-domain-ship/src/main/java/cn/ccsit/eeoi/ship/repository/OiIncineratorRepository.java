package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiIncinerator;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("oiIncineratorRepository")
public interface OiIncineratorRepository extends PagingAndSortingRepository<OiIncinerator,String> {
    /**
     * 根据船舶id逻辑删除设备
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_INCINERATOR SET IS_DELETE = 1,UPDATE_TIME=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiIncineratorByShipId(String shipId);
    /**
     * 根据船舶id逻辑删除设备
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_INCINERATOR SET IS_DELETE = 1,UPDATE_TIME=sysdate where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiIncineratorById(String id);

    List<OiIncinerator> findByShipIdAndIsDelete(String shipId,Integer isDelete);

    OiIncinerator findByIdAndIsDelete(String id,Integer isDelete);
}
