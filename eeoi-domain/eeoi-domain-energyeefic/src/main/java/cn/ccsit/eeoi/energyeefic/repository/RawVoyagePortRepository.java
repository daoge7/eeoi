package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("rawVoyagePortRepository")
public interface
RawVoyagePortRepository extends PagingAndSortingRepository<RawVoyagePort, String>, JpaSpecificationExecutor<RawVoyagePort> {
    @Query(value = "SELECT o.* FROM RAW_VOYAGE_PORT o WHERE o.ID = ?1 AND o.IS_DELETE = ?2", nativeQuery = true)
    RawVoyagePort findByIdAndIsDelete(String voyagePortId, Integer isDelete);

    List<RawVoyagePort> findByTaskIdAndIsDelete(String taskId, Integer isDelete);
    List<RawVoyagePort> findByTaskIdNextAndIsDelete(String taskId, Integer isDelete);

    /**
     * 查询在跨年时间段内所有的港口
     * @param shipId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT * from RAW_VOYAGE_PORT WHERE SHIP_ID = :shipId AND IS_DELETE=0 AND (ARR_TM BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') - (12/24) AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss') + (12/24)\n" +
            "OR DEPT_TM BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') - (12/24) AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss') + (12/24)) order by ARR_TM", nativeQuery = true)
    List<RawVoyagePort> findByAcrossYear(@Param("shipId") String shipId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询期末时间是否在港
     * @param shipId
     * @param startTime
     * @return
     */
    @Query(value = "SELECT * from RAW_VOYAGE_PORT WHERE SHIP_ID = :shipId AND IS_DELETE=0 AND ARR_TM <= \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') + (12/24) AND DEPT_TM >= \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') - (12/24)",nativeQuery = true)
    List<RawVoyagePort> findEndYear(@Param("shipId") String shipId,@Param("startTime") String startTime);

    @Query(value = "select * from RAW_VOYAGE_PORT WHERE SHIP_ID = ?1 AND  ARR_TM between to_date(?2,'yyyy-mm-dd hh24:mi:ss') and to_date(?3,'yyyy-mm-dd hh24:mi:ss') \n" +
            "AND DEPT_TM between to_date(?4,'yyyy-mm-dd hh24:mi:ss') and to_date(?5,'yyyy-mm-dd hh24:mi:ss') AND IS_DELETE = 0", nativeQuery = true)
    List<RawVoyagePort> findNoAcrossYear(String shipId, String startTime1, String endTime1, String startTime2, String endTime2);

    /**
     * 查询报告时间段的内的港口包括跨年的港口
     * @return
     */
    @Query(value = "select * FROM RAW_VOYAGE_PORT WHERE SHIP_ID=:shipId AND IS_DELETE = 0 AND IS_EU = 1 AND \n" +
            "(ARR_TM BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss') OR DEPT_TM BETWEEN  \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss'))",nativeQuery = true)
    List<RawVoyagePort> findByIsEu(@Param("shipId") String shipId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<RawVoyagePort> findAllByIsDeleteAndRefCodeIn(Integer isDelete,List<String> refCodes);
    RawVoyagePort findByIsDeleteAndRefCode(Integer isDelete,String refCode);
}