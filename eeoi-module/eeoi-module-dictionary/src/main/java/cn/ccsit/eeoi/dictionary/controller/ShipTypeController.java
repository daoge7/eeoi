package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.ShipType;
import cn.ccsit.eeoi.dictionary.service.ShipTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/shipType")
public class ShipTypeController extends CommonController {

    @Autowired
    ShipTypeService shipTypeService;

    @GetMapping("/deleteShipTypeById/{id}")
    public ResultVo deleteShipTypeById(@PathVariable String id) {
        return shipTypeService.deleteShipTypeById(id);
    }

    @PostMapping("/saveOrUpdateShipType")
    public ResultVo saveOrUpdateShipType(@RequestBody ShipType shipType) {
        return shipTypeService.saveOrUpdateShipType(shipType);
    }

    @GetMapping("/findShipTypeList")
    public ResultVo findShipTypeList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "csptype", required = false) String name, @RequestParam(value = "euSptype", required = false) String code) {
        return shipTypeService.findAllShipTypesByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findShipTypeById/{id}")
    public ResultVo findShipTypeList(@PathVariable String id) {
        return shipTypeService.findShipTypeById(id);
    }

}