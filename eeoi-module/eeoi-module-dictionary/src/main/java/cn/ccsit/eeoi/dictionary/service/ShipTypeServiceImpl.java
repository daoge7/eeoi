package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.ShipType;
import cn.ccsit.eeoi.dictionary.repository.ShipTypeRepository;
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
@Service("shipTypeService")
public class ShipTypeServiceImpl extends CommonServiceImpl implements ShipTypeService {

    @Autowired
    private ShipTypeRepository shipTypeRepository;

    @Override
    public ResultVo deleteShipTypeById(String id) {
        ShipType shipType = shipTypeRepository.getOne(id);
        shipType.setIsDelete("1");
        ShipType returnEntity = shipTypeRepository.save(shipType);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateShipType(ShipType shipType) {
        shipType.setIsDelete("0");
        ShipType returnEntity = shipTypeRepository.save(shipType);
        return new ResultObjectVo<ShipType>(returnEntity);
    }

    @Override
    public ResultVo findAllShipTypesByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<ShipType> specification = new Specification<ShipType>() {
            @Override
            public Predicate toPredicate(Root<ShipType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null && !"".equals(name)) {
                    Path<Object> name1 = root.get("csptype");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(name1.as(String.class)), "%" + name.toLowerCase() + "%"));
                }
                if (code != null && !"".equals(code)) {
                    Path<Object> ccsCode1 = root.get("euSptype");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + code.toLowerCase() + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<ShipType> page = shipTypeRepository.findAll(specification, pageable);
        List shipTypes = page.getContent();
        Long total = shipTypeRepository.count(specification);
        return new ResultPageVo(total, shipTypes);
    }

    @Override
    public ResultVo findShipTypeById(String id) {
        return new ResultObjectVo(shipTypeRepository.findById(id).get());
    }
}