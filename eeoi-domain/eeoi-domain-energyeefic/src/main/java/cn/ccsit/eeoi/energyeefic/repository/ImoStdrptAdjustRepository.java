package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.ImoStdrptAdjust;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("imoStdrptAdjustRepository")
public interface ImoStdrptAdjustRepository extends PagingAndSortingRepository<ImoStdrptAdjust,String> ,JpaSpecificationExecutor<ImoStdrptAdjust> {
    ImoStdrptAdjust findByRptIdAndIsDelete(String rptId,Integer isDelete);
}
