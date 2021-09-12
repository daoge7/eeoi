package cn.ccsit.eeoi.ship.service;

import java.util.List;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.ship.entity.GcClient;
import cn.ccsit.eeoi.ship.vo.GcClientShowListVo;
import cn.ccsit.eeoi.ship.vo.GcClientVo;
import cn.ccsit.eeoi.system.vo.ServiceResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GcClientService extends CommonService {
	/***
	 * 查找所有船公司
	 * @return
	 */
	public List<GcClientShowListVo> getGcClientsByVo(GcClientVo gcClient);

	/***
	 * 保存船公司
	 * @param parameter
	 * @return
	 */
	public GcClient saveGcClient(GcClient parameter);

	/***
	 * 删除船公司
	 * @param id
	 */
	public ServiceResultVo deleteGcClient(String id);

	/***
	 * 查找用户对应的船公司
	 * @param userId 用户id
	 */
	public List<GcClientShowListVo> getGcClientsByUserId(String userId);

	/***
	 * 通过VO保存船公司实体类
	 * @param gcClientVo 船公司保存VO
	 */
	public ServiceResultVo saveGcClientByVo(GcClientVo gcClientVo);

	/**
	 *@Description:按条件和页码查找
	 *@Param:
	 *@return:
	 *@Author: luhao
	 *@date: 2020/4/21
	 */
	public ResultVo findGcClientsVoByPage(int pageSize, int pageNum, String nameCn, String nameEn, String ccsCode, String userId);

	/**
	 *@Description:通过gcClientList转为GcClientShowVoList
	 *@Param: [gcClients]
	 *@return: java.util.List<cn.ccsit.eeoi.ship.vo.GcClientShowListVo>
	 *@Author: luhao
	 *@date: 2020/4/22
	 */
	public List<GcClientShowListVo> getGcClientShowListVoByGcClientList(List<GcClient> gcClients);

	/**
	 *@Description:
	 *@Param: [gcClient]
	 *@return: cn.ccsit.common.vo.ResultVo
	 *@Author: luhao
	 *@date: 2020/4/22
	 */
	public ResultVo findGcClientShowVoByGcClient(String id);

	/**
	 *@Description:
	 *@Param: [id, children]
	 *@return: cn.ccsit.common.vo.ResultVo
	 *@Author: luhao
	 *@date: 2020/4/22
	 */
	public ResultVo setParentGcClient(String id, List<String> children);

	public ResultVo deleteGcClientById(String id);

	public ResultVo findAllGcClientCates();

}
