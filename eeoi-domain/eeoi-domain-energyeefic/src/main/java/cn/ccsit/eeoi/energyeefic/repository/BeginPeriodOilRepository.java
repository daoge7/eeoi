package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.BdnOil;
import cn.ccsit.eeoi.energyeefic.entity.BeginPeriodOil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository("beginPeriodOilRepository")
public interface BeginPeriodOilRepository extends PagingAndSortingRepository<BeginPeriodOil, String>, JpaSpecificationExecutor<BeginPeriodOil> {

    /**
     * 查询期初油量
     * @param shipId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select OIL_ID oilId,\"SUM\"(FUEL_TONS) oilCons from BEGIN_PERIOD_OIL tt1 WHERE SHIP_ID= ?1 AND IS_DELETE=0 and EXISTS (select 1 from BEGIN_PERIOD tt2 where tt1.BEGIN_PERIOD_ID=tt2.id and tt2.is_delete=0) AND PERIOD_TIME BETWEEN  \"TO_DATE\"(?2, 'yyyy-mm-dd hh24:mi:ss') AND  \"TO_DATE\"(?3, 'yyyy-mm-dd hh24:mi:ss') GROUP BY OIL_ID",nativeQuery = true)
    List<Map<String,Object>> findByShipIdAndPeriodTime(String shipId, String startTime,String endTime);
}
