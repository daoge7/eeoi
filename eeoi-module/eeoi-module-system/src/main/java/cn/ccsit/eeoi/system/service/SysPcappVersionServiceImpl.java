package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.system.entity.SysDocNotice;
import cn.ccsit.eeoi.system.entity.SysFile;
import cn.ccsit.eeoi.system.entity.SysPcappVersion;
import cn.ccsit.eeoi.system.repository.SysFileRepository;
import cn.ccsit.eeoi.system.repository.SysPcappVersionRepository;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysPcappVersionService")
public class SysPcappVersionServiceImpl extends CommonServiceImpl implements SysPcappVersionService, CommonService {

    @Autowired
    private SysPcappVersionRepository sysPcappVersionRepository;

    @Autowired
    private SysFileRepository sysFileRepository;

    @Autowired
    FtpUtils ftpUtils;

    @Override
    public ResultVo findSysPcappVersionById(String id) {
        return new ResultObjectVo(sysPcappVersionRepository.findById(id).get());
    }

    @Override
    public ResultVo findAllSysPcappVersionsByPage(int pageSize, int pageNum, String newVersion, String upType) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,Sort.by("createTm").descending());
        Specification<SysPcappVersion> specification = new Specification<SysPcappVersion>() {
            @Override
            public Predicate toPredicate(Root<SysPcappVersion> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (newVersion != null && !"".equals(newVersion)) {
                    Path<Object> newVersion1 = root.get("newVersion");
                    predicates.add(criteriaBuilder.like(newVersion1.as(String.class), "%" + newVersion + "%"));
                }
                if (upType != null && !"".equals(upType)) {
                    Path<Object> upType1 = root.get("upType");
                    predicates.add(criteriaBuilder.like(upType1.as(String.class), "%" + upType + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<SysPcappVersion> page = sysPcappVersionRepository.findAll(specification, pageable);
        List sysPcappVersions = page.getContent();
        Long total = sysPcappVersionRepository.count(specification);
        return new ResultPageVo(total, sysPcappVersions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo saveOrUpdateSysPcappVersion(SysPcappVersion sysPcappVersion) {
        this.uploadFiles(sysPcappVersion.getFileIds(), sysPcappVersion.getFileNames(), sysPcappVersion.getSysCode());
        sysPcappVersion.setIsDelete("0");
        if (sysPcappVersion.getFileIds() != null && sysPcappVersion.getFileIds().indexOf("_newUploadFile") != -1) {
            String fileIds = sysPcappVersion.getFileIds();
            String fileIdsHandled = StringUtils.remove(fileIds, "_newUploadFile");
            sysPcappVersion.setFileIds(fileIdsHandled);
        }
        SysPcappVersion returnEntity = sysPcappVersionRepository.save(sysPcappVersion);
        return new ResultObjectVo<SysPcappVersion>(returnEntity);
    }

    @Override
    public ResultVo deleteSysPcappVersionById(String id) {
        SysPcappVersion sysPcappVersion = sysPcappVersionRepository.getOne(id);
        sysPcappVersion.setIsDelete("1");
        SysPcappVersion returnEntity = sysPcappVersionRepository.save(sysPcappVersion);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    public ResultVo findSysFilsListByNoticeId(String id) {
        List list = new ArrayList();
        SysPcappVersion sysPcappVersion = sysPcappVersionRepository.findById(id).get();
        String fileIds = sysPcappVersion.getFileIds();
        if (fileIds != null) {
            String[] fileIdsArr = StringUtils.split(fileIds, ",");
            for (int i = 0; i < fileIdsArr.length; i++) {
                SysFile sysFile = sysFileRepository.findByFilePath(fileIdsArr[i]).get(0);
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", sysFile.getFilePath());
                map.put("name", sysFile.getName());
                map.put("url", sysFile.getPath());
                list.add(map);
            }
        }
        return new ResultObjectVo(list);
    }

    @Override
    public ResultVo findAfterSysPcappsList(String version, String sysCode) {
        List<SysPcappVersion> list = sysPcappVersionRepository.findByNewVersionGreaterThanAndSysCodeAndRecStatusAndIsDelete(version, sysCode, "1","0");
        return new ResultPageVo(list.size(), list);
    }

    @Override
    public void downloadFileByFileNumber(String filePath) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        SysFile sysFile = sysFileRepository.findFirstByFilePath(filePath);
        ftpUtils.downloadFile(sysFile.getPath(), sysFile.getName(), response);
    }

    public void uploadFiles(String filesIds, String fileNames, String fileType) {
        String[] ids = StringUtils.split(filesIds, ",");
        String[] names = StringUtils.split(fileNames, ",");
        int j = 0;
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                if (ids[i].indexOf("_newUploadFile") != -1) {
                    String suffix = names[j].substring(names[j].lastIndexOf("."), names[j].length());
                    String ftpDir = ftpUtils.uploadVersionFile(StringUtils.remove(ids[i], "_newUploadFile") + suffix, fileType);
                    SysFile sysFile = new SysFile();
                    sysFile.setPath(ftpDir + "/" + StringUtils.remove(ids[i], "_newUploadFile") + suffix);
                    sysFile.setName(names[j]);
                    sysFile.setFilePath(StringUtils.remove(ids[i], "_newUploadFile"));
                    sysFileRepository.save(sysFile);
                    j++;
                }
            }
        }
    }
}
