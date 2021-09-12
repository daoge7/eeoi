package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.Fuel;
import cn.ccsit.eeoi.dictionary.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/fuel")
public class FuelController extends CommonController {

    @Autowired
    FuelService fuelService;

    @GetMapping("/deleteFuelById/{id}")
    public ResultVo deleteFuelById(@PathVariable String id) {
        return fuelService.deleteFuelById(id);
    }

    @PostMapping("/saveOrUpdateFuel")
    public ResultVo saveOrUpdateFuel(@RequestBody Fuel fuel) {
        return fuelService.saveOrUpdateFuel(fuel);
    }

    @GetMapping("/findFuelList")
    public ResultVo findFuelList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "fuelName", required = false) String name, @RequestParam(value = "fuelCode", required = false) String code) {
        return fuelService.findAllFuelsByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findFuelById/{id}")
    public ResultVo findFuelList(@PathVariable String id) {
        return fuelService.findFuelById(id);
    }
}