package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.Evaluation;
import cn.ccsit.eeoi.dictionary.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:54
 */
@Service("evaluationService")
public class EvaluationServiceImpl extends CommonServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public ResultVo deleteEvaluationById(String id) {
        Evaluation evaluation = evaluationRepository.getOne(id);
        evaluation.setIsDelete("1");
        Evaluation returnEntity = evaluationRepository.save(evaluation);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateEvaluation(Evaluation evaluation) {
        evaluation.setIsDelete("0");
        Evaluation returnEntity = evaluationRepository.save(evaluation);
        return new ResultObjectVo<Evaluation>(returnEntity);
    }

    @Override
    public ResultVo findAllEvaluationsByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<Evaluation> specification = new Specification<Evaluation>() {
            @Override
            public Predicate toPredicate(Root<Evaluation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null && !"".equals(name)) {
                    Path<Object> name1 = root.get("tonName");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + name.toLowerCase() + "%"));
                }
                if (code != null && !"".equals(code)) {
                    Path<Object> ccsCode1 = root.get("tonCode");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + code.toLowerCase() + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Evaluation> page = evaluationRepository.findAll(specification, pageable);
        List dicGrossTons = page.getContent();
        Long total = evaluationRepository.count(specification);
        return new ResultPageVo(total, dicGrossTons);
    }

    @Override
    public ResultVo findEvaluationById(String id) {
        return new ResultObjectVo(evaluationRepository.findById(id).get());
    }
}