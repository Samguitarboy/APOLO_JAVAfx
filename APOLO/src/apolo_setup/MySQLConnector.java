package apolo_setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnector {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSetMetaData metaData = null;
    StringBuffer result = new StringBuffer();

    public MySQLConnector(String connectionStr, String SQLStr) {
        try {
            conn = (Connection) DriverManager.getConnection(connectionStr);
            System.out.println("Database Connection !");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQLStr);

            metaData = rs.getMetaData();
            int numCol = metaData.getColumnCount();

            //print out detail
            while (rs.next()) {
                for (int i = 1; i <= numCol; i++) {
                    System.out.print(rs.getObject(i) + "\t");
                    result.append(rs.getObject(i) +"\n");
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public ResultSet getRs() {
        return rs;
    }

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    public StringBuffer getResult() {
        System.out.println(result);
        return result;
    }

}
