package cn.ccsit.eeoi.portal.controller;

import cn.ccsit.common.enums.language.LangType;
import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.security.DigestSecurityUtils;
import cn.ccsit.common.utils.HttpRequestUtil;
import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.common.utils.JwtUtils;
import cn.ccsit.eeoi.portal.service.PermissionService;
import cn.ccsit.eeoi.portal.vo.LoginVo;
import cn.ccsit.eeoi.portal.vo.LoginVoCrm;
import cn.ccsit.eeoi.portal.vo.UserVo;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import cn.ccsit.eeoi.ship.repository.GcClientRepository;
import cn.ccsit.eeoi.ship.repository.OiShipInfoRepository;
import cn.ccsit.eeoi.ship.service.OiShipInfoService;
import cn.ccsit.eeoi.system.entity.*;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.ccsit.eeoi.system.repository.SysUserClientMapRepository;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
import cn.ccsit.eeoi.system.repository.SysUserRoleRepository;
import cn.ccsit.eeoi.system.service.SysUserService;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import cn.ccsit.eeoi.system.vo.SysUserShowVo;
import cn.ccsit.eeoi.system.vo.UserGcClientVo;
import cn.hutool.core.util.IdUtil;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.gson.Gson;
import com.wf.captcha.ArithmeticCaptcha;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
public class HomeController extends CommonController {

    private static Logger logger = LogManager.getLogger(HomeController.class);
    public static final String CRM_UTL = "https://www.ccs-service.net/ws/userInfoService/getUserInfo";
    public static final String SHIP_COMPANY = "船公司用户";
    public static final String ZHU_GUAN = "主管机关用户";

    public HomeController() {

    }

    @Autowired
    private PermissionService permissionService;

    @Autowired
    @Lazy
    private JwtUtils jwtUtils;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private OiShipInfoRepository oiShipInfoRepository;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;
    @Autowired
    private SysUserClientMapRepository sysUserClientMapRepository;
    @Autowired
    private GcClientRepository gcClientRepository;

    // @Autowired
    // @Lazy
    private JCacheCacheManager jCacheCacheManager;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:/index.html");
        /// return "index";
    }

    @PostMapping("/login")
    @Transactional
    public ResultVo login(@RequestBody(required = false) LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        if (loginVo != null) {
            this.langType = LangType.setLangType(loginVo.getI18n());
        }
        String verifyCode = (String) this.httpSession.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isNullOrEmpty(loginVo.getVerifycode()) || StringUtils.isNullOrEmpty(verifyCode)
                || (!loginVo.getVerifycode().equals(verifyCode))) {
            String msg = this.msg("code.error");
            return new ResultErrorVo(msg);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getUsername(), loginVo.getPassword());
        try {
            subject.login(token);
            SysUser sysUser = permissionService.findSysUserByUsername(token.getUsername());
            String jwtToken = jwtUtils.sign(sysUser.getUsername(), "pc", this.langType);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Authorization", jwtToken);
            UserVo userVo = new UserVo();

            List<Map<String, Object>> pageVo = permissionService.findSysMenuByUserId(sysUser.getId(), this.langType);
            userVo.setAllowPage(pageVo);
            Set<String> buttonVo = permissionService.getPermissions(sysUser.getId());
            userVo.setAllowButton(buttonVo);
            userVo.setUsername(sysUser.getUsername());
            userVo.setName(sysUser.getNickname());
            userVo.setToken(jwtToken);
            userVo.setUserId(sysUser.getId());
            userVo.setSysRoles(sysUser.getUserRoles());
            return new ResultObjectVo<UserVo>(userVo);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ExplicitException("Login error");
        }
    }
