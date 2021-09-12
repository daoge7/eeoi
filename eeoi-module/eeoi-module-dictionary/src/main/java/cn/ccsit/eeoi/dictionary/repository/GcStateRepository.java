package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.GcState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GcStateRepository extends JpaRepository<GcState, String>, JpaSpecificationExecutor<GcState> {

    GcState findTopByEnBrief(String enBrief);

    GcState findTopByCnBrief(String cnBrief);
    @Query(value = "SELECT THREE_CODE FROM gc_state WHERE IS_DELETE=0 AND THREE_CODE IS NOT NULL AND (\"UPPER\"(\"REPLACE\"(CN_BRIEF, ' ',''))=\"UPPER\"(\"REPLACE\"(:flag, ' ','')) OR \"UPPER\"(\"REPLACE\"(EN_BRIEF, ' ',''))=\"UPPER\"(\"REPLACE\"(:flag, ' ','')) OR \"UPPER\"(\"REPLACE\"(CN_NAME, ' ',''))=\"UPPER\"(\"REPLACE\"(:flag, ' ','')) OR \"UPPER\"(\"REPLACE\"(EN_NAME, ' ',''))=\"UPPER\"(\"REPLACE\"(:flag, ' ','')) OR \"UPPER\"(\"REPLACE\"(THREE_CODE, ' ',''))=\"UPPER\"(\"REPLACE\"(:flag, ' ','')))",nativeQuery = true)
    List<String> findThreeCode(@Param("flag") String flag);
    @Query(value = "SELECT * from GC_STATE WHERE IS_DELETE = 0 AND THREE_CODE = ?1",nativeQuery = true)
    GcState findFlag(String flagCode);


}
