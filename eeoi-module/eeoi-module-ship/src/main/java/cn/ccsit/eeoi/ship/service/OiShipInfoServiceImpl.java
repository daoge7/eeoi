package cn.ccsit.eeoi.ship.service;

import cn.ccsit.common.constant.RoleConst;
import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.query.QueryHelp;
import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.common.vo.CurrentUserVo;
import cn.ccsit.eeoi.dictionary.entity.*;
import cn.ccsit.eeoi.dictionary.repository.*;
import cn.ccsit.eeoi.ship.controller.OishipController;
import cn.ccsit.eeoi.ship.dto.*;
import cn.ccsit.eeoi.ship.entity.*;
import cn.ccsit.eeoi.ship.query.OiShipInfoCriteria;
import cn.ccsit.eeoi.ship.query.OiShipInfoSsmisCriteria;
import cn.ccsit.eeoi.ship.repository.*;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipGeEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipInfoEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipMainEngineEntity;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipEquipAttrRepository;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipGeRepository;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipInfoRepository;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipMainEngineRepository;
import cn.ccsit.eeoi.ship.vo.OiShipEquipmentVo;
import cn.ccsit.eeoi.ship.vo.ShipEquipmentFuelMapVo;
import cn.ccsit.eeoi.ship.vo.SsmisOiShipInfoVo;
import cn.ccsit.eeoi.ship.vo.SsmisShipCcsnosVo;
import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.entity.SysUser;
import cn.ccsit.eeoi.system.entity.SysUserRole;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.ccsit.eeoi.system.repository.SysUserClientMapRepository;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service("oiShipInfoService")
public class OiShipInfoServiceImpl extends CommonServiceImpl implements OiShipInfoService, CommonService {

    @Autowired
    private OiShipInfoRepository oiShipInfoRepository;
    @Autowired
    private OiShipBoilerRepository oiShipBoilerRepository;
    @Autowired
    private OiShipCmsaMapRepository oiShipCmsaMapRepository;
    @Autowired
    private OiMainEngineRepository oiMainEngineRepository;
    @Autowired
    private OiShipTurbineRepository oiShipTurbineRepository;
    @Autowired
    private OiIncineratorRepository oiIncineratorRepository;
    @Autowired
    private OiShipOtherRepository oiShipOtherRepository;
    @Autowired
    private OiShipInertRepository oiShipInertRepository;
    @Autowired
    private OiShipGeRepository oiShipGeRepository;
    @Autowired
    private OiShipBatteryRepository oiShipBatteryRepository;
    @Autowired
    private OiShipGeneratorRepository oiShipGeneratorRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysUserClientMapRepository sysUserClientMapRepository;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private GcClientRepository gcClientRepository;

    @Autowired
    private SsmisOiShipInfoRepository ssmisOiShipInfoRepository;
    @Autowired
    private SsmisOiShipEquipAttrRepository ssmisOiShipEquipAttrRepository;
    @Autowired
    private SsmisOiShipMainEngineRepository ssmisOiShipMainEngineRepository;
    @Autowired
    private SsmisOiShipGeRepository ssmisOiShipGeRepository;
    @Autowired
    private ShipEquipmentFuelMapRepository shipEquipmentFuelMapRepository;
    @Autowired
    private ClassificatRepository classificatRepository;
    @Autowired
    private ShipTypeRepository shipTypeRepository;
    @Autowired
    private GcStateRepository gcStateRepository;
    @Autowired
    private GcCityRepository gcCityRepository;
    @Autowired
    private OiShipTfcRepository oiShipTfcRepository;
    @Autowired
    private FuelRepository fuelRepository;
    @Autowired
    private OiShipPropellerRepository oiShipPropellerRepository;

    private static final String CLIENT_CODE = "code";
    private static final String CLIENT_IACS = "iacs";
    private static final String CLIENT_CUID = "cuids";
    private static final String SM_SYS_TREE_ID = "7692";
    private static final String SSMIS_DATA_SOURCE = "SSMIS";


    @Override
    @Transactional
    public ResultVo createOiShip(OiShipInfoDto oiShipInfoDto) {
        ResultStringVo resultStringVo = new ResultStringVo(null);
        GcState flag = gcStateRepository.findFlag(oiShipInfoDto.getFlagCode());
        if (flag != null) {
            oiShipInfoDto.setFlag(flag.getEnName());
        }
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiShipInfoDto.getShipEquipmentFuelMaps();
        List<OiShipCmsaMap> oiShipCmsaMaps = new ArrayList<>();
        List<GcClient> gcClientDoc = gcClientRepository.findByCode(oiShipInfoDto.getDocId());
        List<GcClient> gcClientOwner = gcClientRepository.findByCode(oiShipInfoDto.getOwnerId());
        List<GcClient> gcClientOperate = gcClientRepository.findByCode(oiShipInfoDto.getOperatorId());
        List<GcClient> gcClientBuilder = gcClientRepository.findByCode(oiShipInfoDto.getBuilderId());
        OiShipInfo oiShipInfo = new OiShipInfo();
        List<String> clientIds = oiShipInfoDto.getClientId();
        if (StringUtils.isNotEmpty(oiShipInfoDto.getId())) {
            //更新船舶信息
            oiShipInfo = oiShipInfoRepository.findById(oiShipInfoDto.getId()).get();
            BeanUtils.copyProperties(oiShipInfoDto, oiShipInfo, "shipEquipmentFuelMaps");
            oiShipInfo.setIsCmsa(clientIds == null || clientIds.size() == 0 ? 1 : 0);
            setParams(oiShipInfo, gcClientDoc, gcClientOwner, gcClientOperate, gcClientBuilder);
            oiShipCmsaMapRepository.deleteByShipId(oiShipInfo.getId());
            //更新内河机构映射表
            saveClientIds(clientIds, oiShipInfo, oiShipCmsaMaps);
            //更新燃油类型
            shipEquipmentFuelMapRepository.deleteByShipIdAAndEquipmentIdIsNull(oiShipInfo.getId());
            saveFuelType(shipEquipmentFuelMaps, oiShipInfo.getId(), null);
            oiShipInfo.setUpdateTime(new Date());
            oiShipInfoRepository.save(oiShipInfo);
            resultStringVo.setData(oiShipInfo.getId());
            log.info("船舶信息更新成功");
        } else {
            //根据船检登记号和imo号查询船舶
            Integer shipCount = oiShipInfoRepository.findByRegisterNoOrImoNu(oiShipInfoDto.getImono(), oiShipInfoDto.getRegisterno());
            if (shipCount > 0) {
                return new ResultErrorVo(this.msg("ship.is.exits"));
            }
            //保存船舶信息
            BeanUtils.copyProperties(oiShipInfoDto, oiShipInfo, "shipEquipmentFuelMaps");
            setParams(oiShipInfo, gcClientDoc, gcClientOwner, gcClientOperate, gcClientBuilder);
            oiShipInfo.setCreateTm(new Date());
            oiShipInfo.setIsDelete(0);
            oiShipInfo.setIsCmsa(clientIds == null || clientIds.size() == 0 ? 1 : 0);
            OiShipInfo saveOiShipInfo = oiShipInfoRepository.save(oiShipInfo);
            //保存燃油类型
            saveFuelType(shipEquipmentFuelMaps, saveOiShipInfo.getId(), null);
            //保存内河机构映射表
            saveClientIds(clientIds, saveOiShipInfo, oiShipCmsaMaps);
            resultStringVo.setData(saveOiShipInfo.getId());
            log.info("创建船舶成功");
        }
        return resultStringVo;
    }

    private void saveFuelType(List<ShipEquipmentFuelMapVo> shipEquipmentFuelMapVos, String shipId, String deviceId) {
        if (shipEquipmentFuelMapVos != null && shipEquipmentFuelMapVos.size() != 0) {
            List<ShipEquipmentFuelMap> shipEquipmentFuelMaps = new ArrayList<>();
            shipEquipmentFuelMapVos.forEach(shipEquipmentFuelMapVo -> {
                ShipEquipmentFuelMap shipEquipmentFuelMap = new ShipEquipmentFuelMap();
                shipEquipmentFuelMap.setShipId(shipId);
                shipEquipmentFuelMap.setEquipmentId(deviceId);
                shipEquipmentFuelMap.setIsDelete(0);
                shipEquipmentFuelMap.setFuelId(shipEquipmentFuelMapVo.getFuelId());
                shipEquipmentFuelMap.setFuelName(shipEquipmentFuelMapVo.getFuelName());
                shipEquipmentFuelMap.setConsMethod(shipEquipmentFuelMapVo.getConsMethod());
                shipEquipmentFuelMaps.add(shipEquipmentFuelMap);
            });
            shipEquipmentFuelMapRepository.saveAll(shipEquipmentFuelMaps);
        }
    }


    private void saveOiShipTurbineType(List<String> fuelIds, List<ShipEquipmentFuelMap> shipEquipmentFuelMaps, String shipId, OiShipTurbine oiShipTurbine) {
        if (fuelIds != null && fuelIds.size() != 0) {
            for (int i = 0; i < fuelIds.size(); i++) {
                ShipEquipmentFuelMap shipEquipmentFuelMap = new ShipEquipmentFuelMap();
                shipEquipmentFuelMap.setShipId(shipId);
                shipEquipmentFuelMap.setFuelId(fuelIds.get(i));
                shipEquipmentFuelMap.setEquipmentId(oiShipTurbine.getId());
                shipEquipmentFuelMap.setIsDelete(0);
                shipEquipmentFuelMaps.add(shipEquipmentFuelMap);
            }
            shipEquipmentFuelMapRepository.saveAll(shipEquipmentFuelMaps);
        }
    }

    private void saveClientIds(List<String> clientIds, OiShipInfo oiShipInfo, List<OiShipCmsaMap> oiShipCmsaMaps) {
        if (clientIds != null && clientIds.size() != 0) {
            for (int i = 0; i < clientIds.size(); i++) {
                OiShipCmsaMap oiShipCmsaMap = new OiShipCmsaMap();
                oiShipCmsaMap.setClientId(clientIds.get(i));
                oiShipCmsaMap.setShipId(oiShipInfo.getId());
                oiShipCmsaMap.setRegisterno(oiShipInfo.getRegisterno());
                GcClient gcClientById = gcClientRepository.findGcClientById(clientIds.get(i));
                oiShipCmsaMap.setCmsaName(gcClientById.getNameCn());
                oiShipCmsaMap.setIsDelete(0);
                oiShipCmsaMaps.add(oiShipCmsaMap);
            }
            oiShipCmsaMapRepository.saveAll(oiShipCmsaMaps);
        }
    }

