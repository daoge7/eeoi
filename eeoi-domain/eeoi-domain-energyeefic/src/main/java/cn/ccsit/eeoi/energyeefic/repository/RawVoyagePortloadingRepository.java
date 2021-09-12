package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePortloading;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePortoil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("rawVoyagePortloadingRepository")
public interface RawVoyagePortloadingRepository extends PagingAndSortingRepository<RawVoyagePortloading,String> ,JpaSpecificationExecutor<RawVoyagePortloading> {
    RawVoyagePortloading findByIdAndIsDelete(String id,Integer isDelete);
    List<RawVoyagePortloading> findByPortinfoIdAndIsDelete(String portInfoId, Integer isDelete);
}
