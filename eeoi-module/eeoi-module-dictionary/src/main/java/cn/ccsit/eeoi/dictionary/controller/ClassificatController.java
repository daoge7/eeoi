package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.Classificat;
import cn.ccsit.eeoi.dictionary.service.ClassificatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/classificat")
public class ClassificatController extends CommonController {

    @Autowired
    ClassificatService classificatService;

    @GetMapping("/deleteClassificatById/{id}")
    public ResultVo deleteClassificatById(@PathVariable String id) {
        return classificatService.deleteClassificatById(id);
    }

    @PostMapping("/saveOrUpdateClassificat")
    public ResultVo saveOrUpdateClassificat(@RequestBody Classificat classificat) {
        return classificatService.saveOrUpdateClassificat(classificat);
    }

    @GetMapping("/findClassificatList")
    public ResultVo findClassificatList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "abbrn", required = false) String name, @RequestParam(value = "code", required = false) String code) {
        return classificatService.findAllClassificatsByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findClassificatById/{id}")
    public ResultVo findClassificatList(@PathVariable String id) {
        return classificatService.findClassificatById(id);
    }
}