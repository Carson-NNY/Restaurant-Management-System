package mhl.view;

import mhl.domain.*;
import mhl.service.*;
import mhl.utils.Utility;

import javax.rmi.CORBA.Util;
import java.util.List;

/**
 * @author Carson
 * This is the main view
 */
public class MHLView {
    //注意： 更换访问不同的数据库时要到JDBCUtilesByDruid里把new FileInputStream("src/druid.properties")
    //里面的druid.properties的文件里的内容改变，去访问自己需要的数据库和表 ，目前那表内容：
    //driverClassName=com.mysql.jdbc.Driver
    //url=jdbc:mysql://localhost:3306/mhl?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    //username=root
    //password=newpass
    //initialSize=10
    //minIdle=5
    //maxActive=50
    //maxWait=5000



    // control it whether to exist from the main menu
    private boolean loop =  true;
    private String key = ""; // 接受用户的输入

    // 定义EmployeeService属性对象
    private EmployeeService employeeService = new EmployeeService();
    // 定义一个DiningTableService一个属性
    private DiningTableService diningTableService = new DiningTableService();
    // 定义一个MenuService 属性
    private MenuService menuService = new MenuService();
    // 定义一个BillService 属性
    private BillService billService = new BillService();
    // 扩展的分表设计（员工登陆表和员工信息表）
    private EmployeeInfoService employeeInfoService= new EmployeeInfoService();
    String empId="";

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    // show the main menu
    public  void mainMenu(){
        while (loop){

            System.out.println("====================满汉楼================");
            System.out.println( "\t\t 1 登陆满汉楼");
            System.out.println( "\t\t 2 退出满汉楼");
            System.out.println("请输入你的选择");
             key = Utility.readString(1);
             switch (key){
                 case "1":
                     System.out.print("请输入员工号： " );
                     empId = Utility.readString(50);
                     System.out.print("请输入密码： " );
                     String pwd = Utility.readString(50);
                     Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                     if( employee!=null ){
                         System.out.println("========登陆成功{"+ employee.getName()+"}==========");
                         //显示二级菜单
                         while(loop){
                             System.out.println("========二级菜单===============");
                             System.out.println("\t\t 1 餐桌状态");
                             System.out.println("\t\t 2 预定餐桌");
                             System.out.println("\t\t 3 显示所有菜品");
                             System.out.println("\t\t 4 点餐服务");
                             System.out.println("\t\t 5 查看账单");
                             System.out.println("\t\t 6 结账");
                             System.out.println("\t\t 7 查看此员工信息");
                             System.out.println("\t\t 9 退出满汉楼");
                             System.out.print("请输入选择：");
                             key = Utility.readString(1);
                             switch (key){
                                 case "1":
                                     listDiningTable();
                                     break;
                                 case "2":
                                     orderDiningTable();
                                     break;
                                 case "3":
                                     listMenu();
                                     break;
                                 case "4":
                                     orderMenu();
                                     break;
                                 case "5":
                                     listBill();
                                     break;
                                 case "6":
                                     checkBill();
                                 break;
                                 case "7":  // 老韩扩展的分表设计，（员工登陆表和员工信息表分开）
                                     listemployee(empId);
                                     break;
                                 case "9":
                                     loop = false;
                                     break;
                                 default:
                                     System.out.println("你的输入有误");
                             }

                         }
                     }else{
                         System.out.println("登陆失败");
                     }
                     break;
                 case "2":
                     loop = false;
                     break;
                 default:
                     System.out.println("输入有误，请重新输入");

             }
        }
        System.out.println("退出满汉楼");
    }

    public void listDiningTable(){
        System.out.println("\n餐桌编号\t\t餐桌状态");
        List<DiningTable> list = diningTableService.list();
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
    }

