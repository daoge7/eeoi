package cn.ccsit.eeoi.ship.service;

import cn.ccsit.common.utils.StringUtils;
import cn.ccsit.common.vo.ResultObjectVo;
import cn.ccsit.common.vo.ResultPageVo;
import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.entity.GcClientCate;
import cn.ccsit.eeoi.ship.entity.GcClientCateRela;
import cn.ccsit.eeoi.ship.repository.GcClientCateRepository;
import cn.ccsit.eeoi.ship.repository.GcClientRepository;
import cn.ccsit.eeoi.ship.vo.GcClientCateVo;
import cn.ccsit.eeoi.ship.vo.GcClientShowListVo;
import cn.ccsit.eeoi.ship.vo.GcClientVo;
import cn.ccsit.eeoi.system.entity.SysUserClientMap;
import cn.ccsit.eeoi.system.repository.SysRoleRepository;
import cn.ccsit.eeoi.system.repository.SysUserClientMapRepository;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("gcClientService")
public class GcClientServiceImpl extends CommonServiceImpl implements GcClientService, CommonService {
    @Autowired
    private GcClientRepository gcClientRepository;

    @Autowired
    private GcClientCateRepository gcClientCateRepository;

    @Autowired
    private SysUserClientMapRepository sysUserClientMapRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;

    /***
     * 获取查询列表
     */
    @Override
    public List<GcClientShowListVo> getGcClientsByVo(GcClientVo gcClientVo) {
        List<GcClient> fromGcClients = new ArrayList<GcClient>();
        List<GcClientShowListVo> totalGcClients = new ArrayList<GcClientShowListVo>();
        String jpql = "select c from GcClient c where valid=1 ";
        if (gcClientVo == null) {
            fromGcClients = commonRepository.findAllByJPQLCriteria(GcClient.class, jpql);
        } else {
            // 公司名称
            if (StringUtils.isNotEmpty(gcClientVo.getNameCn())) {
                gcClientVo.setNameCn(gcClientVo.getNameCn().toUpperCase());// 转换成大写
                gcClientVo.setNameCn(gcClientVo.getNameCn().replace(" ", ""));// 去空格
                jpql += " and (upper(c.nameCn) like '%" + gcClientVo.getNameCn() + "%' or upper(c.nameEn) like '%"
                        + gcClientVo.getNameCn() + "%' or upper(c.iacs) like '% " + gcClientVo.getNameCn() + "%')";
            }
            // 地址
            if (StringUtils.isNotEmpty(gcClientVo.getOfficeAdressCn())) {
                gcClientVo.setOfficeAdressCn(gcClientVo.getOfficeAdressCn().toUpperCase());// 转换成大写
                gcClientVo.setOfficeAdressCn(gcClientVo.getOfficeAdressCn().replace(" ", ""));// 去空格
                jpql += " and (upper(c.addrCn) like '%" + gcClientVo.getOfficeAdressCn() + "%' or upper(c.addrEn) like '%"
                        + gcClientVo.getOfficeAdressCn() + "%')";
            }
            // 公司CcsCode
            if (StringUtils.isNotEmpty(gcClientVo.getCcsCode())) {
                gcClientVo.setCcsCode(gcClientVo.getCcsCode().toUpperCase());// 转换成大写
                gcClientVo.setCcsCode(gcClientVo.getCcsCode().replace(" ", ""));// 去空格
                jpql += " and (upper(c.CcsCode) like '%" + gcClientVo.getCcsCode() + "%' )";
            }
            fromGcClients = commonRepository.findAllByJPQLCriteria(GcClient.class, jpql);
        }
        for (GcClient client : fromGcClients) {
            GcClientShowListVo clientShow = new GcClientShowListVo(client);
            totalGcClients.add(clientShow);
        }
        return totalGcClients;
    }

    public List<GcClientShowListVo> getGcClientsByUserId(String userId) {
        List<GcClientShowListVo> fromGcClients = new ArrayList<GcClientShowListVo>();
        List<SysUserClientMap> userCompanyList = sysUserClientMapRepository.findSysUserCompanyByUserId(userId);
        for (SysUserClientMap userCompany : userCompanyList) {
            GcClient client = gcClientRepository.findGcClientById(userCompany.getGcid());
            if (client != null) {
                GcClientShowListVo clientShow = new GcClientShowListVo(client);
                fromGcClients.add(clientShow);
            }
        }
        return fromGcClients;
    }

