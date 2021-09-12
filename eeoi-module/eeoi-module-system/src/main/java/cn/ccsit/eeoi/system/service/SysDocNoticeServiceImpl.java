package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.system.entity.SysDocNotice;
import cn.ccsit.eeoi.system.entity.SysFile;
import cn.ccsit.eeoi.system.repository.SysDocNoticeRepository;
import cn.ccsit.eeoi.system.repository.SysFileRepository;
import cn.ccsit.eeoi.system.utils.FtpUtils;
import cn.ccsit.eeoi.system.vo.SoftWareVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysDocNoticeService")
public class SysDocNoticeServiceImpl extends CommonServiceImpl implements SysDocNoticeService, CommonService {

    @Autowired
    private SysDocNoticeRepository sysDocNoticeRepository;

    @Autowired
    private SysFileRepository sysFileRepository;

    @Autowired
    FtpUtils ftpUtils;

    @Override
    public ResultVo findAllSysDocNoticesByPage(int pageSize, int pageNum, String docTitle, String docNumber, String docType, String recStatus) {

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<SysDocNotice> specification = new Specification<SysDocNotice>() {
            @Override
            public Predicate toPredicate(Root<SysDocNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (docTitle != null && !"".equals(docTitle)) {
                    Path<Object> name1 = root.get("docTitle");
                    predicates.add(criteriaBuilder.like(name1.as(String.class), "%" + docTitle + "%"));
                }
                if (docNumber != null && !"".equals(docNumber)) {
                    Path<Object> ccsCode1 = root.get("docNumber");
                    predicates.add(criteriaBuilder.like(ccsCode1.as(String.class), "%" + docNumber + "%"));
                }
                if (docType != null && !"".equals(docType)) {
                    Path<Object> ccsCode1 = root.get("docType");
                    predicates.add(criteriaBuilder.like(ccsCode1.as(String.class), "%" + docType + "%"));
                }
                if (recStatus != null && !"".equals(recStatus)) {
                    Path<Object> ccsCode1 = root.get("recStatus");
                    predicates.add(criteriaBuilder.like(ccsCode1.as(String.class), "%" + recStatus + "%"));
                }
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<SysDocNotice> page = sysDocNoticeRepository.findAll(specification, pageable);
        List sysDocNotices = page.getContent();
        Long total = sysDocNoticeRepository.count(specification);
        return new ResultPageVo(total, sysDocNotices);
    }

    @Override
    public ResultVo deleteSysDocNoticeById(String id) {
        SysDocNotice sysDocNotice = sysDocNoticeRepository.getOne(id);
        sysDocNotice.setIsDelete("1");
        SysDocNotice returnEntity = sysDocNoticeRepository.save(sysDocNotice);
        if (returnEntity == null) {
            return new ResultBooleanVo(false);
        }
        return new ResultStringVo(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo saveOrUpdateSysDocNotice(SysDocNotice sysDocNotice) {
        this.uploadFiles(sysDocNotice.getFileIds(), sysDocNotice.getFileNames());
        sysDocNotice.setIsDelete("0");
        if (sysDocNotice.getFileIds() != null && sysDocNotice.getFileIds().indexOf("_newUploadFile") != -1) {
            String fileIds = sysDocNotice.getFileIds();
            String fileIdsHandled = StringUtils.remove(fileIds, "_newUploadFile");
            sysDocNotice.setFileIds(fileIdsHandled);
        }
        SysDocNotice returnEntity = sysDocNoticeRepository.save(sysDocNotice);
        return new ResultObjectVo<SysDocNotice>(returnEntity);
    }

    @Override
    public ResultVo findSysDocNoticeById(String id) {
        return new ResultObjectVo(sysDocNoticeRepository.findById(id).get());
    }

    @Override
    public ResultVo findSysFilsListByNoticeId(String id) {
        List list = new ArrayList();
        SysDocNotice sysDocNotice = sysDocNoticeRepository.findById(id).get();
        String fileIds = sysDocNotice.getFileIds();
        String[] fileIdsArr = StringUtils.split(fileIds, ",");
        if (fileIds != null) {
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

    public void uploadFiles(String filesIds, String fileNames) {
        String[] ids = StringUtils.split(filesIds, ",");
        String[] names = StringUtils.split(fileNames, ",");
        int j = 0;
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                if (ids[i].indexOf("_newUploadFile") != -1) {
                    String suffix = names[j].substring(names[j].lastIndexOf("."), names[j].length());
                    String ftpDir = ftpUtils.upFile(StringUtils.remove(ids[i], "_newUploadFile") + suffix);
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

    @Transactional
    public void downloadFile(String ftpPath, String fileName, HttpServletResponse response) {
        ftpUtils.download2Local(StringUtils.substringBeforeLast(ftpPath, "/"), StringUtils.substringAfterLast(ftpPath, "/"));
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(fileName);
        response.setCharacterEncoding("UTF-8");
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type", type);
        // 设置编码
        try {
            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            ftpUtils.downloadFile(StringUtils.substringAfterLast(ftpPath, "/"), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultVo findPreviousThreeNotices() {
        List<SysDocNotice> systemManual = sysDocNoticeRepository.findTop3ByDocTypeAndIsDeleteOrderByCreateTmDesc("1", "0");
        List<SysDocNotice> notice = sysDocNoticeRepository.findTop3ByDocTypeAndIsDeleteOrderByCreateTmDesc("2", "0");
        List<SysDocNotice> otherDocument = sysDocNoticeRepository.findTop3ByDocTypeAndIsDeleteOrderByCreateTmDesc("3", "0");
        List<SysDocNotice> systemNotice = sysDocNoticeRepository.findTop3ByDocTypeAndIsDeleteOrderByCreateTmDesc("4", "0");
        Map<String, List<SysDocNotice>> map = new HashMap<String, List<SysDocNotice>>();
        map.put("systemManual", systemManual);
        map.put("notice", notice);
        map.put("otherDocument", otherDocument);
        map.put("systemNotice", systemNotice);
        return new ResultObjectVo<Map>(map);
    }

    @Override
    public ResultVo findPreviousThreeSystemNotices() {
        List<SysDocNotice> systemNotices = sysDocNoticeRepository.findTop3ByDocTypeAndIsDeleteOrderByCreateTmDesc("4", "0");
        List<Map> systemNoticesVo = new ArrayList<>();
        for (SysDocNotice sysDocNotice : systemNotices) {
            Map<String, String> map = new HashMap<>();
            map.put("txt", sysDocNotice.getDocTitle());
            map.put("txt2", sysDocNotice.getContent());
            systemNoticesVo.add(map);
        }
        return new ResultPageVo(systemNoticesVo.size(), systemNoticesVo);
    }

    @Override
    public ResultVo findAllSysDocNoticeListAndSysFiles(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

        Specification<SysDocNotice> specification = new Specification<SysDocNotice>() {
            @Override
            public Predicate toPredicate(Root<SysDocNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Path<Object> isDelete = root.get("isDelete");
                predicates.add(criteriaBuilder.notEqual(isDelete.as(String.class), "1"));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<SysDocNotice> page = sysDocNoticeRepository.findAll(specification, pageable);
        List<SysDocNotice> sysDocNotices = page.getContent();
        Long total = sysDocNoticeRepository.count(specification);
        List<Map> docVos = new ArrayList<>();

        for (SysDocNotice sysDocNotice : sysDocNotices) {
            Map<String, Object> docMap = new HashMap<>();
            docMap.put("id", sysDocNotice.getId());
            docMap.put("name", sysDocNotice.getDocTitle());
            docMap.put("content", sysDocNotice.getContent());
            docMap.put("path", "");
            if (sysDocNotice.getFileIds() != null) {
                List<Map> fileList = new ArrayList<>();
                String ids[] = sysDocNotice.getFileIds().split(",");
                for (int i = 0; i < ids.length; i++) {
                    SysFile sysFile = sysFileRepository.findFirstByFilePath(ids[i]);
                    Map<String, String> sysFileMap = new HashMap<>();
                    sysFileMap.put("id", sysFile.getId());
                    sysFileMap.put("name", sysFile.getName());
                    sysFileMap.put("path", sysFile.getPath());
                    fileList.add(sysFileMap);
                    docMap.put("children", fileList);
                }
            }
            docVos.add(docMap);
        }
        return new ResultPageVo(docVos.size(), docVos);
    }

    @Override
    public ResultVo getSoftWareDownLoad() {
        Integer docType = 5;
        List<SysDocNotice> byDocType = sysDocNoticeRepository.findByDocType(docType);
        List<SoftWareVo> softWareVos = new ArrayList<>();
        for (int i = 0; i < byDocType.size(); i++) {
            SoftWareVo softWareVo = new SoftWareVo();
            SysDocNotice sysDocNotice = byDocType.get(i);
            String fileIds = sysDocNotice.getFileIds();
            String[] split = fileIds.split(",");
            SysFile byFilePath = sysFileRepository.findFirstByFilePath(split[0]);
            softWareVo.setName(byFilePath.getName());
            softWareVo.setPath(byFilePath.getPath());
            softWareVo.setContent(sysDocNotice.getContent());
            softWareVos.add(softWareVo);
        }
        return new ResultObjectVo<>(softWareVos);
    }
}
