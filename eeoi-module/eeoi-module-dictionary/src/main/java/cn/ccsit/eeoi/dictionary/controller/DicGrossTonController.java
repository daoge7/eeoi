package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.DicGrossTon;
import cn.ccsit.eeoi.dictionary.service.DicGrossTonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/dicGrossTon")
public class DicGrossTonController extends CommonController {

    @Autowired
    DicGrossTonService dicGrossTonService;

    @GetMapping("/deleteDicGrossTonById/{id}")
    public ResultVo deleteDicGrossTonById(@PathVariable String id) {
        return dicGrossTonService.deleteDicGrossTonById(id);
    }

    @PostMapping("/saveOrUpdateDicGrossTon")
    public ResultVo saveOrUpdateDicGrossTon(@RequestBody DicGrossTon dicGrossTon) {
        return dicGrossTonService.saveOrUpdateDicGrossTon(dicGrossTon);
    }

    @GetMapping("/findDicGrossTonList")
    public ResultVo findDicGrossTonList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "tonName", required = false) String name, @RequestParam(value = "tonCode", required = false) String code) {
        return dicGrossTonService.findAllDicGrossTonsByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findDicGrossTonById/{id}")
    public ResultVo findDicGrossTonList(@PathVariable String id) {
        return dicGrossTonService.findDicGrossTonById(id);
    }
}