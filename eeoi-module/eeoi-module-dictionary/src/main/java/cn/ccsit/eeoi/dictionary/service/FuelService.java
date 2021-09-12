package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.Fuel;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface FuelService extends CommonService {

    public ResultVo deleteFuelById(String id);

    public ResultVo saveOrUpdateFuel(Fuel fuel);

    public ResultVo findAllFuelsByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findFuelById(String id);

}
