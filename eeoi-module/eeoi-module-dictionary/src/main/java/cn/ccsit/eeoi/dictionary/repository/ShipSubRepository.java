package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.ShipSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipSubRepository extends JpaRepository<ShipSub, String>, JpaSpecificationExecutor<ShipSub> {
    List<ShipSub> findAllByShipType(String type);
}
