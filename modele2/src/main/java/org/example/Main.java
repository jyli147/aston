package org.example;


public class Main {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // Создаем пользователя

        User newUser1 = new User("Юлия", "jl@mail.com", 16);

        userDAO.createUser(newUser1);


        // Получаем всех пользователей

        System.out.println("Список пользователей:");

        userDAO.getAllUsers().forEach(System.out::println);

        // Обновляем пользователя

        newUser1.setName("Анна Смирнова");

        userDAO.updateUser(newUser1);

        // Получаем пользователя по ID

        User retrievedUser = userDAO.getUserById(newUser1.getId());

        System.out.println("Найден пользователь: " + retrievedUser);

        // Удаляем пользователя

        userDAO.deleteUser(newUser1.getId());

        HibernateUtil.shutdown();

    }

}