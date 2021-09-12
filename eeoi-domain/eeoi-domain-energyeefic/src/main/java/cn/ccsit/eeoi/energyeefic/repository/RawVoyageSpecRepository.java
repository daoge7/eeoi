package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePort;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyageSpec;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("rawVoyageSpecRepository")
public interface RawVoyageSpecRepository extends PagingAndSortingRepository<RawVoyageSpec,String> ,JpaSpecificationExecutor<RawVoyageSpec> {
    RawVoyageSpec findByIdAndIsDelete(String rawVoyageSpecId,Integer isDelete);
    List<RawVoyageSpec> findByTaskIdAndIsDelete(String taskId,Integer isDelete);

    /**
     * 查询在跨年时间段内所有的港口
     * @param shipId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT * from RAW_VOYAGE_SPEC WHERE SHIP_ID = :shipId AND IS_DELETE=0 AND BEGIN_TM BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss')\n" +
            "AND END_TM BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss')", nativeQuery = true)
    List<RawVoyageSpec> findByAcrossYear(@Param("shipId") String shipId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
