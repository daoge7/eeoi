package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.system.vo.LogVo;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service("logService")
public class LogServiceImpl extends CommonServiceImpl implements LogService {


    @Override
    public ResultVo findAllLogFiles(String logFilePath, int pageNum, int pageSize) {
        List<File> fileList = new ArrayList<>(FileUtils.listFiles(new File(logFilePath), null, false));
        Collections.reverse(fileList);
        List<LogVo> logVos = new ArrayList<>();
        for (int i = (pageNum - 1) * pageSize; i < pageNum * pageSize && i<fileList.size(); i++) {
            LogVo logVo = new LogVo();
            logVo.setName(fileList.get(i).getName());
            Long time = fileList.get(i).lastModified();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            logVo.setCreateDate(cal.getTime().toLocaleString());
            try {
                logVo.setContent(FileUtils.readFileToString(fileList.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            logVos.add(logVo);
        }
        long total = fileList.size();
        return new ResultPageVo(total, logVos);
    }

    public static void main(String[] args) {
//        File file = new File("D:\\logs\\logs_2020-06-17 15-38.log");
//        try {
//            String content = FileUtils.readFileToString(file);
//            System.out.println(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<File> fileList = new ArrayList<>(FileUtils.listFiles(new File("D:\\logs"), null, false));
        for (int i = 0; i < fileList.size(); i++) {
            LogVo logVo = new LogVo();
            logVo.setName(fileList.get(i).getName());
            Long time = fileList.get(i).lastModified();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            logVo.setCreateDate(cal.getTime().toLocaleString());
            try {
                logVo.setContent(FileUtils.readFileToString(fileList.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
