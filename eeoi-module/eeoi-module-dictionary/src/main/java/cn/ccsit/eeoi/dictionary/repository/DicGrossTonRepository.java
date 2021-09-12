package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.DicGrossTon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DicGrossTonRepository extends JpaRepository<DicGrossTon, String>, JpaSpecificationExecutor<DicGrossTon> {
}
