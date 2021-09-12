package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.DicGrossTon;
import cn.ccsit.eeoi.dictionary.entity.ShipAge;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface ShipAgeService extends CommonService {

    public ResultVo deleteShipAgeById(String id);

    public ResultVo saveOrUpdateShipAge(ShipAge shipAge);

    public ResultVo findAllShipAgesByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findShipAgeById(String id);

}