    @Override
    public ServiceResultVo saveGcClientByVo(GcClientVo gcClientVo) {
        GcClient gcClient = new GcClient();
        if (!"".equals(gcClientVo.getId())) {
            gcClient.setId(gcClientVo.getId());
        }
        gcClient.setCode(gcClientVo.getCcsCode());
        gcClient.setIacs(gcClientVo.getIacs());
        gcClient.setNameCn(gcClientVo.getNameCn());
        gcClient.setNameEn(gcClientVo.getNameEn());

        gcClient.setAddrCn(gcClientVo.getRegisterAdressCn());
        gcClient.setAddrEn(gcClientVo.getRegisterAdressEn());
        gcClient.setAddrCnNew(gcClientVo.getOfficeAdressCn());
        gcClient.setAddrEnNew(gcClientVo.getOfficeAdressEn());
        gcClient.setContact(gcClientVo.getContactPerson());
        gcClient.setContactTel(gcClientVo.getContactTel());
        gcClient.setContactEmail(gcClientVo.getContactEmail());
        gcClient.setBusiness(gcClientVo.getBusinessScope());
        gcClient.setMainRoute(gcClientVo.getBusinessLine());
        gcClient.setCertNo(gcClientVo.getCertificateNo());
        gcClient.setDelete(false);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
//        try {
//            Date date = simpleDateFormat.parse(gcClientVo.getCertificateDate());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        gcClient.setCertDate(gcClientVo.getCertificateDate());
        gcClient.setRemarkInfo(gcClientVo.getRemark());

        GcClientCateRela gcClientCateRela = new GcClientCateRela();
        gcClientCateRela.setGcClient(gcClient);
        gcClientCateRela.setGcClientCate(gcClientCateRepository.findById(gcClientVo.getCompanyType()).get());
        gcClient.getGcClientCateRelas().add(gcClientCateRela);
        GcClient gcClientReturn = gcClientRepository.save(gcClient);
        if (gcClientVo.getChildrenCompanyIds() != null) {
            this.setParentGcClient(gcClient.getId(), gcClientVo.getChildrenCompanyIds());
        }
        GcClientShowListVo gcClientShowListVo = new GcClientShowListVo(gcClientReturn);
        if (gcClientReturn != null) {
            return new ServiceResultVo(true, gcClientShowListVo);
        } else {
            String msg = "保存失败";
            return new ServiceResultVo(false, msg);
        }
    }

