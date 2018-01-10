package apolo;

public class deleteSQL {

    public void deletesong(String songselect) {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String deletesong = "delete from Songlist where Song_Title = '" + songselect + "';";

        MySQLConnector MSC = new MySQLConnector();
        MSC.connectDB(connectionStr);
        //delete
        MSC.doDelete(deletesong);
        MSC.clearresult();
    }
}
