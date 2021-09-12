package cn.ccsit.eeoi.system.entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @Author LuHao
 * @Date 2020-06-10 10:47:46
 */
@Entity
@Table ( name ="SYS_FILE" )
public class SysFile  implements Serializable {

	private static final long serialVersionUID =  2803850829183602331L;

	/**
	 * 主键ID,GUID
	 */
	@Id
	@Column(name = "ID" )
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/**
	 * 文件名
	 */
	@Column(name = "NAME" )
	private String name;

	/**
	 * 文件路径
	 相对contxt根路径
	 */
	@Column(name = "PATH" )
	private String path;

	/**
	 * MIME文件类型
	 */
	@Column(name = "MIME" )
	private String mime;

	/**
	 * 文件大小
	 */
	@Column(name = "FILE_SIZE" )
	private String fileSize;

	/**
	 * MD5值
	 */
	@Column(name = "MD5" )
	private String md5;

	/**
	 * SHA1值
	 */
	@Column(name = "SHA1" )
	private String sha1;

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

	@Column(name = "FILE_PATH" )
	private String filePath;
	@Transient
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}


	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}


	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}


	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
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


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