    @Override
    public ResultVo findGcClientsVoByPage(int pageSize, int pageNum, String nameCn, String addrCn, String ccsCode, String userId) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<GcClient> specification = new Specification<GcClient>() {
            @Override
            public Predicate toPredicate(Root<GcClient> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> predicatesOr = new ArrayList<>();
//                String jpql = "select count(*) from GcClient c where is_delete=0 ";
                if (nameCn != null && !"".equals(nameCn)) {
                    Path<Object> nameCn1 = root.get("nameCn");
                    Path<Object> nameEn1 = root.get("nameEn");
                    Path<Object> code1 = root.get("code");
                    Path<Object> iacs1 = root.get("iacs");
                    predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(nameCn1.as(String.class)), "%" + nameCn.toLowerCase() + "%"));
                    predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(nameEn1.as(String.class)), "%" + nameCn.toLowerCase() + "%"));
                    predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(code1.as(String.class)), "%" + nameCn.toLowerCase() + "%"));
                    predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(iacs1.as(String.class)), "%" + nameCn.toLowerCase() + "%"));
                }
                if (addrCn != null && !"".equals(addrCn)) {
                    Path<Object> nameEn1 = root.get("addrCn");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(nameEn1.as(String.class)), "%" + addrCn.toLowerCase() + "%"));
                }
                if (ccsCode != null && !"".equals(ccsCode)) {
                    Path<Object> ccsCode1 = root.get("code");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ccsCode1.as(String.class)), "%" + ccsCode.toLowerCase() + "%"));
                }
                Path<Object> isDelete = root.get("delete");
                predicates.add(criteriaBuilder.equal(isDelete.as(Boolean.class), false));
                if(StringUtils.isNotEmpty(userId)){
                    Path<Object> idField = root.get("id");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(idField);
                    List<GcClientShowListVo> userClients=getGcClientsByUserId(userId);
                    for(int i=0;userClients!=null && i<userClients.size();i++){
                        in.value(userClients.get(i).getId());
                    }
                    predicates.add(criteriaBuilder.and(in));
                }
                return predicatesOr.size() > 0 ? criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])), criteriaBuilder.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]))).getRestriction() : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<GcClient> page = gcClientRepository.findAll(specification, pageable);
        List<GcClient> gcClients = page.getContent();
        List<GcClientShowListVo> gcClientShowListVos = this.getGcClientShowListVoByGcClientList(gcClients);
        Long total = gcClientRepository.count(specification);
        ResultPageVo resultPageVo = new ResultPageVo(total, gcClientShowListVos);
        return resultPageVo;
    }

    @Override
    public List<GcClientShowListVo> getGcClientShowListVoByGcClientList(List<GcClient> gcClients) {
        List<GcClientShowListVo> gcClientShowListVos = new ArrayList<>();
        for (int i = 0; i < gcClients.size(); i++) {
            GcClient gcClient = gcClients.get(i);
            GcClientShowListVo gcClientShowVo = new GcClientShowListVo(gcClient);
            gcClientShowListVos.add(gcClientShowVo);
        }
        return gcClientShowListVos;
    }

    @Override
    public ResultVo findGcClientShowVoByGcClient(String id) {
        GcClient gcClient = gcClientRepository.findGcClientById(id);
        GcClientVo gGcClientVo = new GcClientVo(gcClient);
        ResultVo resultVo = new ResultObjectVo<GcClientVo>(gGcClientVo);
        return resultVo;
    }

    @Override
    public ResultVo setParentGcClient(String id, List<String> children) {
        List<GcClient> gcClients = new ArrayList<>();
        GcClient gcClientParent = gcClientRepository.findById(id).get();
        for (int i = 0; i < children.size(); i++) {
            gcClients.add(gcClientRepository.findById(children.get(i)).get());
        }
        gcClientParent.setChildren(gcClients);
        gcClientRepository.save(gcClientParent);
        ServiceResultVo resultVo = new ServiceResultVo(true, "保存成功");
        return resultVo;
    }

    /***
     * 保存
     */
    @Override
    public GcClient saveGcClient(GcClient parameter) {
        return gcClientRepository.save(parameter);
    }

    /***
     * 根据id删除用户
     */
    @Override
    public ServiceResultVo deleteGcClient(String id) {
        GcClient gcClient = gcClientRepository.getGcClientByCUid(id);
        gcClient.setDelete(true);
        GcClient gcClientReturn = gcClientRepository.save(gcClient);
        if (gcClientReturn != null) {
            return new ServiceResultVo(true, gcClientReturn);
        } else {
            String msg = "删除失败";
            return new ServiceResultVo(false, msg);
        }
    }

    public GcClient findById(String id) {
        GcClient gcClient = gcClientRepository.findById(id).get();
        return gcClient;
    }

    public List<GcClientCate> findGcClientCatesByIds(List<String> ids) {
        List<GcClientCate> gcClientCates = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            gcClientCates.add(gcClientCateRepository.findById(ids.get(i)).get());
        }
        return gcClientCates;
    }

    public ResultVo deleteGcClientById(String id) {
        GcClient gcClient = this.findById(id);
        gcClient.setDelete(true);
        gcClientRepository.save(gcClient);
        GcClientShowListVo gcClientShowListVo = new GcClientShowListVo(gcClient);
        return new ServiceResultVo(true, gcClientShowListVo);
    }

    @Override
    public ResultVo findAllGcClientCates() {
        List<GcClientCate> gcClientCates = (List<GcClientCate>) gcClientCateRepository.findAll();
        List<GcClientCateVo> GcClientCateVos = new ArrayList<>();
        for(GcClientCate gcClientCate : gcClientCates){
            GcClientCateVo gcClientCateVo = new GcClientCateVo(gcClientCate);
            GcClientCateVos.add(gcClientCateVo);
        }
        return new ServiceResultVo(true, GcClientCateVos);
    }
}
