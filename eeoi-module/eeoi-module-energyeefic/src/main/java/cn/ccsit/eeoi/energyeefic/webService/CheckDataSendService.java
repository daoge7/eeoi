package cn.ccsit.eeoi.energyeefic.webService;

import cn.ccsit.eeoi.energyeefic.dto.ImportVoyage;
import java.util.List;

public interface CheckDataSendService {
	/**
	 * 老系统上报接口
	 * @param list
	 * @param T
	 * @param S
	 * @return
	 */
	public List<ImportVoyage> CheckDataList(List<ImportVoyage> list, String T, String S);
}
