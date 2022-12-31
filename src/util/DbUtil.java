package util;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class DbUtil {
    private String dbUrl="jdbc:mysql://localhost:3306/db_book";
    private String dbUsername="root";
    private String dbPassword="Nikolas114514";
    private String jdbcName="com.mysql.cj.jdbc.Driver";

    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        return DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
    }
    public void closeCon(Connection con)throws Exception{
        if(con!=null){
            con.close();
        }
    }
}
