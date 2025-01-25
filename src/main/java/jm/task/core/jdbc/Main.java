package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 32);
        userService.saveUser("Petr", "Petrov", (byte) 24);
        userService.saveUser("Semen", "Semyonov", (byte) 45);
        userService.saveUser("James", "Bond", (byte) 40);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
