package cn.ccsit.eeoi.ship.vo;

import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.entity.GcClientCate;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GcClientCateVo {

    private String id;

    private String nameCn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public GcClientCateVo(GcClientCate gcClientCate){
        this.id = gcClientCate.getId();
        this.nameCn = gcClientCate.getNameCn();
    }
}
