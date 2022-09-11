package mhl.service;

import mhl.dao.DiningTableDAO;
import mhl.domain.DiningTable;

import java.util.List;


public class DiningTableService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    public List<DiningTable> list(){
        return diningTableDAO.queryMulti("select id,state from diningTable", DiningTable.class);
    }

    // Query the corresponding table according to id
    public DiningTable getDiningTableById(int id){

        return diningTableDAO.querySingle("select * from diningTable  where id=?",DiningTable.class,id);

    }


    // If the table can be booked, call the method to update the state and some properties
    public boolean orderDiningTable(int id, String orderName,String orderTel){
        int update = diningTableDAO.update("update  diningTable set state='booked', orderName=?, orderTel = ? where id=?",orderName,orderTel,id);
        return update >0;

    }

    // Update table status
    public boolean updateDiningTableState(int id,String state){
        int update = diningTableDAO.update("update  diningTable set state=? where id=?", state, id);
        return update>0;

    }

    public int resetTable(int id){
        int update = diningTableDAO.update("update  diningTable set state ='null', orderName = '', orderTel ='' where id=?",id);
        return update;
    }



}
