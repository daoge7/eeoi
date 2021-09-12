package cn.ccsit.eeoi.ship.controller;

import java.util.List;

import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.vo.CurrentUserVo;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.vo.GcClientParentSettingVo;
import cn.ccsit.eeoi.system.entity.SysUserRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.controller.CommonController;
import cn.ccsit.eeoi.ship.service.GcClientService;
import cn.ccsit.eeoi.ship.vo.GcClientShowListVo;
import cn.ccsit.eeoi.ship.vo.GcClientVo;

@RestController
@RequestMapping("/gcClient")
public class GcClientController extends CommonController {

    //private static Logger logger = LogManager.getLogger(GcClientController.class);

    @Autowired
    private GcClientService gcClientService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/findGcClient/{id}")
    public ResultVo findGcClient(@PathVariable("id") String id) {
        return gcClientService.findGcClientShowVoByGcClient(id);
    }

    @GetMapping("/findGcClientlist")
    public ResultVo findGcLientListByConditionsAndPages(@RequestParam(value = "pageSize", required = true) int pageSize, @RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "nameCn", required = false) String nameCn, @RequestParam(value = "officeAdressCn", required = false) String officeAdressCn, @RequestParam(value = "ccsCode", required = false) String ccsCode) {
        CurrentUserVo currentUser=commonService.getCurrentUser();
        String userId=null;
        for(SysUserRole sur:currentUser.getRoles()){
            if("12".equals(sur.getRoleId())){
                userId=currentUser.getId();
                break;
            }
        }
        return gcClientService.findGcClientsVoByPage(pageSize, pageNum, nameCn, officeAdressCn, ccsCode, userId);
    }

    @GetMapping("/findGcClientCates")
    public ResultVo findGcClientCates() {
        return gcClientService.findAllGcClientCates();

    }

    @GetMapping("/usercompanylist")
    public ResultVo UserCompanyList(String userId) {
        List<GcClientShowListVo> gcClients = gcClientService.getGcClientsByUserId(userId);
        return new ResultPageVo((long) gcClients.size(), gcClients);
    }

    @PostMapping("/createAndUpdateGcClient")
    public ResultVo saveGcClient(@RequestBody GcClientVo gcClientVo) {
        return gcClientService.saveGcClientByVo(gcClientVo);
    }

    @GetMapping("/delete/{id}")
    public ResultVo deleteGcClientById(@PathVariable("id") String id) {
        return gcClientService.deleteGcClientById(id);
    }

    @PostMapping("/setParentGcClient")
    public ResultVo deleteGcClient(@RequestBody GcClientParentSettingVo gcClientParentSettingVo) {
        return gcClientService.setParentGcClient(gcClientParentSettingVo.getId(), gcClientParentSettingVo.getChildren());
    }
}
