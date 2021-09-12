package cn.ccsit.eeoi.dictionary.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.dictionary.entity.Classificat;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: eeoi
 * @description:
 * @author: luhao
 * @create: 2020-05-12 16:53
 */
public interface ClassificatService extends CommonService {

    public ResultVo deleteClassificatById(String id);

    public ResultVo saveOrUpdateClassificat(@RequestBody Classificat classificat);

    public ResultVo findAllClassificats();

    public ResultVo findAllClassificatsByPage(int pageSize, int pageNum, String code, String name);

    public ResultVo findClassificatById(String id);

}
