package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.GcState;
import cn.ccsit.eeoi.dictionary.repository.GcStateRepository;
import cn.ccsit.eeoi.ship.entity.GcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:54
 */
@Service("gcStateService")
public class GcStateServiceImpl extends CommonServiceImpl implements GcStateService, CommonService {

    @Autowired
    private GcStateRepository gcStateRepository;

    @Override
    public ResultVo deleteGcStateById(String id) {
        GcState gcState = gcStateRepository.getOne(id);
        gcState.setIsDelete("1");
        GcState gc = gcStateRepository.save(gcState);
        if (gc == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateGcState(GcState gcState) {
        gcState.setIsDelete("0");
        GcState gc = gcStateRepository.save(gcState);
        return new ResultObjectVo<GcState>(gc);
    }

    @Override
    public ResultVo findAllGcCities() {
        List<GcState> gcCities = gcStateRepository.findAll();
        return new ResultPageVo(gcCities.size(), gcCities);
    }

    @Override
    public ResultVo findAllGcCitiesByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<GcState> specification = new Specification<GcState>() {
            @Override
            public Predicate toPredicate(Root<GcState> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null && !"".equals(name)) {
                    Path<Object> name1 = root.get("cnBrief");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + name.toLowerCase() + "%"));
                }
                if (code != null && !"".equals(code)) {
                    Path<Object> ccsCode1 = root.get("enName");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + code.toLowerCase() + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<GcState> page = gcStateRepository.findAll(specification, pageable);
        List gcCities = page.getContent();
        Long total = gcStateRepository.count(specification);
        return new ResultPageVo(total, gcCities);
    }

    @Override
    public ResultVo findGcStateById(String id) {
        return new ResultObjectVo(gcStateRepository.findById(id).get());
    }

    @Override
    public ResultVo findAllGcStatesEnBrief() {
        List<GcState> gcStates = gcStateRepository.findAll();
        List gcStateVo = new ArrayList();
        for (GcState gc : gcStates) {
            Map map = new HashMap<String, String>();
            map.put("enBrief", gc.getEnBrief());
            map.put("cnBrief", gc.getCnBrief());
            gcStateVo.add(map);
        }
        return new ResultPageVo(gcStateVo.size(), gcStateVo);
    }

    @Override
    public ResultVo findTopEnbriefByCnBrief(String cnBrief) {
        return new ResultStringVo(gcStateRepository.findTopByCnBrief(cnBrief).getEnBrief());
    }

    @Override
    public ResultVo findTopCnBriefByEnBrief(String enBrief) {
        return new ResultStringVo(gcStateRepository.findTopByEnBrief(enBrief).getCnBrief());
    }
}