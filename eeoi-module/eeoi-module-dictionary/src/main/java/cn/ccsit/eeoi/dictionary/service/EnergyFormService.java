package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.EnergyForm;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface EnergyFormService extends CommonService {

    public ResultVo deleteEnergyFormById(String id);

    public ResultVo saveOrUpdateEnergyForm(EnergyForm energyForm);

    public ResultVo findAllEnergyFormsByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findEnergyFormById(String id);

}
