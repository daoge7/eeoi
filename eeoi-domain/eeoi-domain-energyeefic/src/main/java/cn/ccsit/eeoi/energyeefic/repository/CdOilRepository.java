package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.BdnOil;
import cn.ccsit.eeoi.energyeefic.entity.CdOil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("cdOilRepository")
public interface CdOilRepository extends PagingAndSortingRepository<CdOil,String> ,JpaSpecificationExecutor<CdOil> {

}
