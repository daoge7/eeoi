package cn.ccsit.eeoi.energyeefic.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "INTERFACE_REC",  catalog = "")
public class InterfaceRec {
    private String id;
    private String imono;
    private String clientCode;
    private String voyageCode;
    private Date reqTm;
    private String reqTimestamp;
    private String reqMessage;
    private Date respTm;
    private String respTimestamp;
    private String respCode;
    private String respMessage;
    private String creator;
    private Date createTm;
    private String opuser;
    private Date opdate;
    private Integer isDelete;

    public InterfaceRec() {
    }

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
    @Column(name = "IMONO")
    public String getImono() {
        return imono;
    }

    public void setImono(String imono) {
        this.imono = imono;
    }

    @Basic
    @Column(name = "CLIENT_CODE")
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @Basic
    @Column(name = "VOYAGE_CODE")
    public String getVoyageCode() {
        return voyageCode;
    }

    public void setVoyageCode(String voyageCode) {
        this.voyageCode = voyageCode;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQ_TM")
    public Date getReqTm() {
        return reqTm;
    }

    public void setReqTm(Date reqTm) {
        this.reqTm = reqTm;
    }

    @Basic
    @Column(name = "REQ_TIMESTAMP")
    public String getReqTimestamp() {
        return reqTimestamp;
    }

    public void setReqTimestamp(String reqTimestamp) {
        this.reqTimestamp = reqTimestamp;
    }

    @Basic
    @Column(name = "REQ_MESSAGE")
    public String getReqMessage() {
        return reqMessage;
    }

    public void setReqMessage(String reqMessage) {
        this.reqMessage = reqMessage;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RESP_TM")
    public Date getRespTm() {
        return respTm;
    }

    public void setRespTm(Date respTm) {
        this.respTm = respTm;
    }

    @Basic
    @Column(name = "RESP_TIMESTAMP")
    public String getRespTimestamp() {
        return respTimestamp;
    }

    public void setRespTimestamp(String respTimestamp) {
        this.respTimestamp = respTimestamp;
    }

    @Basic
    @Column(name = "RESP_CODE")
    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    @Basic
    @Column(name = "RESP_MESSAGE")
    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    @Basic
    @Column(name = "CREATOR")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TM")
    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }

    @Basic
    @Column(name = "OPUSER")
    public String getOpuser() {
        return opuser;
    }

    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPDATE")
    public Date getOpdate() {
        return opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }
    @Basic
    @Column(name = "IS_DELETE")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterfaceRec that = (InterfaceRec) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(imono, that.imono) &&
                Objects.equals(clientCode, that.clientCode) &&
                Objects.equals(voyageCode, that.voyageCode) &&
                Objects.equals(reqTm, that.reqTm) &&
                Objects.equals(reqTimestamp, that.reqTimestamp) &&
                Objects.equals(reqMessage, that.reqMessage) &&
                Objects.equals(respTm, that.respTm) &&
                Objects.equals(respTimestamp, that.respTimestamp) &&
                Objects.equals(respCode, that.respCode) &&
                Objects.equals(respMessage, that.respMessage) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTm, that.createTm) &&
                Objects.equals(opuser, that.opuser) &&
                Objects.equals(opdate, that.opdate) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, imono, clientCode, voyageCode, reqTm, reqTimestamp, reqMessage, respTm, respTimestamp, respCode, respMessage, creator, createTm, opuser, opdate, isDelete);
    }

    public InterfaceRec(String imono, String clientCode, String voyageCode, Date reqTm, String reqTimestamp, String reqMessage, Date respTm, String respTimestamp, String respCode, String respMessage, Integer isDelete) {
        this.imono = imono;
        this.clientCode = clientCode;
        this.voyageCode = voyageCode;
        this.reqTm = reqTm;
        this.reqTimestamp = reqTimestamp;
        this.reqMessage = reqMessage;
        this.respTm = respTm;
        this.respTimestamp = respTimestamp;
        this.respCode = respCode;
        this.respMessage = respMessage;
        this.isDelete = isDelete;
    }
}
