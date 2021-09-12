package cn.ccsit.eeoi.ship.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the gc_client_cate_rela database table.
 */
@Entity
@Table(name = "gc_client_cate_rela")
@NamedQuery(name = "GcClientCateRela.findAll", query = "SELECT g FROM GcClientCateRela g")
public class GcClientCateRela implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATE_ID")
    private GcClientCate gcClientCate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private GcClient gcClient;

    @Column(name = "TYPE")
    private BigDecimal type;

    public GcClientCateRela() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getType() {
        return this.type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    public void setGcClientCate(GcClientCate gcClientCate) {
        this.gcClientCate = gcClientCate;
    }

    public void setGcClient(GcClient gcClient) {
        this.gcClient = gcClient;
    }

    public GcClientCate getGcClientCate() {
        return gcClientCate;
    }

    public GcClient getGcClient() {
        return gcClient;
    }
}