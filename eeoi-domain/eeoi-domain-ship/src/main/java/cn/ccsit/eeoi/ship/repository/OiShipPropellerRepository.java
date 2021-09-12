package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiShipGe;
import cn.ccsit.eeoi.ship.entity.OiShipPropeller;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("OiShipPropellerRepository")
public interface OiShipPropellerRepository extends PagingAndSortingRepository<OiShipPropeller, String> {
    OiShipPropeller findByIdAndIsDelete(String id,Integer isDelete);
    List<OiShipPropeller> findByShipIdAndIsDelete(String shipId,Integer isDelete);
}
