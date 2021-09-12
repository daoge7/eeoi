package cn.ccsit.eeoi.common.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ccsit.common.constant.FuelConst;
import cn.ccsit.common.constant.RoleConst;
import cn.ccsit.eeoi.common.vo.CalculateOilVo;
import cn.ccsit.eeoi.energyeefic.entity.*;
import cn.ccsit.eeoi.energyeefic.repository.RawVoyagePortRepository;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.repository.GcClientRepository;
import cn.ccsit.eeoi.ship.repository.OiShipCmsaMapRepository;
import cn.ccsit.eeoi.ship.repository.OiShipInfoRepository;
import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import cn.ccsit.common.enums.language.LangType;
import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.eeoi.common.utils.JwtUtils;
import cn.ccsit.eeoi.common.vo.CurrentUserVo;
import cn.ccsit.eeoi.data.repository.CommonRepository;
import cn.ccsit.eeoi.system.entity.SysUser;
import cn.ccsit.eeoi.system.entity.SysUserClientMap;
import cn.ccsit.eeoi.system.entity.SysUserRole;
import cn.ccsit.eeoi.system.repository.SysUserClientMapRepository;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
import cn.ccsit.eeoi.system.repository.SysUserRoleRepository;

@Service("commonService")
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    protected CommonRepository commonRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysUserClientMapRepository sysUserClientMapRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private GcClientRepository gcClientRepository;
    @Autowired
    private OiShipInfoRepository oiShipInfoRepository;
    @Autowired
    private OiShipCmsaMapRepository oiShipCmsaMapRepository;
    @Autowired
    private RawVoyagePortRepository rawVoyagePortRepository;
    private static final String CLIENT_CODE = "code";
    private static final String CLIENT_IACS = "iacs";
    private static final String CLIENT_CUID = "cuids";


    private String token;
    private String userName;
    private LangType langType;

    public Set<String> getShipIdsByUserRole(List<String> roleNames) {
        //根据用户id获取船公司id列表
        SysUser sysUserByUsername = sysUserRepository.findSysUserByUsername(this.getUserName());
        Set<String> shipIdsByShipCodes = null;
        List<String> gcidCompanyByUserId = sysUserClientMapRepository.findGcidCompanyByUserId(sysUserByUsername.getId());
        if (roleNames.contains(RoleConst.ADMIN_ROLE_NAME) || roleNames.contains(RoleConst.CSS_ROLE_NAME)) {
            return shipIdsByShipCodes;
        } else {
            shipIdsByShipCodes = new HashSet<>();
            if (roleNames.contains(RoleConst.SHIP_COMPANY_ROLE_NAME)) {
                shipIdsByShipCodes = getShipComPanyShipIds(gcidCompanyByUserId);
                if (shipIdsByShipCodes.size() == 0) {
                    shipIdsByShipCodes.add("");
                }
            } else if (roleNames.contains(RoleConst.CMSA_ROLE_NAME)) {
                Set<String> byClientIds = new HashSet<>();
                if (gcidCompanyByUserId.size() != 0) {
                    byClientIds = oiShipCmsaMapRepository.findByClientIds(gcidCompanyByUserId);
                } else {
                    byClientIds.add("");
                }
                shipIdsByShipCodes.addAll(byClientIds);
            } else {
                Set<String> byClientIds = new HashSet<>();
                byClientIds.add("");
                shipIdsByShipCodes.addAll(byClientIds);
            }
            return shipIdsByShipCodes;
        }
    }

    public BigDecimal getSailtime(OiShipVoyage oiShipVoyage) {
        String endportid = oiShipVoyage.getEndportid();
        RawVoyagePort endPort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
        long stopPortTime = 0;
        if (endPort != null) {
            Date deptTm = endPort.getDeptTm();
            Date arrTm = endPort.getArrTm();
            if (deptTm != null && arrTm != null) {
                stopPortTime = endPort.getDeptTm().getTime() - endPort.getArrTm().getTime();
            }
        }
        long startTime = 0;
        long endTime = 0;
        if (oiShipVoyage.getStartTime() != null && oiShipVoyage.getEndTime() != null) {
            startTime = oiShipVoyage.getStartTime().getTime();
            endTime = oiShipVoyage.getEndTime().getTime();
        }
        long h = 1000 * 60 * 60;
        BigDecimal sailTime = BigDecimal.valueOf((double) (endTime - startTime - stopPortTime) / h);
        BigDecimal stopTime = new BigDecimal(0);
        if (oiShipVoyage.getStopTime() != null) {
            stopTime = oiShipVoyage.getStopTime();
        }
        sailTime = sailTime.subtract(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
        return sailTime;
    }

    /**
     * 计算跨年航段的航行时间
     *
     * @param oiShipVoyage
     * @return
     */
    public BigDecimal getSailtimeAcross(OiShipVoyage oiShipVoyage) {
        String endportid = oiShipVoyage.getEndportid();
        RawVoyagePort endPort = rawVoyagePortRepository.findByIdAndIsDelete(endportid, 0);
        long time = oiShipVoyage.getEndTime().getTime();
        long stopPortTime = 0;
        if (endPort != null) {
            Date deptTm = endPort.getDeptTm();
            Date arrTm = endPort.getArrTm();
            if (time > arrTm.getTime() && time < deptTm.getTime() && arrTm != null) {
                stopPortTime = time - endPort.getArrTm().getTime();
            }
        }
        long startTime = 0;
        long endTime = 0;
        if (oiShipVoyage.getStartTime() != null && oiShipVoyage.getEndTime() != null) {
            startTime = oiShipVoyage.getStartTime().getTime();
            endTime = oiShipVoyage.getEndTime().getTime();
        }
        long h = 1000 * 60 * 60;
        BigDecimal sailTime = BigDecimal.valueOf((double) (endTime - startTime - stopPortTime) / h);
        BigDecimal stopTime = new BigDecimal(0);
        if (oiShipVoyage.getStopTime() != null) {
            stopTime = oiShipVoyage.getStopTime();
        }
        sailTime = sailTime.subtract(stopTime.divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
        return sailTime;
    }

    private Map<String, Set<String>> getCodeAndIacs(List<String> gcClientIds, Map<String, Set<String>> map, Set<String> codes, Set<String> iacss, Set<String> docCuids) {
        for (String gcClientId : gcClientIds
        ) {
            if (gcClientId == null) {
                continue;
            }
            GcClient gcClient = gcClientRepository.findById(gcClientId).get();
            codes.add(gcClient.getCode());
            iacss.add(gcClient.getIacs());
            docCuids.add(gcClient.getId());
            if (gcClient.getCGroup() != null) {
                if (0 == gcClient.getCGroup()) {
                    List<GcClient> children = gcClient.getChildren();
                    List<String> gcidsByGid = new ArrayList<>();
                    children.forEach(gcClientChiren -> {
                        gcClientIds.add(gcClientChiren.getId());
                    });
                    getCodeAndIacs(gcidsByGid, map, codes, iacss, docCuids);
                }
            }
        }
        map.put(CLIENT_CODE, codes);
        map.put(CLIENT_IACS, iacss);
        map.put(CLIENT_CUID, docCuids);
        return map;
    }

    public String getUserName() {

        Cookie cookie = WebUtils.getCookie(request, "token");
        if (cookie == null) {
            throw new ExplicitException("超时");
        }
        token = cookie.getValue();
        if (token != null) {
            userName = JwtUtils.getUserName(token);
            if (userName == null) {
                throw new ExplicitException(msg("login.timeout"));
            }
        }
        if (StringUtils.isNullOrEmpty(userName)) {
            throw new ExplicitException("服务层提取用户错误");
        }
        return userName;
    }

    /**
     * 获取当前登录人的用户id
     *
     * @return 返回有效值
     */
    public String getUserId() {
        String userName = this.getUserName();
        SysUser sysUser = sysUserRepository.findSysUserByUsername(userName);
        if (sysUser == null) {
            return null;
        } else {

            return sysUser.getId();
        }
    }


    /**
     * 获取当前登录人的信息
     *
     * @return 返回有效值
     */
    @Override
    public CurrentUserVo getCurrentUser() {
        String userName = this.getUserName();
        SysUser sysUser = sysUserRepository.findSysUserByUsername(userName);
        if (sysUser == null) {
            return null;
        } else {
            int userType = sysUser.getUserType();
            if (userType == 1) {
                CurrentUserVo currentUserVo = new CurrentUserVo();
                currentUserVo.setRoles(null);
                return currentUserVo;
            }
            return getCurrentUserByUser(sysUser);
        }
    }

    /**
     * 获取指定用户的信息
     *
     * @return 返回有效值
     */
    private CurrentUserVo getCurrentUserByUser(SysUser sysUser) {
        String userId = sysUser.getId();
        List<SysUserRole> sysUserRoles = sysUserRoleRepository.findSysUserRoleByUserId(userId);
        List<SysUserClientMap> sysUserCompanys = sysUserClientMapRepository.findSysUserCompanyByUserId(userId);
        List<String> companyCodes = new ArrayList<String>();
        List<String> companyIacses = new ArrayList<String>();
        for (SysUserClientMap item : sysUserCompanys) {
            companyCodes.add(item.getClientCode());
            companyIacses.add(item.getClientIacs());
        }
        HashSet h = new HashSet(companyIacses);
        companyIacses.clear();
        companyIacses.addAll(h);
        CurrentUserVo result = new CurrentUserVo();
        result.setCompanyCodes(companyCodes);
        result.setCompanyIacses(companyIacses);
        result.setId(sysUser.getId());
        result.setNameCn(sysUser.getNickname());
        result.setNameCn(sysUser.getNicknameEn());
        result.setRoles(sysUserRoles);
        result.setUserName(sysUser.getUsername());
        return result;
    }


    /***
     * 生成36位UUID
     * @return
     */
    public String UUID36() {
        return UUID.randomUUID().toString();
    }

    /***
     * 生成32位UUID
     * @return
     */
    public String UUID32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 根据语言及传入参数返回具体语言环境的值
     *
     * @param nameCn 中文
     * @param nameEn 英文
     * @return
     */
    public String getLocaleName(String nameCn, String nameEn) {
        if (getLangType() == LangType.zh_CN) {
            return (nameCn == null ? nameEn : nameCn);
        } else {
            return (nameEn == null ? nameCn : nameEn);
        }
    }

    /**
     * 获取国际化语言
     *
     * @return
     */
    private LangType getLangType() {
        Cookie cookie = WebUtils.getCookie(request, "token");
        if (cookie == null) {
            return this.langType;
        }
        this.token = cookie.getValue();
        if (this.token != null) {
            this.langType = JwtUtils.getLangType(this.token);
        }
        return this.langType;
    }


    /**
     * 获取国际化语言
     *
     * @return
     */
    private Locale getLocale() {
        this.langType = getLangType();
        if (this.langType == LangType.zh_CN) {
            return Locale.SIMPLIFIED_CHINESE;
        } else {
            return Locale.US;
        }
    }

    /**
     * 获取国际化信息
     *
     * @param key 键值
     * @return 返回有效值
     */
    public String msg(String key) {
        Locale locale = getLocale();

        String value = messageSource.getMessage(key, null, locale);
        return value;
    }

    /**
     * 获取国际化信息
     *
     * @param key  键值
     * @param args 参数
     * @return
     */
    public String msg(String key, Object[] args) {
        Locale locale = getLocale();
        String value = messageSource.getMessage(key, args, locale);
        return value;
    }

    public Set<String> getShipComPanyShipIds(List<String> gcidCompanyByUserId) {
        Set<String> shipIdsByShipCodes = new HashSet<>();
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> codes = new HashSet<>();
        Set<String> iacss = new HashSet<>();
        Set<String> cuids = new HashSet<>();
        Map<String, Set<String>> codeAndIacs = getCodeAndIacs(gcidCompanyByUserId, map, codes, iacss, cuids);
        List<String> clientCodes = new ArrayList<>(codeAndIacs.get(CLIENT_CODE));
        List<String> clientIacs = new ArrayList<>(codeAndIacs.get(CLIENT_IACS));
        List<String> clientCuids = new ArrayList<>(codeAndIacs.get(CLIENT_CUID));
        Set<String> shipIdsByShipIacs = new HashSet<>();
        Set<String> shipIdsByShipDocCuids = new HashSet<>();
        if (clientCodes.size() != 0) {
            shipIdsByShipCodes = oiShipInfoRepository.findShipIdsByShipCodes(clientCodes);
        } else {
            shipIdsByShipCodes.add("");
        }
        if (clientIacs.size() != 0) {
            shipIdsByShipIacs = oiShipInfoRepository.findShipIdsByShipIacs(clientIacs);
        }
        if (clientCuids.size() != 0) {
            shipIdsByShipDocCuids = oiShipInfoRepository.findShipIdsByShipCuids(clientCuids);
        }
        shipIdsByShipCodes.addAll(shipIdsByShipIacs);
        shipIdsByShipCodes.addAll(shipIdsByShipDocCuids);
        return shipIdsByShipCodes;
    }

    public void downLoadResponse(HttpServletResponse response, InputStream inputStream, XLSTransformer xlsTransformer, Map<String, Object> map) {
        org.apache.poi.ss.usermodel.Workbook sheets;
        try {
            sheets = xlsTransformer.transformXLS(inputStream, map);
        } catch (InvalidFormatException e) {
            log.error("不合理的报告模板");
            e.printStackTrace();
            throw new ExplicitException("不合理的报告模板");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downLoadCmsaResponse(HttpServletResponse response, InputStream inputStream, XLSTransformer xlsTransformer, Map<String, Object> map, Integer size) {
        org.apache.poi.ss.usermodel.Workbook sheets;
        try {
            sheets = xlsTransformer.transformXLS(inputStream, map);
            Sheet sheetAt = sheets.getSheetAt(0);
            int first = 50;
            int last = 52;
            for (int i = 3; i <= size * 3; i += 3) {
                int firstRow = first + i;
                int LastRow = last + i;
                sheetAt.addMergedRegion(new CellRangeAddress(firstRow, LastRow, 7, 7));
            }
        } catch (InvalidFormatException e) {
            log.error("不合理的报告模板");
            e.printStackTrace();
            throw new ExplicitException("不合理的报告模板");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, List<List<RawVoyagePort>>> getEuSegmentsSAndImoASegments(List<RawVoyagePort> rawVoyagePorts) {
        Map<String, List<List<RawVoyagePort>>> map = new HashMap<>();
        List<List<RawVoyagePort>> imoSements = new ArrayList<>();
        List<List<RawVoyagePort>> euSements = new ArrayList<>();
        int imoFromIndex = 0;
        int euFromIndex = 0;
//        for (int i = 0; i < rawVoyagePorts.size(); i++) {
//            RawVoyagePort rawVoyagePort = rawVoyagePorts.get(i);
//            if (rawVoyagePort.getRawVoyagePortloadings().size() < 2) {
//                throw new ExplicitException("无载荷信息");
//            }
//            BigDecimal cargoTonsZero = rawVoyagePort.getRawVoyagePortloadings().get(0).getCargoTons();
//            if (cargoTonsZero == null) {
//                cargoTonsZero = new BigDecimal(0);
//            }
//            BigDecimal cargoTonsOne = rawVoyagePort.getRawVoyagePortloadings().get(1).getCargoTons();
//            if (cargoTonsOne == null) {
//                cargoTonsOne = new BigDecimal(0);
//            }
//            Integer isEu = rawVoyagePort.getIsEu();
//            if (isEu == null) {
//                isEu = 0;
//            }
//            if (!(cargoTonsZero.compareTo(cargoTonsOne) == 0) && 1 == isEu) {
//                euFromIndex = i;
//                break;
//            }
//        }
        //拆分imo航段
        for (int i = 0; i < rawVoyagePorts.size(); i++) {
            RawVoyagePort rawVoyagePort = rawVoyagePorts.get(i);
//            if (rawVoyagePort.getRawVoyagePortloadings().size() < 2) {
//                throw new ExplicitException("无载荷信息");
//            }
            if ("0".equals(rawVoyagePort.getInPort())) {
                if (i > 0) {
                    List<RawVoyagePort> imorawVoyagePorts = rawVoyagePorts.subList(imoFromIndex, i + 1);
                    imoFromIndex = i;
                    imoSements.add(imorawVoyagePorts);
                    //判断是否是欧盟航段
//                    RawVoyagePort rawVoyagePortEuEnd = imorawVoyagePorts.get(imorawVoyagePorts.size() - 1);//欧盟结束港
//                    boolean euEndFlag = getEuFlag(rawVoyagePortEuEnd);
//                    RawVoyagePort rawVoyagePortStart = imorawVoyagePorts.get(0);//欧盟开始港
//                    boolean euStartFlag = getEuFlag(rawVoyagePortStart);
//                    if (euEndFlag || euStartFlag) {
//                        euSements.add(imorawVoyagePorts);
//                    }
                }
            }
        }
        //拆分欧盟航段
        //找出第一个装卸货的港口下标
        for (int i = 0; i < rawVoyagePorts.size(); i++) {
            RawVoyagePort rawVoyagePort = rawVoyagePorts.get(i);
            if (rawVoyagePort.getRawVoyagePortloadings().size() < 2) {
                continue;
            }
            BigDecimal cargoTonsZero = rawVoyagePort.getRawVoyagePortloadings().get(0).getCargoTons();
            if (cargoTonsZero == null) {
                cargoTonsZero = new BigDecimal(0);
            }
            BigDecimal cargoTonsOne = rawVoyagePort.getRawVoyagePortloadings().get(1).getCargoTons();
            if (cargoTonsOne == null) {
                cargoTonsOne = new BigDecimal(0);
            }
            if (cargoTonsOne.compareTo(cargoTonsZero) != 0) {
                euFromIndex = i;
                break;
            }
        }

        for (int i = 0; i < rawVoyagePorts.size(); i++) {
            RawVoyagePort rawVoyagePort = rawVoyagePorts.get(i);
            if (rawVoyagePort.getRawVoyagePortloadings().size() < 2) {
                continue;
            }
            BigDecimal cargoTonsZero = rawVoyagePort.getRawVoyagePortloadings().get(0).getCargoTons();
            if (cargoTonsZero == null) {
                cargoTonsZero = new BigDecimal(0);
            }
            BigDecimal cargoTonsOne = rawVoyagePort.getRawVoyagePortloadings().get(1).getCargoTons();
            if (cargoTonsOne == null) {
                cargoTonsOne = new BigDecimal(0);
            }
            boolean cargoFlag = cargoTonsOne.compareTo(cargoTonsZero) != 0;
            if (euFromIndex == i) {
                continue;
            }
            if ("0".equals(rawVoyagePort.getInPort()) && cargoFlag) {
                if (i > 0) {
                    List<RawVoyagePort> imorawVoyagePorts = rawVoyagePorts.subList(euFromIndex, i + 1);
                    euFromIndex = i;
                    //判断是否是欧盟航段
                    RawVoyagePort rawVoyagePortEuEnd = imorawVoyagePorts.get(imorawVoyagePorts.size() - 1);//欧盟结束港
                    boolean euEndFlag = getEuFlag(rawVoyagePortEuEnd);
                    RawVoyagePort rawVoyagePortStart = imorawVoyagePorts.get(0);//欧盟开始港
                    boolean euStartFlag = getEuFlag(rawVoyagePortStart);
                    if (euEndFlag || euStartFlag) {
                        euSements.add(imorawVoyagePorts);
                    }
                }
            }
        }
        map.put("EU", euSements);
        map.put("IMO", imoSements);
        return map;
    }

    /**
     * 获取港口能否达到生成欧盟航段的标准
     *
     * @param rawVoyagePort 港口信息
     * @return
     */
    private boolean getEuFlag(RawVoyagePort rawVoyagePort) {
        BigDecimal cargoTonsZero = rawVoyagePort.getRawVoyagePortloadings().get(0).getCargoTons();
        if (cargoTonsZero == null) {
            cargoTonsZero = new BigDecimal(0);
        }
        BigDecimal cargoTonsOne = rawVoyagePort.getRawVoyagePortloadings().get(1).getCargoTons();
        if (cargoTonsOne == null) {
            cargoTonsOne = new BigDecimal(0);
        }
        boolean isEuFlag = cargoTonsZero.compareTo(cargoTonsOne) != 0 && rawVoyagePort.getIsEu() == 1;
        return isEuFlag;
    }

    /**
     * 获取单个港口的加油量
     *
     * @param rawVoyageAddoils
     * @return
     */
    public CalculateOilVo getAddOil(List<RawVoyageAddoil> rawVoyageAddoils) {
        CalculateOilVo calculateOilVo = new CalculateOilVo();
        //加油信息
        BigDecimal addhfo = new BigDecimal(0);
        BigDecimal addlfo = new BigDecimal(0);
        BigDecimal addbingfo = new BigDecimal(0);
        BigDecimal adddingfo = new BigDecimal(0);
        BigDecimal addtianfo = new BigDecimal(0);
        BigDecimal addchaifo = new BigDecimal(0);
        BigDecimal addmethanolfo = new BigDecimal(0);
        BigDecimal addoiethanolfo = new BigDecimal(0);
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
        calculateOilVo.setOiHfo(addhfo);
        calculateOilVo.setOiLfo(addlfo);
        calculateOilVo.setOiBing(addbingfo);
        calculateOilVo.setOiDing(adddingfo);
        calculateOilVo.setOiTian(addtianfo);
        calculateOilVo.setOiChai(addchaifo);
        calculateOilVo.setOiOther(addmethanolfo);
        calculateOilVo.setOiethanol(addoiethanolfo);
        return calculateOilVo;
    }

    /**
     * 获取单个港口驳油量
     *
     * @param rawVoyageOutoils
     * @return
     */
    public CalculateOilVo getOutOil(List<RawVoyageOutoil> rawVoyageOutoils) {
        CalculateOilVo calculateOilVo = new CalculateOilVo();
        //航行驳出油信息
        BigDecimal outhfo = new BigDecimal(0);
        BigDecimal outlfo = new BigDecimal(0);
        BigDecimal outbingfo = new BigDecimal(0);
        BigDecimal outdingfo = new BigDecimal(0);
        BigDecimal outtianfo = new BigDecimal(0);
        BigDecimal outchaifo = new BigDecimal(0);
        BigDecimal outmethanolfo = new BigDecimal(0);
        BigDecimal outoiethanolfo = new BigDecimal(0);
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
        calculateOilVo.setOiHfo(outhfo);
        calculateOilVo.setOiLfo(outlfo);
        calculateOilVo.setOiBing(outbingfo);
        calculateOilVo.setOiDing(outdingfo);
        calculateOilVo.setOiTian(outtianfo);
        calculateOilVo.setOiChai(outchaifo);
        calculateOilVo.setOiOther(outmethanolfo);
        calculateOilVo.setOiethanol(outoiethanolfo);
        return calculateOilVo;
    }

    /**
     * 获取单个港口的修正量
     *
     * @param rawVoyagePortoils
     * @return
     */
    public CalculateOilVo getCorrectOil(List<RawVoyagePortoil> rawVoyagePortoils) {
        CalculateOilVo calculateOilVo = new CalculateOilVo();
        //航行修正量油信息
        BigDecimal correcthfo = new BigDecimal(0);
        BigDecimal correctlfo = new BigDecimal(0);
        BigDecimal correctbingfo = new BigDecimal(0);
        BigDecimal correctdingfo = new BigDecimal(0);
        BigDecimal correcttianfo = new BigDecimal(0);
        BigDecimal correctchaifo = new BigDecimal(0);
        BigDecimal correctmethanolfo = new BigDecimal(0);
        BigDecimal correctoiethanolfo = new BigDecimal(0);
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
        calculateOilVo.setOiHfo(correcthfo);
        calculateOilVo.setOiLfo(correctlfo);
        calculateOilVo.setOiBing(correctbingfo);
        calculateOilVo.setOiDing(correctdingfo);
        calculateOilVo.setOiTian(correcttianfo);
        calculateOilVo.setOiChai(correctchaifo);
        calculateOilVo.setOiOther(correctmethanolfo);
        calculateOilVo.setOiethanol(correctoiethanolfo);
        return calculateOilVo;
    }

    /**
     * 计算航次或航段的航行油耗开始港和结束港的油耗
     *
     * @param rawVoyagePortoils
     * @return
     */
    public Map<String, CalculateOilVo> calculateArrAndDept(List<RawVoyagePortoil> rawVoyagePortoils) {
        CalculateOilVo arrOil = new CalculateOilVo();
        CalculateOilVo endOil = new CalculateOilVo();
        HashMap<String, CalculateOilVo> map = new HashMap<>();
        BigDecimal arrHfo = new BigDecimal(0);
        BigDecimal arrLfo = new BigDecimal(0);
        BigDecimal deptHfo = new BigDecimal(0);
        BigDecimal deptLfo = new BigDecimal(0);
        if (rawVoyagePortoils != null) {
            for (int i = 0; i < rawVoyagePortoils.size(); i++) {
                RawVoyagePortoil rawVoyagePortoil = rawVoyagePortoils.get(i);
                String oilId = rawVoyagePortoil.getOilId();
                BigDecimal deptTons = rawVoyagePortoil.getDeptTons();
                BigDecimal arrTons = rawVoyagePortoil.getArrTons();
                BigDecimal correctTons = rawVoyagePortoil.getCorrectTons();
                if (correctTons == null) {
                    correctTons = BigDecimal.ZERO;
                }
                if (deptTons == null) {
                    deptTons = BigDecimal.ZERO;
                }
                if (arrTons == null) {
                    arrTons = BigDecimal.ZERO;
                }
                if (Arrays.asList(FuelConst.HFO_CODE.split(",")).contains(oilId)) {
                    arrHfo = arrHfo.add(arrTons);
                    arrHfo = arrHfo.add(correctTons);
                    deptHfo = deptHfo.add(deptTons);
                    arrOil.setOiHfo(deptHfo);
                    endOil.setOiHfo(arrHfo);
                }
                if (Arrays.asList(FuelConst.LFO_CODE.split(",")).contains(oilId)) {
                    arrLfo = arrLfo.add(arrTons);
                    arrLfo = arrLfo.add(correctTons);
                    deptLfo = deptLfo.add(deptTons);
                    arrOil.setOiLfo(deptLfo);
                    endOil.setOiLfo(arrLfo);
                }
                if (FuelConst.CHAI_CODE.equals(oilId)) {
                    arrOil.setOiChai(deptTons);
                    endOil.setOiChai(arrTons.add(correctTons));
                }
                if (FuelConst.BING_CODE.equals(oilId)) {
                    arrOil.setOiBing(deptTons);
                    endOil.setOiBing(arrTons.add(correctTons));
                }
                if (FuelConst.DING_CODE.equals(oilId)) {
                    arrOil.setOiDing(deptTons);
                    endOil.setOiDing(arrTons.add(correctTons));
                }
                if (FuelConst.TIAN_CODE.equals(rawVoyagePortoil.getOilId())) {
                    arrOil.setOiTian(deptTons);
                    endOil.setOiTian(arrTons.add(correctTons));
                }
                if (FuelConst.METHAN_CODE.equals(oilId)) {
                    arrOil.setOiOther(deptTons);
                    endOil.setOiOther(arrTons.add(correctTons));
                }
                if (FuelConst.OIETHAN_CODE.equals(oilId)) {
                    arrOil.setOiethanol(deptTons);
                    endOil.setOiethanol(arrTons.add(correctTons));
                }
            }
        }
        map.put("arr", arrOil);
        map.put("end", endOil);
        return map;
    }
}
