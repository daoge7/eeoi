package cn.ccsit.eeoi.ship.controller;

import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.ship.entity.FlagDocChange;
import cn.ccsit.eeoi.ship.service.FlagDocChangeService;
import cn.ccsit.eeoi.ship.service.GcClientService;
import cn.ccsit.eeoi.ship.vo.GcClientParentSettingVo;
import cn.ccsit.eeoi.ship.vo.GcClientShowListVo;
import cn.ccsit.eeoi.ship.vo.GcClientVo;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flagDocChange")
public class FlagDocChangeController extends CommonController {

    @Autowired
    private FlagDocChangeService flagDocChangeService;

    @PostMapping("/saveFlagDocChange")
    public ResultVo save(@RequestBody FlagDocChange flagDocChange) {
        return flagDocChangeService.save(flagDocChange);
    }

    @GetMapping("/findOiShipInfoByImoNo/{imoNo}")
    public ResultVo findOiShipInfoByImoNo(@PathVariable String imoNo) {
        return flagDocChangeService.findOiShipInfoByImoNo(imoNo);
    }

    @GetMapping("/deleteFlagDocChangeById/{id}")
    public ResultVo deleteFlagDocChangeById(@PathVariable String id) {
        return flagDocChangeService.deleteFlagDocChangeById(id);
    }

    @GetMapping("/findFlagDocChangeById/{id}")
    public ResultVo findFlagDocChangeById(@PathVariable String id) {
        return flagDocChangeService.findFlagDocChangeById(id);
    }

    @GetMapping("/findAllFlagDocChangeOrderByEffectiveDateDesc")
    public ResultVo findAllFlagDocChangeOrderByEffectiveDateDesc() {
        return flagDocChangeService.findAllFlagDocChangeOrderByEffectiveDateDesc();
    }

    @PostMapping("/deleteMultiFlagDocChangeById")
    public ResultVo deleteMultiFlagDocChangeById(@RequestBody String[] ids) {
        return flagDocChangeService.deleteMultiFlagDocChangeById(ids);
    }

    @GetMapping("/findAllByshipIdOrderByEffectiveDateDesc/{id}")
    public ResultVo findAllByshipIdOrderByEffectiveDateDesc(@PathVariable String id) {
        return flagDocChangeService.findAllByshipIdOrderByEffectiveDateDesc(id,"0");
    }

    @GetMapping("/flatChangeLatest/{id}")
    public ResultVo flatChangeLatest(@PathVariable String id) {
        ServiceResultVo ret = (ServiceResultVo) flagDocChangeService.findAllByshipIdOrderByEffectiveDateDesc(id, "0");
        List<FlagDocChange> data = (List<FlagDocChange>) ret.getData();
        if(data == null) return new ServiceResultVo(true,null);
        ChangeVo vo = new ChangeVo();
        if(data != null && !CollectionUtils.isEmpty(data)){
            List<FlagDocChange> latestFlagChangeList = data.stream().filter(item -> {
                return item.getFlagThreeCodeNew() != null;
            }).sorted(Comparator.comparing(FlagDocChange::getOpdate).reversed())
            .collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(latestFlagChangeList)){
                FlagDocChange latestFlagChange = latestFlagChangeList.get(0);// 获取最新的
                vo.setFlagThreeCodeNew(latestFlagChange.getFlagThreeCodeNew());
                vo.setFlagThreeCodeOld(latestFlagChange.getFlagThreeCodeOld());
            }

            List<FlagDocChange> latestDocChangeList = data.stream().filter(item -> {
                return item.getDocNew() != null;
            }).sorted(Comparator.comparing(FlagDocChange::getOpdate).reversed())
            .collect(Collectors.toList());// 获取最新的
            if(!CollectionUtils.isEmpty(latestDocChangeList)){
                FlagDocChange latestDocChange = latestDocChangeList.get(0);
                vo.setDocNew(latestDocChange.getDocNew());
                vo.setDocOld(latestDocChange.getDocOld());

            }
            return new ServiceResultVo(true, vo);
        }
        return new ServiceResultVo(true,null);
    }

    @Data
    class ChangeVo{
        private String flagThreeCodeOld;
        private String flagThreeCodeNew;
        private String docOld;
        private String docNew;
    }

}
