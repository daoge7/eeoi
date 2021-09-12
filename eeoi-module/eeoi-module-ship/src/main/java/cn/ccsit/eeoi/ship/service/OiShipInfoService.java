package cn.ccsit.eeoi.ship.service;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.ship.dto.*;
import cn.ccsit.eeoi.ship.entity.OiShipPropeller;
import cn.ccsit.eeoi.ship.entity.OiShipTfc;
import cn.ccsit.eeoi.ship.query.OiShipInfoCriteria;
import cn.ccsit.eeoi.ship.query.OiShipInfoSsmisCriteria;
import cn.ccsit.eeoi.ship.vo.SsmisShipCcsnosVo;

import java.util.List;

public interface OiShipInfoService extends CommonService {
    /**
     * 创建船舶
     *
     * @param oiShipInfoDto
     * @return
     */
    ResultVo createOiShip(OiShipInfoDto oiShipInfoDto);

    /**
     * 获取船舶列表
     *
     * @param oiShipInfoCriteria
     * @return
     */
    ResultVo getOiShipInfo(OiShipInfoCriteria oiShipInfoCriteria);

    /**
     * 删除船舶列表
     *
     * @param shipIds
     * @return
     */
    ResultVo deleteOiShipInfo(List<String> shipIds);

    /**
     * 新增和更新船舶
     *
     * @param oiMainEngineDto
     * @return
     */
    ResultVo createAndUpdateMainEngine(OiMainEngineDto oiMainEngineDto);

    /**
     * 获取主机设备数量
     *
     * @param shipId
     * @return
     */
    ResultVo getOiMainEngineCount(String shipId);

    /**
     * 获取副机设备数量
     *
     * @param shipId
     * @return
     */
    ResultVo getOiShipGeCount(String shipId);

    /**
     * 删除主机设备
     *
     * @param mainEngineId
     * @return
     */
    ResultVo deleteOiMainEnginr(String mainEngineId);

    /**
     * 获取主机设备信息
     *
     * @param mainEngineId
     * @return
     */
    ResultVo getOiMainEngine(String mainEngineId);

    /**
     * 新增和更新副机信息
     *
     * @param oiShipGeDto
     * @return
     */
    ResultVo createAndUpdateOiShipGe(OiShipGeDto oiShipGeDto);

    /**
     * 删除副机设备
     *
     * @param mainEngineId
     * @return
     */
    ResultVo deleteOiShipGe(String mainEngineId);

    /**
     * 获取副机设备信息
     *
     * @param oiShipGeId
     * @return
     */
    ResultVo getOiShipGe(String oiShipGeId);

    /**
     * 创建发电机设备
     *
     * @param oiShipGeneratorDto
     * @return
     */
    ResultVo createAndUpdateOiShipGenerator(OiShipGeneratorDto oiShipGeneratorDto);

    /**
     * 获取发电机设备
     *
     * @param id
     * @return
     */
    ResultVo getOiShipGenerator(String id);

    /**
     * 删除发电机设备
     *
     * @param id
     * @return
     */
    ResultVo deleteShipGenerator(String id);

    /**
     * 新增和更新锅炉信息
     *
     * @param oiShipBoilerDto
     * @return
     */
    ResultVo createAndUpdateOiShipBoiler(OiShipBoilerDto oiShipBoilerDto);

    /**
     * 删除锅炉信息
     *
     * @param oiShipBoilerId
     * @return
     */
    ResultVo deleteShipBoilerInfo(String oiShipBoilerId);

    /**
     * 获取锅炉信息
     *
     * @param oiShipBoilerId
     * @return
     */
    ResultVo getOiShipBoilerInfo(String oiShipBoilerId);

    /**
     * 新增和更新惰性气体发生器
     *
     * @param oiShipInertDto
     * @return
     */
    ResultVo createAndUpdateOiShipInert(OiShipInertDto oiShipInertDto);

    /**
     * 获取惰性气体设备
     *
     * @param oiShipInertId
     * @return
     */
    ResultVo getOiShipInert(String oiShipInertId);

