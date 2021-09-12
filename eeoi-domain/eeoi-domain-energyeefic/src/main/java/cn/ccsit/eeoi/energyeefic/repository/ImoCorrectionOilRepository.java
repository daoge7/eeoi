package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.BdnOil;
import cn.ccsit.eeoi.energyeefic.entity.ImoCorrectionOil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("imoCorrectionOilRepository")
public interface ImoCorrectionOilRepository extends PagingAndSortingRepository<ImoCorrectionOil,String> ,JpaSpecificationExecutor<ImoCorrectionOil> {

}
