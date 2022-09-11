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
 * @author Carson
 * 开发BasicDAO， 是其他DAO的父类
 */
public class BasicDAO<T> {  // 泛型指定具体类型

    private  QueryRunner qr =  new QueryRunner();   // 这里的代码需要提前导入jar包（commons-dbutils-1.3）

    //开发通用的 dml 方法, 针对任意的表
    public int update(String sql, Object... parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);
            return update;

        } catch (SQLException e) {
            throw new RuntimeException(e);   //将编译异常->运行异常,抛出. 好处： 调用者
                                    // 可以选择捕获或者不处理它
        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }
    }

    //返回多个对象(即查询的结果是多行), 针对任意表
   // @param clazz 传入一个类的 Class 对象(底层需要知道这个类有哪些属性
    // 通过反射创建javabean对象， 比如 Actor.class)
    //  @param parameters 传入 ? 的具体的值，可以是多个值
    public List<T>  queryMulti(String sql,Class<T> clazz,Object... parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);   //将编译异常->运行异常,抛出. 好处： 调用者
            // 可以选择捕获或者不处理它
        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }

    }


    //查询单行结果 的通用方法
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e);   //将编译异常->运行异常,抛出. 好处： 调用者
            // 可以选择捕获或者不处理它
        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }
    }

    //查询单行单列的方法,即返回单值的方法
    public Object queryScalar(String sql, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtilesByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler(), parameters);


        } catch (SQLException e) {
            throw new RuntimeException(e);   //将编译异常->运行异常,抛出. 好处： 调用者
            // 可以选择捕获或者不处理它
        }finally {
            JDBCUtilesByDruid.close(null,null,connection);
        }

    }

}
