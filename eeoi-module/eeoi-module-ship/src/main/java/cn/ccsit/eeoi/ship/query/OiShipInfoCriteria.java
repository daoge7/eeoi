package cn.ccsit.eeoi.ship.query;

import cn.ccsit.common.query.Query;
import cn.ccsit.common.vo.page.PageVo;
import lombok.Data;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class OiShipInfoCriteria extends PageVo {
    private String sort;
    @Query
    private Integer isDelete = 0;
    /**
     * 模糊查询关键字

     */
    @Query(blurry = "spname,spnameCn,imono,registerno")
    private String shipKeyWord;
    /**
     * 船型
     */
    @Query
    private String sptype;
    /**
     * 船型细分
     */
    @Query
    private String sptypeSub;
    /**
     * 根据船公司进行模糊查询
     */
    @Query(blurry = "ownerId,operatorId,docId,builderId")
    private String shipComPanyName;
    /**
     * 是否是内河机构
     */
    @Query
    private Integer isCmsa;

    @Query(propName = "id",type = Query.Type.IN)
    private Set<String> organs;

    @Query(propName = "id",type = Query.Type.IN)
    private Set<String> roleShipIds;
    /**
     * 内河机构Id
     */
    private String clientId;
    /**
     * 吨位范围
     */
    @Query(type = Query.Type.BETWEEN)
    private List<BigDecimal> dw;
    /**
     * 航速范围
     */
    @Query(type = Query.Type.BETWEEN)
    private List<BigDecimal> serviceSpeed;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getShipKeyWord() {
        return shipKeyWord;
    }

    public void setShipKeyWord(String shipKeyWord) {
        this.shipKeyWord = shipKeyWord;
    }

    public String getSptype() {
        return sptype;
    }

    public Integer getIsCmsa() {
        return isCmsa;
    }

    public void setIsCmsa(Integer isCmsa) {
        this.isCmsa = isCmsa;
    }

    public void setSptype(String sptype) {
        this.sptype = sptype;

    }

    public String getSptypeSub() {
        return sptypeSub;
    }

    public void setSptypeSub(String sptypeSub) {
        this.sptypeSub = sptypeSub;
    }

    public String getShipComPanyName() {
        return shipComPanyName;
    }

    public void setShipComPanyName(String shipComPanyName) {
        this.shipComPanyName = shipComPanyName;
    }


    public Set<String> getOrgans() {
        return organs;
    }

    public void setOrgans(Set<String> organs) {
        this.organs = organs;
    }

    public Set<String> getRoleShipIds() {
        return roleShipIds;
    }

    public void setRoleShipIds(Set<String> roleShipIds) {
        this.roleShipIds = roleShipIds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<BigDecimal> getDw() {
        return dw;
    }

    public void setDw(List<BigDecimal> dw) {
        this.dw = dw;
    }

    public List<BigDecimal> getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(List<BigDecimal> serviceSpeed) {
        this.serviceSpeed = serviceSpeed;
    }



}
