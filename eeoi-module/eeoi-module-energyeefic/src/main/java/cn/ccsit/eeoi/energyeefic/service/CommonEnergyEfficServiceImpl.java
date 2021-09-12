package cn.ccsit.eeoi.energyeefic.service;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.energyeefic.entity.OiShipVoyage;
import cn.ccsit.eeoi.energyeefic.entity.RawVoyagePort;
import cn.ccsit.eeoi.energyeefic.properties.FuelCo2CostProperties;
import cn.ccsit.eeoi.energyeefic.properties.FuelSo2CostProperties;
import cn.ccsit.eeoi.energyeefic.properties.FuelTanCostProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonEnergyEfficServiceImpl extends CommonServiceImpl implements CommonEnergyEfficService {
    @Autowired
    private FuelCo2CostProperties fuelCo2CostProperties;
    @Autowired
    private FuelTanCostProperties fuelTanCostProperties;
    @Autowired
    private FuelSo2CostProperties fuelSo2CostProperties;

    public BigDecimal getDistance(List<OiShipVoyage> oiShipVoyages){
        BigDecimal distance = new BigDecimal(0);
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            distance = distance.add(oiShipVoyages.get(i).getDistance());
        }
        return distance;
    }

    @Override
    public BigDecimal getVoyageCo2cost(List<OiShipVoyage> oiShipVoyages) {
        BigDecimal co2cost = new BigDecimal(0);
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            co2cost = co2cost.add(fuelCo2CostProperties.getHfo().multiply(oiShipVoyage.getOiHfo().add(oiShipVoyage.getStopoiHfo())))
                    .add(fuelCo2CostProperties.getLfo().multiply(oiShipVoyage.getOiLfo().add(oiShipVoyage.getStopoiLfo())))
                    .add(fuelCo2CostProperties.getOiChaiOrOiQi().multiply(oiShipVoyage.getOiChai().add(oiShipVoyage.getStopoiChai())))
                    .add(fuelCo2CostProperties.getPropane().multiply(oiShipVoyage.getOiBing().add(oiShipVoyage.getStopoiBing())))
                    .add(fuelCo2CostProperties.getLng().multiply(oiShipVoyage.getOiTian().add(oiShipVoyage.getStopoiTian())))
                    .add(fuelCo2CostProperties.getButane().multiply(oiShipVoyage.getOiDing().add(oiShipVoyage.getStopoiDing())));
        }
        return co2cost;
    }

    @Override
    public BigDecimal getVoyageCo2costEu(List<OiShipVoyage> oiShipVoyages) {
        BigDecimal co2cost = new BigDecimal(0);
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            co2cost = co2cost.add(BigDecimal.valueOf(3.11400).multiply(oiShipVoyage.getOiHfo()))
                    .add(BigDecimal.valueOf(3.1510).multiply(oiShipVoyage.getOiLfo()))
                    .add(BigDecimal.valueOf(3.206000).multiply(oiShipVoyage.getOiChai()))
                    .add(BigDecimal.valueOf(3.000000).multiply(oiShipVoyage.getOiBing()))
                    .add(BigDecimal.valueOf(2.750000).multiply(oiShipVoyage.getOiTian()))
                    .add(BigDecimal.valueOf(3.030000).multiply(oiShipVoyage.getOiDing()));
        }
        return co2cost;
    }

    public BigDecimal getVoyageTancost(List<OiShipVoyage> oiShipVoyages) {
        BigDecimal tanCost = new BigDecimal(0);
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            tanCost = tanCost.add(fuelTanCostProperties.getHfo().multiply(oiShipVoyage.getOiHfo().add(oiShipVoyage.getStopoiHfo())))
                    .add(fuelTanCostProperties.getLfo().multiply(oiShipVoyage.getOiLfo().add(oiShipVoyage.getStopoiLfo())))
                    .add(fuelTanCostProperties.getOiChaiOrOiQi().multiply(oiShipVoyage.getOiChai().add(oiShipVoyage.getStopoiChai())))
                    .add(fuelTanCostProperties.getPropane().multiply(oiShipVoyage.getOiBing().add(oiShipVoyage.getStopoiBing())))
                    .add(fuelTanCostProperties.getLng().multiply(oiShipVoyage.getOiTian().add(oiShipVoyage.getStopoiTian())))
                    .add(fuelTanCostProperties.getButane().multiply(oiShipVoyage.getOiDing().add(oiShipVoyage.getStopoiDing())));
        }
        return tanCost;
    }

    @Override
    public BigDecimal getVoyageSo2cost(List<Map<String,BigDecimal>> rawVoyagePortoils) {
        BigDecimal so2cost = new BigDecimal(0);
        BigDecimal end = new BigDecimal(0);
        for (int i = 0; i < rawVoyagePortoils.size(); i++) {
            so2cost =   so2cost.add(rawVoyagePortoils.get(i).get("超硫重").multiply(fuelSo2CostProperties.getHSHFO()))
                    .add(rawVoyagePortoils.get(i).get("低硫重").multiply(fuelSo2CostProperties.getLSHFO()))
                    .add(rawVoyagePortoils.get(i).get("超低硫重").multiply(fuelSo2CostProperties.getSLSHFO()))
                    .add(rawVoyagePortoils.get(i).get("超硫轻").multiply(fuelSo2CostProperties.getHSHFO()))
                    .add(rawVoyagePortoils.get(i).get("低硫轻").multiply(fuelSo2CostProperties.getLSHFO()))
                    .add(rawVoyagePortoils.get(i).get("超低硫轻").multiply(fuelSo2CostProperties.getHSHFO()));
        }
        return so2cost;
    }

    @Override
    public Map<String,BigDecimal> getVoyageEeoiTd(List<OiShipVoyage> oiShipVoyages) {
         BigDecimal eeoiTd = new BigDecimal(0);
         BigDecimal teuDistance = new BigDecimal(0);
         BigDecimal peopleDistance = new BigDecimal(0);
         BigDecimal carDistance = new BigDecimal(0);
        BigDecimal zero = BigDecimal.ZERO;
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            OiShipVoyage oiShipVoyage = oiShipVoyages.get(i);
            BigDecimal cargo = oiShipVoyage.getCargo();
            BigDecimal distance = oiShipVoyage.getDistance();
            Integer allNum = oiShipVoyage.getAllNum();
            Integer peopleNum = oiShipVoyage.getPeopleNum();
            Integer carNum = oiShipVoyage.getCarNum();
            if(cargo == null){
                cargo = zero;
            }
            if(distance == null){
                distance = zero;
            }
            if(allNum == null){
                allNum = 0;
            }
            if(peopleNum == null){
                peopleNum = 0;
            }
            if(carNum == null){
                carNum = 0;
            }
            eeoiTd = eeoiTd.add(cargo.multiply(distance));
            teuDistance = teuDistance.add(BigDecimal.valueOf(allNum).multiply(distance));
            peopleDistance = peopleDistance.add(BigDecimal.valueOf(peopleNum).multiply(distance));
            carDistance = carDistance.add(BigDecimal.valueOf(carNum).multiply(distance));
        }
        Map<String,BigDecimal> map = new HashMap<>();
        map.put("eeoiTd",eeoiTd);
        map.put("teuDistance",teuDistance);
        map.put("peopleDistance",peopleDistance);
        map.put("carDistance",carDistance);
        return map;
    }


    @Override
    public BigDecimal getVoyageSpeedDistance(List<OiShipVoyage> oiShipVoyages) {
        BigDecimal speedDistance = new BigDecimal(0);
        for (int i = 0; i < oiShipVoyages.size(); i++) {
            speedDistance = speedDistance.add(oiShipVoyages.get(i).getAvgspeed().multiply(oiShipVoyages.get(i).getDistance()));
        }
        return speedDistance;
    }

    @Override
    public BigDecimal getVoyageserviceSpeedDistance(List<OiShipVoyage> oiShipVoyages, BigDecimal serviceSpeed) {
        BigDecimal serviceDistance = new BigDecimal(0);
        if(!( serviceSpeed == null || serviceSpeed.compareTo(BigDecimal.ZERO)==0)){
            for (int i = 0; i < oiShipVoyages.size(); i++) {
                BigDecimal distance = oiShipVoyages.get(i).getDistance();
                if(distance == null){
                    distance = BigDecimal.ZERO;
                }
                serviceDistance = serviceDistance.add(serviceSpeed.multiply(distance));
            }
        }
        return serviceDistance;
    }


    @Override
    public BigDecimal getdwtDistance(List<OiShipVoyage> oiShipVoyages, BigDecimal dwt) {
        BigDecimal dwtDistance = new BigDecimal(0);
        if(null != dwt && dwt.compareTo(BigDecimal.ZERO)>0){
            for (int i = 0; i < oiShipVoyages.size(); i++) {
                dwtDistance = dwtDistance.add(dwt.multiply(oiShipVoyages.get(i).getDistance()));
            }
        }
        return dwtDistance;
    }
}
