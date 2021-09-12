package cn.ccsit.eeoi.system.service;

import cn.ccsit.common.vo.*;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.system.entity.SysDocNotice;
import cn.ccsit.eeoi.system.entity.SysFile;
import cn.ccsit.eeoi.system.repository.SysDocNoticeRepository;
import cn.ccsit.eeoi.system.repository.SysFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service("sysFileService")
public class SysFileServiceImpl extends CommonServiceImpl implements SysFileService, CommonService {

    @Autowired
    private SysFileRepository sysFileRepository;

    @Override
    public ResultVo findAllSysFilesByPage(int pageSize, int pageNum) {
        return null;
    }

    @Override
    public ResultVo deleteSysFileById(String id) {
        return null;
    }

    @Override
    public ResultVo saveOrUpdateSysFile(SysFile sysFile) {
        return null;
    }

    @Override
    public ResultVo findSysFileById(String id) {
        return null;
    }
}
