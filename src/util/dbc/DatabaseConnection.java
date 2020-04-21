package util.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
public class DatabaseConnection {
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();
    private static final String DBDRIVER = "com.mysql.jdbc.Driver" ;
    private static final String DBURL = "jdbc:mysql://192.168.43.108:3306/crmdb" ;
    private static final String DBUSER = "root" ;
    private static final String PASSWORD = "mysqladmin" ;
    public static void close() {
        Connection conn = THREAD_LOCAL.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            THREAD_LOCAL.remove();//删除掉threadLocal中保存的链接对象，这样就不会再连接,彻底关闭
        }
    }
    public static Connection get() {
        Connection conn = THREAD_LOCAL.get();
        try {
            if (conn == null) {
                conn = rebuildConnection();
                THREAD_LOCAL.set(conn);
            }
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Connection rebuildConnection()throws Exception {
        Connection conn=null;
        Class.forName(DBDRIVER);//向容器中加载驱动程序
        return DriverManager.getConnection(DBURL, DBUSER, PASSWORD);

    }
}


