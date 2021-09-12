package cn.ccsit.eeoi.energyeefic.service;

import cn.ccsit.common.utils.License;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.energyeefic.dto.*;
import cn.ccsit.eeoi.energyeefic.entity.OiShipVoyage;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePort;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyageSpec;
import cn.ccsit.eeoi.energyeefic.vo.CalculateOilVo;
import cn.ccsit.eeoi.energyeefic.vo.MrvDataVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EnergyEefficService {
    /**
     * 航次列表查询
     *
     * @param voyageListDto
     * @return
     */
    ResultVo getVolageList(VoyageListDto voyageListDto);

    ResultVo getRawVoyagePortByVoyageId(String voyageId,Integer voyageType);
    /**
     * 航次列表查询
     *
     * @param voyageListDto
     * @return
     */
    ResultVo getNewVolageList(VoyageListDto voyageListDto);

    /**
     * 港口列表查询
     *
     * @param voyagePortListDto
     * @return
     */
    ResultVo getVolagePortList(VoyagePortListDto voyagePortListDto);

    /**
     * 新增和更新港口数据
     *
     * @param voyagePortDto
     * @return
     */
    ResultVo createAndUpdatePortInfo(VoyagePortDto voyagePortDto);

    /**
     * 新增和更新冰区航行
     *
     * @param rawVoyageSpecDto
     * @return
     */
    ResultVo createAndUpdateIceClassAndRescue(RawVoyageSpecDto rawVoyageSpecDto);

    /**
     * 删除航次
     *
     * @param deleteVoyageListDto
     * @return
     */
    ResultVo deleteVolageList(DeleteVoyageListDto deleteVoyageListDto);

    /**
     * 删除港口
     *
     * @param deleteVoyageListDto
     * @return
     */
    ResultVo deleteVolagePortList(DeleteVoyageListDto deleteVoyageListDto);

    /**
     * 删除冰区航行和救援
     *
     * @param deleteVoyageListDto
     * @return
     */
    ResultVo deleteVolageIceClassAndRescueList(DeleteVoyageListDto deleteVoyageListDto);

    /**
     * 更新航次数据状态
     *
     * @param updateVoyageRecStatusDto
     * @return
     */
    ResultVo updateVolageStatus(UpdateVoyageRecStatusDto updateVoyageRecStatusDto);

    /**
     * 生成航次航段信息
     *
     * @param deleteVoyageListDto
     * @return
     */
    ResultVo generatorVolageInfo(DeleteVoyageListDto deleteVoyageListDto);

    /**
     * 异步生成航次航段
     * @param deleteVoyageListDto
     * @return
     */
    ResultVo generatorVoyageInfoAsynchronous(DeleteVoyageListDto deleteVoyageListDto);

    Map<String, String> checkToken(String timestamp, String token) throws Exception;

    /**
     * 上报数据接口
     *
     * @param voyageData
     * @param timestamp
     * @param token
     * @param request
     * @param response
     * @return
     */
    Map<String,Object> reportMrvData(String voyageData, String timestamp, String token, HttpServletRequest request, HttpServletResponse response);


    MrvDataVo reportdDeleteMrvData(String voyageData, String timestamp, String token, HttpServletRequest request, HttpServletResponse response);

    OiShipVoyage generatorAcrossInfo(List<RawVoyagePort> rawVoyagePorts, List<RawVoyageSpec> rawVoyageSpecs);

    OiShipVoyage generatorAcrossInfoEu(List<RawVoyagePort> rawVoyagePorts, List<RawVoyageSpec> rawVoyageSpecs,Date startTime,Date endTime);

    /**
     * @param shipId     船舶id
     * @param periodTime 期初时间
     * @return
     */
    ResultVo getFuelType(String shipId, Date periodTime);

    /**
     * 合并港口数据
     * @param rawVoyagePorts
     * @return
     */
    List<RawVoyagePort> procVoyageByMergePorts(List<RawVoyagePort> rawVoyagePorts);

    /**
     * 对交接港口进行合并
     * @param rawVoyagePortsListNew
     * @return
     */
    List<RawVoyagePort> mergeHandoverPort(List<RawVoyagePort> rawVoyagePortsListNew);

    CalculateOilVo calculateSailingOil(List<RawVoyagePort> rawVoyagePorts, String type);


    CalculateOilVo calculateSailingOilEU(List<RawVoyagePort> rawVoyagePorts, String type);



    /**
     * 获取航段的详细信息
     * @param voyageId
     * @return
     */
    ResultVo getVoyageDetailInfo(String voyageId);
    /**
     * 获取航段的详细信息
     * @param voyageId
     * @return
     */
    ResultVo getVoyageDetailInfoApp(String voyageId);

    /**
     * 根据航次id获取港口数据
     * @param taskId
     * @return
     */
    ResultVo getPortListByTaskId(String taskId);
    /**
     * 根据航次id获取航段id
     * @param taskId
     * @return
     */
    ResultVo getSegementIdByTaskId(String taskId);

    /**
     * 根据航次id获取航次的详细信息
     * @param taskId
     * @return
     */
    ResultVo getVoyageDeitailInfo(String taskId);

    /**
     * app获取油耗类型
     * @param shipId
     * @return
     */
    ResultVo getFuelTypeApp(String shipId);

    /**
     * 根据船舶的id获取船舶的航次号和船舶是否转换过船旗
     * @param shipId
     * @return
     */
    ResultVo getTasksByShipId(String shipId);

    /**
     * 获取cmsa报告
     * @param cmsaRptDto
     * @return
     */
    ResultVo getCmsaRpt(CmsaRptDto cmsaRptDto);

    /**
     * 查询cmsa报告
     *
     * @param imoDcsRptManagerDto
     * @return
     */
    ResultVo cmsaRptManager(ImoDcsRptManagerDto imoDcsRptManagerDto);

    /**
     * 获取cmsa报告
     * @param cmsaRptDto
     * @return
     */
    void downLoadCmsaRpt(CmsaRptDto cmsaRptDto,HttpServletResponse response);

    /**
     * 删除cmsa报告
     * @param cmsaRptId
     * @return
     */
    ResultVo deleteCmsaRpt(String cmsaRptId);

    ResultVo lockOrUnlock(CmsaRptDto cmsaRptDto);

    ResultVo cmsaRptStatusTag(CmsaRptDto cmsaRptDto);

    /**
     * 确定船舶是否有能效信息，如果有能效信息不能删除船舶
     * @param shipId
     * @return
     */
    ResultVo checkShipEnergyInfo(String shipId);

    /**
     * 上报数据生成航次航段信息
     * @param reaportGenerateVoyageInfoDtos
     */
    void reaportGenerateVoyageInfo(List<ReaportGenerateVoyageInfoDto> reaportGenerateVoyageInfoDtos);

//     List<RawVoyagePort> procVoyageByMergePorts(List<RawVoyagePort> rawVoyagePorts);
}
