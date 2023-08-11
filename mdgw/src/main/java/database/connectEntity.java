package database;

import com.clickhouse.jdbc.ClickHouseDataSource;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Getter
public class connectEntity {
    String driverName;
    String url;
    String user;
    String password;

    public connectEntity(String driverName, String url, String user, String password) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection connection() {
        Connection conn=null;
        try {
            Properties properties = new Properties();
            properties.setProperty("sslmode", "NONE");
            ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
            conn = dataSource.getConnection("admin","password");
        }catch(Exception e){
            System.out.println("connection fail ,please check your entities");
        }
        return conn;
    }

    public void close(AutoCloseable... closes){
        for(AutoCloseable close : closes){
            if(close != null){
                try {
                    close.close();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    close = null;
                }
            }
        }
    }

//    public boolean insert(Connection connection, String sql, String... para){
//
//    }

}
