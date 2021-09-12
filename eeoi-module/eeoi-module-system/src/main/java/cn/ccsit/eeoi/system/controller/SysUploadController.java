package cn.ccsit.eeoi.system.controller;

import cn.ccsit.common.security.CipherSecurityUtils;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.system.utils.FtpProperties;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.SocketException;

@RestController
@RequestMapping("/sysUpload")
public class SysUploadController extends CommonController {

    @Autowired
    FtpProperties ftpProperties;

    @Autowired
    FtpUtils ftpUtils;

    private static Logger logger = LogManager.getLogger(CipherSecurityUtils.class);

    // fileId 貌似是前端生成的，
    @RequestMapping("/upload")
    public String upload(@RequestParam(value="fileId",required=false) String fileId, @RequestParam("file") MultipartFile file) throws SocketException, IOException {
      return ftpUtils.upload2Local(fileId,file);
    }
}
