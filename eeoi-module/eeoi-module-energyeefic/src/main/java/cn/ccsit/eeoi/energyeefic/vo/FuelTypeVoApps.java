package cn.ccsit.eeoi.energyeefic.vo;

import lombok.Data;

import java.util.List;

@Data
public class FuelTypeVoApps {
    private List<FuelTypeOilVoApp> arrFuelTypes;
    private List<FuelTypeAddVoApp> addFuelTypes;
    private List<FuelTypeOutVoApp> outFuelTypes;
}
