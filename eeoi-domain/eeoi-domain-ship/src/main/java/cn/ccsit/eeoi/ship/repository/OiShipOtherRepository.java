package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiShipOther;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("oiShipOtherRepository")
public interface OiShipOtherRepository extends PagingAndSortingRepository<OiShipOther,String> {
    /**
     * 根据船舶id逻辑删除设备
     * @param shipId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_OTHER SET IS_DELETE = 1,UPDATEDATE=sysdate where SHIP_ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipOtherByShipId(String shipId);
    /**
     * 逻辑删除设备
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "UPDATE  OI_SHIP_OTHER SET IS_DELETE = 1,UPDATEDATE=sysdate where ID = ?1", nativeQuery = true)
    @Transactional
    int deleteOiShipOtherById(String id);

    List<OiShipOther> findByShipIdAndIsDelete(String shipId,Integer isDelete);

    OiShipOther findByIdAndIsDelete(String oiShipOtherId,Integer isDelete);
}
