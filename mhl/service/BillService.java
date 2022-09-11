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
 * @author Carson
 * @Version
 */
public class BillService {

    private BillDAO billDAO = new BillDAO();
    // 需要别的Service来协助完成某些功能
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService =new DiningTableService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();


    // 根据id，查询对应bill
    public Bill getBillById(String billId){
        return billDAO.querySingle("select * from bill where billId=?",Bill.class,billId);
    }

    // 如果可以点餐，update bill表
    public boolean orderDish( Integer menuId, Integer nums,
                              Integer diningTableId){
        // 生成一个账单号
        String billId = UUID.randomUUID().toString();

        Menu menu = menuService.getMenuDishById(menuId);

        int update = billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未结账')",billId, menuId, nums,menu.getPrice()*nums, diningTableId);
        if(update <=0 ){
            return false;
        }

        return diningTableService.updateDiningTableState(diningTableId,"就餐中");
    }

    public List<Bill> listBill(){

        List<Bill> bills = billDAO.queryMulti("select * from bill", Bill.class);
        return bills;
    }

    //扩展功能，返回所有账单并且带有菜品名（涉及多表查询）
    // 多表查询，所以创建了 MultiTableBean和相应的DAO类，来合并几个表的信息
    public List<MultiTableBean> listMultiTable(){

        List<MultiTableBean> bills = multiTableDAO.queryMulti(
                "SELECT bill.*, name,price " +
                "from bill,menu " +
                "WHERE bill.menuId = menu.id", MultiTableBean.class);
        return bills;
    }

    public boolean hasPayBillbyDiningTableId(int diningtableId){
        Bill bill = billDAO.querySingle("select * from bill where diningTableId =? and state = '未结账'", Bill.class, diningtableId);
        return  bill !=null;
    }

    public boolean checkOut(int tableId ,String payMode){
        int update = billDAO.update("update bill set state=?  where diningTableId = ? and state = '未结账' ", payMode,tableId);
        if(update <= 0){ return false;}   //这里避免了上面操作失败，下面还会继续执行

        int result = diningTableService.resetTable(tableId);
        return result>0;
    }

}
