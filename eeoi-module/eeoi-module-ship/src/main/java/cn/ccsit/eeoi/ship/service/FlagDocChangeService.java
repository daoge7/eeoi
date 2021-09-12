package cn.ccsit.eeoi.ship.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.ship.entity.FlagDocChange;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.vo.GcClientShowListVo;
import cn.ccsit.eeoi.ship.vo.GcClientVo;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface FlagDocChangeService extends CommonService {

	ResultVo save(FlagDocChange parameter);

	ResultVo findOiShipInfoByImoNo(String no);

	ResultVo findAllFlagDocChangeOrderByEffectiveDateDesc();

	ResultVo findFlagDocChangeById(String id);

	ResultVo deleteFlagDocChangeById(String id);

	ResultVo deleteMultiFlagDocChangeById(String[] id);

	ResultVo findAllByshipIdOrderByEffectiveDateDesc(String id,String isDelete);
}
