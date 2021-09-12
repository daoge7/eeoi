package cn.ccsit.eeoi.energyeefic.schedule;


import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.energyeefic.dto.DeleteVoyageListDto;
import cn.ccsit.eeoi.energyeefic.dto.GeneratorVoyageDto;
import cn.ccsit.eeoi.energyeefic.entity.OiTaskNotify;
import cn.ccsit.eeoi.energyeefic.repository.OiTaskNotifyRepository;
import cn.ccsit.eeoi.energyeefic.service.EnergyEefficService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class EnergyAsynTask {

    @Autowired
    private EnergyEefficService energyEefficService;

    @Autowired
    private OiTaskNotifyRepository oiTaskNotifyRepository;


    @Scheduled(cron = "${cron}")
    public void genVoyage() {
        List<OiTaskNotify> tasks = oiTaskNotifyRepository.fetchNeedProccessTask();
        for(OiTaskNotify task:tasks) {
            task.setStatus(1);
            oiTaskNotifyRepository.save(task);

            DeleteVoyageListDto deleteVoyageListDto = new DeleteVoyageListDto();
            List<GeneratorVoyageDto> generatorVoyageDtos = new ArrayList<>();
            GeneratorVoyageDto voyageDto = new GeneratorVoyageDto();
            voyageDto.setId(task.getTaskId());
            voyageDto.setVolageAndSegmentType(task.getSegmentType());
            generatorVoyageDtos.add(voyageDto);
            deleteVoyageListDto.setGeneratorVoyageDtos(generatorVoyageDtos);


            log.error("task:" + task.getTaskId() + " segemnt:" + task.getSegmentType());
            try{
                long startTime = System.currentTimeMillis();
                ResultVo result = energyEefficService.generatorVoyageInfoAsynchronous(deleteVoyageListDto);
                long used = System.currentTimeMillis() -  startTime;
                log.error("ret: " + result.isResult() + " msg:" + (String)result.getData());

                task.setStatus(result.isResult()? 2 : 3);
                task.setResult((String)result.getData());
                task.setUsedTm(used);
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                task.setStatus(3);
                task.setResult(e.getMessage());
            }finally {
                task.setFinishTm(new Date());
                oiTaskNotifyRepository.save(task);
            }
        }

     }
}
