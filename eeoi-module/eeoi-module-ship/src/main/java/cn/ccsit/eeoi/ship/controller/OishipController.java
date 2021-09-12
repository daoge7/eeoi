package cn.ccsit.eeoi.ship.controller;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.dictionary.service.EnergyFormService;
import cn.ccsit.eeoi.ship.dto.*;
import cn.ccsit.eeoi.ship.entity.OiShipTfc;
import cn.ccsit.eeoi.ship.query.OiShipInfoCriteria;
import cn.ccsit.eeoi.ship.query.OiShipInfoSsmisCriteria;
import cn.ccsit.eeoi.ship.service.OiShipInfoService;
import cn.ccsit.eeoi.ship.vo.SsmisOiShipInfoVo;
import cn.ccsit.eeoi.ship.vo.SsmisShipCcsnosVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shipManager")
public class OishipController {
    @Autowired
    private OiShipInfoService oiShipInfoService;

    @PostMapping("/createAndUpdateShip")
    public ResultVo createAndUpdateOishipInfo(@RequestBody OiShipInfoDto oiShipInfoDto) {
        return oiShipInfoService.createOiShip(oiShipInfoDto);
    }

    @GetMapping("/shipList")
    public ResultVo getOishipInfo(OiShipInfoCriteria oiShipInfoCriteria) {
        return oiShipInfoService.getOiShipInfo(oiShipInfoCriteria);
    }

    @PostMapping("/deleteShip")
    public ResultVo deleteOiShipInfo(@RequestBody BatchDeleteOiShipInfo batchDeleteOiShipInfo) {
        List<String> shipIds = new ArrayList<>();
        batchDeleteOiShipInfo.getShipIds().forEach(oiShipInfoDto -> {
            shipIds.add(oiShipInfoDto.getId());
        });
        return oiShipInfoService.deleteOiShipInfo(shipIds);
    }

    @PostMapping("/createAndUpdateShipMainEngine")
    public ResultVo crecreateAndUpdateShipMainEngine(@RequestBody OiMainEngineDto oiMainEngineDto) {
        return oiShipInfoService.createAndUpdateMainEngine(oiMainEngineDto);
    }

    @GetMapping("/getOiMainCount")
    public ResultVo getOiMainShipCount(String shipId) {
        return oiShipInfoService.getOiMainEngineCount(shipId);
    }

    @GetMapping("/getOiShipGeCount")
    public ResultVo getOiShipGecount(String shipId) {
        return oiShipInfoService.getOiShipGeCount(shipId);
    }

    @GetMapping("/getShipMainEngine")
    public ResultVo getShipMainEngine(String id) {
        return oiShipInfoService.getOiMainEngine(id);
    }

    @PostMapping("/deleteMainEngine")
    public ResultVo deleteShipMainEngine(@RequestBody OiMainEngineDto oiMainEngineDto) {
        return oiShipInfoService.deleteOiMainEnginr(oiMainEngineDto.getId());
    }

    @PostMapping("/createAndUpdateShipAuEnGine")
    public ResultVo createAndUpdateShipAuEnGine(@RequestBody OiShipGeDto oiShipGeDto) {
        return oiShipInfoService.createAndUpdateOiShipGe(oiShipGeDto);
    }

    @GetMapping("/getShipAuEngine")
    public ResultVo getShipAuEngine(String id) {
        return oiShipInfoService.getOiShipGe(id);
    }

    @PostMapping("/deleteShipAuEngine")
    public ResultVo deleteShipGe(@RequestBody OiShipGeDto oiShipGeDto) {
        return oiShipInfoService.deleteOiShipGe(oiShipGeDto.getId());
    }

    @PostMapping("/createAndUpdateShipGenerator")
    public ResultVo createAndUpdateShipGenerator(@RequestBody OiShipGeneratorDto oiShipGeneratorDto) {
        return oiShipInfoService.createAndUpdateOiShipGenerator(oiShipGeneratorDto);
    }

    @GetMapping("/getshipGenerator")
    public ResultVo getShipGenerator(String shipGeneratorId) {
        return oiShipInfoService.getOiShipGenerator(shipGeneratorId);
    }

    @PostMapping("/deleteShipGenerator")
    public ResultVo deleteShipGe(@RequestBody OiShipGeneratorDto oiShipGeneratorDto) {
        return oiShipInfoService.deleteShipGenerator(oiShipGeneratorDto.getId());
    }

    @PostMapping("/createAndUpdateShipBolier")
    public ResultVo createAndUpdateOiShipBoiler(@RequestBody OiShipBoilerDto oiShipBoilerDto) {
        return oiShipInfoService.createAndUpdateOiShipBoiler(oiShipBoilerDto);
    }

    @GetMapping("/getShipBolier")
    public ResultVo getOiShipBoiler(String shipBolierId) {
        return oiShipInfoService.getOiShipBoilerInfo(shipBolierId);
    }

    @PostMapping("/deleteShipBoiler")
    public ResultVo deleteOiShipBoiler(@RequestBody OiShipBoilerDto oiShipBoilerDto) {
        return oiShipInfoService.deleteShipBoilerInfo(oiShipBoilerDto.getId());
    }

    @PostMapping("/createAndUpdateShipInert")
    public ResultVo createAndUpdateOiShipInert(@RequestBody OiShipInertDto oiShipInertDto) {
        return oiShipInfoService.createAndUpdateOiShipInert(oiShipInertDto);
    }

    @GetMapping("/getShipInert")
    public ResultVo getOiShipInert(String shipInertId) {
        return oiShipInfoService.getOiShipInert(shipInertId);
    }

