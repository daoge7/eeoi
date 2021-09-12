package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String>, JpaSpecificationExecutor<Evaluation> {
    @Query(value = "SELECT * from DIC_EVALUATION WHERE IS_DELETE = 0",nativeQuery = true)
    List<Evaluation> findAllByIsDelete();
}
