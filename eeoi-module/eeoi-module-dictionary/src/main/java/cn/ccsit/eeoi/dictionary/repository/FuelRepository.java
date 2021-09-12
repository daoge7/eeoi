package cn.ccsit.eeoi.dictionary.repository;

import cn.ccsit.eeoi.dictionary.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, String>, JpaSpecificationExecutor<Fuel> {
    @Query(value = "SELECT FUEL_CODE FROM DIC_FUEL WHERE IS_DELETE = 0",nativeQuery = true)
    List<String> findFuelCode();
    @Query(value = "SELECT * FROM DIC_FUEL WHERE IS_DELETE = 0",nativeQuery = true)
    List<Fuel> findAll();
    @Query(value = "SELECT * FROM DIC_FUEL WHERE IS_DELETE = 0 AND FUEL_CODE=?1",nativeQuery = true)
    Fuel findByFuelCode(String fuelCode);
}
