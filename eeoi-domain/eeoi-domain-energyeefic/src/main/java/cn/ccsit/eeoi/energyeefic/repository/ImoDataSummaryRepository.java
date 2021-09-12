package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.ImoDataSummary;
import cn.ccsit.eeoi.energyeefic.entity.ImoStdrptAdjust;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("imoDataSummaryRepository")
public interface ImoDataSummaryRepository extends PagingAndSortingRepository<ImoDataSummary,String> ,JpaSpecificationExecutor<ImoDataSummary> {

}
