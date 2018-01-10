package apolo;

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

    public int connectDB(String connectionStr) {
        try {
            conn = (Connection) DriverManager.getConnection(connectionStr);
            System.out.println("Connection Success !");
        } catch (SQLException ex) {
            System.out.println("SQLException" + ex.getMessage());
            System.out.println("SQLState" + ex.getSQLState());
            System.out.println("VendorError" + ex.getErrorCode());
            return -1;
        }

        return 1;
    }

    public void closeconnection() {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public void doInsert(String SQLStr) {
        try {
            stmt = conn.createStatement();
            if (stmt.executeUpdate(SQLStr) == 1) {
                System.out.println("doInsert Success !");
            } else {
                System.out.println("doInsert Fail !");
            }

        } catch (SQLException sqlex) {
            System.out.println("InsertError");
            sqlex.printStackTrace();
        }
    }

    public void doUpdate(String SQLStr) {
        try {
            stmt = conn.createStatement();
            if (stmt.executeUpdate(SQLStr) == 1) {
                System.out.println("doUpdate Success !");
            } else {
                System.out.println("doUpdate Fail !");
            }

        } catch (SQLException sqlex) {
            System.out.println("UpdateError");
            sqlex.printStackTrace();
        }
    }

    public void doquery(String SQLStr) {

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQLStr);

            metaData = rs.getMetaData();
            int numCol = metaData.getColumnCount();

            //print out column detail
            /*for (int i = 1; i <= numCol; i++) {
                //System.out.print(metaData.getColumnName(i)+ "\t");
                result.append(metaData.getColumnName(i) + "\t");
            }
            //System.out.println();
            result.append("\n");*/
            //print out detail
            while (rs.next()) {
                for (int i = 1; i <= numCol; i++) {
                    //System.out.print(rs.getObject(i) + "\t");
                    result.append(rs.getObject(i) + "\t");
                }
                //System.out.println();
                result.append("\n");
            }

        } catch (SQLException sqlex) {
            System.out.println("QueryError");
            sqlex.printStackTrace();
        }

    }

    public void doDelete(String SQLStr) {

        try {
            stmt = conn.createStatement();
            if (stmt.executeUpdate(SQLStr) == 1) {
                System.out.println("Delete Success !");
            } else {
                System.out.println("Delete Fail !");
            }

        } catch (SQLException sqlex) {
            System.out.println("DeleteError");
            sqlex.printStackTrace();
        }

    }

    public void clearresult() {
        result.delete(0, result.length());
    }

    public ResultSet getRs() {
        return rs;
    }

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    public StringBuffer getResult() {
        //System.out.println(result);
        return result;
    }

}
