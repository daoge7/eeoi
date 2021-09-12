package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.Evaluation;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface EvaluationService extends CommonService {

    public ResultVo deleteEvaluationById(String id);

    public ResultVo saveOrUpdateEvaluation(Evaluation evaluation);

    public ResultVo findAllEvaluationsByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findEvaluationById(String id);

}
