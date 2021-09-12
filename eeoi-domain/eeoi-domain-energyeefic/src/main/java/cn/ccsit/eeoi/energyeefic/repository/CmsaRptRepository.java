package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.CmsaRpt;
import cn.ccsit.eeoi.energyeefic.entity.CmsaRptOilCons;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("cmsaRptRepository")
public interface CmsaRptRepository extends PagingAndSortingRepository<CmsaRpt,String> ,JpaSpecificationExecutor<CmsaRpt> {
    CmsaRpt findByIdAndIsDelete(String id,Integer isDelete);

    List<CmsaRpt> findByIdInAndIsDelete(List<String> cmsaRptIds,Integer isDelete);
}
