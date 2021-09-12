package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.InterfaceRec;
import cn.ccsit.eeoi.energyeefic.entity.OiShipTask;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("interfaceRepository")
public interface InterfaceRepository extends PagingAndSortingRepository<InterfaceRec,String> ,JpaSpecificationExecutor<InterfaceRec> {

}
