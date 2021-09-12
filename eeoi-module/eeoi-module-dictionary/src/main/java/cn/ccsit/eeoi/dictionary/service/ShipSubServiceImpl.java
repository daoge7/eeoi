package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.ShipSub;
import cn.ccsit.eeoi.dictionary.repository.ShipSubRepository;
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
@Service("shipSubService")
public class ShipSubServiceImpl extends CommonServiceImpl implements ShipSubService {

    @Autowired
    private ShipSubRepository shipSubRepository;

    @Override
    public ResultVo deleteShipSubById(String id) {
        ShipSub shipSub = shipSubRepository.getOne(id);
        shipSub.setIsDelete("1");
        ShipSub returnEntity = shipSubRepository.save(shipSub);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateShipSub(ShipSub shipSub) {
        shipSub.setIsDelete("0");
        ShipSub returnEntity = shipSubRepository.save(shipSub);
        return new ResultObjectVo<ShipSub>(returnEntity);
    }

    @Override
    public ResultVo findAllShipSubsByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<ShipSub> specification = new Specification<ShipSub>() {
            @Override
            public Predicate toPredicate(Root<ShipSub> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null && !"".equals(name)) {
                    Path<Object> name1 = root.get("shipName");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + name.toLowerCase() + "%"));
                }
                if (code != null && !"".equals(code)) {
                    Path<Object> ccsCode1 = root.get("shipCode");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + code.toLowerCase() + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<ShipSub> page = shipSubRepository.findAll(specification, pageable);
        List dicGrossTons = page.getContent();
        Long total = shipSubRepository.count(specification);
        return new ResultPageVo(total, dicGrossTons);
    }

    @Override
    public ResultVo findShipSubById(String id) {
        return new ResultObjectVo(shipSubRepository.findById(id).get());
    }

    @Override
    public ResultVo findShipSubByShipType(String type) {
        return new ResultObjectVo<List>(shipSubRepository.findAllByShipType(type));
    }
}