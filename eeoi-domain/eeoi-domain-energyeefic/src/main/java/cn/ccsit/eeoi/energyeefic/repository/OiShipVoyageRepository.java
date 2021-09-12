package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.OiShipVoyage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Repository("oiShipVoyageRepository")
public interface OiShipVoyageRepository extends PagingAndSortingRepository<OiShipVoyage, String>, JpaSpecificationExecutor<OiShipVoyage> {
    List<OiShipVoyage> findByTaskidAndIsDelete(String taskId, Integer isDelete);

    /**
     * 根据时间段和船舶id，查询船舶总的航行时间和航行里程
     *
     * @param shipId
     * @param startTime1
     * @param endTime1
     * @param startTime2
     * @param endTime2
     * @return
     */
    @Query(value = "select \"SUM\"(DISTANCE) distance,\"SUM\"(((END_TIME-START_TIME)*24-STOP_TIME-DRIFT_TIME)) distanceTime from OI_SHIP_VOYAGE WHERE SHIP_ID = ?1 AND  START_TIME between to_date(?2,'yyyy-mm-dd hh24:mi:ss') and to_date(?3,'yyyy-mm-dd hh24:mi:ss') \n" +
            "AND END_TIME between to_date(?4,'yyyy-mm-dd hh24:mi:ss') and to_date(?5,'yyyy-mm-dd hh24:mi:ss') AND IS_DELETE = 0", nativeQuery = true)
    Map<String, Object> findDistanceAndDistanceTime(String shipId, String startTime1, String endTime1, String startTime2, String endTime2);

    /**
     * 查询非跨年和非船旗变更和非doc变更航段
     * @param shipId
     * @param startTime1
     * @param endTime1
     * @param startTime2
     * @param endTime2
     * @return
     */
    @Query(value = "select * from OI_SHIP_VOYAGE WHERE SHIP_ID = ?1 AND  START_TIME between to_date(?2,'yyyy-mm-dd hh24:mi:ss') and to_date(?3,'yyyy-mm-dd hh24:mi:ss') \n" +
            "AND END_TIME between to_date(?4,'yyyy-mm-dd hh24:mi:ss') and to_date(?5,'yyyy-mm-dd hh24:mi:ss') AND IS_DELETE = 0 AND VOY_TYPE =0", nativeQuery = true)
    List<OiShipVoyage> findNoAcrossVoyage(String shipId, String startTime1, String endTime1, String startTime2, String endTime2);

//    /**
//     * 查询跨年或者船旗变更和doc变更的航段
//     * @param shipId
//     * @param startYear1
//     * @param endYear1
//     * @param startYear2
//     * @param endYear2
//     * @return
//     */
//    @Query(value = "SELECT * from OI_SHIP_VOYAGE WHERE SHIP_ID=?1 AND IS_DELETE=0 AND (\"TO_CHAR\"(START_TIME,'yyyy')=?2 AND \"TO_CHAR\"(END_TIME,'yyyy')=?3) OR \n" +
//            "(\"TO_CHAR\"(START_TIME,'yyyy')=?4 AND \"TO_CHAR\"(END_TIME,'yyyy')=?5)", nativeQuery = true)
//    List<OiShipVoyage> getAcrossYear(String shipId,String startYear1,String endYear1,String startYear2,String endYear2);
    /**
     * 查询跨年或者船旗变更和doc变更的航段
     * @param shipId
     * @param startYear
     * @param endYear
     * @return
     */
    @Query(value = "SELECT * from OI_SHIP_VOYAGE WHERE SHIP_ID=?1 AND IS_DELETE=0 AND (\"TO_CHAR\"(START_TIME,'yyyy')=?2 AND \"TO_CHAR\"(END_TIME,'yyyy')=?3) AND VOY_TYPE =0 ", nativeQuery = true)
    OiShipVoyage getAcrossYear(String shipId,String startYear,String endYear);

    OiShipVoyage findByIdAndIsDelete(String id,Integer isDelete);

    /**
     * 根据船舶id查询船舶的航段数据
     * @param shipId
     * @param isDelete
     * @return
     */
    List<OiShipVoyage> findByShipIdAndIsDelete(String shipId,Integer isDelete);
}
