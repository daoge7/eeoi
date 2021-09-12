package cn.ccsit.component.fileUpload;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import cn.ccsit.common.exception.ExplicitException;
import cn.ccsit.common.utils.SpringContextUtils;
import cn.ccsit.component.fileUpload.config.properties.UploadProjectProperties;

/**
 * 文件上传处理工具 
 */
public class FileUpload {

    /**
     * 创建一个FileWrapper实体对象
     * @param multipartFile MultipartFile对象
     * @param modulePath 文件模块路径
     */
    public static FileWrapper getFile(MultipartFile multipartFile, String modulePath){
        if (multipartFile.getSize() == 0){
            throw new ExplicitException("File not found");//todo by lfj 
        }
        FileWrapper fileWrapper = new FileWrapper();
        fileWrapper.setMime(multipartFile.getContentType());
        fileWrapper.setSize(multipartFile.getSize());
        fileWrapper.setName(FileUpload.genFileName(multipartFile.getOriginalFilename()));
        fileWrapper.setPath(getPathPattern() + modulePath + FileUpload.genDateMkdir("yyyyMMdd") + fileWrapper.getName());
        return fileWrapper;
    }

    /**
     * 判断文件是否为支持的格式
     * @param multipartFile MultipartFile对象
     * @param types 支持的文件类型数组
     */
    public static boolean isContentType(MultipartFile multipartFile, String[] types){
        List<String> typeList = Arrays.asList(types);
        return typeList.contains(multipartFile.getContentType());
    }

    /**
     * 获取文件上传保存路径
     */
    public static String getUploadPath(){
        UploadProjectProperties properties = SpringContextUtils.getBean(UploadProjectProperties.class);
        return properties.getFilePath();
    }

    /**
     * 获取文件上传目录的静态资源路径
     */
    public static String getPathPattern(){
        UploadProjectProperties properties = SpringContextUtils.getBean(UploadProjectProperties.class);
        //todo by lfj 
        return null;
        //return properties.getStaticPath().replace("/**", "");
    }

    /**
     * 生成随机且唯一的文件名
     */
    public static String genFileName(String originalFilename){
       //todo by lfj 
    	//String fileSuffix = ToolUtils.getFileSuffix(originalFilename);
       //todo by lfj 
    	// return UUID.randomUUID().toString().replace("-", "") + fileSuffix;
    	return null;
    }

    /**
     * 生成指定格式的目录名称(日期格式)
     */
    public static String genDateMkdir(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return "/" + sdf.format(new Date()) + "/";
    }

    /**
     * 获取目标文件对象
     * @param filePath 上传文件路径
     */
    public static File getDestFile(String filePath) throws IOException {

        // 创建保存文件对象
        String path =filePath.replace(getPathPattern(), "");
        String fullFileName = getUploadPath() + path;
        File dest = new File(fullFileName.replace("//", "/"));
        if(!dest.exists()){
            dest.getParentFile().mkdirs();
            dest.createNewFile();
        }
        return dest;
    }

    /**
     * 保存文件及获取文件MD5值和SHA1值  todo 需要在linux环境下进一步测试
     * @param multipartFile MultipartFile对象
     * @param upload Upload
     */  
    public static void transferTo(MultipartFile multipartFile, FileWrapper fileWrapper) throws IOException, NoSuchAlgorithmException {

        byte[] buffer = new byte[4096];
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        try (OutputStream fos = Files.newOutputStream(getDestFile(fileWrapper.getPath()).toPath());
        		InputStream fis = multipartFile.getInputStream()) {
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                md5.update(buffer, 0, len);
                sha1.update(buffer, 0, len);
            }
            fos.flush();
        }
        BigInteger md5Bi = new BigInteger(1, md5.digest());
        BigInteger sha1Bi = new BigInteger(1, sha1.digest());
        fileWrapper.setMd5(md5Bi.toString(16));
        fileWrapper.setSha1(sha1Bi.toString(16));
    }

    /**
     * 获取文件的SHA1值
     */
    public static String getFileSha1(MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0){
            throw new ExplicitException("File not found");
        }
        byte[] buffer = new byte[4096];
        try (InputStream fis = multipartFile.getInputStream()) {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                sha1.update(buffer, 0, len);
            }
            BigInteger sha1Bi = new BigInteger(1, sha1.digest());
            return sha1Bi.toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            return null;
        }
    }

}
