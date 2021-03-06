package cn.ccsit.eeoi.common.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import cn.ccsit.common.utils.WebUtilsPro;
import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.common.vo.ResultVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.jose4j.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cn.ccsit.common.enums.language.LangType;
import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.vo.CurrentUserVo;
import cn.ccsit.eeoi.data.repository.CommonRepository;
import cn.ccsit.eeoi.system.repository.SysUserClientMapRepository;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
import cn.ccsit.eeoi.system.repository.SysUserRoleRepository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;


public class CommonController {

    private static Logger logger = LogManager.getLogger(CommonController.class);

    public CommonController() {

    }

    @Autowired
    private CommonService commonService;

    @Autowired
    private HttpServletRequest request;

    //@Autowired
    //private MessageSource  messageSource;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession httpSession;

    //protected String token;
    protected LangType langType = LangType.zh_CN;


    /*
     * @ModelAttribute public void getRequestAndResponse(HttpServletRequest request,
     * HttpServletResponse response) { this.httpRequest = request; this.httpResponse
     * = response; Cookie cookie = WebUtils.getCookie((HttpServletRequest)
     * request,"token"); if (cookie==null) { return ; } token=cookie.getValue(); }
     */

    public String getUserName() {
        return commonService.getUserName();
    }

    /**
     * ??????????????????????????????
     *
     * @return ???????????????
     */
    public CurrentUserVo getCurrentUser() {
        return commonService.getCurrentUser();
    }


    /***
     * ??????36???UUID
     *
     * @return
     */
    public String UUID36() {
        return commonService.UUID36();
    }

    /***
     * ??????32???UUID
     *
     * @return
     */
    public String UUID32() {
        return commonService.UUID32();
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param nameCn ??????
     * @param nameEn ??????
     * @return
     */
    public String getLocaleName(String nameCn, String nameEn) {
        return commonService.getLocaleName(nameCn, nameEn);
    }

    /**
     * ?????????????????????
     *
     * @param key ??????
     * @return ???????????????
     */
    public String msg(String key) {
        return commonService.msg(key);
    }

    /**
     * ?????????????????????
     *
     * @param key  ??????
     * @param args ??????
     * @return
     */
    public String msg(String key, Object[] args) {
        return commonService.msg(key, args);
    }

    /**
     * ????????????????????????
     *
     * @param realFileName    ?????????????????????
     * @param displayFileName ???????????????????????????
     * @return ?????????
     * @throws Exception
     */
    public ResponseEntity<byte[]> buildResponseEntity(String realFileName, String displayFileName) throws Exception {
        InputStream is = null;
        try {
            if (StringUtils.isNullOrEmpty(realFileName)) {
                logger.error("?????????????????????:fileName is null");
                throw new Exception(this.msg("common.download.filename.empty"));
            }
            File file = new File(realFileName);
            if (!file.exists()) {
                logger.error("???????????????:" + realFileName);
                throw new Exception(this.msg("common.download.filename.noexist"));
            }
            byte[] body = null;
            is = new FileInputStream(file);
            body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/octet-stream"));
            headers.setContentLength(file.length());

            String agent = (String) this.request.getHeader("USER-AGENT");
            if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
                headers.add("Content-Disposition", "attachment;filename*=" + URLEncoder.encode(displayFileName, "UTF-8"));
            } else {
                headers.add("content-disposition", "attachment;filename=" + URLEncoder.encode(displayFileName, "UTF-8"));
            }
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
            return entity;
        } catch (Exception ex) {
            logger.error("??????????????????:" + ex.getMessage());
            throw new Exception(this.msg("common.download.failure"));
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {

                }
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param is              ?????????
     * @param displayFileName ???????????????????????????
     * @param closeFlag       ????????????,true ????????????????????????fase ??????????????????
     * @return ?????????
     * @throws Exception
     */
    public ResponseEntity<byte[]> buildResponseEntity(InputStream is, String displayFileName, boolean closeFlag) throws Exception {

        try {

            if (is == null) {

                throw new Exception(this.msg("common.download.failure"));
            }

            byte[] body = null;
            ;
            body = new byte[is.available()];
            is.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/octet-stream"));

            headers.setContentLength(body.length);

            String agent = (String) this.request.getHeader("USER-AGENT");
            if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
                headers.add("Content-Disposition", "attachment;filename*=" + URLEncoder.encode(displayFileName, "UTF-8"));
            } else {
                headers.add("content-disposition", "attachment;filename=" + URLEncoder.encode(displayFileName, "UTF-8"));
            }
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
            return entity;
        } catch (Exception ex) {
            logger.error("??????????????????:" + ex.getMessage());
            throw new Exception(this.msg("common.download.failure"));
        } finally {
            if (closeFlag) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception ex) {

                    }
                }
            }
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????Error.txt
     *
     * @param errorInfo ??????????????????????????????
     * @return
     */
    public ResponseEntity<byte[]> buildDefaultDownFile(String errorInfo) {
        String defaultErrorFileName = "Error.txt";
        try {
            byte[] body = null;
            if (StringUtils.isNullOrEmpty(errorInfo)) {
                errorInfo = "????????????";
            }
            body = errorInfo.getBytes("UTF-8");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/octet-stream"));
            headers.setContentLength(body.length);
            headers.add("content-disposition", "attachment;filename=" + URLEncoder.encode(defaultErrorFileName, "UTF-8"));
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
            return entity;
        } catch (Exception ex) {
            logger.error("??????????????????:" + ex.getMessage());
            throw new RuntimeException(this.msg("common.download.failure"));
        }
    }

    @GetMapping("/getYears")
    public ResultVo getYears() {
        List<String> years = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        for (int i = year; i >= 2010; i--) {
            years.add(String.valueOf(i));
        }
        return new ResultObjectVo<>(years);
    }
}

