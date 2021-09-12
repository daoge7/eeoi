package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.BdnOil;
import cn.ccsit.eeoi.energyeefic.entity.ImoOil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("bdnOilRepository")
public interface BdnOilRepository extends PagingAndSortingRepository<BdnOil,String> ,JpaSpecificationExecutor<BdnOil> {

}
