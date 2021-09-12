package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.RawVoyageSpecCons;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository("rawVoyageSpecConsRepository")
public interface RawVoyageSpecConsRepository extends PagingAndSortingRepository<RawVoyageSpecCons,String> ,JpaSpecificationExecutor<RawVoyageSpecCons> {
    RawVoyageSpecCons findByIdAndIsDelete(String id,Integer isDelete);
}