    private void setParams(OiShipInfo oiShipInfo, List<GcClient> gcClientDoc, List<GcClient> gcClientOwner, List<GcClient> gcClientOperate, List<GcClient> gcClientBuilder) {
        if (gcClientDoc != null && gcClientDoc.size() > 0) {
            GcClient gcClient = gcClientDoc.get(0);
            oiShipInfo.setDocIacs(gcClient.getIacs());
            oiShipInfo.setDocId(gcClient.getCode());
            oiShipInfo.setDocCuid(gcClient.getCUid());
            String nameEn = gcClient.getNameEn();
            String nameCn = gcClient.getNameCn();
            String docManger = (nameEn != null ? nameEn : nameCn);
            oiShipInfo.setDocmanager(docManger);
        } else {
            oiShipInfo.setDocIacs(null);
            oiShipInfo.setDocId(null);
            oiShipInfo.setDocCuid(null);
            oiShipInfo.setDocmanager(null);
        }
        if (gcClientOwner != null && gcClientOwner.size() > 0) {
            GcClient gcClient = gcClientOwner.get(0);
            oiShipInfo.setOwner(gcClient.getNameCn());
            oiShipInfo.setOwnerAddr(gcClient.getAddrEn());
            oiShipInfo.setOwnerId(gcClient.getCode());
        } else {
            oiShipInfo.setOwner(null);
            oiShipInfo.setOwnerAddr(null);
            oiShipInfo.setOwnerId(null);
        }
        if (gcClientOperate != null && gcClientOperate.size() > 0) {
            GcClient gcClient = gcClientOperate.get(0);
            oiShipInfo.setOperatorId(gcClient.getCode());
            oiShipInfo.setOperatorShip(gcClient.getNameCn());
        } else {
            oiShipInfo.setOperatorId(null);
            oiShipInfo.setOperatorShip(null);
        }
        if (gcClientBuilder != null && gcClientBuilder.size() > 0) {
            GcClient gcClient = gcClientBuilder.get(0);
            oiShipInfo.setBuilderId(gcClient.getCode());
            oiShipInfo.setBuilder(gcClient.getNameCn());
        } else {
            oiShipInfo.setBuilderId(null);
            oiShipInfo.setBuilder(null);
        }
    }

