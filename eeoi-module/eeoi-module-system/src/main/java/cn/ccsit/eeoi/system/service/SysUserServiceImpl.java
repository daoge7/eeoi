package cn.ccsit.eeoi.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import cn.ccsit.eeoi.ship.repository.OiShipInfoRepository;
import cn.ccsit.eeoi.system.vo.*;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.ccsit.common.security.DigestSecurityUtils;
import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultStringVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.repository.GcClientRepository;
import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.entity.SysUser;
import cn.ccsit.eeoi.system.entity.SysUserClientMap;
import cn.ccsit.eeoi.system.entity.SysUserRole;
import cn.ccsit.eeoi.system.repository.SysUserClientMapRepository;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
import cn.ccsit.eeoi.system.repository.SysUserRoleRepository;

@Service("sysUserService")
public class SysUserServiceImpl extends CommonServiceImpl implements SysUserService, CommonService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysUserClientMapRepository sysUserClientMapRepository;

    @Autowired
    private GcClientRepository gcClientRepository;

    @Autowired
    private OiShipInfoRepository oiShipInfoRepository;

    /***
     * 获取查询列表
     */
    @SuppressWarnings("unused")
    @Override
    public ResultPageVo getSysUsersByVo(SysUserVo sysUserVo) {
        Page<SysUser> filePage = null;
        //根据角色id，查询和角色有关的用户id
        String roleId = sysUserVo.getRoleId();
        String jpql = "select c from SysUser c where c.isDelete=0 ";
        if (sysUserVo == null) {
            PageRequest pageable = PageRequest.of(0, 15);
            jpql += "order by upper(c.username) ";
            filePage = commonRepository.findPagedDataByJPQLCriteria(SysUser.class, jpql, pageable);
        } else {
            PageRequest pageable = PageRequest.of(sysUserVo.getCurrentPage() - 1, sysUserVo.getPageSize());
            // 用户名/姓名
            if (StringUtils.isNotEmpty(sysUserVo.getAccount())) {
                sysUserVo.setAccount(sysUserVo.getAccount().toUpperCase());// 转换成大写
                sysUserVo.setAccount(sysUserVo.getAccount().replace(" ", ""));// 前中后去空格
                jpql += " and (replace(upper(c.nickname),' ','' ) like '%" + sysUserVo.getAccount()
                        + "%' or replace(upper(c.nicknameEn), ' ','') like '%" + sysUserVo.getAccount()
                        + "%' or replace(upper(c.username),' ','') like '%" + sysUserVo.getAccount() + "%')";
            }
            // 状态
            if (sysUserVo.getUserstatus() != null) {
                jpql += " and c.status=" + sysUserVo.getUserstatus();
            }

            if (StringUtils.isNotEmpty(roleId)) {
//                List<String> sysUserIdsByRoleId = sysUserRoleRepository.findSysUserIdsByRoleId(roleId);
                String jpqlGc = "(SELECT sur.userId FROM SysUserRole sur,SysRole sr WHERE sur.roleId = sr.id AND sr.id ="+roleId+")";
                jpql += " and c.id in " + jpqlGc;
            }

            if (StringUtils.isNotEmpty(sysUserVo.getCompanyName())) {

                String jpqlGc = "(select a.userId from SysUserClientMap a, GcClient b where a.gcid = b.id  ";
                sysUserVo.setCompanyName(sysUserVo.getCompanyName().toUpperCase());// 转换成大写
                sysUserVo.setCompanyName(sysUserVo.getCompanyName().replace(" ", ""));// 前中后去空格
                jpqlGc += " and (replace(upper(b.nameEn),' ','' ) like '%" + sysUserVo.getCompanyName()
                        + "%' or replace(upper(b.nameCn),' ','') like '%" + sysUserVo.getCompanyName() + "%'))";
                jpql += " and c.id in " + jpqlGc;
            }

            jpql += " order by upper(c.username) asc ";

            filePage = commonRepository.findPagedDataByJPQLCriteria(SysUser.class, jpql, pageable);
        }

        List<SysUserShowListVo> resultList = new ArrayList<SysUserShowListVo>();


        for (SysUser item : filePage) {
            SysUserShowListVo model = new SysUserShowListVo(item);
            resultList.add(model);
        }


        ResultPageVo data = new ResultPageVo(filePage.getTotalElements(), resultList);
        return data;
    }

    /***
     * 保存
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying(clearAutomatically = true)
    public ServiceResultVo saveSysUser(SysUserShowVo user) {
        if (user == null) {
            String msg = this.msg("user.save.fail");
            return new ServiceResultVo(false, msg);
        }
        SysUser sysUser = sysUserRepository.findSysUserById(user.getId());
        if (sysUser == null) {
            SysUserShowVo userSameAccount = getSysUserByAccount(user.getAccount());
            if (userSameAccount != null) {
                String msg = this.msg("user.username.exist");
                return new ServiceResultVo(false, msg);
            }
            sysUser = new SysUser();
            sysUser.setId(java.util.UUID.randomUUID().toString());
            sysUser.setCreateTm(new Date());
            String newSalt = randomNum();
            String newPasswordInput = getDefaultPassword(newSalt);
            sysUser.setSalt(newSalt);
            sysUser.setPassword(newPasswordInput);
        }
        sysUser.setIsDelete(0);
        sysUser.setStatus(user.getUserStatus());
        sysUser.setUsername(user.getAccount());
        sysUser.setNickname(user.getNameCn());
        sysUser.setNicknameEn(user.getNameEn());
        sysUser.setPhone(user.getPhone());
        sysUser.setEmail(user.getEmail());
        sysUser.setRemark(user.getRemark());
        sysUser.setOpdate(new Date());
        List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
        // 循环遍历生成用户对应角色,先删除数据库原有对应角色
        List<SysUserRole> sysUserRoles = sysUserRoleRepository.findSysUserRoleByUserId(sysUser.getId());
        sysUserRoleRepository.deleteAll(sysUserRoles);
        List<String> roleIds = user.getUserRole();
        for (String roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setIsDelete(0);
            sysUserRole.setId(java.util.UUID.randomUUID().toString());
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(roleId);
            sysUserRole.setCreateTm(new Date());
            sysUserRole.setOpdate(new Date());
            userRoleList.add(sysUserRole);
        }
        sysUserRoleRepository.saveAll(userRoleList);

        List<SysUserClientMap> userCompanyList = new ArrayList<SysUserClientMap>();
        // 循环遍历生成用户对应船公司，,先删除数据库原有对应船公司
        List<SysUserClientMap> sysUserCompanys = sysUserClientMapRepository.findSysUserCompanyByUserId(sysUser.getId());
        sysUserClientMapRepository.deleteAll(sysUserCompanys);

        List<String> companyIds = user.getCompanyIds();
        for (String companyId : companyIds) {
            GcClient gcClient = gcClientRepository.findGcClientById(companyId);
            if (gcClient == null) {
                continue;
            }
            SysUserClientMap sysUserClientMap = new SysUserClientMap();
            sysUserClientMap.setClientCode(gcClient.getCode());
            sysUserClientMap.setClientIacs(gcClient.getIacs());
            sysUserClientMap.setIsDelete(0);
            sysUserClientMap.setId(java.util.UUID.randomUUID().toString());
            sysUserClientMap.setUserId(sysUser.getId());
            sysUserClientMap.setGcid(companyId);
            sysUserClientMap.setCreateTm(new Date());
            sysUserClientMap.setOpdate(new Date());
            userCompanyList.add(sysUserClientMap);
        }
        sysUserClientMapRepository.saveAll(userCompanyList);

        sysUser = sysUserRepository.save(sysUser);
        if (sysUser != null) {
            return new ServiceResultVo(true, sysUser.getId());
        } else {
            String msg = this.msg("user.save.fail");
            return new ServiceResultVo(false, msg);
        }
    }

    /***
     * 根据id删除用户
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServiceResultVo deleteSysUser(String id) {
        SysUser sysUser = sysUserRepository.findSysUserById(id);
        if (sysUser == null) {
            String msg = this.msg("user.delete.success");
            return new ServiceResultVo(true, msg);
        }
        // 删除数据库原有对应角色
        List<SysUserRole> sysUserRoles = sysUserRoleRepository.findSysUserRoleByUserId(sysUser.getId());
        sysUserRoleRepository.deleteAll(sysUserRoles);
        // 删除数据库原有对应船公司
        List<SysUserClientMap> sysUserCompanys = sysUserClientMapRepository.findSysUserCompanyByUserId(sysUser.getId());
        sysUserClientMapRepository.deleteAll(sysUserCompanys);
        sysUser.setIsDelete(1);
        sysUser.setOpdate(new Date());
        sysUser = sysUserRepository.save(sysUser);
        if (sysUser != null) {
            String msg = this.msg("user.delete.success");
            return new ServiceResultVo(true, msg);
        } else {
            String msg = this.msg("user.delete.fail");
            return new ServiceResultVo(false, msg);
        }
    }

    /***
     * 根据id查找用户
     */
    @Override
    public SysUserShowVo getSysUserById(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            return null;
        }
        SysUser sysUser = sysUserRepository.findSysUserById(id);
        if (sysUser == null) {
            return null;
        }
        SysUserShowVo showUser = new SysUserShowVo(sysUser);
        List<String> roleIds = new ArrayList<String>();
        List<String> roleIdNames = new ArrayList<String>();
        Set<SysRole> roles = sysUser.getUserRoles();
        for (SysRole item : roles) {
            roleIds.add(item.getId());
            roleIdNames.add(item.getTitle());
        }
        showUser.setUserRoleName(String.join(",", roleIdNames));
        showUser.setUserRole(roleIds);
        Set<GcClient> gcClients = sysUser.getCompanys();
        List<UserGcClientVo> userGcClients = new ArrayList<UserGcClientVo>();
        for (GcClient item : gcClients) {
            UserGcClientVo model = new UserGcClientVo();
            model.setCompanyId(item.getId());
            model.setCompanyCode(item.getCode());
            model.setIacsCode(item.getIacs());
            model.setNameCn(item.getNameCn());
            model.setNameEn(item.getNameEn());
            userGcClients.add(model);
        }

        showUser.setCompanyList(userGcClients);
        return showUser;
    }

    /***
     * 根据登录名查找用户
     */
    @Override
    public SysUserShowVo getSysUserByAccount(String account) {
        if (StringUtils.isNullOrEmpty(account)) {
            return null;
        }
        SysUser sysUser = sysUserRepository.findSysUserByUsername(account);
        if (sysUser == null) {
            return null;
        }
        SysUserShowVo showUser = new SysUserShowVo(sysUser);
        List<String> roleIds = new ArrayList<String>();
        List<String> roleIdNames = new ArrayList<String>();
        Set<SysRole> roles = sysUser.getUserRoles();
        for (SysRole item : roles) {
            roleIds.add(item.getId());
            roleIdNames.add(item.getTitle());
        }
        showUser.setUserRoleName(String.join(",", roleIdNames));
        showUser.setUserRole(roleIds);
        Set<GcClient> gcClients = sysUser.getCompanys();
        List<UserGcClientVo> userGcClients = new ArrayList<UserGcClientVo>();
        for (GcClient item : gcClients) {

            UserGcClientVo model = new UserGcClientVo();
            model.setCompanyId(item.getId());
            model.setCompanyCode(item.getCode());
            model.setIacsCode(item.getIacs());
            model.setNameCn(item.getNameCn());
            model.setNameEn(item.getNameEn());
            userGcClients.add(model);
        }
        showUser.setCompanyList(userGcClients);
        return showUser;
    }

    /***
     * 根据id删除用户
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServiceResultVo deleteUserGcClient(List<String> ids) {

        if (ids.size() == 0) {
            String msg = this.msg("user.delete.success");
            return new ServiceResultVo(true, msg);
        }

        String jpql = "select c from SysUserClientMap c where c.isDelete=0 ";
        String idStrs = String.join("','", ids);
        jpql += " AND c.id in ('" + idStrs + "')";

        List<SysUserClientMap> results = commonRepository.findAllByJPQLCriteria(SysUserClientMap.class, jpql);

        for (SysUserClientMap item : results) {
            item.setIsDelete(1);
            item.setOpdate(new Date());
        }
        Iterable<SysUserClientMap> items = sysUserClientMapRepository.saveAll(results);
        if (items != null) {
            String msg = this.msg("user.delete.success");
            return new ServiceResultVo(true, msg);
        } else {
            String msg = this.msg("user.delete.fail");
            return new ServiceResultVo(true, msg);
        }
    }

    public ServiceResultVo updateUserPassword(SysUserPasswordVo parameter) {
        String userId = this.getUserId();
        if (userId == null) {
            String msg = this.msg("user.password.fail");
            return new ServiceResultVo(false, msg);
        }
        SysUser sysUser = sysUserRepository.findSysUserById(userId);
        if (sysUser == null) {
            String msg = this.msg("user.password.fail");
            return new ServiceResultVo(false, msg);
        }
        String newPassword = parameter.getNewPassword();
//        boolean checkNewPassword = isLetterDigit(newPassword); // 前端校验，后端不在校验格式20200708
//        if (!checkNewPassword) { // 不配那么不通过，返回异常！
//            String msg = this.msg("user.password.checkfail");
//            return new ServiceResultVo(false, msg);
//        }
        String oldPasswordDb = sysUser.getPassword();
        String oldSalt = sysUser.getSalt();
        String oldPasswordPlaintext = parameter.getOldPassword();
        String oldPasswordInput = DigestSecurityUtils.sha256Digest(oldPasswordPlaintext, oldSalt);
        if (!oldPasswordDb.equals(oldPasswordInput)) {
            String msg = this.msg("user.password.oldnotcorrect");
            return new ServiceResultVo(false, msg);
        }
        String newSalt = randomNum();
        String newPasswordInput = DigestSecurityUtils.sha256Digest(newPassword, newSalt);
        sysUser.setSalt(newSalt);
        sysUser.setPassword(newPasswordInput);
        sysUser.setOpdate(new Date());
        sysUser = sysUserRepository.save(sysUser);
        if (sysUser != null) {
            String msg = this.msg("user.password.success");
            return new ServiceResultVo(true, msg);
        } else {
            String msg = this.msg("user.password.fail");
            return new ServiceResultVo(false, msg);
        }
    }

    /**
     * 密码由字母数字特殊符号组成，长度在8到12位。 是否包含
     *
     * @param str
     * @return
     */
    private boolean isLetterDigit(String str) {
//        String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,12}$";
//        boolean isRight = str.matches(PW_PATTERN);
        boolean isRight = newCheck(str);
        return isRight;
    }


    private  boolean newCheck(String str) {  String PW_PATTERN_1 = "[0-9].*";
        String PW_PATTERN_2 = "[a-z].*";
        String PW_PATTERN_3 = "[A-Z].*";
        String PW_PATTERN_4 = "[!@#$%^&*()_].*";
        String PW_PATTERN_EEOR = "[^0-9a-zA-z!@#$%^&*()_].*";
        int sum = 0;
        boolean find_error = Pattern.compile(PW_PATTERN_EEOR).matcher(str).find();
        if(find_error){return false;}
        boolean find_1 = Pattern.compile(PW_PATTERN_1).matcher(str).find();
        boolean find_2 = Pattern.compile(PW_PATTERN_2).matcher(str).find();
        boolean find_3 = Pattern.compile(PW_PATTERN_3).matcher(str).find();
        boolean find_4 = Pattern.compile(PW_PATTERN_4).matcher(str).find();
        if(find_1){sum = sum + 1;}
        if(find_2){sum = sum + 1;}
        if(find_3){sum = sum + 1;}
        if(find_4){sum = sum + 1;}
        if(sum >= 3){
            return true;
        }else {
            return false;
        }
    }


    private String randomNum() {
        return DigestSecurityUtils.getSalt(6);
    }

    public ServiceResultVo resetUserPassword(SysUserPasswordVo parameter) {
        String userId = parameter.getUserId();
        if (userId == null) {
            String msg = this.msg("user.reset.fail");
            return new ServiceResultVo(false, msg);
        }
        SysUser sysUser = sysUserRepository.findSysUserById(userId);
        if (sysUser == null) {
            String msg = this.msg("user.reset.fail");
            return new ServiceResultVo(false, msg);
        }
        String newSalt = randomNum();
        String newPasswordInput = getDefaultPassword(newSalt);
        sysUser.setSalt(newSalt);
        sysUser.setPassword(newPasswordInput);
        sysUser.setOpdate(new Date());
        sysUser = sysUserRepository.save(sysUser);
        if (sysUser != null) {
            String msg = this.msg("user.reset.success");
            return new ServiceResultVo(true, msg);
        } else {
            String msg = this.msg("user.reset.fail");
            return new ServiceResultVo(false, msg);
        }
    }

    @Override
    public ServiceResultVo saveAppUser(AppUserVo appUserVo) {
        SysUser sysUser = sysUserRepository.findSysUserByUsername(appUserVo.getUserName());
        if (sysUser != null) {
            sysUser.setNickname(appUserVo.getCnName());
            sysUser.setNicknameEn(appUserVo.getEnName());
            sysUser.setEmail(appUserVo.getEmail());
            sysUser.setPhone(appUserVo.getPhoneNumber());
            sysUser.setStatus(appUserVo.getUserStatus());
            sysUser.setRemark(appUserVo.getRemark());
            sysUser.setUserType(appUserVo.getUserStatus());
        } else {
            sysUser = new SysUser();
            sysUser.setId(java.util.UUID.randomUUID().toString());
            sysUser.setUsername(appUserVo.getUserName());
//        sysUser.setPassword(getDefaultPassword(appUserVo.getImoNo() + (int) (Math.random() * 900) + 100));
            String newSalt = randomNum();
            sysUser.setPassword(getDefaultPassword(appUserVo.getPassword(), newSalt));
            sysUser.setNickname(appUserVo.getCnName());
            sysUser.setNicknameEn(appUserVo.getEnName());
            sysUser.setEmail(appUserVo.getEmail());
            sysUser.setPhone(appUserVo.getPhoneNumber());
            sysUser.setStatus(appUserVo.getUserStatus());
            sysUser.setRemark(appUserVo.getRemark());
            sysUser.setUserType(appUserVo.getUserStatus());
            sysUser.setSalt(newSalt);
        }

        sysUser = sysUserRepository.save(sysUser);
        OiShipInfo oiShipInfo = oiShipInfoRepository.findById(appUserVo.getShipId()).get();
        oiShipInfo.setAppUser(sysUser.getId());
        oiShipInfoRepository.save(oiShipInfo);
        if (sysUser != null && oiShipInfoRepository != null) {
            String msg = this.msg("user.reset.success");
            return new ServiceResultVo(true, msg);
        } else {
            String msg = this.msg("user.reset.fail");
            return new ServiceResultVo(false, msg);
        }
    }

    @Override
    public ResultVo findAppUserByShipId(String shipId) {
        OiShipInfo oiShipInfo = oiShipInfoRepository.findById(shipId).get();
        SysUser sysUser = sysUserRepository.findSysUserById(oiShipInfo.getAppUser());
        AppUserVo appUserVo = new AppUserVo();
        if (sysUser == null) {
            appUserVo.setUserName(oiShipInfo.getImono() != null ? oiShipInfo.getImono() : oiShipInfo.getRegisterno());
            appUserVo.setStatus("1");
            appUserVo.setUserStatus(1);
            appUserVo.setShipId(shipId);
        } else {
            appUserVo.setStatus("2");
            appUserVo.setUserName(oiShipInfo.getImono() != null ? oiShipInfo.getImono() : oiShipInfo.getRegisterno());
            appUserVo.setCnName(sysUser.getNickname());
            appUserVo.setEnName(sysUser.getNicknameEn());
            appUserVo.setPhoneNumber(sysUser.getPhone());
            appUserVo.setEmail(sysUser.getEmail());
            appUserVo.setRemark(sysUser.getRemark());
            appUserVo.setUserStatus(sysUser.getStatus());
            appUserVo.setShipId(shipId);
        }
        return new ResultObjectVo<AppUserVo>(appUserVo);
    }

    private String getDefaultPassword(String newSalt) {
        String newPassword = "CCSeasy711";
        String newPasswordInput = DigestSecurityUtils.sha256Digest(newPassword, newSalt);
        return newPasswordInput;
    }

    private String getDefaultPassword(String password, String newSalt) {
//        String newPassword = "CCSeasy711";
        String newPasswordInput = DigestSecurityUtils.sha256Digest(password, newSalt);
        return newPasswordInput;
    }

    /*
     * String enCryptPassword=DigestSecurityUtils.sha256Digest(rawPassword, salt);
     */
}
