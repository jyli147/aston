package org.example;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = new UserDAO();
    }

    public void createUser(String name, String email, Integer age) {
        User user = new User(name, email, age);
        userDAO.createUser(user);
    }

    public User getUserById(Long id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            throw new RuntimeException("User  not found with id: " + id);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(Long id, String name, String email, Integer age) {
        User user = userDAO.getUserById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDAO.updateUser(user);
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }
}
