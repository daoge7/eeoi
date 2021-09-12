package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.dictionary.entity.EnergyForm;
import cn.ccsit.eeoi.dictionary.repository.EnergyFormRepository;
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
@Service("energyFormService")
public class EnergyFormServiceImpl extends CommonServiceImpl implements EnergyFormService {

    @Autowired
    private EnergyFormRepository energyFormRepository;

    @Override
    public ResultVo deleteEnergyFormById(String id) {
        EnergyForm energyForm = energyFormRepository.getOne(id);
        energyForm.setIsDelete("1");
        EnergyForm returnEntity = energyFormRepository.save(energyForm);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo saveOrUpdateEnergyForm(EnergyForm energyForm) {
        energyForm.setIsDelete("0");
        EnergyForm returnEntity = energyFormRepository.save(energyForm);
        return new ResultObjectVo<EnergyForm>(returnEntity);
    }

    @Override
    public ResultVo findAllEnergyFormsByPage(int pageSize, int pageNum, String code, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<EnergyForm> specification = new Specification<EnergyForm>() {
            @Override
            public Predicate toPredicate(Root<EnergyForm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
        Page<EnergyForm> page = energyFormRepository.findAll(specification, pageable);
        List dicGrossTons = page.getContent();
        Long total = energyFormRepository.count(specification);
        return new ResultPageVo(total, dicGrossTons);
    }

    @Override
    public ResultVo findEnergyFormById(String id) {
        return new ResultObjectVo(energyFormRepository.findById(id).get());
    }
}