package cn.ccsit.common.constant;

/**
 * 超级管理员常量 
 */
public interface FuelConst {

    /**
     * 油种code表
     */
     String HFO_CODE = "1,11,12";
     String HFO_HIGHT = "1";
     String HFO_HIGHT_NAME = "重燃油HFO(高硫)";
     String HFO_NAME = "重燃油HFO";
     String LFO_HIGHT_NAME = "轻燃油LFO(高硫)";
     String LFO_NAME = "轻燃油LFO";
     String HFO_LOW = "11";
     String HFO_CHAO_LOW = "12";
     String LFO_HIGHT = "2";
     String LFO_LOW = "21";
     String LFO_CHAO_LOW = "22";
     String LFO_CODE = "2,21,22";
     String CHAI_CODE = "3";
     String CHAI_CODE_NAME = "柴油/汽油";
     String BING_CODE = "4";
     String BING_NAME = "液化石油气LPG（丙烷）";
     String TIAN_CODE = "6";
     String TIAN_CODE_NAME = "液化天然气LNG";
     String DING_CODE = "5";
     String DING_NAME = "液化石油气LPG（丁烷）";
     String METHAN_CODE = "7";
     String METHAN_NAME = "甲醇";
     String OIETHAN_CODE = "8";
     String OIETHAN_NAME = "乙醇";
     String FUEL_METHOD = "使用燃油交付单的方法";
     String ALL_OIL_ID = "1,11,12,2,21,22,3,4,5,6,7,8";
}
