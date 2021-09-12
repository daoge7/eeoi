package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.Classificat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificatRepository extends JpaRepository<Classificat,String>, JpaSpecificationExecutor<Classificat> {
    Classificat findByAbbrnAndIsDelete(String abbrn,String isDelete);
    @Query(value = "SELECT * FROM GC_CLASSIFICAT WHERE IS_DELETE = 0 AND CODE=?1",nativeQuery = true)
    Classificat findByCode(Integer code);
}
