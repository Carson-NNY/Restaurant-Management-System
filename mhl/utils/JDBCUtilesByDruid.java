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
 * A util class for JDBC operation
 */
public class JDBCUtilesByDruid {

    private static DataSource ds;

    //Complete ds initialization in a static code block
    static {
        Properties properties = new Properties();
        try {

            properties.load(new FileInputStream("src/druid.properties"));
            ds  = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // get a connection
    public static Connection getConnection() throws SQLException{
        return ds.getConnection();// Connect to the database connection pool
    }

    // Put the connection back into the connection pool (instead of actually breaking the connection, 
    // close puts the Connection object back into the database connection pool
    public static void close(ResultSet resultSet, Statement statement,Connection connection){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close(); 
                // The close of connection here is implemented according to the internal implementation of Druid 
                // and returns connection to the connection pool.
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
