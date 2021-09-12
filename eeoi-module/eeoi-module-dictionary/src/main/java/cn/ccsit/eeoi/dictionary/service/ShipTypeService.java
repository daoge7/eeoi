package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.ShipType;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface ShipTypeService extends CommonService {

    public ResultVo deleteShipTypeById(String id);

    public ResultVo saveOrUpdateShipType(ShipType shipType);

    public ResultVo findAllShipTypesByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findShipTypeById(String id);

}