    @Override
    public ResultVo getOiShipInfo(OiShipInfoCriteria oiShipInfoCriteria) {
        //查询内河管理机构下的船舶
        Set<String> shipIds = oiShipCmsaMapRepository.findByClientId(oiShipInfoCriteria.getClientId());
        oiShipInfoCriteria.setOrgans(shipIds.size() == 0 ? null : shipIds);
        //获取默认的用户角色
        CurrentUserVo currentUser = this.getCurrentUser();
        List<SysUserRole> sysUserRoles = currentUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleRepository.findById(sysUserRole.getRoleId()).get();
            roleNames.add(sysRole.getName());
        });
        Set<String> shipIdsByShipCodes = this.getShipIdsByUserRole(roleNames);
        if (roleNames.contains(RoleConst.ADMIN_ROLE_NAME) || roleNames.contains(RoleConst.CSS_ROLE_NAME)) {
            oiShipInfoCriteria.setRoleShipIds(shipIdsByShipCodes);
        } else {
            if (roleNames.contains(RoleConst.SHIP_COMPANY_ROLE_NAME)) {
                oiShipInfoCriteria.setRoleShipIds(shipIdsByShipCodes);
            } else if (roleNames.contains(RoleConst.CMSA_ROLE_NAME)) {
                oiShipInfoCriteria.setRoleShipIds(shipIdsByShipCodes);
            } else {
                oiShipInfoCriteria.setRoleShipIds(shipIdsByShipCodes);
            }
        }
        PageRequest pageable = PageRequest.of(oiShipInfoCriteria.getCurrentPage() - 1, oiShipInfoCriteria.getPageSize(), Sort.by("spname").ascending());
        Page<OiShipInfo> page = oiShipInfoRepository.findAll((root, oiShipInfoCriteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, oiShipInfoCriteria, criteriaBuilder), pageable);
        List<OiShipInfo> content = page.getContent();
        content.forEach(oiShipInfo -> {
            ShipType shipType = shipTypeRepository.findShipType(oiShipInfo.getSptype());
            if (shipType != null) {
                oiShipInfo.setShipTypeName(shipType.getCsptype());
            }
        });
        ResultPageVo resultPageVo = new ResultPageVo(page);
        return resultPageVo;
    }

    private Map<String, Set<String>> getCodeAndIacs(List<String> gcClientIds, Map<String, Set<String>> map, Set<String> codes, Set<String> iacss, Set<String> docCuids) {
        for (String gcClientId : gcClientIds
        ) {
            GcClient gcClient = gcClientRepository.findById(gcClientId).get();
            codes.add(gcClient.getCode());
            iacss.add(gcClient.getIacs());
            docCuids.add(gcClient.getId());
            if (gcClient.getCGroup().equals(0)) {
                List<GcClient> children = gcClient.getChildren();
                List<String> gcidsByGid = new ArrayList<>();
                children.forEach(gcClientChiren -> {
                    gcClientIds.add(gcClientChiren.getId());
                });
                getCodeAndIacs(gcidsByGid, map, codes, iacss, docCuids);
            }
        }
        map.put(CLIENT_CODE, codes);
        map.put(CLIENT_IACS, iacss);
        map.put(CLIENT_CUID, docCuids);
        return map;
    }

    @Override
    @Transactional
    public ResultVo deleteOiShipInfo(List<String> shipIds) {
        if (shipIds == null || shipIds.size() == 0) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
        shipIds.forEach(shipId -> {
            //删除主机设备
            oiMainEngineRepository.deleteOiMainEngineByShipId(shipId);
            //删除副机信息
            oiShipGeRepository.deleteOiShipGeByShipId(shipId);
            //删除发电机信息
            oiShipGeneratorRepository.deleteOiShipGeneratorByShipId(shipId);
            //删除电池信息
            oiShipBatteryRepository.deleteOiMainEngineByShipId(shipId);
            //删除锅炉信息
            oiShipBoilerRepository.deleteOiShipBoilerByShipId(shipId);
            //删除焚烧炉信息
            oiIncineratorRepository.deleteOiIncineratorByShipId(shipId);
            //删除惰性气体发生器
            oiShipInertRepository.deleteOiShipInertByShipId(shipId);
            //删除涡轮机信息
            oiShipTurbineRepository.deleteOiShipTurbineByShipId(shipId);
            //删除其他设备信息
            oiShipOtherRepository.deleteOiShipOtherByShipId(shipId);
            //删除内河机构相关的船舶信息
            oiShipCmsaMapRepository.deleteOiShipCmsaMapByShipId(shipId);
            //删除船舶信息
            oiShipInfoRepository.deleteOiShipInfoByShipId(shipId);
        });
        ResultVo resultVo = new ResultStringVo(this.msg("ship.delete.success"));
        return resultVo;
    }

    @Override
    @Transactional
    public ResultVo createAndUpdateMainEngine(OiMainEngineDto oiMainEngineDto) {
        log.info("主机设备信息==={}", oiMainEngineDto);
        ResultStringVo resultStringVo = new ResultStringVo(null);
        OiShipInfo oiShipInfo = oiShipInfoRepository.findById(oiMainEngineDto.getShipId()).get();
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiMainEngineDto.getShipEquipmentFuelMaps();
        if (StringUtils.isNotEmpty(oiMainEngineDto.getId())) {
            //更新主机设备信息
            OiMainEngine oiMainEngine = oiMainEngineRepository.findById(oiMainEngineDto.getId()).get();
            BeanUtils.copyProperties(oiMainEngineDto, oiMainEngine, "shipEquipmentFuelMaps");
            shipEquipmentFuelMapRepository.deleteByEquipmentId(oiMainEngine.getId());
            saveFuelType(shipEquipmentFuelMaps, oiShipInfo.getId(), oiMainEngine.getId());
            oiMainEngine.setUpdateTime(new Date());
            oiMainEngineRepository.save(oiMainEngine);
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            //新增主机设备信息
            OiMainEngine oiMainEngine = new OiMainEngine();
            BeanUtils.copyProperties(oiMainEngineDto, oiMainEngine, "shipEquipmentFuelMaps");
//            oiMainEngine.setXh(UUID.randomUUID().toString().substring(0,8));
            oiMainEngine.setIsDelete(0);
            oiMainEngine.setDataSource("create");
            oiMainEngine.setOperator(this.getUserName());
            oiMainEngine.setCcsno(oiShipInfo.getCcsno());
            oiMainEngine.setCreateTm(new Date());
            OiMainEngine saveOiMainEngine = oiMainEngineRepository.save(oiMainEngine);
            saveFuelType(shipEquipmentFuelMaps, oiShipInfo.getId(), saveOiMainEngine.getId());
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo getOiMainEngineCount(String shipId) {
        Integer oiMainEngineCount = oiMainEngineRepository.findByShipId(shipId);
        return new ResultObjectVo<>(oiMainEngineCount);
    }

    @Override
    public ResultVo getOiShipGeCount(String shipId) {
        Integer oiShipGeCount = oiShipGeRepository.findByShipId(shipId);
        return new ResultObjectVo<>(oiShipGeCount);
    }

    @Override
    public ResultVo deleteOiMainEnginr(String mainEngineId) {
        log.debug("主机设备id=={}", mainEngineId);
        if (!StringUtils.isNotEmpty(mainEngineId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
//        oiMainEngineRepository.deleteOiMainEngineById(mainEngineId);
        OiMainEngine oiMainEngine = oiMainEngineRepository.findByIdAndIsDelete(mainEngineId, 0);
        if (null != oiMainEngine) {
            oiMainEngine.setIsDelete(1);
            oiMainEngineRepository.save(oiMainEngine);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo getOiMainEngine(String mainEngineId) {
        log.debug("主机设备id=={}", mainEngineId);
        OiMainEngine oiMainEngine = oiMainEngineRepository.findByIdAndIsDelete(mainEngineId, 0);
        return new ResultObjectVo<>(oiMainEngine);
    }

    @Override
    public ResultVo createAndUpdateOiShipGe(OiShipGeDto oiShipGeDto) {
        ResultStringVo resultStringVo = new ResultStringVo(null);
        OiShipInfo oiShipInfo = oiShipInfoRepository.findById(oiShipGeDto.getShipId()).get();
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiShipGeDto.getShipEquipmentFuelMaps();
        log.debug("副机参数信息=={}", oiShipGeDto);
        if (StringUtils.isNotEmpty(oiShipGeDto.getId())) {
            OiShipGe oiShipGe = oiShipGeRepository.findById(oiShipGeDto.getId()).get();
            BeanUtils.copyProperties(oiShipGeDto, oiShipGe, "shipEquipmentFuelMaps");
            shipEquipmentFuelMapRepository.deleteByEquipmentId(oiShipGe.getId());
            saveFuelType(shipEquipmentFuelMaps, oiShipInfo.getId(), oiShipGe.getId());
            oiShipGe.setOpdate(new Date());
            oiShipGe.setOpuser(this.getUserName());
            oiShipGeRepository.save(oiShipGe);
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            OiShipGe oiShipGe = new OiShipGe();
            BeanUtils.copyProperties(oiShipGeDto, oiShipGe, "shipEquipmentFuelMaps");
//            oiShipGe.setXh(UUID.randomUUID().toString().substring(0,8));
            oiShipGe.setCcsno(oiShipInfo.getCcsno());
            oiShipGe.setCreateTm(new Date());
            oiShipGe.setIsDelete(0);
            oiShipGe.setDataSource("create");
            oiShipGe.setCreator(this.getUserName());
            OiShipGe saveOiShipGe = oiShipGeRepository.save(oiShipGe);
            saveFuelType(shipEquipmentFuelMaps, oiShipInfo.getId(), saveOiShipGe.getId());
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }

        return resultStringVo;
    }

    @Override
    public ResultVo deleteOiShipGe(String oiShipGeId) {
        log.debug("副机设备id=={}", oiShipGeId);
        if (!StringUtils.isNotEmpty(oiShipGeId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
//        oiShipGeRepository.deleteOiShipGeById(mainEngineId);
        OiShipGe oiShipGe = oiShipGeRepository.findByIdAndIsDelete(oiShipGeId, 0);
        if (oiShipGe != null) {
            oiShipGe.setIsDelete(1);
            oiShipGeRepository.save(oiShipGe);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo getOiShipGe(String oiShipGeId) {
        log.debug("副机设备id=={}", oiShipGeId);
        OiShipGe oiShipGe = oiShipGeRepository.findByIdAndIsDelete(oiShipGeId, 0);
        return new ResultObjectVo<>(oiShipGe);
    }

    @Override
    public ResultVo createAndUpdateOiShipGenerator(OiShipGeneratorDto oiShipGeneratorDto) {
        log.debug("新增发电机设备信息=={}", oiShipGeneratorDto);
        ResultStringVo resultStringVo = new ResultStringVo(null);
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiShipGeneratorDto.getShipEquipmentFuelMaps();
        if (StringUtils.isNotEmpty(oiShipGeneratorDto.getId())) {
            OiShipGenerator oiShipGenerator = oiShipGeneratorRepository.findById(oiShipGeneratorDto.getId()).get();
            BeanUtils.copyProperties(oiShipGeneratorDto, oiShipGenerator, "shipEquipmentFuelMaps");
            shipEquipmentFuelMapRepository.deleteByEquipmentId(oiShipGenerator.getId());
            saveFuelType(shipEquipmentFuelMaps, oiShipGeneratorDto.getShipId(), oiShipGenerator.getId());
            oiShipGenerator.setOpdate(new Date());
            oiShipGenerator.setOpuser(this.getUserName());
            oiShipGeneratorRepository.save(oiShipGenerator);
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            OiShipGenerator oiShipGenerator = new OiShipGenerator();
            BeanUtils.copyProperties(oiShipGeneratorDto, oiShipGenerator, "shipEquipmentFuelMaps");
//            oiShipGenerator.setXh(UUID.randomUUID().toString().substring(0,8));
            oiShipGenerator.setIsDelete(0);
            oiShipGenerator.setCreateTm(new Date());
            oiShipGenerator.setDataSource("create");
            oiShipGenerator.setCreator(this.getUserName());
            OiShipGenerator saveOiShiupGenerator = oiShipGeneratorRepository.save(oiShipGenerator);
            saveFuelType(shipEquipmentFuelMaps, oiShipGeneratorDto.getShipId(), saveOiShiupGenerator.getId());
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo getOiShipGenerator(String id) {
        OiShipGenerator oiShipGenerator = oiShipGeneratorRepository.findByIdAndIsDelete(id, 0);
        return new ResultObjectVo<>(oiShipGenerator);
    }

    @Override
    public ResultVo deleteShipGenerator(String id) {
        if (!StringUtils.isNotEmpty(id)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
//        oiShipGeneratorRepository.deleteOiShipGeneratorById(id);
        OiShipGenerator oiShipGenerator = oiShipGeneratorRepository.findByIdAndIsDelete(id, 0);
        if (oiShipGenerator != null) {
            oiShipGenerator.setIsDelete(1);
            oiShipGeneratorRepository.save(oiShipGenerator);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    @Transactional
    public ResultVo createAndUpdateOiShipBoiler(OiShipBoilerDto oiShipBoilerDto) {
        log.debug("锅炉参数=={}", oiShipBoilerDto);
        ResultStringVo resultStringVo = new ResultStringVo(null);
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiShipBoilerDto.getShipEquipmentFuelMaps();
        if (StringUtils.isNotEmpty(oiShipBoilerDto.getId())) {
            OiShipBoiler oiShipBoiler = oiShipBoilerRepository.findById(oiShipBoilerDto.getId()).get();
            BeanUtils.copyProperties(oiShipBoilerDto, oiShipBoiler, "shipEquipmentFuelMaps");
            oiShipBoiler.setUpdateTime(new Date());
            shipEquipmentFuelMapRepository.deleteByEquipmentId(oiShipBoiler.getId());
            oiShipBoilerRepository.save(oiShipBoiler);
            saveFuelType(shipEquipmentFuelMaps, oiShipBoilerDto.getShipId(), oiShipBoiler.getId());
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            //新增
            OiShipBoiler oiShipBoiler = new OiShipBoiler();
            BeanUtils.copyProperties(oiShipBoilerDto, oiShipBoiler, "shipEquipmentFuelMaps");
//            oiShipBoiler.setXh(UUID.randomUUID().toString().substring(0,8));
            oiShipBoiler.setIsDelete(0);
            oiShipBoiler.setOperator(this.getUserName());
            oiShipBoiler.setCreateTm(new Date());
            OiShipBoiler saveBoiler = oiShipBoilerRepository.save(oiShipBoiler);
            saveFuelType(shipEquipmentFuelMaps, oiShipBoilerDto.getShipId(), saveBoiler.getId());
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo deleteShipBoilerInfo(String oiShipBoilerId) {
        log.debug("锅炉设备id=={}", oiShipBoilerId);
        if (!StringUtils.isNotEmpty(oiShipBoilerId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
//        oiShipBoilerRepository.deleteOiShipBoilerById(oiShipBoilerId);
        OiShipBoiler oiShipBoiler = oiShipBoilerRepository.findByIdAndIsDelete(oiShipBoilerId, 0);
        if (oiShipBoiler != null) {
            oiShipBoiler.setIsDelete(1);
            oiShipBoilerRepository.save(oiShipBoiler);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo getOiShipBoilerInfo(String oiShipBoilerId) {
        log.debug("锅炉设备id=={}", oiShipBoilerId);
        if (!StringUtils.isNotEmpty(oiShipBoilerId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
        return new ResultObjectVo<>(oiShipBoilerRepository.findByIdAndIsDelete(oiShipBoilerId, 0));
    }

    @Override
    public ResultVo createAndUpdateOiShipInert(OiShipInertDto oiShipInertDto) {
        log.debug("惰性气体发生器参数=={}", oiShipInertDto);
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiShipInertDto.getShipEquipmentFuelMaps();
        ResultStringVo resultStringVo = new ResultStringVo(null);
        if (StringUtils.isNotEmpty(oiShipInertDto.getId())) {
            OiShipInert oiShipInert = oiShipInertRepository.findById(oiShipInertDto.getId()).get();
            BeanUtils.copyProperties(oiShipInertDto, oiShipInert, "shipEquipmentFuelMaps");
            shipEquipmentFuelMapRepository.deleteByEquipmentId(oiShipInert.getId());
            saveFuelType(shipEquipmentFuelMaps, oiShipInertDto.getShipId(), oiShipInert.getId());
            oiShipInert.setInertOiltype(oiShipInertDto.getInertOiltype());
            oiShipInert.setOpdate(new Date());
            oiShipInert.setOpuser(this.getUserName());
            oiShipInertRepository.save(oiShipInert);
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            OiShipInert oiShipInert = new OiShipInert();
            BeanUtils.copyProperties(oiShipInertDto, oiShipInert, "shipEquipmentFuelMaps");
//            oiShipInert.setXh(UUID.randomUUID().toString().substring(0,8));
            oiShipInert.setInertOiltype(oiShipInertDto.getInertOiltype());
            oiShipInert.setIsDelete(0);
            oiShipInert.setCreateTm(new Date());
            oiShipInert.setCreator(this.getUserName());
            OiShipInert saveOiShipInert = oiShipInertRepository.save(oiShipInert);
            saveFuelType(shipEquipmentFuelMaps, oiShipInertDto.getShipId(), saveOiShipInert.getId());
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo getOiShipInert(String oiShipInertId) {
        log.debug("惰性气体设备id=={}", oiShipInertId);
        if (!StringUtils.isNotEmpty(oiShipInertId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
        OiShipInert oiShipInert = oiShipInertRepository.findById(oiShipInertId).get();
        return new ResultObjectVo<>(oiShipInert);
    }

    @Override
    public ResultVo deleteOiShipInert(String oiShipInertId) {
        log.debug("惰性气体设备id=={}", oiShipInertId);
        if (!StringUtils.isNotEmpty(oiShipInertId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
//        oiShipInertRepository.deleteOiShipInertById(oiShipInertId);
        OiShipInert oiShipInert = oiShipInertRepository.findByIdAndIsDelete(oiShipInertId, 0);
        if (oiShipInert != null) {
            oiShipInert.setIsDelete(1);
            oiShipInertRepository.save(oiShipInert);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo createAndUpdateOiShipTurBine(OiShipTurbineDto oiShipTurbineDto) {
        log.debug("涡轮机=={}", oiShipTurbineDto);
        ResultStringVo resultStringVo = new ResultStringVo(null);
        OiShipInfo oiShipInfo = oiShipInfoRepository.findById(oiShipTurbineDto.getShipId()).get();
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiShipTurbineDto.getShipEquipmentFuelMaps();
        if (StringUtils.isNotEmpty(oiShipTurbineDto.getId())) {
            OiShipTurbine oiShipTurbine = oiShipTurbineRepository.findById(oiShipTurbineDto.getId()).get();
            BeanUtils.copyProperties(oiShipTurbineDto, oiShipTurbine, "shipEquipmentFuelMaps");
            shipEquipmentFuelMapRepository.deleteByEquipmentId(oiShipTurbine.getId());
            saveFuelType(shipEquipmentFuelMaps, oiShipTurbineDto.getShipId(), oiShipTurbine.getId());
            oiShipTurbine.setUpdatedate(new Date());
            oiShipTurbine.setOpdate(new Date());
            oiShipTurbine.setOpuser(this.getUserName());
            oiShipTurbineRepository.save(oiShipTurbine);
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            OiShipTurbine oiShipTurbine = new OiShipTurbine();
            BeanUtils.copyProperties(oiShipTurbineDto, oiShipTurbine, "shipEquipmentFuelMaps");
//            oiShipTurbine.setXh(UUID.randomUUID().toString().substring(0,8));
            oiShipTurbine.setCcsno(oiShipInfo.getCcsno());
            oiShipTurbine.setCreator(this.getUserName());
            oiShipTurbine.setCreateTm(new Date());
            oiShipTurbine.setIsDelete(0);
            OiShipTurbine saveOiShipTurbine = oiShipTurbineRepository.save(oiShipTurbine);
            saveFuelType(shipEquipmentFuelMaps, oiShipTurbineDto.getShipId(), saveOiShipTurbine.getId());
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo getOiShipTurbine(String oiShipTurbineId) {
        log.debug("涡轮机id=={}", oiShipTurbineId);
        if (!StringUtils.isNotEmpty(oiShipTurbineId)) {
            new ExplicitException(this.msg("ship.params.null"));
        }
        OiShipTurbine oiShipTurbine = oiShipTurbineRepository.findByIdAndIsDelete(oiShipTurbineId, 0);
        return new ResultObjectVo<>(oiShipTurbine);
    }

    @Override
    public ResultVo deleteOiShipTurbine(String oiShipTurbineId) {
        log.debug("涡轮机id=={}", oiShipTurbineId);
        if (!StringUtils.isNotEmpty(oiShipTurbineId)) {
            new ExplicitException(this.msg("ship.params.null"));
        }
//        oiShipTurbineRepository.deleteOiShipTurbineById(oiShipTurbineId);
        OiShipTurbine oiShipTurbine = oiShipTurbineRepository.findByIdAndIsDelete(oiShipTurbineId, 0);
        if (oiShipTurbine != null) {
            oiShipTurbine.setIsDelete(1);
            oiShipTurbineRepository.save(oiShipTurbine);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo createAndUpdateIncinerator(OiIncineratorDto oiIncineratorDto) {
        log.debug("焚烧炉参数=={}", oiIncineratorDto);
        ResultStringVo resultStringVo = new ResultStringVo(null);
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMaps = oiIncineratorDto.getShipEquipmentFuelMaps();
        OiShipInfo oiShipInfo = oiShipInfoRepository.findById(oiIncineratorDto.getShipId()).get();
        if (StringUtils.isNotEmpty(oiIncineratorDto.getId())) {
            //更新焚烧炉信息
            OiIncinerator oiIncinerator = oiIncineratorRepository.findById(oiIncineratorDto.getId()).get();
            BeanUtils.copyProperties(oiIncineratorDto, oiIncinerator, "shipEquipmentFuelMaps");
            oiIncinerator.setUpdateTime(new Date());
            oiIncinerator.setOperator(this.getUserName());
            oiIncineratorRepository.save(oiIncinerator);
            shipEquipmentFuelMapRepository.deleteByEquipmentId(oiIncinerator.getId());
            saveFuelType(shipEquipmentFuelMaps, oiIncineratorDto.getShipId(), oiIncinerator.getId());
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            OiIncinerator oiIncinerator = new OiIncinerator();
            BeanUtils.copyProperties(oiIncineratorDto, oiIncinerator, "shipEquipmentFuelMaps");
//            oiIncinerator.setXh(UUID.randomUUID().toString().substring(0,8));
            oiIncinerator.setCcsno(oiShipInfo.getCcsno());
            oiIncinerator.setCreator(this.getUserName());
            oiIncinerator.setIsDelete(0);
            oiIncinerator.setCreateTm(new Date());
            oiIncinerator.setDataSource("create");
            OiIncinerator saveIncinerator = oiIncineratorRepository.save(oiIncinerator);
            saveFuelType(shipEquipmentFuelMaps, oiIncineratorDto.getShipId(), saveIncinerator.getId());
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo getOiIncinerator(String oiIncineratorId) {
        log.debug("焚烧炉id=={}", oiIncineratorId);
        if (!StringUtils.isNotEmpty(oiIncineratorId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
        OiIncinerator oiIncinerator = oiIncineratorRepository.findByIdAndIsDelete(oiIncineratorId, 0);
        return new ResultObjectVo<>(oiIncinerator);
    }

    @Override
    public ResultVo deleteIncinerator(String oiIncineratorId) {
        log.debug("焚烧炉id=={}", oiIncineratorId);
        if (!StringUtils.isNotEmpty(oiIncineratorId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
//        oiIncineratorRepository.deleteOiIncineratorById(oiIncineratorId);
        OiIncinerator oiIncinerator = oiIncineratorRepository.findByIdAndIsDelete(oiIncineratorId, 0);
        if (oiIncinerator != null) {
            oiIncinerator.setIsDelete(1);
            oiIncineratorRepository.save(oiIncinerator);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo createAndUpdateOishipOther(OiShipOtherDto oiShipOtherDto) {
        log.debug("其他设备信息=={}", oiShipOtherDto);
        ResultStringVo resultStringVo = new ResultStringVo(null);
        if (StringUtils.isNotEmpty(oiShipOtherDto.getId())) {
            //更新其他设备信息
            OiShipOther oiShipOther = oiShipOtherRepository.findById(oiShipOtherDto.getId()).get();
            BeanUtils.copyProperties(oiShipOtherDto, oiShipOther);
            oiShipOther.setUpdatedate(new Date());
            oiShipOther.setOpuser(this.getUserName());
            oiShipOtherRepository.save(oiShipOther);
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            //新增其他设备信息
            OiShipOther oiShipOther = new OiShipOther();
            BeanUtils.copyProperties(oiShipOtherDto, oiShipOther);
//            oiShipOther.setXh(UUID.randomUUID().toString().substring(0,8));
            oiShipOther.setCreateTm(new Date());
            oiShipOther.setCreator(this.getUserName());
            oiShipOther.setIsDelete(0);
            oiShipOtherRepository.save(oiShipOther);
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo getOiShipOther(String oiShipOtherId) {
        log.debug("其他设备id=={}", oiShipOtherId);
        if (!StringUtils.isNotEmpty(oiShipOtherId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
        OiShipOther oiShipOther = oiShipOtherRepository.findById(oiShipOtherId).get();
        return new ResultObjectVo<>(oiShipOther);
    }

    @Override
    public ResultVo deleteOiShipOther(String oiShipOtherId) {
        log.debug("其他设备id=={}", oiShipOtherId);
        if (!StringUtils.isNotEmpty(oiShipOtherId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
//        oiShipOtherRepository.deleteOiShipOtherById(oiShipOtherId);
        OiShipOther oiShipOther = oiShipOtherRepository.findByIdAndIsDelete(oiShipOtherId, 0);
        if (oiShipOther != null) {
            oiShipOther.setIsDelete(1);
            oiShipOtherRepository.save(oiShipOther);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo getShipEqipment(String shipId) {
        log.debug("船舶id=={}", shipId);
        if (!StringUtils.isNotEmpty(shipId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
        List<OiMainEngine> oiMainEngines = oiMainEngineRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipGe> oiShipGes = oiShipGeRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipBoiler> oiShipBoilers = oiShipBoilerRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipInert> oiShipInerts = oiShipInertRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipTurbine> oiShipTurbines = oiShipTurbineRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiIncinerator> oiIncinerators = oiIncineratorRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipOther> oiShipOthers = oiShipOtherRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipGenerator> oiShipGenerators = oiShipGeneratorRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipBattery> oiShipBatteries = oiShipBatteryRepository.findByShipIdAndIsDelete(shipId, 0);
        List<OiShipPropeller> oiShipPropellers = oiShipPropellerRepository.findByShipIdAndIsDelete(shipId, 0);
        OiShipEquipmentVo oiShipEquipmentVo = new OiShipEquipmentVo(oiMainEngines, oiShipGes, oiShipGenerators,
                oiShipBoilers, oiIncinerators, oiShipInerts, oiShipTurbines, oiShipOthers, oiShipBatteries, oiShipPropellers);

        return new ResultObjectVo<>(oiShipEquipmentVo);
    }

    @Override
    public ResultVo deleteNotMyComPanyShip(String shipId) {
        log.debug("船舶id=={}", shipId);
        OiShipInfo oiShipInfo = oiShipInfoRepository.findById(shipId).get();
        oiShipInfo.setDocIacs(null);
        oiShipInfo.setDocId(null);
        oiShipInfo.setOperatorId(null);
        oiShipInfo.setOwnerId(null);
        oiShipInfo.setIsDelete(0);
        oiShipInfoRepository.save(oiShipInfo);
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo getSsmisOiShipInfo(OiShipInfoSsmisCriteria oiShipInfoSsmisCriteria) {
        log.debug("ssmis参数信息=={}", oiShipInfoSsmisCriteria);
        Integer startPage = (oiShipInfoSsmisCriteria.getCurrentPage() - 1) * oiShipInfoSsmisCriteria.getPageSize();
        Integer endPage = oiShipInfoSsmisCriteria.getCurrentPage() * oiShipInfoSsmisCriteria.getPageSize();
        //查询所有船舶信息
        List<SsmisOiShipInfoEntity> ssmisOiShipInfoEntities = ssmisOiShipInfoRepository.findByCodeAndImono(oiShipInfoSsmisCriteria.getDocId(), oiShipInfoSsmisCriteria.getImo(), endPage, startPage);
        List<SsmisOiShipInfoVo> ssmisOiShipInfoVos = getSsmisOishipInfo(ssmisOiShipInfoEntities);
        //查询总条数
        Integer count = ssmisOiShipInfoRepository.getCount(oiShipInfoSsmisCriteria.getDocId(), oiShipInfoSsmisCriteria.getImo());
        PageDataVo<SsmisOiShipInfoVo> ssmisOiShipInfoVoPageDataVo = new PageDataVo<>();
        ssmisOiShipInfoVoPageDataVo.setTotal(count);
        ssmisOiShipInfoVoPageDataVo.setItems(ssmisOiShipInfoVos);
        return new ResultObjectVo<>(ssmisOiShipInfoVoPageDataVo);
    }

    private List<SsmisOiShipInfoVo> getSsmisOishipInfo(List<SsmisOiShipInfoEntity> ssmisOiShipInfoEntities) {
        List<SsmisOiShipInfoVo> ssmisOiShipInfoVos = new ArrayList<>();
        ssmisOiShipInfoEntities.forEach(ssmisOiShipInfoEntity -> {
            SsmisOiShipInfoVo ssmisOiShipInfoVo = new SsmisOiShipInfoVo();
            BeanUtils.copyProperties(ssmisOiShipInfoEntity, ssmisOiShipInfoVo);
            List<BigDecimal> eediValue = ssmisOiShipEquipAttrRepository.findByCcsnoAndSmSystree(ssmisOiShipInfoEntity.getCcsno(), SM_SYS_TREE_ID);
            if (eediValue != null && eediValue.size() > 0) {
                ssmisOiShipInfoVo.setEediValue(eediValue.get(0));
            } else {
                ssmisOiShipInfoVo.setEediValue(BigDecimal.ZERO);
            }
            System.out.println("=====================================" + ssmisOiShipInfoEntity.getCcsno());
            List<SsmisOiShipMainEngineEntity> ssmisOiShipMainEngineEntities = ssmisOiShipMainEngineRepository.findByCcsno(ssmisOiShipInfoEntity.getCcsno());
            ;
//            try {
//                 ssmisOiShipMainEngineEntities = ssmisOiShipMainEngineRepository.findByCcsno(ssmisOiShipInfoEntity.getCcsno());
////            }catch (Exception e){
////                SsmisOiShipMainEngineEntity ssmisOiShipMainEngineEntity = new SsmisOiShipMainEngineEntity();
////                ssmisOiShipMainEngineEntity.setCcsno("94K3331");
////                ssmisOiShipMainEngineEntity.setDateManufacturer(null);
////                ssmisOiShipMainEngineEntity.setEnginetype("SULZER 6RTA62");
////                ssmisOiShipMainEngineEntity.setManufacturer("Diesel United Ltd.,Aioi Japan");
////                ssmisOiShipMainEngineEntity.setRatePower(BigDecimal.valueOf(8826));
////                ssmisOiShipMainEngineEntity.setRatedSpeed(BigDecimal.valueOf(96.5));
////                ssmisOiShipMainEngineEntity.setSerialno("");
////                System.out.println("*********************************************"+ssmisOiShipInfoEntity.getCcsno());
////            }
            List<SsmisOiShipGeEntity> ssmisOiShipGeEntities = ssmisOiShipGeRepository.findByCcsnoAndIsDelete(ssmisOiShipInfoEntity.getCcsno());
            ssmisOiShipInfoVo.setSsmisOiShipMainEngineEntities(ssmisOiShipMainEngineEntities);
            ssmisOiShipInfoVo.setSsmisOiShipGeEntities(ssmisOiShipGeEntities);
            ssmisOiShipInfoVos.add(ssmisOiShipInfoVo);
        });
        return ssmisOiShipInfoVos;
    }

    @Override
    @Transactional
    public ResultVo synSsmisOiShipInfo(SsmisShipCcsnosVo ssmisShipCcsnosVo) {
        List<String> ccsnos = new ArrayList<>();
        ssmisShipCcsnosVo.getCcsnos().forEach(ssmisOiShipInfoDto -> {
            ccsnos.add(ssmisOiShipInfoDto.getImono());
        });
        //根据ccsno列表获取需要同步的ssmis船舶信息
        List<SsmisOiShipInfoEntity> ssmisOiShipInfoEntities = ssmisOiShipInfoRepository.findByCcsNos(ccsnos);
        //获取ssmis船舶的设备信息
        List<SsmisOiShipInfoVo> ssmisOiShipInfoVos = getSsmisOishipInfo(ssmisOiShipInfoEntities);
        AtomicInteger saveCount = new AtomicInteger();
        AtomicInteger updateCount = new AtomicInteger();
        sysSmisData(ssmisOiShipInfoVos, saveCount, updateCount);
        return new ResultStringVo(this.msg("ship.ssmis.new") + saveCount.get() + this.msg("ship.ssmis.update") + updateCount.get());
    }
//    @Override
//    @Transactional
//    public ResultVo synSsmisOiShipInfo(SsmisShipCcsnosVo ssmisShipCcsnosVo) {
//        List<String> ccsnos = new ArrayList<>();
//        ssmisShipCcsnosVo.getCcsnos().forEach(ssmisOiShipInfoDto -> {
//            ccsnos.add(ssmisOiShipInfoDto.getCcsno());
//        });
//        //根据ccsno列表获取需要同步的ssmis船舶信息
//        List<SsmisOiShipInfoEntity> ssmisOiShipInfoEntities = ssmisOiShipInfoRepository.findByCcsNos(ccsnos);
//        //获取ssmis船舶的设备信息
//        List<SsmisOiShipInfoVo> ssmisOiShipInfoVos = getSsmisOishipInfo(ssmisOiShipInfoEntities);
//        AtomicInteger saveCount = new AtomicInteger();
//        AtomicInteger updateCount = new AtomicInteger();
//        ssmisOiShipInfoVos.forEach(ssmisOiShipInfoVo -> {
//            OiShipInfo oiShipInfo = oiShipInfoRepository.findByCcsnoAndIsDelete(ssmisOiShipInfoVo.getCcsno(), 0);
//            List<SsmisOiShipMainEngineEntity> ssmisOiShipMainEngineEntities = ssmisOiShipInfoVo.getSsmisOiShipMainEngineEntities();
//            List<SsmisOiShipGeEntity> ssmisOiShipGeEntities = ssmisOiShipInfoVo.getSsmisOiShipGeEntities();
//            GcClient gcClient = gcClientRepository.findByCode(ssmisOiShipInfoVo.getDocCode());
//            Classificat classificat = classificatRepository.findByAbbrnAndIsDelete(ssmisOiShipInfoVo.getShipClass(), "0");
//            List<String> threeCode = gcStateRepository.findThreeCode(ssmisOiShipInfoVo.getFlag());
//            //需要查询
//            List<String> shipTypeCodes = shipTypeRepository.findShipTypeCodes(ssmisOiShipInfoVo.getSptype());
//            if (null == oiShipInfo) {
//                saveCount.getAndIncrement();
//                //ssmis新增船舶信息到eeoi系统
//                OiShipInfo oiShipInfoNew = new OiShipInfo();
//                oiShipInfoNew.setImono(ssmisOiShipInfoVo.getImono());
//                oiShipInfoNew.setCcsno(ssmisOiShipInfoVo.getCcsno());
//                oiShipInfoNew.setRegisterno(ssmisOiShipInfoVo.getCcsno());
//                oiShipInfoNew.setHomePort(ssmisOiShipInfoVo.getPort());
//                oiShipInfoNew.setSpname(ssmisOiShipInfoVo.getSpname());
//                oiShipInfoNew.setSpnameCn(ssmisOiShipInfoVo.getCspname());
//                oiShipInfoNew.setFlag(ssmisOiShipInfoVo.getFlag());
//                oiShipInfoNew.setFlagCode(threeCode.size() > 0 ? threeCode.get(0) : null);
//                oiShipInfoNew.setDocId(ssmisOiShipInfoVo.getDocCode());
//                oiShipInfoNew.setDocmanager(ssmisOiShipInfoVo.getDocName());
//                if (null != gcClient) {
//                    oiShipInfoNew.setDocIacs(gcClient.getIacs());
//                    oiShipInfoNew.setDocCuid(gcClient.getCUid());
//                }
//                oiShipInfoNew.setClass_(classificat == null ? null : classificat.getCode());
//                oiShipInfoNew.setDw(ssmisOiShipInfoVo.getDw());
//                oiShipInfoNew.setSptype(shipTypeCodes.size() > 0 ? shipTypeCodes.get(0) : null);
//                oiShipInfoNew.setFinish(ssmisOiShipInfoVo.getFinish());
//                oiShipInfoNew.setGross(ssmisOiShipInfoVo.getGross());
//                oiShipInfoNew.setNet(ssmisOiShipInfoVo.getNet());
//                oiShipInfoNew.setTeus(ssmisOiShipInfoVo.getTeus());
//                oiShipInfoNew.setSpeed(ssmisOiShipInfoVo.getSpeed());
//                oiShipInfoNew.setServiceSpeed(ssmisOiShipInfoVo.getSpeed());
//                oiShipInfoNew.setEedivalue(ssmisOiShipInfoVo.getEediValue());
//                oiShipInfoNew.setDepth(ssmisOiShipInfoVo.getDraught1());
//                oiShipInfoNew.setWidth(ssmisOiShipInfoVo.getBreadth());
//                oiShipInfoNew.setLength(ssmisOiShipInfoVo.getLength());
//                oiShipInfoNew.setLbp(ssmisOiShipInfoVo.getLenthLl());
//                oiShipInfoNew.setTakenTime(new Date());
//                oiShipInfoNew.setDataSource(SSMIS_DATA_SOURCE);
//                oiShipInfoNew.setCreator(this.getUserName());
//                oiShipInfoNew.setIsDelete(0);
//                oiShipInfoNew.setOwnerId(ssmisOiShipInfoVo.getOwnerCode());
//                oiShipInfoNew.setOwner(ssmisOiShipInfoVo.getOwnerName());
//                oiShipInfoNew.setOperatorId(ssmisOiShipInfoVo.getOperatorCode());
//                oiShipInfoNew.setOperatorShip(ssmisOiShipInfoVo.getOperatorName());
//                oiShipInfoNew.setBuilderId(ssmisOiShipInfoVo.getBuilderCode());
//                oiShipInfoNew.setBuilder(ssmisOiShipInfoVo.getBuilderName());
//                OiShipInfo save = oiShipInfoRepository.save(oiShipInfoNew);
//                //新增主机设备信息
//                saveOiMainEngine(ssmisOiShipMainEngineEntities, save);
//                //新增副机设备信息
//                saveOiShipGe(ssmisOiShipGeEntities, save);
//            } else {
//                updateCount.getAndIncrement();
//                //更新设备信息
//                oiShipInfo.setImono(ssmisOiShipInfoVo.getImono());
//                oiShipInfo.setCcsno(ssmisOiShipInfoVo.getCcsno());
//                oiShipInfo.setHomePort(ssmisOiShipInfoVo.getPort());
//                oiShipInfo.setSpname(ssmisOiShipInfoVo.getSpname());
//                oiShipInfo.setSpnameCn(ssmisOiShipInfoVo.getCspname());
//                oiShipInfo.setFlag(ssmisOiShipInfoVo.getFlag());
//                oiShipInfo.setFlagCode(threeCode.size() > 0 ? threeCode.get(0) : null);
//                //需要根据船级去查询
//                oiShipInfo.setClass_(classificat == null ? null : classificat.getCode());
//                oiShipInfo.setDw(ssmisOiShipInfoVo.getDw());
//                oiShipInfo.setDocId(ssmisOiShipInfoVo.getDocCode());
//                oiShipInfo.setDocmanager(ssmisOiShipInfoVo.getDocName());
//                if (null != gcClient) {
//                    oiShipInfo.setDocIacs(gcClient.getIacs());
//                    oiShipInfo.setDocCuid(gcClient.getCUid());
//                }
//                //需要查询
//                oiShipInfo.setSptype(shipTypeCodes.size() > 0 ? shipTypeCodes.get(0) : null);
//                oiShipInfo.setFinish(ssmisOiShipInfoVo.getFinish());
//                oiShipInfo.setGross(ssmisOiShipInfoVo.getGross());
//                oiShipInfo.setNet(ssmisOiShipInfoVo.getNet());
//                oiShipInfo.setTeus(ssmisOiShipInfoVo.getTeus());
//                oiShipInfo.setSpeed(ssmisOiShipInfoVo.getSpeed());
//                oiShipInfo.setEedivalue(ssmisOiShipInfoVo.getEediValue());
//                oiShipInfo.setDepth(ssmisOiShipInfoVo.getDraught1());
//                oiShipInfo.setWidth(ssmisOiShipInfoVo.getBreadth());
//                oiShipInfo.setLength(ssmisOiShipInfoVo.getLength());
//                oiShipInfo.setLbp(ssmisOiShipInfoVo.getLenthLl());
//                oiShipInfo.setUpdateTime(new Date());
//                oiShipInfo.setOwnerId(ssmisOiShipInfoVo.getOwnerCode());
//                oiShipInfo.setOwner(ssmisOiShipInfoVo.getOwnerName());
//                oiShipInfo.setOperatorId(ssmisOiShipInfoVo.getOperatorCode());
//                oiShipInfo.setOperatorShip(ssmisOiShipInfoVo.getOperatorName());
//                oiShipInfo.setBuilderId(ssmisOiShipInfoVo.getBuilderCode());
//                oiShipInfo.setBuilder(ssmisOiShipInfoVo.getBuilderName());
//                oiShipInfoRepository.save(oiShipInfo);
//                List<OiMainEngine> oiMainEngines = oiMainEngineRepository.findByShipIdAndIsDelete(oiShipInfo.getId(), 0);
//                List<OiShipGe> oiShipGes = oiShipGeRepository.findByShipIdAndIsDelete(oiShipInfo.getId(), 0);
//                if (oiMainEngines.size() == 0) {
//                    saveOiMainEngine(ssmisOiShipMainEngineEntities, oiShipInfo);
//                } else {
//                    OiMainEngine oiMainEngine = oiMainEngines.get(0);
//                    SsmisOiShipMainEngineEntity ssmisOiShipMainEngineEntity = ssmisOiShipMainEngineEntities.get(0);
//                    oiMainEngine.setCcsno(ssmisOiShipMainEngineEntity.getCcsno());
//                    oiMainEngine.setEngineType(ssmisOiShipMainEngineEntity.getEnginetype());
//                    oiMainEngine.setRatedSpeed(ssmisOiShipMainEngineEntity.getRatedSpeed());
//                    oiMainEngine.setRatePower(ssmisOiShipMainEngineEntity.getRatePower());
//                    oiMainEngine.setMfrsName(ssmisOiShipMainEngineEntity.getManufacturer());
//                    oiMainEngine.setSerialNo(ssmisOiShipMainEngineEntity.getSerialno());
//                    oiMainEngine.setUpdateTime(new Date());
//                    oiMainEngine.setXh(String.valueOf(1));
//                    oiMainEngineRepository.save(oiMainEngine);
//
//                }
//                if (oiShipGes.size() == 0) {
//                    saveOiShipGe(ssmisOiShipGeEntities, oiShipInfo);
//                } else {
//                    //进行逻辑删除，然后在插入
//                    oiShipGes.forEach(oiShipGe -> {
//                        if (oiShipGe.getDataSource().equals(SSMIS_DATA_SOURCE)) {
//                            oiShipGe.setIsDelete(1);
//                            oiShipGe.setOpdate(new Date());
//                            oiShipGeRepository.save(oiShipGe);
//                        }
//                    });
//                    saveOiShipGe(ssmisOiShipGeEntities, oiShipInfo);
//                }
//            }
//        });
//        return new ResultStringVo(this.msg("ship.ssmis.new") + saveCount.get() + this.msg("ship.ssmis.update") + updateCount.get());
//    }

    @Override
    public ResultVo createAndUpdateBattery(OiShipBatteryDto oiShipBatteryDto) {
        log.debug("电池信息=={}", oiShipBatteryDto);
        ResultStringVo resultStringVo = new ResultStringVo(null);
        if (StringUtils.isNotEmpty(oiShipBatteryDto.getId())) {
            //更新电池信息
            OiShipBattery getOiShipBattery = oiShipBatteryRepository.findByIdAndIsDelete(oiShipBatteryDto.getId(), 0);
            BeanUtils.copyProperties(oiShipBatteryDto, getOiShipBattery);
            oiShipBatteryRepository.save(getOiShipBattery);
            resultStringVo.setData(this.msg("ship.equipment.update.success"));
        } else {
            OiShipBattery oiShipBattery = new OiShipBattery();
            BeanUtils.copyProperties(oiShipBatteryDto, oiShipBattery);
            oiShipBattery.setIsDelete(0);
            oiShipBatteryRepository.save(oiShipBattery);
            resultStringVo.setData(this.msg("ship.equipment.create.success"));
        }
        return resultStringVo;
    }

    @Override
    public ResultVo getOiShipBattery(String oiShipBatteryId) {
        log.debug("电池信息id=={}", oiShipBatteryId);
        if (!StringUtils.isNotEmpty(oiShipBatteryId)) {
            throw new ExplicitException(this.msg("ship.params.null"));
        }
        OiShipBattery oiShipBattery = oiShipBatteryRepository.findByIdAndIsDelete(oiShipBatteryId, 0);
        return new ResultObjectVo<>(oiShipBattery);
    }

    @Override
    public ResultVo deleteOiShipBattery(OiShipBatteryDto oiShipBatteryDto) {
        OiShipBattery oiShipBattery = oiShipBatteryRepository.findByIdAndIsDelete(oiShipBatteryDto.getId(), 0);
        if (oiShipBattery != null) {
            oiShipBattery.setIsDelete(1);
            oiShipBatteryRepository.save(oiShipBattery);
        }
        return new ResultStringVo(this.msg("ship.equipment.delete.success"));
    }

    @Override
    public ResultVo saveAndUpdateTfc(OiShipTfc oiShipTfc) {
        OiShipTfc oiShipTfcGet = oiShipTfcRepository.findByAverageSpeedAndShipidAndIsDelete(oiShipTfc.getAverageSpeed(), oiShipTfc.getShipid(), 0);
        if (oiShipTfcGet == null) {
            oiShipTfcGet = new OiShipTfc();
        } else {
            oiShipTfc.setId(oiShipTfcGet.getId());
        }
        BeanUtils.copyProperties(oiShipTfc, oiShipTfcGet);
        oiShipTfcGet.setIsDelete(0);
        OiShipTfc save = oiShipTfcRepository.save(oiShipTfcGet);
        if (save != null) {
            return new ResultStringVo(this.msg("energyEeffic.create.success"));
        } else {
            return new ResultErrorVo(this.msg("energyEeffic.create.failed"));
        }
    }

    @Override
    public ResultVo deleteTfc(OiShipTfc oiShipTfc) {
        OiShipTfc oiShipTfc1 = oiShipTfcRepository.findByIdAndIsDelete(oiShipTfc.getId(), 0);
        if (oiShipTfc1 != null) {
            oiShipTfc1.setIsDelete(1);
            oiShipTfcRepository.save(oiShipTfc1);
        }
        return new ResultStringVo(this.msg("energyEeffic.delete.success"));
    }

    @Override
    public ResultVo getTfc(String shipId) {
        List<OiShipTfc> oiShipTfc = oiShipTfcRepository.findByShipidAndIsDelete(shipId, 0);
        return new ResultObjectVo<>(oiShipTfc);
    }

    @Override
    public ResultVo getFuelType() {
        List<Fuel> all = fuelRepository.findAll();
        List<ShipEquipmentFuelMapVo> shipEquipmentFuelMapVos = new ArrayList<>();
        all.forEach(fuel -> {
            ShipEquipmentFuelMapVo shipEquipmentFuelMapVo = new ShipEquipmentFuelMapVo();
            shipEquipmentFuelMapVo.setFuelId(fuel.getFuelCode());
            shipEquipmentFuelMapVo.setFuelName(fuel.getFuelName());
            shipEquipmentFuelMapVo.setConsMethod("1");
            shipEquipmentFuelMapVos.add(shipEquipmentFuelMapVo);
        });

        return new ResultObjectVo<>(shipEquipmentFuelMapVos);
    }

    @Override
    @Transactional
    public ResultVo createAndUpdatePropeller(OishipPropellerDto oishipPropellerDto) {
        String id = oishipPropellerDto.getId();
        String shipId = oishipPropellerDto.getShipId();
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByIdAndIsDelete(shipId, 0);
        oishipPropellerDto.setShipName(oiShipInfo.getSpname());
        OiShipPropeller oiShipPropeller = oiShipPropellerRepository.findByIdAndIsDelete(id, 0);
        if (oiShipPropeller == null) {
            oiShipPropeller = new OiShipPropeller();
            BeanUtils.copyProperties(oishipPropellerDto, oiShipPropeller);
            oiShipPropeller.setIsDelete(0);
            oiShipPropeller.setDataSource("create");
            oiShipPropeller.setDatatype("create");
        } else {
            BeanUtils.copyProperties(oishipPropellerDto, oiShipPropeller);
        }
        OiShipPropeller save = oiShipPropellerRepository.save(oiShipPropeller);
        if (save != null) {
            return new ResultStringVo(this.msg("energyEeffic.create.success"));
        } else {
            return new ResultErrorVo(this.msg("energyEeffic.create.failed"));
        }
    }

    @Override
    public ResultVo getOiShipPropelerById(String id) {
        OiShipPropeller oiShipPropeller = oiShipPropellerRepository.findByIdAndIsDelete(id, 0);
        return new ResultObjectVo<>(oiShipPropeller);
    }

    @Override
    @Transactional
    public ResultVo deleteOiShipPropeller(String id) {
        OiShipPropeller oiShipPropeller = oiShipPropellerRepository.findByIdAndIsDelete(id, 0);
        if (oiShipPropeller != null) {
            oiShipPropeller.setIsDelete(1);
        }
        OiShipPropeller save = oiShipPropellerRepository.save(oiShipPropeller);
        if (save != null) {
            return new ResultStringVo(this.msg("energyEeffic.delete.success"));
        } else {
            return new ResultErrorVo(this.msg("energyEeffic.delete.failed"));
        }
    }

    /**
     * 新增副机
     *
     * @param oiShipGeEntities
     * @return
     */
    private void saveOiShipGe(List<SsmisOiShipGeEntity> oiShipGeEntities, OiShipInfo save) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int j = 0; j < oiShipGeEntities.size(); j++) {
            SsmisOiShipGeEntity ssmisOiShipGeEntity = oiShipGeEntities.get(j);
            OiShipGe oiShipGe = new OiShipGe();
            oiShipGe.setCcsno(ssmisOiShipGeEntity.getCcsno());
            if (null == ssmisOiShipGeEntity.getSetno()) {
                oiShipGe.setXh(String.valueOf(j));
            } else {
                oiShipGe.setXh(String.valueOf(ssmisOiShipGeEntity.getSetno()));
            }
            try {
                Date oiShipGeProductTime = simpleDateFormat.parse(ssmisOiShipGeEntity.getDateManufacturer());
                oiShipGe.setProduceTime(oiShipGeProductTime);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getStackTrace().toString());
                oiShipGe.setProduceTime(null);
            }
            oiShipGe.setIsDelete(0);
            oiShipGe.setEngineType(ssmisOiShipGeEntity.getGemodel());
            oiShipGe.setSerialNo(ssmisOiShipGeEntity.getSerialno());
            oiShipGe.setRatedSpeed(ssmisOiShipGeEntity.getRatedSpeed());
            oiShipGe.setRatePower(ssmisOiShipGeEntity.getRatePower());
            oiShipGe.setMfrsName(ssmisOiShipGeEntity.getManufacturer());
            oiShipGe.setIsDelete(0);
            oiShipGe.setTakenTime(new Date());
            oiShipGe.setCreator("定时任务同步数据");
            oiShipGe.setShipId(save.getId());
            oiShipGe.setShipName(save.getSpnameCn());
            oiShipGe.setDataSource(SSMIS_DATA_SOURCE);
            oiShipGeRepository.save(oiShipGe);
        }
    }

    private void saveOiMainEngine(List<SsmisOiShipMainEngineEntity> ssmisOiShipMainEngineEntities, OiShipInfo save) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < ssmisOiShipMainEngineEntities.size(); i++) {
            SsmisOiShipMainEngineEntity ssmisOiShipMainEngineEntity = ssmisOiShipMainEngineEntities.get(i);
            OiMainEngine oiMainEngine = new OiMainEngine();
            oiMainEngine.setCcsno(ssmisOiShipMainEngineEntity.getCcsno());
            oiMainEngine.setEngineType(ssmisOiShipMainEngineEntity.getEnginetype());
            oiMainEngine.setRatedSpeed(ssmisOiShipMainEngineEntity.getRatedSpeed());
            oiMainEngine.setRatePower(ssmisOiShipMainEngineEntity.getRatePower());
            oiMainEngine.setMfrsName(ssmisOiShipMainEngineEntity.getManufacturer());
            oiMainEngine.setSerialNo(ssmisOiShipMainEngineEntity.getSerialno());
            try {
                Date oiShipMainEngineProductTime = simpleDateFormat.parse(ssmisOiShipMainEngineEntity.getDateManufacturer());
                oiMainEngine.setProduceTime(oiShipMainEngineProductTime);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getStackTrace().toString());
                oiMainEngine.setProduceTime(null);
            }
            oiMainEngine.setShipId(save.getId());
            oiMainEngine.setShipName(save.getSpnameCn());
            oiMainEngine.setXh(String.valueOf(i + 1));
            oiMainEngine.setIsDelete(0);
            oiMainEngine.setTakenTime(new Date());
            oiMainEngine.setDataSource(SSMIS_DATA_SOURCE);
            oiMainEngine.setOperator("定时任务同步数据");
            oiMainEngineRepository.save(oiMainEngine);
        }
    }

    @Scheduled(cron = "0 0 23 * * ?")
    @Override
    public String sysSsmisShipInfo() {
        System.out.println("===============================定时任务启动");
        //根据ccsno列表获取需要同步的ssmis船舶信息
        List<SsmisOiShipInfoEntity> ssmisOiShipInfoEntities = ssmisOiShipInfoRepository.findAllSsmisPage(8262, 8000);
        //获取ssmis船舶的设备信息
        List<SsmisOiShipInfoVo> ssmisOiShipInfoVos = getSsmisOishipInfo(ssmisOiShipInfoEntities);
        AtomicInteger saveCount = new AtomicInteger();
        AtomicInteger updateCount = new AtomicInteger();
        sysSmisData(ssmisOiShipInfoVos, saveCount, updateCount);
        log.info("新增" + saveCount + "更新" + updateCount);
        return "新增" + saveCount + "更新" + updateCount;
    }

    private void sysSmisData(List<SsmisOiShipInfoVo> ssmisOiShipInfoVos, AtomicInteger saveCount, AtomicInteger updateCount) {
        ssmisOiShipInfoVos.forEach(ssmisOiShipInfoVo -> {
            OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoAndIsDelete(ssmisOiShipInfoVo.getImono());
            List<SsmisOiShipMainEngineEntity> ssmisOiShipMainEngineEntities = ssmisOiShipInfoVo.getSsmisOiShipMainEngineEntities();
            List<SsmisOiShipGeEntity> ssmisOiShipGeEntities = ssmisOiShipInfoVo.getSsmisOiShipGeEntities();
            List<GcClient> gcClients = gcClientRepository.findByCodeList(ssmisOiShipInfoVo.getDocCode());
            Classificat classificat = classificatRepository.findByAbbrnAndIsDelete(ssmisOiShipInfoVo.getShipClass(), "0");
            List<String> threeCode = gcStateRepository.findThreeCode(ssmisOiShipInfoVo.getFlag());
            //需要查询
            List<String> shipTypeCodes = shipTypeRepository.findShipTypeCodes(ssmisOiShipInfoVo.getSptype());
            if (oiShipInfo == null) {
                saveCount.getAndIncrement();
                //ssmis新增船舶信息到eeoi系统
                OiShipInfo oiShipInfoNew = new OiShipInfo();
                oiShipInfoNew.setImono(ssmisOiShipInfoVo.getImono());
                oiShipInfoNew.setCcsno(ssmisOiShipInfoVo.getCcsno());
                oiShipInfoNew.setRegisterno(ssmisOiShipInfoVo.getCcsno());
                oiShipInfoNew.setHomePort(ssmisOiShipInfoVo.getPort());
                oiShipInfoNew.setSpname(ssmisOiShipInfoVo.getSpname());
                oiShipInfoNew.setSpnameCn(ssmisOiShipInfoVo.getCspname());
                oiShipInfoNew.setFlag(ssmisOiShipInfoVo.getFlag());
                oiShipInfoNew.setFlagCode(threeCode.size() > 0 ? threeCode.get(0) : null);
                oiShipInfoNew.setDocId(ssmisOiShipInfoVo.getDocCode());
                oiShipInfoNew.setDocmanager(ssmisOiShipInfoVo.getDocName());
                if (gcClients != null && gcClients.size() > 0) {
                    GcClient gcClient = gcClients.get(0);
                    oiShipInfoNew.setDocIacs(gcClient.getIacs());
                    oiShipInfoNew.setDocCuid(gcClient.getCUid());
                }
                oiShipInfoNew.setClass_(classificat == null ? null : classificat.getAbbrn());
                oiShipInfoNew.setDw(ssmisOiShipInfoVo.getDw());
                oiShipInfoNew.setSptype(shipTypeCodes.size() > 0 ? shipTypeCodes.get(0) : null);
                oiShipInfoNew.setFinish(ssmisOiShipInfoVo.getFinish());
                oiShipInfoNew.setGross(ssmisOiShipInfoVo.getGross());
                oiShipInfoNew.setNet(ssmisOiShipInfoVo.getNet());
                oiShipInfoNew.setTeus(ssmisOiShipInfoVo.getTeus());
                oiShipInfoNew.setSpeed(ssmisOiShipInfoVo.getSpeed());
                oiShipInfoNew.setServiceSpeed(ssmisOiShipInfoVo.getSpeed());
                oiShipInfoNew.setEedivalue(ssmisOiShipInfoVo.getEediValue());
                oiShipInfoNew.setDepth(ssmisOiShipInfoVo.getDraught1());
                oiShipInfoNew.setWidth(ssmisOiShipInfoVo.getBreadth());
                oiShipInfoNew.setLength(ssmisOiShipInfoVo.getLength());
                oiShipInfoNew.setLbp(ssmisOiShipInfoVo.getLenthLl());
                oiShipInfoNew.setTakenTime(new Date());
                oiShipInfoNew.setDataSource(SSMIS_DATA_SOURCE);
                oiShipInfoNew.setCreator("定时任务同步数据");
                oiShipInfoNew.setIsDelete(0);
                oiShipInfoNew.setOwnerId(ssmisOiShipInfoVo.getOwnerCode());
                oiShipInfoNew.setOwner(ssmisOiShipInfoVo.getOwnerName());
                oiShipInfoNew.setOperatorId(ssmisOiShipInfoVo.getOperatorCode());
                oiShipInfoNew.setOperatorShip(ssmisOiShipInfoVo.getOperatorName());
                oiShipInfoNew.setBuilderId(ssmisOiShipInfoVo.getBuilderCode());
                oiShipInfoNew.setBuilder(ssmisOiShipInfoVo.getBuilderName());
                OiShipInfo save = oiShipInfoRepository.save(oiShipInfoNew);
                //新增主机设备信息
                String shipClass = ssmisOiShipInfoVo.getShipClass();
                if ("CCS".equals(shipClass)) {
                    saveOiMainEngine(ssmisOiShipMainEngineEntities, save);
                    //新增副机设备信息
                    saveOiShipGe(ssmisOiShipGeEntities, save);
                }
            } else {
                updateCount.getAndIncrement();
                //更新设备信息
                String imono = ssmisOiShipInfoVo.getImono();
                oiShipInfo.setTakenTime(new Date());
                oiShipInfo.setDataSource(SSMIS_DATA_SOURCE);
                if (imono != null) {
                    oiShipInfo.setImono(imono);
                }
                String ccsno = ssmisOiShipInfoVo.getCcsno();
                if (ccsno != null) {
                    oiShipInfo.setRegisterno(ccsno);
                    oiShipInfo.setCcsno(ccsno);
                }
                String port = ssmisOiShipInfoVo.getPort();
                if (port != null) {
                    oiShipInfo.setHomePort(port);
                }
                String spname = ssmisOiShipInfoVo.getSpname();
                if (spname != null) {

                    oiShipInfo.setSpname(spname);
                }
                String cspname = ssmisOiShipInfoVo.getCspname();
                if (cspname != null) {

                    oiShipInfo.setSpnameCn(cspname);
                }
                String flag = ssmisOiShipInfoVo.getFlag();
                if (flag != null) {
                    oiShipInfo.setFlag(flag);
                    oiShipInfo.setFlagCode(threeCode.size() > 0 ? threeCode.get(0) : null);
                }

                //需要根据船级去查询
                String shipClass = ssmisOiShipInfoVo.getShipClass();
                if (shipClass != null) {
                    oiShipInfo.setClass_(classificat == null ? null : classificat.getAbbrn());
                }
                BigDecimal dw = ssmisOiShipInfoVo.getDw();
                if (dw != null) {
                    oiShipInfo.setDw(dw);
                }
                String docCode = ssmisOiShipInfoVo.getDocCode();
                if (docCode != null) {
                    oiShipInfo.setDocId(docCode);
                    if (gcClients != null && gcClients.size() > 0) {
                        GcClient gcClient = gcClients.get(0);
                        oiShipInfo.setDocIacs(gcClient.getIacs());
                        oiShipInfo.setDocCuid(gcClient.getCUid());
                    }
                }
                String docName = ssmisOiShipInfoVo.getDocName();
                if (docName != null) {
                    oiShipInfo.setDocmanager(docName);
                }
                //需要查询
                oiShipInfo.setSptype(shipTypeCodes.size() > 0 ? shipTypeCodes.get(0) : null);
                Date finish = ssmisOiShipInfoVo.getFinish();
                if (finish != null) {
                    oiShipInfo.setFinish(finish);
                }
                BigDecimal gross = ssmisOiShipInfoVo.getGross();
                if (gross != null) {

                    oiShipInfo.setGross(gross);
                }
                BigDecimal net = ssmisOiShipInfoVo.getNet();
                if (net != null) {

                    oiShipInfo.setNet(net);
                }
                Integer teus = ssmisOiShipInfoVo.getTeus();
                if (teus != null) {
                    oiShipInfo.setTeus(teus);
                }
                BigDecimal speed = ssmisOiShipInfoVo.getSpeed();
                if (speed != null) {

                    oiShipInfo.setSpeed(speed);
                    oiShipInfo.setServiceSpeed(speed);
                }
                BigDecimal eediValue = ssmisOiShipInfoVo.getEediValue();
                if (eediValue != null) {

                    oiShipInfo.setEedivalue(eediValue);
                }
                BigDecimal draught1 = ssmisOiShipInfoVo.getDraught1();
                if (draught1 != null) {
                    oiShipInfo.setDepth(draught1);
                }
                BigDecimal breadth = ssmisOiShipInfoVo.getBreadth();
                if (breadth != null) {

                    oiShipInfo.setWidth(breadth);
                }
                BigDecimal length = ssmisOiShipInfoVo.getLength();
                if (length != null) {

                    oiShipInfo.setLength(length);
                }
                BigDecimal lenthLl = ssmisOiShipInfoVo.getLenthLl();
                if (lenthLl != null) {
                    oiShipInfo.setLbp(lenthLl);
                }
                oiShipInfo.setUpdateTime(new Date());
                String ownerCode = ssmisOiShipInfoVo.getOwnerCode();
                if (ownerCode != null) {

                    oiShipInfo.setOwnerId(ownerCode);
                }
                String ownerName = ssmisOiShipInfoVo.getOwnerName();
                if (ownerName != null) {

                    oiShipInfo.setOwner(ownerName);
                }
                String operatorCode = ssmisOiShipInfoVo.getOperatorCode();
                if (operatorCode != null) {
                    oiShipInfo.setOperatorId(operatorCode);
                }
                String operatorName = ssmisOiShipInfoVo.getOperatorName();
                if (operatorName != null) {

                    oiShipInfo.setOperatorShip(operatorName);
                }
                String builderCode = ssmisOiShipInfoVo.getBuilderCode();
                if (builderCode != null) {
                    oiShipInfo.setBuilderId(builderCode);
                }
                String builderName = ssmisOiShipInfoVo.getBuilderName();
                if (builderName != null) {
                    oiShipInfo.setBuilder(builderName);
                }
                oiShipInfoRepository.save(oiShipInfo);
                List<OiMainEngine> oiMainEngines = oiMainEngineRepository.findByShipIdAndIsDelete(oiShipInfo.getId(), 0);
                List<OiShipGe> oiShipGes = oiShipGeRepository.findByShipIdAndIsDelete(oiShipInfo.getId(), 0);
                if ("CCS".equals(shipClass)) {
                    if (ssmisOiShipMainEngineEntities.size() != 0) {
                        oiMainEngines.forEach(oiMainEngine -> {
                            oiMainEngine.setIsDelete(1);
                            oiMainEngine.setUpdateTime(new Date());
                        });
                        oiMainEngineRepository.saveAll(oiMainEngines);
                        saveOiMainEngine(ssmisOiShipMainEngineEntities, oiShipInfo);
                    }
                    if (ssmisOiShipGeEntities.size() != 0) {
                        oiShipGes.forEach(oiShipGe -> {
                            oiShipGe.setIsDelete(1);
                            oiShipGe.setOpdate(new Date());
                        });
                        oiShipGeRepository.saveAll(oiShipGes);
                        saveOiShipGe(ssmisOiShipGeEntities, oiShipInfo);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {

    }


    /*
     * @Override public List<OiShipInfoShowVo> getOiShipInfosByVo(OiShipInfoVo
     * oiShipInfoVo) { List<OiShipInfo> fromOiShipInfos = new
     * ArrayList<OiShipInfo>(); List<OiShipInfoShowVo> totalOiShipInfos = new
     * ArrayList<OiShipInfoShowVo>(); String jpql =
     * "select c from OiShipInfo c where 1=1"; if (oiShipInfoVo == null) {
     * fromOiShipInfos = commonRepository.findAllByJPQLCriteria(OiShipInfo.class,
     * jpql); } else { // 船公司 if
     * (StringUtils.isNotEmpty(oiShipInfoVo.getOperator())) {
     * oiShipInfoVo.setOperator(oiShipInfoVo.getOperator().toUpperCase());// 转换成大写
     * jpql += " and (upper(c.name) like '%" + oiShipInfoVo.getOperator() +
     * "%' or upper(c.title) like '%" + oiShipInfoVo.getOperator() + "%')"; } //
     * 船舶名称/IMO No./船检登记号 if (StringUtils.isNotEmpty(oiShipInfoVo.getNameOrimo())) {
     * oiShipInfoVo.setNameOrimo(oiShipInfoVo.getNameOrimo().toUpperCase());// 转换成大写
     * jpql += " and (upper(c.imono) like '%" + oiShipInfoVo.getNameOrimo() +
     * "%' or upper(c.spnameCn) like '%" + oiShipInfoVo.getNameOrimo() +
     * "%' or upper(c.spname) like '%" + oiShipInfoVo.getNameOrimo() + "%')"; } if
     * (StringUtils.isNotEmpty(oiShipInfoVo.getStartSpeed())) { jpql +=
     * " and c.speed>=" + oiShipInfoVo.getStartSpeed() + "'"; } if
     * (StringUtils.isNotEmpty(oiShipInfoVo.getEndSpeed())) { jpql +=
     * " and c.speed<=" + oiShipInfoVo.getEndSpeed() + "'"; } if
     * (StringUtils.isNotEmpty(oiShipInfoVo.getShipTypeCode())) { jpql +=
     * " and c.sptype='" + oiShipInfoVo.getShipTypeCode() + "'"; } if
     * (StringUtils.isNotEmpty(oiShipInfoVo.getShipSubTypeCode())) { jpql +=
     * " and c.sptypeSub='" + oiShipInfoVo.getShipSubTypeCode() + "'"; } if
     * (StringUtils.isNotEmpty(oiShipInfoVo.getGross())) { jpql += " and c.gross='"
     * + oiShipInfoVo.getGross() + "'"; } fromOiShipInfos =
     * commonRepository.findAllByJPQLCriteria(OiShipInfo.class, jpql); } for
     * (OiShipInfo client : fromOiShipInfos) { OiShipInfoShowVo shipShow = new
     * OiShipInfoShowVo(client); totalOiShipInfos.add(shipShow); } return
     * totalOiShipInfos; }
     */
}
