package cn.ccsit.eeoi.energyeefic.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.energyeefic.dto.*;
import cn.ccsit.eeoi.energyeefic.service.MrvDataService;
import cn.ccsit.eeoi.energyeefic.vo.DownLoadDataVo;
import cn.ccsit.eeoi.energyeefic.vo.EuRptVo;
import cn.ccsit.eeoi.energyeefic.vo.LibyaDownLoadVo;
import cn.ccsit.eeoi.energyeefic.webService.ImoReportClient;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/mrv")
public class MrvController {
    @Autowired
    private MrvDataService mrvDataService;
    @Autowired
    private ImoReportClient imoReportClient;

    @PostMapping("/dataAssessment")
    public ResultVo dataAssessment(@RequestBody MrvDataAssessmentDto mrvDataAssessmentDto) {
        return mrvDataService.getDataEvaluation(mrvDataAssessmentDto);
    }

    @PostMapping("/segmentMonitoring")
    public ResultVo segmentMonitoring(@RequestBody MrvDataAssessmentDto mrvDataAssessmentDto) {
        return mrvDataService.getSegmentMonitoring(mrvDataAssessmentDto);
    }

    @PostMapping("/downloadDataAssessment")
    public void downLoadDataAssessment(@RequestBody DownLoadDataVo downLoadDataVo, HttpServletResponse response) {
        mrvDataService.downLoadDataAssessment(downLoadDataVo.getMrvDataAssessmentVos(), response);
    }

    @PostMapping("/downloadSegmentMonitoring")
    public void downloadSegmentMonitoring(@RequestBody DownLoadDataVo downLoadDataVo, HttpServletResponse response) {
        mrvDataService.downLoadSegmentMonitoring(downLoadDataVo.getSegmentMonitoringVos(), response);
    }

    @PostMapping("/getImoStdRpts")
    public ResultVo getImoStdRpt(@RequestBody ImoDcsRptManagerDto imoDcsRptManagerDto) {
        return mrvDataService.dcsPortManager(imoDcsRptManagerDto);
    }

    @PostMapping("/imoStdRptStatusTag")
    public ResultVo imoStdRptStatusTag(@RequestBody ImoStdRptStatusTagDto imoStdRptStatusTagDto) {
        return mrvDataService.imoStdRptStatusTag(imoStdRptStatusTagDto);
    }

    @PostMapping("/imoLockOrUnlock")
    public ResultVo lockOrUnlock(@RequestBody ImoStdRptStatusTagDto imoStdRptStatusTagDto) {
        return mrvDataService.lockOrUnlock(imoStdRptStatusTagDto);
    }

    @PostMapping("/deleteImoStdRpt")
    public ResultVo deleteImoStdRpt(@RequestBody ImoStdRptStatusTagDto imoStdRptStatusTagDto) {
        return mrvDataService.deleteImoStdRpt(imoStdRptStatusTagDto);
    }

    @GetMapping("/getDcsPeriodTime")
    public ResultVo getDcsPeriodTime(@RequestParam("shipId") String shipId, @RequestParam("year") String year, @RequestParam("rptType") String rptType) {
    	System.out.println("come in");
        return mrvDataService.getDcsPeriodTime(shipId, year, rptType);
    }

    @PostMapping("/generatorImoRpt")
    public ResultVo generatorImoRpt(@RequestBody GeneratorImoRptDto generatorImoRptDto) {
        return mrvDataService.generatorImoRpt(generatorImoRptDto);
    }

    @PostMapping("/saveImoRpt")
    public ResultVo saveImoRpt(@RequestBody SaveGeneratorDcsDto saveGeneratorDcsDto) {
        return mrvDataService.saveGeneratorDcs(saveGeneratorDcsDto);
    }

    @PostMapping("/saveManualDcs")
    public ResultVo saveManualDcs(@RequestBody SaveManuelDcsDto saveManuelDcsDto) {
        return mrvDataService.saveManualDcs(saveManuelDcsDto);
    }

    @PostMapping("/getManuleDcs")
    public ResultVo getManuleDcs(@RequestBody DownLoadDataVo downLoadDataVo) {
        return mrvDataService.getManuleDcs(downLoadDataVo.getImoRptId());
    }

    @PostMapping("/downLoadCollectionData")
    public void downLoadCollectionData(@RequestBody DownLoadDataVo downLoadDataVo, HttpServletResponse response) {
        mrvDataService.downLoadCollectionData(downLoadDataVo.getImoRptId(), null, response);
    }

    @PostMapping("/downLoadDcs")
    public void downLoadDcs(@RequestBody DownLoadDataVo downLoadDataVo, HttpServletResponse response) {
        String imoRptId = downLoadDataVo.getImoRptId();
        String type = downLoadDataVo.getType();
        mrvDataService.downLoadDcs(imoRptId, type, response);
    }

    @PostMapping("/downLoadBdnData")
    public void downLoadBdnData(@RequestBody DownLoadDataVo downLoadDataVo, HttpServletResponse response) {
        String imoRptId = downLoadDataVo.getImoRptId();
        mrvDataService.downLoadBdnData(imoRptId, response);
    }

    @PostMapping("/saveAndUpdatePeriodOil")
    public ResultVo saveAndUpdatePeriodOil(@RequestBody BeginOilDto beginOilDto) {
        return mrvDataService.saveAndUpdatePeriodOil(beginOilDto);
    }

