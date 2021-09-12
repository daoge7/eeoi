package cn.ccsit.eeoi.energyeefic.service;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.eeoi.energyeefic.entity.PortArrDeptInfo;
import cn.ccsit.eeoi.energyeefic.repository.PortArrDeptRepository;
import cn.ccsit.eeoi.energyeefic.repository.RawVoyagePortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PortArrDeptServiceImp {
    @Autowired
    PortArrDeptRepository portArrDeptService;

    public List<PortArrDeptInfo> getPortArrDept(LocalDateTime startTime, LocalDateTime endTime, String shipId){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yearBegin  = LocalDateTime.of(now.getYear(),1,1,0,0,0);
        if(startTime == null){
            startTime = yearBegin;
        }
        if(endTime== null){
            endTime = now;
        }
        if(startTime.isAfter(endTime)){
            throw new ExplicitException("查询开始时间不得晚于结束时间");
        }
        String startTimeStr = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endTimeStr = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return portArrDeptService.getPortArrDept(startTimeStr, endTimeStr, shipId);
//          List<PortArrDeptVo> retList = em
//                .createNamedQuery("PortArrDeptVo")
//                .setParameter("startTime",startTimeStr)
//                .setParameter("endTime",endTimeStr)
//                .setParameter("shipId", shipId)
//                .getResultList();
//          return retList;
//        return portArrDeptService.getPortArrDept(startTimeStr, endTimeStr, shipId);
    }


}
