package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import cn.ccsit.eeoi.ship.entity.OiShipTfc;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Repository("oiShipTfcRepository")
public interface OiShipTfcRepository extends PagingAndSortingRepository<OiShipTfc,String> ,JpaSpecificationExecutor<OiShipTfc> {
    OiShipTfc findByAverageSpeedAndShipidAndIsDelete(Integer avgSpeed,String shipId,Integer isDelete);
    OiShipTfc findByIdAndIsDelete(String id,Integer isDelete);
    List<OiShipTfc> findByShipidAndIsDelete(String shipId,Integer isDelete);
}
