package cn.ccsit.eeoi.system.controller;

import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.system.entity.SysDocNotice;
import cn.ccsit.eeoi.system.service.LogService;
import cn.ccsit.eeoi.system.service.SysDocNoticeService;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/log")
public class LogController extends CommonController {

    @Autowired
    LogService logService;

    @Value("${log.logPath}")
    String logPath;

    @GetMapping("/findAllLogFiles")
    public ResultVo findAllLogFiles(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) {
        return logService.findAllLogFiles(logPath,pageNum,pageSize);
    }
}