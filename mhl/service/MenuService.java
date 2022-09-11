package mhl.service;

import mhl.dao.MenuDAO;
import mhl.domain.Menu;

import java.util.LinkedHashSet;
import java.util.List;


public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    // Return all dish names
    public List<Menu>  listDish(){
        return menuDAO.queryMulti("select * from menu",Menu.class);
    }

    public Menu getMenuDishById(int id){
        Menu menu = menuDAO.querySingle("select * from Menu where id=?", Menu.class, id);
        return menu;
    }

}
