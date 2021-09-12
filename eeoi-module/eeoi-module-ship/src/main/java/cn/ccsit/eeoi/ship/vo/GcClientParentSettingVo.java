package cn.ccsit.eeoi.ship.vo;

import java.util.List;

/**
 * @program: eeoi
 * @description: 设置集团子公司VO
 * @author: luhao
 * @create: 2020-04-22 15:52
 */
public class GcClientParentSettingVo {
    private String id;
    private List<String> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
