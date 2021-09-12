package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.EuEmissionRpt;
import cn.ccsit.eeoi.energyeefic.entity.ImoBdnSummary;
import cn.ccsit.eeoi.energyeefic.entity.ImoStdrpt;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("euEmissionRptRepository")
public interface EuEmissionRptRepository extends PagingAndSortingRepository<EuEmissionRpt, String>, JpaSpecificationExecutor<EuEmissionRpt> {
    EuEmissionRpt findByIdAndIsDelete(String id, Integer isDelete);

    @Query(value = "SELECT * from EU_EMISSION_RPT WHERE IS_DELETE=0 AND SHIP_ID=?1 AND " +
            "START_TM between to_date(?2,'yyyy-mm-dd hh24:mi:ss') and to_date(?3,'yyyy-mm-dd hh24:mi:ss') AND END_TM between to_date(?4,'yyyy-mm-dd hh24:mi:ss') and to_date(?5,'yyyy-mm-dd hh24:mi:ss')", nativeQuery = true)
    List<EuEmissionRpt> findByShipIdAndIsDeleteAndStartTmBetweenAndEndTmBetween(String shipId, String startTime1, String endTime1, String startTime2, String endTime2);

    @Query(value = "SELECT  * FROM EU_EMISSION_RPT WHERE IS_DELETE = 0 AND SHIP_ID =?1 AND \"TO_CHAR\"(START_TM,'yyyy') = ?2", nativeQuery = true)
    EuEmissionRpt findByShipIdAndStartTime(String shipId, Integer year);
}
