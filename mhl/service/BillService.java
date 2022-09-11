package mhl.service;

import mhl.dao.BillDAO;
import mhl.dao.MultiTableDAO;
import mhl.domain.Bill;
import mhl.domain.DiningTable;
import mhl.domain.Menu;
import mhl.domain.MultiTableBean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * any operation related to it Bill
 */
public class BillService {

    private BillDAO billDAO = new BillDAO();
    // Additional services are needed to assist with certain functions
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService =new DiningTableService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();


    // Query the corresponding bill according to id
    public Bill getBillById(String billId){
        return billDAO.querySingle("select * from bill where billId=?",Bill.class,billId);
    }

    // If you can order, update bill form
    public boolean orderDish( Integer menuId, Integer nums,
                              Integer diningTableId){
        // Generate a billing number
        String billId = UUID.randomUUID().toString();

        Menu menu = menuService.getMenuDishById(menuId);

        int update = billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'unpaid')",billId, menuId, nums,menu.getPrice()*nums, diningTableId);
        if(update <=0 ){
            return false;
        }

        return diningTableService.updateDiningTableState(diningTableId,"in use");
    }

    public List<Bill> listBill(){

        List<Bill> bills = billDAO.queryMulti("select * from bill", Bill.class);
        return bills;
    }

    //Extended function to return all bills with dish names (involving multi-table queries)
    // Multi-table query, so MultiTableBean and corresponding DAO classes are created to merge the information of several tables
    public List<MultiTableBean> listMultiTable(){

        List<MultiTableBean> bills = multiTableDAO.queryMulti(
                "SELECT bill.*, name,price " +
                "from bill,menu " +
                "WHERE bill.menuId = menu.id", MultiTableBean.class);
        return bills;
    }

    public boolean hasPayBillbyDiningTableId(int diningtableId){
        Bill bill = billDAO.querySingle("select * from bill where diningTableId =? and state = 'unpaid'", Bill.class, diningtableId);
        return  bill !=null;
    }

    public boolean checkOut(int tableId ,String payMode){
        int update = billDAO.update("update bill set state=?  where diningTableId = ? and state = 'unpaid' ", payMode,tableId);
        if(update <= 0){ return false;}   //This avoids the following will continue to execute in the event of failure of the above operation.

        int result = diningTableService.resetTable(tableId);
        return result>0;
    }

}
