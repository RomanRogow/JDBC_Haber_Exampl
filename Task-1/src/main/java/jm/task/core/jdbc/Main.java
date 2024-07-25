package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

//        userService.createUsersTable();
//        userService.getAllUsers();
//        userService.dropUsersTable();
//        userService.createUsersTable();
//        userService.saveUser("Егор", "Петров", (byte) 36);
//        userService.saveUser("Иван","Иванов", (byte) 34);
//        userService.saveUser("Денис","Юрьевич", (byte) 37);
//        userService.saveUser("Нури","Карндал", (byte) 39);
//        userService.removeUserById(7);
//        userService.getAllUsers();
//        userService.cleanUsersTable();
//        userService.dropUsersTable();

//        userService.createUsersTable();
        userService.dropUsersTable();

        // реализуйте алгоритм здесь
    }
}
