import org.example.HibernateUtil;
import org.example.User;
import org.example.UserDAO;
import org.example.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TestUseService {

    private UserDAO userDAO;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // Создаем мок-объект UserDAO
        userDAO = Mockito.mock(UserDAO.class);
        // Передаем мок-объект в UserService
        userService = new UserService(userDAO);
    }

    @Test
    public void testCreateUser() {
        // Данные для нового пользователя
        String name = "John Doe";
        String email = "uioe@e.com";
        Integer age = 30;

        // Вызываем метод createUser
        userService.createUser(name, email, age);

        // Проверяем, что метод createUser у UserDAO был вызван с правильным объектом User
        verify(userDAO, times(1)).createUser(argThat(user ->
                user.getName().equals(name) &&
                        user.getEmail().equals(email) &&
                        user.getAge().equals(age)
        ));
    }

    @Test
    public void testGetUserById() {
        // Создаем тестового пользователя
        Long id = 46L;
        String name = "Jone";
        String email = "Dd@com.ru";
        Integer age = 10;

        // Создаем пользователя и устанавливаем значения
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);

        // Настраиваем поведение мокированного userDAO
        Mockito.when(userDAO.getUserById(id)).thenReturn(user);

        // Вызываем метод getUserById
        User retrievedUser = userService.getUserById(id);

        // Проверяем, что полученный пользователь совпадает с ожидаемым
        assertNotNull(retrievedUser);
        assertEquals(name, retrievedUser.getName());
        assertEquals(email, retrievedUser.getEmail());
        assertEquals(age, retrievedUser.getAge());
    }

    @Test
    public void testGetAllUsers() {
        // Создаем список тестовых пользователей
        List<User> users = new ArrayList<>();
        users.add(new User("Alice", "alice@example.com", 25));
        users.add(new User("Bob", "bob@example.com", 28));

        // Настраиваем поведение мокированного userDAO
        Mockito.when(userDAO.getAllUsers()).thenReturn(users);

        // Вызываем метод getAllUsers
        List<User> retrievedUsers = userService.getAllUsers();

        // Проверяем, что полученный список пользователей совпадает с ожидаемым
        assertEquals(2, retrievedUsers.size());
        assertEquals("Alice", retrievedUsers.get(0).getName());
        assertEquals("Bob", retrievedUsers.get(1).getName());
    }

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    public void testUpdateUser() {
        // Подготовка
        Long id = 1L;
        String name = "Jone";
        String email = "Dd@com.ru";
        Integer age = 10;

        User userToUpdate = new User();
        Mockito.when(userDAO.getUserById(id)).thenReturn(userToUpdate);

        // Действие
        userService.updateUser(id, name, email, age);

        Mockito.verify(userDAO).updateUser(userCaptor.capture());
        User value = userCaptor.getValue();
        assertEquals(name, value.getName());
        assertEquals(email, value.getEmail());
        assertEquals(age, value.getAge());
    }

    @Test
    public void testUpdateUserNull() {
        // Подготовка
        Long id = 3L;
        String name = "June";
        String email = "D@com.ru";
        Integer age = 10;

        User user = null;
        Mockito.when(userDAO.getUserById(id)).thenReturn(user);

        // Действие
        userService.updateUser(id, name, email, age);

        // Проверка, что updateUser не был вызван, так как пользователь не найден
        Mockito.verify(userDAO, never()).updateUser(any(User.class));
    }
}
