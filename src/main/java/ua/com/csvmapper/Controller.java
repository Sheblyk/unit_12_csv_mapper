package ua.com.csvmapper;

import ua.com.csvmapper.entity.User;
import ua.com.csvmapper.mapper.TableMapper;
import ua.com.csvmapper.table.CSVTable;

import java.util.List;

public class Controller {

    public void run(){
        CSVTable csvTable = new CSVTable("input.csv");
        System.out.println("Operation with table structure:\nGet [2][6]\n" + csvTable.getElementByIndexes(2, 6));
        System.out.println("Get [1][email]\n" + csvTable.getElementByName(1, "email"));
        System.out.println("Get all headers\n" + csvTable.getHeaders() + "\n");
        TableMapper tableMapper = new TableMapper();
        List<User> users = tableMapper.map(csvTable, User.class);
        System.out.println("Result of mapping: ");
        for (User c : users) {
            System.out.println(c);
        }
    }
}
