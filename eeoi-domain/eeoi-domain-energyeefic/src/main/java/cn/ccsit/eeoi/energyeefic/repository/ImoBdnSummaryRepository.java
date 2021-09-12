package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.ImoBdnSummary;
import cn.ccsit.eeoi.energyeefic.entity.ImoDataSummary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("imoBdnSummaryRepository")
public interface ImoBdnSummaryRepository extends PagingAndSortingRepository<ImoBdnSummary,String> ,JpaSpecificationExecutor<ImoBdnSummary> {

}
