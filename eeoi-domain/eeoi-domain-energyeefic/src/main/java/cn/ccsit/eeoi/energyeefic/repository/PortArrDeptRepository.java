package cn.ccsit.eeoi.energyeefic.repository;

import cn.ccsit.eeoi.energyeefic.entity.PortArrDeptInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("portArrDeptRepository")
public interface PortArrDeptRepository extends PagingAndSortingRepository<PortArrDeptInfo, String>, JpaSpecificationExecutor<PortArrDeptInfo> {


        @Query(value = "SELECT \n" +
            "port.ID AS ID,\n" +
            "port.task, \n" +
            "port.PORTCN,\n" +
            "port.PORTEN,\n" +
            "port.ARR_TM,\n" +
            "port.ARR_ZONE,\n" +
            " port.DEPT_TM,\n" +
            " port.DEPT_ZONE,\n" +
            " port.IN_PORT," +
            " port.DISTANCE," +
            "  portoill.arr_port_lfo,\n" +
            "  portoill.dept_port_lfo,\n" +
            "  portoilh.arr_port_hfo,\n" +
            "  portoilh.dept_port_hfo,\n" +
            "  portoilm.arr_port_m,\n" +
            "  portoilm.dept_port_m,\n" +
            "  addoill.add_lfo,\n" +
            "  addoilh.add_hfo,\n" +
            "  addoilm.add_m,\n" +
            "  outoill.out_lfo,\n" +
            "  outoilh.out_hfo,\n" +
            "  outoilm.out_m,\n" +
            "  loadl.arr_tons,\n" +
            "  loadh.dept_tons\n" +
            "FROM" +
            "(SELECT \n" +
            "    port.ID AS ID,\n" +
            "    task.task," +
            "    port.PORTCN,\n" +
            "    port.PORTEN,\n" +
            "    port.ARR_TM,\n" +
            "    port.DISTANCE,\n" +
            "    port.ARR_ZONE,\n" +
            "    port.DEPT_TM,\n" +
            "    port.DEPT_ZONE,\n" +
            "    port.IN_PORT\n" +
            "  FROM raw_voyage_port port\n" +
            "  LEFT JOIN oi_ship_task task on port.TASK_ID = task.ID \n"+
            "  WHERE port.SHIP_ID = :shipId \n" +
            "  AND port.IS_DELETE = 0\n" +
            "  AND port.REF_CODE IS NOT NULL\n" +
            "  AND task.IS_DELETE = 0\n" +
            "  AND (port.ARR_TM BETWEEN TO_DATE(:startTime, 'yyyy-mm-dd hh24:mi:ss')AND TO_DATE(:endTime, 'yyyy-mm-dd hh24:mi:ss'))\n" +
            "  ) port \n" +
            "  \n" +
            "  LEFT JOIN\n" +
            "    (SELECT \n" +
            "      portoil.PORTINFO_ID AS PORTINFO_ID, \n" +
            "      SUM(portoil.ARR_TONS)  AS arr_port_lfo, \n" +
            "      SUM(portoil.DEPT_TONS) AS dept_port_lfo\n" +
            "    FROM raw_voyage_portoil  portoil\n" +
            "    WHERE portoil.PORTINFO_ID IN \n" +
            "      ( SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE port.SHIP_ID = :shipId AND port.IS_DELETE = 0 )\n" +
            "    AND portoil.IS_DELETE = 0\n" +
            "    GROUP BY portoil.PORTINFO_ID, SUBSTR(portoil.OIL_ID, 0, 1)\n" +
            "    HAVING SUBSTR(portoil.OIL_ID, 0, 1) = 2\n" +
            "    ) portoill ON port.ID = portoill.PORTINFO_ID\n" +
            "    \n" +
            "  LEFT JOIN \n" +
            "    (SELECT \n" +
            "      portoil.PORTINFO_ID, \n" +
            "      SUM(portoil.ARR_TONS)  AS arr_port_hfo, \n" +
            "      SUM(portoil.DEPT_TONS) AS dept_port_hfo\n" +
            "    FROM raw_voyage_portoil  portoil\n" +
            "    WHERE portoil.PORTINFO_ID IN \n" +
            "      (SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE port.SHIP_ID = :shipId AND port.IS_DELETE = 0 )\n" +
            "    AND portoil.IS_DELETE = 0\n" +
            "    GROUP BY portoil.PORTINFO_ID, SUBSTR(portoil.OIL_ID, 0, 1)\n" +
            "    HAVING SUBSTR(portoil.OIL_ID, 0, 1) = 1\n" +
            "    )portoilh ON port.ID = portoilh.PORTINFO_ID\n" +
            "    \n" +
            "  \n" +
            "    LEFT JOIN \n" +
            "    (SELECT \n" +
            "      portoil.PORTINFO_ID, \n" +
            "      SUM(portoil.ARR_TONS)  AS arr_port_m, \n" +
            "      SUM(portoil.DEPT_TONS) AS dept_port_m\n" +
            "    FROM raw_voyage_portoil  portoil\n" +
            "    WHERE portoil.PORTINFO_ID IN \n" +
            "      (SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE port.SHIP_ID = :shipId AND port.IS_DELETE = 0 )\n" +
            "    AND portoil.IS_DELETE = 0\n" +
            "    GROUP BY portoil.PORTINFO_ID, portoil.OIL_ID\n" +
            "    HAVING portoil.OIL_ID = 3\n" +
            "    )portoilm ON port.ID = portoilm.PORTINFO_ID\n" +
            "  \n" +
            "  LEFT JOIN\n" +
            "    (SELECT \n" +
            "      addoil.PORTINFO_ID, \n" +
            "      SUM(addoil.ADD_TONS) AS add_lfo\n" +
            "    FROM RAW_VOYAGE_ADDOIL addoil\n" +
            "    WHERE addoil.PORTINFO_ID IN \n" +
            "      (SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE port.SHIP_ID = :shipId AND port.IS_DELETE = 0 )  \n" +
            "    AND addoil.IS_DELETE = 0\n" +
            "    GROUP BY addoil.PORTINFO_ID, SUBSTR(addoil.OIL_ID, 0, 1)\n" +
            "    HAVING SUBSTR(addoil.OIL_ID, 0, 1) = 2 \n" +
            "    ) addoill ON addoill.PORTINFO_ID = port.ID\n" +
            "   \n" +
            "    LEFT JOIN\n" +
            "    (SELECT \n" +
            "      addoil.PORTINFO_ID, \n" +
            "      SUM(addoil.ADD_TONS) AS add_hfo\n" +
            "    FROM RAW_VOYAGE_ADDOIL addoil\n" +
            "    WHERE addoil.PORTINFO_ID IN \n" +
            "      (SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE port.SHIP_ID = :shipId AND port.IS_DELETE = 0 )  \n" +
            "    AND addoil.IS_DELETE = 0\n" +
            "    GROUP BY addoil.PORTINFO_ID, SUBSTR(addoil.OIL_ID, 0, 1)\n" +
            "    HAVING SUBSTR(addoil.OIL_ID, 0, 1) = 1 \n" +
            "    ) addoilh ON addoilh.PORTINFO_ID = port.ID\n" +
            "    \n" +
            "   \n" +
            "    LEFT JOIN\n" +
            "    (SELECT \n" +
            "      addoil.PORTINFO_ID, \n" +
            "      SUM(addoil.ADD_TONS) AS add_m\n" +
            "    FROM RAW_VOYAGE_ADDOIL addoil\n" +
            "    WHERE addoil.PORTINFO_ID IN \n" +
            "      (SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE port.SHIP_ID = :shipId AND port.IS_DELETE = 0 )  \n" +
            "    AND addoil.IS_DELETE = 0\n" +
            "    GROUP BY addoil.PORTINFO_ID, addoil.OIL_ID\n" +
            "    HAVING addoil.OIL_ID = 3\n" +
            "    ) addoilm ON addoilm.PORTINFO_ID = port.ID\n" +
            "    \n" +
            "    \n" +
            "  \n" +
            "    LEFT JOIN\n" +
            "    (SELECT \n" +
            "      outoil.PORTINFO_ID, \n" +
            "      SUM(outoil.OUT_TONS) AS out_lfo\n" +
            "    FROM RAW_VOYAGE_OUTOIL outoil\n" +
            "    WHERE outoil.PORTINFO_ID IN \n" +
            "      (\n" +
            "        SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE \n" +
            "        port.IS_DELETE = 0\n" +
            "        AND port.SHIP_ID = :shipId   \n" +
            "      ) \n" +
            "    AND outoil.IS_DELETE = 0\n" +
            "    GROUP BY outoil.PORTINFO_ID, SUBSTR(outoil.OIL_ID, 0, 1)\n" +
            "    HAVING SUBSTR(outoil.OIL_ID, 0, 1) = 2\n" +
            "    ) outoill ON outoill.PORTINFO_ID = port.ID\n" +
            " \n" +
            "    LEFT JOIN\n" +
            "    (SELECT \n" +
            "      outoil.PORTINFO_ID, \n" +
            "      SUM(outoil.OUT_TONS) AS out_hfo\n" +
            "    FROM RAW_VOYAGE_OUTOIL outoil\n" +
            "    WHERE outoil.PORTINFO_ID IN \n" +
            "      (\n" +
            "        SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE \n" +
            "        port.IS_DELETE = 0\n" +
            "        AND port.SHIP_ID = :shipId   \n" +
            "      ) \n" +
            "    AND outoil.IS_DELETE = 0\n" +
            "    GROUP BY outoil.PORTINFO_ID, SUBSTR(outoil.OIL_ID, 0, 1)\n" +
            "    HAVING SUBSTR(outoil.OIL_ID, 0, 1) = 1\n" +
            "    ) outoilh ON outoilh.PORTINFO_ID = port.ID\n" +
            "    \n" +
            "  \n" +
            "    LEFT JOIN\n" +
            "    (SELECT \n" +
            "      outoil.PORTINFO_ID, \n" +
            "      SUM(outoil.OUT_TONS) AS out_m\n" +
            "    FROM RAW_VOYAGE_OUTOIL outoil\n" +
            "    WHERE outoil.PORTINFO_ID IN \n" +
            "      (\n" +
            "        SELECT port.ID\n" +
            "        FROM raw_voyage_port port\n" +
            "        WHERE \n" +
            "        port.IS_DELETE = 0\n" +
            "        AND port.SHIP_ID = :shipId   \n" +
            "      ) \n" +
            "    AND outoil.IS_DELETE = 0\n" +
            "    GROUP BY outoil.PORTINFO_ID, outoil.OIL_ID\n" +
            "    HAVING outoil.OIL_ID = 3\n" +
            "    ) outoilm ON outoilm.PORTINFO_ID = port.ID\n" +
            "    \n" +
            "\n" +
            "    LEFT JOIN\n" +
            "    (SELECT \n" +
            "      loading.PORTINFO_ID,\n" +
            "      loading.CARGO_TONS AS arr_tons\n" +
            "      FROM raw_voyage_portloading loading\n" +
            "      WHERE loading.PORTINFO_ID IN \n" +
            "        (\n" +
            "          SELECT port.ID\n" +
            "          FROM raw_voyage_port port\n" +
            "          WHERE \n" +
            "          port.IS_DELETE = 0\n" +
            "          AND port.SHIP_ID = :shipId   \n" +
            "        )\n" +
            "      AND loading.IS_DELETE = 0\n" +
            "      AND LOADING_TYPE = 1\n" +
            "    )  loadl ON loadl.PORTINFO_ID = port.ID\n" +
            "\n" +
            "    LEFT JOIN\n" +
            "    (SELECT \n" +
            "      loading.PORTINFO_ID,\n" +
            "      loading.CARGO_TONS AS dept_tons\n" +
            "      FROM raw_voyage_portloading loading\n" +
            "      WHERE loading.PORTINFO_ID IN \n" +
            "        (\n" +
            "          SELECT port.ID\n" +
            "          FROM raw_voyage_port port\n" +
            "          WHERE \n" +
            "          port.IS_DELETE = 0\n" +
            "          AND port.SHIP_ID = :shipId   \n" +
            "        )\n" +
            "      AND loading.IS_DELETE = 0\n" +
            "      AND LOADING_TYPE = 0\n" +
            "    )  loadh ON loadh.PORTINFO_ID = port.ID\n" +
            "ORDER BY port.ARR_TM DESC   ", nativeQuery = true)
    public List<PortArrDeptInfo> getPortArrDept(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                       @Param("shipId") String shipId);
//    {
//        List<PortArrDeptVo> retList = em
//                .createNativeQuery(SQL,"PortArrDeptVoMapping")
//                .setParameter("startTime",startTime)
//                .setParameter("endTime",endTime)
//                .setParameter("shipId", shipId)
//                .getResultList();
//        return retList;
//    }
}