    @PostMapping("/deleteShipInert")
    public ResultVo deleteOiShipInert(@RequestBody OiShipInertDto oiShipInertDto) {
        return oiShipInfoService.deleteOiShipInert(oiShipInertDto.getId());
    }

    @PostMapping("/createAndUpdateShipTurbine")
    public ResultVo createAndUpdateOiShipTurBine(@RequestBody OiShipTurbineDto oiShipTurbineDto) {
        return oiShipInfoService.createAndUpdateOiShipTurBine(oiShipTurbineDto);
    }

    @GetMapping("/getShipTurbine")
    public ResultVo getOiShipTurBine(String turbineId) {
        return oiShipInfoService.getOiShipTurbine(turbineId);
    }

    @PostMapping("/deleteShipTurbine")
    public ResultVo deleteOiShipTurBine(@RequestBody OiShipTurbineDto oiShipTurbineDto) {
        return oiShipInfoService.deleteOiShipTurbine(oiShipTurbineDto.getId());
    }

    @PostMapping("/createAndUpdateShipIncinerator")
    public ResultVo createAndUpdateIncinerator(@RequestBody OiIncineratorDto oiIncineratorDto) {
        return oiShipInfoService.createAndUpdateIncinerator(oiIncineratorDto);
    }

    @GetMapping("/getShipIncinerator")
    public ResultVo getOiIncinerator(String shipIncineratorId) {
        return oiShipInfoService.getOiIncinerator(shipIncineratorId);
    }

    @PostMapping("/deleteShipIncinerator")
    public ResultVo deleteIncinerator(@RequestBody OiIncineratorDto oiIncineratorDto) {
        return oiShipInfoService.deleteIncinerator(oiIncineratorDto.getId());
    }

    @PostMapping("/createAndUpdateShipOtherEquipment")
    public ResultVo createAndUpdateOishipOther(@RequestBody OiShipOtherDto oiShipOtherDto) {
        return oiShipInfoService.createAndUpdateOishipOther(oiShipOtherDto);
    }

    @GetMapping("/getShipOtherEquipment")
    public ResultVo getOiShipOther(String otherEquipmentId) {
        return oiShipInfoService.getOiShipOther(otherEquipmentId);
    }

    @PostMapping("/deleteOtherEquipment")
    public ResultVo deleteOiShipOther(@RequestBody OiShipOtherDto oiShipOtherDto) {
        return oiShipInfoService.deleteOiShipOther(oiShipOtherDto.getId());
    }

    @GetMapping("/getShipEqipment")
    public ResultVo getEquipmentIds(String shipId) {
        return oiShipInfoService.getShipEqipment(shipId);
    }

    @GetMapping("/notMyCompanyShip")
    public ResultVo notMyCompanyShip(String shipId) {
        return oiShipInfoService.deleteNotMyComPanyShip(shipId);
    }

    @GetMapping("/ssmisOishipInfoList")
    public ResultVo getSsmisOiShipInfo(OiShipInfoSsmisCriteria oiShipInfoSsmisCriteria) {
        return oiShipInfoService.getSsmisOiShipInfo(oiShipInfoSsmisCriteria);
    }

    @PostMapping("/synSsmisShipInfo")
    public ResultVo synSsmisShipInfo(@RequestBody SsmisShipCcsnosVo ssmisShipCcsnosVo) {
        return oiShipInfoService.synSsmisOiShipInfo(ssmisShipCcsnosVo);
    }

    @PostMapping("/createAndUpdateBattery")
    public ResultVo createAndUpdateBattery(@RequestBody OiShipBatteryDto oiShipBatteryDto) {
        return oiShipInfoService.createAndUpdateBattery(oiShipBatteryDto);
    }

    @GetMapping("/getOiShipBattery")
    public ResultVo getOiShipBattery(String oiShipBatteryId) {
        return oiShipInfoService.getOiShipBattery(oiShipBatteryId);
    }

    @PostMapping("/deleteOiShipBattery")
    public ResultVo deleteOiShipBattery(@RequestBody OiShipBatteryDto oiShipBatteryDto) {
        return oiShipInfoService.deleteOiShipBattery(oiShipBatteryDto);
    }

    @PostMapping("/saveAndUpdateTfc")
    public ResultVo saveAndUpdateTfc(@RequestBody OiShipTfc oiShipTfc) {
        return oiShipInfoService.saveAndUpdateTfc(oiShipTfc);
    }

    @PostMapping("/deleteTfc")
    public ResultVo deleteTfc(@RequestBody OiShipTfc oiShipTfc) {
        return oiShipInfoService.deleteTfc(oiShipTfc);
    }

    @GetMapping("/getTfc")
    public ResultVo getTfc(@RequestParam("shipId") String shipId) {
        return oiShipInfoService.getTfc(shipId);
    }

    @GetMapping("/getFuelType")
    public ResultVo getFuelType() {
        return oiShipInfoService.getFuelType();
    }

    @PostMapping("/createAndUpdatePropeller")
    public ResultVo createAndUpdatePropeller(@RequestBody OishipPropellerDto oishipPropellerDto) {
        return oiShipInfoService.createAndUpdatePropeller(oishipPropellerDto);
    }
    @PostMapping("/deleteOiShipPropeller")
    public ResultVo deleteOiShipPropeller(@RequestBody OishipPropellerDto oishipPropellerDto) {
        return oiShipInfoService.deleteOiShipPropeller(oishipPropellerDto.getId());
    }

    @GetMapping("/getOiShipPropelerById")
    public ResultVo getOiShipPropelerById(String oiShipPropellerId) {
        return oiShipInfoService.getOiShipPropelerById(oiShipPropellerId);
    }
}
