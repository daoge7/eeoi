package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.GcState;
import cn.ccsit.eeoi.dictionary.service.GcStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/gcState")
public class GcStateController extends CommonController {

    @Autowired
    GcStateService gcStateService;

    @GetMapping("/deleteGcStateById/{id}")
    public ResultVo deleteGcStateById(@PathVariable String id) {
        return gcStateService.deleteGcStateById(id);
    }

    @PostMapping("/saveOrUpdateGcState")
    public ResultVo saveOrUpdateGcState(@RequestBody GcState gcState) {
        return gcStateService.saveOrUpdateGcState(gcState);
    }

    @GetMapping("/findGcStateList")
    public ResultVo findGcStateList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "cnName", required = false) String name, @RequestParam(value = "enName", required = false) String code) {
        return gcStateService.findAllGcCitiesByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findGcStateById/{id}")
    public ResultVo findGcStateList(@PathVariable String id) {
        return gcStateService.findGcStateById(id);
    }

    @GetMapping("/findAllGcStatesEnBrief")
    public ResultVo findAllGcStatesEnBrief() {
        return gcStateService.findAllGcStatesEnBrief();
    }

    @GetMapping("/findCnBriefName")
    public ResultVo findCnBriefName(@RequestParam(value = "enBrief", required = true) String enBrief) {
        return gcStateService.findTopCnBriefByEnBrief(enBrief);
    }

    @GetMapping("/findEnBriefName")
    public ResultVo findEnBriefName(@RequestParam(value = "cnBrief", required = true) String cnBrief) {
        return gcStateService.findTopEnbriefByCnBrief(cnBrief);
    }
}