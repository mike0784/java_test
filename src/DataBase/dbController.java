package DataBase;

import java.sql.*;
import java.util.*;

public class dbController {
    private String fileDB;
    public Statement statmt;
    public ResultSet resSet;
    private List<String> pattern = Arrays.asList("id", "name", "count", "description");
    private String table = "toy";

    private static Connection connection;

    public dbController(String fileDB) throws SQLException, ClassNotFoundException {
        this.fileDB = fileDB;
        //DriverManager.registerDriver(new JDBC());
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.fileDB);
        this.createDB();
    }

    public void createDB()  throws SQLException, ClassNotFoundException
    {
        statmt = connection.createStatement();
        statmt.execute(String.format("CREATE TABLE if not exists '%s' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'count' INTEGER, 'description' text)", this.table));
    }

    public List<Dictionary> readDB() throws SQLException
    {
        List<Dictionary> result = new ArrayList<>();

        int count = 0;
        resSet = statmt.executeQuery(String.format("SELECT * FROM %s", this.table));
        while(resSet.next())
        {
            Dictionary<String, String> line = new Hashtable();
            for(int i = 0; i < pattern.size(); i++)
            {
                line.put(pattern.get(i), resSet.getString(pattern.get(i)));
            }
            result.add(line);
            count++;
        }
        return result;
    }

    public void insertDB(Dictionary<String, String> line) throws SQLException
    {
        //System.out.print(line.get());
        String query = String.format("INSERT INTO '%s' ('%s', '%s', '%s') VALUES ('%s', '%s', '%s')",
                this.table,
                pattern.get(1),
                pattern.get(2),
                pattern.get(3),
                line.get(pattern.get(1)),
                line.get(pattern.get(2)),
                line.get(pattern.get(3)));
        statmt.execute(query);
    }

    public void updateDB(Dictionary<String, String> line) throws SQLException
    {
        resSet = statmt.executeQuery(String.format("SELECT * FROM %s WHERE ID=%s", this.table, line.get(pattern.get(0))));
        if(resSet != null)
        {
            String query = String.format(String.format("UPDATE %s SET %s='%s', %s=%s, %s='%s' WHERE id=%s",
                    this.table,
                    pattern.get(1),
                    line.get(pattern.get(1)),
                    pattern.get(2),
                    line.get(pattern.get(2)),
                    pattern.get(3),
                    line.get(pattern.get(3)),
                    line.get(pattern.get(0))));
            System.out.println(query);
            statmt.execute(query);
        }
        else{
            System.out.format("Записи с id=%s не существует", line.get(pattern.get(0)));
        }

    }

    public void Close() throws ClassNotFoundException, SQLException
    {
        connection.close();
        statmt.close();
        if(this.resSet != null) resSet.close();
        System.out.println("Соединения закрыты");
    }
}
