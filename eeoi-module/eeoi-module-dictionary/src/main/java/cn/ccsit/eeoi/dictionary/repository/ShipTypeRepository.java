package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipTypeRepository extends JpaRepository<ShipType, String>, JpaSpecificationExecutor<ShipType> {
    ShipType findByEuSptypeAndIsDelete(String euType,Integer isDelete);
    @Query(value = "select * from GC_SHIP_TYPE WHERE IS_DELETE = 0 AND SPCODE = ?1",nativeQuery = true)
    ShipType findShipType(String spcode);
    @Query(value = "SELECT SPCODE FROM gc_ship_type WHERE IS_DELETE=0 AND SPCODE IS NOT NULL AND (\"UPPER\"(\"REPLACE\"(SPTYPE, ' ',''))=\"UPPER\"(\"REPLACE\"(:spType, ' ','')) OR \"UPPER\"(\"REPLACE\"(CSPTYPE, ' ',''))=\"UPPER\"(\"REPLACE\"(:spType, ' ','')) OR \"UPPER\"(\"REPLACE\"(IMO_SPTYPE, ' ',''))=\"UPPER\"(\"REPLACE\"(:spType, ' ','')) OR \"UPPER\"(\"REPLACE\"(EU_SPTYPE, ' ',''))=\"UPPER\"(\"REPLACE\"(:spType, ' ','')))",nativeQuery = true)
    List<String> findShipTypeCodes(@Param("spType") String spType);
}
