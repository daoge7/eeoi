package cn.ccsit.eeoi.ship.ssmisrepository;


import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipGeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Repository("ssmisOiShipGeRepository")
public interface SsmisOiShipGeRepository extends PagingAndSortingRepository<SsmisOiShipGeEntity,String> ,JpaSpecificationExecutor<SsmisOiShipGeEntity> {
    @Query(value = "SELECT ROWNUM+1 AS id,t1.* from SSMISHOST.VIEW_SM_PRIME_MOVER t1 WHERE T1.IS_DELETE=1 and T1.CCSNO = ?1",nativeQuery = true)
    List<SsmisOiShipGeEntity> findByCcsnoAndIsDelete(String ccsno);
}
