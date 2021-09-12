package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.GcCity;
import cn.ccsit.eeoi.dictionary.service.GcCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/gcCity")
public class GcCityController extends CommonController {

    @Autowired
    GcCityService gcCityService;

    @GetMapping("/deleteGcCityById/{id}")
    public ResultVo deleteGcCityById(@PathVariable String id) {
        return gcCityService.deleteGcCityById(id);
    }

    @PostMapping("/saveOrUpdateGcCity")
    public ResultVo saveOrUpdateGcCity(@RequestBody GcCity gcCity) {
        return gcCityService.saveOrUpdateGcCity(gcCity);
    }

    @GetMapping("/findGcCityList")
    public ResultVo findGcCityList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "enName", required = false) String name, @RequestParam(value = "code", required = false) String code, @RequestParam(value = "cnName", required = false) String cnName) {
        return gcCityService.findAllGcCitiesByPage(pageSize, pageNum, code, name, cnName);
    }

    @GetMapping("/findGcCityListForApp")
    public ResultVo findGcCityListForApp(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "enName", required = false) String name, @RequestParam(value = "code", required = false) String code, @RequestParam(value = "cnName", required = false) String cnName) {
        return gcCityService.findAllGcCitiesByPage(pageSize, pageNum, code, name, cnName);
    }

    @GetMapping("/findGcCityById/{id}")
    public ResultVo findGcCityList(@PathVariable String id) {
        return gcCityService.findGcCityById(id);
    }
}