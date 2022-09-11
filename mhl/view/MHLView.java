package mhl.view;

import mhl.domain.*;
import mhl.service.*;
import mhl.utils.Utility;

import javax.rmi.CORBA.Util;
import java.util.List;

/**
 * the main class for executing the syetem
 */
public class MHLView {
    //note: if access different databases, need to go to JDBCUtilesByDruid and change  the content of druid.properties in
    // new FileInputStream("src/druid.properties") to the database and tables you need ，The contents of the table at present:
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
    private String key = ""; // Accept user input

    // Define EmployeeService object
    private EmployeeService employeeService = new EmployeeService();
    // Define a DiningTableService 
    private DiningTableService diningTableService = new DiningTableService();
    // Define a MenuService 
    private MenuService menuService = new MenuService();
    // Define a  BillService 
    private BillService billService = new BillService();
    private EmployeeInfoService employeeInfoService= new EmployeeInfoService();
    String empId="";

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    // show the main menu
    public  void mainMenu(){
        while (loop){

            System.out.println("====================Operating System================");
            System.out.println( "\t\t 1 Log in");
            System.out.println( "\t\t 2 Log out");
            System.out.println("Please enter your choice");
             key = Utility.readString(1);
             switch (key){
                 case "1":
                     System.out.print("Please enter the employee ID： " );
                     empId = Utility.readString(50);
                     System.out.print("Enter Your password： " );
                     String pwd = Utility.readString(50);
                     Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                     if( employee!=null ){
                         System.out.println("========Log in successfully{"+ employee.getName()+"}==========");
                         //Show secondary menu
                         while(loop){
                             System.out.println("========Second-level menu===============");
                             System.out.println("\t\t 1 Table status");
                             System.out.println("\t\t 2 Book a table");
                             System.out.println("\t\t 3 Show all dishes");
                             System.out.println("\t\t 4 Ordering service");
                             System.out.println("\t\t 5 View the bill");
                             System.out.println("\t\t 6 Check out");
                             System.out.println("\t\t 7 View this employee information");
                             System.out.println("\t\t 9 exit");
                             System.out.print("Please enter a selection：");
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
                                 case "7": 
                                     listemployee(empId);
                                     break;
                                 case "9":
                                     loop = false;
                                     break;
                                 default:
                                     System.out.println("invalid input");
                             }

                         }
                     }else{
                         System.out.println("Login failed");
                     }
                     break;
                 case "2":
                     loop = false;
                     break;
                 default:
                     System.out.println("Input error, please re-enter");

             }
        }
        System.out.println("exit");
    }

    public void listDiningTable(){
        System.out.println("\nTable number\t\tTable status");
        List<DiningTable> list = diningTableService.list();
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
    }

    public  void orderDiningTable() {
        System.out.println("===============Book a table===============");
        System.out.print("Please select the table number you want to book (- 1 exit)）： ");
        int orderId = Utility.readInt();
        if(orderId == -1){
            System.out.println("Cancel the reservation");
            return;
        }
        char c = Utility.readConfirmSelection();
        if(c == 'Y'){
            DiningTable diningTable= diningTableService.getDiningTableById(orderId);
        if(diningTable == null){
            System.out.println("The reserved table does not exist by the number.");
            return;
        }
        if( ! "null".equals(diningTable.getState())  ){
            System.out.println("The table has been occupied.");
            return;
        }
            System.out.print("Reservation person's name：");
            String orderName = Utility.readString(50);
            System.out.print("Reservation phone number：");
            String orderTel  = Utility.readString(50);
            if(diningTableService.orderDiningTable(orderId,orderName,orderTel)){
                System.out.println("===========Booking successful==============");
            }else{
                System.out.println("===========Booking failure===========");
            }
        }   else{
            System.out.println("===========Cancel the reservation==========");
        }
    }
    public  void listMenu(){
        System.out.println("\nDish number\t\tdish name\t\tCategory\t\tPrice");
        List<Menu> menus = menuService.listDish();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }
    public  void listBill(){

        System.out.println("\nID\t\tdish ID\t\tnumber of dish\t\tPrice\t\tTable number\t\tDate\t\t\t\t\t\t\tStatus\t\tDish Name\t\tPrice");
        List<MultiTableBean> multiTableBeans = billService.listMultiTable();
        for (MultiTableBean multiTableBean : multiTableBeans) {
            System.out.println(multiTableBean);
        }

    }


    public  void orderMenu() {
        System.out.println("==============Ordering service================");
        System.out.print("Please select the table number to be booked (- 1 exit)： ");
        int orderTableId = Utility.readInt();
        if(orderTableId   == -1){
            System.out.println("Cancel the order");
            return;
        }
        System.out.print("Please select the item number you want to order (- 1 exit): ");
        int orderMenuId = Utility.readInt();
        if( orderMenuId ==  -1){
            System.out.println("Cancel the order");
            return;
        }
        System.out.print("Please select the number of key dishes (- 1 exit)： ");
        int nums = Utility.readInt();

        if( nums  == -1){
            System.out.println("Cancel the order");
            return;
        }
        char c = Utility.readConfirmSelection();
        if(c == 'Y'){
            DiningTable diningTable= diningTableService.getDiningTableById(orderTableId);
            if(diningTable == null){
                System.out.println("The table doesn't exist.");
                return;
            }
            Menu menuDishById = menuService.getMenuDishById(orderMenuId);
            if(menuDishById == null){
                System.out.println("The ordered dish does not exist.");
                return;
            }

           if( billService.orderDish(orderMenuId,nums,orderTableId)){
               System.out.println("========= successfully=========");
           }else{
               System.out.println("=========Order Failed=========");
           }
        }
    }

    public void checkBill(){
        System.out.println("===============Checkout service===============");
        System.out.print("Please select the table number to check out (- 1 exit)： ");
        int orderTableId = Utility.readInt();
        if(orderTableId   == -1){
            System.out.println("Cancel the order");
            return;
        }

        DiningTable diningTableById = diningTableService.getDiningTableById(orderTableId);
        if(diningTableById == null){
            System.out.println("Invalid table number！");
            return;
        }

        if(! billService.hasPayBillbyDiningTableId(orderTableId)){
            System.out.println("There is no unpaid bill for this table.");
            return;
        }


        System.out.print("The method of checkout (cash / Alipay / Wechat) enter to indicate exit: ");
        String s = Utility.readString(50,"");
        if(!("cash".equals(s) || "Alipay".equals(s) ||"Wechat".equals(s))){
            System.out.println("Invalid way of payment");
            return;
        }
        char c = Utility.readConfirmSelection();
        if(c == 'Y'){
            boolean b = billService.checkOut(orderTableId,s);
            if(b){
                System.out.println("Check out successfully");
            }else{
                System.out.println("Failed to check out");
            }
        }
    }

    public void listemployee(String empId){

        EmployeeInfo employeeInfo = employeeInfoService.listEmployeeInfo(empId);
        System.out.println(employeeInfo);

    }
}
