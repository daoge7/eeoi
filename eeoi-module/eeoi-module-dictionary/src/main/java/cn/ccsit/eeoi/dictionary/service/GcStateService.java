package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.GcState;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface GcStateService extends CommonService {

    public ResultVo deleteGcStateById(String id);

    public ResultVo saveOrUpdateGcState(@RequestBody GcState gcState);

    public ResultVo findAllGcCities();

    public ResultVo findAllGcCitiesByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findGcStateById(String id);

    public ResultVo findAllGcStatesEnBrief();

    ResultVo findTopEnbriefByCnBrief(String cnBrief);

    ResultVo findTopCnBriefByEnBrief(String enBrief);

}
