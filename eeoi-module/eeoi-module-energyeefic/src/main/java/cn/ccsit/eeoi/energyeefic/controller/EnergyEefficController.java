package cn.ccsit.eeoi.energyeefic.controller;

import cn.ccsit.common.vo.ResultErrorVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.energyeefic.dto.*;
import cn.ccsit.eeoi.energyeefic.entity.CmsaRpt;
import cn.ccsit.eeoi.energyeefic.entity.OiTaskNotify;
import cn.ccsit.eeoi.energyeefic.repository.OiShipTaskRepository;
import cn.ccsit.eeoi.energyeefic.repository.OiTaskNotifyRepository;
import cn.ccsit.eeoi.energyeefic.service.EnergyEefficService;
import cn.ccsit.eeoi.energyeefic.vo.DownLoadDataVo;
import cn.ccsit.eeoi.energyeefic.vo.MrvDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/energyEfficiency")
@RestController
@Slf4j
public class EnergyEefficController {
    @Autowired
    private EnergyEefficService energyEefficService;

    @Autowired
    private OiShipTaskRepository oiShipTaskRepository;

    @Autowired
    private OiTaskNotifyRepository oiTaskNotifyRepository;

    public static final String MRV_DATA = "mrvDataVo";
    public static final String REAPORT_DTO = "reaportDtos";

    @GetMapping("/voyageList")
    public ResultVo getVoyageList(VoyageListDto voyageListDto) {
        return energyEefficService.getVolageList(voyageListDto);
    }

    @GetMapping("/getRawVoyagePortByVoyageId")
    public ResultVo getRawVoyagePortByVoyageId(@RequestParam("voyageId") String voyageId, @RequestParam Integer voyageType) {
        return energyEefficService.getRawVoyagePortByVoyageId(voyageId, voyageType);
    }

    @GetMapping("/voyageListNew")
    public ResultVo getNewVolageList(VoyageListDto voyageListDto) {
        return energyEefficService.getNewVolageList(voyageListDto);
    }

    @GetMapping("/getVoyageInfo")
    public ResultVo getVoyageDeitailInfo(@RequestParam String taskId) {
        return energyEefficService.getVoyageDeitailInfo(taskId);
    }

    @GetMapping("/getPortListByTaskId")
    public ResultVo getPortListByTaskId(@RequestParam String taskId) {
        return energyEefficService.getPortListByTaskId(taskId);
    }

    @GetMapping("/getSegementIdByTaskId")
    public ResultVo getSegementIdByTaskId(@RequestParam String taskId) {
        return energyEefficService.getSegementIdByTaskId(taskId);
    }

    @GetMapping("/getPortList")
    public ResultVo getVolagePortList(VoyagePortListDto voyagePortListDto) {
        return energyEefficService.getVolagePortList(voyagePortListDto);
    }

