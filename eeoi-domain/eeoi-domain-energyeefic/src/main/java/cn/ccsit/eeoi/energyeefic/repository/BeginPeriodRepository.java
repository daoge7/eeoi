package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.BeginPeriod;
import cn.ccsit.eeoi.energyeefic.entity.BeginPeriodOil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository("beginPeriodRepository")
public interface BeginPeriodRepository extends PagingAndSortingRepository<BeginPeriod, String>, JpaSpecificationExecutor<BeginPeriod> {
    BeginPeriod findByShipIdAndPeriodTimeAndIsDelete(String shipId, Date periodTime, Integer isDelete);

    BeginPeriod findByIdAndIsDelete(String id, Integer isDelete);

    @Query(value = "SELECT count(*) from BEGIN_PERIOD WHERE IS_DELETE=0 AND SHIP_ID=?1 AND " +
            "(PERIOD_TIME = \"TO_DATE\"(?2, 'yyyy-mm-dd hh24:mi:ss') OR PERIOD_TIME = \"TO_DATE\"(?3, 'yyyy-mm-dd hh24:mi:ss'))", nativeQuery = true)
    Integer findByShipIdAndIsDeleteAndPeriodTimeOrPeriodTime(String shipId, String startTime, String endTime);
//    @Query(value = "SELECT PERIOD_TIME FROM BEGIN_PERIOD WHERE IS_DELETE = 0 AND SHIP_ID= ?1 ORDER BY PERIOD_TIME",nativeQuery = true)
//    List<Date> getPeriodTime(String shipId);
    @Query(value = "SELECT PERIOD_TIME FROM BEGIN_PERIOD WHERE IS_DELETE = 0 AND SHIP_ID = ?1 AND \"TO_CHAR\"(PERIOD_TIME,'yyyy') BETWEEN ?2 AND ?3  ORDER BY PERIOD_TIME",nativeQuery = true)
    List<Date> getPeriodTime(String shipId,String startYear,String endYear);

    /**
     * 根据船舶id和期初期末时间查询期初期末量
     *
     * @param shipId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT PERIOD_TIME from BEGIN_PERIOD WHERE IS_DELETE=0 AND SHIP_ID=?1 AND " +
            "PERIOD_TIME between to_date(?2,'yyyy-mm-dd hh24:mi:ss') and to_date(?3,'yyyy-mm-dd hh24:mi:ss')", nativeQuery = true)
    List<Date> findByShipIdAndIsDeleteAndPeriodTimeBetween(String shipId, String startTime, String endTime);

    @Query(value = "SELECT * FROM BEGIN_PERIOD WHERE IS_DELETE = 0 AND SHIP_ID = ?1 AND PERIOD_TIME = \"TO_DATE\"(?2, 'yyyy-mm-dd hh24:mi:ss')", nativeQuery = true)
    BeginPeriod findByPeriodTimeAndIsDelete(String shipId, String periodTime);
}
