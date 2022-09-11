package mhl.dao;

import mhl.utils.JDBCUtilesByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Develop BasicDAO, which is the parent class of other DAOs
 */
public class BasicDAO<T> {  

    private  QueryRunner qr =  new QueryRunner();   // The code here needs to import the jar package in advance（commons-dbutils-1.3）

    //Develop generic dml methods for arbitrary tables
    public int update(String sql, Object... parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);
            return update;

        } catch (SQLException e) {
            throw new RuntimeException(e);   //make complie exception-> runtime exception, Benefit: The caller can choose to capture it or not process it

        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }
    }

    //Returns multiple objects (that is, the result of the query is multiple rows), for any table
   // @param clazz Pass in a class object (needs to know what properties the class has.)
    // Create javabean objects through reflection, for example Actor.class)
    //  @param parameters pass in the value of ?,   Can be multiple values
    public List<T>  queryMulti(String sql,Class<T> clazz,Object... parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);  
        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }

    }


    //A general method for querying single-row results
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e);   
        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }
    }

    //The method of querying a single row and single column, that is, the method of returning a single value
    public Object queryScalar(String sql, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler(), parameters);


        } catch (SQLException e) {
            throw new RuntimeException(e); 
        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }

    }

}
