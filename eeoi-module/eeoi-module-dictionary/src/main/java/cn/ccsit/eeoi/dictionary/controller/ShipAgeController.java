package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.DicGrossTon;
import cn.ccsit.eeoi.dictionary.entity.ShipAge;
import cn.ccsit.eeoi.dictionary.service.DicGrossTonService;
import cn.ccsit.eeoi.dictionary.service.ShipAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/shipAge")
public class ShipAgeController extends CommonController {

    @Autowired
    ShipAgeService shipAgeService;

    @GetMapping("/deleteShipAgeById/{id}")
    public ResultVo deleteShipAgeById(@PathVariable String id) {
        return shipAgeService.deleteShipAgeById(id);
    }

    @PostMapping("/saveOrUpdateShipAge")
    public ResultVo saveOrUpdateShipAge(@RequestBody ShipAge shipAge) {
        return shipAgeService.saveOrUpdateShipAge(shipAge);
    }

    @GetMapping("/findShipAgeList")
    public ResultVo findShipAgeList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "tonName", required = false) String name, @RequestParam(value = "tonCode", required = false) String code) {
        return shipAgeService.findAllShipAgesByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findShipAgeById/{id}")
    public ResultVo findShipAgeList(@PathVariable String id) {
        return shipAgeService.findShipAgeById(id);
    }
}