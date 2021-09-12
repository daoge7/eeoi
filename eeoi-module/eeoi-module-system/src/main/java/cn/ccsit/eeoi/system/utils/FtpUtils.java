package cn.ccsit.eeoi.system.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.util.Calendar;

@Component
@Data
public class FtpUtils {

    @Autowired
    FtpProperties ftpProperties;

    private FTPClient ftpClient = null; // FTP 客户端代理

    private static final Log log = LogFactory.getLog(FtpUtils.class);

    /**
     * 打开到FTP服务器的连接
     */
    public boolean connectServer() {

        boolean flag = true;
        int reply;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpProperties.getServer());
            ftpClient.login(ftpProperties.getUserName(), ftpProperties.getPassword());
            ftpClient.setControlEncoding("UTF-8"); // 文件名乱码,默认ISO8859-1，不支持中文
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode(); // 用被动模式传输,解决linux服务长时间等待，导致超时问
            ftpClient.setBufferSize(1024 * 1024);//设置缓存区，默认缓冲区大小是1024，也就是1K
//            System.out.println("Connected to " + ftpProperties.getServer());
            log.warn("Connected to " + ftpProperties.getServer());
//            System.out.print(ftpClient.getReplyString());
            log.warn(" FTPServer Reply:" + ftpClient.getReplyString());
            reply = ftpClient.getReplyCode();
            ftpClient.setDataTimeout(120000);

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                log.warn("FTP server refused connection.");
                flag = false;
            }

        } catch (SocketException e) {
            flag = false;
            e.printStackTrace();
            System.err.println("登录ftp服务器 " + ftpProperties.getServer() + " 失败,连接超时！");
            log.warn("登录ftp服务器 " + ftpProperties.getServer() + " 失败,连接超时！");
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
            System.err.println("登录ftp服务器 " + ftpProperties.getServer() + " 失败，FTP服务器无法打开！");
            log.warn("登录ftp服务器 " + ftpProperties.getServer() + " 失败，FTP服务器无法打开！");
        }
        System.out.println("登陆ftp服务器成功" + ftpProperties.getServer());
        log.warn("登陆ftp服务器成功" + ftpProperties.getServer());
        return flag;
    }

    public String upload2Local(String fileId, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        File localPath = new File(ftpProperties.getLocalPath());
        if (!localPath.exists() && !localPath.isDirectory()) {
            log.error("本地临时目录" + ftpProperties.getLocalPath() + "不存在");
            localPath.mkdirs();
        }
        String fileNameTemp = fileId + suffix;
        try {
            File dest = new File(ftpProperties.getLocalPath() + fileNameTemp);
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNameTemp;
    }

    /**
     * 将文件从应用服务器上传到FTP服务器
     */
    public String upFile(String fileName) {
        connectServer();
        FileInputStream in = null;
        try {
            log.warn("ftpinfo.properties FTP Path :" + ftpProperties.getFtpPath());
            log.warn("ftpinfo.properties localPath :" + ftpProperties.getLocalPath());
            in = new FileInputStream(new File(ftpProperties.getLocalPath() + "/" + fileName));
            String ftpDir = ftpProperties.getFtpPath() + getYearMonthStr();
            String[] paths = ftpDir.split("/");
            int len = paths.length;
            for (int i = 0; i < len; i++) {
                ftpClient.changeWorkingDirectory(paths[i]);
                if (ftpClient.getReplyCode() != 250) {
                    ftpClient.makeDirectory(paths[i]);
                    ftpClient.changeWorkingDirectory(paths[i]);
                }
            }
            ftpClient.storeFile(fileName, in);
            log.warn("storeFile Completed");
            in.close();
//            return ftpClient.getReplyCode() == 226;
            return ftpDir;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("upload error", e);
            return null;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("disconnect fail ------->>>{}", e.getCause());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("outputStream close fail ------->>>{}", e.getCause());
                }
            }
        }
    }

    public String uploadVersionFile(String fileName, String fileType) {
        connectServer();
        FileInputStream in = null;
        String dirStr;
        try {
            log.warn("ftpinfo.properties FTP Path :" + ftpProperties.getFtpPath());
            log.warn("ftpinfo.properties localPath :" + ftpProperties.getLocalPath());
            in = new FileInputStream(new File(ftpProperties.getLocalPath() + "/" + fileName));
            if ("01".equals(fileType)) {
                dirStr = "eemsUpgrade/pc";
            } else if ("02".equals(fileType)) {
                dirStr = "eemsUpgrade/app";
            } else {
                dirStr = "";
            }
            String ftpDir = ftpProperties.getFtpPath() + dirStr;
            String[] paths = ftpDir.split("/");
            int len = paths.length;
            for (int i = 0; i < len; i++) {
                boolean chagenDirFlag = ftpClient.changeWorkingDirectory(paths[i]);
                if (chagenDirFlag == false) {
                    ftpClient.makeDirectory(paths[i]);
                    ftpClient.changeWorkingDirectory(paths[i]);
                }
            }
            ftpClient.storeFile(fileName, in);
            log.warn("storeFile Completed");
            in.close();
//            return ftpClient.getReplyCode() == 226;
            return ftpDir;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("upload error", e);
            return null;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("disconnect fail ------->>>{}", e.getCause());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("outputStream close fail ------->>>{}", e.getCause());
                }
            }
        }
    }

    public void downloadFile(String ftpPath, String fileName, HttpServletResponse response) {
        download2Local(StringUtils.substringBeforeLast(ftpPath, "/"), StringUtils.substringAfterLast(ftpPath, "/"));
        try {
            String type = new MimetypesFileTypeMap().getContentType(fileName);
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", type);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        downloadFile(StringUtils.substringAfterLast(ftpPath, "/"), response);
    }

    /**
     * 从FTP服务器将文件下载到应用服务器
     */
    public void download2Local(String remoteFilePath, String fileName) {
        connectServer();
        if (ftpClient == null) {
            return;
        }
        OutputStream outputStream = null;
        try {
            ftpClient.changeWorkingDirectory(StringUtils.remove(remoteFilePath, fileName));
            FTPFile[] ftpFiles = ftpClient.listFiles(fileName);
            Boolean flag = false;
            //遍历当前目录下的文件，判断是否存在待下载的文件
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.getName().equals(fileName)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                log.error("directory：{" + remoteFilePath + "}下没有 {" + fileName + "}");
                return;
            }
            File file = new File(ftpProperties.getLocalPath());
            if (!file.exists() && !file.isDirectory()) {
                log.error("本地临时目录" + ftpProperties.getLocalPath() + "不存在");
                file.mkdirs();
            }
            outputStream = new FileOutputStream(ftpProperties.getLocalPath() + fileName);//创建文件输出流
            Boolean isSuccess = ftpClient.retrieveFile(remoteFilePath + "/" + fileName, outputStream); //下载文件
            if (!isSuccess) {
                log.error("download file 【{" + fileName + "}】 fail");
            }
            log.info("download file success");
            ftpClient.logout();
        } catch (IOException e) {
            log.error("download file 【{" + remoteFilePath + "}】 fail ------->>>{" + e.getCause() + "}");
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("disconnect fail ------->>>{}", e.getCause());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close fail ------->>>{}", e.getCause());
                }
            }
        }
    }


    /**
     * 删除FTP服务器上面的文件
     */
    public void deleteFtpUtils(String filePathAndName) {
        try {
            log.warn("deleteFtpUtils");
            log.warn("deleteFtpUtils path:" + ftpProperties.getFtpPath());
            log.warn("deleteFtpUtils localPath:" + ftpProperties.getLocalPath());
            log.warn("deleteFtpUtils completed path:" + ftpProperties.getFtpPath());
            ftpClient.deleteFile(filePathAndName);
            log.warn("deleteFtpUtils file:" + filePathAndName);
            log.warn("deleteFtpUtils over");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("deleteFtpUtils error", e);
        }
    }

    public void remove(String oldPath, String newPath, String[] fileNames) {
        FtpUtils[] fs;

        try {
            if (newPath.startsWith("/")) {
                newPath = newPath.substring(1);
            } else {
                String[] paths = newPath.split("/");
                int len = paths.length;
                for (int i = 0; i < len; i++) {
                    ftpClient.changeWorkingDirectory(paths[i]);
                    if (ftpClient.getReplyCode() != 250) {
                        ftpClient.makeDirectory(paths[i]);
                        ftpClient.changeWorkingDirectory(paths[i]);
                    }
                }
            }

            ftpClient.changeWorkingDirectory("");
            if (fileNames != null) {
                for (int j = 0; j < fileNames.length; j++) {
                    ftpClient.rename(oldPath + fileNames[j], newPath + fileNames[j]);
                    log.warn("rename: -pre " + oldPath + fileNames[j] + ",-after " + newPath + fileNames[j]);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("FtpUtils remove error", e);
        }
    }

    /**
     * 当用户将文件上传到FTP上后，删除应用服务器上的文件
     */
    public boolean deleteLocalFile(String filePath, String fileName) {
        boolean flag = false;

        try {
            log.warn("delete server file path:" + ftpProperties.getFtpPath());
            String localFile = ftpProperties.getLocalPath() + filePath + fileName;
            log.warn("delete server file file:" + localFile);
            File file = new File(localFile);
            if (!file.exists()) {
                return flag;
            }
            if (file.isFile()) {
                file.delete();
                log.warn("delete server file over");
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("delete server file error", e);
            return flag;
        }
        return flag;
    }

    /**
     * 关闭到FTP服务器的连接
     */
    public void closeConnect() {
        System.out.println("关闭ftp服务器");
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            log.warn("ftp close !");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("close error!");
        }
    }

    public String getYearMonthStr() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        return year + "-" + month;
    }

    /**
     * HTTP下载文件
     */

    public void downloadFile(String fileName, HttpServletResponse res) {
        // 发送给客户端的数据
        try {
            OutputStream outputStream = res.getOutputStream();
            byte[] buff = new byte[1024];
            FileInputStream bis = null;
            // 读取filename
            bis = new FileInputStream(new File(ftpProperties.getLocalPath() + fileName));
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = bis.read(b)) > 0) {
                outputStream.write(b, 0, i);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
