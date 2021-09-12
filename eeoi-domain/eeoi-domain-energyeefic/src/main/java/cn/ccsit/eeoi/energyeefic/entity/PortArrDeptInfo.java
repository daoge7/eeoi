package cn.ccsit.eeoi.energyeefic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * All copyright (c) 2021  CCSE
 *
 * @author liu ZhenDong
 * create on 2021/3/31 17:35
 */

@Entity(name="MyPort")
@Data
public class PortArrDeptInfo {

    @Id
    @Column(name = "ID")
    private String id;
    //    private String taskId;
//    private String taskCode;
//    @Column(name = "")
    private String portcn;
    private String porten;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ARR_TM")
    private LocalDateTime arrTm;

    @Column(name = "task")
    private String task;

    @Column(name = "ARR_ZONE")
    private BigDecimal arrZone;
    @Column(name = "DISTANCE")
    private BigDecimal distance;

    @Column(name = "DEPT_TM")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deptTm;
    @Column(name = "DEPT_ZONE")
    private BigDecimal deptZone;
    @Column(name = "IN_PORT")
    private String inPort;

//    //  抵港离港 重油 轻油 柴汽油
    @Column(name = "arr_port_lfo")
    private BigDecimal arrPortLfo;
    @Column(name = "dept_port_lfo")
    private BigDecimal deptPortLfo;
    @Column(name = "arr_port_hfo")
    private BigDecimal arrPortHfo;
    @Column(name = "dept_port_hfo")
    private BigDecimal deptPortHfo;
    @Column(name = "arr_port_m")
    private BigDecimal arrPortM;
    @Column(name = "dept_port_m")
    private BigDecimal deptPortM;
//
//    // 加油 重油 轻油 柴汽油
    @Column(name = "add_lfo")
    private BigDecimal addLfo;
    @Column(name = "add_hfo")
    private BigDecimal addHfo;
    @Column(name = "add_m")
    private BigDecimal addM;
//
//
//    // 驳油 重油 轻油 柴汽油
    @Column(name = "out_lfo")
    private BigDecimal outLfo;
    @Column(name = "out_hfo")
    private BigDecimal outHfo;
    @Column(name = "out_m")
    private BigDecimal outM;
//
//    // 载荷
    @Column(name = "arr_tons")
    private BigDecimal arrTons;
    @Column(name = "dept_tons")
    private BigDecimal deptTons;
}
