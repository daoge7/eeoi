package cn.ccsit.eeoi.ship.ssmisrepository;

import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipInfoEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ssmisOiShipInfoRepository")
public interface SsmisOiShipInfoRepository extends PagingAndSortingRepository<SsmisOiShipInfoEntity, String>, JpaSpecificationExecutor<SsmisOiShipInfoEntity> {
    @Query(value = "select * from (\n" +
            "SELECT\n" +
            "ROWNUM AS rn,t1.*\n" +
            "FROM\n" +
            "  SSMISHOST.SM_SHIP t1\n" +
            "LEFT JOIN SSMISHOST.SM_SHIP_EQUIP_ATTR t2 ON T1.CCSNO = T2.CCSNO\n" +
            "AND T2.ATTRVALUE IS NOT NULL\n" +
            "AND T2.SM_SYS_TREE_ID = '7692'\n" +
            "where T1.IS_DELETE=1 and (T1.DOC_CODE=?1 or  T1.IMONO =?2) AND T1.IMONO IS  NOT NULL and ROWNUM<=?3\n" +
            ") tabss WHERE TABSS.RN>?4", nativeQuery = true)
    List<SsmisOiShipInfoEntity> findByCodeAndImono(String docCode, String imono, Integer endPage, Integer startEnd);

    @Query(value = "SELECT \"COUNT\"(1) AS total\n" +
            "FROM\n" +
            "  SSMISHOST.SM_SHIP t1\n" +
            "LEFT JOIN SSMISHOST.SM_SHIP_EQUIP_ATTR t2 ON T1.CCSNO = T2.CCSNO\n" +
            "AND T2.ATTRVALUE IS NOT NULL\n" +
            "AND T2.SM_SYS_TREE_ID = '7692'\n" +
            "where T1.IS_DELETE=1 and (T1.DOC_CODE=?1 or  T1.IMONO = ?2) AND T1.IMONO IS  NOT NULL", nativeQuery = true)
    Integer getCount(String docCode, String imono);

    @Query(value = "SELECT\n" +
            "  t1.*\n" +
            "FROM\n" +
            "  SSMISHOST.SM_SHIP t1\n" +
            "LEFT JOIN SSMISHOST.SM_SHIP_EQUIP_ATTR t2 ON T1.CCSNO = T2.CCSNO\n" +
            "AND T2.ATTRVALUE IS NOT NULL\n" +
            "AND T2.SM_SYS_TREE_ID = '7692'\n" +
            "\n" +
            "where T1.IS_DELETE=1 and T1.IMONO in ?1", nativeQuery = true)
    List<SsmisOiShipInfoEntity> findByCcsNos(List<String> ccsnos);

        @Query(value = "SELECT\n" +
            "  T1.*\n" +
            "FROM\n" +
            "  SSMISHOST.SM_SHIP t1\n" +
            "LEFT JOIN SSMISHOST.SM_SHIP_EQUIP_ATTR t2 ON T1.CCSNO = T2.CCSNO\n" +
            "AND T2.ATTRVALUE IS NOT NULL\n" +
            "AND T2.SM_SYS_TREE_ID = '7692'\n" +
            "\n" +
            "where T1.IS_DELETE=1 and t1.IMONO IS NOT null and T1.MOD_TIME IS NOT NULL AND t1.MOD_TIME >= trunc(sysdate - 3) \n" +
            "AND T1.IMONO <> '-' \n" +
            "AND t1.IMONO<> '---' \n" +
            "AND T1.IMONO <> '--' \n" +
            "AND T1.IMONO <> '-----' \n" +
            "AND T1.IMONO <> '- - -'\n" +
            "AND T1.IMONO <> '----'\n" +
            "AND T1.IMONO <> 'N.A.'", nativeQuery = true)
    List<SsmisOiShipInfoEntity> findAllSsmis();
    @Query(value = "select * from (\n" +
            "SELECT\n" +
            "ROWNUM AS rn,t1.*\n" +
            "FROM\n" +
            "  SSMISHOST.SM_SHIP t1\n" +
            "LEFT JOIN SSMISHOST.SM_SHIP_EQUIP_ATTR t2 ON T1.CCSNO = T2.CCSNO\n" +
            "AND T2.ATTRVALUE IS NOT NULL\n" +
            "AND T2.SM_SYS_TREE_ID = '7692'\n" +
            "where T1.IS_DELETE=1 AND T1.IMONO IS  NOT NULL AND T1.IMONO <> '-' \n" +
            "AND t1.IMONO<> '---' \n" +
            "AND T1.IMONO <> '--' \n" +
            "AND T1.IMONO <> '-----' \n" +
            "AND T1.IMONO <> '- - -'\n" +
            "AND T1.IMONO <> '----'\n" +
            "AND T1.IMONO <> 'N.A.' and ROWNUM<=?1\n" +
            ") tabss WHERE TABSS.RN>?2", nativeQuery = true)
    List<SsmisOiShipInfoEntity> findAllSsmisPage(Integer endPage, Integer startEnd);

}