    public  void orderDiningTable() {
        System.out.println("===============预定餐桌===============");
        System.out.print("请选择要预定的餐桌编号（-1退出）： ");
        int orderId = Utility.readInt();
        if(orderId == -1){
            System.out.println("取消预定餐桌");
            return;
        }
        char c = Utility.readConfirmSelection();
        if(c == 'Y'){
            DiningTable diningTable= diningTableService.getDiningTableById(orderId);
        if(diningTable == null){
            System.out.println("预定的餐桌不存在");
            return;
        }
        if( ! "空".equals(diningTable.getState())  ){
            System.out.println("该餐位已经被占用");
            return;
        }
            System.out.print("预定人名字：");
            String orderName = Utility.readString(50);
            System.out.print("预定人电话：");
            String orderTel  = Utility.readString(50);
            if(diningTableService.orderDiningTable(orderId,orderName,orderTel)){
                System.out.println("===========预定成功==============");
            }else{
                System.out.println("===========预定失败===========");
            }
        }   else{
            System.out.println("===========取消预定餐桌==========");
        }
    }
    public  void listMenu(){
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        List<Menu> menus = menuService.listDish();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }
    public  void listBill(){
//        System.out.println("\n编号\t\t菜品号\t\t9菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
//        List<Bill> bills = billService.listBill();
//        for (Bill bill : bills) {
//            System.out.println(bill);
        System.out.println("\n编号\t\t菜品号\t\t9菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜品名\t\t价格");
        List<MultiTableBean> multiTableBeans = billService.listMultiTable();
        for (MultiTableBean multiTableBean : multiTableBeans) {
            System.out.println(multiTableBean);
        }

    }


    // 点餐
    public  void orderMenu() {
        System.out.println("===============点餐服务===============");
        System.out.print("请选择要预定的餐桌编号（-1退出）： ");
        int orderTableId = Utility.readInt();
        if(orderTableId   == -1){
            System.out.println("取消点餐");
            return;
        }
        System.out.print("请选择要预定的菜品编号（-1退出）： ");
        int orderMenuId = Utility.readInt();
        if( orderMenuId ==  -1){
            System.out.println("取消点餐");
            return;
        }
        System.out.print("请选择要点菜品数量（-1退出）： ");
        int nums = Utility.readInt();

        if( nums  == -1){
            System.out.println("取消点餐");
            return;
        }
        char c = Utility.readConfirmSelection();
        if(c == 'Y'){
            DiningTable diningTable= diningTableService.getDiningTableById(orderTableId);
            if(diningTable == null){
                System.out.println("餐桌不存在");
                return;
            }
            Menu menuDishById = menuService.getMenuDishById(orderMenuId);
            if(menuDishById == null){
                System.out.println("预定的菜品不存在");
                return;
            }

           if( billService.orderDish(orderMenuId,nums,orderTableId)){
               System.out.println("=========点餐成功=========");
           }else{
               System.out.println("=========点餐失败=========");
           }
        }
    }

    public void checkBill(){
        System.out.println("===============结账服务===============");
        System.out.print("请选择要结账的餐桌编号（-1退出）： ");
        int orderTableId = Utility.readInt();
        if(orderTableId   == -1){
            System.out.println("取消点餐");
            return;
        }

        DiningTable diningTableById = diningTableService.getDiningTableById(orderTableId);
        if(diningTableById == null){
            System.out.println("无效餐桌号！");
            return;
        }

        if(! billService.hasPayBillbyDiningTableId(orderTableId)){
            System.out.println("该餐位没有未结账账单");
            return;
        }


        System.out.print("结账的方式（现金/支付宝/微信） 回车表示退出： ");
        String s = Utility.readString(50,"");
        if(!("现金".equals(s) || "支付宝".equals(s) ||"微信".equals(s))){
            System.out.println("无效支付方式");
            return;
        }
        char c = Utility.readConfirmSelection();
        if(c == 'Y'){
            boolean b = billService.checkOut(orderTableId,s);
            if(b){
                System.out.println("结账成功");
            }else{
                System.out.println("结账失败");
            }
        }
    }

    public void listemployee(String empId){

        EmployeeInfo employeeInfo = employeeInfoService.listEmployeeInfo(empId);
        System.out.println(employeeInfo);

    }
}
