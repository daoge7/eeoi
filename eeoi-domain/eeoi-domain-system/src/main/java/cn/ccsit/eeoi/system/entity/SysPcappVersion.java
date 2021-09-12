package cn.ccsit.eeoi.system.entity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/** 
 * @Author LuHao 
 * @Date 2020-06-22 10:36:33 
 */
@Entity
@Table ( name ="SYS_PCAPP_VERSION" )
@Data
public class SysPcappVersion  implements Serializable {

	private static final long serialVersionUID =  4893456119093694863L;

	/**
	 * GUID
	 */
	@Id
   	@Column(name = "ID" )
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/**
	 * 00: 船端版系统   01: 移动版系统
	 */
   	@Column(name = "SYS_CODE" )
	private String sysCode;

   	@Column(name = "PRE_VERSION" )
	private String preVersion;

	/**
	 * 字符串型版本号，字符串值 高版本必需大于低版本
	 */
   	@Column(name = "NEW_VERSION" )
	private String newVersion;

   	@Column(name = "UP_CONTENT" )
	private String upContent;

	/**
	 * 0: 全量升级   1:增量升级
	 */
   	@Column(name = "UP_TYPE" )
	private String upType;

	/**
	 * 升级文件，只能使用.zip
	 */
   	@Column(name = "FILE_IDS" )
	private String fileIds;

   	@Column(name = "REMARK" )
	private String remark;

	/**
	 * 0：拟稿
    1：发布
    2：失效
	 */
   	@Column(name = "REC_STATUS" )
	private String recStatus;

   	@Column(name = "CREATOR" )
	private String creator;

   	@Column(name = "CREATE_TM" )
	private Date createTm;

	/**
	 * 原字段：OPERATOR
	 */
   	@Column(name = "OPUSER" )
	private String opuser;

	/**
	 * 原字段：UPDATE_TIME
	 */
   	@Column(name = "OPDATE" )
	private Date opdate;

	/**
	 * 0:正常；1:删除
	 */
   	@Column(name = "IS_DELETE" )
	private String isDelete;

	@Transient
	private String fileNames;

}
