package cn.ccsit.eeoi.energyeefic.controller;

import cn.ccsit.common.vo.PageDataVo;
import cn.ccsit.common.vo.ResultErrorVo;
import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.energyeefic.dto.ImoDcsRptManagerDto;
import cn.ccsit.eeoi.energyeefic.entity.PortArrDeptInfo;
import cn.ccsit.eeoi.energyeefic.service.MrvDataServiceImp;
import cn.ccsit.eeoi.energyeefic.service.PortArrDeptServiceImp;
import cn.ccsit.eeoi.energyeefic.vo.ImoDcsRptManagerVo;
import cn.ccsit.eeoi.energyeefic.webService.ImoReportClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 网络端航次到港统计信息
 */
@RequestMapping("/portArrDept")
@RestController
@Slf4j
public class PorArrDeptController {

    @Autowired
    PortArrDeptServiceImp portArrDeptServiceImp;

    @GetMapping("/getPortInfo")
    public ResultVo getPortArrDept(
            @RequestParam(value = "startTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(value = "endTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime endTime,
           @RequestParam(value = "shipId",required = true) String shipId){

        List<PortArrDeptInfo> retList = portArrDeptServiceImp.getPortArrDept(startTime, endTime, shipId);
        ResultVo resultVo = new ResultObjectVo<>(retList);
        return resultVo;
    }

    @GetMapping("/bigdecimal")
    public List<BigDecimal> getBig(){
        ArrayList<BigDecimal> retList = new ArrayList<>();
        retList.add(new BigDecimal(0));
        retList.add(new BigDecimal(0.000));
        retList.add(new BigDecimal(0).setScale(4,BigDecimal.ROUND_HALF_UP));
        return retList;
    }


    /**
     * 要报送DCS报告了，规则：
     * 1、锁定状态的、报告周期为2020年内的；
     * 2、除中国旗、liberia旗以外船旗的需要报送；
     * 3、 每报告报送成功后要有一个log文件记录下来，就是要记录哪份报告什么时候报送成功了
     * @return
     */
    private final int pageSize = 4000; // 用一页数据，获取所有，避免翻页可能的重复

    @Autowired
    MrvDataServiceImp mrvDataServiceImp;

    @Autowired
    ImoReportClient client;

    private final String[] exceptNation = {"Republic of Liberia","People's Republic of China"};

    @GetMapping("/sendBatch")
    public String sendImoBatch(
            @RequestParam(value = "startTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(value = "endTime",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime endTime,
            @RequestParam(value = "exceptionNations",required = false)String[] exceptionNations
    ) throws ParseException {
        ImoDcsRptManagerDto queryDto = new ImoDcsRptManagerDto();
        int firstPage = 1;
        queryDto.setCurrentPage(firstPage);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        if(startTime == null){
            startDate  = sdf.parse("2019-12-31");
        }else {
            startDate =Date.from(  startTime.atZone(ZoneId.systemDefault()).toInstant() );
        }
        if(startTime == null){
            endDate  = sdf.parse("2021-01-01");
        }else {
            endDate =Date.from(  endTime.atZone(ZoneId.systemDefault()).toInstant() );
        }
        queryDto.setStartTime(startDate);
        queryDto.setEndTime(endDate);
        PageDataVo<ImoDcsRptManagerVo> pageDataVo = sendBatch(firstPage, queryDto);
        queryDto.setPageSize(pageSize);
        int total = pageDataVo.getTotal();
        int loop = (int)Math.ceil( (double)total / pageSize );

        for(int index = 2; index < loop ; index++){
            // todo 以后用线程池优化发送
               queryDto.setCurrentPage(index);
               sendBatch(index, queryDto);
        }

        return "send over";
    }

    final String rptStatus = "2"; // 锁定状态

    private  PageDataVo<ImoDcsRptManagerVo> sendBatch(int currentPage,ImoDcsRptManagerDto queryDto ){
        queryDto.setCurrentPage(currentPage);
        queryDto.setPageSize(pageSize);// 每页20条
        queryDto.setImoRptStatus(rptStatus); // 设定锁定状态
        ResultObjectVo<PageDataVo<ImoDcsRptManagerVo>> resultVo =
                (ResultObjectVo<PageDataVo<ImoDcsRptManagerVo>>)mrvDataServiceImp.dcsPortManager(queryDto);
        PageDataVo<ImoDcsRptManagerVo> pageDataVo = (PageDataVo<ImoDcsRptManagerVo> ) resultVo.getData();
        List<ImoDcsRptManagerVo> items = pageDataVo.getItems();
        Set<String> exceptSet = Arrays.stream(exceptNation).collect(Collectors.toSet());
        List<ImoDcsRptManagerVo> useCollect = items.stream().filter(item -> {
            boolean is_contain = exceptSet.contains(item.getFlag());
            return !is_contain;
        }).collect(Collectors.toList());

        for(ImoDcsRptManagerVo vo : useCollect){
            String id = vo.getId();
            String[] ids = {id};
            try {
                // 复用上传
                ResultVo report = mrvDataServiceImp.imoReport(ids);
                if(report.isResult()){
                    String success = (String) report.getData();
                    // 上传成功记录
                    log.warn("imo send report on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "|" + vo.getImoNu() + "|" + success);
                }else{
                    String fail = (String) report.getData();
                    // 上传失败记录
                    log.warn("imo send report on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "|" + vo.getImoNu() + "|" + fail);
                }
            }catch (Exception e){
                // 内部异常获取，避免循环打断
                log.error(vo.getImoNu() + "inner exception :" + e);
            }

        }

        return pageDataVo;
    }
}
