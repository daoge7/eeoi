package cn.ccsit.eeoi.ship.ssmisrepository;


import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipGeEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipMainEngineEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisShipEquipAttrEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("ssmisOiShipMainEngineRepository")
public interface SsmisOiShipMainEngineRepository extends PagingAndSortingRepository<SsmisOiShipMainEngineEntity,String> ,JpaSpecificationExecutor<SsmisOiShipMainEngineEntity> {
    List<SsmisOiShipMainEngineEntity> findByCcsno(String ccsno);
}
