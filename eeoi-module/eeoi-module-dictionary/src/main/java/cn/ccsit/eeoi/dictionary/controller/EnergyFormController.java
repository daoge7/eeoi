package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.EnergyForm;
import cn.ccsit.eeoi.dictionary.service.EnergyFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/energyForm")
public class EnergyFormController extends CommonController {

    @Autowired
    EnergyFormService energyFormService;

    @GetMapping("/deleteEnergyFormById/{id}")
    public ResultVo deleteEnergyFormById(@PathVariable String id) {
        return energyFormService.deleteEnergyFormById(id);
    }

    @PostMapping("/saveOrUpdateEnergyForm")
    public ResultVo saveOrUpdateEnergyForm(@RequestBody EnergyForm energyForm) {
        return energyFormService.saveOrUpdateEnergyForm(energyForm);
    }

    @GetMapping("/findEnergyFormList")
    public ResultVo findEnergyFormList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "formName", required = false) String name, @RequestParam(value = "formCode", required = false) String code) {
        return energyFormService.findAllEnergyFormsByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findEnergyFormById/{id}")
    public ResultVo findEnergyFormList(@PathVariable String id) {
        return energyFormService.findEnergyFormById(id);
    }
}