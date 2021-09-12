package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.ShipSub;
import cn.ccsit.eeoi.dictionary.service.ShipSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/shipSub")
public class ShipSubController extends CommonController {

    @Autowired
    ShipSubService shipSubService;

    @GetMapping("/deleteShipSubById/{id}")
    public ResultVo deleteShipSubById(@PathVariable String id) {
        return shipSubService.deleteShipSubById(id);
    }

    @PostMapping("/saveOrUpdateShipSub")
    public ResultVo saveOrUpdateShipSub(@RequestBody ShipSub shipSub) {
        return shipSubService.saveOrUpdateShipSub(shipSub);
    }

    @GetMapping("/findShipSubList")
    public ResultVo findShipSubList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "subName", required = false) String name, @RequestParam(value = "subCode", required = false) String code) {
        return shipSubService.findAllShipSubsByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findShipSubById/{id}")
    public ResultVo findShipSubList(@PathVariable String id) {
        return shipSubService.findShipSubById(id);
    }

    @GetMapping("/findShipSubByShipType/{id}")
    public ResultVo findShipSubByShipType(@PathVariable String id) {
        return shipSubService.findShipSubByShipType(id);
    }
}