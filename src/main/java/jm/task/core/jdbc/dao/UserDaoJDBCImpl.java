package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String CREATE_TABLE_SQL = """
                CREATE TABLE user (
                id BIGINT AUTO_INCREMENT PRIMARY KEY ,
                name VARCHAR(128) NOT NULL,
                lastName VARCHAR(128) NOT NULL,
                age TINYINT UNSIGNED NOT NULL
                );
                """;

        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            if (preparedStatement.executeUpdate() == 0) {
                System.out.println("Table created");
            }
        }
        catch (SQLException e) {
            System.out.println("User table already exists");
        }
    }

    public void dropUsersTable() {
        final String DROP_TABLE_SQL = """
                DROP TABLE user;
                """;

        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            if (preparedStatement.executeUpdate() == 0) {
                System.out.println("Table dropped");
            }
        }
        catch (SQLException e) {
            System.out.println("User table does not exist");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SAVE_USER_SQL = """
                INSERT INTO user (name, lastName, age)
                VALUES (?, ?, ?)
                """;

        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.printf("User с именем — %s %s добавлен в базу данных\n", name, lastName);
            }
        }
        catch (SQLException e) {
            System.out.println("Saving user failed");
        }
    }

    public void removeUserById(long id) {
        final String DELETE_USER_SQL = """
                DELETE
                FROM user
                WHERE id = ?
                """;

        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.printf("User с id — %d удален из базы данных\n", id);
            }
        }
        catch (SQLException e) {
            System.out.println("Saving user failed");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        final String GET_ALL_SQL = """
                SELECT id, name, lastName, age
                FROM user
                """;

        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        }
        catch (SQLException e) {
            System.out.println("Select user failed");
        }
        return users;
    }

    public void cleanUsersTable() {
        final String CLEAR_USER_SQL = """
                DELETE
                FROM user
                """;

        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(CLEAR_USER_SQL)) {
            System.out.printf("Удалено %d записей User из базы данных\n", preparedStatement.executeUpdate());
        }
        catch (SQLException e) {
            System.out.println("Saving user failed");
        }
    }
}
