package cn.ccsit.eeoi.system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccsit.eeoi.common.service.CommonService;
import cn.ccsit.eeoi.common.service.CommonServiceImpl;
import cn.ccsit.eeoi.system.entity.SysMenu;
import cn.ccsit.eeoi.system.repository.SysMenuRepository;
import cn.ccsit.eeoi.system.vo.SysMenuVo;

@Service("sysMenuService")
public class SysMenuServicelmpl extends CommonServiceImpl implements SysMenuService, CommonService {

	@Autowired
	private SysMenuRepository sysMenuRepository;

	public List<Map<String, Object>> GetAllSysMenus() {

		List<SysMenu> sysMenus = sysMenuRepository.findAllSysMenu();

		List<SysMenuVo> menus = new ArrayList<SysMenuVo>();

		List<SysMenuVo> buttons = new ArrayList<SysMenuVo>();

		for (SysMenu item : sysMenus) {
			SysMenuVo model = new SysMenuVo();
			model.setId(item.getId());
			model.setIcon(item.getIcon());
			model.setPid(item.getPid());
			model.setSeqNo(item.getSeqNo());
			model.setTitle(item.getTitle());
			model.setTitleEn(item.getTitleEn());
			model.setUrl(item.getUrl());
			model.setButtons(new ArrayList<SysMenuVo>());
			if (item.getMenuType() != 3) {
				menus.add(model);
			} else {
				buttons.add(model);
			}
		}
		for (SysMenuVo menu : menus) {
			for (SysMenuVo button : buttons) {
				if (menu.getId().equals(button.getPid())) {
					menu.getButtons().add(button);
				}
			}
		}
		List<Map<String, Object>> menuList = getChild("0", menus);
		return menuList;
	}

	/***
	 * pid 父id allList 所有数据库查出来的list数据
	 */
	private List<Map<String, Object>> getChild(String pid, List<SysMenuVo> allList) {

		List<Map<String, Object>> childList = new ArrayList<>();// 用于保存子节点的list

		for (SysMenuVo ms : allList) {
			// 判断传入的父id是否等于自身的，如果是，就说明自己是子节点
			if (pid.equals(ms.getPid())) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", ms.getId());
				map.put("pid", ms.getPid());
				map.put("title", this.getLocaleName(ms.getTitle(), ms.getTitleEn()));
				map.put("icon", ms.getIcon());
				map.put("sort", ms.getSeqNo());
				map.put("url", ms.getUrl());
				map.put("children", new Object[] {});
				map.put("buttons", ms.getButtons());
				childList.add(map); // 加入子节点
			}
		}
		// 排序
		Collections.sort(childList, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Integer name1 = Integer.valueOf(o1.get("sort").toString());
				Integer name2 = Integer.valueOf(o2.get("sort").toString());
				return name1.compareTo(name2);
			}
		});
		for (Map<String, Object> map : childList) {// 遍历子节点，继续递归判断每个子节点是否还含有子节点
			List<Map<String, Object>> tList = getChild(String.valueOf(map.get("id")), allList);
			map.put("children", tList);
		}
		return childList;
	}
}
