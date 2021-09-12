package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.OiShipTask;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("oiShipTaskRepository")
public interface OiShipTaskRepository extends PagingAndSortingRepository<OiShipTask, String>, JpaSpecificationExecutor<OiShipTask> {
    @Query(value = "SELECT OST.\"ID\" from OI_SHIP_TASK ost LEFT JOIN OI_SHIP_INFO osin ON OST.SHIP_ID = OSIN.\"ID\" WHERE OST.IS_DELETE = 0 AND OST.SHIP_ID = ?1 AND OST.TASK = ?2 AND OST.TASKYEAR = ?3", nativeQuery = true)
    String findByTaskAndShipIsDelete(String shipId, String task, String taskYear);

    OiShipTask findByShipIdAndIsDeleteAndTaskAndTaskyear(String shipId, Integer isDelete, String task, String taskYaer);

    OiShipTask findByIdAndIsDelete(String volageId, Integer isDelete);

    OiShipTask findByTaskAndIsDelete(String task, Integer isDelete);

    @Query(value = "SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\tOI_SHIP_TASK\n" +
            "WHERE\n" +
            "\tIS_DELETE = 0\n" +
            "AND SHIP_ID = :shipId\n" +
            "AND (\n" +
            "\t(\n" +
            "\t\t\"TO_DATE\" (\n" +
            "\t\t\t:startTime,\n" +
            "\t\t\t'yyyy-mm-dd hh24:mi:ss'\n" +
            "\t\t) BETWEEN START_TIME\n" +
            "\t\tAND END_TIME\n" +
            "\t\tOR \"TO_DATE\" (\n" +
            "\t\t\t:endTime,\n" +
            "\t\t\t'yyyy-mm-dd hh24:mi:ss'\n" +
            "\t\t) BETWEEN START_TIME\n" +
            "\t\tAND END_TIME\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\t(\n" +
            "\t\t\tSTART_TIME BETWEEN \"TO_DATE\" (\n" +
            "\t\t\t\t:startTime,\n" +
            "\t\t\t\t'yyyy-mm-dd hh24:mi:ss'\n" +
            "\t\t\t)\n" +
            "\t\t\tAND \"TO_DATE\" (\n" +
            "\t\t\t\t:endTime,\n" +
            "\t\t\t\t'yyyy-mm-dd hh24:mi:ss'\n" +
            "\t\t\t)\n" +
            "\t\t\tOR END_TIME BETWEEN \"TO_DATE\" (\n" +
            "\t\t\t\t:startTime,\n" +
            "\t\t\t\t'yyyy-mm-dd hh24:mi:ss'\n" +
            "\t\t\t)\n" +
            "\t\t\tAND \"TO_DATE\" (\n" +
            "\t\t\t\t:endTime,\n" +
            "\t\t\t\t'yyyy-mm-dd hh24:mi:ss'\n" +
            "\t\t\t)\n" +
            "\t\t)\n" +
            "\t)\n" +
            ")", nativeQuery = true)
    List<OiShipTask> findOiShipTask(@Param("shipId") String shipId, @Param("startTime") String startTime, @Param("endTime") String endTime);
//    @Query(value = "SELECT * FROM OI_SHIP_TASK  WHERE SHIP_ID = :shipId  AND IS_DELETE = 0 AND (START_TIME BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss') OR \n" +
//            "END_TIME BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd hh24:mi:ss') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd hh24:mi:ss'))",nativeQuery = true)
//    List<OiShipTask> findOiShipTask(@Param("shipId") String shipId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询结束时间在时间范围内的航次
     *
     * @param shipId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT * FROM OI_SHIP_TASK  WHERE SHIP_ID = :shipId  AND IS_DELETE = 0 AND END_TIME BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd')", nativeQuery = true)
    List<OiShipTask> findOiShipTaskEndTime(@Param("shipId") String shipId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<OiShipTask> findByShipIdAndIsDelete(String shipId, Integer isDelete);

    @Query(value = "SELECT \"ID\" from OI_SHIP_TASK WHERE \"UPPER\"(\"TRIM\"(TASK)) = ?1 AND IS_DELETE = 0", nativeQuery = true)
    List<String> findTaskIdsByTaskNu(String taskNu);

    @Query(value = "SELECT OST.\"ID\" from OI_SHIP_TASK ost LEFT JOIN OI_SHIP_INFO osin ON OST.SHIP_ID = OSIN.\"ID\" WHERE OST.IS_DELETE = 0 AND OST.SHIP_ID = ?1 AND OST.TASK = ?2", nativeQuery = true)
    String findByShipIsDelete(String shipId, String task);

    @Query(value = "SELECT OST.\"ID\" from OI_SHIP_TASK ost LEFT JOIN OI_SHIP_INFO osin ON OST.SHIP_ID = OSIN.\"ID\" WHERE OST.IS_DELETE = 0 AND OST.SHIP_ID = ?1 AND OST.TASK = ?2 AND START_TIME IS NOT NULL ORDER BY START_TIME DESC ", nativeQuery = true)
    List<String> findListByShipIsDelete(String shipId, String task);

    @Query(value = "SELECT OST.* from OI_SHIP_TASK ost LEFT JOIN OI_SHIP_INFO osin ON OST.SHIP_ID = OSIN.\"ID\" WHERE OST.IS_DELETE = 0 AND OST.SHIP_ID = ?1 AND OST.TASK = ?2 AND START_TIME IS NOT NULL ORDER BY START_TIME DESC ", nativeQuery = true)
    List<OiShipTask> findOiShipTaskListByShipIsDelete(String shipId, String task);

}
