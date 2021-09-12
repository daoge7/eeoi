package cn.ccsit.eeoi.ship.repository;


import cn.ccsit.eeoi.ship.entity.GcClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("gcClientRepository")
public interface GcClientRepository extends JpaRepository<GcClient, String>, JpaSpecificationExecutor<GcClient> {

    @Query("select u from GcClient u where  u.id=:id")
    public GcClient findGcClientById(@Param("id") String id);

    @Query("select u from GcClient u where  u.CUid=:cUid")
    public GcClient getGcClientByCUid(@Param("cUid") String cUid);

    @Query(value = "SELECT * from GC_CLIENT WHERE IS_DELETE = 0 AND CODE=?1 ORDER BY \"ID\"", nativeQuery = true)
    List<GcClient> findByCode(String code);

    @Query(value = "SELECT * from GC_CLIENT WHERE IS_DELETE = 0 AND CODE=?1 AND C_UID IS NOT NULL and IACS IS NOT NULL", nativeQuery = true)
    List<GcClient> findByCodeList(String code);

    @Query(value = "select * from GC_CLIENT WHERE CODE = ?1 AND IS_DELETE = 0 ORDER BY \"ID\"", nativeQuery = true)
    List<GcClient> findByCodeAndIsDelete(String code);

    @Query(value = "select * from GC_CLIENT WHERE CODE = ?1 AND IS_DELETE = 0 ORDER BY \"ID\"", nativeQuery = true)
    List<GcClient> findListByCodeAndIsDelete(String code);

    @Query(value = "SELECT code from GC_CLIENT WHERE IACS = ?1 AND IS_DELETE = 0", nativeQuery = true)
    List<String> findByIacs(String iacs);

    @Query(value = "SELECT id from GC_CLIENT WHERE IACS = ?1 AND IS_DELETE = 0", nativeQuery = true)
    List<String> findIdByIacs(String iacs);

    @Query(value = "SELECT * from GC_CLIENT WHERE id = ?1 AND IS_DELETE = 0", nativeQuery = true)
    GcClient findByIdAndIsDelete(String iacs);

    @Query(value = "select * from GC_CLIENT where id = ?1 and IS_DELETE = 0", nativeQuery = true)
    public GcClient findGcClientByIdAndIsdelete(String id);
//todo
    @Query(value = "select * from GC_CLIENT WHERE code = ?1 AND IS_DELETE = 0 ORDER BY \"ID\"", nativeQuery = true)
    public List<GcClient> findByCuidAndCode(String code);
}
