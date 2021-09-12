package cn.ccsit.eeoi.energyeefic.service;



import cn.ccsit.eeoi.energyeefic.entity.OiShipVoyage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CommonEnergyEfficService {
    /**
     * 计算航段的二氧化碳排放量
     * @param oiShipVoyages
     * @return
     */
     BigDecimal getVoyageCo2cost(List<OiShipVoyage> oiShipVoyages);

    /**
     * 计算航段的二氧化碳排放量(EU)
     * @param oiShipVoyages
     * @return
     */
    BigDecimal getVoyageCo2costEu(List<OiShipVoyage> oiShipVoyages);

    /**
     * 硫氧化物排放量
     * @param rawVoyagePortoils
     * @return
     */
     BigDecimal getVoyageSo2cost(List<Map<String, BigDecimal>> rawVoyagePortoils);

    /**
     * 获取每一航段的运输功能
     * @param oiShipVoyages
     * @return
     */
     Map<String,BigDecimal> getVoyageEeoiTd(List<OiShipVoyage> oiShipVoyages);
    /**
     * 获取每一航段speedDistance
     * @param oiShipVoyages
     * @return
     */
     BigDecimal getVoyageSpeedDistance(List<OiShipVoyage> oiShipVoyages);

    /**
     * 获取航段降速比分母
     * @param oiShipVoyages
     * @return
     */
     BigDecimal getVoyageserviceSpeedDistance(List<OiShipVoyage> oiShipVoyages, BigDecimal serviceSpeed);

    BigDecimal getdwtDistance(List<OiShipVoyage> oiShipVoyages, BigDecimal dwt);
}
