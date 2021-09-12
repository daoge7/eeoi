package cn.ccsit.eeoi.system.enums;

import lombok.Getter;
 
@Getter
public enum ActionLogEnum {

    /**
     * 业务日志行为
     */
    BUSINESS((Integer) 1, "业务"),
    /**
     * 用户登录日志行为
     */
    LOGIN((Integer) 2, "登录"),
    /**
     * 系统日志行为（报错信息）
     */
    SYSTEM((Integer) 3, "系统");

    private Integer code;

    private String message;

    ActionLogEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
