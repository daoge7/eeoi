package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.FlagDocChange;
import cn.ccsit.eeoi.ship.entity.GcClientCate;
import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("flagDocChangeRepository")
public interface FlagDocChangeRepository extends JpaRepository<FlagDocChange,String> {

    List<FlagDocChange> findAllByOrderByEffectiveDateDesc();

    List<FlagDocChange> findAllByshipIdAndIsDeleteOrderByEffectiveDateDesc(String shipId,String isDelete);
    @Query(value = "SELECT * FROM FLAG_DOC_CHANGE WHERE IS_DELETE = 0 AND IMO_NO = ?1 AND CHANGE_TYPE = ?2 ORDER BY EFFECTIVE_DATE DESC",nativeQuery = true)
    List<FlagDocChange> findByImoAndIsDeleteOrderByEffectiveDateDesc(String imoNo,String changeType);
}
