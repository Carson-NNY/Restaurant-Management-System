package mhl.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Carson
 * @Version
 */
public class JDBCUtilesByDruid {

    private static DataSource ds;

    //在静态代码块完成 ds初始化
    static {
        Properties properties = new Properties();
        try {

            properties.load(new FileInputStream("src/druid.properties"));
            ds  = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 得到connection
    public static Connection getConnection() throws SQLException{
        return ds.getConnection();// 连接到数据库连接池
    }

    // 把连接放回连接池 （close不是真的断掉连接，而是把Connection对象放回数据库连接池
    public static void close(ResultSet resultSet, Statement statement,Connection connection){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close(); // close不是真的断掉连接，而是把Connection对象放回数据库连接池
                // 这里的connection的close是按照Druid的内部实现， 返回connection到连接池
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
