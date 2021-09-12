package cn.ccsit.eeoi.system.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author LuHao
 * @Date 2020-06-09 11:52:21
 */
@Entity
@Table(name = "SYS_DOC_NOTICE")
public class SysDocNotice implements Serializable {

    private static final long serialVersionUID = 1024903774746871478L;

    /**
     * GUID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "DOC_TITLE")
    private String docTitle;

    /**
     * 0:规范公约
     * 1:通函通告
     * 2:系统手册
     * 3:其他资料
     * 4:app软件
     * 9:系统公告
     */
    @Column(name = "DOC_TYPE")
    private String docType;

    @Column(name = "DOC_NUMBER")
    private String docNumber;

    @Column(name = "CONTENT")
    private String content;

    /**
     * UPLOAD_FILES.id，多文件时逗号间隔
     */
    @Column(name = "FILE_IDS")
    private String fileIds;

    @Column(name = "REMARK")
    private String remark;

    /**
     * 0：拟稿
     * 1：发布
     * 2：失效
     */
    @Column(name = "REC_STATUS")
    private String recStatus;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATE_TM")
    private Date createTm;

    /**
     * 原字段：OPERATOR
     */
    @Column(name = "OPUSER")
    private String opuser;

    /**
     * 原字段：UPDATE_TIME
     */
    @Column(name = "OPDATE")
    private Date opdate;

    /**
     * 0:正常；1:删除
     */
    @Column(name = "IS_DELETE")
    private String isDelete;

    @Transient
    private String fileNames;

    @Transient
    private List fileList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }


    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public List getFileList() {
        return fileList;
    }

    public void setFileList(List fileList) {
        this.fileList = fileList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }


    public String getOpuser() {
        return opuser;
    }

    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }


    public Date getOpdate() {
        return opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }


    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

}
