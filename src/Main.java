import views.View;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        View view = new View("toy.db");
        view.run();


    }

    /*public void addToy(Integer id, String name, Integer count, Integer weight)
    {
        this.array.add(new toy(id, name, count, weight));
    }*/


}