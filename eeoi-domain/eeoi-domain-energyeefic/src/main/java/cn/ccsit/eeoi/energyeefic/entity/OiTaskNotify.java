package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OI_TASK_NOTIFY", catalog = "")
public class OiTaskNotify {
    private String id;
    private String shipId;
    private String taskId;
    private Integer segmentType;


    private Date createTm;

    public OiTaskNotify(String shipId, String taskId, Integer segmentType,Integer status) {
        this.shipId = shipId;
        this.taskId = taskId;
        this.segmentType = segmentType;
        this.status = status;
    }

    public OiTaskNotify() {

    }

    private Date finishTm;
    private Integer status;
    private Integer ver;
    private String result;
    private Integer isDelete;
    private Long usedTm;
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Basic
    @Column(name = "ship_id")
    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }
    @Basic
    @Column(name = "task_id")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @Basic
    @Column(name = "segment_type")
    public Integer getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(Integer segmentType) {
        this.segmentType = segmentType;
    }
    @Basic
    @Column(name = "create_tm")
    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }
    @Basic
    @Column(name = "finish_tm")
    public Date getFinishTm() {
        return finishTm;
    }

    public void setFinishTm(Date finishTm) {
        this.finishTm = finishTm;
    }
    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @Basic
    @Column(name = "ver")
    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }
    @Basic
    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    @Basic
    @Column(name = "IS_DELETE")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "USED_TM")
    public Long getUsedTm() {
        return usedTm;
    }

    public void setUsedTm(Long usedTm) {
        this.usedTm = usedTm;
    }


}
