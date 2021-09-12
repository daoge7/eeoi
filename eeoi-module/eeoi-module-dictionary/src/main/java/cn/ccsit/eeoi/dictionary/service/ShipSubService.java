package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.ShipSub;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface ShipSubService extends CommonService {

    public ResultVo deleteShipSubById(String id);

    public ResultVo saveOrUpdateShipSub(ShipSub shipSub);

    public ResultVo findAllShipSubsByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findShipSubById(String id);

    public ResultVo findShipSubByShipType(String type);

}
