package cn.ccsit.eeoi;

import cn.ccsit.common.vo.ResultVo;
import cn.ccsit.eeoi.data.repository.CommonRepository;
import cn.ccsit.eeoi.ship.entity.OiShipCmsaMap;
import cn.ccsit.eeoi.ship.entity.OiShipInfo;
import cn.ccsit.eeoi.ship.query.OiShipInfoCriteria;
import cn.ccsit.eeoi.ship.repository.OiMainEngineRepository;
import cn.ccsit.eeoi.ship.repository.OiShipBoilerRepository;
import cn.ccsit.eeoi.ship.repository.OiShipCmsaMapRepository;
import cn.ccsit.eeoi.ship.repository.OiShipInfoRepository;
import cn.ccsit.eeoi.ship.service.OiShipInfoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootTest
class EeoiWebApplicationTests {
	@Autowired
	private OiShipInfoRepository oiShipInfoRepository;
	@Autowired
	private OiShipInfoService oiShipInfoService;
	@Autowired
	private OiShipBoilerRepository oiShipBoilerRepository;
	@Autowired
	private CommonRepository commonRepository;
	@Autowired
	private OiShipCmsaMapRepository oiShipCmsaMapRepository;
	@Autowired
	private OiMainEngineRepository oiMainEngineRepository;

	@Test
	void contextLoads() {
	}
	@Test
	void testOiShip() {
		OiShipInfo oiShipInfo = new OiShipInfo();
		oiShipInfo.setAffiliation("app");
		oiShipInfo.setBuilder("buldldl");
		OiShipInfo save = oiShipInfoRepository.save(oiShipInfo);
		System.out.println(save.getId());
	}
	@Test
	void testOiShipQurey() {
		OiShipInfoCriteria oiShipInfoCriteria = new OiShipInfoCriteria();
		oiShipInfoCriteria.setPageSize(2);
		oiShipInfoCriteria.setConcurrentPage(0);
		oiShipInfoCriteria.setShipKeyWord("djdjdjdjdjdj");
		ResultVo oiShipInfo = oiShipInfoService.getOiShipInfo(oiShipInfoCriteria);
		System.out.println("KKK");
	}
	@Test
	@Transactional
	void testDeleteOiBoiler() {
//		oiShipBoilerRepository.deleteByShipId("2c9a40d27153412b01715343c9740000");
//		Optional<OiShipBoiler> djdjdjdjdjdjdjdjd = oiShipBoilerRepository.findById("djdjdjdjdjdjdjdjd");
//		System.out.println();
	}
	@Test
	@Transactional
	void testCommonRepository() {
//		oiShipBoilerRepository.deleteByShipId("2c9a40d27153412b01715343c9740000");
//		Optional<OiShipBoiler> djdjdjdjdjdjdjdjd = oiShipBoilerRepository.findById("djdjdjdjdjdjdjdjd");
//		System.out.println();
		OiShipInfo byId = commonRepository.findById(OiShipInfo.class, "2c9a40d27153412b01715343c9740000");
		System.out.println();
	}
	@Test
	void testOiShipCmsaMapRepository() {
		OiShipCmsaMap oiShipCmsaMap = new OiShipCmsaMap();
		oiShipCmsaMap.setClientId("clientId");
		oiShipCmsaMap.setCmsaBranch("tembrn");
		OiShipCmsaMap save = oiShipCmsaMapRepository.save(oiShipCmsaMap);
		Assert.assertTrue(save != null);
	}
	@Test
	void testOiMainEngineRepository() {
//		int i = oiMainEngineRepository.deleteOiMainEngineByShipId("2c9a40d271586fe50171587890000");
//		System.out.println(i);
//		List<String> strings = Arrays.asList("2c9a40d271586fe50171587144890000", "2c9a40d2715873ef0171587460a60000");
//		strings.forEach(shipId -> {
//			oiShipInfoRepository.deleteOiShipInfoByShipId(shipId);
//		});
//		Set<String> clientId = oiShipCmsaMapRepository.findByClientId("fhfhfhfhffhf");
//		System.out.println(clientId);
//		Integer byRegisterNoOrImoNu = oiShipInfoRepository.findByRegisterNoOrImoNu("", "qwer");
//		System.out.println(byRegisterNoOrImoNu);
		Integer byShipId = oiMainEngineRepository.findByShipId("2c9a40d271586fe50171587144890000");
		System.out.println(byShipId);
	}

}
