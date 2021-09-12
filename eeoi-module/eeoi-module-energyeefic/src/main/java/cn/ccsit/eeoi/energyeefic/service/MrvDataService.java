package cn.ccsit.eeoi.energyeefic.service;


import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.energyeefic.dto.*;
import cn.ccsit.eeoi.energyeefic.entity.BeginPeriodOil;
import cn.ccsit.eeoi.energyeefic.entity.InterfaceRec;
import cn.ccsit.eeoi.energyeefic.entity.OiShipTask;
import cn.ccsit.eeoi.energyeefic.entity.OiShipVoyage;
import cn.ccsit.eeoi.energyeefic.vo.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface MrvDataService {
    /**
     * 获取数据评估
     *
     * @param mrvDataAssessmentDto
     * @return
     */
    ResultVo getDataEvaluation(MrvDataAssessmentDto mrvDataAssessmentDto);

    /**
     * 获取船舶的历史数据
     *
     * @param minAvgSpeed
     * @param maxAvgSpeed
     * @param time
     * @param shipId
     * @param isRu
     * @return
     * @throws ParseException
     */
    List<OiShipVoyage> getOiShipVoyagesHistory(BigDecimal minAvgSpeed, BigDecimal maxAvgSpeed, String time, String shipId, Integer isRu) throws ParseException;

    /**
     * 获取航段监测数据
     *
     * @param mrvDataAssessmentDto
     * @return
     */
    ResultVo getSegmentMonitoring(MrvDataAssessmentDto mrvDataAssessmentDto);

    /**
     * 导出数据评估Excel
     *
     * @param mrvDataAssessmentVos
     * @param response
     */
    void downLoadDataAssessment(List<MrvDataAssessmentVo> mrvDataAssessmentVos, HttpServletResponse response);

    /**
     * 导出航段监测Excel
     *
     * @param segmentMonitoringVos
     * @param response
     */
    void downLoadSegmentMonitoring(List<SegmentMonitoringVo> segmentMonitoringVos, HttpServletResponse response);

    /**
     * 查询imo报告状态
     *
     * @param imoDcsRptManagerDto
     * @return
     */
    ResultVo dcsPortManager(ImoDcsRptManagerDto imoDcsRptManagerDto);

    /**
     * 状态标记
     *
     * @param imoStdRptStatusTagDto
     * @return
     */
    ResultVo imoStdRptStatusTag(ImoStdRptStatusTagDto imoStdRptStatusTagDto);

    /**
     * 解锁或者锁定报告
     *
     * @param imoStdRptStatusTagDto
     * @return
     */
    ResultVo lockOrUnlock(ImoStdRptStatusTagDto imoStdRptStatusTagDto);

    /**
     * 删除imo报告
     *
     * @param imoStdRptStatusTagDto
     * @return
     */
    ResultVo deleteImoStdRpt(ImoStdRptStatusTagDto imoStdRptStatusTagDto);

    /**
     * 获取可生成报告的时间段
     *
     * @param shipId
     * @param year
     * @return
     */
    ResultVo getDcsPeriodTime(String shipId, String year, String rptType);

    /**
     * 生成imo
     *
     * @param generatorImoRptDto
     * @return
     */
    ResultVo generatorImoRpt(GeneratorImoRptDto generatorImoRptDto);

    /**
     * 保存和编辑独立生成dcs报告
     *
     * @param saveGeneratorDcsDto
     * @return
     */
    ResultVo saveGeneratorDcs(SaveGeneratorDcsDto saveGeneratorDcsDto);

    /**
     * 保存和编辑手动填写的dcs报告
     *
     * @return
     */
    ResultVo saveManualDcs(SaveManuelDcsDto saveManuelDcsDto);

    /**
     * 导出中英文dcs报告
     *
     * @param imoRptId
     * @param type
     * @param response
     */
    void downLoadDcs(String imoRptId, String type, HttpServletResponse response);

    /**
     * 导出中英文collectionData报告
     *
     * @param imoRptId
     * @param type
     * @param response
     */
    void downLoadCollectionData(String imoRptId, String type, HttpServletResponse response);

    /**
     * 导出bdn报告
     *
     * @param imoRptId
     * @param response
     */
    void downLoadBdnData(String imoRptId, HttpServletResponse response);

    /**
     * 手动报告
     *
     * @param imoRptId
     * @return
     */
    ResultVo getManuleDcs(String imoRptId);

    /**
     * 增加期初量
     *
     * @param beginOilDto
     * @return
     */
    ResultVo saveAndUpdatePeriodOil(BeginOilDto beginOilDto);

    /**
     * 删除期初量
     *
     * @param beginOilDto
     * @return
     */
    ResultVo deletePeriodOil(BeginOilDto beginOilDto);

    /**
     * 查询期初量
     *
     * @param beginOilDto
     * @return
     */
    ResultVo getPeriodOil(BeginOilDto beginOilDto);

    /**
     * 解锁锁定
     *
     * @param beginOilDto
     * @return
     */
    ResultVo lockOrUnlock(BeginOilDto beginOilDto);

    /**
     * 生成欧盟航段报告
     *
     * @param generatorImoRptDto
     * @return
     */
    ResultVo generatorEuDcs(GeneratorImoRptDto generatorImoRptDto);

    /**
     * 保存或修改欧盟报告
     *
     * @param euRptVo
     * @return
     */
    ResultVo saveEuDcs(EuRptVo euRptVo);

    /**
     * 锁定或解锁欧盟报告
     *
     * @param euRptVo
     * @return
     */
    ResultVo lockOrUnlockEuDcs(EuRptVo euRptVo);

    /**
     * 删除欧盟报告
     *
     * @param euRptVo
     * @return
     */
    ResultVo deleteEuDcs(EuRptVo euRptVo);

    /**
     * 获取欧盟报告
     *
     * @param imoDcsRptManagerDto
     * @return
     */
    ResultVo getEudcs(ImoDcsRptManagerDto imoDcsRptManagerDto);

    /**
     * 导出EU报告
     *
     * @param euRptVo
     * @param response
     */
    void downEuDcs(EuRptVo euRptVo, HttpServletResponse response);

    /**
     * 下载cmsa报告
     *
     * @param cmsaRptDto
     * @param response
     */
    void downCmsa(CmsaRptDto cmsaRptDto, HttpServletResponse response);

    /**
     * 上传Imo报告
     *
     * @param ids
     */

    ResultVo imoReport(String[] ids);

    /**
     * 单船年度能效统计和能效指标统计
     *
     * @param energyEfficDto
     * @return
     */
    ResultVo singalShipYear(EnergyEfficDto energyEfficDto);

    String imoLibyaReports(LibyaDownLoadVo libyaDownLoadVo);

    /**
     * 公司年度能效统计和能效指标统计
     *
     * @param energyEfficDto
     * @return
     */
    ResultVo shipComPanyYear(EnergyEfficDto energyEfficDto);

    /**
     * 下载单船年度能效数据和能效指标
     *
     * @param energyEfficDto
     * @param response
     */
    void downLoadSigalShip(EnergyEfficDto energyEfficDto, HttpServletResponse response);

    /**
     * 下载公司年度能效数据和能效指标
     *
     * @param energyEfficDto
     * @param response
     */
    void downLoadComPanyShip(EnergyEfficDto energyEfficDto, HttpServletResponse response);

    /**
     * 获取能效趋势分析视图
     *
     * @param energyEfficiencyTrendAnalysisDto
     * @return
     */
    ResultVo getEnergyEfficencyTrend(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto);

    /**
     * 获取能效趋势分析数据
     *
     * @param energyEfficiencyTrendAnalysisDto
     * @return
     */
    ResultVo getEnergyEfficencyTrendData(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto);

    /**
     * 获取能效比较分析
     * @return
     */
    ResultVo getCompareAnalysisData(CompareAnalysisData compareAnalysisData);
    /**
     * 获取能效比较分析数据
     * @return
     */
    ResultVo getCompareAnalysisDatas(CompareAnalysisData compareAnalysisData);

    void downLoadEnergyEfficencyTrendData(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto, HttpServletResponse response);

    void downLoadCompareAnalysisData(CompareAnalysisData compareAnalysisData, HttpServletResponse response);

    /**
     * 能效数据跳转
     * @param informationDto
     * @return
     */
    ResultVo getEnergyEfficencyTrendMessage(InformationDto informationDto);

}
