package mhl.service;

import mhl.dao.MenuDAO;
import mhl.domain.Menu;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Carson
 * @Version
 */
public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    //返回所有菜品
    public List<Menu>  listDish(){
        return menuDAO.queryMulti("select * from menu",Menu.class);
    }

    public Menu getMenuDishById(int id){
        Menu menu = menuDAO.querySingle("select * from Menu where id=?", Menu.class, id);
        return menu;
    }

}
