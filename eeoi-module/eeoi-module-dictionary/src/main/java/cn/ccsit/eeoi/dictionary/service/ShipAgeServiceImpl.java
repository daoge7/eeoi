package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.ShipAge;
import cn.ccsit.eeoi.dictionary.repository.ShipAgeRepository;
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
@Service("shipAgeService")
public class ShipAgeServiceImpl extends CommonServiceImpl implements ShipAgeService {

    @Autowired
    private ShipAgeRepository shipAgeRepository;

    @Override
    public ResultVo deleteShipAgeById(String id) {
        ShipAge shipAge = shipAgeRepository.getOne(id);
        shipAge.setIsDelete("1");
        ShipAge returnEntity = shipAgeRepository.save(shipAge);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateShipAge(ShipAge shipAge) {
        shipAge.setIsDelete("0");
        ShipAge returnEntity = shipAgeRepository.save(shipAge);
        return new ResultObjectVo<ShipAge>(returnEntity);
    }

    @Override
    public ResultVo findAllShipAgesByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<ShipAge> specification = new Specification<ShipAge>() {
            @Override
            public Predicate toPredicate(Root<ShipAge> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
        Page<ShipAge> page = shipAgeRepository.findAll(specification, pageable);
        List dicGrossTons = page.getContent();
        Long total = shipAgeRepository.count(specification);
        return new ResultPageVo(total, dicGrossTons);
    }

    @Override
    public ResultVo findShipAgeById(String id) {
        return new ResultObjectVo(shipAgeRepository.findById(id).get());
    }
}