    @PostMapping("/createAndUpdatePortAndAnchorOrDrifting")
    public ResultVo createAndupdatePortInfo(@Validated @RequestBody VoyagePortDto voyagePortDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            return new ResultErrorVo(fieldError.getDefaultMessage());
        }
        return energyEefficService.createAndUpdatePortInfo(voyagePortDto);
    }

    @PostMapping("/createAndUpdateIceClassAndRescue")
    public ResultVo createAndUpdateIceClassAndRescue(@RequestBody RawVoyageSpecDto rawVoyageSpecDto) {
        return energyEefficService.createAndUpdateIceClassAndRescue(rawVoyageSpecDto);
    }

    @PostMapping("/deleteVolageList")
    public ResultVo deleteVolageList(@RequestBody DeleteVoyageListDto deleteVoyageListDto) {
        return energyEefficService.deleteVolageList(deleteVoyageListDto);
    }

    @PostMapping("/deleteVolagePortList")
    public ResultVo deleteVolagePortList(@RequestBody DeleteVoyageListDto deleteVoyageListDto) {
        return energyEefficService.deleteVolagePortList(deleteVoyageListDto);
    }

    @PostMapping("/deleteVolageIceClassAndRescueList")
    public ResultVo deleteVolageIceClassAndRescueList(@RequestBody DeleteVoyageListDto deleteVoyageListDto) {
        return energyEefficService.deleteVolageIceClassAndRescueList(deleteVoyageListDto);
    }

    @PostMapping("/lockVoyageSatus")
    public ResultVo lockVoyageSatus(@RequestBody UpdateVoyageRecStatusDto updateVoyageRecStatusDto) {
        return energyEefficService.updateVolageStatus(updateVoyageRecStatusDto);
    }

    @PostMapping("/generatorVoyageInfo")
    public ResultVo generatorVolage(@RequestBody DeleteVoyageListDto deleteVoyageListDto) {
        return energyEefficService.generatorVolageInfo(deleteVoyageListDto);
    }

    /**
     * 异步生成航次航段接口
     *
     * @param deleteVoyageListDto
     * @return
     */
    @PostMapping("/generatorVoyageInfoAsynchronous")
    public ResultVo generatorVoyageInfoAsynchronous(@RequestBody DeleteVoyageListDto deleteVoyageListDto) {
        return energyEefficService.generatorVoyageInfoAsynchronous(deleteVoyageListDto);
    }

    @PostMapping("/voyageData2CCS")
    public MrvDataVo reportMrvData(@RequestParam(value = "voyageData") String voyageData, @RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "token") String token, HttpServletRequest request, HttpServletResponse response) {
        //上报港口数据
        Map<String, Object> map = new HashMap<>();
        MrvDataVo mrvDataVo = null;
        try {
            map = energyEefficService.reportMrvData(voyageData, timestamp, token, request, response);
            mrvDataVo = (MrvDataVo) map.get(MRV_DATA);
        } catch (Exception e) {
            log.error(e.getMessage());
            mrvDataVo = new MrvDataVo();
            mrvDataVo.setRespcode("10001");
            mrvDataVo.setRespMsg("Interface request failed, data imported failed ,数据导入失败");
        }
        List<ReaportGenerateVoyageInfoDto> reaportGenerateVoyageInfoDtos = (List<ReaportGenerateVoyageInfoDto>) map.get(REAPORT_DTO);
        //生成航段数据
        for (ReaportGenerateVoyageInfoDto reaportGenerateVoyageInfoDto : reaportGenerateVoyageInfoDtos) {
            String shipId = reaportGenerateVoyageInfoDto.getOiShipInfo().getId();
            String taskNu = reaportGenerateVoyageInfoDto.getTaskNu();
            List<String> listByShipIsDelete = oiShipTaskRepository.findListByShipIsDelete(shipId,
                    taskNu);
            String byTaskAndShipIsDelete = null;
            if (listByShipIsDelete != null && listByShipIsDelete.size() > 0) {
                byTaskAndShipIsDelete = listByShipIsDelete.get(0);
            }
            OiTaskNotify byShipIdAndTaskId = oiTaskNotifyRepository.findByShipIdAndTaskId(shipId, byTaskAndShipIsDelete);
            if (byShipIdAndTaskId == null) {
                byShipIdAndTaskId = new OiTaskNotify(shipId, byTaskAndShipIsDelete, 0, 0);
                byShipIdAndTaskId.setIsDelete(0);
            } else {
                byShipIdAndTaskId.setStatus(0);
            }
            oiTaskNotifyRepository.save(byShipIdAndTaskId);
        }
//        energyEefficService.reaportGenerateVoyageInfo(reaportGenerateVoyageInfoDtos);
        return mrvDataVo;
    }


    @PostMapping("/voyageDataDelete2CCS")
    public MrvDataVo reaport(@RequestParam(value = "voyageData") String voyageData,
                             @RequestParam(value = "timestamp") String timestamp,
                             @RequestParam(value = "token") String token, HttpServletRequest request, HttpServletResponse response) {
        return energyEefficService.reportdDeleteMrvData(voyageData, timestamp, token, request, response);
    }

    @GetMapping("/getFuelType")
    public ResultVo getFuelType(@RequestParam(value = "shipId", required = false) String shipId, @RequestParam(value = "periodTime", required = false) Date periodTime) {
        return energyEefficService.getFuelType(shipId, periodTime);
    }

    @GetMapping("/getFuelTypeApp")
    public ResultVo getFuelTypeApp(@RequestParam String shipId) {
        return energyEefficService.getFuelTypeApp(shipId);
    }

    @GetMapping("/getVoyageDetailInfo")
    public ResultVo getVoyageDetailInfo(@RequestParam("voyageId") String voyageId) {
        return energyEefficService.getVoyageDetailInfo(voyageId);
    }

    @GetMapping("/getVoyageDetailInfoApp")
    public ResultVo getVoyageDetailInfoApp(@RequestParam("voyageId") String voyageId) {
        return energyEefficService.getVoyageDetailInfoApp(voyageId);
    }

    @GetMapping("/getTasksByShipId")
    public ResultVo getTasksByShipId(@RequestParam("shipId") String shipId) {
        return energyEefficService.getTasksByShipId(shipId);
    }

    @PostMapping("/getCmsaRpt")
    public ResultVo getCmsa(@RequestBody CmsaRptDto cmsaRptDto) {
        return energyEefficService.getCmsaRpt(cmsaRptDto);
    }

    @PostMapping("/getCmsaStdRpt")
    public ResultVo getCmsaStdRpt(@RequestBody ImoDcsRptManagerDto imoDcsRptManagerDto) {
        return energyEefficService.cmsaRptManager(imoDcsRptManagerDto);
    }

    @PostMapping("/downLoadCmsaRpt")
    public void downLoadCmsaRpt(@RequestBody CmsaRptDto cmsaRptDto, HttpServletResponse response) {
        energyEefficService.downLoadCmsaRpt(cmsaRptDto, response);
    }

    @PostMapping("/deleteCmsaRpt")
    public ResultVo deleteCmsaRpt(@RequestBody CmsaRptDto cmsaRptDto) {
        return energyEefficService.deleteCmsaRpt(cmsaRptDto.getId());
    }

    @PostMapping("/lockOrUnLockCmsaRpt")
    public ResultVo lockOrUnLockCmsaRpt(@RequestBody CmsaRptDto cmsaRptDto) {
        return energyEefficService.lockOrUnlock(cmsaRptDto);
    }

    @PostMapping("/cmsaRptStatusTag")
    public ResultVo cmsaRptStatusTag(@RequestBody CmsaRptDto cmsaRptDto) {
        return energyEefficService.cmsaRptStatusTag(cmsaRptDto);
    }

    @PostMapping("/checkShipEnergyInfo")
    public ResultVo checkShipEnergyInfo(String shipId) {
        return energyEefficService.checkShipEnergyInfo(shipId);
    }

}
