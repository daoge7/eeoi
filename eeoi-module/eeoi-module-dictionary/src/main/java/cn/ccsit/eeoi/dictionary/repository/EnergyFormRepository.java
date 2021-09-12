package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.EnergyForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyFormRepository extends JpaRepository<EnergyForm, String>, JpaSpecificationExecutor<EnergyForm> {
}
