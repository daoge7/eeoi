package cn.ccsit.component.fileUpload;


public class FileWrapper {

	public FileWrapper() {
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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
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

	/** 文件名 */
	private String name;

	/** 文件路径 */
	private String path;

	/** 文件类型 */
	private String mime;

	/** 文件大小 */
	private Long size;

	/** 文件md5值 */
	private String md5;

	/** 文件sha1值 */
	private String sha1;
}
