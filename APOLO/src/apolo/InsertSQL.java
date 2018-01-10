package apolo;

public class InsertSQL {

    public void recordtoDB(String username, int score, String songselect) {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String insertrecord = "insert into playrecord values(N'" + username + "'," + score + ",CURRENT_DATE,null,N'" + songselect + "');";
        MySQLConnector MSC_insert = new MySQLConnector();
        MSC_insert.connectDB(connectionStr);

        MSC_insert.doInsert(insertrecord);
    }
}
