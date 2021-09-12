package cn.ccsit.eeoi.ship.service;

import cn.ccsit.common.constant.RoleConst;
import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.query.QueryHelp;
import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.common.vo.CurrentUserVo;
import cn.ccsit.eeoi.dictionary.entity.Classificat;
import cn.ccsit.eeoi.dictionary.entity.Fuel;
import cn.ccsit.eeoi.dictionary.entity.GcState;
import cn.ccsit.eeoi.dictionary.entity.ShipType;
import cn.ccsit.eeoi.dictionary.repository.*;
import cn.ccsit.eeoi.ship.dto.*;
import cn.ccsit.eeoi.ship.entity.*;
import cn.ccsit.eeoi.ship.query.OiShipInfoCriteria;
import cn.ccsit.eeoi.ship.query.OiShipInfoSsmisCriteria;
import cn.ccsit.eeoi.ship.repository.*;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipGeEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipInfoEntity;
import cn.ccsit.eeoi.ship.ssmisentity.SsmisOiShipMainEngineEntity;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipEquipAttrRepository;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipGeRepository;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipInfoRepository;
import cn.ccsit.eeoi.ship.ssmisrepository.SsmisOiShipMainEngineRepository;
import cn.ccsit.eeoi.ship.vo.OiShipEquipmentVo;
import cn.ccsit.eeoi.ship.vo.ShipEquipmentFuelMapVo;
import cn.ccsit.eeoi.ship.vo.SsmisOiShipInfoVo;
import cn.ccsit.eeoi.ship.vo.SsmisShipCcsnosVo;
import cn.ccsit.eeoi.system.entity.SysRole;
import cn.ccsit.eeoi.system.entity.SysUserRole;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.ccsit.eeoi.system.repository.SysUserClientMapRepository;
import cn.ccsit.eeoi.system.repository.SysUserRepository;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service("flagDocChangeService")
public class FlagDocChangeServiceImpl extends CommonServiceImpl implements FlagDocChangeService, CommonService {

    @Autowired
    private FlagDocChangeRepository flagDocChangeRepository;

    @Autowired
    private OiShipInfoRepository oiShipInfoRepository;

    @Autowired
    private GcClientRepository gcClientRepository;

    @Override
    public ResultVo save(FlagDocChange parameter) {
        parameter.setIsDelete("0");
        FlagDocChange flagDocChange = flagDocChangeRepository.save(parameter);
        return new ServiceResultVo(true, "save success");
    }

    @Override
    public ResultVo findOiShipInfoByImoNo(String no) {
        OiShipInfo oiShipInfo = oiShipInfoRepository.findByImonoOrRegisternoAndIsDelete(no, no, 0);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", oiShipInfo.getSpnameCn() + "/" + oiShipInfo.getSpname());
        hashMap.put("shipId", oiShipInfo.getId());
        return new ResultObjectVo<HashMap>(hashMap);
    }

    @Override
    public ResultVo findAllFlagDocChangeOrderByEffectiveDateDesc() {
        List<FlagDocChange> flagDocChanges = flagDocChangeRepository.findAllByOrderByEffectiveDateDesc();
        return new ResultObjectVo<>(flagDocChanges);
    }

    @Override
    public ResultVo findFlagDocChangeById(String id) {
        FlagDocChange flagDocChange = flagDocChangeRepository.findById(id).get();
        List<GcClient> byCodeList = gcClientRepository.findByCode(flagDocChange.getDocOld());
        if (byCodeList != null && byCodeList.size() > 0) {
            flagDocChange.setOldDocName(byCodeList.get(0).getNameCn());
        }
        List<GcClient> byCodeListNew = gcClientRepository.findByCode(flagDocChange.getDocNew());
        if (byCodeListNew != null && byCodeListNew.size() > 0) {
            flagDocChange.setNewDocName(byCodeListNew.get(0).getNameCn());
        }
        return new ResultObjectVo<>(flagDocChange);
    }

    @Override
    public ResultVo deleteFlagDocChangeById(String id) {
        FlagDocChange flagDocChange = flagDocChangeRepository.findById(id).get();
        flagDocChange.setIsDelete("1");
        FlagDocChange returnEntity = flagDocChangeRepository.save(flagDocChange);
        if (returnEntity == null) {
            return new ServiceResultVo(false, "delete fail");
        } else {
            return new ServiceResultVo(true, "delete success");
        }
    }

    @Override
    public ResultVo deleteMultiFlagDocChangeById(String[] id) {
        for (int i = 0; i < id.length; i++) {
            FlagDocChange flagDocChange = flagDocChangeRepository.findById(id[i]).get();
            flagDocChange.setIsDelete("1");
            FlagDocChange returnEntity = flagDocChangeRepository.save(flagDocChange);
            if (returnEntity == null) {
                break;
            }
        }
        return new ServiceResultVo(true, "delete success");
    }

    @Override
    public ResultVo findAllByshipIdOrderByEffectiveDateDesc(String shipId, String isDelete) {
        List list = flagDocChangeRepository.findAllByshipIdAndIsDeleteOrderByEffectiveDateDesc(shipId, "0");
        return new ServiceResultVo(true, list);
    }

}
