package cn.ccsit.eeoi.energyeefic.service;

import cn.ccsit.common.constant.FuelConst;
import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.utils.*;
import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.common.vo.CurrentUserVo;
import cn.ccsit.eeoi.dictionary.entity.*;
import cn.ccsit.eeoi.dictionary.repository.EvaluationRepository;
import cn.ccsit.eeoi.dictionary.repository.GcCityRepository;
import cn.ccsit.eeoi.dictionary.repository.GcStateRepository;
import cn.ccsit.eeoi.dictionary.repository.ShipTypeRepository;
import cn.ccsit.eeoi.energyeefic.dto.*;
import cn.ccsit.eeoi.energyeefic.entity.*;
import cn.ccsit.eeoi.energyeefic.properties.FuelCo2CostProperties;
import cn.ccsit.eeoi.energyeefic.properties.FuelConsMethodProperties;
import cn.ccsit.eeoi.energyeefic.repository.*;
import cn.ccsit.eeoi.energyeefic.vo.*;
import cn.ccsit.eeoi.energyeefic.webService.ImoReportClient;
import cn.ccsit.eeoi.ship.entity.*;
import cn.ccsit.eeoi.ship.repository.*;
import cn.ccsit.eeoi.system.entity.OiShipChglog;
import cn.ccsit.eeoi.system.entity.SysFile;
import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.entity.SysUserRole;
import cn.ccsit.eeoi.system.repository.OiShipChglogRepository;
import cn.ccsit.eeoi.system.repository.SysFileRepository;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import cn.hutool.core.util.IdUtil;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.codehaus.plexus.util.ExceptionUtils;
import org.jboss.logging.NDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.Bidi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MrvDataServiceImp extends CommonServiceImpl implements MrvDataService {
    @Autowired
    private OiShipVoyageRepository oiShipVoyageRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private OiShipTfcRepository oiShipTfcRepository;
    @Autowired
    private CommonEnergyEfficService commonEnergyEfficServiceImpl;
    @Autowired
    private EnergyEefficService energyEefficService;
    @Autowired
    private ImoStdrptRepository imoStdrptRepository;
    @Autowired
    private ImoStdrptAdjustRepository imoStdrptAdjustRepository;
    @Autowired
    private BeginPeriodOilRepository beginPeriodOilRepository;
    @Autowired
    private OiShipInfoRepository oiShipInfoRepository;
    @Autowired
    private RawVoyageAddoilRepository rawVoyageAddoilRepository;
    @Autowired
    private RawVoyageOutoilRepository rawVoyageOutoilRepository;
    @Autowired
    private RawVoyageSludgeoilRepository rawVoyageSludgeoilRepository;
    @Autowired
    private RawVoyagePortoilRepository rawVoyagePortoilRepository;
    @Autowired
    private RawVoyagePortRepository rawVoyagePortRepository;
    @Autowired
    private RawVoyageSpecRepository rawVoyageSpecRepository;
    @Autowired
    private ImoDataSummaryRepository imoDataSummaryRepository;
    @Autowired
    private CdOilRepository cdOilRepository;
    @Autowired
    private ImoOilRepository imoOilRepository;
    @Autowired
    private ImoBdnSummaryRepository imoBdnSummaryRepository;
    @Autowired
    private BdnOilRepository bdnOilRepository;
    @Autowired
    private ImoCorrectionOilRepository imoCorrectionOilRepository;
    @Autowired
    private ShipTypeRepository shipTypeRepository;
    @Autowired
    private OiMainEngineRepository oiMainEngineRepository;
    @Autowired
    private OiShipGeRepository oiShipGeRepository;
    @Autowired
    private GcStateRepository gcStateRepository;
    @Autowired
    private FtpUtils ftpUtils;
    @Autowired
    private SysFileRepository sysFileRepository;
    @Autowired
    private BeginPeriodRepository beginPeriodRepository;
    @Autowired
    private ShipEquipmentFuelMapRepository shipEquipmentFuelMapRepository;
    @Autowired
    private OiShipTaskRepository oiShipTaskRepository;
    @Autowired
    private GcClientRepository gcClientRepository;
    @Autowired
    private GcCityRepository gcCityRepository;
    @Autowired
    private EuEmissionRptRepository euEmissionRptRepository;
    @Autowired
    private FuelCo2CostProperties fuelCo2CostProperties;
    @Autowired
    private FuelConsMethodProperties fuelConsMethodProperties;
    @Autowired
    private ImoReportClient imoReportClient;
    @Autowired
    private OiShipChglogRepository oiShipChglogRepository;

    @Autowired
    private FlagDocChangeRepository flagDocChangeRepository;

    public static final String FUEL_PER_MILE_NAME = "每海里油耗";
    public static final String CO2_PER_MILE = "每海里C02排放";
    public static final String FUEL_PER_MILE_NAME_TONS = "每吨海里油耗";
    public static final String CO2_PER_MILE_TONS = "每吨海里C02排放";
    public static final String DAY_FUEL_TONS = "每日主机油耗";
    public static final String SPEED = "航速";
    public static final String GROSS = "总吨";
    public static final String DESIGN_SPEED = "设计航速范围";
    public static final String ACCR_NUM = "DANAK 7508";
    public static final String VERIFY_NAME = "CHINA CLASSIFICATION SOCIETY";
    public static final String VERIFY_ADDR = "CCS Mansion 9 DONGZHIMEN NAN DA JIE";
    public static final String EMM_BO_UNCER = "10%";

    @Override
    public ResultVo getDataEvaluation(MrvDataAssessmentDto mrvDataAssessmentDto) {
        //获取评估参数

        log.error("数据评估参数信息==={}", mrvDataAssessmentDto);
        List<Evaluation> evaluations = evaluationRepository.findAllByIsDelete();
        List<MrvDataAssessmentVo> mrvDataAssessmentVos = new ArrayList<>();
        Map<String, BigDecimal> map = new HashMap<>();
        evaluations.forEach(evaluation -> {
            map.put(evaluation.getParamname(), evaluation.getParamvalue());
        });
        //获取非历史正常的数据
        List<OiShipVoyage> oiShipVoyages = new ArrayList<>();
        PageRequest of = PageRequest.of((mrvDataAssessmentDto.getCurrentPage() - 1), mrvDataAssessmentDto.getPageSize(), Sort.by("oiShipInfo.spname", "startTime").ascending());
        Page<OiShipVoyage> all = null;
        try {
            all = oiShipVoyageRepository.findAll(queryOiShipVoyageList(mrvDataAssessmentDto), of);
            oiShipVoyages = all.getContent();
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("时间格式不正确");
            throw new ExplicitException("时间格式不正确");
        }
        BigDecimal fuelConsOnWay = new BigDecimal(0);
        BigDecimal fuelConsStop = new BigDecimal(0);
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            fuelConsOnWay = BigDecimal.ZERO;
            ;
            fuelConsStop = BigDecimal.ZERO;
            ;
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            BigDecimal avgspeed = oiShipVoyage.getAvgspeed();
            BigDecimal distance = oiShipVoyage.getDistance();
            fuelConsOnWay = fuelConsOnWay.add(oiShipVoyage.getOiHfo()).add(oiShipVoyage.getOiChai()).add(oiShipVoyage.getOiLfo());
            fuelConsStop = fuelConsStop.add(oiShipVoyage.getStopoiHfo()).add(oiShipVoyage.getStopoiChai()).add(oiShipVoyage.getStopoiLfo());
            BigDecimal zero = BigDecimal.ZERO;
            if (distance == null) {
                distance = zero;
            }
            BigDecimal co2Cost = oiShipVoyage.getCo2Cost();
            if (co2Cost == null) {
                co2Cost = zero;
            }
            BigDecimal cargo = oiShipVoyage.getCargo();
            if (cargo == null) {
                cargo = zero;
            }
            BigDecimal oiHfo = oiShipVoyage.getOiHfo();
            if (oiHfo == null) {
                oiHfo = zero;
            }
            //每海里油耗等
            BigDecimal fuelPerMile = distance.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : fuelConsOnWay.divide(distance, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal co2PerMile = distance.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : co2Cost.divide(distance, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal fuelPerMileTons = distance.multiply(cargo).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : fuelConsOnWay.multiply(BigDecimal.valueOf(1000000)).divide(distance.multiply(cargo), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal co2PerMileTons = distance.multiply(cargo).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : co2Cost.multiply(BigDecimal.valueOf(1000000)).divide(distance.multiply(cargo), 2, BigDecimal.ROUND_HALF_UP);
            MrvDataAssessmentVo mrvDataAssessmentVo = new MrvDataAssessmentVo();
            //获取特定船舶的历史数据
            BigDecimal avgSpeedDifference = map.get(SPEED).multiply(oiShipVoyage.getAvgspeed());
            BigDecimal minAvgSpeed = avgspeed.subtract(avgSpeedDifference);
            BigDecimal maxAvgSpeed = avgspeed.add(avgSpeedDifference);
            BigDecimal fuelPerMileAbnormalData = new BigDecimal(0);
            BigDecimal co2AbnormalData = new BigDecimal(0);
            BigDecimal fuelPerMileTonAbnormalData = new BigDecimal(0);
            BigDecimal co2PerMileTonsAbnormalData = new BigDecimal(0);
            List<OiShipVoyage> oiShipVoyagesHistory = null;
            try {
                oiShipVoyagesHistory = getOiShipVoyagesHistory(minAvgSpeed, maxAvgSpeed, mrvDataAssessmentDto.getYear(), oiShipVoyage.getShipId(), mrvDataAssessmentDto.getIsEu());
            } catch (ParseException e) {
                log.error("时间格式不正确");
                throw new ExplicitException("时间格式不正确");
            }
            BigDecimal distanceSum = zero;
            BigDecimal co2CostSum = zero;
            BigDecimal cargoSum = zero;
            BigDecimal oilSum = zero;
            BigDecimal eeoiTd = new BigDecimal(0);
            boolean isOrNoVoyageHistory = oiShipVoyagesHistory.size() > 0;
            for (int j = 0; j < oiShipVoyagesHistory.size(); j++) {
                OiShipVoyage oiShipVoyageHistory = oiShipVoyagesHistory.get(j);
                distanceSum = distanceSum.add(oiShipVoyageHistory.getDistance() == null ? zero : oiShipVoyageHistory.getDistance());
                co2CostSum = co2CostSum.add(oiShipVoyageHistory.getCo2Cost() == null ? zero : oiShipVoyageHistory.getCo2Cost());
                cargoSum = cargoSum.add(oiShipVoyageHistory.getCargo() == null ? zero : oiShipVoyageHistory.getCargo());
                oilSum = oilSum.add(oiShipVoyageHistory.getOiHfo() == null ? zero : oiShipVoyageHistory.getOiHfo());
                eeoiTd = eeoiTd.add(cargoSum.multiply(distanceSum));
            }
            //每海里油耗
            BigDecimal fuelPerMileHistory = distanceSum.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : oilSum.divide(distanceSum, 2, BigDecimal.ROUND_HALF_UP);
            //每海里二氧化碳排放
            BigDecimal co2PerMileHistory = distanceSum.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : co2CostSum.divide(distanceSum, 2, BigDecimal.ROUND_HALF_UP);
            //每吨每海里油耗
            BigDecimal fuelPerMileTonsHistory = eeoiTd.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : oilSum.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 2, BigDecimal.ROUND_HALF_UP);
            //每吨每海里二氧化碳排放
            BigDecimal co2PerMileTonsHistory = eeoiTd.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : co2CostSum.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 2, BigDecimal.ROUND_HALF_UP);
            //差值
            BigDecimal fuelPerMileDef = fuelPerMileHistory.multiply(map.get(FUEL_PER_MILE_NAME));
            BigDecimal co2PerMileDef = co2PerMileHistory.multiply(map.get(CO2_PER_MILE));
            BigDecimal fuelPerMileTonDef = fuelPerMileTonsHistory.multiply(map.get(FUEL_PER_MILE_NAME_TONS));
            BigDecimal co2PerMileTonsDef = co2PerMileTonsHistory.multiply(map.get(CO2_PER_MILE_TONS));
            if (isOrNoVoyageHistory && (fuelPerMile.compareTo(fuelPerMileHistory.subtract(fuelPerMileDef)) < 0 || fuelPerMile.compareTo(fuelPerMileHistory.add(fuelPerMileDef)) > 0)) {
                fuelPerMileAbnormalData = map.get(FUEL_PER_MILE_NAME);
            }
            if (isOrNoVoyageHistory && (isOrNoVoyageHistory && (co2PerMile.compareTo(co2PerMileHistory.subtract(co2PerMileDef)) < 0 || co2PerMile.compareTo(co2PerMileHistory.add(co2PerMileDef)) > 0))) {
                co2AbnormalData = map.get(CO2_PER_MILE);
            }
            if (isOrNoVoyageHistory && (fuelPerMileTons.compareTo(fuelPerMileTonsHistory.subtract(fuelPerMileTonDef)) < 0 || fuelPerMileTons.compareTo(fuelPerMileTonsHistory.add(fuelPerMileTonDef)) > 0)) {
                fuelPerMileTonAbnormalData = map.get(FUEL_PER_MILE_NAME_TONS);
            }
            if (isOrNoVoyageHistory && (co2PerMileTons.compareTo(co2PerMileTonsHistory.subtract(co2PerMileTonsDef)) < 0 || co2PerMileTons.compareTo(co2PerMileTonsHistory.add(co2PerMileTonsDef)) > 0)) {
                co2PerMileTonsAbnormalData = map.get(CO2_PER_MILE_TONS);
            }
            //计算航行每日油耗和停港每日油耗
            long endTime = oiShipVoyage.getEndTime().getTime();
            long startTime = oiShipVoyage.getStartTime().getTime();
            BigDecimal stopTime = oiShipVoyage.getStopTime();
            if (stopTime == null) {
                stopTime = zero;
            }
            BigDecimal dayStopTime = stopTime.divide(BigDecimal.valueOf(1440), 2, BigDecimal.ROUND_HALF_UP);
            long d = 1000 * 60 * 60 * 24;
            BigDecimal dayNum = BigDecimal.valueOf((double) (endTime - startTime) / d);
            dayNum = dayNum.subtract(dayStopTime);
            BigDecimal fuelPerDay = dayNum.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : fuelConsOnWay.divide(dayNum, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal fuelPerDayStop = dayStopTime.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : fuelConsStop.divide(dayStopTime, 2, BigDecimal.ROUND_HALF_UP);
            //获取理论油耗
            OiShipTfc oiShipTfc = oiShipTfcRepository.findByAverageSpeedAndShipidAndIsDelete(oiShipVoyage.getAvgspeed().intValue(), oiShipVoyage.getShipId(), 0);
            BigDecimal fuelPerDayTheory = null;
            if (oiShipTfc != null) {
                fuelPerDayTheory = oiShipTfc.getShipFuelCons();
            } else {
                fuelPerDayTheory = BigDecimal.ZERO;
            }
            BigDecimal fuelPerDayMinTheory = fuelPerDayTheory.subtract(map.get(DAY_FUEL_TONS).multiply(fuelPerDayTheory));
            BigDecimal fuelPerDayMaxTheory = fuelPerDayTheory.add(map.get(DAY_FUEL_TONS).multiply(fuelPerDayTheory));
            BigDecimal dayFuelTonsAbnormalData = new BigDecimal(0);
            if ((fuelPerDay.compareTo(fuelPerDayMinTheory) < 0 || fuelPerDay.compareTo(fuelPerDayMaxTheory) > 0) && oiShipTfc != null && isOrNoVoyageHistory) {
                dayFuelTonsAbnormalData = map.get(DAY_FUEL_TONS);
            }
            OiShipInfo oiShipInfo = oiShipVoyage.getOiShipInfo();
            BigDecimal speed = oiShipInfo.getSpeed();
            if (speed == null) {
                speed = new BigDecimal(0);
            }
            BigDecimal designSpeed = speed.add(map.get(DESIGN_SPEED).multiply(speed));
            BigDecimal avgSpeedAbnormalData = new BigDecimal(0);
            if (oiShipVoyage.getAvgspeed().compareTo(designSpeed) > 0) {
                avgSpeedAbnormalData = map.get(DESIGN_SPEED);
            }
            //计算降速比
            BigDecimal voyageSpeedDistance = commonEnergyEfficServiceImpl.getVoyageSpeedDistance(Arrays.asList(oiShipVoyage));
            BigDecimal voyageserviceSpeedDistance = commonEnergyEfficServiceImpl.getVoyageserviceSpeedDistance(Arrays.asList(oiShipVoyage), oiShipInfo.getServiceSpeed() == null ? speed : oiShipInfo.getServiceSpeed());
            BigDecimal speedRate = voyageserviceSpeedDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : voyageSpeedDistance.divide(voyageserviceSpeedDistance, 2, BigDecimal.ROUND_HALF_UP);
            //计算载货量利用率
            BigDecimal voyageEeoiTd = commonEnergyEfficServiceImpl.getVoyageEeoiTd(Arrays.asList(oiShipVoyage)).get("eeoiTd");
            BigDecimal getdwtDistance = commonEnergyEfficServiceImpl.getdwtDistance(Arrays.asList(oiShipVoyage), oiShipInfo.getDw());
            BigDecimal useRate = getdwtDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : voyageEeoiTd.divide(getdwtDistance, 2, BigDecimal.ROUND_HALF_UP);
            mrvDataAssessmentVo.setFuelPerMile(fuelPerMile);
            mrvDataAssessmentVo.setFuelPerMileAbnormalData(fuelPerMileAbnormalData);
            mrvDataAssessmentVo.setFuelPerMileTon(fuelPerMileTons);
            mrvDataAssessmentVo.setFuelPerMileTonAbnormalData(fuelPerMileTonAbnormalData);
            mrvDataAssessmentVo.setCo2PerMile(co2PerMile);
            mrvDataAssessmentVo.setCo2AbnormalData(co2AbnormalData);
            mrvDataAssessmentVo.setCo2PerMileTons(co2PerMileTons);
            mrvDataAssessmentVo.setCo2PerMileTonsAbnormalData(co2PerMileTonsAbnormalData);
            mrvDataAssessmentVo.setDayFuelTons(fuelPerDay);
            mrvDataAssessmentVo.setDayFuelTonsStop(fuelPerDayStop);
            mrvDataAssessmentVo.setDayFuelTonsAbnormalData(dayFuelTonsAbnormalData);
            mrvDataAssessmentVo.setAvgSpeed(oiShipVoyage.getAvgspeed());
            mrvDataAssessmentVo.setAvgSpeedAbnormalData(avgSpeedAbnormalData);
            mrvDataAssessmentVo.setDropRadio(speedRate);
            mrvDataAssessmentVo.setLoadUtilizationRate(useRate);
            mrvDataAssessmentVo.setDistance(oiShipVoyage.getDistance());
            mrvDataAssessmentVo.setShipName(oiShipInfo.getSpname());
            mrvDataAssessmentVo.setStartTime(oiShipVoyage.getStartTime());
            mrvDataAssessmentVo.setEndTime(oiShipVoyage.getEndTime());
            mrvDataAssessmentVo.setStartPort(oiShipVoyage.getStartporten());
            mrvDataAssessmentVo.setEndPort(oiShipVoyage.getEndporten());
            mrvDataAssessmentVo.setSegmentNu(oiShipVoyage.getVoyageno());
            OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(oiShipVoyage.getTaskid(), 0);
            if (oiShipTask != null) {
                mrvDataAssessmentVo.setTaskNu(oiShipTask.getTask());
            }
            mrvDataAssessmentVos.add(mrvDataAssessmentVo);
        }
        if (mrvDataAssessmentDto.getIsAbnormalData()) {
            mrvDataAssessmentVos = mrvDataAssessmentVos.stream().filter(mrvDataAssessmentVo -> {
                return mrvDataAssessmentVo.getCo2AbnormalData().compareTo(BigDecimal.ZERO) > 0 || mrvDataAssessmentVo.getAvgSpeedAbnormalData().compareTo(BigDecimal.ZERO) > 0
                        || mrvDataAssessmentVo.getCo2PerMileTonsAbnormalData().compareTo(BigDecimal.ZERO) > 0 || mrvDataAssessmentVo.getDayFuelTonsAbnormalData().compareTo(BigDecimal.ZERO) > 0
                        || mrvDataAssessmentVo.getFuelPerMileAbnormalData().compareTo(BigDecimal.ZERO) > 0 || mrvDataAssessmentVo.getFuelPerMileTonAbnormalData().compareTo(BigDecimal.ZERO) > 0
                        || mrvDataAssessmentVo.getDistance().compareTo(BigDecimal.ZERO) == 0 || mrvDataAssessmentVo.getDropRadio().compareTo(BigDecimal.valueOf(1)) > 0
                        || mrvDataAssessmentVo.getLoadUtilizationRate().compareTo(BigDecimal.valueOf(1)) > 0 || mrvDataAssessmentVo.getEndTime().compareTo(mrvDataAssessmentVo.getStartTime()) < 0;
            }).collect(Collectors.toList());
        }
//        if (StringUtils.isNotEmpty(mrvDataAssessmentDto.getTaskNu())) {
//            mrvDataAssessmentVos = mrvDataAssessmentVos.stream().filter(mrvDataAssessmentVo -> {
//                return mrvDataAssessmentVo.getTaskNu().equals(mrvDataAssessmentDto.getTaskNu());
//            }).collect(Collectors.toList());
//        }
        PageDataVo<MrvDataAssessmentVo> voyageListVoPageDataVo = new PageDataVo<>();
        voyageListVoPageDataVo.setItems(mrvDataAssessmentVos);
        voyageListVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(voyageListVoPageDataVo);
    }

    public List<OiShipVoyage> getOiShipVoyagesHistory(BigDecimal minAvgSpeed, BigDecimal maxAvgSpeed, String time, String shipId, Integer isEu) throws ParseException {
        if (!StringUtils.isNotEmpty(time)) {
            Calendar date = Calendar.getInstance();
            time = String.valueOf(date.get(Calendar.YEAR));
        }
        int parseTime = Integer.parseInt(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = simpleDateFormat.parse((parseTime - 1) + "-01-01");
        Date endTime = simpleDateFormat.parse((parseTime + "-12-31"));
        System.out.println(startTime);
        System.out.println(endTime);
        Specification<OiShipVoyage> oiShipVoyageSpecification = queryOiShipVoyageHostoryList(minAvgSpeed, maxAvgSpeed, startTime, endTime, shipId, isEu);
        return oiShipVoyageRepository.findAll(oiShipVoyageSpecification);
    }

    @Override
    public ResultVo getSegmentMonitoring(MrvDataAssessmentDto mrvDataAssessmentDto) {
        PageRequest of = PageRequest.of((mrvDataAssessmentDto.getCurrentPage() - 1), mrvDataAssessmentDto.getPageSize(), Sort.by("oiShipInfo.spname", "startTime").ascending());
        Page<OiShipVoyage> all = null;
        BigDecimal zero = BigDecimal.ZERO;
        try {
            all = oiShipVoyageRepository.findAll(queryOiShipVoyageList(mrvDataAssessmentDto), of);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("时间格式不正确");
            throw new ExplicitException("时间格式不正确");
        }
        List<SegmentMonitoringVo> segmentMonitoringVos = new ArrayList<>();
        List<OiShipVoyage> oiShipVoyages = all.getContent();
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            Integer voyType = oiShipVoyage.getVoyType();
            BigDecimal stopTime = oiShipVoyage.getStopTime();
            if (stopTime == null) {
                stopTime = zero;
            }
            SegmentMonitoringVo segmentMonitoringVo = new SegmentMonitoringVo();
            segmentMonitoringVo.setCargo(oiShipVoyage.getCargo());
            segmentMonitoringVo.setDistance(oiShipVoyage.getDistance());
            segmentMonitoringVo.setEndPort(oiShipVoyage.getEndporten());
            segmentMonitoringVo.setStartPort(oiShipVoyage.getStartporten());
            segmentMonitoringVo.setSatrtPortDeptTime(oiShipVoyage.getStartTime());
            segmentMonitoringVo.setEndPortDeptTime(oiShipVoyage.getEndTime());
            segmentMonitoringVo.setShipName(oiShipVoyage.getOiShipInfo().getSpname());
            segmentMonitoringVo.setStopTime(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
            segmentMonitoringVo.setOilHfo(oiShipVoyage.getOiHfo());
            segmentMonitoringVo.setOilLfo(oiShipVoyage.getOiLfo());
            segmentMonitoringVo.setOilChaiQi(oiShipVoyage.getOiChai());
            segmentMonitoringVo.setStopOilHfo(oiShipVoyage.getStopoiHfo());
            segmentMonitoringVo.setStopOilLfo(oiShipVoyage.getStopoiLfo());
            segmentMonitoringVo.setStopOilChaiQi(oiShipVoyage.getStopoiChai());
            OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(oiShipVoyage.getTaskid(), 0);
            if (oiShipTask != null) {
                segmentMonitoringVo.setTaskNu(oiShipTask.getTask());
            }
            segmentMonitoringVo.setSegmentNu(oiShipVoyage.getVoyageno());
            long startTime = 0;
            long endTime = 0;
            long endPortTime = 0;
            String endportid = oiShipVoyage.getEndportid();
            RawVoyagePort rawVoyagePort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
            if (rawVoyagePort != null) {
                Date deptTm = rawVoyagePort.getDeptTm();
                Date arrTm = rawVoyagePort.getArrTm();
                if (deptTm != null && arrTm != null) {
                    endPortTime = deptTm.getTime() - arrTm.getTime();
                }
            }
            if (oiShipVoyage.getStartTime() != null && oiShipVoyage.getEndTime() != null) {
                startTime = oiShipVoyage.getStartTime().getTime();
                endTime = oiShipVoyage.getEndTime().getTime();
            }
            long h = 1000 * 60 * 60;
            BigDecimal sailTime = BigDecimal.ZERO;
            if (voyType == 1) {
                sailTime = BigDecimal.valueOf((double) (endTime - startTime) / h);
            } else {
                sailTime = BigDecimal.valueOf((double) (endTime - startTime - endPortTime) / h);
            }
            segmentMonitoringVo.setSailTime(sailTime.subtract(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP));
            segmentMonitoringVos.add(segmentMonitoringVo);
        }
        if (mrvDataAssessmentDto.getIsAbnormalData()) {
            segmentMonitoringVos = segmentMonitoringVos.stream().filter(segmentMonitoringVo -> {
                return segmentMonitoringVo.getDistance().compareTo(BigDecimal.ZERO) == 0 || segmentMonitoringVo.getEndPortDeptTime().compareTo(segmentMonitoringVo.getSatrtPortDeptTime()) < 0;
            }).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(mrvDataAssessmentDto.getTaskNu())) {
            segmentMonitoringVos = segmentMonitoringVos.stream().filter(segmentMonitoringVo -> {
                return segmentMonitoringVo.getTaskNu().equals(mrvDataAssessmentDto.getTaskNu());
            }).collect(Collectors.toList());
        }
        PageDataVo<SegmentMonitoringVo> voyageListVoPageDataVo = new PageDataVo<>();
        voyageListVoPageDataVo.setItems(segmentMonitoringVos);
        voyageListVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(voyageListVoPageDataVo);
    }

    @Override
    public void downLoadDataAssessment(List<MrvDataAssessmentVo> mrvDataAssessmentVos, HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MrvDataAssessmentVo mrvDataAssessmentVo : mrvDataAssessmentVos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("船名", mrvDataAssessmentVo.getShipName());
            map.put("时间段", mrvDataAssessmentVo.getStartTime() + "~" + mrvDataAssessmentVo.getEndTime());
            map.put("每海里油耗", mrvDataAssessmentVo.getFuelPerMile());
            map.put("海海里CO2排放", mrvDataAssessmentVo.getCo2PerMile());
            map.put("每吨海海里油耗", mrvDataAssessmentVo.getFuelPerMileTon());
            map.put("每吨海海里CO2排放", mrvDataAssessmentVo.getCo2PerMileTons());
            map.put("每日油耗", mrvDataAssessmentVo.getDayFuelTons());
            map.put("停靠港每日油耗", mrvDataAssessmentVo.getDayFuelTonsStop());
            map.put("平均航速", mrvDataAssessmentVo.getAvgSpeed());
            list.add(map);
        }
        try {
            FileUtils.downloadExcel(list, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downLoadSegmentMonitoring(List<SegmentMonitoringVo> segmentMonitoringVos, HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SegmentMonitoringVo segmentMonitoringVo : segmentMonitoringVos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("船名", segmentMonitoringVo.getShipName());
            map.put("航段时间", segmentMonitoringVo.getSatrtPortDeptTime().toString() + "~" + segmentMonitoringVo.getEndPortDeptTime().toString());
            map.put("出发港", segmentMonitoringVo.getStartPort());
            map.put("抵达港", segmentMonitoringVo.getEndPort());
            map.put("航行里程", segmentMonitoringVo.getDistance());
            map.put("载货/客量", segmentMonitoringVo.getCargo());
            map.put("航行时间", segmentMonitoringVo.getSailTime());
            map.put("途中停泊时间", segmentMonitoringVo.getStopTime());
            map.put("航行HFO消耗", segmentMonitoringVo.getOilHfo());
            map.put("航行LFO消耗", segmentMonitoringVo.getOilLfo());
            map.put("航行GDO/MGO消耗", segmentMonitoringVo.getOilChaiQi());
            map.put("装卸港停泊HFO消耗", segmentMonitoringVo.getStopOilHfo());
            map.put("装卸港停泊LFO消耗", segmentMonitoringVo.getStopOilLfo());
            map.put("装卸港停泊GDO/MGO消耗", segmentMonitoringVo.getStopOilChaiQi());
            list.add(map);
        }
        try {
            FileUtils.downloadExcel(list, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultVo dcsPortManager(ImoDcsRptManagerDto imoDcsRptManagerDto) {
        //分页条件
        PageRequest of = PageRequest.of((imoDcsRptManagerDto.getCurrentPage() - 1), imoDcsRptManagerDto.getPageSize(), Sort.by("startTm", "oiShipInfo.spname").descending());
        Page<ImoStdrpt> all = imoStdrptRepository.findAll(queryImoReaportList(imoDcsRptManagerDto), of);
        List<ImoStdrpt> imoStdrpts = all.getContent();
        List<ImoDcsRptManagerVo> imoDcsRptManagerVos = new ArrayList<>();
        for (int i = 0; i < imoStdrpts.size(); i++) {
            ImoStdrpt imoStdrpt = imoStdrpts.get(i);
            ImoDcsRptManagerVo imoDcsRptManagerVo = new ImoDcsRptManagerVo();
            imoDcsRptManagerVo.setId(imoStdrpt.getId());
            imoDcsRptManagerVo.setDocManager(imoStdrpt.getDocName());
            GcState gcState = gcStateRepository.findFlag(imoStdrpt.getCountryFlag());
            imoDcsRptManagerVo.setFlag(gcState == null ? null : gcState.getEnName());
            imoDcsRptManagerVo.setImoNu(imoStdrpt.getImono());
            imoDcsRptManagerVo.setRptEndTime(imoStdrpt.getEndTm());
            imoDcsRptManagerVo.setRptStartTime(imoStdrpt.getStartTm());
            ShipType shipType = shipTypeRepository.findShipType(imoStdrpt.getSpType());
            imoDcsRptManagerVo.setShipType(shipType == null ? null : shipType.getSptype());
            imoDcsRptManagerVo.setRptStatus(imoStdrpt.getRecStatus());
            imoDcsRptManagerVo.setReaportSource(imoStdrpt.getRptSource());
            imoDcsRptManagerVo.setShipName(imoStdrpt.getShipName());
            imoDcsRptManagerVo.setShipId(imoStdrpt.getShipId());
            imoDcsRptManagerVos.add(imoDcsRptManagerVo);
        }
        PageDataVo<ImoDcsRptManagerVo> imoDcsRptManagerVoPageDataVo = new PageDataVo<>();
        imoDcsRptManagerVoPageDataVo.setItems(imoDcsRptManagerVos);
        imoDcsRptManagerVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(imoDcsRptManagerVoPageDataVo);
    }

    @Override
    @Transactional
    public ResultVo imoStdRptStatusTag(ImoStdRptStatusTagDto imoStdRptStatusTagDto) {
        //查询需要标记状态的报告列表
        List<String> imoStdRptIds = imoStdRptStatusTagDto.getImoStdRptIds();
        List<ImoStdrpt> imoStdrpts = (List<ImoStdrpt>) imoStdrptRepository.findAllById(imoStdRptIds);
        for (int i = 0; i < imoStdrpts.size(); i++) {
            ImoStdrpt imoStdrpt = imoStdrpts.get(i);
            if (Integer.parseInt(imoStdrpt.getRecStatus()) < 2) {
                return new ResultErrorVo(this.msg("imoStdRptStatus.is.unlock"));
            }
            imoStdrpt.setRecStatus("3");
            imoStdrpt.getImoOils().forEach(imoOil -> {
                imoOil.setRecStatus("3");
            });
            List<ImoBdnSummary> imoBdnSummaries = imoStdrpt.getImoBdnSummaries();
            List<ImoDataSummary> imoDataSummaries = imoStdrpt.getImoDataSummaries();
            if (imoBdnSummaries != null) {
                imoStdrpt.getImoBdnSummaries().forEach(imoBdnSummary -> {
                    imoBdnSummary.setRecStatus("3");
                    imoBdnSummary.getBdnOils().forEach(bdnOil -> {
                        bdnOil.setRecStatus("3");
                    });
                });
            }
            if (imoDataSummaries != null) {
                imoStdrpt.getImoDataSummaries().forEach(imoDataSummary -> {
                    imoDataSummary.setRecStatus("3");
                    imoDataSummary.getCdOils().forEach(cdOil -> {
                        cdOil.setRecStatus("3");
                    });
                });
            }
            ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(imoStdrpt.getId(), 0);
            if (imoStdrptAdjust != null) {
                imoStdrptAdjust.getImoCorrectionOils().forEach(imoCorrectionOil -> {
                    imoCorrectionOil.setRecStatus("3");
                });
                imoStdrptAdjustRepository.save(imoStdrptAdjust);
            }
        }
        try {
            imoStdrptRepository.saveAll(imoStdrpts);
        } catch (Exception e) {
            return new ResultErrorVo(this.msg("imoStdRptStatus.tag.fail"));
        }
        return new ResultStringVo("imoStdRptStatus.tag.success");
    }

    @Override
    @Transactional
    public ResultVo lockOrUnlock(ImoStdRptStatusTagDto imoStdRptStatusTagDto) {
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(imoStdRptStatusTagDto.getImoStdId(), 0);
        String recStatus = imoStdrpt.getRecStatus();
        String setRecStatus = "";
        if ("2".equals(recStatus) || "3".equals(recStatus)) {
            setRecStatus = "1";

        } else {
            setRecStatus = "2";
        }
        imoStdrpt.setRecStatus(setRecStatus);
        String finalSetRecStatus = setRecStatus;
        imoStdrpt.getImoOils().forEach(imoOil -> {
            imoOil.setRecStatus(finalSetRecStatus);
        });
        if (imoStdRptStatusTagDto.getRptSource().equals("1")) {
            imoStdrpt.getImoBdnSummaries().forEach(imoBdnSummary -> {
                imoBdnSummary.setRecStatus(finalSetRecStatus);
                imoBdnSummary.getBdnOils().forEach(bdnOil -> {
                    bdnOil.setRecStatus(finalSetRecStatus);
                });
            });
            imoStdrpt.getImoDataSummaries().forEach(imoDataSummary -> {
                imoDataSummary.setRecStatus(finalSetRecStatus);
                imoDataSummary.getCdOils().forEach(cdOil -> {
                    cdOil.setRecStatus(finalSetRecStatus);
                });
            });
            ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(imoStdrpt.getId(), 0);
            if (imoStdrptAdjust != null) {
                imoStdrptAdjust.getImoCorrectionOils().forEach(imoCorrectionOil -> {
                    imoCorrectionOil.setRecStatus(finalSetRecStatus);
                });
                imoStdrptAdjustRepository.save(imoStdrptAdjust);
            }
        }
        ImoStdrpt save = imoStdrptRepository.save(imoStdrpt);
        if (save != null) {
            if ("2".equals(recStatus) || "3".equals(recStatus)) {
                return new ResultStringVo(this.msg("imoStdRptStatus.tag.unlock.success"));
            } else {
                return new ResultStringVo(this.msg("imoStdRptStatus.tag.lock.success"));
            }
        } else {
            if ("2".equals(recStatus) || "3".equals(recStatus)) {
                return new ResultErrorVo(this.msg("imoStdRptStatus.tag.unlock.fail"));
            } else {
                return new ResultErrorVo(this.msg("imoStdRptStatus.tag.lock.fail"));
            }
        }
    }

    @Override
    @Transactional
    public ResultVo deleteImoStdRpt(ImoStdRptStatusTagDto imoStdRptStatusTagDto) {
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(imoStdRptStatusTagDto.getImoStdId(), 0);
        imoStdrpt.setIsDelete(1);
        imoStdrpt.getImoOils().forEach(imoOil -> {
            imoOil.setIsDelete(1);
        });
        List<ImoBdnSummary> imoBdnSummaries = imoStdrpt.getImoBdnSummaries();
        List<ImoDataSummary> imoDataSummaries = imoStdrpt.getImoDataSummaries();
        if (imoBdnSummaries != null) {
            imoStdrpt.getImoBdnSummaries().forEach(imoBdnSummary -> {
                imoBdnSummary.setIsDelete(1);
                imoBdnSummary.getBdnOils().forEach(bdnOil -> {
                    bdnOil.setIsDelete(1);
                });
            });
        }
        if (imoDataSummaries != null) {
            imoStdrpt.getImoDataSummaries().forEach(imoDataSummary -> {
                imoDataSummary.setIsDelete(1);
                imoDataSummary.getCdOils().forEach(cdOil -> {
                    cdOil.setIsDelete(1);
                });
            });
        }
        ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(imoStdrpt.getId(), 0);
        if (imoStdrptAdjust != null) {
            imoStdrptAdjust.setIsDelete(1);
            imoStdrptAdjust.getImoCorrectionOils().forEach(imoCorrectionOil -> {
                imoCorrectionOil.setIsDelete(1);
            });
            imoStdrptAdjustRepository.save(imoStdrptAdjust);
        }
        ImoStdrpt save = imoStdrptRepository.save(imoStdrpt);
        if (save != null) {
            return new ResultStringVo(this.msg("imoRpt.delete.success"));
        } else {
            return new ResultErrorVo(this.msg("imoRpt.delete.fail"));
        }
    }

    @Override
    public ResultVo getDcsPeriodTime(String shipId, String year, String rptType) {
        log.error("获取时间段的参数==============={}", shipId + year);
        String start = year + "-01-01 00:00:00";
        String end = (Integer.valueOf(year) + 1) + "-01-01 00:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ImoRptTimeVo> imoRptVo = null;
        try {
//            Integer count = beginPeriodRepository.findByShipIdAndIsDeleteAndPeriodTimeOrPeriodTime(shipId, start, end);
            int endYear = Integer.valueOf(year) + 1;
            List<Date> periodTime = beginPeriodRepository.getPeriodTime(shipId, year, String.valueOf(endYear));
            if (periodTime.size() < 2) {
                return new ResultErrorVo(this.msg("imoRpt.generator.period.time.fail"));
            } else {
                Date date = periodTime.get(periodTime.size() - 1);
                end = simpleDateFormat.format(date);
            }
            if ("0".equals(rptType)) {
                imoRptVo = getImoRptVo(year, shipId, start, end);
            } else {
                imoRptVo = getEuRptVo(year, shipId, start, end);
            }
        } catch (ParseException e) {
            log.error("日期格式不正确={}", year);
            e.printStackTrace();
            throw new ExplicitException("日期格式不正确");
        }
        return new ResultObjectVo<>(imoRptVo);
    }

    //todo
    @Override
    @Transactional
    public ResultVo generatorImoRpt(GeneratorImoRptDto generatorImoRptDto) {

        String imoRptId = generatorImoRptDto.getImoRptId();

        String shipId = generatorImoRptDto.getShipId();

        //根据船舶id获取在报告年份的航次

        Map<String, String> useMethod = getUseMethod(shipId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        Date startTime = DateUtils.appendDate(generatorImoRptDto.getStartTime(),0,false);
//
//        Date endTime = DateUtils.appendDate(generatorImoRptDto.getEndTime(),0,false);
        Date startTime = generatorImoRptDto.getStartTime();
        Date endTime = generatorImoRptDto.getEndTime();

        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);

        //查询报告
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(imoRptId, 0);

        String recStatus = "2";

        String rptSource = null;

        if (imoStdrpt != null) {
            rptSource = imoStdrpt.getRptSource();
        }

        //返回vo
        ImoRptVo imoRptVo = new ImoRptVo();
        Calendar instance = Calendar.getInstance();
        instance.setTime(startTime);
        if ((null == imoStdrpt || "1".equals(imoStdrpt.getRecStatus())) && !"2".equals(rptSource)) {

            //获取船舶航次信息
            List<OiShipTask> oiShipTasks = oiShipTaskRepository.findOiShipTask(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));

//            Integer count = beginPeriodRepository.findByShipIdAndIsDeleteAndPeriodTimeOrPeriodTime(shipId, startYear + "-01-01 00:00:00", startYear + 1 + "-01-01 00:00:00");
            //todo
            List<OiShipVoyage> oiShipVoyageGeners = getOiShipVoyages(oiShipTasks, "IMO");
            recStatus = "1";

            //获取imo标准报告数据
            ImoStdrpt imoStdrptSave = new ImoStdrpt();

            if (imoStdrpt != null) {
                imoStdrptSave.setId(imoStdrpt.getId());
                //删除此imo的bdn、cd报告
                imoStdrpt.setIsDelete(1);
                imoStdrpt.getImoOils().forEach(imoOil -> {
                    imoOil.setIsDelete(1);
                });

                List<ImoBdnSummary> imoBdnSummaries = imoStdrpt.getImoBdnSummaries();

                List<ImoDataSummary> imoDataSummaries = imoStdrpt.getImoDataSummaries();

                if (imoBdnSummaries != null) {

                    imoStdrpt.getImoBdnSummaries().forEach(imoBdnSummary -> {

                        imoBdnSummary.setIsDelete(1);

                        imoBdnSummary.getBdnOils().forEach(bdnOil -> {

                            bdnOil.setIsDelete(1);

                        });

                    });

                }

                if (imoDataSummaries != null) {

                    imoStdrpt.getImoDataSummaries().forEach(imoDataSummary -> {

                        imoDataSummary.setIsDelete(1);

                        imoDataSummary.getCdOils().forEach(cdOil -> {

                            cdOil.setIsDelete(1);

                        });

                    });

                }

            }

            //筛选出没有跨年的航段
            List<OiShipVoyage> noAcrossVoyage = oiShipVoyageGeners.stream().filter(oiShipVoyage -> {

                return (oiShipVoyage.getStartTime().getTime() >= startTime.getTime() && oiShipVoyage.getStartTime().getTime() <= endTime.getTime())

                        && (oiShipVoyage.getEndTime().getTime() >= startTime.getTime() && oiShipVoyage.getEndTime().getTime() <= endTime.getTime());

            }).collect(Collectors.toList());

            //筛选出跨年的航段
            List<OiShipVoyage> collectPreYearVoyage = oiShipVoyageGeners.stream().filter(oiShipVoyage -> {

                return startTime.getTime() > oiShipVoyage.getStartTime().getTime() && startTime.getTime() < oiShipVoyage.getEndTime().getTime();

            }).collect(Collectors.toList());

            List<OiShipVoyage> collectPrenextYearVoyage = oiShipVoyageGeners.stream().filter(oiShipVoyage -> {

                return endTime.getTime() > oiShipVoyage.getStartTime().getTime() && endTime.getTime() < oiShipVoyage.getEndTime().getTime();

            }).collect(Collectors.toList());

            Date preYearStartTime = startTime;//上一年跨年航段开始时间

            Date nextYearEndTime = endTime;//下一年跨年航段结束时间

            //计算期初油量模糊匹配 默认时间加减一分钟

            instance.setTime(startTime);

            instance.add(Calendar.MINUTE, -1);

            Date startMinute = instance.getTime();

            instance.setTime(startTime);

            instance.add(Calendar.MINUTE, 1);

            Date endMinute = instance.getTime();

            instance.setTime(endTime);

            instance.add(Calendar.MINUTE, -1);

            Date endStartMionute = instance.getTime();

            instance.setTime(endTime);

            instance.add(Calendar.MINUTE, 1);

            Date endEndMionute = instance.getTime();

            List<Map<String, Object>> startBeginOils = beginPeriodOilRepository.findByShipIdAndPeriodTime(shipId, simpleDateFormat.format(startMinute), simpleDateFormat.format(endMinute));

            List<Map<String, Object>> endBeginOils = beginPeriodOilRepository.findByShipIdAndPeriodTime(shipId, simpleDateFormat.format(endStartMionute), simpleDateFormat.format(endEndMionute));

            CalculateDcsOilVo startOilCons = getOilCons(startBeginOils);

            CalculateDcsOilVo endOilCons = getOilCons(endBeginOils);
            //TODO: 第二瓶颈所在
            List<OiShipVoyage> oiShipVoyages = getOiShipVoyages(shipId, simpleDateFormat, collectPreYearVoyage, collectPrenextYearVoyage, preYearStartTime, nextYearEndTime, startOilCons, endOilCons);
            //TODO: 第三瓶颈所在,zxh改造，在这里计算出统计周期内所有的驳油渣记录
            List<RawVoyageSludge> sludgeCons = rawVoyageSludgeoilRepository.getSludgeList(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));
            List<RawVoyageSludge> collectSluageOut = sludgeCons.stream().filter(x -> {
                String portinfoId = x.getPortinfoId();
                Date sludgeOutTm = x.getSludgeOutTm();
                RawVoyagePort byIdAndIsDelete = rawVoyagePortRepository.findByIdAndIsDelete(portinfoId, 0);
                if (byIdAndIsDelete != null) {
                    BigDecimal arrZone = byIdAndIsDelete.getArrZone();
                    Date date = DateUtils.localToUTC(sludgeOutTm, arrZone);
                    return date.getTime() >= startTime.getTime() && date.getTime() <= endTime.getTime();
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            imoStdrptSave.setStartTm(startTime);

            imoStdrptSave.setEndTm(endTime);

            imoStdrptSave.setIsDelete(0);

            imoStdrptSave.setRecStatus("1");
            //船旗国变更记录
            List<FlagDocChange> flagChange =
                    flagDocChangeRepository.findByImoAndIsDeleteOrderByEffectiveDateDesc(oiShipInfo.getImono(), "1");
            //查询doc变更记录
            List<FlagDocChange> docChange =
                    flagDocChangeRepository.findByImoAndIsDeleteOrderByEffectiveDateDesc(oiShipInfo.getImono(), "2");
            if (flagChange != null && flagChange.size() > 0) {
                FlagDocChange flagDocChange = flagChange.get(0);
                String flagThreeCodeOld = flagDocChange.getFlagThreeCodeOld();
                String flagThreeCodeNew = flagDocChange.getFlagThreeCodeNew();
                if(StringUtils.isNullOrEmpty(flagThreeCodeOld)){
                    flagThreeCodeOld = oiShipInfo.getFlagCode();
                }
                if(StringUtils.isNullOrEmpty(flagThreeCodeNew)){
                    flagThreeCodeNew = oiShipInfo.getFlagCode();
                }
                Date effectiveDate = flagDocChange.getEffectiveDate();
                if(effectiveDate != null && effectiveDate.compareTo(endTime)>0){
                    imoStdrptSave.setCountryFlag(flagThreeCodeOld);
                }else {
                    imoStdrptSave.setCountryFlag(flagThreeCodeNew);
                }
            } else {
                imoStdrptSave.setCountryFlag(oiShipInfo.getFlagCode());
            }

            if (docChange != null && docChange.size() > 0) {
                FlagDocChange flagDocChange = docChange.get(0);
                Date effectiveDate = flagDocChange.getEffectiveDate();
                String docNew = flagDocChange.getDocNew();
                String newDocName = null;
                String docOld = flagDocChange.getDocOld();
                String oldDocName = null;
                if(StringUtils.isNullOrEmpty(docOld)){
                    docOld = oiShipInfo.getDocIacs();
                }else {
                    List<GcClient> byCodeList = gcClientRepository.findByCode(docOld);
                    if(byCodeList!= null && byCodeList.size()>0){
                        GcClient gcClient = byCodeList.get(0);
                        oldDocName = gcClient.getNameCn();
                    }
                }
                if(StringUtils.isNullOrEmpty(docNew)){
                    docNew = oiShipInfo.getDocIacs();
                }else {
                    List<GcClient> byCodeList = gcClientRepository.findByCode(docNew);
                    if(byCodeList!= null && byCodeList.size()>0){
                        GcClient gcClient = byCodeList.get(0);
                        newDocName = gcClient.getNameCn();
                    }
                }
                if(StringUtils.isNullOrEmpty(oldDocName)){
                    oldDocName = oiShipInfo.getDocmanager();
                }
                if(StringUtils.isNullOrEmpty(newDocName)){
                    newDocName = oiShipInfo.getDocmanager();
                }
                if(effectiveDate != null && effectiveDate.compareTo(endTime)>0){
                    imoStdrptSave.setDocIacs(docOld);
                    imoStdrptSave.setDocName(oldDocName);
                }else {
                    imoStdrptSave.setDocIacs(docNew);
                    imoStdrptSave.setDocName(newDocName);
                }

            } else {
                imoStdrptSave.setDocIacs(oiShipInfo.getDocIacs());

                imoStdrptSave.setDocName(oiShipInfo.getDocmanager());
            }

            imoStdrptSave.setRptSource("1");

            String refCode = UUID.randomUUID().toString().replace("-", "");

            imoStdrptSave.setRefCode(refCode);

            imoStdrptSave.setDwTons(oiShipInfo.getDw());

            StringBuilder gePower = new StringBuilder("");

            StringBuilder mainPower = new StringBuilder("");

            List<OiShipGe> oiShipGes = oiShipInfo.getOiShipGes();

            List<OiMainEngine> oiMainEngines = oiShipInfo.getOiMainEngines();

            for (int i = 0; i < oiShipGes.size(); i++) {

                OiShipGe oiShipGe = oiShipGes.get(i);

                if (i == oiShipGes.size() - 1) {

                    gePower.append(oiShipGe.getRatePower());

                } else {

                    gePower.append(oiShipGe.getRatePower() + ",");

                }

            }

            imoStdrptSave.setGePower(gePower.toString());

            for (int i = 0; i < oiMainEngines.size(); i++) {

                OiMainEngine oiMainEngine = oiMainEngines.get(i);

                if (i == oiMainEngines.size() - 1) {

                    mainPower.append(oiMainEngine.getRatePower());

                } else {

                    mainPower.append(oiMainEngine.getRatePower() + ",");

                }

            }

            imoStdrptSave.setMePower(mainPower.toString());

            imoStdrptSave.setEediVal(oiShipInfo.getEedivalue());

            imoStdrptSave.setGrossTons(oiShipInfo.getGross());

            imoStdrptSave.setNetTons(oiShipInfo.getNet());

            imoStdrptSave.setIceClass(oiShipInfo.getIce());

            imoStdrptSave.setShipId(oiShipInfo.getId());

            imoStdrptSave.setShipName(oiShipInfo.getSpname());

            imoStdrptSave.setSpType(oiShipInfo.getSptype());

            imoStdrptSave.setImono(oiShipInfo.getImono());

            if (imoStdrptSave.getId() == null) {
                imoStdrptSave.setOpdate(new Date());

                imoStdrptSave.setOpuser(this.getUserName());

                imoStdrptSave.setCreateDate(new Date());

                imoStdrptSave.setCreator(this.getUserName());
            } else {
                imoStdrptSave.setOpdate(new Date());

                imoStdrptSave.setOpuser(this.getUserName());
            }

            ImoStdrpt imoSave = imoStdrptRepository.save(imoStdrptSave);

            String imoStdRptId = imoSave.getId();

            //填充修正量

            if (imoStdrpt == null) {

                ImoStdrptAdjust imoStdrptAdjust = new ImoStdrptAdjust();

                imoStdrptAdjust.setIsDelete(0);

                BigDecimal zero = new BigDecimal(0);

                imoStdrptAdjust.setHourOnsea(zero);

                imoStdrptAdjust.setDistOnsea(zero);

                imoStdrptAdjust.setImono(oiShipInfo.getImono());

                imoStdrptAdjust.setRefCode(refCode);

                imoStdrptAdjust.setRptSource("1");

                imoStdrptAdjust.setRptId(imoStdRptId);

                ImoStdrptAdjust save = imoStdrptAdjustRepository.save(imoStdrptAdjust);

                String adjustId = save.getId();

                List<ImoCorrectionOil> imoCorrectionOils = Arrays.asList(new ImoCorrectionOil(adjustId, FuelConst.HFO_HIGHT, FuelConst.HFO_HIGHT_NAME, zero, "0", 0)

                        , new ImoCorrectionOil(adjustId, FuelConst.LFO_HIGHT, FuelConst.LFO_HIGHT_NAME, zero, "0", 0)

                        , new ImoCorrectionOil(adjustId, FuelConst.CHAI_CODE, FuelConst.CHAI_CODE_NAME, zero, "0", 0)

                        , new ImoCorrectionOil(adjustId, FuelConst.TIAN_CODE, FuelConst.TIAN_CODE_NAME, zero, "0", 0)

                        , new ImoCorrectionOil(adjustId, FuelConst.BING_CODE, FuelConst.BING_NAME, zero, "0", 0)

                        , new ImoCorrectionOil(adjustId, FuelConst.DING_CODE, FuelConst.DING_NAME, zero, "0", 0)

                        , new ImoCorrectionOil(adjustId, FuelConst.METHAN_CODE, FuelConst.METHAN_NAME, zero, "0", 0)

                        , new ImoCorrectionOil(adjustId, FuelConst.OIETHAN_CODE, FuelConst.OIETHAN_NAME, zero, "0", 0));

                imoCorrectionOilRepository.saveAll(imoCorrectionOils);

                imoStdrptAdjust.setImoCorrectionOils(imoCorrectionOils);

            }

            //获取collection data数据
            //TODO: 最大瓶劲所在，标准报告的数据应该也可以用这个imodataSummary对象，collect data和标准报告的算法与数据要同源
            List<ImoDataSummary> imoDataSummary = getImoDataSummary(noAcrossVoyage, oiShipVoyages, oiShipInfo, collectSluageOut, imoStdRptId, useMethod, collectPreYearVoyage,
                    collectPrenextYearVoyage, preYearStartTime, nextYearEndTime, startOilCons, endOilCons, shipId);

            BigDecimal distance = imoDataSummary.stream().map(ImoDataSummary::getDistOnsea).reduce(BigDecimal.ZERO, BigDecimal::add);//获取全年的航程

            imoSave.setDistOnsea(distance);

            BigDecimal distanceTime = imoDataSummary.stream().map(ImoDataSummary::getHourOnsea).reduce(BigDecimal.ZERO, BigDecimal::add);//航行时间

            imoSave.setHourOnsea(distanceTime.setScale(2, BigDecimal.ROUND_HALF_UP));

            imoStdrpt = imoStdrptRepository.save(imoSave);

            BigDecimal hfo = new BigDecimal(0);

            BigDecimal lfo = new BigDecimal(0);

            BigDecimal chai = new BigDecimal(0);

            BigDecimal propane = new BigDecimal(0);

            BigDecimal butane = new BigDecimal(0);

            BigDecimal methanol = new BigDecimal(0);

            BigDecimal ethanol = new BigDecimal(0);

            BigDecimal lng = new BigDecimal(0);

            List<ImoOil> imoOils = new ArrayList<>();

            for (int i = 0; i < imoDataSummary.size(); i++) {

                ImoDataSummary imoDataSummary1 = imoDataSummary.get(i);

                List<CdOil> cdOils = imoDataSummary1.getCdOils();

                for (int j = 0; j < cdOils.size(); j++) {

                    CdOil cdOil = cdOils.get(j);

                    if (FuelConst.HFO_HIGHT.equals(cdOil.getOilId())) {

                        hfo = hfo.add(cdOil.getFuelTons());

                    }

                    if (FuelConst.LFO_HIGHT.equals(cdOil.getOilId())) {

                        lfo = lfo.add(cdOil.getFuelTons());

                    }

                    if (FuelConst.CHAI_CODE.equals(cdOil.getOilId())) {

                        chai = chai.add(cdOil.getFuelTons());

                    }

                    if (FuelConst.BING_CODE.equals(cdOil.getOilId())) {

                        propane = propane.add(cdOil.getFuelTons());

                    }

                    if (FuelConst.DING_CODE.equals(cdOil.getOilId())) {

                        butane = butane.add(cdOil.getFuelTons());

                    }

                    if (FuelConst.METHAN_CODE.equals(cdOil.getOilId())) {

                        methanol = methanol.add(cdOil.getFuelTons());

                    }

                    if (FuelConst.OIETHAN_CODE.equals(cdOil.getOilId())) {

                        ethanol = ethanol.add(cdOil.getFuelTons());

                    }

                    if (FuelConst.TIAN_CODE.equals(cdOil.getOilId())) {

                        lng = lng.add(cdOil.getFuelTons());

                    }

                }

            }

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.HFO_HIGHT, FuelConst.HFO_HIGHT_NAME, useMethod.get(FuelConst.HFO_HIGHT), hfo, "0", 0));

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.LFO_HIGHT, FuelConst.LFO_HIGHT_NAME, useMethod.get(FuelConst.LFO_HIGHT), lfo, "0", 0));

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.CHAI_CODE, FuelConst.CHAI_CODE_NAME, useMethod.get(FuelConst.CHAI_CODE), chai, "0", 0));

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.TIAN_CODE, FuelConst.TIAN_CODE_NAME, useMethod.get(FuelConst.TIAN_CODE), lng, "0", 0));

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.BING_CODE, FuelConst.BING_NAME, useMethod.get(FuelConst.BING_CODE), propane, "0", 0));

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.DING_CODE, FuelConst.DING_NAME, useMethod.get(FuelConst.DING_CODE), butane, "0", 0));

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.METHAN_CODE, FuelConst.METHAN_NAME, useMethod.get(FuelConst.METHAN_CODE), methanol, "0", 0));

            imoOils.add(new ImoOil(imoStdRptId, FuelConst.OIETHAN_CODE, FuelConst.OIETHAN_NAME, useMethod.get(FuelConst.OIETHAN_CODE), ethanol, "0", 0));

            imoOilRepository.saveAll(imoOils);

            imoStdrpt.setImoOils(imoOils);

            //获取bdn数据

            List<ImoBdnSummary> imoBdnSummaries = new ArrayList<>();

            List<RawVoyageAddoil> rawVoyageAddoils = rawVoyageAddoilRepository.getBdnOil(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));//获取加油量
            //根据时区筛选加油量，zxh改造，在这里计算出统计周期内所有的加油记录
            List<RawVoyageAddoil> collectAddOil = rawVoyageAddoils.stream().filter(x -> {
                String portinfoId = x.getPortinfoId();
                Date addTm = x.getAddTm();
                RawVoyagePort rawVoyagePort = rawVoyagePortRepository.findByIdAndIsDelete(portinfoId, 0);
                if (rawVoyagePort != null) {
                    BigDecimal arrZone = rawVoyagePort.getArrZone();
                    Date dateUtc = DateUtils.localToUTC(addTm, arrZone);
                    return dateUtc.getTime() >= startTime.getTime() && dateUtc.getTime() <= endTime.getTime();
                } else {
                    return false;
                }
            }).collect(Collectors.toList());

            List<RawVoyageOutoil> outOils = rawVoyageOutoilRepository.getOutOils(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));//获取驳油量
            //筛选驳出量，zxh改造，在这里计算出统计周期内所有的驳油渣记录，
            List<RawVoyageOutoil> collectOutOil = outOils.stream().filter(x -> {
                String portinfoId = x.getPortinfoId();
                Date outTm = x.getOutTm();
                RawVoyagePort rawVoyagePort = rawVoyagePortRepository.findByIdAndIsDelete(portinfoId, 0);
                if (rawVoyagePort != null) {
                    BigDecimal arrZone = rawVoyagePort.getArrZone();
                    Date date = DateUtils.localToUTC(outTm, arrZone);
                    return date.getTime() >= startTime.getTime() && date.getTime() <= endTime.getTime();
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            //筛选驳油渣量。zxh 没有用到，20210320注掉，已经有collectSluageOut了
            //List<RawVoyageSludge> outSluageOils = rawVoyageSludgeoilRepository.getSludgeList(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));//获取驳油渣量

            //填充修正量数据 todo
            for (int i = 0; i < collectOutOil.size(); i++) {
                RawVoyageOutoil rawVoyageOutoil = collectOutOil.get(i);
                Date outTm = rawVoyageOutoil.getOutTm();
                String portinfoId = rawVoyageOutoil.getPortinfoId();
                Date date = coverAddTime(portinfoId, outTm);
                ImoBdnSummary imoBdnSummary = new ImoBdnSummary();

                imoBdnSummary.setRecStatus("1");

                imoBdnSummary.setIsDelete(0);

                imoBdnSummary.setCountryFlag(oiShipInfo.getFlagCode());

                imoBdnSummary.setDocIcas(oiShipInfo.getDocIacs());

                imoBdnSummary.setDocName(oiShipInfo.getDocmanager());

                imoBdnSummary.setImono(oiShipInfo.getImono());

                imoBdnSummary.setShipName(oiShipInfo.getSpname());

                imoBdnSummary.setStartTm(date);

                imoBdnSummary.setRecType(2);

                imoBdnSummary.setRptId(imoStdRptId);

                ImoBdnSummary save = imoBdnSummaryRepository.save(imoBdnSummary);

                BdnOil bdnOil = new BdnOil(save.getId(), rawVoyageOutoil.getOilId(), rawVoyageOutoil.getOilName(), FuelConst.FUEL_METHOD, rawVoyageOutoil.getOutTons(), "0", 0);

                bdnOilRepository.save(bdnOil);

            }

            for (int i = 0; i < collectSluageOut.size(); i++) {

                RawVoyageSludge rawVoyageSludge = collectSluageOut.get(i);
                Date outTm = rawVoyageSludge.getSludgeOutTm();
//                String portinfoId = rawVoyageSludge.getPortinfoId();
                //zxh 不再计算UTC日期，前面已经计算过了
//                Date date = coverAddTime(portinfoId, outTm);
                ImoBdnSummary imoBdnSummary = new ImoBdnSummary();

                imoBdnSummary.setRecStatus("1");

                imoBdnSummary.setIsDelete(0);

                imoBdnSummary.setCountryFlag(oiShipInfo.getFlagCode());

                imoBdnSummary.setDocIcas(oiShipInfo.getDocIacs());

                imoBdnSummary.setDocName(oiShipInfo.getDocmanager());

                imoBdnSummary.setImono(oiShipInfo.getImono());

                imoBdnSummary.setShipName(oiShipInfo.getSpname());

                imoBdnSummary.setStartTm(outTm);

                imoBdnSummary.setRecType(2);

                imoBdnSummary.setRptId(imoStdRptId);

                ImoBdnSummary save = imoBdnSummaryRepository.save(imoBdnSummary);

                BdnOil bdnOil = new BdnOil(save.getId(), rawVoyageSludge.getOilId(), null, FuelConst.FUEL_METHOD, rawVoyageSludge.getSludgeTons(), "0", 0);
                bdnOilRepository.save(bdnOil);

            }

            //填充bdn数据

            for (int i = 0; i < collectAddOil.size(); i++) {

                RawVoyageAddoil rawVoyageAddoil = collectAddOil.get(i);
                Date addTm = rawVoyageAddoil.getAddTm();
                String portinfoId = rawVoyageAddoil.getPortinfoId();
                Date date = coverAddTime(portinfoId, addTm);
                ImoBdnSummary imoBdnSummary = new ImoBdnSummary();

                imoBdnSummary.setRecStatus("1");

                imoBdnSummary.setIsDelete(0);

                imoBdnSummary.setCountryFlag(oiShipInfo.getFlagCode());

                imoBdnSummary.setDocIcas(oiShipInfo.getDocIacs());

                imoBdnSummary.setDocName(oiShipInfo.getDocmanager());

                imoBdnSummary.setImono(oiShipInfo.getImono());

                imoBdnSummary.setShipName(oiShipInfo.getSpname());

                imoBdnSummary.setStartTm(date);

                imoBdnSummary.setRecType(0);

                imoBdnSummary.setRptId(imoStdRptId);

                ImoBdnSummary save = imoBdnSummaryRepository.save(imoBdnSummary);

                BdnOil bdnOil = new BdnOil(save.getId(), rawVoyageAddoil.getOilId(), rawVoyageAddoil.getOilName(), FuelConst.FUEL_METHOD, rawVoyageAddoil.getAddTons(), "0", 0);

                bdnOilRepository.save(bdnOil);

            }

            List<CalculateDcsOilVo> calculateDcsOilVos = Arrays.asList(startOilCons, endOilCons);

            for (int i = 0; i < calculateDcsOilVos.size(); i++) {

                CalculateDcsOilVo calculateDcsOilVo = calculateDcsOilVos.get(i);

                BigDecimal bdnHfo = new BigDecimal(0);

                BigDecimal bdnLfo = new BigDecimal(0);

                bdnHfo = calculateDcsOilVo.getHfoHight().add(calculateDcsOilVo.getHfoLow()).add(calculateDcsOilVo.getHfoSuperLow());

                bdnLfo = calculateDcsOilVo.getLfoHight().add(calculateDcsOilVo.getLfoLow()).add(calculateDcsOilVo.getLfoSuperLow());

                ImoBdnSummary imoBdnSummary = new ImoBdnSummary();

                imoBdnSummary.setRecStatus("1");

                imoBdnSummary.setIsDelete(0);

                imoBdnSummary.setCountryFlag(oiShipInfo.getFlagCode());

                imoBdnSummary.setDocIcas(oiShipInfo.getDocIacs());

                imoBdnSummary.setDocName(oiShipInfo.getDocmanager());

                imoBdnSummary.setImono(oiShipInfo.getImono());

                imoBdnSummary.setShipName(oiShipInfo.getSpname());

                if (i == 0) {

                    imoBdnSummary.setStartTm(startTime);

                }

                if (i == 1) {

                    imoBdnSummary.setStartTm(endTime);

                }

                imoBdnSummary.setRecType(1);

                imoBdnSummary.setRptId(imoStdRptId);

                ImoBdnSummary save = imoBdnSummaryRepository.save(imoBdnSummary);

                String bdnId = save.getId();

                List<BdnOil> bdnOils = Arrays.asList(new BdnOil(bdnId, FuelConst.HFO_HIGHT, FuelConst.HFO_HIGHT_NAME, useMethod.get(FuelConst.HFO_HIGHT), bdnHfo, "0", 0)

                        , new BdnOil(bdnId, FuelConst.LFO_HIGHT, FuelConst.LFO_HIGHT_NAME, useMethod.get(FuelConst.LFO_HIGHT), bdnLfo, "0", 0)

                        , new BdnOil(bdnId, FuelConst.CHAI_CODE, FuelConst.CHAI_CODE_NAME, useMethod.get(FuelConst.CHAI_CODE), calculateDcsOilVo.getChaiFo(), "0", 0)

                        , new BdnOil(bdnId, FuelConst.TIAN_CODE, FuelConst.TIAN_CODE_NAME, useMethod.get(FuelConst.TIAN_CODE), calculateDcsOilVo.getLng(), "0", 0)

                        , new BdnOil(bdnId, FuelConst.BING_CODE, FuelConst.BING_NAME, useMethod.get(FuelConst.BING_CODE), calculateDcsOilVo.getPropane(), "0", 0)

                        , new BdnOil(bdnId, FuelConst.DING_CODE, FuelConst.DING_NAME, useMethod.get(FuelConst.DING_CODE), calculateDcsOilVo.getButane(), "0", 0)

                        , new BdnOil(bdnId, FuelConst.METHAN_CODE, FuelConst.METHAN_NAME, useMethod.get(FuelConst.METHAN_CODE), calculateDcsOilVo.getMethanol(), "0", 0)

                        , new BdnOil(bdnId, FuelConst.OIETHAN_CODE, FuelConst.OIETHAN_NAME, useMethod.get(FuelConst.OIETHAN_CODE), calculateDcsOilVo.getEthanol(), "0", 0));

                bdnOilRepository.saveAll(bdnOils);

            }

        }

        ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(imoStdrpt.getId(), 0);

        if (null != imoStdrptAdjust) {

            imoRptVo.setCorrectionDistance(imoStdrptAdjust.getDistOnsea());

            imoRptVo.setCorrectionDistanceHour(imoStdrptAdjust.getHourOnsea());

            List<ImoCorrectionOil> imoCorrectionOils = imoStdrptAdjust.getImoCorrectionOils();

            imoCorrectionOils.forEach(imoCorrectionOil -> {

                if (FuelConst.HFO_HIGHT.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionHfo(imoCorrectionOil.getFuelTons());

                }

                if (FuelConst.LFO_HIGHT.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionLfo(imoCorrectionOil.getFuelTons());

                }

                if (FuelConst.CHAI_CODE.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionChaiFo(imoCorrectionOil.getFuelTons());

                }

                if (FuelConst.TIAN_CODE.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionLng(imoCorrectionOil.getFuelTons());

                }

                if (FuelConst.BING_CODE.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionPropane(imoCorrectionOil.getFuelTons());

                }

                if (FuelConst.DING_CODE.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionButane(imoCorrectionOil.getFuelTons());

                }

                if (FuelConst.METHAN_CODE.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionMethanol(imoCorrectionOil.getFuelTons());

                }

                if (FuelConst.OIETHAN_CODE.equals(imoCorrectionOil.getOilId())) {

                    imoRptVo.setCorrectionEthanol(imoCorrectionOil.getFuelTons());

                }

            });

        }

        Map<String, String> mapCn = fuelConsMethodProperties.getMapCn();

        imoRptVo.setImoRptId(imoStdrpt.getId());

        imoStdrpt.getImoOils().forEach(imoOil -> {

            if (FuelConst.HFO_HIGHT.equals(imoOil.getOilId())) {

                imoRptVo.setHfo(imoOil.getFuelTons());

            }

            if (FuelConst.LFO_HIGHT.equals(imoOil.getOilId())) {

                imoRptVo.setLfo(imoOil.getFuelTons());

            }

            if (FuelConst.CHAI_CODE.equals(imoOil.getOilId())) {

                imoRptVo.setChaiFo(imoOil.getFuelTons());

            }

            if (FuelConst.TIAN_CODE.equals(imoOil.getOilId())) {

                imoRptVo.setLng(imoOil.getFuelTons());

            }

            if (FuelConst.BING_CODE.equals(imoOil.getOilId())) {

                imoRptVo.setPropane(imoOil.getFuelTons());

            }

            if (FuelConst.DING_CODE.equals(imoOil.getOilId())) {

                imoRptVo.setButane(imoOil.getFuelTons());

            }

            if (FuelConst.METHAN_CODE.equals(imoOil.getOilId())) {

                imoRptVo.setMethanol(imoOil.getFuelTons());

            }

            if (FuelConst.OIETHAN_CODE.equals(imoOil.getOilId())) {

                imoRptVo.setEthanol(imoOil.getFuelTons());

            }

        });
        String shipOilMethod = getEnOrCnConsMethod(mapCn, shipId);
        imoRptVo.setMethodUse(shipOilMethod == null || shipOilMethod.equals("") ? "BDN" : shipOilMethod);

        imoRptVo.setDistance(imoStdrpt.getDistOnsea());

        imoRptVo.setDistanceHour(imoStdrpt.getHourOnsea());

        imoRptVo.setGross(imoStdrpt.getGrossTons());

        imoRptVo.setNet(imoStdrpt.getNetTons());

        imoRptVo.setDwt(imoStdrpt.getDwTons());

        BigDecimal eediVal = imoStdrpt.getEediVal();
        if (eediVal == null || (eediVal.compareTo(BigDecimal.ZERO)) == 0) {
            imoRptVo.setEedi("N/A");
        } else {
            imoRptVo.setEedi(eediVal.toString());
        }

        imoRptVo.setIce(imoStdrpt.getIceClass());

        imoRptVo.setStartTime(imoStdrpt.getStartTm());

        imoRptVo.setEndTime(imoStdrpt.getEndTm());

        imoRptVo.setImoNu(imoStdrpt.getImono());

        imoRptVo.setRecStatus(recStatus);
        List<String> oiMainEngines = new ArrayList<>();
        String mePower = imoStdrpt.getMePower();
        if (StringUtils.isNotEmpty(mePower)) {
            oiMainEngines = Arrays.asList(mePower.split(","));
        }
        String gePower = imoStdrpt.getGePower();
        List<String> oiShipGes = new ArrayList<>();
        if (StringUtils.isNotEmpty(gePower)) {
            oiShipGes = Arrays.asList(gePower.split(","));
        }
        List<String> mainAndGe = getMainAndGe(oiShipGes, oiMainEngines);

        imoRptVo.setMainEngines(mainAndGe.get(0));

        imoRptVo.setAuxEngines(mainAndGe.get(1));

        ShipType shipType = shipTypeRepository.findShipType(imoStdrpt.getSpType());

        imoRptVo.setShipType(shipType == null ? null : shipType.getCsptype());

        return new ResultObjectVo<>(imoRptVo);

    }

    private List<OiShipVoyage> getOiShipVoyages(List<OiShipTask> oiShipTasks, String voyageType) {
        //生成航段信息
        List<OiShipVoyage> oiShipVoyageGeners = new ArrayList<>();
        List<RawVoyagePort> rawVoyagePorts = null;
        List<RawVoyagePort> rawVoyagePortsNext = null;
        List<RawVoyageSpec> rawVoyageSpecs = null;
        for (OiShipTask oiShipTask : oiShipTasks) {
            rawVoyagePorts = oiShipTask.getRawVoyagePorts();
            rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
            rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();
            rawVoyagePorts.addAll(rawVoyagePortsNext);
            if (rawVoyagePorts == null || rawVoyagePorts.size() < 2) {
                continue;
            }
            rawVoyagePorts.sort((x, y) -> {
                if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                    if (x.getCreateTm() == null || y.getCreateTm() == null) {
                        return 1;
                    }
                    return x.getCreateTm().compareTo(y.getCreateTm());
                } else {
                    return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                }
            });
            if (!"0".equals(rawVoyagePorts.get(0).getInPort())) {
                continue;
            }
            if (!"0".equals(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getInPort())) {
                continue;
            }
            //拆分航段
            if (oiShipTask.getTask().equals("渤中工区-1")) {
                System.out.print(1);
            }
            List<RawVoyagePort> rawVoyagePortsMergeNext = energyEefficService.mergeHandoverPort(rawVoyagePorts);
            List<RawVoyagePort> rawVoyagePortsMerge = energyEefficService.procVoyageByMergePorts(rawVoyagePortsMergeNext);
            Map<String, List<List<RawVoyagePort>>> euSegmentsSAndImoASegments = getEuSegmentsSAndImoASegments(rawVoyagePortsMerge);
            List<List<RawVoyagePort>> imoSements = euSegmentsSAndImoASegments.get(voyageType);
            for (int i = 0; i < imoSements.size(); i++) {
                List<RawVoyagePort> rawVoyagePorts1 = imoSements.get(i);
                OiShipVoyage oiShipVoyage = energyEefficService.generatorAcrossInfo(rawVoyagePorts1, rawVoyageSpecs);
                oiShipVoyageGeners.add(oiShipVoyage);
            }
        }
        return oiShipVoyageGeners;
    }

    private List<OiShipVoyage> getOiShipVoyagesEu(List<OiShipTask> oiShipTasks, String voyageType, Date startTime, Date endTime) {
        //生成航段信息
        List<OiShipVoyage> oiShipVoyageGeners = new ArrayList<>();
        List<RawVoyagePort> rawVoyagePortsAcrossYear = new ArrayList<>();
        CalculateOilVo stopPre = null;
        CalculateOilVo stopNext = null;
        //筛选出节点跨年的港口
        double prePercent = 0.0;
        double nextPercent = 0.0;
        for (OiShipTask oiShipTask : oiShipTasks) {
            List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
            List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
            List<RawVoyageSpec> rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();
            rawVoyagePorts.addAll(rawVoyagePortsNext);
            rawVoyagePorts.sort((x, y) -> {
                if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                    return x.getCreateTm().compareTo(y.getCreateTm());
                } else {
                    return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                }
            });
            if (rawVoyagePorts.size() < 2) {
                continue;
            }
            if (!"0".equals(rawVoyagePorts.get(0).getInPort())) {
                continue;
            }
            if (!"0".equals(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getInPort())) {
                continue;
            }
            //处理交接港口合并
            List<RawVoyagePort> rawVoyagePortsMerger = energyEefficService.mergeHandoverPort(rawVoyagePorts);
            //合并港口
            List<RawVoyagePort> rawVoyagePortsMerge = energyEefficService.procVoyageByMergePorts(rawVoyagePortsMerger);

            List<RawVoyagePort> collect = rawVoyagePortsMerge.stream().filter(rawVoyagePort -> {
                Map<String, Date> utcTimeByPort = getUtcTimeByPort(rawVoyagePort);
                Date arr = utcTimeByPort.get("arr");
                Date dept = utcTimeByPort.get("dept");
                return (startTime.getTime() >= arr.getTime() && startTime.getTime() <= dept.getTime()) || (endTime.getTime() >= arr.getTime() && endTime.getTime() <= dept.getTime());
            }).collect(Collectors.toList());
            if (collect != null && collect.size() > 0) {
                RawVoyagePort rawVoyagePort = new RawVoyagePort();
                rawVoyagePort.setInPort("0");
                collect.add(0, rawVoyagePort);
                //计算比例
//                RawVoyagePort rawVoyagePortAcross = collect.get(1);
//                Map<String, Date> utcTimeByPort = getUtcTimeByPort(rawVoyagePortAcross);
//                Date arr = utcTimeByPort.get("arr");
//                Date dept = utcTimeByPort.get("dept");
//                if (startTime.getTime() >= arr.getTime() && startTime.getTime() <= dept.getTime()) {
//                    prePercent = ((double) (dept.getTime() - startTime.getTime()) / (double) (dept.getTime() - arr.getTime()));
//                    stopPre = energyEefficService.calculateSailingOil(collect, "stop");
//                }
//                if (endTime.getTime() >= arr.getTime() && endTime.getTime() <= dept.getTime()) {
//                    nextPercent = 1 - (double) (endTime.getTime() - arr.getTime()) / (double) (dept.getTime() - arr.getTime());
//                    stopNext = energyEefficService.calculateSailingOil(collect, "stop");
//                }
            }
            //拆分航段
            Map<String, List<List<RawVoyagePort>>> euSegmentsSAndImoASegments = getEuSegmentsSAndImoASegments(rawVoyagePortsMerge);
            List<List<RawVoyagePort>> imoSements = euSegmentsSAndImoASegments.get(voyageType);
            for (int i = 0; i < imoSements.size(); i++) {
                List<RawVoyagePort> rawVoyagePorts1 = imoSements.get(i);
                if (oiShipTask.getTask().equals("2017-05")) {
                    int ppp1 = 1;
                }
                OiShipVoyage oiShipVoyage = energyEefficService.generatorAcrossInfoEu(rawVoyagePorts1, rawVoyageSpecs, startTime, endTime);
                oiShipVoyageGeners.add(oiShipVoyage);
            }
        }
        //计算停港油耗
        //获取第一个航段将停港油耗合并
//        if (oiShipVoyageGeners.size() > 0 && stopNext != null) {
//            OiShipVoyage oiShipVoyage = oiShipVoyageGeners.get(0);
//            oiShipVoyage.setStopethanol(oiShipVoyage.getStopethanol().add(stopPre.getOiethanol().multiply(BigDecimal.valueOf(prePercent))).subtract(stopNext.getOiethanol().multiply(BigDecimal.valueOf(nextPercent))));
//            oiShipVoyage.setStopoiChai(oiShipVoyage.getStopoiChai().add(stopPre.getOiChai().multiply(BigDecimal.valueOf(prePercent))).subtract(stopNext.getOiChai().multiply(BigDecimal.valueOf(nextPercent))));
//            oiShipVoyage.setStopoiBing(oiShipVoyage.getStopoiBing().add(stopPre.getOiBing().multiply(BigDecimal.valueOf(prePercent))).subtract(stopNext.getOiBing().multiply(BigDecimal.valueOf(nextPercent))));
//            oiShipVoyage.setStopoiHfo(oiShipVoyage.getStopoiHfo().add(stopPre.getOiHfo().multiply(BigDecimal.valueOf(prePercent))).subtract(stopNext.getOiHfo().multiply(BigDecimal.valueOf(nextPercent))));
//            oiShipVoyage.setStopoiLfo(oiShipVoyage.getStopoiLfo().add(stopPre.getOiLfo().multiply(BigDecimal.valueOf(prePercent))).subtract(stopNext.getOiLfo().multiply(BigDecimal.valueOf(nextPercent))));
//        }
        return oiShipVoyageGeners;
    }

    /**
     * @param shipId                   船舶id
     * @param simpleDateFormat
     * @param collectPreYearVoyage     上跨年航段
     * @param collectPrenextYearVoyage 下跨年航段
     * @param preYearStartTime         上跨年航段开始时间
     * @param nextYearEndTime          下跨年航段结束时间
     * @param startOilCons             期初量
     * @param endOilCons               期末量
     * @return
     */
    private List<OiShipVoyage> getOiShipVoyages(String shipId, SimpleDateFormat simpleDateFormat, List<OiShipVoyage> collectPreYearVoyage, List<OiShipVoyage> collectPrenextYearVoyage, Date preYearStartTime, Date nextYearEndTime, CalculateDcsOilVo startOilCons, CalculateDcsOilVo endOilCons) {
        List<OiShipVoyage> oiShipVoyages = new ArrayList<>();
        if (collectPreYearVoyage.size() != 0) {
            OiShipVoyage preYearVoyage = collectPreYearVoyage.get(0);
            Date preYearEndTime = preYearVoyage.getEndTime();//上一年跨年航段结束时间
            Date startTime = preYearVoyage.getStartTime();//上一年跨年航段的开始时间
//            String endportid = preYearVoyage.getEndportid();
//            BigDecimal timeZone = BigDecimal.valueOf(0);
//            if (endportid != null) {
//                RawVoyagePort rawVoyagePort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
//                timeZone = rawVoyagePort.getDeptZone();
//            }
//            Date preYearEndTimeQuery = DateUtils.utcToLocal(preYearEndTime, timeZone);//查询条件
            //查询包含在跨年航段内的所有港口

            List<RawVoyagePort> byPreAcrossYear = rawVoyagePortRepository.findByAcrossYear(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(preYearEndTime));
            //合并港口
            List<RawVoyagePort> rawVoyagePortsMerge = energyEefficService.procVoyageByMergePorts(byPreAcrossYear);
            List<RawVoyagePort> noAcross = rawVoyagePortsMerge.stream().filter(x -> {
                Map<String, Date> utcTimeByPort = getUtcTimeByPort(x);
                Date arr = utcTimeByPort.get("arr");
                Date dept = utcTimeByPort.get("dept");
                return (arr.getTime() >= preYearStartTime.getTime() && arr.getTime() <= preYearEndTime.getTime()) || (dept.getTime() >= preYearStartTime.getTime() && dept.getTime() <= preYearEndTime.getTime());
            }).collect(Collectors.toList());
            //查询包含在跨年航段内的所有冰区航行和救援
            List<RawVoyageSpec> byPreRawSpecAcrossYear = rawVoyageSpecRepository.findByAcrossYear(shipId, simpleDateFormat.format(preYearStartTime), simpleDateFormat.format(preYearEndTime));
            //查询年末时间段是否在港
//            List<RawVoyagePort> endPreYearList = rawVoyagePortRepository.findEndYear(shipId, simpleDateFormat.format(preYearStartTime));
            List<RawVoyagePort> collectEndPreYear = rawVoyagePortsMerge.stream().filter(rawVoyagePort -> {
                Map<String, Date> utcTimeByPort = getUtcTimeByPort(rawVoyagePort);
                Date arr = utcTimeByPort.get("arr");
                Date dept = utcTimeByPort.get("dept");
                return preYearStartTime.getTime() >= arr.getTime() && preYearStartTime.getTime() <= dept.getTime();
            }).collect(Collectors.toList());
            RawVoyagePort endPreYear = null;
            if (collectEndPreYear.size() > 0) {
                endPreYear = collectEndPreYear.get(0);
            }
            //计算上一年跨年航段的信息
            List<RawVoyagePortloading> rawVoyagePortloadings = new ArrayList<>();
            OiShipVoyage oiShipVoyagePre = null;
            if (null == endPreYear) {
                //封装一个不存在的港口
                endPreYear = getAcrossRawPort(preYearStartTime, startOilCons, rawVoyagePortloadings, BigDecimal.ZERO);
                noAcross.add(0, endPreYear);
                //上一年的跨年航段
                oiShipVoyagePre = energyEefficService.generatorAcrossInfo(noAcross, byPreRawSpecAcrossYear);
            }
            //判断期初时间是否在港，并且是跨年航段的最后一个港口
            if (noAcross.size() == 1 && endPreYear != null) {
                //跨年时间段在港航段数据
                oiShipVoyagePre = getOiShipVoyageStopPort(preYearStartTime, startOilCons, preYearEndTime, endPreYear);
            }
            if (noAcross.size() > 1 && endPreYear != null) {
                RawVoyagePort rawVoyagePortNew = new RawVoyagePort();
                String recordType = rawVoyagePortNew.getRecordType();
                BeanUtils.copyProperties(endPreYear, rawVoyagePortNew);
                //跨年港口在港油耗
                CalculateOilVo calculateOilVoStop = getStopPortOil(rawVoyagePortNew, preYearStartTime, preYearEndTime, startOilCons);
                List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePortNew.getRawVoyagePortoils();
                //在港油耗加跨年港口的离港存量
                List<RawVoyagePortoil> rawVoyagePortoilsNew = new ArrayList<>();
                for (RawVoyagePortoil rawVoyagePortoil : rawVoyagePortoils) {
                    RawVoyagePortoil rawVoyagePortoilNew = new RawVoyagePortoil();
                    String oilId = rawVoyagePortoil.getOilId();
                    rawVoyagePortoilNew.setOilId(oilId);
//                    rawVoyagePortoil.setId(null);
                    BigDecimal deptTons = rawVoyagePortoil.getDeptTons();
                    if (deptTons == null) {
                        deptTons = BigDecimal.ZERO;
                    }
                    if (FuelConst.HFO_HIGHT.equals(oilId)) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiHfo());
                    }
                    if (FuelConst.LFO_HIGHT.equals(oilId)) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiLfo());
                    }
                    if (FuelConst.CHAI_CODE.equals(oilId)) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiChai());
                    }
                    if (FuelConst.BING_CODE.equals(oilId)) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiBing());
                    }
                    if (FuelConst.DING_CODE.equals(oilId)) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiDing());
                    }
                    if (FuelConst.TIAN_CODE.equals(rawVoyagePortoil.getOilId())) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiTian());
                    }
                    if (FuelConst.METHAN_CODE.equals(oilId)) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiOther());
                    }
                    if (FuelConst.OIETHAN_CODE.equals(oilId)) {
                        deptTons = deptTons.add(calculateOilVoStop.getOiethanol());
                    }
                    rawVoyagePortoilNew.setDeptTons(deptTons);
                    rawVoyagePortoilsNew.add(rawVoyagePortoilNew);
                }
                rawVoyagePortNew.setRecordType("0");//因为在港的港口有可能是港界外或者途中抛锚，必须设置为正常的港界内途径港才能计算航段
                rawVoyagePortNew.setInPort("0");
                rawVoyagePortNew.setId(null);
                rawVoyagePortNew.setRawVoyagePortoils(rawVoyagePortoilsNew);
                noAcross.set(0, rawVoyagePortNew);
                //上一年的跨年航段
                oiShipVoyagePre = energyEefficService.generatorAcrossInfo(noAcross, byPreRawSpecAcrossYear);
                oiShipVoyagePre.setStartTime(preYearStartTime);
            }
            oiShipVoyages.add(preYearVoyage);
            oiShipVoyages.add(oiShipVoyagePre);
        }
        if (collectPrenextYearVoyage.size() != 0) {
            OiShipVoyage nextYearVoyage = collectPrenextYearVoyage.get(0);
            String startportid = nextYearVoyage.getStartportid();
            Date nextYearStartTime = nextYearVoyage.getStartTime();//下一年跨年航段开始时间
//            RawVoyagePort startRawPort = rawVoyagePortRepository.findByIdAndIsDelete(startportid, 0);
//            Date dateLocal = DateUtils.utcToLocal(nextYearStartTime, startRawPort.getDeptZone());
            RawVoyagePort endRawVoyagePort = rawVoyagePortRepository.findByIdAndIsDelete(nextYearVoyage.getEndportid(), 0);
            Date endTime = nextYearVoyage.getEndTime();
            //查询包含在跨年航段内的所有港口
            List<RawVoyagePort> byNextAcrossYear = rawVoyagePortRepository.findByAcrossYear(shipId, simpleDateFormat.format(nextYearStartTime), simpleDateFormat.format(endTime));
            //合并港口
            List<RawVoyagePort> rawVoyagePortsMerge = energyEefficService.procVoyageByMergePorts(byNextAcrossYear);
            List<RawVoyagePort> noAcrossNext = rawVoyagePortsMerge.stream().filter(x -> {
                Map<String, Date> utcTimeByPort = getUtcTimeByPort(x);
                Date arr = utcTimeByPort.get("arr");
                Date dept = utcTimeByPort.get("dept");
                return (arr.getTime() >= nextYearStartTime.getTime() && arr.getTime() <= nextYearEndTime.getTime()) || (dept.getTime() >= nextYearStartTime.getTime() && dept.getTime() <= nextYearEndTime.getTime());
            }).collect(Collectors.toList());
            //查询包含在跨年航段内的所有冰区航行和救援
            List<RawVoyageSpec> byNextRawSpecAcrossYear = rawVoyageSpecRepository.findByAcrossYear(shipId, simpleDateFormat.format(nextYearStartTime), simpleDateFormat.format(nextYearEndTime));
            //查询年末时间段是否在港
//            List<RawVoyagePort> endYearList = rawVoyagePortRepository.findEndYear(shipId, simpleDateFormat.format(nextYearEndTime));
            List<RawVoyagePort> collectEndNextYear = rawVoyagePortsMerge.stream().filter(rawVoyagePort -> {
                Map<String, Date> utcTimeByPort = getUtcTimeByPort(rawVoyagePort);
                Date arr = utcTimeByPort.get("arr");
                Date dept = utcTimeByPort.get("dept");
                return nextYearEndTime.getTime() >= arr.getTime() && nextYearEndTime.getTime() <= dept.getTime();
            }).collect(Collectors.toList());
            RawVoyagePort endNextYear = null;
            if (collectEndNextYear.size() > 0) {
                endNextYear = collectEndNextYear.get(0);
            }
            //计算下一年跨年航段的信息
            OiShipVoyage oiShipVoyageNext = null;
            if (null == endNextYear) {
                endNextYear = getAcrossRawPort(nextYearEndTime, endOilCons, endRawVoyagePort.getRawVoyagePortloadings(), endRawVoyagePort.getDistance());
                noAcrossNext.add(endNextYear);
                noAcrossNext.sort((x, y) -> {
                    if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                        return x.getCreateTm().compareTo(y.getCreateTm());
                    } else {
                        return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                    }
                });
                oiShipVoyageNext = energyEefficService.generatorAcrossInfo(noAcrossNext, byNextRawSpecAcrossYear);
            } else {
                if (!(endNextYear.getRecordType().equals("0"))) {
                    RawVoyagePort rawVoyagePortNew = new RawVoyagePort();
                    rawVoyagePortNew.setInPort("0");
                    rawVoyagePortNew.setRecordType("0");
                    rawVoyagePortNew.setArrTm(endNextYear.getArrTm());
                    rawVoyagePortNew.setArrZone(endNextYear.getArrZone());
                    rawVoyagePortNew.setDeptTm(nextYearEndTime);
                    rawVoyagePortNew.setDeptZone(BigDecimal.ZERO);
                    rawVoyagePortNew.setRawVoyageAddoils(endNextYear.getRawVoyageAddoils());
                    rawVoyagePortNew.setRawVoyageOutoils(endNextYear.getRawVoyageOutoils());
                    rawVoyagePortNew.setRawVoyagePortoils(endNextYear.getRawVoyagePortoils());
                    rawVoyagePortNew.setRawVoyagePortloadings(endNextYear.getRawVoyagePortloadings());
                    rawVoyagePortNew.setRawVoyageSludges(endNextYear.getRawVoyageSludges());
                    noAcrossNext.set(noAcrossNext.size() - 1, rawVoyagePortNew);
                    oiShipVoyageNext = energyEefficService.generatorAcrossInfo(noAcrossNext, byNextRawSpecAcrossYear);
                    Date dateArr = DateUtils.localToUTC(endNextYear.getArrTm(), endNextYear.getArrZone());
                    CalculateOilVo calculateOilVoStop = getStopPortOilAnar(endNextYear, dateArr, nextYearEndTime, endOilCons);
                    oiShipVoyageNext.setStopoiHfo(calculateOilVoStop.getOiHfo());
                    oiShipVoyageNext.setStopoiLfo(calculateOilVoStop.getOiLfo());
                    oiShipVoyageNext.setStopoiChai(calculateOilVoStop.getOiChai());
                    oiShipVoyageNext.setStopoiBing(calculateOilVoStop.getOiBing());
                    oiShipVoyageNext.setStopoiDing(calculateOilVoStop.getOiDing());
                    oiShipVoyageNext.setStopoiTian(calculateOilVoStop.getOiTian());
                    oiShipVoyageNext.setStopethanol(calculateOilVoStop.getOiethanol());
                    oiShipVoyageNext.setStopoiOther(calculateOilVoStop.getOiOther());
//                    oiShipVoyageNext.setOiHfo(oiShipVoyageNext.getOiHfo().subtract(endOilCons.getHfoHight()).subtract(endOilCons.getHfoLow().subtract(endOilCons.getHfoSuperLow())));
//                    oiShipVoyageNext.setOiLfo(oiShipVoyageNext.getOiLfo().subtract(endOilCons.getLfoHight()).subtract(endOilCons.getLfoLow()).subtract(endOilCons.getLfoSuperLow()));
//                    oiShipVoyageNext.setOiChai(oiShipVoyageNext.getOiChai().subtract(endOilCons.getChaiFo()));
//                    oiShipVoyageNext.setOiBing(oiShipVoyageNext.getOiBing().subtract(endOilCons.getPropane()));
//                    oiShipVoyageNext.setOiDing(oiShipVoyageNext.getStopoiDing().subtract(endOilCons.getButane()));
//                    oiShipVoyageNext.setOiethanol(oiShipVoyageNext.getStopethanol().subtract(endOilCons.getEthanol()));
//                    oiShipVoyageNext.setOiOther(oiShipVoyageNext.getStopoiOther().subtract(endOilCons.getOther()));
//                    oiShipVoyageNext.setOiTian(oiShipVoyageNext.getStopoiTian().subtract(endOilCons.getLng()));
                } else {
                    //获取年末时间段到跨年航段结束时间的停港消耗量
                    CalculateOilVo calculateOilVoStop = getStopPortOil(endNextYear, nextYearEndTime, endTime, endOilCons);
                    //如果为港界外条件
                    if (endNextYear.getInPort().equals("1")) {
                        RawVoyagePort rawVoyagePortNew = new RawVoyagePort();
                        rawVoyagePortNew.setInPort("0");
                        rawVoyagePortNew.setRecordType("0");
                        rawVoyagePortNew.setDistance(endNextYear.getDistance());
                        rawVoyagePortNew.setArrTm(endNextYear.getArrTm());
                        rawVoyagePortNew.setArrZone(endNextYear.getArrZone());
                        rawVoyagePortNew.setDeptTm(endNextYear.getDeptTm());
                        rawVoyagePortNew.setDeptZone(endNextYear.getDeptZone());
                        rawVoyagePortNew.setRawVoyageAddoils(endNextYear.getRawVoyageAddoils());
                        rawVoyagePortNew.setRawVoyageOutoils(endNextYear.getRawVoyageOutoils());
                        rawVoyagePortNew.setRawVoyagePortoils(endNextYear.getRawVoyagePortoils());
                        rawVoyagePortNew.setRawVoyagePortloadings(endNextYear.getRawVoyagePortloadings());
                        rawVoyagePortNew.setRawVoyageSludges(endNextYear.getRawVoyageSludges());
                        noAcrossNext.set(noAcrossNext.size() - 1, rawVoyagePortNew);
                        nextYearVoyage = energyEefficService.generatorAcrossInfo(noAcrossNext, byNextRawSpecAcrossYear);
                    }
                    //跨年航段的停港减去没有跨年的停港油耗
                    nextYearVoyage.setStopoiHfo(nextYearVoyage.getStopoiHfo().subtract(calculateOilVoStop.getOiHfo()));
                    nextYearVoyage.setStopoiLfo(nextYearVoyage.getStopoiLfo().subtract(calculateOilVoStop.getOiLfo()));
                    nextYearVoyage.setStopoiChai(nextYearVoyage.getStopoiChai().subtract(calculateOilVoStop.getOiChai()));
                    nextYearVoyage.setStopoiBing(nextYearVoyage.getStopoiBing().subtract(calculateOilVoStop.getOiBing()));
                    nextYearVoyage.setStopoiDing(nextYearVoyage.getStopoiDing().subtract(calculateOilVoStop.getOiDing()));
                    nextYearVoyage.setStopethanol(nextYearVoyage.getStopethanol().subtract(calculateOilVoStop.getOiethanol()));
                    nextYearVoyage.setStopoiOther(nextYearVoyage.getStopoiOther().subtract(calculateOilVoStop.getOiOther()));
                    nextYearVoyage.setStopoiTian(nextYearVoyage.getStopoiTian().subtract(calculateOilVoStop.getOiTian()));
                    oiShipVoyageNext = nextYearVoyage;
                }

            }
            //下一年的跨年航段
            oiShipVoyageNext.setStartTime(nextYearStartTime);
            oiShipVoyageNext.setEndTime(nextYearEndTime);
            oiShipVoyages.add(nextYearVoyage);
            oiShipVoyages.add(oiShipVoyageNext);
        }
        return oiShipVoyages;
    }

    private List<OiShipVoyage> getSingleOiShipVoyages(OiShipTask oiShipTask, String voyageType) {
        //生成航段信息
        List<OiShipVoyage> oiShipVoyageGeners = new ArrayList<>();
        List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
        List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
        List<RawVoyageSpec> rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();
        rawVoyagePorts.addAll(rawVoyagePortsNext);
        if (rawVoyagePorts.size() < 2) {
            throw new ExplicitException(oiShipTask.getTask() + "航次港口少于两个无法生成报告");
        }
        rawVoyagePorts.sort((x, y) -> {
            if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                return x.getCreateTm().compareTo(y.getCreateTm());
            } else {
                return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
            }
        });
        if (!"0".equals(rawVoyagePorts.get(0).getInPort())) {
            throw new ExplicitException(oiShipTask.getTask() + "航次第一个港口不是港界内");
        }
        if (!"0".equals(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getInPort())) {
            throw new ExplicitException(oiShipTask.getTask() + "航次最后一个港口不是港界内");
        }
        //拆分航段
        Map<String, List<List<RawVoyagePort>>> euSegmentsSAndImoASegments = getEuSegmentsSAndImoASegments(rawVoyagePorts);
        List<List<RawVoyagePort>> imoSements = euSegmentsSAndImoASegments.get(voyageType);
        for (int i = 0; i < imoSements.size(); i++) {
            List<RawVoyagePort> rawVoyagePorts1 = imoSements.get(i);
            OiShipVoyage oiShipVoyage = energyEefficService.generatorAcrossInfo(rawVoyagePorts1, rawVoyageSpecs);
            oiShipVoyageGeners.add(oiShipVoyage);
        }
        return oiShipVoyageGeners;
    }

    @Override
    @Transactional
    public ResultVo saveGeneratorDcs(SaveGeneratorDcsDto saveGeneratorDcsDto) {
        log.info("保存dcs报告参数==={}", saveGeneratorDcsDto);
        String imoRptId = saveGeneratorDcsDto.getImoRptId();
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(saveGeneratorDcsDto.getImoRptId(), 0);
        //根据船舶id获取在报告年份的航次
        Map<String, String> useMethod = getUseMethod(imoStdrpt.getShipId());
        imoStdrpt.setHourOnsea(saveGeneratorDcsDto.getDistanceHour());
        imoStdrpt.setDistOnsea(saveGeneratorDcsDto.getDistance());
        //修改imo标准报告的油耗
        List<ImoOil> imoOils = imoStdrpt.getImoOils();
        List<ImoOil> imoOilsPeriod = new ArrayList<>();
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.HFO_HIGHT, FuelConst.HFO_HIGHT_NAME, useMethod.get(FuelConst.HFO_HIGHT), saveGeneratorDcsDto.getHfo(), "0", 0));
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.LFO_HIGHT, FuelConst.LFO_HIGHT_NAME, useMethod.get(FuelConst.LFO_HIGHT), saveGeneratorDcsDto.getLfo(), "0", 0));
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.CHAI_CODE, FuelConst.CHAI_CODE_NAME, useMethod.get(FuelConst.CHAI_CODE), saveGeneratorDcsDto.getChaiFo(), "0", 0));
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.TIAN_CODE, FuelConst.TIAN_CODE_NAME, useMethod.get(FuelConst.TIAN_CODE), saveGeneratorDcsDto.getLng(), "0", 0));
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.BING_CODE, FuelConst.BING_NAME, useMethod.get(FuelConst.BING_CODE), saveGeneratorDcsDto.getPropane(), "0", 0));
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.DING_CODE, FuelConst.DING_NAME, useMethod.get(FuelConst.DING_CODE), saveGeneratorDcsDto.getButane(), "0", 0));
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.METHAN_CODE, FuelConst.METHAN_NAME, useMethod.get(FuelConst.METHAN_CODE), saveGeneratorDcsDto.getMethanol(), "0", 0));
        imoOilsPeriod.add(new ImoOil(imoRptId, FuelConst.OIETHAN_CODE, FuelConst.OIETHAN_NAME, useMethod.get(FuelConst.OIETHAN_CODE), saveGeneratorDcsDto.getEthanol(), "0", 0));
        for (int i = 0; i < imoOilsPeriod.size(); i++) {
            ImoOil imoOilPeriod = imoOilsPeriod.get(i);
            for (int j = 0; j < imoOils.size(); j++) {
                ImoOil imoOil = imoOils.get(j);
                if (imoOil.getOilId().equals(imoOilPeriod.getOilId())) {
                    imoOil.setFuelTons(imoOilPeriod.getFuelTons());
                    imoOilsPeriod.set(i, imoOil);
                }
            }

        }
        imoOilRepository.saveAll(imoOilsPeriod);
//        imoOils.forEach(imoOil -> {
//            if (FuelConst.HFO_HIGHT.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getHfo());
//            }
//            if (FuelConst.LFO_HIGHT.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getLfo());
//            }
//            if (FuelConst.CHAI_CODE.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getChaiFo());
//            }
//            if (FuelConst.BING_CODE.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getPropane());
//            }
//            if (FuelConst.DING_CODE.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getButane());
//            }
//            if (FuelConst.TIAN_CODE.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getLng());
//            }
//            if (FuelConst.METHAN_CODE.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getMethanol());
//            }
//            if (FuelConst.OIETHAN_CODE.equals(imoOil.getOilId())) {
//                imoOil.setFuelTons(saveGeneratorDcsDto.getEthanol());
//            }
//        });
        //修改或者保存修正量
        ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(imoStdrpt.getId(), 0);
        String adjustId = imoStdrptAdjust.getId();
        imoStdrptAdjust.setDistOnsea(saveGeneratorDcsDto.getCorrectionDistance());
        imoStdrptAdjust.setHourOnsea(saveGeneratorDcsDto.getCorrectionDistanceHour());
        List<ImoCorrectionOil> imoCorrectionOils = imoStdrptAdjust.getImoCorrectionOils();
//        imoCorrectionOils.forEach(imoCorrectionOil -> {
//            if (FuelConst.HFO_HIGHT.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionHfo());
//            }
//            if (FuelConst.LFO_HIGHT.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionLfo());
//            }
//            if (FuelConst.CHAI_CODE.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionChaiFo());
//            }
//            if (FuelConst.BING_CODE.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionPropane());
//            }
//            if (FuelConst.DING_CODE.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionButane());
//            }
//            if (FuelConst.TIAN_CODE.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionLng());
//            }
//            if (FuelConst.METHAN_CODE.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionMethanol());
//            }
//            if (FuelConst.OIETHAN_CODE.equals(imoCorrectionOil.getOilId())) {
//                imoCorrectionOil.setFuelTons(saveGeneratorDcsDto.getCorrectionEthanol());
//            }
//        });
        List<ImoCorrectionOil> imoCorrectionOilsPriod = Arrays.asList(new ImoCorrectionOil(adjustId, FuelConst.HFO_HIGHT, FuelConst.HFO_HIGHT_NAME, saveGeneratorDcsDto.getCorrectionHfo(), "0", 0)
                , new ImoCorrectionOil(adjustId, FuelConst.LFO_HIGHT, FuelConst.LFO_HIGHT_NAME, saveGeneratorDcsDto.getCorrectionLfo(), "0", 0)
                , new ImoCorrectionOil(adjustId, FuelConst.CHAI_CODE, FuelConst.CHAI_CODE_NAME, saveGeneratorDcsDto.getCorrectionChaiFo(), "0", 0)
                , new ImoCorrectionOil(adjustId, FuelConst.TIAN_CODE, FuelConst.TIAN_CODE_NAME, saveGeneratorDcsDto.getCorrectionLng(), "0", 0)
                , new ImoCorrectionOil(adjustId, FuelConst.BING_CODE, FuelConst.BING_NAME, saveGeneratorDcsDto.getCorrectionPropane(), "0", 0)
                , new ImoCorrectionOil(adjustId, FuelConst.DING_CODE, FuelConst.DING_NAME, saveGeneratorDcsDto.getCorrectionButane(), "0", 0)
                , new ImoCorrectionOil(adjustId, FuelConst.METHAN_CODE, FuelConst.METHAN_NAME, saveGeneratorDcsDto.getCorrectionMethanol(), "0", 0)
                , new ImoCorrectionOil(adjustId, FuelConst.OIETHAN_CODE, FuelConst.OIETHAN_NAME, saveGeneratorDcsDto.getCorrectionEthanol(), "0", 0));
        for (int i = 0; i < imoCorrectionOilsPriod.size(); i++) {
            ImoCorrectionOil imoCorrectionOilPeriod = imoCorrectionOilsPriod.get(i);
            for (int j = 0; j < imoCorrectionOils.size(); j++) {
                ImoCorrectionOil imoCorrectionOil = imoCorrectionOils.get(j);
                if (imoCorrectionOilPeriod.getOilId().equals(imoCorrectionOil.getOilId())) {
                    imoCorrectionOil.setFuelTons(imoCorrectionOilPeriod.getFuelTons());
                    imoCorrectionOilsPriod.set(i, imoCorrectionOil);
                }
            }
        }
        imoCorrectionOilRepository.saveAll(imoCorrectionOilsPriod);
        imoStdrptAdjustRepository.save(imoStdrptAdjust);
        ImoStdrpt save = imoStdrptRepository.save(imoStdrpt);
        if (null != save) {
            return new ResultStringVo(this.msg("imoRpt.save.success"));
        } else {
            return new ResultErrorVo(this.msg("imoRpt.save.fail"));
        }
    }

    @Override
    @Transactional
    public ResultVo saveManualDcs(SaveManuelDcsDto saveManuelDcsDto) {
        log.info("手动新增dcs报告参数====={}", saveManuelDcsDto);
        //保存dcs报告
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(saveManuelDcsDto.getImoRptId(), 0);
        String imoNu = saveManuelDcsDto.getImoNu();
        if (imoStdrpt == null) {
            imoStdrpt = new ImoStdrpt();
        } else {
            imoOilRepository.deleteByRptId(imoStdrpt.getId());
        }
        imoStdrpt.setDistOnsea(saveManuelDcsDto.getDistance());
        imoStdrpt.setHourOnsea(saveManuelDcsDto.getDistanceHour());
        List<ImoOil> imoOils = new ArrayList<>();
        imoStdrpt.setImono(imoNu);
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(imoNu, imoNu, 0);
        if (oiShipInfo == null) {
            imoStdrpt.setDwTons(saveManuelDcsDto.getDwt());
            oiShipInfo = new OiShipInfo();
        } else {
            imoStdrpt.setDwTons(oiShipInfo.getDw());
        }
        imoStdrpt.setSpType(saveManuelDcsDto.getShipType());
        imoStdrpt.setIceClass(saveManuelDcsDto.getIce());
        imoStdrpt.setNetTons(saveManuelDcsDto.getNet());
        imoStdrpt.setGrossTons(saveManuelDcsDto.getGross());
        imoStdrpt.setEediVal(saveManuelDcsDto.getEedi());
        imoStdrpt.setShipId(oiShipInfo.getId());
        imoStdrpt.setShipName(oiShipInfo.getSpname());
        imoStdrpt.setDocIacs(oiShipInfo.getDocIacs());
        imoStdrpt.setDocName(oiShipInfo.getDocmanager());
        StringBuilder gePower = new StringBuilder("");
        StringBuilder mainPower = new StringBuilder("");
        List<SaveManuelDeviceDto> oiMainEngines = saveManuelDcsDto.getOiMainEngines();
        List<SaveManuelDeviceDto> oiShipGes = saveManuelDcsDto.getOiShipGes();
        for (int i = 0; i < oiShipGes.size(); i++) {
            BigDecimal oiShipGeRatePower = oiShipGes.get(i).getRatePower();
            if (i == oiShipGes.size() - 1) {
                gePower.append(oiShipGeRatePower);
            } else {
                gePower.append(oiShipGeRatePower + ",");
            }
        }
        imoStdrpt.setGePower(gePower.toString());
        for (int i = 0; i < oiMainEngines.size(); i++) {
            BigDecimal oiMainEngineRatePower = oiMainEngines.get(i).getRatePower();
            if (i == oiMainEngines.size() - 1) {
                mainPower.append(oiMainEngineRatePower);
            } else {
                mainPower.append(oiMainEngineRatePower + ",");
            }
        }
        imoStdrpt.setMePower(mainPower.toString());
        imoStdrpt.setRptSource("0");
        imoStdrpt.setCountryFlag(saveManuelDcsDto.getFlagCode());
        imoStdrpt.setRecStatus("1");
        imoStdrpt.setIsDelete(0);
        String refCode = UUID.randomUUID().toString().replace("-", "");
        imoStdrpt.setStartTm(saveManuelDcsDto.getStartTime());
        imoStdrpt.setEndTm(saveManuelDcsDto.getEndTime());
        imoStdrpt.setRefCode(refCode);
        //上传bdn文件
        if (StringUtils.isNotEmpty(saveManuelDcsDto.getBdnFileDir())) {
            String bdnFtpDir = ftpUtils.upFile(saveManuelDcsDto.getBdnFileDir()) + "/" + saveManuelDcsDto.getBdnFileDir();
            SysFile bdnSysFile = new SysFile();
            bdnSysFile.setPath(bdnFtpDir);
            bdnSysFile.setName(saveManuelDcsDto.getBdnFileName());
            SysFile bdnFile = sysFileRepository.save(bdnSysFile);
            imoStdrpt.setBdnFileId(bdnFile.getId());
        }
        //上传cd文件
        if (StringUtils.isNotEmpty(saveManuelDcsDto.getCdFileDir())) {
            SysFile cdSysFile = new SysFile();
            String cdFtpDir = ftpUtils.upFile(saveManuelDcsDto.getCdFileDir()) + "/" + saveManuelDcsDto.getCdFileDir();
            cdSysFile.setPath(cdFtpDir);
            cdSysFile.setName(saveManuelDcsDto.getCdFileName());
            SysFile cdFile = sysFileRepository.save(cdSysFile);
            imoStdrpt.setCdFileId(cdFile.getId());
        }
        ImoStdrpt save = imoStdrptRepository.save(imoStdrpt);
        saveManuelDcsDto.getManualOilDtos().forEach(manualOilDto -> {
            ImoOil imoOil = new ImoOil();
            imoOil.setFuelTons(manualOilDto.getFuelTons());
            imoOil.setIsDelete(0);
            imoOil.setRecStatus("1");
            imoOil.setOilId(manualOilDto.getOilId());
            imoOil.setOilMethod(manualOilDto.getUseMethod());
            imoOil.setOilName(manualOilDto.getOilName());
            imoOils.add(imoOil);
            imoOil.setRptId(save.getId());
        });
        imoOilRepository.saveAll(imoOils);
        if (null != save) {
            return new ResultStringVo(this.msg("imoRpt.save.success"));
        } else {
            return new ResultErrorVo(this.msg("imoRpt.save.fail"));
        }
    }

    @Override
    public void downLoadDcs(String imoRptId, String type, HttpServletResponse response) {
        //查询dcs报告
        log.info("报告id=========={}", imoRptId);
        log.info("报告的类型======={}", type);
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(imoRptId, 0);
        String shipId = imoStdrpt.getShipId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        if (imoStdrpt != null) {
            String mePower = imoStdrpt.getMePower();
            String gePower = imoStdrpt.getGePower();
            if (mePower == null) {
                mePower = "";
            }
            if (gePower == null) {
                gePower = "";
            }
            List<String> oiMainEngines = Arrays.asList(mePower.split(","));
            List<String> oiShipGes = Arrays.asList(gePower.split(","));
            List<String> mainAndGe = getMainAndGe(oiShipGes, oiMainEngines);
            map.put("startTime", simpleDateFormat.format(imoStdrpt.getStartTm()));
            map.put("endTime", simpleDateFormat.format(imoStdrpt.getEndTm()));
            map.put("imono", imoStdrpt.getImono());
            ShipType shipType = shipTypeRepository.findShipType(imoStdrpt.getSpType());
            Map<String, String> mapCn = fuelConsMethodProperties.getMapCn();
            Map<String, String> mapEn = fuelConsMethodProperties.getMapEn();
            String oilMethod = getEnOrCnConsMethod(mapEn, shipId);
            if ("0".equals(type)) {
                map.put("shiptypeCn", shipType.getCsptype());
                map.put("zxMon", oilMethod == null || oilMethod.equals("") ? "BDN" : oilMethod);
            } else {
                map.put("shiptypeCn", shipType.getImoSptype());
                map.put("zxMon", oilMethod == null || oilMethod.equals("") ? "BDN" : oilMethod);
            }
            map.put("grossTon", imoStdrpt.getGrossTons());
            map.put("netTon", imoStdrpt.getNetTons());
            map.put("deadWeightTon", imoStdrpt.getDwTons());
            map.put("eedi",
                    (imoStdrpt.getEediVal() == null || (imoStdrpt.getEediVal().compareTo(BigDecimal.ZERO) == 0)
                            ? "N/A" : imoStdrpt.getEediVal()));
            map.put("iceGrade", (imoStdrpt.getIceClass() == null || imoStdrpt.getIceClass().equals("")) ? "N/A" : imoStdrpt.getIceClass());
            map.put("mePower", mainAndGe.get(0));
            map.put("gePower", mainAndGe.get(1));
            map.put("distance", imoStdrpt.getDistOnsea());
            map.put("hours", imoStdrpt.getHourOnsea());
            //计算油耗
            List<ImoOil> imoOils = imoStdrpt.getImoOils();
            ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(imoStdrpt.getId(), 0);
            List<ImoCorrectionOil> imoCorrectionOils = null;
            if (imoStdrptAdjust != null) {
                imoCorrectionOils = imoStdrptAdjust.getImoCorrectionOils();
            }
            BigDecimal hfoTons = new BigDecimal(0);// 重油消耗量
            BigDecimal lfoTons = new BigDecimal(0);// 轻油消耗量
            BigDecimal chaiTons = new BigDecimal(0);// 柴/汽油消耗量
            BigDecimal bingTons = new BigDecimal(0);// 丙烷消耗量
            BigDecimal dingTons = new BigDecimal(0);// 丁烷消耗量
            BigDecimal tianTons = new BigDecimal(0);// 天燃气消耗量
            BigDecimal methanol = new BigDecimal(0);// 甲醇消耗量
            BigDecimal ethanol = new BigDecimal(0);//乙醇消耗量
            for (int i = 0; i < imoOils.size(); i++) {
                ImoOil imoOil = imoOils.get(i);
                if (FuelConst.HFO_HIGHT.equals(imoOil.getOilId()) ||
                        FuelConst.HFO_LOW.equals(imoOil.getOilId()) ||
                        FuelConst.HFO_CHAO_LOW.equals(imoOil.getOilId())) {
                    hfoTons = hfoTons.add(imoOil.getFuelTons());
                }
                if (FuelConst.LFO_HIGHT.equals(imoOil.getOilId()) ||
                        FuelConst.LFO_LOW.equals(imoOil.getOilId()) ||
                        FuelConst.LFO_CHAO_LOW.equals(imoOil.getOilId())) {
                    lfoTons = lfoTons.add(imoOil.getFuelTons());
                }
                if (FuelConst.CHAI_CODE.equals(imoOil.getOilId())) {
                    chaiTons = chaiTons.add(imoOil.getFuelTons());
                }
                if (FuelConst.BING_CODE.equals(imoOil.getOilId())) {
                    bingTons = bingTons.add(imoOil.getFuelTons());
                }
                if (FuelConst.DING_CODE.equals(imoOil.getOilId())) {
                    dingTons = dingTons.add(imoOil.getFuelTons());
                }
                if (FuelConst.METHAN_CODE.equals(imoOil.getOilId())) {
                    methanol = methanol.add(imoOil.getFuelTons());
                }
                if (FuelConst.OIETHAN_CODE.equals(imoOil.getOilId())) {
                    ethanol = ethanol.add(imoOil.getFuelTons());
                }
                if (FuelConst.TIAN_CODE.equals(imoOil.getOilId())) {
                    tianTons = tianTons.add(imoOil.getFuelTons());
                }
            }
            for (int i = 0; imoCorrectionOils != null && i < imoCorrectionOils.size(); i++) {
                ImoCorrectionOil imoCorrectionOil = imoCorrectionOils.get(i);
                if (FuelConst.HFO_HIGHT.equals(imoCorrectionOil.getOilId())) {
                    hfoTons = hfoTons.add(imoCorrectionOil.getFuelTons());
                }
                if (FuelConst.LFO_HIGHT.equals(imoCorrectionOil.getOilId())) {
                    lfoTons = lfoTons.add(imoCorrectionOil.getFuelTons());
                }
                if (FuelConst.CHAI_CODE.equals(imoCorrectionOil.getOilId())) {
                    chaiTons = chaiTons.add(imoCorrectionOil.getFuelTons());
                }
                if (FuelConst.BING_CODE.equals(imoCorrectionOil.getOilId())) {
                    bingTons = bingTons.add(imoCorrectionOil.getFuelTons());
                }
                if (FuelConst.DING_CODE.equals(imoCorrectionOil.getOilId())) {
                    dingTons = dingTons.add(imoCorrectionOil.getFuelTons());
                }
                if (FuelConst.METHAN_CODE.equals(imoCorrectionOil.getOilId())) {
                    methanol = methanol.add(imoCorrectionOil.getFuelTons());
                }
                if (FuelConst.OIETHAN_CODE.equals(imoCorrectionOil.getOilId())) {
                    ethanol = ethanol.add(imoCorrectionOil.getFuelTons());
                }
                if (FuelConst.TIAN_CODE.equals(imoCorrectionOil.getOilId())) {
                    tianTons = tianTons.add(imoCorrectionOil.getFuelTons());
                }
            }
            map.put("chaiTons", chaiTons);
            map.put("lfoTons", lfoTons);
            map.put("hfoTons", hfoTons);
            map.put("bingTons", bingTons);
            map.put("dingTons", dingTons);
            map.put("tianTons", tianTons);
            map.put("methanolTons", methanol);
            map.put("ethanolTons", ethanol);
            String path = "/downLoad/IMO_STDRPT_DATA.xlsx";
            if (!"0".equals(type)) {
                path = "/downLoad/IMO_STDRPT_DATA_EN.xlsx";
            }
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            XLSTransformer xlsTransformer = new XLSTransformer();
            org.apache.poi.ss.usermodel.Workbook sheets = null;
            downLoadResponse(response, inputStream, xlsTransformer, map);
        }
    }

    //TODO: 校正算法
    @Override
    public void downLoadCollectionData(String imoRptId, String type, HttpServletResponse response) {
        XLSTransformer xlsTransformer = new XLSTransformer();
        //查询collection data数据
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(imoRptId, 0);
        List<ImoDataSummary> imoDataSummaries = imoStdrpt.getImoDataSummaries();
        InputStream inputStream = this.getClass().getResourceAsStream("/downLoad/IMO_COLLECTED_DATA_SUMMARIES.xls");
        Map<String, Object> map = getImoCDExcel(imoDataSummaries);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        downLoadResponse(response, inputStream, xlsTransformer, map);
    }

    @Override
    public void downLoadBdnData(String imoRptId, HttpServletResponse response) {

        log.info("imo报告id===={}", imoRptId);

        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(imoRptId, 0);

        InputStream inputStream = this.getClass().getResourceAsStream("/downLoad/IMO_BDN_TEMPLATE.xlsx");

        if (imoStdrpt != null) {

            List<ImoBdnSummary> imoBdnSummaries = imoStdrpt.getImoBdnSummaries();


            //筛选出期末量

            List<ImoBdnSummary> begins = imoBdnSummaries.stream().filter(imoBdnSummary -> {

                return imoBdnSummary.getRecType() == 1;

            }).collect(Collectors.toList());

            //对期初量进行排序

            begins.sort((x, y) -> {

                return x.getStartTm().compareTo(y.getStartTm());

            });

            //筛选出bdn加油量

            List<ImoBdnSummary> bdns = imoBdnSummaries.stream().filter(imoBdnSummary -> {

                return imoBdnSummary.getRecType() == 0;

            }).collect(Collectors.toList());

            //筛选出修正量

            List<ImoBdnSummary> correctOils = imoBdnSummaries.stream().filter(imoBdnSummary -> {

                return imoBdnSummary.getRecType() == 2;

            }).collect(Collectors.toList());

            //构造导出的模板结构

            Map map = new HashMap();

            map.put("imono", imoStdrpt.getImono());

            DownLoadBdn bdnDownLoadBdnVo = getDownLoadBdnVo(bdns);

            //todo

            DownLoadBdn bdnDownLoadCorrectVo = getDownLoadBdnVo(correctOils);

            DownLoadBdn beginDownLoadBdnVo = getDownLoadBdnVo(begins);

            DownLoadBdnVo downLoadBdnVo = bdnDownLoadBdnVo.getDownLoadBdnVo();

            //todo

            DownLoadBdnVo downLoadCorrectVo = bdnDownLoadCorrectVo.getDownLoadBdnVo();

            List<DownLoadBdnVo> downLoadBdnVos = beginDownLoadBdnVo.getDownLoadBdnVos();

            BigDecimal allHfo = new BigDecimal(0);

            BigDecimal allLfo = new BigDecimal(0);

            BigDecimal allChai = new BigDecimal(0);

            BigDecimal allLng = new BigDecimal(0);

            BigDecimal allPropane = new BigDecimal(0);

            BigDecimal allButane = new BigDecimal(0);

            BigDecimal allOther = new BigDecimal(0);

            map.put("addList", bdnDownLoadBdnVo.getDownLoadBdnVos());

            map.put("periodList", beginDownLoadBdnVo.getDownLoadBdnVos());

            map.put("addSumChai", downLoadBdnVo.getChaiFo());

            map.put("addSumLfo", downLoadBdnVo.getLfo());

            map.put("addSumHfo", downLoadBdnVo.getHfo());

            map.put("addSumPropane", downLoadBdnVo.getPropane());

            map.put("addSumButane", downLoadBdnVo.getButane());

            map.put("addSumLng", downLoadBdnVo.getLng());

            map.put("addSumOther", downLoadBdnVo.getOther());

            //todo
            List<DownLoadBdnVo> downLoadBdnVosCorrect = bdnDownLoadCorrectVo.getDownLoadBdnVos();
            BigDecimal zero = BigDecimal.ZERO;
            for (DownLoadBdnVo loadBdnVo : downLoadBdnVosCorrect) {
                loadBdnVo.setHfo(zero.subtract(loadBdnVo.getHfo()));
                loadBdnVo.setButane(zero.subtract(loadBdnVo.getButane()));
                loadBdnVo.setLfo(zero.subtract(loadBdnVo.getLfo()));
                loadBdnVo.setLng(zero.subtract(loadBdnVo.getLng()));
                loadBdnVo.setChaiFo(zero.subtract(loadBdnVo.getChaiFo()));
                loadBdnVo.setPropane(zero.subtract(loadBdnVo.getPropane()));
                loadBdnVo.setOther(zero.subtract(loadBdnVo.getOther()));
            }
            map.put("correctionList", downLoadBdnVosCorrect);

            map.put("correctSumChai", BigDecimal.ZERO.subtract(downLoadCorrectVo.getChaiFo()));

            map.put("correctSumLfo", BigDecimal.ZERO.subtract(downLoadCorrectVo.getLfo()));

            map.put("correctSumHfo", BigDecimal.ZERO.subtract(downLoadCorrectVo.getHfo()));

            map.put("correctSumPropane", BigDecimal.ZERO.subtract(downLoadCorrectVo.getPropane()));

            map.put("correctSumButane", BigDecimal.ZERO.subtract(downLoadCorrectVo.getButane()));

            map.put("correctSumLng", BigDecimal.ZERO.subtract(downLoadCorrectVo.getLng()));

            map.put("correctSumOther", BigDecimal.ZERO.subtract(downLoadCorrectVo.getOther()));

            if (begins.size() > 1) {

                DownLoadBdnVo begin = downLoadBdnVos.get(0);

                DownLoadBdnVo end = downLoadBdnVos.get(1);

                map.put("beginSumChai", begin.getChaiFo().subtract(end.getChaiFo()));

                map.put("beginSumLfo", begin.getLfo().subtract(end.getLfo()));

                map.put("beginSumHfo", begin.getHfo().subtract(end.getHfo()));

                map.put("beginSumPropane", begin.getPropane().subtract(end.getPropane()));

                map.put("beginSumButane", begin.getButane().subtract(end.getButane()));

                map.put("beginSumLng", begin.getLng().subtract(end.getLng()));

                map.put("beginSumOther", begin.getOther().subtract(end.getOther()));

                //todo

                allHfo = allHfo.add(downLoadBdnVo.getHfo()).add(begin.getHfo().subtract(end.getHfo())).subtract(downLoadCorrectVo.getHfo());

                allLfo = allLfo.add(downLoadBdnVo.getLfo()).add(begin.getLfo().subtract(end.getLfo())).subtract(downLoadCorrectVo.getLfo());

                allChai = allChai.add(downLoadBdnVo.getChaiFo()).add(begin.getChaiFo().subtract(end.getChaiFo())).subtract(downLoadCorrectVo.getChaiFo());

                allLng = allLng.add(downLoadBdnVo.getLng()).add(begin.getLng().subtract(end.getLng())).subtract(downLoadCorrectVo.getLng());

                allPropane = allPropane.add(downLoadBdnVo.getPropane()).add(begin.getPropane().subtract(end.getPropane())).subtract(downLoadCorrectVo.getPropane());

                allButane = allButane.add(downLoadBdnVo.getButane()).add(begin.getButane().subtract(end.getButane())).subtract(downLoadCorrectVo.getButane());

                allOther = allOther.add(downLoadBdnVo.getOther()).add(begin.getOther().subtract(end.getOther())).subtract(downLoadCorrectVo.getOther());

            }

            map.put("allHfo", allHfo);

            map.put("allLfo", allLfo);

            map.put("allChai", allChai);

            map.put("allLng", allLng);

            map.put("allPropane", allPropane);

            map.put("allButane", allButane);

            map.put("allOther", allOther);

            XLSTransformer xlsTransformer = new XLSTransformer();

            downLoadResponse(response, inputStream, xlsTransformer, map);

        }


    }

    @Override
    public ResultVo getManuleDcs(String imoRptId) {
        log.info("imo报告id==={}", imoRptId);
        ImoStdrpt imoStdrpt = imoStdrptRepository.findByIdAndIsDelete(imoRptId, 0);
        SaveManuelDcsDto saveManuelDcsDto = new SaveManuelDcsDto();
        saveManuelDcsDto.setImoRptId(imoStdrpt.getId());
        saveManuelDcsDto.setDistance(imoStdrpt.getDistOnsea());
        saveManuelDcsDto.setDistanceHour(imoStdrpt.getHourOnsea());
        saveManuelDcsDto.setDwt(imoStdrpt.getDwTons());
        saveManuelDcsDto.setEedi(imoStdrpt.getEediVal());
        saveManuelDcsDto.setFlagCode(imoStdrpt.getCountryFlag());
        saveManuelDcsDto.setStartTime(imoStdrpt.getStartTm());
        saveManuelDcsDto.setEndTime(imoStdrpt.getEndTm());
        saveManuelDcsDto.setGross(imoStdrpt.getGrossTons());
        saveManuelDcsDto.setNet(imoStdrpt.getNetTons());
        saveManuelDcsDto.setImoNu(imoStdrpt.getImono());
        saveManuelDcsDto.setIce(imoStdrpt.getIceClass());
        saveManuelDcsDto.setShipType(imoStdrpt.getSpType());
        String shipId = imoStdrpt.getShipId();
        if (shipId != null) {
            saveManuelDcsDto.setShipId(imoStdrpt.getShipId());
        }
        SysFile cdFile = sysFileRepository.selectById(imoStdrpt.getCdFileId());
        SysFile bdnFile = sysFileRepository.selectById(imoStdrpt.getBdnFileId());
        if (cdFile != null) {
            saveManuelDcsDto.setCdFileDir(cdFile.getPath());
            saveManuelDcsDto.setCdFileName(cdFile.getName());
        }
        if (bdnFile != null) {
            saveManuelDcsDto.setBdnFileDir(bdnFile.getPath());
            saveManuelDcsDto.setBdnFileName(bdnFile.getName());
        }

        List<ImoOil> imoOils = imoStdrpt.getImoOils();
        List<ManualOilDto> manualOilDtos = new ArrayList<>();
        imoOils.forEach(imoOil -> {
            ManualOilDto manualOilDto = new ManualOilDto();
            manualOilDto.setOilId(imoOil.getOilId());
            manualOilDto.setOilName(imoOil.getOilName());
            manualOilDto.setFuelTons(imoOil.getFuelTons());
            manualOilDto.setUseMethod(imoOil.getOilMethod());
            manualOilDtos.add(manualOilDto);
        });
        saveManuelDcsDto.setManualOilDtos(manualOilDtos);
        List<SaveManuelDeviceDto> oiShipMains = new ArrayList<>();
        List<SaveManuelDeviceDto> oiShipGes = new ArrayList<>();
        List<String> mes = Arrays.asList(imoStdrpt.getMePower().split(","));
        List<String> ges = Arrays.asList(imoStdrpt.getGePower().split(","));
        mes.forEach(s -> {
            SaveManuelDeviceDto saveManuelDeviceDto = new SaveManuelDeviceDto();
            saveManuelDeviceDto.setRatePower(new BigDecimal(s));
            oiShipMains.add(saveManuelDeviceDto);
        });
        ges.forEach(s -> {
            SaveManuelDeviceDto saveManuelDeviceDto = new SaveManuelDeviceDto();
            saveManuelDeviceDto.setRatePower(new BigDecimal(s));
            oiShipGes.add(saveManuelDeviceDto);
        });
        saveManuelDcsDto.setOiShipGes(oiShipGes);
        saveManuelDcsDto.setOiMainEngines(oiShipMains);
        return new ResultObjectVo<>(saveManuelDcsDto);
    }

    @Override
    @Transactional
    public ResultVo saveAndUpdatePeriodOil(BeginOilDto beginOilDto) {
        log.info("期初油量参数======", beginOilDto);
        List<BeginPeriodOilDto> beginPeriodOilDtos = beginOilDto.getBeginPeriodOils();
        Date periodTime = beginOilDto.getPeriodTime();
        String shipId = beginOilDto.getShipId();
        String periodId = beginOilDto.getPeriodId();
        BeginPeriod beginPeriod = beginPeriodRepository.findByShipIdAndPeriodTimeAndIsDelete(shipId, periodTime, 0);
        if (beginPeriod != null) {
            periodId = beginPeriod.getId();
        }
        BeginPeriod beginPeriod1 = beginPeriodRepository.findByIdAndIsDelete(periodId, 0);
        if (beginPeriod1 == null) {
            beginPeriod1 = new BeginPeriod();
            beginPeriod1.setIsDelete(0);
            beginPeriod1.setRecStatus("0");
        }
        beginPeriod1.setShipId(shipId);
        beginPeriod1.setPeriodTime(periodTime);
        BeginPeriod save = beginPeriodRepository.save(beginPeriod1);
        List<BeginPeriodOil> beginPeriodOils1 = new ArrayList<>();
        if (beginPeriodOilDtos != null) {
            beginPeriodOilDtos.forEach(beginPeriodOilDto -> {
                BeginPeriodOil beginPeriodOil = new BeginPeriodOil();
                beginPeriodOil.setPeriodTime(periodTime);
                beginPeriodOil.setShipId(shipId);
                beginPeriodOil.setIsDelete(0);
                beginPeriodOil.setRecStatus("0");
                beginPeriodOil.setOilId(beginPeriodOilDto.getFuelCode());
                beginPeriodOil.setOilName(beginPeriodOilDto.getFuelName());
                beginPeriodOil.setFuelTons(beginPeriodOilDto.getFuelTons());
                beginPeriodOil.setId(beginPeriodOilDto.getId());
                beginPeriodOil.setBeginPeriodId(save.getId());
                beginPeriodOils1.add(beginPeriodOil);
            });
        }
        Iterable<BeginPeriodOil> beginPeriodOils = beginPeriodOilRepository.saveAll(beginPeriodOils1);
        if (beginPeriodOils != null) {
            return new ResultStringVo(this.msg("energyEeffic.create.success"));
        } else {
            return new ResultStringVo(this.msg("energyEeffic.create.failed"));
        }
    }

    @Override
    @Transactional
    public ResultVo deletePeriodOil(BeginOilDto beginOilDto) {
        log.info("删除参数===={}", beginOilDto);
        try {
            BeginPeriod beginPeriod = beginPeriodRepository.findByIdAndIsDelete(beginOilDto.getPeriodId(), 0);
            if (beginPeriod != null) {
                beginPeriod.setIsDelete(1);
                beginPeriod.getBeginPeriodOils().forEach(beginPeriodOil -> {
                    beginPeriodOil.setIsDelete(1);
                });
                beginPeriodRepository.save(beginPeriod);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultErrorVo(this.msg("energyEeffic.delete.failed"));
        }
        return new ResultStringVo(this.msg("energyEeffic.delete.success"));
    }

    @Override
    public ResultVo getPeriodOil(BeginOilDto beginOilDto) {
        log.info("期初油量参数==={}", beginOilDto);
        Page<BeginPeriod> all = beginPeriodRepository.findAll(queryBeginPeriodList(beginOilDto), PageRequest.of((beginOilDto.getCurrentPage() - 1), beginOilDto.getPageSize()));
        List<BeginPeriod> content = all.getContent();
        List<BeginPeriodVo> beginPeriodVos = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            BeginPeriodVo beginPeriodVo = new BeginPeriodVo();
            BeginPeriod beginPeriod = content.get(i);
            beginPeriodVo.setPeriodId(beginPeriod.getId());
            beginPeriodVo.setPeriodTime(beginPeriod.getPeriodTime());
            beginPeriodVo.setShipId(beginPeriod.getShipId());
            beginPeriodVo.setShipName(beginPeriod.getOiShipInfo().getSpname());
            beginPeriodVo.setRecStatus(beginPeriod.getRecStatus());
            List<BeginPeriodOil> beginPeriodOils = beginPeriod.getBeginPeriodOils();
            List<BeginPeriodOilDto> beginPeriodOilDtos = new ArrayList<>();
            beginPeriodOils.forEach(beginPeriodOil -> {
                BeginPeriodOilDto beginPeriodOilDto = new BeginPeriodOilDto();
                beginPeriodOilDto.setId(beginPeriodOil.getId());
                beginPeriodOilDto.setFuelCode(beginPeriodOil.getOilId());
                beginPeriodOilDto.setFuelName(beginPeriodOil.getOilName());
                beginPeriodOilDto.setFuelTons(beginPeriodOil.getFuelTons());
                beginPeriodOilDtos.add(beginPeriodOilDto);
            });
            beginPeriodVo.setBeginPeriodOilDtos(beginPeriodOilDtos);
            beginPeriodVos.add(beginPeriodVo);
        }
        PageDataVo<BeginPeriodVo> objectPageDataVo = new PageDataVo<>();
        objectPageDataVo.setTotal((int) all.getTotalElements());
        objectPageDataVo.setItems(beginPeriodVos);
        return new ResultObjectVo<>(objectPageDataVo);
    }

    @Override
    @Transactional
    public ResultVo lockOrUnlock(BeginOilDto beginOilDto) {
        BeginPeriod beginPeriod = beginPeriodRepository.findByIdAndIsDelete(beginOilDto.getPeriodId(), 0);
        if (beginPeriod != null) {
            String recStatus = beginPeriod.getRecStatus();
            if (recStatus.equals("0")) {
                recStatus = "1";
            } else {
                recStatus = "0";
            }
            beginPeriod.setRecStatus(recStatus);
            String finalRecStatus = recStatus;
            beginPeriod.getBeginPeriodOils().forEach(beginPeriodOil -> {
                beginPeriodOil.setRecStatus(finalRecStatus);
            });
            beginPeriodRepository.save(beginPeriod);
        }
        return new ResultStringVo(this.msg("energyEeffic.update.recstatus.success"));
    }

    @Override
    public ResultVo generatorEuDcs(GeneratorImoRptDto generatorImoRptDto) {
        //获取船舶的基本信息
        Integer year = generatorImoRptDto.getYear();
        String shipId = generatorImoRptDto.getShipId();
        EuEmissionRpt byIdAndIsDelete = euEmissionRptRepository.findByShipIdAndStartTime(shipId, year);
        //查询出船舶的基本信息
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);
        GcCity byCode = gcCityRepository.findByCode(oiShipInfo.getHomePort());
        ShipType shipType = shipTypeRepository.findShipType(oiShipInfo.getSptype());
        String start = year + "-01-01 00:00:00";
        String end = year + "-12-31 23:59:59";
        if (byIdAndIsDelete == null || byIdAndIsDelete.getRecStatus().equals("1")) {
            //根据船舶id获取在报告年份的航次
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endTime = null;
            Date startTime = null;
            try {
                startTime = simpleDateFormat.parse(start);
                endTime = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new ExplicitException("时间格式不正确");
            }
            //获取船舶的信息
            List<OiShipTask> oiShipTasks = oiShipTaskRepository.findOiShipTask(shipId, start, end);
            List<OiShipVoyage> oiShipVoyages = getOiShipVoyagesEu(oiShipTasks, "EU", startTime, endTime);
            //筛选报告年份的航段
            Date finalStartTime = startTime;
            Date finalEndTime = endTime;
            List<OiShipVoyage> noAcrossVoyage = oiShipVoyages.stream().filter(oiShipVoyage -> {
                return (oiShipVoyage.getStartTime().getTime() >= finalStartTime.getTime() && oiShipVoyage.getStartTime().getTime() <= finalEndTime.getTime());
            }).collect(Collectors.toList());
            BigDecimal allDistance = noAcrossVoyage.stream().map(OiShipVoyage::getDistance).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal allDisHour = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            if (allDistance == null) {
                allDistance = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            }
            BigDecimal eeoiTd = commonEnergyEfficServiceImpl.getVoyageEeoiTd(noAcrossVoyage).get("eeoiTd");
            //筛选出发是eu，到达是eu，eu之间的
            List<OiShipVoyage> doubleEu = new ArrayList<>();
            List<OiShipVoyage> startEu = new ArrayList<>();
            List<OiShipVoyage> endEu = new ArrayList<>();
            for (int i = 0; i < noAcrossVoyage.size(); i++) {
                OiShipVoyage oiShipVoyage = noAcrossVoyage.get(i);
                allDisHour = allDisHour.add(oiShipVoyage.getSailTime());
                RawVoyagePort startPort = rawVoyagePortRepository.findByIdAndIsDelete(oiShipVoyage.getStartportid(), 0);
                RawVoyagePort endPort = rawVoyagePortRepository.findByIdAndIsDelete(oiShipVoyage.getEndportid(), 0);
                Integer isEuStart = startPort.getIsEu();
                Integer isEuEnd = endPort.getIsEu();
                judgeIsEu(doubleEu, startEu, endEu, oiShipVoyage, isEuStart, isEuEnd);
            }


            //计算总的重油，轻油和柴油的油耗和航行小时和航行海里
            BigDecimal hfo = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal lfo = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal chai = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal bing = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal tian = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal ding = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal methanol = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal ethanol = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal totalCo2StartAll = new BigDecimal(0.0000);
            BigDecimal totalCo2All = new BigDecimal(0.0000);
            BigDecimal totalCo2EndAll = new BigDecimal(0.0000);
            BigDecimal totalCo2PortAll = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            int timeFlag = 0;  // = 1 时间段在 A港口之外和AB之内  =2 在AB之内和B港之外  =3 分别在A港口、B港口之外
            for (int i = 0; i < oiShipVoyages.size(); i++) {
                OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
                RawVoyagePort startPort = rawVoyagePortRepository.findByIdAndIsDelete(oiShipVoyage.getStartportid(), 0);
                RawVoyagePort endPort = rawVoyagePortRepository.findByIdAndIsDelete(oiShipVoyage.getEndportid(), 0);
                Integer isEuStart = startPort.getIsEu();
                Integer isEuEnd = endPort.getIsEu();
                if ((startPort.getDeptTm().getTime() >= startTime.getTime()) && (startPort.getDeptTm().getTime() >= endTime.getTime()) ||
                        ((endPort.getDeptTm().getTime() <= startTime.getTime()) && (endPort.getDeptTm().getTime() <= endTime.getTime()))) {
                    continue;
                }
                if ((startPort.getDeptTm().getTime() >= startTime.getTime()) && (endPort.getDeptTm().getTime() <= endTime.getTime())) {
                    timeFlag = 3;
                } else if ((startPort.getDeptTm().getTime() >= startTime.getTime()) && (endPort.getDeptTm().getTime() >= endTime.getTime())) {
                    timeFlag = 1;
                } else {
                    timeFlag = 2;
                }
                if (isEuStart == 0 && isEuEnd == 0) {
                    continue;
                }
                if (isEuStart == 1 && isEuEnd != 1) {
                    if (timeFlag == 2) {
                        //时间在起始港之后，且结束不是欧盟，不计入。
                        continue;
                    }
                    hfo = hfo.add(oiShipVoyage.getOiHfo());
                    chai = chai.add(oiShipVoyage.getOiChai());
                    lfo = lfo.add(oiShipVoyage.getOiLfo());
                    bing = bing.add(oiShipVoyage.getOiBing());
                    ding = ding.add(oiShipVoyage.getOiDing());
                    tian = tian.add(oiShipVoyage.getOiTian());
                    methanol = methanol.add(oiShipVoyage.getOiOther());
                    ethanol = ethanol.add(oiShipVoyage.getOiethanol());

                }
                if (isEuStart != 1 && isEuEnd == 1)//起始不是欧盟，结束是欧盟
                {

                    //每个港口的系数
                    double k = 1;
                    Date start1 = getUtcTimeByPort(endPort).get("arr");
//                    Date end1 = getUtcTimeByPort(endPort).get("dept");
                    // Date start1 = oiShipVoyage.getStartTime();
                    Date end1 = oiShipVoyage.getEndTime();
                    BigDecimal tmpCo2Port = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
                    if (start1.getTime() >= startTime.getTime() && end1.getTime() <= endTime.getTime()) {
                        k = 1;
                    }
                    //港口跨终点
                    else if (start1.getTime() >= startTime.getTime() && start1.getTime() <= endTime.getTime() && end1.getTime() >= endTime.getTime()) {
                        k = ((double) endTime.getTime() - start1.getTime()) / ((double) end1.getTime() - start1.getTime());
                    }
                    //港口跨整个统计区间
                    else if (start1.getTime() <= startTime.getTime() && end1.getTime() >= endTime.getTime()) {
                        k = ((double) endTime.getTime() - startTime.getTime()) / ((double) end1.getTime() - start1.getTime());
                    }
                    //港口跨起点
                    else if (start1.getTime() <= startTime.getTime() && end1.getTime() >= startTime.getTime() && end1.getTime() <= endTime.getTime()) {
                        k = ((double) end1.getTime() - startTime.getTime()) / ((double) end1.getTime() - start1.getTime());
                    }
                    //不在统计的时间区间内
                    else if ((start1.getTime() >= endTime.getTime() && end1.getTime() >= endTime.getTime()) || (start1.getTime() <= startTime.getTime() && end1.getTime() <= startTime.getTime())) {
                        k = 0;
                    }

                    switch (timeFlag) {
                        case 1:
                            //起始不是EU,结束是EU
                            hfo = hfo.add(oiShipVoyage.getOiHfo().add(oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k))));
                            lfo = lfo.add(oiShipVoyage.getOiLfo().add(oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k))));
                            chai = chai.add(oiShipVoyage.getOiChai().add(oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k))));
                            tian = tian.add(oiShipVoyage.getOiTian().add(oiShipVoyage.getStopoiTian().multiply(BigDecimal.valueOf(k))));
                            bing = bing.add(oiShipVoyage.getOiBing().add(oiShipVoyage.getStopoiBing().multiply(BigDecimal.valueOf(k))));
                            ding = ding.add(oiShipVoyage.getOiDing().add(oiShipVoyage.getStopoiDing().multiply(BigDecimal.valueOf(k))));
                            methanol = methanol.add(oiShipVoyage.getOiOther().add(oiShipVoyage.getStopoiOther().multiply(BigDecimal.valueOf(k))));
                            ethanol = ethanol.add(oiShipVoyage.getOiethanol().add(oiShipVoyage.getStopethanol().multiply(BigDecimal.valueOf(k))));

                            tmpCo2Port = oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.11400))).add(
                                    oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.1510)))).add(
                                    oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.206000))));
                            break;
                        case 2:
                            //起始不是EU,结束是EU
                            hfo = hfo.add(oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k)));
                            chai = chai.add(oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k)));
                            lfo = lfo.add(oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k)));
                            bing = bing.add(oiShipVoyage.getStopoiBing().multiply(BigDecimal.valueOf(k)));
                            ding = ding.add(oiShipVoyage.getStopoiDing().multiply(BigDecimal.valueOf(k)));
                            tian = tian.add(oiShipVoyage.getStopoiTian().multiply(BigDecimal.valueOf(k)));
                            methanol = methanol.add(oiShipVoyage.getStopoiOther().multiply(BigDecimal.valueOf(k)));
                            ethanol = ethanol.add(oiShipVoyage.getStopethanol().multiply(BigDecimal.valueOf(k)));

                            tmpCo2Port = oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.11400))).add(
                                    oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.1510)))).add(
                                    oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.206000))));
                            break;
                        case 3:
                            hfo = hfo.add(oiShipVoyage.getOiHfo().add(oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k))));
                            lfo = lfo.add(oiShipVoyage.getOiLfo().add(oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k))));
                            chai = chai.add(oiShipVoyage.getOiChai().add(oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k))));
                            tian = tian.add(oiShipVoyage.getOiTian().add(oiShipVoyage.getStopoiTian().multiply(BigDecimal.valueOf(k))));
                            bing = bing.add(oiShipVoyage.getOiBing().add(oiShipVoyage.getStopoiBing().multiply(BigDecimal.valueOf(k))));
                            ding = ding.add(oiShipVoyage.getOiDing().add(oiShipVoyage.getStopoiDing().multiply(BigDecimal.valueOf(k))));
                            methanol = methanol.add(oiShipVoyage.getOiOther().add(oiShipVoyage.getStopoiOther().multiply(BigDecimal.valueOf(k))));
                            ethanol = ethanol.add(oiShipVoyage.getOiethanol().add(oiShipVoyage.getStopethanol().multiply(BigDecimal.valueOf(k))));

                            tmpCo2Port = oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.11400))).add(
                                    oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.1510)))).add(
                                    oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.206000))));
                            break;
                        default:
                            break;
                    }
                    totalCo2PortAll = totalCo2PortAll.add(tmpCo2Port);
                }

                if (isEuStart == 1 && isEuEnd == 1)//起始和结束都是欧盟
                {
                    double k = 1;
                    Date start1 = getUtcTimeByPort(endPort).get("arr"); //endPort.getArrTm();
//                    Date end1 = endPort.getDeptTm();
                    //Date start1 = oiShipVoyage.getStartTime();
                    Date end1 = oiShipVoyage.getEndTime();
                    BigDecimal tmpCo2Port = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
                    if (start1.getTime() >= startTime.getTime() && end1.getTime() <= endTime.getTime()) {
                        k = 1;
                    }
                    //港口跨终点
                    else if (start1.getTime() >= startTime.getTime() && start1.getTime() <= endTime.getTime() && end1.getTime() >= endTime.getTime()) {
                        k = ((double) endTime.getTime() - start1.getTime()) / ((double) end1.getTime() - start1.getTime());
                    }
                    //港口跨整个统计区间
                    else if (start1.getTime() <= startTime.getTime() && end1.getTime() >= endTime.getTime()) {
                        k = ((double) endTime.getTime() - startTime.getTime()) / ((double) end1.getTime() - start1.getTime());
                    }
                    //港口跨起点
                    else if (start1.getTime() <= startTime.getTime() && end1.getTime() >= startTime.getTime() && end1.getTime() <= endTime.getTime()) {
                        k = ((double) end1.getTime() - startTime.getTime()) / ((double) end1.getTime() - start1.getTime());
                    }
                    //不在统计的时间区间内
                    else if ((start1.getTime() >= endTime.getTime() && end1.getTime() >= endTime.getTime()) || (start1.getTime() <= startTime.getTime() && end1.getTime() <= startTime.getTime())) {
                        k = 0;
                    }

                    switch (timeFlag) {
                        case 1:
                            //起始是EU,结束是EU
                            hfo = hfo.add(oiShipVoyage.getOiHfo().add(oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k))));
                            lfo = lfo.add(oiShipVoyage.getOiLfo().add(oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k))));
                            chai = chai.add(oiShipVoyage.getOiChai().add(oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k))));
                            tian = tian.add(oiShipVoyage.getOiTian().add(oiShipVoyage.getStopoiTian().multiply(BigDecimal.valueOf(k))));
                            bing = bing.add(oiShipVoyage.getOiBing().add(oiShipVoyage.getStopoiBing().multiply(BigDecimal.valueOf(k))));
                            ding = ding.add(oiShipVoyage.getOiDing().add(oiShipVoyage.getStopoiDing().multiply(BigDecimal.valueOf(k))));
                            methanol = methanol.add(oiShipVoyage.getOiOther().add(oiShipVoyage.getStopoiOther().multiply(BigDecimal.valueOf(k))));
                            ethanol = ethanol.add(oiShipVoyage.getOiethanol().add(oiShipVoyage.getStopethanol().multiply(BigDecimal.valueOf(k))));

                            tmpCo2Port = oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.11400))).add(
                                    oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.1510)))).add(
                                    oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.206000))));
                            break;
                        case 2:
                            //起始是EU,结束是EU
                            hfo = hfo.add(oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k)));
                            chai = chai.add(oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k)));
                            lfo = lfo.add(oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k)));
                            bing = bing.add(oiShipVoyage.getStopoiBing().multiply(BigDecimal.valueOf(k)));
                            ding = ding.add(oiShipVoyage.getStopoiDing().multiply(BigDecimal.valueOf(k)));
                            tian = tian.add(oiShipVoyage.getStopoiTian().multiply(BigDecimal.valueOf(k)));
                            methanol = methanol.add(oiShipVoyage.getStopoiOther().multiply(BigDecimal.valueOf(k)));
                            ethanol = ethanol.add(oiShipVoyage.getStopethanol().multiply(BigDecimal.valueOf(k)));

                            tmpCo2Port = oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.11400))).add(
                                    oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.1510)))).add(
                                    oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.206000))));
                            break;
                        case 3:
                            //起始是EU,结束是EU
                            hfo = hfo.add(oiShipVoyage.getOiHfo().add(oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k))));
                            lfo = lfo.add(oiShipVoyage.getOiLfo().add(oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k))));
                            chai = chai.add(oiShipVoyage.getOiChai().add(oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k))));
                            tian = tian.add(oiShipVoyage.getOiTian().add(oiShipVoyage.getStopoiTian().multiply(BigDecimal.valueOf(k))));
                            bing = bing.add(oiShipVoyage.getOiBing().add(oiShipVoyage.getStopoiBing().multiply(BigDecimal.valueOf(k))));
                            ding = ding.add(oiShipVoyage.getOiDing().add(oiShipVoyage.getStopoiDing().multiply(BigDecimal.valueOf(k))));
                            methanol = methanol.add(oiShipVoyage.getOiOther().add(oiShipVoyage.getStopoiOther().multiply(BigDecimal.valueOf(k))));
                            ethanol = ethanol.add(oiShipVoyage.getOiethanol().add(oiShipVoyage.getStopethanol().multiply(BigDecimal.valueOf(k))));

                            tmpCo2Port = oiShipVoyage.getStopoiHfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.11400))).add(
                                    oiShipVoyage.getStopoiLfo().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.1510)))).add(
                                    oiShipVoyage.getStopoiChai().multiply(BigDecimal.valueOf(k).multiply(BigDecimal.valueOf(3.206000))));
                            break;
                        default:
                            break;
                    }
                    totalCo2PortAll = totalCo2PortAll.add(tmpCo2Port);
                }
            }


            //计算油耗量
//            for (int i = 0; i < noAcrossVoyage.size(); i++) {
//                OiShipVoyage oiShipVoyage = noAcrossVoyage.get(i);
//                hfo = hfo.add(oiShipVoyage.getOiHfo()).add(oiShipVoyage.getStopoiHfo());
//                lfo = lfo.add(oiShipVoyage.getOiLfo()).add(oiShipVoyage.getStopoiLfo());
//                chai = chai.add(oiShipVoyage.getOiChai()).add(oiShipVoyage.getStopoiChai());
//                bing = bing.add(oiShipVoyage.getOiBing()).add(oiShipVoyage.getOiBing());
//                tian = tian.add(oiShipVoyage.getOiTian()).add(oiShipVoyage.getOiTian());
//                ding = ding.add(oiShipVoyage.getOiDing()).add(oiShipVoyage.getOiDing());
//                methanol = methanol.add(oiShipVoyage.getOiOther()).add(oiShipVoyage.getOiOther());
//                ethanol = ethanol.add(oiShipVoyage.getOiethanol()).add(oiShipVoyage.getOiethanol());
//            }
            //计算总的co2排放量
            BigDecimal co2cost = hfo.multiply(BigDecimal.valueOf(3.11400))
                    .add(lfo.multiply(BigDecimal.valueOf(3.1510)))
                    .add(chai.multiply(BigDecimal.valueOf(3.206000)))
                    .add(bing.multiply(BigDecimal.valueOf(3.000000)))
                    .add(ding.multiply(BigDecimal.valueOf(3.030000)))
                    .add(tian.multiply(BigDecimal.valueOf(2.750000)))
                    .add(methanol.multiply(BigDecimal.valueOf(1.375000)))
                    .add(ethanol.multiply(BigDecimal.valueOf(1.913000)));
            BigDecimal allEuCo2 = co2cost;
//            BigDecimal allEuCo2 = commonEnergyEfficServiceImpl.getVoyageCo2cost(oiShipVoyages);
            //计算欧盟之间的co2的排放
            BigDecimal doubleEuCo2 = commonEnergyEfficServiceImpl.getVoyageCo2costEu(doubleEu);
            BigDecimal startEuCo2 = commonEnergyEfficServiceImpl.getVoyageCo2costEu(startEu);
            BigDecimal endEuCo2 = commonEnergyEfficServiceImpl.getVoyageCo2costEu(endEu);
            //计算EU港口停港co2排放量
            List<RawVoyagePort> byIsEu = rawVoyagePortRepository.findByIsEu(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));//查询报告时间段内的港口
            //筛选出开始时间和结束时间内的港口
            Date finalStartTime1 = startTime;
            Date finalEndTime1 = endTime;
            RawVoyagePort rawVoyagePort = new RawVoyagePort();
            rawVoyagePort.setInPort("0");
            rawVoyagePort.setRecordType("0");
            List<RawVoyagePort> collect = byIsEu.stream().filter(x -> {
                Map<String, Date> utcTimeByPort = getUtcTimeByPort(x);
                Date arr = utcTimeByPort.get("arr");
                Date dept = utcTimeByPort.get("dept");
                String recordType = x.getRecordType();
                String inPort = x.getInPort();
                return (arr.getTime() >= finalStartTime1.getTime() && arr.getTime() <= finalEndTime1.getTime()) && (dept.getTime() >= finalStartTime1.getTime() && dept.getTime() <= finalEndTime1.getTime() && recordType.equals("0") && "0".equals(inPort));
            }).collect(Collectors.toList());
            //合并港口
            List<RawVoyagePort> rawVoyagePorts = energyEefficService.procVoyageByMergePorts(collect);
            rawVoyagePorts.add(0, rawVoyagePort);
            CalculateOilVo sailing = energyEefficService.calculateSailingOil(rawVoyagePorts, "stop");
            BigDecimal zero = BigDecimal.ZERO;
            OiShipVoyage oiShipVoyageSailing = new OiShipVoyage(zero, zero, zero, zero, zero, zero, zero, zero, sailing.getOiHfo(), sailing.getOiLfo(),
                    sailing.getOiChai(), sailing.getOiBing(), sailing.getOiDing(), sailing.getOiTian(), sailing.getOiOther(), sailing.getOiethanol());
            BigDecimal voyageCo2costStop = commonEnergyEfficServiceImpl.getVoyageCo2cost(Arrays.asList(oiShipVoyageSailing));
            //查询冰区航行记录
            List<RawVoyageSpec> byAcrossYearRawVoyageSpecs = rawVoyageSpecRepository.findByAcrossYear(shipId, simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));
            //计算冰区航行里程和航行小时
            BigDecimal specDistance = byAcrossYearRawVoyageSpecs.stream().map(RawVoyageSpec::getDistance).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal specHour = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);
            for (int i = 0; i < byAcrossYearRawVoyageSpecs.size(); i++) {
                RawVoyageSpec rawVoyageSpec = byAcrossYearRawVoyageSpecs.get(i);
                long startTimeSpec = rawVoyageSpec.getBeginTm().getTime();
                long endTimeSpec = rawVoyageSpec.getEndTm().getTime();
                long h = 1000 * 60 * 60;
                BigDecimal sailTime = BigDecimal.valueOf((double) (endTimeSpec - startTimeSpec) / h);
                specHour = specHour.add(sailTime);
            }
            BigDecimal allOil = hfo.add(lfo).add(chai).add(methanol).add(ethanol).add(bing).add(ding);
            BigDecimal oilPermile = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);//每海里油耗
            BigDecimal co2PerMile = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);//每海里co2排放量
            if (allDistance.compareTo(BigDecimal.ZERO) > 0) {
                //计算每海里燃油消耗量
                oilPermile = allOil.multiply(BigDecimal.valueOf(1000)).divide(allDistance, 2, BigDecimal.ROUND_HALF_UP);
                //计算每海里co2排放量
                co2PerMile = allEuCo2.multiply(BigDecimal.valueOf(1000)).divide(allDistance, 2, BigDecimal.ROUND_HALF_UP);
            }
            BigDecimal oilPerTd = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);//每运输功燃油消耗
            BigDecimal co2PerTd = new BigDecimal(0.0000).setScale(4, BigDecimal.ROUND_HALF_UP);//每运输功co2排放
            if (eeoiTd.compareTo(BigDecimal.ZERO) > 0) {
                //计算每运输功燃油消耗量
                oilPerTd = allOil.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 4, BigDecimal.ROUND_HALF_UP);//将吨转换成克
                //计算每运输功燃油消耗量
                co2PerTd = allEuCo2.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 4, BigDecimal.ROUND_HALF_UP);//将吨转换成克
            }
            //获取排放源
            List<OiMainEngine> oiMainEngines = oiShipInfo.getOiMainEngines();
            List<OiShipGe> oiShipGes = oiShipInfo.getOiShipGes();
            List<OiShipBoiler> oiShipBoilers = oiShipInfo.getOiShipBoilers();
            StringBuilder emissionSource = new StringBuilder();
            if (oiMainEngines.size() > 0) {
                emissionSource.append("main engines");
            }
            if (oiShipGes.size() > 0) {
                if (oiMainEngines.size() > 0) {
                    emissionSource.append(",auxiliary engines");
                } else {
                    emissionSource.append("auxiliary engines");
                }
            }
            if (oiShipBoilers.size() > 0) {
                if (oiMainEngines.size() > 0 || oiShipGes.size() > 0) {
                    emissionSource.append(",boilers");
                } else {
                    emissionSource.append("boilers");
                }
            }
            EuRptVo euRptVo = new EuRptVo();
            Map<String, String> shipMap = fuelConsMethodProperties.getShipMap();
            if (byIdAndIsDelete != null) {
                euRptVo.setId(byIdAndIsDelete.getId());
                euRptVo.setEuLadenCons(byIdAndIsDelete.getEuLadenCons());
                euRptVo.setEuCargoheatCons(byIdAndIsDelete.getEuCargoheatCons());
            }
            euRptVo.setShipId(oiShipInfo.getId());
            euRptVo.setShipName(oiShipInfo.getSpname());
            euRptVo.setImono(oiShipInfo.getImono());
            euRptVo.setHomePortCode(oiShipInfo.getHomePort());
            euRptVo.setHfoFactorr(fuelCo2CostProperties.getHfo());
            euRptVo.setLfoFactorr(fuelCo2CostProperties.getLfo());
            euRptVo.setChaiFactorr(fuelCo2CostProperties.getOiChaiOrOiQi());
            euRptVo.setCountryFlag(oiShipInfo.getFlagCode());
            //查询船籍港
            if (byCode != null) {
                euRptVo.setHomePortName(byCode.getEnName());
            }
            euRptVo.setIceClass(oiShipInfo.getIce());
            euRptVo.setSpType(shipType == null ? null : shipType.getSptype());
            euRptVo.setSpTypeCode(oiShipInfo.getSptype());
            euRptVo.setEediVal(oiShipInfo.getEedivalue());
            euRptVo.setEivVal(oiShipInfo.getEiv());
            //查询管理公司
            List<GcClient> gcClientList = gcClientRepository.findByCodeAndIsDelete(oiShipInfo.getDocId());
            GcClient gcClient = null;
            if (gcClientList != null && gcClientList.size() > 0) {
                gcClient = gcClientList.get(0);
            }
            if (gcClient != null) {
                euRptVo.setDocName(gcClient.getNameEn());
                euRptVo.setDocAddr(gcClient.getAddrEn());
                euRptVo.setDocRegAddr(gcClient.getAddrEn());
            }
            //查询船东
            List<GcClient> gcClientOwnerList = gcClientRepository.findByCodeAndIsDelete(oiShipInfo.getOwnerId());
            GcClient gcClientOwner = null;
            if (gcClientOwnerList != null && gcClientOwnerList.size() > 0) {
                gcClientOwner = gcClientOwnerList.get(0);
            }
            if (gcClientOwner != null) {
                euRptVo.setOwnerName(gcClientOwner.getNameEn());
                euRptVo.setOwnerAddr(gcClientOwner.getAddrEn());
                euRptVo.setOwnerRegAddr(gcClientOwner.getAddrEn());
            }
            euRptVo.setAccrNum(ACCR_NUM);
            euRptVo.setVerifierName(VERIFY_NAME);
            euRptVo.setVerifierAddr(VERIFY_ADDR);
            euRptVo.setHomePortCode(oiShipInfo.getHomePort());
            euRptVo.setHomePortName(oiShipInfo.getHomePort());
            euRptVo.setMrvpEmail(oiShipInfo.getEmail());
            euRptVo.setMrvpTele(oiShipInfo.getTelephone());
            euRptVo.setMrvpFname(oiShipInfo.getFirstname());
            euRptVo.setMrvpJobtitle(oiShipInfo.getJobtitle());
            euRptVo.setMrvpTitle(oiShipInfo.getTitle());
            euRptVo.setCo2PerMile2(co2PerMile.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setCo2PerWork2(co2PerTd.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setFuelConsPerMile(oilPermile.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setFuelConsPerWork(oilPerTd.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setEuInnerCo2Tons(doubleEuCo2.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setEuToCo2Tons(endEuCo2.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setEuFrCo2Tons(startEuCo2.setScale(4, BigDecimal.ROUND_HALF_UP));
//            euRptVo.setEuBerthCo2Tons(allEuCo2.subtract(doubleEuCo2).subtract(startEuCo2).subtract(endEuCo2).setScale(2, BigDecimal.ROUND_HALF_UP));
            euRptVo.setEuBerthCo2Tons(totalCo2PortAll.setScale(4, BigDecimal.ROUND_HALF_UP));

            euRptVo.setDistOnsea(allDistance.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setHourOnsea(allDisHour.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setDistIce(specDistance.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setHourIce(specHour.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setTransportWork(eeoiTd.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setEuCo2Tons(allEuCo2.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setHfoTons(hfo.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setLfoTons(lfo.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setDgoTons(chai.setScale(4, BigDecimal.ROUND_HALF_UP));
            euRptVo.setEmmSource(emissionSource.toString());
            euRptVo.setEmmGeMethod(shipMap.get(oiShipInfo.getConsMethod()));
            euRptVo.setEmmMeMethod(shipMap.get(oiShipInfo.getConsMethod()));
            euRptVo.setEmmBoMethod2(shipMap.get(oiShipInfo.getConsMethod()));
            euRptVo.setEmmGeUncer(EMM_BO_UNCER);
            euRptVo.setEmmMeUncer(EMM_BO_UNCER);
            euRptVo.setEmmBoUncer2(EMM_BO_UNCER);
            euRptVo.setStartTm(startTime);
            euRptVo.setEndTm(endTime);
            return new ResultObjectVo<>(euRptVo);
        } else {
            EuRptVo euRptVo = new EuRptVo();
            BeanUtils.copyProperties(byIdAndIsDelete, euRptVo);
            euRptVo.setSpType(shipType == null ? null : shipType.getCsptype());
            if (byCode != null) {
                euRptVo.setHomePortName(byCode.getEnName());
            }
            return new ResultObjectVo<EuRptVo>(euRptVo);
        }

    }

    @Override
    @Transactional
    public ResultVo saveEuDcs(EuRptVo euRptVo) {
        EuEmissionRpt euEmissionRpt = new EuEmissionRpt();
        EuEmissionRpt byIdAndIsDelete = euEmissionRptRepository.findByIdAndIsDelete(euRptVo.getId(), 0);
        BeanUtils.copyProperties(euRptVo, euEmissionRpt);
        if (byIdAndIsDelete != null) {
            euEmissionRpt.setFileId(byIdAndIsDelete.getFileId());
        }
        euEmissionRpt.setIsDelete(0);
        euEmissionRpt.setRecStatus("1");
        euEmissionRpt.setRptSource("1");
        //上传欧盟报告文件
        if (euRptVo.getFileDir() != null) {
            String euFtpDir = ftpUtils.upFile(euRptVo.getFileDir());
            SysFile euSysFile = new SysFile();
            euSysFile.setPath(euFtpDir);
            euSysFile.setName(euRptVo.getFileName());
            SysFile euFile = sysFileRepository.save(euSysFile);
            euEmissionRpt.setFileId(euFile.getId());
        }
        EuEmissionRpt save = euEmissionRptRepository.save(euEmissionRpt);
        if (save != null) {
            return new ResultStringVo(this.msg("energyEeffic.create.success"));
        } else {
            return new ResultStringVo(this.msg("energyEeffic.create.failed"));
        }
    }

    @Override
    @Transactional
    public ResultVo deleteEuDcs(EuRptVo euRptVo) {
        EuEmissionRpt byIdAndIsDelete = euEmissionRptRepository.findByIdAndIsDelete(euRptVo.getId(), 0);
        EuEmissionRpt save = null;
        if (byIdAndIsDelete != null) {
            byIdAndIsDelete.setIsDelete(1);
            save = euEmissionRptRepository.save(byIdAndIsDelete);
        }
        if (save != null) {
            return new ResultStringVo(this.msg("energyEeffic.delete.success"));
        } else {
            return new ResultErrorVo(this.msg("energyEeffic.delete.failed"));
        }
    }

    @Override
    public ResultVo getEudcs(ImoDcsRptManagerDto imoDcsRptManagerDto) {
        //分页条件
        PageRequest of = PageRequest.of((imoDcsRptManagerDto.getCurrentPage() - 1), imoDcsRptManagerDto.getPageSize());
        Page<EuEmissionRpt> all = euEmissionRptRepository.findAll(queryEuReaportList(imoDcsRptManagerDto), of);
        List<EuEmissionRpt> euEmissionRpts = all.getContent();
        List<ImoDcsRptManagerVo> imoDcsRptManagerVos = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        for (int i = 0; i < euEmissionRpts.size(); i++) {
            EuEmissionRpt euEmissionRpt = euEmissionRpts.get(i);
            ImoDcsRptManagerVo imoDcsRptManagerVo = new ImoDcsRptManagerVo();
            imoDcsRptManagerVo.setId(euEmissionRpt.getId());
            imoDcsRptManagerVo.setDocManager(euEmissionRpt.getDocName());
            GcState gcState = gcStateRepository.findFlag(euEmissionRpt.getCountryFlag());
            imoDcsRptManagerVo.setFlag(gcState == null ? null : gcState.getEnName());
            imoDcsRptManagerVo.setImoNu(euEmissionRpt.getImono());
            ShipType shipType = shipTypeRepository.findShipType(euEmissionRpt.getSpTypeCode());
            imoDcsRptManagerVo.setShipType(shipType == null ? null : shipType.getSptype());
            imoDcsRptManagerVo.setRptStatus(euEmissionRpt.getRecStatus());
            imoDcsRptManagerVo.setReaportSource(euEmissionRpt.getRptSource());
            imoDcsRptManagerVo.setShipName(euEmissionRpt.getShipName());
            imoDcsRptManagerVo.setShipId(euEmissionRpt.getShipId());
            imoDcsRptManagerVo.setRptStartTime(euEmissionRpt.getStartTm());
            imoDcsRptManagerVo.setRptEndTime(euEmissionRpt.getEndTm());
            instance.setTime(euEmissionRpt.getStartTm());
            imoDcsRptManagerVo.setEuMrvYear(String.valueOf(instance.get(Calendar.YEAR)));
            imoDcsRptManagerVos.add(imoDcsRptManagerVo);
        }
        PageDataVo<ImoDcsRptManagerVo> imoDcsRptManagerVoPageDataVo = new PageDataVo<>();
        imoDcsRptManagerVoPageDataVo.setItems(imoDcsRptManagerVos);
        imoDcsRptManagerVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(imoDcsRptManagerVoPageDataVo);
    }

    @Override
    public void downEuDcs(EuRptVo euRptVo, HttpServletResponse response) {
        InputStream inputStream = this.getClass().getResourceAsStream("/downLoad/EU_MRV_TEMPLATE.xlsx");
        XLSTransformer xlsTransformer = new XLSTransformer();
        org.apache.poi.ss.usermodel.Workbook sheets = null;
        Map<String, Object> map = EntityBeanUtils.objectToMap(euRptVo);

        //处理整型
        Map<String, Object> handleMap = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            if (v instanceof BigDecimal) {
                ((BigDecimal) v).setScale(4, BigDecimal.ROUND_HALF_UP);
                String num2str = v.toString();
                handleMap.put(k, num2str);
            } else {
                handleMap.put(k, v);
            }

        });

        downLoadResponse(response, inputStream, xlsTransformer, handleMap);
    }

//    private void downLoadResponse(HttpServletResponse response, InputStream inputStream, XLSTransformer xlsTransformer, Map<String, Object> map) {
//        org.apache.poi.ss.usermodel.Workbook sheets;
//        try {
//            sheets = xlsTransformer.transformXLS(inputStream, map);
//        } catch (InvalidFormatException e) {
//            log.error("不合理的报告模板");
//            e.printStackTrace();
//            throw new ExplicitException("不合理的报告模板");
//        }
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
//        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
//        ServletOutputStream outputStream = null;
//        try {
//            outputStream = response.getOutputStream();
//            sheets.write(outputStream);
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void downCmsa(CmsaRptDto cmsaRptDto, HttpServletResponse response) {
        Date startTime = cmsaRptDto.getStartTime();
        Date endTime = cmsaRptDto.getEndTime();
        String shipId = cmsaRptDto.getShipId();
        //查询该船是否有船旗转换或者经营人变更
        List<OiShipChglog> oiShipChglogFlag = oiShipChglogRepository.findByShipIdAndIsDeleteAndChgType(shipId, 0, 0);//查询船旗国变更
        List<OiShipChglog> oiShipChglogDoc = oiShipChglogRepository.findByShipIdAndIsDeleteAndChgType(shipId, 0, 1);//查询经营人变更
        cmsaRptDto.setIsTurnToFlalg(oiShipChglogFlag.size() > 0);
        cmsaRptDto.setIsTurnToDoc(oiShipChglogDoc.size() > 0);
        cmsaRptDto.setNotUse(oiShipChglogDoc.size() == 0 && oiShipChglogFlag.size() == 0);
        InputStream inputStream = this.getClass().getResourceAsStream("/downLoad/CMSA_REAPORT.xlsx");
        XLSTransformer xlsTransformer = new XLSTransformer();
        org.apache.poi.ss.usermodel.Workbook sheets = null;
        cmsaRptDto.setYearRpt(true);
        cmsaRptDto.setVoyageRpt(false);
        Map<String, Object> map = new HashMap<>();
        ResultVo cmsaRpt = energyEefficService.getCmsaRpt(cmsaRptDto);
        if (cmsaRpt.isResult()) {
            CmsaRptVo data = (CmsaRptVo) cmsaRpt.getData();
            map = EntityBeanUtils.objectToMap(data);
            Boolean voyageRpt = (Boolean) map.get("voyageRpt");
            Boolean yearRpt = (Boolean) map.get("yearRpt");
            Boolean isTurnToFlalg = (Boolean) map.get("isTurnToFlalg");
            Boolean isTurnToDoc = (Boolean) map.get("isTurnToDoc");
            Boolean notUse = (Boolean) map.get("notUse");
            if (voyageRpt == null || voyageRpt) {
                map.put("voyageReaPort", "☒");
            } else {
                map.put("voyageReaPort", "□");
            }
            if (yearRpt == null || yearRpt) {
                map.put("yearReaPort", "☒");
            } else {
                map.put("yearReaPort", "□");
            }
            if (isTurnToFlalg == null || isTurnToFlalg) {
                map.put("transferFlag", "☒");
            } else {
                map.put("transferFlag", "□");
            }
            if (isTurnToDoc == null || isTurnToDoc) {
                map.put("transferOwner", "☒");
            } else {
                map.put("transferOwner", "□");
            }
            if (notUse == null || notUse) {
                map.put("notUse", "☒");
            } else {
                map.put("notUse", "□");
            }
        }
        downLoadResponse(response, inputStream, xlsTransformer, map);
    }

    @Override
    public ResultVo imoReport(String[] ids) {
        List<ReaportImoRptVo> reportVos = getReaportImoRptVo(Arrays.asList(ids));
        Integer flag = 0;
        for (ReaportImoRptVo item : reportVos) {
            boolean b = imoReportClient.sendPost(item);
            if (b) {
                flag++;
                ImoStdRptStatusTagDto imoStdRptStatusTagDto = new ImoStdRptStatusTagDto();
                imoStdRptStatusTagDto.setImoStdRptIds(Arrays.asList(item.getId()));
                imoStdRptStatusTag(imoStdRptStatusTagDto);
            }
        }
        if (flag == reportVos.size()) {
            return new ResultStringVo(this.msg("imoRpt.submission.success"));
        } else {
            return new ResultErrorVo(this.msg("imoRpt.submission.failed"));
        }
    }

    @Override
    public String imoLibyaReports(LibyaDownLoadVo libyaDownLoadVo) {
        List<String> libyaIds = getLibya(libyaDownLoadVo);
        List<ReaportImoRptVo> reaportImoRptVos = getReaportImoRptVo(libyaIds);
        return imoReportClient.downloadLibyaReports(reaportImoRptVos);
    }

    //    @Override
//    public ResultVo shipComPanyYear(EnergyEfficDto energyEfficDto) {
//        String startYear = energyEfficDto.getStartYear();
//        String endYear = energyEfficDto.getEndYear();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<OiShipTask> oiShipTasks = oiShipTaskRepository.findAll(queryVolageList(energyEfficDto));
//        List<SingalShipStatVo> singalShipStatVos = new ArrayList<>();
//        Map<String, List<OiShipTask>> shipGroupMap = oiShipTasks.stream().collect(Collectors.groupingBy(oiShipTask -> oiShipTask.getShipId()));
//        Set<String> set = shipGroupMap.keySet();
//        List<String> shipIds = new ArrayList<>(set);
//        GcClient byCodeAndIsDelete = gcClientRepository.findByCodeAndIsDelete(energyEfficDto.getComPany());
//        String companyName = null;
//        if (byCodeAndIsDelete != null) {
//            companyName = byCodeAndIsDelete.getNameEn();
//        }
//        //划分年度区间
//        int xh = 0;
//        for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
//            xh++;
//            boolean flag = false;
//            try {
//                Date startTime = simpleDateFormat.parse(Integer.valueOf(i) + "-01-01 00:00:00");
//                Date endTime = simpleDateFormat.parse(Integer.valueOf(i) + "-12-31 23:59:00");
//                //判断期初量是否存在
//                List<OiShipTask> collect = oiShipTasks.stream().filter(oiShipTask -> {
//                    if (oiShipTask.getStartTime() != null && oiShipTask.getEndTime() != null) {
//                        return (oiShipTask.getStartTime().getTime() >= startTime.getTime() && oiShipTask.getStartTime().getTime() <= endTime.getTime())
//                                || (oiShipTask.getEndTime().getTime() >= startTime.getTime() && oiShipTask.getEndTime().getTime() <= endTime.getTime());
//                    } else {
//                        return false;
//                    }
//                }).collect(Collectors.toList());
//                if (collect.size() > 0) {
//                    for (int j = 0; j < shipIds.size(); j++) {
//                        String shipId = shipIds.get(j);
//                        Integer count = beginPeriodRepository.findByShipIdAndIsDeleteAndPeriodTimeOrPeriodTime(shipId, Integer.valueOf(i) + "-01-01 00:00:00", Integer.valueOf(i + 1) + "-01-01 00:00:00");
//                        if (count < 2) {
//                            SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
//                            singalShipStatVo.setComPanyName(companyName);
//                            singalShipStatVo.setXh(xh);
//                            singalShipStatVo.setYear(String.valueOf(i));
//                            singalShipStatVos.add(singalShipStatVo);
//                            flag = true;
//                            break;
//                        }
//                    }
//                    if (!flag) {
//                        SingalShipStatVo singalShipEnery = getSingalShipEnery(collect, startTime, endTime);
//                        singalShipEnery.setComPanyName(companyName);
//                        singalShipEnery.setXh(xh);
//                        singalShipStatVos.add(singalShipEnery);
//                    }
//                } else {
//                    SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
//                    singalShipStatVo.setComPanyName(companyName);
//                    singalShipStatVo.setXh(xh);
//                    singalShipStatVo.setYear(String.valueOf(i));
//                    singalShipStatVos.add(singalShipStatVo);
//                }
//            } catch (ParseException e) {
//                log.error("时间转换错误=={}", Integer.valueOf(startYear) + "-01-01 00:00:00");
//                e.printStackTrace();
//                throw new ExplicitException("时间类型转换错误");
//            }
//        }
//        return new ResultObjectVo<>(singalShipStatVos);
//    }
    @Override
    public ResultVo shipComPanyYear(EnergyEfficDto energyEfficDto) {
        String startYear = energyEfficDto.getStartYear();
        String endYear = energyEfficDto.getEndYear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OiShipVoyage> oiShipVoyages = oiShipVoyageRepository.findAll(queryVolageList(energyEfficDto));
        List<SingalShipStatVo> singalShipStatVos = new ArrayList<>();
        List<GcClient> gcClientList = gcClientRepository.findByCodeAndIsDelete(energyEfficDto.getComPany());
        GcClient gcClient = null;
        if (gcClientList != null && gcClientList.size() > 0) {
            gcClient = gcClientList.get(0);
        }
        String companyName = null;
        if (gcClient != null) {
            companyName = gcClient.getNameEn();
        }
        //划分年度区间
        int xh = 0;
        for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
            xh++;
            boolean flag = false;
            try {
                Date startTime = simpleDateFormat.parse(Integer.valueOf(i) + "-01-01 00:00:00");
                Date endTime = simpleDateFormat.parse(Integer.valueOf(i) + "-12-31 23:59:00");
                //判断期初量是否存在
                List<OiShipVoyage> collect = oiShipVoyages.stream().filter(oiShipVoyage -> {
                    if (oiShipVoyage.getStartTime() != null && oiShipVoyage.getEndTime() != null) {
                        return (oiShipVoyage.getStartTime().getTime() >= startTime.getTime() && oiShipVoyage.getStartTime().getTime() <= endTime.getTime());
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());
                if (collect.size() > 0) {
                    SingalShipStatVo singalShipEnery = getSingalShipEnery(collect, startTime, endTime);
                    singalShipEnery.setComPanyName(companyName);
                    singalShipEnery.setXh(xh);
                    singalShipStatVos.add(singalShipEnery);
                } else {
                    SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
                    singalShipStatVo.setComPanyName(companyName);
                    singalShipStatVo.setXh(xh);
                    singalShipStatVo.setYear(String.valueOf(i));
                    singalShipStatVos.add(singalShipStatVo);
                }
            } catch (ParseException e) {
                log.error("时间转换错误=={}", Integer.valueOf(startYear) + "-01-01 00:00:00");
                e.printStackTrace();
                throw new ExplicitException("时间类型转换错误");
            }
        }
        return new ResultObjectVo<>(singalShipStatVos);
    }

    @Override
    public void downLoadSigalShip(EnergyEfficDto energyEfficDto, HttpServletResponse response) {
        List<SingalShipStatVo> singalShipStatVos = (List<SingalShipStatVo>) getSingalShip(energyEfficDto, null).getData();
        Map<String, Object> map = new HashMap<>();
        InputStream inputStream = null;
        String dataType = energyEfficDto.getDataType();
        List<GcClient> gcClientList = gcClientRepository.findByCodeAndIsDelete(energyEfficDto.getComPany());
        GcClient gcClient = null;
        if (gcClientList != null && gcClientList.size() > 0) {
            gcClient = gcClientList.get(0);
        }
        if ("0".equals(dataType)) {
            if ("0".equals(energyEfficDto.getExportType())) {
                inputStream = this.getClass().getResourceAsStream("/downLoad/SINGAL_SHIP_STATIS_EN.xlsx");
            } else {
                inputStream = this.getClass().getResourceAsStream("/downLoad/SINGAL_SHIP_STATIS_CN.xlsx");
            }
        } else {
            if ("0".equals(energyEfficDto.getExportType())) {
                inputStream = this.getClass().getResourceAsStream("/downLoad/SINGAL_SHIP_NENG_STATIS_EN.xlsx");
            } else {
                inputStream = this.getClass().getResourceAsStream("/downLoad/SINGAL_SHIP_NENG_STATIS_CN.xlsx");
            }
        }
        XLSTransformer xlsTransformer = new XLSTransformer();
        //查询船舶类型
        ShipType shipType = shipTypeRepository.findShipType(energyEfficDto.getSpTypeCode());
        if (shipType != null) {
            map.put("shipType", shipType.getCsptype());
        }
        OiShipInfo byIdAndIsDelete = oiShipInfoRepository.findByIdAndIsDelete(energyEfficDto.getShipName(), 0);
        if (byIdAndIsDelete != null) {
            map.put("shipName", byIdAndIsDelete.getSpname());
        }
        if (gcClient != null) {
            map.put("comPanyName", gcClient.getNameEn());
        }
        map.put("startYear", energyEfficDto.getStartYear());
        map.put("endYear", energyEfficDto.getEndYear());
        map.put("list", singalShipStatVos);
        downLoadResponse(response, inputStream, xlsTransformer, map);

    }

    @Override
    public void downLoadComPanyShip(EnergyEfficDto energyEfficDto, HttpServletResponse response) {
        //查询公司名称
        List<GcClient> gcClientList = gcClientRepository.findByCodeAndIsDelete(energyEfficDto.getComPany());
        GcClient gcClient = null;
        if (gcClientList != null && gcClientList.size() > 0) {
            gcClient = gcClientList.get(0);
        }
        Map<String, Object> map = new HashMap<>();
        String comPanyName = null;
        if (gcClient != null) {
            map.put("comPanyName", gcClient.getNameEn());
            comPanyName = gcClient.getNameEn();
        }
        List<SingalShipStatVo> singalShipStatVos = (List<SingalShipStatVo>) shipComPanyYear(energyEfficDto).getData();
        InputStream inputStream = null;
        String dataType = energyEfficDto.getDataType();
        if ("0".equals(dataType)) {
            if ("0".equals(energyEfficDto.getExportType())) {
                inputStream = this.getClass().getResourceAsStream("/downLoad/COMPANY_SHIP_STATIS_EN.xlsx");
            } else {
                inputStream = this.getClass().getResourceAsStream("/downLoad/COMPANY_SHIP_STATIS_CN.xlsx");
            }
        } else {
            if ("0".equals(energyEfficDto.getExportType())) {
                inputStream = this.getClass().getResourceAsStream("/downLoad/COMPANY_SHIP_NENG_STATIS_EN.xlsx");
            } else {
                inputStream = this.getClass().getResourceAsStream("/downLoad/COMPANY_SHIP_NENG_STATIS_CN.xlsx");
            }
        }
        XLSTransformer xlsTransformer = new XLSTransformer();
        map.put("startYear", energyEfficDto.getStartYear());
        map.put("endYear", energyEfficDto.getEndYear());
        map.put("list", singalShipStatVos);
        downLoadResponse(response, inputStream, xlsTransformer, map);
    }

    @Override
    public ResultVo getEnergyEfficencyTrend(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto) {
        //查询航次信息
        String rollingCycleUnit = energyEfficiencyTrendAnalysisDto.getRollingCycleUnit();
        String shipId = energyEfficiencyTrendAnalysisDto.getShipId();
        String shipCompanyId = energyEfficiencyTrendAnalysisDto.getShipCompanyId();
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);
        List<GcClient> gcClientList = gcClientRepository.findByCodeAndIsDelete(shipCompanyId);
        GcClient gcClient = null;
        if (gcClientList != null && gcClientList.size() > 0) {
            gcClient = gcClientList.get(0);
        }
        List<OiShipVoyage> oiShipVoyageNoVayage = new ArrayList<>();
        List<OiShipTask> oiShipTasks = new ArrayList<>();
        if (!"航次".equals(rollingCycleUnit)) {
            oiShipVoyageNoVayage = oiShipVoyageRepository.findAll(queryEnergyVoyageList(energyEfficiencyTrendAnalysisDto));
        } else {
            oiShipTasks = oiShipTaskRepository.findAll(queryEnergyVolageList(energyEfficiencyTrendAnalysisDto));
        }
        String name = null;
        if (oiShipInfo != null) {
            name = "（" + oiShipInfo.getSpname() + "）";
        } else {
            if (gcClient != null) {
                name = "（" + gcClient.getNameEn() + "）";
            }
        }
        List<String> indexItems = energyEfficiencyTrendAnalysisDto.getIndexItems();//获取指标项
        String startYear = energyEfficiencyTrendAnalysisDto.getStartYear();
        String endYear = energyEfficiencyTrendAnalysisDto.getEndYear();
        BigDecimal zero = BigDecimal.ZERO;
        //eeoi
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVosEeoi = new ArrayList<>();
        EneryEfficTrendIndexVo eneryEfficTrendIndexVoEeoi = new EneryEfficTrendIndexVo();
        eneryEfficTrendIndexVoEeoi.setId("1");
        //每海里油耗
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVosFuelPerMile = new ArrayList<>();
        EneryEfficTrendIndexVo eneryEfficTrendIndexVoFuelPerMile = new EneryEfficTrendIndexVo();
        eneryEfficTrendIndexVoFuelPerMile.setId("2");
        //每运输功油耗
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVosFuelPerT = new ArrayList<>();
        EneryEfficTrendIndexVo eneryEfficTrendIndexVoFuelPerT = new EneryEfficTrendIndexVo();
        eneryEfficTrendIndexVoFuelPerT.setId("3");
        //每海里二氧化碳排放
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVosCo2PerMile = new ArrayList<>();
        EneryEfficTrendIndexVo eneryEfficTrendIndexVoCo2PerMile = new EneryEfficTrendIndexVo();
        eneryEfficTrendIndexVoCo2PerMile.setId("4");
        //每运输单位二氧化碳排放
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVosCo2PerT = new ArrayList<>();
        EneryEfficTrendIndexVo eneryEfficTrendIndexVoCo2PerT = new EneryEfficTrendIndexVo();
        eneryEfficTrendIndexVoCo2PerT.setId("5");
        //降速比
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVosDropRadio = new ArrayList<>();
        EneryEfficTrendIndexVo eneryEfficTrendIndexVoDropRadio = new EneryEfficTrendIndexVo();
        eneryEfficTrendIndexVoDropRadio.setId("6");
        //载货量利用率
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVosUserRate = new ArrayList<>();
        EneryEfficTrendIndexVo eneryEfficTrendIndexVoUserRate = new EneryEfficTrendIndexVo();
        eneryEfficTrendIndexVoUserRate.setId("7");
//        1：eeoi 2：每海里油耗 3：每运输单位油耗 4：每海里co2排放
//                * 5：每运输单位co2排放 6：降速比 7：载货量利用率
        if ("航次".equals(rollingCycleUnit)) {
            for (int j = 0; j < oiShipTasks.size(); j++) {
                EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoEeoi = new EneryEfficTrendIndexDataVo();//eeoi
                eneryEfficTrendIndexDataVoEeoi.setIndexId("1");
                EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoFuelPerMile = new EneryEfficTrendIndexDataVo();//每海里油耗
                eneryEfficTrendIndexDataVoFuelPerMile.setIndexId("2");
                EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoFuelPerT = new EneryEfficTrendIndexDataVo();//每运输功油耗
                eneryEfficTrendIndexDataVoFuelPerT.setIndexId("3");
                EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoCo2PerMile = new EneryEfficTrendIndexDataVo();//每海里二氧化碳排放
                eneryEfficTrendIndexDataVoCo2PerMile.setIndexId("4");
                EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoCo2PerT = new EneryEfficTrendIndexDataVo();//每运输单位二氧化碳排放
                eneryEfficTrendIndexDataVoCo2PerT.setIndexId("5");
                EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoDropRadio = new EneryEfficTrendIndexDataVo();//降速比
                eneryEfficTrendIndexDataVoDropRadio.setIndexId("6");
                EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoUserRate = new EneryEfficTrendIndexDataVo();//载货量利用率
                eneryEfficTrendIndexDataVoUserRate.setIndexId("7");
                OiShipTask oiShipTask = oiShipTasks.get(j);
                BigDecimal userate = oiShipTask.getUserate();//载货量利用率
                if (userate == null) {
                    userate = new BigDecimal(0.0000);
                }
                BigDecimal speedrate = oiShipTask.getSpeedrate();//降速比
                if (speedrate == null) {
                    speedrate = new BigDecimal(0.0000);
                }
                List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
                BigDecimal distanceSum = new BigDecimal(0.0000);
                BigDecimal cargoSum = new BigDecimal(0.0000);
                BigDecimal co2Cost = new BigDecimal(0.0000);
                BigDecimal oiHfoSum = new BigDecimal(0.0000);
                for (int k = 0; k < oiShipVoyages.size(); k++) {
                    OiShipVoyage oiShipVoyage = oiShipVoyages.get(k);
                    BigDecimal distance = oiShipVoyage.getDistance();
                    BigDecimal cargo = oiShipVoyage.getCargo();
                    BigDecimal co2 = oiShipVoyage.getCo2Cost();
                    BigDecimal oiHfo = oiShipVoyage.getOiHfo();
                    if (distance == null) {
                        distance = zero;
                    }
                    if (cargo == null) {
                        cargo = zero;
                    }
                    if (co2 == null) {
                        co2 = zero;
                    }
                    if (oiHfo == null) {
                        oiHfo = zero;
                    }
                    oiHfoSum = oiHfoSum.add(oiHfo);
                    distanceSum = distanceSum.add(distance);
                    cargoSum = cargoSum.add(cargo);
                    co2Cost = co2Cost.add(co2);
                }
                //计算eeoi
                BigDecimal eeoi = new BigDecimal(0);
                BigDecimal fuelPerMile = new BigDecimal(0);
                BigDecimal fuelPerCargoMile = new BigDecimal(0);//每运输功油耗
                BigDecimal co2PerMile = new BigDecimal(0);//每海里co2排放
                BigDecimal co2PerT = new BigDecimal(0);//每运输单位co2排放
                if (cargoSum.multiply(distanceSum).compareTo(BigDecimal.ZERO) > 0) {
                    eeoi = co2Cost.multiply(BigDecimal.valueOf(1000000)).divide(cargoSum.multiply(distanceSum), 2, BigDecimal.ROUND_HALF_UP);
                    fuelPerMile = oiHfoSum.divide(distanceSum, 2, BigDecimal.ROUND_HALF_UP);
                    fuelPerCargoMile = oiHfoSum.multiply(BigDecimal.valueOf(1000)).divide(
                            cargoSum.multiply(distanceSum).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP), 2, BigDecimal.ROUND_HALF_UP);
                    co2PerMile = co2Cost.multiply(BigDecimal.valueOf(10)).divide(distanceSum, 2, BigDecimal.ROUND_HALF_UP);
                    co2PerT = co2Cost.multiply(BigDecimal.valueOf(100)).divide(cargoSum, 2, BigDecimal.ROUND_HALF_UP);
                }
                eneryEfficTrendIndexDataVoEeoi.setData(eeoi);
                eneryEfficTrendIndexDataVoEeoi.setTimeOrVoyage(oiShipTask.getTask());
                eneryEfficTrendIndexDataVoEeoi.setName("EEOI(g/t∙nm)");
                eneryEfficTrendIndexDataVosEeoi.add(eneryEfficTrendIndexDataVoEeoi);
                //每海里油耗
                eneryEfficTrendIndexDataVoFuelPerMile.setData(fuelPerMile);
                eneryEfficTrendIndexDataVoFuelPerMile.setTimeOrVoyage(oiShipTask.getTask());
                eneryEfficTrendIndexDataVoFuelPerMile.setName("每海里油耗(kg/nm)");
                eneryEfficTrendIndexDataVosFuelPerMile.add(eneryEfficTrendIndexDataVoFuelPerMile);
                //每运输功油耗
                eneryEfficTrendIndexDataVoFuelPerT.setData(fuelPerCargoMile);
                eneryEfficTrendIndexDataVoFuelPerT.setTimeOrVoyage(oiShipTask.getTask());
                eneryEfficTrendIndexDataVoFuelPerT.setName("每运输功油耗(kg/kt∙nm)");
                eneryEfficTrendIndexDataVosFuelPerT.add(eneryEfficTrendIndexDataVoFuelPerT);
                //每运海里co2排放
                eneryEfficTrendIndexDataVoCo2PerMile.setData(co2PerMile);
                eneryEfficTrendIndexDataVoCo2PerMile.setTimeOrVoyage(oiShipTask.getTask());
                eneryEfficTrendIndexDataVoCo2PerMile.setName("每海里CO2排放(100kg/nm)");
                eneryEfficTrendIndexDataVosCo2PerMile.add(eneryEfficTrendIndexDataVoCo2PerMile);
                //每运输单位co2排放
                eneryEfficTrendIndexDataVoCo2PerT.setData(co2PerT);
                eneryEfficTrendIndexDataVoCo2PerT.setTimeOrVoyage(oiShipTask.getTask());
                eneryEfficTrendIndexDataVoCo2PerT.setName("每运输单位CO2排放(10kg/t)");
                eneryEfficTrendIndexDataVosCo2PerT.add(eneryEfficTrendIndexDataVoCo2PerT);
                //降速比
                eneryEfficTrendIndexDataVoDropRadio.setData(userate.multiply(BigDecimal.valueOf(100)));
                eneryEfficTrendIndexDataVoDropRadio.setTimeOrVoyage(oiShipTask.getTask());
                eneryEfficTrendIndexDataVoDropRadio.setName("降速比(%)");
                eneryEfficTrendIndexDataVosDropRadio.add(eneryEfficTrendIndexDataVoDropRadio);
                //载货量利用率
                eneryEfficTrendIndexDataVoUserRate.setData(speedrate.multiply(BigDecimal.valueOf(100)));
                eneryEfficTrendIndexDataVoUserRate.setTimeOrVoyage(oiShipTask.getTask());
                eneryEfficTrendIndexDataVoUserRate.setName("载货量利用率(%)");
                eneryEfficTrendIndexDataVosUserRate.add(eneryEfficTrendIndexDataVoUserRate);

            }
        } else if ("月".equals(rollingCycleUnit)) {
            //拆分月
            for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
                for (int j = 1; j <= 12; j++) {
                    String start = null;
                    if (j < 10) {
                        start = i + "-0" + j + "-01 00:00:00";
                    } else {
                        start = i + "-" + j + "-01 00:00:00";
                    }
                    String end = null;
                    String month = null;
                    if (j < 10) {
                        month = i + "-0";
                    } else {
                        month = i + "-";

                    }
                    if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(j)) {
                        end = month + j + "-31 23:59:59";
                    } else if (Arrays.asList(4, 6, 9, 11).contains(j)) {
                        end = month + j + "-30 23:59:59";
                    } else {
                        boolean b1 = i % 4 == 0;

                        boolean b2 = i % 100 != 0;

                        boolean b3 = i % 400 == 0;

                        if (b1 && b2 || b3) {
                            end = month + j + "-29 23:59:59";
                        } else {
                            end = month + j + "-28 23:59:59";
                        }
                    }
                    Map<String, EneryEfficTrendIndexDataVo> yue = getIndex(oiShipVoyageNoVayage, start, end, j, "月");
                    eneryEfficTrendIndexDataVosEeoi.add(yue.get("1"));
                    eneryEfficTrendIndexDataVosFuelPerMile.add(yue.get("2"));
                    eneryEfficTrendIndexDataVosFuelPerT.add(yue.get("3"));
                    eneryEfficTrendIndexDataVosCo2PerMile.add(yue.get("4"));
                    eneryEfficTrendIndexDataVosCo2PerT.add(yue.get("5"));
                    eneryEfficTrendIndexDataVosDropRadio.add(yue.get("6"));
                    eneryEfficTrendIndexDataVosUserRate.add(yue.get("7"));
                }

            }
        } else if ("季".equals(rollingCycleUnit)) {
            //拆分季度
            for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
                int quarter = 0;
                for (int j = 1; j <= 12; j += 3) {
                    quarter++;
                    int endMonth = j + 2;
                    String start = null;
                    if (j < 10) {
                        start = i + "-0" + j + "-01 00:00:00";
                    } else {
                        start = i + "-" + j + "-01 00:00:00";
                    }
                    String end = null;
                    String month = null;
                    if (j < 10) {
                        month = i + "-0";
                    } else {
                        month = i + "-";

                    }
                    if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(endMonth)) {
                        end = month + (j + 2) + "-31 23:59:59";
                    } else if (Arrays.asList(4, 6, 9, 11).contains(endMonth)) {
                        end = month + (j + 2) + "-30 23:59:59";
                    } else {
                        boolean b1 = i % 4 == 0;

                        boolean b2 = i % 100 != 0;

                        boolean b3 = i % 400 == 0;

                        if (b1 && b2 || b3) {
                            end = month + (j + 2) + "-29 23:59:59";
                        } else {
                            end = month + (j + 2) + "-28 23:59:59";
                        }
                    }
                    Map<String, EneryEfficTrendIndexDataVo> yue = getIndex(oiShipVoyageNoVayage, start, end, quarter, "季");
                    eneryEfficTrendIndexDataVosEeoi.add(yue.get("1"));
                    eneryEfficTrendIndexDataVosFuelPerMile.add(yue.get("2"));
                    eneryEfficTrendIndexDataVosFuelPerT.add(yue.get("3"));
                    eneryEfficTrendIndexDataVosCo2PerMile.add(yue.get("4"));
                    eneryEfficTrendIndexDataVosCo2PerT.add(yue.get("5"));
                    eneryEfficTrendIndexDataVosDropRadio.add(yue.get("6"));
                    eneryEfficTrendIndexDataVosUserRate.add(yue.get("7"));
                }

            }

        } else {
            for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
                String start = i + "-01-01 00:00:00";
                String end = i + "-12-31 23:59:59";
                Map<String, EneryEfficTrendIndexDataVo> yue = getIndex(oiShipVoyageNoVayage, start, end, 0, "年");
                eneryEfficTrendIndexDataVosEeoi.add(yue.get("1"));
                eneryEfficTrendIndexDataVosFuelPerMile.add(yue.get("2"));
                eneryEfficTrendIndexDataVosFuelPerT.add(yue.get("3"));
                eneryEfficTrendIndexDataVosCo2PerMile.add(yue.get("4"));
                eneryEfficTrendIndexDataVosCo2PerT.add(yue.get("5"));
                eneryEfficTrendIndexDataVosDropRadio.add(yue.get("6"));
                eneryEfficTrendIndexDataVosUserRate.add(yue.get("7"));
            }
        }
        //eeoi
        Map<String, Object> xdataAndYdataEEoi = getXdataAndYdata(eneryEfficTrendIndexDataVosEeoi);
        List<String> xDataEEoi = (List<String>) xdataAndYdataEEoi.get("xData");
        List<BigDecimal> yDataEEoi = (List<BigDecimal>) xdataAndYdataEEoi.get("yData");
        eneryEfficTrendIndexVoEeoi.setXData(xDataEEoi);
        eneryEfficTrendIndexVoEeoi.setYData(yDataEEoi);
        eneryEfficTrendIndexVoEeoi.setName(name + "EEOI(g/t∙nm)");
        eneryEfficTrendIndexVoEeoi.setNameEn("EEOI(g/t∙nm)");
        eneryEfficTrendIndexVoEeoi.setType("line");
        eneryEfficTrendIndexVoEeoi.setYAxisIndex(0);
        eneryEfficTrendIndexVoEeoi.setEneryEfficTrendIndexDataVos(eneryEfficTrendIndexDataVosEeoi);
        //每海里油耗
        eneryEfficTrendIndexVoFuelPerMile.setYAxisIndex(0);
        eneryEfficTrendIndexVoFuelPerMile.setName(name + "每海里油耗(kg/nm)");
        eneryEfficTrendIndexVoFuelPerMile.setType("line");
        Map<String, Object> xdataAndYdataFuelPerMile = getXdataAndYdata(eneryEfficTrendIndexDataVosFuelPerMile);
        List<String> xDataFuelPerMile = (List<String>) xdataAndYdataFuelPerMile.get("xData");
        List<BigDecimal> yDataFuelPerMile = (List<BigDecimal>) xdataAndYdataFuelPerMile.get("yData");
        eneryEfficTrendIndexVoFuelPerMile.setXData(xDataFuelPerMile);
        eneryEfficTrendIndexVoFuelPerMile.setYData(yDataFuelPerMile);
        eneryEfficTrendIndexVoFuelPerMile.setEneryEfficTrendIndexDataVos(eneryEfficTrendIndexDataVosFuelPerMile);
        //每运输功油耗
        eneryEfficTrendIndexVoFuelPerT.setYAxisIndex(0);
        eneryEfficTrendIndexVoFuelPerT.setName(name + "每运输功油耗(kg/kt∙nm)");
        eneryEfficTrendIndexVoFuelPerT.setType("line");
        Map<String, Object> xdataAndYdataFuelPerT = getXdataAndYdata(eneryEfficTrendIndexDataVosFuelPerT);
        List<String> xDataFuelPerT = (List<String>) xdataAndYdataFuelPerT.get("xData");
        List<BigDecimal> yDataFuelPerT = (List<BigDecimal>) xdataAndYdataFuelPerT.get("yData");
        eneryEfficTrendIndexVoFuelPerT.setYData(yDataFuelPerT);
        eneryEfficTrendIndexVoFuelPerT.setXData(xDataFuelPerT);
        eneryEfficTrendIndexVoFuelPerT.setEneryEfficTrendIndexDataVos(eneryEfficTrendIndexDataVosFuelPerT);

        //每运海里co2排放
        eneryEfficTrendIndexVoCo2PerMile.setYAxisIndex(0);
        eneryEfficTrendIndexVoCo2PerMile.setName(name + "每海里CO2排放(100kg/nm)");
        eneryEfficTrendIndexVoCo2PerMile.setType("line");
        Map<String, Object> xdataAndYdataCo2PerMile = getXdataAndYdata(eneryEfficTrendIndexDataVosCo2PerMile);
        List<String> xDataCo2PerMile = (List<String>) xdataAndYdataCo2PerMile.get("xData");
        List<BigDecimal> yDataCo2PerMile = (List<BigDecimal>) xdataAndYdataCo2PerMile.get("yData");
        eneryEfficTrendIndexVoCo2PerMile.setXData(xDataCo2PerMile);
        eneryEfficTrendIndexVoCo2PerMile.setYData(yDataCo2PerMile);
        eneryEfficTrendIndexVoCo2PerMile.setEneryEfficTrendIndexDataVos(eneryEfficTrendIndexDataVosCo2PerMile);
        //每运输单位co2排放
        eneryEfficTrendIndexVoCo2PerT.setYAxisIndex(0);
        eneryEfficTrendIndexVoCo2PerT.setName(name + "每运输单位CO2排放(10kg/t)");
        eneryEfficTrendIndexVoCo2PerT.setType("line");
        Map<String, Object> xdataAndYdataCo2PerT = getXdataAndYdata(eneryEfficTrendIndexDataVosCo2PerT);
        List<String> xDataCo2PerT = (List<String>) xdataAndYdataCo2PerT.get("xData");
        List<BigDecimal> yDataCo2PerT = (List<BigDecimal>) xdataAndYdataCo2PerT.get("yData");
        eneryEfficTrendIndexVoCo2PerT.setYData(yDataCo2PerT);
        eneryEfficTrendIndexVoCo2PerT.setXData(xDataCo2PerT);
        eneryEfficTrendIndexVoCo2PerT.setEneryEfficTrendIndexDataVos(eneryEfficTrendIndexDataVosCo2PerT);
        //降速比
        eneryEfficTrendIndexVoDropRadio.setYAxisIndex(1);
        eneryEfficTrendIndexVoDropRadio.setName(name + "降速比(%)");
        eneryEfficTrendIndexVoDropRadio.setType("bar");
        Map<String, Object> xdataAndYdataDropRadio = getXdataAndYdata(eneryEfficTrendIndexDataVosDropRadio);
        List<String> xDataDropRadio = (List<String>) xdataAndYdataDropRadio.get("xData");
        List<BigDecimal> yDataDropRadio = (List<BigDecimal>) xdataAndYdataDropRadio.get("yData");
        eneryEfficTrendIndexVoDropRadio.setXData(xDataDropRadio);
        eneryEfficTrendIndexVoDropRadio.setYData(yDataDropRadio);
        eneryEfficTrendIndexVoDropRadio.setEneryEfficTrendIndexDataVos(eneryEfficTrendIndexDataVosDropRadio);
        //载货量利用率
        eneryEfficTrendIndexVoUserRate.setYAxisIndex(1);
        eneryEfficTrendIndexVoUserRate.setName(name + "载货量利用率(%)");
        eneryEfficTrendIndexVoUserRate.setType("bar");
        Map<String, Object> xdataAndYdataUserRate = getXdataAndYdata(eneryEfficTrendIndexDataVosUserRate);
        List<String> xDataUserRate = (List<String>) xdataAndYdataUserRate.get("xData");
        List<BigDecimal> yDataUserRate = (List<BigDecimal>) xdataAndYdataUserRate.get("yData");
        eneryEfficTrendIndexVoUserRate.setXData(xDataUserRate);
        eneryEfficTrendIndexVoUserRate.setYData(yDataUserRate);
        eneryEfficTrendIndexVoUserRate.setEneryEfficTrendIndexDataVos(eneryEfficTrendIndexDataVosUserRate);
        List<EneryEfficTrendIndexVo> indexVos = new ArrayList<>();
        if (indexItems.contains(eneryEfficTrendIndexVoEeoi.getId())) {
            indexVos.add(eneryEfficTrendIndexVoEeoi);
        }
        if (indexItems.contains(eneryEfficTrendIndexVoFuelPerMile.getId())) {
            indexVos.add(eneryEfficTrendIndexVoFuelPerMile);
        }
        if (indexItems.contains(eneryEfficTrendIndexVoFuelPerT.getId())) {
            indexVos.add(eneryEfficTrendIndexVoFuelPerT);
        }
        if (indexItems.contains(eneryEfficTrendIndexVoCo2PerMile.getId())) {
            indexVos.add(eneryEfficTrendIndexVoCo2PerMile);
        }
        if (indexItems.contains(eneryEfficTrendIndexVoCo2PerT.getId())) {
            indexVos.add(eneryEfficTrendIndexVoCo2PerT);
        }
        if (indexItems.contains(eneryEfficTrendIndexVoDropRadio.getId())) {
            indexVos.add(eneryEfficTrendIndexVoDropRadio);
        }
        if (indexItems.contains(eneryEfficTrendIndexVoUserRate.getId())) {
            indexVos.add(eneryEfficTrendIndexVoUserRate);
        }
        EneryEfficTrendVo eneryEfficTrendVo = new EneryEfficTrendVo();
        eneryEfficTrendVo.setEneryEfficTrendIndexVos(indexVos);
        if (gcClient != null) {
            eneryEfficTrendVo.setShipComPanyName(gcClient.getNameEn());
        }
        if (oiShipInfo != null) {
            eneryEfficTrendVo.setShipName(oiShipInfo.getSpname());
        }
        return new ResultObjectVo<>(eneryEfficTrendVo);
    }

    @Override
    public ResultVo getEnergyEfficencyTrendData(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto) {
        String rollingCycleUnit = energyEfficiencyTrendAnalysisDto.getRollingCycleUnit();
        ResultVo energyEfficencyTrend = getEnergyEfficencyTrend(energyEfficiencyTrendAnalysisDto);
        List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVos = new ArrayList<>();
        if (energyEfficencyTrend.getData() != null) {
            EneryEfficTrendVo data = (EneryEfficTrendVo) energyEfficencyTrend.getData();
            List<EneryEfficTrendIndexVo> eneryEfficTrendIndexVos = data.getEneryEfficTrendIndexVos();
            for (int i = 0; i < eneryEfficTrendIndexVos.size(); i++) {
                EneryEfficTrendIndexVo eneryEfficTrendIndexVo = eneryEfficTrendIndexVos.get(i);
                eneryEfficTrendIndexDataVos.addAll(eneryEfficTrendIndexVo.getEneryEfficTrendIndexDataVos());
            }
        }
        Map<String, List<EneryEfficTrendIndexDataVo>> collect = eneryEfficTrendIndexDataVos.stream().collect(Collectors.groupingBy(eneryEfficTrendIndexDataVo -> eneryEfficTrendIndexDataVo.getTimeOrVoyage()));
        List<EneryEfficTrendDataVo> eneryEfficTrendDataVos = new ArrayList<>();
        EneryEfficTrendDataTypeVo eneryEfficTrendDataTypeVo = new EneryEfficTrendDataTypeVo();
        if ("航次".equals(rollingCycleUnit)) {
            eneryEfficTrendDataTypeVo.setType("航次");
        } else {
            eneryEfficTrendDataTypeVo.setType("日期");
        }
        for (Map.Entry<String, List<EneryEfficTrendIndexDataVo>> entry : collect.entrySet()) {
            EneryEfficTrendDataVo eneryEfficTrendDataVo = new EneryEfficTrendDataVo();
            eneryEfficTrendDataVo.setTime(entry.getKey());

            List<EneryEfficTrendIndexDataVo> value = entry.getValue();
            value.forEach(eneryEfficTrendIndexDataVo -> {
                String indexId = eneryEfficTrendIndexDataVo.getIndexId();
                BigDecimal data = eneryEfficTrendIndexDataVo.getData();
                if ("1".equals(indexId)) {
                    eneryEfficTrendDataVo.setEeoi(data);
                }
                if ("2".equals(indexId)) {
                    eneryEfficTrendDataVo.setFuelPerMile(data);
                }
                if ("3".equals(indexId)) {
                    eneryEfficTrendDataVo.setFuelPerT(data);
                }
                if ("4".equals(indexId)) {
                    eneryEfficTrendDataVo.setCo2PerMile(data);
                }
                if ("5".equals(indexId)) {
                    eneryEfficTrendDataVo.setCo2PerT(data);
                }
                if ("6".equals(indexId)) {
                    eneryEfficTrendDataVo.setDropRadio(data);
                }
                if ("7".equals(indexId)) {
                    eneryEfficTrendDataVo.setUserRate(data);
                }
            });
            eneryEfficTrendDataVos.add(eneryEfficTrendDataVo);
        }
        eneryEfficTrendDataVos.sort((x, y) -> {
            String timeX = x.getTime();
            String timeY = y.getTime();
            if ("航次".equals(rollingCycleUnit)) {
                return x.getTime().compareTo(y.getTime());
            } else if ("月".equals(rollingCycleUnit)) {
                String yearX = timeX.substring(0, 4);
                String monthX = timeX.substring(5);
                String yearY = timeY.substring(0, 4);
                String monthY = timeY.substring(5);
                if (yearX.equals(yearY)) {
                    return Integer.valueOf(monthX) - Integer.valueOf(monthY);
                } else {
                    return Integer.valueOf(yearX) - Integer.valueOf(yearY);
                }
            } else if ("季".equals(rollingCycleUnit)) {
                String yearX = timeX.substring(0, 4);
                String yearY = timeY.substring(0, 4);
                String seaX = timeX.substring(6, 7);
                String seaY = timeY.substring(6, 7);
                if (yearX.equals(yearY)) {
                    return Integer.valueOf(seaX) - Integer.valueOf(seaY);
                } else {
                    return Integer.valueOf(yearX) - Integer.valueOf(yearY);
                }
            } else {
                String yearX = timeX.substring(0, 4);
                String yearY = timeY.substring(0, 4);
                return Integer.valueOf(yearX) - Integer.valueOf(yearY);
            }
        });
        eneryEfficTrendDataTypeVo.setEneryEfficTrendDataVos(eneryEfficTrendDataVos);
        return new ResultObjectVo<>(eneryEfficTrendDataTypeVo);
    }

    @Override
    public ResultVo getCompareAnalysisData(CompareAnalysisData compareAnalysisData) {
        List<CompareAnalysisDataCondition> compareAnalysisDataConditions = compareAnalysisData.getCompareAnalysisDataConditions();
        String startYear = compareAnalysisData.getStartYear();
        String endYear = compareAnalysisData.getEndYear();
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if (!StringUtils.isNotEmpty(startYear)) {
            startYear = String.valueOf(instance.get(Calendar.YEAR));
        }
        if (!StringUtils.isNotEmpty(endYear)) {
            endYear = String.valueOf(instance.get(Calendar.YEAR));
        }
        String rollingCycleUnit = compareAnalysisData.getRollingCycleUnit();
        List<String> indexItems = compareAnalysisData.getIndexItems();
        List<EneryEfficTrendIndexVo> eneryEfficTrendIndexVosAll = new ArrayList<>();
        for (int i = 0; i < compareAnalysisDataConditions.size(); i++) {
            CompareAnalysisDataCondition compareAnalysisDataCondition = compareAnalysisDataConditions.get(i);
            String shipCompanyId = compareAnalysisDataCondition.getShipCompanyId();
            String shipId = compareAnalysisDataCondition.getShipId();
            if (!StringUtils.isNotEmpty(shipCompanyId) && !StringUtils.isNotEmpty(shipId)) {
                continue;
            }
            EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto = new EnergyEfficiencyTrendAnalysisDto();
            BeanUtils.copyProperties(compareAnalysisDataCondition, energyEfficiencyTrendAnalysisDto);
            energyEfficiencyTrendAnalysisDto.setEndYear(endYear);
            energyEfficiencyTrendAnalysisDto.setStartYear(startYear);
            energyEfficiencyTrendAnalysisDto.setRollingCycleUnit(rollingCycleUnit);
            energyEfficiencyTrendAnalysisDto.setIndexItems(indexItems);
            ResultVo energyEfficencyTrend = getEnergyEfficencyTrend(energyEfficiencyTrendAnalysisDto);
            if (energyEfficencyTrend.isResult()) {
                EneryEfficTrendVo data = (EneryEfficTrendVo) energyEfficencyTrend.getData();
                List<EneryEfficTrendIndexVo> eneryEfficTrendIndexVos = data.getEneryEfficTrendIndexVos();
                eneryEfficTrendIndexVosAll.addAll(eneryEfficTrendIndexVos);
            } else {
                return energyEfficencyTrend;
            }
        }
        return new ResultObjectVo<>(eneryEfficTrendIndexVosAll);
    }

    @Override
    public ResultVo getCompareAnalysisDatas(CompareAnalysisData compareAnalysisData) {
        List<CompareAnalysisDataCondition> compareAnalysisDataConditions = compareAnalysisData.getCompareAnalysisDataConditions();//获取条件参数
        String startYear = compareAnalysisData.getStartYear();
        String endYear = compareAnalysisData.getEndYear();
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if (!StringUtils.isNotEmpty(startYear)) {
            startYear = String.valueOf(instance.get(Calendar.YEAR));
        }
        if (!StringUtils.isNotEmpty(endYear)) {
            endYear = String.valueOf(instance.get(Calendar.YEAR));
        }
        String rollingCycleUnit = compareAnalysisData.getRollingCycleUnit();
        List<String> indexItems = compareAnalysisData.getIndexItems();
        List<CompareAnalysisDataVo> compareAnalysisDataVos = new ArrayList<>();
        for (int i = 0; i < compareAnalysisDataConditions.size(); i++) {
            CompareAnalysisDataVo compareAnalysisDataVo = new CompareAnalysisDataVo();
            CompareAnalysisDataCondition compareAnalysisDataCondition = compareAnalysisDataConditions.get(i);
            String shipId = compareAnalysisDataCondition.getShipId();
            String shipCompanyId = compareAnalysisDataCondition.getShipCompanyId();
            OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);
            List<GcClient> gcClientList = gcClientRepository.findByCodeAndIsDelete(shipCompanyId);
            GcClient gcClient = null;
            if (gcClientList != null && gcClientList.size() > 0) {
                gcClient = gcClientList.get(0);
            }
            if (oiShipInfo != null) {
                compareAnalysisDataVo.setName(oiShipInfo.getSpname());
                compareAnalysisDataVo.setType("1");
            } else {
                if (gcClient != null) {
                    compareAnalysisDataVo.setName(gcClient.getNameEn());
                    compareAnalysisDataVo.setType("0");
                }
            }
            EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto = new EnergyEfficiencyTrendAnalysisDto();
            BeanUtils.copyProperties(compareAnalysisDataCondition, energyEfficiencyTrendAnalysisDto);
            energyEfficiencyTrendAnalysisDto.setEndYear(endYear);
            energyEfficiencyTrendAnalysisDto.setStartYear(startYear);
            energyEfficiencyTrendAnalysisDto.setRollingCycleUnit(rollingCycleUnit);
            energyEfficiencyTrendAnalysisDto.setIndexItems(indexItems);
            ResultVo energyEfficencyTrendData = getEnergyEfficencyTrendData(energyEfficiencyTrendAnalysisDto);
            if (energyEfficencyTrendData.isResult()) {
                EneryEfficTrendDataTypeVo data = (EneryEfficTrendDataTypeVo) energyEfficencyTrendData.getData();
                compareAnalysisDataVo.setEneryEfficTrendDataVos(data.getEneryEfficTrendDataVos());
            } else {
                return energyEfficencyTrendData;
            }
            compareAnalysisDataVos.add(compareAnalysisDataVo);
        }
        return new ResultObjectVo<>(compareAnalysisDataVos);
    }

    @Override
    public void downLoadEnergyEfficencyTrendData(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String shipId = energyEfficiencyTrendAnalysisDto.getShipId();
        String type = energyEfficiencyTrendAnalysisDto.getType();
        String rollingCycleUnit = energyEfficiencyTrendAnalysisDto.getRollingCycleUnit();
        String shipCompanyId = energyEfficiencyTrendAnalysisDto.getShipCompanyId();
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);
        List<GcClient> gcClientList = gcClientRepository.findByCodeAndIsDelete(shipCompanyId);
        GcClient gcClient = null;
        if (gcClientList != null && gcClientList.size() > 0) {
            gcClient = gcClientList.get(0);
        }
        XLSTransformer xlsTransformer = new XLSTransformer();
        InputStream inputStream = null;
        if ("0".equals(type)) {
            inputStream = this.getClass().getResourceAsStream("/downLoad/ENERGY_EFFIC_TREND_ANAL_EN.xlsx");
        } else {
            inputStream = this.getClass().getResourceAsStream("/downLoad/ENERGY_EFFIC_TREND_ANAL.xlsx");
        }
        if (oiShipInfo != null) {
            if ("0".equals(type)) {
                map.put("name", "ship name");
            } else {
                map.put("name", "船名");
            }
            map.put("value", oiShipInfo.getSpname());
        } else {
            if (gcClient != null) {
                if ("0".equals(type)) {
                    map.put("name", "shipping company");
                } else {
                    map.put("name", "船公司");
                }
                map.put("value", gcClient.getNameEn());
            }
        }
        if ("航次".equals(rollingCycleUnit)) {
            map.put("type", "航次");
        } else {
            map.put("type", "日期");
        }
        ResultVo energyEfficencyTrendData = getEnergyEfficencyTrendData(energyEfficiencyTrendAnalysisDto);
        if (energyEfficencyTrendData.isResult()) {
            EneryEfficTrendDataTypeVo data = (EneryEfficTrendDataTypeVo) energyEfficencyTrendData.getData();
            List<EneryEfficTrendDataVo> eneryEfficTrendDataVos = data.getEneryEfficTrendDataVos();
            map.put("list", eneryEfficTrendDataVos);
        }
        downLoadResponse(response, inputStream, xlsTransformer, map);
    }

    @Override
    public void downLoadCompareAnalysisData(CompareAnalysisData compareAnalysisData, HttpServletResponse response) {
        String type = compareAnalysisData.getType();
        ResultVo compareAnalysisDatas = getCompareAnalysisDatas(compareAnalysisData);
        List<EneryEfficTrendDataVo> eneryEfficTrendDataVosAll = new ArrayList<>();
        List<CompareAnalysisDataVo> compareAnalysisDataVos = new ArrayList<>();
        InputStream inputStream = null;
        if ("0".equals(type)) {
            inputStream = this.getClass().getResourceAsStream("/downLoad/ENERGY_COMPARE_DATA_EN.xlsx");
        } else {
            inputStream = this.getClass().getResourceAsStream("/downLoad/ENERGY_COMPARE_DATA_CN.xlsx");
        }
        if (compareAnalysisDatas.isResult()) {
            compareAnalysisDataVos = (List<CompareAnalysisDataVo>) compareAnalysisDatas.getData();
            for (int i = 0; i < compareAnalysisDataVos.size(); i++) {
                CompareAnalysisDataVo compareAnalysisDataVo = compareAnalysisDataVos.get(i);
                String name = compareAnalysisDataVo.getName();
                List<EneryEfficTrendDataVo> eneryEfficTrendDataVos = compareAnalysisDataVo.getEneryEfficTrendDataVos();
                for (int j = 0; j < eneryEfficTrendDataVos.size(); j++) {
                    EneryEfficTrendDataVo eneryEfficTrendDataVo = eneryEfficTrendDataVos.get(j);
                    if ("0".equals(type)) {
                        eneryEfficTrendDataVo.setCondition("condition " + (i + 1));
                    } else {
                        eneryEfficTrendDataVo.setCondition("条件 " + (i + 1));
                    }
                    eneryEfficTrendDataVo.setName(name);
                    eneryEfficTrendDataVosAll.add(eneryEfficTrendDataVo);
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("condition1", eneryEfficTrendDataVosAll);
        XLSTransformer xlsTransformer = new XLSTransformer();
        downLoadResponse(response, inputStream, xlsTransformer, map);
    }

    @Override
    public ResultVo getEnergyEfficencyTrendMessage(InformationDto informationDto) {
        //解密token
        log.info("船舶获取参数==={}", informationDto);
        String token = informationDto.getToken();
        String auditTime = informationDto.getAuditTime();
        Integer rollPeriod = informationDto.getRollPeriod();
        String shipImono = informationDto.getShipImono();
        Integer type = informationDto.getType();
        String timestamp = null;
        try {
            timestamp = Des8Util.decrypt(token);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
        // 时间戳不在当前时间的15分钟内，则返回代码10003，该部分代码对船端时，可以不验证，应对船端时间不正确的情况
        if (Long.parseLong(timestamp) > (System.currentTimeMillis() + 900000)
                || Long.parseLong(timestamp) < (System.currentTimeMillis() - 900000)) {
            log.info("时间戳超时====={}", timestamp);
            return new ResultErrorVo("时间戳超时");
        }
        EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto = new EnergyEfficiencyTrendAnalysisDto();
        if (type == 0) {
            OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(shipImono, shipImono, 0);
            if (oiShipInfo == null) {
                log.info("======船舶不存在");
                return new ResultErrorVo("船舶不存在");
            }
            energyEfficiencyTrendAnalysisDto.setShipId(oiShipInfo.getId());
            energyEfficiencyTrendAnalysisDto.setRollingCycleUnit("航次");
        } else {
            energyEfficiencyTrendAnalysisDto.setRollingCycleUnit("年");
            List<String> byIacs = gcClientRepository.findIdByIacs(shipImono);
            if (byIacs == null || byIacs.size() == 0) {
                log.info("======船公司不存在");
                return new ResultErrorVo("船公司不存在");
            }
            energyEfficiencyTrendAnalysisDto.setShipCompanyId(byIacs.get(0));
        }
        String year = auditTime.substring(0, 4);
        energyEfficiencyTrendAnalysisDto.setStartYear(year);
        energyEfficiencyTrendAnalysisDto.setEndYear(year);
        energyEfficiencyTrendAnalysisDto.setRollingCycle(rollPeriod);
        energyEfficiencyTrendAnalysisDto.setIndexItems(Arrays.asList("1"));
        return new ResultObjectVo<>(energyEfficiencyTrendAnalysisDto);
    }


    @Override
    public ResultVo singalShipYear(EnergyEfficDto energyEfficDto) {
        return getSingalShip(energyEfficDto, null);
    }

    @Override
    @Transactional
    public ResultVo lockOrUnlockEuDcs(EuRptVo euRptVo) {
        EuEmissionRpt byIdAndIsDelete = euEmissionRptRepository.findByIdAndIsDelete(euRptVo.getId(), 0);
        EuEmissionRpt euEmissionRpt = null;
        if (byIdAndIsDelete != null) {
            String recStatus = byIdAndIsDelete.getRecStatus();
            if ("2".equals(recStatus) || "3".equals(recStatus)) {
                recStatus = "1";
            } else {
                recStatus = "2";
            }
            byIdAndIsDelete.setRecStatus(recStatus);
            euEmissionRpt = euEmissionRptRepository.save(byIdAndIsDelete);
        }
        if (euEmissionRpt != null) {
            return new ResultStringVo(this.msg("energyEeffic.update.success"));
        } else {
            return new ResultStringVo(this.msg("energyEeffic.update.failed"));
        }
    }

    private void judgeIsEu(List<OiShipVoyage> doubleEu, List<OiShipVoyage> startEu, List<OiShipVoyage> endEu, OiShipVoyage oiShipVoyageNoAcross, Integer isEuStart, Integer isEnd) {
        if (isEuStart == 1 && isEnd != 1) {
            startEu.add(oiShipVoyageNoAcross);
        } else if (isEnd == 1 && isEuStart != 1) {
            endEu.add(oiShipVoyageNoAcross);
        } else if (isEuStart == 1 && isEnd == 1) {
            doubleEu.add(oiShipVoyageNoAcross);
        }
    }


    /**
     * 历史航段查询条件
     *
     * @param minAvgSpeed
     * @param maxAvgSpeed
     * @param startTime
     * @param endTime
     * @param shipId
     * @param isEu
     * @return
     */
    private Specification<OiShipVoyage> queryOiShipVoyageHostoryList(BigDecimal minAvgSpeed, BigDecimal maxAvgSpeed, Date startTime, Date endTime, String shipId, Integer isEu) {
        return new Specification<OiShipVoyage>() {
            @Override
            public Predicate toPredicate(Root<OiShipVoyage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<OiShipVoyage, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                if (startTime != null && endTime != null) {
                    predicatesAnd.add(cb.greaterThanOrEqualTo(root.get("startTime"), startTime));
                    predicatesAnd.add(cb.lessThanOrEqualTo(root.get("endTime"), endTime));
                }
                predicatesAnd.add(cb.greaterThanOrEqualTo(root.get("avgspeed"), minAvgSpeed));
                predicatesAnd.add(cb.lessThanOrEqualTo(root.get("avgspeed"), maxAvgSpeed));
                predicatesAnd.add(cb.equal(root.get("voyType"), isEu));
                predicatesAnd.add(cb.equal(root.get("shipId"), shipId));
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                return criteriaQuery.where(and).getRestriction();
            }
        };
    }

    /**
     * 非历史航段查询条件
     *
     * @param mrvDataAssessmentDto
     * @return
     * @throws ParseException
     */
    private Specification<OiShipVoyage> queryOiShipVoyageList(MrvDataAssessmentDto mrvDataAssessmentDto) throws ParseException {
        String shipComPanyName = mrvDataAssessmentDto.getShipComPanyName();
        String taskNu = mrvDataAssessmentDto.getTaskNu();
        taskNu = taskNu.replace(" ", "").toUpperCase();
        List<String> taskIdsByTaskNu = oiShipTaskRepository.findTaskIdsByTaskNu(taskNu);
        String shipName = mrvDataAssessmentDto.getShipName();
        Integer isEu = mrvDataAssessmentDto.getIsEu();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
            roleNames.add(sysRole.getName());
        });
        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        return new Specification<OiShipVoyage>() {
            @SneakyThrows
            @Override
            public Predicate toPredicate(Root<OiShipVoyage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                String year = mrvDataAssessmentDto.getYear();
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesOrTime = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<OiShipVoyage, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                Path<Object> path = root.get("oiShipInfo").get("id");
                CriteriaBuilder.In<Object> in = cb.in(path);
                Path<Object> pathTaskId = root.get("taskid");
                CriteriaBuilder.In<Object> inTask = cb.in(pathTaskId);
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
                    shipIdsByShipCodes.forEach(s -> {
                        in.value(s);
                    });
                    predicatesAnd.add(cb.and(in));
                }
                if (taskIdsByTaskNu != null && taskIdsByTaskNu.size() != 0) {
                    taskIdsByTaskNu.forEach(s -> {
                        inTask.value(s);
                    });
                    predicatesAnd.add(cb.and(inTask));
                }
                if (StringUtils.isNotEmpty(year)) {
                    Date startTime = simpleDateFormat.parse(year + "-01-01");
                    Date endTime = simpleDateFormat.parse(year + "-12-31");
                    predicatesOrTime.add(cb.between(root.get("startTime"), startTime, endTime));
                    predicatesOrTime.add(cb.between(root.get("endTime"), startTime, endTime));
                }
                predicatesAnd.add(cb.equal(root.get("voyType"), isEu));
                if (StringUtils.isNotEmpty(shipName)) {
                    predicatesAnd.add(cb.equal(root.get("shipId"), shipName));
                }
                if (StringUtils.isNotEmpty(shipComPanyName)) {
                    predicatesOr.add(cb.equal(join.get("docId"), shipComPanyName));
                    predicatesOr.add(cb.equal(join.get("operatorId"), shipComPanyName));
                    predicatesOr.add(cb.equal(join.get("ownerId"), shipComPanyName));
                    predicatesOr.add(cb.equal(join.get("builderId"), shipComPanyName));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                Predicate orTime = cb.or(predicatesOrTime.toArray(new Predicate[predicatesOrTime.size()]));
                if (predicatesOr.size() > 0 && predicatesOrTime.size() > 0) {
                    return criteriaQuery.where(and, or, orTime).getRestriction();
                }
                if (predicatesOr.size() > 0 && predicatesOrTime.size() == 0) {
                    return criteriaQuery.where(and, or).getRestriction();
                }
                if (predicatesOr.size() == 0 && predicatesOrTime.size() > 0) {
                    return criteriaQuery.where(and, orTime).getRestriction();
                }
                return criteriaQuery.where(and).getRestriction();
            }
        };
    }

    private Specification<BeginPeriod> queryBeginPeriodList(BeginOilDto beginOilDto) {
        String year = beginOilDto.getYear();
        String keyWord = beginOilDto.getKeyWord();
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
            roleNames.add(sysRole.getName());
        });
        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Specification<BeginPeriod>() {
            @Override
            public Predicate toPredicate(Root<BeginPeriod> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<BeginPeriod, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                Path<Object> path = root.get("oiShipInfo").get("id");
                CriteriaBuilder.In<Object> in = cb.in(path);
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
                    shipIdsByShipCodes.forEach(s -> {
                        in.value(s);
                    });
                    predicatesAnd.add(cb.and(in));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(year)) {
                    try {
                        Date start = simpleDateFormat.parse(year + "-01-01 00:00:00");
                        Date end = simpleDateFormat.parse(year + "-12-31 23:59:59");
                        predicatesAnd.add(cb.between(root.get("periodTime"), start, end));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (StringUtils.isNotEmpty(keyWord)) {
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("spname"))), "%" + keyWord.trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("spnameCn"))), "%" + keyWord.trim() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("imono"))), "%" + keyWord.trim() + "%"));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                } else {
                    return criteriaQuery.where(and).getRestriction();
                }
            }
        };
    }

    /**
     * imo报告查询条件
     *
     * @param imoDcsRptManagerDto
     * @return
     */
    private Specification<ImoStdrpt> queryImoReaportList(ImoDcsRptManagerDto imoDcsRptManagerDto) {
        String keyword = imoDcsRptManagerDto.getKeyword();
        String imoRptStatus = imoDcsRptManagerDto.getImoRptStatus();
        String flag = imoDcsRptManagerDto.getFlag();
        Date startTime = imoDcsRptManagerDto.getStartTime();
        Date endTime = imoDcsRptManagerDto.getEndTime();
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
            roleNames.add(sysRole.getName());
        });
        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        return new Specification<ImoStdrpt>() {
            @Override
            public Predicate toPredicate(Root<ImoStdrpt> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesOrTime = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Path<Object> path = root.get("oiShipInfo").get("id");
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                CriteriaBuilder.In<Object> in = cb.in(path);
                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
                    shipIdsByShipCodes.forEach(s -> {
                        in.value(s);
                    });
                    predicatesAnd.add(cb.and(in));
                }
                if (StringUtils.isNotEmpty(flag)) {
                    predicatesAnd.add(cb.like(cb.upper(cb.trim(root.get("countryFlag"))), "%" + flag.trim().toUpperCase() + "%"));
                }
                if (StringUtils.isNotEmpty(imoRptStatus)) {
                    predicatesAnd.add(cb.equal(root.get("recStatus"), imoRptStatus));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (startTime != null && endTime != null) {
                    predicatesOrTime.add(cb.between(root.<Date>get("startTm"), startTime, endTime));
                    predicatesOrTime.add(cb.between(root.<Date>get("endTm"), startTime, endTime));
                }
                if (StringUtils.isNotEmpty(keyword)) {
                    predicatesOr.add(cb.like(cb.upper(cb.trim(root.get("imono"))), "%" + keyword.trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(root.get("docName"))), "%" + keyword.trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(root.get("shipName"))), "%" + keyword.trim().toUpperCase() + "%"));
                }
                Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                Predicate orTime = cb.or(predicatesOrTime.toArray(new Predicate[predicatesOrTime.size()]));
                if (predicatesOr.size() > 0 && predicatesOrTime.size() > 0) {
                    return criteriaQuery.where(and, or, orTime).getRestriction();
                }
                if (predicatesOr.size() > 0 && predicatesOrTime.size() == 0) {
                    return criteriaQuery.where(and, or).getRestriction();
                }
                if (predicatesOr.size() == 0 && predicatesOrTime.size() > 0) {
                    return criteriaQuery.where(and, orTime).getRestriction();
                }
                return criteriaQuery.where(and).getRestriction();
            }
        };
    }

    /**
     * imo报告查询条件
     *
     * @param imoDcsRptManagerDto
     * @return
     */
    private Specification<EuEmissionRpt> queryEuReaportList(ImoDcsRptManagerDto imoDcsRptManagerDto) {
        String keyword = imoDcsRptManagerDto.getKeyword();
        String flag = imoDcsRptManagerDto.getFlag();
        Date startTime = imoDcsRptManagerDto.getStartTime();
        Date endTime = imoDcsRptManagerDto.getEndTime();
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
            roleNames.add(sysRole.getName());
        });
        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        return new Specification<EuEmissionRpt>() {
            @Override
            public Predicate toPredicate(Root<EuEmissionRpt> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<OiShipVoyage, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                Path<Object> path = root.get("oiShipInfo").get("id");
                CriteriaBuilder.In<Object> in = cb.in(path);
                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
                    shipIdsByShipCodes.forEach(s -> {
                        in.value(s);
                    });
                    predicatesAnd.add(cb.and(in));
                }
                if (StringUtils.isNotEmpty(flag)) {
                    predicatesAnd.add(cb.like(cb.upper(cb.trim(root.get("countryFlag"))), "%" + flag.trim().toUpperCase() + "%"));
                }
                if (startTime != null && endTime != null) {
                    predicatesAnd.add(cb.between(root.<Date>get("startTm"), startTime, endTime));
                    predicatesAnd.add(cb.between(root.<Date>get("endTm"), startTime, endTime));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(keyword)) {
                    predicatesOr.add(cb.like(cb.upper(cb.trim(root.get("imono"))), "%" + keyword.trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(root.get("docName"))), "%" + keyword.trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(root.get("shipName"))), "%" + keyword.trim().toUpperCase() + "%"));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                }
                return criteriaQuery.where(and).getRestriction();
            }
        };
    }

    private List<ImoRptTimeVo> getImoRptVo(String year, String shipId, String startTime, String endTime) throws ParseException {
        List<ImoRptTimeVo> imoRptTimeVos = new ArrayList<>();
        //查询期初和期末量的日期
        List<Date> beginPeriodOils = beginPeriodRepository.findByShipIdAndIsDeleteAndPeriodTimeBetween(shipId, startTime, endTime);
        //查询已生成报告的时间段
        List<ImoStdrpt> imoStdrpts = imoStdrptRepository.findByShipIdAndIsDeleteAndStartTmBetweenAndEndTmBetween(shipId, startTime, endTime, startTime, endTime);
        imoStdrpts.forEach(imoStdrpt -> {
            ImoRptTimeVo imoRptTimeVo = new ImoRptTimeVo();
            imoRptTimeVo.setStartTime(imoStdrpt.getStartTm());
            imoRptTimeVo.setEndTime(imoStdrpt.getEndTm());
            imoRptTimeVo.setImoRptStatus(2);
            imoRptTimeVo.setImoRptId(imoStdrpt.getId());
            imoRptTimeVo.setShipId(shipId);
            imoRptTimeVos.add(imoRptTimeVo);
        });
        List<Date> dates = new ArrayList<>();
        beginPeriodOils.forEach(date -> {
            dates.add(date);
        });
        //去掉重复时间
        List<Date> distinctDate = dates.stream().distinct().collect(Collectors.toList());
        distinctDate.sort(Date::compareTo);
        //拆解时间段
        Calendar instance = Calendar.getInstance();
        for (int i = 0; i < distinctDate.size() - 1; i++) {
            boolean flag = true;
            ImoRptTimeVo imoRptTimeVo = new ImoRptTimeVo();
            imoRptTimeVo.setImoRptStatus(0);
            imoRptTimeVo.setShipId(shipId);
            Date startDate = distinctDate.get(i);
            Date endDate = distinctDate.get(i + 1);
            instance.setTime(endDate);
            instance.add(Calendar.MILLISECOND, -1);
            endDate = instance.getTime();
            for (int j = 0; j < imoStdrpts.size(); j++) {
                ImoStdrpt imoStdrpt = imoStdrpts.get(j);
                long ss1 = startDate.getTime();
                long ee1 = endDate.getTime();
                long ss2 = imoStdrpt.getStartTm().getTime();
                long ee2 = imoStdrpt.getEndTm().getTime();
                if (((ss1 < ss2) && (ee1 > ss2)) || ((ss1 > ss2) && (ss1 < ee2))) {
                    imoRptTimeVo.setImoRptStatus(1);
                    flag = false;
                }
                if ((ss1 == ss2) && (ee1 == ee2 + 999)) {
                    flag = false;
                }
            }
            imoRptTimeVo.setStartTime(startDate);
            imoRptTimeVo.setEndTime(endDate);

            if (flag) {
                imoRptTimeVos.add(imoRptTimeVo);
            }
        }
        return imoRptTimeVos;
    }

    private List<ImoRptTimeVo> getEuRptVo(String year, String shipId, String startTime, String endTime) throws ParseException {
        List<ImoRptTimeVo> imoRptTimeVos = new ArrayList<>();
        //查询期初和期末量的日期
        List<Date> beginPeriodOils = beginPeriodRepository.findByShipIdAndIsDeleteAndPeriodTimeBetween(shipId, startTime, endTime);
        //查询已生成报告的时间段
        List<EuEmissionRpt> euEmissionRpts = euEmissionRptRepository.findByShipIdAndIsDeleteAndStartTmBetweenAndEndTmBetween(shipId, startTime, endTime, startTime, endTime);
        euEmissionRpts.forEach(euEmissionRpt -> {
            ImoRptTimeVo imoRptTimeVo = new ImoRptTimeVo();
            imoRptTimeVo.setStartTime(euEmissionRpt.getStartTm());
            imoRptTimeVo.setEndTime(euEmissionRpt.getEndTm());
            imoRptTimeVo.setImoRptStatus(2);
            imoRptTimeVo.setImoRptId(euEmissionRpt.getId());
            imoRptTimeVo.setShipId(shipId);
            imoRptTimeVos.add(imoRptTimeVo);
        });
        List<Date> dates = new ArrayList<>();
        beginPeriodOils.forEach(date -> {
            dates.add(date);
        });
        //去掉重复时间
        List<Date> distinctDate = dates.stream().distinct().collect(Collectors.toList());
        distinctDate.sort(Date::compareTo);
        //拆解时间段
        Calendar instance = Calendar.getInstance();
        for (int i = 0; i < distinctDate.size() - 1; i++) {
            ImoRptTimeVo imoRptTimeVo = new ImoRptTimeVo();
            imoRptTimeVo.setImoRptStatus(0);
            imoRptTimeVo.setShipId(shipId);
            Date startDate = distinctDate.get(i);
            Date endDate = distinctDate.get(i + 1);
            instance.setTime(endDate);
            instance.add(Calendar.MINUTE, -1);
            endDate = instance.getTime();
            for (int j = 0; j < euEmissionRpts.size(); j++) {
                EuEmissionRpt euEmissionRpt = euEmissionRpts.get(j);
                long ss1 = startDate.getTime();
                long ee1 = endDate.getTime();
                long ss2 = euEmissionRpt.getStartTm().getTime();
                long ee2 = euEmissionRpt.getEndTm().getTime();
                if (((ss1 <= ss2) && (ee1 >= ss2)) || ((ss1 >= ss2) && (ss1 <= ee2))) {
                    imoRptTimeVo.setImoRptStatus(1);
                    continue;
                }
            }
            imoRptTimeVo.setStartTime(startDate);
            imoRptTimeVo.setEndTime(endDate);
            imoRptTimeVos.add(imoRptTimeVo);
        }
        return imoRptTimeVos;
    }

    private CalculateDcsOilVo getOilCons(List<Map<String, Object>> maps) {
        CalculateDcsOilVo calculateDcsOilVo = new CalculateDcsOilVo();
        BigDecimal hfoHight = new BigDecimal(0);
        BigDecimal hfoLow = new BigDecimal(0);
        BigDecimal hfoSuperLow = new BigDecimal(0);
        BigDecimal lfoHight = new BigDecimal(0);
        BigDecimal lfoLow = new BigDecimal(0);
        BigDecimal lfoSuperLow = new BigDecimal(0);
        BigDecimal chaiFo = new BigDecimal(0);
        BigDecimal propane = new BigDecimal(0);
        BigDecimal butane = new BigDecimal(0);
        BigDecimal lng = new BigDecimal(0);
        BigDecimal methanol = new BigDecimal(0);
        BigDecimal Ethanol = new BigDecimal(0);
        BigDecimal other = new BigDecimal(0);
        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> stringObjectMap = maps.get(i);
            BigDecimal oilCons = (BigDecimal) stringObjectMap.get("oilCons");
            if (oilCons == null) {
                oilCons = BigDecimal.ZERO;
            }
            if (FuelConst.HFO_HIGHT.equals(stringObjectMap.get("oilId"))) {
                hfoHight = hfoHight.add(oilCons);
            }
            if (FuelConst.HFO_LOW.equals(stringObjectMap.get("oilId"))) {
                hfoLow = hfoLow.add(oilCons);

            }
            if (FuelConst.HFO_CHAO_LOW.equals(stringObjectMap.get("oilId"))) {
                hfoSuperLow = hfoSuperLow.add(oilCons);

            }
            if (FuelConst.LFO_HIGHT.equals(stringObjectMap.get("oilId"))) {
                lfoHight = lfoHight.add(oilCons);
            }
            if (FuelConst.LFO_LOW.equals(stringObjectMap.get("oilId"))) {
                lfoLow = lfoLow.add(oilCons);
            }
            if (FuelConst.LFO_CHAO_LOW.equals(stringObjectMap.get("oilId"))) {
                lfoSuperLow = lfoSuperLow.add(oilCons);
            }
            if (FuelConst.CHAI_CODE.equals(stringObjectMap.get("oilId"))) {
                chaiFo = chaiFo.add(oilCons);
            }
            if (FuelConst.BING_CODE.equals(stringObjectMap.get("oilId"))) {
                propane = propane.add(oilCons);
            }
            if (FuelConst.DING_CODE.equals(stringObjectMap.get("oilId"))) {
                butane = butane.add(oilCons);
            }
            if (FuelConst.TIAN_CODE.equals(stringObjectMap.get("oilId"))) {
                lng = lng.add(oilCons);
            }
            if (FuelConst.METHAN_CODE.equals(stringObjectMap.get("oilId"))) {
                methanol = methanol.add(oilCons);

            }
            if (FuelConst.OIETHAN_CODE.equals(stringObjectMap.get("oilId"))) {
                Ethanol = Ethanol.add(oilCons);
            }
            calculateDcsOilVo.setButane(butane);
            calculateDcsOilVo.setChaiFo(chaiFo);
            calculateDcsOilVo.setEthanol(Ethanol);
            calculateDcsOilVo.setHfoHight(hfoHight);
            calculateDcsOilVo.setHfoLow(hfoLow);
            calculateDcsOilVo.setHfoSuperLow(hfoSuperLow);
            calculateDcsOilVo.setLfoHight(lfoHight);
            calculateDcsOilVo.setLfoLow(lfoLow);
            calculateDcsOilVo.setLfoSuperLow(lfoSuperLow);
            calculateDcsOilVo.setLng(lng);
            calculateDcsOilVo.setMethanol(methanol);
            calculateDcsOilVo.setPropane(propane);
        }
        return calculateDcsOilVo;
    }

    private RawVoyagePort getAcrossRawPort(Date preYearEndTime, CalculateDcsOilVo startOilCons, List<RawVoyagePortloading> rawVoyagePortloadings, BigDecimal distance) {
        RawVoyagePort rawVoyagePort = new RawVoyagePort();
        List<RawVoyageAddoil> rawVoyageAddoils = new ArrayList<>();
        List<RawVoyageOutoil> rawVoyageOutoils = new ArrayList<>();
        List<RawVoyageSludge> rawVoyageSludges = new ArrayList<>();
        rawVoyagePort.setRecordType("0");
        // todo
        rawVoyagePort.setArrTm(preYearEndTime);
        rawVoyagePort.setDeptTm(preYearEndTime);
        rawVoyagePort.setDistance(distance);
        rawVoyagePort.setArrZone(BigDecimal.valueOf(0));
        rawVoyagePort.setDeptZone(BigDecimal.valueOf(0));
        rawVoyagePort.setRawVoyagePortloadings(rawVoyagePortloadings);
        List<RawVoyagePortoil> rawVoyagePortoils = new ArrayList<>();
        BigDecimal zero = BigDecimal.ZERO;
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.HFO_HIGHT, startOilCons.getHfoHight(), startOilCons.getHfoHight(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.HFO_LOW, startOilCons.getHfoLow(), startOilCons.getHfoLow(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.HFO_CHAO_LOW, startOilCons.getHfoSuperLow(), startOilCons.getHfoSuperLow(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.LFO_HIGHT, startOilCons.getLfoHight(), startOilCons.getLfoHight(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.LFO_LOW, startOilCons.getLfoLow(), startOilCons.getLfoLow(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.LFO_CHAO_LOW, startOilCons.getLfoSuperLow(), startOilCons.getLfoSuperLow(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.CHAI_CODE, startOilCons.getChaiFo(), startOilCons.getChaiFo(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.TIAN_CODE, startOilCons.getLng(), startOilCons.getLng(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.BING_CODE, startOilCons.getPropane(), startOilCons.getPropane(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.DING_CODE, startOilCons.getButane(), startOilCons.getButane(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.METHAN_CODE, startOilCons.getMethanol(), startOilCons.getMethanol(), zero));
        rawVoyagePortoils.add(new RawVoyagePortoil(FuelConst.OIETHAN_CODE, startOilCons.getEthanol(), startOilCons.getEthanol(), zero));
        rawVoyagePort.setRawVoyagePortoils(rawVoyagePortoils);
        rawVoyagePort.setRawVoyageAddoils(rawVoyageAddoils);
        rawVoyagePort.setRawVoyageOutoils(rawVoyageOutoils);
        rawVoyagePort.setRawVoyageSludges(rawVoyageSludges);
        return rawVoyagePort;
    }

    /**
     * 获取collection data集合
     *
     * @param oiShipVoyages     非跨年航段集合
     * @param oiShipVoyageArray 跨年航段数组
     * @param oiShipInfo        船舶信息
     * @param sludgeCons        驳油渣量
     * @return
     */

    private List<ImoDataSummary> getImoDataSummary(List<OiShipVoyage> oiShipVoyages, List<OiShipVoyage> oiShipVoyageArray, OiShipInfo oiShipInfo, List<RawVoyageSludge> sludgeCons, String rptId, Map<String, String> useMethod,
                                                   List<OiShipVoyage> collectPreYearVoyage, List<OiShipVoyage> collectPrenextYearVoyage, Date preYearStartTime, Date nextYearEndTime, CalculateDcsOilVo startOilCons, CalculateDcsOilVo endOilCons, String shipId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ImoDataSummary> imoDataSummaries = new ArrayList<>();
        oiShipVoyages.sort((x, y) -> {
            Date startTimeX = x.getStartTime();
            Date startTimeY = y.getStartTime();
            if (startTimeY != null && startTimeX != null) {
                if (startTimeX.getTime() > startTimeY.getTime()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        });
        BigDecimal preYearVoyageSailtime = new BigDecimal(0);
        //判断是否上一年是否跨年
        if (collectPreYearVoyage.size() == 0 && oiShipVoyages.size() > 0) {
            //跨年将期初量计算入内
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(0);//第一个航段
            Date startTime = oiShipVoyage.getStartTime();
            List<RawVoyagePort> byAcrossYear = rawVoyagePortRepository.findByAcrossYear(shipId, simpleDateFormat.format(preYearStartTime)
                    , simpleDateFormat.format(startTime));
            List<RawVoyagePort> rawVoyagePortsMerge = energyEefficService.procVoyageByMergePorts(byAcrossYear);
            List<RawVoyagePort> noAcross = rawVoyagePortsMerge.stream().filter(x -> {
                Map<String, Date> utcTimeByPort = getUtcTimeByPort(x);
                Date arr = utcTimeByPort.get("arr");
                Date dept = utcTimeByPort.get("dept");
                return (arr.getTime() >= preYearStartTime.getTime() && arr.getTime() <= startTime.getTime()) || (dept.getTime() >= preYearStartTime.getTime() && dept.getTime() <= startTime.getTime());
            }).collect(Collectors.toList());
            if (noAcross.size() > 0) {
                RawVoyagePort rawVoyagePort = noAcross.get(0);
                List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();
                List<RawVoyagePortoil> rawVoyagePortoilsNew = new ArrayList<>();
                for (RawVoyagePortoil rawVoyagePortoil : rawVoyagePortoils) {
                    BigDecimal deptTons = rawVoyagePortoil.getDeptTons();
                    BigDecimal arrTons = BigDecimal.ZERO;
                    String oilId = rawVoyagePortoil.getOilId();
                    RawVoyagePortoil rawVoyagePortoilNew = new RawVoyagePortoil();
                    rawVoyagePortoilNew.setOilId(oilId);
                    if (FuelConst.HFO_HIGHT.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getHfoHight());
                    }
                    if (FuelConst.HFO_LOW.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getHfoLow());
                    }
                    if (FuelConst.HFO_CHAO_LOW.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getHfoSuperLow());
                    }
                    if (FuelConst.LFO_HIGHT.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getLfoHight());
                    }
                    if (FuelConst.LFO_LOW.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getLfoLow());
                    }
                    if (FuelConst.LFO_CHAO_LOW.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getLfoSuperLow());
                    }

                    if (FuelConst.CHAI_CODE.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getChaiFo());
                    }

                    if (FuelConst.BING_CODE.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getPropane());

                    }

                    if (FuelConst.DING_CODE.equals(oilId)) {

                        arrTons = arrTons.add(startOilCons.getButane());
                    }

                    if (FuelConst.TIAN_CODE.equals(oilId)) {
                        arrTons = arrTons.add(startOilCons.getLng());
                    }
                    rawVoyagePortoilNew.setArrTons(arrTons);
                    rawVoyagePortoilNew.setDeptTons(deptTons);
                    rawVoyagePortoilsNew.add(rawVoyagePortoilNew);
                }
                rawVoyagePort.setRawVoyagePortoils(rawVoyagePortoilsNew);
                RawVoyagePort rawVoyagePortNew = new RawVoyagePort();//创建虚拟港口
                rawVoyagePortNew.setInPort("0");
                List<RawVoyagePort> rawVoyagePorts = new ArrayList<>();
                rawVoyagePorts.add(rawVoyagePortNew);
                rawVoyagePorts.add(rawVoyagePort);
                CalculateOilVo stop = energyEefficService.calculateSailingOil(rawVoyagePorts, "stop");
                oiShipVoyage.setOiHfo(oiShipVoyage.getOiHfo().add(stop.getOiHfo()));
                oiShipVoyage.setOiLfo(oiShipVoyage.getOiLfo().add(stop.getOiLfo()));
                oiShipVoyage.setOiChai(oiShipVoyage.getOiChai().add(stop.getOiChai()));
                oiShipVoyage.setStartTime(preYearStartTime);
            }
        }
        //判断下半年是否跨年
        if (collectPrenextYearVoyage.size() == 0 && oiShipVoyages.size() > 0) {
            //查询出最后一个港口的离港存量
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(oiShipVoyages.size() - 1);
            OiShipVoyage oiShipVoyageNew = new OiShipVoyage();
            String endportid = oiShipVoyage.getEndportid();
            RawVoyagePort rawVoyagePort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
            List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();
            CalculateDcsOilVo calculateDcsOilVo = new CalculateDcsOilVo();
            BeanUtils.copyProperties(endOilCons, calculateDcsOilVo);
            for (RawVoyagePortoil rawVoyagePortoil : rawVoyagePortoils) {
                String oilId = rawVoyagePortoil.getOilId();
                BigDecimal deptTons = rawVoyagePortoil.getDeptTons();
                if (deptTons == null) {
                    deptTons = BigDecimal.ZERO;
                }
                if (FuelConst.HFO_HIGHT.equals(oilId)) {
                    calculateDcsOilVo.setHfoHight(deptTons.subtract(calculateDcsOilVo.getHfoHight()));
                }
                if (FuelConst.HFO_LOW.equals(oilId)) {
                    calculateDcsOilVo.setHfoLow(deptTons.subtract(calculateDcsOilVo.getHfoLow()));
                }
                if (FuelConst.HFO_CHAO_LOW.equals(oilId)) {
                    calculateDcsOilVo.setHfoSuperLow(deptTons.subtract(calculateDcsOilVo.getHfoSuperLow()));
                }
                if (FuelConst.LFO_HIGHT.equals(oilId)) {
                    calculateDcsOilVo.setLfoHight(deptTons.subtract(calculateDcsOilVo.getLfoHight()));
                }
                if (FuelConst.LFO_LOW.equals(oilId)) {
                    calculateDcsOilVo.setLfoLow(deptTons.subtract(calculateDcsOilVo.getLfoLow()));
                }
                if (FuelConst.LFO_CHAO_LOW.equals(oilId)) {
                    calculateDcsOilVo.setLfoSuperLow(deptTons.subtract(calculateDcsOilVo.getLfoSuperLow()));
                }
                if (FuelConst.CHAI_CODE.equals(oilId)) {
                    calculateDcsOilVo.setChaiFo(deptTons.subtract(calculateDcsOilVo.getChaiFo()));
                }
                if (FuelConst.BING_CODE.equals(oilId)) {
                    calculateDcsOilVo.setPropane(deptTons.subtract(calculateDcsOilVo.getPropane()));
                }
                if (FuelConst.DING_CODE.equals(oilId)) {
                    calculateDcsOilVo.setButane(deptTons.subtract(calculateDcsOilVo.getButane()));
                }
                if (FuelConst.TIAN_CODE.equals(oilId)) {
                    calculateDcsOilVo.setLng(deptTons.subtract(calculateDcsOilVo.getLng()));
                }
            }
            oiShipVoyageNew.setOiHfo(calculateDcsOilVo.getHfoHight().add(calculateDcsOilVo.getHfoLow()).add(calculateDcsOilVo.getHfoSuperLow()));
            oiShipVoyageNew.setOiLfo(calculateDcsOilVo.getLfoHight().add(calculateDcsOilVo.getLfoLow()).add(calculateDcsOilVo.getLfoSuperLow()));
            oiShipVoyageNew.setOiChai(calculateDcsOilVo.getChaiFo());
            oiShipVoyageNew.setOiethanol(calculateDcsOilVo.getEthanol());
            oiShipVoyageNew.setOiTian(calculateDcsOilVo.getLng());
            oiShipVoyageNew.setOiDing(calculateDcsOilVo.getButane());
            oiShipVoyageNew.setOiBing(calculateDcsOilVo.getPropane());
            oiShipVoyageNew.setOiOther(calculateDcsOilVo.getOther());
            oiShipVoyageNew.setEndTime(nextYearEndTime);
            oiShipVoyageNew.setStartTime(oiShipVoyage.getEndTime());
            BigDecimal zero = BigDecimal.ZERO;
            oiShipVoyageNew.setStopethanol(zero);
            oiShipVoyageNew.setStopoiOther(zero);
            oiShipVoyageNew.setStopoiTian(zero);
            oiShipVoyageNew.setStopoiBing(zero);
            oiShipVoyageNew.setStopoiLfo(zero);
            oiShipVoyageNew.setStopoiChai(zero);
            oiShipVoyageNew.setStopoiHfo(zero);
            oiShipVoyageNew.setStopoiDing(zero);
            //计算航行时间
            long startTime = 0;
            long endTime = 0;
            if (oiShipVoyage.getStartTime() != null && oiShipVoyage.getEndTime() != null) {
                startTime = oiShipVoyageNew.getStartTime().getTime();
                endTime = oiShipVoyageNew.getEndTime().getTime();
            }
            long h = 1000 * 60 * 60;
            BigDecimal sailTime = BigDecimal.valueOf((double) (endTime - startTime) / h);
            oiShipVoyageNew.setSailTime(sailTime);
            oiShipVoyages.add(oiShipVoyageNew);
        }

        if (oiShipVoyageArray.size() >= 2) {//todo

            OiShipVoyage preYearVoyage = oiShipVoyageArray.get(0);

            OiShipVoyage oiShipVoyagePre = oiShipVoyageArray.get(1);

            oiShipVoyages.add(0, oiShipVoyagePre);

            preYearVoyageSailtime = preYearVoyage.getSailTime();

        }

        BigDecimal nextYearVoyageSailtime = new BigDecimal(0);

        if (oiShipVoyageArray.size() == 4) {

            OiShipVoyage nextYearVoyage = oiShipVoyageArray.get(2);

            OiShipVoyage oiShipVoyageNext = oiShipVoyageArray.get(3);

            oiShipVoyages.add(oiShipVoyageNext);

            nextYearVoyageSailtime = nextYearVoyage.getSailTime();

        }

        //计算跨年航段的航行里程和油耗

        for (int i = 0; i < oiShipVoyages.size(); i++) {

            List<CdOil> cdOils = new ArrayList<>();

            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            if (oiShipVoyage == null) continue;

            ImoDataSummary imoDataSummaryPre = new ImoDataSummary();

            BigDecimal distance = oiShipVoyage.getDistance();
            if (distance == null) {
                distance = BigDecimal.ZERO;
            }

            BigDecimal sailtime = oiShipVoyage.getSailTime();
            if (sailtime == null) {
                sailtime = BigDecimal.ZERO;
            }

            if (oiShipVoyageArray.size() >= 2) {
                if (i == 0) {
                    OiShipVoyage oiShipVoyage1 = oiShipVoyageArray.get(0);
                    BigDecimal timePerCent = preYearVoyageSailtime.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : sailtime.divide(preYearVoyageSailtime, 4, BigDecimal.ROUND_HALF_UP);
                    distance = oiShipVoyage1.getDistance().multiply(timePerCent).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }

            if (oiShipVoyageArray.size() == 4) {
                if (i == oiShipVoyages.size() - 1) {
//                    sailtime = getSailtimeAcross(oiShipVoyage);
                    OiShipVoyage nextYearVoyage = oiShipVoyageArray.get(2);
                    BigDecimal bigDecimalPercent = nextYearVoyageSailtime.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : sailtime.divide(nextYearVoyageSailtime, 4, BigDecimal.ROUND_HALF_UP);
                    distance = nextYearVoyage.getDistance().multiply(bigDecimalPercent).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }

            imoDataSummaryPre.setStartTm(oiShipVoyage.getStartTime());

            imoDataSummaryPre.setEndTm(oiShipVoyage.getEndTime());

            imoDataSummaryPre.setRecStatus("1");

            imoDataSummaryPre.setIsDelete(0);

            imoDataSummaryPre.setCountryFlag(oiShipInfo.getFlagCode());

            imoDataSummaryPre.setDocIcas(oiShipInfo.getDocIacs());

            imoDataSummaryPre.setDocName(oiShipInfo.getDocmanager());

            imoDataSummaryPre.setImono(oiShipInfo.getImono());

            imoDataSummaryPre.setDistOnsea(distance);

            imoDataSummaryPre.setHourOnsea(sailtime);

            imoDataSummaryPre.setShipName(oiShipInfo.getSpname());

            imoDataSummaryPre.setRptId(rptId);

            ImoDataSummary save = imoDataSummaryRepository.save(imoDataSummaryPre);

            String sumId = save.getId();
            Date endTime = oiShipVoyage.getEndTime();
            Date startTime = oiShipVoyage.getStartTime();
            //根据航段的开始时间和结束时间查询驳油渣
//            String id = oiShipInfo.getId();
//            String startTimeString = simpleDateFormat.format(startTime);
//            String endTimeString = simpleDateFormat.format(endTime);
            //zxh调校，在主方法里已经查询过DCS周期内的油渣记录了
            //List<RawVoyageSludge> sludgeList = rawVoyageSludgeoilRepository.getSludgeList(id, startTimeString, endTimeString);
            List<RawVoyageSludge> collectSluageOutSummary = sludgeCons.stream().filter(x -> {
                String portinfoId = x.getPortinfoId();
                Date sludgeOutTm = x.getSludgeOutTm();
                //zxh调校，在主方法发过来的油渣日期已经 是UTC了，在这里不用转换了
                RawVoyagePort byIdAndIsDelete = rawVoyagePortRepository.findByIdAndIsDelete(portinfoId, 0);
                if (byIdAndIsDelete != null) {
                    BigDecimal arrZone = byIdAndIsDelete.getArrZone();
                    Date date = DateUtils.localToUTC(sludgeOutTm, arrZone);
                    return date.getTime() >= startTime.getTime() && date.getTime() <= endTime.getTime();
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            BigDecimal sluageTons = BigDecimal.ZERO;
            if (collectSluageOutSummary != null && collectSluageOutSummary.size() > 0) {
                sluageTons = collectSluageOutSummary.stream().map(RawVoyageSludge::getSludgeTons).reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            cdOils.add(new CdOil(sumId, FuelConst.HFO_HIGHT, FuelConst.HFO_HIGHT_NAME, useMethod.get(FuelConst.HFO_HIGHT), oiShipVoyage.getOiHfo().add(oiShipVoyage.getStopoiHfo()).subtract(sluageTons), "0", 0));

            cdOils.add(new CdOil(sumId, FuelConst.LFO_HIGHT, FuelConst.LFO_HIGHT_NAME, useMethod.get(FuelConst.LFO_HIGHT), oiShipVoyage.getOiLfo().add(oiShipVoyage.getStopoiLfo()), "0", 0));

            cdOils.add(new CdOil(sumId, FuelConst.CHAI_CODE, FuelConst.CHAI_CODE_NAME, useMethod.get(FuelConst.CHAI_CODE), oiShipVoyage.getOiChai().add(oiShipVoyage.getStopoiChai()), "0", 0));

            cdOils.add(new CdOil(sumId, FuelConst.TIAN_CODE, FuelConst.TIAN_CODE_NAME, useMethod.get(FuelConst.TIAN_CODE), oiShipVoyage.getOiTian().add(oiShipVoyage.getStopoiTian()), "0", 0));

            cdOils.add(new CdOil(sumId, FuelConst.BING_CODE, FuelConst.BING_NAME, useMethod.get(FuelConst.BING_CODE), oiShipVoyage.getOiBing().add(oiShipVoyage.getStopoiBing()), "0", 0));

            cdOils.add(new CdOil(sumId, FuelConst.DING_CODE, FuelConst.DING_NAME, useMethod.get(FuelConst.DING_CODE), oiShipVoyage.getOiDing().add(oiShipVoyage.getStopoiDing()), "0", 0));

            cdOils.add(new CdOil(sumId, FuelConst.METHAN_CODE, FuelConst.METHAN_NAME, useMethod.get(FuelConst.METHAN_CODE), oiShipVoyage.getOiOther().add(oiShipVoyage.getStopoiOther()), "0", 0));

            cdOils.add(new CdOil(sumId, FuelConst.OIETHAN_CODE, FuelConst.OIETHAN_NAME, useMethod.get(FuelConst.OIETHAN_CODE), oiShipVoyage.getOiethanol().add(oiShipVoyage.getStopethanol()), "0", 0));

            cdOilRepository.saveAll(cdOils);

            imoDataSummaryPre.setCdOils(cdOils);

            imoDataSummaries.add(imoDataSummaryPre);

        }

        return imoDataSummaries;

    }


    //    public File getImoCDExcel(String templateXlsName, List<ImoDataSummary> imoDataSummaries) {
//        List<ImoCDExcelVo> imoCDExcelVos = new ArrayList<>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        File file = null;
//        try {
//            XLSTransformer transformer = new XLSTransformer();
//            // 获取模板
//            String tempPath = System.getProperty("java.io.tmpdir") + IdUtil.fastSimpleUUID() + ".xlsx";
//            Map beans = new HashMap();
//            BigDecimal voyageNm = new BigDecimal(0);//航程
//            BigDecimal voyageHour = new BigDecimal(0);//航行小时
//            BigDecimal hfoTons = new BigDecimal(0);// 重油消耗量
//            BigDecimal lfoTons = new BigDecimal(0);// 轻油消耗量
//            BigDecimal chaiTons = new BigDecimal(0);// 柴/汽油消耗量
//            BigDecimal bingTons = new BigDecimal(0);// 丙烷消耗量
//            BigDecimal dingTons = new BigDecimal(0);// 丁烷消耗量
//            BigDecimal tianTons = new BigDecimal(0);// 天燃气消耗量
//            BigDecimal oiOther = new BigDecimal(0);// 甲醇消耗量
//            for (int i = 0; i < imoDataSummaries.size(); i++) {
//                ImoDataSummary imoDataSummary = imoDataSummaries.get(i);
//                ImoCDExcelVo imoCDExcelVo = new ImoCDExcelVo();
//                imoCDExcelVo.setDistance(imoDataSummary.getDistOnsea());
//                imoCDExcelVo.setHourOnSea(imoDataSummary.getHourOnsea());
//                imoCDExcelVo.setBtime(simpleDateFormat.format(imoDataSummary.getStartTm()));
//                imoCDExcelVo.setBtimeDate(imoDataSummary.getStartTm());
//                imoCDExcelVo.setEtime(simpleDateFormat.format(imoDataSummary.getEndTm()));
//                List<CdOil> cdOils = imoDataSummary.getCdOils();
//                for (int j = 0; j < cdOils.size(); j++) {
//                    CdOil cdOil = cdOils.get(j);
//                    if (FuelConst.HFO_HIGHT.equals(cdOil.getOilId())) {
//                        hfoTons = hfoTons.add(cdOil.getFuelTons());
//                        imoCDExcelVo.setOiHfo(cdOil.getFuelTons());
//                    }
//                    if (FuelConst.LFO_HIGHT.equals(cdOil.getOilId())) {
//                        lfoTons = lfoTons.add(cdOil.getFuelTons());
//                        imoCDExcelVo.setOiLfo(cdOil.getFuelTons());
//                    }
//                    if (FuelConst.CHAI_CODE.equals(cdOil.getOilId())) {
//                        chaiTons = chaiTons.add(cdOil.getFuelTons());
//                        imoCDExcelVo.setOiChai(cdOil.getFuelTons());
//                    }
//                    if (FuelConst.BING_CODE.equals(cdOil.getOilId())) {
//                        bingTons = bingTons.add(cdOil.getFuelTons());
//                        imoCDExcelVo.setOiBing(cdOil.getFuelTons());
//                    }
//                    if (FuelConst.DING_CODE.equals(cdOil.getOilId())) {
//                        dingTons = dingTons.add(cdOil.getFuelTons());
//                        imoCDExcelVo.setOiDing(cdOil.getFuelTons());
//                    }
//                    if (FuelConst.METHAN_CODE.equals(cdOil.getOilId())) {
//                        oiOther = oiOther.add(cdOil.getFuelTons());
//                        imoCDExcelVo.setOiOther(cdOil.getFuelTons());
//                    }
//                    if (FuelConst.TIAN_CODE.equals(cdOil.getOilId())) {
//                        tianTons = tianTons.add(cdOil.getFuelTons());
//                        imoCDExcelVo.setOiTian(cdOil.getFuelTons());
//                    }
//                }
//                imoCDExcelVos.add(imoCDExcelVo);
//                voyageNm = voyageNm.add(imoDataSummary.getDistOnsea());
//                voyageHour = voyageHour.add(imoDataSummary.getHourOnsea());
//            }
//            imoCDExcelVos.sort((x,y)->{
//                Date operatorTimedateX = x.getBtimeDate();
//                Date operatorTimedateY = y.getBtimeDate();
//                if(operatorTimedateX != null && operatorTimedateY != null){
//                    return operatorTimedateX.compareTo(operatorTimedateY);
//                }else {
//                    return -1;
//                }
//            });
//            beans.put("list", imoCDExcelVos);
//            beans.put("totalNm", voyageNm);
//            beans.put("totalHour", voyageHour);
//            beans.put("totalHfo", hfoTons);
//            beans.put("totalLfo", lfoTons);
//            beans.put("totalChai", chaiTons);
//            beans.put("totalBing", bingTons);
//            beans.put("totalDing", dingTons);
//            beans.put("totalTian", tianTons);
//            beans.put("totalOther", oiOther);
//            transformer.transformXLS(templateXlsName, beans, tempPath);
//            file = new File(tempPath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
    public Map<String, Object> getImoCDExcel(List<ImoDataSummary> imoDataSummaries) {
        List<ImoCDExcelVo> imoCDExcelVos = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map beans = new HashMap();
        try {
            BigDecimal voyageNm = new BigDecimal(0);//航程
            BigDecimal voyageHour = new BigDecimal(0);//航行小时
            BigDecimal hfoTons = new BigDecimal(0);// 重油消耗量
            BigDecimal lfoTons = new BigDecimal(0);// 轻油消耗量
            BigDecimal chaiTons = new BigDecimal(0);// 柴/汽油消耗量
            BigDecimal bingTons = new BigDecimal(0);// 丙烷消耗量
            BigDecimal dingTons = new BigDecimal(0);// 丁烷消耗量
            BigDecimal tianTons = new BigDecimal(0);// 天燃气消耗量
            BigDecimal oiOther = new BigDecimal(0);// 甲醇消耗量
            for (int i = 0; i < imoDataSummaries.size(); i++) {
                ImoDataSummary imoDataSummary = imoDataSummaries.get(i);
                ImoCDExcelVo imoCDExcelVo = new ImoCDExcelVo();
                imoCDExcelVo.setDistance(imoDataSummary.getDistOnsea());
                imoCDExcelVo.setHourOnSea(imoDataSummary.getHourOnsea());
                imoCDExcelVo.setBtime(simpleDateFormat.format(imoDataSummary.getStartTm()));
                imoCDExcelVo.setBtimeDate(imoDataSummary.getStartTm());
                imoCDExcelVo.setEtime(simpleDateFormat.format(imoDataSummary.getEndTm()));
                List<CdOil> cdOils = imoDataSummary.getCdOils();
                for (int j = 0; j < cdOils.size(); j++) {
                    CdOil cdOil = cdOils.get(j);
                    if (FuelConst.HFO_HIGHT.equals(cdOil.getOilId())) {
                        hfoTons = hfoTons.add(cdOil.getFuelTons());
                        imoCDExcelVo.setOiHfo(cdOil.getFuelTons());
                    }
                    if (FuelConst.LFO_HIGHT.equals(cdOil.getOilId())) {
                        lfoTons = lfoTons.add(cdOil.getFuelTons());
                        imoCDExcelVo.setOiLfo(cdOil.getFuelTons());
                    }
                    if (FuelConst.CHAI_CODE.equals(cdOil.getOilId())) {
                        chaiTons = chaiTons.add(cdOil.getFuelTons());
                        imoCDExcelVo.setOiChai(cdOil.getFuelTons());
                    }
                    if (FuelConst.BING_CODE.equals(cdOil.getOilId())) {
                        bingTons = bingTons.add(cdOil.getFuelTons());
                        imoCDExcelVo.setOiBing(cdOil.getFuelTons());
                    }
                    if (FuelConst.DING_CODE.equals(cdOil.getOilId())) {
                        dingTons = dingTons.add(cdOil.getFuelTons());
                        imoCDExcelVo.setOiDing(cdOil.getFuelTons());
                    }
                    if (FuelConst.METHAN_CODE.equals(cdOil.getOilId())) {
                        oiOther = oiOther.add(cdOil.getFuelTons());
                        imoCDExcelVo.setOiOther(cdOil.getFuelTons());
                    }
                    if (FuelConst.TIAN_CODE.equals(cdOil.getOilId())) {
                        tianTons = tianTons.add(cdOil.getFuelTons());
                        imoCDExcelVo.setOiTian(cdOil.getFuelTons());
                    }
                }
                imoCDExcelVos.add(imoCDExcelVo);
                voyageNm = voyageNm.add(imoDataSummary.getDistOnsea());
                voyageHour = voyageHour.add(imoDataSummary.getHourOnsea());
            }
            imoCDExcelVos.sort((x, y) -> {
                Date operatorTimedateX = x.getBtimeDate();
                Date operatorTimedateY = y.getBtimeDate();
                if (operatorTimedateX != null && operatorTimedateY != null) {
                    return operatorTimedateX.compareTo(operatorTimedateY);
                } else {
                    return -1;
                }
            });
            beans.put("list", imoCDExcelVos);
            beans.put("totalNm", voyageNm);
            beans.put("totalHour", voyageHour);
            beans.put("totalHfo", hfoTons);
            beans.put("totalLfo", lfoTons);
            beans.put("totalChai", chaiTons);
            beans.put("totalBing", bingTons);
            beans.put("totalDing", dingTons);
            beans.put("totalTian", tianTons);
            beans.put("totalOther", oiOther);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }

    private DownLoadBdn getDownLoadBdnVo(List<ImoBdnSummary> bdns) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<DownLoadBdnVo> downLoadBdnVos = new ArrayList<>();
        DownLoadBdn downLoadBdn = new DownLoadBdn();
        BigDecimal sumHfo = new BigDecimal(0);
        BigDecimal sumLfo = new BigDecimal(0);
        BigDecimal sumChai = new BigDecimal(0);
        BigDecimal sumPropane = new BigDecimal(0);
        BigDecimal sumButane = new BigDecimal(0);
        BigDecimal sumOther = new BigDecimal(0);
        BigDecimal sumLng = new BigDecimal(0);
        for (int i = 0; i < bdns.size(); i++) {
            ImoBdnSummary imoBdnSummary = bdns.get(i);
            List<BdnOil> bdnOils = imoBdnSummary.getBdnOils();
            DownLoadBdnVo downLoadBdnVo = new DownLoadBdnVo();
            downLoadBdnVo.setOperatorTime(simpleDateFormat.format(imoBdnSummary.getStartTm()));
            downLoadBdnVo.setOperatorTimedate(imoBdnSummary.getStartTm());
            BigDecimal hfo = new BigDecimal(0);
            BigDecimal lfo = new BigDecimal(0);
            BigDecimal chai = new BigDecimal(0);
            BigDecimal propane = new BigDecimal(0);
            BigDecimal butane = new BigDecimal(0);
            BigDecimal other = new BigDecimal(0);
            BigDecimal lng = new BigDecimal(0);
            for (int j = 0; j < bdnOils.size(); j++) {
                BdnOil bdnOil = bdnOils.get(j);
                List<String> stringsHfo = Arrays.asList(FuelConst.HFO_CODE.split(","));
                List<String> stringsLfo = Arrays.asList(FuelConst.LFO_CODE.split(","));
                if (stringsHfo.contains(bdnOil.getOilId())) {
                    hfo = hfo.add(bdnOil.getFuelTons());
                    sumHfo = sumHfo.add(bdnOil.getFuelTons());
                }
                if (stringsLfo.contains(bdnOil.getOilId())) {
                    lfo = lfo.add(bdnOil.getFuelTons());
                    sumLfo = sumLfo.add(bdnOil.getFuelTons());
                }
                if (FuelConst.CHAI_CODE.equals(bdnOil.getOilId())) {
                    chai = chai.add(bdnOil.getFuelTons());
                    sumChai = sumChai.add(bdnOil.getFuelTons());
                }
                if (FuelConst.BING_CODE.equals(bdnOil.getOilId())) {
                    propane = propane.add(bdnOil.getFuelTons());
                    sumPropane = sumPropane.add(bdnOil.getFuelTons());
                }
                if (FuelConst.DING_CODE.equals(bdnOil.getOilId())) {
                    butane = butane.add(bdnOil.getFuelTons());
                    sumButane = sumButane.add(bdnOil.getFuelTons());
                }
                if (FuelConst.METHAN_CODE.equals(bdnOil.getOilId())) {
                    other = other.add(bdnOil.getFuelTons());
                    sumOther = sumOther.add(bdnOil.getFuelTons());
                }
                if (FuelConst.TIAN_CODE.equals(bdnOil.getOilId())) {
                    lng = lng.add(bdnOil.getFuelTons());
                    sumLng = sumLng.add(bdnOil.getFuelTons());
                }
            }
            downLoadBdnVo.setHfo(hfo);
            downLoadBdnVo.setLfo(lfo);
            downLoadBdnVo.setChaiFo(chai);
            downLoadBdnVo.setButane(butane);
            downLoadBdnVo.setPropane(propane);
            downLoadBdnVo.setLng(lng);
            downLoadBdnVo.setOther(other);
            downLoadBdnVos.add(downLoadBdnVo);
        }
        downLoadBdnVos.sort((x, y) -> {
            Date operatorTimedateX = x.getOperatorTimedate();
            Date operatorTimedateY = y.getOperatorTimedate();
            if (operatorTimedateX != null && operatorTimedateY != null) {
                return operatorTimedateX.compareTo(operatorTimedateY);
            } else {
                return -1;
            }
        });
        downLoadBdn.setDownLoadBdnVos(downLoadBdnVos);
        downLoadBdn.setDownLoadBdnVo(new DownLoadBdnVo(sumChai, sumHfo, sumLfo, sumPropane, sumButane, sumLng, sumOther));
        return downLoadBdn;
    }

    /**
     * 根据船舶获取油耗类型和测量方法
     *
     * @param shipId
     * @return
     */
    private Map<String, String> getUseMethod(String shipId) {
        Map<String, String> map = new HashMap<>();
        //根据船舶id查询油耗类型
        List<ShipEquipmentFuelMap> useMethod = shipEquipmentFuelMapRepository.findUseMethod(shipId);
        useMethod.forEach(shipEquipmentFuelMap -> {
            map.put(shipEquipmentFuelMap.getFuelId(), shipEquipmentFuelMap.getConsMethod());
        });
        return map;
    }

    private List<String> getMainAndGe(List<String> oiShipGes, List<String> oiMainEngines) {
        List<String> strings = new ArrayList<>();
        StringBuilder gePowerAppend = new StringBuilder("");
        StringBuilder mainPowerApend = new StringBuilder("");
        for (int i = 0; i < oiShipGes.size(); i++) {
            String s = oiShipGes.get(i);
            if (i == oiShipGes.size() - 1) {
                gePowerAppend.append("No" + (i + 1) + ":" + s);
            } else {
                gePowerAppend.append("No" + (i + 1) + ":" + s + "/");
            }
        }
        for (int i = 0; i < oiMainEngines.size(); i++) {
            String s = oiMainEngines.get(i);
            if (i == oiMainEngines.size() - 1) {
                mainPowerApend.append("No" + (i + 1) + ":" + s);
            } else {
                mainPowerApend.append("No" + (i + 1) + ":" + s + "/");
            }
        }
        strings.add(mainPowerApend.toString());
        strings.add(gePowerAppend.toString());
        return strings;
    }

    /**
     * 获取中文或者英文燃油测量方法
     *
     * @param map
     * @param shipId
     * @return
     */
    private String getEnOrCnConsMethod(Map<String, String> map, String shipId) {
        List<String> consMehtods = shipEquipmentFuelMapRepository.findByConsMethod(shipId);
        StringBuilder consMethod = new StringBuilder();
        for (int i = 0; i < consMehtods.size(); i++) {
            String s = map.get(consMehtods.get(i));
            if (i >= 1) {
                consMethod.append(",");
            }
            consMethod.append(s);
        }
        return consMethod.toString();
    }

    /**
     * 根据IMO DCS报告ID号，获取IMO报告数据对象VO
     * 返回的参数完全按IMO DCS要求，包括国家代码、日期格式、油品名称等
     *
     * @param List <String> imoRptIds
     * @return List<ReaportImoRptVo>
     */
    private List<ReaportImoRptVo> getReaportImoRptVo(List<String> imoRptIds) {
        List<ReaportImoRptVo> reaportImoRptVos = new ArrayList<>();
        List<ImoStdrpt> byIds = imoStdrptRepository.findByIds(imoRptIds);
        Map<String, String> mapEn = fuelConsMethodProperties.getMapEn();
        byIds.forEach(imoStdrpt -> {
//            OiShipInfo oiShipInfo = imoStdrpt.getOiShipInfo();
            ReaportImoRptVo reaportImoRptVo = new ReaportImoRptVo();
            reaportImoRptVo.setId(imoStdrpt.getId());
            ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(imoStdrpt.getId(), 0);
            List<ImoCorrectionOil> imoCorrectionOils = null;
            if (imoStdrptAdjust != null) {
                imoCorrectionOils = imoStdrptAdjust.getImoCorrectionOils();
            }
            List<ImoOilVo> imoOilVos = new ArrayList<>();
            List<ImoCorrectionOil> finalImoCorrectionOils = imoCorrectionOils;
            imoStdrpt.getImoOils().forEach(imoOil -> {
                String oilId = imoOil.getOilId();
                BigDecimal fuelTons = imoOil.getFuelTons();
                if (fuelTons == null) {
                    fuelTons = BigDecimal.ZERO;
                }
                ImoOilVo imoOilVo = new ImoOilVo();
//                if (finalImoCorrectionOils != null) {
//                    for (ImoCorrectionOil finalImoCorrectionOil : finalImoCorrectionOils) {
//                        String oilIdCorrect = finalImoCorrectionOil.getOilId();
//                        if (oilId.equals(oilIdCorrect)) {
//                            BigDecimal fuelTonsCorrect = finalImoCorrectionOil.getFuelTons();
//                            if (fuelTonsCorrect == null) {
//                                fuelTonsCorrect = BigDecimal.ZERO;
//                            }
//                            fuelTons = fuelTons.add(fuelTonsCorrect);
//                        }
//                    }
//                }
                imoOilVo.setOilId(imoOil.getOilId());
                fuelTons.setScale(0, BigDecimal.ROUND_HALF_UP); // 报送取整
                imoOilVo.setQuantity(fuelTons);
                String oilMethod = "BDN";
                String oilMethodGet = imoOil.getOilMethod();
                if ("使用燃油交付单的方法".equals(oilMethodGet)) {
                    oilMethod = "BDN";
                }
                if ("使用流量计的方法".equals(oilMethodGet)) {
                    oilMethod = "FlowMeter";
                }
                if ("使用燃油舱柜监测的方法".equals(oilMethodGet)) {
                    oilMethod = "BunkerTankMonitoring";
                }
                imoOilVo.setDataCollectionMethod(oilMethod);
                //TODO1: 燃油类型对应不正确，应该兼容HFO三种油，LFO也有同样的问题，已处理
                if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(imoOil.getOilId())) {
                    imoOilVo.setType("HeavyFuel");
                }
                if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(imoOil.getOilId())) {
                    imoOilVo.setType("LightFuel");
                }
                if (FuelConst.CHAI_CODE.equals(imoOil.getOilId())) {
                    imoOilVo.setType("DieselGasOil");
                }
                if (FuelConst.TIAN_CODE.equals(imoOil.getOilId())) {
                    imoOilVo.setType("LNG");
                }
                if (FuelConst.BING_CODE.equals(imoOil.getOilId())) {
                    imoOilVo.setType("LPGPropage");
                }
                if (FuelConst.DING_CODE.equals(imoOil.getOilId())) {
                    imoOilVo.setType("LPGButane");
                }
                if (FuelConst.METHAN_CODE.equals(imoOil.getOilId())) {
                    imoOilVo.setType("Methanol");
                }
                if (FuelConst.OIETHAN_CODE.equals(imoOil.getOilId())) {
                    imoOilVo.setType("Ethanol");
                }
                imoOilVos.add(imoOilVo);
            });
            //zxh: IMO报告里存的本身就是三位国家代码，不需要再做转换
//            GcState flag = gcStateRepository.findFlag(imoStdrpt.getCountryFlag());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            reaportImoRptVo.setFlag(imoStdrpt.getCountryFlag());
            reaportImoRptVo.setDistance(imoStdrpt.getDistOnsea());
            reaportImoRptVo.setDistanceHour(imoStdrpt.getHourOnsea());
            reaportImoRptVo.setGross(imoStdrpt.getGrossTons());
            reaportImoRptVo.setNet(imoStdrpt.getNetTons().setScale(0, BigDecimal.ROUND_HALF_UP));
            reaportImoRptVo.setDwt(imoStdrpt.getDwTons().setScale(0, BigDecimal.ROUND_HALF_UP));
            if (imoStdrpt.getEediVal() == null) {
                imoStdrpt.setEediVal(BigDecimal.ZERO);
            }
            reaportImoRptVo.setEedi(imoStdrpt.getEediVal().setScale(0, BigDecimal.ROUND_HALF_UP));
            reaportImoRptVo.setIce(imoStdrpt.getIceClass());
            reaportImoRptVo.setStartTime(simpleDateFormat.format(imoStdrpt.getStartTm()));
            reaportImoRptVo.setEndTime(simpleDateFormat.format(imoStdrpt.getEndTm()));
            reaportImoRptVo.setImoNu(imoStdrpt.getImono());
            reaportImoRptVo.setRefCode(imoStdrpt.getRefCode());
            reaportImoRptVo.setImoOils(imoOilVos);

            //zxh：处理主机功率信息，形成列表，应对No1: 1020.0 / No2: 1020.0 / No3: 1020.0的老格式
            String mePower = imoStdrpt.getMePower();
            List<String> oiMainEngines = new ArrayList<String>();
            if (mePower != null && mePower.indexOf("/") >= 0) {
                String[] mes = mePower.split("/");
                for (String me : mes) {
                    String m = me.split(":")[1];
                    if (m != null && !"".equals(m.trim())) {
                        oiMainEngines.add(m.trim());
                    }
                }
            } else if (mePower != null && mePower.indexOf(",") >= 0) {
                String[] mes = mePower.split(",");
                for (String me : mes) {
                    oiMainEngines.add(me == null ? "" : me.trim());
                }
            } else {
                oiMainEngines.add(mePower == null ? "" : mePower.trim());
            }
            //zxh：处理副机功率信息，形成列表，应对No1: 1020.0 / No2: 1020.0 / No3: 1020.0的老格式
            String gePower = imoStdrpt.getGePower();
            List<String> oiShipGes = new ArrayList<String>();
            if (gePower != null && gePower.indexOf("/") >= 0) {
                String[] ges = gePower.split("/");
                for (String ge : ges) {
                    String g = ge.split(":")[1];
                    if (g != null && !"".equals(g.trim())) {
                        oiShipGes.add(g.trim());
                    }
                }
            } else if (gePower != null && gePower.indexOf(",") >= 0) {
                String[] ges = gePower.split(",");
                for (String ge : ges) {
                    oiShipGes.add(ge == null ? "" : ge.trim());
                }
            } else {
                oiShipGes.add(gePower == null ? "" : gePower.trim());
            }
            reaportImoRptVo.setMainEngines(oiMainEngines);
            reaportImoRptVo.setAuxEngines(oiShipGes);
            ShipType shipType = shipTypeRepository.findShipType(imoStdrpt.getSpType());
            reaportImoRptVo.setShipType(shipType == null ? null : shipType.getImoSptype());
            if("Other".equals( shipType.getImoSptype() )){
                OiShipInfo shipInfo = oiShipInfoRepository.findByIdAndIsDelete(imoStdrpt.getShipId(), 0);
                if(shipInfo != null){
                    reaportImoRptVo.setShipTypeOther(shipInfo.getSptypeOther());
                }
            }
            plusCorrectQty(reaportImoRptVo, imoStdrpt.getId());
            reaportImoRptVos.add(reaportImoRptVo);
        });
        return reaportImoRptVos;
    }

    private void plusCorrectQty(ReaportImoRptVo reaportImoRptVo, String dcsRptId) {
        ImoStdrptAdjust imoStdrptAdjust = imoStdrptAdjustRepository.findByRptIdAndIsDelete(dcsRptId, 0);
        List<ImoOilVo> imoOilVos = reaportImoRptVo.getImoOils();
        if (imoStdrptAdjust == null) {
            return;
        }
        if(imoStdrptAdjust.getDistOnsea() != null){
            reaportImoRptVo.setDistance(reaportImoRptVo.getDistance().add(imoStdrptAdjust.getDistOnsea()));
        }else {
            reaportImoRptVo.setDistance(reaportImoRptVo.getDistance());
        }
        if (imoStdrptAdjust.getImoCorrectionOils() == null) {
            return;
        }
        imoOilVos.forEach(imoOilVo -> {
            for (ImoCorrectionOil imoCorrectionOil : (imoStdrptAdjust.getImoCorrectionOils())) {
                if (imoCorrectionOil.getOilId().equals(imoOilVo.getOilId())) {
                    if(imoCorrectionOil.getFuelTons() != null){
                        imoOilVo.setQuantity(imoOilVo.getQuantity().add(imoCorrectionOil.getFuelTons()));
                    }
                    break;
                }
            }
        });
    }

    private List<String> getLibya(LibyaDownLoadVo libyaDownLoadVo) {
        Date endTime = libyaDownLoadVo.getEndTime();
        List<String> imoRptIds = new ArrayList<>();
        Date startTime = libyaDownLoadVo.getStartTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if ("0".equals(libyaDownLoadVo.getDownLoadType())) {
            imoRptIds = libyaDownLoadVo.getImoRptIds();
            return imoRptIds;
        } else if ("1".equals(libyaDownLoadVo.getDownLoadType())) {
            if (startTime != null && endTime != null) {
                //查询利比亚船旗时间段内的报告
                imoRptIds = imoStdrptRepository.findByAndCountryFlag("LBR", simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));
                log.info(imoRptIds.toString());
            } else {
                imoRptIds = imoStdrptRepository.findByAndCountryFlag("LBR");
            }
        }
        return imoRptIds;
    }

    //    private SingalShipStatVo getSingalShipEnery(List<OiShipTask> oiShipTasks, Date startTime, Date endTime) {
//        BigDecimal oiHfo = new BigDecimal("0");
//        BigDecimal oiLfo = new BigDecimal("0");
//        BigDecimal oiChai = new BigDecimal("0");
//        BigDecimal oiBing = new BigDecimal("0");
//        BigDecimal oiDing = new BigDecimal("0");
//        BigDecimal oiTian = new BigDecimal("0");
//        BigDecimal methanol = new BigDecimal("0");
//        BigDecimal ethanol = new BigDecimal("0");
//        BigDecimal oiHfoStop = new BigDecimal("0");
//        BigDecimal oiLfoStop = new BigDecimal("0");
//        BigDecimal oiChaiStop = new BigDecimal("0");
//        BigDecimal oiBingStop = new BigDecimal("0");
//        BigDecimal oiDingStop = new BigDecimal("0");
//        BigDecimal oiTianStop = new BigDecimal("0");
//        BigDecimal turnOver = new BigDecimal(0);
//        BigDecimal turnOverBox = new BigDecimal(0);
//        BigDecimal allHour = new BigDecimal(0);//总的航行时间
//        BigDecimal eeoi = new BigDecimal(0);
//        BigDecimal loadrate = new BigDecimal(0);
//        BigDecimal dropRatio = new BigDecimal(0);
//        BigDecimal co2PerMile = new BigDecimal(0);
//        BigDecimal co2PerCargo = new BigDecimal(0);
//        BigDecimal oilPerTurnOver = new BigDecimal(0);
//        BigDecimal co2PerCargoTeu = new BigDecimal(0);
//        BigDecimal oilPerTurnOverTeu = new BigDecimal(0);
//        BigDecimal boxRadio = new BigDecimal(0);
//        BigDecimal dwt = new BigDecimal(0);
//        Integer box = 0;//总箱数
//        Integer boxHeavy = 0;//重箱数
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<OiShipVoyage> oiShipVoyages = getOiShipVoyages(oiShipTasks, "IMO");
//        OiShipInfo oiShipInfo = oiShipTasks.get(0).getOiShipInfo();
//        BigDecimal dw = oiShipInfo.getDw();//船舶载重吨
//        String shipId = oiShipInfo.getId();
//        //筛选出没有跨年的航段
//        List<OiShipVoyage> noAcrossVoyage = oiShipVoyages.stream().filter(oiShipVoyage -> {
//            return (oiShipVoyage.getStartTime().getTime() >= startTime.getTime() && oiShipVoyage.getStartTime().getTime() <= endTime.getTime())
//                    && (oiShipVoyage.getEndTime().getTime() >= startTime.getTime() && oiShipVoyage.getEndTime().getTime() <= endTime.getTime());
//        }).collect(Collectors.toList());
//        Calendar instance = Calendar.getInstance();
//        //筛选出跨年的航段
//        List<OiShipVoyage> collectPreYearVoyage = oiShipVoyages.stream().filter(oiShipVoyage -> {
//            return startTime.getTime() > oiShipVoyage.getStartTime().getTime() && startTime.getTime() < oiShipVoyage.getEndTime().getTime();
//        }).collect(Collectors.toList());
//        List<OiShipVoyage> collectPrenextYearVoyage = oiShipVoyages.stream().filter(oiShipVoyage -> {
//            return endTime.getTime() > oiShipVoyage.getStartTime().getTime() && endTime.getTime() < oiShipVoyage.getEndTime().getTime();
//        }).collect(Collectors.toList());
//        //计算期初油量模糊匹配 默认时间加减一分钟
//        instance.setTime(startTime);
//        instance.add(Calendar.MINUTE, -1);
//        Date startMinute = instance.getTime();
//        instance.setTime(startTime);
//        instance.add(Calendar.MINUTE, 1);
//        Date endMinute = instance.getTime();
//        instance.setTime(endTime);
//        instance.add(Calendar.MINUTE, -1);
//        Date endStartMionute = instance.getTime();
//        instance.setTime(endTime);
//        instance.add(Calendar.MINUTE, 1);
//        Date endEndMionute = instance.getTime();
//        List<Map<String, Object>> startBeginOils = beginPeriodOilRepository.findByShipIdAndPeriodTime(shipId, simpleDateFormat.format(startMinute), simpleDateFormat.format(endMinute));
//        List<Map<String, Object>> endBeginOils = beginPeriodOilRepository.findByShipIdAndPeriodTime(shipId, simpleDateFormat.format(endStartMionute), simpleDateFormat.format(endEndMionute));
//        CalculateDcsOilVo startOilCons = getOilCons(startBeginOils);
//        CalculateDcsOilVo endOilCons = getOilCons(endBeginOils);
//        List<OiShipVoyage> acrossYearVoyages = getOiShipVoyages(shipId, simpleDateFormat, collectPreYearVoyage, collectPrenextYearVoyage, startTime, endTime, startOilCons, endOilCons);
//        BigDecimal emptyDistance = noAcrossVoyage.stream().map(OiShipVoyage::getEmptyDistance).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal allDistance = noAcrossVoyage.stream().map(OiShipVoyage::getDistance).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal startNoAcrossYearVoyageDisHour = new BigDecimal(0);
//        BigDecimal startNoAcrossYearVoyageDisTance = new BigDecimal(0);
//        BigDecimal endNoAcrossYearVoyageDisHour = new BigDecimal(0);
//        BigDecimal endNoAcrossYearVoyageDisTance = new BigDecimal(0);
//        if (acrossYearVoyages.size() == 2) {
//            OiShipVoyage oiShipVoyageAcross = acrossYearVoyages.get(0);
//            OiShipVoyage oiShipVoyageNoAcross = acrossYearVoyages.get(1);
//            String endportid = oiShipVoyageAcross.getEndportid();
//            RawVoyagePort endRawPort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
//            List<RawVoyagePortloading> rawVoyagePortloadings = endRawPort.getRawVoyagePortloadings();
//            BigDecimal sailtimeAcross = getSailtime(oiShipVoyageAcross);
//            startNoAcrossYearVoyageDisHour = getSailtime(oiShipVoyageNoAcross);
//            BigDecimal distanceAcrossDistance = oiShipVoyageAcross.getDistance();
//            if (sailtimeAcross.compareTo(BigDecimal.ZERO) > 0) {
//                startNoAcrossYearVoyageDisTance = distanceAcrossDistance.multiply(startNoAcrossYearVoyageDisHour.divide(sailtimeAcross, 2, BigDecimal.ROUND_HALF_UP));
//            }
//            for (int j = 0; j < rawVoyagePortloadings.size(); j++) {
//                RawVoyagePortloading rawVoyagePortloading = rawVoyagePortloadings.get(j);
//                if (rawVoyagePortloading.getLoadingType().equals("1") && rawVoyagePortloading.getCargoTons().compareTo(BigDecimal.ZERO) == 0) {
//                    emptyDistance = emptyDistance.add(startNoAcrossYearVoyageDisTance);
//                }
//            }
//            allDistance = allDistance.add(startNoAcrossYearVoyageDisTance);
//            noAcrossVoyage.add(oiShipVoyageNoAcross);
//        }
//        if (acrossYearVoyages.size() == 4) {
//            OiShipVoyage oiShipVoyageAcross = acrossYearVoyages.get(2);
//            OiShipVoyage oiShipVoyageNoAcross = acrossYearVoyages.get(3);
//            String endportid = oiShipVoyageAcross.getEndportid();
//            RawVoyagePort endRawPort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
//            List<RawVoyagePortloading> rawVoyagePortloadings = endRawPort.getRawVoyagePortloadings();
//            BigDecimal sailtimeAcross = getSailtime(oiShipVoyageAcross);
//            endNoAcrossYearVoyageDisHour = getSailtime(oiShipVoyageNoAcross);
//            BigDecimal distanceAcrossDistance = oiShipVoyageAcross.getDistance();
//            if (sailtimeAcross.compareTo(BigDecimal.ZERO) > 0) {
//                endNoAcrossYearVoyageDisTance = distanceAcrossDistance.multiply(endNoAcrossYearVoyageDisHour.divide(sailtimeAcross, 2, BigDecimal.ROUND_HALF_UP));
//            }
//            for (int j = 0; j < rawVoyagePortloadings.size(); j++) {
//                RawVoyagePortloading rawVoyagePortloading = rawVoyagePortloadings.get(j);
//                if (rawVoyagePortloading.getLoadingType().equals("1") && rawVoyagePortloading.getCargoTons().compareTo(BigDecimal.ZERO) == 0) {
//                    emptyDistance = emptyDistance.add(endNoAcrossYearVoyageDisTance);
//                }
//            }
//            allDistance = allDistance.add(endNoAcrossYearVoyageDisTance);
//            noAcrossVoyage.add(oiShipVoyageNoAcross);
//        }
//        for (int i = 0; i < noAcrossVoyage.size(); i++) {
//            OiShipVoyage oiShipVoyage = noAcrossVoyage.get(i);
//            oiHfo = oiHfo.add(oiShipVoyage.getOiHfo()).add(oiShipVoyage.getStopoiHfo());
//            oiHfoStop = oiHfoStop.add(oiShipVoyage.getStopoiHfo());
//            oiLfo = oiLfo.add(oiShipVoyage.getOiLfo()).add(oiShipVoyage.getStopoiLfo());
//            oiLfoStop = oiLfoStop.add(oiShipVoyage.getStopoiLfo());
//            oiChai = oiChai.add(oiShipVoyage.getOiChai()).add(oiShipVoyage.getStopoiChai());
//            oiChaiStop = oiChaiStop.add(oiShipVoyage.getStopoiChai());
//            oiBing = oiBing.add(oiShipVoyage.getOiBing()).add(oiShipVoyage.getStopoiBing());
//            oiBingStop = oiBingStop.add(oiShipVoyage.getStopoiBing());
//            oiDing = oiDing.add(oiShipVoyage.getOiDing()).add(oiShipVoyage.getStopoiDing());
//            oiDingStop = oiDingStop.add(oiShipVoyage.getStopoiDing());
//            oiTian = oiTian.add(oiShipVoyage.getOiTian()).add(oiShipVoyage.getStopoiTian());
//            oiTianStop = oiTianStop.add(oiShipVoyage.getStopoiTian());
//            methanol = methanol.add(oiShipVoyage.getOiOther()).add(oiShipVoyage.getStopoiOther());
//            ethanol = ethanol.add(oiShipVoyage.getOiethanol()).add(oiShipVoyage.getStopethanol());
//            dwt = dwt.add(oiShipVoyage.getCargo());
//            box += oiShipVoyage.getAllNum();
//            boxHeavy += oiShipVoyage.getHeavyNum();
//            allHour = allHour.add(getSailtime(oiShipVoyage));
//        }
//        SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
//        singalShipStatVo.setOiHfo(oiHfo);
//        singalShipStatVo.setOiLfo(oiLfo);
//        singalShipStatVo.setOiBing(oiBing);
//        singalShipStatVo.setOiDing(oiDing);
//        singalShipStatVo.setOiChai(oiChai);
//        singalShipStatVo.setOiTian(oiTian);
//        singalShipStatVo.setOiHfoStop(oiHfoStop);
//        singalShipStatVo.setOiLfoStop(oiLfoStop);
//        singalShipStatVo.setOiBingStop(oiBingStop);
//        singalShipStatVo.setOiDingStop(oiDingStop);
//        singalShipStatVo.setOiChaiStop(oiChaiStop);
//        singalShipStatVo.setOiTianStop(oiTianStop);
//        singalShipStatVo.setDistance(allDistance);
//        singalShipStatVo.setDistanceEmpty(emptyDistance);
//        singalShipStatVo.setDwt(dwt);
//        BigDecimal eeoiTd = dwt.multiply(allDistance);//运输功
//        turnOver = dwt.multiply(allDistance).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
//        singalShipStatVo.setTurnOver(turnOver);
//        turnOverBox = allDistance.multiply(BigDecimal.valueOf(box)).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
//        singalShipStatVo.setTurnOverBox(turnOverBox);
//        singalShipStatVo.setShipName(oiShipInfo.getSpname());
//        instance.setTime(startTime);
//        singalShipStatVo.setYear(String.valueOf(instance.get(Calendar.YEAR)));
//        //计算二氧化碳排放量
//        BigDecimal zero = BigDecimal.ZERO;
//        OiShipVoyage oiShipVoyageSailing = new OiShipVoyage(oiHfo, oiLfo,
//                oiChai, oiBing, oiDing, oiTian, methanol, ethanol, zero, zero, zero, zero, zero, zero, zero, zero);
//        BigDecimal voyageCo2cost = commonEnergyEfficServiceImpl.getVoyageCo2cost(Arrays.asList(oiShipVoyageSailing));
//        BigDecimal allOil = oiHfo.add(oiLfo).add(oiChai).add(oiBing).add(oiDing).add(oiTian).add(methanol).add(ethanol);
//        if (eeoiTd.compareTo(BigDecimal.ZERO) > 0) {
//            eeoi = voyageCo2cost.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 2, BigDecimal.ROUND_HALF_UP);//计算eeoi
//            oilPerTurnOver = allOil.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 2, BigDecimal.ROUND_HALF_UP);//计算单位周转量能耗
//        }
//        //计算载重量利用率
//        if (dw != null && dw.compareTo(BigDecimal.ZERO) > 0) {
//            loadrate = eeoiTd.divide(dw.multiply(allDistance), 2, BigDecimal.ROUND_HALF_UP);
//        }
//        //计算平均速度
//        BigDecimal avgSpeed = new BigDecimal(0);
//        if (allHour.compareTo(BigDecimal.ZERO) == 0) {
//            avgSpeed = oiShipInfo.getSpeed();
//        } else {
//            avgSpeed = allDistance.divide(allHour, 2, BigDecimal.ROUND_HALF_UP);
//        }
//        //计算降速比
//        BigDecimal serviceSpeed = oiShipInfo.getServiceSpeed();//服务航速
//        if (serviceSpeed == null) {
//            serviceSpeed = oiShipInfo.getSpeed();
//        }
//        if (serviceSpeed.compareTo(BigDecimal.ZERO) > 0) {
//            dropRatio = avgSpeed.multiply(allDistance).divide(serviceSpeed.multiply(allDistance), 2, BigDecimal.ROUND_HALF_UP);
//        }
//        //计算每海里co2排放
//        if (allDistance.compareTo(BigDecimal.ZERO) > 0) {
//            co2PerMile = voyageCo2cost.multiply(BigDecimal.valueOf(1000)).divide(allDistance, 2, BigDecimal.ROUND_HALF_UP);
//        }
//        //计算单位运输量的co2排放
//        if (dwt.compareTo(BigDecimal.ZERO) > 0) {
//            co2PerCargo = voyageCo2cost.multiply(BigDecimal.valueOf(1000)).divide(dwt, 2, BigDecimal.ROUND_HALF_UP);
//        }
//        BigDecimal eeoiTeu = allDistance.multiply(BigDecimal.valueOf(box));
//        if (eeoiTeu.compareTo(BigDecimal.ZERO) > 0) {
//            co2PerCargoTeu = voyageCo2cost.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTeu, 2, BigDecimal.ROUND_HALF_UP); //计算单位运输Teu二氧化碳排放
//            oilPerTurnOverTeu = allOil.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTeu, 2, BigDecimal.ROUND_HALF_UP);//计算单位周转能耗二氧化碳排放
//        }
//        //单位周转量能耗
//        BigDecimal co2PerTeu = new BigDecimal(0);
//        if (box > 0) {
//            co2PerTeu = voyageCo2cost.multiply(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(box), 2, BigDecimal.ROUND_HALF_UP);
//            boxRadio = BigDecimal.valueOf(boxHeavy / box);//计算箱位利用率
//        }
//        singalShipStatVo.setBoxRadio(boxRadio);
//        singalShipStatVo.setEeoi(eeoi);
//        singalShipStatVo.setLoadrate(loadrate);
//        singalShipStatVo.setDropRatio(dropRatio);
//        singalShipStatVo.setCo2PerMile(co2PerMile);
//        singalShipStatVo.setCo2PerCargo(co2PerCargo);
//        singalShipStatVo.setOilPerTurnOver(oilPerTurnOver);
//        singalShipStatVo.setCo2PerCargoTeu(co2PerCargoTeu);
//        singalShipStatVo.setOilPerTurnOverTeu(oilPerTurnOverTeu);
//        singalShipStatVo.setCo2PerTeu(co2PerTeu);
//        return singalShipStatVo;
//    }
    private SingalShipStatVo getSingalShipEnery(List<OiShipVoyage> oiShipVoyages, Date startTime, Date endTime) {
        Calendar instance = Calendar.getInstance();
        BigDecimal oiHfo = new BigDecimal(0);
        BigDecimal oiLfo = new BigDecimal(0);
        BigDecimal oiChai = new BigDecimal(0);
        BigDecimal oiBing = new BigDecimal(0);
        BigDecimal oiDing = new BigDecimal(0);
        BigDecimal oiTian = new BigDecimal(0);
        BigDecimal methanol = new BigDecimal(0);
        BigDecimal ethanol = new BigDecimal(0);
        BigDecimal oiHfoStop = new BigDecimal(0);
        BigDecimal oiLfoStop = new BigDecimal(0);
        BigDecimal oiChaiStop = new BigDecimal(0);
        BigDecimal oiBingStop = new BigDecimal(0);
        BigDecimal oiDingStop = new BigDecimal(0);
        BigDecimal oiTianStop = new BigDecimal(0);
        BigDecimal turnOver = new BigDecimal(0);
        BigDecimal turnOverBox = new BigDecimal(0);
        BigDecimal allHour = new BigDecimal(0);//总的航行时间
        BigDecimal eeoi = new BigDecimal(0);
        BigDecimal loadrate = new BigDecimal(0);
        BigDecimal dropRatio = new BigDecimal(0);
        BigDecimal co2PerMile = new BigDecimal(0);
        BigDecimal co2PerCargo = new BigDecimal(0);
        BigDecimal oilPerTurnOver = new BigDecimal(0);
        BigDecimal co2PerCargoTeu = new BigDecimal(0);
        BigDecimal oilPerTurnOverTeu = new BigDecimal(0);
        BigDecimal boxRadio = new BigDecimal(0);
        BigDecimal dwt = new BigDecimal(0);
        BigDecimal allDistance = new BigDecimal(0);
        BigDecimal emptyDistance = new BigDecimal(0);
        Integer box = 0;//总箱数
        Integer boxHeavy = 0;//重箱数
        OiShipInfo oiShipInfo = oiShipVoyages.get(0).getOiShipInfo();
        BigDecimal dw = oiShipInfo.getDw();//船舶载重吨
        String shipId = oiShipInfo.getId();
        BigDecimal zero = BigDecimal.ZERO;
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            BigDecimal oiHfo1 = oiShipVoyage.getOiHfo();
            if (oiHfo1 == null) {
                oiHfo1 = zero;
            }
            BigDecimal stopoiHfo = oiShipVoyage.getStopoiHfo();
            if (stopoiHfo == null) {
                stopoiHfo = zero;
            }
            BigDecimal oiLfo1 = oiShipVoyage.getOiLfo();
            if (oiLfo1 == null) {
                oiLfo1 = zero;
            }
            BigDecimal stopoiLfo = oiShipVoyage.getStopoiLfo();
            if (stopoiLfo == null) {
                stopoiLfo = zero;
            }
            BigDecimal oiChai1 = oiShipVoyage.getOiChai();
            if (oiChai1 == null) {
                oiChai1 = zero;
            }
            BigDecimal stopoiChai = oiShipVoyage.getStopoiChai();
            if (stopoiChai == null) {
                stopoiChai = zero;
            }
            BigDecimal oiBing1 = oiShipVoyage.getOiBing();
            if (oiBing1 == null) {
                oiBing1 = zero;
            }
            BigDecimal stopoiBing = oiShipVoyage.getStopoiBing();
            if (stopoiBing == null) {
                stopoiBing = zero;
            }
            BigDecimal oiDing1 = oiShipVoyage.getOiDing();
            if (oiDing1 == null) {
                oiDing1 = zero;
            }
            BigDecimal stopoiDing = oiShipVoyage.getStopoiDing();
            if (stopoiDing == null) {
                stopoiDing = zero;
            }
            BigDecimal oiTian1 = oiShipVoyage.getOiTian();
            if (oiTian1 == null) {
                oiTian1 = zero;
            }
            BigDecimal stopoiTian = oiShipVoyage.getStopoiTian();
            if (stopoiTian == null) {
                stopoiTian = zero;
            }
            BigDecimal oiOther = oiShipVoyage.getOiOther();
            if (oiOther == null) {
                oiOther = zero;
            }
            BigDecimal stopoiOther = oiShipVoyage.getStopoiOther();
            if (stopoiOther == null) {
                stopoiOther = zero;
            }
            BigDecimal oiethanol = oiShipVoyage.getOiethanol();
            if (oiethanol == null) {
                oiethanol = zero;
            }
            BigDecimal stopethanol = oiShipVoyage.getStopethanol();
            if (stopethanol == null) {
                stopethanol = zero;
            }
            oiHfo = oiHfo.add(oiHfo1).add(stopoiHfo);
            oiHfoStop = oiHfoStop.add(stopoiHfo);
            oiLfo = oiLfo.add(oiLfo1).add(stopoiLfo);
            oiLfoStop = oiLfoStop.add(stopoiLfo);
            oiChai = oiChai.add(oiChai1).add(stopoiChai);
            oiChaiStop = oiChaiStop.add(stopoiChai);
            oiBing = oiBing.add(oiBing1).add(stopoiBing);
            oiBingStop = oiBingStop.add(stopoiBing);
            oiDing = oiDing.add(oiDing1).add(stopoiDing);
            oiDingStop = oiDingStop.add(stopoiDing);
            oiTian = oiTian.add(oiTian1).add(stopoiTian);
            oiTianStop = oiTianStop.add(stopoiTian);
            methanol = methanol.add(oiOther).add(stopoiOther);
            ethanol = ethanol.add(oiethanol).add(stopethanol);
            BigDecimal cargo = oiShipVoyage.getCargo();
            Integer allNum = oiShipVoyage.getAllNum();
            Integer heavyNum = oiShipVoyage.getHeavyNum();
            if (cargo == null) {
                cargo = zero;
            }
            if (allNum == null) {
                allNum = 0;
            }
            if (heavyNum == null) {
                heavyNum = 0;
            }
            dwt = dwt.add(cargo);
            box += allNum;
            boxHeavy += heavyNum;
            allHour = allHour.add(getSailtime(oiShipVoyage));
            BigDecimal distance = oiShipVoyage.getDistance();
            if (distance == null) {
                distance = new BigDecimal(0);
            }
            allDistance = allDistance.add(distance);
            BigDecimal emptyDistance1 = oiShipVoyage.getEmptyDistance();
            if (emptyDistance1 == null) {
                emptyDistance1 = new BigDecimal(0);
            }
            emptyDistance = emptyDistance.add(emptyDistance1);
        }
        SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
        singalShipStatVo.setOiHfo(oiHfo);
        singalShipStatVo.setOiLfo(oiLfo);
        singalShipStatVo.setOiBing(oiBing);
        singalShipStatVo.setOiDing(oiDing);
        singalShipStatVo.setOiChai(oiChai);
        singalShipStatVo.setOiTian(oiTian);
        singalShipStatVo.setOiHfoStop(oiHfoStop);
        singalShipStatVo.setOiLfoStop(oiLfoStop);
        singalShipStatVo.setOiBingStop(oiBingStop);
        singalShipStatVo.setOiDingStop(oiDingStop);
        singalShipStatVo.setOiChaiStop(oiChaiStop);
        singalShipStatVo.setOiTianStop(oiTianStop);
        singalShipStatVo.setDistance(allDistance);
        singalShipStatVo.setDistanceEmpty(emptyDistance);
        singalShipStatVo.setDwt(dwt);
        BigDecimal eeoiTd = dwt.multiply(allDistance);//运输功
        turnOver = dwt.multiply(allDistance).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
        singalShipStatVo.setTurnOver(turnOver);
        turnOverBox = allDistance.multiply(BigDecimal.valueOf(box)).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP);
        singalShipStatVo.setTurnOverBox(turnOverBox);
        singalShipStatVo.setShipName(oiShipInfo.getSpname());
        instance.setTime(startTime);
        singalShipStatVo.setYear(String.valueOf(instance.get(Calendar.YEAR)));
        //计算二氧化碳排放量
        OiShipVoyage oiShipVoyageSailing = new OiShipVoyage(oiHfo, oiLfo,
                oiChai, oiBing, oiDing, oiTian, methanol, ethanol, zero, zero, zero, zero, zero, zero, zero, zero);
        BigDecimal voyageCo2cost = commonEnergyEfficServiceImpl.getVoyageCo2cost(Arrays.asList(oiShipVoyageSailing));
        BigDecimal allOil = oiHfo.add(oiLfo).add(oiChai).add(oiBing).add(oiDing).add(oiTian).add(methanol).add(ethanol);
        if (eeoiTd.compareTo(BigDecimal.ZERO) > 0) {
            eeoi = voyageCo2cost.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 2, BigDecimal.ROUND_HALF_UP);//计算eeoi
            oilPerTurnOver = allOil.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 2, BigDecimal.ROUND_HALF_UP);//计算单位周转量能耗
        }
        //计算载重量利用率
        if (dw != null && dw.compareTo(BigDecimal.ZERO) > 0) {
            loadrate = eeoiTd.divide(dw.multiply(allDistance), 2, BigDecimal.ROUND_HALF_UP);
        }
        //计算平均速度
        BigDecimal avgSpeed = new BigDecimal(0);
        if (allHour.compareTo(BigDecimal.ZERO) == 0) {
            avgSpeed = oiShipInfo.getSpeed();
        } else {
            avgSpeed = allDistance.divide(allHour, 2, BigDecimal.ROUND_HALF_UP);
        }
        //计算降速比
        BigDecimal serviceSpeed = oiShipInfo.getServiceSpeed();//服务航速
        if (serviceSpeed == null) {
            serviceSpeed = oiShipInfo.getSpeed();
        }
        if (serviceSpeed.compareTo(BigDecimal.ZERO) > 0) {
            dropRatio = avgSpeed.multiply(allDistance).divide(serviceSpeed.multiply(allDistance), 2, BigDecimal.ROUND_HALF_UP);
        }
        //计算每海里co2排放
        if (allDistance.compareTo(BigDecimal.ZERO) > 0) {
            co2PerMile = voyageCo2cost.multiply(BigDecimal.valueOf(1000)).divide(allDistance, 2, BigDecimal.ROUND_HALF_UP);
        }
        //计算单位运输量的co2排放
        if (dwt.compareTo(BigDecimal.ZERO) > 0) {
            co2PerCargo = voyageCo2cost.multiply(BigDecimal.valueOf(1000)).divide(dwt, 2, BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal eeoiTeu = allDistance.multiply(BigDecimal.valueOf(box));
        if (eeoiTeu.compareTo(BigDecimal.ZERO) > 0) {
            co2PerCargoTeu = voyageCo2cost.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTeu, 2, BigDecimal.ROUND_HALF_UP); //计算单位运输Teu二氧化碳排放
            oilPerTurnOverTeu = allOil.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTeu, 2, BigDecimal.ROUND_HALF_UP);//计算单位周转能耗二氧化碳排放
        }
        //单位周转量能耗
        BigDecimal co2PerTeu = new BigDecimal(0);
        if (box > 0) {
            co2PerTeu = voyageCo2cost.multiply(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(box), 2, BigDecimal.ROUND_HALF_UP);
            boxRadio = BigDecimal.valueOf(boxHeavy / box);//计算箱位利用率
        }
        singalShipStatVo.setBoxRadio(boxRadio);
        singalShipStatVo.setEeoi(eeoi);
        singalShipStatVo.setLoadrate(loadrate);
        singalShipStatVo.setDropRatio(dropRatio);
        singalShipStatVo.setCo2PerMile(co2PerMile);
        singalShipStatVo.setCo2PerCargo(co2PerCargo);
        singalShipStatVo.setOilPerTurnOver(oilPerTurnOver);
        singalShipStatVo.setCo2PerCargoTeu(co2PerCargoTeu);
        singalShipStatVo.setOilPerTurnOverTeu(oilPerTurnOverTeu);
        singalShipStatVo.setCo2PerTeu(co2PerTeu);
        return singalShipStatVo;
    }

    private Specification<OiShipVoyage> queryVolageList(EnergyEfficDto energyEfficDto) {
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
            roleNames.add(sysRole.getName());
        });
        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        return new Specification<OiShipVoyage>() {
            @Override
            public Predicate toPredicate(Root<OiShipVoyage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<OiShipVoyage, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                Path<Object> path = root.get("oiShipInfo").get("id");
                CriteriaBuilder.In<Object> in = cb.in(path);
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                if (StringUtils.isNotEmpty(energyEfficDto.getSpTypeCode())) {
                    predicatesAnd.add(cb.equal(join.get("sptype"), energyEfficDto.getSpTypeCode()));
                }
                if (StringUtils.isNotEmpty(energyEfficDto.getShipName())) {
                    predicatesAnd.add(cb.equal(join.get("id"), energyEfficDto.getShipName()));
                }
                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
                    shipIdsByShipCodes.forEach(s -> {
                        in.value(s);
                    });
                    predicatesAnd.add(cb.and(in));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(energyEfficDto.getComPany())) {
                    predicatesOr.add(cb.equal(join.get("docId"), energyEfficDto.getComPany()));
                    predicatesOr.add(cb.equal(join.get("operatorId"), energyEfficDto.getComPany()));
                    predicatesOr.add(cb.equal(join.get("ownerId"), energyEfficDto.getComPany()));
                    predicatesOr.add(cb.equal(join.get("builderId"), energyEfficDto.getComPany()));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                } else {
                    return criteriaQuery.where(and).getRestriction();
                }
            }
        };
    }

    /**
     * 能效趋势查询航次
     *
     * @param energyEfficiencyTrendAnalysisDto
     * @return
     */
    private Specification<OiShipTask> queryEnergyVolageList(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto) {
//        CurrentUserVo currentUser = this.getCurrentUser();
//        List<SysUserRole> sysUserRoles = currentUser.getRoles();
//        List<String> roleNames = new ArrayList<>();
//        sysUserRoles.forEach(sysUserRole -> {
//            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
//            roleNames.add(sysRole.getName());
//        });
//        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        String endYear = energyEfficiencyTrendAnalysisDto.getEndYear();
        String startYear = energyEfficiencyTrendAnalysisDto.getStartYear();
        String grossRange = energyEfficiencyTrendAnalysisDto.getGrossRange();//吨位范围
        String shipAgeRange = energyEfficiencyTrendAnalysisDto.getShipAgeRange();//船范围
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //计算船年龄范围区间
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        Date endTime = null;
        Date startTime = null;
        if (StringUtils.isNotEmpty(shipAgeRange)) {
            instance.add(Calendar.YEAR, -Integer.valueOf(shipAgeRange.split("~")[0]));
            endTime = instance.getTime();
            instance.add(Calendar.YEAR, -Integer.valueOf(shipAgeRange.split("~")[1]));
            startTime = instance.getTime();
        }
        Date finalStartTime = startTime;
        Date finalEndTime = endTime;
        return new Specification<OiShipTask>() {
            @Override
            public Predicate toPredicate(Root<OiShipTask> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<OiShipTask, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                Path<Object> path = root.get("oiShipInfo").get("id");
                CriteriaBuilder.In<Object> in = cb.in(path);
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipTypeCode())) {
                    predicatesAnd.add(cb.equal(join.get("sptype"), energyEfficiencyTrendAnalysisDto.getShipTypeCode()));
                }
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipTypeSub())) {
                    predicatesAnd.add(cb.equal(join.get("sptypeSub"), energyEfficiencyTrendAnalysisDto.getShipTypeSub()));
                }
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipId())) {
                    predicatesAnd.add(cb.equal(join.get("id"), energyEfficiencyTrendAnalysisDto.getShipId()));
                }
                if (StringUtils.isNotEmpty(grossRange)) {
                    predicatesAnd.add(cb.between(join.get("gross"), grossRange.split("~")[0], grossRange.split("~")[1]));
                }
                if (StringUtils.isNotEmpty(shipAgeRange)) {
                    predicatesAnd.add(cb.between(join.get("finish"), finalStartTime, finalEndTime));
                }
                if (StringUtils.isNotEmpty(startYear) && StringUtils.isNotEmpty(endYear)) {
                    try {
                        predicatesAnd.add(cb.between(root.get("startTime"), simpleDateFormat.parse(startYear + "-01-01 00:00:00"), simpleDateFormat.parse(endYear + "-12-31 00:00:00")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        log.error(e.getStackTrace().toString());
                    }
                }
//                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
//                    shipIdsByShipCodes.forEach(s -> {
//                        in.value(s);
//                    });
//                    predicatesAnd.add(cb.and(in));
//                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipCompanyId())) {
                    predicatesOr.add(cb.equal(join.get("docId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
                    predicatesOr.add(cb.equal(join.get("operatorId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
                    predicatesOr.add(cb.equal(join.get("ownerId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
                    predicatesOr.add(cb.equal(join.get("builderId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                } else {
                    return criteriaQuery.where(and).getRestriction();
                }
            }
        };
    }

    /**
     * 能效趋势航段查询
     *
     * @param energyEfficiencyTrendAnalysisDto
     * @return
     */
    private Specification<OiShipVoyage> queryEnergyVoyageList(EnergyEfficiencyTrendAnalysisDto energyEfficiencyTrendAnalysisDto) {
//        CurrentUserVo currentUser = this.getCurrentUser();
//        List<SysUserRole> sysUserRoles = currentUser.getRoles();
//        List<String> roleNames = new ArrayList<>();
//        sysUserRoles.forEach(sysUserRole -> {
//            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
//            roleNames.add(sysRole.getName());
//        });
//        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        String endYear = energyEfficiencyTrendAnalysisDto.getEndYear();
        String startYear = energyEfficiencyTrendAnalysisDto.getStartYear();
        String grossRange = energyEfficiencyTrendAnalysisDto.getGrossRange();//吨位范围
        String shipAgeRange = energyEfficiencyTrendAnalysisDto.getShipAgeRange();//船范围
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //计算船年龄范围区间
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        Date endTime = null;
        Date startTime = null;
        if (StringUtils.isNotEmpty(shipAgeRange)) {
            instance.add(Calendar.YEAR, -Integer.valueOf(shipAgeRange.split("~")[0]));
            endTime = instance.getTime();
            instance.add(Calendar.YEAR, -Integer.valueOf(shipAgeRange.split("~")[1]));
            startTime = instance.getTime();
        }
        Date finalStartTime = startTime;
        Date finalEndTime = endTime;
        return new Specification<OiShipVoyage>() {
            @Override
            public Predicate toPredicate(Root<OiShipVoyage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<OiShipVoyage, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                Path<Object> pathDocId = root.get("oiShipInfo").get("docId");
                Path<Object> pathOperatorId = root.get("oiShipInfo").get("operatorId");
                Path<Object> pathOwnerId = root.get("oiShipInfo").get("ownerId");
                Path<Object> pathBuilderId = root.get("oiShipInfo").get("builderId");
//                CriteriaBuilder.In<Object> in = cb.in(path);
                CriteriaBuilder.In<Object> inDoc = cb.in(pathDocId);
                CriteriaBuilder.In<Object> inOperator = cb.in(pathOperatorId);
                CriteriaBuilder.In<Object> inOwner = cb.in(pathOwnerId);
                CriteriaBuilder.In<Object> inBuilder = cb.in(pathBuilderId);
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipTypeCode())) {
                    predicatesAnd.add(cb.equal(join.get("sptype"), energyEfficiencyTrendAnalysisDto.getShipTypeCode()));
                }
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipTypeSub())) {
                    predicatesAnd.add(cb.equal(join.get("sptypeSub"), energyEfficiencyTrendAnalysisDto.getShipTypeSub()));
                }
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipId())) {
                    predicatesAnd.add(cb.equal(join.get("id"), energyEfficiencyTrendAnalysisDto.getShipId()));
                }
                if (StringUtils.isNotEmpty(grossRange)) {
                    predicatesAnd.add(cb.between(join.get("gross"), BigDecimal.valueOf(Integer.valueOf(grossRange.split("~")[0])), BigDecimal.valueOf(Integer.valueOf(grossRange.split("~")[1]))));
                }
                if (StringUtils.isNotEmpty(shipAgeRange)) {
                    predicatesAnd.add(cb.between(join.get("finish"), finalStartTime, finalEndTime));
                }
                if (StringUtils.isNotEmpty(startYear) && StringUtils.isNotEmpty(endYear)) {
                    try {
                        predicatesAnd.add(cb.between(root.get("startTime"), simpleDateFormat.parse(startYear + "-01-01 00:00:00"), simpleDateFormat.parse(endYear + "-12-31 00:00:00")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        log.error(e.getStackTrace().toString());
                    }
                }
//                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
//                    shipIdsByShipCodes.forEach(s -> {
//                        in.value(s);
//                    });
//                    predicatesAnd.add(cb.and(in));
//                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(energyEfficiencyTrendAnalysisDto.getShipCompanyId())) {
                    GcClient gcClientById = gcClientRepository.findByIdAndIsDelete(energyEfficiencyTrendAnalysisDto.getShipCompanyId());
                    List<String> byIacs = gcClientRepository.findByIacs(gcClientById.getIacs());
                    if (byIacs != null && byIacs.size() != 0) {
                        for (int i = 0; i < byIacs.size(); i++) {
                            String s = byIacs.get(i);
                            inBuilder.value(s);
                            inOperator.value(s);
                            inDoc.value(s);
                            inOwner.value(s);
                        }
                    }
                    predicatesOr.add(cb.and(inDoc));
                    predicatesOr.add(cb.and(inOperator));
                    predicatesOr.add(cb.and(inBuilder));
                    predicatesOr.add(cb.and(inOwner));
//                    predicatesOr.add(cb.equal(join.get("docId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
//                    predicatesOr.add(cb.equal(join.get("operatorId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
//                    predicatesOr.add(cb.equal(join.get("ownerId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
//                    predicatesOr.add(cb.equal(join.get("builderId"), energyEfficiencyTrendAnalysisDto.getShipCompanyId()));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                } else {
                    return criteriaQuery.where(and).getRestriction();
                }
            }
        };
    }

    //    private ResultVo getSingalShip(EnergyEfficDto energyEfficDto, String comPanyName) {
//        String startYear = energyEfficDto.getStartYear();
//        String endYear = energyEfficDto.getEndYear();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<OiShipTask> all = oiShipTaskRepository.findAll(queryVolageList(energyEfficDto));
//        //将集合按照船舶进行分组
//        Map<String, List<OiShipTask>> shipGroupMap = all.stream().collect(Collectors.groupingBy(oiShipTask -> oiShipTask.getShipId()));
//        List<SingalShipStatVo> singalShipStatVos = new ArrayList<>();
//        //划分年度区间
//        int xh = 0;
//        for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
//            try {
//                String start = Integer.valueOf(i) + "-01-01 00:00:00";
//                String end = Integer.valueOf(i) + "-12-31 23:59:00";
//                Date startTime = simpleDateFormat.parse(start);
//                Date endTime = simpleDateFormat.parse(end);
//                //遍历map并根据时间段进行分组
//                for (Map.Entry<String, List<OiShipTask>> entry : shipGroupMap.entrySet()) {
//                    xh++;
//                    List<OiShipTask> oiShipTasks = entry.getValue();
//                    OiShipTask oiShipTaskIndexZEro = oiShipTasks.get(0);
//                    OiShipInfo oiShipInfo = oiShipTaskIndexZEro.getOiShipInfo();
//                    //筛选出符合年份的航次
//                    List<OiShipTask> collect = oiShipTasks.stream().filter(oiShipTask -> {
//                        if (oiShipTask.getStartTime() != null && oiShipTask.getEndTime() != null) {
//                            return (oiShipTask.getStartTime().getTime() >= startTime.getTime() && oiShipTask.getStartTime().getTime() <= endTime.getTime())
//                                    || (oiShipTask.getEndTime().getTime() >= startTime.getTime() && oiShipTask.getEndTime().getTime() <= endTime.getTime());
//                        } else {
//                            return false;
//                        }
//                    }).collect(Collectors.toList());
//                    if (collect.size() > 0) {
//                        Integer count = beginPeriodRepository.findByShipIdAndIsDeleteAndPeriodTimeOrPeriodTime(oiShipInfo.getId(), Integer.valueOf(i) + "-01-01 00:00:00", Integer.valueOf(i + 1) + "-01-01 00:00:00");
//                        if (count < 2) {
//                            SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
//                            singalShipStatVo.setShipName(oiShipInfo.getSpname());
//                            singalShipStatVo.setXh(xh);
//                            singalShipStatVo.setYear(String.valueOf(i));
//                            singalShipStatVos.add(singalShipStatVo);
//                            break;
//                        }
//                        SingalShipStatVo singalShipEnery = getSingalShipEnery(collect, startTime, endTime);
//                        singalShipEnery.setComPanyName(comPanyName);
//                        singalShipEnery.setXh(xh);
//                        singalShipStatVos.add(singalShipEnery);
//                    } else {
//                        SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
//                        singalShipStatVo.setShipName(oiShipInfo.getSpname());
//                        singalShipStatVo.setXh(xh);
//                        singalShipStatVo.setYear(String.valueOf(i));
//                        singalShipStatVos.add(singalShipStatVo);
//                    }
//                }
//                //判断期初量是否存在
//            } catch (ParseException e) {
//                log.error("时间转换错误=={}", Integer.valueOf(startYear) + "-01-01 00:00:00");
//                e.printStackTrace();
//                throw new ExplicitException("时间类型转换错误");
//            }
//        }
//        return new ResultObjectVo<>(singalShipStatVos);
//    }
    private ResultVo getSingalShip(EnergyEfficDto energyEfficDto, String comPanyName) {
        String startYear = energyEfficDto.getStartYear();
        String endYear = energyEfficDto.getEndYear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OiShipVoyage> all = oiShipVoyageRepository.findAll(queryVolageList(energyEfficDto));
        //将集合按照船舶进行分组
        Map<String, List<OiShipVoyage>> shipGroupMap = all.stream().collect(Collectors.groupingBy(oiShipVoyage -> oiShipVoyage.getShipId()));
        List<SingalShipStatVo> singalShipStatVos = new ArrayList<>();
        //划分年度区间
        int xh = 0;
        for (int i = Integer.valueOf(startYear); i <= Integer.valueOf(endYear); i++) {
            try {
                String start = Integer.valueOf(i) + "-01-01 00:00:00";
                String end = Integer.valueOf(i) + "-12-31 23:59:00";
                Date startTime = simpleDateFormat.parse(start);
                Date endTime = simpleDateFormat.parse(end);
                //遍历map并根据时间段进行分组
                for (Map.Entry<String, List<OiShipVoyage>> entry : shipGroupMap.entrySet()) {
                    xh++;
                    List<OiShipVoyage> oiShipVoyages = entry.getValue();
                    OiShipVoyage oiShipVoyageZero = oiShipVoyages.get(0);
                    OiShipInfo oiShipInfo = oiShipVoyageZero.getOiShipInfo();
                    //筛选出符合年份的航次
                    List<OiShipVoyage> collect = oiShipVoyages.stream().filter(oiShipVoyage -> {
                        if (oiShipVoyage.getStartTime() != null && oiShipVoyage.getEndTime() != null) {
                            return (oiShipVoyage.getStartTime().getTime() >= startTime.getTime() && oiShipVoyage.getStartTime().getTime() <= endTime.getTime());
                        } else {
                            return false;
                        }
                    }).collect(Collectors.toList());
                    if (collect.size() > 0) {
                        SingalShipStatVo singalShipEnery = getSingalShipEnery(collect, startTime, endTime);
                        singalShipEnery.setComPanyName(comPanyName);
                        singalShipEnery.setXh(xh);
                        singalShipStatVos.add(singalShipEnery);
                    } else {
                        SingalShipStatVo singalShipStatVo = new SingalShipStatVo();
                        singalShipStatVo.setShipName(oiShipInfo.getSpname());
                        singalShipStatVo.setXh(xh);
                        singalShipStatVo.setYear(String.valueOf(i));
                        singalShipStatVos.add(singalShipStatVo);
                    }
                }
                //判断期初量是否存在
            } catch (ParseException e) {
                log.error("时间转换错误=={}", Integer.valueOf(startYear) + "-01-01 00:00:00");
                e.printStackTrace();
                throw new ExplicitException("时间类型转换错误");
            }
        }
        return new ResultObjectVo<>(singalShipStatVos);
    }

    private List<EneryEfficTrendIndexVo> getEneryEfficTrendIndexVo(List<OiShipVoyage> oiShipVoyages, List<String> indexs) {
        List<EneryEfficTrendIndexVo> eneryEfficTrendIndexVos = new ArrayList<>();
        return eneryEfficTrendIndexVos;
    }

    private Map<String, EneryEfficTrendIndexDataVo> getIndex(List<OiShipVoyage> oiShipVoyages,
                                                             String startTime, String endTime, int j, String type) {
        Map<String, EneryEfficTrendIndexDataVo> map = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal zero = BigDecimal.ZERO;
        //根据时间筛选出符合条件的航段
        List<OiShipVoyage> collect = oiShipVoyages.stream().filter(oiShipVoyage -> {
            try {
                Date startTime1 = oiShipVoyage.getStartTime();
                if (startTime1 == null) {
                    return false;
                } else {
                    return oiShipVoyage.getStartTime().getTime() >= simpleDateFormat.parse(startTime).getTime()
                            && oiShipVoyage.getStartTime().getTime() <= simpleDateFormat.parse(endTime).getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                log.error(e.getStackTrace().toString());
                return false;
            }
        }).collect(Collectors.toList());
        EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoEeoi = new EneryEfficTrendIndexDataVo();//eeoi
        eneryEfficTrendIndexDataVoEeoi.setIndexId("1");
        EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoFuelPerMile = new EneryEfficTrendIndexDataVo();//每海里油耗
        eneryEfficTrendIndexDataVoFuelPerMile.setIndexId("2");
        EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoFuelPerT = new EneryEfficTrendIndexDataVo();//每运输功油耗
        eneryEfficTrendIndexDataVoFuelPerT.setIndexId("3");
        EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoCo2PerMile = new EneryEfficTrendIndexDataVo();//每海里二氧化碳排放
        eneryEfficTrendIndexDataVoCo2PerMile.setIndexId("4");
        EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoCo2PerT = new EneryEfficTrendIndexDataVo();//每运输单位二氧化碳排放
        eneryEfficTrendIndexDataVoCo2PerT.setIndexId("5");
        EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoDropRadio = new EneryEfficTrendIndexDataVo();//降速比
        eneryEfficTrendIndexDataVoDropRadio.setIndexId("6");
        EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVoUserRate = new EneryEfficTrendIndexDataVo();//载货量利用率
        eneryEfficTrendIndexDataVoUserRate.setIndexId("7");
        BigDecimal distanceSum = new BigDecimal(0);
        BigDecimal cargoSum = new BigDecimal(0);
        BigDecimal co2Cost = new BigDecimal(0);
        BigDecimal oiHfoSum = new BigDecimal(0);
        BigDecimal userate = new BigDecimal(0);//载货量利用率
        BigDecimal speedrate = new BigDecimal(0);//降速比
        BigDecimal eeoiTd = new BigDecimal(0);
        for (int i = 0; i < collect.size(); i++) {
            OiShipVoyage oiShipVoyage = collect.get(i);
            OiShipInfo oiShipInfo = oiShipVoyage.getOiShipInfo();
            BigDecimal speed = oiShipInfo.getSpeed();
            //计算降速比
            BigDecimal voyageSpeedDistance = commonEnergyEfficServiceImpl.getVoyageSpeedDistance(Arrays.asList());
            BigDecimal voyageserviceSpeedDistance = commonEnergyEfficServiceImpl.getVoyageserviceSpeedDistance(Arrays.asList(oiShipVoyage), oiShipInfo.getServiceSpeed() == null ? speed : oiShipInfo.getServiceSpeed());
            BigDecimal speedRate = voyageserviceSpeedDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : voyageSpeedDistance.divide(voyageserviceSpeedDistance, 2, BigDecimal.ROUND_HALF_UP);
            speedrate = speedrate.add(speedRate);
            //计算载货量利用率
            BigDecimal voyageEeoiTd = commonEnergyEfficServiceImpl.getVoyageEeoiTd(Arrays.asList(oiShipVoyage)).get("eeoiTd");
            BigDecimal getdwtDistance = commonEnergyEfficServiceImpl.getdwtDistance(Arrays.asList(oiShipVoyage), oiShipInfo.getDw());
            BigDecimal useRate = getdwtDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0) : voyageEeoiTd.divide(getdwtDistance, 2, BigDecimal.ROUND_HALF_UP);
            userate = userate.add(useRate);
            BigDecimal distance = oiShipVoyage.getDistance();
            BigDecimal cargo = oiShipVoyage.getCargo();
            BigDecimal co2 = oiShipVoyage.getCo2Cost();
            BigDecimal oiHfo = oiShipVoyage.getOiHfo();
            if (distance == null) {
                distance = zero;
            }
            if (cargo == null) {
                cargo = zero;
            }
            if (co2 == null) {
                co2 = zero;
            }
            if (oiHfo == null) {
                oiHfo = zero;
            }
            eeoiTd = eeoiTd.add(cargo.multiply(distance));
            oiHfoSum = oiHfoSum.add(oiHfo);
            distanceSum = distanceSum.add(distance);
            cargoSum = cargoSum.add(cargo);
            co2Cost = co2Cost.add(co2);
        }
        //计算eeoi
        BigDecimal eeoi = new BigDecimal(0);
        BigDecimal fuelPerMile = new BigDecimal(0);
        BigDecimal fuelPerCargoMile = new BigDecimal(0);//每运输功油耗
        BigDecimal co2PerMile = new BigDecimal(0);//每海里co2排放
        BigDecimal co2PerT = new BigDecimal(0);//每运输单位co2排放
        if (eeoiTd.compareTo(BigDecimal.ZERO) > 0) {
            eeoi = co2Cost.multiply(BigDecimal.valueOf(1000000)).divide(eeoiTd, 2, BigDecimal.ROUND_HALF_UP);
            fuelPerMile = oiHfoSum.divide(distanceSum, 2, BigDecimal.ROUND_HALF_UP);
            fuelPerCargoMile = oiHfoSum.multiply(BigDecimal.valueOf(1000)).divide(
                    eeoiTd.divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP), 2, BigDecimal.ROUND_HALF_UP);
            co2PerMile = co2Cost.multiply(BigDecimal.valueOf(10)).divide(distanceSum, 2, BigDecimal.ROUND_HALF_UP);
            co2PerT = co2Cost.multiply(BigDecimal.valueOf(100)).divide(cargoSum, 2, BigDecimal.ROUND_HALF_UP);
        }
        eneryEfficTrendIndexDataVoEeoi.setData(eeoi);
        eneryEfficTrendIndexDataVoEeoi.setName("EEOI(g/t∙nm)");
        //每海里油耗
        eneryEfficTrendIndexDataVoFuelPerMile.setData(fuelPerMile);
        eneryEfficTrendIndexDataVoFuelPerMile.setName("每海里油耗(kg/nm)");
        //每运输功油耗
        eneryEfficTrendIndexDataVoFuelPerT.setData(fuelPerCargoMile);
        eneryEfficTrendIndexDataVoFuelPerT.setName("每运输功油耗(kg/kt∙nm)");
        //每运海里co2排放
        eneryEfficTrendIndexDataVoCo2PerMile.setData(co2PerMile);
        eneryEfficTrendIndexDataVoCo2PerMile.setName("每海里CO2排放(100kg/nm)");
        //每运输单位co2排放
        eneryEfficTrendIndexDataVoCo2PerT.setData(co2PerT);
        eneryEfficTrendIndexDataVoCo2PerT.setName("每运输单位CO2排放(10kg/t)");
        //降速比
        if (collect.size() == 0) {
            eneryEfficTrendIndexDataVoDropRadio.setData(zero);
        } else {
            eneryEfficTrendIndexDataVoDropRadio.setData(speedrate.multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(collect.size()), 2, BigDecimal.ROUND_HALF_UP));

        }
        eneryEfficTrendIndexDataVoDropRadio.setName("降速比(%)");
        //载货量利用率
        if (collect.size() == 0) {
            eneryEfficTrendIndexDataVoUserRate.setData(zero);
        } else {
            eneryEfficTrendIndexDataVoUserRate.setData(userate.multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(collect.size()), 2, BigDecimal.ROUND_HALF_UP));
        }
        eneryEfficTrendIndexDataVoUserRate.setName("载货量利用率(%)");
        String time = null;
        if ("月".equals(type)) {
            time = startTime.substring(0, 4) + "." + j;
        } else if ("季".equals(type)) {
            time = startTime.substring(0, 4) + "年第" + j + "季度";
        } else {
            time = startTime.substring(0, 4);
        }
        eneryEfficTrendIndexDataVoEeoi.setTimeOrVoyage(time);
        eneryEfficTrendIndexDataVoFuelPerMile.setTimeOrVoyage(time);
        eneryEfficTrendIndexDataVoFuelPerT.setTimeOrVoyage(time);
        eneryEfficTrendIndexDataVoCo2PerMile.setTimeOrVoyage(time);
        eneryEfficTrendIndexDataVoCo2PerT.setTimeOrVoyage(time);
        eneryEfficTrendIndexDataVoDropRadio.setTimeOrVoyage(time);
        eneryEfficTrendIndexDataVoUserRate.setTimeOrVoyage(time);
        map.put("1", eneryEfficTrendIndexDataVoEeoi);
        map.put("2", eneryEfficTrendIndexDataVoFuelPerMile);
        map.put("3", eneryEfficTrendIndexDataVoFuelPerT);
        map.put("4", eneryEfficTrendIndexDataVoCo2PerMile);
        map.put("5", eneryEfficTrendIndexDataVoCo2PerT);
        map.put("6", eneryEfficTrendIndexDataVoDropRadio);
        map.put("7", eneryEfficTrendIndexDataVoUserRate);
        return map;
    }

    private Map<String, Object> getXdataAndYdata(List<EneryEfficTrendIndexDataVo> eneryEfficTrendIndexDataVos) {
        List<String> xData = new ArrayList<>();
        List<BigDecimal> yData = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < eneryEfficTrendIndexDataVos.size(); i++) {
            EneryEfficTrendIndexDataVo eneryEfficTrendIndexDataVo = eneryEfficTrendIndexDataVos.get(i);
            xData.add(eneryEfficTrendIndexDataVo.getTimeOrVoyage());
            yData.add(eneryEfficTrendIndexDataVo.getData());
        }
        map.put("xData", xData);
        map.put("yData", yData);
        return map;
    }

    private OiShipVoyage getOiShipVoyageStopPort(Date preYearStartTime, CalculateDcsOilVo startOilCons, Date preYearEndTime, RawVoyagePort endPreYear) {
        OiShipVoyage oiShipVoyagePre;
        oiShipVoyagePre = new OiShipVoyage();
        CalculateOilVo calculateOilVoStop = getStopPortOil(endPreYear, preYearStartTime, preYearEndTime, startOilCons);
        BigDecimal zero = BigDecimal.ZERO;
        oiShipVoyagePre.setOiHfo(zero);
        oiShipVoyagePre.setOiLfo(zero);
        oiShipVoyagePre.setOiBing(zero);
        oiShipVoyagePre.setOiDing(zero);
        oiShipVoyagePre.setOiChai(zero);
        oiShipVoyagePre.setOiOther(zero);
        oiShipVoyagePre.setOiethanol(zero);
        oiShipVoyagePre.setOiTian(zero);
        oiShipVoyagePre.setStopoiHfo(calculateOilVoStop.getOiHfo());
        oiShipVoyagePre.setStopoiLfo(calculateOilVoStop.getOiLfo());
        oiShipVoyagePre.setStopoiChai(calculateOilVoStop.getOiChai());
        oiShipVoyagePre.setStopoiBing(calculateOilVoStop.getOiBing());
        oiShipVoyagePre.setStopoiDing(calculateOilVoStop.getOiDing());
        oiShipVoyagePre.setStopoiOther(calculateOilVoStop.getOiOther());
        oiShipVoyagePre.setStopoiTian(calculateOilVoStop.getOiTian());
        oiShipVoyagePre.setStopethanol(calculateOilVoStop.getOiethanol());
        oiShipVoyagePre.setDistance(zero);
        oiShipVoyagePre.setStartTime(preYearStartTime);
        oiShipVoyagePre.setEndTime(preYearEndTime);
        long h = 1000 * 60;
        BigDecimal stopTime = BigDecimal.valueOf((double) (preYearEndTime.getTime() - preYearStartTime.getTime()) / h);
        oiShipVoyagePre.setStopTime(stopTime);
        return oiShipVoyagePre;
    }

    /**
     * 计算跨年港口的停港油耗
     *
     * @param rawVoyagePort
     * @return
     */
    private CalculateOilVo getStopPortOil(RawVoyagePort rawVoyagePort, Date preYearStartTime, Date preYearEndTime, CalculateDcsOilVo calculateDcsOilVo) {
        CalculateOilVo calculateOilVo = new CalculateOilVo();//停港油耗
        List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();
        BigDecimal arrZone = rawVoyagePort.getArrZone();
        //筛选出时间段内的加油，驳油
        List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();
        rawVoyageAddoils = rawVoyageAddoils.stream().filter(rawVoyageAddoil -> {
            Date addTm = rawVoyageAddoil.getAddTm();
            long time = 0;
            if (addTm != null) {
                Date date = DateUtils.localToUTC(addTm, arrZone);
                time = date.getTime();
            }
            long timeStart = preYearStartTime.getTime();
            long timeEnd = preYearEndTime.getTime();
            return time >= timeStart && time <= timeEnd;
        }).collect(Collectors.toList());
        List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();
        rawVoyageOutoils = rawVoyageOutoils.stream().filter(rawVoyageOutoil -> {
            Date outTm = rawVoyageOutoil.getOutTm();
            long time = 0;
            if (outTm != null) {
                Date date = DateUtils.localToUTC(outTm, arrZone);
                time = date.getTime();
            }
            long timeStart = preYearStartTime.getTime();
            long timeEnd = preYearEndTime.getTime();
            return time >= timeStart && time <= timeEnd;
        }).collect(Collectors.toList());
        cn.ccsit.eeoi.common.vo.CalculateOilVo addOil = getAddOil(rawVoyageAddoils);//获取加油量
        cn.ccsit.eeoi.common.vo.CalculateOilVo outOil = getOutOil(rawVoyageOutoils);//获取驳油量
        cn.ccsit.eeoi.common.vo.CalculateOilVo arr = calculateArrAndDept(rawVoyagePortoils).get("arr");//获取离港存量
        BigDecimal hfo = calculateDcsOilVo.getHfoHight().add(calculateDcsOilVo.getHfoLow()).add(calculateDcsOilVo.getHfoSuperLow())
                .add(addOil.getOiHfo()).subtract(outOil.getOiHfo()).subtract(arr.getOiHfo());
        BigDecimal lfo = calculateDcsOilVo.getLfoHight().add(calculateDcsOilVo.getLfoLow()).add(calculateDcsOilVo.getLfoSuperLow())
                .add(addOil.getOiLfo()).subtract(outOil.getOiLfo()).subtract(arr.getOiLfo());
        BigDecimal chai = calculateDcsOilVo.getChaiFo().add(addOil.getOiChai()).subtract(outOil.getOiChai()).subtract(arr.getOiChai());
        BigDecimal propane = calculateDcsOilVo.getPropane().add(addOil.getOiBing()).subtract(outOil.getOiBing()).subtract(arr.getOiBing());
        BigDecimal butane = calculateDcsOilVo.getButane().add(addOil.getOiDing()).subtract(outOil.getOiDing()).subtract(arr.getOiDing());
        BigDecimal methanl = calculateDcsOilVo.getMethanol().add(addOil.getOiOther()).subtract(outOil.getOiOther()).subtract(arr.getOiOther());
        BigDecimal etnal = calculateDcsOilVo.getEthanol().add(addOil.getOiethanol()).subtract(outOil.getOiethanol()).subtract(arr.getOiethanol());
        BigDecimal oiTian = calculateDcsOilVo.getLng().add(addOil.getOiTian()).subtract(outOil.getOiTian()).subtract(arr.getOiTian());
        calculateOilVo.setOiHfo(hfo);
        calculateOilVo.setOiLfo(lfo);
        calculateOilVo.setOiOther(methanl);
        calculateOilVo.setOiethanol(etnal);
        calculateOilVo.setOiTian(oiTian);
        calculateOilVo.setOiDing(butane);
        calculateOilVo.setOiBing(propane);
        calculateOilVo.setOiChai(chai);
        return calculateOilVo;
    }

    /**
     * 计算抛锚的停港油耗
     *
     * @param rawVoyagePort
     * @param preYearStartTime
     * @param preYearEndTime
     * @param calculateDcsOilVo
     * @return
     */
    private CalculateOilVo getStopPortOilAnar(RawVoyagePort rawVoyagePort, Date preYearStartTime, Date preYearEndTime, CalculateDcsOilVo calculateDcsOilVo) {
        CalculateOilVo calculateOilVo = new CalculateOilVo();//停港油耗
        List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();
        BigDecimal arrZone = rawVoyagePort.getArrZone();
        //筛选出时间段内的加油，驳油
        List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();
        rawVoyageAddoils = rawVoyageAddoils.stream().filter(rawVoyageAddoil -> {
            Date addTm = rawVoyageAddoil.getAddTm();
            long time = 0;
            if (addTm != null) {
                Date date = DateUtils.localToUTC(addTm, arrZone);
                time = date.getTime();
            }
            long timeStart = preYearStartTime.getTime();
            long timeEnd = preYearEndTime.getTime();
            return time >= timeStart && time <= timeEnd;
        }).collect(Collectors.toList());
        List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();
        rawVoyageOutoils = rawVoyageOutoils.stream().filter(rawVoyageOutoil -> {
            Date outTm = rawVoyageOutoil.getOutTm();
            long time = 0;
            if (outTm != null) {
                Date date = DateUtils.localToUTC(outTm, arrZone);
                time = date.getTime();
            }
            long timeStart = preYearStartTime.getTime();
            long timeEnd = preYearEndTime.getTime();
            return time >= timeStart && time <= timeEnd;
        }).collect(Collectors.toList());
        cn.ccsit.eeoi.common.vo.CalculateOilVo addOil = getAddOil(rawVoyageAddoils);//获取加油量
        cn.ccsit.eeoi.common.vo.CalculateOilVo outOil = getOutOil(rawVoyageOutoils);//获取驳油量
        cn.ccsit.eeoi.common.vo.CalculateOilVo arr = calculateArrAndDept(rawVoyagePortoils).get("arr");//获取离港存量
        BigDecimal hfo = arr.getOiHfo().subtract(calculateDcsOilVo.getHfoHight()).subtract(calculateDcsOilVo.getHfoLow()).
                subtract(calculateDcsOilVo.getHfoSuperLow()).add(addOil.getOiHfo()).subtract(outOil.getOiHfo());
        BigDecimal lfo = arr.getOiLfo().subtract(calculateDcsOilVo.getLfoHight()).subtract(calculateDcsOilVo.getLfoLow()).
                subtract(calculateDcsOilVo.getLfoSuperLow()).add(addOil.getOiLfo()).subtract(outOil.getOiLfo());
        BigDecimal chai = arr.getOiChai().add(addOil.getOiChai()).subtract(outOil.getOiChai()).subtract(calculateDcsOilVo.getChaiFo());
        BigDecimal oiTian = arr.getOiTian().add(addOil.getOiTian()).subtract(outOil.getOiTian()).subtract(calculateDcsOilVo.getLng());
        BigDecimal propane = arr.getOiBing().add(addOil.getOiBing()).subtract(outOil.getOiBing()).subtract(calculateDcsOilVo.getPropane());
        BigDecimal butane = arr.getOiDing().add(addOil.getOiDing()).subtract(outOil.getOiDing()).subtract(calculateDcsOilVo.getButane());
        calculateOilVo.setOiHfo(hfo);
        calculateOilVo.setOiLfo(lfo);
//        calculateOilVo.setOiOther(methanl);
//        calculateOilVo.setOiethanol(etnal);
        calculateOilVo.setOiTian(oiTian);
        calculateOilVo.setOiDing(butane);
        calculateOilVo.setOiBing(propane);
        calculateOilVo.setOiChai(chai);
        return calculateOilVo;
    }

    /**
     * 转化加油和驳油驳油渣时间
     *
     * @param portInfoId 获取港口的抵港时区
     * @param time       要转化的时间
     * @return
     */
    private Date coverAddTime(String portInfoId, Date time) {
        RawVoyagePort rawVoyagePort = rawVoyagePortRepository.findByIdAndIsDelete(portInfoId, 0);
        BigDecimal arrZone = rawVoyagePort.getArrZone();
        return DateUtils.localToUTC(time, arrZone);
    }

    private Map<String, Date> getUtcTimeByPort(RawVoyagePort rawVoyagePort) {
        Map<String, Date> map = new HashMap<>();
        Date arrTm = rawVoyagePort.getArrTm();
        BigDecimal arrZone = rawVoyagePort.getArrZone();
        Date deptTm = rawVoyagePort.getDeptTm();
        BigDecimal deptZone = rawVoyagePort.getDeptZone();
        Date dateArr = DateUtils.localToUTC(arrTm, arrZone);
        Date dateDept = DateUtils.localToUTC(deptTm, deptZone);
        map.put("arr", dateArr);
        map.put("dept", dateDept);
        return map;
    }
}
