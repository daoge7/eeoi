package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.RawVoyageAddoil;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyageOutoil;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyageSludge;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository("rawVoyageSludgeoilRepository")
public interface RawVoyageSludgeoilRepository extends PagingAndSortingRepository<RawVoyageSludge, String>, JpaSpecificationExecutor<RawVoyageSludge> {
    RawVoyageSludge findByIdAndIsDelete(String id, Integer isDelete);
    @Query(value = "select \"SUM\"(SLUDGE_TONS) oilCons from RAW_VOYAGE_SLUDGE WHERE SHIP_ID=?1 AND IS_DELETE=0 AND SLUDGE_OUT_TM>=\"TO_DATE\"(?2, 'yyyy-mm-dd hh24:mi:ss') AND SLUDGE_OUT_TM<=\"TO_DATE\"(?3, 'yyyy-mm-dd hh24:mi:ss') GROUP BY OIL_ID",nativeQuery = true)
    BigDecimal getSludgeCons(String shipId,String startTime,String endTime);

    @Query(value = "select * from RAW_VOYAGE_SLUDGE WHERE SHIP_ID=?1 AND IS_DELETE=0 AND SLUDGE_OUT_TM>=\"TO_DATE\"(?2, 'yyyy-mm-dd hh24:mi:ss') - (12/24) AND SLUDGE_OUT_TM<=\"TO_DATE\"(?3, 'yyyy-mm-dd hh24:mi:ss') + (12/24)",nativeQuery = true)
    List<RawVoyageSludge> getSludgeList(String shipId,String startTime,String endTime);

    List<RawVoyageSludge> findByPortinfoIdAndIsDelete(String portInfoId,Integer isDelete);
}
