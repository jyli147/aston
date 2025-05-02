package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);

        // Создание пользователей
        userService.createUser("Миша", "mitr@example.com", 30);
        userService.createUser("Маша", "mopt@example.com", 25);

        // Получение всех пользователей
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);

        // Обновление пользователя
        userService.updateUser(1L, "Иван Петров", "p.petrov@example.com", 31);

        // Получение всех пользователей
        List<User> users1 = userService.getAllUsers();
        users1.forEach(System.out::println);

        // Удаление пользователя
        userService.deleteUser(4L);

        // Получение всех пользователей
        List<User> users2 = userService.getAllUsers();
        users2.forEach(System.out::println);
    }
}
