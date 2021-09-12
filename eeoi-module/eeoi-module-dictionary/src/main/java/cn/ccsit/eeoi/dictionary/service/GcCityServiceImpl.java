package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.GcCity;
import cn.ccsit.eeoi.dictionary.repository.GcCityRepository;
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
@Service("gcCityService")
public class GcCityServiceImpl extends CommonServiceImpl implements GcCityService, CommonService {

    @Autowired
    private GcCityRepository gcCityRepository;

    @Override
    public ResultVo deleteGcCityById(String id) {
        GcCity gcCity = gcCityRepository.getOne(id);
        gcCity.setIsDelete("1");
        GcCity gc = gcCityRepository.save(gcCity);
        if (gc == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateGcCity(GcCity gcCity) {
        gcCity.setIsDelete("0");
        GcCity gc = gcCityRepository.save(gcCity);
        return new ResultObjectVo<GcCity>(gc);
    }

    @Override
    public ResultVo findAllGcCities() {
        List<GcCity> gcCities = gcCityRepository.findAll();
        return new ResultPageVo(gcCities.size(), gcCities);
    }

    @Override
    public ResultVo findAllGcCitiesByPage(int pageSize, int pageNum, String code, String enName, String cnName) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<GcCity> specification = new Specification<GcCity>() {
            @Override
            public Predicate toPredicate(Root<GcCity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> predicatesOr = new ArrayList<>();
                if (enName != null && !"".equals(enName)) {
                    Path<Object> name1 = root.get("enName");
                    predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + enName.toLowerCase() + "%"));
                    Path<Object> cnName1 = root.get("cnName");
                    predicatesOr.add(criteriaBuilder.like(cnName1.as(String.class), "%" + enName + "%"));
                    Path<Object> code1 = root.get("code");
                    predicatesOr.add(criteriaBuilder.like(code1.as(String.class), "%" + enName + "%"));
                }
                if (code != null && !"".equals(code)) {
                    Path<Object> ccsCode1 = root.get("code");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + code.toLowerCase() + "%"));
                }
//                if (cnName != null && !"".equals(cnName)) {
//                    Path<Object> cnName1 = root.get("cnName");
//                    predicates.add(criteriaBuilder.like(cnName1.as(String.class), "%" + cnName + "%"));
//                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));

                return predicatesOr.size() > 0 ? criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])), criteriaBuilder.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]))).getRestriction() : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<GcCity> page = gcCityRepository.findAll(specification, pageable);
        List gcCities = page.getContent();
        Long total = gcCityRepository.count(specification);
        return new ResultPageVo(total, gcCities);
    }

    @Override
    public ResultVo findAllGcCitiesByPageForApp(int pageSize, int pageNum, String code, String enName, String cnName) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<GcCity> specification = new Specification<GcCity>() {
            @Override
            public Predicate toPredicate(Root<GcCity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> predicatesOr = new ArrayList<>();
                if (enName != null && !"".equals(enName)) {
                    Path<Object> name1 = root.get("enName");
                    predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + enName.toLowerCase() + "%"));
                    Path<Object> cnName1 = root.get("cnName");
                    predicatesOr.add(criteriaBuilder.like(cnName1.as(String.class), "%" + enName + "%"));
                    Path<Object> code1 = root.get("code");
                    predicatesOr.add(criteriaBuilder.like(code1.as(String.class), "%" + enName + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));

                return predicatesOr.size() > 0 ? criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])), criteriaBuilder.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]))).getRestriction() : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<GcCity> page = gcCityRepository.findAll(specification, pageable);
        List gcCities = page.getContent();
        Long total = gcCityRepository.count(specification);
        return new ResultPageVo(total, gcCities);
    }

    @Override
    public ResultVo findGcCityById(String id) {
        return new ResultObjectVo(gcCityRepository.findById(id).get());
    }
}