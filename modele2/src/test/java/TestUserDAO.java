import org.example.HibernateUtil;
import org.example.User;
import org.example.UserDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserDAO {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("postgress");

    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        HibernateUtil.setDatabase(url,
                postgresContainer.getUsername(),
                postgresContainer.getPassword());
        userDAO = new UserDAO();
    }

    @BeforeEach
    void cleanUpDatabase() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeMutationQuery("TRUNCATE TABLE users RESTART IDENTITY").executeUpdate();
            session.getTransaction().commit();
        }
    }

      // Тест для проверки создания пользователя
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setName("J");
        user.setEmail("j@com.ru");
        user.setAge(27);
        userDAO.createUser(user);
        assertNotNull(user.getId());
    }

    // Тест для проверки получения всех пользователей
    @Test
    public void testGetAllUsers() {
        // Создаем двух пользователей
        User usertest1 = new User();
        usertest1.setName("Jyli");
        usertest1.setEmail("jyl@com.ru");
        usertest1.setAge(27);
        userDAO.createUser(usertest1);

        User usertest2 = new User();
        usertest2.setName("Jl");
        usertest2.setEmail("jyl@com.ru");
        usertest2.setAge(27);
        userDAO.createUser(usertest2);

        // Получаем всех пользователей из базы данных
        List<User> users = userDAO.getAllUsers();

        // Проверяем, что количество пользователей равно 2
        assertEquals(2, users.size());
    }

    // Тест для проверки обновления пользователя
    @Test
    public void testUpdateUser() {
        // Создаем нового пользователя
        User user = new User("Charlie", "char@example.com", 35);
        userDAO.createUser(user); // Сохраняем пользователя в базе данных

        // Обновляем имя пользователя
        user.setName("Charlie Updated");
        userDAO.updateUser(user); // Сохраняем изменения

        // Извлекаем обновленного пользователя
        User updatedUser = userDAO.getUserById(user.getId());
        // Проверяем, что имя обновлено
        assertEquals("Charlie Updated", updatedUser.getName());
    }

    // Тест для проверки удаления пользователя
    @Test
    public void testDeleteUser() {
        // Создаем нового пользователя
        User user = new User("Dave", "dave@example.com", 40);
        userDAO.createUser(user); // Сохраняем пользователя в базе данных

        // Удаляем пользователя
        userDAO.deleteUser(user.getId());
        // Проверяем, что пользователь был удален
        User deletedUser = userDAO.getUserById(user.getId());
        assertNull(deletedUser); // Убедитесь, что пользователь теперь null
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setName("jylia147");
        user.setEmail("jylia147@ma.ru");
        user.setAge(12);
        userDAO.createUser(user);
        User retrievedUser = userDAO.getUserById(user.getId());
        assertEquals("jylia147", retrievedUser.getName());
        assertEquals("jylia147@ma.ru", retrievedUser.getEmail());
    }
}

