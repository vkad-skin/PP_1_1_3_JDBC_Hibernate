package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Name 1", "Lastname 1", (byte) 13);
        service.saveUser("Name 2", "Lastname 2", (byte) 54);
        service.saveUser("Name 3", "Lastname 3", (byte) 89);
        service.saveUser("Name 4", "Lastname 4", (byte) 25);

        service.getAllUsers().forEach(System.out::println);
        service.cleanUsersTable();
        service.dropUsersTable();

    }
}