// todo
    @PostMapping("/loginCrm")
    @Transactional
    public ResultVo loginCrm(@RequestBody(required = false) LoginVoCrm loginVoCrm, HttpServletRequest request, HttpServletResponse response) {

        if (loginVoCrm != null) {
            this.langType = LangType.setLangType(loginVoCrm.getI18n());
        }
        String tokenCrm = loginVoCrm.getToken();
        String uid = loginVoCrm.getUid();
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String tokenEeoi = Base64.encodeBase64String(curDate.getBytes());
        if (!tokenCrm.equals(tokenEeoi)) {
            logger.error("crm token校验失败");
            throw new ExplicitException("Login error");
        }
        String csmUrl = CRM_UTL;
        String p = HttpRequestUtil.sendPost(csmUrl, "uid=" + uid + "&type=5");
        Gson gson = new Gson();
        CrmUserInfo crmUserInfo = gson.fromJson(p, CrmUserInfo.class);
        SysUser sysUserSave = sysUserRepository.findSysUserByUsername(crmUserInfo.getFUserName());
        String salt = DigestSecurityUtils.getSalt(6);
        String newPassword = "CCSeasy711";
        String newPasswordInput = DigestSecurityUtils.sha256Digest(newPassword, salt);
        String name = null;
        String fUserType = crmUserInfo.getFUserType();

        if ("1".equals(fUserType)) {

            name = SHIP_COMPANY;

        } else if ("10".equals(fUserType)) {

            name = ZHU_GUAN;

        }

        SysRole sysRole = null;
        if (sysUserSave == null) {
            sysRole = sysRoleRepository.findByNameAndIsDelete(name, 0);

            sysUserSave = new SysUser();

            sysUserSave.setEmail(crmUserInfo.getFEMail());

            sysUserSave.setNickname(crmUserInfo.getFLinkMan());

            sysUserSave.setUsername(crmUserInfo.getFUserName());

            sysUserSave.setPassword(newPasswordInput);

            sysUserSave.setSalt(salt);

            sysUserSave.setPhone(crmUserInfo.getFTelePhone());

            sysUserSave.setStatus(1);

            sysUserSave.setIsDelete(0);

            sysUserSave.setUserType(0);

            sysUserSave.setLastTime(new Date());
            sysUserSave = sysUserRepository.save(sysUserSave);
            //保存用户和角色关系

            saveUserRole(crmUserInfo, sysRole, sysUserSave.getId());

        } else {
            String fid = sysUserSave.getId();//crm用户id
            sysRole = sysRoleRepository.findByNameAndIsDelete(name, 0);
            List<SysUserRole> sysUserRoles = sysUserRoleRepository.findSysUserRoleByUserId(fid);
            if (sysUserRoles == null || sysUserRoles.size() == 0) {
                //保存用户和角色关系
                saveUserRole(crmUserInfo, sysRole, fid);

            } else {
                List<String> roleIds = new ArrayList<>();
                for (SysUserRole sysUserRole : sysUserRoles) {
                    roleIds.add(sysUserRole.getRoleId());
                }
                if (!roleIds.contains(sysRole.getId())) {

                    //保存用户和角色关系
                    saveUserRole(crmUserInfo, sysRole, fid);

                }

            }
            sysUserSave.setEmail(crmUserInfo.getFEMail());

            sysUserSave.setNickname(crmUserInfo.getFLinkMan());

            sysUserSave.setUsername(crmUserInfo.getFUserName());

            sysUserSave.setPhone(crmUserInfo.getFTelePhone());

            sysUserSave.setLastTime(new Date());

            sysUserSave = sysUserRepository.save(sysUserSave);
        }
        String fid = sysUserSave.getId();
        //保存船子公司
        List<CrmRelaCompanise> relaCompanise = crmUserInfo.getRelaCompanise();
        for (int i = 0; i < relaCompanise.size(); i++) {
            CrmRelaCompanise crmRelaCompanise = relaCompanise.get(i);
            //查询有效公司
            String clientCode = crmRelaCompanise.getCLIENT_CODE();
            String cuid = crmRelaCompanise.getCUID();
            List<GcClient> gcClientList = gcClientRepository.findByCuidAndCode(clientCode);
            GcClient gcClient = null;
            if(gcClientList != null && gcClientList.size()>0){
                gcClient = gcClientList.get(0);
            }
            if (gcClient == null) {
                gcClient = new GcClient();
                gcClient.setDelete(false);
                gcClient.setAddrCn(crmUserInfo.getFAddress());
                gcClient.setCode(crmRelaCompanise.getCLIENT_CODE());
                gcClient.setIacs(crmRelaCompanise.getIACS_CODE());
                gcClient.setNameCn(crmRelaCompanise.getCLIENT_NAME_CN());
                gcClient.setNameEn(crmRelaCompanise.getCLIENT_NAME_EN());
                gcClient.setCUid(crmRelaCompanise.getCUID());
                gcClient.setContact(crmRelaCompanise.getCONTACT_NAME());
                gcClient.setCreateTm(new Date());
                gcClient.setOpuser(crmUserInfo.getFUserName());
                GcClient gcClientSave = gcClientRepository.save(gcClient);
                SysUserClientMap sysUserClientMap = new SysUserClientMap();
                sysUserClientMap.setIsDelete(0);
                sysUserClientMap.setClientCode(gcClientSave.getCode());
                sysUserClientMap.setClientIacs(gcClientSave.getIacs());
                sysUserClientMap.setGcid(gcClientSave.getId());
                sysUserClientMap.setUserId(fid);
                sysUserClientMap.setOpuser(crmUserInfo.getFUserName());
                sysUserClientMapRepository.save(sysUserClientMap);
            } else {
                String gcClientId = gcClient.getId();
                //查询有效的映射关系
                List<SysUserClientMap> sysUserClientMapList = sysUserClientMapRepository.findSysUserCompanyByUserIdAndRoleId(fid, gcClientId);
                SysUserClientMap sysUserClientMap = null;
                if(sysUserClientMapList != null && sysUserClientMapList.size() > 0 ){
                    sysUserClientMap = sysUserClientMapList.get(0);
                }
                if (sysUserClientMap == null) {
                    sysUserClientMap = new SysUserClientMap();
                    sysUserClientMap.setIsDelete(0);
                    sysUserClientMap.setClientCode(gcClient.getCode());
                    sysUserClientMap.setClientIacs(gcClient.getIacs());
                    sysUserClientMap.setGcid(gcClientId);
                    sysUserClientMap.setUserId(fid);
                    sysUserClientMap.setOpuser(crmUserInfo.getFUserName());
                    sysUserClientMapRepository.save(sysUserClientMap);
                }
            }


        }
        try {
            SysUser sysUser = permissionService.findSysUserByUsername(sysUserSave.getUsername());

            String jwtToken = jwtUtils.sign(sysUser.getUsername(), "pc", this.langType);

            response.setHeader("Access-Control-Expose-Headers", "Authorization");

            response.setHeader("Authorization", jwtToken);
            UserVo userVo = new UserVo();


            List<Map<String, Object>> pageVo = permissionService.findSysMenuByUserId(sysUser.getId(), this.langType);

            userVo.setAllowPage(pageVo);

            Set<String> buttonVo = permissionService.getPermissions(sysUser.getId());

            userVo.setAllowButton(buttonVo);

            userVo.setUsername(sysUser.getUsername());

            userVo.setName(sysUser.getNickname());

            userVo.setToken(jwtToken);

            userVo.setUserId(sysUser.getId());

            userVo.setSysRoles(sysUser.getUserRoles());

            logger.info("" + crmUserInfo.getFUserName() + new Date().toString());

            return new ResultObjectVo<UserVo>(userVo);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ExplicitException("Login error");
        }
    }

    private void saveUserRole(CrmUserInfo crmUserInfo, SysRole sysRole, String fid) {
        SysUserRole sysUserRole = new SysUserRole();

        sysUserRole.setIsDelete(0);

        sysUserRole.setRoleId(sysRole.getId());

        sysUserRole.setUserId(fid);

        sysUserRole.setOpdate(new Date());

        sysUserRole.setOpuser(crmUserInfo.getFUserName());

        sysUserRole.setCreateTm(new Date());

        sysUserRoleRepository.save(sysUserRole);
    }


    @PostMapping("/alogin")
    @Transactional
    public ResultVo alogin(@RequestBody(required = false) LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        if (loginVo != null) {
            this.langType = LangType.setLangType(loginVo.getI18n());
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getUsername(), loginVo.getPassword());
        UserVo userVo = new UserVo();
        try {
            subject.login(token);
            SysUser sysUser = permissionService.findSysUserByUsername(token.getUsername());
            if (sysUser == null) {
                userVo.setMessage("用户名或密码不存在");
                return new ResultErrorObjectVo<>(userVo);
            }

            String jwtToken = jwtUtils.sign(sysUser.getUsername(), "app", this.langType);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Authorization", jwtToken);
            OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(loginVo.getUsername(), loginVo.getUsername(), 0);
            userVo.setShipId(oiShipInfo.getId());
            userVo.setUsername(sysUser.getUsername());
            userVo.setName(sysUser.getNickname());
            userVo.setShipName(oiShipInfo.getSpnameCn());
            userVo.setPhone(sysUser.getPhone());
            userVo.setToken(jwtToken);
            userVo.setUserId(sysUser.getId());
            userVo.setSysRoles(sysUser.getUserRoles());
            return new ResultObjectVo<UserVo>(userVo);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            userVo.setMessage("登录失败");
            return new ResultErrorObjectVo<>(userVo);
        }
    }
    @GetMapping(value = "/logout")
    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        Map<String, Object> outResult = new HashMap<String, Object>(2) {{
            put("code", "50000");
            put("msg", "JWT timeout");
        }};
        return outResult;
    }

    @GetMapping(value = "/code")
    public Map<String, Object> getCode(HttpServletRequest request, HttpServletResponse response) {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        this.httpSession.setAttribute(Constants.KAPTCHA_SESSION_KEY, result);
        String uuid = super.httpSession.getId();
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return imgResult;
    }

    @GetMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {

        byte[] captchaJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String createText = captchaProducer.createText();
            this.httpSession.setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
            BufferedImage bufferdImage = captchaProducer.createImage(createText);
            ImageIO.write(bufferdImage, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();

    }

    @RequiresPermissions("valid")
    @GetMapping("/valid")
    public ResultVo Valid() {
        return new ResultStringVo("测试");
    }

    @GetMapping(value = "downattach", produces = "application/octet-stream; charset=UTF-8")
    public ResponseEntity<byte[]> downAttachment(String id) throws Exception {

        InputStream is = null;

        File file = new File("d:/a.xlsx");
        is = new FileInputStream(file);

        return this.buildResponseEntity(is, "测试报表.xlsx", true);
        //return this.buildDefaultDownFile("file not found");

    }

//    private LoginVoCrm checkCrm(String token, String uid) {
////token加密验证
//        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//
//        if (!token.equals(sourceToken)) {
//            loginForm.setMsg("TOKEN验证失败，拒绝登录！");
//            loginForm.setPwd(null);
//            loginForm.setUpw(null);
//            forwardString = "login";
//            return mapping.findForward(forwardString);
//        }
//        return null;
//    }
}
