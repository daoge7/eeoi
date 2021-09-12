package cn.ccsit.eeoi.portal.security.shiro.db;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

/**
 * 构造此类目的是redis 缓存时需要序列化，并且需要有缺省构造函数
 * @author lfj
 *
 */
public class CustomByteSource implements org.apache.shiro.util.ByteSource, Serializable {

	private static final long serialVersionUID = 6115626810034580999L;

	private final byte[] bytes;
	private String cachedHex;
	private String cachedBase64;

	public CustomByteSource(){
		this.bytes=null;
	}
	
	public CustomByteSource(byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * Creates an instance by converting the characters to a byte array (assumes
	 * UTF-8 encoding).
	 *
	 * @param chars the source characters to use to create the underlying byte
	 *              array.
	 * @since 1.1
	 */
	public CustomByteSource(char[] chars) {
		this.bytes = CodecSupport.toBytes(chars);
	}

	/**
	 * Creates an instance by converting the String to a byte array (assumes UTF-8
	 * encoding).
	 *
	 * @param string the source string to convert to a byte array (assumes UTF-8
	 *               encoding).
	 * @since 1.1
	 */
	public CustomByteSource(String string) {
		this.bytes = CodecSupport.toBytes(string);
	}

	/**
	 * Creates an instance using the sources bytes directly - it does not create a
	 * copy of the argument's byte array.
	 *
	 * @param source the source to use to populate the underlying byte array.
	 * @since 1.1
	 */
	public CustomByteSource(ByteSource source) {
		this.bytes = source.getBytes();
	}

	/**
	 * Creates an instance by converting the file to a byte array.
	 *
	 * @param file the file from which to acquire bytes.
	 * @since 1.1
	 */
	public CustomByteSource(File file) {
		this.bytes = new BytesHelper().getBytes(file);
	}

	/**
	 * Creates an instance by converting the stream to a byte array.
	 *
	 * @param stream the stream from which to acquire bytes.
	 * @since 1.1
	 */
	public CustomByteSource(InputStream stream) {
		this.bytes = new BytesHelper().getBytes(stream);
	}

	/**
	 * Returns {@code true} if the specified object is a recognized data type that
	 * can be easily converted to bytes by instances of this class, {@code false}
	 * otherwise.
	 * <p/>
	 * This implementation returns {@code true} IFF the specified object is an
	 * instance of one of the following types:
	 * <ul>
	 * <li>{@code byte[]}</li>
	 * <li>{@code char[]}</li>
	 * <li>{@link ByteSource}</li>
	 * <li>{@link String}</li>
	 * <li>{@link File}</li></li>{@link InputStream}</li>
	 * </ul>
	 *
	 * @param o the object to test to see if it can be easily converted to bytes by
	 *          instances of this class.
	 * @return {@code true} if the specified object can be easily converted to bytes
	 *         by instances of this class, {@code false} otherwise.
	 * @since 1.2
	 */
	public static boolean isCompatible(Object o) {
		return o instanceof byte[] || o instanceof char[] || o instanceof String || o instanceof ByteSource
				|| o instanceof File || o instanceof InputStream;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public boolean isEmpty() {
		return this.bytes == null || this.bytes.length == 0;
	}

	public String toHex() {
		if (this.cachedHex == null) {
			this.cachedHex = Hex.encodeToString(getBytes());
		}
		return this.cachedHex;
	}

	public String toBase64() {
		if (this.cachedBase64 == null) {
			this.cachedBase64 = Base64.encodeToString(getBytes());
		}
		return this.cachedBase64;
	}

	public String toString() {
		return toBase64();
	}

	public int hashCode() {
		if (this.bytes == null || this.bytes.length == 0) {
			return 0;
		}
		return Arrays.hashCode(this.bytes);
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof ByteSource) {
			ByteSource bs = (ByteSource) o;
			return Arrays.equals(getBytes(), bs.getBytes());
		}
		return false;
	}

	private static final class BytesHelper extends CodecSupport {
		public byte[] getBytes(File file) {
			return toBytes(file);
		}

		public byte[] getBytes(InputStream stream) {
			return toBytes(stream);
		}
	}

}