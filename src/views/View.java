package views;

import toys.toy;
import DataBase.dbController;

import java.sql.SQLException;
import java.util.*;
import java.io.*;

public class View {
    public ArrayList<toy> array = new ArrayList<>();
    public dbController db;
    private List<String> pattern = Arrays.asList("id", "name", "count", "description");
    private String fileDB;

    private ArrayList<toy> prize = new ArrayList<>();

    public View(String fileDB) {
        this.fileDB = fileDB;
    }

    public void run() throws SQLException, ClassNotFoundException {
        Commands com;
        this.db = new dbController(this.fileDB);
        String input = "";

        while (true){
            Scanner in = new Scanner(System.in);
            System.out.print("Введите команду: ");
            input = in.nextLine();
            com = Commands.valueOf(input.toUpperCase());
            if(com == Commands.EXIT)
            {
                db.Close();
                return;
            }
            switch (com)
            {
                case ADD:
                    this.insertToy();
                    break;
                case READ:
                    this.readToy();
                    break;
                case VIEW:
                    this.viewToys();
                    break;
                case UPDATE:
                    this.updateToy();
                    break;
                case RAFFLE:
                    this.raffle();
                    break;
                case OUTPRIZE:
                    this.outputPrize();
                    break;
                default:
                    System.out.println("Команда не найдена");
            }
        }
    }

    public void insertToy()
    {
        String name = this.prompt("Введите название игрушки: ");
        String count = this.prompt("Введите количество игрушек: ");
        String description = this.prompt("Описание игрушки: ");
        Dictionary<String, String> result = new Hashtable<>();
        result.put(this.pattern.get(1), name);
        result.put(this.pattern.get(2), count);
        result.put(this.pattern.get(3), description);
        try {
            this.db.insertDB(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readToy() throws SQLException {
        List<Dictionary> lines = this.db.readDB();
        for(int i = 0; i < lines.size(); i++)
        {
            Dictionary<String, String> line = new Hashtable<>();
            line = lines.get(i);
            this.array.add(new toy(Integer.parseInt(line.get(pattern.get(0))), line.get(pattern.get(1)), Integer.parseInt(line.get(pattern.get(2))), 0, line.get(pattern.get(3))));
        }
    }

    public void viewToys()
    {
        if(this.array.size() == 0)
        {
            System.out.println("Сначала прочитайте БД.\nЕсли БД пуста, то внесите туда игрушки");
        }
        else{
            for(int i = 0; i < this.array.size(); i++)
            {
                //toy line = new Hashtable<>();
                //line = this.array.get(i);
                String result = String.format("ID: %d\nНазвание: %s\nКоличество: %d\nВес: %d\nОписание: %s",
                        this.array.get(i).getId(),
                        this.array.get(i).getName(),
                        this.array.get(i).getCount(),
                        this.array.get(i).getWeight(),
                        this.array.get(i).getDescription());
                System.out.println(result);
                System.out.println("--------------------------------------------------------------------");
            }
        }
    }

    public void updateToy() throws SQLException {
        String id = this.prompt("Введите ID игрушки: ");
        String name = this.prompt("Введите название игрушки: ");
        String count = this.prompt("Введите количество игрушек: ");
        String description = this.prompt("Введите описание игрушки: ");

        Dictionary<String, String> line = new Hashtable();
        line.put(pattern.get(0), id);
        line.put(pattern.get(1), name);
        line.put(pattern.get(2), count);
        line.put(pattern.get(3), description);
        this.db.updateDB(line);
    }

    private void raffle()
    {
        Random random = new Random();
        int pos = random.nextInt(this.array.size());
        if(this.array.get(pos).getCount() != 0)
        {
            this.array.get(pos).setCount(this.array.get(pos).getCount() - 1);
            prize.add(this.array.get(pos));
        }
    }

    public void outputPrize()
    {
        if(this.array.size() != 0)
        {
            try(FileWriter writer = new FileWriter("prize.txt", true))
            {
                String text = this.array.get(0).getName();
                writer.write(text);
                // запись по символам
                writer.append('\n');
                writer.append('E');
                writer.flush();
            }
            catch(IOException ex){
                System.out.println("Ошибка записи в файл. " + ex.getMessage());
            }
            this.array.remove(0);
        }
        else{
            System.out.println("Больше призов нет");
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

}
