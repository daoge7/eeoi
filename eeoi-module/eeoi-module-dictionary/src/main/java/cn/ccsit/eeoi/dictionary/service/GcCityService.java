package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.GcCity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface GcCityService extends CommonService {

    public ResultVo deleteGcCityById(String id);

    public ResultVo saveOrUpdateGcCity(@RequestBody GcCity gcCity);

    public ResultVo findAllGcCities();

    public ResultVo findAllGcCitiesByPage(int pageSize, int pageNum, String code, String enName, String cnName);
    public ResultVo findAllGcCitiesByPageForApp(int pageSize, int pageNum, String code, String enName, String cnName);

    public ResultVo findGcCityById(String id);

}
