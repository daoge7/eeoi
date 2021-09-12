package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.Classificat;
import cn.ccsit.eeoi.dictionary.repository.ClassificatRepository;
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
@Service("classificatService")
public class ClassificatServiceImpl extends CommonServiceImpl implements ClassificatService, CommonService {

    @Autowired
    private ClassificatRepository classificatRepository;

    @Override
    public ResultVo deleteClassificatById(String id) {
        Classificat classificat = classificatRepository.getOne(id);
        classificat.setIsDelete("1");
        Classificat gc = classificatRepository.save(classificat);
        if(gc == null){
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateClassificat(Classificat classificat) {
        classificat.setIsDelete("0");
        Classificat gc = classificatRepository.save(classificat);
        return new ResultObjectVo<Classificat>(gc);
    }

    @Override
    public ResultVo findAllClassificats() {
        List<Classificat> gcCities = classificatRepository.findAll();
        return new ResultPageVo(gcCities.size(), gcCities);
    }

    @Override
    public ResultVo findAllClassificatsByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<Classificat> specification = new Specification<Classificat>() {
            @Override
            public Predicate toPredicate(Root<Classificat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null && !"".equals(name)) {
                    Path<Object> name1 = root.get("abbrn");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + name.toLowerCase() + "%"));
                }
                if (code != null && !"".equals(code)) {
                    Path<Object> ccsCode1 = root.get("code");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + code.toLowerCase() + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Classificat> page = classificatRepository.findAll(specification, pageable);
        List gcCities = page.getContent();
        Long total = classificatRepository.count(specification);
        return new ResultPageVo(total, gcCities);
    }

    @Override
    public ResultVo findClassificatById(String id) {
        return new ResultObjectVo(classificatRepository.findById(id).get());
    }
}