    /**
     * 删除惰性气体发生器
     *
     * @param oiShipInertId
     * @return
     */
    ResultVo deleteOiShipInert(String oiShipInertId);

    /**
     * 新增和更新涡轮机
     *
     * @param oiShipTurbineDto
     * @return
     */
    ResultVo createAndUpdateOiShipTurBine(OiShipTurbineDto oiShipTurbineDto);

    /**
     * 获取涡轮机信息
     *
     * @param oiShipTurbineId
     * @return
     */
    ResultVo getOiShipTurbine(String oiShipTurbineId);

    /**
     * 删除涡轮机
     *
     * @param oiShipTurbineId
     * @return
     */
    ResultVo deleteOiShipTurbine(String oiShipTurbineId);

    /**
     * 新增和更新焚烧炉信息
     *
     * @param oiIncineratorDto
     * @return
     */
    ResultVo createAndUpdateIncinerator(OiIncineratorDto oiIncineratorDto);

    /**
     * 查询焚烧炉信息
     *
     * @param oiIncineratorId
     * @return
     */
    ResultVo getOiIncinerator(String oiIncineratorId);

    /**
     * 删除焚烧炉信息
     *
     * @param oiIncineratorId
     * @return
     */
    ResultVo deleteIncinerator(String oiIncineratorId);

    /**
     * 新增和更新其他设备信息
     *
     * @param oiShipOtherDto
     * @return
     */
    ResultVo createAndUpdateOishipOther(OiShipOtherDto oiShipOtherDto);

    /**
     * 获取其他设备信息
     *
     * @param oiShipOtherId
     * @return
     */
    ResultVo getOiShipOther(String oiShipOtherId);

    /**
     * 删除其他设备信息
     *
     * @param oiShipOtherId
     * @return
     */
    ResultVo deleteOiShipOther(String oiShipOtherId);

    /**
     * 获取设备id和xh
     *
     * @param shipId
     * @return
     */
    ResultVo getShipEqipment(String shipId);

    /**
     * 删除非我司船舶
     *
     * @param shipId
     * @return
     */
    ResultVo deleteNotMyComPanyShip(String shipId);

    /**
     * 获取ssmis数据库的船舶信息
     *
     * @param oiShipInfoSsmisCriteria
     * @return
     */
    ResultVo getSsmisOiShipInfo(OiShipInfoSsmisCriteria oiShipInfoSsmisCriteria);

    /**
     * 同步ssmis船舶数据
     * @param shipIds
     */
    ResultVo synSsmisOiShipInfo(SsmisShipCcsnosVo shipIds);

    /**
     * 新增和更新电池信息
     * @param oiShipBatteryDto
     * @return
     */
    ResultVo createAndUpdateBattery(OiShipBatteryDto oiShipBatteryDto);

    /**
     * 获取电池信息
     * @param oiShipBatteryId
     * @return
     */
    ResultVo getOiShipBattery(String oiShipBatteryId);

    /**
     * 删除电池信息
      * @param oiShipBatteryDto
     * @return
     */
    ResultVo deleteOiShipBattery(OiShipBatteryDto oiShipBatteryDto);

    /**
     *
     * @return
     */
    ResultVo saveAndUpdateTfc(OiShipTfc oiShipTfc);

    /**
     * 删除tfc
     * @param oiShipTfc
     * @return
     */
    ResultVo deleteTfc(OiShipTfc oiShipTfc);

    /**
     * 获取tfc
     * @param shipId
     * @return
     */
    ResultVo getTfc(String shipId);

    /**
     * 获取所有的油耗类型
     * @return
     */
    ResultVo getFuelType();

    /**
     * 创建或更新螺旋桨设备
     * @param oishipPropellerDto
     * @return
     */
    ResultVo createAndUpdatePropeller(OishipPropellerDto oishipPropellerDto);

    /**
     * 根据id获取螺旋桨的信息
      * @param id
     * @return
     */
    ResultVo getOiShipPropelerById(String id);

    /**
     * 删除螺旋桨设备
     * @param id
     * @return
     */
    ResultVo deleteOiShipPropeller(String id);
    public String sysSsmisShipInfo();
}
