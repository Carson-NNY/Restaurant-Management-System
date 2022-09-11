package mhl.service;

import mhl.dao.DiningTableDAO;
import mhl.domain.DiningTable;

import java.util.List;

/**
 * @author Carson
 * @Version
 */
public class DiningTableService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    public List<DiningTable> list(){
        return diningTableDAO.queryMulti("select id,state from diningTable", DiningTable.class);
    }

    // 根据id，查询对应餐桌
    public DiningTable getDiningTableById(int id){

        return diningTableDAO.querySingle("select * from diningTable  where id=?",DiningTable.class,id);

    }


    // 如果餐桌可以预定，调用方法对state和一些属性更新，
    public boolean orderDiningTable(int id, String orderName,String orderTel){
        int update = diningTableDAO.update("update  diningTable set state='已经预定', orderName=?, orderTel = ? where id=?",orderName,orderTel,id);
        return update >0;

    }

    // 更新餐桌状态,
    public boolean updateDiningTableState(int id,String state){
        int update = diningTableDAO.update("update  diningTable set state=? where id=?", state, id);
        return update>0;

    }

    public int resetTable(int id){
        int update = diningTableDAO.update("update  diningTable set state ='空', orderName = '', orderTel ='' where id=?",id);
        return update;
    }



}
