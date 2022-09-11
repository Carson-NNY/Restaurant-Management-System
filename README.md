# Restaurant-Management-System
This is a restaurant management system, which has some basic features like booking tables, checking out and such.



commons-dbutils-1.3.jar /  druid-1.1.10.jar / mysql-connector-java-5.1.37-bin.jar ,druid.properties  are some basic files for performing connecting database pool.

Under 'Dao' package, BasicDAO.java / BillDAO.java / DiningTableDAO.java / EmployeeDAO.java / EmployeeInfoDAO.java /  MenuDAO.java /  MultiTableDAO.java are java files which help executing queries.

Under 'domain' package BasicDAO.java / BillDAO.java / DiningTableDAO.java / EmployeeDAO.java / EmployeeInfoDAO.java / MenuDAO.java / MultiTableDAO.java are correspond java files which reflect the relevant tables in database.

Under 'service' package, BillService.java / DiningTableService.java / EmployeeInfoService.java / EmployeeService.java / MenuService.java are used to perform all functions in a restaurant system.

Under 'utils' package, JDBCUtilesByDruid.java / Utility.java work as helper classes to help connect to database or restrict input.


Under 'view' package, MHLView.java are the main class which call the methods of other classes.
