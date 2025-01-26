package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        final String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS user (
                id BIGINT AUTO_INCREMENT PRIMARY KEY ,
                name VARCHAR(128) NOT NULL,
                lastName VARCHAR(128) NOT NULL,
                age TINYINT UNSIGNED NOT NULL
                );
                """;

        try (Session session = Util.getConfiguration()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Error creating user table");
        }
    }

    @Override
    public void dropUsersTable() {

        final String CREATE_TABLE_SQL = """
                DROP TABLE IF EXISTS user;
                """;

        try (Session session = Util.getConfiguration()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Error dropping user table");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getConfiguration()) {

            session.beginTransaction();
            User user = new User(name, lastName, age);
            var result = session.save(user);
            System.out.printf("User с именем — %s %s добавлен в базу данных\n", name, lastName);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Saving user failed");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getConfiguration()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.printf("User с id — %d удален из базы данных\n", id);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Saving user failed");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getConfiguration()) {
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Select user failed");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getConfiguration()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            System.out.println("Записи User удалены из базы данных");
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Deleting user failed");
        }

    }
}
