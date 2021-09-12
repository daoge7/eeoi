package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePortoil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository("rawVoyagePortoilRepository")
public interface RawVoyagePortoilRepository extends PagingAndSortingRepository<RawVoyagePortoil,String> ,JpaSpecificationExecutor<RawVoyagePortoil> {
    RawVoyagePortoil findByIdAndIsDelete(String id,Integer isDelete);

    List<RawVoyagePortoil> findByPortinfoIdAndIsDelete(String portInfoId,Integer isDelete);
    @Query(value = "select OIL_ID oilId,\"SUM\"(CORRECT_TONS) oilCons from RAW_VOYAGE_PORTOIL WHERE SHIP_ID=?1 AND IS_DELETE=0 AND CORRECT_TM>=\"TO_DATE\"(?2, 'yyyy-MM-dd') AND CORRECT_TM<=\"TO_DATE\"(?3, 'yyyy-MM-dd') GROUP BY OIL_ID",nativeQuery = true)
    List<Map<String,Object>> getCorrctionCons(String shipId, String startTime, String endTime);

}
