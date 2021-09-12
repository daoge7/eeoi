package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.DicGrossTon;
import cn.ccsit.eeoi.dictionary.repository.DicGrossTonRepository;
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
@Service("dicGrossTonService")
public class DicGrossTonServiceImpl extends CommonServiceImpl implements DicGrossTonService {

    @Autowired
    private DicGrossTonRepository dicGrossTonRepository;

    @Override
    public ResultVo deleteDicGrossTonById(String id) {
        DicGrossTon dicGrossTon = dicGrossTonRepository.getOne(id);
        dicGrossTon.setIsDelete("1");
        DicGrossTon returnEntity = dicGrossTonRepository.save(dicGrossTon);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateDicGrossTon(DicGrossTon dicGrossTon) {
        dicGrossTon.setIsDelete("0");
        DicGrossTon returnEntity = dicGrossTonRepository.save(dicGrossTon);
        return new ResultObjectVo<DicGrossTon>(returnEntity);
    }

    @Override
    public ResultVo findAllDicGrossTonsByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<DicGrossTon> specification = new Specification<DicGrossTon>() {
            @Override
            public Predicate toPredicate(Root<DicGrossTon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
        Page<DicGrossTon> page = dicGrossTonRepository.findAll(specification, pageable);
        List dicGrossTons = page.getContent();
        Long total = dicGrossTonRepository.count(specification);
        return new ResultPageVo(total, dicGrossTons);
    }

    @Override
    public ResultVo findDicGrossTonById(String id) {
        return new ResultObjectVo(dicGrossTonRepository.findById(id).get());
    }
}