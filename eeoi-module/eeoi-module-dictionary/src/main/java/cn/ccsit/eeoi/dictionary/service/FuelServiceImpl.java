package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.Fuel;
import cn.ccsit.eeoi.dictionary.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Service("fuelService")
public class FuelServiceImpl extends CommonServiceImpl implements FuelService {

    @Autowired
    private FuelRepository fuelRepository;

    @Override
    public ResultVo deleteFuelById(String id) {
        Fuel fuel = fuelRepository.getOne(id);
        fuel.setIsDelete("1");
        Fuel returnEntity = fuelRepository.save(fuel);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateFuel(Fuel fuel) {
        fuel.setIsDelete("0");
        Fuel returnEntity = fuelRepository.save(fuel);
        return new ResultObjectVo<Fuel>(returnEntity);
    }

    @Override
    public ResultVo findAllFuelsByPage(int pageSize, int pageNum, String code, String name) {
        Sort orders = Sort.by("fuelName");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, orders);
        Specification<Fuel> specification = new Specification<Fuel>() {
            @Override
            public Predicate toPredicate(Root<Fuel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null && !"".equals(name)) {
                    Path<Object> name1 = root.get("formName");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + name.toLowerCase() + "%"));
                }
                if (code != null && !"".equals(code)) {
                    Path<Object> ccsCode1 = root.get("formCode");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + code.toLowerCase() + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Page<Fuel> page = fuelRepository.findAll(specification, pageable);
        List dicGrossTons = page.getContent();
        Long total = fuelRepository.count(specification);
        return new ResultPageVo(total, dicGrossTons);
    }

    @Override
    public ResultVo findFuelById(String id) {
        return new ResultObjectVo(fuelRepository.findById(id).get());
    }
}