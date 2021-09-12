package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.ImoOil;
import cn.ccsit.eeoi.energyeefic.entity.ImoStdrpt;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("imoOilRepository")
public interface ImoOilRepository extends PagingAndSortingRepository<ImoOil, String>, JpaSpecificationExecutor<ImoOil> {
    void deleteByRptId(String rptId);
}
