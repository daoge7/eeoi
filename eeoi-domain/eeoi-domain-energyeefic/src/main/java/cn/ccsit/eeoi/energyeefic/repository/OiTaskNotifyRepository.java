package cn.ccsit.eeoi.energyeefic.repository;


import cn.ccsit.eeoi.energyeefic.entity.BdnOil;
import cn.ccsit.eeoi.energyeefic.entity.OiTaskNotify;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("oiTaskNotifyRepository")
public interface OiTaskNotifyRepository extends PagingAndSortingRepository<OiTaskNotify,String> ,JpaSpecificationExecutor<OiTaskNotify> {
    /**
     * 根据船舶id和航次id查询记录
     * @return
     */
    @Query(value = "SELECT * FROM OI_TASK_NOTIFY WHERE is_delete = 0 AND ship_id = ?1 AND task_id = ?2 and status=0",nativeQuery = true)
    OiTaskNotify findByShipIdAndTaskId(String shipId,String taskId);

    @Query(value="SELECT * FROM OI_TASK_NOTIFY where is_delete = 0 and status =0",nativeQuery = true)
    List<OiTaskNotify> fetchNeedProccessTask();

}
