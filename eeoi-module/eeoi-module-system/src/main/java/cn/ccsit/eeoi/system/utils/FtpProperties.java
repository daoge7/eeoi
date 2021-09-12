package cn.ccsit.eeoi.system.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ftp")
@Data
public class FtpProperties {
    public String userName; // FTP 登录用户名

    public String password; // FTP 登录密码

    public String server; // FTP 服务器地址IP地址

    public String ftpPath; // FTP 服务器文件路径

    public String localPath; // 本地服务器文件路径

    public String devProdPath; //开发环境和生产环境的URL前缀
}