    @PostMapping("/deletePeriodOil")
    public ResultVo deletePeriodOil(@RequestBody BeginOilDto beginOilDto) {
        return mrvDataService.deletePeriodOil(beginOilDto);
    }

    @PostMapping("/getPeriodOil")
    public ResultVo getPeriodOil(@RequestBody BeginOilDto beginOilDto) {
        return mrvDataService.getPeriodOil(beginOilDto);
    }

    @PostMapping("/lockOrUnlock")
    public ResultVo lockOrUnlock(@RequestBody BeginOilDto beginOilDto) {
        return mrvDataService.lockOrUnlock(beginOilDto);
    }

    @PostMapping("/generatorEuDcs")
    public ResultVo generatorEuDcs(@RequestBody GeneratorImoRptDto generatorImoRptDto) {
        return mrvDataService.generatorEuDcs(generatorImoRptDto);
    }

    @PostMapping("/saveEuDcs")
    public ResultVo saveEuDcs(@RequestBody EuRptVo euRptVo) {
        return mrvDataService.saveEuDcs(euRptVo);
    }

    @PostMapping("/lockOrUnlockEuDcs")
    public ResultVo lockOrUnlockEuDcs(@RequestBody EuRptVo euRptVo) {
        return mrvDataService.lockOrUnlockEuDcs(euRptVo);
    }

    @PostMapping("/deleteEuDcs")
    public ResultVo deleteEuDcs(@RequestBody EuRptVo euRptVo) {
        return mrvDataService.deleteEuDcs(euRptVo);
    }

    @PostMapping("/getEudcs")
    public ResultVo getEudcs(@RequestBody ImoDcsRptManagerDto imoDcsRptManagerDto) {
        return mrvDataService.getEudcs(imoDcsRptManagerDto);
    }

    @PostMapping("/downEuDcs")
    public void downEuDcs(@RequestBody EuRptVo euRptVo, HttpServletResponse response) {
        mrvDataService.downEuDcs(euRptVo, response);
    }

    @PostMapping("/downCmsa")
    public void downCmsa(@RequestBody CmsaRptDto cmsaRptDto, HttpServletResponse response) {
        mrvDataService.downCmsa(cmsaRptDto, response);
    }

    @RequestMapping("/imoReport")
    public ResultVo imoReport(@RequestBody String[] imoRptIds) {
        return mrvDataService.imoReport(imoRptIds);
    }

    @PostMapping("/imoReportLibya")
    public String imoReportLibya(@RequestBody LibyaDownLoadVo libyaDownLoadVo) {
        return mrvDataService.imoLibyaReports(libyaDownLoadVo);
    }

    @PostMapping("/singalShipYear")
    public ResultVo singalShipYear(@RequestBody EnergyEfficDto energyEfficDto) {
        return mrvDataService.singalShipYear(energyEfficDto);
    }

    @PostMapping("/shipComPanyYear")
    public ResultVo shipComPanyYear(@RequestBody EnergyEfficDto energyEfficDto) {
        return mrvDataService.shipComPanyYear(energyEfficDto);
    }

    @PostMapping("/downLoadSigalShip")
    public void downLoadSigalShip(@RequestBody EnergyEfficDto energyEfficDto, HttpServletResponse response) {
        mrvDataService.downLoadSigalShip(energyEfficDto, response);
    }

    @PostMapping("/downLoadComPanyShip")
    public void downLoadComPanyShip(@RequestBody EnergyEfficDto energyEfficDto, HttpServletResponse response) {
        mrvDataService.downLoadComPanyShip(energyEfficDto, response);
    }

    @PostMapping("/downLoadEnergyEfficencyTrendData")
    public void downLoadEnergyEfficencyTrendData(@RequestBody EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto, HttpServletResponse response) {
        mrvDataService.downLoadEnergyEfficencyTrendData(energyEfficiencyTrendAnalysisDto, response);
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam(value = "fileName", required = true) String fileName) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        imoReportClient.downloadFile(fileName, response);
    }

    @PostMapping("/getEnergyEfficencyTrend")
    public ResultVo getEnergyEfficencyTrend(@RequestBody EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto) {
        return mrvDataService.getEnergyEfficencyTrend(energyEfficiencyTrendAnalysisDto);
    }

    @PostMapping("/getEnergyEfficencyTrendData")
    public ResultVo getEnergyEfficencyTrendData(@RequestBody EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto) {
        return mrvDataService.getEnergyEfficencyTrendData(energyEfficiencyTrendAnalysisDto);
    }

    @PostMapping("/getCompareAnalysisData")
    public ResultVo getCompareAnalysisData(@RequestBody CompareAnalysisData compareAnalysisData) {
        return mrvDataService.getCompareAnalysisData(compareAnalysisData);
    }

    @PostMapping("/getCompareAnalysisDatas")
    public ResultVo getCompareAnalysisDatas(@RequestBody CompareAnalysisData compareAnalysisData) {
        return mrvDataService.getCompareAnalysisDatas(compareAnalysisData);
    }

    @PostMapping("/downLoadCompareAnalysisData")
    public void downLoadCompareAnalysisData(@RequestBody CompareAnalysisData compareAnalysisData, HttpServletResponse response) {
        mrvDataService.downLoadCompareAnalysisData(compareAnalysisData, response);
    }
    @PostMapping("/getEnergyEfficencyTrendMessage")
    public ResultVo getEnergyEfficencyTrendMessage(@RequestBody InformationDto informationDto) {
        return mrvDataService.getEnergyEfficencyTrendMessage(informationDto);
    }

}
