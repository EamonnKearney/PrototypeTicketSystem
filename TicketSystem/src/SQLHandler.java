import java.sql.*;
import java.util.ArrayList;

/**Credit to Rafail Kaufman*/
public class SQLHandler {
    static Connection c;
    static {
        try {
            //Database configurations
            //As applicable
            c = DriverManager.getConnection("jdbc:mysql://localhost:3308/dbtest",
                    "<<<enter-username>>>", "<<<enter-pass>>>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ResultSet execute(String query) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Statement stm = c.createStatement();
            return stm.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Something went wrong with SQL query execution");
            throw e;
        }
    }
    public static int executeUpdate(String query) throws SQLException {
        try {
            Statement stm = c.createStatement();
            return stm.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Something went wrong with SQL query execution");
            throw e;
        }
    }

    public static void INSERT(String tableName, String[] columns, String[] values, int quantity) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i != columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(") VALUES");
        for (int q = 0; q < quantity; q++) {
            query.append(" (");
            for (int i = 0; i < values.length; i++) {
                query.append(values[i]);
                if (i != values.length - 1) {
                    query.append(", ");
                }
            }
            if (q!=quantity - 1){

                query.append("),");
            } else {
                query.append(")");
            }
        }
        System.out.println(query);
        executeUpdate(query.toString());
    }

    public static void printResult(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            System.out.println(rs);
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                if (i > 1) {
                    System.out.print(",  ");
                }
                String value = rs.getString(i);
                System.out.print(value + " " + rsmd.getColumnName(i));
            }
        }
    }

    public static ArrayList<String> getTableNames() throws SQLException, ClassNotFoundException {
        ArrayList<String> tableNames = new ArrayList<>();
        ResultSet rs = execute("SHOW TABLES");
        ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {

                tableNames.add(rs.getString(i));

            }
        }
        return tableNames;
    }
}