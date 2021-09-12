package cn.ccsit.eeoi.energyeefic.service;

import cn.ccsit.common.constant.FuelConst;
import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.utils.*;
import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.vo.CurrentUserVo;
import cn.ccsit.eeoi.dictionary.entity.Fuel;
import cn.ccsit.eeoi.dictionary.entity.GcState;
import cn.ccsit.eeoi.dictionary.entity.ShipType;
import cn.ccsit.eeoi.dictionary.repository.FuelRepository;
import cn.ccsit.eeoi.dictionary.repository.GcStateRepository;
import cn.ccsit.eeoi.dictionary.repository.ShipTypeRepository;
import cn.ccsit.eeoi.energyeefic.dto.*;
import cn.ccsit.eeoi.energyeefic.entity.*;
import cn.ccsit.eeoi.energyeefic.properties.FuelConsMethodProperties;
import cn.ccsit.eeoi.energyeefic.repository.*;
import cn.ccsit.eeoi.energyeefic.utils.BeanCopyUtil;
import cn.ccsit.eeoi.energyeefic.vo.*;
import cn.ccsit.eeoi.ship.entity.*;
import cn.ccsit.eeoi.ship.repository.GcClientRepository;
import cn.ccsit.eeoi.ship.repository.OiShipInfoRepository;
import cn.ccsit.eeoi.ship.repository.ShipEquipmentFuelMapRepository;
import cn.ccsit.eeoi.system.entity.OiShipChglog;
import cn.ccsit.eeoi.system.entity.SysDict;
import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.entity.SysUserRole;
import cn.ccsit.eeoi.system.repository.OiShipChglogRepository;
import cn.ccsit.eeoi.system.repository.SysDictRepository;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.input.BrokenReader;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Past;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnergyEefficServiceImpl extends CommonEnergyEfficServiceImpl implements EnergyEefficService {
    @Autowired
    private OiShipTaskRepository oiShipTaskRepository;
    @Autowired
    private OiShipVoyageRepository oiShipVoyageRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private RawVoyagePortRepository rawVoyagePortRepository;
    @Autowired
    private OiShipInfoRepository oiShipInfoRepository;
    @Autowired
    private RawVoyagePortoilRepository rawVoyagePortoilRepository;
    @Autowired
    private RawVoyagePortloadingRepository rawVoyagePortloadingRepository;
    @Autowired
    private RawVoyageSpecRepository rawVoyageSpecRepository;
    @Autowired
    private RawVoyageSpecConsRepository rawVoyageSpecConsRepository;
    @Autowired
    private SysDictRepository sysDictRepository;
    @Autowired
    private GcClientRepository gcClientRepository;
    @Autowired
    private FuelRepository fuelRepository;
    @Autowired
    private InterfaceRepository interfaceRepository;
    @Autowired
    private RawVoyageAddoilRepository rawVoyageAddoilRepository;
    @Autowired
    private RawVoyageOutoilRepository rawVoyageOutoilRepository;
    @Autowired
    private RawVoyageSludgeoilRepository rawVoyageSludgeoilRepository;
    @Autowired
    private ShipEquipmentFuelMapRepository shipEquipmentFuelMapRepository;
    @Autowired
    private BeginPeriodRepository beginPeriodRepository;
    @Autowired
    private BeginPeriodOilRepository beginPeriodOilRepository;
    @Autowired
    private OiShipChglogRepository oiShipChglogRepository;
    @Autowired
    private ShipTypeRepository shipTypeRepository;
    @Autowired
    private FuelConsMethodProperties fuelConsMethodProperties;
    @Autowired
    private CmsaRptRepository cmsaRptRepository;
    @Autowired
    private CmsaRptOilConsRepository cmsaRptOilConsRepository;
    @Autowired
    private GcStateRepository gcStateRepository;
    @Autowired
    private OiTaskNotifyRepository oiTaskNotifyRepository;
    private final static String STOP_PORT_OIL = "stop";
    private final static String SAILING_OIL = "sailing";
    private final static String ICE_PORT_OIL = "ice";
    private final static String RSECUE_OIL = "rsecue";
    public static final String LICENSE_KEY = "A5483FA7";
    public static final String PERMISSION_CODE = "0,1,2";
    public static final String MRV_DATA = "mrvDataVo";
    public static final String REAPORT_DTO = "reaportDtos";
    /**
     * 客船和客滚船类型
     */
    private final static String RAWPX_PASS_SHIP_TYPE = "ropa";

    @Override
    public ResultVo getVolageList(VoyageListDto voyageListDto) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "oiShipInfo.spname"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "startTime"));
        Page<OiShipTask> all = oiShipTaskRepository.findAll(queryVolageList(voyageListDto),
                PageRequest.of(voyageListDto.getCurrentPage() - 1, voyageListDto.getPageSize(),
                        Sort.by(orders)));
        List<OiShipTask> content = all.getContent();
        List<VoyageListVo> voyageListVos = new ArrayList<>();
        List<OiShipTask> oiShipTasks = new ArrayList<>(content);
        oiShipTasks.forEach(oiShipTask -> {
            OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();
            String startPort = oiShipTask.getStartPort();
            String destPort = oiShipTask.getDestPort();
            RawVoyagePort startPortRaw = rawVoyagePortRepository.findByIdAndIsDelete(startPort, 0);
            RawVoyagePort destPortRaw = rawVoyagePortRepository.findByIdAndIsDelete(destPort, 0);
            VoyageListVo voyageListVo = new VoyageListVo();
            voyageListVo.setId(oiShipTask.getId());
            voyageListVo.setVolageCode(oiShipTask.getTask());
            voyageListVo.setImo(oiShipInfo.getImono());
            voyageListVo.setRegisterNo(oiShipInfo.getRegisterno());
            voyageListVo.setShipNameCn(oiShipInfo.getSpnameCn());
            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
            voyageListVo.setStartTime(oiShipTask.getStartTime());
            voyageListVo.setEndTime(oiShipTask.getEndTime());
            voyageListVo.setStartPortEn(oiShipTask.getStartPort());
            if (destPortRaw != null) {
                voyageListVo.setEndPortEn(destPortRaw.getPorten());
            } else {
                voyageListVo.setEndPortEn(destPort);
            }
            if (startPortRaw != null) {
                voyageListVo.setStartPortEn(startPortRaw.getPorten());
            } else {
                voyageListVo.setStartPortEn(startPort);
            }
            voyageListVo.setEeoiValue(oiShipTask.getEeoiValue());
            voyageListVo.setCarbonDioxideEmission(oiShipTask.getEeoiFc());
            voyageListVo.setDropRatio(oiShipTask.getSpeedrate());
            voyageListVo.setCapacityUtilization(oiShipTask.getUserate());
            voyageListVo.setRecStatus(oiShipTask.getRecStatus());
            voyageListVo.setVolageAndSegmentType(0);
            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
            voyageListVo.setShipId(oiShipInfo.getId());
            voyageListVo.setEeoiTd(oiShipTask.getEeoiTd());
            voyageListVo.setDistance(oiShipTask.getDistance());
            voyageListVo.setVoyageFlag(0);
            BigDecimal voyageDistanceTime = new BigDecimal(0);

            List<SegmentsVo> segmentsVos = new ArrayList<>();
            List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
            oiShipVoyages.sort((x, y) -> {
                if (x.getVoyType() != null && y.getVoyType() != null) {
                    if (x.getVoyType() == y.getVoyType()) {
                        if (StringUtils.isNotEmpty(x.getVoyageno()) && StringUtils.isNotEmpty(y.getVoyageno())) {
                            return Integer.valueOf(x.getVoyageno()) - Integer.valueOf(y.getVoyageno());
                        } else {
                            return -1;
                        }
                    }
                    return x.getVoyType() - y.getVoyType();
                } else {
                    return -1;
                }
            });
//            if ((oiShipTask.getStartPort() == null || oiShipTask.getStartPort().isEmpty()) && oiShipVoyages != null && oiShipVoyages.size() > 0) {
//                voyageListVo.setStartPortEn(oiShipVoyages.get(0).getStartporten());
//            }
//            if ((oiShipTask.getDestPort() == null || oiShipTask.getDestPort().isEmpty()) && oiShipVoyages != null && oiShipVoyages.size() > 0) {
//                voyageListVo.setEndPortEn(oiShipVoyages.get(oiShipVoyages.size() - 1).getEndporten());
//            }
            for (int i = 0; i < oiShipVoyages.size(); i++) {
                OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);

                List<RawVoyagePort> rawVoyagePorts = new ArrayList<>();
                SegmentsVo segmentsVo = new SegmentsVo();
                segmentsVo.setId(oiShipVoyage.getId());
                segmentsVo.setImo(oiShipInfo.getImono());
                segmentsVo.setRegisterNo(oiShipInfo.getRegisterno());
                segmentsVo.setVolageCode(oiShipVoyage.getVoyageno());
                segmentsVo.setShipNameCn(oiShipInfo.getSpnameCn());
                segmentsVo.setShipNameEn(oiShipInfo.getSpname());
                segmentsVo.setStartTime(oiShipVoyage.getStartTime());
                segmentsVo.setEndTime(oiShipVoyage.getEndTime());
                segmentsVo.setEeoiValue(BigDecimal.ZERO);
                segmentsVo.setCapacityUtilization(BigDecimal.ZERO);
                segmentsVo.setCarbonDioxideEmission(BigDecimal.ZERO);
                segmentsVo.setDropRatio(BigDecimal.ZERO);
                segmentsVo.setRecStatus(oiShipTask.getRecStatus());
                segmentsVo.setVoyType(oiShipVoyage.getVoyType());
                segmentsVo.setVolageAndSegmentType(1);
                segmentsVo.setStartPort(oiShipVoyage.getStartportcn());
                segmentsVo.setEndPort(oiShipVoyage.getEndportcn());
                segmentsVo.setHfo(oiShipVoyage.getOiHfo());
                segmentsVo.setChaiOil(oiShipVoyage.getOiChai());
                segmentsVo.setCargo(oiShipVoyage.getCargo());
                segmentsVo.setBallest(oiShipVoyage.getBallast());
                segmentsVo.setStartPortEn(oiShipVoyage.getStartporten());
                segmentsVo.setEndPortEn(oiShipVoyage.getEndporten());
                segmentsVo.setDistance(oiShipVoyage.getDistance());
                segmentsVo.setStopTime(oiShipVoyage.getStopTime());
                segmentsVo.setDistanceTime(getSailtime(oiShipVoyage));
                segmentsVo.setVoyageFlag(1);
                voyageDistanceTime = voyageDistanceTime.add(segmentsVo.getDistanceTime());
//                if (oiShipVoyage.getStartPort() != null && oiShipVoyage.getEndPort() != null) {
//                    rawVoyagePorts.add(oiShipVoyage.getStartPort());
//                    rawVoyagePorts.add(oiShipVoyage.getEndPort());
//                }
//                List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
//                segmentsVo.setVoyagePortVos(voyagePortVos);
                segmentsVos.add(segmentsVo);

            }
            voyageListVo.setChildren(segmentsVos);
            voyageListVo.setHasChildren(segmentsVos.size() == 0 ? false : true);
            voyageListVo.setDistanceHour(voyageDistanceTime);
//            List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
//            if (rawVoyagePorts.size() >= 1) {
//                voyageListVo.setStartPortEn(rawVoyagePorts.get(0).getPorten());
//                voyageListVo.setEndPortEn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPorten());
//                voyageListVo.setStartPortCn(rawVoyagePorts.get(0).getPortcn());
//                voyageListVo.setEndPortCn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPortcn());
//            }
//            List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
//            rawVoyagePorts.addAll(rawVoyagePortsNext);
//            rawVoyagePorts.sort((x, y) -> {
//                Date arrTmX = x.getArrTm();
//                Date arrTmY = y.getArrTm();
//                Date createTmX = x.getCreateTm();
//                Date createTmY = y.getCreateTm();
//                if (arrTmX != null && arrTmY != null) {
//                    if (DateUtils.localToUTC(x.getArrTm()).compareTo(DateUtils.localToUTC(y.getArrTm())) == 0) {
//                        if (createTmX != null && createTmY != null) {
//                            return x.getCreateTm().compareTo(y.getCreateTm());
//                        } else {
//                            return -1;
//                        }
//                    } else {
//                        return DateUtils.localToUTC(x.getArrTm()).compareTo(DateUtils.localToUTC(y.getArrTm()));
//                    }
//                } else {
//                    return -1;
//                }
//
//            });
//            List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
//            voyageListVo.setVoyagePortVos(voyagePortVos);
//            List<RawVoyageSpec> rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();
//            List<VoyageSpecVo> voyageSpecVos = new ArrayList<>();
//            rawVoyageSpecs.forEach(rawVoyageSpec -> {
//                VoyageSpecVo voyageSpecVo = new VoyageSpecVo();
//                voyageSpecVo.setId(rawVoyageSpec.getId());
//                voyageSpecVo.setShipId(oiShipInfo.getId());
//                voyageSpecVo.setShipName(oiShipInfo.getSpnameCn());
//                voyageSpecVo.setShipNameEn(oiShipInfo.getSpname());
//                voyageSpecVo.setRecodeType(rawVoyageSpec.getRecordType());
//                voyageSpecVo.setIceStartTime(rawVoyageSpec.getBeginTm());
//                voyageSpecVo.setIceEndTime(rawVoyageSpec.getEndTm());
//                voyageSpecVo.setDistance(rawVoyageSpec.getDistance());
//                voyageSpecVo.setVoyageCode(oiShipTask.getTask());
//                voyageSpecVo.setBeginZone(rawVoyageSpec.getBeginZone());
//                List<RawVoyageSpecCons> rawVoyageSpecCons = rawVoyageSpec.getRawVoyageSpecCons();
//                BigDecimal bigDecimalHfo = new BigDecimal(0);
//                List<String> hfoIds = Arrays.asList(FuelConst.HFO_CODE.split(","));
//                BigDecimal bigDecimalLfo = new BigDecimal(0);
//                List<String> lfoIds = Arrays.asList(FuelConst.LFO_CODE.split(","));
//                BigDecimal bigDecimalChaifo = new BigDecimal(0);
//                BigDecimal bigDecimalOtherOil = new BigDecimal(0);
//                List<RawVoyageSpecConsVo> rawVoyageSpecConsVos = new ArrayList<>();
//                for (int i = 0; i < rawVoyageSpecCons.size(); i++) {
//                    RawVoyageSpecCons rawVoyageSpecCons1 = rawVoyageSpecCons.get(i);
//                    if (rawVoyageSpecCons1.getConsTons() == null) {
//                        rawVoyageSpecCons1.setConsTons(BigDecimal.ZERO);
//                    }
//                    if (rawVoyageSpecCons1.getOutTons() == null) {
//                        rawVoyageSpecCons1.setOutTons(BigDecimal.ZERO);
//                    }
//                    RawVoyageSpecConsVo rawVoyageSpecConsVo = new RawVoyageSpecConsVo();
//                    rawVoyageSpecConsVo.setId(rawVoyageSpecCons1.getId());
//                    rawVoyageSpecConsVo.setOilId(rawVoyageSpecCons1.getOilId());
//                    rawVoyageSpecConsVo.setOilName(rawVoyageSpecCons1.getOilName());
//                    rawVoyageSpecConsVo.setConsTons(rawVoyageSpecCons1.getConsTons());
//                    rawVoyageSpecConsVo.setOutTons(rawVoyageSpecCons1.getOutTons());
//                    rawVoyageSpecConsVo.setOutTm(rawVoyageSpecCons1.getOutTm());
//                    rawVoyageSpecConsVos.add(rawVoyageSpecConsVo);
//                    if (hfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
//                        bigDecimalHfo = bigDecimalHfo.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    } else if (lfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
//                        bigDecimalLfo = bigDecimalLfo.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    } else if (FuelConst.CHAI_CODE.equals(rawVoyageSpecCons.get(i).getOilId())) {
//                        bigDecimalChaifo = bigDecimalChaifo.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    } else {
//                        bigDecimalOtherOil = bigDecimalOtherOil.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    }
//                }
//                voyageSpecVo.setHfo(bigDecimalHfo);
//                voyageSpecVo.setLfo(bigDecimalLfo);
//                voyageSpecVo.setDiesel(bigDecimalChaifo);
//                voyageSpecVo.setOtherOil(bigDecimalOtherOil);
//                voyageSpecVo.setRawVoyageSpecConsVos(rawVoyageSpecConsVos);
//                voyageSpecVos.add(voyageSpecVo);
//            });
//            voyageListVo.setVoyageSpecVos(voyageSpecVos);
            voyageListVos.add(voyageListVo);
        });
        PageDataVo<VoyageListVo> volageListVoPageDataVo = new PageDataVo<>();
        volageListVoPageDataVo.setItems(voyageListVos);
        volageListVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(volageListVoPageDataVo);
    }

    /**
     * 根据航段或航次id获取港口数据
     *
     * @param voyageId
     * @param voyageType 0：航次 1：航段
     * @return
     */
    @Override
    public ResultVo getRawVoyagePortByVoyageId(String voyageId, Integer voyageType) {
        VoyageListVo voyageListVo = new VoyageListVo();
        if (voyageType == 1) {
            List<RawVoyagePort> rawVoyagePorts = new ArrayList<>();
            OiShipVoyage oiShipVoyage = oiShipVoyageRepository.findByIdAndIsDelete(voyageId, 0);
            String endportid = oiShipVoyage.getEndportid();
            String startportid = oiShipVoyage.getStartportid();
            RawVoyagePort endPort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
            RawVoyagePort startPort = rawVoyagePortRepository.findByIdAndIsDelete(startportid, 0);
            OiShipInfo oiShipInfo = oiShipVoyage.getOiShipInfo();
            if (endPort != null && startPort != null) {
                rawVoyagePorts.add(startPort);
                rawVoyagePorts.add(endPort);
            }
            List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
            voyageListVo.setVoyagePortVos(voyagePortVos);
        } else {
            OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(voyageId, 0);
            OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();
            List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
            List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
            rawVoyagePorts.addAll(rawVoyagePortsNext);
            rawVoyagePorts.sort((x, y) -> {
                Date arrTmX = x.getArrTm();
                Date arrTmY = y.getArrTm();
                Date createTmX = x.getCreateTm();
                Date createTmY = y.getCreateTm();
                if (arrTmX != null && arrTmY != null) {
                    if (x.getArrTm().compareTo(y.getArrTm()) == 0) {
                        if (createTmX != null && createTmY != null) {
                            return x.getCreateTm().compareTo(y.getCreateTm());
                        } else {
                            return -1;
                        }
                    } else {
                        return x.getArrTm().compareTo(y.getArrTm());
                    }
                } else {
                    return -1;
                }

            });
            List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
            List<RawVoyageSpec> rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();
            List<VoyageSpecVo> voyageSpecVos = new ArrayList<>();
            if (rawVoyageSpecs != null) {
                rawVoyageSpecs.forEach(rawVoyageSpec -> {
                    VoyageSpecVo voyageSpecVo = new VoyageSpecVo();
                    voyageSpecVo.setId(rawVoyageSpec.getId());
                    voyageSpecVo.setShipId(oiShipInfo.getId());
                    voyageSpecVo.setShipName(oiShipInfo.getSpnameCn());
                    voyageSpecVo.setShipNameEn(oiShipInfo.getSpname());
                    voyageSpecVo.setRecodeType(rawVoyageSpec.getRecordType());
                    voyageSpecVo.setIceStartTime(rawVoyageSpec.getBeginTm());
                    voyageSpecVo.setIceEndTime(rawVoyageSpec.getEndTm());
                    voyageSpecVo.setDistance(rawVoyageSpec.getDistance());
                    voyageSpecVo.setVoyageCode(oiShipTask.getTask());
                    voyageSpecVo.setBeginZone(rawVoyageSpec.getBeginZone());
                    List<RawVoyageSpecCons> rawVoyageSpecCons = rawVoyageSpec.getRawVoyageSpecCons();
                    BigDecimal bigDecimalHfo = new BigDecimal(0);
                    List<String> hfoIds = Arrays.asList(FuelConst.HFO_CODE.split(","));
                    BigDecimal bigDecimalLfo = new BigDecimal(0);
                    List<String> lfoIds = Arrays.asList(FuelConst.LFO_CODE.split(","));
                    BigDecimal bigDecimalChaifo = new BigDecimal(0);
                    BigDecimal bigDecimalOtherOil = new BigDecimal(0);
                    List<RawVoyageSpecConsVo> rawVoyageSpecConsVos = new ArrayList<>();
                    for (int i = 0; i < rawVoyageSpecCons.size(); i++) {
                        RawVoyageSpecCons rawVoyageSpecCons1 = rawVoyageSpecCons.get(i);
                        if (rawVoyageSpecCons1.getConsTons() == null) {
                            rawVoyageSpecCons1.setConsTons(BigDecimal.ZERO);
                        }
                        if (rawVoyageSpecCons1.getOutTons() == null) {
                            rawVoyageSpecCons1.setOutTons(BigDecimal.ZERO);
                        }
                        RawVoyageSpecConsVo rawVoyageSpecConsVo = new RawVoyageSpecConsVo();
                        rawVoyageSpecConsVo.setId(rawVoyageSpecCons1.getId());
                        rawVoyageSpecConsVo.setOilId(rawVoyageSpecCons1.getOilId());
                        rawVoyageSpecConsVo.setOilName(rawVoyageSpecCons1.getOilName());
                        rawVoyageSpecConsVo.setConsTons(rawVoyageSpecCons1.getConsTons());
                        rawVoyageSpecConsVo.setOutTons(rawVoyageSpecCons1.getOutTons());
                        rawVoyageSpecConsVo.setOutTm(rawVoyageSpecCons1.getOutTm());
                        rawVoyageSpecConsVos.add(rawVoyageSpecConsVo);
                        if (hfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
                            bigDecimalHfo = bigDecimalHfo.add(rawVoyageSpecCons.get(i).getConsTons())
                                    .add(rawVoyageSpecCons1.getOutTons());
                        } else if (lfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
                            bigDecimalLfo = bigDecimalLfo.add(rawVoyageSpecCons.get(i).getConsTons())
                                    .add(rawVoyageSpecCons1.getOutTons());
                        } else if (FuelConst.CHAI_CODE.equals(rawVoyageSpecCons.get(i).getOilId())) {
                            bigDecimalChaifo = bigDecimalChaifo.add(rawVoyageSpecCons.get(i).getConsTons())
                                    .add(rawVoyageSpecCons1.getOutTons());
                        } else {
                            bigDecimalOtherOil = bigDecimalOtherOil.add(rawVoyageSpecCons.get(i).getConsTons())
                                    .add(rawVoyageSpecCons1.getOutTons());
                        }
                    }
                    voyageSpecVo.setHfo(bigDecimalHfo);
                    voyageSpecVo.setLfo(bigDecimalLfo);
                    voyageSpecVo.setDiesel(bigDecimalChaifo);
                    voyageSpecVo.setOtherOil(bigDecimalOtherOil);
                    voyageSpecVo.setRawVoyageSpecConsVos(rawVoyageSpecConsVos);
                    voyageSpecVos.add(voyageSpecVo);
                });
            }
            voyageListVo.setVoyagePortVos(voyagePortVos);
            voyageListVo.setVoyageSpecVos(voyageSpecVos);
        }
        return new ResultObjectVo<>(voyageListVo);
    }
//    @Override
//    public ResultVo getVolageList(VoyageListDto voyageListDto) {
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.DESC, "oiShipInfo.spname"));
//        orders.add(new Sort.Order(Sort.Direction.DESC, "startTime"));
//        Page<OiShipTask> all = oiShipTaskRepository.findAll(queryVolageList(voyageListDto),
//                PageRequest.of(voyageListDto.getCurrentPage() - 1, voyageListDto.getPageSize(),
//                        Sort.by(orders)));
//        List<OiShipTask> content = all.getContent();
//        List<VoyageListVo> voyageListVos = new ArrayList<>();
//        List<OiShipTask> oiShipTasks = new ArrayList<>(content);
//        oiShipTasks.forEach(oiShipTask -> {
//            OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();
//            VoyageListVo voyageListVo = new VoyageListVo();
//            voyageListVo.setId(oiShipTask.getId());
//            voyageListVo.setVolageCode(oiShipTask.getTask());
//            voyageListVo.setImo(oiShipInfo.getImono());
//            voyageListVo.setRegisterNo(oiShipInfo.getRegisterno());
//            voyageListVo.setShipNameCn(oiShipInfo.getSpnameCn());
//            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
//            voyageListVo.setStartTime(oiShipTask.getStartTime());
//            voyageListVo.setEndTime(oiShipTask.getEndTime());
//            voyageListVo.setEeoiValue(oiShipTask.getEeoiValue());
//            voyageListVo.setCarbonDioxideEmission(oiShipTask.getEeoiFc());
//            voyageListVo.setDropRatio(oiShipTask.getSpeedrate());
//            voyageListVo.setCapacityUtilization(oiShipTask.getUserate());
//            voyageListVo.setRecStatus(oiShipTask.getRecStatus());
//            voyageListVo.setVolageAndSegmentType(0);
//            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
//            voyageListVo.setShipId(oiShipInfo.getId());
//            voyageListVo.setEeoiTd(oiShipTask.getEeoiTd());
//            voyageListVo.setDistance(oiShipTask.getDistance());
//            BigDecimal voyageDistanceTime = new BigDecimal(0);
//
//            List<SegmentsVo> segmentsVos = new ArrayList<>();
//            List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
//            oiShipVoyages.sort((x, y) -> {
//                if (x.getVoyType() != null && y.getVoyType() != null) {
//                    if (x.getVoyType() == y.getVoyType()) {
//                        if (StringUtils.isNotEmpty(x.getVoyageno()) && StringUtils.isNotEmpty(y.getVoyageno())) {
//                            return Integer.valueOf(x.getVoyageno()) - Integer.valueOf(y.getVoyageno());
//                        } else {
//                            return -1;
//                        }
//                    }
//                    return x.getVoyType() - y.getVoyType();
//                } else {
//                    return -1;
//                }
//            });
//            if ((oiShipTask.getStartPort() == null || oiShipTask.getStartPort().isEmpty()) && oiShipVoyages != null && oiShipVoyages.size() > 0) {
//                voyageListVo.setStartPortEn(oiShipVoyages.get(0).getStartporten());
//            }
//            if ((oiShipTask.getDestPort() == null || oiShipTask.getDestPort().isEmpty()) && oiShipVoyages != null && oiShipVoyages.size() > 0) {
//                voyageListVo.setEndPortEn(oiShipVoyages.get(oiShipVoyages.size() - 1).getEndporten());
//            }
//            for (int i = 0; i < oiShipVoyages.size(); i++) {
//                OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
//
//                List<RawVoyagePort> rawVoyagePorts = new ArrayList<>();
//                SegmentsVo segmentsVo = new SegmentsVo();
//                segmentsVo.setId(oiShipVoyage.getId());
//                segmentsVo.setImo(oiShipInfo.getImono());
//                segmentsVo.setRegisterNo(oiShipInfo.getRegisterno());
//                segmentsVo.setVolageCode(oiShipVoyage.getVoyageno());
//                segmentsVo.setShipNameCn(oiShipInfo.getSpnameCn());
//                segmentsVo.setShipNameEn(oiShipInfo.getSpname());
//                segmentsVo.setStartTime(oiShipVoyage.getStartTime());
//                segmentsVo.setEndTime(oiShipVoyage.getEndTime());
//                segmentsVo.setEeoiValue(BigDecimal.ZERO);
//                segmentsVo.setCapacityUtilization(BigDecimal.ZERO);
//                segmentsVo.setCarbonDioxideEmission(BigDecimal.ZERO);
//                segmentsVo.setDropRatio(BigDecimal.ZERO);
//                segmentsVo.setRecStatus(oiShipTask.getRecStatus());
//                segmentsVo.setVoyType(oiShipVoyage.getVoyType());
//                segmentsVo.setVolageAndSegmentType(1);
//                segmentsVo.setStartPort(oiShipVoyage.getStartportcn());
//                segmentsVo.setEndPort(oiShipVoyage.getEndportcn());
//                segmentsVo.setHfo(oiShipVoyage.getOiHfo());
//                segmentsVo.setChaiOil(oiShipVoyage.getOiChai());
//                segmentsVo.setCargo(oiShipVoyage.getCargo());
//                segmentsVo.setBallest(oiShipVoyage.getBallast());
//                segmentsVo.setStartPortEn(oiShipVoyage.getStartporten());
//                segmentsVo.setEndPortEn(oiShipVoyage.getEndporten());
//                segmentsVo.setDistance(oiShipVoyage.getDistance());
//                segmentsVo.setStopTime(oiShipVoyage.getStopTime());
//                segmentsVo.setDistanceTime(getSailtime(oiShipVoyage));
////                voyageDistanceTime = voyageDistanceTime.add(segmentsVo.getDistanceTime());
////                if (oiShipVoyage.getStartPort() != null && oiShipVoyage.getEndPort() != null) {
////                    rawVoyagePorts.add(oiShipVoyage.getStartPort());
////                    rawVoyagePorts.add(oiShipVoyage.getEndPort());
////                }
////                List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
////                segmentsVo.setVoyagePortVos(voyagePortVos);
//                segmentsVos.add(segmentsVo);
//
//            }
//            voyageListVo.setChildren(segmentsVos);
//            voyageListVo.setHasChildren(segmentsVos.size() == 0 ? false : true);
//            voyageListVo.setDistanceHour(voyageDistanceTime);
////            List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
////            List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
////            rawVoyagePorts.addAll(rawVoyagePortsNext);
////            rawVoyagePorts.sort((x, y) -> {
////                Date arrTmX = x.getArrTm();
////                Date arrTmY = y.getArrTm();
////                Date createTmX = x.getCreateTm();
////                Date createTmY = y.getCreateTm();
////                if (arrTmX != null && arrTmY != null) {
////                    if (x.getArrTm().compareTo(y.getArrTm()) == 0) {
////                        if (createTmX != null && createTmY != null) {
////                            return x.getCreateTm().compareTo(y.getCreateTm());
////                        } else {
////                            return -1;
////                        }
////                    } else {
////                        return x.getArrTm().compareTo(y.getArrTm());
////                    }
////                } else {
////                    return -1;
////                }
////
////            });
////            if (rawVoyagePorts.size() >= 1) {
////                voyageListVo.setStartPortEn(rawVoyagePorts.get(0).getPorten());
////                voyageListVo.setEndPortEn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPorten());
////                voyageListVo.setStartPortCn(rawVoyagePorts.get(0).getPortcn());
////                voyageListVo.setEndPortCn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPortcn());
////            }
////            List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
////            voyageListVo.setVoyagePortVos(voyagePortVos);
////            List<RawVoyageSpec> rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();
////            List<VoyageSpecVo> voyageSpecVos = new ArrayList<>();
////            rawVoyageSpecs.forEach(rawVoyageSpec -> {
////                VoyageSpecVo voyageSpecVo = new VoyageSpecVo();
////                voyageSpecVo.setId(rawVoyageSpec.getId());
////                voyageSpecVo.setShipId(oiShipInfo.getId());
////                voyageSpecVo.setShipName(oiShipInfo.getSpnameCn());
////                voyageSpecVo.setShipNameEn(oiShipInfo.getSpname());
////                voyageSpecVo.setRecodeType(rawVoyageSpec.getRecordType());
////                voyageSpecVo.setIceStartTime(rawVoyageSpec.getBeginTm());
////                voyageSpecVo.setIceEndTime(rawVoyageSpec.getEndTm());
////                voyageSpecVo.setDistance(rawVoyageSpec.getDistance());
////                voyageSpecVo.setVoyageCode(oiShipTask.getTask());
////                voyageSpecVo.setBeginZone(rawVoyageSpec.getBeginZone());
////                List<RawVoyageSpecCons> rawVoyageSpecCons = rawVoyageSpec.getRawVoyageSpecCons();
////                BigDecimal bigDecimalHfo = new BigDecimal(0);
////                List<String> hfoIds = Arrays.asList(FuelConst.HFO_CODE.split(","));
////                BigDecimal bigDecimalLfo = new BigDecimal(0);
////                List<String> lfoIds = Arrays.asList(FuelConst.LFO_CODE.split(","));
////                BigDecimal bigDecimalChaifo = new BigDecimal(0);
////                BigDecimal bigDecimalOtherOil = new BigDecimal(0);
////                List<RawVoyageSpecConsVo> rawVoyageSpecConsVos = new ArrayList<>();
////                for (int i = 0; i < rawVoyageSpecCons.size(); i++) {
////                    RawVoyageSpecCons rawVoyageSpecCons1 = rawVoyageSpecCons.get(i);
////                    if (rawVoyageSpecCons1.getConsTons() == null) {
////                        rawVoyageSpecCons1.setConsTons(BigDecimal.ZERO);
////                    }
////                    if (rawVoyageSpecCons1.getOutTons() == null) {
////                        rawVoyageSpecCons1.setOutTons(BigDecimal.ZERO);
////                    }
////                    RawVoyageSpecConsVo rawVoyageSpecConsVo = new RawVoyageSpecConsVo();
////                    rawVoyageSpecConsVo.setId(rawVoyageSpecCons1.getId());
////                    rawVoyageSpecConsVo.setOilId(rawVoyageSpecCons1.getOilId());
////                    rawVoyageSpecConsVo.setOilName(rawVoyageSpecCons1.getOilName());
////                    rawVoyageSpecConsVo.setConsTons(rawVoyageSpecCons1.getConsTons());
////                    rawVoyageSpecConsVo.setOutTons(rawVoyageSpecCons1.getOutTons());
////                    rawVoyageSpecConsVo.setOutTm(rawVoyageSpecCons1.getOutTm());
////                    rawVoyageSpecConsVos.add(rawVoyageSpecConsVo);
////                    if (hfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
////                        bigDecimalHfo = bigDecimalHfo.add(rawVoyageSpecCons.get(i).getConsTons())
////                                .add(rawVoyageSpecCons1.getOutTons());
////                    } else if (lfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
////                        bigDecimalLfo = bigDecimalLfo.add(rawVoyageSpecCons.get(i).getConsTons())
////                                .add(rawVoyageSpecCons1.getOutTons());
////                    } else if (FuelConst.CHAI_CODE.equals(rawVoyageSpecCons.get(i).getOilId())) {
////                        bigDecimalChaifo = bigDecimalChaifo.add(rawVoyageSpecCons.get(i).getConsTons())
////                                .add(rawVoyageSpecCons1.getOutTons());
////                    } else {
////                        bigDecimalOtherOil = bigDecimalOtherOil.add(rawVoyageSpecCons.get(i).getConsTons())
////                                .add(rawVoyageSpecCons1.getOutTons());
////                    }
////                }
////                voyageSpecVo.setHfo(bigDecimalHfo);
////                voyageSpecVo.setLfo(bigDecimalLfo);
////                voyageSpecVo.setDiesel(bigDecimalChaifo);
////                voyageSpecVo.setOtherOil(bigDecimalOtherOil);
////                voyageSpecVo.setRawVoyageSpecConsVos(rawVoyageSpecConsVos);
////                voyageSpecVos.add(voyageSpecVo);
////            });
////            voyageListVo.setVoyageSpecVos(voyageSpecVos);
//            voyageListVos.add(voyageListVo);
//        });
//        PageDataVo<VoyageListVo> volageListVoPageDataVo = new PageDataVo<>();
//        volageListVoPageDataVo.setItems(voyageListVos);
//        volageListVoPageDataVo.setTotal((int) all.getTotalElements());
//        return new ResultObjectVo<>(volageListVoPageDataVo);
//    }
//    @Override
//    public ResultVo getVolageList(VoyageListDto voyageListDto) {
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.DESC, "oiShipInfo.spname"));
//        orders.add(new Sort.Order(Sort.Direction.DESC, "startTime"));
//        Page<OiShipTask> all = oiShipTaskRepository.findAll(queryVolageList(voyageListDto),
//                PageRequest.of(voyageListDto.getCurrentPage() - 1, voyageListDto.getPageSize(),
//                        Sort.by(orders)));
//        List<OiShipTask> content = all.getContent();
//        List<VoyageListVo> voyageListVos = new ArrayList<>();
//        List<OiShipTask> oiShipTasks = new ArrayList<>(content);
//        oiShipTasks.forEach(oiShipTask -> {
//            OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();
//            VoyageListVo voyageListVo = new VoyageListVo();
//            voyageListVo.setId(oiShipTask.getId());
//            voyageListVo.setVolageCode(oiShipTask.getTask());
//            voyageListVo.setImo(oiShipInfo.getImono());
//            voyageListVo.setRegisterNo(oiShipInfo.getRegisterno());
//            voyageListVo.setShipNameCn(oiShipInfo.getSpnameCn());
//            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
//            voyageListVo.setStartTime(oiShipTask.getStartTime());
//            voyageListVo.setEndTime(oiShipTask.getEndTime());
//            voyageListVo.setEeoiValue(oiShipTask.getEeoiValue());
//            voyageListVo.setCarbonDioxideEmission(oiShipTask.getEeoiFc());
//            voyageListVo.setDropRatio(oiShipTask.getSpeedrate());
//            voyageListVo.setCapacityUtilization(oiShipTask.getUserate());
//            voyageListVo.setRecStatus(oiShipTask.getRecStatus());
//            voyageListVo.setVolageAndSegmentType(0);
//            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
//            voyageListVo.setShipId(oiShipInfo.getId());
//            voyageListVo.setEeoiTd(oiShipTask.getEeoiTd());
//            voyageListVo.setDistance(oiShipTask.getDistance());
//            BigDecimal voyageDistanceTime = new BigDecimal(0);
//
//            List<SegmentsVo> segmentsVos = new ArrayList<>();
//            List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
//            oiShipVoyages.sort((x, y) -> {
//                if (x.getVoyType() != null && y.getVoyType() != null) {
//                    if (x.getVoyType() == y.getVoyType()) {
//                        if (StringUtils.isNotEmpty(x.getVoyageno()) && StringUtils.isNotEmpty(y.getVoyageno())) {
//                            return Integer.valueOf(x.getVoyageno()) - Integer.valueOf(y.getVoyageno());
//                        } else {
//                            return -1;
//                        }
//                    }
//                    return x.getVoyType() - y.getVoyType();
//                } else {
//                    return -1;
//                }
//            });
//            if ((oiShipTask.getStartPort() == null || oiShipTask.getStartPort().isEmpty()) && oiShipVoyages != null && oiShipVoyages.size() > 0) {
//                voyageListVo.setStartPortEn(oiShipVoyages.get(0).getStartporten());
//            }
//            if ((oiShipTask.getDestPort() == null || oiShipTask.getDestPort().isEmpty()) && oiShipVoyages != null && oiShipVoyages.size() > 0) {
//                voyageListVo.setEndPortEn(oiShipVoyages.get(oiShipVoyages.size() - 1).getEndporten());
//            }
//            for (int i = 0; i < oiShipVoyages.size(); i++) {
//                OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
//
//                List<RawVoyagePort> rawVoyagePorts = new ArrayList<>();
//                SegmentsVo segmentsVo = new SegmentsVo();
//                segmentsVo.setId(oiShipVoyage.getId());
//                segmentsVo.setImo(oiShipInfo.getImono());
//                segmentsVo.setRegisterNo(oiShipInfo.getRegisterno());
//                segmentsVo.setVolageCode(oiShipVoyage.getVoyageno());
//                segmentsVo.setShipNameCn(oiShipInfo.getSpnameCn());
//                segmentsVo.setShipNameEn(oiShipInfo.getSpname());
//                segmentsVo.setStartTime(oiShipVoyage.getStartTime());
//                segmentsVo.setEndTime(oiShipVoyage.getEndTime());
//                segmentsVo.setEeoiValue(BigDecimal.ZERO);
//                segmentsVo.setCapacityUtilization(BigDecimal.ZERO);
//                segmentsVo.setCarbonDioxideEmission(BigDecimal.ZERO);
//                segmentsVo.setDropRatio(BigDecimal.ZERO);
//                segmentsVo.setRecStatus(oiShipTask.getRecStatus());
//                segmentsVo.setVoyType(oiShipVoyage.getVoyType());
//                segmentsVo.setVolageAndSegmentType(1);
//                segmentsVo.setStartPort(oiShipVoyage.getStartportcn());
//                segmentsVo.setEndPort(oiShipVoyage.getEndportcn());
//                segmentsVo.setHfo(oiShipVoyage.getOiHfo());
//                segmentsVo.setChaiOil(oiShipVoyage.getOiChai());
//                segmentsVo.setCargo(oiShipVoyage.getCargo());
//                segmentsVo.setBallest(oiShipVoyage.getBallast());
//                segmentsVo.setStartPortEn(oiShipVoyage.getStartporten());
//                segmentsVo.setEndPortEn(oiShipVoyage.getEndporten());
//                segmentsVo.setDistance(oiShipVoyage.getDistance());
//                segmentsVo.setStopTime(oiShipVoyage.getStopTime());
//                segmentsVo.setDistanceTime(getSailtime(oiShipVoyage));
//                voyageDistanceTime = voyageDistanceTime.add(segmentsVo.getDistanceTime());
//                if (oiShipVoyage.getStartPort() != null && oiShipVoyage.getEndPort() != null) {
//                    rawVoyagePorts.add(oiShipVoyage.getStartPort());
//                    rawVoyagePorts.add(oiShipVoyage.getEndPort());
//                }
//                List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
//                segmentsVo.setVoyagePortVos(voyagePortVos);
//                segmentsVos.add(segmentsVo);
//
//            }
//            voyageListVo.setChildren(segmentsVos);
//            voyageListVo.setHasChildren(segmentsVos.size() == 0 ? false : true);
//            voyageListVo.setDistanceHour(voyageDistanceTime);
//            List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
//            List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
//            rawVoyagePorts.addAll(rawVoyagePortsNext);
//            rawVoyagePorts.sort((x, y) -> {
//                Date arrTmX = x.getArrTm();
//                Date arrTmY = y.getArrTm();
//                Date createTmX = x.getCreateTm();
//                Date createTmY = y.getCreateTm();
//                if (arrTmX != null && arrTmY != null) {
//                    if (x.getArrTm().compareTo(y.getArrTm()) == 0) {
//                        if (createTmX != null && createTmY != null) {
//                            return x.getCreateTm().compareTo(y.getCreateTm());
//                        } else {
//                            return -1;
//                        }
//                    } else {
//                        return x.getArrTm().compareTo(y.getArrTm());
//                    }
//                } else {
//                    return -1;
//                }
//
//            });
//            if (rawVoyagePorts.size() >= 1) {
//                voyageListVo.setStartPortEn(rawVoyagePorts.get(0).getPorten());
//                voyageListVo.setEndPortEn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPorten());
//                voyageListVo.setStartPortCn(rawVoyagePorts.get(0).getPortcn());
//                voyageListVo.setEndPortCn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPortcn());
//            }
//            List<VoyagePortVo> voyagePortVos = getVoyagePortVos(rawVoyagePorts, oiShipInfo);
//            voyageListVo.setVoyagePortVos(voyagePortVos);
//            List<RawVoyageSpec> rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();
//            List<VoyageSpecVo> voyageSpecVos = new ArrayList<>();
//            rawVoyageSpecs.forEach(rawVoyageSpec -> {
//                VoyageSpecVo voyageSpecVo = new VoyageSpecVo();
//                voyageSpecVo.setId(rawVoyageSpec.getId());
//                voyageSpecVo.setShipId(oiShipInfo.getId());
//                voyageSpecVo.setShipName(oiShipInfo.getSpnameCn());
//                voyageSpecVo.setShipNameEn(oiShipInfo.getSpname());
//                voyageSpecVo.setRecodeType(rawVoyageSpec.getRecordType());
//                voyageSpecVo.setIceStartTime(rawVoyageSpec.getBeginTm());
//                voyageSpecVo.setIceEndTime(rawVoyageSpec.getEndTm());
//                voyageSpecVo.setDistance(rawVoyageSpec.getDistance());
//                voyageSpecVo.setVoyageCode(oiShipTask.getTask());
//                voyageSpecVo.setBeginZone(rawVoyageSpec.getBeginZone());
//                List<RawVoyageSpecCons> rawVoyageSpecCons = rawVoyageSpec.getRawVoyageSpecCons();
//                BigDecimal bigDecimalHfo = new BigDecimal(0);
//                List<String> hfoIds = Arrays.asList(FuelConst.HFO_CODE.split(","));
//                BigDecimal bigDecimalLfo = new BigDecimal(0);
//                List<String> lfoIds = Arrays.asList(FuelConst.LFO_CODE.split(","));
//                BigDecimal bigDecimalChaifo = new BigDecimal(0);
//                BigDecimal bigDecimalOtherOil = new BigDecimal(0);
//                List<RawVoyageSpecConsVo> rawVoyageSpecConsVos = new ArrayList<>();
//                for (int i = 0; i < rawVoyageSpecCons.size(); i++) {
//                    RawVoyageSpecCons rawVoyageSpecCons1 = rawVoyageSpecCons.get(i);
//                    if (rawVoyageSpecCons1.getConsTons() == null) {
//                        rawVoyageSpecCons1.setConsTons(BigDecimal.ZERO);
//                    }
//                    if (rawVoyageSpecCons1.getOutTons() == null) {
//                        rawVoyageSpecCons1.setOutTons(BigDecimal.ZERO);
//                    }
//                    RawVoyageSpecConsVo rawVoyageSpecConsVo = new RawVoyageSpecConsVo();
//                    rawVoyageSpecConsVo.setId(rawVoyageSpecCons1.getId());
//                    rawVoyageSpecConsVo.setOilId(rawVoyageSpecCons1.getOilId());
//                    rawVoyageSpecConsVo.setOilName(rawVoyageSpecCons1.getOilName());
//                    rawVoyageSpecConsVo.setConsTons(rawVoyageSpecCons1.getConsTons());
//                    rawVoyageSpecConsVo.setOutTons(rawVoyageSpecCons1.getOutTons());
//                    rawVoyageSpecConsVo.setOutTm(rawVoyageSpecCons1.getOutTm());
//                    rawVoyageSpecConsVos.add(rawVoyageSpecConsVo);
//                    if (hfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
//                        bigDecimalHfo = bigDecimalHfo.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    } else if (lfoIds.contains(rawVoyageSpecCons.get(i).getOilId())) {
//                        bigDecimalLfo = bigDecimalLfo.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    } else if (FuelConst.CHAI_CODE.equals(rawVoyageSpecCons.get(i).getOilId())) {
//                        bigDecimalChaifo = bigDecimalChaifo.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    } else {
//                        bigDecimalOtherOil = bigDecimalOtherOil.add(rawVoyageSpecCons.get(i).getConsTons())
//                                .add(rawVoyageSpecCons1.getOutTons());
//                    }
//                }
//                voyageSpecVo.setHfo(bigDecimalHfo);
//                voyageSpecVo.setLfo(bigDecimalLfo);
//                voyageSpecVo.setDiesel(bigDecimalChaifo);
//                voyageSpecVo.setOtherOil(bigDecimalOtherOil);
//                voyageSpecVo.setRawVoyageSpecConsVos(rawVoyageSpecConsVos);
//                voyageSpecVos.add(voyageSpecVo);
//            });
//            voyageListVo.setVoyageSpecVos(voyageSpecVos);
//            voyageListVos.add(voyageListVo);
//        });
//        PageDataVo<VoyageListVo> volageListVoPageDataVo = new PageDataVo<>();
//        volageListVoPageDataVo.setItems(voyageListVos);
//        volageListVoPageDataVo.setTotal((int) all.getTotalElements());
//        return new ResultObjectVo<>(volageListVoPageDataVo);
//    }

    @Override
    public ResultVo getNewVolageList(VoyageListDto voyageListDto) {
        Page<OiShipTask> all = oiShipTaskRepository.findAll(queryVolageList(voyageListDto),
                PageRequest.of(voyageListDto.getCurrentPage() - 1, voyageListDto.getPageSize(),
                        Sort.by("oiShipInfo.spname").ascending()));
        List<OiShipTask> content = all.getContent();
        List<VoyageListVoNew> voyageListVos = new ArrayList<>();
        List<OiShipTask> oiShipTasks = new ArrayList<>(content);
        oiShipTasks.sort((x, y) -> {
            if (x.getStartTime() != null && y.getStartTime() != null) {
                return x.getStartTime().compareTo(y.getStartTime());
            } else {
                return -1;
            }
        });
        oiShipTasks.forEach(oiShipTask -> {
            OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();
            VoyageListVoNew voyageListVo = new VoyageListVoNew();
            voyageListVo.setId(oiShipTask.getId());
            voyageListVo.setVolageCode(oiShipTask.getTask());
            voyageListVo.setImo(oiShipInfo.getImono());
            voyageListVo.setRegisterNo(oiShipInfo.getRegisterno());
            voyageListVo.setShipNameCn(oiShipInfo.getSpnameCn());
            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
            voyageListVo.setStartTime(oiShipTask.getStartTime());
            voyageListVo.setEndTime(oiShipTask.getEndTime());
            voyageListVo.setEeoiValue(oiShipTask.getEeoiValue());
            //计算每海里二氧化碳的排放量
            BigDecimal eeoiFc = oiShipTask.getEeoiFc();
            BigDecimal distance = oiShipTask.getDistance();
            if (eeoiFc == null) {
                eeoiFc = BigDecimal.ZERO;
            }
            if (distance == null) {
                distance = BigDecimal.ZERO;
            }
            BigDecimal carbonPerNm = BigDecimal.ZERO;
            if (distance.compareTo(BigDecimal.ZERO) > 0) {
                carbonPerNm = eeoiFc.multiply(BigDecimal.valueOf(1000)).divide(distance, 2, BigDecimal.ROUND_HALF_UP);
            }
            voyageListVo.setCarbonDioxideEmission(carbonPerNm);
            voyageListVo.setDropRatio(oiShipTask.getSpeedrate());
            voyageListVo.setCapacityUtilization(oiShipTask.getUserate());
            voyageListVo.setRecStatus(oiShipTask.getRecStatus());
            voyageListVo.setShipNameEn(oiShipInfo.getSpname());
            voyageListVo.setEeoiTd(oiShipTask.getEeoiTd());
            voyageListVo.setDistance(oiShipTask.getDistance());
            BigDecimal voyageDistanceTime = new BigDecimal(0);
            voyageListVo.setDistanceHour(voyageDistanceTime);
            List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
            List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
            voyageListVo.setVoyageNum(oiShipVoyages.size());
            for (int i = 0; i < oiShipVoyages.size(); i++) {
                OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
                BigDecimal sailtime = getSailtime(oiShipVoyage);
                voyageDistanceTime = voyageDistanceTime.add(sailtime);
            }
            if (rawVoyagePorts.size() >= 1) {
                voyageListVo.setStartPortEn(rawVoyagePorts.get(0).getPorten());
                voyageListVo.setEndPortEn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPorten());
                voyageListVo.setStartPortCn(rawVoyagePorts.get(0).getPortcn());
                voyageListVo.setEndPortCn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPortcn());
            }
            voyageListVos.add(voyageListVo);
        });
        PageDataVo<VoyageListVoNew> volageListVoPageDataVo = new PageDataVo<>();
        volageListVoPageDataVo.setItems(voyageListVos);
        volageListVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(volageListVoPageDataVo);
    }

    @Override
    public ResultVo getVolagePortList(VoyagePortListDto voyagePortListDto) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "oiShipInfo.spname"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "arrTm"));
        Page<RawVoyagePort> all = rawVoyagePortRepository.findAll(queryVolagePortList(voyagePortListDto), PageRequest
                .of(voyagePortListDto.getCurrentPage() - 1, voyagePortListDto.getPageSize(), Sort.by(orders)));
        List<RawVoyagePort> rawVoyagePorts = all.getContent();
        List<VoyagePortVo> voyagePortVos = new ArrayList<>();
        rawVoyagePorts.forEach(rawVoyagePort -> {
            VoyagePortVo voyagePortVo = new VoyagePortVo();
            voyagePortVo.setId(rawVoyagePort.getId());
            voyagePortVo.setPortName(rawVoyagePort.getPorten());
            voyagePortVo.setPortNameCn(rawVoyagePort.getPortcn());
            voyagePortVo.setInPort("0".equals(rawVoyagePort.getInPort()) ? true : false);
            voyagePortVo.setPortWork(Arrays.asList(rawVoyagePort.getPortWork().split(",")));
            voyagePortVo.setDistance(rawVoyagePort.getDistance());
            voyagePortVo.setArrivalTime(rawVoyagePort.getArrTm());
            voyagePortVo.setFormThePortTime(rawVoyagePort.getDeptTm());
            List<RawVoyagePortloading> rawVoyagePortloadings = rawVoyagePort.getRawVoyagePortloadings();
            BigDecimal arrivalLoad = new BigDecimal(0);
            BigDecimal outOfPortLoad = new BigDecimal(0);
            for (int i = 0; i < rawVoyagePortloadings.size(); i++) {
                if ("1".equals(rawVoyagePortloadings.get(i).getLoadingType())) {
                    arrivalLoad = arrivalLoad.add(rawVoyagePortloadings.get(i).getCargoTons());
                } else if ("0".equals(rawVoyagePortloadings.get(i).getLoadingType())) {
                    outOfPortLoad = outOfPortLoad.add(rawVoyagePortloadings.get(i).getCargoTons());
                }
            }
            voyagePortVo.setArrivalLoad(arrivalLoad);
            voyagePortVo.setCargoOutOfPort(outOfPortLoad);
            List<VoyagePortOilVo> voyagePortOilVos = new ArrayList<>();
            List<VoyagePortLordingVo> voyagePortLordingVos = new ArrayList<>();
            rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
                VoyagePortOilVo voyagePortOilVo = new VoyagePortOilVo();
                BeanUtils.copyProperties(rawVoyagePortoil, voyagePortOilVo);
                voyagePortOilVos.add(voyagePortOilVo);
            });
            rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                VoyagePortLordingVo voyagePortLordingVo = new VoyagePortLordingVo();
                BeanUtils.copyProperties(rawVoyagePortloading, voyagePortLordingVo);
                voyagePortLordingVos.add(voyagePortLordingVo);
            });
            voyagePortVo.setVoyagePortOilVos(voyagePortOilVos);
            voyagePortVo.setVoyagePortLordingVos(voyagePortLordingVos);
            voyagePortVos.add(voyagePortVo);
        });
        PageDataVo<VoyagePortVo> volagePortVoPageDataVo = new PageDataVo<>();
        volagePortVoPageDataVo.setItems(voyagePortVos);
        volagePortVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(volagePortVoPageDataVo);
    }

    @Override
    @Transactional
    public ResultVo createAndUpdatePortInfo(VoyagePortDto voyagePortDto) {
        RawVoyagePort byIdAndIsDelete = rawVoyagePortRepository.findByIdAndIsDelete(voyagePortDto.getId(), 0);
        List<VoyagePortOutSulageOilDto> voyagePortOutSulageOilDtos = voyagePortDto.getVoyagePortOutSulageOilDtos();
        if (voyagePortOutSulageOilDtos == null) {
            voyagePortOutSulageOilDtos = new ArrayList<>();
        }
        Iterator<VoyagePortOutSulageOilDto> iterator = voyagePortOutSulageOilDtos.iterator();
        while (iterator.hasNext()) {
            VoyagePortOutSulageOilDto next = iterator.next();
            BigDecimal sludgeTons = next.getSludgeTons();
            if (sludgeTons == null || sludgeTons.compareTo(BigDecimal.ZERO) == 0) {
                iterator.remove();
            }
        }
        voyagePortDto.setVoyagePortOutSulageOilDtos(voyagePortOutSulageOilDtos);
        String refCode = voyagePortDto.getRefCode();
        Calendar instance = Calendar.getInstance();
        instance.setTime(voyagePortDto.getDeptTm());
        if (refCode != null) {
            byIdAndIsDelete = rawVoyagePortRepository.findByIsDeleteAndRefCode(0, refCode);
        }
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(voyagePortDto.getShipId(), 0);
        StringBuilder taskCode = new StringBuilder();
        List<String> listByShipIsDelete = oiShipTaskRepository.findListByShipIsDelete(voyagePortDto.getShipId(),
                voyagePortDto.getFirstVolageCode());
        String byTaskAndShipIsDelete = null;
        if (listByShipIsDelete != null && listByShipIsDelete.size() > 0) {
            byTaskAndShipIsDelete = listByShipIsDelete.get(0);
        }
        List<String> listByShipIsDeleteSecond = oiShipTaskRepository.findListByShipIsDelete(voyagePortDto.getShipId(),
                voyagePortDto.getSecondVolageCode());
        String byTaskAndShipIsDeleteSecond = null;
        if (listByShipIsDeleteSecond != null && listByShipIsDeleteSecond.size() > 0) {
            byTaskAndShipIsDeleteSecond = listByShipIsDeleteSecond.get(0);
        }
        if (StringUtils.isNotEmpty(voyagePortDto.getFirstVolageCode())) {
            if (null == byTaskAndShipIsDelete) {
                OiShipTask oiShipTask = new OiShipTask();
                oiShipTask.setTask(voyagePortDto.getFirstVolageCode());
                oiShipTask.setShipId(voyagePortDto.getShipId());
                oiShipTask.setRecStatus("0");
                oiShipTask.setIsDelete(0);
                oiShipTask.setTaskyear(String.valueOf(instance.get(Calendar.YEAR)));
                oiShipTask.setStartTime(voyagePortDto.getDeptTm());
                oiShipTask.setEndTime(voyagePortDto.getDeptTm());
                OiShipTask save = oiShipTaskRepository.save(oiShipTask);
                byTaskAndShipIsDelete = save.getId();
            } else {
                OiShipTask byIdAndIsDelete1 = oiShipTaskRepository.findByIdAndIsDelete(byTaskAndShipIsDelete, 0);
                if (byIdAndIsDelete1.getRecStatus().equals("1")) {
                    return new ResultErrorVo(this.msg("task.lock.status"));
                }
            }
            taskCode.append(Calendar.getInstance().get(Calendar.YEAR) + "##" + voyagePortDto.getFirstVolageCode());
        }
        if (StringUtils.isNotEmpty(voyagePortDto.getSecondVolageCode())) {
            if (null == byTaskAndShipIsDeleteSecond) {
                OiShipTask oiShipTask = new OiShipTask();
                oiShipTask.setTask(voyagePortDto.getSecondVolageCode());
                oiShipTask.setShipId(voyagePortDto.getShipId());
                oiShipTask.setRecStatus("0");
                oiShipTask.setIsDelete(0);
                oiShipTask.setStartTime(voyagePortDto.getDeptTm());
                oiShipTask.setEndTime(voyagePortDto.getDeptTm());
                oiShipTask.setTaskyear(String.valueOf(instance.get(Calendar.YEAR)));
                OiShipTask save = oiShipTaskRepository.save(oiShipTask);
                byTaskAndShipIsDeleteSecond = save.getId();
            } else {
                OiShipTask byIdAndIsDeleteSecond = oiShipTaskRepository.findByIdAndIsDelete(byTaskAndShipIsDelete, 0);
                if (byIdAndIsDeleteSecond.getRecStatus().equals("1")) {
                    return new ResultErrorVo(this.msg("task.lock.status"));
                }
            }
            taskCode.append(
                    "," + Calendar.getInstance().get(Calendar.YEAR) + "##" + voyagePortDto.getSecondVolageCode());
        }
        List<String> anchorPurpose = voyagePortDto.getAnchorPurpose();
        StringBuilder stringBuilder = new StringBuilder();
        if (anchorPurpose != null) {
            for (int i = 0; i < anchorPurpose.size(); i++) {
                String s = anchorPurpose.get(i);
                if (i == anchorPurpose.size() - 1) {
                    stringBuilder.append(s);
                } else {
                    stringBuilder.append(s + ",");
                }
            }
        }
        //插入和更新航次航次号1
//        OiTaskNotify byShipIdAndTaskId = oiTaskNotifyRepository.findByShipIdAndTaskId(voyagePortDto.getShipId(), byTaskAndShipIsDelete);
//        if (byShipIdAndTaskId == null) {
//            byShipIdAndTaskId = new OiTaskNotify(voyagePortDto.getShipId(), byTaskAndShipIsDelete, 0, 0);
//            byShipIdAndTaskId.setIsDelete(0);
//        } else {
//            byShipIdAndTaskId.setStatus(0);
//        }
//        oiTaskNotifyRepository.save(byShipIdAndTaskId);
        //插入和更新航次航次号2
//        OiTaskNotify byShipIdAndTaskIdSecond= oiTaskNotifyRepository.findByShipIdAndTaskId(voyagePortDto.getShipId(), byTaskAndShipIsDeleteSecond);
//        if (byShipIdAndTaskIdSecond == null) {
//            byShipIdAndTaskIdSecond = new OiTaskNotify(voyagePortDto.getShipId(), byTaskAndShipIsDeleteSecond, 0, 0);
//            byShipIdAndTaskIdSecond.setIsDelete(0);
//        } else {
//            byShipIdAndTaskIdSecond.setStatus(0);
//        }
//        oiTaskNotifyRepository.save(byShipIdAndTaskIdSecond);
        if (null != byIdAndIsDelete) {
            // 删除所有港口
            List<RawVoyageSludge> rawVoyageSludges = byIdAndIsDelete.getRawVoyageSludges();
            List<RawVoyageAddoil> rawVoyageAddoils = byIdAndIsDelete.getRawVoyageAddoils();
            List<RawVoyageOutoil> rawVoyageOutoils = byIdAndIsDelete.getRawVoyageOutoils();
            if (rawVoyageSludges != null) {
                rawVoyageSludges.forEach(rawVoyageSludge -> {
                    rawVoyageSludge.setIsDelete(1);
                });
            }
            if (rawVoyageOutoils != null) {
                rawVoyageOutoils.forEach(rawVoyageOutoil -> {
                    rawVoyageOutoil.setIsDelete(1);
                });
            }
            if (rawVoyageAddoils != null) {
                rawVoyageAddoils.forEach(rawVoyageAddoil -> {
                    rawVoyageAddoil.setIsDelete(1);
                });
            }
            String portId = byIdAndIsDelete.getId();
            BeanUtils.copyProperties(voyagePortDto, byIdAndIsDelete);
            byIdAndIsDelete.setId(portId);
            if (voyagePortDto.getIsEu() != null) {
                byIdAndIsDelete.setIsEu(voyagePortDto.getIsEu() ? 1 : 0);
            }

            byIdAndIsDelete.setInPort((voyagePortDto.getInPort() == null || voyagePortDto.getInPort()) ? "0" : "1");
            byIdAndIsDelete.setPortWork(stringBuilder.toString());
            byIdAndIsDelete.setTaskId(byTaskAndShipIsDelete);
            byIdAndIsDelete.setTaskIdNext(byTaskAndShipIsDeleteSecond);
            byIdAndIsDelete.setTaskCode(taskCode.toString());
            List<RawVoyagePortoil> rawVoyagePortoils = byIdAndIsDelete.getRawVoyagePortoils();
            List<RawVoyagePortloading> rawVoyagePortloadings = byIdAndIsDelete.getRawVoyagePortloadings();
            List<VoyagePortLordingDto> volagePortLoadingInfos = voyagePortDto.getVolagePortLoadingInfos();
            List<VoyagePortOilDto> portOilInfos = voyagePortDto.getPortOilInfos();
            for (int i = 0; i < volagePortLoadingInfos.size(); i++) {
                String id = volagePortLoadingInfos.get(i).getId();
                RawVoyagePortloading rawVoyagePortloadingUpdate = rawVoyagePortloadingRepository.findByIdAndIsDelete(id,
                        0);
                if (null != rawVoyagePortloadingUpdate) {
                    // 修改港口载荷信息
                    for (int j = 0; j < rawVoyagePortloadings.size(); j++) {
                        if (id.equals(rawVoyagePortloadings.get(j).getId())) {
                            BeanUtils.copyProperties(volagePortLoadingInfos.get(i), rawVoyagePortloadings.get(j));
                            rawVoyagePortloadings.get(j).setTaskId(byTaskAndShipIsDelete);
                            rawVoyagePortloadings.get(j).setTaskIdNext(byTaskAndShipIsDeleteSecond);
                            rawVoyagePortloadings.get(j).setShipId(voyagePortDto.getShipId());
                        }
                    }
                } else {
                    // 增加港口载荷信息
                    RawVoyagePortloading rawVoyagePortloading = new RawVoyagePortloading();
                    BeanUtils.copyProperties(volagePortLoadingInfos.get(i), rawVoyagePortloading);
                    rawVoyagePortloading.setTaskId(byTaskAndShipIsDelete);
                    rawVoyagePortloading.setTaskIdNext(byTaskAndShipIsDeleteSecond);
                    rawVoyagePortloading.setPortinfoId(byIdAndIsDelete.getId());
                    rawVoyagePortloading.setShipId(voyagePortDto.getShipId());
                    rawVoyagePortloading.setIsDelete(0);
                    rawVoyagePortloading.setRecStatus("0");
                    rawVoyagePortloadingRepository.save(rawVoyagePortloading);
                }
            }
            for (int i = 0; i < portOilInfos.size(); i++) {
                String id = portOilInfos.get(i).getId();
                RawVoyagePortoil byIdAndIsDelete1 = rawVoyagePortoilRepository.findByIdAndIsDelete(id, 0);
                if (null != byIdAndIsDelete1) {
                    // 修改港口油耗信息
                    for (int j = 0; j < rawVoyagePortoils.size(); j++) {
                        if (id.equals(rawVoyagePortoils.get(j).getId())) {
                            BeanUtils.copyProperties(portOilInfos.get(i), rawVoyagePortoils.get(j));
                            rawVoyagePortoils.get(j).setTaskId(byTaskAndShipIsDelete);
                            rawVoyagePortoils.get(j).setTaskIdNext(byTaskAndShipIsDeleteSecond);
                            rawVoyagePortoils.get(j).setShipId(voyagePortDto.getShipId());
                        }
                    }
                } else {
                    // 增加港口油耗信息
                    RawVoyagePortoil rawVoyagePortoil = new RawVoyagePortoil();
                    BeanUtils.copyProperties(portOilInfos.get(i), rawVoyagePortoil);
                    rawVoyagePortoil.setTaskId(byTaskAndShipIsDelete);
                    rawVoyagePortoil.setTaskIdNext(byTaskAndShipIsDeleteSecond);
                    rawVoyagePortoil.setPortinfoId(byIdAndIsDelete.getId());
                    rawVoyagePortoil.setShipId(byIdAndIsDelete.getShipId());
                    rawVoyagePortoil.setIsDelete(0);
                    rawVoyagePortoil.setRecStatus("0");
                    rawVoyagePortoilRepository.save(rawVoyagePortoil);
                }
            }
            boolean resultVo = saveAddOutSludge(voyagePortDto, byIdAndIsDelete);
//            if (!resultVo) {
//                throw new ExplicitException(this.msg("add.time.file"));
//            }
            if (!voyagePortDto.getRecordType().equals("0")) {
                byIdAndIsDelete.setInPort("1");
            }
            RawVoyagePort save = rawVoyagePortRepository.save(byIdAndIsDelete);
            if (null != save) {
                return new ResultStringVo(this.msg("energyEeffic.update.success"));
            } else {
                return new ResultStringVo(this.msg("energyEeffic.update.failed"));
            }
        } else {
            // 增加港口信息
            RawVoyagePort rawVoyagePort = new RawVoyagePort();
            BeanUtils.copyProperties(voyagePortDto, rawVoyagePort);
            rawVoyagePort.setIsDelete(0);
            rawVoyagePort.setRecStatus("0");
            if (voyagePortDto.getIsEu() != null) {
                rawVoyagePort.setIsEu(voyagePortDto.getIsEu() ? 1 : 0);
            }
            if (voyagePortDto.getInPort() != null) {
                rawVoyagePort.setInPort(voyagePortDto.getInPort() ? "0" : "1");
            }
            rawVoyagePort.setPortWork(stringBuilder.toString());
            rawVoyagePort.setTaskId(byTaskAndShipIsDelete);
            rawVoyagePort.setTaskIdNext(byTaskAndShipIsDeleteSecond);
            rawVoyagePort.setTaskCode(taskCode.toString());
            RawVoyagePort saveRawVoyagePort = rawVoyagePortRepository.save(rawVoyagePort);
            List<RawVoyagePortoil> rawVoyagePortoils = new ArrayList<>();
            List<RawVoyagePortloading> rawVoyagePortloadings = new ArrayList<>();
            String finalByTaskAndShipIsDelete = byTaskAndShipIsDelete;
            String finalByTaskAndShipIsDeleteSecond = byTaskAndShipIsDeleteSecond;
            // 添加油耗消息
            voyagePortDto.getPortOilInfos().forEach(voyagePortOilDto -> {
                RawVoyagePortoil rawVoyagePortoil = new RawVoyagePortoil();
                BeanUtils.copyProperties(voyagePortOilDto, rawVoyagePortoil);
                rawVoyagePortoil.setTaskId(finalByTaskAndShipIsDelete);
                rawVoyagePortoil.setTaskIdNext(finalByTaskAndShipIsDeleteSecond);
                rawVoyagePortoil.setPortinfoId(saveRawVoyagePort.getId());
                rawVoyagePortoil.setShipId(voyagePortDto.getShipId());
                rawVoyagePortoil.setIsDelete(0);
                rawVoyagePortoil.setRecStatus("0");
                rawVoyagePortoils.add(rawVoyagePortoil);
            });
            String finalByTaskAndShipIsDelete1 = byTaskAndShipIsDelete;
            String finalByTaskAndShipIsDeleteSecond1 = byTaskAndShipIsDeleteSecond;
            rawVoyagePortoilRepository.saveAll(rawVoyagePortoils);
            // 添加载荷消息
            voyagePortDto.getVolagePortLoadingInfos().forEach(voyagePortLordingDto -> {
                RawVoyagePortloading rawVoyagePortloading = new RawVoyagePortloading();
                BeanUtils.copyProperties(voyagePortLordingDto, rawVoyagePortloading);
                rawVoyagePortloading.setTaskId(finalByTaskAndShipIsDelete1);
                rawVoyagePortloading.setTaskIdNext(finalByTaskAndShipIsDeleteSecond1);
                rawVoyagePortloading.setPortinfoId(saveRawVoyagePort.getId());
                rawVoyagePortloading.setShipId(voyagePortDto.getShipId());
                rawVoyagePortloading.setIsDelete(0);
                rawVoyagePortloading.setRecStatus("0");
                rawVoyagePortloadings.add(rawVoyagePortloading);
            });
            rawVoyagePortloadingRepository.saveAll(rawVoyagePortloadings);
            boolean resultVo = saveAddOutSludge(voyagePortDto, saveRawVoyagePort);
//            if (!resultVo) {
//                throw new ExplicitException(this.msg("add.time.file"));
//            }
            if (null != saveRawVoyagePort) {
                return new ResultStringVo(saveRawVoyagePort.getId());
            } else {
                return new ResultStringVo(this.msg("energyEeffic.create.failed"));
            }
        }

    }

    @Override
    @Transactional
    public ResultVo createAndUpdateIceClassAndRescue(RawVoyageSpecDto rawVoyageSpecDto) {
        RawVoyageSpec byIdAndIsDelete = rawVoyageSpecRepository.findByIdAndIsDelete(rawVoyageSpecDto.getId(), 0);
        Date endTm = rawVoyageSpecDto.getEndTm();
        Calendar instance = Calendar.getInstance();
        instance.setTime(endTm);
        List<String> listByShipIsDelete = oiShipTaskRepository.findListByShipIsDelete(rawVoyageSpecDto.getShipId(),
                rawVoyageSpecDto.getVoyageCode());
        String byTaskAndShipIsDelete = null;
        if (listByShipIsDelete != null && listByShipIsDelete.size() > 0) {
            byTaskAndShipIsDelete = listByShipIsDelete.get(0);
        }
        if (null == byTaskAndShipIsDelete) {
            OiShipTask oiShipTask = new OiShipTask();
            oiShipTask.setTask(rawVoyageSpecDto.getVoyageCode());
            oiShipTask.setShipId(rawVoyageSpecDto.getShipId());
            oiShipTask.setRecStatus("0");
            oiShipTask.setIsDelete(0);
            OiShipTask save = oiShipTaskRepository.save(oiShipTask);
            byTaskAndShipIsDelete = save.getId();
        } else {
            OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(byTaskAndShipIsDelete, 0);
            if (oiShipTask.getRecStatus().equals("1")) {
                return new ResultErrorVo(this.msg("task.lock.status"));
            }
        }
        if (null != byIdAndIsDelete) {
            BeanUtils.copyProperties(rawVoyageSpecDto, byIdAndIsDelete);
            byIdAndIsDelete.setTaskId(byTaskAndShipIsDelete);
            List<RawVoyageSpecConsDto> volageSpecCons = rawVoyageSpecDto.getVolageSpecCons();
            List<RawVoyageSpecCons> rawVoyageSpecCons = byIdAndIsDelete.getRawVoyageSpecCons();
            for (int i = 0; i < volageSpecCons.size(); i++) {
                RawVoyageSpecConsDto rawVoyageSpecConsDto = volageSpecCons.get(i);
                BigDecimal outTons = rawVoyageSpecConsDto.getOutTons();
                Date outTm = rawVoyageSpecConsDto.getOutTm();
                if (outTm != null) {
                    if (!(rawVoyageSpecConsDto.getOutTm().getTime() >= rawVoyageSpecDto.getBeginTm().getTime()
                            && rawVoyageSpecConsDto.getOutTm().getTime() <= rawVoyageSpecDto.getEndTm().getTime())) {
                        return new ResultErrorVo(this.msg("add.time.file"));
                    }
                } else {
                    if (outTons != null && outTons.compareTo(BigDecimal.ZERO) > 0) {
                        return new ResultErrorVo(this.msg("add.time.file"));
                    }
                }
                String id = volageSpecCons.get(i).getId();
                RawVoyageSpecCons byIdAndIsDelete1 = rawVoyageSpecConsRepository.findByIdAndIsDelete(id, 0);
                if (byIdAndIsDelete1 != null) {
                    for (int j = 0; j < rawVoyageSpecCons.size(); j++) {
                        if (id.equals(rawVoyageSpecCons.get(j).getId())) {
                            BeanUtils.copyProperties(volageSpecCons.get(i), rawVoyageSpecCons.get(j));
                            rawVoyageSpecCons.get(j).setTaskId(byTaskAndShipIsDelete);
                            rawVoyageSpecCons.get(j).setShipId(rawVoyageSpecDto.getShipId());
                        }
                    }
                } else {
                    RawVoyageSpecCons rawVoyageSpecCons1 = new RawVoyageSpecCons();
                    BeanUtils.copyProperties(volageSpecCons.get(i), rawVoyageSpecCons1);
                    rawVoyageSpecCons1.setSpecId(byIdAndIsDelete.getId());
                    rawVoyageSpecCons1.setShipId(rawVoyageSpecDto.getShipId());
                    rawVoyageSpecCons1.setTaskId(byTaskAndShipIsDelete);
                    rawVoyageSpecCons1.setRecStatus("0");
                    rawVoyageSpecCons1.setIsDelete(0);
                    rawVoyageSpecConsRepository.save(rawVoyageSpecCons1);
                }
            }
            RawVoyageSpec save = rawVoyageSpecRepository.save(byIdAndIsDelete);
            if (null != save) {
                return new ResultStringVo(this.msg("energyEeffic.update.success"));
            } else {
                return new ResultStringVo(this.msg("energyEeffic.update.failed"));
            }
        } else {
            RawVoyageSpec rawVoyageSpec = new RawVoyageSpec();
            BeanUtils.copyProperties(rawVoyageSpecDto, rawVoyageSpec);
            rawVoyageSpec.setIsDelete(0);
            rawVoyageSpec.setRecStatus("0");
            rawVoyageSpec.setTaskId(byTaskAndShipIsDelete);
            RawVoyageSpec saveRawVoyageSpec = rawVoyageSpecRepository.save(rawVoyageSpec);
            List<RawVoyageSpecCons> rawVoyageSpecCons = new ArrayList<>();
            String finalByTaskAndShipIsDelete = byTaskAndShipIsDelete;
            List<RawVoyageSpecConsDto> volageSpecCons = rawVoyageSpecDto.getVolageSpecCons();
            for (int i = 0; i < volageSpecCons.size(); i++) {
                RawVoyageSpecConsDto rawVoyageSpecConsDto = volageSpecCons.get(i);
                BigDecimal outTons = rawVoyageSpecConsDto.getOutTons();
                Date outTm = rawVoyageSpecConsDto.getOutTm();
                if (outTm != null) {
                    if (!(rawVoyageSpecConsDto.getOutTm().getTime() >= rawVoyageSpecDto.getBeginTm().getTime()
                            && rawVoyageSpecConsDto.getOutTm().getTime() <= rawVoyageSpecDto.getEndTm().getTime())) {
                        return new ResultErrorVo(this.msg("add.time.file"));
//                        throw new ExplicitException(this.msg("add.time.file"));
                    }
                } else {
                    if (outTons != null && outTons.compareTo(BigDecimal.ZERO) > 0) {
                        return new ResultErrorVo(this.msg("add.time.file"));
                    }
//                    throw new ExplicitException(this.msg("add.time.file"));
                }
                RawVoyageSpecCons rawVoyageSpecCons1 = new RawVoyageSpecCons();
                BeanUtils.copyProperties(rawVoyageSpecConsDto, rawVoyageSpecCons1);
                rawVoyageSpecCons1.setSpecId(saveRawVoyageSpec.getId());
                rawVoyageSpecCons1.setShipId(rawVoyageSpecDto.getShipId());
                rawVoyageSpecCons1.setTaskId(finalByTaskAndShipIsDelete);
                rawVoyageSpecCons1.setIsDelete(0);
                rawVoyageSpecCons1.setRecStatus("0");
                rawVoyageSpecCons.add(rawVoyageSpecCons1);
            }
            rawVoyageSpecConsRepository.saveAll(rawVoyageSpecCons);
            if (null != saveRawVoyageSpec) {
                return new ResultStringVo(this.msg("energyEeffic.create.success"));
            } else {
                return new ResultStringVo(this.msg("energyEeffic.create.failed"));
            }
        }
    }

    @Override
    public ResultVo deleteVolageList(DeleteVoyageListDto deleteVoyageListDto) {
        // 锁定航次不能删除
        List<OiShipTask> oiShipTasks = (List<OiShipTask>) oiShipTaskRepository
                .findAllById(deleteVoyageListDto.getVolageIdList());
        oiShipTasks.forEach(oiShipTask -> {
            oiShipTask.setIsDelete(1);
            oiShipTask.getOiShipVoyages().forEach(oiShipVoyage -> {
                oiShipVoyage.setIsDelete(1);
            });
            oiShipTask.getRawVoyagePorts().forEach(rawVoyagePort -> {
                rawVoyagePort.setIsDelete(1);
                rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
                    rawVoyagePortoil.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                    rawVoyagePortloading.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {
                    rawVoyageAddoil.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {
                    rawVoyageOutoil.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
                    rawVoyageSludge.setIsDelete(1);
                });

            });
            oiShipTask.getRawVoyagePortsNext().forEach(rawVoyagePort -> {
                rawVoyagePort.setIsDelete(1);
                rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
                    rawVoyagePortoil.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                    rawVoyagePortloading.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {
                    rawVoyageAddoil.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {
                    rawVoyageOutoil.setIsDelete(1);
                });
                rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
                    rawVoyageSludge.setIsDelete(1);
                });
            });
            oiShipTask.getRawVoyageSpecs().forEach(rawVoyageSpec -> {
                rawVoyageSpec.setIsDelete(1);
                rawVoyageSpec.getRawVoyageSpecCons().forEach(rawVoyageSpecCons -> {
                    rawVoyageSpecCons.setIsDelete(1);
                });
            });
        });
        Iterable<OiShipTask> oiShipTasks1 = oiShipTaskRepository.saveAll(oiShipTasks);
        if (oiShipTasks1 != null) {
            return new ResultStringVo(this.msg("energyEeffic.delete.success"));
        } else {
            return new ResultStringVo(this.msg("energyEeffic.delete.failed"));
        }
    }

    @Override
//    public ResultVo deleteVolagePortList(DeleteVoyageListDto deleteVoyageListDto) {
//        List<RawVoyagePort> rawVoyagePorts = (List<RawVoyagePort>) rawVoyagePortRepository.findAllById(deleteVoyageListDto.getVolagePortIdList());
//        rawVoyagePorts.forEach(rawVoyagePort -> {
//            if (null != rawVoyagePort.getTaskId() && null == rawVoyagePort.getTaskIdNext()) {
//                rawVoyagePort.setIsDelete(1);
//                rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
//                    rawVoyagePortoil.setIsDelete(1);
//                });
//                rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
//                    rawVoyagePortloading.setIsDelete(1);
//                });
//                rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {
//                    rawVoyageAddoil.setIsDelete(1);
//                });
//                rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {
//                    rawVoyageOutoil.setIsDelete(1);
//                });
//                rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
//                    rawVoyageSludge.setIsDelete(1);
//                });
//            }
//        });
//        Iterable<RawVoyagePort> rawVoyagePorts1 = rawVoyagePortRepository.saveAll(rawVoyagePorts);
//        if (rawVoyagePorts1 != null) {
//            return new ResultStringVo(this.msg("energyEeffic.delete.success"));
//        } else {
//            return new ResultStringVo(this.msg("energyEeffic.delete.failed"));
//        }
//    }
    public ResultVo deleteVolagePortList(DeleteVoyageListDto deleteVoyageListDto) {
        List<RawVoyagePort> rawVoyagePorts = (List<RawVoyagePort>) rawVoyagePortRepository
                .findAllById(deleteVoyageListDto.getVolagePortIdList());
        rawVoyagePorts.forEach(rawVoyagePort -> {
            rawVoyagePort.setIsDelete(1);
            rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
                rawVoyagePortoil.setIsDelete(1);
            });
            rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                rawVoyagePortloading.setIsDelete(1);
            });
            rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {
                rawVoyageAddoil.setIsDelete(1);
            });
            rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {
                rawVoyageOutoil.setIsDelete(1);
            });
            rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
                rawVoyageSludge.setIsDelete(1);
            });
        });
        Iterable<RawVoyagePort> rawVoyagePorts1 = rawVoyagePortRepository.saveAll(rawVoyagePorts);
        if (rawVoyagePorts1 != null) {
            return new ResultStringVo(this.msg("energyEeffic.delete.success"));
        } else {
            return new ResultStringVo(this.msg("energyEeffic.delete.failed"));
        }
    }

    @Override
    public ResultVo deleteVolageIceClassAndRescueList(DeleteVoyageListDto deleteVoyageListDto) {
        List<RawVoyageSpec> rawVoyageSpecs = (List<RawVoyageSpec>) rawVoyageSpecRepository
                .findAllById(deleteVoyageListDto.getVolageIceAndRescueIdList());
        rawVoyageSpecs.forEach(rawVoyageSpec -> {
            rawVoyageSpec.setIsDelete(1);
            rawVoyageSpec.getRawVoyageSpecCons().forEach(rawVoyageSpecCons -> {
                rawVoyageSpecCons.setIsDelete(1);
            });
        });
        Iterable<RawVoyageSpec> rawVoyageSpecs1 = rawVoyageSpecRepository.saveAll(rawVoyageSpecs);
        if (rawVoyageSpecs1 != null) {
            return new ResultStringVo(this.msg("energyEeffic.delete.success"));
        } else {
            return new ResultStringVo(this.msg("energyEeffic.delete.failed"));
        }
    }

    @Override
    public ResultVo updateVolageStatus(UpdateVoyageRecStatusDto updateVoyageRecStatusDto) {
        OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(updateVoyageRecStatusDto.getVolageId(), 0);
        String recStatus = updateVoyageRecStatusDto.getRecStatus();
        if (oiShipTask != null) {
            oiShipTask.setRecStatus(recStatus);
            oiShipTask.getRawVoyagePorts().forEach(rawVoyagePort -> {
                rawVoyagePort.setRecStatus(recStatus);
                rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
                    rawVoyagePortoil.setRecStatus(recStatus);
                });
                rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                    rawVoyagePortloading.setRecStatus(recStatus);
                });
                rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {
                    rawVoyageAddoil.setRecStatus(recStatus);
                });
                rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {
                    rawVoyageOutoil.setRecStatus(recStatus);
                });
                rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
                    rawVoyageSludge.setRecStatus(recStatus);
                });
            });
            oiShipTask.getRawVoyageSpecs().forEach(rawVoyageSpec -> {
                rawVoyageSpec.setRecStatus(recStatus);
                rawVoyageSpec.getRawVoyageSpecCons().forEach(rawVoyageSpecCons -> {
                    rawVoyageSpecCons.setRecStatus(recStatus);
                });
            });
        }
        OiShipTask save = oiShipTaskRepository.save(oiShipTask);
        if (null != save) {
            return new ResultStringVo(this.msg("energyEeffic.update.recstatus.success"));
        } else {
            return new ResultStringVo(this.msg("energyEeffic.update.recstatus.failed"));
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ResultVo generatorVolageInfo(DeleteVoyageListDto deleteVoyageListDto) {
        List<String> volageIdList = new ArrayList<>();
        deleteVoyageListDto.getGeneratorVoyageDtos().forEach(generatorVolageDto -> {
            if (generatorVolageDto.getVolageAndSegmentType() == 0) {
                volageIdList.add(generatorVolageDto.getId());
            }
        });
        try {
            for (int i = 0; i < volageIdList.size(); i++) {
                String volageId = volageIdList.get(i);
                OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(volageId, 0);
                List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
                oiShipVoyages.forEach(oiShipVoyage -> {
                    oiShipVoyage.setIsDelete(1);
                });
                oiShipVoyageRepository.saveAll(oiShipVoyages);
                List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
                List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
                rawVoyagePorts.addAll(rawVoyagePortsNext);
                if (rawVoyagePorts.size() < 2) {
                    return new ResultErrorVo(this.msg("energyEeffic.volage.rawPortNum"));
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
                    return new ResultErrorVo(this.msg("energyEeffic.volage.startRawPortInert"));
                }
                if (!"0".equals(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getInPort())) {
                    return new ResultErrorVo(this.msg("energyEeffic.volage.endRawPortInert"));
                }
                //对交接港口进行合并
                List<RawVoyagePort> rawVoyagePortsMergeHandover = mergeHandoverPort(rawVoyagePorts);
                //合并港界内移泊的港口
                List<RawVoyagePort> rawVoyagePortsMerge = procVoyageByMergePorts(rawVoyagePortsMergeHandover);
                generatorVolageInfo(oiShipTask, rawVoyagePortsMerge);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResultErrorVo(this.msg("energyEeffic.volage.generator.failed"));
        }
        return new ResultStringVo(this.msg("energyEeffic.volage.generator.success"));
    }

    /**
     * 异步生成航次航段
     *
     * @param deleteVoyageListDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ResultVo generatorVoyageInfoAsynchronous(DeleteVoyageListDto deleteVoyageListDto) {
        List<String> volageIdList = new ArrayList<>();
        deleteVoyageListDto.getGeneratorVoyageDtos().forEach(generatorVolageDto -> {
            if (generatorVolageDto.getVolageAndSegmentType() == 0) {
                volageIdList.add(generatorVolageDto.getId());
            }
        });
        try {
            for (int i = 0; i < volageIdList.size(); i++) {
                String volageId = volageIdList.get(i);
                OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(volageId, 0);
                List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
                oiShipVoyages.forEach(oiShipVoyage -> {
                    oiShipVoyage.setIsDelete(1);
                });
                oiShipVoyageRepository.saveAll(oiShipVoyages);
                List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
                List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
                rawVoyagePorts.addAll(rawVoyagePortsNext);
                if (rawVoyagePorts.size() < 2) {
                    return new ResultErrorVo(this.msg("energyEeffic.volage.rawPortNum"));
                }
                rawVoyagePorts.sort((x, y) -> {
                    if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                        return x.getCreateTm().compareTo(y.getCreateTm());
                    } else {
                        return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                    }
                });
                if (!"0".equals(rawVoyagePorts.get(0).getInPort())) {
                    return new ResultErrorVo("出发港不是港界内");
                }
                if (!"0".equals(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getInPort())) {
                    return new ResultErrorVo("结束港不是港界内");
                }
                //处理交接港口
                List<RawVoyagePort> rawVoyagePortsMergeNext = mergeHandoverPort(rawVoyagePorts);
                //合并港口
                List<RawVoyagePort> rawVoyagePortsMerge = procVoyageByMergePorts(rawVoyagePortsMergeNext);
                generatorVolageInfo(oiShipTask, rawVoyagePortsMerge);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultErrorVo("生成航次航段失败");
        }
        return new ResultStringVo("执行成功");
    }

    @Override
    public Map<String, String> checkToken(String timestamp, String token) {
        // Auto-generated method stub
        // 定义返回对象
        Map resMap = new HashMap<String, String>();
        Gson gson = new Gson();
        String decryptToken = "";
        String orgCode = "";
        Pattern pattern = Pattern.compile("[0-9]*");
        // 当时间戳为空、时间戳不是纯数字
        if ("".equals(timestamp) || timestamp == null || !pattern.matcher(timestamp).matches()) {
            resMap.put("respCode", "10003");
            resMap.put("respMsg", "Interface request failed with incorrect timestamp. 接口请求失败，时间戳不正确.");
            return resMap;
        }
        // 时间戳不在当前时间的15分钟内，则返回代码10003，该部分代码对船端时，可以不验证，应对船端时间不正确的情况
//        if (Long.parseLong(timestamp) > (System.currentTimeMillis() + 900000)
//                || Long.parseLong(timestamp) < (System.currentTimeMillis() - 900000)) {
//            resMap.put("respCode", "10003");
//            resMap.put("respMsg", "Interface request failed, request time out of range. 接口请求失败，时间不正确.");
//            return resMap;
//        }

        String adminToken = timestamp + "CCSEeeoi"; // CCS管理用token
        MD5 md5 = new MD5(adminToken);
        adminToken = md5.asHex(); // 加密后的字符串
        if (adminToken.equals(token) || adminToken.toUpperCase().equals(token)) {
            resMap.put("respCode", "0");
            resMap.put("respMsg", "admin. 管理权限.");
        } else {
            try {
                decryptToken = Des8Util.decrypt(token);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("解密token失败", token);
                resMap.put("respCode", "10001");
                resMap.put("respMsg", "Authentication token decryption failed. 鉴权信息解密失败.");
                return resMap;
            }
            orgCode = decryptToken.replace(timestamp, "");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            // 获取licence
            String licence = orgCode.substring(1);
            // 解密licence
            String decryptLicense = null;
            try {
                decryptLicense = LicenseUtils.decrypt(licence, LICENSE_KEY);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("解密license失败", licence);
                resMap.put("respCode", "10001");
                resMap.put("respMsg", "Authentication license decryption failed. 解密license失败");
                return resMap;
            }
            // 反序列化license
            License deserializeLicense = LicenseUtils.deserialize(decryptLicense);
            resMap.put("license", gson.toJson(deserializeLicense));
            if ("C".equals(deserializeLicense.getType())) {
                resMap.put("respCode", "1");
                resMap.put("clientCode", orgCode.substring(1));
            } else if ("S".equals(deserializeLicense.getType())) {
                resMap.put("respCode", "2");
                resMap.put("clientCode", orgCode.substring(1));
            } else {
                resMap.put("respCode", "20002");
                resMap.put("respMsg", "接口请求成功，报文中未包含有效数据");
            }
        } else {
            resMap.put("respCode", "20002");
            resMap.put("respMsg", "接口请求成功，报文中未包含有效数据");
        }
        return resMap;
    }

    @Override
    @Transactional
    public Map<String, Object> reportMrvData(String voyageData, String timestamp, String token, HttpServletRequest request, HttpServletResponse response) {
        log.info("voyageData:" + voyageData);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar instance = Calendar.getInstance();

        MrvDataVo mrvDataVo = new MrvDataVo("", "10001", "Interface request failed, data imported failed ,数据导入失败");

        HashMap<String, Object> map = new HashMap<>();

        List<ReaportGenerateVoyageInfoDto> reaportGenerateVoyageInfoDtos = new ArrayList<>();
        int length = voyageData.length();
        String oriReqData = null;
        if (length > 3000) {
            oriReqData = voyageData.substring(0, 3000);
        } else {
            oriReqData = voyageData.substring(0, length - 1);
        }
        try {
            voyageData = new String(voyageData.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("json编码失败={}", voyageData);
            mrvDataVo.setRespMsg("json编码失败");
            saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);
            throw new ExplicitException("json编码失败");
        }

        Map<String, String> resultMap = checkToken(timestamp, token);
//        Map<String, String> resultMap = new HashMap<>();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        List<String> checkCodes = Arrays.asList("0", "1", "2");

        if (!checkCodes.contains(resultMap.get("respCode"))) {

            mrvDataVo.setRespMsg(resultMap.get("respMsg"));

            mrvDataVo.setRespcode(resultMap.get("respCode"));

            map.put(MRV_DATA, mrvDataVo);

            map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);
            saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);
            return map;

        }

        License license = gson.fromJson(resultMap.get("license"), License.class);

        try {
            if (Arrays.asList(PERMISSION_CODE.split(",")).contains(resultMap.get("respCode"))) {

                // 解析报文
                VoyageDataDto voyageDataDto = new VoyageDataDto();

                String taskNu = null;

                voyageData = voyageData.replace("\"\"", "null");

                try {

                    voyageDataDto = gson.fromJson(voyageData, VoyageDataDto.class);

                } catch (Exception e) {

                    e.printStackTrace();
                    log.error("请求报文格式错误={}", voyageData);
                    mrvDataVo.setRespMsg("Request message format error. 请求报文格式错误.");
                    map.put(MRV_DATA, mrvDataVo);
                    map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);
                    saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);
                    return map;

                }

                // 参数类进行转换
                //fix by cg 202010302  解决接口上传数据重复的情况
                List<VoyagePortDto> voyagePortDtos = new ArrayList<>();
                List<String> volageCodes = new ArrayList<>();

                voyageDataDto.getVoyagePorts().forEach(reaportVoyagePortDto -> {

                    VoyagePortDto voyagePortDto = new VoyagePortDto();

                    BeanUtils.copyProperties(reaportVoyagePortDto, voyagePortDto);

                    voyagePortDto.setFirstVolageCode(reaportVoyagePortDto.getFirstVoyageCode());

                    voyagePortDto.setSecondVolageCode(reaportVoyagePortDto.getSecondVoyageCode());

                    voyagePortDto.setIsEu(!"0".equals(reaportVoyagePortDto.getIsEu()));

                    voyagePortDto.setVoyagePortAddOilDtos(reaportVoyagePortDto.getVoyageAddoils());

                    voyagePortDto.setVoyagePortOutOilDtos(reaportVoyagePortDto.getVoyageOutoils());

                    voyagePortDto.setVoyagePortOutSulageOilDtos(reaportVoyagePortDto.getVoyageSludges());

                    voyagePortDto.setPortOilInfos(reaportVoyagePortDto.getVoyagePortoils());

                    voyagePortDto.setVolagePortLoadingInfos(reaportVoyagePortDto.getVoyagePortloadings());

                    voyagePortDto.setInPort("0".equals(reaportVoyagePortDto.getInPort()));

                    String portWork = reaportVoyagePortDto.getPortWork();

                    if (portWork != null) {

                        voyagePortDto.setAnchorPurpose(Arrays.asList(reaportVoyagePortDto.getPortWork().split(",")));

                    }


//                     voyagePortDtos.add(voyagePortDto);
                    String key = Optional.ofNullable(reaportVoyagePortDto.getFirstVoyageCode()).orElse("")
                            + "-" + Optional.ofNullable(reaportVoyagePortDto.getSecondVoyageCode()).orElse("")
                            + "-" + Optional.ofNullable(reaportVoyagePortDto.getRefCode()).orElse("");
                    if (!volageCodes.contains(key)) {
                        log.error("voyagePortDto: {volageCode:" + voyagePortDto.getFirstVolageCode() + "}");
                        voyagePortDtos.add(voyagePortDto);
                        volageCodes.add(key);
                    }
                });

                List<RawVoyageSpecDto> voyageSpecs = voyageDataDto.getVoyageSpecs();

                if (voyageSpecs == null) {

                    voyageSpecs = new ArrayList<>();

                }

                // 按照imo号和船检注册号进行分组

                Map<String, List<VoyagePortDto>> shipGroupMap = voyagePortDtos.stream()
                        .collect(Collectors.groupingBy(voyagePortDto -> voyagePortDto.getImoNo()));

                // 按照航次号1进行分组

                Map<String, List<VoyagePortDto>> shipGroupMapFirstCode = voyagePortDtos.stream()
                        .collect(Collectors.groupingBy(voyagePortDto -> voyagePortDto.getFirstVolageCode()));

                Map<String, List<RawVoyageSpecDto>> shipGroupMapFirstCodeSpec = voyageSpecs.stream()
                        .collect(Collectors.groupingBy(rawVoyageSpecDto -> rawVoyageSpecDto.getFirstVoyageCode()));

                List<String> imoNus = new ArrayList<>(shipGroupMap.keySet());

                // 校验权限
                String respCode = resultMap.get("respCode");

                Set<String> shipComPanyShipIds = new HashSet<>();

                if ("1".equals(respCode)) {

                    // 获取公司的船舶列表

                    // 获取公司的船舶列表
                    List<GcClient> gcClientList = gcClientRepository.findListByCodeAndIsDelete(license.getKey());
                    List<String> gcClientIds = new ArrayList<>();
                    for (GcClient gcClient : gcClientList) {
                        gcClientIds.add(gcClient.getId());
                    }

                    shipComPanyShipIds = this.getShipComPanyShipIds(gcClientIds);

                    if (gcClientList == null || gcClientList.size() == 0) {

                        mrvDataVo.setRespcode("10002");

                        mrvDataVo.setRespMsg(
                                "Interface request failed, validation failed, company or ship does not exist 接口请求失败，验证失败 船公司不存在");

                        saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);

                        map.put(MRV_DATA, mrvDataVo);

                        map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);

                        return map;

                    }
                    OiShipInfo oiShipInfo = null;
                    for (int i = 0; i < imoNus.size(); i++) {
                        String s = imoNus.get(i);
                        oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(s, s, 0);
                        if (!shipComPanyShipIds.contains(oiShipInfo.getId())) {

                            mrvDataVo.setRespcode("11001");

                            mrvDataVo.setRespMsg(
                                    "The interface request failed and the ship does not belong to the company 接口请求失败，船舶不属于该公司");

                            mrvDataVo.setImoNo(s);

                            saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);

                            map.put(MRV_DATA, mrvDataVo);

                            map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);

                            return map;

                        }

                    }

                }

                if ("2".equals(respCode)) {

                    OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(license.getKey(),
                            license.getKey(), 0);

                    VoyagePortDto voyagePortDto = voyagePortDtos.get(0);

                    if (!license.getKey().equals(voyagePortDto.getImoNo())) {

                        mrvDataVo.setRespcode("10002");

                        mrvDataVo.setRespMsg(
                                "The interface request failed and the ship does not belong to the company 接口请求失败，license船舶imoNo和港口数据中的船舶imoNo不一致");

                        saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);

                        map.put(MRV_DATA, mrvDataVo);

                        map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);

                        return map;

                    }

                    if (null == oiShipInfo) {

                        mrvDataVo.setRespcode("10002");

                        mrvDataVo.setRespMsg("The interface request failed and the ship does not exist 接口请求失败，船舶不存在");

                        saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);

                        map.put(MRV_DATA, mrvDataVo);

                        map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);

                        return map;

                    }

                }

                // 操作期初量

                List<ReaptPeriodOilsDto> periods = voyageDataDto.getPeriods();

                for (int i = 0; periods != null && i < periods.size(); i++) {

                    ReaptPeriodOilsDto reaptPeriodOilsDto = periods.get(i);

                    BeginPeriod beginPeriodNew = new BeginPeriod();

                    String imoNo = reaptPeriodOilsDto.getImoNo();

                    OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(imoNo, imoNo, 0);

                    if (null == oiShipInfo) {

                        mrvDataVo.setRespcode("10002");

                        mrvDataVo.setRespMsg("The interface request failed and the ship does not exist 接口请求失败，船舶不存在");

                        saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);

                        map.put(MRV_DATA, mrvDataVo);

                        map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);

                        return map;

                    }

                    if ("1".equals(respCode)) {

                        if (!shipComPanyShipIds.contains(oiShipInfo.getId())) {

                            mrvDataVo.setRespcode("11001");

                            mrvDataVo.setRespMsg(
                                    "The interface request failed and the ship does not belong to the company 接口请求失败，船舶不属于该公司");

                            mrvDataVo.setImoNo(imoNo);

                            saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);

                            map.put(MRV_DATA, mrvDataVo);

                            map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);

                            return map;

                        }

                    }

                    Date periodTime = reaptPeriodOilsDto.getPeriodTime();

                    beginPeriodNew.setIsDelete(0);

                    beginPeriodNew.setRecStatus("0");

                    beginPeriodNew.setPeriodTime(periodTime);

                    beginPeriodNew.setShipId(oiShipInfo.getId());

                    List<ReaptBeginPeriodOilDto> beginPeriodOils = reaptPeriodOilsDto.getBeginPeriodOils();

                    BeginPeriod beginPeriod = beginPeriodRepository.findByPeriodTimeAndIsDelete(oiShipInfo.getId(),
                            simpleDateFormat.format(periodTime));

                    if (beginPeriod != null && beginPeriod.getRecStatus().equals("0")) {
                        beginPeriod.setIsDelete(1);
                        List<BeginPeriodOil> beginPeriodOils1 = beginPeriod.getBeginPeriodOils();
                        for (int j = 0; beginPeriodOils1 != null && j < beginPeriodOils1.size(); j++) {
                            BeginPeriodOil beginPeriodOil = beginPeriodOils1.get(j);
                            beginPeriodOil.setIsDelete(1);
                        }
                        beginPeriodRepository.save(beginPeriod);
                    }

                    if (beginPeriod == null || beginPeriod.getRecStatus().equals("0")) {

                        BeginPeriod save = beginPeriodRepository.save(beginPeriodNew);

                        List<BeginPeriodOil> oils = new ArrayList<>();

                        for (int j = 0; j < beginPeriodOils.size(); j++) {

                            ReaptBeginPeriodOilDto reaptBeginPeriodOilDto = beginPeriodOils.get(j);

                            BeginPeriodOil beginPeriodOil = new BeginPeriodOil();

                            beginPeriodOil.setIsDelete(0);

                            beginPeriodOil.setRecStatus("0");

                            beginPeriodOil.setOilId(reaptBeginPeriodOilDto.getOilId());

                            beginPeriodOil.setOilName(reaptBeginPeriodOilDto.getOilName());

                            beginPeriodOil.setFuelTons(reaptBeginPeriodOilDto.getBeginTons());

                            beginPeriodOil.setPeriodTime(periodTime);

                            beginPeriodOil.setShipId(oiShipInfo.getId());

                            beginPeriodOil.setBeginPeriodId(save.getId());

                            oils.add(beginPeriodOil);

                        }

                        beginPeriodOilRepository.saveAll(oils);

                    }

                }

                // 按航次进行操作

                for (Map.Entry<String, List<VoyagePortDto>> entry : shipGroupMapFirstCode.entrySet()) {
                    ReaportGenerateVoyageInfoDto reaportGenerateVoyageInfoDto = new ReaportGenerateVoyageInfoDto();

                    taskNu = entry.getKey();

                    List<VoyagePortDto> voyagePortDtosTask = entry.getValue();

                    voyagePortDtosTask.sort((x, y) -> {

                        Date deptTmX = x.getDeptTm();

                        Date deptTmY = y.getDeptTm();

                        return deptTmX.compareTo(deptTmY);
                    });
                    List<RawVoyageSpecDto> rawVoyageSpecDtos = shipGroupMapFirstCodeSpec.get(taskNu);

                    if (rawVoyageSpecDtos == null) {
                        rawVoyageSpecDtos = new ArrayList<>();
                    }

                    // 校验港口信息

                    List<String> fuelCode = fuelRepository.findFuelCode();

                    for (int i = 0; i < voyagePortDtosTask.size(); i++) {

                        VoyagePortDto voyagePortDto1 = voyagePortDtosTask.get(i);

                        List<VoyagePortOilDto> portOilInfos = voyagePortDto1.getPortOilInfos();

                        List<VoyagePortLordingDto> volagePortLoadingInfos = voyagePortDto1.getVolagePortLoadingInfos();

                        if (volagePortLoadingInfos == null || volagePortLoadingInfos.size() != 2) {

                            mrvDataVo.setRespcode("11002");

                            mrvDataVo.setRespMsg("Interface request failed,firstVoyageCode:" + taskNu
                                    + " one port must have two sets of arrival and departure loading information.接口请求失败，一个途经港必需有抵离港载货量两套信息.");

                            saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), taskNu);

                            throw new ExplicitException(mrvDataVo.getRespMsg());

                        }

                        if (portOilInfos == null || portOilInfos.size() < 0) {

                            mrvDataVo.setRespcode("11002");

                            mrvDataVo.setRespMsg("Interface request failed, firstVoyageCode:" + taskNu
                                    + "there is no fuel information in port data of voyage.接口请求失败，航次途经港数据里没有燃油信息");

                            saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), taskNu);

                            throw new ExplicitException(mrvDataVo.getRespMsg());

                        }

                        for (int j = 0; j < portOilInfos.size(); j++) {

                            VoyagePortOilDto voyagePortOilDto = portOilInfos.get(j);

                            if (voyagePortOilDto.getOilId() == null || !fuelCode.contains(voyagePortOilDto.getOilId())) {

                                mrvDataVo.setRespcode("11003");

                                mrvDataVo.setRespMsg("Interface request failed,firstVoyageCode:" + taskNu
                                        + " fuel oil code is incorrect. 接口请求失败，燃油代码不正确");

                                saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), taskNu);

                                throw new ExplicitException(mrvDataVo.getRespMsg());

                            }

                        }

                    }

                    // 校验冰区航行救援燃油代码

                    for (int i = 0; i < rawVoyageSpecDtos.size(); i++) {

                        RawVoyageSpecDto rawVoyageSpecDto = rawVoyageSpecDtos.get(i);

                        List<RawVoyageSpecConsDto> volageSpecCons = rawVoyageSpecDto.getVolageSpecCons();

                        for (int j = 0; j < volageSpecCons.size(); j++) {

                            RawVoyageSpecConsDto rawVoyageSpecConsDto = volageSpecCons.get(j);

                            if (rawVoyageSpecConsDto.getOilId() == null
                                    || !fuelCode.contains(rawVoyageSpecConsDto.getOilId())) {

                                mrvDataVo.setRespcode("11003");

                                mrvDataVo.setRespMsg("Interface request failed," + taskNu
                                        + " fuel oil code is incorrect. 接口请求失败，燃油代码不正确");

                                saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), taskNu);
                                throw new ExplicitException(mrvDataVo.getRespMsg());

                            }

                        }

                    }

                    VoyagePortDto voyagePortDto = voyagePortDtosTask.get(0);

                    instance.setTime(voyagePortDto.getDeptTm());

                    OiShipInfo oiShipInfo = oiShipInfoRepository
                            .findByImonoOrRegisternoAndIsDelete(voyagePortDto.getImoNo(), voyagePortDto.getImoNo(), 0);

                    reaportGenerateVoyageInfoDto.setTaskNu(taskNu);

                    reaportGenerateVoyageInfoDto.setYear(instance.get(Calendar.YEAR));

                    reaportGenerateVoyageInfoDto.setOiShipInfo(oiShipInfo);

                    reaportGenerateVoyageInfoDtos.add(reaportGenerateVoyageInfoDto);

                    // 根据船舶id和航次号年份确定航次id
                    OiShipTask oiShipTask = null;
                    List<OiShipTask> oiShipTaskList = oiShipTaskRepository.findOiShipTaskListByShipIsDelete(
                            oiShipInfo.getId(), taskNu);
                    if (oiShipTaskList != null && oiShipTaskList.size() > 0) {
                        oiShipTask = oiShipTaskList.get(0);
                    }
                    if (oiShipTask != null) {
                        //校验航次状态，
                        String recStatus = oiShipTask.getRecStatus();
                        if ("1".equals(recStatus)) {
                            mrvDataVo.setRespMsg(oiShipTask.getTask() + " 航次状态已锁定，请解锁航次再次上报");
                            map.put(MRV_DATA, mrvDataVo);
                            saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), null);
                            throw new ExplicitException("航次状态锁定无法上报数据");
                        }
                        // 删除航次和航次的航段信息以及删除冰区航行信息
                        List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
                        List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
                        if (rawVoyagePorts != null) {
                            rawVoyagePorts.forEach(rawVoyagePort -> {

                                for (VoyagePortDto portDto : voyagePortDtosTask) {

                                    if (portDto.getRefCode().equals(rawVoyagePort.getRefCode())) {

                                        rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {

                                            rawVoyagePortoil.setIsDelete(1);

                                        });

                                        rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {

                                            rawVoyagePortloading.setIsDelete(1);

                                        });

                                        rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {

                                            rawVoyageAddoil.setIsDelete(1);

                                        });

                                        rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {

                                            rawVoyageOutoil.setIsDelete(1);

                                        });

                                        rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {

                                            rawVoyageSludge.setIsDelete(1);
                                        });

                                    }

                                }

                            });
                        }

                        if (oiShipVoyages != null) {
                            for (int i = 0; i < oiShipVoyages.size(); i++) {

                                OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);

                                oiShipVoyage.setIsDelete(1);

                            }
                        }

                        if (rawVoyageSpecDtos != null && rawVoyageSpecDtos.size() > 0) {

                            List<RawVoyageSpec> rawVoyageSpecs = oiShipTask.getRawVoyageSpecs();

                            for (int i = 0; i < rawVoyageSpecs.size(); i++) {

                                RawVoyageSpec rawVoyageSpec = rawVoyageSpecs.get(i);

                                rawVoyageSpec.setIsDelete(1);

                                List<RawVoyageSpecCons> rawVoyageSpecCons = rawVoyageSpec.getRawVoyageSpecCons();

                                for (int j = 0; j < rawVoyageSpecCons.size(); j++) {

                                    RawVoyageSpecCons rawVoyageSpecCons1 = rawVoyageSpecCons.get(i);

                                    rawVoyageSpecCons1.setIsDelete(1);

                                }

                            }

                        }

                        OiShipTask save = oiShipTaskRepository.save(oiShipTask);

                    }

                    // 保存港口信息
                    String finalOriReqData = oriReqData;
                    voyagePortDtosTask.forEach(voyagePortDto1 -> {
                        try {
                            voyagePortDto1.setShipId(oiShipInfo.getId());
                            createAndUpdatePortInfo(voyagePortDto1);
                        } catch (Exception e) {
                            log.error(e.getStackTrace().toString());
                            String portcn = voyagePortDto1.getPorten();
                            String firstVolageCode = voyagePortDto1.getFirstVolageCode();
                            String message = firstVolageCode + "航次" + portcn + "港口数据导入失败";
                            mrvDataVo.setRespMsg(message);
                            mrvDataVo.setRespcode("10001");
                            saveInterface(mrvDataVo.getRespcode(), finalOriReqData, mrvDataVo.getRespMsg(), firstVolageCode);
                            e.printStackTrace();
                            throw new ExplicitException(message);
                        }

                    });

                    // 保存冰区航行和救援信息

                    rawVoyageSpecDtos.forEach(rawVoyageSpecDto -> {
                        try {
                            rawVoyageSpecDto.setShipId(oiShipInfo.getId());

                            rawVoyageSpecDto.setVoyageCode(voyagePortDto.getFirstVolageCode());

                            createAndUpdateIceClassAndRescue(rawVoyageSpecDto);
                        } catch (Exception e) {
                            log.error(e.getStackTrace().toString());
                            String firstVolageCode = rawVoyageSpecDto.getFirstVoyageCode();
                            String message = firstVolageCode + "航次冰区航行或救援数据导入失败";
                            mrvDataVo.setRespMsg(message);
                            mrvDataVo.setRespcode("10001");
                            saveInterface(mrvDataVo.getRespcode(), finalOriReqData, mrvDataVo.getRespMsg(), firstVolageCode);
                            throw new ExplicitException(message);
                        }

                    });

                    mrvDataVo.setImoNo(oiShipInfo.getImono());

                    mrvDataVo.setFirstVoyageCode(taskNu);

                    mrvDataVo.setRespcode("200000");

                    mrvDataVo.setRespMsg("数据导入成功");

                    map.put(MRV_DATA, mrvDataVo);

                    map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);

                }
                saveInterface(mrvDataVo.getRespcode(), oriReqData, mrvDataVo.getRespMsg(), taskNu);
                return map;
            } else {
                map.put(MRV_DATA,
                        new MrvDataVo(new Date().toString(), resultMap.get("respCode"), resultMap.get("respMsg")));
                map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);
                return map;

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            map.put(MRV_DATA, mrvDataVo);
            map.put(REAPORT_DTO, reaportGenerateVoyageInfoDtos);
            return map;
        }

    }

    @Override
    public MrvDataVo reportdDeleteMrvData(String voyageData, String timestamp, String token, HttpServletRequest request,
                                          HttpServletResponse response) {
        MrvDataVo mrvDataVo = new MrvDataVo(new Date().toString(), "20000", "数据删除成功");
        try {
            voyageData = new String(voyageData.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("json编码失败={}", voyageData);
            throw new ExplicitException("json编码失败");
        }
        Map<String, String> resultMap = checkToken(timestamp, token);
        List<String> checkCodes = Arrays.asList("0", "1", "2");
        if (!checkCodes.contains(resultMap.get("respCode"))) {
            mrvDataVo.setRespMsg(resultMap.get("respMsg"));
            mrvDataVo.setRespcode(resultMap.get("respCode"));
            return mrvDataVo;
        }
        Gson gson = new Gson();
        License license = gson.fromJson(resultMap.get("license"), License.class);
        if (Arrays.asList(PERMISSION_CODE.split(",")).contains(resultMap.get("respCode"))) {
            // 解析报文
            ReaportDeletePortDto reaportDeletePortDto = new ReaportDeletePortDto();
            try {
                voyageData = voyageData.replace("\"\"", "null");
                try {
                    reaportDeletePortDto = gson.fromJson(voyageData, ReaportDeletePortDto.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("请求报文格式错误={}", voyageData);
                    saveInterface(null, voyageData, "请求报文格式错误", null);
                    return new MrvDataVo(new Date().toString(), "20002", "Request message format error. 请求报文格式错误.");
                }
                List<ReaportDeletePortDtos> deletePorts = reaportDeletePortDto.getDeletePorts();
                List<String> imoNus = new ArrayList<>();
                for (int i = 0; i < deletePorts.size(); i++) {
                    ReaportDeletePortDtos reaportDeletePortDtos = deletePorts.get(i);
                    imoNus.add(reaportDeletePortDtos.getImoNo());
                }
                // 校验权限
                String respCode = resultMap.get("respCode");
                if ("1".equals(respCode)) {
                    // 获取公司的船舶列表
                    List<GcClient> gcClientList = gcClientRepository.findListByCodeAndIsDelete(license.getKey());
                    List<String> gcClientIds = new ArrayList<>();
                    for (GcClient gcClient : gcClientList) {
                        gcClientIds.add(gcClient.getId());
                    }
                    Set<String> shipComPanyShipIds = this.getShipComPanyShipIds(gcClientIds);
                    if (gcClientList == null || gcClientList.size() == 0) {
                        mrvDataVo.setRespcode("10002");
                        mrvDataVo.setRespMsg(
                                "Interface request failed, validation failed, company or ship does not exist 接口请求失败，验证失败 船公司不存在");
                        saveInterface(mrvDataVo.getRespcode(), voyageData, mrvDataVo.getRespMsg(), null);
                        return mrvDataVo;
                    }

                    for (int i = 0; i < imoNus.size(); i++) {
                        String s = imoNus.get(i);
                        OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(s, s, 0);
                        if (!shipComPanyShipIds.contains(oiShipInfo.getId())) {
                            mrvDataVo.setRespcode("11001");
                            mrvDataVo.setRespMsg(
                                    "The interface request failed and the ship does not belong to the company 接口请求失败，船舶不属于该公司");
                            mrvDataVo.setImoNo(s);
                            saveInterface(mrvDataVo.getRespcode(), voyageData, mrvDataVo.getRespMsg(), null);
                            return mrvDataVo;
                        }
                    }
                }
                if ("2".equals(respCode)) {
                    OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(license.getKey(),
                            license.getKey(), 0);
                    String imoNu = null;
                    if (imoNus.size() > 0) {
                        imoNu = imoNus.get(0);
                    }
                    if (!license.getKey().equals(imoNu)) {
                        mrvDataVo.setRespcode("10002");
                        mrvDataVo.setRespMsg(
                                "The interface request failed and the ship does not belong to the company 接口请求失败，license船舶imoNo和港口数据中的船舶imoNo不一致");
                        saveInterface(mrvDataVo.getRespcode(), voyageData, mrvDataVo.getRespMsg(), null);
                        return mrvDataVo;
                    }
                    if (null == oiShipInfo) {
                        mrvDataVo.setRespcode("10002");
                        mrvDataVo.setRespMsg("The interface request failed and the ship does not exist 接口请求失败，船舶不存在");
                        saveInterface(mrvDataVo.getRespcode(), voyageData, mrvDataVo.getRespMsg(), null);
                        return mrvDataVo;
                    }
                }
                // 查询refcode包含的所有港口
                for (int i = 0; i < deletePorts.size(); i++) {
                    ReaportDeletePortDtos reaportDeletePortDtos = deletePorts.get(i);
                    String imoNo = reaportDeletePortDtos.getImoNo();
                    List<String> refCodes = reaportDeletePortDtos.getRefCodes();
                    List<RawVoyagePort> rawVoyagePorts = rawVoyagePortRepository.findAllByIsDeleteAndRefCodeIn(0,
                            refCodes);
                    // 按航次id进行分组
                    Map<String, List<RawVoyagePort>> collect = rawVoyagePorts.stream()
                            .collect(Collectors.groupingBy(rawVoyagePort -> rawVoyagePort.getTaskId()));
                    Set<String> set = collect.keySet();
                    List<String> taskIds = new ArrayList<>(set);
                    for (int j = 0; j < rawVoyagePorts.size(); j++) {
                        RawVoyagePort rawVoyagePort = rawVoyagePorts.get(j);
                        rawVoyagePort.setIsDelete(1);
                        rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
                            rawVoyagePortoil.setIsDelete(1);
                        });
                        rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                            rawVoyagePortloading.setIsDelete(1);
                        });
                        rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {
                            rawVoyageAddoil.setIsDelete(1);
                        });
                        rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {
                            rawVoyageOutoil.setIsDelete(1);
                        });
                        rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
                            rawVoyageSludge.setIsDelete(1);
                        });
                    }
                    if (rawVoyagePorts.size() > 0) {
                        rawVoyagePortRepository.saveAll(rawVoyagePorts);
                    }
                    for (int j = 0; j < taskIds.size(); j++) {
                        String taskId = taskIds.get(i);
                        List<RawVoyagePort> byTaskIdAndIsDelete = rawVoyagePortRepository
                                .findByTaskIdAndIsDelete(taskId, 0);
                        if (byTaskIdAndIsDelete.size() == 0) {
                            // 清除冰区航行和航段
                            OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(taskId, 0);
                            oiShipTask.setIsDelete(1);
                            oiShipTask.getOiShipVoyages().forEach(oiShipVoyage -> {
                                oiShipVoyage.setIsDelete(1);
                            });
                            oiShipTask.getRawVoyageSpecs().forEach(rawVoyageSpec -> {
                                rawVoyageSpec.setIsDelete(1);
                                rawVoyageSpec.getRawVoyageSpecCons().forEach(rawVoyageSpecCons -> {
                                    rawVoyageSpecCons.setIsDelete(1);
                                });
                            });
                            oiShipTaskRepository.save(oiShipTask);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new MrvDataVo(new Date().toString(), "10001",
                        "Interface request failed, data imported failed ,数据导入失败");
            }
            return mrvDataVo;
        } else {
            return new MrvDataVo(new Date().toString(), resultMap.get("respCode"), resultMap.get("respMsg"));
        }
    }

    private OiShipTask generatorVolageInfo(OiShipTask oiShipTask, List<RawVoyagePort> rawVoyagePorts) {
        RawVoyagePort rawVoyagePortStart = rawVoyagePorts.get(0);
        RawVoyagePort rawVoyagePortEnd = rawVoyagePorts.get(rawVoyagePorts.size() - 1);
        oiShipTask.setStartTime(DateUtils.localToUTC(rawVoyagePortStart.getDeptTm(), rawVoyagePortStart.getDeptZone()));
        oiShipTask.setEndTime(DateUtils.localToUTC(rawVoyagePortEnd.getDeptTm(), rawVoyagePortEnd.getDeptZone()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rawVoyagePortStart.getDeptTm());
        oiShipTask.setTaskyear(String.valueOf(calendar.get(Calendar.YEAR)));
        Map<String, List<List<RawVoyagePort>>> euSegmentsSAndImoASegments = getEuSegmentsSAndImoASegments(
                rawVoyagePorts);
        List<List<RawVoyagePort>> imoSements = euSegmentsSAndImoASegments.get("IMO");
        List<List<RawVoyagePort>> euSements = euSegmentsSAndImoASegments.get("EU");
        List<RawVoyageSpec> rawVoyageSpecs = rawVoyageSpecRepository.findByTaskIdAndIsDelete(oiShipTask.getId(), 0);
        List<OiShipVoyage> imoOiShipVoyages = new ArrayList<>();
        for (int i = 0; i < imoSements.size(); i++) {
            List<RawVoyagePort> rawVoyagePorts1 = imoSements.get(i);
            OiShipVoyage oiShipVoyage = generatorImoSementsInfo(oiShipTask, rawVoyagePorts1, rawVoyageSpecs, 0,
                    i + 1 + "");
            imoOiShipVoyages.add(oiShipVoyage);
        }
        // 判断船舶类型计算不同的eeoi
        SysDict byDicTypeAndIsDelete = sysDictRepository.findByDicTypeAndIsDelete(RAWPX_PASS_SHIP_TYPE, 0);
        List<String> strings = Arrays.asList(byDicTypeAndIsDelete.getVal().split(","));
        BigDecimal voyageCo2cost = this.getVoyageCo2cost(imoOiShipVoyages);
        BigDecimal voyageEeoiTd = this.getVoyageEeoiTd(imoOiShipVoyages).get("eeoiTd");
        BigDecimal teuDistance = this.getVoyageEeoiTd(imoOiShipVoyages).get("teuDistance");
        BigDecimal peopleDistance = this.getVoyageEeoiTd(imoOiShipVoyages).get("peopleDistance");
        BigDecimal carDistance = this.getVoyageEeoiTd(imoOiShipVoyages).get("carDistance");
        BigDecimal distance = this.getDistance(imoOiShipVoyages);
        BigDecimal voyageSpeedDistance = this.getVoyageSpeedDistance(imoOiShipVoyages);
        OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();

        //fix by cg 20210301 servicespeed 为0的情况没有显示
        BigDecimal voyageserviceSpeedDistance = this.getVoyageserviceSpeedDistance(imoOiShipVoyages,
                oiShipInfo.getServiceSpeed() == null || oiShipInfo.getServiceSpeed().compareTo(BigDecimal.ZERO) == 0 ? oiShipInfo.getSpeed() : oiShipInfo.getServiceSpeed());
        BigDecimal getdwtDistance = this.getdwtDistance(imoOiShipVoyages, oiShipInfo.getDw());
        BigDecimal eeoi = voyageEeoiTd.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0)
                : voyageCo2cost.multiply(BigDecimal.valueOf(1000000)).divide(voyageEeoiTd, 2, BigDecimal.ROUND_HALF_UP);
        if (strings.contains(oiShipInfo.getSptype())) {
            eeoi = getdwtDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0)
                    : voyageCo2cost.multiply(BigDecimal.valueOf(1000000)).divide(getdwtDistance, 2,
                    BigDecimal.ROUND_HALF_UP);
        }
        oiShipTask.setEeoiValue(eeoi);
        if (distance.compareTo(BigDecimal.ZERO) == 0) {
            oiShipTask.setCo2Per(BigDecimal.ZERO);
        } else {
            oiShipTask.setCo2Per(
                    voyageCo2cost.multiply(BigDecimal.valueOf(1000)).divide(distance, 2, BigDecimal.ROUND_HALF_UP));
        }

        oiShipTask.setSpeedrate(voyageserviceSpeedDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0)
                : voyageSpeedDistance.multiply(BigDecimal.valueOf(100)).divide(voyageserviceSpeedDistance, 2,
                BigDecimal.ROUND_HALF_UP));
        oiShipTask.setUserate(getdwtDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0)
                : voyageEeoiTd.multiply(BigDecimal.valueOf(100)).divide(getdwtDistance, 2, BigDecimal.ROUND_HALF_UP));
        BigDecimal emptyDistance = calculateEmptyDistance(rawVoyagePorts);
        if (distance.equals(BigDecimal.ZERO) || distance.compareTo(BigDecimal.ZERO) == 0) {
            oiShipTask.setEmptyrate(BigDecimal.ZERO);
        } else {
            oiShipTask.setEmptyrate(emptyDistance.divide(distance, 2, BigDecimal.ROUND_HALF_UP));
        }
        oiShipTask.setStartPort(rawVoyagePortStart.getId());
        oiShipTask.setDestPort(rawVoyagePortEnd.getId());
        oiShipTask.setEeoiFc(voyageCo2cost);
        oiShipTask.setEeoiTd(voyageEeoiTd);
        oiShipTask.setDistance(distance);
        oiShipTask.setSpeedDistance(voyageSpeedDistance);
        oiShipTask.setEmptyDistance(emptyDistance);
        oiShipTask.setContainerNumberDistance(teuDistance);
        oiShipTask.setCarNumberDistance(carDistance);
        oiShipTask.setPeopleNumberDistance(peopleDistance);
        oiShipTask.setEeoiContainerNumber(teuDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0)
                : voyageCo2cost.divide(teuDistance, 2, BigDecimal.ROUND_HALF_UP));
        oiShipTask.setEeoiCarNumber(carDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0)
                : voyageCo2cost.divide(carDistance, 2, BigDecimal.ROUND_HALF_UP));
        oiShipTask.setEeoiPeopleNumber(peopleDistance.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(0)
                : voyageCo2cost.divide(peopleDistance, 2, BigDecimal.ROUND_HALF_UP));
        List<OiShipVoyage> euOiShipVoyages = new ArrayList<>();
        for (int i = 0; i < euSements.size(); i++) {
            List<RawVoyagePort> rawVoyagePorts1 = euSements.get(i);
            OiShipVoyage oiShipVoyage = generatorImoSementsInfo(oiShipTask, rawVoyagePorts1, rawVoyageSpecs, 1,
                    i + 1 + "");
            euOiShipVoyages.add(oiShipVoyage);
        }
        OiShipTask save = oiShipTaskRepository.save(oiShipTask);
        return save;
    }

    /**
     * 计算航段的航程
     *
     * @param rawVoyagePorts
     * @return
     */
    private BigDecimal calculateSementDistance(List<RawVoyagePort> rawVoyagePorts) {
        BigDecimal sementDistance = new BigDecimal(0);
        for (int i = 1; i < rawVoyagePorts.size(); i++) {
            sementDistance = sementDistance.add(rawVoyagePorts.get(i).getDistance());
        }
        return sementDistance;
    }

    /**
     * 计算空载航程
     *
     * @param rawVoyagePorts
     * @return
     */
    private BigDecimal calculateEmptyDistance(List<RawVoyagePort> rawVoyagePorts) {
        BigDecimal emptyDistance = new BigDecimal(0);
        for (int i = 1; i < rawVoyagePorts.size(); i++) {
            List<RawVoyagePortloading> rawVoyagePortloadings = rawVoyagePorts.get(i).getRawVoyagePortloadings();
            for (int j = 0; j < rawVoyagePortloadings.size(); j++) {
                RawVoyagePortloading rawVoyagePortloading = rawVoyagePortloadings.get(j);
                BigDecimal cargoTons = rawVoyagePortloading.getCargoTons();
                if (cargoTons == null) {
                    cargoTons = BigDecimal.ZERO;
                }
                if (rawVoyagePortloading.getLoadingType().equals("1") && cargoTons.compareTo(BigDecimal.ZERO) == 0) {
                    BigDecimal distance = rawVoyagePorts.get(i).getDistance();
                    if (distance == null) {
                        distance = BigDecimal.ZERO;
                    }
                    emptyDistance = emptyDistance.add(distance);
                }
            }
        }
        return emptyDistance;
    }

    //  todo
    private OiShipVoyage generatorImoSementsInfo(OiShipTask oiShipTask, List<RawVoyagePort> rawVoyagePorts,
                                                 List<RawVoyageSpec> rawVoyageSpecs, Integer voyType, String voyageNo) {
        if (rawVoyagePorts.size() <= 1) {
            throw new ExplicitException(this.msg("energyEeffic.volage.generator.illegal"));
        }
        OiShipVoyage oiShipVoyage = new OiShipVoyage();
        RawVoyagePort rawVoyagePortStart = rawVoyagePorts.get(0);
        RawVoyagePort rawVoyagePortEnd = rawVoyagePorts.get(rawVoyagePorts.size() - 1);
        oiShipVoyage.setShipId(oiShipTask.getShipId());
        oiShipVoyage.setShiptypeId(oiShipTask.getOiShipInfo().getSptype());
        oiShipVoyage.setTaskid(oiShipTask.getId());
        oiShipVoyage.setVoyageno(voyageNo);
        oiShipVoyage.setVoyType(voyType);
        oiShipVoyage.setIsDelete(0);
        oiShipVoyage.setStartporten(rawVoyagePortStart.getPorten());
        oiShipVoyage.setStartportcn(rawVoyagePortStart.getPortcn());
        oiShipVoyage.setStartportid(rawVoyagePortStart.getId());
        oiShipVoyage.setStartTime(DateUtils.localToUTC(rawVoyagePortStart.getDeptTm(), rawVoyagePortStart.getDeptZone()));
        oiShipVoyage.setEndportcn(rawVoyagePortEnd.getPortcn());
        oiShipVoyage.setEndporten(rawVoyagePortEnd.getPorten());
        oiShipVoyage.setEndportid(rawVoyagePortEnd.getId());
        if (voyType == 1) {
            oiShipVoyage.setEndTime(DateUtils.localToUTC(rawVoyagePortEnd.getArrTm(), rawVoyagePortEnd.getArrZone()));
            oiShipVoyage.setWaitTime(calculateWaitTimeEU(rawVoyagePorts));
            oiShipVoyage.setStopTime(calculateStopTimeEU(rawVoyagePorts));
        } else {
            oiShipVoyage.setWaitTime(calculateWaitTime(rawVoyagePorts));
            oiShipVoyage.setStopTime(calculateStopTime(rawVoyagePorts));
            oiShipVoyage.setEndTime(DateUtils.localToUTC(rawVoyagePortEnd.getDeptTm(), rawVoyagePortEnd.getDeptZone()));
        }
        oiShipVoyage.setDriftingTime(calculateDriftTime(rawVoyagePorts));
        RawVoyagePortloading rawVoyagePortloading = calculateLoadInfo(rawVoyagePorts);
        oiShipVoyage.setCargo(rawVoyagePortloading.getCargoTons());
        oiShipVoyage.setBallast(rawVoyagePortloading.getBallastTons());
        oiShipVoyage.setAllNum(rawVoyagePortloading.getAllBoxNum());
        oiShipVoyage.setHeavyNum(rawVoyagePortloading.getHeavyBoxNum());
        oiShipVoyage.setPeopleNum(rawVoyagePortloading.getPeopleNum());
        oiShipVoyage.setCarNum(rawVoyagePortloading.getCarsNum());
        CalculateOilVo calculateOilVoSailing = null;
        if (voyType == 1) {
            calculateOilVoSailing = calculateSailingOilEU(rawVoyagePorts, "sailing");
        } else {
            calculateOilVoSailing = calculateSailingOil(rawVoyagePorts, "sailing");
        }

        oiShipVoyage.setOiHfo(calculateOilVoSailing.getOiHfo());
        oiShipVoyage.setOiLfo(calculateOilVoSailing.getOiLfo());
        oiShipVoyage.setOiBing(calculateOilVoSailing.getOiBing());
        oiShipVoyage.setOiDing(calculateOilVoSailing.getOiDing());
        oiShipVoyage.setOiChai(calculateOilVoSailing.getOiChai());
        oiShipVoyage.setOiOther(calculateOilVoSailing.getOiOther());
        oiShipVoyage.setOiethanol(calculateOilVoSailing.getOiethanol());
        oiShipVoyage.setOiTian(calculateOilVoSailing.getOiTian());
        CalculateOilVo calculateOilVoStop = null;
        if (voyType == 1) {
            calculateOilVoStop = calculateSailingOilEU(rawVoyagePorts, "stop");
        } else {
            calculateOilVoStop = calculateSailingOil(rawVoyagePorts, "stop");
        }
        oiShipVoyage.setStopoiHfo(calculateOilVoStop.getOiHfo());
        oiShipVoyage.setStopoiLfo(calculateOilVoStop.getOiLfo());
        oiShipVoyage.setStopoiChai(calculateOilVoStop.getOiChai());
        oiShipVoyage.setStopoiBing(calculateOilVoStop.getOiBing());
        oiShipVoyage.setStopoiDing(calculateOilVoStop.getOiDing());
        oiShipVoyage.setStopoiOther(calculateOilVoStop.getOiOther());
        oiShipVoyage.setStopoiTian(calculateOilVoStop.getOiTian());
        oiShipVoyage.setStopethanol(calculateOilVoStop.getOiethanol());
        if (rawVoyageSpecs != null && rawVoyageSpecs.size() > 0) {
            CalculateOilVo ice = calculateIceAndRescue(rawVoyagePorts, "ice", rawVoyageSpecs);
            oiShipVoyage.setIceHfo(ice.getOiHfo());
            oiShipVoyage.setIceLfo(ice.getOiLfo());
            oiShipVoyage.setIceBing(ice.getOiBing());
            oiShipVoyage.setIceDing(ice.getOiDing());
            oiShipVoyage.setIceTian(ice.getOiTian());
            oiShipVoyage.setIceChai(ice.getOiChai());
            oiShipVoyage.setIceMethanol(ice.getOiOther());
            oiShipVoyage.setIceEthanol(ice.getOiethanol());
            CalculateOilVo rsecue = calculateIceAndRescue(rawVoyagePorts, "rsecue", rawVoyageSpecs);
            // 计算空载航程
            BigDecimal emptyDistance = calculateEmptyDistance(rawVoyagePorts);
            oiShipVoyage.setRescueHfo(rsecue.getOiHfo());
            oiShipVoyage.setRescueLfo(rsecue.getOiLfo());
            oiShipVoyage.setRescueBing(rsecue.getOiBing());
            oiShipVoyage.setRescueDing(rsecue.getOiDing());
            oiShipVoyage.setRescueTian(rsecue.getOiTian());
            oiShipVoyage.setRescueChai(rsecue.getOiChai());
            oiShipVoyage.setRescueMethanol(rsecue.getOiOther());
            oiShipVoyage.setRescueEthanol(rsecue.getOiethanol());
        }
        oiShipVoyage.setStartEurope(rawVoyagePortStart.getIsEu().toString());
        oiShipVoyage.setEndEurope(rawVoyagePortEnd.getIsEu().toString());
        oiShipVoyage.setCo2Cost(this.getVoyageCo2cost(Arrays.asList(oiShipVoyage)));
        oiShipVoyage.setTancost(this.getVoyageTancost(Arrays.asList(oiShipVoyage)));
        oiShipVoyage.setDistance(calculateDistance(rawVoyagePorts));
        BigDecimal arrZone = rawVoyagePortEnd.getArrZone();
        Date arrTm = rawVoyagePortEnd.getArrTm();
        Date arrTmUtc = DateUtils.localToUTC(arrTm, arrZone);
        Date deptTm = rawVoyagePortStart.getDeptTm();
        BigDecimal deptZone = rawVoyagePortStart.getDeptZone();
        Date deptTmUtc = DateUtils.localToUTC(deptTm, deptZone);
        double time = ((double) (arrTmUtc.getTime() - deptTmUtc.getTime())
                / (1000 * 60 * 60));
        BigDecimal sailTime = BigDecimal.valueOf(time);
        BigDecimal stopTime = oiShipVoyage.getStopTime();

        //fix by cg 20210301 处理航行时间为0的情况,如果航行时间为0，则直接设置平均速度为0
        BigDecimal sub = sailTime.subtract(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
        oiShipVoyage.setAvgspeed(sub.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                oiShipVoyage.getDistance().divide(
                        sub, 2,
                        BigDecimal.ROUND_HALF_UP));
        oiShipVoyage.setSailTime(sub);
        OiShipVoyage save = oiShipVoyageRepository.save(oiShipVoyage);
        return oiShipVoyage;
    }

    @Override
    public OiShipVoyage generatorAcrossInfo(List<RawVoyagePort> rawVoyagePorts, List<RawVoyageSpec> rawVoyageSpecs) {
        OiShipVoyage oiShipVoyage = new OiShipVoyage();
        RawVoyagePort rawVoyagePortStart = rawVoyagePorts.get(0);
        Date deptTm = rawVoyagePortStart.getDeptTm();
        BigDecimal deptZone = rawVoyagePortStart.getDeptZone();
        RawVoyagePort rawVoyagePortEnd = rawVoyagePorts.get(rawVoyagePorts.size() - 1);
        BigDecimal arrZone = rawVoyagePortEnd.getArrZone();
        Date arrTm = rawVoyagePortEnd.getArrTm();
        RawVoyagePortloading rawVoyagePortloading = calculateLoadInfo(rawVoyagePorts);
        oiShipVoyage.setIsDelete(0);
        oiShipVoyage.setStartporten(rawVoyagePortStart.getPorten());
        oiShipVoyage.setStartportcn(rawVoyagePortStart.getPortcn());
        oiShipVoyage.setStartportid(rawVoyagePortStart.getId());
        oiShipVoyage.setStartTime(DateUtils.localToUTC(rawVoyagePortStart.getDeptTm(), rawVoyagePortStart.getDeptZone()));
        oiShipVoyage.setEndportcn(rawVoyagePortEnd.getPortcn());
        oiShipVoyage.setEndporten(rawVoyagePortEnd.getPorten());
        oiShipVoyage.setEndportid(rawVoyagePortEnd.getId());
        // todo
        oiShipVoyage.setEndTime(DateUtils.localToUTC(rawVoyagePortEnd.getDeptTm(), rawVoyagePortEnd.getDeptZone()));
        oiShipVoyage.setWaitTime(calculateWaitTime(rawVoyagePorts));
        oiShipVoyage.setStopTime(calculateStopTime(rawVoyagePorts));
        oiShipVoyage.setDriftingTime(calculateDriftTime(rawVoyagePorts));
        oiShipVoyage.setCargo(rawVoyagePortloading.getCargoTons());
        oiShipVoyage.setBallast(rawVoyagePortloading.getBallastTons());
        oiShipVoyage.setAllNum(rawVoyagePortloading.getAllBoxNum());
        oiShipVoyage.setHeavyNum(rawVoyagePortloading.getHeavyBoxNum());
        oiShipVoyage.setPeopleNum(rawVoyagePortloading.getPeopleNum());
        oiShipVoyage.setCarNum(rawVoyagePortloading.getCarsNum());
        CalculateOilVo calculateOilVoSailing = calculateSailingOil(rawVoyagePorts, "sailing");
        oiShipVoyage.setOiHfo(calculateOilVoSailing.getOiHfo());
        oiShipVoyage.setOiLfo(calculateOilVoSailing.getOiLfo());
        oiShipVoyage.setOiBing(calculateOilVoSailing.getOiBing());
        oiShipVoyage.setOiDing(calculateOilVoSailing.getOiDing());
        oiShipVoyage.setOiChai(calculateOilVoSailing.getOiChai());
        oiShipVoyage.setOiOther(calculateOilVoSailing.getOiOther());
        oiShipVoyage.setOiethanol(calculateOilVoSailing.getOiethanol());
        oiShipVoyage.setOiTian(calculateOilVoSailing.getOiTian());
        CalculateOilVo calculateOilVoStop = calculateSailingOil(rawVoyagePorts, "stop");
        oiShipVoyage.setStopoiHfo(calculateOilVoStop.getOiHfo());
        oiShipVoyage.setStopoiLfo(calculateOilVoStop.getOiLfo());
        oiShipVoyage.setStopoiChai(calculateOilVoStop.getOiChai());
        oiShipVoyage.setStopoiBing(calculateOilVoStop.getOiBing());
        oiShipVoyage.setStopoiDing(calculateOilVoStop.getOiDing());
        oiShipVoyage.setStopoiOther(calculateOilVoStop.getOiOther());
        oiShipVoyage.setStopoiTian(calculateOilVoStop.getOiTian());
        oiShipVoyage.setStopethanol(calculateOilVoStop.getOiethanol());
        CalculateOilVo ice = calculateIceAndRescue(rawVoyagePorts, "ice", rawVoyageSpecs);
        oiShipVoyage.setIceHfo(ice.getOiHfo());
        oiShipVoyage.setIceLfo(ice.getOiLfo());
        oiShipVoyage.setIceBing(ice.getOiBing());
        oiShipVoyage.setIceDing(ice.getOiDing());
        oiShipVoyage.setIceTian(ice.getOiTian());
        oiShipVoyage.setIceChai(ice.getOiChai());
        oiShipVoyage.setIceMethanol(ice.getOiOther());
        oiShipVoyage.setIceEthanol(ice.getOiethanol());
        CalculateOilVo rsecue = calculateIceAndRescue(rawVoyagePorts, "rsecue", rawVoyageSpecs);
        oiShipVoyage.setRescueHfo(rsecue.getOiHfo());
        oiShipVoyage.setRescueLfo(rsecue.getOiLfo());
        oiShipVoyage.setRescueBing(rsecue.getOiBing());
        oiShipVoyage.setRescueDing(rsecue.getOiDing());
        oiShipVoyage.setRescueTian(rsecue.getOiTian());
        oiShipVoyage.setRescueChai(rsecue.getOiChai());
        oiShipVoyage.setRescueMethanol(rsecue.getOiOther());
        oiShipVoyage.setRescueEthanol(rsecue.getOiethanol());
        oiShipVoyage.setDistance(calculateDistance(rawVoyagePorts));
        oiShipVoyage.setEmptyDistance(calculateEmptyDistance(rawVoyagePorts));
        double time = ((double) (DateUtils.localToUTC(arrTm, arrZone).getTime() - DateUtils.localToUTC(deptTm, deptZone).getTime())
                / (1000 * 60 * 60));
        BigDecimal sailTime = BigDecimal.valueOf(time);
        BigDecimal stopTime = oiShipVoyage.getStopTime();

        //fix by cg 20210301 处理航行时间为0的情况,如果航行时间为0，则直接设置平均速度为0
        BigDecimal sub = sailTime.subtract(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
        oiShipVoyage.setSailTime(sub);
        return oiShipVoyage;
    }

    /**
     * 计算欧盟港口
     *
     * @param rawVoyagePorts
     * @param rawVoyageSpecs
     * @return
     */
    @Override
    public OiShipVoyage generatorAcrossInfoEu(List<RawVoyagePort> rawVoyagePorts, List<RawVoyageSpec> rawVoyageSpecs, Date startTime, Date endTime) {
        OiShipVoyage oiShipVoyage = new OiShipVoyage();
        RawVoyagePort rawVoyagePortStart = rawVoyagePorts.get(0);
        Date deptTm = rawVoyagePortStart.getDeptTm();
        BigDecimal deptZone = rawVoyagePortStart.getDeptZone();
        RawVoyagePort rawVoyagePortEnd = rawVoyagePorts.get(rawVoyagePorts.size() - 1);
        BigDecimal arrZone = rawVoyagePortEnd.getArrZone();
        Date arrTm = rawVoyagePortEnd.getArrTm();
        RawVoyagePortloading rawVoyagePortloading = calculateLoadInfo(rawVoyagePorts);
        oiShipVoyage.setIsDelete(0);
        oiShipVoyage.setStartporten(rawVoyagePortStart.getPorten());
        oiShipVoyage.setStartportcn(rawVoyagePortStart.getPortcn());
        oiShipVoyage.setStartportid(rawVoyagePortStart.getId());
        oiShipVoyage.setStartTime(DateUtils.localToUTC(deptTm, deptZone));
        oiShipVoyage.setEndportcn(rawVoyagePortEnd.getPortcn());
        oiShipVoyage.setEndporten(rawVoyagePortEnd.getPorten());
        oiShipVoyage.setEndportid(rawVoyagePortEnd.getId());
        // todo
        oiShipVoyage.setEndTime(DateUtils.localToUTC(rawVoyagePortEnd.getDeptTm(), rawVoyagePortEnd.getDeptZone()));
        oiShipVoyage.setWaitTime(calculateWaitTimeEU(rawVoyagePorts));
        oiShipVoyage.setStopTime(calculateStopTimeEU(rawVoyagePorts));
        oiShipVoyage.setDriftingTime(calculateDriftTime(rawVoyagePorts));
        oiShipVoyage.setCargo(rawVoyagePortloading.getCargoTons());
        oiShipVoyage.setBallast(rawVoyagePortloading.getBallastTons());
        oiShipVoyage.setAllNum(rawVoyagePortloading.getAllBoxNum());
        oiShipVoyage.setHeavyNum(rawVoyagePortloading.getHeavyBoxNum());
        oiShipVoyage.setPeopleNum(rawVoyagePortloading.getPeopleNum());
        oiShipVoyage.setCarNum(rawVoyagePortloading.getCarsNum());
        CalculateOilVo calculateOilVoSailing = calculateSailingOilEU(rawVoyagePorts, "sailing");
        oiShipVoyage.setOiHfo(calculateOilVoSailing.getOiHfo());
        oiShipVoyage.setOiLfo(calculateOilVoSailing.getOiLfo());
        oiShipVoyage.setOiBing(calculateOilVoSailing.getOiBing());
        oiShipVoyage.setOiDing(calculateOilVoSailing.getOiDing());
        oiShipVoyage.setOiChai(calculateOilVoSailing.getOiChai());
        oiShipVoyage.setOiOther(calculateOilVoSailing.getOiOther());
        oiShipVoyage.setOiethanol(calculateOilVoSailing.getOiethanol());
        oiShipVoyage.setOiTian(calculateOilVoSailing.getOiTian());
        CalculateOilVo calculateOilVoStop = calculateSailingOilEU(rawVoyagePorts, "stop");
        Integer isEu = rawVoyagePortEnd.getIsEu();
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal percent = BigDecimal.valueOf(1);
        Map<String, Date> utcTimeByPortEnd = getUtcTimeByPort(rawVoyagePortEnd);
        Date arr = utcTimeByPortEnd.get("arr");
        Date dept = utcTimeByPortEnd.get("dept");
        if (endTime.getTime() >= arr.getTime() && endTime.getTime() <= dept.getTime()) {

        }
        //判断抵达港是不是跨年
        if (isEu == 1) {
            oiShipVoyage.setStopoiHfo(calculateOilVoStop.getOiHfo().multiply(percent));
            oiShipVoyage.setStopoiLfo(calculateOilVoStop.getOiLfo().multiply(percent));
            oiShipVoyage.setStopoiChai(calculateOilVoStop.getOiChai().multiply(percent));
            oiShipVoyage.setStopoiBing(calculateOilVoStop.getOiBing().multiply(percent));
            oiShipVoyage.setStopoiDing(calculateOilVoStop.getOiDing().multiply(percent));
            oiShipVoyage.setStopoiOther(calculateOilVoStop.getOiOther().multiply(percent));
            oiShipVoyage.setStopoiTian(calculateOilVoStop.getOiTian().multiply(percent));
            oiShipVoyage.setStopethanol(calculateOilVoStop.getOiethanol().multiply(percent));
        } else {
            oiShipVoyage.setStopoiHfo(zero);
            oiShipVoyage.setStopoiLfo(zero);
            oiShipVoyage.setStopoiChai(zero);
            oiShipVoyage.setStopoiBing(zero);
            oiShipVoyage.setStopoiDing(zero);
            oiShipVoyage.setStopoiOther(zero);
            oiShipVoyage.setStopoiTian(zero);
            oiShipVoyage.setStopethanol(zero);
        }
        CalculateOilVo ice = calculateIceAndRescue(rawVoyagePorts, "ice", rawVoyageSpecs);
        oiShipVoyage.setIceHfo(ice.getOiHfo());
        oiShipVoyage.setIceLfo(ice.getOiLfo());
        oiShipVoyage.setIceBing(ice.getOiBing());
        oiShipVoyage.setIceDing(ice.getOiDing());
        oiShipVoyage.setIceTian(ice.getOiTian());
        oiShipVoyage.setIceChai(ice.getOiChai());
        oiShipVoyage.setIceMethanol(ice.getOiOther());
        oiShipVoyage.setIceEthanol(ice.getOiethanol());
        CalculateOilVo rsecue = calculateIceAndRescue(rawVoyagePorts, "rsecue", rawVoyageSpecs);
        oiShipVoyage.setRescueHfo(rsecue.getOiHfo());
        oiShipVoyage.setRescueLfo(rsecue.getOiLfo());
        oiShipVoyage.setRescueBing(rsecue.getOiBing());
        oiShipVoyage.setRescueDing(rsecue.getOiDing());
        oiShipVoyage.setRescueTian(rsecue.getOiTian());
        oiShipVoyage.setRescueChai(rsecue.getOiChai());
        oiShipVoyage.setRescueMethanol(rsecue.getOiOther());
        oiShipVoyage.setRescueEthanol(rsecue.getOiethanol());
        oiShipVoyage.setDistance(calculateDistance(rawVoyagePorts));
        oiShipVoyage.setEmptyDistance(calculateEmptyDistance(rawVoyagePorts));

        double time = ((double) (DateUtils.localToUTC(arrTm, arrZone).getTime() - DateUtils.localToUTC(deptTm, deptZone).getTime())
                / (1000 * 60 * 60));
        BigDecimal sailTime = BigDecimal.valueOf(time);
        BigDecimal stopTime = oiShipVoyage.getStopTime();
        //fix by cg 20210301 处理航行时间为0的情况,如果航行时间为0，则直接设置平均速度为0
        BigDecimal sub = sailTime.subtract(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
        oiShipVoyage.setSailTime(sub);
        return oiShipVoyage;
    }

    @Override
    public ResultVo getFuelType(String shipId, Date periodTime) {
        log.info("船舶id===={}", shipId);
        List<FuelTypeVo> fuelTypeVos = new ArrayList<>();
        if (shipId == null) {
            List<Fuel> fuels = fuelRepository.findAll();
            fuels.forEach(fuel -> {
                FuelTypeVo fuelTypeVo = new FuelTypeVo();
                fuelTypeVo.setFuelCode(fuel.getFuelCode());
                fuelTypeVo.setFuelName(fuel.getFuelName());
                fuelTypeVo.setFuelTons(BigDecimal.ZERO);
                fuelTypeVos.add(fuelTypeVo);
            });
        } else {
            List<String> fuelcodeIds = shipEquipmentFuelMapRepository.findByShipIdAndIsDelete(shipId);
            fuelcodeIds.forEach(s -> {
                FuelTypeVo fuelTypeVo = new FuelTypeVo();
                Fuel byFuelCode = fuelRepository.findByFuelCode(s);
                if (byFuelCode != null) {
                    fuelTypeVo.setFuelCode(byFuelCode.getFuelCode());
                    fuelTypeVo.setFuelName(byFuelCode.getFuelName());
                    fuelTypeVo.setFuelTons(BigDecimal.ZERO);
                    fuelTypeVo.setShipId(shipId);
                    if (periodTime != null) {
                        fuelTypeVo.setPeriodTime(periodTime);
                    }
                    fuelTypeVos.add(fuelTypeVo);
                }
            });
        }
        return new ResultObjectVo<>(fuelTypeVos);
    }

    /**
     * 计算冰区和救援油耗
     *
     * @param rawVoyagePorts
     * @param type
     * @return
     */
    private CalculateOilVo calculateIceAndRescue(List<RawVoyagePort> rawVoyagePorts, String type,
                                                 List<RawVoyageSpec> rawVoyageSpecs) {
        CalculateOilVo calculateOilVo = new CalculateOilVo();
        RawVoyagePort rawVoyagePortStart = rawVoyagePorts.get(0);
        RawVoyagePort rawVoyagePortEnd = rawVoyagePorts.get(rawVoyagePorts.size() - 1);
        List<RawVoyageSpec> volageRawVoyageSpecsIce = rawVoyageSpecs.stream().filter(rawVoyageSpec -> {
            Date beginTm = rawVoyageSpec.getBeginTm();
            Date deptTmStart = rawVoyagePortStart.getDeptTm();
            Date deptTmEnd = rawVoyagePortEnd.getDeptTm();
            if (beginTm == null || deptTmStart == null || deptTmEnd == null) {
                return false;
            } else {
                return (beginTm.getTime() - deptTmStart.getTime()) >= 0
                        && (beginTm.getTime() - deptTmEnd.getTime()) <= 0 && "0".equals(rawVoyageSpec.getRecordType());
            }
        }).collect(Collectors.toList());
        List<RawVoyageSpec> volageRawVoyageSpecsRsecue = rawVoyageSpecs.stream().filter(rawVoyageSpec -> {
            Date beginTm = rawVoyageSpec.getBeginTm();
            Date deptTmStart = rawVoyagePortStart.getDeptTm();
            Date deptTmEnd = rawVoyagePortEnd.getDeptTm();
            if (beginTm == null || deptTmStart == null || deptTmEnd == null) {
                return false;
            } else {
                return (beginTm.getTime() - deptTmStart.getTime()) >= 0
                        && (beginTm.getTime() - deptTmEnd.getTime()) <= 0 && "1".equals(rawVoyageSpec.getRecordType());
            }
        }).collect(Collectors.toList());
        BigDecimal hfo = new BigDecimal(0);
        BigDecimal lfo = new BigDecimal(0);
        BigDecimal bingfo = new BigDecimal(0);
        BigDecimal dingfo = new BigDecimal(0);
        BigDecimal tianfo = new BigDecimal(0);
        BigDecimal chaifo = new BigDecimal(0);
        BigDecimal methanolfo = new BigDecimal(0);
        BigDecimal oiethanolfo = new BigDecimal(0);
        if (type.equals(ICE_PORT_OIL)) {
            setIceAndRsecue(volageRawVoyageSpecsIce, hfo, lfo, bingfo, dingfo, tianfo, chaifo, methanolfo, oiethanolfo);
        } else if (type.equals(RSECUE_OIL)) {
            setIceAndRsecue(volageRawVoyageSpecsRsecue, hfo, lfo, bingfo, dingfo, tianfo, chaifo, methanolfo,
                    oiethanolfo);
        }
        calculateOilVo.setOiHfo(hfo);
        calculateOilVo.setOiLfo(lfo);
        calculateOilVo.setOiChai(chaifo);
        calculateOilVo.setOiBing(bingfo);
        calculateOilVo.setOiDing(dingfo);
        calculateOilVo.setOiTian(tianfo);
        calculateOilVo.setOiOther(methanolfo);
        calculateOilVo.setOiethanol(oiethanolfo);
        return calculateOilVo;
    }

    private void setIceAndRsecue(List<RawVoyageSpec> rawVoyageSpecs, BigDecimal hfo, BigDecimal lfo, BigDecimal bingfo,
                                 BigDecimal dingfo, BigDecimal tianfo, BigDecimal chaifo, BigDecimal methanolfo, BigDecimal oiethanolfo) {
        for (int i = 0; i < rawVoyageSpecs.size(); i++) {
            List<RawVoyageSpecCons> rawVoyageSpecCons = rawVoyageSpecs.get(i).getRawVoyageSpecCons();
            for (int j = 0; j < rawVoyageSpecCons.size(); j++) {
                RawVoyageSpecCons rawVoyageSpecCons1 = rawVoyageSpecCons.get(i);
                BigDecimal consTons = rawVoyageSpecCons1.getConsTons();
                String oilId = rawVoyageSpecCons1.getOilId();
                if (consTons == null) {
                    consTons = BigDecimal.ZERO;
                }
                if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {
                    hfo = hfo.add(consTons);
                }
                if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {
                    lfo = lfo.add(consTons);
                }
                if (FuelConst.CHAI_CODE.equals(oilId)) {
                    chaifo = chaifo.add(consTons);
                }
                if (FuelConst.BING_CODE.equals(oilId)) {
                    bingfo = bingfo.add(consTons);
                }
                if (FuelConst.DING_CODE.equals(oilId)) {
                    dingfo = dingfo.add(consTons);
                }
                if (FuelConst.TIAN_CODE.equals(oilId)) {
                    tianfo = tianfo.add(consTons);
                }
                if (FuelConst.METHAN_CODE.equals(oilId)) {
                    methanolfo = methanolfo.add(consTons);
                }
                if (FuelConst.OIETHAN_CODE.equals(oilId)) {
                    oiethanolfo = oiethanolfo.add(consTons);
                }
            }

        }
    }

    /**
     * 计算航行油耗(imo)
     *
     * @param rawVoyagePorts
     * @return
     */

    @Override

    public CalculateOilVo calculateSailingOil(List<RawVoyagePort> rawVoyagePorts, String type) {

        CalculateOilVo calculateOilVo = new CalculateOilVo();

        BigDecimal hfo = new BigDecimal(0);

        BigDecimal lfo = new BigDecimal(0);

        BigDecimal bingfo = new BigDecimal(0);

        BigDecimal dingfo = new BigDecimal(0);

        BigDecimal tianfo = new BigDecimal(0);

        BigDecimal chaifo = new BigDecimal(0);

        BigDecimal methanolfo = new BigDecimal(0);

        BigDecimal oiethanolfo = new BigDecimal(0);

        List<RawVoyagePort> oiRawPorts = rawVoyagePorts.stream().filter(rawVoyagePort -> {

            return !"2".equals(rawVoyagePort.getRecordType());

        }).collect(Collectors.toList());

        if (oiRawPorts.size() > 1) {

            cn.ccsit.eeoi.common.vo.CalculateOilVo calculateOilVoStart = calculateArrAndDept(
                    oiRawPorts.get(0).getRawVoyagePortoils()).get("arr");

            cn.ccsit.eeoi.common.vo.CalculateOilVo calculateOilVoEnd = calculateArrAndDept(
                    oiRawPorts.get(oiRawPorts.size() - 1).getRawVoyagePortoils()).get("end");
            //计算港口修正量

            // 航行加油信息

            BigDecimal addhfo = new BigDecimal(0);

            BigDecimal addlfo = new BigDecimal(0);

            BigDecimal addbingfo = new BigDecimal(0);

            BigDecimal adddingfo = new BigDecimal(0);

            BigDecimal addtianfo = new BigDecimal(0);

            BigDecimal addchaifo = new BigDecimal(0);

            BigDecimal addmethanolfo = new BigDecimal(0);

            BigDecimal addoiethanolfo = new BigDecimal(0);

            // 航行驳出油信息

            BigDecimal outhfo = new BigDecimal(0);

            BigDecimal outlfo = new BigDecimal(0);

            BigDecimal outbingfo = new BigDecimal(0);

            BigDecimal outdingfo = new BigDecimal(0);

            BigDecimal outtianfo = new BigDecimal(0);

            BigDecimal outchaifo = new BigDecimal(0);

            BigDecimal outmethanolfo = new BigDecimal(0);

            BigDecimal outoiethanolfo = new BigDecimal(0);

            // 停港加油信息

            BigDecimal addhfoStop = new BigDecimal(0);

            BigDecimal addlfoStop = new BigDecimal(0);

            BigDecimal addbingfoStop = new BigDecimal(0);

            BigDecimal adddingfoStop = new BigDecimal(0);

            BigDecimal addtianfoStop = new BigDecimal(0);

            BigDecimal addchaifoStop = new BigDecimal(0);

            BigDecimal addmethanolfoStop = new BigDecimal(0);

            BigDecimal addoiethanolfoStop = new BigDecimal(0);

            // 停港驳出油信息

            BigDecimal outhfoStop = new BigDecimal(0);

            BigDecimal outlfoStop = new BigDecimal(0);

            BigDecimal outbingfoStop = new BigDecimal(0);

            BigDecimal outdingfoStop = new BigDecimal(0);

            BigDecimal outtianfoStop = new BigDecimal(0);

            BigDecimal outchaifoStop = new BigDecimal(0);

            BigDecimal outmethanolfoStop = new BigDecimal(0);

            BigDecimal outoiethanolfoStop = new BigDecimal(0);

            // 航行修正量油信息

            BigDecimal correcthfo = new BigDecimal(0);

            BigDecimal correctlfo = new BigDecimal(0);

            BigDecimal correctbingfo = new BigDecimal(0);

            BigDecimal correctdingfo = new BigDecimal(0);

            BigDecimal correcttianfo = new BigDecimal(0);

            BigDecimal correctchaifo = new BigDecimal(0);

            BigDecimal correctmethanolfo = new BigDecimal(0);

            BigDecimal correctoiethanolfo = new BigDecimal(0);

            // 停港修正量油信息

            BigDecimal correcthfoStop = new BigDecimal(0);

            BigDecimal correctlfoStop = new BigDecimal(0);

            BigDecimal correctbingfoStop = new BigDecimal(0);

            BigDecimal correctdingfoStop = new BigDecimal(0);

            BigDecimal correcttianfoStop = new BigDecimal(0);

            BigDecimal correctchaifoStop = new BigDecimal(0);

            BigDecimal correctmethanolfoStop = new BigDecimal(0);

            BigDecimal correctoiethanolfoStop = new BigDecimal(0);

            // 抵港油信息

            BigDecimal arrhfo = new BigDecimal(0);

            BigDecimal arrlfo = new BigDecimal(0);

            BigDecimal arrbingfo = new BigDecimal(0);

            BigDecimal arrdingfo = new BigDecimal(0);

            BigDecimal arrtianfo = new BigDecimal(0);

            BigDecimal arrchaifo = new BigDecimal(0);

            BigDecimal arrmethanolfo = new BigDecimal(0);

            BigDecimal arroiethanolfo = new BigDecimal(0);

            // 离港油信息

            BigDecimal depthfo = new BigDecimal(0);

            BigDecimal deptlfo = new BigDecimal(0);

            BigDecimal deptbingfo = new BigDecimal(0);

            BigDecimal deptdingfo = new BigDecimal(0);

            BigDecimal depttianfo = new BigDecimal(0);

            BigDecimal deptchaifo = new BigDecimal(0);

            BigDecimal deptmethanolfo = new BigDecimal(0);

            BigDecimal deptoiethanolfo = new BigDecimal(0);

            for (int i = 1; i < oiRawPorts.size() - 1; i++) {

                RawVoyagePort rawVoyagePort = oiRawPorts.get(i);

                List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();

                List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();

                List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();

                if (rawVoyageAddoils != null) {

                    for (int j = 0; j < rawVoyageAddoils.size(); j++) {

                        RawVoyageAddoil rawVoyageAddoil = rawVoyageAddoils.get(j);

                        BigDecimal addTons = rawVoyageAddoil.getAddTons();

                        if (addTons == null) {

                            addTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageAddoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            addhfo = addhfo.add(addTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            addlfo = addlfo.add(addTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            addchaifo = addchaifo.add(addTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            addbingfo = addbingfo.add(addTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            adddingfo = adddingfo.add(addTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            addtianfo = addtianfo.add(addTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            addmethanolfo = addmethanolfo.add(addTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            addoiethanolfo = addoiethanolfo.add(addTons);

                        }

                    }

                }

                if (rawVoyageOutoils != null) {

                    for (int j = 0; j < rawVoyageOutoils.size(); j++) {

                        RawVoyageOutoil rawVoyageOutoil = rawVoyageOutoils.get(j);

                        BigDecimal outTons = rawVoyageOutoil.getOutTons();

                        if (outTons == null) {

                            outTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageOutoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            outhfo = outhfo.add(outTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            outlfo = outlfo.add(outTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            outchaifo = outchaifo.add(outTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            outbingfo = outbingfo.add(outTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            outdingfo = outdingfo.add(outTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            outtianfo = outtianfo.add(outTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            outmethanolfo = outmethanolfo.add(outTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            outoiethanolfo = outoiethanolfo.add(outTons);

                        }

                    }

                }

                if (rawVoyagePortoils != null) {

                    for (int j = 0; j < rawVoyagePortoils.size(); j++) {

                        RawVoyagePortoil rawVoyagePortoil = rawVoyagePortoils.get(j);

                        BigDecimal correctTons = rawVoyagePortoil.getCorrectTons();

                        String oilId = rawVoyagePortoil.getOilId();

                        if (correctTons == null) {

                            correctTons = new BigDecimal(0);

                        }

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            correcthfo = correcthfo.add(correctTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            correctlfo = correctlfo.add(correctTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            correctchaifo = correctchaifo.add(correctTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            correctbingfo = correctbingfo.add(correctTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            correctdingfo = correctdingfo.add(correctTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            correcttianfo = correcttianfo.add(correctTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            correctmethanolfo = correctmethanolfo.add(correctTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            correctoiethanolfo = correctoiethanolfo.add(correctTons);

                        }

                    }

                }

            }

            // 获取停港油耗

            oiRawPorts = oiRawPorts.stream().filter(x -> {

                return "0".equals(x.getInPort());

            }).collect(Collectors.toList());

            for (int i = 1; i < oiRawPorts.size(); i++) {

                RawVoyagePort rawVoyagePort = oiRawPorts.get(i);

                List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();

                List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();

                List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();

                if (rawVoyageAddoils != null) {

                    for (int j = 0; j < rawVoyageAddoils.size(); j++) {

                        RawVoyageAddoil rawVoyageAddoil = rawVoyageAddoils.get(j);

                        BigDecimal addTons = rawVoyageAddoil.getAddTons();

                        if (addTons == null) {

                            addTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageAddoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            addhfoStop = addhfoStop.add(addTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            addlfoStop = addlfoStop.add(addTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            addchaifoStop = addchaifoStop.add(addTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            addbingfoStop = addbingfoStop.add(addTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            adddingfoStop = adddingfoStop.add(addTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            addtianfoStop = addtianfoStop.add(addTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            addmethanolfoStop = addmethanolfoStop.add(addTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            addoiethanolfoStop = addoiethanolfoStop.add(addTons);

                        }

                    }

                }

                if (rawVoyageOutoils != null) {

                    for (int j = 0; j < rawVoyageOutoils.size(); j++) {

                        RawVoyageOutoil rawVoyageOutoil = rawVoyageOutoils.get(j);

                        BigDecimal outTons = rawVoyageOutoil.getOutTons();

                        if (outTons == null) {

                            outTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageOutoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            outhfoStop = outhfoStop.add(outTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            outlfoStop = outlfoStop.add(outTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            outchaifoStop = outchaifoStop.add(outTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            outbingfoStop = outbingfoStop.add(outTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            outdingfoStop = outdingfoStop.add(outTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            outtianfoStop = outtianfoStop.add(outTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            outmethanolfoStop = outmethanolfoStop.add(outTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            outoiethanolfoStop = outoiethanolfoStop.add(outTons);

                        }

                    }

                }

                if (rawVoyagePortoils != null) {

                    for (int j = 0; j < rawVoyagePortoils.size(); j++) {

                        RawVoyagePortoil rawVoyagePortoil = rawVoyagePortoils.get(j);

                        BigDecimal arrTons = rawVoyagePortoil.getArrTons();

                        BigDecimal deptTons = rawVoyagePortoil.getDeptTons();

                        BigDecimal correctTons = rawVoyagePortoil.getCorrectTons();

                        String oilId = rawVoyagePortoil.getOilId();

                        if (arrTons == null) {

                            arrTons = new BigDecimal(0);

                        }

                        if (deptTons == null) {

                            deptTons = new BigDecimal(0);

                        }

                        if (correctTons == null) {

                            correctTons = new BigDecimal(0);

                        }

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            arrhfo = arrhfo.add(arrTons);

                            depthfo = depthfo.add(deptTons);

                            correcthfoStop = correcthfoStop.add(correctTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            arrlfo = arrlfo.add(arrTons);

                            deptlfo = deptlfo.add(deptTons);

                            correctlfoStop = correctlfoStop.add(correctTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            arrchaifo = arrchaifo.add(arrTons);

                            deptchaifo = deptchaifo.add(deptTons);

                            correctchaifoStop = correctchaifoStop.add(correctTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            arrbingfo = arrbingfo.add(arrTons);

                            deptbingfo = deptbingfo.add(deptTons);

                            correctbingfoStop = correctbingfoStop.add(correctTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            arrdingfo = arrdingfo.add(arrTons);

                            deptdingfo = deptdingfo.add(deptTons);

                            correctdingfoStop = correctdingfoStop.add(correctTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            arrtianfo = arrtianfo.add(arrTons);

                            depttianfo = depttianfo.add(deptTons);

                            correcttianfoStop = correcttianfoStop.add(correctTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            arrmethanolfo = arrmethanolfo.add(arrTons);

                            deptmethanolfo = deptmethanolfo.add(deptTons);

                            correctmethanolfoStop = correctmethanolfoStop.add(correctTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            arroiethanolfo = arroiethanolfo.add(arrTons);

                            deptoiethanolfo = deptoiethanolfo.add(deptTons);

                            correctoiethanolfoStop = correctoiethanolfoStop.add(correctTons);

                        }

                    }

                }

            }

            if (type.equals(SAILING_OIL)) {
                //中间港口不计算修正量
                hfo = calculateOilVoStart.getOiHfo().subtract(calculateOilVoEnd.getOiHfo()).add(addhfo).subtract(outhfo)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

                lfo = calculateOilVoStart.getOiLfo().subtract(calculateOilVoEnd.getOiLfo()).add(addlfo).subtract(outlfo)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

                bingfo = calculateOilVoStart.getOiBing().subtract(calculateOilVoEnd.getOiBing()).add(addbingfo)
                        .subtract(outbingfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                dingfo = calculateOilVoStart.getOiDing().subtract(calculateOilVoEnd.getOiDing()).add(adddingfo)
                        .subtract(outdingfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                tianfo = calculateOilVoStart.getOiTian().subtract(calculateOilVoEnd.getOiTian()).add(addtianfo)
                        .subtract(outtianfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                chaifo = calculateOilVoStart.getOiChai().subtract(calculateOilVoEnd.getOiChai()).add(addchaifo)
                        .subtract(outchaifo).setScale(2, BigDecimal.ROUND_HALF_UP);

                methanolfo = calculateOilVoStart.getOiOther().subtract(calculateOilVoEnd.getOiOther())
                        .add(addmethanolfo).subtract(outmethanolfo)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

                oiethanolfo = calculateOilVoStart.getOiethanol().subtract(calculateOilVoEnd.getOiethanol())
                        .add(addoiethanolfo).subtract(outoiethanolfo).add(correctoiethanolfo)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

            } else if (type.equals(STOP_PORT_OIL)) {

                hfo = arrhfo.subtract(depthfo).add(addhfoStop).subtract(outhfoStop).add(correcthfoStop).setScale(2,
                        BigDecimal.ROUND_HALF_UP);

                lfo = arrlfo.subtract(deptlfo).add(addlfoStop).subtract(outlfoStop).add(correctlfoStop).setScale(2,
                        BigDecimal.ROUND_HALF_UP);

                bingfo = arrbingfo.subtract(deptbingfo).add(addbingfoStop).subtract(outbingfoStop)
                        .add(correctbingfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                dingfo = arrdingfo.subtract(deptdingfo).add(adddingfoStop).subtract(outdingfoStop)
                        .add(correctdingfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                tianfo = arrtianfo.subtract(depttianfo).add(addtianfoStop).subtract(outtianfoStop)
                        .add(correcttianfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                chaifo = arrchaifo.subtract(deptchaifo).add(addchaifoStop).subtract(outchaifoStop)
                        .add(correctchaifoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                methanolfo = arrmethanolfo.subtract(deptmethanolfo).add(addmethanolfoStop).subtract(outmethanolfoStop)
                        .add(correctmethanolfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                oiethanolfo = arroiethanolfo.subtract(deptoiethanolfo).add(addoiethanolfoStop)
                        .subtract(outoiethanolfoStop).add(correctoiethanolfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

            }

        }

        calculateOilVo.setOiHfo(hfo);

        calculateOilVo.setOiLfo(lfo);

        calculateOilVo.setOiBing(bingfo);

        calculateOilVo.setOiDing(dingfo);

        calculateOilVo.setOiTian(tianfo);

        calculateOilVo.setOiChai(chaifo);

        calculateOilVo.setOiOther(methanolfo);

        calculateOilVo.setOiethanol(oiethanolfo);

        return calculateOilVo;

    }

    /**
     * 计算航行油耗(EU)
     *
     * @param rawVoyagePorts
     * @return
     */
    @Override
    public CalculateOilVo calculateSailingOilEU(List<RawVoyagePort> rawVoyagePorts, String type) {

        CalculateOilVo calculateOilVo = new CalculateOilVo();

        BigDecimal hfo = new BigDecimal(0);

        BigDecimal lfo = new BigDecimal(0);

        BigDecimal bingfo = new BigDecimal(0);

        BigDecimal dingfo = new BigDecimal(0);

        BigDecimal tianfo = new BigDecimal(0);

        BigDecimal chaifo = new BigDecimal(0);

        BigDecimal methanolfo = new BigDecimal(0);

        BigDecimal oiethanolfo = new BigDecimal(0);

        List<RawVoyagePort> oiRawPorts = rawVoyagePorts.stream().filter(rawVoyagePort -> {

            return !"2".equals(rawVoyagePort.getRecordType());

        }).collect(Collectors.toList());

        if (oiRawPorts.size() > 1) {

            cn.ccsit.eeoi.common.vo.CalculateOilVo calculateOilVoStart = calculateArrAndDept(
                    oiRawPorts.get(0).getRawVoyagePortoils()).get("arr");

            cn.ccsit.eeoi.common.vo.CalculateOilVo calculateOilVoEnd = calculateArrAndDept(
                    oiRawPorts.get(oiRawPorts.size() - 1).getRawVoyagePortoils()).get("end");
            //计算港口修正量

            // 航行加油信息

            BigDecimal addhfo = new BigDecimal(0);

            BigDecimal addlfo = new BigDecimal(0);

            BigDecimal addbingfo = new BigDecimal(0);

            BigDecimal adddingfo = new BigDecimal(0);

            BigDecimal addtianfo = new BigDecimal(0);

            BigDecimal addchaifo = new BigDecimal(0);

            BigDecimal addmethanolfo = new BigDecimal(0);

            BigDecimal addoiethanolfo = new BigDecimal(0);

            // 航行驳出油信息

            BigDecimal outhfo = new BigDecimal(0);

            BigDecimal outlfo = new BigDecimal(0);

            BigDecimal outbingfo = new BigDecimal(0);

            BigDecimal outdingfo = new BigDecimal(0);

            BigDecimal outtianfo = new BigDecimal(0);

            BigDecimal outchaifo = new BigDecimal(0);

            BigDecimal outmethanolfo = new BigDecimal(0);

            BigDecimal outoiethanolfo = new BigDecimal(0);

            // 停港加油信息

            BigDecimal addhfoStop = new BigDecimal(0);

            BigDecimal addlfoStop = new BigDecimal(0);

            BigDecimal addbingfoStop = new BigDecimal(0);

            BigDecimal adddingfoStop = new BigDecimal(0);

            BigDecimal addtianfoStop = new BigDecimal(0);

            BigDecimal addchaifoStop = new BigDecimal(0);

            BigDecimal addmethanolfoStop = new BigDecimal(0);

            BigDecimal addoiethanolfoStop = new BigDecimal(0);

            // 停港驳出油信息

            BigDecimal outhfoStop = new BigDecimal(0);

            BigDecimal outlfoStop = new BigDecimal(0);

            BigDecimal outbingfoStop = new BigDecimal(0);

            BigDecimal outdingfoStop = new BigDecimal(0);

            BigDecimal outtianfoStop = new BigDecimal(0);

            BigDecimal outchaifoStop = new BigDecimal(0);

            BigDecimal outmethanolfoStop = new BigDecimal(0);

            BigDecimal outoiethanolfoStop = new BigDecimal(0);

            // 航行修正量油信息

            BigDecimal correcthfo = new BigDecimal(0);

            BigDecimal correctlfo = new BigDecimal(0);

            BigDecimal correctbingfo = new BigDecimal(0);

            BigDecimal correctdingfo = new BigDecimal(0);

            BigDecimal correcttianfo = new BigDecimal(0);

            BigDecimal correctchaifo = new BigDecimal(0);

            BigDecimal correctmethanolfo = new BigDecimal(0);

            BigDecimal correctoiethanolfo = new BigDecimal(0);

            // 停港修正量油信息

            BigDecimal correcthfoStop = new BigDecimal(0);

            BigDecimal correctlfoStop = new BigDecimal(0);

            BigDecimal correctbingfoStop = new BigDecimal(0);

            BigDecimal correctdingfoStop = new BigDecimal(0);

            BigDecimal correcttianfoStop = new BigDecimal(0);

            BigDecimal correctchaifoStop = new BigDecimal(0);

            BigDecimal correctmethanolfoStop = new BigDecimal(0);

            BigDecimal correctoiethanolfoStop = new BigDecimal(0);

            // 抵港油信息

            BigDecimal arrhfo = new BigDecimal(0);

            BigDecimal arrlfo = new BigDecimal(0);

            BigDecimal arrbingfo = new BigDecimal(0);

            BigDecimal arrdingfo = new BigDecimal(0);

            BigDecimal arrtianfo = new BigDecimal(0);

            BigDecimal arrchaifo = new BigDecimal(0);

            BigDecimal arrmethanolfo = new BigDecimal(0);

            BigDecimal arroiethanolfo = new BigDecimal(0);

            // 离港油信息

            BigDecimal depthfo = new BigDecimal(0);

            BigDecimal deptlfo = new BigDecimal(0);

            BigDecimal deptbingfo = new BigDecimal(0);

            BigDecimal deptdingfo = new BigDecimal(0);

            BigDecimal depttianfo = new BigDecimal(0);

            BigDecimal deptchaifo = new BigDecimal(0);

            BigDecimal deptmethanolfo = new BigDecimal(0);

            BigDecimal deptoiethanolfo = new BigDecimal(0);

            // 油渣

            BigDecimal slugeHfo = new BigDecimal(0);

            BigDecimal slugeLfo = new BigDecimal(0);


            for (int i = 1; i < oiRawPorts.size() - 1; i++) {

                RawVoyagePort rawVoyagePort = oiRawPorts.get(i);

                List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();

                List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();

                List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();

                List<RawVoyageSludge> rawVoyageSludges = rawVoyagePort.getRawVoyageSludges();


                if (rawVoyageAddoils != null) {

                    for (int j = 0; j < rawVoyageAddoils.size(); j++) {

                        RawVoyageAddoil rawVoyageAddoil = rawVoyageAddoils.get(j);

                        BigDecimal addTons = rawVoyageAddoil.getAddTons();

                        if (addTons == null) {

                            addTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageAddoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            addhfo = addhfo.add(addTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            addlfo = addlfo.add(addTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            addchaifo = addchaifo.add(addTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            addbingfo = addbingfo.add(addTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            adddingfo = adddingfo.add(addTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            addtianfo = addtianfo.add(addTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            addmethanolfo = addmethanolfo.add(addTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            addoiethanolfo = addoiethanolfo.add(addTons);

                        }

                    }

                }

                if (rawVoyageOutoils != null) {

                    for (int j = 0; j < rawVoyageOutoils.size(); j++) {

                        RawVoyageOutoil rawVoyageOutoil = rawVoyageOutoils.get(j);

                        BigDecimal outTons = rawVoyageOutoil.getOutTons();

                        if (outTons == null) {

                            outTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageOutoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            outhfo = outhfo.add(outTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            outlfo = outlfo.add(outTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            outchaifo = outchaifo.add(outTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            outbingfo = outbingfo.add(outTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            outdingfo = outdingfo.add(outTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            outtianfo = outtianfo.add(outTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            outmethanolfo = outmethanolfo.add(outTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            outoiethanolfo = outoiethanolfo.add(outTons);

                        }

                    }

                }

                if (rawVoyagePortoils != null) {

                    for (int j = 0; j < rawVoyagePortoils.size(); j++) {

                        RawVoyagePortoil rawVoyagePortoil = rawVoyagePortoils.get(j);

                        BigDecimal correctTons = rawVoyagePortoil.getCorrectTons();

                        String oilId = rawVoyagePortoil.getOilId();

                        if (correctTons == null) {

                            correctTons = new BigDecimal(0);

                        }

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            correcthfo = correcthfo.add(correctTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            correctlfo = correctlfo.add(correctTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            correctchaifo = correctchaifo.add(correctTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            correctbingfo = correctbingfo.add(correctTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            correctdingfo = correctdingfo.add(correctTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            correcttianfo = correcttianfo.add(correctTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            correctmethanolfo = correctmethanolfo.add(correctTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            correctoiethanolfo = correctoiethanolfo.add(correctTons);

                        }

                    }

                }

                if (rawVoyageSludges != null) {

                    for (int j = 0; j < rawVoyageSludges.size(); j++) {

                        RawVoyageSludge rawVoyageSludge = rawVoyageSludges.get(j);

                        BigDecimal sludgeTons = rawVoyageSludge.getSludgeTons();

                        String oilId = rawVoyageSludge.getOilId();

                        if (sludgeTons == null) {

                            sludgeTons = new BigDecimal(0);

                        }

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            slugeHfo = slugeHfo.add(sludgeTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            slugeLfo = slugeLfo.add(sludgeTons);

                        }


                    }

                }

            }

            // 获取停港油耗

            oiRawPorts = oiRawPorts.stream().filter(x -> {

                return "0".equals(x.getInPort());

            }).collect(Collectors.toList());


            for (int i = oiRawPorts.size() - 1; i < oiRawPorts.size(); i++) {

                RawVoyagePort rawVoyagePort = oiRawPorts.get(i);

                List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();

                List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();

                List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();

                List<RawVoyageSludge> rawVoyageSludges = rawVoyagePort.getRawVoyageSludges();

                if (rawVoyageAddoils != null) {

                    for (int j = 0; j < rawVoyageAddoils.size(); j++) {

                        RawVoyageAddoil rawVoyageAddoil = rawVoyageAddoils.get(j);

                        BigDecimal addTons = rawVoyageAddoil.getAddTons();

                        if (addTons == null) {

                            addTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageAddoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            addhfoStop = addhfoStop.add(addTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            addlfoStop = addlfoStop.add(addTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            addchaifoStop = addchaifoStop.add(addTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            addbingfoStop = addbingfoStop.add(addTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            adddingfoStop = adddingfoStop.add(addTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            addtianfoStop = addtianfoStop.add(addTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            addmethanolfoStop = addmethanolfoStop.add(addTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            addoiethanolfoStop = addoiethanolfoStop.add(addTons);

                        }

                    }

                }

                if (rawVoyageOutoils != null) {

                    for (int j = 0; j < rawVoyageOutoils.size(); j++) {

                        RawVoyageOutoil rawVoyageOutoil = rawVoyageOutoils.get(j);

                        BigDecimal outTons = rawVoyageOutoil.getOutTons();

                        if (outTons == null) {

                            outTons = BigDecimal.ZERO;

                        }

                        String oilId = rawVoyageOutoil.getOilId();

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            outhfoStop = outhfoStop.add(outTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            outlfoStop = outlfoStop.add(outTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            outchaifoStop = outchaifoStop.add(outTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            outbingfoStop = outbingfoStop.add(outTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            outdingfoStop = outdingfoStop.add(outTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            outtianfoStop = outtianfoStop.add(outTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            outmethanolfoStop = outmethanolfoStop.add(outTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            outoiethanolfoStop = outoiethanolfoStop.add(outTons);

                        }

                    }

                }

                if (rawVoyagePortoils != null) {

                    for (int j = 0; j < rawVoyagePortoils.size(); j++) {

                        RawVoyagePortoil rawVoyagePortoil = rawVoyagePortoils.get(j);

                        BigDecimal arrTons = rawVoyagePortoil.getArrTons();

                        BigDecimal deptTons = rawVoyagePortoil.getDeptTons();

                        BigDecimal correctTons = rawVoyagePortoil.getCorrectTons();

                        String oilId = rawVoyagePortoil.getOilId();

                        if (arrTons == null) {

                            arrTons = new BigDecimal(0);

                        }

                        if (deptTons == null) {

                            deptTons = new BigDecimal(0);

                        }

                        if (correctTons == null) {

                            correctTons = new BigDecimal(0);

                        }

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            arrhfo = arrhfo.add(arrTons);

                            depthfo = depthfo.add(deptTons);

                            correcthfoStop = correcthfoStop.add(correctTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            arrlfo = arrlfo.add(arrTons);

                            deptlfo = deptlfo.add(deptTons);

                            correctlfoStop = correctlfoStop.add(correctTons);

                        }

                        if (FuelConst.CHAI_CODE.equals(oilId)) {

                            arrchaifo = arrchaifo.add(arrTons);

                            deptchaifo = deptchaifo.add(deptTons);

                            correctchaifoStop = correctchaifoStop.add(correctTons);

                        }

                        if (FuelConst.BING_CODE.equals(oilId)) {

                            arrbingfo = arrbingfo.add(arrTons);

                            deptbingfo = deptbingfo.add(deptTons);

                            correctbingfoStop = correctbingfoStop.add(correctTons);

                        }

                        if (FuelConst.DING_CODE.equals(oilId)) {

                            arrdingfo = arrdingfo.add(arrTons);

                            deptdingfo = deptdingfo.add(deptTons);

                            correctdingfoStop = correctdingfoStop.add(correctTons);

                        }

                        if (FuelConst.TIAN_CODE.equals(oilId)) {

                            arrtianfo = arrtianfo.add(arrTons);

                            depttianfo = depttianfo.add(deptTons);

                            correcttianfoStop = correcttianfoStop.add(correctTons);

                        }

                        if (FuelConst.METHAN_CODE.equals(oilId)) {

                            arrmethanolfo = arrmethanolfo.add(arrTons);

                            deptmethanolfo = deptmethanolfo.add(deptTons);

                            correctmethanolfoStop = correctmethanolfoStop.add(correctTons);

                        }

                        if (FuelConst.OIETHAN_CODE.equals(oilId)) {

                            arroiethanolfo = arroiethanolfo.add(arrTons);

                            deptoiethanolfo = deptoiethanolfo.add(deptTons);

                            correctoiethanolfoStop = correctoiethanolfoStop.add(correctTons);

                        }

                    }

                }

                if (rawVoyageSludges != null) {

                    for (int j = 0; j < rawVoyageSludges.size(); j++) {

                        RawVoyageSludge rawVoyageSludge = rawVoyageSludges.get(j);

                        BigDecimal sludgeTons = rawVoyageSludge.getSludgeTons();

                        String oilId = rawVoyageSludge.getOilId();

                        if (sludgeTons == null) {

                            sludgeTons = new BigDecimal(0);

                        }

                        if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {

                            slugeHfo = slugeHfo.add(sludgeTons);

                        }

                        if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {

                            slugeLfo = slugeLfo.add(sludgeTons);

                        }


                    }

                }
            }

            if (type.equals(SAILING_OIL)) {

                hfo = calculateOilVoStart.getOiHfo().subtract(calculateOilVoEnd.getOiHfo()).add(addhfo).subtract(outhfo)
                        .add(correcthfo).subtract(slugeHfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                lfo = calculateOilVoStart.getOiLfo().subtract(calculateOilVoEnd.getOiLfo()).add(addlfo).subtract(outlfo)
                        .add(correctlfo).subtract(slugeLfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                bingfo = calculateOilVoStart.getOiBing().subtract(calculateOilVoEnd.getOiBing()).add(addbingfo)
                        .subtract(outbingfo).add(correctbingfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                dingfo = calculateOilVoStart.getOiDing().subtract(calculateOilVoEnd.getOiDing()).add(adddingfo)
                        .subtract(outdingfo).add(correctdingfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                tianfo = calculateOilVoStart.getOiTian().subtract(calculateOilVoEnd.getOiTian()).add(addtianfo)
                        .subtract(outtianfo).add(correcttianfo).setScale(2, BigDecimal.ROUND_HALF_UP);

                chaifo = calculateOilVoStart.getOiChai().subtract(calculateOilVoEnd.getOiChai()).add(addchaifo)
                        .subtract(outchaifo).add(correctchaifo).setScale(2, BigDecimal.ROUND_HALF_UP);

                methanolfo = calculateOilVoStart.getOiOther().subtract(calculateOilVoEnd.getOiOther())
                        .add(addmethanolfo).subtract(outmethanolfo).add(correctmethanolfo)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

                oiethanolfo = calculateOilVoStart.getOiethanol().subtract(calculateOilVoEnd.getOiethanol())
                        .add(addoiethanolfo).subtract(outoiethanolfo).add(correctoiethanolfo)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

            } else if (type.equals(STOP_PORT_OIL)) {

                hfo = arrhfo.subtract(depthfo).add(addhfoStop).subtract(outhfoStop).add(correcthfoStop).setScale(2,
                        BigDecimal.ROUND_HALF_UP);

                lfo = arrlfo.subtract(deptlfo).add(addlfoStop).subtract(outlfoStop).add(correctlfoStop).setScale(2,
                        BigDecimal.ROUND_HALF_UP);

                bingfo = arrbingfo.subtract(deptbingfo).add(addbingfoStop).subtract(outbingfoStop)
                        .add(correctbingfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                dingfo = arrdingfo.subtract(deptdingfo).add(adddingfoStop).subtract(outdingfoStop)
                        .add(correctdingfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                tianfo = arrtianfo.subtract(depttianfo).add(addtianfoStop).subtract(outtianfoStop)
                        .add(correcttianfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                chaifo = arrchaifo.subtract(deptchaifo).add(addchaifoStop).subtract(outchaifoStop)
                        .add(correctchaifoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                methanolfo = arrmethanolfo.subtract(deptmethanolfo).add(addmethanolfoStop).subtract(outmethanolfoStop)
                        .add(correctmethanolfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

                oiethanolfo = arroiethanolfo.subtract(deptoiethanolfo).add(addoiethanolfoStop)
                        .subtract(outoiethanolfoStop).add(correctoiethanolfoStop).setScale(2, BigDecimal.ROUND_HALF_UP);

            }

        }

        calculateOilVo.setOiHfo(hfo);

        calculateOilVo.setOiLfo(lfo);

        calculateOilVo.setOiBing(bingfo);

        calculateOilVo.setOiDing(dingfo);

        calculateOilVo.setOiTian(tianfo);

        calculateOilVo.setOiChai(chaifo);

        calculateOilVo.setOiOther(methanolfo);

        calculateOilVo.setOiethanol(oiethanolfo);

        return calculateOilVo;

    }


    @Override
    public ResultVo getVoyageDetailInfo(String voyageId) {
        OiShipVoyage oiShipVoyage = oiShipVoyageRepository.findByIdAndIsDelete(voyageId, 0);
        VoyageDetailInfo voyageDetailInfo = new VoyageDetailInfo();
        voyageDetailInfo.setAllBox(oiShipVoyage.getAllNum());
        voyageDetailInfo.setAvgSpeed(oiShipVoyage.getAvgspeed());
        voyageDetailInfo.setBallest(oiShipVoyage.getBallast());
        voyageDetailInfo.setCargo(oiShipVoyage.getCargo());
        voyageDetailInfo.setCars(oiShipVoyage.getCarNum());
        voyageDetailInfo.setDistance(oiShipVoyage.getDistance());
        voyageDetailInfo.setEndPort(oiShipVoyage.getEndporten());
        voyageDetailInfo.setEndTime(oiShipVoyage.getEndTime());
        voyageDetailInfo.setHeavyBox(oiShipVoyage.getHeavyNum());
        voyageDetailInfo.setPeoples(oiShipVoyage.getPeopleNum());
        voyageDetailInfo.setStartPort(oiShipVoyage.getStartporten());
        voyageDetailInfo.setStartTime(oiShipVoyage.getStartTime());
        voyageDetailInfo.setStopTime(oiShipVoyage.getStopTime());
        OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(oiShipVoyage.getTaskid(), 0);
        if (oiShipTask != null) {
            voyageDetailInfo.setTaskNu(oiShipTask.getTask());
        }
        voyageDetailInfo.setVoyageNu(oiShipVoyage.getVoyageno());
        BigDecimal oiHfo = oiShipVoyage.getOiHfo();
        BigDecimal oiLfo = oiShipVoyage.getOiLfo();
        BigDecimal oiChai = oiShipVoyage.getOiChai();
        BigDecimal oiBing = oiShipVoyage.getOiBing();
        BigDecimal oiDing = oiShipVoyage.getOiDing();
        BigDecimal oiTian = oiShipVoyage.getOiTian();
        BigDecimal oiOther = oiShipVoyage.getOiOther();// 甲醇
        BigDecimal oiethanol = oiShipVoyage.getOiethanol();
        BigDecimal stopoiHfo = oiShipVoyage.getStopoiHfo();
        BigDecimal stopoiLfo = oiShipVoyage.getStopoiLfo();
        BigDecimal stopoiChai = oiShipVoyage.getStopoiChai();
        BigDecimal stopoiBing = oiShipVoyage.getStopoiBing();
        BigDecimal stopoiDing = oiShipVoyage.getStopoiDing();
        BigDecimal stopoiTian = oiShipVoyage.getStopoiTian();
        BigDecimal stopoiOther = oiShipVoyage.getStopoiOther();
        BigDecimal stopethanol = oiShipVoyage.getStopethanol();
        BigDecimal iceHfo = oiShipVoyage.getIceHfo();
        BigDecimal iceLfo = oiShipVoyage.getIceLfo();
        BigDecimal iceChai = oiShipVoyage.getIceChai();
        BigDecimal iceBing = oiShipVoyage.getIceBing();
        BigDecimal iceDing = oiShipVoyage.getIceDing();
        BigDecimal iceTian = oiShipVoyage.getIceTian();
        BigDecimal iceMethanol = oiShipVoyage.getIceMethanol();
        BigDecimal iceEthanol = oiShipVoyage.getIceEthanol();
        BigDecimal rescueHfo = oiShipVoyage.getRescueHfo();
        BigDecimal rescueLfo = oiShipVoyage.getRescueLfo();
        BigDecimal rescueChai = oiShipVoyage.getRescueChai();
        BigDecimal rescueBing = oiShipVoyage.getRescueBing();
        BigDecimal rescueDing = oiShipVoyage.getRescueDing();
        BigDecimal rescueTian = oiShipVoyage.getRescueTian();
        BigDecimal rescueMethanol = oiShipVoyage.getRescueMethanol();
        BigDecimal rescueEthanol = oiShipVoyage.getRescueEthanol();
        VoyageDeitailInfoOilVo hfo = new VoyageDeitailInfoOilVo(FuelConst.HFO_HIGHT, "重燃油", oiHfo, stopoiHfo, iceHfo,
                rescueHfo);
        VoyageDeitailInfoOilVo lfo = new VoyageDeitailInfoOilVo(FuelConst.LFO_HIGHT, "轻燃油", oiLfo, stopoiLfo, iceLfo,
                rescueLfo);
        VoyageDeitailInfoOilVo chai = new VoyageDeitailInfoOilVo(FuelConst.CHAI_CODE, FuelConst.CHAI_CODE_NAME, oiChai,
                stopoiChai, iceChai, rescueChai);
        VoyageDeitailInfoOilVo bing = new VoyageDeitailInfoOilVo(FuelConst.BING_CODE, FuelConst.BING_NAME, oiBing,
                stopoiBing, iceBing, rescueBing);
        VoyageDeitailInfoOilVo ding = new VoyageDeitailInfoOilVo(FuelConst.DING_CODE, FuelConst.DING_NAME, oiDing,
                stopoiDing, iceDing, rescueDing);
        VoyageDeitailInfoOilVo tian = new VoyageDeitailInfoOilVo(FuelConst.TIAN_CODE, FuelConst.TIAN_CODE_NAME, oiTian,
                stopoiTian, iceTian, rescueTian);
        VoyageDeitailInfoOilVo methanol = new VoyageDeitailInfoOilVo(FuelConst.METHAN_CODE, FuelConst.METHAN_NAME,
                oiOther, stopoiOther, iceMethanol, rescueMethanol);
        VoyageDeitailInfoOilVo enthal = new VoyageDeitailInfoOilVo(FuelConst.OIETHAN_CODE, FuelConst.OIETHAN_NAME,
                oiethanol, stopethanol, iceEthanol, rescueEthanol);
        List<VoyageDeitailInfoOilVo> voyageDeitailInfoOilVos = Arrays.asList(hfo, lfo, chai, bing, ding, tian, methanol,
                enthal);
        voyageDetailInfo.setVoyageDeitailInfoOilVos(voyageDeitailInfoOilVos);
        return new ResultObjectVo<>(voyageDetailInfo);
    }

    @Override
    public ResultVo getVoyageDetailInfoApp(String voyageId) {
        OiShipVoyage oiShipVoyage = oiShipVoyageRepository.findByIdAndIsDelete(voyageId, 0);
        VoyageDetailInfoApp voyageDetailInfo = new VoyageDetailInfoApp();
        voyageDetailInfo.setBallest(oiShipVoyage.getBallast());
        voyageDetailInfo.setCargo(oiShipVoyage.getCargo());
        voyageDetailInfo.setDistance(oiShipVoyage.getDistance());
        voyageDetailInfo.setEndPort(oiShipVoyage.getEndporten());
        voyageDetailInfo.setEndTime(oiShipVoyage.getEndTime());
        voyageDetailInfo.setStartPort(oiShipVoyage.getStartporten());
        voyageDetailInfo.setStartTime(oiShipVoyage.getStartTime());
        voyageDetailInfo.setStartPortCn(oiShipVoyage.getStartportcn());
        voyageDetailInfo.setEndPortCn(oiShipVoyage.getEndportcn());
        voyageDetailInfo.setDistanceHour(getSailtime(oiShipVoyage));
        voyageDetailInfo.setStopTime(oiShipVoyage.getStopTime() == null ? BigDecimal.ZERO : oiShipVoyage.getStopTime());
        BigDecimal hfo = new BigDecimal(0);
        BigDecimal lfo = new BigDecimal(0);
        BigDecimal chai = new BigDecimal(0);
        BigDecimal oiHfo = oiShipVoyage.getOiHfo();
        BigDecimal zero = BigDecimal.ZERO;
        if (oiHfo == null) {
            oiHfo = zero;
        }
        BigDecimal stopoiHfo = oiShipVoyage.getStopoiHfo();
        if (stopoiHfo == null) {
            stopoiHfo = zero;
        }
        BigDecimal oiLfo = oiShipVoyage.getOiLfo();
        if (oiLfo == null) {
            oiLfo = zero;
        }
        BigDecimal stopoiLfo = oiShipVoyage.getStopoiLfo();
        if (stopoiLfo == null) {
            stopoiLfo = zero;
        }
        BigDecimal oiChai = oiShipVoyage.getOiChai();
        if (oiChai == null) {
            oiChai = zero;
        }
        BigDecimal stopoiChai = oiShipVoyage.getStopoiChai();
        if (stopoiChai == null) {
            stopoiChai = zero;
        }
        hfo = hfo.add(oiHfo).add(stopoiHfo);
        lfo = lfo.add(oiLfo).add(stopoiLfo);
        chai = chai.add(oiChai).add(stopoiChai);
        voyageDetailInfo.setHfo(hfo);
        voyageDetailInfo.setLfo(lfo);
        voyageDetailInfo.setChai(chai);
        return new ResultObjectVo<>(voyageDetailInfo);
    }

    @Override
    public ResultVo getPortListByTaskId(String taskId) {
        OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(taskId, 0);
        OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();
        List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
        List<RawVoyagePort> rawVoyagePortsNext = oiShipTask.getRawVoyagePortsNext();
        rawVoyagePorts.addAll(rawVoyagePortsNext);
        rawVoyagePorts.sort((x, y) -> {
            Date arrTmX = x.getArrTm();
            Date arrTmY = y.getArrTm();
            Date createTmX = x.getCreateTm();
            Date createTmY = y.getCreateTm();
            if (arrTmX != null && arrTmY != null) {
                if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                    if (createTmX != null && createTmY != null) {
                        return x.getCreateTm().compareTo(y.getCreateTm());
                    } else {
                        return -1;
                    }
                } else {
                    return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                }
            } else {
                return -1;
            }

        });
        List<VoyagePortDto> voyagePortVos = getVoyagePortVosApp(rawVoyagePorts, oiShipInfo);
        return new ResultObjectVo<>(voyagePortVos);
    }

    @Override
    public ResultVo getSegementIdByTaskId(String taskId) {
        OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(taskId, 0);
        List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
        oiShipVoyages.sort((x, y) -> {
            if (StringUtils.isNotEmpty(x.getVoyageno()) && StringUtils.isNotEmpty(y.getVoyageno())) {
                return Integer.valueOf(x.getVoyageno()) - Integer.valueOf(y.getVoyageno());
            } else {
                return -1;
            }
        });
        List<String> segmentIds = new ArrayList<>();
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            segmentIds.add(oiShipVoyage.getId());
        }
        return new ResultObjectVo<>(segmentIds);
    }

    @Override
    public ResultVo getVoyageDeitailInfo(String taskId) {
        OiShipTask oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(taskId, 0);
        OiShipInfo oiShipInfo = oiShipTask.getOiShipInfo();
        VoyageListVoNew voyageListVo = new VoyageListVoNew();
        voyageListVo.setId(oiShipTask.getId());
        voyageListVo.setVolageCode(oiShipTask.getTask());
        voyageListVo.setImo(oiShipInfo.getImono());
        voyageListVo.setRegisterNo(oiShipInfo.getRegisterno());
        voyageListVo.setShipNameCn(oiShipInfo.getSpnameCn());
        voyageListVo.setShipNameEn(oiShipInfo.getSpname());
        voyageListVo.setStartTime(oiShipTask.getStartTime());
        voyageListVo.setEndTime(oiShipTask.getEndTime());
        voyageListVo.setEeoiValue(oiShipTask.getEeoiValue());
        voyageListVo.setCarbonDioxideEmission(oiShipTask.getEeoiFc());
        voyageListVo.setDropRatio(oiShipTask.getSpeedrate());
        voyageListVo.setCapacityUtilization(oiShipTask.getUserate());
        voyageListVo.setRecStatus(oiShipTask.getRecStatus());
        voyageListVo.setShipNameEn(oiShipInfo.getSpname());
        voyageListVo.setEeoiTd(oiShipTask.getEeoiTd());
        voyageListVo.setDistance(oiShipTask.getDistance());
        BigDecimal voyageDistanceTime = new BigDecimal(0);
        List<RawVoyagePort> rawVoyagePorts = oiShipTask.getRawVoyagePorts();
        List<OiShipVoyage> oiShipVoyages = oiShipTask.getOiShipVoyages();
        voyageListVo.setVoyageNum(oiShipVoyages.size());
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            BigDecimal sailtime = getSailtime(oiShipVoyage);
            voyageDistanceTime = voyageDistanceTime.add(sailtime);
        }
        voyageListVo.setDistanceHour(voyageDistanceTime.setScale(2, BigDecimal.ROUND_HALF_UP));
        if (rawVoyagePorts.size() >= 1) {
            voyageListVo.setStartPortEn(rawVoyagePorts.get(0).getPorten());
            voyageListVo.setEndPortEn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPorten());
            voyageListVo.setStartPortCn(rawVoyagePorts.get(0).getPortcn());
            voyageListVo.setEndPortCn(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getPortcn());
        }
        return new ResultObjectVo<>(voyageListVo);
    }

    @Override
    public ResultVo getFuelTypeApp(String shipId) {
        log.info("船舶id===={}", shipId);
        FuelTypeVoApps fuelTypeVoApps = new FuelTypeVoApps();

        List<String> fuelcodeIds = shipEquipmentFuelMapRepository.findByShipIdAndIsDelete(shipId);
        List<FuelTypeOilVoApp> fuelTypeVoAppsArr = new ArrayList<>();
        List<FuelTypeOutVoApp> fuelTypeVoAppsOut = new ArrayList<>();
        List<FuelTypeAddVoApp> fuelTypeVoAppsAdd = new ArrayList<>();
        fuelcodeIds.forEach(s -> {
            FuelTypeOilVoApp fuelTypeVoArr = new FuelTypeOilVoApp();
            FuelTypeOutVoApp fuelTypeVoOut = new FuelTypeOutVoApp();
            Fuel byFuelCode = fuelRepository.findByFuelCode(s);
            if (byFuelCode != null) {
                fuelTypeVoArr.setOilId(byFuelCode.getFuelCode());
                fuelTypeVoArr.setOilName(byFuelCode.getFuelName());
                fuelTypeVoOut.setOilId(byFuelCode.getFuelCode());
                fuelTypeVoOut.setOilName(byFuelCode.getFuelName());
                fuelTypeVoAppsArr.add(fuelTypeVoArr);
                fuelTypeVoAppsOut.add(fuelTypeVoOut);
            }
        });
        fuelTypeVoAppsAdd.add(new FuelTypeAddVoApp("燃油", "燃油"));
        List<String> hfoIds = Arrays.asList("1", "11", "12", "2", "21", "22");
        fuelcodeIds.forEach(s -> {
            FuelTypeAddVoApp fuelTypeVo = new FuelTypeAddVoApp();
            Fuel byFuelCode = fuelRepository.findByFuelCode(s);
            if (byFuelCode != null && !hfoIds.contains(byFuelCode.getFuelCode())) {
                fuelTypeVo.setOilId(byFuelCode.getFuelCode());
                fuelTypeVo.setOilName(byFuelCode.getFuelName());
                fuelTypeVoAppsAdd.add(fuelTypeVo);
            }
        });
        if (fuelcodeIds.size() == 0) {
            List<Fuel> all = fuelRepository.findAll();
            for (int i = 0; i < all.size(); i++) {
                Fuel fuel = all.get(i);
                FuelTypeOilVoApp fuelTypeVoArr = new FuelTypeOilVoApp();
                FuelTypeOutVoApp fuelTypeVoOut = new FuelTypeOutVoApp();
                fuelTypeVoArr.setOilId(fuel.getFuelCode());
                fuelTypeVoArr.setOilName(fuel.getFuelName());
                fuelTypeVoOut.setOilId(fuel.getFuelCode());
                fuelTypeVoOut.setOilName(fuel.getFuelName());
                fuelTypeVoAppsArr.add(fuelTypeVoArr);
                fuelTypeVoAppsOut.add(fuelTypeVoOut);
            }
        }
        if (fuelcodeIds.size() == 0) {
            List<Fuel> all = fuelRepository.findAll();
            for (int i = 0; i < all.size(); i++) {
                Fuel fuel = all.get(i);
                if (!hfoIds.contains(fuel.getFuelCode())) {
                    FuelTypeAddVoApp fuelTypeVo = new FuelTypeAddVoApp();
                    fuelTypeVo.setOilId(fuel.getFuelCode());
                    fuelTypeVo.setOilName(fuel.getFuelName());
                    fuelTypeVoAppsAdd.add(fuelTypeVo);
                }
            }
        }
        fuelTypeVoAppsArr.sort((x, y) -> {
            return x.getOilId().compareTo(y.getOilId());
        });
        fuelTypeVoAppsOut.sort((x, y) -> {
            return x.getOilId().compareTo(y.getOilId());
        });
        fuelTypeVoAppsAdd.sort((x, y) -> {
            return x.getOilId().compareTo(y.getOilId());
        });
        fuelTypeVoApps.setArrFuelTypes(fuelTypeVoAppsArr);
        fuelTypeVoApps.setOutFuelTypes(fuelTypeVoAppsOut);
        fuelTypeVoApps.setAddFuelTypes(fuelTypeVoAppsAdd);
        return new ResultObjectVo<>(fuelTypeVoApps);
    }

    private void setArrAndDept(BigDecimal arr, BigDecimal dept, BigDecimal correct, RawVoyagePortoil rawVoyagePortoil) {
        arr = arr.add(rawVoyagePortoil.getArrTons());
        dept = dept.add(rawVoyagePortoil.getDeptTons());
        correct = correct.add(rawVoyagePortoil.getCorrectTons());
    }

    /**
     * 计算总航行距离
     *
     * @param rawVoyagePorts
     * @return
     */
    private BigDecimal calculateDistance(List<RawVoyagePort> rawVoyagePorts) {
        List<RawVoyagePort> distanceRawVoyagePorts = rawVoyagePorts.stream().filter(rawVoyagePort -> {
            return "0".equals(rawVoyagePort.getRecordType());
        }).collect(Collectors.toList());
        BigDecimal distance = new BigDecimal(0);
        if (distanceRawVoyagePorts.size() != 0) {
            for (int i = 1; i < distanceRawVoyagePorts.size(); i++) {
                BigDecimal distanceRawPort = distanceRawVoyagePorts.get(i).getDistance();
                if (distanceRawPort == null) {
                    distanceRawPort = BigDecimal.ZERO;
                }
                distance = distance.add(distanceRawPort);
            }
            distance = distance.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return distance;
    }

    /**
     * 计算平均载荷信息
     *
     * @param rawVoyagePorts
     * @return
     */
    private RawVoyagePortloading calculateLoadInfo(List<RawVoyagePort> rawVoyagePorts) {
        RawVoyagePortloading returnRawVoyagePortloading = new RawVoyagePortloading();
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal cargoTons = new BigDecimal(0);
        BigDecimal ballastTons = new BigDecimal(0);
        BigDecimal allBoxNum = new BigDecimal(0);
        BigDecimal heavyBoxNum = new BigDecimal(0);
        BigDecimal peopleNum = new BigDecimal(0);
        BigDecimal carsNum = new BigDecimal(0);
        List<RawVoyagePort> normalRawPorts = rawVoyagePorts.stream().filter(rawVoyagePort -> {
            return "0".equals(rawVoyagePort.getRecordType());
        }).collect(Collectors.toList());
        if (normalRawPorts.size() != 0) {
            for (int i = 1; i < normalRawPorts.size(); i++) {
                RawVoyagePort rawVoyagePort = normalRawPorts.get(i);
                BigDecimal distance = rawVoyagePort.getDistance();
                if (distance == null) {
                    distance = zero;
                }
                List<RawVoyagePortloading> rawVoyagePortloadings = rawVoyagePort.getRawVoyagePortloadings();
                if (rawVoyagePortloadings != null) {
                    for (int j = 0; j < rawVoyagePortloadings.size(); j++) {
                        RawVoyagePortloading rawVoyagePortloading = rawVoyagePortloadings.get(j);
                        BigDecimal cargoTonsGet = rawVoyagePortloading.getCargoTons();
                        if (cargoTonsGet == null) {
                            cargoTonsGet = zero;
                        }
                        BigDecimal ballastTonsGet = rawVoyagePortloading.getBallastTons();
                        if (ballastTonsGet == null) {
                            ballastTonsGet = zero;
                        }
                        Integer allBoxNumGet = rawVoyagePortloading.getAllBoxNum();
                        if (allBoxNumGet == null) {
                            allBoxNumGet = 0;
                        }
                        Integer heavyBoxNumGet = rawVoyagePortloading.getHeavyBoxNum();
                        if (heavyBoxNumGet == null) {
                            heavyBoxNumGet = 0;
                        }
                        Integer peopleNumGet = rawVoyagePortloading.getPeopleNum();
                        if (peopleNumGet == null) {
                            peopleNumGet = 0;
                        }
                        Integer carsNumGet = rawVoyagePortloading.getCarsNum();
                        if (carsNumGet == null) {
                            carsNumGet = 0;
                        }
                        if (rawVoyagePortloading.getLoadingType().equals("1")) {
                            cargoTons = cargoTons.add(cargoTonsGet.multiply(distance));
                            ballastTons = ballastTons.add(ballastTonsGet.multiply(distance));
                            allBoxNum = allBoxNum.add(distance.multiply(BigDecimal.valueOf(allBoxNumGet)));
                            heavyBoxNum = heavyBoxNum.add(distance.multiply(BigDecimal.valueOf(heavyBoxNumGet)));
                            peopleNum = peopleNum.add(distance.multiply(BigDecimal.valueOf(peopleNumGet)));
                            carsNum = carsNum.add(distance.multiply(BigDecimal.valueOf(carsNumGet)));
                        }
                    }
                }
            }
        }
        BigDecimal distance = calculateDistance(rawVoyagePorts);
        if (distance.compareTo(BigDecimal.ZERO) > 0) {
            returnRawVoyagePortloading.setCargoTons(cargoTons.divide(distance, 2, BigDecimal.ROUND_HALF_UP));
            returnRawVoyagePortloading.setBallastTons(ballastTons.divide(distance, 2, BigDecimal.ROUND_HALF_UP));
            returnRawVoyagePortloading.setAllBoxNum(allBoxNum.divide(distance, 2, BigDecimal.ROUND_HALF_UP).intValue());
            returnRawVoyagePortloading
                    .setHeavyBoxNum(heavyBoxNum.divide(distance, 2, BigDecimal.ROUND_HALF_UP).intValue());
            returnRawVoyagePortloading.setPeopleNum(peopleNum.divide(distance, 2, BigDecimal.ROUND_HALF_UP).intValue());
            returnRawVoyagePortloading.setCarsNum(carsNum.divide(distance, 2, BigDecimal.ROUND_HALF_UP).intValue());
        } else {
            returnRawVoyagePortloading.setCargoTons(zero);
            returnRawVoyagePortloading.setBallastTons(zero);
            returnRawVoyagePortloading.setAllBoxNum(0);
            returnRawVoyagePortloading.setHeavyBoxNum(0);
            returnRawVoyagePortloading.setPeopleNum(0);
            returnRawVoyagePortloading.setCarsNum(0);
        }
        return returnRawVoyagePortloading;
    }

    /**
     * 计算锚泊时间
     *
     * @param rawVoyagePorts
     * @return
     */
    private BigDecimal calculateWaitTime(List<RawVoyagePort> rawVoyagePorts) {
        List<RawVoyagePort> anchorRawVoyagePort = rawVoyagePorts.stream().filter(rawVoyagePort -> {
            return "1".equals(rawVoyagePort.getInPort()) && !"2".equals(rawVoyagePort.getRecordType());
        }).collect(Collectors.toList());
        BigDecimal waitTime = new BigDecimal(0);
        if (anchorRawVoyagePort.size() != 0) {
            for (int i = 0; i < anchorRawVoyagePort.size(); i++) {
                RawVoyagePort rawVoyagePort = anchorRawVoyagePort.get(i);
                //计算时区
                BigDecimal arrZone = rawVoyagePort.getArrZone();
                BigDecimal deptZone = rawVoyagePort.getDeptZone();
                Date deptTm = rawVoyagePort.getDeptTm();
                Date arrTm = rawVoyagePort.getArrTm();
                deptTm = DateUtils.localToUTC(deptTm, deptZone);
                arrTm = DateUtils.localToUTC(arrTm, arrZone);
                long h = 1000 * 60;
                if (deptTm != null && arrTm != null) {
                    waitTime = waitTime.add(BigDecimal.valueOf(
                            (double) (deptTm.getTime() - arrTm.getTime()) / h));
                }
            }
            waitTime = waitTime.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return waitTime;
    }

    /**
     * 计算锚泊时间(EU)
     *
     * @param rawVoyagePorts
     * @return
     */
    private BigDecimal calculateWaitTimeEU(List<RawVoyagePort> rawVoyagePorts) {
        List<RawVoyagePort> anchorRawVoyagePort = rawVoyagePorts.stream().filter(rawVoyagePort -> {
            return !"2".equals(rawVoyagePort.getRecordType());
        }).collect(Collectors.toList());
        BigDecimal waitTime = new BigDecimal(0);
        if (anchorRawVoyagePort.size() != 0) {
            for (int i = 1; i < anchorRawVoyagePort.size() - 1; i++) {
                RawVoyagePort rawVoyagePort = anchorRawVoyagePort.get(i);
                //计算时区
                BigDecimal arrZone = rawVoyagePort.getArrZone();
                BigDecimal deptZone = rawVoyagePort.getDeptZone();
                Date deptTm = rawVoyagePort.getDeptTm();
                Date arrTm = rawVoyagePort.getArrTm();
                deptTm = DateUtils.localToUTC(deptTm, deptZone);
                arrTm = DateUtils.localToUTC(arrTm, arrZone);
                long h = 1000 * 60;
                if (deptTm != null && arrTm != null) {
                    waitTime = waitTime.add(BigDecimal.valueOf(
                            (double) (deptTm.getTime() - arrTm.getTime()) / h));
                }
            }
            waitTime = waitTime.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return waitTime;
    }

    /**
     * 计算船舶停泊时间
     *
     * @param rawVoyagePorts
     * @return
     */
    //todo
    private BigDecimal calculateStopTime(List<RawVoyagePort> rawVoyagePorts) {
        BigDecimal waitTime = calculateWaitTime(rawVoyagePorts);
        List<RawVoyagePort> stopRawVoyagePort = rawVoyagePorts.stream().filter(rawVoyagePort -> {
            return "2".equals(rawVoyagePort.getRecordType());
        }).collect(Collectors.toList());
        BigDecimal stopTime = new BigDecimal(0);
        if (stopRawVoyagePort.size() != 0) {
            for (int i = 0; i < stopRawVoyagePort.size(); i++) {
                RawVoyagePort rawVoyagePort = stopRawVoyagePort.get(i);
                BigDecimal arrZone = rawVoyagePort.getArrZone();
                BigDecimal deptZone = rawVoyagePort.getDeptZone();
                Date deptTm = rawVoyagePort.getDeptTm();
                Date arrTm = rawVoyagePort.getArrTm();
                deptTm = DateUtils.localToUTC(deptTm, deptZone);
                arrTm = DateUtils.localToUTC(arrTm, arrZone);
                long h = 1000 * 60;
                if (deptTm != null && arrTm != null) {
                    stopTime = stopTime.add(BigDecimal.valueOf((double) (deptTm.getTime() - arrTm.getTime()) / h));
                }
            }
            stopTime = stopTime.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return waitTime.add(stopTime);
    }

    /**
     * 计算船舶停泊时间(EU)
     *
     * @param rawVoyagePorts
     * @return
     */
    //todo
    private BigDecimal calculateStopTimeEU(List<RawVoyagePort> rawVoyagePorts) {
        BigDecimal waitTime = calculateWaitTimeEU(rawVoyagePorts);
        List<RawVoyagePort> stopRawVoyagePort = rawVoyagePorts.stream().filter(rawVoyagePort -> {
            return "2".equals(rawVoyagePort.getRecordType());
        }).collect(Collectors.toList());
        BigDecimal stopTime = new BigDecimal(0);
        if (stopRawVoyagePort.size() != 0) {
            for (int i = 0; i < stopRawVoyagePort.size(); i++) {
                RawVoyagePort rawVoyagePort = stopRawVoyagePort.get(i);
                BigDecimal arrZone = rawVoyagePort.getArrZone();
                BigDecimal deptZone = rawVoyagePort.getDeptZone();
                Date deptTm = rawVoyagePort.getDeptTm();
                Date arrTm = rawVoyagePort.getArrTm();
                deptTm = DateUtils.localToUTC(deptTm, deptZone);
                arrTm = DateUtils.localToUTC(arrTm, arrZone);
                long h = 1000 * 60;
                if (deptTm != null && arrTm != null) {
                    stopTime = stopTime.add(BigDecimal.valueOf((double) (deptTm.getTime() - arrTm.getTime()) / h));
                }
            }
            stopTime = stopTime.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return waitTime.add(stopTime);
    }

    private BigDecimal calculateDriftTime(List<RawVoyagePort> rawVoyagePorts) {
        List<RawVoyagePort> driftRawVoyagePort = rawVoyagePorts.stream().filter(rawVoyagePort -> {
            return "2".equals(rawVoyagePort.getRecordType());
        }).collect(Collectors.toList());
        BigDecimal driftTime = new BigDecimal(0);
        if (driftRawVoyagePort.size() != 0) {
            for (int i = 0; i < driftRawVoyagePort.size(); i++) {
                RawVoyagePort rawVoyagePort = driftRawVoyagePort.get(i);
                Date deptTm = rawVoyagePort.getDeptTm();
                Date arrTm = rawVoyagePort.getArrTm();
                long h = 1000 * 60 * 60;
                if (deptTm != null && arrTm != null) {
                    driftTime = driftTime.add(BigDecimal.valueOf((double) (deptTm.getTime() - arrTm.getTime()) / h));
                }
            }
            driftTime = driftTime.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return driftTime;
    }

    private Specification<RawVoyagePort> queryVolagePortList(VoyagePortListDto voyagePortListDto) {
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
            roleNames.add(sysRole.getName());
        });
        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        return new Specification<RawVoyagePort>() {
            @Override
            public Predicate toPredicate(Root<RawVoyagePort> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<OiShipTask, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
                predicatesAnd.add(cb.equal(join.get("isDelete"), 0));
                Path<Object> path = root.get("oiShipInfo").get("id");

                CriteriaBuilder.In<Object> in = cb.in(path);
                if (shipIdsByShipCodes != null && shipIdsByShipCodes.size() != 0) {
                    shipIdsByShipCodes.forEach(s -> {
                        in.value(s);
                    });
                    predicatesAnd.add(cb.and(in));
                }
                predicatesAnd.add(cb.equal(root.get("isDelete"), 0));
                if (StringUtils.isNotEmpty(voyagePortListDto.getVolageCode())) {
                    predicatesAnd.add(cb.like(cb.upper(cb.trim(root.get("taskCode"))),
                            "%" + voyagePortListDto.getVolageCode().trim().toUpperCase() + "%"));
                }
                if (voyagePortListDto.getStartTime() != null && voyagePortListDto.getEndTime() != null) {
                    predicatesAnd.add(cb.greaterThanOrEqualTo(root.get("arrTm").as(Date.class),
                            voyagePortListDto.getStartTime()));
                    predicatesAnd.add(
                            cb.lessThanOrEqualTo(root.get("arrTm").as(Date.class), voyagePortListDto.getEndTime()));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(voyagePortListDto.getShipNameOrImoOrRegisterNo())) {
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("imono"))),
                            "%" + voyagePortListDto.getShipNameOrImoOrRegisterNo().trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("registerno"))),
                            "%" + voyagePortListDto.getShipNameOrImoOrRegisterNo().trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("spname"))),
                            "%" + voyagePortListDto.getShipNameOrImoOrRegisterNo().trim().toUpperCase() + "%"));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                } else {
                    return criteriaQuery.where(and).getRestriction();
                }
            }
        };
    }

    private Specification<OiShipTask> queryVolageList(VoyageListDto voyageListDto) {
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        Set<String> shipIdsByShipCodes = null;
        if (sysUserRoles != null) {
            List<String> roleNames = new ArrayList<>();
            sysUserRoles.forEach(sysUserRole -> {
                SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
                roleNames.add(sysRole.getName());
            });
            shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        }
        Set<String> finalShipIdsByShipCodes = shipIdsByShipCodes;
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
                if (finalShipIdsByShipCodes != null && finalShipIdsByShipCodes.size() != 0) {
                    finalShipIdsByShipCodes.forEach(s -> {
                        in.value(s);
                    });
                    predicatesAnd.add(cb.and(in));
                }
                if (StringUtils.isNotEmpty(voyageListDto.getVolageCode())) {
                    predicatesAnd.add(cb.like(cb.upper(cb.trim(root.get("task"))),
                            "%" + voyageListDto.getVolageCode().trim().toUpperCase() + "%"));
                }
                if (voyageListDto.getStartTime() != null && voyageListDto.getEndTime() != null) {
                    predicatesAnd.add(cb.between(root.<Date>get("startTime"), voyageListDto.getStartTime(),
                            voyageListDto.getEndTime()));
                    predicatesAnd.add(cb.between(root.<Date>get("endTime"), voyageListDto.getStartTime(),
                            voyageListDto.getEndTime()));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(voyageListDto.getShipComPanyName())) {
                    predicatesOr.add(cb.equal(join.get("docId"), voyageListDto.getShipComPanyName()));
                    predicatesOr.add(cb.equal(join.get("operatorId"), voyageListDto.getShipComPanyName()));
                    predicatesOr.add(cb.equal(join.get("ownerId"), voyageListDto.getShipComPanyName()));
                    predicatesOr.add(cb.equal(join.get("builderId"), voyageListDto.getShipComPanyName()));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                } else if (StringUtils.isNotEmpty(voyageListDto.getShipNameOrImoOrRegisterNo())) {
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("imono"))),
                            "%" + voyageListDto.getShipNameOrImoOrRegisterNo().trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("registerno"))),
                            "%" + voyageListDto.getShipNameOrImoOrRegisterNo().trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("spname"))),
                            "%" + voyageListDto.getShipNameOrImoOrRegisterNo().trim().toUpperCase() + "%"));
                    predicatesOr.add(cb.like(cb.upper(cb.trim(join.get("spnameCn"))),
                            "%" + voyageListDto.getShipNameOrImoOrRegisterNo().trim() + "%"));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                } else {
                    return criteriaQuery.where(and).getRestriction();
                }
            }
        };
    }


    private InterfaceRec saveInterface(String respCode, String reqMessage, String respMessage,
                                       String voyageCode) {
        InterfaceRec interfaceRec = new InterfaceRec();
        interfaceRec.setRespTm(DateUtils.getCurrentDate());
        interfaceRec.setReqTm(DateUtils.getCurrentDate());
        interfaceRec.setReqMessage(reqMessage);
        interfaceRec.setRespMessage(respMessage);
        interfaceRec.setRespCode(respCode);
        interfaceRec.setVoyageCode(voyageCode);
        return interfaceRepository.save(interfaceRec);
    }

    private Map<String, String> getAddOilType(BigDecimal viscosity, BigDecimal sulfurPercent) {
        Map<String, String> map = new HashMap();
        if (BigDecimal.valueOf(80).compareTo(viscosity) < 0) {
            String hfo = "";
            String oilName = "";
            if (BigDecimal.valueOf(0.5).compareTo(sulfurPercent) <= 0) {
                hfo = FuelConst.HFO_HIGHT;
                oilName = "燃油";
            } else if (BigDecimal.valueOf(0.5).compareTo(sulfurPercent) > 0
                    && BigDecimal.valueOf(0.1).compareTo(sulfurPercent) <= 0) {
                hfo = FuelConst.HFO_LOW;
                oilName = "燃油";
            } else {
                hfo = FuelConst.HFO_CHAO_LOW;
                oilName = "燃油";
            }
            map.put("oilType", "hfo");
            map.put("hfo", hfo);
            map.put("oilName", oilName);
        } else {
            String lfo = "";
            String oilName = "";
            if (BigDecimal.valueOf(0.5).compareTo(sulfurPercent) <= 0) {
                lfo = FuelConst.LFO_HIGHT;
                oilName = "燃油";
            } else if (BigDecimal.valueOf(0.5).compareTo(sulfurPercent) > 0
                    && BigDecimal.valueOf(0.1).compareTo(sulfurPercent) <= 0) {
                lfo = FuelConst.LFO_LOW;
                oilName = "燃油";
            } else {
                lfo = FuelConst.LFO_CHAO_LOW;
                oilName = "燃油";
            }
            map.put("oilType", "lfo");
            map.put("lfo", lfo);
            map.put("oilName", oilName);
        }
        return map;
    }

//    private BigDecimal getSailtime(OiShipVoyage oiShipVoyage) {
//        long startTime = 0;
//        long endTime = 0;
//        if (oiShipVoyage.getStartTime() != null && oiShipVoyage.getEndTime() != null) {
//            startTime = oiShipVoyage.getStartTime().getTime();
//            endTime = oiShipVoyage.getEndTime().getTime();
//        }
//        long h = 1000 * 60 * 60;
//        BigDecimal sailTime = BigDecimal.valueOf((double) (endTime - startTime) / h);
//        BigDecimal stopTime = new BigDecimal(0);
//        if (oiShipVoyage.getStopTime() != null) {
//            stopTime = oiShipVoyage.getStopTime();
//        }
//        sailTime = sailTime.subtract(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
//        return sailTime;
//    }

    private boolean saveAddOutSludge(VoyagePortDto voyagePortDto, RawVoyagePort rawVoyagePort) {
        List<RawVoyageAddoil> rawVoyageAddoils = new ArrayList<>();
        List<RawVoyageOutoil> rawVoyageOutoils = new ArrayList<>();
        List<RawVoyageSludge> rawVoyageSludges = new ArrayList<>();
        // 添加加油消息
        List<VoyagePortAddOilDto> voyagePortAddOilDtos = voyagePortDto.getVoyagePortAddOilDtos();
        if (voyagePortAddOilDtos == null) {
            voyagePortAddOilDtos = new ArrayList<>();
        }
        for (int i = 0; i < voyagePortAddOilDtos.size(); i++) {
            VoyagePortAddOilDto voyagePortAddOilDto = voyagePortAddOilDtos.get(i);

//            if (voyagePortAddOilDto.getAddTm() != null) {
//                if (!(voyagePortAddOilDto.getAddTm().getTime() >= rawVoyagePort.getArrTm().getTime()
//                        && voyagePortAddOilDto.getAddTm().getTime() <= rawVoyagePort.getDeptTm().getTime())) {
//                    return false;
//                }
//            } else {
//                return false;
//            }
            // 计算的添加的油属于哪种油
            BigDecimal viscosity = voyagePortAddOilDto.getViscosity();
            BigDecimal sulfurPercent = voyagePortAddOilDto.getSulfurPercent();
            RawVoyageAddoil rawVoyageAddoil = new RawVoyageAddoil();
            BeanUtils.copyProperties(voyagePortAddOilDto, rawVoyageAddoil);
            if ("燃油".equals(voyagePortAddOilDto.getOilId())) {
                Map<String, String> addOilType = getAddOilType(viscosity, sulfurPercent);
                String oilType = addOilType.get("oilType");
                if ("hfo".equals(oilType)) {
                    rawVoyageAddoil.setOilId(addOilType.get("hfo"));
                    rawVoyageAddoil.setOilName(addOilType.get("oilName"));
                } else {
                    rawVoyageAddoil.setOilId(addOilType.get("lfo"));
                    rawVoyageAddoil.setOilName(addOilType.get("oilName"));
                }
            }
            String[] lfoCode = FuelConst.LFO_CODE.split(",");
            String[] hfoCode = FuelConst.HFO_CODE.split(",");
            List<String> hfo = Arrays.asList(ArrayUtils.addAll(lfoCode, hfoCode));//todo
            if (hfo.contains(voyagePortAddOilDto.getOilId())) {
                rawVoyageAddoil.setOilName("燃油");
            }
            //todo 转化时间
            rawVoyageAddoil.setPortinfoId(rawVoyagePort.getId());
            rawVoyageAddoil.setShipId(voyagePortDto.getShipId());
            rawVoyageAddoil.setIsDelete(0);
            rawVoyageAddoil.setRecStatus("0");
            rawVoyageAddoils.add(rawVoyageAddoil);
        }
        List<VoyagePortOutOilDto> voyagePortOutOilDtos = voyagePortDto.getVoyagePortOutOilDtos();
        if (voyagePortOutOilDtos == null) {
            voyagePortOutOilDtos = new ArrayList<>();
        }
        for (int i = 0; i < voyagePortOutOilDtos.size(); i++) {
            VoyagePortOutOilDto voyagePortOutOilDto = voyagePortOutOilDtos.get(i);
//            if (voyagePortOutOilDto.getOutTm() != null) {
//                if (!(voyagePortOutOilDto.getOutTm().getTime() >= rawVoyagePort.getArrTm().getTime()
//                        && voyagePortOutOilDto.getOutTm().getTime() <= rawVoyagePort.getDeptTm().getTime())) {
//                    return false;
//                }
//            } else {
//                return false;
//            }
            RawVoyageOutoil rawVoyageOutoil = new RawVoyageOutoil();
            BeanUtils.copyProperties(voyagePortOutOilDto, rawVoyageOutoil);
            rawVoyageOutoil.setPortinfoId(rawVoyagePort.getId());
            rawVoyageOutoil.setShipId(voyagePortDto.getShipId());
            rawVoyageOutoil.setIsDelete(0);
            rawVoyageOutoil.setRecStatus("0");
            rawVoyageOutoils.add(rawVoyageOutoil);
        }
        List<VoyagePortOutSulageOilDto> voyagePortOutSulageOilDtos = voyagePortDto.getVoyagePortOutSulageOilDtos();
        for (int i = 0; i < voyagePortOutSulageOilDtos.size(); i++) {
            VoyagePortOutSulageOilDto voyagePortOutSulageOilDto = voyagePortOutSulageOilDtos.get(i);
//            if (voyagePortOutSulageOilDto.getSludgeOutTm() != null) {
//                if (!(voyagePortOutSulageOilDto.getSludgeOutTm().getTime() >= rawVoyagePort.getArrTm().getTime()
//                        && voyagePortOutSulageOilDto.getSludgeOutTm().getTime() <= rawVoyagePort.getDeptTm()
//                        .getTime())) {
//                    return false;
//                }
//            } else {
//                return false;
//            }
            RawVoyageSludge rawVoyageSludge = new RawVoyageSludge();
            BeanUtils.copyProperties(voyagePortOutSulageOilDto, rawVoyageSludge);
            Date outTm = voyagePortOutSulageOilDto.getOutTm();
            if (outTm != null) {
                rawVoyageSludge.setSludgeOutTm(outTm);
            }
            rawVoyageSludge.setOilId(FuelConst.HFO_HIGHT);
            rawVoyageSludge.setPortinfoId(rawVoyagePort.getId());
            rawVoyageSludge.setShipId(voyagePortDto.getShipId());
            rawVoyageSludge.setIsDelete(0);
            rawVoyageSludge.setRecStatus("0");
            rawVoyageSludges.add(rawVoyageSludge);
        }
        rawVoyageAddoilRepository.saveAll(rawVoyageAddoils);
        rawVoyageOutoilRepository.saveAll(rawVoyageOutoils);
        rawVoyageSludgeoilRepository.saveAll(rawVoyageSludges);
        return true;
    }

    private List<VoyagePortVo> getVoyagePortVos(List<RawVoyagePort> rawVoyagePorts, OiShipInfo oiShipInfo) {
        List<VoyagePortVo> voyagePortVos = new ArrayList<>();
        if (rawVoyagePorts != null) {
            rawVoyagePorts.forEach(rawVoyagePort -> {
                OiShipTask oiShipTask1 = oiShipTaskRepository.findByIdAndIsDelete(rawVoyagePort.getTaskId(), 0);
                OiShipTask oiShipTask2 = oiShipTaskRepository.findByIdAndIsDelete(rawVoyagePort.getTaskIdNext(), 0);
                VoyagePortVo voyagePortVo = new VoyagePortVo();
                voyagePortVo.setId(rawVoyagePort.getId());
                voyagePortVo.setRecodeType(Integer.valueOf(rawVoyagePort.getRecordType()));
                voyagePortVo.setFirstVolageCode(oiShipTask1 != null ? oiShipTask1.getTask() : null);
                voyagePortVo.setSecondVolageCode(oiShipTask2 != null ? oiShipTask2.getTask() : null);
                voyagePortVo.setShipName(oiShipInfo.getSpnameCn());
                voyagePortVo.setShipNameEn(oiShipInfo.getSpname());
                voyagePortVo.setShipId(oiShipInfo.getId());
                voyagePortVo.setPortName(rawVoyagePort.getPorten());
                voyagePortVo.setPortNameCn(rawVoyagePort.getPortcn());
                voyagePortVo.setArrivalTime(rawVoyagePort.getArrTm());
                voyagePortVo.setFormThePortTime(rawVoyagePort.getDeptTm());
                voyagePortVo.setLocalArrivalTime(DateUtils.localToUTC(rawVoyagePort.getArrTm(), rawVoyagePort.getArrZone()));
                voyagePortVo.setLocalFormThePortTime(DateUtils.localToUTC(rawVoyagePort.getDeptTm(), rawVoyagePort.getDeptZone()));
                voyagePortVo.setDistance(rawVoyagePort.getDistance());
                voyagePortVo.setInPort("0".equals(rawVoyagePort.getInPort()) ? true : false);
                if (rawVoyagePort.getPortWork() != null) {
                    voyagePortVo.setPortWork(Arrays.asList(rawVoyagePort.getPortWork().split(",")));
                }
                voyagePortVo.setPortId(rawVoyagePort.getPortid());
                voyagePortVo.setShorePower(rawVoyagePort.getShorePower());
                voyagePortVo.setArrZone(rawVoyagePort.getArrZone());
                voyagePortVo.setDeptZone(rawVoyagePort.getDeptZone());
                voyagePortVo.setIsEu(rawVoyagePort.getIsEu());
                voyagePortVo.setRecStatus(rawVoyagePort.getRecStatus());
                List<VoyagePortOilVo> voyagePortOilVos = new ArrayList<>();
                List<VoyagePortLordingVo> voyagePortLordingVos = new ArrayList<>();
                List<VoyagePortAddOilVo> voyagePortAddOilVos = new ArrayList<>();
                List<VoyagePortOutOilVo> voyagePortOutOilVos = new ArrayList<>();
                List<VoyagePortOutSulageOilVo> voyagePortOutSulageOilVos = new ArrayList<>();
                List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();
                List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();
                rawVoyagePort.getRawVoyagePortoils().forEach(rawVoyagePortoil -> {
                    VoyagePortOilVo voyagePortOilVo = new VoyagePortOilVo();
                    BeanUtils.copyProperties(rawVoyagePortoil, voyagePortOilVo);
                    BigDecimal addTons = new BigDecimal(0);
                    BigDecimal outTons = new BigDecimal(0);
                    for (int i = 0; i < rawVoyageAddoils.size(); i++) {
                        RawVoyageAddoil rawVoyageAddoil = rawVoyageAddoils.get(i);
                        if (rawVoyageAddoil.getOilId().equals(rawVoyagePortoil.getOilId())) {
                            if (rawVoyageAddoil.getAddTons() != null) {
                                addTons = addTons.add(rawVoyageAddoil.getAddTons());
                            }
                        }
                    }
                    for (int i = 0; i < rawVoyageOutoils.size(); i++) {
                        RawVoyageOutoil rawVoyageOutoil = rawVoyageOutoils.get(i);
                        if (rawVoyageOutoil == null || rawVoyageOutoil.getOilId() == null || rawVoyagePortoil.getOilId() == null)
                            continue;
                        if (rawVoyageOutoil.getOilId().equals(rawVoyagePortoil.getOilId())) {
                            if (rawVoyageOutoil.getOutTons() != null) {
                                outTons = outTons.add(rawVoyageOutoil.getOutTons());
                            }
                        }
                    }
                    voyagePortOilVo.setAddTons(addTons);
                    voyagePortOilVo.setOutTons(outTons);
                    voyagePortOilVos.add(voyagePortOilVo);
                });
                rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                    VoyagePortLordingVo voyagePortLordingVo = new VoyagePortLordingVo();
                    BeanUtils.copyProperties(rawVoyagePortloading, voyagePortLordingVo);
                    voyagePortLordingVos.add(voyagePortLordingVo);
                });
                rawVoyagePort.getRawVoyageAddoils().forEach(rawVoyageAddoil -> {
                    VoyagePortAddOilVo voyagePortAddOilVo = new VoyagePortAddOilVo();
                    BeanUtils.copyProperties(rawVoyageAddoil, voyagePortAddOilVo);
                    if ("燃油".equals(rawVoyageAddoil.getOilName())) {
                        voyagePortAddOilVo.setOilId("燃油");
                        voyagePortAddOilVo.setOilName("燃油");
                    }
                    voyagePortAddOilVo.setSulfurPercent(rawVoyageAddoil.getSulfurPercent() == null ? BigDecimal.ZERO
                            : rawVoyageAddoil.getSulfurPercent());
                    voyagePortAddOilVos.add(voyagePortAddOilVo);
                });
                rawVoyagePort.getRawVoyageOutoils().forEach(rawVoyageOutoil -> {
                    VoyagePortOutOilVo voyagePortOutOilVo = new VoyagePortOutOilVo();
                    BeanUtils.copyProperties(rawVoyageOutoil, voyagePortOutOilVo);
                    voyagePortOutOilVos.add(voyagePortOutOilVo);
                });
                rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
                    VoyagePortOutSulageOilVo voyagePortOutSulageOilVo = new VoyagePortOutSulageOilVo();
                    BeanUtils.copyProperties(rawVoyageSludge, voyagePortOutSulageOilVo);
                    voyagePortOutSulageOilVo.setOutTm(rawVoyageSludge.getSludgeOutTm());
                    voyagePortOutSulageOilVos.add(voyagePortOutSulageOilVo);
                });
                voyagePortVo.setVoyagePortOilVos(voyagePortOilVos);
                voyagePortVo.setVoyagePortLordingVos(voyagePortLordingVos);
                voyagePortVo.setVoyagePortAddOilVos(voyagePortAddOilVos);
                voyagePortVo.setVoyagePortOutOilVos(voyagePortOutOilVos);
                voyagePortVo.setVoyagePortOutSulageOilVos(voyagePortOutSulageOilVos);
                voyagePortVos.add(voyagePortVo);
            });
        }
        return voyagePortVos;
    }

    private List<VoyagePortDto> getVoyagePortVosApp(List<RawVoyagePort> rawVoyagePorts, OiShipInfo oiShipInfo) {
        List<VoyagePortDto> voyagePortVos = new ArrayList<>();
        rawVoyagePorts.forEach(rawVoyagePort -> {
            OiShipTask oiShipTask1 = oiShipTaskRepository.findByIdAndIsDelete(rawVoyagePort.getTaskId(), 0);
            OiShipTask oiShipTask2 = oiShipTaskRepository.findByIdAndIsDelete(rawVoyagePort.getTaskIdNext(), 0);
            VoyagePortDto voyagePortVo = new VoyagePortDto();
            voyagePortVo.setId(rawVoyagePort.getId());
            voyagePortVo.setRecordType(rawVoyagePort.getRecordType());
            voyagePortVo.setFirstVolageCode(oiShipTask1 != null ? oiShipTask1.getTask() : null);
            voyagePortVo.setSecondVolageCode(oiShipTask2 != null ? oiShipTask2.getTask() : null);
//            voyagePortVo.setShipName(oiShipInfo.getSpnameCn());
//            voyagePortVo.setShipNameEn(oiShipInfo.getSpname());
            voyagePortVo.setShipId(oiShipInfo.getId());
            voyagePortVo.setPorten(rawVoyagePort.getPorten());
            voyagePortVo.setPortcn(rawVoyagePort.getPortcn());
            voyagePortVo.setArrTm(rawVoyagePort.getArrTm());
            voyagePortVo.setDeptTm(rawVoyagePort.getDeptTm());
            voyagePortVo.setDistance(rawVoyagePort.getDistance());
            voyagePortVo.setInPort("0".equals(rawVoyagePort.getInPort()) ? true : false);
            if (rawVoyagePort.getPortWork() != null) {
                voyagePortVo.setAnchorPurpose(Arrays.asList(rawVoyagePort.getPortWork().split(",")));
            }
//            voyagePortVo.setPortId(rawVoyagePort.getPortid());
            voyagePortVo.setShorePower(rawVoyagePort.getShorePower());
            voyagePortVo.setArrZone(rawVoyagePort.getArrZone());
            voyagePortVo.setDeptZone(rawVoyagePort.getDeptZone());
//            voyagePortVo.setIsEu(rawVoyagePort.getIsEu());
//            voyagePortVo.setRecStatus(rawVoyagePort.getRecStatus());
            List<VoyagePortOilDto> voyagePortOilVos = new ArrayList<>();
            List<VoyagePortLordingDto> voyagePortLordingVos = new ArrayList<>();
            List<VoyagePortAddOilDto> voyagePortAddOilVos = new ArrayList<>();
            List<VoyagePortOutOilDto> voyagePortOutOilVos = new ArrayList<>();
            List<VoyagePortOutSulageOilDto> voyagePortOutSulageOilVos = new ArrayList<>();
            List<RawVoyageAddoil> rawVoyageAddoils = rawVoyagePort.getRawVoyageAddoils();
            List<RawVoyageOutoil> rawVoyageOutoils = rawVoyagePort.getRawVoyageOutoils();
            ResultVo fuelTypeApp = getFuelTypeApp(oiShipInfo.getId());
            List<FuelTypeAddVoApp> addFuelTypes = new ArrayList<>();
            List<FuelTypeOilVoApp> arrAndOutFuelTypes = new ArrayList<>();
            if (fuelTypeApp.isResult()) {
                FuelTypeVoApps data = (FuelTypeVoApps) fuelTypeApp.getData();
                addFuelTypes = data.getAddFuelTypes();
                arrAndOutFuelTypes = data.getArrFuelTypes();
            }
            List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePort.getRawVoyagePortoils();
            // 提取燃油的类型
            Set<String> oildsPortOils = new HashSet<>();
            for (int i = 0; i < rawVoyagePortoils.size(); i++) {
                RawVoyagePortoil rawVoyagePortoil = rawVoyagePortoils.get(i);
                oildsPortOils.add(rawVoyagePortoil.getOilId());
            }
            // 合并燃油类型
            for (int i = 0; i < arrAndOutFuelTypes.size(); i++) {
                FuelTypeOilVoApp fuelTypeVoApp = arrAndOutFuelTypes.get(i);
                if (!oildsPortOils.contains(fuelTypeVoApp.getOilId())) {
                    RawVoyagePortoil rawVoyagePortoil = new RawVoyagePortoil();
                    rawVoyagePortoil.setOilId(fuelTypeVoApp.getOilId());
                    rawVoyagePortoil.setOilName(fuelTypeVoApp.getOilName());
                    rawVoyagePortoils.add(rawVoyagePortoil);
                }
            }
            List<RawVoyageAddoil> rawVoyageAddoilsApp = rawVoyagePort.getRawVoyageAddoils();
            List<RawVoyageOutoil> rawVoyageOutoilsApp = rawVoyagePort.getRawVoyageOutoils();
            // 提取驳出油耗类型
            Set<String> oildsOutOils = new HashSet<>();
            for (int i = 0; i < rawVoyageOutoilsApp.size(); i++) {
                RawVoyageOutoil rawVoyageOutoil = rawVoyageOutoilsApp.get(i);
                oildsOutOils.add(rawVoyageOutoil.getOilId());
            }
            // 合并驳出有类型
            for (int i = 0; i < arrAndOutFuelTypes.size(); i++) {
                FuelTypeOilVoApp fuelTypeVoApp = arrAndOutFuelTypes.get(i);
                if (!oildsOutOils.contains(fuelTypeVoApp.getOilId())) {
                    RawVoyageOutoil rawVoyageOutoil = new RawVoyageOutoil();
                    rawVoyageOutoil.setOilId(fuelTypeVoApp.getOilId());
                    rawVoyageOutoil.setOilName(fuelTypeVoApp.getOilName());
                    rawVoyageOutoilsApp.add(rawVoyageOutoil);
                }
            }
            // 提取加油的油耗类型
            Set<String> oildsAddOils = new HashSet<>();
            for (int i = 0; i < rawVoyageAddoilsApp.size(); i++) {
                RawVoyageAddoil rawVoyageAddoil = rawVoyageAddoilsApp.get(i);
                oildsOutOils.add(rawVoyageAddoil.getOilId());
            }
            // 合并加油类型类型
            for (int i = 0; i < addFuelTypes.size(); i++) {
                FuelTypeAddVoApp fuelTypeVoApp = addFuelTypes.get(i);
                if (!oildsAddOils.contains(fuelTypeVoApp.getOilId())) {
                    RawVoyageAddoil rawVoyageAddoil = new RawVoyageAddoil();
                    rawVoyageAddoil.setOilId(fuelTypeVoApp.getOilId());
                    rawVoyageAddoil.setOilName(fuelTypeVoApp.getOilName());
                    rawVoyageAddoilsApp.add(rawVoyageAddoil);
                }
            }
            rawVoyagePortoils.forEach(rawVoyagePortoil -> {
                VoyagePortOilDto voyagePortOilVo = new VoyagePortOilDto();
                BeanUtils.copyProperties(rawVoyagePortoil, voyagePortOilVo);
                BigDecimal addTons = new BigDecimal(0);
                BigDecimal outTons = new BigDecimal(0);
                for (int i = 0; i < rawVoyageAddoils.size(); i++) {
                    RawVoyageAddoil rawVoyageAddoil = rawVoyageAddoils.get(i);
                    if (rawVoyageAddoil.getOilId().equals(rawVoyagePortoil.getOilId())) {
                        addTons = addTons.add(
                                rawVoyageAddoil.getAddTons() == null ? BigDecimal.ZERO : rawVoyageAddoil.getAddTons());
                    }
                }
                for (int i = 0; i < rawVoyageOutoils.size(); i++) {
                    RawVoyageOutoil rawVoyageOutoil = rawVoyageOutoils.get(i);
                    if (rawVoyageOutoil.getOilId().equals(rawVoyagePortoil.getOilId())) {
                        outTons = outTons.add(
                                rawVoyageOutoil.getOutTons() == null ? BigDecimal.ZERO : rawVoyageOutoil.getOutTons());
                    }
                }
                voyagePortOilVo.setAddTons(addTons);
                voyagePortOilVo.setOutTons(outTons);
                voyagePortOilVos.add(voyagePortOilVo);
            });
            rawVoyagePort.getRawVoyagePortloadings().forEach(rawVoyagePortloading -> {
                VoyagePortLordingDto voyagePortLordingVo = new VoyagePortLordingDto();
                BeanUtils.copyProperties(rawVoyagePortloading, voyagePortLordingVo);
                voyagePortLordingVos.add(voyagePortLordingVo);
            });
            rawVoyageAddoilsApp.forEach(rawVoyageAddoil -> {
                VoyagePortAddOilDto voyagePortAddOilVo = new VoyagePortAddOilDto();
                BeanUtils.copyProperties(rawVoyageAddoil, voyagePortAddOilVo);
                if (rawVoyageAddoil.getOilName().equals("燃油")) {
                    voyagePortAddOilVo.setOilId("燃油");
                    voyagePortAddOilVo.setOilName("燃油");
                }
                voyagePortAddOilVo.setSulfurPercent(
                        new BigDecimal(100).multiply(rawVoyageAddoil.getSulfurPercent() == null ? BigDecimal.ZERO
                                : rawVoyageAddoil.getSulfurPercent()));
                voyagePortAddOilVos.add(voyagePortAddOilVo);
            });
            rawVoyageOutoilsApp.forEach(rawVoyageOutoil -> {
                VoyagePortOutOilDto voyagePortOutOilVo = new VoyagePortOutOilDto();
                BeanUtils.copyProperties(rawVoyageOutoil, voyagePortOutOilVo);
                voyagePortOutOilVos.add(voyagePortOutOilVo);
            });
            rawVoyagePort.getRawVoyageSludges().forEach(rawVoyageSludge -> {
                VoyagePortOutSulageOilDto voyagePortOutSulageOilVo = new VoyagePortOutSulageOilDto();
                BeanUtils.copyProperties(rawVoyageSludge, voyagePortOutSulageOilVo);
                voyagePortOutSulageOilVos.add(voyagePortOutSulageOilVo);
            });
            voyagePortVo.setPortOilInfos(voyagePortOilVos);
            voyagePortVo.setVolagePortLoadingInfos(voyagePortLordingVos);
            voyagePortVo.setVoyagePortAddOilDtos(voyagePortAddOilVos);
            voyagePortVo.setVoyagePortOutOilDtos(voyagePortOutOilVos);
            voyagePortVo.setVoyagePortOutSulageOilDtos(voyagePortOutSulageOilVos);
            voyagePortVos.add(voyagePortVo);
        });
        return voyagePortVos;
    }

    private List<RawVoyagePort> getRawPort(List<RawVoyagePort> rawVoyagePorts) {
        for (int i = 0; i < rawVoyagePorts.size(); i++) {
            RawVoyagePort rawVoyagePort = rawVoyagePorts.get(i);
            List<RawVoyagePortoil> rawVoyagePortoils = rawVoyagePortoilRepository
                    .findByPortinfoIdAndIsDelete(rawVoyagePort.getId(), 0);
            List<RawVoyagePortloading> rawVoyagePortloadings = rawVoyagePortloadingRepository
                    .findByPortinfoIdAndIsDelete(rawVoyagePort.getId(), 0);
            List<RawVoyageAddoil> rawVoyageAddoils = rawVoyageAddoilRepository
                    .findByPortinfoIdAndIsDelete(rawVoyagePort.getId(), 0);
            List<RawVoyageOutoil> rawVoyageOutoils = rawVoyageOutoilRepository
                    .findByPortinfoIdAndIsDelete(rawVoyagePort.getId(), 0);
            List<RawVoyageSludge> rawVoyageSludges = rawVoyageSludgeoilRepository
                    .findByPortinfoIdAndIsDelete(rawVoyagePort.getId(), 0);
            rawVoyagePort.setRawVoyagePortoils(rawVoyagePortoils);
            rawVoyagePort.setRawVoyagePortloadings(rawVoyagePortloadings);
            rawVoyagePort.setRawVoyageAddoils(rawVoyageAddoils);
            rawVoyagePort.setRawVoyageOutoils(rawVoyageOutoils);
            rawVoyagePort.setRawVoyageSludges(rawVoyageSludges);
        }
        return rawVoyagePorts;
    }

    @Override
    public ResultVo getTasksByShipId(String shipId) {
        CmsaGetTaskVo cmsaGetTaskVo = new CmsaGetTaskVo();
        List<OiShipTask> oiShipTasks = oiShipTaskRepository.findByShipIdAndIsDelete(shipId, 0);
        // 查询该船是否有船旗转换或者经营人变更
        List<OiShipChglog> oiShipChglogFlag = oiShipChglogRepository.findByShipIdAndIsDeleteAndChgType(shipId, 0, 0);// 查询船旗国变更
        List<OiShipChglog> oiShipChglogDoc = oiShipChglogRepository.findByShipIdAndIsDeleteAndChgType(shipId, 0, 1);// 查询经营人变更
        cmsaGetTaskVo.setIsTurnToFlalg(oiShipChglogFlag.size() > 0);
        cmsaGetTaskVo.setIsTurnToDoc(oiShipChglogDoc.size() > 0);
        cmsaGetTaskVo.setNotUse(oiShipChglogDoc.size() == 0 && oiShipChglogFlag.size() == 0);
        List<CmsaTaskVo> cmsaTaskVos = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < oiShipTasks.size(); i++) {
            OiShipTask oiShipTask = oiShipTasks.get(i);
            CmsaTaskVo cmsaTaskVo = new CmsaTaskVo();
            cmsaTaskVo.setTaskId(oiShipTask.getId());
            cmsaTaskVo.setTaskNu(oiShipTask.getTask());
            Date startTime = oiShipTask.getStartTime();
            Date endTime = oiShipTask.getEndTime();
            if (startTime != null && endTime != null) {
                cmsaTaskVo.setStartTime(simpleDateFormat.format(startTime));
                cmsaTaskVo.setEndTime(simpleDateFormat.format(endTime));
            }
            cmsaTaskVos.add(cmsaTaskVo);
        }
        cmsaGetTaskVo.setCmsaTaskVos(cmsaTaskVos);
        return new ResultObjectVo<>(cmsaGetTaskVo);
    }

    @Override
    @Transactional
    public ResultVo getCmsaRpt(CmsaRptDto cmsaRptDto) {
        Map<String, String> mapCn = fuelConsMethodProperties.getMapCn();
        String id = cmsaRptDto.getId();
        CmsaRpt byIdAndIsDelete = cmsaRptRepository.findByIdAndIsDelete(id, 0);
        String cmsaRptId = null;
        CmsaRpt cmsaRpt = new CmsaRpt();
        if (byIdAndIsDelete != null) {
            cmsaRpt = byIdAndIsDelete;
            cmsaRptId = byIdAndIsDelete.getId();
            List<CmsaRptOilCons> byRptIdAndIsDelete = cmsaRptOilConsRepository
                    .findByRptIdAndIsDelete(byIdAndIsDelete.getId(), 0);
            if (byRptIdAndIsDelete != null && byRptIdAndIsDelete.size() > 0) {
                byRptIdAndIsDelete.forEach(cmsaRptOilCons -> {
                    cmsaRptOilCons.setIsDelete(1);
                });
            }
            cmsaRptOilConsRepository.saveAll(byRptIdAndIsDelete);
        }
        Boolean voyageRpt = cmsaRptDto.getVoyageRpt();
        Boolean yearRpt = cmsaRptDto.getYearRpt();
        String shipId = cmsaRptDto.getShipId();
        Date startTime = cmsaRptDto.getStartTime();
        Date endTime = cmsaRptDto.getEndTime();
        Map<String, String> map = new HashMap<>();
        // 根据船舶id查询油耗类型
        List<ShipEquipmentFuelMap> useMethod = shipEquipmentFuelMapRepository.findUseMethod(shipId);
        useMethod.forEach(shipEquipmentFuelMap -> {
            map.put(shipEquipmentFuelMap.getFuelId(), shipEquipmentFuelMap.getConsMethod());
        });
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<OiShipTask> oiShipTasks = new ArrayList<>();
        OiShipTask oiShipTask = null;
        if (voyageRpt) {
            oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(cmsaRptDto.getTaskId(), 0);
            oiShipTasks.add(oiShipTask);
            cmsaRpt.setRptType("1");
            cmsaRpt.setTaskId(oiShipTask.getId());
        }
        if (yearRpt) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            int endYear = instance.get(Calendar.YEAR);
            int startYear = endYear - 1;
            if (startTime == null || endTime == null) {
                try {
                    startTime = simpleDateFormat.parse(startYear + "-01-01");
                    endTime = simpleDateFormat.parse(startYear + "-12-31");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            List<OiShipTask> oiShipTaskEndTime = oiShipTaskRepository.findOiShipTaskEndTime(shipId,
                    simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));
            oiShipTasks.addAll(oiShipTaskEndTime);
            cmsaRpt.setRptType("0");
        }
        List<OiShipVoyage> oiShipVoyages = new ArrayList<>();
        BigDecimal distanceOnSea = new BigDecimal(0);
        BigDecimal hourOnSea = new BigDecimal(0);
        BigDecimal hourOprate = new BigDecimal(0);// 营运时间
        BigDecimal andian = new BigDecimal(0);// 岸电消耗量
        BigDecimal hfo = new BigDecimal(0);
        BigDecimal lfo = new BigDecimal(0);
        BigDecimal chaifo = new BigDecimal(0);
        BigDecimal lng = new BigDecimal(0);
        BigDecimal bing = new BigDecimal(0);
        BigDecimal ding = new BigDecimal(0);
        BigDecimal ethe = new BigDecimal(0);// 乙醇
        BigDecimal me = new BigDecimal(0);// 甲醇
        for (int i = 0; i < oiShipTasks.size(); i++) {
            OiShipTask oiShipTaskGet = oiShipTasks.get(i);
            List<RawVoyagePort> rawVoyagePorts = oiShipTaskGet.getRawVoyagePorts();
            List<RawVoyagePort> rawVoyagePortsNext = oiShipTaskGet.getRawVoyagePortsNext();
            rawVoyagePorts.addAll(rawVoyagePortsNext);
            for (int j = 0; j < rawVoyagePorts.size(); j++) {
                RawVoyagePort rawVoyagePort = rawVoyagePorts.get(j);
                BigDecimal shorePower = rawVoyagePort.getShorePower();
                if (shorePower == null) {
                    shorePower = new BigDecimal(0);
                }
                andian = andian.add(shorePower);
            }
            List<RawVoyageSpec> rawVoyageSpecs = oiShipTaskGet.getRawVoyageSpecs();
            if (rawVoyagePorts.size() < 2) {
                break;
//                return new ResultErrorVo(this.msg("energyEeffic.volage.rawPortNum"));
            }
            rawVoyagePorts.sort((x, y) -> {
                if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                    return x.getCreateTm().compareTo(y.getCreateTm());
                } else {
                    return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                }
            });
            if (!"0".equals(rawVoyagePorts.get(0).getInPort())) {
//                return new ResultErrorVo(this.msg("energyEeffic.volage.startRawPortInert"));
                break;
            }
            if (!"0".equals(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getInPort())) {
//                return new ResultErrorVo(this.msg("energyEeffic.volage.endRawPortInert"));
                break;
            }
            Map<String, List<List<RawVoyagePort>>> euSegmentsSAndImoASegments = getEuSegmentsSAndImoASegments(
                    rawVoyagePorts);
            List<List<RawVoyagePort>> imoSements = euSegmentsSAndImoASegments.get("IMO");
            BigDecimal zero = new BigDecimal(0);
            for (int j = 0; j < imoSements.size(); j++) {
                List<RawVoyagePort> rawVoyagePortsImo = imoSements.get(j);
                OiShipVoyage oiShipVoyage = generatorAcrossInfo(rawVoyagePortsImo, rawVoyageSpecs);
                oiShipVoyages.add(oiShipVoyage);
                BigDecimal distance = oiShipVoyage.getDistance();
                BigDecimal oiHfo = oiShipVoyage.getOiHfo();
                BigDecimal stopoiHfo = oiShipVoyage.getStopoiHfo();
                if (oiHfo == null) {
                    oiHfo = zero;
                }
                if (stopoiHfo == null) {
                    stopoiHfo = zero;
                }
                hfo = hfo.add(oiHfo).add(stopoiHfo);
                BigDecimal oiLfo = oiShipVoyage.getOiLfo();
                BigDecimal stopoiLfo = oiShipVoyage.getStopoiLfo();
                if (oiLfo == null) {
                    oiLfo = zero;
                }
                if (stopoiLfo == null) {
                    stopoiLfo = zero;
                }
                lfo = lfo.add(oiLfo).add(stopoiLfo);
                BigDecimal oiChai = oiShipVoyage.getOiChai();
                BigDecimal stopoiChai = oiShipVoyage.getStopoiChai();
                if (oiChai == null) {
                    oiChai = zero;
                }
                if (stopoiChai == null) {
                    stopoiChai = zero;
                }
                chaifo = chaifo.add(oiChai).add(stopoiChai);
                BigDecimal oiTian = oiShipVoyage.getOiTian();
                BigDecimal stopoiTian = oiShipVoyage.getStopoiTian();
                if (oiTian == null) {
                    oiTian = zero;
                }
                if (stopoiTian == null) {
                    stopoiTian = zero;
                }
                lng.add(oiTian).add(stopoiTian);
                BigDecimal oiBing = oiShipVoyage.getOiBing();
                BigDecimal stopoiBing = oiShipVoyage.getStopoiBing();
                if (oiBing == null) {
                    oiBing = zero;
                }
                if (stopoiBing == null) {
                    stopoiBing = zero;
                }
                bing = bing.add(oiBing).add(stopoiBing);
                BigDecimal oiDing = oiShipVoyage.getOiDing();
                BigDecimal stopoiDing = oiShipVoyage.getStopoiDing();
                if (oiDing == null) {
                    oiDing = zero;
                }
                if (stopoiDing == null) {
                    stopoiDing = zero;
                }
                ding.add(oiDing).add(stopoiDing);
                BigDecimal oiOther = oiShipVoyage.getOiOther();// 甲醇
                BigDecimal oiOtherStop = oiShipVoyage.getStopoiOther();// 甲醇
                if (oiOther == null) {
                    oiOther = zero;
                }
                if (oiOtherStop == null) {
                    oiOtherStop = zero;
                }
                me = me.add(oiOther).add(oiOtherStop);
                BigDecimal oiethanol = oiShipVoyage.getOiethanol();
                BigDecimal stopethanol = oiShipVoyage.getStopethanol();// 乙醇
                if (oiethanol == null) {
                    oiethanol = zero;
                }
                if (stopethanol == null) {
                    stopethanol = zero;
                }
                ethe = ethe.add(oiethanol).add(stopethanol);
                if (distance == null) {
                    distance = new BigDecimal(0);
                }
                distanceOnSea = distanceOnSea.add(distance);
                hourOnSea = hourOnSea.add(getSailtime(oiShipVoyage));
            }
            List<RawVoyagePort> collect = rawVoyagePorts.stream().filter(rawVoyagePort -> {
                List<RawVoyagePortloading> rawVoyagePortloadings = rawVoyagePort.getRawVoyagePortloadings();
                RawVoyagePortloading rawVoyagePortloadingArr = rawVoyagePortloadings.get(0);
                RawVoyagePortloading rawVoyagePortloadingEnd = rawVoyagePortloadings.get(1);
                BigDecimal cargoTonsArr = rawVoyagePortloadingArr.getCargoTons();
                BigDecimal cargoTonsEnd = rawVoyagePortloadingEnd.getCargoTons();
                if (cargoTonsArr == null) {
                    cargoTonsArr = new BigDecimal(0);
                }
                if (cargoTonsEnd == null) {
                    cargoTonsEnd = new BigDecimal(0);
                }
                String portWork = rawVoyagePort.getPortWork();
                List<String> strings = Arrays.asList(portWork.split(","));
                return cargoTonsArr.compareTo(cargoTonsEnd) != 0 && !(strings.contains("8") || strings.contains("6"));// 6代表修理
                // 8：代表避险
            }).collect(Collectors.toList());
            BigDecimal hourOprateTime = calculateStopTime(collect);
            hourOprate = hourOprate.add(hourOprateTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
        }
        CmsaRptVo cmsaRptVo = new CmsaRptVo();
        cmsaRptVo.setTeu(oiShipInfo.getTeus());
        String spname = oiShipInfo.getSpname();
        String spnameCn = oiShipInfo.getSpnameCn();
        cmsaRptVo.setRptOrg(spnameCn + "/" + spname);
        cmsaRptVo.setVerifierName("中国船级社/China Classification Society");
        cmsaRptVo.setVoyageRpt(voyageRpt);
        if (voyageRpt) {
            String startPortId = oiShipTask.getStartPort();
            String destPortId = oiShipTask.getDestPort();
            RawVoyagePort startPort = rawVoyagePortRepository.findByIdAndIsDelete(startPortId, 0);
            RawVoyagePort endPort = rawVoyagePortRepository.findByIdAndIsDelete(destPortId, 0);
            if (startPort != null) {
                cmsaRptVo.setPrePort(startPort.getPorten() + "/" + startPort.getPorten());
            }
            cmsaRptVo.setPrePortArrTime(oiShipTask.getStartTime());
            if (endPort != null) {
                cmsaRptVo.setNextPort(endPort.getPortcn() + "/" + endPort.getPorten());
            }
            cmsaRptVo.setNextPortArrTime(oiShipTask.getEndTime());
            startTime = oiShipTask.getStartTime();
            endTime = oiShipTask.getEndTime();
        }
        cmsaRptVo.setYearRpt(yearRpt);
        cmsaRptVo.setStartTm(startTime);
        cmsaRptVo.setEndTm(endTime);
        cmsaRptVo.setIsTurnToFlalg(cmsaRptDto.getIsTurnToFlalg());
        cmsaRptVo.setIsTurnToDoc(cmsaRptDto.getIsTurnToDoc());
        cmsaRptVo.setNotUse(cmsaRptDto.getNotUse());
        cmsaRptVo.setShipName(oiShipInfo.getSpnameCn() + "/" + oiShipInfo.getSpname());
        List<GcClient> byCodeList = gcClientRepository.findByCode(oiShipInfo.getDocId());
        if (byCodeList != null && byCodeList.size() > 0) {
            GcClient byCode = byCodeList.get(0);
            cmsaRptVo.setDocName(byCode.getNameCn() + "/" + byCode.getNameEn());
            cmsaRptVo.setDocIacs(byCode.getIacs());
        }
        String flagCode = oiShipInfo.getFlagCode();
        GcState gcState = gcStateRepository.findFlag(flagCode);
        if (gcState != null) {

            cmsaRptVo.setCountryFlag(gcState.getEnName());
        }
        ShipType shipType = shipTypeRepository.findShipType(oiShipInfo.getSptype());
        if (shipType != null) {
            cmsaRptVo.setSpType(shipType.getCsptype());
        }
        if (oiShipInfo.getIsCmsa() == 0) {
            cmsaRptVo.setSpCate("内河船");
        } else {
            cmsaRptVo.setSpCate("国际航行船舶");
        }
        cmsaRptVo.setBuilderTime(oiShipInfo.getBuildContractTm());
        cmsaRptVo.setGrossTons(oiShipInfo.getGross());
        cmsaRptVo.setNetTons(oiShipInfo.getNet());
        cmsaRptVo.setDwTons(oiShipInfo.getDw());
        cmsaRptVo.setEediVal(oiShipInfo.getEedivalue());
        cmsaRptVo.setIceClass(oiShipInfo.getIce());
        cmsaRptVo.setDesignSpeed(oiShipInfo.getSpeed());
        BigDecimal mePower = new BigDecimal(0);
        List<OiMainEngine> oiMainEngines = oiShipInfo.getOiMainEngines();
        for (int i = 0; i < oiMainEngines.size(); i++) {
            OiMainEngine oiMainEngine = oiMainEngines.get(i);
            BigDecimal ratePower = oiMainEngine.getRatePower();
            if (ratePower == null) {
                ratePower = new BigDecimal(0);
            }
            mePower = mePower.add(ratePower);

        }
        cmsaRptVo.setMePower(mePower);
        BigDecimal gePower = new BigDecimal(0);
        List<OiShipGe> oiShipGes = oiShipInfo.getOiShipGes();
        for (int i = 0; i < oiShipGes.size(); i++) {
            OiShipGe oiShipGe = oiShipGes.get(i);
            BigDecimal ratePower = oiShipGe.getRatePower();
            if (ratePower == null) {
                ratePower = new BigDecimal(0);
            }
            gePower = gePower.add(ratePower);
        }
        cmsaRptVo.setGePower(gePower);
        BigDecimal cargoDistance = this.getVoyageEeoiTd(oiShipVoyages).get("eeoiTd");
        BigDecimal teuDistance = this.getVoyageEeoiTd(oiShipVoyages).get("teuDistance");
        BigDecimal peopleDistance = this.getVoyageEeoiTd(oiShipVoyages).get("peopleDistance");
        cmsaRptVo.setTonsNm(cargoDistance.divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP));
        cmsaRptVo.setTeuNms(teuDistance);
        cmsaRptVo.setPesNms(peopleDistance);
        cmsaRptVo.setDistOnsea(distanceOnSea);
        cmsaRptVo.setHourOnsea(hourOnSea.setScale(2, BigDecimal.ROUND_HALF_UP));
        cmsaRptVo.setHourOpera(hourOnSea.add(hourOprate).setScale(2, BigDecimal.ROUND_HALF_UP));
        cmsaRptVo.setLandPowerKwh(andian);
        cmsaRptVo.setImono(oiShipInfo.getImono());
        BeanUtils.copyProperties(cmsaRptVo, cmsaRpt);
        cmsaRpt.setId(cmsaRptId);
        cmsaRpt.setSpType(oiShipInfo.getSptype());
        cmsaRpt.setShipId(shipId);
        if (!StringUtils.isNotEmpty(cmsaRptId)) {
            cmsaRpt.setIsDelete(0);
            cmsaRpt.setRecStatus("1");
            cmsaRpt.setRptSource("1");
            cmsaRpt.setRefCode(UUID32());
        }
        cmsaRpt.setImono(oiShipInfo.getImono());
        CmsaRpt save = cmsaRptRepository.save(cmsaRpt);
        // 查询燃油种类
        List<String> oilIds = shipEquipmentFuelMapRepository.findByShipIdAndIsDelete(shipId);
        if (oilIds.size() == 0) {
            oilIds = Arrays.asList(FuelConst.ALL_OIL_ID.split(","));
        }
        List<CmsaFuelTypeVo> cmsaFuelTypeVos = new ArrayList<>();
        String oilId = null;
        String oilName = null;
        String method = null;
        BigDecimal fuelTons = new BigDecimal(0);
        List<CmsaRptOilCons> cmsaRptOilConsList = new ArrayList<>();
        if (oilIds.contains(FuelConst.HFO_HIGHT) || oilIds.contains(FuelConst.HFO_LOW)
                || oilIds.contains(FuelConst.HFO_CHAO_LOW)) {
            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = "1";
            oilName = FuelConst.HFO_NAME;
            String method1 = map.get(FuelConst.HFO_HIGHT);
            if (method1 != null) {
                method = mapCn.get(method1);
            } else {
                String method2 = map.get(FuelConst.HFO_LOW);
                String method3 = map.get(FuelConst.HFO_CHAO_LOW);
                if (method2 != null) {
                    method = mapCn.get(method2);
                } else {
                    method = mapCn.get(method3);
                }
            }
            fuelTons = hfo;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);

        }
        if (oilIds.contains(FuelConst.LFO_HIGHT) || oilIds.contains(FuelConst.LFO_LOW)
                || oilIds.contains(FuelConst.LFO_CHAO_LOW)) {
            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = "2";
            oilName = FuelConst.LFO_NAME;
            String method1 = map.get(FuelConst.LFO_HIGHT);
            if (method1 != null) {
                method = mapCn.get(method1);
            } else {
                String method2 = map.get(FuelConst.LFO_LOW);
                String method3 = map.get(FuelConst.LFO_CHAO_LOW);
                if (method2 != null) {
                    method = mapCn.get(method2);
                } else {
                    method = mapCn.get(method3);
                }
            }
            fuelTons = lfo;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);
        }
        if (oilIds.contains(FuelConst.CHAI_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = FuelConst.CHAI_CODE;
            oilName = FuelConst.CHAI_CODE_NAME;
            method = mapCn.get(map.get(FuelConst.CHAI_CODE));
            fuelTons = chaifo;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);
        }
        if (oilIds.contains(FuelConst.TIAN_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = FuelConst.TIAN_CODE;
            oilName = FuelConst.TIAN_CODE_NAME;
            method = mapCn.get(map.get(FuelConst.TIAN_CODE));
            fuelTons = lng;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);
        }
        if (oilIds.contains(FuelConst.BING_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = FuelConst.BING_CODE;
            oilName = FuelConst.BING_NAME;
            method = mapCn.get(map.get(FuelConst.BING_CODE));
            fuelTons = bing;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);
        }
        if (oilIds.contains(FuelConst.DING_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = FuelConst.DING_CODE;
            oilName = FuelConst.DING_NAME;
            method = mapCn.get(map.get(FuelConst.DING_CODE));
            fuelTons = ding;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);
        }
        if (oilIds.contains(FuelConst.METHAN_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = FuelConst.METHAN_CODE;
            oilName = FuelConst.METHAN_NAME;
            method = mapCn.get(map.get(FuelConst.METHAN_CODE));
            fuelTons = me;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);
        }
        if (oilIds.contains(FuelConst.OIETHAN_CODE)) {
            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            CmsaRptOilCons cmsaRptOilCons = new CmsaRptOilCons();
            oilId = FuelConst.OIETHAN_CODE;
            oilName = FuelConst.OIETHAN_NAME;
            method = mapCn.get(map.get(FuelConst.OIETHAN_CODE));
            fuelTons = ethe;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
            cmsaRptOilCons.setCmsaFuelId(oilId);
            cmsaRptOilCons.setImono(oiShipInfo.getImono());
            cmsaRptOilCons.setIsDelete(0);
            cmsaRptOilCons.setRecStatus("1");
            cmsaRptOilCons.setTons(fuelTons);
            cmsaRptOilCons.setUsedMethod(method);
            cmsaRptOilCons.setRptId(save.getId());
            cmsaRptOilConsList.add(cmsaRptOilCons);
        }
        cmsaRptOilConsRepository.saveAll(cmsaRptOilConsList);
        for (int i = 0; i < cmsaFuelTypeVos.size(); i++) {
            CmsaFuelTypeVo cmsaFuelTypeVo = cmsaFuelTypeVos.get(i);
            cmsaFuelTypeVo.setOilName("燃料" + (i + 1));
            cmsaFuelTypeVo.setOilNameEn("Fuel" + (i + 1));
        }
        cmsaRptVo.setCmsaFuelTypeVos(cmsaFuelTypeVos);
        return new ResultObjectVo<>(cmsaRptVo);
    }

    public CmsaRptVo getCmsaRptDownLoad(CmsaRptDto cmsaRptDto) {
        Map<String, String> mapCn = fuelConsMethodProperties.getMapCn();
        String id = cmsaRptDto.getId();
        CmsaRpt byIdAndIsDelete = cmsaRptRepository.findByIdAndIsDelete(id, 0);
        String cmsaRptId = null;
        CmsaRpt cmsaRpt = new CmsaRpt();
        Boolean voyageRpt = cmsaRptDto.getVoyageRpt();
        Boolean yearRpt = cmsaRptDto.getYearRpt();
        String shipId = cmsaRptDto.getShipId();
        Date startTime = cmsaRptDto.getStartTime();
        Date endTime = cmsaRptDto.getEndTime();
        Map<String, String> map = new HashMap<>();
        // 根据船舶id查询油耗类型
        List<ShipEquipmentFuelMap> useMethod = shipEquipmentFuelMapRepository.findUseMethod(shipId);
        useMethod.forEach(shipEquipmentFuelMap -> {
            map.put(shipEquipmentFuelMap.getFuelId(), shipEquipmentFuelMap.getConsMethod());
        });
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<OiShipTask> oiShipTasks = new ArrayList<>();
        OiShipTask oiShipTask = null;
        if (voyageRpt) {
            oiShipTask = oiShipTaskRepository.findByIdAndIsDelete(cmsaRptDto.getTaskId(), 0);
            oiShipTasks.add(oiShipTask);
            cmsaRpt.setRptType("1");
            cmsaRpt.setTaskId(oiShipTask.getId());
        }
        if (yearRpt) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            int endYear = instance.get(Calendar.YEAR);
            int startYear = endYear - 1;
            if (startTime == null || endTime == null) {
                try {
                    startTime = simpleDateFormat.parse(startYear + "-01-01");
                    endTime = simpleDateFormat.parse(startYear + "-12-31");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            List<OiShipTask> oiShipTaskEndTime = oiShipTaskRepository.findOiShipTaskEndTime(shipId,
                    simpleDateFormat.format(startTime), simpleDateFormat.format(endTime));
            oiShipTasks.addAll(oiShipTaskEndTime);
            cmsaRpt.setRptType("0");
        }
        List<OiShipVoyage> oiShipVoyages = new ArrayList<>();
        BigDecimal distanceOnSea = new BigDecimal(0);
        BigDecimal hourOnSea = new BigDecimal(0);
        BigDecimal hourOprate = new BigDecimal(0);// 营运时间
        BigDecimal andian = new BigDecimal(0);// 岸电消耗量
        BigDecimal hfo = new BigDecimal(0);
        BigDecimal lfo = new BigDecimal(0);
        BigDecimal chaifo = new BigDecimal(0);
        BigDecimal lng = new BigDecimal(0);
        BigDecimal bing = new BigDecimal(0);
        BigDecimal ding = new BigDecimal(0);
        BigDecimal ethe = new BigDecimal(0);// 乙醇
        BigDecimal me = new BigDecimal(0);// 甲醇
        for (int i = 0; i < oiShipTasks.size(); i++) {
            OiShipTask oiShipTaskGet = oiShipTasks.get(i);
            List<RawVoyagePort> rawVoyagePorts = oiShipTaskGet.getRawVoyagePorts();
            List<RawVoyagePort> rawVoyagePortsNext = oiShipTaskGet.getRawVoyagePortsNext();
            rawVoyagePorts.addAll(rawVoyagePortsNext);
            for (int j = 0; j < rawVoyagePorts.size(); j++) {
                RawVoyagePort rawVoyagePort = rawVoyagePorts.get(j);
                BigDecimal shorePower = rawVoyagePort.getShorePower();
                if (shorePower == null) {
                    shorePower = new BigDecimal(0);
                }
                andian = andian.add(shorePower);
            }
            List<RawVoyageSpec> rawVoyageSpecs = oiShipTaskGet.getRawVoyageSpecs();
            if (rawVoyagePorts.size() < 2) {
                break;
//                return new ResultErrorVo(this.msg("energyEeffic.volage.rawPortNum"));
            }
            rawVoyagePorts.sort((x, y) -> {
                if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                    return x.getCreateTm().compareTo(y.getCreateTm());
                } else {
                    return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                }
            });
            if (!"0".equals(rawVoyagePorts.get(0).getInPort())) {
//                return new ResultErrorVo(this.msg("energyEeffic.volage.startRawPortInert"));
                break;
            }
            if (!"0".equals(rawVoyagePorts.get(rawVoyagePorts.size() - 1).getInPort())) {
//                return new ResultErrorVo(this.msg("energyEeffic.volage.endRawPortInert"));
                break;
            }
            Map<String, List<List<RawVoyagePort>>> euSegmentsSAndImoASegments = getEuSegmentsSAndImoASegments(
                    rawVoyagePorts);
            List<List<RawVoyagePort>> imoSements = euSegmentsSAndImoASegments.get("IMO");
            BigDecimal zero = new BigDecimal(0);
            for (int j = 0; j < imoSements.size(); j++) {
                List<RawVoyagePort> rawVoyagePortsImo = imoSements.get(j);
                OiShipVoyage oiShipVoyage = generatorAcrossInfo(rawVoyagePortsImo, rawVoyageSpecs);
                oiShipVoyages.add(oiShipVoyage);
                BigDecimal distance = oiShipVoyage.getDistance();
                BigDecimal oiHfo = oiShipVoyage.getOiHfo();
                BigDecimal stopoiHfo = oiShipVoyage.getStopoiHfo();
                if (oiHfo == null) {
                    oiHfo = zero;
                }
                if (stopoiHfo == null) {
                    stopoiHfo = zero;
                }
                hfo = hfo.add(oiHfo).add(stopoiHfo);
                BigDecimal oiLfo = oiShipVoyage.getOiLfo();
                BigDecimal stopoiLfo = oiShipVoyage.getStopoiLfo();
                if (oiLfo == null) {
                    oiLfo = zero;
                }
                if (stopoiLfo == null) {
                    stopoiLfo = zero;
                }
                lfo = lfo.add(oiLfo).add(stopoiLfo);
                BigDecimal oiChai = oiShipVoyage.getOiChai();
                BigDecimal stopoiChai = oiShipVoyage.getStopoiChai();
                if (oiChai == null) {
                    oiChai = zero;
                }
                if (stopoiChai == null) {
                    stopoiChai = zero;
                }
                chaifo = chaifo.add(oiChai).add(stopoiChai);
                BigDecimal oiTian = oiShipVoyage.getOiTian();
                BigDecimal stopoiTian = oiShipVoyage.getStopoiTian();
                if (oiTian == null) {
                    oiTian = zero;
                }
                if (stopoiTian == null) {
                    stopoiTian = zero;
                }
                lng.add(oiTian).add(stopoiTian);
                BigDecimal oiBing = oiShipVoyage.getOiBing();
                BigDecimal stopoiBing = oiShipVoyage.getStopoiBing();
                if (oiBing == null) {
                    oiBing = zero;
                }
                if (stopoiBing == null) {
                    stopoiBing = zero;
                }
                bing = bing.add(oiBing).add(stopoiBing);
                BigDecimal oiDing = oiShipVoyage.getOiDing();
                BigDecimal stopoiDing = oiShipVoyage.getStopoiDing();
                if (oiDing == null) {
                    oiDing = zero;
                }
                if (stopoiDing == null) {
                    stopoiDing = zero;
                }
                ding.add(oiDing).add(stopoiDing);
                BigDecimal oiOther = oiShipVoyage.getOiOther();// 甲醇
                BigDecimal oiOtherStop = oiShipVoyage.getStopoiOther();// 甲醇
                if (oiOther == null) {
                    oiOther = zero;
                }
                if (oiOtherStop == null) {
                    oiOtherStop = zero;
                }
                me = me.add(oiOther).add(oiOtherStop);
                BigDecimal oiethanol = oiShipVoyage.getOiethanol();
                BigDecimal stopethanol = oiShipVoyage.getStopethanol();// 乙醇
                if (oiethanol == null) {
                    oiethanol = zero;
                }
                if (stopethanol == null) {
                    stopethanol = zero;
                }
                ethe = ethe.add(oiethanol).add(stopethanol);
                if (distance == null) {
                    distance = new BigDecimal(0);
                }
                distanceOnSea = distanceOnSea.add(distance);
                hourOnSea = hourOnSea.add(getSailtime(oiShipVoyage));
            }
            List<RawVoyagePort> collect = rawVoyagePorts.stream().filter(rawVoyagePort -> {
                List<RawVoyagePortloading> rawVoyagePortloadings = rawVoyagePort.getRawVoyagePortloadings();
                RawVoyagePortloading rawVoyagePortloadingArr = rawVoyagePortloadings.get(0);
                RawVoyagePortloading rawVoyagePortloadingEnd = rawVoyagePortloadings.get(1);
                BigDecimal cargoTonsArr = rawVoyagePortloadingArr.getCargoTons();
                BigDecimal cargoTonsEnd = rawVoyagePortloadingEnd.getCargoTons();
                if (cargoTonsArr == null) {
                    cargoTonsArr = new BigDecimal(0);
                }
                if (cargoTonsEnd == null) {
                    cargoTonsEnd = new BigDecimal(0);
                }
                String portWork = rawVoyagePort.getPortWork();
                List<String> strings = Arrays.asList(portWork.split(","));
                return cargoTonsArr.compareTo(cargoTonsEnd) != 0 && !(strings.contains("8") || strings.contains("6"));// 6代表修理
                // 8：代表避险
            }).collect(Collectors.toList());
            BigDecimal hourOprateTime = calculateStopTime(collect);
            hourOprate = hourOprate.add(hourOprateTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
        }
        CmsaRptVo cmsaRptVo = new CmsaRptVo();
        cmsaRptVo.setTeu(oiShipInfo.getTeus());
        String spname = oiShipInfo.getSpname();
        String spnameCn = oiShipInfo.getSpnameCn();
        cmsaRptVo.setRptOrg(spnameCn + "/" + spname);
        cmsaRptVo.setVerifierName("中国船级社/China Classification Society");
        cmsaRptVo.setVoyageRpt(voyageRpt);
        if (voyageRpt) {
            String startPortId = oiShipTask.getStartPort();
            String destPortId = oiShipTask.getDestPort();
            RawVoyagePort startPort = rawVoyagePortRepository.findByIdAndIsDelete(startPortId, 0);
            RawVoyagePort endPort = rawVoyagePortRepository.findByIdAndIsDelete(destPortId, 0);
            if (startPort != null) {
                cmsaRptVo.setPrePort(startPort.getPorten() + "/" + startPort.getPorten());
            }
            cmsaRptVo.setPrePortArrTime(oiShipTask.getStartTime());
            if (endPort != null) {
                cmsaRptVo.setNextPort(endPort.getPortcn() + "/" + endPort.getPorten());
            }
            cmsaRptVo.setNextPortArrTime(oiShipTask.getEndTime());
            startTime = oiShipTask.getStartTime();
            endTime = oiShipTask.getEndTime();
        }
        cmsaRptVo.setYearRpt(yearRpt);
        cmsaRptVo.setStartTm(startTime);
        cmsaRptVo.setEndTm(endTime);
        cmsaRptVo.setIsTurnToFlalg(cmsaRptDto.getIsTurnToFlalg());
        cmsaRptVo.setIsTurnToDoc(cmsaRptDto.getIsTurnToDoc());
        cmsaRptVo.setNotUse(cmsaRptDto.getNotUse());
        cmsaRptVo.setShipName(oiShipInfo.getSpnameCn() + "/" + oiShipInfo.getSpname());
        List<GcClient> byCodeList = gcClientRepository.findByCode(oiShipInfo.getDocId());
        if (byCodeList != null && byCodeList.size() > 0) {
            GcClient byCode = byCodeList.get(0);
            cmsaRptVo.setDocName(byCode.getNameCn() + "/" + byCode.getNameEn());
            cmsaRptVo.setDocIacs(byCode.getIacs());
        }
        String flagCode = oiShipInfo.getFlagCode();
        GcState gcState = gcStateRepository.findFlag(flagCode);
        if (gcState != null) {

            cmsaRptVo.setCountryFlag(gcState.getEnName());
        }
        ShipType shipType = shipTypeRepository.findShipType(oiShipInfo.getSptype());
        if (shipType != null) {
            cmsaRptVo.setSpType(shipType.getCsptype());
        }
        if (oiShipInfo.getIsCmsa() == 0) {
            cmsaRptVo.setSpCate("内河船");
        } else {
            cmsaRptVo.setSpCate("国际航行船舶");
        }
        cmsaRptVo.setBuilderTime(oiShipInfo.getBuildContractTm());
        cmsaRptVo.setGrossTons(oiShipInfo.getGross());
        cmsaRptVo.setNetTons(oiShipInfo.getNet());
        cmsaRptVo.setDwTons(oiShipInfo.getDw());
        cmsaRptVo.setEediVal(oiShipInfo.getEedivalue());
        cmsaRptVo.setIceClass(oiShipInfo.getIce());
        cmsaRptVo.setDesignSpeed(oiShipInfo.getSpeed());
        BigDecimal mePower = new BigDecimal(0);
        List<OiMainEngine> oiMainEngines = oiShipInfo.getOiMainEngines();
        for (int i = 0; i < oiMainEngines.size(); i++) {
            OiMainEngine oiMainEngine = oiMainEngines.get(i);
            BigDecimal ratePower = oiMainEngine.getRatePower();
            if (ratePower == null) {
                ratePower = new BigDecimal(0);
            }
            mePower = mePower.add(ratePower);

        }
        cmsaRptVo.setMePower(mePower);
        BigDecimal gePower = new BigDecimal(0);
        List<OiShipGe> oiShipGes = oiShipInfo.getOiShipGes();
        for (int i = 0; i < oiShipGes.size(); i++) {
            OiShipGe oiShipGe = oiShipGes.get(i);
            BigDecimal ratePower = oiShipGe.getRatePower();
            if (ratePower == null) {
                ratePower = new BigDecimal(0);
            }
            gePower = gePower.add(ratePower);
        }
        cmsaRptVo.setGePower(gePower);
        BigDecimal cargoDistance = this.getVoyageEeoiTd(oiShipVoyages).get("eeoiTd");
        BigDecimal teuDistance = this.getVoyageEeoiTd(oiShipVoyages).get("teuDistance");
        BigDecimal peopleDistance = this.getVoyageEeoiTd(oiShipVoyages).get("peopleDistance");
        cmsaRptVo.setTonsNm(cargoDistance.divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP));
        cmsaRptVo.setTeuNms(teuDistance);
        cmsaRptVo.setPesNms(peopleDistance);
        cmsaRptVo.setDistOnsea(distanceOnSea);
        cmsaRptVo.setHourOnsea(hourOnSea.setScale(2, BigDecimal.ROUND_HALF_UP));
        cmsaRptVo.setHourOpera(hourOnSea.add(hourOprate).setScale(2, BigDecimal.ROUND_HALF_UP));
        cmsaRptVo.setLandPowerKwh(andian);
        cmsaRptVo.setImono(oiShipInfo.getImono());
        BeanUtils.copyProperties(cmsaRptVo, cmsaRpt);
        cmsaRpt.setId(cmsaRptId);
        cmsaRpt.setSpType(oiShipInfo.getSptype());
        cmsaRpt.setShipId(shipId);
        if (!StringUtils.isNotEmpty(cmsaRptId)) {
            cmsaRpt.setIsDelete(0);
            cmsaRpt.setRecStatus("1");
            cmsaRpt.setRptSource("1");
            cmsaRpt.setRefCode(UUID32());
        }
        cmsaRpt.setImono(oiShipInfo.getImono());
//        CmsaRpt save = cmsaRptRepository.save(cmsaRpt);
        // 查询燃油种类
        List<String> oilIds = shipEquipmentFuelMapRepository.findByShipIdAndIsDelete(shipId);
        if (oilIds.size() == 0) {
            oilIds = Arrays.asList(FuelConst.ALL_OIL_ID.split(","));
        }
        List<CmsaFuelTypeVo> cmsaFuelTypeVos = new ArrayList<>();
        String oilId = null;
        String oilName = null;
        String method = null;
        BigDecimal fuelTons = new BigDecimal(0);
        List<CmsaRptOilCons> cmsaRptOilConsList = new ArrayList<>();
        if (oilIds.contains(FuelConst.HFO_HIGHT) || oilIds.contains(FuelConst.HFO_LOW)
                || oilIds.contains(FuelConst.HFO_CHAO_LOW)) {
            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = "1";
            oilName = FuelConst.HFO_NAME;
            String method1 = map.get(FuelConst.HFO_HIGHT);
            if (method1 != null) {
                method = mapCn.get(method1);
            } else {
                String method2 = map.get(FuelConst.HFO_LOW);
                String method3 = map.get(FuelConst.HFO_CHAO_LOW);
                if (method2 != null) {
                    method = mapCn.get(method2);
                } else {
                    method = mapCn.get(method3);
                }
            }
            fuelTons = hfo;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);

        }
        if (oilIds.contains(FuelConst.LFO_HIGHT) || oilIds.contains(FuelConst.LFO_LOW)
                || oilIds.contains(FuelConst.LFO_CHAO_LOW)) {
            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = "2";
            oilName = FuelConst.LFO_NAME;
            String method1 = map.get(FuelConst.LFO_HIGHT);
            if (method1 != null) {
                method = mapCn.get(method1);
            } else {
                String method2 = map.get(FuelConst.LFO_LOW);
                String method3 = map.get(FuelConst.LFO_CHAO_LOW);
                if (method2 != null) {
                    method = mapCn.get(method2);
                } else {
                    method = mapCn.get(method3);
                }
            }
            fuelTons = lfo;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
        }
        if (oilIds.contains(FuelConst.CHAI_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = FuelConst.CHAI_CODE;
            oilName = FuelConst.CHAI_CODE_NAME;
            method = mapCn.get(map.get(FuelConst.CHAI_CODE));
            fuelTons = chaifo;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
        }
        if (oilIds.contains(FuelConst.TIAN_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = FuelConst.TIAN_CODE;
            oilName = FuelConst.TIAN_CODE_NAME;
            method = mapCn.get(map.get(FuelConst.TIAN_CODE));
            fuelTons = lng;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
        }
        if (oilIds.contains(FuelConst.BING_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = FuelConst.BING_CODE;
            oilName = FuelConst.BING_NAME;
            method = mapCn.get(map.get(FuelConst.BING_CODE));
            fuelTons = bing;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
        }
        if (oilIds.contains(FuelConst.DING_CODE)) {

            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = FuelConst.DING_CODE;
            oilName = FuelConst.DING_NAME;
            method = mapCn.get(map.get(FuelConst.DING_CODE));
            fuelTons = ding;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
        }
        if (oilIds.contains(FuelConst.METHAN_CODE)) {
            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = FuelConst.METHAN_CODE;
            oilName = FuelConst.METHAN_NAME;
            method = mapCn.get(map.get(FuelConst.METHAN_CODE));
            fuelTons = me;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
        }
        if (oilIds.contains(FuelConst.OIETHAN_CODE)) {
            CmsaFuelTypeVo cmsaFuelTypeVo = new CmsaFuelTypeVo();
            oilId = FuelConst.OIETHAN_CODE;
            oilName = FuelConst.OIETHAN_NAME;
            method = mapCn.get(map.get(FuelConst.OIETHAN_CODE));
            fuelTons = ethe;
            cmsaFuelTypeVo.setMethod(method);
            cmsaFuelTypeVo.setFuelTons(fuelTons);
            cmsaFuelTypeVo.setOilId(oilId);
            cmsaFuelTypeVo.setOilName(oilName);
            cmsaFuelTypeVos.add(cmsaFuelTypeVo);
        }
        for (int i = 0; i < cmsaFuelTypeVos.size(); i++) {
            CmsaFuelTypeVo cmsaFuelTypeVo = cmsaFuelTypeVos.get(i);
            cmsaFuelTypeVo.setOilName("燃料" + (i + 1));
            cmsaFuelTypeVo.setOilNameEn("Fuel" + (i + 1));
        }
        cmsaRptVo.setCmsaFuelTypeVos(cmsaFuelTypeVos);
        return cmsaRptVo;
    }

    @Override
    public ResultVo cmsaRptManager(ImoDcsRptManagerDto imoDcsRptManagerDto) {
        // 分页条件
        PageRequest of = PageRequest.of((imoDcsRptManagerDto.getCurrentPage() - 1), imoDcsRptManagerDto.getPageSize(),
                Sort.by("startTm", "oiShipInfo.spname").ascending());
        Page<CmsaRpt> all = cmsaRptRepository.findAll(queryCmsaReaportList(imoDcsRptManagerDto), of);
        List<CmsaRpt> imoStdrpts = all.getContent();
        List<ImoDcsRptManagerVo> imoDcsRptManagerVos = new ArrayList<>();
        for (int i = 0; i < imoStdrpts.size(); i++) {
            CmsaRpt cmsaRpt = imoStdrpts.get(i);
            ImoDcsRptManagerVo imoDcsRptManagerVo = new ImoDcsRptManagerVo();
            imoDcsRptManagerVo.setId(cmsaRpt.getId());
            imoDcsRptManagerVo.setDocManager(cmsaRpt.getDocName());
            imoDcsRptManagerVo.setFlag(cmsaRpt.getCountryFlag());
            imoDcsRptManagerVo.setImoNu(cmsaRpt.getImono());
            imoDcsRptManagerVo.setRptEndTime(cmsaRpt.getEndTm());
            imoDcsRptManagerVo.setRptStartTime(cmsaRpt.getStartTm());
            ShipType shipType = shipTypeRepository.findShipType(cmsaRpt.getSpType());
            imoDcsRptManagerVo.setShipType(shipType == null ? null : shipType.getSptype());
            imoDcsRptManagerVo.setRptStatus(cmsaRpt.getRecStatus());
            imoDcsRptManagerVo.setReaportSource(cmsaRpt.getRptSource());
            imoDcsRptManagerVo.setShipName(cmsaRpt.getShipName());
            String shipId = cmsaRpt.getShipId();
            imoDcsRptManagerVo.setShipId(shipId);
            imoDcsRptManagerVo.setTaskId(cmsaRpt.getTaskId());
            // 查询该船是否有船旗转换或者经营人变更
            List<OiShipChglog> oiShipChglogFlag = oiShipChglogRepository.findByShipIdAndIsDeleteAndChgType(shipId, 0,
                    0);// 查询船旗国变更
            List<OiShipChglog> oiShipChglogDoc = oiShipChglogRepository.findByShipIdAndIsDeleteAndChgType(shipId, 0, 1);// 查询经营人变更
            imoDcsRptManagerVo.setIsTurnToFlalg(oiShipChglogFlag.size() > 0);
            imoDcsRptManagerVo.setIsTurnToDoc(oiShipChglogDoc.size() > 0);
            imoDcsRptManagerVo.setNotUse(oiShipChglogDoc.size() == 0 && oiShipChglogFlag.size() == 0);
            imoDcsRptManagerVo.setRptType(cmsaRpt.getRptType());
            imoDcsRptManagerVo.setVoyageRpt(cmsaRpt.getRptType().equals("1"));
            imoDcsRptManagerVo.setYearRpt(cmsaRpt.getRptType().equals("0"));
            imoDcsRptManagerVos.add(imoDcsRptManagerVo);
        }
        PageDataVo<ImoDcsRptManagerVo> imoDcsRptManagerVoPageDataVo = new PageDataVo<>();
        imoDcsRptManagerVoPageDataVo.setItems(imoDcsRptManagerVos);
        imoDcsRptManagerVoPageDataVo.setTotal((int) all.getTotalElements());
        return new ResultObjectVo<>(imoDcsRptManagerVoPageDataVo);
    }

    @Override
    public void downLoadCmsaRpt(CmsaRptDto cmsaRptDto, HttpServletResponse response) {
        Boolean isTurnToDoc = cmsaRptDto.getIsTurnToDoc();
        Boolean isTurnToFlalg = cmsaRptDto.getIsTurnToFlalg();
        Boolean notUse = cmsaRptDto.getNotUse();
        Boolean voyageRpt = cmsaRptDto.getVoyageRpt();
        Boolean yearRpt = cmsaRptDto.getYearRpt();
        CmsaRptVo cmsaRptDownLoad = getCmsaRptDownLoad(cmsaRptDto);
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/downLoad/CMSA_REAPORT.xlsx");
        if (cmsaRptDownLoad != null) {
            Map<String, Object> map = EntityBeanUtils.objectToMap(cmsaRptDownLoad);
            if (voyageRpt) {
                map.put("voyageReaPort", "☒");
            } else {
                map.put("voyageReaPort", "□");
            }
            if (yearRpt) {
                map.put("yearReaPort", "☒");
            } else {
                map.put("yearReaPort", "□");
            }
            if (isTurnToFlalg) {
                map.put("transferFlag", "☒");
            } else {
                map.put("transferFlag", "□");
            }
            if (isTurnToDoc) {
                map.put("transferOwner", "☒");
            } else {
                map.put("transferOwner", "□");
            }
            if (notUse) {
                map.put("notUse", "☒");
            } else {
                map.put("notUse", "□");
            }
            XLSTransformer xlsTransformer = new XLSTransformer();
            List<CmsaFuelTypeVo> cmsaFuelTypeVos = (List<CmsaFuelTypeVo>) map.get("cmsaFuelTypeVos");
            downLoadCmsaResponse(response, resourceAsStream, xlsTransformer, map, cmsaFuelTypeVos.size());
        }
    }

    @Override
    @Transactional
    public ResultVo deleteCmsaRpt(String cmsaRptId) {
        CmsaRpt cmsaRpt = cmsaRptRepository.findByIdAndIsDelete(cmsaRptId, 0);
        CmsaRpt save = null;
        if (cmsaRpt != null) {
            cmsaRpt.setIsDelete(1);
            List<CmsaRptOilCons> cmsaRptOilCons = cmsaRpt.getCmsaRptOilCons();
            cmsaRptOilCons.forEach(cmsaRptOilCons1 -> {
                cmsaRptOilCons1.setIsDelete(1);
            });
            save = cmsaRptRepository.save(cmsaRpt);
        }
        if (save != null) {
            return new ResultStringVo(this.msg("energyEeffic.delete.success"));
        } else {
            return new ResultErrorVo(this.msg("energyEeffic.delete.failed"));
        }
    }

    @Override
    @Transactional
    public ResultVo lockOrUnlock(CmsaRptDto cmsaRptDto) {
        CmsaRpt cmsaRpt = cmsaRptRepository.findByIdAndIsDelete(cmsaRptDto.getId(), 0);
        String recStatus = cmsaRpt.getRecStatus();
        String setRecStatus = "";
        if ("2".equals(recStatus) || "3".equals(recStatus)) {
            setRecStatus = "1";

        } else {
            setRecStatus = "2";
        }
        cmsaRpt.setRecStatus(setRecStatus);
        List<CmsaRptOilCons> cmsaRptOilCons = cmsaRpt.getCmsaRptOilCons();
        for (int i = 0; i < cmsaRptOilCons.size(); i++) {
            CmsaRptOilCons cmsaRptOilCons1 = cmsaRptOilCons.get(i);
            cmsaRptOilCons1.setRecStatus(setRecStatus);
        }
        CmsaRpt save = cmsaRptRepository.save(cmsaRpt);
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
    public ResultVo cmsaRptStatusTag(CmsaRptDto cmsaRptDto) {
        try {
            List<String> cmsaRptIds = cmsaRptDto.getCmsaRptIds();
            List<CmsaRpt> cmsaRpts = cmsaRptRepository.findByIdInAndIsDelete(cmsaRptIds, 0);
            for (int i = 0; i < cmsaRpts.size(); i++) {
                CmsaRpt cmsaRpt = cmsaRpts.get(i);
                cmsaRpt.setRecStatus("3");
                List<CmsaRptOilCons> cmsaRptOilCons = cmsaRpt.getCmsaRptOilCons();
                for (int j = 0; j < cmsaRptOilCons.size(); j++) {
                    CmsaRptOilCons cmsaRptOilCons1 = cmsaRptOilCons.get(j);
                    cmsaRptOilCons1.setRecStatus("3");
                }
            }
            Iterable<CmsaRpt> cmsaRpts1 = cmsaRptRepository.saveAll(cmsaRpts);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            return new ResultErrorVo(this.msg("imoStdRptStatus.tag.fail"));
        }
        return new ResultStringVo("imoStdRptStatus.tag.success");
    }

    @Override
    public ResultVo checkShipEnergyInfo(String shipId) {
        List<OiShipVoyage> byShipIdAndIsDelete = oiShipVoyageRepository.findByShipIdAndIsDelete(shipId, 0);
        if (byShipIdAndIsDelete != null && byShipIdAndIsDelete.size() > 0) {
            return new ResultErrorVo(this.msg("ship.exits.energy"));
        }
        return new ResultStringVo("");
    }

    private Specification<CmsaRpt> queryCmsaReaportList(ImoDcsRptManagerDto imoDcsRptManagerDto) {
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
        return new Specification<CmsaRpt>() {
            @Override
            public Predicate toPredicate(Root<CmsaRpt> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicatesOr = new ArrayList<>();
                List<Predicate> predicatesAnd = new ArrayList<>();
                Join<CmsaRpt, OiShipInfo> join = root.join("oiShipInfo", JoinType.LEFT);
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
                    predicatesAnd.add(
                            cb.like(cb.upper(cb.trim(root.get("countryFlag"))), "%" + flag.trim().toUpperCase() + "%"));
                }
                if (startTime != null && endTime != null) {
                    predicatesAnd.add(cb.between(root.<Date>get("startTm"), startTime, endTime));
                    predicatesAnd.add(cb.between(root.<Date>get("endTm"), startTime, endTime));
                }
                if (StringUtils.isNotEmpty(imoRptStatus)) {
                    predicatesAnd.add(cb.equal(root.get("recStatus"), imoRptStatus));
                }
                Predicate and = cb.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
                if (StringUtils.isNotEmpty(keyword)) {
                    predicatesOr.add(
                            cb.like(cb.upper(cb.trim(root.get("imono"))), "%" + keyword.trim().toUpperCase() + "%"));
                    predicatesOr.add(
                            cb.like(cb.upper(cb.trim(root.get("docName"))), "%" + keyword.trim().toUpperCase() + "%"));
                    predicatesOr.add(
                            cb.like(cb.upper(cb.trim(root.get("shipName"))), "%" + keyword.trim().toUpperCase() + "%"));
                    Predicate or = cb.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
                    return criteriaQuery.where(and, or).getRestriction();
                }
                return criteriaQuery.where(and).getRestriction();
            }
        };
    }

    private RawVoyagePort mergePorts(List<RawVoyagePort> rawVoyagePorts) {
        if (rawVoyagePorts.size() == 0) return null;
//        Gson gson = new Gson();
//        log.error("合并前:" + gson.toJson(rawVoyagePorts));
        if (rawVoyagePorts.size() == 1) return rawVoyagePorts.get(0);

        int size = rawVoyagePorts.size();

        RawVoyagePort same = rawVoyagePorts.get(0);
        same.setDeptTm(rawVoyagePorts.get(size - 1).getDeptTm());        //最后一个离港时间
        same.setDeptZone(rawVoyagePorts.get(size - 1).getDeptZone());

        RawVoyagePort port = null;
//        BigDecimal distance = BigDecimal.ZERO;
        //合并之后的港口信息
        for (int i = 1; i < size; i++) {
            port = rawVoyagePorts.get(i);

            //加油信息
            List<RawVoyageAddoil> rawVoyageAddoils = port.getRawVoyageAddoils();
            List<RawVoyageAddoil> rawVoyageAddoilsNew = BeanCopyUtil.copyListProperties(rawVoyageAddoils, RawVoyageAddoil::new);
            rawVoyageAddoilsNew.forEach(rawVoyageAddoil -> {
                rawVoyageAddoil.setPortinfoId(same.getId());
            });
            List<RawVoyageAddoil> rawVoyageAddoilsSame = same.getRawVoyageAddoils();
            List<RawVoyageAddoil> rawVoyageAddoilsSameNew = BeanCopyUtil.copyListProperties(rawVoyageAddoilsSame, RawVoyageAddoil::new);
            rawVoyageAddoilsSameNew.addAll(rawVoyageAddoilsNew);
//            distance = distance.add(port.getDistance());
            same.setRawVoyageAddoils(rawVoyageAddoilsSameNew);

            //驳油信息
            List<RawVoyageOutoil> rawVoyageOutoils = port.getRawVoyageOutoils();
            List<RawVoyageOutoil> rawVoyageOutoilsNew = BeanCopyUtil.copyListProperties(rawVoyageOutoils, RawVoyageOutoil::new);
            rawVoyageOutoilsNew.forEach(rawVoyageOutoil -> {
                rawVoyageOutoil.setPortinfoId(same.getId());
            });
            List<RawVoyageOutoil> rawVoyageOutoilsSame = same.getRawVoyageOutoils();
            List<RawVoyageOutoil> rawVoyageOutoilsSameNew = BeanCopyUtil.copyListProperties(rawVoyageOutoilsSame, RawVoyageOutoil::new);
            rawVoyageOutoilsSameNew.addAll(rawVoyageOutoilsNew);
            same.setRawVoyageOutoils(rawVoyageOutoilsSameNew);

            //驳油渣
            List<RawVoyageSludge> rawVoyageSludges = port.getRawVoyageSludges();
            List<RawVoyageSludge> rawVoyageSludgesNew = BeanCopyUtil.copyListProperties(rawVoyageSludges, RawVoyageSludge::new);
            rawVoyageSludgesNew.forEach(rawVoyageSludge -> {
                rawVoyageSludge.setPortinfoId(same.getId());
            });
            List<RawVoyageSludge> rawVoyageSludgesSame = same.getRawVoyageSludges();
            List<RawVoyageSludge> rawVoyageSludgesSameNew = BeanCopyUtil.copyListProperties(rawVoyageSludgesSame, RawVoyageSludge::new);
            rawVoyageSludgesSameNew.addAll(rawVoyageSludgesNew);
            same.setRawVoyageSludges(rawVoyageSludgesSameNew);
        }
        //取最后一个港口的离港油量
        Map<String, List<RawVoyagePortoil>> lastPortOilsMap = port.getRawVoyagePortoils().stream().
                collect(Collectors.groupingBy(RawVoyagePortoil::getOilId));
        List<RawVoyagePortoil> sameOils = same.getRawVoyagePortoils();
        List<RawVoyagePortoil> sameOilsNew = BeanCopyUtil.copyListProperties(sameOils, RawVoyagePortoil::new);
        for (RawVoyagePortoil oil : sameOilsNew) {
            String oilId = oil.getOilId();
            if (lastPortOilsMap.containsKey(oilId)) {
                List<RawVoyagePortoil> portoils = lastPortOilsMap.get(oilId);
                if (portoils.size() > 0) {
                    oil.setDeptTons(portoils.get(0).getDeptTons());
                    oil.setTaskIdNext(portoils.get(0).getTaskIdNext());
                }
            }
        }
        same.setRawVoyagePortoils(sameOilsNew);

        //最后一个港口的离港载货
        List<RawVoyagePortloading> lastPortLoading = port.getRawVoyagePortloadings()
                .stream()
                .filter(l -> l.getLoadingType().equals("0"))
                .collect(Collectors.toList());
        List<RawVoyagePortloading> samePortLoading = same.getRawVoyagePortloadings()
                .stream()
                .filter(l -> l.getLoadingType().equals("0"))
                .collect(Collectors.toList());
        if (lastPortLoading.size() > 0 && samePortLoading.size() > 0) {
            samePortLoading.get(0).setCargoTons(lastPortLoading.get(0).getCargoTons());
            samePortLoading.get(0).setBallastTons(lastPortLoading.get(0).getBallastTons());
            samePortLoading.get(0).setAllBoxNum(lastPortLoading.get(0).getAllBoxNum());
            samePortLoading.get(0).setHeavyBoxNum(lastPortLoading.get(0).getHeavyBoxNum());
            samePortLoading.get(0).setPeopleNum(lastPortLoading.get(0).getPeopleNum());
            samePortLoading.get(0).setCarsNum(lastPortLoading.get(0).getCarsNum());
        }

        //下一个任务号
        same.setTaskIdNext(port.getTaskIdNext());
//        same.setDistance(same.getDistance().add(distance));
//        log.error("合并结果 " + gson.toJson(same));
        return same;
    }

    @Override
    public List<RawVoyagePort> procVoyageByMergePorts(List<RawVoyagePort> rawVoyagePorts) {
//查询出本航次交接航次数据
        if (rawVoyagePorts == null)
            return null;
        List<RawVoyagePort> rawVoyagePortsListNew = new ArrayList<>();
        for (RawVoyagePort rawVoyagePort : rawVoyagePorts) {
            RawVoyagePort rawVoyagePortNew = new RawVoyagePort();
            BeanUtils.copyProperties(rawVoyagePort, rawVoyagePortNew);
            rawVoyagePortsListNew.add(rawVoyagePortNew);
        }
        List<RawVoyagePort> mergedPorts = new ArrayList<>();
        //按时间排序
        rawVoyagePortsListNew.sort((x, y) -> {
            if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                if (x.getCreateTm() == null || y.getCreateTm() == null) {
                    return 1;
                }
                return x.getCreateTm().compareTo(y.getCreateTm());
            } else {
                return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
            }
        });

        for (RawVoyagePort ra : rawVoyagePorts) {
            log.error("port id:" + ra.getId() + ",name " + ra.getPorten() + ",port " + ra.getPortid());
        }

        //查找重复港口
        String portName = "";
        String key;
        List<RawVoyagePort> samePorts = new ArrayList<>();
        for (RawVoyagePort port : rawVoyagePortsListNew) {
//            key = Optional.ofNullable(port.getPortid()).orElse("") + "-" + Optional.ofNullable(port.getPorten()).orElse("");
            key = Optional.ofNullable(port.getPortid()).orElse("") + "-" + Optional.ofNullable(port.getPorten()).orElse("")
                    + "-" + port.getInPort();
            log.error("key:" + key);
            if (!portName.equals("")) {
                if (portName.equals(key) && "0".equals(port.getInPort())) {
                    samePorts.add(port);
                } else {
                    if (samePorts.size() > 0) {
                        RawVoyagePort mport = mergePorts(samePorts);
                        mergedPorts.add(mport);
                        samePorts.clear();
                    }
                    portName = key;
                    samePorts.add(port);
                }
            } else {
                portName = key;
                samePorts.add(port);
            }
        }
        //最后一个港口
        if (samePorts.size() > 0) {
            RawVoyagePort mport = mergePorts(samePorts);
            mergedPorts.add(mport);
        }
        Gson gson = new Gson();
        log.error("合并后的结果");
        for (RawVoyagePort ra : mergedPorts) {
            log.error("port id:" + ra.getId() + ",name " + ra.getPorten() + ",port " + ra.getPortid());
        }
        log.error(gson.toJson(mergedPorts));
        return mergedPorts;
    }

    @Override
    public List<RawVoyagePort> mergeHandoverPort(List<RawVoyagePort> rawVoyagePorts) {
        List<RawVoyagePort> rawVoyagePortsListNew = new ArrayList<>();
        for (RawVoyagePort rawVoyagePort : rawVoyagePorts) {
            RawVoyagePort rawVoyagePortNew = new RawVoyagePort();
            BeanUtils.copyProperties(rawVoyagePort, rawVoyagePortNew);
            rawVoyagePortsListNew.add(rawVoyagePortNew);
        }
        if (rawVoyagePortsListNew != null && rawVoyagePortsListNew.size() > 0) {
            RawVoyagePort rawVoyagePort = rawVoyagePortsListNew.get(rawVoyagePortsListNew.size() - 1);
            RawVoyagePort rawVoyagePortPre = rawVoyagePortsListNew.get(0);
            String portenPre = rawVoyagePortPre.getPorten();
            String portidPre = rawVoyagePortPre.getPortid();
            String taskId = rawVoyagePortPre.getTaskId();
            String porten = rawVoyagePort.getPorten();
            String portid = rawVoyagePort.getPortid();
            String taskIdNext = rawVoyagePort.getTaskIdNext();
            if (taskIdNext != null) {
                List<RawVoyagePort> rawVoyagePortsNext = rawVoyagePortRepository.findByTaskIdAndIsDelete(taskIdNext, 0);
                if (rawVoyagePortsNext != null) {
                    rawVoyagePortsNext.sort((x, y) -> {
                        if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                            if (x.getCreateTm() == null || y.getCreateTm() == null) {
                                return 1;
                            }
                            return x.getCreateTm().compareTo(y.getCreateTm());
                        } else {
                            return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                        }
                    });
                    for (RawVoyagePort voyagePort : rawVoyagePortsNext) {
                        String portenNext = voyagePort.getPorten();
                        String portidNext = voyagePort.getPortid();
                        String inPort = voyagePort.getInPort();
                        boolean portEnFlag = false;
                        boolean portIdFlag = false;
                        if (porten != null) {
                            portEnFlag = porten.equals(portenNext);
                        }
                        if (portid != null) {
                            portIdFlag = portid.equals(portidNext);
                        }
                        if ((portEnFlag || portIdFlag) && "0".equals(inPort)) {
                            RawVoyagePort rawVoyagePortNext = new RawVoyagePort();
                            BeanUtils.copyProperties(voyagePort, rawVoyagePortNext);
                            rawVoyagePortsListNew.add(rawVoyagePortNext);
                        } else {
                            break;
                        }
                    }
                }
            }
            if (taskId != null) {
                List<RawVoyagePort> rawVoyagePortsNext = rawVoyagePortRepository.findByTaskIdAndIsDelete(taskId, 0);
                if (rawVoyagePortsNext != null) {
                    rawVoyagePortsNext.sort((x, y) -> {
                        if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                            if (x.getCreateTm() == null || y.getCreateTm() == null) {
                                return 1;
                            }
                            return x.getCreateTm().compareTo(y.getCreateTm());
                        } else {
                            return DateUtils.localToUTC(y.getArrTm(), y.getArrZone()).compareTo(DateUtils.localToUTC(x.getArrTm(), x.getArrZone()));
                        }
                    });
                    for (int i = 1; i < rawVoyagePortsNext.size(); i++) {
                        RawVoyagePort voyagePort = rawVoyagePortsNext.get(i);
                        String portenNext = voyagePort.getPorten();
                        String portidNext = voyagePort.getPortid();
                        String inPort = voyagePort.getInPort();
                        boolean portEnFlag = false;
                        boolean portIdFlag = false;
                        if (portenPre != null) {
                            portEnFlag = portenPre.equals(portenNext);
                        }
                        if (portidPre != null) {
                            portIdFlag = portidPre.equals(portidNext);
                        }
                        if ((portEnFlag || portIdFlag) && "0".equals(inPort)) {
                            RawVoyagePort rawVoyagePortNext = new RawVoyagePort();
                            BeanUtils.copyProperties(voyagePort, rawVoyagePortNext);
                            rawVoyagePortsListNew.add(rawVoyagePortNext);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return rawVoyagePortsListNew;
    }

    public static void main(String[] args) {
        String data = "{\"periods\":[{\"beginPeriodOils\":[{\"beginTons\":4.64,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"beginTons\":2164.18,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"beginTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"beginTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"beginTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"beginTons\":11.75,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"beginTons\":320.53,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"imoNo\":\"9695145\",\"periodTime\":\"2020-01-01 00:00:00\"},{\"beginPeriodOils\":[{\"beginTons\":2374.54,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"beginTons\":0,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"beginTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"beginTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"beginTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"beginTons\":596.33,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"beginTons\":224.76,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"imoNo\":\"9695145\",\"periodTime\":\"2021-01-01 00:00:00\"}],\"voyagePorts\":[{\"arrTm\":\"2019-12-25 10:00:00\",\"arrZone\":8,\"deptTm\":\"2019-12-25 10:00:00\",\"deptZone\":8,\"distance\":1,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"0\",\"isEu\":\"0\",\"portWork\":\"1\",\"portcn\":\"青岛\",\"porten\":\"QINGDAO\",\"portid\":\"CNTAO\",\"recordType\":\"0\",\"refCode\":\"328886D572BC441DAE852105AC3405FA\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":11639,\"ballastTons\":0,\"cargoTons\":112352.7,\"carsNum\":0,\"heavyBoxNum\":7112,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":0,\"ballastTons\":0,\"cargoTons\":0,\"carsNum\":0,\"heavyBoxNum\":0,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":0,\"correctTm\":\"2019-12-25 10:00:00\",\"correctTons\":0,\"deptTons\":4.642,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-25 10:00:00\",\"correctTons\":0,\"deptTons\":2503.541,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-25 10:00:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-25 10:00:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-25 10:00:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-25 10:00:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-25 10:00:00\",\"correctTons\":0,\"deptTons\":184.349,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2019-12-26 15:00:00\",\"arrZone\":8,\"deptTm\":\"2019-12-26 22:24:00\",\"deptZone\":8,\"distance\":410.7,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"1\",\"isEu\":\"0\",\"portWork\":\"9\",\"portcn\":\"上海\",\"porten\":\"SHANGHAI\",\"portid\":\"CNSHA\",\"recordType\":\"0\",\"refCode\":\"84A2C70EFADB4EB1AA5EDCE6CB71A13E\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":11639,\"ballastTons\":0,\"cargoTons\":112352.7,\"carsNum\":0,\"heavyBoxNum\":7112,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":11639,\"ballastTons\":0,\"cargoTons\":112352.7,\"carsNum\":0,\"heavyBoxNum\":7112,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2019-12-26 22:24:00\",\"correctTons\":0,\"deptTons\":4.642,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":2413.96,\"correctTm\":\"2019-12-26 22:24:00\",\"correctTons\":0,\"deptTons\":2410.053,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-26 22:24:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-26 22:24:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-26 22:24:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2019-12-26 22:24:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":184.349,\"correctTm\":\"2019-12-26 22:24:00\",\"correctTons\":0,\"deptTons\":184.349,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2019-12-27 02:12:00\",\"arrZone\":8,\"deptTm\":\"2019-12-28 03:36:00\",\"deptZone\":8,\"distance\":1,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"0\",\"isEu\":\"0\",\"portWork\":\"1,4\",\"portcn\":\"上海\",\"porten\":\"SHANGHAI\",\"portid\":\"CNSHA\",\"recordType\":\"0\",\"refCode\":\"33E732E63FA24ED6A83386898A8873D9\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[{\"addBillNo\":\"\",\"addTm\":\"2019-12-28 03:36:00\",\"addTons\":136.183,\"isoBrand\":\"ISO-F-DMA\",\"oilId\":\"3\",\"oilName\":\"柴油/汽油\",\"sulfurPercent\":0,\"viscosity\":5}],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":10504.5,\"ballastTons\":0,\"cargoTons\":108471.3,\"carsNum\":0,\"heavyBoxNum\":8767.5,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":11639,\"ballastTons\":0,\"cargoTons\":112352.7,\"carsNum\":0,\"heavyBoxNum\":7112,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2019-12-28 03:36:00\",\"correctTons\":0,\"deptTons\":4.462,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":2399.251,\"correctTm\":\"2019-12-28 03:36:00\",\"correctTons\":0,\"deptTons\":2384.94,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-28 03:36:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-28 03:36:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-28 03:36:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2019-12-28 03:36:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":184.349,\"correctTm\":\"2019-12-28 03:36:00\",\"correctTons\":0,\"deptTons\":320.532,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2019-12-28 10:30:00\",\"arrZone\":8,\"deptTm\":\"2019-12-28 17:18:00\",\"deptZone\":8,\"distance\":134,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"1\",\"isEu\":\"0\",\"portWork\":\"9\",\"portcn\":\"宁波\",\"porten\":\"NINGBO\",\"portid\":\"CNNGB\",\"recordType\":\"0\",\"refCode\":\"4E1D3E5CCA1540349B1EF654AB4083DD\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":10504.5,\"ballastTons\":0,\"cargoTons\":108471.3,\"carsNum\":0,\"heavyBoxNum\":8767.5,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":10504.5,\"ballastTons\":0,\"cargoTons\":108471.3,\"carsNum\":0,\"heavyBoxNum\":8767.5,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2019-12-28 17:18:00\",\"correctTons\":0.18,\"deptTons\":4.642,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":2362.272,\"correctTm\":\"2019-12-28 17:18:00\",\"correctTons\":0,\"deptTons\":2358.735,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-28 17:18:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-28 17:18:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-28 17:18:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2019-12-28 17:18:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":320.532,\"correctTm\":\"2019-12-28 17:18:00\",\"correctTons\":0,\"deptTons\":320.532,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2019-12-28 22:00:00\",\"arrZone\":8,\"deptTm\":\"2019-12-29 22:24:00\",\"deptZone\":8,\"distance\":1,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"0\",\"isEu\":\"0\",\"portWork\":\"0\",\"portcn\":\"宁波\",\"porten\":\"NINGBO\",\"portid\":\"CNNGB\",\"recordType\":\"0\",\"refCode\":\"77AE455BFC934607B3F476537A0F4ADF\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":11082.75,\"ballastTons\":0,\"cargoTons\":118538.6,\"carsNum\":0,\"heavyBoxNum\":10892.75,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":10504.5,\"ballastTons\":0,\"cargoTons\":108471.3,\"carsNum\":0,\"heavyBoxNum\":8767.5,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2019-12-29 22:24:00\",\"correctTons\":0,\"deptTons\":4.642,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":2350.106,\"correctTm\":\"2019-12-29 22:24:00\",\"correctTons\":0,\"deptTons\":2336.266,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-29 22:24:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-29 22:24:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2019-12-29 22:24:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2019-12-29 22:24:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":320.532,\"correctTm\":\"2019-12-29 22:24:00\",\"correctTons\":0,\"deptTons\":320.532,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2020-01-01 05:06:00\",\"arrZone\":8,\"deptTm\":\"2020-01-02 04:12:00\",\"deptZone\":8,\"distance\":789,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"0\",\"isEu\":\"0\",\"portWork\":\"0\",\"portcn\":\"南沙\",\"porten\":\"NANSHA\",\"portid\":\"CNNSH\",\"recordType\":\"0\",\"refCode\":\"AF5696FC4685459D895C15650E241593\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":12734.5,\"ballastTons\":0,\"cargoTons\":126598.7,\"carsNum\":0,\"heavyBoxNum\":12734.5,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":11082.75,\"ballastTons\":0,\"cargoTons\":118538.6,\"carsNum\":0,\"heavyBoxNum\":10892.75,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2020-01-02 04:12:00\",\"correctTons\":0,\"deptTons\":4.642,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":2165.798,\"correctTm\":\"2020-01-02 04:12:00\",\"correctTons\":0,\"deptTons\":2152.881,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-02 04:12:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-02 04:12:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-02 04:12:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2020-01-02 04:12:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":320.532,\"correctTm\":\"2020-01-02 04:12:00\",\"correctTons\":0,\"deptTons\":320.532,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2020-01-06 07:00:00\",\"arrZone\":8,\"deptTm\":\"2020-01-07 06:00:00\",\"deptZone\":8,\"distance\":1462.2,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"0\",\"isEu\":\"0\",\"portWork\":\"0,4\",\"portcn\":\"SINGAPORE\",\"porten\":\"SINGAPORE\",\"portid\":\"SGSIN\",\"recordType\":\"0\",\"refCode\":\"4B5261F059FC43E9B28F57DCB412C928\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[{\"addBillNo\":\"\",\"addTm\":\"2020-01-07 06:00:00\",\"addTons\":3069.335,\"isoBrand\":\"ISO-F-RMG\",\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\",\"sulfurPercent\":0.48,\"viscosity\":180}],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":15162.75,\"ballastTons\":0,\"cargoTons\":154399.4,\"carsNum\":0,\"heavyBoxNum\":15162.75,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":12734.5,\"ballastTons\":0,\"cargoTons\":126598.7,\"carsNum\":0,\"heavyBoxNum\":12734.5,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2020-01-07 06:00:00\",\"correctTons\":0,\"deptTons\":4.642,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":1831.549,\"correctTm\":\"2020-01-07 06:00:00\",\"correctTons\":0,\"deptTons\":4886.781,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-07 06:00:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-07 06:00:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-07 06:00:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2020-01-07 06:00:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":320.532,\"correctTm\":\"2020-01-07 06:00:00\",\"correctTons\":0,\"deptTons\":320.532,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2020-01-15 00:00:00\",\"arrZone\":4,\"deptTm\":\"2020-01-17 05:30:00\",\"deptZone\":4,\"distance\":3522.1,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"1\",\"isEu\":\"0\",\"portWork\":\"9\",\"portcn\":\"SHARJAH\",\"porten\":\"SHARJAH\",\"portid\":\"AESHJ\",\"recordType\":\"0\",\"refCode\":\"48B80AC4880F4520923BA024B7F7540E\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":15162.75,\"ballastTons\":0,\"cargoTons\":154399.4,\"carsNum\":0,\"heavyBoxNum\":15162.75,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":15162.75,\"ballastTons\":0,\"cargoTons\":154399.4,\"carsNum\":0,\"heavyBoxNum\":15162.75,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2020-01-17 05:30:00\",\"correctTons\":0,\"deptTons\":4.642,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":3814.969,\"correctTm\":\"2020-01-17 05:30:00\",\"correctTons\":0,\"deptTons\":3777.682,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-17 05:30:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-17 05:30:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-17 05:30:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2020-01-17 05:30:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":320.532,\"correctTm\":\"2020-01-17 05:30:00\",\"correctTons\":0,\"deptTons\":320.532,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]},{\"arrTm\":\"2020-01-17 11:06:00\",\"arrZone\":4,\"deptTm\":\"2020-01-19 16:42:00\",\"deptZone\":4,\"distance\":15.8,\"firstVoyageCode\":\"033W\",\"imoNo\":\"9695145\",\"inPort\":\"0\",\"isEu\":\"0\",\"portWork\":\"1,4\",\"portcn\":\"JEBEL ALI\",\"porten\":\"JEBEL ALI\",\"portid\":\"AEJEA\",\"recordType\":\"0\",\"refCode\":\"AA2A6088534E49FF8CACF72DA9A0BF34\",\"secondVoyageCode\":\"\",\"shipId\":\"\",\"shorePower\":0,\"voyageAddoils\":[{\"addBillNo\":\"\",\"addTm\":\"2020-01-19 16:42:00\",\"addTons\":896.699,\"isoBrand\":\"ISO-F-RMG\",\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\",\"sulfurPercent\":2.5,\"viscosity\":180}],\"voyageOutoils\":[],\"voyagePortloadings\":[{\"allBoxNum\":13806.25,\"ballastTons\":0,\"cargoTons\":108757.2,\"carsNum\":0,\"heavyBoxNum\":8484.25,\"loadingType\":\"0\",\"peopleNum\":0},{\"allBoxNum\":15162.75,\"ballastTons\":0,\"cargoTons\":154399.4,\"carsNum\":0,\"heavyBoxNum\":15162.75,\"loadingType\":\"1\",\"peopleNum\":0}],\"voyagePortoils\":[{\"arrTons\":4.642,\"correctTm\":\"2020-01-19 16:42:00\",\"correctTons\":0,\"deptTons\":901.341,\"oilId\":\"1\",\"oilName\":\"高硫重燃油 HS HFO\"},{\"arrTons\":3756.963,\"correctTm\":\"2020-01-19 16:42:00\",\"correctTons\":0,\"deptTons\":3726.311,\"oilId\":\"11\",\"oilName\":\"低硫重燃油 LS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-19 16:42:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"12\",\"oilName\":\"超低硫重燃油 SLS HFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-19 16:42:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"2\",\"oilName\":\"高硫轻燃油 HS LFO\"},{\"arrTons\":0,\"correctTm\":\"2020-01-19 16:42:00\",\"correctTons\":0,\"deptTons\":0,\"oilId\":\"21\",\"oilName\":\"低硫轻燃油 LS LFO\"},{\"arrTons\":11.752,\"correctTm\":\"2020-01-19 16:42:00\",\"correctTons\":0,\"deptTons\":11.752,\"oilId\":\"22\",\"oilName\":\"超低硫轻燃油 SLS LFO\"},{\"arrTons\":320.532,\"correctTm\":\"2020-01-19 16:42:00\",\"correctTons\":0,\"deptTons\":320.532,\"oilId\":\"3\",\"oilName\":\"柴油/汽油\"}],\"voyageSludges\":[]}],\"voyageSpecs\":[]}";
        JSONObject jsonObject = new JSONObject(data);
        String s = jsonObject.toString();
        System.out.println(s);
    }

    @Override
    @Transactional
    public void reaportGenerateVoyageInfo(List<ReaportGenerateVoyageInfoDto> reaportGenerateVoyageInfoDtos) {
        if (reaportGenerateVoyageInfoDtos != null && reaportGenerateVoyageInfoDtos.size() > 0) {
            for (ReaportGenerateVoyageInfoDto reaportGenerateVoyageInfoDto : reaportGenerateVoyageInfoDtos) {
                try {
                    Integer year = reaportGenerateVoyageInfoDto.getYear();
                    OiShipInfo oiShipInfo = reaportGenerateVoyageInfoDto.getOiShipInfo();
                    String taskNu = reaportGenerateVoyageInfoDto.getTaskNu();
                    // 生成航次航段信息
                    OiShipTask oiShipTaskAfter = null;
                    List<OiShipTask> oiShipTaskList = oiShipTaskRepository.findOiShipTaskListByShipIsDelete(oiShipInfo.getId(), taskNu);
                    if (oiShipTaskList != null && oiShipTaskList.size() > 0) {
                        oiShipTaskAfter = oiShipTaskList.get(0);
                    }
                    List<RawVoyagePort> rawVoyagePorts = rawVoyagePortRepository
                            .findByTaskIdAndIsDelete(oiShipTaskAfter.getId(), 0);
                    List<RawVoyagePort> rawPort = getRawPort(rawVoyagePorts);
                    List<RawVoyagePort> rawVoyagePortsNext = rawVoyagePortRepository
                            .findByTaskIdNextAndIsDelete(oiShipTaskAfter.getId(), 0);
                    List<RawVoyagePort> rawPortNext = getRawPort(rawVoyagePortsNext);
                    rawPort.addAll(rawPortNext);
                    rawPort.sort((x, y) -> {
                        if (DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone())) == 0) {
                            return x.getCreateTm().compareTo(y.getCreateTm());
                        } else {
                            return DateUtils.localToUTC(x.getArrTm(), x.getArrZone()).compareTo(DateUtils.localToUTC(y.getArrTm(), y.getArrZone()));
                        }
                    });
                    if (!"0".equals(rawPort.get(0).getInPort())) {
                        String resMsg = "The interface request failed. The port of origin must be an in-port port.接口请求失败，起始港必须是港界内港口.";
                        throw new ExplicitException(resMsg);
                    }
                    if (!"0".equals(rawPort.get(rawPort.size() - 1).getInPort())) {
                        String resMsg = "The interface request failed. The ending port must be an internal port.接口请求失败，结束港必须是港界内港口.";
                        throw new ExplicitException(resMsg);
                    }
                    oiShipTaskAfter.setOiShipInfo(oiShipInfo);
                    generatorVolageInfo(oiShipTaskAfter, rawPort);
                } catch (Exception e) {
                    log.error(e.getStackTrace().toString());
                    e.printStackTrace();
                }
            }
        }
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

//    public BigDecimal getRealStopTime(RawVoyagePort rawVoyagePortEnd, BigDecimal stopTime) {
//        BigDecimal realStopTime = new BigDecimal(0);
//        long minute = 1000*60;
//        BigDecimal minu = stopTime.multiply(BigDecimal.valueOf(minute));//毫秒只
//        Date deptTm = rawVoyagePortEnd.getDeptTm();
//        Date arrTm = rawVoyagePortEnd.getArrTm();
//        if(deptTm != null && arrTm != null){
//            long time = deptTm.getTime()-arrTm.getTime();
//          realStopTime =
//        }
//
//        return null;
//    }
}
