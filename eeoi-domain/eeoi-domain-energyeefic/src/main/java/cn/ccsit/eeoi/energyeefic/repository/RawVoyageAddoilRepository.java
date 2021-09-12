package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.ImoBdnSummary;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyageAddoil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Repository("rawVoyageAddoilRepository")
public interface RawVoyageAddoilRepository extends PagingAndSortingRepository<RawVoyageAddoil, String>, JpaSpecificationExecutor<RawVoyageAddoil> {
    RawVoyageAddoil findByIdAndIsDelete(String id, Integer isDelete);

    @Query(value = "select OIL_ID oilId,\"SUM\"(ADD_TONS) oilCons from RAW_VOYAGE_ADDOIL WHERE SHIP_ID=?1 AND IS_DELETE=0 AND ADD_TM>=\"TO_DATE\"(?2, 'yyyy-MM-dd') AND ADD_TM<=\"TO_DATE\"(?3, 'yyyy-MM-dd') GROUP BY OIL_ID", nativeQuery = true)
    List<Map<String, Object>> getAddOil(String shipId, String startTime, String endTime);

    @Query(value = "select * from RAW_VOYAGE_ADDOIL WHERE SHIP_ID=?1 AND IS_DELETE=0 AND ADD_TM>=\"TO_DATE\"(?2, 'yyyy-mm-dd hh24:mi:ss') -(12/24) AND ADD_TM<=\"TO_DATE\"(?3, 'yyyy-mm-dd hh24:mi:ss') + (12/24)", nativeQuery = true)
    List<RawVoyageAddoil> getBdnOil(String shipId, String startTime, String endTime);

    List<RawVoyageAddoil> findByPortinfoIdAndIsDelete(String portInfoId,Integer isDelete);

}
