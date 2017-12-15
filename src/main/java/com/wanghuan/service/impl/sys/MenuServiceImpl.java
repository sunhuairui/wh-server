package com.wanghuan.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanghuan.dao.MenuDao;
import com.wanghuan.model.sys.MenuEntity;
import com.wanghuan.service.sys.MenuService;

@Service("menuServiceImpl")
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<MenuEntity> menuList(int id) {
		List<String> idList = menuDao.getMenuIdByRole(id);
		String idstemp = "";
		for (String idtemp : idList) {
			idstemp = idstemp + idtemp;
		}
		String[] ids = idstemp.split(";");
		List<MenuEntity> parentMenuList = menuDao.getParentMenuListById(ids);
		List<MenuEntity> childrenMenuList = menuDao.getMenuListById(ids);
		List<MenuEntity> menuList = new ArrayList<MenuEntity>();

		for (MenuEntity parentMenu : parentMenuList) {
			List<MenuEntity> menuListTemp = new ArrayList<MenuEntity>();
			for (MenuEntity childrenMenu : childrenMenuList) {
				if (parentMenu.getId() == childrenMenu.getParentId()) {
					menuListTemp.add(childrenMenu);
				}
			}
			parentMenu.setChildren(menuListTemp);
			menuList.add(parentMenu);
		}

		return menuList;
	}

	@Override
	public List<MenuEntity> menusList(int pageSize, int start) {
		return menuDao.menusList(pageSize, start);
	}

	@Override
	public Integer menusSize(int pageSize, int start) {
		return menuDao.menusSize(pageSize, start);
	}

	@Override
	public void insertMenu(MenuEntity menuEntity) {
		menuDao.insertMenu(menuEntity);
	}

	@Override
	public void updateMenu(MenuEntity menuEntity) {
		menuDao.updateMenu(menuEntity);
	}

	@Override
	public void deleteMenus(List<String> groupId) {
		menuDao.deleteMenus(groupId);
	}

}
