package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.ImoStdrpt;
import cn.ccsit.eeoi.energyeefic.entity.OiShipTask;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository("imoStdrptRepository")
public interface ImoStdrptRepository extends PagingAndSortingRepository<ImoStdrpt, String>, JpaSpecificationExecutor<ImoStdrpt> {
    @Query(value = "SELECT * from IMO_STDRPT WHERE IS_DELETE=0 AND SHIP_ID=?1 AND " +
            "START_TM between to_date(?2,'yyyy-mm-dd hh24:mi:ss') and to_date(?3,'yyyy-mm-dd hh24:mi:ss') AND END_TM between to_date(?4,'yyyy-mm-dd hh24:mi:ss') and to_date(?5,'yyyy-mm-dd hh24:mi:ss')", nativeQuery = true)
    List<ImoStdrpt> findByShipIdAndIsDeleteAndStartTmBetweenAndEndTmBetween(String shipId, String startTime1, String endTime1, String startTime2, String endTime2);

    ImoStdrpt findByIdAndIsDelete(String imoRptId, Integer isDelete);

    @Query(value = "SELECT * from IMO_STDRPT WHERE IS_DELETE = 0 AND \"ID\" IN (?1)", nativeQuery = true)
    List<ImoStdrpt> findByIds(List<String> imoRptIds);

    @Query(value = "SELECT \"ID\" from IMO_STDRPT WHERE IS_DELETE = 0 AND REC_STATUS = 2 AND COUNTRY_FLAG = :code AND \n" +
            "START_TM BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd') AND END_TM BETWEEN \"TO_DATE\"(:startTime, 'yyyy-mm-dd') AND \"TO_DATE\"(:endTime, 'yyyy-mm-dd')", nativeQuery = true)
    List<String> findByAndCountryFlag(@Param("code") String code, @Param("startTime") String startTime, @Param("endTime") String endTime);
    @Query(value = "SELECT \"ID\" from IMO_STDRPT WHERE IS_DELETE = 0 AND COUNTRY_FLAG = :code", nativeQuery = true)
    List<String> findByAndCountryFlag(@Param("code") String code);
}
