package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.CmsaRptOilCons;
import cn.ccsit.eeoi.energyeefic.entity.EuEmissionRpt;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("cmsaRptOilConsRepository")
public interface CmsaRptOilConsRepository extends PagingAndSortingRepository<CmsaRptOilCons,String> ,JpaSpecificationExecutor<CmsaRptOilCons> {
List<CmsaRptOilCons> findByRptIdAndIsDelete(String rptId,Integer isDelete);
}
