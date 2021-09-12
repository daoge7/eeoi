package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the gc_client_cate database table.
 */
@Entity
@Table(name = "gc_client_cate")
@NamedQuery(name = "GcClientCate.findAll", query = "SELECT g FROM GcClientCate g")
public class GcClientCate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private BigDecimal code;

    @Column(name = "NAME_CN")
    private String nameCn;

    @Column(name = "NAME_EN")
    private String nameEn;

    private String pic;

    @Column(name = "REMARK_INFO")
    private String remarkInfo;

    private BigDecimal serial;

    private BigDecimal special;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    private BigDecimal valid;
//
//	@ManyToMany(targetEntity = GcClient.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "GC_CLIENT_CATE_RELA",
//			joinColumns = {@JoinColumn(name = "CATE_ID")},
//			inverseJoinColumns = {@JoinColumn(name = "CLIENT_ID")})
//	private List<GcClient> gcClients;

    @OneToMany(mappedBy = "gcClientCate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GcClientCateRela> gcClientCateRelas;

    public List<GcClientCateRela> getGcClientCateRelas() {
        return gcClientCateRelas;
    }

    public void setGcClientCateRelas(List<GcClientCateRela> gcClientCateRelas) {
        this.gcClientCateRelas = gcClientCateRelas;
    }

    public GcClientCate() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getCode() {
        return this.code;
    }

    public void setCode(BigDecimal code) {
        this.code = code;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRemarkInfo() {
        return this.remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public BigDecimal getSerial() {
        return this.serial;
    }

    public void setSerial(BigDecimal serial) {
        this.serial = serial;
    }

    public BigDecimal getSpecial() {
        return this.special;
    }

    public void setSpecial(BigDecimal special) {
        this.special = special;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public BigDecimal getValid() {
        return this.valid;
    }

    public void setValid(BigDecimal valid) {
        this.valid = valid;
    }

//	public List<GcClient> getGcClients() {
//		return gcClients;
//	}
//
//	public void setGcClients(List<GcClient> gcClients) {
//		this.gcClients = gcClients;
//	}
}