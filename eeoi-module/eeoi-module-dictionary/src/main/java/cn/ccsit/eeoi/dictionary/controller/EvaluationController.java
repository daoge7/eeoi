package cn.ccsit.eeoi.dictionary.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.dictionary.entity.Evaluation;
import cn.ccsit.eeoi.dictionary.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:49
 */

@RestController
@RequestMapping("/evaluation")
public class EvaluationController extends CommonController {

    @Autowired
    EvaluationService evaluationService;

    @GetMapping("/deleteEvaluationById/{id}")
    public ResultVo deleteEvaluationById(@PathVariable String id) {
        return evaluationService.deleteEvaluationById(id);
    }

    @PostMapping("/saveOrUpdateEvaluation")
    public ResultVo saveOrUpdateEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationService.saveOrUpdateEvaluation(evaluation);
    }

    @GetMapping("/findEvaluationList")
    public ResultVo findEvaluationList(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "tonName", required = false) String name, @RequestParam(value = "tonCode", required = false) String code) {
        return evaluationService.findAllEvaluationsByPage(pageSize, pageNum, code, name);
    }

    @GetMapping("/findEvaluationById/{id}")
    public ResultVo findEvaluationList(@PathVariable String id) {
        return evaluationService.findEvaluationById(id);
    }
}