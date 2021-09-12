package cn.ccsit.eeoi.ship.ssmisrepository;


import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipGeEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisShipEquipAttrEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository("ssmisOiShipEquipAttrRepository")
public interface SsmisOiShipEquipAttrRepository extends PagingAndSortingRepository<SsmisShipEquipAttrEntity,String> ,JpaSpecificationExecutor<SsmisShipEquipAttrEntity> {
    @Query(value = "select ATTRVALUE  from SSMISHOST.SM_SHIP_EQUIP_ATTR WHERE CCSNO =  ?1 and SM_SYS_TREE_ID = ?2 AND ATTRVALUE IS NOT NULL",nativeQuery = true)
    List<BigDecimal> findByCcsnoAndSmSystree(String ccsno, String smSysTreeId);
}
