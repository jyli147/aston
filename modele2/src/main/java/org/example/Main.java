package org.example;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // Создаем пользователя

        User newUser = new User("Анна", "anna@mail.com", 123);

        userDAO.createUser(newUser);

        // Получаем всех пользователей

        System.out.println("Список пользователей:");

        userDAO.getAllUsers().forEach(System.out::println);

        // Обновляем пользователя

        newUser.setName("Анна Смирнова");

        userDAO.updateUser(newUser);

        // Получаем пользователя по ID

        User retrievedUser = userDAO.getUserById(newUser.getId());

        System.out.println("Найден пользователь: " + retrievedUser);

        // Удаляем пользователя

        userDAO.deleteUser(newUser.getId());

        HibernateUtil.shutdown();

    }

}