package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.GcCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GcCityRepository extends JpaRepository<GcCity,String>, JpaSpecificationExecutor<GcCity> {
    @Query(value = "SELECT * FROM GC_CITY WHERE IS_DELETE = 0 AND  CODE = ?1 ",nativeQuery = true)
    GcCity findByCode(String code);
}
