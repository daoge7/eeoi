package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.DicGrossTon;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface DicGrossTonService extends CommonService {

    public ResultVo deleteDicGrossTonById(String id);

    public ResultVo saveOrUpdateDicGrossTon(@RequestBody DicGrossTon dicGrossTon);

    public ResultVo findAllDicGrossTonsByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findDicGrossTonById(String id);

}
